package com.midea.cloud.srm.supcooperate.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.order.DeliveryNoteStatus;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteWms;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryNoteService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 *   送货单表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:06
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/order/deliveryNote")
public class DeliveryNoteController extends BaseController {

    @Autowired
    private IDeliveryNoteService iDeliveryNoteService;


    /**
     * 根据送货单id获取送货单内容
     * @return
     */
    @GetMapping("/getDeliveryDTO")
    public DeliveryNoteSaveRequestDTO getDeliveryDTO(Long orderId){
        Assert.notNull(orderId, "送货单id不能为空");
        return iDeliveryNoteService.getDeliveryDTO(orderId);
    }

    /**
     * 供应商创建发货单
     * @param param
     */
    @PostMapping("/save")
    public void save(@RequestBody DeliveryNoteSaveRequestDTO param) {
        LoginAppUser user=AppUserUtil.getLoginAppUser();
        Assert.isTrue(StringUtils.equals(user.getUserType(), UserType.VENDOR.name()), "送货单是由供应商创建");

        DeliveryNote deliveryNote = param.getDeliveryNote();
        List<DeliveryNoteDetailDTO> detailList = param.getDetailList();
        List<Fileupload> procurementFile = param.getProcurementFile();
        List<DeliveryNoteWms> deliveryNoteWms = param.getDeliveryNoteWms();

        Assert.notNull(deliveryNote, "送货单不能为空");
        Assert.notNull(detailList, "送货单明细不能为空");
        Assert.notNull(deliveryNote.getDeliveryDate(), "本次送货日期不能为空");
        Assert.notNull(deliveryNote.getOrganizationId(), "采购组织ID不能为空");
        Assert.notNull(deliveryNote.getOrganizationCode(), "采购组织编码不能为空");
        Assert.notNull(deliveryNote.getOrganizationName(), "采购组织名称不能为空");

        Set<Long> orderDetailIdSet = new HashSet();
        for(int i=0;i<detailList.size();i++){
            Assert.notNull(detailList.get(i).getOrderDetailId(), "订单明细ID不能为空");
            Assert.notNull(detailList.get(i).getDeliveryQuantity(), "本次送货数量不能为空");
            detailList.get(i).setLineNum(i+1);
            orderDetailIdSet.add(detailList.get(i).getOrderDetailId());

        }

        if(orderDetailIdSet.size()!=detailList.size()){
            Assert.isTrue(false, "订单明细不能重复");
        }
        iDeliveryNoteService.saveOrUpdate(deliveryNote, detailList,procurementFile,deliveryNoteWms);
    }

    /**
     * 供应商编辑送货单 进行保存
     * @param param
     */
    @PostMapping("/update")
    public void update(@RequestBody DeliveryNoteSaveRequestDTO param) {
        LoginAppUser user=AppUserUtil.getLoginAppUser();
        Assert.isTrue(StringUtils.equals(user.getUserType(), UserType.VENDOR.name()), "送货单是由供应商编辑");

        DeliveryNote deliveryNote = param.getDeliveryNote();
        List<DeliveryNoteDetailDTO> detailList = param.getDetailList();
        List<Fileupload> procurementFile = param.getProcurementFile();
        List<DeliveryNoteWms> deliveryNoteWms = param.getDeliveryNoteWms();

        Assert.notNull(deliveryNote, "送货单不能为空");
        Assert.notNull(detailList, "送货单明细不能为空");
        Assert.notNull(deliveryNote.getDeliveryNoteId(), "送货单ID不能为空");

        for(DeliveryNoteDetail item : detailList){
            Assert.notNull(item.getOrderDetailId(), "订单明细ID不能为空");
            Assert.notNull(item.getLineNum(), "行号不能为空");
            Assert.notNull(item.getDeliveryQuantity(), "本次送货数量不能为空");
        }
        iDeliveryNoteService.saveOrUpdate(deliveryNote, detailList,procurementFile,deliveryNoteWms);
    }


    /**
     * 送货单分页查询
     * @param deliveryNoteRequestDTO 送货单数据请求传输对象
     * @return
     */
    @PostMapping("/deliveryNotePage")
    public PageInfo<DeliveryNoteDTO> deliveryNotePage(@RequestBody DeliveryNoteRequestDTO deliveryNoteRequestDTO) {
        PageUtil.startPage(deliveryNoteRequestDTO.getPageNum(), deliveryNoteRequestDTO.getPageSize());
        return new PageInfo(iDeliveryNoteService.listPage(deliveryNoteRequestDTO));
    }

