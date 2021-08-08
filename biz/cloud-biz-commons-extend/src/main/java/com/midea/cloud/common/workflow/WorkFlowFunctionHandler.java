package com.midea.cloud.common.workflow;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  工作流事件Handler类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/25
 *  修改内容:
 * </pre>
 */
public abstract class WorkFlowFunctionHandler {

    /**
     * Description cbpm回调事件 ---- 获取表单数据(规则参数)
     * @Param businessId 业务审批表ID
     * @Param mapEventData 事件map(processId-流程ID,handlerId操作人,handlerName操作人姓名,eventType事件类型,handlerIp,操作人IP)
     * @return String
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.25
     * @throws Exception
     **/
    public abstract Map<String, Object> getFormData(Long businessId, Map<String, Object> mapEventData) throws Exception;

    /**
     * Description 起草人保存草稿事件(自定义)
     * @Param mapEventData 事件map(processId-流程ID,handlerId操作人,handlerName操作人姓名,eventType事件类型,handlerIp,操作人IP)
     * @return 
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.19
     * @throws 
     **/
    public abstract Map<String, Object>  draftEvent(Long businessId, Map<String, Object> mapEventData) throws Exception;

    /**
     * Description cbpm回调事件 ---- 起草人提交
     * @Param businessId 业务审批表ID
     * @Param mapEventData 事件map
     * @return String
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.25
     * @throws Exception
     **/
    public String draftSubmitEvent(Long businessId, Map<String, Object> mapEventData) throws Exception{
        return null;
    }

    /**
     * Description cbpm回调事件 ---- 流程结束(审批通过事件)
     * @Param businessId 业务审批表ID
     * @Param mapEventData 事件map
     * @return String
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.25
     * @throws Exception
     **/
    public String processEndEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        return null;
    }

    /**
     * Description cbpm回调事件 ---- ---- 流程废弃
     * @Param businessId 业务审批表ID
     * @Param mapEventData 事件map
     * @return String
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.25
     * @throws Exception
     **/
    public String processAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception{

        return null;
    }

    /**
     * Description cbpm回调事件 ---- 起草人废弃
     * @Param businessId 业务审批表ID
     * @Param mapEventData 事件map
     * @return String
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.25
     * @throws Exception
     **/
    public String draftAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception{

        return null;
    }

    /**
     * Description cbpm回调事件 ---- 起草人撤回
     * @Param businessId 业务审批表ID
     * @Param mapEventData 事件map
     * @return String
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.25
     * @throws Exception
     **/
    public String draftReturnEvent(Long businessId, Map<String, Object> mapEventData) throws Exception{

        return null;
    }

    /**
     * Description cbpm回调事件 ---- 处理人驳回
     * @Param businessId 业务审批表ID
     * @Param mapEventData 事件map
     * @return String
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.25
     * @throws Exception
     **/
    public String handleRefuseEvent(Long businessId, Map<String, Object> mapEventData) throws Exception{

        return null;
    }

    /**
     * Description cbpm回调事件 ---- 获取移动审批表单数据
     * @Param businessId 业务审批表ID
     * @Param mapEventData 事件map
     * @return String
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.25
     * @throws Exception
     **/
    public Map<String, Object> getMobileFormData(Long businessId, Map<String, Object> mapEventData) throws Exception{

        return new HashMap<String, Object>();
    }

    /**
     * Description cbpm节点进入事件
     * @Param businessId 业务审批表ID
     * @Param mapEventData 事件map
     * @return String
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.25
     * @throws Exception
     **/
    public String activityStartEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        return null;
    }

    /**
     * Description cbpm 审批人废弃事件
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.15
     * @throws
     **/
    public String handleAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        return null;
    }

    /**
     * Description 节点废弃
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.15
     * @throws
     **/
    public String activityAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        return null;
    }

}
