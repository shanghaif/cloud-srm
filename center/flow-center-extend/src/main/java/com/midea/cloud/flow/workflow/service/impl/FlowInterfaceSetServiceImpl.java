package com.midea.cloud.flow.workflow.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.DataEncrypt;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.common.utils.redis.RedisLockUtil;
import com.midea.cloud.flow.common.constant.FlowCommonConst;
import com.midea.cloud.flow.workflow.mapper.FlowInterfaceSetMapper;
import com.midea.cloud.flow.workflow.service.ICbpmWorkFlowService;
import com.midea.cloud.flow.workflow.service.IFlowInterfaceSetService;
import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.flow.process.dto.ProcessSystemDTO;
import com.midea.cloud.srm.model.flow.process.entity.FlowInterfaceSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
*  <pre>
 *  工作流接口访问配置表 服务实现类
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-26 19:28:59
 *  修改内容:
 * </pre>
*/
@Service
@Slf4j
public class FlowInterfaceSetServiceImpl extends ServiceImpl<FlowInterfaceSetMapper, FlowInterfaceSet> implements IFlowInterfaceSetService {

    /**调用地址**/
    @Value("${work-flow.sourceAddr}")
    private String sourceAddr;
    /**跳转地址*/
    @Value("${work-flow.forwardTo}")
    private String forwardTo;
    /**服务名称前缀**/
    @Value("${work-flow.serviceName}")
    private String serviceName;
    /**服务器url**/
    @Value("${work-flow.url}")
    private String url;
    /**业务系统appId**/
//    @Value("${work-flow.appId}")
//    private String appId;
    /**业务系统sysId*/
//    @Value("${work-flow.sysId}")
//    private String sysId;
    /**租户ID**/
    @Value("${work-flow.tenanId}")
    private String tenanId;
    /**首先配置的appId**/
    @Value("${work-flow.adminAppId}")
    private String adminAppId;
    /**首先配置的admin的salt*/
    @Value("${work-flow.adminSalt}")
    private String adminSalt;

    /**Idass门户Url*/
    @Value("${work-flow.idaasUrl}")
    private String idaasUrl;

    /**是否启动idssToken开关*/
    @Value("${work-flow.idaasToken}")
    public boolean idaasToken;

    @Autowired
    private RedisLockUtil redisLockUtil;

    @Autowired
    private RestTemplate restTemplate; // rest请求对象

    /**工作流接口访问配置表 Service接口*/
    @Autowired
    private IFlowInterfaceSetService iFlowInterfaceSetService;

