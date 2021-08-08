package com.midea.cloud.srm.ps.http.fssc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.enums.pm.ps.FSSCResponseCode;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.common.utils.NamedThreadFactory;
import com.midea.cloud.component.config.RibbonTemplateWrapper;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.api.ApiClient;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.pm.ps.http.AdvanceApplyDto;
import com.midea.cloud.srm.model.pm.ps.anon.external.FsscStatus;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.pm.ps.http.BgtCheckReqParamDto;
import com.midea.cloud.srm.model.pm.ps.http.PaymentApplyDto;
import com.midea.cloud.srm.ps.http.fssc.service.IFSSCReqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import java.util.Date;
import java.util.HashMap;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/27 15:59
 *  修改内容:
 * </pre>
 */
@Slf4j
@Service
public class FSSCReqServiceImpl implements IFSSCReqService {

//    private static final String GET_TOKEN_URL = "/open/auth/token/login";//获取令牌url
//    private static final String APPLY_RELEASE_URL = ":28080/interface-internal/lgibgt/applyRelease";//预算释放url
//    private static final String APPLY_FREEZE_URL = ":28080/interface-internal/lgibgt/applyFreeze";//预算冻结url
//    private static final String PAYMENT_URL = ":28080/interface-internal/syncBoeController/saveThirdBoe"; //采购结算对接费控url
//    private static final String ONLINE_INVOICE_URL = ":28080/interface-internal/syncBoeController/saveThirdBoe";//网上开票对接费控url
//    private static final String ADVANCE_URL = ":28080/interface-internal/syncBoeController/saveThirdBoe"; //预付款对接费控url
//
//    private static final String ABANDON_URL = "http://10.0.10.26:28080/interface-internal/syncThirdBoeStatusController/cancelBoe";//对接费控作废url
//    private static final String TEMP_APPLY_FREEZE_URL = "http://10.0.10.26:28080/interface-internal/lgibgt/applyFreeze";
//    private static final String TEMP_APPLY_RELEASE_URL = "http://10.0.10.26:28080/interface-internal/lgibgt/applyRelease";
//    private static final String TEMP_URL = "http://10.0.10.26:28080/interface-internal/syncBoeController/saveThirdBoe";
//    private static final String LOGIN_URL = "http://10.0.10.26/open/auth/token/login";

    @Value("${ceea.fssc.appKey}")
    private String appKey;

    @Value("${ceea.fssc.appSecret}")
    private String appSecret;

    @Value("${ceea.fssc.loginUrl}")
    private String loginUrl;

    @Value("${ceea.fssc.abandonUrl}")
    private String abandonUrl;

    @Value("${ceea.fssc.applyFreezeUrl}")
    private String applyFreezeUrl;

    @Value("${ceea.fssc.applyReleaseUrl}")
    private String applyReleaseUrl;

    @Value("${ceea.fssc.saveThirdBoeUrl}")
    private String saveThirdBoeUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApiClient apiClient;

    private void logFSSC(Object requestBody ,
                         ResponseEntity<FSSCResult> responseBody ,
                         String interfaceName ,
                         String url,
                         String businessNo){
        // 日志收集类
        InterfaceLogDTO interfaceLogDTO = new InterfaceLogDTO();
        interfaceLogDTO.setServiceName(interfaceName); // 说明
        interfaceLogDTO.setServiceType("HTTP"); // 接收方式
        interfaceLogDTO.setType("SEND"); // 类型
        interfaceLogDTO.setDealTime(1L);
        interfaceLogDTO.setBillId(businessNo);
        interfaceLogDTO.setServiceInfo(JSON.toJSONString(requestBody)); // 入参
        interfaceLogDTO.setCreationDateBegin(new Date()); // 开始时间
        interfaceLogDTO.setCreationDateEnd(new Date()); // 结束时间
        interfaceLogDTO.setUrl(url);
        interfaceLogDTO.setReturnInfo(JSON.toJSONString(responseBody)); // 出参
        FSSCResult fsscResult = responseBody.getBody();
        if(fsscResult == null){
            log.error("警告！！！调用费控接口返回为null！！！:"+JSON.toJSONString(responseBody));
            fsscResult = new FSSCResult();
        }
        if("0".equals(fsscResult.getCode())){
            interfaceLogDTO.setStatus("SUCCESS"); // 状态
            interfaceLogDTO.setFinishDate(new Date()); // 完成时间
        }else {
            interfaceLogDTO.setStatus("FAIL");
            interfaceLogDTO.setErrorInfo(JSON.toJSONString(responseBody));
        }
        apiClient.createInterfaceLog(interfaceLogDTO);
    }

