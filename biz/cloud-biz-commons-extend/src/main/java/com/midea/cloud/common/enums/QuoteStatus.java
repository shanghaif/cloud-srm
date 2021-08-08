package com.midea.cloud.common.enums;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author lixnc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-17 9:16
 *  修改内容:
 * </pre>
 */
public enum QuoteStatus {

    SELECTED("SELECTED", "已选定");

    private final String key;
    private final String value;

    QuoteStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}