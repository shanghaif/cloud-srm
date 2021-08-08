package com.midea.cloud.common.enums;

/**
 * <pre>
 *
 * </pre>
 *
 * @author yixinyx@meicloud.com
 * @version 1.00.00
 * <p>
 *     样品确认状态
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public enum SampleStatusType {

    DRAFT("拟定", "DRAFT"),// 创建人可以编辑、删除
    PUBLISHED("已发布", "PUBLISHED"),//不可操作
    CONFIRMED("已确认", "CONFIRMED"),// 不可操作
    REFUSED("已拒绝", "REFUSED"),// 不可操作
    SUBMITTED("已提交", "SUBMITTED"),// 创建人提交审批流程
    REJECTED("已驳回", "REJECTED"),//创建人可以编辑、删除
    APPROVED("已审批", "APPROVED"),//被审批通过
    WITHDRAW("已撤回", "WITHDRAW");// 增加状态,add zhenggh9  20210402

    private String name;
    private String value;

    SampleStatusType(String name, String value) {
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
