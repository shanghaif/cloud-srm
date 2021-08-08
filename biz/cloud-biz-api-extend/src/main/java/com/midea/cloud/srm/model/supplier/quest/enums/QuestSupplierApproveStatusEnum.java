package com.midea.cloud.srm.model.supplier.quest.enums;

/**
 * 供应商问卷调查状态
 * <pre>
 * 引用功能模块：供应商问卷调查
 * </pre>
 *
 * @author bing5.wang@midea.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public enum QuestSupplierApproveStatusEnum {

    DRAFT("拟定", "DRAFT"),
    PUBLISH("已发布", "PUBLISH"),
    WRITING("填写中", "WRITING"),
    WRITED("已填写", "WRITED"),
    PRE_PASS("预审通过", "PRE_PASS"),
    PRE_REJECTED("预审驳回", "PRE_REJECTED"),
    SUBMITTED("已提交", "SUBMITTED"),
    REJECTED("已驳回", "REJECTED"),
    APPROVED("已审批", "APPROVED"),
    ABANDONED("已废弃", "ABANDONED"),
    WITHDRAW("已撤回", "WITHDRAW");

    private String name;
    private String value;

    private QuestSupplierApproveStatusEnum(String name, String value) {
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
    public static QuestSupplierApproveStatusEnum get(String value ){
        for(QuestSupplierApproveStatusEnum o: QuestSupplierApproveStatusEnum.values()){
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
