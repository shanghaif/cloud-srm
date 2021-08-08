package com.midea.cloud.srm.model.supplier.info.dto;

import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfoDetail;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  供应商全部信息实体类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/9 19:44
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class VendorInformation extends BaseEntity {
    // 公司基本信息
    private CompanyInfo companyInfo;
    // 公司基本信息从表
    private CompanyInfoDetail companyInfoDetail;
    // 组织与品类
    private List<CategoryDTO> orgCategorys;
}
