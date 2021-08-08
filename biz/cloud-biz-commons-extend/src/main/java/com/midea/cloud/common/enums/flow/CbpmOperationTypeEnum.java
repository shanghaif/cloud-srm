package com.midea.cloud.common.enums.flow;
/**
 * <pre>
 * cdpm流程操作过程枚举类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/16
 *  修改内容:
 * </pre>
 */
public enum CbpmOperationTypeEnum {
    HANDLER_PASS("handler_pass", "通过"),
    HANDLER_REFUSE("handler_refuse", "驳回"),
    HANDLER_COMMUNICATE("handler_communicate", "沟通"),
    HANDLER_COMMISSION("handler_commission", "转办"),
    HANDLER_RETURN_COMMUNICATE("handler_returnCommunicate","回复沟通"),
    HANDLER_CANCEL_COMMUNICATE("handler_cancelCommunicate","取消沟通"),
    DRAFT_SUBMIT("draft_submit","起草人提交"),
    DRAFT("draft","保存起草"),
    DRAFT_ABANDON("draft_abandon","起草人废弃"),
    DRAFT_RETURN("draft_return","起草人撤回"),
    HANDLER_ABANDON("handler_abandon","审批人废弃"),
    CIRCULATE("circulate","传阅"),
    GET_FORM_DATA("getFormData","获取表单数据[备注：非回调事件，用于规则参数]"),
    REMIND("remind","催办"),
    DRAFT_EVENT("draftEvent","起草人保存草稿"),
    INSTANCE_ID("instanceId", "实例化流程");

    public String key;
    public String value;
    CbpmOperationTypeEnum(String key, String value){
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
