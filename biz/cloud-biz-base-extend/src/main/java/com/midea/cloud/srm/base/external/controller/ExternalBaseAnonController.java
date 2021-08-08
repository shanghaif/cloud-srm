package com.midea.cloud.srm.base.external.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.srm.base.material.controller.MaterialItemController;
import com.midea.cloud.srm.base.organization.controller.OrganizationController;
import com.midea.cloud.srm.base.organization.service.IGidailyRateService;
import com.midea.cloud.srm.base.organization.service.IOrganizationService;
import com.midea.cloud.srm.base.purchase.controller.PurchaseCategoryController;
import com.midea.cloud.srm.base.purchase.service.IPurchaseCategoryService;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.price.gidailyrate.GidailyRate;

@RestController
@RequestMapping("/base-anon/external")
public class ExternalBaseAnonController extends BaseController {
	@Autowired
	private OrganizationController organizationController;

	@Autowired
	private PurchaseCategoryController purchaseCategoryController;
	
	@Autowired
	private MaterialItemController materialItemController;
	
	@Autowired
	private IOrganizationService iOrganizationService;
	
	@Autowired
	private IPurchaseCategoryService iPurchaseCategoryService;
	
	@Autowired
	private IGidailyRateService iGidailyRateService;
	
	/**
     * 组织接口
     * @return
     */
    @Transactional
    @PostMapping("/organization/saveOrUpdateOrganization")
    public void saveOrUpdateOrganization(@RequestBody List<Organization> organizations) {
    	Assert.notNull(organizations, "接口数据不存在");
    	if (null != organizations && organizations.size() > 0) {
    		for (Organization organization : organizations) {
    			Assert.notNull(organization.getOrganizationTypeId(), "组织类型不存在！");
    	    	Assert.notNull(organization.getParentOrganizationCodes(), "父zu不存在！");
    	    	setOrganizationParent(organization);
    	    	organizationController.saveOrUpdateOrganization(organization);
    		}
    	}
    }
    
    private void setOrganizationParent(Organization organization) {
    	if (null != organization.getParentOrganizationCodes()) {
    		String [] organizationCodes = organization.getParentOrganizationCodes().split(",");
    		Organization org = null;
    		StringBuffer parentOrganizationIds = new StringBuffer();
    		StringBuffer parentOrganizationNames = new StringBuffer();
    		for (String organizationCode : organizationCodes) {
    			QueryWrapper<Organization> wrapper = new QueryWrapper<Organization>();
    			wrapper.eq("ORGANIZATION_CODE", organizationCode);
    			org = iOrganizationService.getOne(wrapper);
    			Assert.notNull(organization, "父组织不存在");
    			parentOrganizationIds.append(org.getOrganizationId()).append(",");
    			parentOrganizationNames.append(org.getOrganizationName()).append(",");
    		}
    		organization.setParentOrganizationIds(parentOrganizationIds.toString());
    		organization.setParentOrganizationNames(parentOrganizationNames.toString());
    	}
    }
    
    /**
     * 批量添加采购分类
     * @param purchaseCategories
     */
    @PostMapping("/purchaseCategory/batchSaveOrUpdate")
    public void batchSaveOrUpdate(@RequestBody List<PurchaseCategory> purchaseCategories) {
       purchaseCategoryController.batchSaveOrUpdate(purchaseCategories);
    }
    
    /**
     * 批量保存或更新
     */
    @PostMapping("/material/saveOrUpdateMBatch")
    public void saveOrUpdateMBatch(@RequestBody  List<MaterialItem> materialItems){
    	if (null != materialItems && materialItems.size() > 0) {
    		for (MaterialItem item : materialItems) {
    			Assert.notNull(item.getMaterialCode(), "物料编码不能为空");
    			Assert.notNull(item.getMaterialName(), "物料名称不能为空");
    			Assert.notNull(item.getCategoryId(), "品类不存在");
    			Assert.notNull(item.getUnit(), "单位不能为空");
    			Assert.notNull(item.getUnitName(), "单位编码不存在");
    			PurchaseCategory category  = iPurchaseCategoryService.getById(item.getCategoryId());
    			iPurchaseCategoryService.setCategoryFullName(category);
    			item.setCategoryFullName(category.getCategoryFullName());
    		}
    	}
        materialItemController.saveOrUpdateMBatch(materialItems);
    }
    
//    /**
//     * 批量保存汇率
//     */
//    @PostMapping("/gidailyRate/saveBatchGidailyRate")
//    public void saveBatchGidailyRate(@RequestBody List<GidailyRate> list){
//    	iGidailyRateService.batchSave(list);
//    }
    
}
