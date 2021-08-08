package com.midea.cloud.srm.model.supplier.vendororgcategory.vo;

import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * VO for {@link CompanyInfo} and {@link OrgCategory}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendorOrgCateRelVO implements Serializable {

    private Long    companyId;
    private String  companyCode;
    private String  companyName;

    private Long    orgCategoryId;
    private Long    orgId;
    private String  orgCode;
    private String  orgName;
    private Long    categoryId;
    private String  categoryCode;
    private String  categoryName;
    private String  categoryFullId;
    private String  categoryFullName;
    private String  serviceStatus;


    /**
     * 对象转换 - {@link CompanyInfo}.
     *
     * @return A new {@link CompanyInfo}.
     */
    public CompanyInfo toCompanyInfo() {
        CompanyInfo companyInfo = new CompanyInfo();
        BeanUtils.copyProperties(this, companyInfo);
        return companyInfo;
    }

    /**
     * 对象转换 - {@link OrgCategory}.
     *
     * @return A new {@link OrgCategory}.
     */
    public OrgCategory toOrgCategory() {
        OrgCategory orgCategory = new OrgCategory();
        BeanUtils.copyProperties(this, orgCategory);
        return orgCategory;
    }
}
