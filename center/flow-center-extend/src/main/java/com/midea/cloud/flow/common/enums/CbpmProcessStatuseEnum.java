package com.midea.cloud.flow.common.enums;

/**
 * <pre>
 * cbpm流程状态枚举
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/20
 *  修改内容:
 * </pre>
 */
public enum CbpmProcessStatuseEnum {
    ABANDON_STATUS("00","废弃"),
    DRAFT_STATUS("10","草稿"),
    REFUSE_STATUS("11","驳回"),
    PENGING_STATUS("20","待审"),
    ERROR_STATUS("21","出错"),
    PASS_STATUS("30","通过"),
    DRAW_UP_STATUS("05","拟定"); //自定义流程状态
    private String key;
    private String value;
    CbpmProcessStatuseEnum(String key, String value){
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
