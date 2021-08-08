package com.midea.cloud.flow.common.method;

import com.midea.cloud.common.enums.flow.CbpmEventTypeEnum;
import com.midea.cloud.common.enums.flow.CbpmFormTemplateIdEnum;
import com.midea.cloud.common.enums.flow.CbpmModuleIdEnum;
import com.midea.cloud.common.enums.flow.CbpmOperationTypeEnum;
import com.midea.cloud.flow.common.enums.CbpmNodeTypeEnum;
import com.midea.cloud.flow.common.enums.ShuntServiceEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  工作流程相关共用方法
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/21 11:05
 *  修改内容:
 * </pre>
 */
public class WorkFLowCommonMethod {

    /**
     * Description 获取事件类型选项Map
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.21
     * @throws
     **/
    public static final Map<String, Object> getEventTypeOptionMap(){
        Map<String, Object> envetTypeMap = new HashMap<>();
        for(CbpmEventTypeEnum eventType : CbpmEventTypeEnum.values()){
            envetTypeMap.put(eventType.getKey(), eventType.getValue());
        }
        return envetTypeMap;
    }

    /**
     * Description 根据事件类型key获取value
     * @Param key 事件类型Key值
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.21
     * @throws
     **/
    public static final String getEventTypeByKey(String key){
        String result = "";

        for(CbpmEventTypeEnum evenType : CbpmEventTypeEnum.values()){
            if(evenType.getKey().equals(key)){
                result = evenType.getValue();
            }
        }
        return result;
    }

    /**
     * Description 根据事件类型value获取key
     * @Param value事件类型Value值
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.21
     * @throws
     **/
    public static final String getEventTypeByValue(String value){
        String result = "";
        for(CbpmEventTypeEnum evenType : CbpmEventTypeEnum.values()){
            if(evenType.getValue().equals(value)){
                result = evenType.getKey();
            }
        }
        return result;
    }

    /**
     * Description 获取分流服务Map
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.21
     * @throws
     **/
    public static final Map<String, Object> getShuntServiceMap(){
        Map<String, Object> map = new HashMap<>();
        for(ShuntServiceEnum shuntService : ShuntServiceEnum.values()){
            map.put(shuntService.getKey(), shuntService.getValue());
        }
        return map;
    }

    /**
     * Description 根据分流服务Key获取Value值
     * @Param key 分流服务Key
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.21
     * @throws
     **/
    public static final String getShuntServiveByKey(String key){
        String result = "";
        for(ShuntServiceEnum shuntService : ShuntServiceEnum.values()){
            if(shuntService.getKey().equals(key)){
                result = shuntService.getValue();
            }
        }
        return result;
    }

    /**
     * Description 根据分流服务Value获取Key值
     * @Param value 分流服务的value
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.21
     * @throws
     **/
    public static final String getShuntServiveByValue(String value){
        String result = "";
        for(ShuntServiceEnum shuntService : ShuntServiceEnum.values()){
            if(shuntService.getValue().equals(value)){
                result = shuntService.getKey();
            }
        }
        return result;
    }

    /**
     * Description 根据流程节点类型key获取Value
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.26
     * @throws
     **/
    public static final String getNodeTypeByValue(String key){
        String result = "";
        for(CbpmNodeTypeEnum nodeType : CbpmNodeTypeEnum.values()){
            if(nodeType.getKey().equals(key)){
                result = nodeType.getValue();
            }
        }
        return result;
    }

    /**
     * Description 根据流程节点类型key获取Value
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.26
     * @throws
     **/
    public static final String getNodeTypeByKey(String value){
        String result = "";
        for(CbpmNodeTypeEnum nodeType : CbpmNodeTypeEnum.values()){
            if(nodeType.getKey().equals(value)){
                result = nodeType.getKey();
            }
        }
        return result;
    }

    /**
     * Description 根据系统业务模板ID获取系统业务模块ID
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.17
     * @throws
     **/
    public static final String getModuleIdKeyByFormTemplateId(String formTemplateId){
        String moduleIdKey = "";
        if(StringUtils.isNotBlank(formTemplateId)){
            if(CbpmFormTemplateIdEnum.QUA_OF_REVIEW.getKey().equals(formTemplateId)){   //资质审查
                moduleIdKey = CbpmModuleIdEnum.SUPAUTH.getKey();
            }else if(CbpmFormTemplateIdEnum.INIT_PROJECT_APPROVAL.getKey().equals(formTemplateId)){   //招标立项审批
                moduleIdKey = CbpmModuleIdEnum.BID.getKey();
            }else if(CbpmFormTemplateIdEnum.END_PROJECT_APPROVAL.getKey().equals(formTemplateId)){   //招标结项审批
                moduleIdKey = CbpmModuleIdEnum.BID.getKey();
            }else if(CbpmFormTemplateIdEnum.INQUIRY_APPROVAL_FLOW.getKey().equals(formTemplateId)){ //价格审批单
                moduleIdKey = CbpmModuleIdEnum.INQ.getKey();
            }
        }
        return moduleIdKey;
    }

    /**
     * Description 根据操作类型Key获取流程事件Key
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.02
     * @throws
     **/
    public static final String getEventTypeByType(String opertionType){
        String evenType = "";
        if(CbpmOperationTypeEnum.DRAFT_SUBMIT.getKey().equals(opertionType)){
            evenType = CbpmEventTypeEnum.DRAFT_SUBMIT_EVENT.getKey();
        }else if(CbpmOperationTypeEnum.HANDLER_PASS.getKey().equals(opertionType)){
            evenType = CbpmEventTypeEnum.HANDLE_PASS_EVENT.getKey();
        }else if(CbpmOperationTypeEnum.HANDLER_REFUSE.getKey().equals(opertionType)){
            evenType = CbpmEventTypeEnum.HANDLE_REFUSE_EVENT.getKey();
        }else if(CbpmOperationTypeEnum.HANDLER_COMMUNICATE.getKey().equals(opertionType)){
            evenType = CbpmEventTypeEnum.HANDLE_COMMUNICATE_EVENT.getKey();
        }else if(CbpmOperationTypeEnum.HANDLER_COMMISSION.getKey().equals(opertionType)){
            evenType = CbpmEventTypeEnum.HANDLE_COMMISSION_EVENT.getKey();
        }else if(CbpmOperationTypeEnum.HANDLER_RETURN_COMMUNICATE.getKey().equals(opertionType)){
            evenType = CbpmEventTypeEnum.HANDLE_RETURN_COMMUNICATE_EVENT.getKey();
        }else if(CbpmOperationTypeEnum.HANDLER_CANCEL_COMMUNICATE.getKey().equals(opertionType)){
            evenType = CbpmEventTypeEnum.HANDLE_CANCEL_COMMUNICATE_EVENT.getKey();
        }else if(CbpmOperationTypeEnum.DRAFT_ABANDON.getKey().equals(opertionType)){
            evenType = CbpmEventTypeEnum.DRAFT_ABANDON_EVENT.getKey();
        }else if(CbpmOperationTypeEnum.DRAFT_RETURN.getKey().equals(opertionType)){
            evenType = CbpmEventTypeEnum.DRAFT_RETURN_EVENT.getKey();
        }else if(CbpmOperationTypeEnum.HANDLER_ABANDON.getKey().equals(opertionType)){
            evenType = CbpmEventTypeEnum.HANDLE_ABANDON_EVENT.getKey();
        }
        return evenType;
    }


}