    @Override
    public String getToken() {
        MultiValueMap map = new LinkedMultiValueMap();
        ResponseEntity<FSSCResult> response = null;

        map.add("appKey", appKey);
        map.add("appSecret", appSecret);
        /*String reqUrl = url + GET_TOKEN_URL;*/
        String reqUrl = loginUrl;
        log.info(reqUrl);
        try {
            log.info("------------------开始请求FSSC获取token------------------");
            response = restTemplate.postForEntity(reqUrl, map, FSSCResult.class);
            FSSCResult body = response.getBody();
            log.info("FSSC请求token:" + body.getToken());
            log.info("获取token返回信息:" + JsonUtil.entityToJsonStr(body));
            return body.getToken();
        } catch (Exception e) {
            log.info("------------------请求FSSC获取token异常------------------");
            log.error("错误信息:", e.getMessage());
            e.printStackTrace();
            throw new BaseException(LocaleHandler.getLocaleMsg("请求FSSC获取token失败"));
        }finally {
            try {
                logFSSC(map , response , "获取费控token" , reqUrl,"");
            }catch (Exception e){
                log.error("保存接口日志信息失败",e);
            }
        }
    }

    @Override
    public FSSCResult applyRelease(BgtCheckReqParamDto bgtCheckReqParamDto) {
        String token = getToken();
        HttpHeaders headers = getHttpHeaders(token);
        log.info("bgtCheckReqParamDto参数=================>" + JsonUtil.entityToJsonStr(bgtCheckReqParamDto));
        ResponseEntity<FSSCResult> responseEntity = null;
        System.out.println("bgtCheckReqParamDto参数=================>" + JsonUtil.entityToJsonStr(bgtCheckReqParamDto));
        HttpEntity entity = new HttpEntity(JsonUtil.entityToJsonStr(bgtCheckReqParamDto), headers);
        String requestJsonString = JsonUtil.entityToJsonStr(bgtCheckReqParamDto);
        String responseJsonString = "";
        try {
            String reqUrl = applyReleaseUrl;
            log.info("请求url:" + reqUrl);
            log.info("请求参数:" + entity.getBody());
            log.info("------------------开始请求FSSC执行预算释放------------------");
            responseEntity = restTemplate.postForEntity(reqUrl, entity, FSSCResult.class);
            log.info("请求预算释放返回值: ===========================================>" + responseEntity.getBody().toString());
            responseEntity = restTemplate.postForEntity(reqUrl, entity, FSSCResult.class);
            responseJsonString = JsonUtil.entityToJsonStr(responseEntity.getBody());
            log.info("请求预算释放返回值: ===========================================>" + responseJsonString);
            return responseEntity.getBody();
        } catch (Exception e) {
            log.info("------------------请求FSSC执行预算释放异常------------------");
            log.error("错误信息:", e.getMessage());
            e.printStackTrace();
            throw new BaseException(LocaleHandler.getLocaleMsg("请求FSSC执行预算释放失败"));
        }finally {
            try {
                logFSSC(bgtCheckReqParamDto , responseEntity , "请求FSSC执行预算释放",
                        applyReleaseUrl , bgtCheckReqParamDto.getDocumentNum());
            }catch (Exception e){
                log.error("保存接口日志信息失败",e);
            }
        }

    }

