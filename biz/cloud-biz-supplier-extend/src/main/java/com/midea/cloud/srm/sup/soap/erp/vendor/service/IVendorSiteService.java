package com.midea.cloud.srm.sup.soap.erp.vendor.service;

import com.midea.cloud.srm.model.supplier.info.entity.SiteInfo;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorSite.VendorSiteOutputParameters;

/**
 * <pre>
 * NSrm调用Erp接口推送供应商地点信息
 * </pre>
 *
 * @update xiexh12@meicloud.com
 * <p>
 * 修改日期: 2020/9/20 11:13
 * </pre>
 */
public interface IVendorSiteService {

    /**
     * Description 调用erp接口推送供应商地点信息
     *
     * @return
     * @Param vendorInfo
     * @Author xiexh12@meicloud.com
     * @Date 2020.09.04
     **/
    VendorSiteOutputParameters sendVendorSite(SiteInfo siteInfo);
}
