package com.midea.cloud.common.enums.pm.ps;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/2 16:37
 *  修改内容:
 * </pre>
 */
public enum FsscStatusCode {

    FSSC_STATUS_10("单据撤回", "10"),//单据撤回
    FSSC_STATUS_11("单据驳回", "11"),//单据驳回
    FSSC_STATUS_30("单据完成", "30"),//单据完成
    FSSC_STATUS_100("ERP发票反馈", "100");//ERP发票反馈

    private String name;
    private String value;

    FsscStatusCode(String name, String value) {
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
