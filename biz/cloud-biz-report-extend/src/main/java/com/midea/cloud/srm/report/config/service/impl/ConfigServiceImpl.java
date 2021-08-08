package com.midea.cloud.srm.report.config.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.report.config.dto.ConfigAllDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.CrSetCategoryDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.CrSetMaterialDTO;
import com.midea.cloud.srm.model.report.costreduction.entity.CrSet;
import com.midea.cloud.srm.model.report.costreduction.entity.CrSetCategory;
import com.midea.cloud.srm.model.report.costreduction.entity.CrSetCategoryInfo;
import com.midea.cloud.srm.model.report.costreduction.entity.CrSetMaterial;
import com.midea.cloud.srm.model.report.costreduction.entity.CrSetMaterialInfo;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseConfigDTO;
import com.midea.cloud.srm.model.report.purchase.entity.PurchaseConfig;
import com.midea.cloud.srm.model.report.purchase.entity.PurchaseConfigDetail;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierConfigDTO;
import com.midea.cloud.srm.model.report.supplier.entity.SupplierConfig;
import com.midea.cloud.srm.report.config.service.IConfigService;
import com.midea.cloud.srm.report.costreduction.service.ICrSetCategoryInfoService;
import com.midea.cloud.srm.report.costreduction.service.ICrSetCategoryService;
import com.midea.cloud.srm.report.costreduction.service.ICrSetMaterialInfoService;
import com.midea.cloud.srm.report.costreduction.service.ICrSetMaterialService;
import com.midea.cloud.srm.report.costreduction.service.ICrSetService;
import com.midea.cloud.srm.report.order.service.IPurchaseConfigDetailService;
import com.midea.cloud.srm.report.order.service.IPurchaseConfigService;
import com.midea.cloud.srm.report.supplier.service.ISupplierConfigService;
import com.nimbusds.openid.connect.sdk.claims.Gender;

@Service
public class ConfigServiceImpl implements IConfigService {

	@Autowired
	private IPurchaseConfigService purchaseConfigService;
	@Autowired
	private IPurchaseConfigDetailService  purchaseConfigDetailService;
	@Autowired
	private ISupplierConfigService supplierConfigService;
	