    @Override
    public FSSCResult applyFreeze(BgtCheckReqParamDto bgtCheckReqParamDto) {
        String token = getToken();
        HttpHeaders headers = getHttpHeaders(token);
        System.out.println("bgtCheckReqParamDto参数=================>" + JsonUtil.entityToJsonStr(bgtCheckReqParamDto));
        HttpEntity entity = new HttpEntity(JsonUtil.entityToJsonStr(bgtCheckReqParamDto), headers);
        ResponseEntity<FSSCResult> responseEntity = null;
        try {
            String reqUrl = applyFreezeUrl;
            log.info("请求url:" + reqUrl);
            log.info("请求参数:" + entity.getBody());
            System.out.println("请求参数:" + entity.getBody());
            log.info("------------------开始请求FSSC执行预算冻结------------------");
            responseEntity = restTemplate.postForEntity(reqUrl, entity, FSSCResult.class);
            log.info("请求预算冻结返回值: ===========================================>" + responseEntity.getBody().toString());
            return responseEntity.getBody();
        } catch (Exception e) {
            log.info("------------------请求FSSC执行预算冻结异常------------------");
            log.error("错误信息:", e.getMessage());
            e.printStackTrace();
            throw new BaseException(LocaleHandler.getLocaleMsg("请求FSSC执行预算冻结失败"));
        }finally {
            try {
                logFSSC(bgtCheckReqParamDto , responseEntity , "请求FSSC执行预算冻结" ,
                        applyFreezeUrl , bgtCheckReqParamDto.getDocumentNum());
            }catch (Exception e){
                log.error("保存接口日志信息失败",e);
            }
        }
    }
    /**
     * 提交付款申请
     * @param paymentApplyDto
     * @return
     */
    @Override
    public FSSCResult submitPaymentApply(PaymentApplyDto paymentApplyDto) {
        String token = getToken();
        HttpHeaders headers = getHttpHeaders(token);
        System.out.println("paymentApplyDto=================>" + JsonUtil.entityToJsonStr(paymentApplyDto));
        HttpEntity entity = new HttpEntity(JsonUtil.entityToJsonStr(paymentApplyDto),headers);
        ResponseEntity<FSSCResult> responseEntity = null;
        try {
            /*String reqUrl = url + PAYMENT_URL;*/
            String reqUrl = saveThirdBoeUrl;
            log.info("请求url:" + reqUrl);
            log.info("请求参数:" + entity.getBody());
            System.out.println("请求参数:" + entity.getBody());
            log.info("------------------开始请求FSSC执行提交付款申请------------------");
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setConnectTimeout(60*60*1000);
            requestFactory.setReadTimeout(60*60*1000);
            RestTemplate newRestTemplate = new RestTemplate(requestFactory);
//            ResponseEntity<FSSCResult> responseEntity = restTemplate.postForEntity(reqUrl, entity, FSSCResult.class);
            responseEntity = newRestTemplate.postForEntity(reqUrl, entity, FSSCResult.class);
            log.info("提交付款申请返回值: ===========================================>" + JSONObject.toJSONString(responseEntity));
            return responseEntity.getBody();
        }catch (Exception e){
            log.info("------------------请求FSSC提交付款申请异常------------------");
            log.error("错误信息:", e.getMessage());
            log.error("返回结果{}", JSONObject.toJSONString(responseEntity));
            e.printStackTrace();
            throw new BaseException(LocaleHandler.getLocaleMsg("请求FSSC提交付款申请失败"));
        }finally {
            try {
                logFSSC(paymentApplyDto , responseEntity , "请求FSSC提交付款申请",
                        saveThirdBoeUrl , paymentApplyDto.getBoeHeader().getSourceSystemNum());
            }catch (Exception e){
                log.error("保存接口日志信息失败",e);
            }
        }
    }

    /**
     * 提交预付款申请
     * @param advanceApplyDto
     * @return
     */
    @Override
    public FSSCResult submitAdvanceApply(AdvanceApplyDto advanceApplyDto) {
        String token = getToken();
        HttpHeaders headers = getHttpHeaders(token);
        System.out.println("advanceApplyDto=================>" + JsonUtil.entityToJsonStr(advanceApplyDto));
        HttpEntity entity = new HttpEntity(JsonUtil.entityToJsonStr(advanceApplyDto),headers);
        Long startTime = System.currentTimeMillis();
        ResponseEntity<FSSCResult> responseEntity = null;
        try {
            /*String reqUrl = url + ADVANCE_URL;*/
            String reqUrl = saveThirdBoeUrl;
            log.info("请求url:" + reqUrl);
            log.info("请求参数:" + entity.getBody());
            System.out.println("请求参数:" + entity.getBody());
            log.info("------------------开始请求FSSC执行提交预付款申请------------------");
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setConnectTimeout(60*60*1000);
            requestFactory.setReadTimeout(60*60*1000);
            RestTemplate newRestTemplate = new RestTemplate(requestFactory);
            responseEntity = newRestTemplate.postForEntity(reqUrl, entity, FSSCResult.class);
            log.info("提交预付款申请返回值: ===========================================>" + responseEntity.getBody().toString()+"用时(秒): "+(System.currentTimeMillis()-startTime)/1000);
            return responseEntity.getBody();
        }catch (Exception e){
            log.info("------------------请求FSSC提交预付款申请异常------------------"+"用时(秒): "+(System.currentTimeMillis()-startTime)/1000);
            log.error("错误信息:", e);
            throw new BaseException(LocaleHandler.getLocaleMsg("请求FSSC提交预付款申请失败"));
        }finally {
            try {
                logFSSC(advanceApplyDto , responseEntity , "请求FSSC提交预付款申请",
                        saveThirdBoeUrl, advanceApplyDto.getBoeHeader().getSourceSystemNum());
            }catch (Exception e){
                log.error("保存接口日志信息失败",e);
            }
        }
    }

