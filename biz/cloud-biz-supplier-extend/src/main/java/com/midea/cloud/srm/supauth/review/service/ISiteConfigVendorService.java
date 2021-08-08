package com.midea.cloud.srm.supauth.review.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteConfigVendor;

/**
*  <pre>
 *  现场评审周期设置(按供应商) 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-29 16:40:00
 *  修改内容:
 * </pre>
*/
public interface ISiteConfigVendorService extends IService<SiteConfigVendor> {

    /**
     * 新增或编辑
     * @param siteConfigVendor
     */
    void saveOrUpdateSiteConfigVendor(SiteConfigVendor siteConfigVendor);

    PageInfo<SiteConfigVendor> listPageByParm(SiteConfigVendor siteConfigVendor);
}
