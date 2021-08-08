package com.midea.cloud.srm.po.order.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.order.NoteStatus;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.DeliveryNoticeDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.DeliveryNoticeRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.DeliveryNotice;
import com.midea.cloud.srm.model.suppliercooperate.order.vo.DeliveryNoticeImportVO;
import com.midea.cloud.srm.po.order.service.IDeliveryNoticeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <pre>
 *   送货通知单表 前端控制器
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
@RequestMapping("/po/deliveryNotice")
public class DeliveryNoticeController extends BaseController {

    @Autowired
    private SupcooperateClient supcooperateClient;
    @Autowired
    private IDeliveryNoticeService iDeliveryNoticeService;

    /**
     * 分页查询
     * @param requestDTO
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<DeliveryNoticeDTO> listPage(@RequestBody DeliveryNoticeRequestDTO requestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                &&!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "用户类型不存在");
        }

        return supcooperateClient.deliveryNoticelListPage(requestDTO);
    }

    /**
     *
     * 修改送货通知单(暂不使用)
     * @param deliveryNotice
     */
    @PostMapping("/update")
    public void update(@RequestBody DeliveryNotice deliveryNotice) {
        Assert.notNull(deliveryNotice,"数据格式错误");
        Assert.notNull(deliveryNotice.getDeliveryNoticeId(),"送货通知单ID不能为空");

        DeliveryNotice checkNotice = supcooperateClient.getDeliveryNoticeById(deliveryNotice.getDeliveryNoticeId());
        Assert.notNull(checkNotice,"送货通知单不存在");
        Assert.isTrue(StringUtils.equals(NoteStatus.EDIT.name(),checkNotice.getDeliveryNoticeStatus()),"只能修改拟态送货通知单");

        DeliveryNotice updateNotice = new DeliveryNotice();
        updateNotice.setDeliveryNoticeId(deliveryNotice.getDeliveryNoticeId());
        updateNotice.setComments(deliveryNotice.getComments());
        updateNotice.setDeliveryTime(deliveryNotice.getDeliveryTime());
        supcooperateClient.updateDeliveryNoticeById(updateNotice);
    }

    /**
     * 供应商批量发布
     *
     * @param deliveryNotices 送货通知单deliveryNotices
     */
    @PostMapping("/releasedBatch")
    public void releasedBatch(@RequestBody List<DeliveryNotice> deliveryNotices) {
        deliveryNotices.forEach(item->{
            Assert.notNull(item.getDeliveryNoticeId(),"送货通知单ID不能为空");
        });
        iDeliveryNoticeService.releasedBatch(deliveryNotices);
    }

    /**
     * excel导入送货通知
     */
    @PostMapping("/excelImportLine")
    public void excelImport(@RequestParam("file") MultipartFile file) throws IOException {
        Assert.notNull(file, "文件上传失败");
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        Assert.isTrue((org.apache.commons.lang3.StringUtils.equals("xls", suffix.toLowerCase()) || org.apache.commons.lang3.StringUtils.equals("xlsx", suffix.toLowerCase())),
                "请上传excel文件");
        List<Object> voList = EasyExcelUtil.readExcelWithModel(file.getInputStream(), DeliveryNoticeImportVO.class);
        iDeliveryNoticeService.importExcelInfo(voList);
    }
}
