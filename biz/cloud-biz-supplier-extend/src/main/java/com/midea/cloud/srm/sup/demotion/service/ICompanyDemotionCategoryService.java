package com.midea.cloud.srm.sup.demotion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotion;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotionCategory;

import java.util.List;

/**
*  <pre>
 *  供应商升降级品类行表 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-05 17:50:08
 *  修改内容:
 * </pre>
*/
public interface ICompanyDemotionCategoryService extends IService<CompanyDemotionCategory> {

    /**
     * 根据供应商id和升降级类型查询品类
     * @param queryDTO
     * @return
     */
    List<CompanyDemotionCategory> queryCategorysByParam(CompanyDemotion queryDTO);

    /**
     * 保存或更新降级品类行
     * @param companyDemotionCategories
     */
    void saveOrUpdateDemotionCategorys(List<CompanyDemotionCategory> companyDemotionCategories);

}
