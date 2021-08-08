package com.midea.cloud.srm.model.base.clarify.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tanjl11
 * @date 2020/10/07 21:41
 */
@AllArgsConstructor
@Getter
public enum ClarifyType {
    NOTICE("NOTICE", "公告"),
    REPLY("REPLY", "回复");
    private String code;
    private String value;
}
