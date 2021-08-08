package com.midea.cloud.flow.external;

import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.flow.common.constant.FlowCommonConst;
import com.midea.cloud.flow.workflow.service.IFlowInterfaceSetService;
import com.midea.cloud.flow.workflow.service.IWorkFlowService;
import com.midea.cloud.flow.workflow.service.impl.StartPingServiceHandler;
import com.midea.cloud.srm.model.common.BaseController;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 工作流共用外部调用Controller 类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/03 16:00
 *  修改内容:
 * </pre>
 */
@RestController
public class WorkFlowExternalController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkFlowExternalController.class);

    @Autowired
    private IWorkFlowService iWorkFlowService;

    /**租户ID**/
    @Value("${work-flow.tenanId}")
    private String tenanId;

    /**工作流接口访问配置表 Service接口*/
    @Autowired
    private IFlowInterfaceSetService iFlowInterfaceSetService;

    /**
     * Description Cbpm 统一回调事件方法
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.03
     * @throws
     **/
    @PostMapping("/event/workflow/newEventFlowCallBackCBPM")
    public Map<String, Object> newEventFlowCallBackCBPM(@RequestParam(name ="appId") String appId, @RequestParam(name ="token") String token,
                                                        @RequestBody Map<String, Object> mipCallBackParams){

        Map<String, Object> result = new HashMap<>();
        /**token校验*/
        LOGGER.info("流程回调Token校验appId：{},token：{}",appId,token);
        if(StringUtils.isBlank(appId) || StringUtils.isEmpty(appId) ){
            result.put("success", false);
            result.put("msg", FlowCommonConst.APPID_NOT_NULL);
            return  result;
        }
        if(StringUtils.isBlank(token) || StringUtils.isEmpty(token)){
            result.put("success", false);
            result.put("msg", FlowCommonConst.TOKEN_NOT_NULL);
            return  result;
        }

        /**根据租户Id获取对应的cbpm的salt相关信息*/
        String appIdParam = StartPingServiceHandler.APP_ID;
        String salt = StartPingServiceHandler.SALT;
        if(StringUtils.isBlank(appIdParam) || StringUtils.isEmpty(appIdParam) ){
            result.put("success", false);
            result.put("msg", FlowCommonConst.APPID_NOT_NULL);
            return  result;
        }
        if(StringUtils.isBlank(salt) || StringUtils.isEmpty(salt)){
            result.put("success", false);
            result.put("msg", FlowCommonConst.SALT_NOT_NULL);
            return  result;
        }

        if(!appId.equals(appIdParam)){
            result.put("success", false);
            result.put("msg", FlowCommonConst.APPID_NOT_EQUALS);
            return  result;
        }
        if(null == mipCallBackParams){
            result.put("success", false);
            result.put("msg", ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
            return  result;
        }
        try{
            Map<String, Object> callBack = iWorkFlowService.newEventFlowCallBackCBPM(appId, token, mipCallBackParams);
            result.put("success", true);
        } catch (Exception e){
            LOGGER.error("回调事件异常：",e);
            //回调异常，记录异常信息
            String msg = ExceptionUtils.getFullStackTrace(e);
            String processId = "-404";
            if(mipCallBackParams.get("body") != null){
                Map<String, Object> body = (Map<String, Object>) mipCallBackParams.get("body");
                if(body.get("processId") != null){
                    processId = (String) body.get("processId");
                }
            }
            result.put("success", false);
            result.put("msg", "SRM系统回调失败:"+e);
            try {
                //记录回调事件
                iWorkFlowService.recordCallBackEvent(processId, null, JsonUtil.entityToJsonStr(mipCallBackParams), "EventStatus_Error", msg);
            }catch (Exception e1){
                //如果回调时报错，资质审查的会修改状态为DRAFT（嵌套在回调方法始终觉得不是最优方法，后续了解看看有没有更好的方案实现）
                LOGGER.error("SRM系统回调失败: appId为{},流程ID为{}{}", appId, processId, e1);
            }
        }

        return result;
    }

}
