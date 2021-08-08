package com.midea.cloud.common.enums;

/**
 * 组织品类日志单据类型
 * <pre>
 * 引用功能模块：资质审查
 * </pre>
 *
 * @author yanling.fang@meicloud.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public enum OrgCateBillType {

    REVIEW_FORM("资质审查单据", "REVIEW_FORM"),
    SITE_FORM("现场评审单据", "SITE_FORM"),
    EFFECT_FORM("供方生效单据", "EFFECT_FORM"),
    ORG_CAT_FORM("组织品类合作终止", "ORG_CAT_FORM"),
	SAMPLE_FORM("样品确认", "SAMPLE_FORM"),
	MATERIAL_FORM("物料试用", "MATERIAL_FORM");
    private String name;
    private String value;

    private OrgCateBillType(String name, String value) {
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
