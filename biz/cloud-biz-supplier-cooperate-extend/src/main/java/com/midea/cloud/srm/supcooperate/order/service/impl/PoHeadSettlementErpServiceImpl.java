package com.midea.cloud.srm.supcooperate.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.base.soap.erp.suppliercooperate.dto.RequestSettlementOrder;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.PoHeadSettlementErp;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.PoLineSettlementErp;
import com.midea.cloud.srm.supcooperate.order.mapper.PoHeadSettlementErpMapper;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderService;
import com.midea.cloud.srm.supcooperate.order.service.IPoHeadSettlementErpService;
import com.midea.cloud.srm.supcooperate.order.service.IPoLineSettlementErpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
*  <pre>
 *   服务实现类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-02 15:32:59
 *  修改内容:
 * </pre>
*/
@Service
@Slf4j
public class PoHeadSettlementErpServiceImpl extends ServiceImpl<PoHeadSettlementErpMapper, PoHeadSettlementErp> implements IPoHeadSettlementErpService {


    @Autowired
    private IPoLineSettlementErpService poLineSettlementErpService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderDetailService orderDetailService;
    @Autowired
    private BaseClient baseClient;

    @Transactional
    @Override
    public SoapResponse acceptErpData(List<RequestSettlementOrder.RequestInfo.CONTEXT.PoHeader.RECORD> recordList, String instId, String requestTime) {
        List<PoHeadSettlementErp> poHeadSettlementErpList = new ArrayList<>();
        List<PoLineSettlementErp> poLineSettlementErpList = new ArrayList<>();
        List<Order> orderUpdateList = new ArrayList<>();
        String returnStatus = "";
        String returnMsg = "";

        if(CollectionUtils.isEmpty(recordList)){
            return returnResponse("E","报文内容为空",requestTime,instId);
        }

        try {
            /*校验数据的完整性*/
            checkRecords(recordList);
            String erpOrderNumber = recordList.get(0).getPO_NUMBER();
            String orderNumber = recordList.get(0).getPO_LINE().getRecord().get(0).getSOURCE_PO_NUM();

            //伟杰哥说erp推送过来的数据都是同一采购订单 2020-12-05
            List<Order> orderList = orderService.list(new QueryWrapper<Order>(new Order().setOrderNumber(orderNumber)));
            Order dbOrder = orderList.get(0);
            if(CollectionUtils.isEmpty(orderList) || Objects.isNull(dbOrder.getOrderId())){
                throw new BaseException(LocaleHandler.getLocaleMsg("找不到相关的采购订单"));
            }
            //采购订单行信息
            List<OrderDetail> orderDetailList = orderDetailService.list(new QueryWrapper<>(new OrderDetail().setOrderId(dbOrder.getOrderId())));
            if(orderDetailList.isEmpty()){
                throw new BaseException(LocaleHandler.getLocaleMsg("找不到相关的采购订单行信息"));
            }
                Map<String , BigDecimal> orderReceivedMap = orderDetailList.stream().collect(Collectors.toMap(
                    o -> o.getLineNum().toString(),
                    o -> Optional.ofNullable(o.getReceivedQuantity()).orElse(BigDecimal.ZERO) ,
                    (o, o2) -> o));

            for(RequestSettlementOrder.RequestInfo.CONTEXT.PoHeader.RECORD record : recordList){
                Long poHeadId = IdGenrator.generate();
                PoHeadSettlementErp poHeadSettlementErp = new PoHeadSettlementErp();
                BeanUtils.copyProperties(record,poHeadSettlementErp);
                poHeadSettlementErp.setPoHeadSettlementErpId(poHeadId);
                poHeadSettlementErpList.add(poHeadSettlementErp);

                /*校验是否有采购订单*/
                List<RequestSettlementOrder.RequestInfo.CONTEXT.PoHeader.RECORD.PoLine.RECORD2> record2List = record.getPO_LINE().getRecord();
                if(CollectionUtils.isNotEmpty(record2List)){
                    for(RequestSettlementOrder.RequestInfo.CONTEXT.PoHeader.RECORD.PoLine.RECORD2 record2:record2List){
                        Long poLineId = IdGenrator.generate();
                        PoLineSettlementErp poLineSettlementErp = new PoLineSettlementErp();
                        BeanUtils.copyProperties(record2,poLineSettlementErp);
                        poLineSettlementErp.setContractNumber(record2.getCONTRACT_NUMBER());
                        poLineSettlementErp.setItemCategory(record2.getITEM_CODE());
                        poLineSettlementErp.setItemCode(record2.getITEM_CODE());
                        poLineSettlementErp.setItemDescription(record2.getITEM_DESCRIPTION());
                        poLineSettlementErp.setLineNum(record2.getLINE_NUM());
                        poLineSettlementErp.setPrimaryQuantity(new BigDecimal(record2.getPRIMARY_QUANTITY()));
                        poLineSettlementErp.setPrimaryUnit(record2.getPRIMARY_UNIT());
                        poLineSettlementErp.setQuantity(new BigDecimal(record2.getQUANTITY()));
                        poLineSettlementErp.setShipToLocationAddress(record2.getSHIP_TO_LOCATION_ADDRESS());
                        poLineSettlementErp.setSourcePoNum(record2.getSOURCE_PO_NUM());
                        poLineSettlementErp.setSourcePoLineNum(record2.getSOURCE_PO_LINE_NUM());
                        poLineSettlementErp.setTaxableFlag(record2.getTAXABLE_FLAG());
                        poLineSettlementErp.setTaxName(record2.getTAX_NAME());
                        poLineSettlementErp.setTaxPrice(new BigDecimal(record2.getTAX_PRICE()));
                        poLineSettlementErp.setUnitMeasLookupCode(record2.getUNIT_MEAS_LOOKUP_CODE());
                        poLineSettlementErp.setUnitPrice(new BigDecimal(record2.getUNIT_PRICE()));
                        poLineSettlementErp.setPoLineSettlementErpId(poLineId);
                        poLineSettlementErp.setPoHeadSettlemenErpId(poHeadId);
                        //写入订单头行表id
                        for(OrderDetail orderDetail : orderDetailList){
                            if(!orderDetail.getLineNum().equals(poLineSettlementErp.getSourcePoLineNum())){
                                continue;
                            }
                            poLineSettlementErp.setPoLineId(orderDetail.getOrderDetailId().toString());
                            poLineSettlementErp.setPoHeaderId(orderDetail.getOrderId().toString());
                        }
                        poLineSettlementErpList.add(poLineSettlementErp);

                        //累加订单行上的接收数量
                        if (!StringUtils.isNumeric(poLineSettlementErp.getQuantity().toString())) {
                            throw new BaseException(LocaleHandler.getLocaleMsg(String.format("接收行%s错误，接收数量只能为数字！",poLineSettlementErp.getSourcePoLineNum())));
                        }
                        BigDecimal receivedQuantity = orderReceivedMap.get(poLineSettlementErp.getSourcePoLineNum());
                        receivedQuantity = receivedQuantity.add(poLineSettlementErp.getQuantity());
                        orderReceivedMap.put(poLineSettlementErp.getSourcePoLineNum() , receivedQuantity);
                    }
                }
            }

            //修改订单状态为结算过
            Order updateOrder = new Order()
                    .setOrderId(dbOrder.getOrderId())
                    .setEprOrderNumber(erpOrderNumber)
                    .setHasSettlement(YesOrNo.YES.getValue());

            this.saveBatch(poHeadSettlementErpList);
            poLineSettlementErpService.saveBatch(poLineSettlementErpList);
            orderService.updateById(updateOrder);

            //批量更新订单行接收数量
            List<OrderDetail> updateOrderDetailList = new ArrayList<>();
            for(OrderDetail orderDetail : orderDetailList){
                orderDetail.setOrderDetailId( orderDetail.getOrderId());
                BigDecimal receivedQuantity  = orderReceivedMap.get(orderDetail.getLineNum());
                orderDetail.setReceiveNum(receivedQuantity);
            }
            orderDetailService.updateBatchById(updateOrderDetailList);

            returnStatus = "S";
            returnMsg = "接收成功";
        } catch (Exception e){
            returnStatus = "E";
            returnMsg = e.getMessage();
            log.error("操作失败",e);
            /*强制手动回滚事务*/
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
            return returnResponse(returnStatus, returnMsg,requestTime,instId);
        }
    }

