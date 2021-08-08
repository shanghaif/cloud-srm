package com.midea.cloud.srm.model.inq.inquiry.dto;

import lombok.Data;

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
 *  修改日期: 2020-4-16 11:14
 *  修改内容:
 * </pre>
 */
@Data
public class InquiryDeadlineRequestDTO {

    /**
     * 询价id
     */
    private Long inquiryId;

    /**
     * 截止时间
     */
    private String deadline;
}