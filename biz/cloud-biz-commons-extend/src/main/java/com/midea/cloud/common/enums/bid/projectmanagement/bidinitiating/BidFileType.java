package com.midea.cloud.common.enums.bid.projectmanagement.bidinitiating;

/**
 * <pre>
 * 招标-附件-文件类型 字典
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
public enum BidFileType {

    Enterprise("内部", "Enterprise"),
    Supplier("外部", "Supplier");

    private String name;
    private String value;

    private BidFileType(String name, String value) {
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
    public static BidFileType get(String value) {
        for (BidFileType o : BidFileType.values()) {
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
