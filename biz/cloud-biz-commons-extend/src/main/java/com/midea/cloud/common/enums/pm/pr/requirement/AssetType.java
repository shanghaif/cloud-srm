package com.midea.cloud.common.enums.pm.pr.requirement;

/**
 * <pre>
 *  资产类别枚举(longi)
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/27 13:13
 *  修改内容:
 * </pre>
 */
public enum AssetType {

    EQ("设备类—硅片或外围动力", "EQ"),
    IT("IT类", "IT"),
    TT("物流运输类(硅片或外围动力)", "TT");

    private String name;
    private String value;

    AssetType(String name, String value) {
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
