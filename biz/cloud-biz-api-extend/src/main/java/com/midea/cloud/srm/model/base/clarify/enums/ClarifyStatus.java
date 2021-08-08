package com.midea.cloud.srm.model.base.clarify.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tanjl11
 * @date 2020/10/08 11:15
 * 澄清状态
 */
@Getter
@AllArgsConstructor
public enum ClarifyStatus {
    UNPUBLISH("UNPUBLISH","未发布"),
    PUBLISH("PUBLISH","已发布"),
    WAITRESPONSE("WAITRESPONSE","待回复"),
    ALREADYRESPONSE("ALREADYRESPONSE","已回复");

    private String code;
    private String value;
}
