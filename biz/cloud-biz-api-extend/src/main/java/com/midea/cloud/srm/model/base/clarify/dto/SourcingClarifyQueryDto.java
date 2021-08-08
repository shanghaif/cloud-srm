package com.midea.cloud.srm.model.base.clarify.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author tanjl11
 * @date 2020/10/07 20:59
 */
@Data
public class SourcingClarifyQueryDto {
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

    private Integer pageNum;

    private Integer pageSize;
}
