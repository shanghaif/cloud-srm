package com.midea.cloud.srm.report.config.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.report.config.dto.ConfigAllDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.CrSetCategoryDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.CrSetMaterialDTO;
import com.midea.cloud.srm.model.report.costreduction.entity.CrSetCategory;
import com.midea.cloud.srm.model.report.costreduction.entity.CrSetMaterial;
import com.midea.cloud.srm.model.report.supplier.entity.SupplierConfig;

/**
 * 
 * 
 * <pre>
 * 。
 * </pre>
 * 
 * @author  kuangzm
 * @version 1.00.00
 * 
 *<pre>
 * 	修改记录
 * 	修改后版本:
 *	修改人： 
 *	修改日期:2020年12月3日 下午8:44:28
 *	修改内容:
 * </pre>
 */
public interface IConfigService {
	/**
	 * 保存配置信息
	 */
	ConfigAllDTO saveConfig(@RequestBody ConfigAllDTO allDTO);
	
	/**
	 * 查询配置信息
	 */
	ConfigAllDTO queryConfig();
	
	/**
	 * 查询供应商芬妮下配置信息
	 */
	public SupplierConfig querySupplierConfig();
	
	public void saveCrSetCategory(List<CrSetCategory> list);
	
	public PageInfo<CrSetCategory> queryCrSetCategoryById(CrSetCategoryDTO crSetCategory);
	
	public void deleteCrSetCategoryById(List<Long> setCategoryIds);
	
	public void saveCrSetMaterial(List<CrSetMaterial> list);
	
	public List<CrSetMaterial> queryCrSetMaterialById(CrSetMaterialDTO crSetMaterial);
	
	public void deleteCrSetMaterialById(List<Long> setMaterialIds);
}
