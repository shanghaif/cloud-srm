package com.midea.cloud.srm.model.base.clarify.dto;


import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author tanjl11
 * @date 2020/10/07 13:56
 *
 */
@Data
public class SourcingClarifyDto {
    /**
     * 澄清公告主键
     */
    @NotNull(message = "澄清公告id不能为空")
    private Long clarifyId;


    /**
     * 澄清公告标题
     */
    @NotEmpty(message = "澄清公告标题不能为空")
    private String clarifyTitle;

    /**
     * 截止时间
     */
    @NotNull(message = "截止时间不能为空")
    @Future(message = "截止时间不能小于当前时间")
    private Date endDate;

    /**
     * 寻源项目id
     */
    @NotNull(message = "寻源项目id不能为空")
    private Long sourcingId;

    /**
     * 寻源类型，招标、询比价、竞价
     */
    @NotEmpty(message = "寻源类型不能为空")
    private String sourcingType;

    /**
     * 寻源项目编号
     */
    @NotEmpty(message = "寻源项目编号不能为空")
    private String sourcingNumber;

    /**
     * 寻源项目名称
     */
    @NotEmpty(message = "寻源项目编号不能为空")
    private String sourcingProjectName;

    @Valid
    private List<SourcingClarifyFileDto> files;
}
