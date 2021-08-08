package com.midea.cloud.srm.model.inq.quote.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-23 9:58
 *  修改内容:
 * </pre>
 */
@Data
public class QuoteTrackingItemDTO {

    /**
     * 报价单ID
     */
    private Long quoteId;

    /**
     * 报价单号
     */
    private String quoteNo;

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
     * 电子邮件
     */
    private String email;

    /**
     * 状态
     */
    private String quoteStatus;

    /**
     * 报价撤回原因
     */
    private String rejrctReason;

    /**
     * 报价币种
     */
    private String currency;

    /**
     * 提交人
     */
    private String createdBy;

    /**
     * 提交时间
     */
    private Date creationDate;

    /**
     * 作废说明
     */
    private String cancelDescription;

    /**
     * 总价
     */
    private BigDecimal totalAmount;
}