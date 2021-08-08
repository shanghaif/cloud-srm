package com.midea.cloud.srm.sup.demotion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplier.demotion.dto.CompanyDemotionDTO;
import com.midea.cloud.srm.model.supplier.demotion.dto.CompanyDemotionQueryDTO;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotion;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotionOrg;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;

import java.util.List;

/**
*  <pre>
 *  供应商升降级表 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-05 17:49:32
 *  修改内容:
 * </pre>
*/
public interface ICompanyDemotionService extends IService<CompanyDemotion> {

    /**
     * 分页条件查询
     * @param queryDTO
     * @return
     */
    PageInfo<CompanyDemotion> listPageByParam(CompanyDemotionQueryDTO queryDTO);

    /**
     * 条件查询
     * @param queryDTO
     * @return
     */
    List<CompanyDemotion> listByParam(CompanyDemotionQueryDTO queryDTO);

    /**
     * 查询单据详情
     * @param companyDemotionId
     * @return
     */
    CompanyDemotionDTO getDemotionById(Long companyDemotionId);

    /**
     * 升降级单据暂存
     * @param demotionDTO
     */
    Long saveTemporary(CompanyDemotionDTO demotionDTO);

    /**
     * 升降级单据提交
     * @param demotionDTO
     */
    void submit(CompanyDemotionDTO demotionDTO);

    /**
     * 暂存、提交前校验
     * @param demotionDTO
     * @param status
     */
    void check(CompanyDemotionDTO demotionDTO, String status);

    /**
     * 升降级单据删除
     * @param companyDemotionId
     */
    void deleteById(Long companyDemotionId);

    /**
     * 升降级单据审批
     * @param companyDemotion
     */
    void approve(CompanyDemotion companyDemotion);

    /**
     * 升降级单据驳回
     * @param companyDemotion
     */
    void reject(CompanyDemotion companyDemotion);

    /**
     * 升降级单据撤回
     * @param companyDemotion
     */
    void withDraw(CompanyDemotion companyDemotion);

    /**
     * 根据供应商id和升降级OU行查询orgCategory
     * @param companyId
     * @param companyDemotionOrgList
     * @return
     */
    List<OrgCategory> getOrgCategoryByCompanyAndDemotionOrgs(Long companyId, List<CompanyDemotionOrg> companyDemotionOrgList);

}
