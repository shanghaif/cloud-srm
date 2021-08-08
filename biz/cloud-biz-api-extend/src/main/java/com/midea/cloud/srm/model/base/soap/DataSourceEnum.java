package com.midea.cloud.srm.model.base.soap;

import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 *  系统来源枚举类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/12 17:53
 *  修改内容:
 * </pre>
 */
public enum DataSourceEnum {
    ERP_SYS("ERP", "erp系统"),
    NSRM_SYS("NSrm", "NSRM系统");

    private String key;
    private String value;

    DataSourceEnum(String key, String value){
        this.key = key;
        this.value = value;
    }

    public static DataSourceEnum get(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (DataSourceEnum e : DataSourceEnum.values()) {
                if (e.getValue().equals(value)) {
                    return e;
                }
            }
        }
        return null;
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
