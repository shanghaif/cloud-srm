package com.midea.cloud.srm.report.config.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.report.config.dto.ConfigAllDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.CrSetCategoryDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.CrSetMaterialDTO;
import com.midea.cloud.srm.model.report.costreduction.entity.CrSet;
import com.midea.cloud.srm.model.report.costreduction.entity.CrSetCategory;
import com.midea.cloud.srm.model.report.costreduction.entity.CrSetMaterial;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseConfigDTO;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierConfigDTO;
import com.midea.cloud.srm.report.config.service.IConfigService;

@RestController
@RequestMapping("/config")
public class ConfigController extends BaseController {
	
	@Autowired
	private IConfigService iConfigService;
	
	/**
	 * 查询配置信息
	 * 
	 * @param paramDto
	 * @return
	 * @throws ParseException
	 */
	@PostMapping("/queryConfig")
	public ConfigAllDTO queryConfig() throws ParseException {
		return iConfigService.queryConfig();
	}
	
	/**
	 * 查询配置信息
	 * 
	 * @param paramDto
	 * @return
	 * @throws ParseException
	 */
	@PostMapping("/saveConfig")
	public ConfigAllDTO saveConfig(@RequestBody ConfigAllDTO allDTO) throws ParseException {
		Assert.notNull(allDTO.getPurchaseList(), "采购分析配置不能为空");
		Assert.notNull(allDTO.getSupplier(), "供应商分析配置不能为空");
		Assert.notNull(allDTO.getCostReductionList(), "降本分析配置不能为空");
		for (PurchaseConfigDTO dto : allDTO.getPurchaseList()) {
			Assert.notNull(dto.getFullPathIds(), "组织路径不能为空");
			Assert.notNull(dto.getOrganizationNames(), "组织名称不能为空");
			Assert.notNull(dto.getType(), "类型不能为空");
			Assert.notNull(dto.getDays(), "入库偏差天数不能为空");
		}
		SupplierConfigDTO sDTO = allDTO.getSupplier();
		Assert.notNull(sDTO.getOrderAmount(), "订单金额不能为空");
		Assert.notNull(sDTO.getAreaOne(), "供应商区域分布阶段一不能为空");
		Assert.notNull(sDTO.getAreaTwoEnd(), "供应商区域分布阶段二开始值不能为空");
		Assert.notNull(sDTO.getAreaTwoEnd(), "供应商区域分布阶段二结束值不能为空");
		Assert.notNull(sDTO.getAreaThreeStart(), "供应商区域分布阶段三开始值不能为空");
		Assert.notNull(sDTO.getAreaThreeEnd(), "供应商区域分布阶段三结束值不能为空");
		Assert.notNull(sDTO.getAreaFour(), "供应商区域分布阶段四不能为空");
		Assert.notNull(sDTO.getPurchaseOne(), "采购金额供方占比阶段一不能为空");
		Assert.notNull(sDTO.getPurchaseTwoEnd(), "采购金额供方占比阶段二开始值不能为空");
		Assert.notNull(sDTO.getPurchaseTwoEnd(), "采购金额供方占比阶段二结束值不能为空");
		Assert.notNull(sDTO.getPurchaseThreeStart(), "采购金额供方占比阶段三开始值不能为空");
		Assert.notNull(sDTO.getPurchaseThreeEnd(), "采购金额供方占比阶段三结束值不能为空");
		Assert.notNull(sDTO.getPurchaseFour(), "采购金额供方占比阶段四不能为空");
		Assert.notNull(sDTO.getCategoryOne(), "品类供方数占比阶段一不能为空");
		Assert.notNull(sDTO.getCategoryTwoEnd(), "品类供方数占比阶段二开始值不能为空");
		Assert.notNull(sDTO.getCategoryTwoEnd(), "品类供方数占比阶段二结束值不能为空");
		Assert.notNull(sDTO.getCategoryThreeStart(), "品类供方数占比阶段三开始值不能为空");
		Assert.notNull(sDTO.getCategoryThreeEnd(), "品类供方数占比阶段三结束值不能为空");
		Assert.notNull(sDTO.getCategoryFour(), "品类供方数占比阶段四不能为空");
		
		Assert.notNull(sDTO.getCategoryCrOne(), "品类降本金额阶段一不能为空");
		Assert.notNull(sDTO.getCategoryCrTwoEnd(), "品类降本金额阶段二开始值不能为空");
		Assert.notNull(sDTO.getCategoryCrTwoEnd(), "品类降本金额阶段二结束值不能为空");
		Assert.notNull(sDTO.getCategoryCrThreeStart(), "品类降本金额阶段三开始值不能为空");
		Assert.notNull(sDTO.getCategoryCrThreeEnd(), "品类降本金额阶段三结束值不能为空");
		Assert.notNull(sDTO.getCategoryCrFour(), "品类降本金额阶段四不能为空");
		
		for (CrSet s : allDTO.getCostReductionList()) {
			Assert.notNull(s.getSetId(), "降本分析ID不能为空");
			Assert.notNull(s.getYear(), "降本分析年度不能为空");
			Assert.notNull(s.getRate(), "降本分析年度总体降本率不能为空");
		}
		return iConfigService.saveConfig(allDTO);
	}
	
	
	/**
	 * 获取ID
	 * 
	 * @param paramDto
	 * @return
	 * @throws ParseException
	 */
	@PostMapping("/getID")
	public Long getID() throws ParseException {
		return IdGenrator.generate();
	}
	
