package com.midea.cloud.srm.supcooperate.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.pm.po.CeeaWarehousingReturnDetailEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.pm.PmClient;
import com.midea.cloud.srm.model.base.soap.erp.suppliercooperate.dto.WarehousingReturnDetailEntity;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.PurchaseRequirementDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetailErp;
import com.midea.cloud.srm.supcooperate.order.mapper.WarehousingReturnDetailErpMapper;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderService;
import com.midea.cloud.srm.supcooperate.order.service.IWarehousingReturnDetailErpService;
import com.midea.cloud.srm.supcooperate.order.service.IWarehousingReturnDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
*  <pre>
 *  入库退货明细erp表 服务实现类
 * </pre>
*
* @author chenwj92@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-25 14:30:44
 *  修改内容:
 * </pre>
*/
@Service
@Slf4j
public class WarehousingReturnDetailErpServiceImpl extends ServiceImpl<WarehousingReturnDetailErpMapper, WarehousingReturnDetailErp> implements IWarehousingReturnDetailErpService {

    @Autowired
    private IWarehousingReturnDetailService warehousingReturnDetailService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private PmClient pmClient;

    @Autowired
    private WarehousingReturnDetailErpMapper warehousingReturnDetailErpMapper;

    /**
     * 批量查询出事务类型为：【RECEIVE】【RECEIVE_STANDARD】【RETURN TO VENDOR】【RETURN_TO_VENDOR】,IF_HANDLE为【N】的数据
     * @return
     */
    @Override
    @Transactional
    public BaseResult transferErpToSrm() {
        long startTime = System.currentTimeMillis();
        int count = warehousingReturnDetailErpMapper.countWarehousingReturnDetailErp();
        List<WarehousingReturnDetailErp> warehousingReturnDetailErpList = null;
        if(count < 1000){
            warehousingReturnDetailErpList = warehousingReturnDetailErpMapper.listWarehousingReturnDetailErp(0);
            handle(warehousingReturnDetailErpList);
        }else{
            int time = (int)Math.ceil(count/1000.0);
            for(int i=0;i<time;i++){
                warehousingReturnDetailErpList = warehousingReturnDetailErpMapper.listWarehousingReturnDetailErp(i);
                handle(warehousingReturnDetailErpList);
            }
        }

        Long totalTime = System.currentTimeMillis() - startTime;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("查询出来的数据有" + count + "条.总共执行" + totalTime + "毫秒");

        return BaseResult.build(ResultCode.SUCCESS,stringBuilder.toString());
    }

