package com.midea.cloud.srm.supcooperate.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.order.Unreconciled;
import com.midea.cloud.common.enums.order.UnsettledBillType;
import com.midea.cloud.common.enums.pm.po.PurchaseOrderEnum;
import com.midea.cloud.common.enums.pm.po.ReceivedStatusEnum;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.*;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledDetail;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceipt;
import com.midea.cloud.srm.supcooperate.order.mapper.DeliveryNoteDetailMapper;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryNoteDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderService;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IUnsettledDetailService;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IWarehouseReceiptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  送货单明细 服务实现类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:13
 *  修改内容:
 * </pre>
 */
@Service
public class DeliveryNoteDetailServiceImpl extends ServiceImpl<DeliveryNoteDetailMapper, DeliveryNoteDetail> implements IDeliveryNoteDetailService {
    @Autowired
    private IUnsettledDetailService iUnsettledDetailService;
    @Autowired
    private IOrderDetailService iOrderDetailService;
    @Autowired
    private IWarehouseReceiptService iWarehouseReceiptService;
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private DeliveryNoteDetailMapper deliveryNoteDetailMapper;
    @Autowired
    private BaseClient baseClient;

    //注释日期 2021/2/19 注释内容第78-82，88行
    @Override
    public List<DeliveryNoteDetailDTO> listPage(DeliveryNoteRequestDTO deliveryNoteRequestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.notNull(loginAppUser, "用户不存在");
        Assert.notNull(loginAppUser.getUserType(), "用户类型不存在");

        if (StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
            deliveryNoteRequestDTO.setVendorId(loginAppUser.getCompanyId());
        }
        List<DeliveryNoteDetailDTO> list = this.getBaseMapper().findList(deliveryNoteRequestDTO);
//        list.forEach(item->{
//            item.setWarehouseReceiptQuantity(new BigDecimal(0));
//            List<WarehouseReceipt> wrList = iWarehouseReceiptService.list(
//                    new QueryWrapper<WarehouseReceipt>().eq("DELIVERY_NOTE_DETAIL_ID",item.getDeliveryNoteDetailId())
//                            .eq("WAREHOUSE_RECEIPT_STATUS", ReceivedStatusEnum.CONFIRMED).isNotNull("WAREHOUSE_RECEIPT_QUANTITY"));

//            wrList.forEach(item1->{
//                item.setWarehouseReceiptQuantity(item.getWarehouseReceiptQuantity().add(item1.getWarehouseReceiptQuantity()));
//            });

//        });
        return list;
    }

    @Override
    public List<DeliveryNoteDetailDTO> listDeliveryNoteDetail(DeliveryNoteRequestDTO deliveryNoteRequestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Assert.notNull(loginAppUser, "用户不存在");
        Assert.notNull(loginAppUser.getUserType(), "用户类型不存在");
        if (StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
            deliveryNoteRequestDTO.setVendorId(loginAppUser.getCompanyId());
        }
        List<DeliveryNoteDetailDTO> list = this.getBaseMapper().findDeliveryNoteDetailList(deliveryNoteRequestDTO);
        return list;
    }

    @Override
    public List<DeliveryNoteDetailReceiveDTO> receiveListPage(ReceiveRequestDTO receiveRequestDTO) {
        return this.getBaseMapper().findReceiveList(receiveRequestDTO);
    }

