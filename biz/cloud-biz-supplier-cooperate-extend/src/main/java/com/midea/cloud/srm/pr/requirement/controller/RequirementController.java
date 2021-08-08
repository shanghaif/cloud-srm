package com.midea.cloud.srm.pr.requirement.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.util.StringUtil;
import com.midea.cloud.common.enums.pm.pr.requirement.RequirementApproveStatus;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.PurchaseRequirementApsDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.PurchaseRequirementDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.pr.requirement.service.IRequirementHeadService;

@RestController
@RequestMapping("/pr-anon/external/requirement")
public class RequirementController  extends BaseController {
	
	@Autowired
    private IRequirementHeadService iRequirementHeadService;

	@PostMapping("/submitApproval")
    public BaseResult submitApproval(@RequestBody PurchaseRequirementApsDTO purchaseRequirementDTO) {
		
		PurchaseRequirementDTO dto = new PurchaseRequirementDTO();
		RequirementHead head = purchaseRequirementDTO;
		head.setSourceSystem("MRP");
		Assert.notNull(head.getOrgId(), LocaleHandler.getLocaleMsg("业务实体不存在SRM系统中"));
		Assert.notNull(head.getCreatedId(), LocaleHandler.getLocaleMsg("员工号不正确"));
		Assert.isTrue(StringUtil.isNotEmpty(head.getOrganizationCode()),LocaleHandler.getLocaleMsg("库存组织名称为空"));
		Assert.notNull(head.getOrganizationId(),LocaleHandler.getLocaleMsg("库存组织不存在SRM系统中"));
		Assert.isTrue(StringUtil.isNotEmpty(head.getOrgCode()),LocaleHandler.getLocaleMsg("业务实体名称为空"));
		Assert.notNull(head.getOrgId(), LocaleHandler.getLocaleMsg("业务实体不存在SRM系统中"));
		Assert.notNull(head.getRequirementHeadNum(), LocaleHandler.getLocaleMsg("采购申请单号为空"));
		List<RequirementLine> lines = purchaseRequirementDTO.getRequirementLineList();
		if (lines.size() > 0) {
			for (RequirementLine line : lines) {
				line.setCeeaDeliveryPlaceSite(line.getCeeaDeliveryPlace());
				line.setCeeaDeliveryPlace("");
				Assert.isTrue(StringUtil.isNotEmpty(line.getMaterialCode()), LocaleHandler.getLocaleMsg("物料编码为空"));
				Assert.notNull(line.getMaterialId(), LocaleHandler.getLocaleMsg("srm不存在该物料"));
				line.setSourceSystem("MRP");
				line.setOrgCode(head.getOrgCode());
				line.setOrgId(head.getOrgId());
				line.setOrgName(head.getOrgName());
				line.setOrganizationCode(head.getOrganizationCode());
				line.setOrganizationId(head.getOrganizationId());
				line.setOrganizationName(head.getOrganizationName());
			}
		}
		
		
		dto.setRequirementHead(purchaseRequirementDTO);
		dto.setRequirementLineList(purchaseRequirementDTO.getRequirementLineList());
		
		BaseResult baseResult = iRequirementHeadService.approvalMrp(dto, RequirementApproveStatus.APPROVED.getValue());
        return baseResult;
    }
	
}
