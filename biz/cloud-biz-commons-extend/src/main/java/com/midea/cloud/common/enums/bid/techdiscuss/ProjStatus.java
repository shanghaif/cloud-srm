package com.midea.cloud.common.enums.bid.techdiscuss;

/**
 * <pre>
 * 招标-技术交流-项目状态 字典码:PROJ_STATUS
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 19:09:20
 *  修改内容:
 * </pre>
 */
public enum ProjStatus {

    DRAFT("拟定", "DRAFT"),
    PUBLISHED("已发布", "PUBLISHED"),
    CLOSE("已截止", "CLOSE");

    private String name;
    private String value;

    private ProjStatus(String name, String value) {
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
    public static ProjStatus get(String value) {
        for (ProjStatus o : ProjStatus.values()) {
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
