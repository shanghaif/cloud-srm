package com.midea.cloud.srm.po.order.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteRequestDTO;
import com.midea.cloud.srm.supcooperate.order.service.IDeliveryNoteService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *  送货单表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/27 10:31
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/po/deliveryNote")
public class DeliveryNoteController extends BaseController {

    @Autowired
    private IDeliveryNoteService scIDeliveryNoteService;

    /**
     * 分页查询
     * @param deliveryNoteRequestDTO 送货单数据请求传输对象
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<DeliveryNoteDTO> listPage(@RequestBody DeliveryNoteRequestDTO deliveryNoteRequestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(!StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())){
            Assert.isTrue(false, "不是采购商");
        }
        PageUtil.startPage(deliveryNoteRequestDTO.getPageNum(), deliveryNoteRequestDTO.getPageSize());
        return new PageInfo(scIDeliveryNoteService.listPage(deliveryNoteRequestDTO));
    }
}
