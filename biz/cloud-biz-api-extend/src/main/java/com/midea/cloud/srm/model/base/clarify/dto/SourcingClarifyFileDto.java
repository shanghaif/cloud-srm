package com.midea.cloud.srm.model.base.clarify.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author tanjl11
 * @date 2020/10/07 14:57
 */
@Data
public class SourcingClarifyFileDto {
    /**
     * 澄清公告的文件id
     */
    private Long clarifyFileId;

    /**
     * 文件中心的id
     */
    @NotNull(message = "文件中心")
    private Long fileUploadId;

    /**
     * 文件名
     */
    @NotEmpty(message = "文件名不能为空")
    private String clarifyFileName;

    /**
     * 文件备注
     */
    private String comments;
}