    /**
     * 针对物料编码为空的脏数据情况，希望脏数据没有天堂
     * @return
     */
    private String fixBugDataOrderMaterialCode(Long materialId , Long orderId){
        if(Objects.isNull(materialId)){
            throw new BaseException(LocaleHandler.getLocaleMsg("srm-数据缺失，采购订单id["+orderId+"]物料id为空，请检查"));
        }
        List<MaterialItem> materialItems = baseClient.listMaterialItemsByIds(Arrays.asList(materialId));

        if(materialItems.isEmpty() ||
            Objects.isNull(materialItems.get(0)) ||
            StringUtils.isBlank(materialItems.get(0).getMaterialCode())){

            throw new BaseException(LocaleHandler.getLocaleMsg("srm-数据错误，采购订单id["+orderId+"]物料id["+materialId+"]不存在，请检查"));
        }

        return materialItems.get(0).getMaterialCode();
    }

    /**
     * 校验 PO_HEAD 的 PO_NUMBER 不可为空
     * 校验 PO_LINE 的 SOURCE_PO_NUM 不可为空
     * @param recordList
     */
    private void checkRecords(List<RequestSettlementOrder.RequestInfo.CONTEXT.PoHeader.RECORD> recordList) {
        if(CollectionUtils.isEmpty(recordList)){
            throw new BaseException(LocaleHandler.getLocaleMsg("PO_HEAD不可为空"));
        }

        for(RequestSettlementOrder.RequestInfo.CONTEXT.PoHeader.RECORD record:recordList){
            if(StringUtils.isBlank(record.getPO_NUMBER())){
                throw new BaseException(LocaleHandler.getLocaleMsg("PO_HEAD的PO_NUMBER不可为空"));
            }
            if(record.getPO_LINE() == null || CollectionUtils.isEmpty(record.getPO_LINE().getRecord())){
                throw new BaseException(LocaleHandler.getLocaleMsg("PO_LINE不可为空"));
            }
            List<RequestSettlementOrder.RequestInfo.CONTEXT.PoHeader.RECORD.PoLine.RECORD2> record2List = record.getPO_LINE().getRecord();
            for(RequestSettlementOrder.RequestInfo.CONTEXT.PoHeader.RECORD.PoLine.RECORD2 record2:record2List){
                if(StringUtils.isBlank(record2.getSOURCE_PO_NUM())){
                    throw new BaseException(LocaleHandler.getLocaleMsg("PO_LINE的SOURCE_PO_NUM不可为空"));
                }
            }


        }
    }


    public SoapResponse returnResponse(String returnStatus, String returnMsg, String requestTime, String instId){
        SoapResponse returnResponse = new SoapResponse();
        Date nowDate = new Date();
        returnResponse.setSuccess("true");
        SoapResponse.RESPONSE responseValue = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfoValue = new SoapResponse.RESPONSE.EsbInfo();
        esbInfoValue.setReturnStatus(returnStatus);
        esbInfoValue.setReturnMsg(returnMsg);
        esbInfoValue.setResponseTime(requestTime);
        if(StringUtils.isBlank(requestTime)){
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfoValue.setInstId(instId);
        esbInfoValue.setRequestTime(requestTime);
        esbInfoValue.setAttr1("");
        esbInfoValue.setAttr2("");
        esbInfoValue.setAttr3("");
        responseValue.setEsbInfo(esbInfoValue);
        returnResponse.setResponse(responseValue);
        return returnResponse;
    }
}
