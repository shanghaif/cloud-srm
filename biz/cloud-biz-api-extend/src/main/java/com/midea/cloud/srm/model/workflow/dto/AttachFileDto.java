package com.midea.cloud.srm.model.workflow.dto;

import lombok.Data;

/**
 * @Author vent
 * @Description
 **/
@Data
public class AttachFileDto {
    //附件id
    private Long fileId;
    //附件对应的fdKey
    private String fdKey;
}