    @Override
    public FSSCResult submitOnlineInvoice(PaymentApplyDto paymentApplyDto) {
        String token = getToken();
        HttpHeaders headers = getHttpHeaders(token);
        System.out.println("paymentApplyDto=================>" + JsonUtil.entityToJsonStr(paymentApplyDto));
        HttpEntity entity = new HttpEntity(JsonUtil.entityToJsonStr(paymentApplyDto),headers);
        ResponseEntity<FSSCResult> responseEntity = null;
        try {
            /*String reqUrl = saveThirdBoeUrl;*/
            String reqUrl = saveThirdBoeUrl;
            log.info("请求url:" + reqUrl);
            log.info("请求参数:" + entity.getBody());
            System.out.println("请求参数:" + entity.getBody());
            log.info("------------------开始请求FSSC执行提交网上开票------------------");
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setConnectTimeout(60*60*1000);
            requestFactory.setReadTimeout(60*60*1000);
            RestTemplate newRestTemplate = new RestTemplate(requestFactory);
            responseEntity = newRestTemplate.postForEntity(reqUrl, entity, FSSCResult.class);
            log.info("提交网上开票返回值: ===========================================>" + responseEntity.getBody().toString());
            return responseEntity.getBody();
        }catch (Exception e){
            log.info("------------------请求FSSC提交网上开票申请异常------------------");
            log.error("错误信息:", e);
            e.printStackTrace();
            throw new BaseException(LocaleHandler.getLocaleMsg("请求FSSC提交网上开票失败"));
        }finally {
            try {
                logFSSC(paymentApplyDto , responseEntity , "请求FSSC提交网上开票" ,
                        saveThirdBoeUrl , paymentApplyDto.getBoeHeader().getSourceSystemNum());
            }catch (Exception e){
                log.error("保存接口日志信息失败",e);
            }
        }
    }


    @Override
    public FSSCResult abandon(FsscStatus fsscStatus) {
        String token = getToken();
        HttpHeaders headers = getHttpHeaders(token);
        System.out.println("fsscStatus=================>" + JsonUtil.entityToJsonStr(fsscStatus));
        HttpEntity entity = new HttpEntity(JsonUtil.entityToJsonStr(fsscStatus),headers);
        ResponseEntity<FSSCResult> responseEntity = null;
        try {
            String reqUrl = abandonUrl;
            log.info("请求url:" + reqUrl);
            log.info("请求参数:" + entity.getBody());
            System.out.println("请求参数:" + entity.getBody());
            log.info("------------------开始请求FSSC执行作废------------------");
            responseEntity = restTemplate.postForEntity(reqUrl, entity, FSSCResult.class);
            log.info("执行作废返回值: ===========================================>" + responseEntity.getBody().toString());
            return responseEntity.getBody();
        }catch (RestClientException e){
            log.info("------------------请求FSSC执行作废异常------------------");
            log.error("错误信息:", e);
            e.printStackTrace();
            throw new BaseException(LocaleHandler.getLocaleMsg("请求FSSC执行作废失败"));
        }finally {
            try {
                logFSSC(fsscStatus , responseEntity , "请求FSSC执行作废",
                        abandonUrl , fsscStatus.getFsscNo());
            }catch (Exception e){
                log.error("保存接口日志信息失败",e);
            }
        }
    }

    private HttpHeaders getHttpHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mediaType);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Accept-Language", "zh-CN");
        headers.add("ZFS_TOKEN", token);
        return headers;
    }
}
