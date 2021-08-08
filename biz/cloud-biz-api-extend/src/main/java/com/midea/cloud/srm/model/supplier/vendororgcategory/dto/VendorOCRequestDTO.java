package com.midea.cloud.srm.model.supplier.vendororgcategory.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author zhuwl7@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-2 10:14
 *  修改内容:
 * </pre>
 */
@Data
public class VendorOCRequestDTO extends BaseDTO {
    private Long vendorId;
    private String vendorCode;
    private String vendorName;
    private Long orgId;
    private String fullPathId;
    private String orgCode;
    private String orgName;
    private String orgServiceStatus;
    private Long categoryId;
    private String categoryCode;
    private String categoryName;
    private String categoryServiceStatus;

}
