package com.midea.cloud.srm.supcooperate.order.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.*;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.AppointDeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppoint;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppointVisitor;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryAppointService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  送货预约表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/28 11:37
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/order/deliveryAppoint")
public class DeliveryAppointController extends BaseController {
    @Autowired
    private IDeliveryAppointService iDeliveryAppointService;

    /**
     * 供应商创建送货预约单
     * @param param
     */
    @PostMapping("/save")
    public void save(@RequestBody DeliveryAppointSaveRequestDTO param) {
        param.setOpt("add");
        saveOrUpdate(param);
    }

    /**
     * 供应商修改送货预约单
     * @param param
     */
    @PostMapping("/update")
    public void update(@RequestBody DeliveryAppointSaveRequestDTO param) {
        saveOrUpdate(param);
    }

    /**
     * 分页查询
     * @param deliveryAppointRequestDTO 送货预约单数据请求传输对象
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<DeliveryAppoint> listPage(@RequestBody DeliveryAppointRequestDTO deliveryAppointRequestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }
        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            deliveryAppointRequestDTO.setVendorId(loginAppUser.getCompanyId());
        }

        PageUtil.startPage(deliveryAppointRequestDTO.getPageNum(), deliveryAppointRequestDTO.getPageSize());
        return new PageInfo(iDeliveryAppointService.listPage(deliveryAppointRequestDTO));
    }

    /**
     * 查询详情
     * @param deliveryAppointId 送货预约单ID
     * @return
     */
    @GetMapping("/getDeliveryAppointById")
    public DeliveryAppointVO getDeliveryAppointById(@RequestParam("deliveryAppointId") Long deliveryAppointId) {
        Assert.notNull(deliveryAppointId,"送货预约单ID不能为空");
        return iDeliveryAppointService.getDeliveryAppointById(deliveryAppointId);
    }

    /**
     * 批量确认送货预约单
     * @param ids
     */
    @PostMapping("/confirmBatch")
    public void confirmBatch(@RequestBody List<Long> ids) {
        iDeliveryAppointService.confirmBatch(ids);
    }


    /**
     * 批量拒绝送货单
     * @param requestDTO
     */
    @PostMapping("/refuseBatch")
    public void refuseBatch(@RequestBody DeliveryAppointRequestDTO requestDTO) {
        iDeliveryAppointService.refuseBatch(requestDTO);
    }


    /**
     * 新增、修改、提交统一处理操作
     * @param param
     */
    private void saveOrUpdate(DeliveryAppointSaveRequestDTO param){
        DeliveryAppoint deliveryAppoint = param.getDeliveryAppoint();
        List<DeliveryAppointVisitor> deliveryAppointVisitors = param.getDeliveryAppointVisitors();
        List<AppointDeliveryNote> appointDeliveryNotes = param.getAppointDeliveryNotes();

        Assert.notNull(deliveryAppoint, "送货单不能为空");
        Assert.notNull(deliveryAppointVisitors, "来访人员不能为空");
        Assert.notNull(appointDeliveryNotes, "送货单明细不能为空");
        if(StringUtils.equals("add",param.getOpt())){
            deliveryAppoint.setDeliveryAppointId(null);
        }else{
            Assert.notNull(deliveryAppoint.getDeliveryAppointId(), "送货预约单ID不能为空");
        }
        Assert.notNull(deliveryAppoint.getOrganizationId(), "采购组织ID不能为空");
        Assert.notNull(deliveryAppoint.getOrganizationCode(), "采购组织编号不能为空");
        Assert.notNull(deliveryAppoint.getOrganizationName(), "采购组织名称不能为空");
        Assert.notNull(deliveryAppoint.getVendorId(), "供应商ID不能为空");
        Assert.notNull(deliveryAppoint.getVendorCode(), "供应商编号不能为空");
        Assert.notNull(deliveryAppoint.getVendorName(), "供应商名称不能为空");
        Assert.notNull(deliveryAppoint.getReceivedFactory(), "收货工厂不能为空");

        //车辆信息
        Assert.notNull(deliveryAppoint.getLicensePlate(),"车牌号码不能为空");
        Assert.notNull(deliveryAppoint.getEntryTime(), "进入时间不能为空");
        Assert.notNull(deliveryAppoint.getEntryPlace(), "进入地点不能为空");

        //受访人
        Assert.notNull(deliveryAppoint.getRespondents(), "受访人不能为空");

        for(DeliveryAppointVisitor item : deliveryAppointVisitors){
            Assert.notNull(item.getVisitorName(), "来访人员姓名不能为空");
            Assert.notNull(item.getIdType(), "来访人员证件类型不能为空");
            Assert.notNull(item.getIdNo(), "来访人员证件不能为空");
        }

        //送货单
        for(AppointDeliveryNote item:appointDeliveryNotes){
            Assert.notNull(item.getDeliveryNoteId(), "送货单ID不能为空");
        }

        iDeliveryAppointService.saveOrUpdate(param.getOpt(),deliveryAppoint, deliveryAppointVisitors,appointDeliveryNotes);
    }


    /**
     * 暂存送货预约单
     * @param requestDTO
     * @return
     */
    @PostMapping("/temporarySave")
    public Long temporarySave(@RequestBody DeliveryAppointSaveRequestDTO requestDTO){
        return iDeliveryAppointService.temporarySave(requestDTO);
    }

    /**
     * 提交送货预约单
     * @param requestDTO
     * @return
     */
    @PostMapping("/submit")
    public Long submit(@RequestBody DeliveryAppointSaveRequestDTO requestDTO){
        return iDeliveryAppointService.submit(requestDTO);
    }

    /**
     * 供应商批量提交
     * @param ids
     */
    @PostMapping("/submitBatch")
    public void submitBatch(@RequestBody List<Long> ids) {
        iDeliveryAppointService.submitBatch(ids);
    }

    /**
     * 批量删除
     * @param ids
     */
    @PostMapping("/batchDelete")
    public void batchDelete(@RequestBody List<Long> ids){
        iDeliveryAppointService.batchDelete(ids);
    }


}
