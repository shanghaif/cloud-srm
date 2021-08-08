package com.midea.cloud.srm.feign.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 工作流基本的Feign，用于其他模块使用工作流Feign集成类
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
public class CbpmBaseFeign implements ApplicationContextAware, DisposableBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(CbpmBaseFeign.class);

    /**完整路劲类名*/
    private String classPath;
    /**方法名*/
    private String methodName;
    /**业务Key(对应templateCode)*/
    private String businessKey;
    /**业务审批ID*/
    private Long businessId;
    /**事件类型Map集合*/
    private Map<String, Object> mapEventData;
    /**模板ID*/
    private String modelId;
    /**处理人*/
    private String handlerId;
    /**处理人名*/
    private String handlerName;
    /**处理机器IP地址*/
    private String handlerIp;

    /**初始化上下文*/
    private static ApplicationContext applicationContext = null;

    /**
     * Description 取得存储在静态变量中的ApplicationContext.
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.26
     **/
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Description 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * @Param requiredType Class类
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.26
     **/
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    /**
     * Description 通过反射机制映射到具体实现类
     * @Param classPath 类名
     * @Param methodName 方法名
     * @Param params 参数
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.25
     * @throws Exception
     **/
    protected Map<String, Object> invokMethod(String classPath, String methodName, Object[] params, Class[] paramClass)
            throws Exception{
        Class hanlderClass = Class.forName(classPath);
        Method method = hanlderClass.getMethod(methodName, paramClass);
        Object bean = CbpmBaseFeign.getBean(hanlderClass);
        Object obj = method.invoke(bean, params);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "0");
        map.put("msg", "成功");
        map.put("data", obj);
        return map;
    }

    /**
     * Description 工作流基本的Feign，用于其他模块继承工作流Feign方法
     * @Param
     * @return 
     * @Author wuwl18@meicloud.com
     * @Date 2020.04.07
     * @throws 
     **/
    protected Map<String, Object> cbpmBaseFeignHandler(Map<String, Object> eventParamMap) throws Exception{
/*        if(eventParamMap.get("businessKey") != null){
            this.businessKey = eventParamMap.get("businessKey").toString();
        } else {
            this.businessKey = null;
        }*/
        if(eventParamMap.get("businessId") != null){
            this.businessId = Long.parseLong(eventParamMap.get("businessId").toString());
        } else {
            this.businessId = null;
        }
/*        if(eventParamMap.get("modelId") != null){
            this.modelId = eventParamMap.get("modelId").toString();
        } else {
            this.modelId = null;
        }*/
        if(eventParamMap.get("classPath") != null){
            this.classPath = eventParamMap.get("classPath").toString();
        } else {
            this.classPath = null;
        }
        if(eventParamMap.get("methodName") != null){
            this.methodName = eventParamMap.get("methodName").toString();
        } else {
            this.methodName = null;
        }
        if(eventParamMap.get("mapEventData") != null){
            this.mapEventData = (Map<String, Object>)eventParamMap.get("mapEventData");
        } else {
            this.mapEventData = null;
        }
/*        if(eventParamMap.get("handlerId") != null){
            this.handlerId = eventParamMap.get("handlerId").toString();
        } else {
            this.handlerId = null;
        }
        if(eventParamMap.get("handlerName") != null){
            this.handlerName = eventParamMap.get("handlerName").toString();
        } else {
            this.handlerName = null;
        }
        if(eventParamMap.get("handlerIp") != null){
            this.handlerIp = eventParamMap.get("handlerIp").toString();
        } else {
            this.handlerIp = null;
        }*/

        if(this.classPath == null || this.methodName == null){
            return null;
        }

        Object[] params = {this.businessId, this.mapEventData};
        Class[] paramClass = {Long.class, Map.class};
        return invokMethod(this.classPath, this.methodName, params, paramClass);
    }


    /**
     * Description 清除SpringContextHolder中的ApplicationContext为Null.
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.26
     * @throws
     **/
    public static void clearHolder() {
        LOGGER.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
        applicationContext = null;
    }

    @Override
    public void destroy() throws Exception {
        CbpmBaseFeign.clearHolder();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (CbpmBaseFeign.applicationContext != null) {
            LOGGER.warn("biz-feign模块的SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
                    + CbpmBaseFeign.applicationContext);
        }
        CbpmBaseFeign.applicationContext = applicationContext;
    }
}
