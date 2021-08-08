package com.midea.cloud.common.enums.pm.pr.requirement;

/**
 * <pre>
 *   采购申请类型(longi)
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/27 13:04
 *  修改内容:
 * </pre>
 */
public enum PrType {

    PR_TYPE_01("固定资产类", "01"),
    PR_TYPE_02("食材类", "02"),
    PR_TYPE_03("非生产性物资类", "03"),
    PR_TYPE_40("服务类", "40"),
    PR_TYPE_05("备品备件类", "05"),
    PR_TYPE_06("废旧物资类", "06");

    private String name;
    private String value;

    PrType(String name, String value) {
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
