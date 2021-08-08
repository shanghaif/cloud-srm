package com.midea.cloud.srm.supauth.review.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteConfigCate;

/**
*  <pre>
 *  现场评审周期设置(按品类) 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-29 16:38:22
 *  修改内容:
 * </pre>
*/
public interface ISiteConfigCateService extends IService<SiteConfigCate> {

    /**
     * 新增或者编辑
     * @param siteConfigCate
     */
    void saveOrUpdateSiteConfigCate(SiteConfigCate siteConfigCate);

    /**
     * 条件分页查询
     * @param siteConfigCate
     * @return
     */
    PageInfo<SiteConfigCate> listPageByParm(SiteConfigCate siteConfigCate);
}
