package com.midea.cloud.srm.supcooperate.reconciliation.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.UnsettledOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.UnsettledDetail;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IUnsettledDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <pre>
 *  未结算数量账单明细表 前端控制器
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/7 15:08
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/reconciliation/unsettledDetail")
public class UnsettledDetailController extends BaseController {

    @Autowired
    private IUnsettledDetailService iUnsettledDetailService;

    /**
     * 分页查询
     *
     * @param requestDTO 账单对象
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<UnsettledDetail> listPage(@RequestBody UnsettledOrderRequestDTO requestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                && !StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())) {
            Assert.isTrue(false, "用户类型不存在");
        }
        if(requestDTO.getUnsettledOrderId()==null){
            Assert.notNull(requestDTO.getOrganizationId(), "采购组织ID不能为空");
            Assert.notNull(requestDTO.getStartDateStr(), "开始日期不能为空");
            Assert.notNull(requestDTO.getEndDateStr(), "截止日期不能为空");
        }

        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            requestDTO.setVendorId(loginAppUser.getCompanyId());
        }
        PageUtil.startPage(requestDTO.getPageNum(), requestDTO.getPageSize());
        return new PageInfo(iUnsettledDetailService.findList(requestDTO));
    }

    /**
     *  查询物料数量统计
     *
     * @param requestDTO 账单对象
     * @return
     */
    @PostMapping("/materialCountList")
    public List<UnsettledDetail> materialCountList(@RequestBody UnsettledOrderRequestDTO requestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                && !StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())) {
            Assert.isTrue(false, "用户类型不存在");
        }
        if(requestDTO.getUnsettledOrderId()==null){
            Assert.notNull(requestDTO.getOrganizationId(), "采购组织ID不能为空");
            Assert.notNull(requestDTO.getStartDateStr(), "开始日期不能为空");
            Assert.notNull(requestDTO.getEndDateStr(), "截止日期不能为空");
        }

        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            requestDTO.setVendorId(loginAppUser.getCompanyId());
        }

        return iUnsettledDetailService.materialCountList(requestDTO);
    }
}
