package com.midea.cloud.srm.supcooperate.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.annotation.AuthData;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.neworder.OrderDetailStatus;
import com.midea.cloud.common.enums.order.DeliveryNoteStatus;
import com.midea.cloud.common.enums.order.JITOrder;
import com.midea.cloud.common.enums.rbac.MenuEnum;
import com.midea.cloud.common.enums.supcooperate.DeliveryNoteSource;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.soap.DataSourceEnum;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlan;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlanDetail;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.OrderDeliveryDetail;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteWms;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.erpacceptvendorshipmentsoapbiz.*;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.DeliveryNotice;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.supcooperate.deliver.service.IDeliverPlanDetailService;
import com.midea.cloud.srm.supcooperate.deliver.service.IDeliverPlanService;
import com.midea.cloud.srm.supcooperate.deliver.service.IOrderDeliveryDetailService;
import com.midea.cloud.srm.supcooperate.deliverynote.service.IDeliveryNoteWmsService;
import com.midea.cloud.srm.supcooperate.deliverynote.soap.erpacceptvendorshipmentsoapbiz.ErpAcceptVendorShipmentSoapBizPtt;
import com.midea.cloud.srm.supcooperate.order.mapper.DeliveryNoteDetailMapper;
import com.midea.cloud.srm.supcooperate.order.mapper.DeliveryNoteMapper;
import com.midea.cloud.srm.supcooperate.order.mapper.OrderDetailMapper;
import com.midea.cloud.srm.supcooperate.order.mapper.OrderMapper;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryNoteDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryNoteService;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryNoticeService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.coyote.Response;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  送货单表 服务实现类
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
public class DeliveryNoteServiceImpl extends ServiceImpl<DeliveryNoteMapper, DeliveryNote> implements IDeliveryNoteService {
    @Value("${bindqserp.erpUrl}")
    private String address;
    @Value("${bindqserp.erpUserName}")
    private String USERNAME;
    @Value("${bindqserp.erpPassword}")
    private String PASSWORD;
    @Autowired
    private DeliveryNoteMapper deliveryNoteMapper;
    @Autowired
    private DeliveryNoteDetailMapper deliveryNoteDetailMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private SupplierClient supplierClient;
    @Autowired
    private IDeliveryNoticeService iDeliveryNoticeService;
    @Autowired
    private IDeliveryNoteDetailService iDeliveryNoteDetailService;
    @Autowired
    FileCenterClient fileCenterClient;
    @Autowired
    private IDeliveryNoteWmsService iDeliveryNoteWmsService;
    @Autowired
    private IDeliverPlanDetailService iDeliverPlanDetailService;
    @Autowired
    private IOrderDeliveryDetailService iOrderDeliveryDetailService;
    @Autowired
    private IDeliverPlanService iDeliverPlanService;
    @Resource
    private IOrderDetailService orderDetailService;


    @Override
    public DeliveryNoteSaveRequestDTO getDeliveryDTO(Long orderId) {
        DeliveryNoteSaveRequestDTO deliveryNoteSaveRequestDTO = new DeliveryNoteSaveRequestDTO();
        //获取送货单
        DeliveryNote deliveryNote = this.getById(orderId);
        Assert.notNull(deliveryNote, "此送货单不存在");
        deliveryNoteSaveRequestDTO.setDeliveryNote(deliveryNote);

        //获取附件的文件列表
        PageInfo<Fileupload> procurementFile = fileCenterClient.listPage(new Fileupload().setBusinessId(orderId).setFileFunction("procurementFile"), "");
        if (procurementFile.getList() != null && procurementFile.getList().size() > 0) {
            deliveryNoteSaveRequestDTO.setProcurementFile(procurementFile.getList());
        }

        return deliveryNoteSaveRequestDTO;
    }

