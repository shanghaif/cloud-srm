package com.midea.cloud.common.enums.bid.projectmanagement.signupmanagement;

/**
 * <pre>
 * 投标报名附件表(供应商端) 附件类型 字典码:FILE_TYPE
 * </pre>
 *
 * @author zhuomb1@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-10 09:50:20
 *  修改内容:
 * </pre>
 */

public enum VendorFileType {
    ENROLL("报名","ENROLL"),
    BIDING("投标","BIDING"),
    TECH_DISCUSS_REPLY("技术交流回复","TECH_DISCUSS_REPLY"),
    BIDING_QUESTION("投标质疑","BIDING_QUESTION"),
    BIDING_ANSWER("投标澄清","BIDING_ANSWER");

    private String name;
    private String value;
    private VendorFileType(String name, String value) {
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
    public static VendorFileType get(String value) {
        for (VendorFileType o : VendorFileType.values()) {
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
