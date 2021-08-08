package com.midea.cloud.srm.inq.inquiry.service;

import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.Quota;
import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.inq.quota.vo.QuotaCalculateParameter;
import com.midea.cloud.srm.model.inq.quota.vo.QuotaCalculateResult;
import com.midea.cloud.srm.model.inq.quota.vo.VendorInfo;
import com.midea.cloud.srm.model.inq.quota.vo.VendorPriceInfo;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  配额表 服务类
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 16:40:36
 *  修改内容:
 * </pre>
*/
public interface IQuotaService extends IService<Quota> {
    List<Quota> quotaList(QuotaDTO quotaDTO);
    void updateAndAdd(QuotaDTO quotaDTO);
    QuotaDTO getQuota(Long id);
    void removeAlls(Long id);
    Map<Integer,QuotaCalculateResult> getCalculate(List<QuotaCalculateParameter> quotaCalculateParameter);
    //List<Integer> capacityAwardOfBid(List<VendorPriceInfo> vendorPriceInfoList, Long categoryId, Map<BigDecimal, Collection<VendorInfo>> minDiscountPriceMap,Map<Long, List<OrgCategory>> byCategoryAllMap, Map<Long, CompanyInfo> companyInfoMap);
}