    /**
     * 保存送货单
     *
     * @param deliveryNote
     * @param detailList
     * @param procurementFile
     * @param deliveryNoteWms
     */
    @Transactional
    @Override
    public void saveOrUpdate(DeliveryNote deliveryNote, List<DeliveryNoteDetailDTO> detailList, List<Fileupload> procurementFile, List<DeliveryNoteWms> deliveryNoteWms) {
        //开始保存或新增订单头
        if (deliveryNote.getDeliveryNoteId() == null) {
            //如果送货单不存在，生成送货单的ID、送货单号、送货单状态
            deliveryNote.setDeliveryNoteId(IdGenrator.generate());
            deliveryNote.setDeliveryNumber(baseClient.seqGen(SequenceCodeConstant.SEQ_SSC_DELIVERY_NOTE_NUM));
            deliveryNote.setDeliveryNoteStatus(DeliveryNoteStatus.CREATE.name());

            LoginAppUser user = AppUserUtil.getLoginAppUser();
            //当前登录用户为供应商 设置供应商ID、供应商编码、供应商名称
            if (deliveryNote.getVendorId() == null) {
                Assert.notNull(user.getCompanyId(), "该用户不属于任何供应商，请完善供应商信息后重试。");
                deliveryNote.setVendorId(user.getCompanyId());
            }
            if (deliveryNote.getVendorCode() == null) {
                deliveryNote.setVendorCode(user.getCompanyCode());
            }
            if (deliveryNote.getVendorName() == null) {
                deliveryNote.setVendorName(user.getCompanyName());
            }

        } else {

            DeliveryNote checkDeliveryNote = deliveryNoteMapper.selectById(deliveryNote.getDeliveryNoteId());
            //送货单==null时抛出
            Assert.notNull(checkDeliveryNote, "送货单不存在");
            //只有在拟定状态才可以修改
            Assert.isTrue(StringUtils.equals(DeliveryNoteStatus.CREATE.name(), deliveryNote.getDeliveryNoteStatus()), "只有拟定状态才能修改");
        }
        this.saveOrUpdate(deliveryNote);


        //根据库存升序排序
        Collections.sort(detailList, new Comparator<DeliveryNoteDetail>() {
            @Override
            public int compare(DeliveryNoteDetail o1, DeliveryNoteDetail o2) {
                Assert.notNull(o1.getOrderDetailId(), "订单明细信息缺失");
                Assert.notNull(o2.getOrderDetailId(), "订单明细信息缺失");
                //情况一：如果订单明细相同
                //1、如果到货计划行信息都存在则对比
                //2、如果到货计划一个存在，一个不存在，直接让不存在的排第一
                if (o1.getOrderDetailId().equals(o2.getOrderDetailId())) {
                    //1、不能选择多行相同的采购订单
                    if (o1.getCeeaArrivalDetailId() == null && (o2.getCeeaArrivalDetailId()) == null) {
                        Assert.isTrue(false, "不能选择多行相同的采购订单");
                        //2、按照到货计划id排序
                    } else if (o1.getCeeaArrivalDetailId() != null && o2.getCeeaArrivalDetailId() != null) {
                        //3、不能选择多行相同的到货计划行
                        Assert.isTrue(o1.getCeeaArrivalDetailId().equals(o2.getCeeaArrivalDetailId()), "不能选择多行相同的到货计划行");
                        return o1.getCeeaArrivalDetailId().compareTo(o2.getCeeaArrivalDetailId());
                    } else {
                        //4、不存在到货计划，先按存在的存
                        return o1.getCeeaArrivalDetailId() == null ? -1 : 1;
                    }
                }
                return o1.getOrderDetailId().compareTo(o2.getOrderDetailId());
            }
        });
        //开始保存或新增送货单明细
        //获取所有的明细列表
        List<Long> collect = detailList.stream().map(DeliveryNoteDetail::getOrderDetailId).distinct().collect(Collectors.toList());
        List<Long> getDeliveryNoteDetailIdList = detailList.stream().filter(s -> s.getDeliveryNoteDetailId() != null).map(DeliveryNoteDetail::getDeliveryNoteDetailId).distinct().collect(Collectors.toList());
        //获取保存外的数据--》删除
        QueryWrapper<DeliveryNoteDetail> deliveryNoteDetailQueryWrapper = new QueryWrapper<>();
        deliveryNoteDetailQueryWrapper.eq("DELIVERY_NOTE_ID", deliveryNote.getDeliveryNoteId());
        deliveryNoteDetailQueryWrapper.notIn(CollectionUtils.isNotEmpty(getDeliveryNoteDetailIdList), "DELIVERY_NOTE_DETAIL_ID", getDeliveryNoteDetailIdList);
        List<DeliveryNoteDetail> list = iDeliveryNoteDetailService.list(deliveryNoteDetailQueryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            // 释放数量
            this.freedNumber(list);
            List<Long> collectList = list.stream().map(DeliveryNoteDetail::getDeliveryNoteDetailId).distinct().collect(Collectors.toList());
            // 删除要清除的行
            iDeliveryNoteDetailService.removeByIds(collectList);
        }


        QueryWrapper<OrderRequestDTO> orderRequestDTOQueryWrapper = new QueryWrapper<>();
        orderRequestDTOQueryWrapper.in("od.ORDER_DETAIL_ID", collect);
        orderRequestDTOQueryWrapper.eq("od.ORDER_DETAIL_STATUS", OrderDetailStatus.ACCEPT.name());
        List<OrderDetailDTO> listCopy = orderDetailMapper.findListCopy(orderRequestDTOQueryWrapper);
        Assert.isTrue(CollectionUtils.isNotEmpty(listCopy), "找不到对应的订单明细。");
        for (DeliveryNoteDetail item : detailList) {
            Long deliveryNoteDetailId = item.getDeliveryNoteDetailId();
            // 原送货数量
            BigDecimal oldDeliveryQuantity = BigDecimal.ZERO;
            if (null != deliveryNoteDetailId) {
                DeliveryNoteDetail noteDetail = iDeliveryNoteDetailService.getById(deliveryNoteDetailId);
                if(null != noteDetail) oldDeliveryQuantity = null != noteDetail.getDeliveryQuantity()?noteDetail.getDeliveryQuantity():BigDecimal.ZERO;
            }
            // 本次送货数量
            BigDecimal deliveryQuantity = item.getDeliveryQuantity();
            BigDecimal subtract = deliveryQuantity.subtract(oldDeliveryQuantity);
            if(DeliveryNoteSource.PURCHASE_ORDER.name().equals(item.getOrderSource())){
                /**
                 *  供应商创建选择[采购订单]创建[送货单], 回写送货数量到[采购订单]的累计送货数量/送货通知数量
                 */
                // 查询订单明细行
                OrderDetail orderDetail = orderDetailService.getById(item.getOrderDetailId());
                if(null != orderDetail){
                    BigDecimal deliveryNoticeQuantity = null != orderDetail.getDeliveryNoticeQuantity()? orderDetail.getDeliveryNoticeQuantity():BigDecimal.ZERO;
                    orderDetail.setDeliveryNoticeQuantity(deliveryNoticeQuantity.add(subtract));
                    orderDetailService.updateById(orderDetail);
                }
            }if(DeliveryNoteSource.DELIVERY_NOTICE.name().equals(item.getOrderSource())){
                /**
                 * 供应商创建选择[送货通知]创建[送货单], 回写送货数量到[送货通知]的累计送货数量
                 */
                // 查询送货通知单
                DeliveryNotice deliveryNotice = iDeliveryNoticeService.getById(item.getDeliveryNoticeId());
                if(null != deliveryNotice){
                    BigDecimal deliveryQuantitySum = null != deliveryNotice.getDeliveryQuantitySum()? deliveryNotice.getDeliveryQuantitySum():BigDecimal.ZERO;
                    deliveryNotice.setDeliveryQuantitySum(deliveryQuantitySum.add(subtract));
                    iDeliveryNoticeService.updateById(deliveryNotice);
                }
            }

            if (deliveryNoteDetailId == null) {
                item.setDeliveryNoteDetailId(IdGenrator.generate());
                item.setDeliveryNoteId(deliveryNote.getDeliveryNoteId());
                //批量保存
                iDeliveryNoteDetailService.save(item);
            } else {
                //批量修改
                iDeliveryNoteDetailService.updateById(item);
            }
        }


        //保存附件
        if (CollectionUtils.isNotEmpty(procurementFile)) {
            fileCenterClient.bindingFileupload(procurementFile, deliveryNote.getDeliveryNoteId());
        }
        //保存新增的wms清单
        if (CollectionUtils.isNotEmpty(deliveryNoteWms)) {
            for (DeliveryNoteWms deliveryNoteWmss : deliveryNoteWms) {
                if (deliveryNoteWmss.getDeliveryNoteId() == null) {
                    deliveryNoteWmss.setDeliveryNoteId(deliveryNote.getDeliveryNoteId());
                    UpdateWrapper<DeliveryNoteWms> wrapper = new UpdateWrapper<>();
                    wrapper.eq("DELIVERY_NOTE_WMS_ID", deliveryNoteWmss.getDeliveryNoteWmsId());
                    iDeliveryNoteWmsService.update(deliveryNoteWmss, wrapper);
                }
            }
        }
        doForConfirmDeliver(deliveryNote.getDeliveryNoteId());
    }

