package com.midea.cloud.srm.model.inq.inquiry.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-7 14:50
 *  修改内容:
 * </pre>
 */
@Data
public class VendorNQueryResponseDTO {

    /**
     * 供应商N值主键
     */
    private Long vendorNId;

    /**
     * 询价单id
     */
    private Long inquiryId;

    /**
     * 邀请供应商主键
     */
    private Long inquiryVendorId;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 供应商id
     */
    private Long vendorId;

    /**
     * 供应商编码
     */
    private String vendorCode;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * N值
     */
    private BigDecimal paymentTerm;
}