    /**cbpm工作流接口类*/
    @Resource
    private ICbpmWorkFlowService iCbpmWorkFlowService;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void getProcessSalt() throws Exception {

        if(StringUtils.isBlank(tenanId) || StringUtils.isEmpty(tenanId)){
            Assert.notNull(tenanId, FlowCommonConst.TENANTI_ID_NOT_NULL);
        }

        /**判断是否有工作流接口访问配置信息,如果没有则新建**/
        boolean isSaveInstanceSet = false; //是否保存工作流接口信息，如果查询不到数据才保存
        FlowInterfaceSet queryFlowInterfaceSet = new FlowInterfaceSet();
        queryFlowInterfaceSet.setDeleteFlag(FlowCommonConst.DELETE_FLAG_NO);
        queryFlowInterfaceSet.setTenantId(tenanId);
        List<FlowInterfaceSet> flowInterfaceSetList = iFlowInterfaceSetService.list(new QueryWrapper<>(queryFlowInterfaceSet));
        if(CollectionUtils.isEmpty(flowInterfaceSetList) || 1 > flowInterfaceSetList.size()){
            isSaveInstanceSet = true;
        }

        /**根据之前配置好的系统信息salt，生成一条业务使用的系统配置信息*/
        if(isSaveInstanceSet) {

            /**新增系统信息**/
            ProcessSystemDTO processSystem = new ProcessSystemDTO();
            String appId = StringUtil.genChar(10);
            if(StringUtils.isBlank(appId) || StringUtils.isEmpty(appId)){
               Assert.notNull(appId, FlowCommonConst.APPID_NOT_NULL);
            }
            String admin = "admin";
            processSystem.setFdName(appId);
            processSystem.setFdCode(appId);
            processSystem.setDocCreatorId(admin);
            processSystem.setAuthAeditorIds(admin);
            processSystem.setAuthAreaderIds(admin);
            Map<String, Object> processSaltMap = this.getBodyData("addSystem", processSystem);

            if(null == processSaltMap){
                throw new Exception("保存系统配置信息盐值为空");
            }else if(null == processSaltMap.get("fdSalt")){
                throw new Exception("保存系统配置信息盐值为空");
            }

            /**新增系统信息成功之后,保存到工作流接口访问配置表*/
            if(null != processSaltMap && null != processSaltMap.get("fdSalt")){
                FlowInterfaceSet flowInterfaceSet = new FlowInterfaceSet();
                flowInterfaceSet.setInterfaceSetId(IdGenrator.generate());
                flowInterfaceSet.setDeleteFlag(FlowCommonConst.DELETE_FLAG_NO);
                flowInterfaceSet.setAppId(appId);
                flowInterfaceSet.setSysId(appId);
                flowInterfaceSet.setSalt(String.valueOf(processSaltMap.get("fdSalt")));
                flowInterfaceSet.setForwardTo(forwardTo);
                flowInterfaceSet.setServiceName(serviceName);
                flowInterfaceSet.setSourceAddr(sourceAddr);
                flowInterfaceSet.setUrl(url);
                flowInterfaceSet.setCreationDate(new Date());
                flowInterfaceSet.setCreatedBy("system");
                flowInterfaceSet.setCreatedFullName("system");
                flowInterfaceSet.setCreatedByIp("127.0.0.1");
                flowInterfaceSet.setCreatedId(0L);
                flowInterfaceSet.setVersion(FlowCommonConst.VERSION_0);
                flowInterfaceSet.setTenantId(tenanId);
                int saveCount = getBaseMapper().insert(flowInterfaceSet);
                if(0 > saveCount){
                    log.error("保存系统配置信息失败");
                }
            }else{
                log.error("保存系统配置信息失败,原因：返回盐值为空");
            }
        }
    }

    /**
     * Description 根据获取的接口名称和查询条件获取data的Map数据,如果接口有异常则抛出具体的异常信息
     * @Param apiName 接口名称 flowProcessQuery查询实体类
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     * @throws
     **/
    private Map<String, Object> getBodyData(String apiName, Object object) throws Exception {
        Map<String, Object> result = new HashMap<>();
        result = this.getBody(apiName, object);
        if(null != result) {
            String resultCode = String.valueOf(result.get("resultCode"));
            if (!FlowCommonConst.SUCCESS_CODE_010.equals(resultCode)) {
                log.error("调用cbpm工作流"+apiName+"接口时报错: "+String.valueOf(result.get("resultMsg")));
                throw new Exception(String.valueOf(result.get("resultMsg")));
            }
            result = (Map) result.get("data");
        }
        return result;
    }

    /**
     * Description 根据获取的接口名称和查询条件获取Body的Map数据,如果接口有异常则抛出具体的异常信息
     * @Param apiName 接口名称 flowProcessQuery查询实体类
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     * @throws
     **/
    private Map<String, Object> getBody(String apiName, Object object) throws Exception {
        Map<String, Object> result = new HashMap<>();
        if(null != object){
            Map<String, Object> body = new HashMap<>();
            if(object instanceof BaseDTO) {
                body = JsonUtil.parseObjectToMap(object);
                if(null != body) {
                    Map<String, Object> mapResult = postForObject(apiName, body);
                    result = (null != mapResult) ? (Map)mapResult.get("body") : new HashMap<>();
                }
            }
        }
        return result;
    }