    @Override
    @Transactional
    public void delete(Long deliveryNoteId) {
        List<DeliveryNoteDetail> deliveryNoteDetails = iDeliveryNoteDetailService.list(Wrappers.lambdaQuery(DeliveryNoteDetail.class).
                eq(DeliveryNoteDetail::getDeliveryNoteId, deliveryNoteId));
        if(CollectionUtils.isNotEmpty(deliveryNoteDetails)){
            // 释放数量
            this.freedNumber(deliveryNoteDetails);
            // 删除行
            List<Long> ids = deliveryNoteDetails.stream().map(DeliveryNoteDetail::getDeliveryNoteDetailId).collect(Collectors.toList());
            iDeliveryNoteDetailService.removeByIds(ids);
        }
        this.removeById(deliveryNoteId);
    }

    // 送货单删除释放数量
    public void freedNumber(List<DeliveryNoteDetail> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            // 释放送货通知或采购单明细的累计送货数量
            list.forEach(deliveryNoteDetail -> {
                // 本次送货数量
                BigDecimal deliveryQuantity = null != deliveryNoteDetail.getDeliveryQuantity() ? deliveryNoteDetail.getDeliveryQuantity() : BigDecimal.ZERO;
                if(DeliveryNoteSource.PURCHASE_ORDER.name().equals(deliveryNoteDetail.getOrderSource())){
                    /**
                     *  回写送货数量到[采购订单]的累计送货数量/送货通知数量
                     */
                    // 查询订单明细行
                    OrderDetail orderDetail = orderDetailService.getById(deliveryNoteDetail.getOrderDetailId());
                    if(null != orderDetail){
                        // 送货通知引用数量
                        BigDecimal deliveryNoticeQuantity = null != orderDetail.getDeliveryNoticeQuantity()?orderDetail.getDeliveryNoticeQuantity():BigDecimal.ZERO;
                        BigDecimal subtract = deliveryNoticeQuantity.subtract(deliveryQuantity);
                        orderDetail.setDeliveryNoticeQuantity(subtract.compareTo(BigDecimal.ZERO) >= 0?subtract:BigDecimal.ZERO);
                        orderDetailService.updateById(orderDetail);
                    }
                }if(DeliveryNoteSource.DELIVERY_NOTICE.name().equals(deliveryNoteDetail.getOrderSource())){
                    /**
                     * 回写送货数量到[送货通知]的累计送货数量
                     */
                    // 查询送货通知单
                    DeliveryNotice deliveryNotice = iDeliveryNoticeService.getById(deliveryNoteDetail.getDeliveryNoticeId());
                    if(null != deliveryNotice){
                        // 累计送货数量
                        BigDecimal deliveryQuantitySum = null != deliveryNotice.getDeliveryQuantitySum()?deliveryNotice.getDeliveryQuantitySum():BigDecimal.ZERO;
                        BigDecimal subtract = deliveryQuantitySum.subtract(deliveryQuantity);
                        deliveryNotice.setDeliveryQuantitySum(subtract.compareTo(BigDecimal.ZERO) >= 0?subtract:BigDecimal.ZERO);
                        iDeliveryNoticeService.updateById(deliveryNotice);
                    }
                }
            });
        }
    }

    public void doForConfirmDeliver(Long deliveryNoteId) {
        QueryWrapper<DeliveryNoteDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("DELIVERY_NOTE_ID", deliveryNoteId);
        List<DeliveryNoteDetail> deliveryNoteDetailList = deliveryNoteDetailMapper.selectList(wrapper);
        //确认发货，修改采购明细对应的累计收货数量
        for (DeliveryNoteDetail deliveryNoteDetail : deliveryNoteDetailList) {
            Assert.notNull(deliveryNoteDetail.getDeliveryQuantity(), "存在本次送货数量为零的送货明细。");
            //存在到货计划的要回写采购订单的送货单据
            if (deliveryNoteDetail.getCeeaArrivalDetailId() != null) {
                // 回写到货计划明细单号
                OrderDeliveryDetail orderDeliveryDetail = iOrderDeliveryDetailService.getById(deliveryNoteDetail.getCeeaArrivalDetailId());
                Assert.notNull(orderDeliveryDetail, "找不到扣库的到货计划单。");
                //回写到货计划
                //获取订单数量
                BigDecimal orderNum = orderDeliveryDetail.getPlanReceiveNum();
                //获取累计数量
                BigDecimal receiveSum = orderDeliveryDetail.getDeliveryQuantity();
                //计算累计数量
                BigDecimal adds = receiveSum.add(deliveryNoteDetail.getDeliveryQuantity());
                Assert.isTrue(adds.compareTo(orderNum) != 1, "物料编号：" + orderDeliveryDetail.getMaterialCode() + ",的本次送货数量累计已送货数量大于订单数量");
                //替换累计数据值
                iOrderDeliveryDetailService.updateById(new OrderDeliveryDetail().setDeliveryQuantity(adds).setOrderDeliveryDetailId(orderDeliveryDetail.getOrderDeliveryDetailId()));
                //回写采购订单
            }
            OrderDetail orderDetail = orderDetailMapper.selectById(deliveryNoteDetail.getOrderDetailId());
            Assert.notNull(orderDetail, "找不到扣库的采购订单。");
            //获取订单数量
            BigDecimal orderNum = orderDetail.getOrderNum()==null?new BigDecimal(0):orderDetail.getOrderNum();
            //获取累计数量
            BigDecimal receiveSum = orderDetail.getReceiveSum()==null?new BigDecimal(0):orderDetail.getReceiveSum();
            //计算累计数量
            BigDecimal adds = receiveSum.add(deliveryNoteDetail.getDeliveryQuantity());
            Assert.isTrue(adds.compareTo(orderNum) != 1, "物料编号：" + orderDetail.getMaterialCode() + ",的本次送货数量累计已送货数量大于订单数量");
            //替换累计数据值
            //orderDetail.setReceiveSum(adds);
            orderDetailMapper.updateById(new OrderDetail().setReceiveSum(adds).setOrderDetailId(deliveryNoteDetail.getOrderDetailId()));
        }
    }
    /**
     * 回写过程
     *
     * @param orderDetail
     * @param deliveryNoteDetail
     * @return
     */
    @Transactional
    public DeliveryNoteDetail updateDliveryplan(OrderDetail orderDetail, DeliveryNoteDetail deliveryNoteDetail) throws Exception {
        if (StringUtils.isNotEmpty(orderDetail.getCeeaPlanReceiveNum()) && orderDetail.getCeeaPromiseReceiveDate() != null) {
            QueryWrapper<DeliverPlan> deliverPlanQueryWrapper = new QueryWrapper<>();
            deliverPlanQueryWrapper.eq("DELIVER_PLAN_NUM", orderDetail.getCeeaPlanReceiveNum());
            //获取到货计划号的对应的到货计划行
            List<DeliverPlan> deliverPlanList = iDeliverPlanService.list(deliverPlanQueryWrapper);
            Assert.isTrue(CollectionUtils.isNotEmpty(deliverPlanList), "找不到相应的到货计划。");
            QueryWrapper<DeliverPlanDetail> deliverPlanDetailQueryWrapper = new QueryWrapper<>();
            deliverPlanDetailQueryWrapper.eq("DELIVER_PLAN_ID", deliverPlanList.get(0).getDeliverPlanId());
            List<DeliverPlanDetail> deliverPlanDetailList = iDeliverPlanDetailService.list(deliverPlanDetailQueryWrapper);
            Assert.isTrue(CollectionUtils.isNotEmpty(deliverPlanDetailList), "找不到相应的到货计划明细。");
            DeliverPlanDetail getDeliverPlanDetail = new DeliverPlanDetail();
            boolean flag = false;
            //已送货总数量
            BigDecimal tqDelivered = new BigDecimal(0).add(deliveryNoteDetail.getDeliveryQuantity());
            //匹配总数量
            BigDecimal mtNumber = new BigDecimal(0);
            for (DeliverPlanDetail deliverPlanDetail : deliverPlanDetailList) {
                //累计送货总数量
                if (deliverPlanDetail.getDeliveryQuantity() == null) {
                    deliverPlanDetail.setDeliveryQuantity(new BigDecimal(0));
                }
                if (deliverPlanDetail.getOrderQuantityMatched() == null) {
                    deliverPlanDetail.setOrderQuantityMatched(new BigDecimal(0));
                }
                tqDelivered = tqDelivered.add(deliverPlanDetail.getDeliveryQuantity());
                if (deliverPlanDetail.getOrderQuantityMatched().compareTo(new BigDecimal(0)) > 0) {
                    mtNumber = mtNumber.add(deliverPlanDetail.getOrderQuantityMatched());
                }
                if (deliverPlanDetail.getSchMonthlyDate() == DateUtil.dateToLocalDate(orderDetail.getCeeaPromiseReceiveDate())) {
                    flag = true;
                    getDeliverPlanDetail = deliverPlanDetail;
                    getDeliverPlanDetail.setDeliveryQuantity(deliveryNoteDetail.getDeliveryQuantity());
                }
            }
            //修改对应到货计划的匹配度
            DeliverPlan deliverPlan = new DeliverPlan()
                    .setDeliverPlanId(deliverPlanDetailList.get(0).getDeliverPlanId())
                    .setMatchDegree(tqDelivered.divide(mtNumber));
            iDeliverPlanService.updateById(deliverPlan);
            //修改到货计划行的已送货数量
            if (flag) {
                iDeliverPlanDetailService.updateById(getDeliverPlanDetail);
            }
//----------------------------------------------------------------------------------------------------------
            flag = false;
            //获取到货计划行ID对应的所有到货计划送货明细
            OrderDeliveryDetail orderDeliveryDetail = new OrderDeliveryDetail().setOrderDetailId(orderDetail.getOrderDetailId());
            List<OrderDeliveryDetail> orderDeliveryDetailList = iOrderDeliveryDetailService.orderDeliveryDetailList(orderDeliveryDetail);
            if (CollectionUtils.isNotEmpty(orderDeliveryDetailList)) {
                Assert.isTrue(false, "回写过程报错了");
            }
            for (OrderDeliveryDetail orderDeliveryDetails : orderDeliveryDetailList) {
                if (orderDeliveryDetails.getOrderDetailId() == orderDetail.getOrderDetailId()) {
                    flag = true;
                    orderDeliveryDetail = orderDeliveryDetails;
                    if (deliveryNoteDetail.getDeliveryQuantity().compareTo(new BigDecimal(0)) < 0) {
                        orderDeliveryDetail.setDeliveryQuantity(new BigDecimal(0));
                    } else {
                        if (orderDeliveryDetail.getDeliveryQuantity().compareTo(new BigDecimal(0)) > 0) {
                            Assert.isTrue(false, "到货计划已经被使用");
                        }
                        orderDeliveryDetail.setDeliveryQuantity(deliveryNoteDetail.getDeliveryQuantity());
                    }
                }
            }
            //修改对应的到货计划送货详情行id
            if (flag) {
                iOrderDeliveryDetailService.updateById(orderDeliveryDetail);
            }
            return deliveryNoteDetail.setCeeaArrivalDetailId(orderDeliveryDetail.getOrderDeliveryDetailId());
        }
        return deliveryNoteDetail;
    }


    @Override
    @AuthData(module = {MenuEnum.BUYER_DELIVERY_ORDER , MenuEnum.SUPPLIER_SIGN})
    public List<DeliveryNoteDTO> listPage(DeliveryNoteRequestDTO deliveryNoteRequestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (ObjectUtils.isEmpty(loginAppUser)){
            return new ArrayList<>();
        }
        return deliveryNoteMapper.findDeliveryNoteList(deliveryNoteRequestDTO);
//        return deliveryNoteMapper.list(deliveryNoteRequestDTO);
    }



    @Override
    public WorkCount countCreate(DeliveryNoteRequestDTO deliveryNoteRequestDTO) {
        Integer count = deliveryNoteMapper.countCreate(deliveryNoteRequestDTO);

        WorkCount workCount = new WorkCount();
        workCount.setTitle("待创建送货单");
        workCount.setUrl("/vendorOrderSynergy/vendorDeliveryOrder?from=workCount&deliveryNoteStatus=" + deliveryNoteRequestDTO.getDeliveryNoteStatus());
        workCount.setCount(count);
        return workCount;
    }

    @Transactional
    @Override
    public void submitBatch(List<Long> ids) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        List list = new ArrayList();

        ids.forEach(item -> {
            DeliveryNote checkDeliveryNote = deliveryNoteMapper.selectById(item);
            Assert.notNull(checkDeliveryNote, "找不到送货单");
            if (!StringUtils.equals(DeliveryNoteStatus.CREATE.name(), checkDeliveryNote.getDeliveryNoteStatus())) {
                Assert.isTrue(false, "请提交待提交送货单");
            }

            List<DeliveryNoteDetail> deliveryNoteDetails = deliveryNoteDetailMapper.selectList(new QueryWrapper<DeliveryNoteDetail>()
                    .eq("DELIVERY_NOTE_ID", checkDeliveryNote.getDeliveryNoteId()));
            for (DeliveryNoteDetail deliveryNoteDetail : deliveryNoteDetails) {
                Assert.notNull(deliveryNoteDetail.getDeliveryQuantity(), "本次送货数量不能为空，请检查送货数量");

                OrderDetail orderDetail = orderDetailMapper.selectById(deliveryNoteDetail.getOrderDetailId());
                Assert.notNull(orderDetail, "找不到送订单明细");
                Order order = orderMapper.selectById(orderDetail.getOrderId());
                Assert.notNull(orderDetail, "找不到送订单");

                if (StringUtils.equals(order.getJitOrder(), JITOrder.Y.name())) {
                    //是jit订单，判断送货数量是否超过通
                    Assert.notNull(deliveryNoteDetail.getDeliveryNoticeId(), "JIt订单明细找不到送货通知单信息");
                    DeliveryNotice notice = iDeliveryNoticeService.getById(deliveryNoteDetail.getDeliveryNoticeId());
                    Assert.notNull(notice, "JIt订单明细找不到送货通知单信息");

                    if ((deliveryNoteDetail.getDeliveryQuantity().add(notice.getReceiveSum())).compareTo(notice.getNoticeSum()) == 1) {
                        Assert.isTrue(false, "存在本次送货数量>送货通知单通知数量-已收货数量，请检查本次送货数量");
                    }
                } else {
                    //检查本次送货数量是否大于订单数量-累计收货数量
                    if ((deliveryNoteDetail.getDeliveryQuantity().add(orderDetail.getReceiveSum())).compareTo(orderDetail.getOrderNum()) == 1) {
                        Assert.isTrue(false, "存在本次送货数量>订单数量-累计收货数量，请检查本次送货数量");
                    }
                }
            }

            DeliveryNote deliveryNote = new DeliveryNote();
            deliveryNote.setDeliveryNoteId(item);
            deliveryNote.setDeliveryNoteStatus(DeliveryNoteStatus.DELIVERED.name());
            deliveryNote.setSubmittedId(loginAppUser.getUserId());
            deliveryNote.setSubmittedBy(loginAppUser.getUsername());
            deliveryNote.setSubmittedTime(new Date());

            list.add(deliveryNote);
        });
        this.updateBatchById(list);
    }

    /**
     * 供应商对送货单确认发货，更新送货单状态
     *
     * @param deliveryNoteId
     */
    @Override
    public void confirmDeliveryNoteStatus(Long deliveryNoteId) {
        DeliveryNote deliveryNote = deliveryNoteMapper.selectById(deliveryNoteId);
        deliveryNote.setDeliveryNoteStatus(DeliveryNoteStatus.DELIVERED.name());
        deliveryNoteMapper.updateById(deliveryNote);
    }

    @Override
    public void cancelDeliveryNoteStatus(Long deliveryNoteId) {
        DeliveryNote deliveryNote = deliveryNoteMapper.selectById(deliveryNoteId);
        deliveryNote.setDeliveryNoteStatus(DeliveryNoteStatus.CANCELLING.name());
        deliveryNoteMapper.updateById(deliveryNote);
    }

    /**
     * 确认发货
     *
     * @param deliveryNoteId
     */
    @Transactional
    @Override
    public void getAffirmDelivery(Long deliveryNoteId) {
        //获取id对应的送货单
        DeliveryNote deliveryNote = deliveryNoteMapper.selectById(deliveryNoteId);
        //修改送货单的送货状态为确认发货
        deliveryNote.setDeliveryNoteStatus(DeliveryNoteStatus.DELIVERED.name());
        //修改对应送货单状态
        deliveryNoteMapper.updateById(deliveryNote);
        //获取送货单对应的列表
       /* QueryWrapper<DeliveryNoteDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("DELIVERY_NOTE_ID", deliveryNoteId);
        List<DeliveryNoteDetail> deliveryNoteDetailList = deliveryNoteDetailMapper.selectList(wrapper);
        //确认发货，修改采购明细对应的累计收货数量
        for (DeliveryNoteDetail deliveryNoteDetail : deliveryNoteDetailList) {
            Assert.notNull(deliveryNoteDetail.getDeliveryQuantity(), "存在本次送货数量为零的送货明细。");
            //存在到货计划的要回写采购订单的送货单据
            if (deliveryNoteDetail.getCeeaArrivalDetailId() != null) {
                // 回写到货计划明细单号
                OrderDeliveryDetail orderDeliveryDetail = iOrderDeliveryDetailService.getById(deliveryNoteDetail.getCeeaArrivalDetailId());
                Assert.notNull(orderDeliveryDetail, "找不到扣库的到货计划单。");
                //回写到货计划
                //获取订单数量
                BigDecimal orderNum = orderDeliveryDetail.getPlanReceiveNum();
                //获取累计数量
                BigDecimal receiveSum = orderDeliveryDetail.getDeliveryQuantity();
                //计算累计数量
                BigDecimal adds = receiveSum.add(deliveryNoteDetail.getDeliveryQuantity());
                Assert.isTrue(adds.compareTo(orderNum) != 1, "物料编号：" + orderDeliveryDetail.getMaterialCode() + ",的本次送货数量累计已送货数量大于订单数量");
                //替换累计数据值
                iOrderDeliveryDetailService.updateById(new OrderDeliveryDetail().setDeliveryQuantity(adds).setOrderDeliveryDetailId(orderDeliveryDetail.getOrderDeliveryDetailId()));
                //回写采购订单
            }
            OrderDetail orderDetail = orderDetailMapper.selectById(deliveryNoteDetail.getOrderDetailId());
            Assert.notNull(orderDetail, "找不到扣库的采购订单。");
            //获取订单数量
            BigDecimal orderNum = orderDetail.getOrderNum()==null?new BigDecimal(0):orderDetail.getOrderNum();
            //获取累计数量
            BigDecimal receiveSum = orderDetail.getReceiveSum()==null?new BigDecimal(0):orderDetail.getReceiveSum();
            //计算累计数量
            BigDecimal adds = receiveSum.add(deliveryNoteDetail.getDeliveryQuantity());
            Assert.isTrue(adds.compareTo(orderNum) != 1, "物料编号：" + orderDetail.getMaterialCode() + ",的本次送货数量累计已送货数量大于订单数量");
            //替换累计数据值
            //orderDetail.setReceiveSum(adds);
            orderDetailMapper.updateById(new OrderDetail().setReceiveSum(adds).setOrderDetailId(deliveryNoteDetail.getOrderDetailId()));
        }*/
//        try {
//            Organization organization = baseClient.get(deliveryNote.getOrganizationId());
//            Assert.notNull(organization, "找不到对应的库存组织。");
//            Assert.isTrue(StringUtils.isNotEmpty(organization.getErpOrgId()), "找不到对应的库存组织的repID。");
//            deliveryNote.setOrganizationId(Long.valueOf(organization.getErpOrgId()));
//            CompanyInfo companyInfo = supplierClient.getCompanyInfo(deliveryNote.getVendorId());
//            Assert.notNull(companyInfo, "找不到对应的供应商信息。");
//            Assert.notNull(companyInfo.getErpVendorId(), "找不到对应的供应商的erpID。");
//            deliveryNote.setVendorId(companyInfo.getErpVendorId());
//            Organization org = baseClient.get(deliveryNote.getOrgId());
//            Assert.notNull(org, "找不到对应的供应商信息。");
//            Assert.isTrue(StringUtils.isNotEmpty(org.getErpOrgId()), "找不到对应的供应商的erpID。");
//            deliveryNote.setOrgId(Long.valueOf(org.getErpOrgId()));
//        } catch (Exception e) {
//            log.error("操作失败", e);
//            Assert.isTrue(false, e.getMessage() + "获取基础信息失败，请重试。");
//        }
        //石墨有人说关闭的
        //this.getBindQSErp(deliveryNote, deliveryNoteDetailList, "CREATE");
    }

    /**
     * 取消发货
     *
     * @param deliveryNoteId
     */
    @Transactional
    @Override
    public void getCancelDelivery(Long deliveryNoteId) {
        //修改订单状态
        DeliveryNote deliveryNote = deliveryNoteMapper.selectById(deliveryNoteId);
        deliveryNote.setDeliveryNoteStatus(DeliveryNoteStatus.CREATE.name());
        deliveryNoteMapper.updateById(deliveryNote);
        //获取送货单对应的列表
        QueryWrapper<DeliveryNoteDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("DELIVERY_NOTE_ID", deliveryNoteId);
        List<DeliveryNoteDetail> deliveryNoteDetailList = deliveryNoteDetailMapper.selectList(wrapper);
        //取消发货，修改采购明细对应的累计收货数量
        for (DeliveryNoteDetail deliveryNoteDetail : deliveryNoteDetailList) {
            Assert.notNull(deliveryNoteDetail.getDeliveryQuantity(), "存在本次送货数量为零的送货明细。");
            //存在到货计划的要回写采购订单的送货单据
            if (deliveryNoteDetail.getCeeaArrivalDetailId() != null) {
                // 回写到货计划明细单号
                OrderDeliveryDetail orderDeliveryDetail = iOrderDeliveryDetailService.getById(deliveryNoteDetail.getCeeaArrivalDetailId());
                Assert.notNull(orderDeliveryDetail, "找不到扣库的到货计划单。");
                //获取订单数量
                BigDecimal orderNum = orderDeliveryDetail.getPlanReceiveNum();
                //获取累计数量
                BigDecimal receiveSum = orderDeliveryDetail.getDeliveryQuantity();
                //计算累计数量
                BigDecimal adds = receiveSum.subtract(deliveryNoteDetail.getDeliveryQuantity());
                //替换累计数据值
                iOrderDeliveryDetailService.updateById(new OrderDeliveryDetail().setDeliveryQuantity(adds).setOrderDeliveryDetailId(orderDeliveryDetail.getOrderDeliveryDetailId()));
            }
            OrderDetail orderDetail = orderDetailMapper.selectById(deliveryNoteDetail.getOrderDetailId());
            //获取订单数量
            BigDecimal orderNum = orderDetail.getOrderNum();
            //获取累计数量
            BigDecimal receiveSum = orderDetail.getReceiveSum();
            //计算累计数量
            BigDecimal adds = receiveSum.subtract(deliveryNoteDetail.getDeliveryQuantity());
            //替换累计数据值
            orderDetailMapper.updateById(new OrderDetail().setReceiveSum(adds).setOrderDetailId(deliveryNoteDetail.getOrderDetailId()));
        }
//        try {
//            Organization organization = baseClient.get(deliveryNote.getOrganizationId());
//            Assert.notNull(organization, "业务实体信息。");
//            Assert.isTrue(StringUtils.isNotEmpty(organization.getErpOrgId()), "找不到对应业务实体信息ErpOrgId。");
//            deliveryNote.setOrganizationId(Long.valueOf(organization.getErpOrgId()));
//            CompanyInfo companyInfo = supplierClient.getCompanyInfo(deliveryNote.getVendorId());
//            Assert.notNull(companyInfo, "找不到对应的供应商信息。");
//            Assert.notNull(companyInfo.getErpVendorId(), "找不到对应的供应商的erpID。");
//            deliveryNote.setVendorId(companyInfo.getErpVendorId());
//            Organization organization1 = baseClient.get(deliveryNote.getOrgId());
//            Assert.notNull(organization1, "业务实体信息。");
//            Assert.isTrue(StringUtils.isNotEmpty(organization1.getErpOrgId()), "找不到对应业务实体信息ErpOrgId。");
//            deliveryNote.setOrgId(Long.valueOf(organization1.getErpOrgId()));
//        } catch (Exception e) {
//            log.error("操作失败", e);
//            Assert.isTrue(false, "获取基础信息失败，请重试。");
//        }
        //石墨有人说关闭的
        //this.getBindQSErp(deliveryNote, deliveryNoteDetailList, "CANCEL");
    }

    /**
     * 创建送货预约-选择送货单
     * @param deliveryNoteRequestDTO
     * @return
     */
    @Override
    public PageInfo<DeliveryNoteDTO> listInDeliveryAppoint(DeliveryNoteRequestDTO deliveryNoteRequestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(Objects.isNull(loginAppUser)){
            return new PageInfo<>(Collections.EMPTY_LIST);
        }
        if(!UserType.VENDOR.name().equals(loginAppUser.getUserType()) &&
            !UserType.BUYER.name().equals(loginAppUser.getUserType())
        ){
            throw new BaseException(LocaleHandler.getLocaleMsg("用户类型不存在"));
        }
        if(UserType.VENDOR.name().equals(loginAppUser.getUserType())){
            deliveryNoteRequestDTO.setVendorId(loginAppUser.getCompanyId());
        }
        PageUtil.startPage(deliveryNoteRequestDTO.getPageNum(), deliveryNoteRequestDTO.getPageSize());
        return new PageInfo<DeliveryNoteDTO>(deliveryNoteMapper.listInDeliveryAppoint(deliveryNoteRequestDTO));
    }

    /**
     * 确认发货推送erp
     *
     * @param deliveryNote
     * @param deliveryNoteDetailList
     * @param status                 发货状态
     * @throws Exception
     */
    @Transactional
    public void getBindQSErp(DeliveryNote deliveryNote, List<DeliveryNoteDetail> deliveryNoteDetailList, String status) {
        Response response = new Response();
        try {
            //String str = "http://soatest.longi.com:8011/NSrm/Erp/VendorShipment/ProxyServices/ErpAcceptVendorShipmentSoapProxy?wsdl";
            // 修改密码接口地址
            //String address = str;
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();

            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(address);
            jaxWsProxyFactoryBean.setUsername(USERNAME);
            jaxWsProxyFactoryBean.setPassword(PASSWORD);
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(ErpAcceptVendorShipmentSoapBizPtt.class);
            // 创建一个代理接口实现
            ErpAcceptVendorShipmentSoapBizPtt service = (ErpAcceptVendorShipmentSoapBizPtt) jaxWsProxyFactoryBean.create();
            //送货单明细列表
            ArrayList<APPSCUXRCVASNIFAX1362720X1X21> entityLineList = new ArrayList<>();
            ArrayList<APPSCUXRCVASNIFAX1362720X1X36> appscuxrcvasnifax1362720X1X36s = new ArrayList<>();
            appscuxrcvasnifax1362720X1X36s.add(new APPSCUXRCVASNIFAX1362720X1X36());
            APPSCUXRCVASNIFAX1362720X1X35 appscuxrcvasnifax1362720X1X35 = new APPSCUXRCVASNIFAX1362720X1X35();
            appscuxrcvasnifax1362720X1X35.setLotlistitem(appscuxrcvasnifax1362720X1X36s);
            //添加送货明细
            for (DeliveryNoteDetail deliveryNoteDetail : deliveryNoteDetailList) {
                OrderDetailDTO orderDetailDTO = orderDetailMapper.selectByIdAndCode(deliveryNoteDetail.getOrderDetailId());
                Assert.isTrue(StringUtils.isNotEmpty(orderDetailDTO.getOrderNumber()), "订单号获取失败。");
                Assert.notNull(orderDetailDTO.getLineNum(), "订单行号获取失败。");
                entityLineList.add(APPSCUXRCVASNIFAX1362720X1X21.builder()
                        .sourcelineid(deliveryNoteDetail.getDeliveryNoteDetailId().toString())//来源系统行Id
                        .ponumber(orderDetailDTO.getOrderNumber())//订单编号
                        .polinenumber(BigDecimal.valueOf(orderDetailDTO.getLineNum()))//订单行号
                        .shiptoorganizationid(BigDecimal.valueOf(deliveryNote.getOrganizationId()))//收货库存组织ID
                        .linestatus(status)  //订单行状态
                        .itemnumber(orderDetailDTO.getMaterialCode())//物料编码
                        .quantity(deliveryNoteDetail.getDeliveryQuantity())//送货数量
                        .lotlist(appscuxrcvasnifax1362720X1X35)
                        .unitofmeasure(orderDetailDTO.getUnit())//单位
                        .build());
            }
            //添加送货单头
            APPSCUXRCVASNIFACX1362720X1X8 entityHead = APPSCUXRCVASNIFACX1362720X1X8.builder()
                    //业务实体
                    .operationunitid(BigDecimal.valueOf(deliveryNote.getOrgId()))
                    .operationname(deliveryNote.getOrgName())
                    //送货单号
                    .shipmentnum(deliveryNote.getDeliveryNumber())
                    //供应商
                    .vendorid(BigDecimal.valueOf(deliveryNote.getVendorId()))
                    .vendorname(deliveryNote.getVendorName())
                    //供应商送货日期
                    .shippeddate(DateUtil.format(deliveryNote.getDeliveryDate()))
                    //预计到货日期
                    .expectedreceiptdate(DateUtil.format(deliveryNote.getExpectedArrivalDate()))
                    //来源系统
                    .sourcesyscode(DataSourceEnum.NSRM_SYS.getKey())
                    //来源系统头Id
                    .sourceheaderid(deliveryNote.getDeliveryNoteId().toString())
                    //接口说明
                    .ifacemean("供应商送货单据")
                    .lines(APPSCUXRCVASNIFAX1362720X1X20.builder().linesitem(entityLineList).build())
                    .build();

            List<APPSCUXRCVASNIFACX1362720X1X8> entityList = new ArrayList<>();
            entityList.add(entityHead);

            InputAcceptVendor inputAcceptVendor = new InputAcceptVendor();
            inputAcceptVendor.setPasninfotbl(APPSCUXRCVASNIFACX1362720X1X7.builder().pasninfotblitem(entityList).build());
            APPSCUXRCVASNIFACX1362720X1X1 appscuxrcvasnifacx1362720X1X1 = new APPSCUXRCVASNIFACX1362720X1X1();
            appscuxrcvasnifacx1362720X1X1.setInstid(deliveryNote.getDeliveryNoteId().toString());
            appscuxrcvasnifacx1362720X1X1.setRequesttime(DateUtil.format(new Date()));
            inputAcceptVendor.setPesbinforec(appscuxrcvasnifacx1362720X1X1);
            //返回信息
            OutputAcceptVendor outputAcceptVendor = service.erpAcceptVendorShipmentSoapBiz(inputAcceptVendor);
            if (null != outputAcceptVendor.getXasnresulttbltype()) {
                log.debug("-----------------------------------连接成功----------------------------------------");
                APPSCUXRCVASNIFAX1362720X1X39 xesbresultinforec = outputAcceptVendor.getXesbresultinforec();
                Assert.notNull(xesbresultinforec, "Erp对接失败！请稍后重试");
                Assert.isTrue(StringUtils.isNotEmpty(xesbresultinforec.getReturnstatus()), "Erp对接失败！返回状态为空，请稍后重试");
                Assert.isTrue("S".equals(xesbresultinforec.getReturnstatus()), "返回错误信息:" + xesbresultinforec.getReturnmsg().toString());
                log.debug("成功标识：" + xesbresultinforec.toString());
                log.debug("成功调用：response: " + JsonUtil.entityToJsonStr(response));
            }
        } catch (Exception e) {
            log.error("错误信息: ", e);
            Assert.isTrue(false, e.getMessage());
        }

    }
}
