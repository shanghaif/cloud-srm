package com.midea.cloud.srm.model.base.clarify.vo;


import lombok.Data;

import java.util.Date;

/**
 * @author tanjl11
 * @date 2020/10/07 20:55
 */
@Data
public class SourcingClarifyReplyVO {
    /**
     * 澄清公告回复主键
     */
    private Long clarifyReplyId;

    /**
     * 澄清公告主键
     */
    private Long clarifyId;

    /**
     * 澄清公告编号
     */
    private String clarifyNumber;

    /**
     * 澄清公告标题
     */
    private String clarifyTitle;

    /**
     * 截止时间
     */
    private Date endDate;

    /**
     * 寻源类型，招标、询比价、竞价
     */
    private String sourcingType;

    /**
     * 寻源项目id
     */
    private Long sourcingId;

    /**
     * 寻源项目编号
     */
    private String sourcingNumber;

    /**
     * 寻源项目名称
     */
    private String sourcingProjectName;

    /**
     * 供应商id
     */
    private Long vendorId;

    /**
     * 供应商名
     */
    private String vendorName;

    /**
     * 回复内容
     */
    private String replyContent;
}
