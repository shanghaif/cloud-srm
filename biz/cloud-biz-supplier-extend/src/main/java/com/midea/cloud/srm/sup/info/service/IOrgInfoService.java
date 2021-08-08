package com.midea.cloud.srm.sup.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.info.entity.OrgInfo;

import java.util.List;

/**
*  <pre>
 *  合作组织信息 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 16:21:46
 *  修改内容:
 * </pre>
*/
public interface IOrgInfoService extends IService<OrgInfo> {

    void saveOrUpdateOrg(OrgInfo orgInfo, Long companyId);

    List<OrgInfo> getByCompanyId(Long vendorId);

    void removeByCompanyId(Long companyId);

    /**
     * 根据公司ID查询合作组织
     * @param companyId
     * @return
     */
    List<OrgInfo> listOrgInfoByCompanyId(Long companyId);

    /**
     * 根据服务状态和供应商ID查询合作组织
     * @param serviceStatus
     * @param companyId
     * @return
     */
    List<OrgInfo> listOrgInfoByServiceStatusAndCompanyId(Long companyId, String... serviceStatus);

    /**
     * 根据组织ID和公司ID获取合作组织
     * @param orgId
     * @param companyId
     * @return
     */
    OrgInfo getOrgInfoByOrgIdAndCompanyId(Long orgId, Long companyId);

    /**
     * 更新组织服务状态
     * @param orgInfo
     */
    void updateOrgInfoServiceStatus(OrgInfo orgInfo);

    List<OrgInfo> queryOrgInfoByVendorId(Long companyId);
}
