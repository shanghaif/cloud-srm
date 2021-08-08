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
public enum InquiryPublishStatusEnum {
    DRAFT("DRAFT","拟定"),
    UNPUBLISH("UNPUBLISH","未发布"),
    PUBLISHED("PUBLISHED","已发布"),
    CANCEL("CANCEL","已取消"),
    RECEI_QUOTATION("RECEI_QUOTATION","接收报价中"),
    BEING_SELECTED("BEING_SELECTED","评选中"),
    FIXED_PRICE("FIXED_PRICE","已定价"),
    CLOSE_QUOTATION("CLOSE_QUOTATION","已截止报价");
    private String key;
    private String value;
    InquiryPublishStatusEnum(String key, String value){
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
