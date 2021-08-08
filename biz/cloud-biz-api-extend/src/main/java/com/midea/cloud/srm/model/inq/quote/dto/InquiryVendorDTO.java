package com.midea.cloud.srm.model.inq.quote.dto;

import lombok.Data;

import java.util.List;

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
 *  修改日期: 2020-4-20 15:52
 *  修改内容:
 * </pre>
 */
@Data
public class InquiryVendorDTO {

    /**
     * 询价邀请供应商id
     */
    private Long inquiryVendorId;

    /**
     * 询价单id
     */
    private Long inquiryId;

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
     * 联系人
     */
    private String linkMan;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 报价权限
     */
    private List<QuoteAuthDTO> quoteAuths;
}