    private void handle(List<WarehousingReturnDetailErp> warehousingReturnDetailErpList) {
        List<WarehousingReturnDetail> warehousingReturnDetailAddOrUpdates = new LinkedList<>();
        List<WarehousingReturnDetailErp> warehousingReturnDetailErpUpdates = new LinkedList<>();
        /*查询订单头*/
        List<String> orderNumbers = warehousingReturnDetailErpList.stream().map(item -> item.getPoNumber()).collect(Collectors.toList());
        QueryWrapper<Order> orderWrapper = new QueryWrapper();
        orderWrapper.and(w -> w.in("ERP_ORDER_NUMBER",orderNumbers).or().in("ORDER_NUMBER",orderNumbers));
        List<Order> orderList = orderService.list(orderWrapper);
        /*查询订单行*/
        List<Long> orderIds = orderList.stream().map(item -> item.getOrderId()).collect(Collectors.toList());
        QueryWrapper<OrderDetail> orderDetailWrapper = new QueryWrapper<>();
        orderDetailWrapper.in("ORDER_ID",orderIds);
        List<OrderDetail> orderDetailList = orderDetailService.list(orderDetailWrapper);
        /*查询采购申请行*/
        List<Long> requirementLineIds = orderDetailList.stream().map(item -> item.getCeeaRequirementLineId()).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(requirementLineIds)){
            log.info("查询不到采购申请行id");
            return;
        }
        List<RequirementLine> requirementLineList = pmClient.getRequirementLineByIdsForAnon(requirementLineIds);
        /*查询采购申请头*/
        List<Long> requirementHeadIds = requirementLineList.stream().map(item -> item.getRequirementHeadId()).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(requirementHeadIds)){
            log.info("查询不到采购申请头id");
            return;
        }
        List<RequirementHead> requirementHeadList = pmClient.getRequirementHeadByIdsForAnon(requirementHeadIds);

        for(WarehousingReturnDetailErp item:warehousingReturnDetailErpList){
            checkData(item);
            /*如果校验到txn_id重复，则为重复推送*/
            /*QueryWrapper<WarehousingReturnDetailErp> repeatWrapper = new QueryWrapper<>();
            repeatWrapper.eq("TXN_ID",item.getTxnId());
            List<WarehousingReturnDetailErp> erpList = this.list(repeatWrapper);
            if(!CollectionUtils.isEmpty(warehousingReturnDetailErpList)){
                log.info("txd_id重复");
                continue;
            }*/
            /*如果检验到类型不是【接收】【退货至供应商】,则直接插入*/
            if(item.getTxnType().equals("RECEIVE") ||
                    item.getTxnType().equals("RECEIVE_STANDARD") ||
                    item.getTxnType().equals("RETURN TO VENDOR") ||
                    item.getTxnType().equals("RETRUN_TO_VENDOR")
            ){
                Order order = null;
                OrderDetail orderDetail = null;
                RequirementHead requirementHead = null;
                RequirementLine requirementLine = null;

                for(Order orderItem:orderList){
                    if(item.getPoNumber().equals(orderItem.getOrderNumber()) || item.getPoNumber().equals(orderItem.getEprOrderNumber())){
                        order = orderItem;
                        break;
                    }
                }
                if(order == null){
                    continue;
                }

                for(OrderDetail orderDetailItem:orderDetailList){
                    if(Objects.equals(order.getOrderId(),orderDetailItem.getOrderId()) &&
                        Objects.equals(item.getPoLineNum(),orderDetailItem.getLineNum())
                    ){
                        orderDetail = orderDetailItem;
                        break;
                    }
                }

                if(orderDetail == null){
                    continue;
                }

                String requirementHeadNum = null;
                Integer rowNum = null;
                String projectNum = null;
                String projectName = null;
                if("Y".equals(orderDetail.getCeeaIfRequirement())){
                    for(RequirementLine requirementLineItem:requirementLineList){
                        if(Objects.equals(requirementLineItem.getRequirementLineId(),orderDetail.getCeeaRequirementLineId())){
                            requirementLine = requirementLineItem;
                            break;
                        }
                    }
                    if(requirementLine == null){
                        continue;
                    }

                    for(RequirementHead requirementHeadItem: requirementHeadList){
                        if(Objects.equals(requirementHeadItem.getRequirementHeadId(),requirementLine.getRequirementHeadId())){
                            requirementHead = requirementHeadItem;
                            break;
                        }
                    }
                    if(requirementHead == null){
                        continue;
                    }
                    requirementHeadNum = requirementLine.getRequirementHeadNum();
                    rowNum = requirementLine.getRowNum();
                    projectNum = requirementHead.getCeeaProjectNum();
                    projectName = requirementHead.getCeeaProjectName();
                }
                Date date = new Date();
                WarehousingReturnDetail warehousingReturnDetail = new WarehousingReturnDetail();
                warehousingReturnDetail.setReceiveOrderNo(item.getReceiveNum())  //接收单号
                        .setReceiveOrderLineNo(Integer.parseInt(item.getReceiveLineNum()))  //接收行号
                        .setTxnId(item.getTxnId())
                        .setErpOrgId(item.getOperationUnitId())  //erp业务实体id
                        .setErpOrganizationId(item.getOrganizationId())  //erp库存组织id
                        .setErpOrganizationCode(item.getOrganizationCode())  //erp库存组织编码
                        .setErpVendorId(item.getVendorId())  //erp供应商id
                        .setErpVendorCode(item.getVendorNumber())   //erp供应商编码
                        .setOrgId(order.getCeeaOrgId())  //业务实体id
                        .setOrgName(item.getOperationUnit())       //业务实体名称
                        .setOrganizationId(orderDetail.getCeeaOrganizationId()) //库存组织id
                        .setOrganizationCode(orderDetail.getCeeaOrganizationCode())  //库存组织编码
                        .setOrganizationName(orderDetail.getCeeaOrganizationName()) //库存组织名称
                        .setVendorId(order.getVendorId())  //供应商id
                        .setVendorCode(order.getVendorCode())   //供应商编码
                        .setItemCode(item.getItemNumber())      //物料编码
                        .setItemName(item.getItemDescr())    //物料名称
                        .setUnit(item.getUnitOfMeasure())   //单位名称
                        .setUnitCode(item.getUnitOfMeasure()) //单位编码名称
                        .setOrderNumber(item.getPoNumber())  //采购订单号
                        .setLineNum(item.getPoLineNum().intValue())  //订单行号
                        .setAsnNumber(item.getAsnNumber())
                        .setAsnLineNum(item.getAsnLineNum())
                        .setCreatedBy("erp推送")
                        .setCreationDate(date)
                        .setUnitPriceExcludingTax(item.getPrice()) //单价不含税
                        .setPoLineId(item.getPoLineId())  //采购订单行id
                        .setShipLineId(item.getShipLineId())    //发运行id
                        .setShipLineNum(item.getShipLineNum())   //发运行编号
                        /*order数据*/
                        .setOrgCode(order.getCeeaOrgCode())   //库存组织编码
                        .setVendorName(order.getVendorName())  //供应商名称
                        /*orderDetail数据*/
                        .setCurrencyId(orderDetail.getCurrencyId())  //币种id
                        .setCurrencyCode(orderDetail.getCurrencyCode())   //币种编码
                        .setCurrencyName(orderDetail.getCurrencyName())   //币种名称
                        .setOrganizationName(orderDetail.getCeeaOrganizationName())  //库存组织名称
                        .setCategoryId(orderDetail.getCategoryId())  //物料小类id
                        .setCategoryName(orderDetail.getCategoryName())  //物料小类名称
                        .setCategoryCode(orderDetail.getCategoryCode()) //物料小类编码
                        .setItemId(orderDetail.getMaterialId())  //物料id
                        .setContractNo(orderDetail.getCeeaContractNo())  //采购合同号
                        .setUnitPriceContainingTax(orderDetail.getCeeaUnitTaxPrice()) //含税单价
                        .setTaxRate(orderDetail.getCeeaTaxRate())  //税率
                        .setTaxKey(orderDetail.getCeeaTaxKey())  //税码
                        /*requirement数据*/
                        .setRequirementHeadNum(requirementHeadNum)  //采购申请单号
                        .setRowNum(rowNum)   //申请行号
                        .setProjectNum(projectNum)  //项目编码
                        .setProjectName(projectName);  //项目名称
                /*todo 物料小类编码*/
                /*todo 任务编号 任务名称 */
                /*todo 单价(含税)*/
                /*赋值warehousingReturnDetailErp(属性名称相同，类型不同 BeanUtils.copyProperties()不起效果)*/
                WarehousingReturnDetailErp warehousingReturnDetailErpUpdate = new WarehousingReturnDetailErp();
                Date txnDate = item.getTxnDate();
                Date exchangeDate = item.getExchangeDate();


                /*20201013版本：不对数据做逻辑处理，直接插入*/
                if(item.getTxnType().equals("RECEIVE") || item.getTxnType().equals("RECEIVE_STANDARD")){
                    /*接收*/
                    warehousingReturnDetail.setWarehousingReturnDetailId(IdGenrator.generate())
                            .setType(CeeaWarehousingReturnDetailEnum.RECEIVE.getValue())
                            .setReceiveNum(item.getQuantity())
                            .setDealNum(1)
                            .setParentTxnId(item.getTxnId())
                            .setEnable("Y")
                            .setReceiveDate(txnDate);
                    warehousingReturnDetailErpUpdate.setInsertSequence(1)
                            .setIfHandle("Y")
                            .setWarehousingReturnDetailId(warehousingReturnDetail.getWarehousingReturnDetailId());
                    /*设置未开票数量*/
                    warehousingReturnDetail.setNotInvoiceQuantity(warehousingReturnDetail.getReceiveNum());//设置未开票数量等于接收数量(后面开票业务需要用到)
                    /*回写订单明细接收数量*/
                    if(orderDetail.getReceivedQuantity() == null){
                        orderDetail.setReceivedQuantity(BigDecimal.ZERO);
                    }
                    orderDetailService.updateById(new OrderDetail()
                            .setOrderDetailId(orderDetail.getOrderDetailId())
                            .setReceivedQuantity(orderDetail.getReceivedQuantity().add(item.getQuantity()))
                    );
                }else if(item.getTxnType().equals("RETURN TO VENDOR") || item.getTxnType().equals("RETRUN_TO_VENDOR")){
                    /*退货至供应商*/
                    warehousingReturnDetail.setWarehousingReturnDetailId(IdGenrator.generate())
                            .setType(CeeaWarehousingReturnDetailEnum.RETURN.getValue())
                            .setReturnToSupplierNum(item.getQuantity())
                            .setReceiveNum(BigDecimal.ZERO.subtract(item.getQuantity()))
                            .setDealNum(1)
                            .setReturnToSupplierDate(txnDate);
                    warehousingReturnDetailErpUpdate.setInsertSequence(1)
                            .setWarehousingReturnDetailId(warehousingReturnDetail.getWarehousingReturnDetailId());
                    /*设置未开票数量*/
                    warehousingReturnDetail.setNotInvoiceQuantity(warehousingReturnDetail.getReceiveNum());//设置未开票数量等于接收数量(后面开票业务需要用到)

                    /*递归查询获取 退货类型的 parentTxnId*/
                    WarehousingReturnDetailErp erpEntity = new WarehousingReturnDetailErp();
                    BeanUtils.copyProperties(item,erpEntity);

                    while (erpEntity != null && !"RECEIVE".equals(erpEntity.getTxnType()) && !"RECEIVE_STANDARD".equals(erpEntity.getTxnType())){
                        if(Objects.isNull(erpEntity.getParentTxnId())){
                            erpEntity = null;
                        }else{
                            WarehousingReturnDetailErp param = new WarehousingReturnDetailErp()
                                    .setTxnId(erpEntity.getParentTxnId());
                            List<WarehousingReturnDetailErp> list = this.list(new QueryWrapper<>(param));
                            if(CollectionUtils.isNotEmpty(list)){
                                erpEntity = list.get(0);
                            }else{
                                erpEntity = null;
                            }

                        }
                    }
                    if(erpEntity == null){
                        /*没有检测到parentTxnId */
                        warehousingReturnDetailErpUpdate.setIfHandle("N");
                        warehousingReturnDetail.setEnable("N");
                    }else{
                        warehousingReturnDetailErpUpdate.setIfHandle("Y");
                        warehousingReturnDetail.setParentTxnId(erpEntity.getTxnId())
                                .setEnable("Y");
                    }
                }else{
                    Assert.notNull(null,LocaleHandler.getLocaleMsg("事务处理类型错误：TxnType = " + item.getTxnType()));
                }
                warehousingReturnDetailAddOrUpdates.add(warehousingReturnDetail);
                warehousingReturnDetailErpUpdates.add(warehousingReturnDetailErpUpdate);


            }else{
                log.info("类型不是【接收】【退货至供应商】");
            }

        }

        warehousingReturnDetailService.saveBatch(warehousingReturnDetailAddOrUpdates);
        this.updateBatchById(warehousingReturnDetailErpUpdates);

    }

    public void checkData(WarehousingReturnDetailErp warehousingReturnDetailErp){
        Assert.hasText(warehousingReturnDetailErp.getTxnType(), LocaleHandler.getLocaleMsg("事务处理类型代码不可为空"));
        Assert.notNull(warehousingReturnDetailErp.getTxnDate(), LocaleHandler.getLocaleMsg("事务处理时间不可为空"));
        Assert.notNull(warehousingReturnDetailErp.getQuantity(),LocaleHandler.getLocaleMsg("数量不可为空"));
        Assert.hasText(warehousingReturnDetailErp.getReceiveNum(),LocaleHandler.getLocaleMsg("接收号不可为空"));
        Assert.hasText(warehousingReturnDetailErp.getReceiveLineNum(),LocaleHandler.getLocaleMsg("接收行号不可为空"));
        Assert.hasText(warehousingReturnDetailErp.getPoNumber(),LocaleHandler.getLocaleMsg("采购订单编号不可为空"));
        Assert.notNull(warehousingReturnDetailErp.getPoLineNum(), LocaleHandler.getLocaleMsg("采购订单行号不可为空"));
    }
}
