package com.midea.cloud.common.enums;

/**
 * <pre>
 *
 * </pre>
 *
 * @author yixinyx@meicloud.com
 * @version 1.00.00
 * <p>
 *     样品确认测试结果
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public enum SampleTestResultType {

    QUALIFICATION("合格", "QUALIFICATION"),// 合格
    DISQUALIFICATION("不合格", "DISQUALIFICATION");//不合格

    private String name;
    private String value;

    SampleTestResultType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
