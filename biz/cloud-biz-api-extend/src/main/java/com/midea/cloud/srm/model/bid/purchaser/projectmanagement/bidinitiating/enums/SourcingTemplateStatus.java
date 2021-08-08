package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.enums;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 寻源模板状态
 *
 * @author zixuan.yan@meicloud.com
 */
@Getter
@AllArgsConstructor
public enum SourcingTemplateStatus {
    VALID("VALID", "生效"),
    INVALID("INVALID", "失效");

    private final String    itemValue;
    private final String    itemName;

    public static SourcingTemplateStatus get(String itemValue) {
        return Arrays.stream(SourcingTemplateStatus.values())
                .filter(sourcingTemplateStatus -> sourcingTemplateStatus.getItemValue().equals(itemValue))
                .findAny()
                .orElseThrow(() -> new ApiException("Could not find SourcingTemplateStatus with [" + itemValue + "]"));
    }
}
