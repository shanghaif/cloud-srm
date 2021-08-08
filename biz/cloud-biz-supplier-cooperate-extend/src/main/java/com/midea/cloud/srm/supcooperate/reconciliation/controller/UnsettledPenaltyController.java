package com.midea.cloud.srm.supcooperate.reconciliation.controller;

import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.UnsettledOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto.UnsettledPenaltyDTO;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IUnsettledPenaltyService;
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
 *  未结算数量账单罚扣款明细表 前端控制器
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
@RequestMapping("/reconciliation/unsettledPenalty")
public class UnsettledPenaltyController extends BaseController {

    @Autowired
    private IUnsettledPenaltyService iUnsettledPenaltyService;

    /**
     * 查询所有未结算数量账单罚扣款
     *
     * @param requestDTO 账单对象
     * @return
     */
    @PostMapping("/findUnsettledList")
    public List<UnsettledPenaltyDTO> findUnsettledList(@RequestBody UnsettledOrderRequestDTO requestDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())
                && !StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())) {
            Assert.isTrue(false, "用户类型不存在");
        }

        if(StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())){
            requestDTO.setVendorId(loginAppUser.getCompanyId());
        }
        if(requestDTO.getUnsettledOrderId()==null){
            Assert.notNull(requestDTO.getOrganizationId(), "采购组织ID不能为空");
            Assert.notNull(requestDTO.getStartDateStr(), "开始日期不能为空");
            Assert.notNull(requestDTO.getEndDateStr(), "截止日期不能为空");
        }

        return iUnsettledPenaltyService.findUnsettledList(requestDTO);
    }
}
