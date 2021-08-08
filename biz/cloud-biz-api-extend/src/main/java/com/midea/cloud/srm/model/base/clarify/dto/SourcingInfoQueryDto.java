package com.midea.cloud.srm.model.base.clarify.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author tanjl11
 * @date 2020/10/16 9:10
 */
@Data
public class SourcingInfoQueryDto {
    private String sourcingProjectName;
    private String sourcingNumber;
    private String sourcingType;
    private Integer pageSize;
    private Integer pageNum;
}