	@Autowired
	private ICrSetService crSetService;
	@Autowired
	private ICrSetCategoryService crSetCategoryService;
	@Autowired
	private ICrSetCategoryInfoService crSetCategoryInfoService;
	@Autowired
	private ICrSetMaterialService crSetMaterialService;
	@Autowired
	private ICrSetMaterialInfoService crSetMaterialInfoService;
	/**
	 * 保存配置信息
	 */
	@Transactional
	@Override
	public ConfigAllDTO saveConfig(@RequestBody ConfigAllDTO allDTO) {
		Map<String,Object> sampMap = new HashMap<String,Object>();
		List<PurchaseConfigDTO> purchaseList = allDTO.getPurchaseList();
		if (null != purchaseList && purchaseList.size() > 0) {
			PurchaseConfig purchaseConfig = null;
			List<PurchaseConfigDetail> details = new ArrayList<PurchaseConfigDetail>();
			PurchaseConfigDetail detail = null;
			List<Long> ids = new ArrayList<Long>();
			for (PurchaseConfigDTO purchase : purchaseList) {
				Long id = null;
				if (null != purchase.getConfigId()) { //更新
					id = purchase.getConfigId();
					//清除采购分析配置
					purchaseConfig = purchaseConfigService.getById(id);
					purchaseConfig.setDays(purchase.getDays());
					purchaseConfig.setFullPathIds(purchase.getFullPathIds());
					purchaseConfig.setOrganizationNames(purchase.getOrganizationNames());
					purchaseConfig.setType(purchase.getType());
					
				} else { // 创建
					id = IdGenrator.generate();
					purchaseConfig = new PurchaseConfig();
					BeanUtils.copyProperties(purchase, purchaseConfig);
					purchaseConfig.setConfigId(id);
					purchase.setConfigId(id);
				}
				ids.add(id);
				this.purchaseConfigService.saveOrUpdate(purchaseConfig);
				if (null != purchase.getFullPathIds()) {
					String [] fullPathIds = purchase.getFullPathIds().split(",");
					String [] organizationNames = purchase.getOrganizationNames().split(",");
					int i=0;
					for (String fullPathId : fullPathIds) {
						if(sampMap.containsKey(fullPathId)) {
							throw new BaseException("存在重复的组织");
						}
						sampMap.put(fullPathId, null);
						detail = new PurchaseConfigDetail();
						Long detailId = IdGenrator.generate();
						detail.setDetailId(detailId);
						detail.setConfigId(id);
						detail.setFullPathId(fullPathId);
						detail.setOrganizationName(organizationNames[i]);
						details.add(detail);
						i++;
					}
				}
			}
			//删除不存在的组织
			this.purchaseConfigService.remove((Wrapper<PurchaseConfig>) new QueryWrapper().notIn("CONFIG_ID", ids));
			//删除明细
			this.purchaseConfigDetailService.remove((Wrapper<PurchaseConfigDetail>) new QueryWrapper());
			//保存采购分析明细
			this.purchaseConfigDetailService.saveBatch(details);
			
			ids = new ArrayList<Long>();
			//保存降本分析数据
			for (CrSet set : allDTO.getCostReductionList()) {
				ids.add(set.getSetId());
				this.crSetService.saveOrUpdate(set);
			}
			//删除降本分析不存在的数据
			this.crSetService.remove((Wrapper<CrSet>) new QueryWrapper().notIn("SET_ID", ids));
		}
		
		//5.保存或更新供应商分析配置
		Long configId = null;
		SupplierConfig supplierConfig = null;
		SupplierConfigDTO dto = allDTO.getSupplier();
		if (null != allDTO.getSupplier() && null != allDTO.getSupplier().getConfigId()) { //更新
			configId = allDTO.getSupplier().getConfigId();
			SupplierConfig temp = this.supplierConfigService.getById(configId);
			supplierConfig = new SupplierConfig();
			BeanUtils.copyProperties(dto, supplierConfig);
			supplierConfig.setCreatedBy(temp.getCreatedBy());
			supplierConfig.setCreatedByIp(temp.getCreatedByIp());
			supplierConfig.setCreatedId(temp.getCreatedId());
			supplierConfig.setCreationDate(temp.getCreationDate());
			supplierConfig.setSynDate(temp.getSynDate());//同步时间更新
		} else {//创建
			this.supplierConfigService.remove((Wrapper<SupplierConfig>) new QueryWrapper() );
			configId = IdGenrator.generate();
			supplierConfig = new SupplierConfig();
			BeanUtils.copyProperties(dto, supplierConfig);
			supplierConfig.setConfigId(configId);
			allDTO.getSupplier().setConfigId(configId);
		}
		this.supplierConfigService.saveOrUpdate(supplierConfig);
		return allDTO;
	}
	
	/**
	 * 查询配置信息
	 */
	@Override
	public ConfigAllDTO queryConfig() {
		ConfigAllDTO all = new ConfigAllDTO();
		List<PurchaseConfigDTO> purchaseList = new ArrayList<PurchaseConfigDTO>();
		PurchaseConfigDTO purchase = null;
		List<PurchaseConfig> list = this.purchaseConfigService.list();
		if (null != list && list.size() > 0) {
			for (PurchaseConfig p : list) {
				purchase = new PurchaseConfigDTO();
				BeanUtils.copyProperties(p, purchase);
				purchaseList.add(purchase);
			}
		}
		all.setPurchaseList(purchaseList);
		SupplierConfig supplier = this.supplierConfigService.getOne(new QueryWrapper());
		SupplierConfigDTO s = new SupplierConfigDTO();
		if (null != supplier) {
			BeanUtils.copyProperties(supplier,s);
		}
		all.setSupplier(s);
		List<CrSet> csList = this.crSetService.list();
		all.setCostReductionList(csList);
		return all;
	}
	
	/**
	 * 查询供应商芬妮下配置信息
	 */
	@Override
	public SupplierConfig querySupplierConfig() {
		SupplierConfig supplier = this.supplierConfigService.getOne(new QueryWrapper());
		return supplier;
	}
	
