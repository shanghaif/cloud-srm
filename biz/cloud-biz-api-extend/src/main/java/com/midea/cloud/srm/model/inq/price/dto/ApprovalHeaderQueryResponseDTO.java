package com.midea.cloud.srm.model.inq.price.dto;

import lombok.Data;

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
 *  修改日期: 2020-4-9 16:05
 *  修改内容:
 * </pre>
 */
@Data
public class ApprovalHeaderQueryResponseDTO {

    /**
     * 价格审批单头id
     */
    private Long approvalHeaderId;

    /**
     * 价格审批单号
     */
    private String approvalNo;

    /**
     * 寻源方式
     */
    private String sourceType;

    /**
     * 寻源id
     */
    private Long businessId;

    /**
     * 寻源单号
     */
    private String businessNo;

    /**
     * 寻源标题
     */
    private String businessTitle;

    /**
     * 审核状态
     */
    private String status;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 审批时间
     */
    private Date approvalDate;
}