    /**
     * 供应商分页查询
     * @param deliveryNoteRequestDTO 送货单数据请求传输对象
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<DeliveryNoteDTO> listPage(@RequestBody DeliveryNoteRequestDTO deliveryNoteRequestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }
        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            deliveryNoteRequestDTO.setVendorId(loginAppUser.getCompanyId());
        }
        PageUtil.startPage(deliveryNoteRequestDTO.getPageNum(), deliveryNoteRequestDTO.getPageSize());
        return new PageInfo(iDeliveryNoteService.listPage(deliveryNoteRequestDTO));
    }

    /**
     * 创建送货预约-选择送货单
     * @param deliveryNoteRequestDTO
     * @return
     */
    @PostMapping("/listInDeliveryAppoint")
    public PageInfo<DeliveryNoteDTO> listInDeliveryAppoint(@RequestBody DeliveryNoteRequestDTO deliveryNoteRequestDTO){
        return iDeliveryNoteService.listInDeliveryAppoint(deliveryNoteRequestDTO);
    }

    /**
     * 待创建送货单统计
     * @return
     */
    @GetMapping("/countCreate")
    public WorkCount countCreate() {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }
        DeliveryNoteRequestDTO deliveryNoteRequestDTO = new DeliveryNoteRequestDTO();
        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            deliveryNoteRequestDTO.setVendorId(loginAppUser.getCompanyId());
        }
        deliveryNoteRequestDTO.setDeliveryNoteStatus(DeliveryNoteStatus.CREATE.name());

        return iDeliveryNoteService.countCreate(deliveryNoteRequestDTO);
    }


    /**
     * 供应商批量提交送货单
     * @param ids 订单ids
     */
    @PostMapping("/submitBatch")
    public void submitBatch(@RequestBody List<Long> ids) {
        Assert.notNull(ids,"送货单id不能为空");
        Assert.isTrue(ids.size()>0,"送货单id不能为空");

        iDeliveryNoteService.submitBatch(ids);
    }

    /**
     * 确认发货
     * @param deliveryNoteId
     */
    @GetMapping("/getAffirmDelivery")
    public void getAffirmDelivery(Long deliveryNoteId){
        Assert.isTrue(deliveryNoteId!=null, "送货单号为空");
        iDeliveryNoteService.getAffirmDelivery(deliveryNoteId);
    }

    /**
     * 采购商确认状态
     * @param deliveryNoteId
     */
    @GetMapping("/confirmOrderStatus")
    public void confirmOrderStatus(Long deliveryNoteId){
        Assert.isTrue(deliveryNoteId!=null, "送货单号为空");
        UpdateWrapper<DeliveryNote> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("PURCHASE_CONFIRM_STATUS","Y");
        updateWrapper.eq("DELIVERY_NOTE_ID",deliveryNoteId);
        iDeliveryNoteService.update(updateWrapper);
    }

    /**
     * 取消发货
     * @param deliveryNoteId
     */
    @GetMapping("/getCancelDelivery")
    public void getCancelDelivery(Long deliveryNoteId){
        Assert.isTrue(deliveryNoteId!=null, "送货单号为空");
        iDeliveryNoteService.getCancelDelivery(deliveryNoteId);
    }

    /**
     *
     * @param deliveryNoteId
     */
    @GetMapping("/delete")
    public void getDelete(Long deliveryNoteId){
        Assert.isTrue(deliveryNoteId!=null, "送货单号为空");
//        iDeliveryNoteService.removeById(deliveryNoteId);
        iDeliveryNoteService.delete(deliveryNoteId);
    }

    /**
     * 获取供应商首次送货单
     * @param vendorId 订单ids
     */
    @GetMapping("/getFirstDeliveryNo")
    public DeliveryNote getFirstDeliveryNo(@RequestParam("vendorId") Long vendorId){
        DeliveryNote deliveryNote = null;
        List<DeliveryNote> deliveryNotes = iDeliveryNoteService.list(new QueryWrapper<>(new DeliveryNote().setVendorId(vendorId)));
        if(!CollectionUtils.isEmpty(deliveryNotes)){
            deliveryNote = deliveryNotes.get(0);
        }
        return deliveryNote;
    }
}
