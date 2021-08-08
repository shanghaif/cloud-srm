package com.midea.cloud.srm.po.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.order.JITOrder;
import com.midea.cloud.common.enums.order.NoteStatus;
import com.midea.cloud.common.enums.pm.po.PurchaseOrderEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.rbac.user.dto.UserPermissionDTO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.DeliveryNotice;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.DeliveryNoticeImportVO;
import com.midea.cloud.srm.po.order.service.IDeliveryNoticeService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

/**
 * <pre>
 *  送货通知单表 服务实现类
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
public class DeliveryNoticeServiceImpl implements IDeliveryNoticeService {
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private RbacClient rbacClient;
    @Resource
    private com.midea.cloud.srm.supcooperate.order.service.IDeliveryNoticeService deliveryNoticeService;
    @Resource
    private IOrderService scIOrderService;
    @Resource
    private IOrderDetailService scIOrderDetailService;

    @Override
    @Transactional
    public void releasedBatch(List<DeliveryNotice> deliveryNotices) {
        deliveryNoticeService.releasedBatch(deliveryNotices);
    }


    /**
     * 获取检验的导入数据
     * @param list
     */
    private Map<Integer,List<DeliveryNoticeImportVO>> getCheckImportData(List<DeliveryNoticeImportVO> list){
        Integer deliveryNoticeHeadSum = 0;
        Map<Integer,List<DeliveryNoticeImportVO>> deliveryNotices = new HashMap<>();
        for(int i=0;i<list.size();i++){
            DeliveryNoticeImportVO item = list.get(i);
            if(i==0){
                Assert.isTrue(item.getLineNum()==1,"第" + (i + 2) + "行获取送货通知单行号填写错误");

                List<DeliveryNoticeImportVO> list1 = new ArrayList();
                list1.add(item);
                deliveryNotices.put(deliveryNoticeHeadSum++,list1);
            }else{
                DeliveryNoticeImportVO lastItem = list.get(i-1);//获取上一行数据
                if(item.getLineNum()==lastItem.getLineNum()+1){
                    //行号递增，可能是同一个通知单的明细
                    //判断与上一个采购组织是否一致，不一致则提示
                    Assert.isTrue(StringUtils.equals(item.getOrganizationName(),lastItem.getOrganizationName()),
                            "第" + (i + 1) + "行与第"+(i+2)+"行的采购组织不一致");
                    deliveryNotices.get(deliveryNoticeHeadSum-1).add(item);
                }else{
                    Assert.isTrue(item.getLineNum()==1,"第" + (i + 2) + "行送货通知单行号错误");
                    //下一个通知单
                    List<DeliveryNoticeImportVO> list1 = new ArrayList();
                    list1.add(item);
                    deliveryNotices.put(deliveryNoticeHeadSum++,list1);
                }
            }
        }
        return deliveryNotices;
    }

    /**
     * 获取导入的数据
     * @param voList
     * @return
     */
    private List<DeliveryNoticeImportVO> getImportData(List<Object> voList){
        List<DeliveryNoticeImportVO> list = new ArrayList();
        for(int i=0;i<voList.size();i++) {
            DeliveryNoticeImportVO vo = (DeliveryNoticeImportVO) voList.get(i);
            Assert.notNull(StringUtils.isNotBlank(vo.getOrganizationName()),"第" + (i + 2) + "行获取采购组织为空,请检查采购组织");
            Assert.notNull(StringUtils.isNotBlank(vo.getOrderNumber()),"第" + (i + 2) + "行获取采购订单编号为空,请检查采购订单编号");
            Assert.notNull(vo.getLineNum(),"第" + (i + 2) + "行获取送货通知单行号为空,请检查货通知单行号");
            Assert.notNull(vo.getOrderLineNum(),"第" + (i + 2) + "行获取采购订单行号为空,请检查采购订单行号");
            Assert.notNull(StringUtils.isNotBlank(vo.getMaterialCode()),"第" + (i + 2) + "行获取物料编码为空,请检查物料编码");
            Assert.notNull(vo.getNoticeSum(),"第" + (i + 2) + "行获取通知数量为空,请检查通知数量");
            Assert.notNull(vo.getDeliveryTime(),"第" + (i + 2) + "行获取送货时间为空,请检查送货时间");

            list.add(vo);
        }
        return list;
    }

    @Override
    @Transactional
    public void importExcelInfo(List<Object> voList) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        UserPermissionDTO userPermissionDTO = rbacClient.getByBuyer(loginAppUser.getUserId());

        List<DeliveryNoticeImportVO> importDatas = getImportData(voList);
        Map<Integer,List<DeliveryNoticeImportVO>> checkImportData = getCheckImportData(importDatas);

        List<DeliveryNotice> deliveryNotices = new ArrayList();
        for(Map.Entry<Integer,List<DeliveryNoticeImportVO>> item:checkImportData.entrySet()){
            Set<Long> vendorIdSet = new HashSet();//用于判断是否同一个供应商的订单
            List<DeliveryNoticeImportVO> vos = item.getValue();
            String deliveryNoticeNum = baseClient.seqGen(SequenceCodeConstant.SEQ_SSC_DELIVERY_NOTICE_NUM);
            for(int i=0;i<vos.size();i++){
                DeliveryNoticeImportVO vo = vos.get(i);
                Order order = scIOrderService.getOne(new QueryWrapper<Order>().eq("ORDER_NUMBER", vo.getOrderNumber()));
                Assert.notNull(order,"采购订单编号'"+vo.getOrderNumber()+"'找不到对应订单,请检查采购订单编号");
                Assert.isTrue(StringUtils.equals(order.getOrderStatus(), PurchaseOrderEnum.ACCEPT.name()),"采购订单编号'"+vo.getOrderNumber()+"'，采购商未接受订单，暂不可创建通知");
//                Assert.isTrue(checkOrganizationPermission(userPermissionDTO.getOrganizationUsers(),order.getOrganizationId()),
//                    "没有'"+vo.getOrganizationName()+"'采购组织权限");
                vendorIdSet.add(order.getVendorId());
                OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
                orderRequestDTO.setMaterialCode(vo.getMaterialCode());
                orderRequestDTO.setOrderId(order.getOrderId());
                orderRequestDTO.setJitOrder(JITOrder.Y.name());
//                orderRequestDTO.setLineNum(vo.getOrderLineNum());
                OrderDetail orderDetail = scIOrderDetailService.getJitOrderDetail(orderRequestDTO);
                Assert.notNull(orderDetail,"找不到订单号'"+vo.getOrderNumber()+"',物料编号：'"+vo.getMaterialCode()+"',采购订单行号'"+vo.getOrderLineNum()+"'的JIT订单明细");

                if(((vo.getNoticeSum().add(orderDetail.getReceiveSum())).compareTo(orderDetail.getOrderNum()))==1){
                    Assert.isTrue(false,"采购订单编号：'"+vo.getOrderNumber()+"',采购订单行号：'"
                            +vo.getOrderLineNum()+"'的通知送货数量>订单数量-订单已收货数量，请检查通知送货数量");
                }

                DeliveryNotice deliveryNotice = new DeliveryNotice();
                deliveryNotice.setDeliveryNoticeId(IdGenrator.generate());
                deliveryNotice.setNoticeSum(vo.getNoticeSum());
                deliveryNotice.setDeliveryTime(vo.getDeliveryTime());
                deliveryNotice.setLineNum(i+1);
                deliveryNotice.setOrderDetailId(orderDetail.getOrderDetailId());
                deliveryNotice.setOrderLineNum(vo.getOrderLineNum());
                deliveryNotice.setOrderId(order.getOrderId());
                deliveryNotice.setDeliveryNoticeNum(deliveryNoticeNum);
                deliveryNotice.setDeliveryNoticeStatus(NoteStatus.EDIT.name());

                deliveryNotices.add(deliveryNotice);
            }
            Assert.isTrue(vendorIdSet.size()==1,"第"+(item.getKey()+1)+"个送货通知的订单不是同一个供应商");
        }

        deliveryNoticeService.saveBatch(deliveryNotices);
    }

    /**
     * 新增送货通知单
     * @param deliveryNoticeList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(List<DeliveryNotice> deliveryNoticeList) {
        //校验
        checkIfAdd(deliveryNoticeList);
        //批量添加送货通知单
        deliveryNoticeService.batchAdd(deliveryNoticeList);

    }

    /**
     * 批量更新送货通知单
     * @param deliveryNoticeList
     */
    @Override
    public void batchUpdate(List<DeliveryNotice> deliveryNoticeList) {
        //校验
        checkIfUpdate(deliveryNoticeList);
        //批量更新送货通知单
        deliveryNoticeService.batchUpdate(deliveryNoticeList);
    }

    /**
     * 校验是否可添加
     * @param deliveryNoticeList
     */
    private void checkIfAdd(List<DeliveryNotice> deliveryNoticeList){
        //校验
        if(CollectionUtils.isEmpty(deliveryNoticeList)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请至少勾选一条数据"));
        }
    }

    /**
     * 校验是否可更新
     * @param deliveryNoticeList
     */
    private void checkIfUpdate(List<DeliveryNotice> deliveryNoticeList){
        //校验
        if(CollectionUtils.isEmpty(deliveryNoticeList)){
            throw new BaseException(LocaleHandler.getLocaleMsg("请至少勾选一条数据"));
        }
        for(DeliveryNotice deliveryNotice : deliveryNoticeList){
            if(Objects.isNull(deliveryNotice.getDeliveryTime())){
                throw new BaseException(LocaleHandler.getLocaleMsg("送货日期必填"));
            }
            if(Objects.isNull(deliveryNotice.getNoticeSum())){
                throw new BaseException(LocaleHandler.getLocaleMsg("送货通知数量必填"));
            }
            if(Objects.isNull(deliveryNotice.getDeliveryNoticeId())){
                throw new BaseException(LocaleHandler.getLocaleMsg("请传递送货通知id"));
            }
        }
    }

    /**
     * 检查用户是否用户采购组织权限
     * @param organizationUsers
     * @param organizationId
     * @return
     */
    private Boolean checkOrganizationPermission(List<OrganizationUser> organizationUsers,Long organizationId){
        for(OrganizationUser item : organizationUsers) {
            if (item.getOrganizationId().equals(organizationId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 批量删除送货通知单
     * @param ids
     */
    @Override
    public void batchDelete(List<Long> ids) {
        deliveryNoticeService.batchDeleteDeliveryNotice(ids);
    }
}
