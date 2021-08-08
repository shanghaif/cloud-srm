package com.midea.cloud.srm.sup.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.info.dto.SiteInfoQueryDTO;
import com.midea.cloud.srm.model.supplier.info.entity.SiteInfo;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
*  <pre>
 *  地点信息（供应商） 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-20 11:19:01
 *  修改内容:
 * </pre>
*/
public interface ISiteInfoService extends IService<SiteInfo> {

    void saveOrUpdateSite(SiteInfo siteInfo, Long companyId);

    void saveOrUpdateSite(SiteInfo siteInfo);

    List<SiteInfo> listSiteInfoByParam(SiteInfoQueryDTO siteInfoQueryDTO);

    void removeByCompanyId(Long companyId);

    List<SiteInfo> getByCompanyId(Long companyId);

    SiteInfo getSiteInfoByParm(SiteInfo siteInfo);

    List<SiteInfo> getSiteInfosByParam(SiteInfo siteInfo);

    void adjustSiteErpOrgId();

    List<SiteInfo> listSiteInfoForOrder(SiteInfoQueryDTO siteInfoQueryDTO);
}
