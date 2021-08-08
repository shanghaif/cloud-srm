package com.midea.cloud.srm.po.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.enums.contract.ManagementControlModel;
import com.midea.cloud.common.enums.pm.po.PurchaseOrderEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.repush.service.RepushHandlerService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.contract.ContractClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.cm.contract.entity.ContractMaterial;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.po.order.service.IOrderCheckService;
import com.midea.cloud.srm.po.workflow.OrderFlow;
import com.midea.cloud.srm.pr.documents.service.ISubsequentDocumentsService;
import com.midea.cloud.srm.pr.requirement.service.IRequirementHeadService;
import com.midea.cloud.srm.pr.requirement.service.IRequirementLineService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  订单个性化校验逻辑
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/12/8 下午 02:10
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class OrderCheckServiceImpl implements IOrderCheckService {

    @Autowired
    private SupplierClient supplierClient;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private ContractClient contractClient;

    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private IRequirementLineService iRequirementLineService;

    @Autowired
    private OrderFlow orderFlow;

    @Autowired
    private ISubsequentDocumentsService iSubsequentDocumentsService;

    @Autowired
    private RepushHandlerService repushHandlerService;

    @Autowired
    private IRequirementHeadService requirementHeadService;

    @Autowired
    private IOrderDetailService scIOrderDetailService;

    @Autowired
    private IOrderService scIOrderService;


    /**
     * 校验是否有足够的下单数量
     * @param orderDetailList
     */
    @Override
    public void checkOrderNumEnough(List<OrderDetail> orderDetailList ){
        Set<Long> requirementIds = orderDetailList.stream().filter(o -> Objects.nonNull(o.getCeeaRequirementLineId()))
                .map(o -> o.getCeeaRequirementLineId()).collect(Collectors.toSet());
        if(requirementIds.isEmpty()){
            return ;
        }
        //订单行当前数量 + 非废弃状态订单(ABANDONED) <= 需求行数量/
        //获取采购申请 最大数量
        StringBuilder sb = new StringBuilder("物料编码：");
        List<OrderDetail> afterDetailList = iRequirementLineService.getRequirementQuantityForOrderCheck(orderDetailList);
        Map<Long , BigDecimal> reqMaxQuantity = afterDetailList.stream().collect(Collectors.toMap(
                o-> o.getCeeaRequirementLineId() , o-> Optional.ofNullable(o.getRequirementLineTotalQuantity()).orElse(BigDecimal.ZERO) , (o, o2) -> o)
        );
        //本次下单数量
        Map<Long , BigDecimal> currentQuantity = orderDetailList.stream().collect(Collectors.toMap(
                o -> o.getCeeaRequirementLineId() , o-> Optional.ofNullable(o.getOrderNum()).orElse(BigDecimal.ZERO) , (o, o2) -> o
        ));
        Map<Long , OrderDetail> reqOrderDetailMap = orderDetailList.stream().collect(Collectors.toMap(
                o -> o.getCeeaRequirementLineId() , o-> o , (o, o2) -> o
        ));
        //获取采购申请的 历史 订单
        QueryWrapper<OrderDetail> wrapper = new QueryWrapper<>();
        wrapper.in("CEEA_REQUIREMENT_LINE_ID",requirementIds);
        List<OrderDetail> dbHistoryOrderDetails = scIOrderDetailService.list(wrapper);
        //获取采购申请的 历史  废弃订单id
        List<Long> orderIdList = dbHistoryOrderDetails
                .stream().map(o -> o.getOrderId()).collect(Collectors.toList());
        Set<Long> abandonedSet = (orderIdList == null || orderIdList.size() == 0 ? new ArrayList<Order>() : scIOrderService.listByIds(dbHistoryOrderDetails
                .stream().map(o -> o.getOrderId()).collect(Collectors.toList())))
                .stream().filter(order -> PurchaseOrderEnum.ABANDONED.getValue().equals(order.getOrderStatus()))
                .map(order -> order.getOrderId())
                .collect(Collectors.toSet());
        //从历史数据中移除本次数据,移除废弃的
        Set<Long> inputOrderDetailIds = orderDetailList.stream().map(o -> o.getOrderDetailId()).collect(Collectors.toSet());
        List<OrderDetail> historyOrderDetails = dbHistoryOrderDetails.stream()
                .filter(o -> !inputOrderDetailIds.contains(o.getOrderDetailId()))
                .filter(o -> !abandonedSet.contains(o.getOrderId())).collect(Collectors.toList());
        boolean reqNumOverflow = false;
        Map<Long , List<OrderDetail>> historyOrderDetailGroup = historyOrderDetails.stream()
                .collect(Collectors.groupingBy(OrderDetail::getCeeaRequirementLineId));
        for(Long requirementId : historyOrderDetailGroup.keySet()){
            List<OrderDetail> orderDetails = historyOrderDetailGroup.get(requirementId);
            if(orderDetails.isEmpty()){
                continue;
            }
            BigDecimal allReqQuantity = Objects.isNull(currentQuantity.get(requirementId)) ?
                    BigDecimal.ZERO : currentQuantity.get(requirementId);
            for(OrderDetail orderDetail : orderDetails){
                BigDecimal downOrderNum = Objects.isNull(orderDetail.getOrderNum()) ?
                        BigDecimal.ZERO : orderDetail.getOrderNum();
                allReqQuantity = allReqQuantity.add(downOrderNum);
            }
            BigDecimal maxQuantity = reqMaxQuantity.get(requirementId);
            if(allReqQuantity.compareTo(maxQuantity) > 0){
                reqNumOverflow = true;
                OrderDetail overFlowDetail = reqOrderDetailMap.get(requirementId);
                sb.append("[").append(overFlowDetail.getMaterialCode()).append("(")
                        .append(allReqQuantity.setScale(2 , BigDecimal.ROUND_HALF_UP)).append("/")
                        .append(maxQuantity.setScale(2,BigDecimal.ROUND_HALF_UP)).append(")]、");
            }
        }
        if(reqNumOverflow){
            throw  new BaseException(sb.append("已经超出采购申请数量，请确认!").toString());
        }
    }

    /**
     * 校验是否符合合同要求（金额/数量）
     */
    @Override
    public void verifyTheOrderRelatedContractInfo(List<OrderDetail> orderDetailList){
        if(orderDetailList.isEmpty()){ return ; }

        //1.获取订单价格来源是合同的
        final String CONTRACT_TYPE  = "CONTRACT";
        List<OrderDetail> thisVerificationOrderDetails = orderDetailList.stream().filter(
                o -> CONTRACT_TYPE.equals(o.getCeeaPriceSourceType())
        ).collect(Collectors.toList());
        if(thisVerificationOrderDetails.isEmpty()){ return ; }

        //2.获取订单行信息上对应的合同物料信息
        List<ContractMaterial> contractMaterialList = new ArrayList<>();
        Set<Long> priceSourceIds = thisVerificationOrderDetails.stream().map(
                p -> Optional.ofNullable(p.getCeeaPriceSourceId()).orElse(-1l)
        ).collect(Collectors.toSet());
        for(Long sourceId : priceSourceIds){
            ContractMaterial contractMaterial = new ContractMaterial().setContractMaterialId(sourceId);
            contractMaterialList.add(contractMaterial);
        }
        contractMaterialList = contractClient.listContractMaterialMore(contractMaterialList);
        if(contractMaterialList.isEmpty()){ return ; }

        //3.获取合同信息
        List<ContractHead> contractHeadList = new ArrayList<>();
        Set<Long> contractHeadIds = contractMaterialList.stream().map(
                c -> Optional.ofNullable(c.getContractHeadId()).orElse(-1l)
        ).collect(Collectors.toSet());
        for(Long contractHeadId : contractHeadIds){
            ContractHead contractHead = new ContractHead().setContractHeadId(contractHeadId);
            contractHeadList.add(contractHead);
        }
        contractHeadList = contractClient.listContractHeadMore(contractHeadList);
        if(contractHeadList.isEmpty()){ return ;}

        //* 再根据 b.CONTRACT_HEAD_ID 找到合同头表 的CEEA_CONTROL_METHOD（管控方式） 字段 有五种类型
//* 				无 -- return ;
//* 				金额 -- 回到b表 根据b表的金额 - 初始化金额（b.CEEA_INIT_AMOUNT）  >= 所有订单（非取消和非拟定状态）的订单行的CEEA_PRICE_SOURCE_ID 对应的金额之和（a.CEEA_AMOUNT_INCLUDING_TAX）
//* 				数量、--同上，只不过金额变成了数量
//* 				金额+数量 --金额&&数量校验都要通过
//* 				总金额 -- 根据b表找到头表,用头表计算
        verifyTheOrderRelatedContract(thisVerificationOrderDetails , contractMaterialList , contractHeadList);
    }

  

    private void verifyTheOrderRelatedContract(List<OrderDetail> thisVerificationOrderDetails ,
                                               List<ContractMaterial> contractMaterialList ,
                                               List<ContractHead> contractHeadList){
        //对旧有订单进行数据累加（金额/数量）
        Map<Long , BigDecimal> cumulativeAmountMap = new HashMap<>();
        Map<Long , BigDecimal> cumulativeQuantityMap = new HashMap<>();
        Map<Long , BigDecimal> contractCumulativeAmountMap = new HashMap<>();

        Map<Long ,String> materialsInTheContract =  contractMaterialList.stream().collect(Collectors.toMap(
                c -> c.getContractHeadId() ,
                c -> c.getContractMaterialId().toString() ,
                (c1, c2) ->{ return new StringBuilder(c1.toString()).append(";").append(c2.toString()).toString();}
        ));
        Map<Long , ContractMaterial> contractMaterialMap = contractMaterialList.stream().collect(Collectors.toMap(
                c -> c.getContractMaterialId() , c -> c , (o, o2) -> o
        ));
        List<OrderDetail> historicalOrderDetails = scIOrderDetailService.listValidOrderDetail(thisVerificationOrderDetails);
        Set<Long> thisVerificationOrderDetailsIds = thisVerificationOrderDetails.stream().map(o -> o.getOrderDetailId()).collect(Collectors.toSet());
        //根据合同物料id分组
        Map<Long , List<OrderDetail>> orderDetailGroup = historicalOrderDetails.stream().filter(orderDetail -> {
            //*排除此次保存的订单 避免累加溢出
            return !thisVerificationOrderDetailsIds.contains(orderDetail.getOrderDetailId());
        }).collect(
                Collectors.groupingBy(OrderDetail::getCeeaPriceSourceId)
        );
        //累计金额、数量
        orderDetailGroup.keySet().stream().peek(
                key -> {
                    //根据合同行信息累计数量/金额
                    BigDecimal cumulativeAmount =  BigDecimal.ZERO;
                    BigDecimal cumulativeQuantity = BigDecimal.ZERO;
                    for(OrderDetail orderDetail : orderDetailGroup.get(key)){
                        cumulativeAmount = cumulativeAmount.add(Optional.ofNullable(orderDetail.getCeeaAmountIncludingTax()).orElse(BigDecimal.ZERO));
                        cumulativeQuantity = cumulativeQuantity.add(Optional.ofNullable(orderDetail.getOrderNum()).orElse(BigDecimal.ZERO));
                    }
                    cumulativeAmountMap.put(key , cumulativeAmount);
                    cumulativeQuantityMap.put(key , cumulativeQuantity);

                    //根据合同头信息累计金额
                    for(Long cnHeadId : materialsInTheContract.keySet()){
                        String materialInCn = materialsInTheContract.get(cnHeadId);
                        if(materialInCn.indexOf(key.toString()) == -1 ){
                            continue;
                        }
                        BigDecimal headCumulativeAmount = Optional.ofNullable(contractCumulativeAmountMap.get(cnHeadId)).orElse(BigDecimal.ZERO);
                        headCumulativeAmount = headCumulativeAmount.add(cumulativeAmount);
                        contractCumulativeAmountMap.put(cnHeadId , headCumulativeAmount);
                    }
                }
        );
        //开始对保存的订单进行校验
        for(OrderDetail orderDetail : thisVerificationOrderDetails){
            ContractMaterial contractMaterial = contractMaterialMap.get(orderDetail.getCeeaPriceSourceId());
            ContractHead currentOrderContract = contractHeadList.stream().filter(c -> contractMaterial.getContractHeadId().equals(c.getContractHeadId())).findFirst().get();
            //金额
            if(ManagementControlModel.CERTAION_AMOUNT.getValue().equals(currentOrderContract.getCeeaControlMethod())){
                //历史金额
                BigDecimal cumulativeAmount = Optional.ofNullable(cumulativeAmountMap.get(contractMaterial.getContractMaterialId())).orElse(BigDecimal.ZERO);
                //本次金额
                cumulativeAmount = cumulativeAmount.add(orderDetail.getCeeaAmountIncludingTax());
                //可下单金额
                BigDecimal availableOrderAmount = Optional.ofNullable(contractMaterial.getAmount()).orElse(BigDecimal.ZERO)
                                        .subtract(Optional.ofNullable(contractMaterial.getCeeaInitAmount()).orElse(BigDecimal.ZERO));
                if(availableOrderAmount.compareTo(cumulativeAmount) == -1){
                    throw new BaseException(String.format("订单中[%s]金额已经超出合同[%s - %s]上限，请检查!" ,
                            contractMaterial.getMaterialName() ,
                            currentOrderContract.getContractNo(),
                            currentOrderContract.getContractName()));
                }
                cumulativeAmountMap.put(contractMaterial.getContractMaterialId() , cumulativeAmount);

                //数量
            }else if(ManagementControlModel.QUAN_LIMIT.getValue().equals(currentOrderContract.getCeeaControlMethod())){
                //历史的订单数量
                BigDecimal cumulativeQuantity = Optional.ofNullable(cumulativeQuantityMap.get(contractMaterial.getContractMaterialId())).orElse(BigDecimal.ZERO);
                //加上本次的订单数量
                cumulativeQuantity = cumulativeQuantity.add(orderDetail.getOrderNum());
                //可下单数量
                BigDecimal availableOrderQuantity = Optional.ofNullable(contractMaterial.getContractQuantity()).orElse(BigDecimal.ZERO)
                                            .subtract(Optional.ofNullable(contractMaterial.getCeeaInitNumber()).orElse(BigDecimal.ZERO));
                if(cumulativeQuantity.compareTo(availableOrderQuantity) == -1){
                    throw new BaseException(String.format("订单中[%s]数量已经超出合同[%s - %s]上限，请检查!" ,
                            contractMaterial.getMaterialName() ,
                            currentOrderContract.getContractNo(),
                            currentOrderContract.getContractName()));
                }
                cumulativeQuantity = cumulativeQuantity.add(availableOrderQuantity);
                cumulativeQuantityMap.put(contractMaterial.getContractMaterialId() , cumulativeQuantity);

                //金额+数量
            }else if(ManagementControlModel.QUAN_AMOUNT_LIMIT.getValue().equals(currentOrderContract.getCeeaControlMethod())){

                BigDecimal cumulativeAmount = Optional.ofNullable(cumulativeAmountMap.get(contractMaterial.getContractMaterialId())).orElse(BigDecimal.ZERO);
                cumulativeAmount = cumulativeAmount.add(orderDetail.getCeeaAmountIncludingTax());
                BigDecimal availableOrderAmount = Optional.ofNullable(contractMaterial.getAmount()).orElse(BigDecimal.ZERO)
                                    .subtract(Optional.ofNullable(contractMaterial.getCeeaInitAmount()).orElse(BigDecimal.ZERO));
                if(availableOrderAmount.compareTo(cumulativeAmount) == -1){
                    throw new BaseException(String.format("订单中[%s]金额已经超出合同[%s - %s]上限，请检查!" ,
                            contractMaterial.getMaterialName() ,
                            currentOrderContract.getContractNo(),
                            currentOrderContract.getContractName()));
                }
                cumulativeAmountMap.put(contractMaterial.getContractMaterialId() , cumulativeAmount);

                BigDecimal cumulativeQuantity = Optional.ofNullable(cumulativeQuantityMap.get(contractMaterial.getContractMaterialId())).orElse(BigDecimal.ZERO);
                cumulativeQuantity = cumulativeQuantity.add(orderDetail.getOrderNum());
                BigDecimal availableOrderQuantity = Optional.ofNullable(contractMaterial.getContractQuantity()).orElse(BigDecimal.ZERO)
                                                .subtract(Optional.ofNullable(contractMaterial.getCeeaInitNumber()).orElse(BigDecimal.ZERO));
                if(cumulativeQuantity.compareTo(availableOrderQuantity) == -1){
                    throw new BaseException(String.format("订单中[%s]数量已经超出合同[%s - %s]上限，请检查!" ,
                            contractMaterial.getMaterialName() ,
                            currentOrderContract.getContractNo(),
                            currentOrderContract.getContractName()));
                }
                cumulativeQuantityMap.put(contractMaterial.getContractMaterialId() , cumulativeQuantity);


                //合同总金额,20201208 目前甲方还在讨论中
            }else if("总金额".equals(currentOrderContract.getCeeaControlMethod())){
                //获取合同历史已用金额
                BigDecimal headCumulativeAmount = Optional.ofNullable(contractCumulativeAmountMap.get(currentOrderContract.getContractHeadId())).orElse(BigDecimal.ZERO);
                //加上当前订单行金额
                headCumulativeAmount = headCumulativeAmount.add(orderDetail.getCeeaAmountIncludingTax());
                //最大可用金额 = 合同初始化金额
                BigDecimal availableOrderAmount = Optional.ofNullable(currentOrderContract.getIncludeTaxAmount()).orElse(BigDecimal.ZERO);
                if(availableOrderAmount.compareTo(headCumulativeAmount) == -1){
                    throw new BaseException(String.format("订单中[%s]总金额已经超出合同[%s - %s]上限，请检查!" ,
                            contractMaterial.getMaterialName() ,
                            currentOrderContract.getContractNo(),
                            currentOrderContract.getContractName()));
                }
                contractCumulativeAmountMap.put(currentOrderContract.getContractHeadId(), headCumulativeAmount);
            }
        }

    }

}
