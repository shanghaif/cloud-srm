package com.midea.cloud.srm.sup.demotion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.demotion.dto.DemotionOrgQueryDTO;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotionOrg;

import java.util.List;

/**
*  <pre>
 *  供应商升降级业务实体行表 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-05 17:50:44
 *  修改内容:
 * </pre>
*/
public interface ICompanyDemotionOrgService extends IService<CompanyDemotionOrg> {

    /**
     * 根据供应商id和品类id查询合作OU
     * @param queryDTO
     * @return
     */
    List<CompanyDemotionOrg> queryOrgsByParam(DemotionOrgQueryDTO queryDTO);

    /**
     * 保存或更新降级OU行
     * @param companyDemotionOrgs
     */
    void saveOrUpdateDemotionOrgs(List<CompanyDemotionOrg> companyDemotionOrgs);

}
