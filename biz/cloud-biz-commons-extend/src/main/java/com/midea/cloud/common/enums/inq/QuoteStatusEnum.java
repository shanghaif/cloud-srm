package com.midea.cloud.common.enums.inq;

/**
 * <pre>
 * 流程节点类型枚举
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/20 19:17
 *  修改内容:
 * </pre>
 */
public enum QuoteStatusEnum {
    SAVE("DRAFT","待报价"),
    SUBMIT("SUBMIT","已报价"),
    ROLLBACK("ROLLBACK","撤回报价"),
    CANCEL("CANCEL","已作废");
    private String key;
    private String value;
    QuoteStatusEnum(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
