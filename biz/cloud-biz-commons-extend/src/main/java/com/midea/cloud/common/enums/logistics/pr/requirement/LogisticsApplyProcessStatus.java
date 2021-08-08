package com.midea.cloud.common.enums.logistics.pr.requirement;

/**
 * <pre>
 *  字典：LOGISTICS_APPLY_PROCESS_STATUS
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2021/1/23 13:52
 *  修改内容:
 * </pre>
 */
public enum LogisticsApplyProcessStatus {
    UNPROCESSED("未处理", "UNPROCESSED"),
    CONVERT_BID("已转招标", "CONVERT_BID"),
    FINISHED("已完成", "FINISHED");

    private String name;
    private String value;

    private LogisticsApplyProcessStatus(String name, String value) {
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

    /**
     * 通过指定value值获取枚举
     *
     * @param value
     * @return
     */
    public static LogisticsApplyProcessStatus get(String value) {
        for (LogisticsApplyProcessStatus o : values()) {
            if (o.value.equals(value)) {
                return o;
            }
        }
        return null;
    }

    /**
     * 枚举值列表是否包含指定code
     *
     * @param code
     * @return true or false
     */
    public static boolean isContain(String code) {
        return (get(code) != null);
    }
}
