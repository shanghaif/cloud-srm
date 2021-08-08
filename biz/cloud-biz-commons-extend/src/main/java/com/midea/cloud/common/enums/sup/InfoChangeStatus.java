package com.midea.cloud.common.enums.sup;

/**
 * 信息变更状态
 * <pre>
 * 引用功能模块：ALL8186651031896384
 * </pre>
 *
 * @author wei3.zou@meicloud.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public enum InfoChangeStatus {

    DRAFT("拟定", "DRAFT"),
    VENDOR_SUBMITTED("供方已提交", "VENDOR_SUBMITTED"),
    SUBMITTED("已提交", "SUBMITTED"),
    REJECTED("已驳回", "REJECTED"),
    APPROVED("已审批", "APPROVED"),
    ABANDONED("已废弃", "ABANDONED"),
    WITHDRAW("已撤回", "WITHDRAW");

    private String name;
    private String value;

    private InfoChangeStatus(String name, String value) {
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
     *
     * 通过指定value值获取枚举
     * @param value
     * @return
     */
    public static InfoChangeStatus get( String value ){
        for(InfoChangeStatus o:InfoChangeStatus.values()){
            if(o.value.equals(value)){
                return o;
            }
        }
        return null;
    }

    /**
     * 枚举值列表是否包含指定code
     * @param code
     * @return true or false
     */
    public static boolean isContain( String code ){
        return (get(code)!=null);
    }

}
