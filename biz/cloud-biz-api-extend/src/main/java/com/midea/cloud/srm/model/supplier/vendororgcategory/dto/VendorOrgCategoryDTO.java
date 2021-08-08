package com.midea.cloud.srm.model.supplier.vendororgcategory.dto;

import lombok.Data;

import java.util.Date;

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
 *  修改日期: 2020-4-2 9:25
 *  修改内容:
 * </pre>
 */
@Data
public class VendorOrgCategoryDTO {
    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 供应商CODE
     */
    private String vendorCode;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 组织CODE
     */
    private String orgCode;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 组织状态
     */
    private String orgServiceStatus;

    /**
     * 组织生效日期
     */
    private Date orgStartDate;

    /**
     * 组织失效日期
     */
    private Date orgEndDate;

    /**
     * 品类ID
     */
    private Long categoryId;

    /**
     * 品类CODE
     */
    private String categoryCode;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 品类全称
     */
    private String categoryFullName;

    /**
     * 品类状态
     */
    private String categoryServiceStatus;

    /**
     * 品类生效时间
     */
    private Date categoryStartDate;

    /**
     * 品类失效时间
     */
    private Date categoryEndDate;



}
