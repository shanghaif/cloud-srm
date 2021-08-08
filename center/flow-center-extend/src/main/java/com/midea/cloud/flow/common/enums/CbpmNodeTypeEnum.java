package com.midea.cloud.flow.common.enums;

/**
 * <pre>
 * 流程节点类型枚举
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/20 19:17
 *  修改内容:
 * </pre>
 */
public enum CbpmNodeTypeEnum {
    START_NODE("startNode","开始节点"),
    END_NODE("endNode","结束节点"),
    APPROVE_NODE("approveNode","审批节点"),
    SEND_NODE("sendNode","抄送节点"),
    CONDITION_NODE("conditionNode","条件分支"),
    ROBOT_NODE("robotNode","机器人节点"),
    MANUAL_NODE("manualNode","人工决策节点");
    private String key;
    private String value;
    CbpmNodeTypeEnum(String key, String value){
        this.key = key;
        this.value = value;
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