	@Override
	public void saveCrSetCategory(List<CrSetCategory> list) {
		if (null != list && list.size() > 0) {
			List<CrSetCategoryInfo> details = new ArrayList<CrSetCategoryInfo>();
			CrSetCategory temp = null;
			for (CrSetCategory category: list) {
				//更新物料信息
				if (null != category.getSetCategoryId()) {
					temp = this.crSetCategoryService.getById(category.getSetCategoryId());
					if (null != temp) {
						temp.setCategoryIds(category.getCategoryIds());
						temp.setCategoryNames(category.getCategoryNames());
						temp.setRate(category.getRate());
						this.crSetCategoryService.updateById(temp);
					} else {
						category.setSetCategoryId(IdGenrator.generate());
						this.crSetCategoryService.save(category);
					}
				} else {
					category.setSetCategoryId(IdGenrator.generate());
					this.crSetCategoryService.save(category);
				}
				//删除明细
				this.crSetCategoryInfoService.remove((Wrapper<CrSetCategoryInfo>) new QueryWrapper().eq("SET_CATEGORY_ID", category.getSetCategoryId()));
				CrSetCategoryInfo info = null;
				if (null != category.getCategoryIds()) {
					String [] categoryIds = category.getCategoryIds().split(",");
					String [] categoryNames = category.getCategoryNames().split(",");
					
					for (int i=0 ;i<categoryIds.length;i++) {
						info = new CrSetCategoryInfo();
						info.setCategoryId(Long.valueOf(categoryIds[i]));
						info.setCategoryName(categoryNames[i]);
						info.setInfoId(IdGenrator.generate());
						info.setSetCategoryId(category.getSetCategoryId());
						details.add(info);
					}
				}
			}
			this.crSetCategoryInfoService.saveBatch(details);
		}
	}
	@Override
	public PageInfo<CrSetCategory> queryCrSetCategoryById(CrSetCategoryDTO crSetCategory) {
		PageUtil.startPage(crSetCategory.getPageNum(), crSetCategory.getPageSize());
        Long setId = crSetCategory.getSetId();
        QueryWrapper<CrSetCategory> wrapper = new QueryWrapper<CrSetCategory>();
        if (null != setId) {
        	wrapper.like("SET_ID", setId);
        }
        wrapper.orderByDesc("CREATION_DATE");
        return new PageInfo<CrSetCategory>(crSetCategoryService.list(wrapper));
	}
	
	@Override
	public void deleteCrSetCategoryById(List<Long> setCategoryIds) {
		crSetCategoryService.removeByIds(setCategoryIds);
	}
	
	@Override
	public void saveCrSetMaterial(List<CrSetMaterial> list) {
		if (null != list && list.size() > 0) {
			List<CrSetMaterialInfo> details = new ArrayList<CrSetMaterialInfo>();
			CrSetMaterial temp = null;
			for (CrSetMaterial material: list) {
				//更新物料信息
				if (null != material.getSetMaterialId()) {
					temp = this.crSetMaterialService.getById(material.getSetMaterialId());
					if (null != temp) {
						temp.setMaterialIds(material.getMaterialIds());
						temp.setMaterialNames(material.getMaterialNames());
						temp.setPrice(material.getPrice());
						this.crSetMaterialService.updateById(temp);
					} else {
						material.setSetMaterialId(IdGenrator.generate());
						this.crSetMaterialService.save(material);
					}
				} else {
					material.setSetMaterialId(IdGenrator.generate());
					this.crSetMaterialService.save(material);
				}
				//删除明细
				this.crSetMaterialInfoService.remove((Wrapper<CrSetMaterialInfo>) new QueryWrapper().eq("SET_MATERIAL_ID", material.getSetMaterialId()));
				CrSetMaterialInfo info = null;
				if (null != material.getMaterialIds()) {
					String [] materialIds = material.getMaterialIds().split(",");
					String [] materialNames = material.getMaterialNames().split(",");
					
					for (int i=0 ;i<materialIds.length;i++) {
						info = new CrSetMaterialInfo();
						info.setMaterialId(Long.valueOf(materialIds[i]));
						info.setMaterialName(materialNames[i]);
						info.setInfoId(IdGenrator.generate());
						info.setSetMaterialId(material.getSetMaterialId());
						details.add(info);
					}
				}
			}
			this.crSetMaterialInfoService.saveBatch(details);
		}
	}
	
	@Override
	public List<CrSetMaterial> queryCrSetMaterialById(CrSetMaterialDTO crSetMaterial) {
        Long setId = crSetMaterial.getSetId();
        QueryWrapper<CrSetMaterial> wrapper = new QueryWrapper<CrSetMaterial>();
        if (null != setId) {
        	wrapper.like("SET_ID", setId);
        }
        wrapper.orderByDesc("CREATION_DATE");
        return crSetMaterialService.list(wrapper);
	}
	
	@Override
	public void deleteCrSetMaterialById(List<Long> setMaterialIds) {
		crSetMaterialService.removeByIds(setMaterialIds);
	}
}
