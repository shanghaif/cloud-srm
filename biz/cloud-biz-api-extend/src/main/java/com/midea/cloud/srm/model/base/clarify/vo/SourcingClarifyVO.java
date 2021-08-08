package com.midea.cloud.srm.model.base.clarify.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author tanjl11
 * @date 2020/10/07 15:28
 */
@Data
public class SourcingClarifyVO {

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
     * 澄清公告状态
     */
    private String clarifyStatus;

    /**
     * 发布时间
     */
    private Date publishDate;

    /**
     * 截止时间
     */
    private Date endDate;

    /**
     * 寻源项目id
     */
    private Long sourcingId;

    /**
     * 寻源类型，招标、询比价、竞价
     */
    private String sourcingType;

    /**
     * 寻源项目编号
     */
    private String sourcingNumber;

    /**
     * 寻源项目名称
     */
    private String sourcingProjectName;
    /**
     * 供应商回复列表
     */
    private List<SourcingClarifyReplyVO> replys;
}
