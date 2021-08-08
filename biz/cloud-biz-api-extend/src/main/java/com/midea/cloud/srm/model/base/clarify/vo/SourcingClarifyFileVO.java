package com.midea.cloud.srm.model.base.clarify.vo;

import lombok.Data;

/**
 * @author tanjl11
 * @date 2020/10/07 20:57
 */
@Data
public class SourcingClarifyFileVO {

    private Long clarifyFileId;

    /**
     * 澄清类型 发布/回复
     */
    private Long clarifyType;

    /**
     * 文件中心的id
     */
    private Long fileUploadId;

    /**
     * 文件名
     */
    private String clarifyFileName;

    /**
     * 文件备注
     */
    private String comments;
}
