package com.midea.cloud.common.enums.logistics;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.enums.SourceFrom;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * <pre>
 *  物流采购订单 单据来源类型 SOURCE_DATA
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2021/1/19 19:38
 *  修改内容:
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum OrderSourceFrom {
    MANUAL("MANUAL","手工"),
    LOGISTICS_BID("LOGISTICS_BID","招标");

    private final String    itemValue;
    private final String    itemName;

    public static OrderSourceFrom get(String itemValue) {
        return Arrays.stream(OrderSourceFrom.values())
                .filter(sourceFrom -> sourceFrom.getItemValue().equals(itemValue))
                .findAny()
                .orElseThrow(() -> new ApiException("Could not find SourceFrom with [" + itemValue + "]"));
    }

}