    @Override
    public void updateBatchByExcel(List<DeliveryNoteDetail> list) {
        for (DeliveryNoteDetail deliveryNoteDetail : list) {
            try {
                DeliveryNoteDetail noteDetail = this.getBaseMapper().selectById(deliveryNoteDetail.getDeliveryNoteDetailId());
                if (noteDetail == null) {
                    deliveryNoteDetail.getErrorCell("deliveryNoteDetailId").setComment("找不到应收对明细");
                    continue;
                }
                //判断收货数量是否大于送货申请数量
                if(deliveryNoteDetail.getReceivedNum().compareTo(noteDetail.getDeliveryQuantity())==1){
                    deliveryNoteDetail.getErrorCell("receivedNum").setComment("收货数量大于送货数量，请检查收货数量");
                    continue;
                }
                UnsettledDetail detail = new UnsettledDetail();
                detail.setBillId(deliveryNoteDetail.getDeliveryNoteDetailId());
                detail.setBillType(UnsettledBillType.RECEIPT.name());
                QueryWrapper<UnsettledDetail> wrapper = new QueryWrapper<>(detail);
                UnsettledDetail unsettledDetail = iUnsettledDetailService.getOne(wrapper);
                if (unsettledDetail != null) {
                    deliveryNoteDetail.getErrorCell("lineErrorContents").setComment("已更新过");
                    continue;
                }
                OrderDetail orderDetail = iOrderDetailService.getById(noteDetail.getOrderDetailId());
                if ((orderDetail.getReceiveSum().add(noteDetail.getReceivedNum())).compareTo(orderDetail.getOrderNum()) == 1 ) {
                    deliveryNoteDetail.getErrorCell("receivedNum").setComment("累计订单收货数量超过订单数量，请检查收货数量");
                    continue;
                }
                Order order = iOrderService.getById(orderDetail.getOrderId());


                //之前未更新数量，且未创建未结算数量账单
                unsettledDetail = new UnsettledDetail();
                unsettledDetail.setUnsettledDetailId(IdGenrator.generate());
                unsettledDetail.setBillId(noteDetail.getDeliveryNoteDetailId());
                unsettledDetail.setBillNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_SSC_UNSETTLED_ORDER_NUM));
                unsettledDetail.setBillType(UnsettledBillType.RECEIPT.name());
                unsettledDetail.setBusinessDate(new Date());

                unsettledDetail.setVendorId(order.getVendorId());
                unsettledDetail.setOrganizationId(order.getOrganizationId());
                unsettledDetail.setMaterialCode(orderDetail.getMaterialCode());
                unsettledDetail.setMaterialName(orderDetail.getMaterialName());
                unsettledDetail.setNotInvoiced(Unreconciled.Y.name());
                unsettledDetail.setOrderNumber(order.getOrderNumber());
                unsettledDetail.setUnitPriceExcludingTax(orderDetail.getCeeaUnitNoTaxPrice());
                unsettledDetail.setUnreconciled(Unreconciled.Y.name());
                unsettledDetail.setUnsettledNum(deliveryNoteDetail.getReceivedNum());
                unsettledDetail.setUnit(orderDetail.getUnit());
                unsettledDetail.setPrice(unsettledDetail.getUnsettledNum()
                        .multiply(unsettledDetail.getUnitPriceExcludingTax()));

                deliveryNoteDetail.setReceivedTime(unsettledDetail.getBusinessDate());
                this.getBaseMapper().updateById(deliveryNoteDetail);
                iUnsettledDetailService.save(unsettledDetail);

                //更新送货通知单订单累计收货量和状态
                //更新订单明细累计收货数量 = 原来累计收货数量+新送货单的收货数量,剩余订单数量=原来剩余订单数量-新送货单的收货数量
                iOrderDetailService.updateById(new OrderDetail().setOrderDetailId(orderDetail.getOrderDetailId())
                        .setReceiveSum(orderDetail.getReceiveSum().add(noteDetail.getReceivedNum())));
            } catch (Exception e) {
                log.error("操作失败",e);
                deliveryNoteDetail.getErrorCell("lineErrorContents").setComment("更改失败");
            }
        }
    }

    /**
     * @add xiexh12@meicloud.com
     * @param deliveryNoteDetailId
     * @param batchNum
     * @param deliveryQuantity
     * 供应商进行确认发货时，对已输入的送货单信息进行保存，同时回写订单详情表的累计收货数量
     */
    @Override
    public void confirmDeliveryNoteDetailStatus(Long deliveryNoteDetailId, String batchNum, BigDecimal deliveryQuantity) {
        DeliveryNoteDetail deliveryNoteDetail = this.getById(deliveryNoteDetailId);
        Long orderDetailId = deliveryNoteDetail.getOrderDetailId();
        OrderDetail orderDetail = iOrderDetailService.getById(orderDetailId);

        orderDetail.setCeeaBatchNum(batchNum);
        BigDecimal orderNum = orderDetail.getOrderNum();
        BigDecimal receiveSum = orderDetail.getReceiveSum();
        BigDecimal newReceiveSum = receiveSum.add(deliveryQuantity);
        Assert.isTrue(orderNum.compareTo(newReceiveSum)>=0, "本次送货数量不能大于剩余未送货数量");
        orderDetail.setReceiveSum(newReceiveSum);
        iOrderDetailService.updateById(orderDetail);
        this.getBaseMapper().updateById(deliveryNoteDetail);
    }


    @Override
    public List<DeliveryNoteDetail> DeliveryNoteDetailList(DeliveryNoteDetail deliveryNoteDetail){
        QueryWrapper<DeliveryNoteDetail> wrapper = new QueryWrapper<>();
        wrapper.eq(null!=deliveryNoteDetail.getCeeaArrivalDetailId(),"CEEA_ARRIVAL_DETAIL_ID",deliveryNoteDetail.getCeeaArrivalDetailId());
        return this.list(wrapper);
    }


    /**
     * 创建入库单-查询送货单明细
     * @param deliveryNoteRequestDTO
     * @return
     */
    @Override
    public PageInfo<DeliveryNoteDetailDTO> listInWarehouseReceipt(DeliveryNoteRequestDTO deliveryNoteRequestDTO) {
        PageUtil.startPage(deliveryNoteRequestDTO.getPageNum(),deliveryNoteRequestDTO.getPageSize());
        return new PageInfo<DeliveryNoteDetailDTO>(deliveryNoteDetailMapper.listInWarehouseReceipt(deliveryNoteRequestDTO));
    }

    /**
     * 创建退货单-查询送货单明细
     * @param deliveryNoteRequestDTO
     * @return
     */
    @Override
    public PageInfo<DeliveryNoteDetailDTO> listInReturnOrder(DeliveryNoteRequestDTO deliveryNoteRequestDTO) {
        PageUtil.startPage(deliveryNoteRequestDTO.getPageNum(),deliveryNoteRequestDTO.getPageSize());
        return new PageInfo<DeliveryNoteDetailDTO>(deliveryNoteDetailMapper.listInReturnOrder(deliveryNoteRequestDTO));
    }
}