	@PostMapping("/saveCrSetCategory")
	public void saveCrSetCategory(@RequestBody List<CrSetCategory> list) {
		Assert.notNull(list, "保存对象不能为空");
		for(CrSetCategory category : list) {
			Assert.notNull(category.getSetId(), "配置ID不能为空");
			Assert.notNull(category.getCategoryIds(), "品类ID不能为空");
			Assert.notNull(category.getCategoryNames(), "品类名称不能为空");
			Assert.notNull(category.getRate(), "降本率不能为空");
		}
		this.iConfigService.saveCrSetCategory(list);
	}
	
	@PostMapping("/getCrSetCategory")
	public PageInfo<CrSetCategory> getCrSetCategory(@RequestBody CrSetCategoryDTO dto) {
		Assert.notNull(dto, "查询对象不能为空");
		Assert.notNull(dto.getSetId(), "配置ID不能为空");
		return this.iConfigService.queryCrSetCategoryById(dto);
	}
	
	@PostMapping("/delCrSetCategory")
	public void delCrSetCategory(@RequestBody List<Long> setCategoryIds) {
		Assert.notNull(setCategoryIds, "删除ID不能为空");
		this.iConfigService.deleteCrSetCategoryById(setCategoryIds);
	}
	
	@PostMapping("/saveCrSetMaterial")
	public void saveCrSetMaterial(@RequestBody List<CrSetMaterial> list) {
		Assert.notNull(list, "保存对象不能为空");
		for(CrSetMaterial material : list) {
			Assert.notNull(material.getSetId(), "配置ID不能为空");
			Assert.notNull(material.getMaterialIds(), "物料ID不能为空");
			Assert.notNull(material.getMaterialNames(), "物料名称不能为空");
			Assert.notNull(material.getPrice(), "冻结价格不能为空");
		}
		this.iConfigService.saveCrSetMaterial(list);
	}
	
	@PostMapping("/getCrSetMaterial")
	public List<CrSetMaterial> getCrSetMaterial(@RequestBody CrSetMaterialDTO dto) {
		Assert.notNull(dto, "查询对象不能为空");
		Assert.notNull(dto.getSetId(), "配置ID不能为空");
		return this.iConfigService.queryCrSetMaterialById(dto);
	}
	
	@PostMapping("/delCrSetMaterial")
	public void delCrSetMaterial(@RequestBody List<Long> setMaterialIds) {
		Assert.notNull(setMaterialIds, "删除ID不能为空");
		this.iConfigService.deleteCrSetMaterialById(setMaterialIds);
	}
}