    /**
     * Description 根据http请求获取cbpm流程引擎接口信息
     * @Param methodName 接口名 body查询参数
     * @return Map<String,Object>
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     **/
    private Map<String,Object> postForObject(String methodName,Map<String, Object> body){

        //获取统一的header 请求头
        JSONObject reqParam = this.getReqHeader(true);
        //已经拼接好一个完整的reqParam请求信息了
        reqParam.put("body", body);
        System.out.println("body参数："+ JsonUtil.entityToJsonStr(body));
        //到此为止,请求体已经完成,下面开始拼接token验证
        String token = null;
        try {
            token = this.getToken(reqParam);
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println("=============token================");
        System.out.println(token);
        //拼接URL
        String url = this.getUrl(token,methodName);
        //拼接 消息头
        HttpHeaders headers =  this.getreqHeaders();
        //底层流的基本实体
        HttpEntity<String> entity = new HttpEntity<String>(reqParam.toJSONString(), headers);
        //这个result,是工作流返回的参数
        String result = null;
        try {
            Long start = System.currentTimeMillis();
            result = restTemplate.postForObject(new URI(url), entity, String.class);
            Long end = System.currentTimeMillis();
            Long diff = end - start;
/*            if(diff.compareTo(1500L) == 1 ){
                logger.info("请求Cbpm接口耗时：" + (end - start) + " ms; 请求URL：" + url);
            }*/
            log.info("请求Cbpm接口耗时：" + (end - start) + " ms; 请求URL：" + url);
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("======================工作流返回的参数======================");
        System.out.println(result);
        Map<String,Object> mapData = JsonUtil.parseJsonStrToMap(result);
        return mapData;
    }

    //拼接请求头信息
    private JSONObject getReqHeader(boolean b) {

        JSONObject reqParam = new JSONObject();

        //header那的参数
        JSONObject header = new JSONObject();
        //sourceAddr参数
        try {
            header.put("sourceAddr", InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            header.put("sourceAddr", sourceAddr);
        }
        //forwardTo参数
        header.put("forwardTo", forwardTo);
        //timestamp参数     调用方的格林威治时间说
        header.put("timestamp", String.valueOf(System.currentTimeMillis()));
        //sn参数 每次调用都不一样的唯一序列号
        header.put("sn", UUID.randomUUID());
        //timeZone时区参数
        header.put("timeZone", "UTC");
        //serviceName参数
        header.put("serviceName", serviceName);
        //到此为止呢,我们就把header这块内容拼接完毕,下面开始拼接body参数
        reqParam.put("header", header);
        return reqParam;
    }

    //拼接头信息
    private HttpHeaders getreqHeaders() {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mediaType);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        //国际化在这放语言噢
        headers.add("Accept-Language","zh-CN");

        /**私有云的需要去掉获取idass_token*/
        if(idaasToken) {
            String key = "x_idass_token_value";
            Object idaasToken = redisTemplate.opsForValue().get(key);
            if (null == idaasToken) {
                try {
                    redisLockUtil.lock(key, 3);
                    idaasToken = iCbpmWorkFlowService.getIdaasToken();
                    redisTemplate.opsForValue().set(key, idaasToken, 110, TimeUnit.MINUTES);
                } finally {
                    redisLockUtil.unlock(key);
                }
            }
            headers.add("X-IDaaS-Token", String.valueOf(idaasToken));
        }
        return headers;
    }

    //组装Url
    private String getUrl(String token, String methedName) {

        //url前面固定的参数 uat环境：
        String uatURL = url;
        //方法名称
        String url = uatURL + methedName + "?appId="+ adminAppId + "&token=" + token;

        return url;
    }

    //获取token
    private String getToken(JSONObject reqParam) throws UnsupportedEncodingException {
        //找流程引擎的同事拿到对应的盐 -->  580758d8-fd4a-439e-8866-fa3f39640a0e
        //DataEncrypt 这个类,我们会提供,在接口卡中有代码,直接拷贝代码即可
        String token = DataEncrypt.getEncryptStr(reqParam.toString(), adminSalt);
        token = URLEncoder.encode(token, "UTF-8");
        return token;
    };


}
