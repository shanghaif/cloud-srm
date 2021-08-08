package com.midea.cloud.srm.sup.riskraider.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.constants.DESUtilForRisk;
import com.midea.cloud.common.enums.sup.RiskIcToIndexEnum;
import com.midea.cloud.common.enums.sup.RiskInfoEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.riskraider.log.dto.RiskResponse2DTO;
import com.midea.cloud.srm.model.supplier.riskraider.log.dto.RiskResponseDTO;
import com.midea.cloud.srm.model.supplier.riskraider.log.entity.RaiderJobLogEntity;
import com.midea.cloud.srm.model.supplier.riskraider.log.entity.RiskInfoLog;
import com.midea.cloud.srm.model.supplier.riskraider.toEs.dto.RaiderEsDto;
import com.midea.cloud.srm.sup.info.service.impl.CompanyInfoServiceImpl;
import com.midea.cloud.srm.sup.riskraider.log.service.IRiskInfoLogService;
import com.midea.cloud.srm.sup.riskraider.log.service.impl.RaiderJobLogServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author vent
 * @Description
 **/
@Component
@Slf4j
public class HandleRaiderInfo {

    private static RestTemplate restTemplate;

    static {
        try {
            TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> {
                return true;
            };
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial((KeyStore)null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);

            HttpHost proxy = new HttpHost("10.74.154.88", 8088);

            CloseableHttpClient httpClient = httpClientBuilder.setProxy(proxy).build();
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setHttpClient(httpClient);
            restTemplate = new RestTemplate(factory);
        } catch (Exception ex) {
        }
    }

    @Autowired
    private IRiskInfoLogService iRiskInfoLogService;
    @Autowired
    private CompanyInfoServiceImpl companyInfoService;
    @Resource
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private RaiderJobLogServiceImpl raiderJobLogService;



//    private final String userName = "LJLNAPI";
//    private final String appKey = "2c90808f763ac4a80176f5ef6b2102f6";
//    private final String accountId = "ff808081704e2ca3017066f7182612e1";
//    private final String accountType= "2";
//    private final String apiKeyForLongi= "10UaH1Z5c01DPK+zSNHFelFGxTsNmMeN5S3T6NNKVaVTudw+/WZqQA==";\

    @Value("${raider.userName}")
    private String userName;
    @Value("${raider.appKey}")
    private String appKey;
    @Value("${raider.accountId}")
    private String accountId;
    @Value("${raider.accountType}")
    private String accountType;
    @Value("${raider.apiKeyForLongi}")
    private String apiKeyForLongi;
    
    private ThreadLocal<Long> logId = new ThreadLocal<>();
    private AtomicLong id;

    public JSONObject getRiskInfo(CompanyInfo companyInfo, String interfaceCode) throws Exception {
        return getRiskInfo(companyInfo,interfaceCode,false,true,false,null,null,null);
    }
    public JSONObject getRiskInfo(CompanyInfo companyInfo, String interfaceCode,boolean hasAccountId) throws Exception {
        return getRiskInfo(companyInfo,interfaceCode,hasAccountId,true,false,null,null,null);
    }
    public JSONObject getRiskInfo(CompanyInfo companyInfo, String interfaceCode,Integer currentPageNo,Integer pageSize) throws Exception {
        return getRiskInfo(companyInfo,interfaceCode,false,true,false,currentPageNo,pageSize,null);
    }
    public JSONObject getRiskInfo(CompanyInfo companyInfo, String interfaceCode,Integer currentPageNo,Integer pageSize,String newsTags) throws Exception {
        return getRiskInfo(companyInfo,interfaceCode,false,true,false,currentPageNo,pageSize,newsTags);
    }
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getRiskInfo(CompanyInfo companyInfo,String interfaceCode,boolean hasAccountId,boolean isEnterpriseName,boolean isKeywords,Integer currentPageNo,Integer pageSize,String newsTags ) throws Exception {
        Assert.notNull(companyInfo,"查询公司信息为空");
        String companyName = companyInfo.getCompanyName();
        if(StringUtils.isBlank(companyName) || StringUtils.isBlank(interfaceCode)){
            throw new BaseException(LocaleHandler.getLocaleMsg("查询公司名称不能为空"));
        }
        Assert.hasText(companyName, LocaleHandler.getLocaleMsg("查询公司名称不能为空"));
        Assert.hasText(interfaceCode, LocaleHandler.getLocaleMsg("接口编码不能为空"));
        String url = RiskInfoEnum.getValueByCode(interfaceCode);
        Assert.hasText(url, LocaleHandler.getLocaleMsg("接口编码错误"));
        checkInterfaceCount(interfaceCode,companyInfo.getCompanyId());

        URI uri = new URI(url);
        HashMap<String, Object> paramMap = new HashMap<>();
        String encrypt = DESUtilForRisk.encrypt(companyName, appKey);
        paramMap.put("userName",userName);
        if(isEnterpriseName){
            paramMap.put("enterpriseName",encrypt);
        }
        if (isKeywords){
            paramMap.put("keywords",accountId);
        }
        if(hasAccountId){
            paramMap.put("accountId",accountId);
        }
        if(Objects.nonNull(currentPageNo)){
            paramMap.put("currentPageNo",currentPageNo);
        }
        if(Objects.nonNull(pageSize)){
            paramMap.put("pageSize",pageSize);
        }
        if(Objects.nonNull(newsTags)){
            paramMap.put("newsTags",newsTags);
        }


        //日志


        RiskInfoLog riskInfoLog = new RiskInfoLog()
                .setVendorId(companyInfo.getCompanyId())
                .setVendorCode(companyInfo.getCompanyCode())
                .setVendorName(companyInfo.getCompanyName())
                .setParameterIn(JSON.toJSONString(paramMap))
                .setInterfaceCode(interfaceCode);

        if(Objects.isNull(logId.get())){
            logId.set(IdGenrator.generate());
        }else {
            logId.remove();
            logId.set(IdGenrator.generate());
        }

        if(Objects.nonNull(iRiskInfoLogService.getById(logId.get()))){
            logId.set(IdGenrator.generate());
        }
        riskInfoLog.setLogId(logId.get());

        riskInfoLog.setParameterIn(JSON.toJSONString(paramMap));
        JSONObject jsonObject = null;

        try {
            RiskResponseDTO result = restTemplate.postForObject(uri, paramMap, RiskResponseDTO.class);
            log.info("调用接口{}，入参：{}，返回值：{}",interfaceCode,JSON.toJSONString(paramMap),JSON.toJSONString(result));
            jsonObject = result.getResultData();
//            RiskInfoLog byId = iRiskInfoLogService.getById(8592528882401280l);
//            jsonObject=JSONObject.parseObject(byId.getResultData());
            JSONObject interfaceInfo = result.getInterfaceInfo();

            riskInfoLog.setResultCode(result.getResultCode())
                    .setResultData(JSON.toJSONString(jsonObject))
                    .setResultMsg(result.getResultMsg())
                    .setBillFlag(Objects.isNull(interfaceInfo)?null:interfaceInfo.getJSONObject("billingInfo").getString("billFlag"))
                    .setBillMsg(Objects.isNull(interfaceInfo)?null:interfaceInfo.getJSONObject("billingInfo").getString("billMsg"));
            if(Objects.nonNull(companyInfo.getJobLogId())){
                raiderJobLogService.update(null,
                        Wrappers.lambdaUpdate(RaiderJobLogEntity.class)
                                .set(RaiderJobLogEntity::getResultCode,result.getResultCode())
                                .eq(RaiderJobLogEntity::getJobLogId,companyInfo.getJobLogId()));
            }

        }finally {
            iRiskInfoLogService.save(riskInfoLog);
            logId.remove();
        }
        return jsonObject;
    }

    public <T> T getRiskInfo(CompanyInfo companyInfo,String interfaceCode,Class<T> clazz) throws Exception {
        return getRiskInfo(companyInfo,interfaceCode,clazz,false);
    }
    @Transactional(rollbackFor = Exception.class)
    public <T> T getRiskInfo(CompanyInfo companyInfo,String interfaceCode,Class<T> clazz,boolean hasAccountId) throws Exception {
        String companyName = companyInfo.getCompanyName();
        if(StringUtils.isBlank(companyName) || StringUtils.isBlank(interfaceCode)){
            throw new BaseException(LocaleHandler.getLocaleMsg("查询公司名称不能为空"));
        }
        Assert.hasText(companyName, LocaleHandler.getLocaleMsg("查询公司名称不能为空"));
        Assert.hasText(interfaceCode, LocaleHandler.getLocaleMsg("接口编码不能为空"));
        String url = RiskInfoEnum.getValueByCode(interfaceCode);
        Assert.hasText(url, LocaleHandler.getLocaleMsg("接口编码错误"));
        checkInterfaceCount(interfaceCode,companyInfo.getCompanyId());

        URI uri = new URI(url);
        HashMap<String, String> paramMap = new HashMap<>();
//        String userName = userName;
//        String appKey = "2c90808f763ac4a80176f5ef6b2102f6";
        String encrypt = DESUtilForRisk.encrypt(companyName, appKey);
        paramMap.put("userName",userName);
        paramMap.put("enterpriseName",encrypt);
        if(hasAccountId){
            paramMap.put("accountId",accountId);
        }

        //日志
        if(Objects.isNull(logId.get())){
            logId.set(IdGenrator.generate());
        }else {
            logId.remove();
            logId.set(IdGenrator.generate());
        }
        Long id = logId.get();
        RiskInfoLog riskInfoLog = new RiskInfoLog()
                .setLogId(id)
                .setVendorId(companyInfo.getCompanyId())
                .setVendorCode(companyInfo.getCompanyCode())
                .setVendorName(companyInfo.getCompanyName())
                .setParameterIn(JSON.toJSONString(paramMap))
                .setInterfaceCode(interfaceCode);

        riskInfoLog.setParameterIn(JSON.toJSONString(paramMap));
        JSONObject jsonObject = null;

        try {
            RiskResponseDTO result = restTemplate.postForObject(uri, paramMap, RiskResponseDTO.class);
            log.info("调用接口{}，入参：{}，返回值：{}",interfaceCode,JSON.toJSONString(paramMap),JSON.toJSONString(result));
            jsonObject = result.getResultData();
            JSONObject interfaceInfo = result.getInterfaceInfo();

            riskInfoLog.setResultCode(result.getResultCode())
                    .setResultData(JSON.toJSONString(jsonObject))
                    .setResultMsg(result.getResultMsg())
                    .setBillFlag(Objects.isNull(interfaceInfo)?null:interfaceInfo.getJSONObject("billingInfo").getString("billFlag"))
                    .setBillMsg(Objects.isNull(interfaceInfo)?null:interfaceInfo.getJSONObject("billingInfo").getString("billMsg"));
            if(Objects.nonNull(companyInfo.getJobLogId())){
                raiderJobLogService.update(null,
                        Wrappers.lambdaUpdate(RaiderJobLogEntity.class)
                                .set(RaiderJobLogEntity::getResultCode,result.getResultCode())
                                .eq(RaiderJobLogEntity::getJobLogId,companyInfo.getJobLogId()));
            }

        }finally {
            iRiskInfoLogService.save(riskInfoLog);
            logId.remove();
        }
        return JSON.toJavaObject(jsonObject,clazz);
    }

    public void checkInterfaceCount(String interfaceCode,Long companyId){
        //统计该接口当前的调用次数
        //成功 >= 2
        boolean flag = false;
        int count = iRiskInfoLogService.count(Wrappers.lambdaQuery(RiskInfoLog.class)
                .eq(RiskInfoLog::getInterfaceCode, interfaceCode)
                .eq(RiskInfoLog::getVendorId, companyId)
                .eq(RiskInfoLog::getResultCode, "0")
                .last(" and to_days(creation_date) = to_days(now())")
        );
        if(count >= 2){
            String resultMsg = LocaleHandler.getLocaleMsg("接口[x],已达今天调用上限。", interfaceCode);
            throw new BaseException(resultMsg);
        }
        //总次数>=5
        int count1 = iRiskInfoLogService.count(Wrappers.lambdaQuery(RiskInfoLog.class)
                .select()
                .eq(RiskInfoLog::getInterfaceCode, interfaceCode)
                .eq(RiskInfoLog::getVendorId, companyId)
                .last(" and to_days(creation_date) = to_days(now())")
        );
        if(count1 >= 5){
            String resultMsg = LocaleHandler.getLocaleMsg("接口[x],已达今天调用上限。", interfaceCode);
            throw new BaseException(resultMsg);
        }
    }



    @Transactional(rollbackFor = Exception.class)
    public JSONObject getRiskInfoForUpdateRaider(RaiderEsDto raiderEsDto) throws Exception {
        Assert.notNull(raiderEsDto,"查询公司信息为空");
        String companyName = raiderEsDto.getCompanyName();
        String interfaceCode = raiderEsDto.getInterfaceCode();
        if(StringUtils.isBlank(companyName) || StringUtils.isBlank(interfaceCode)){
            throw new BaseException(LocaleHandler.getLocaleMsg("查询公司名称不能为空"));
        }
        Assert.hasText(companyName, LocaleHandler.getLocaleMsg("查询公司名称不能为空"));
        Assert.hasText(interfaceCode, LocaleHandler.getLocaleMsg("接口编码不能为空"));
        String url = RiskInfoEnum.getValueByCode(interfaceCode);
        Assert.hasText(url, LocaleHandler.getLocaleMsg("接口编码错误"));
        checkInterfaceCount(interfaceCode,raiderEsDto.getCompanyId());

        URI uri = new URI(url);
        HashMap<String, Object> paramMap = new HashMap<>();
        String encrypt = DESUtilForRisk.encrypt(companyName, appKey);
        paramMap.put("userName",userName);
//        paramMap.put("apiKey",apiKeyForLongi); // TODO 云里雾里
        paramMap.put("apiKey",encrypt);
        if(StringUtils.isNotEmpty(raiderEsDto.getEnterpriseName())){
            paramMap.put("enterpriseName", raiderEsDto.getEnterpriseName());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getAccountId())
                || Objects.equals(interfaceCode,"R12")
                || Objects.equals(interfaceCode,"R13")
                || Objects.equals(interfaceCode,"R14")
        ){
            paramMap.put("accountId",accountId);
        }


        if(StringUtils.isNotEmpty(raiderEsDto.getAccountType())){
            paramMap.put("accountType",raiderEsDto.getAccountType());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getEndDate())){
            paramMap.put("endDate",raiderEsDto.getEndDate());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getMonitorCycle())){
            paramMap.put("monitorCycle",raiderEsDto.getMonitorCycle());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getRiskSituation())){
            paramMap.put("riskSituation",raiderEsDto.getRiskSituation());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getEventLevel())){
            paramMap.put("eventLevel",raiderEsDto.getEventLevel());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getAssociatedEnterprises())){
            paramMap.put("associatedEnterprises",raiderEsDto.getAssociatedEnterprises());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getRiskChange())){
            paramMap.put("riskChange",raiderEsDto.getRiskChange());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getEventSubType())){
            paramMap.put("eventSubType",raiderEsDto.getEventSubType());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getMonitorId())){
            paramMap.put("monitorId",raiderEsDto.getMonitorId());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getMonitorCycle())){
            paramMap.put("monitorCycle",raiderEsDto.getMonitorCycle());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getTagList())){
            paramMap.put("newsTags",raiderEsDto.getTagList());
        }
        if(CollectionUtils.isNotEmpty(raiderEsDto.getCompanyNames())){
            paramMap.put("companyNames",raiderEsDto.getCompanyNames());
        }

        if(Objects.nonNull(raiderEsDto.getPageNum())){
            paramMap.put("currentPageNo",raiderEsDto.getPageNum());
        }
        if(Objects.nonNull(raiderEsDto.getPageSize())){
            paramMap.put("pageSize",raiderEsDto.getPageSize());
        }


        //日志
        if(Objects.isNull(logId.get())){
            logId.set(IdGenrator.generate());
        }else {
            logId.remove();
            logId.set(IdGenrator.generate());
        }
        Long id = logId.get();
        RiskInfoLog riskInfoLog = new RiskInfoLog()
                .setLogId(id)
                .setVendorId(raiderEsDto.getCompanyId())
                .setVendorCode(raiderEsDto.getCompanyCode())
                .setVendorName(raiderEsDto.getCompanyName())
                .setParameterIn(JSON.toJSONString(paramMap))
                .setInterfaceCode(interfaceCode);

        riskInfoLog.setParameterIn(JSON.toJSONString(paramMap));
        JSONObject jsonObject = null;

        try {
            RiskResponseDTO result = restTemplate.postForObject(uri, paramMap, RiskResponseDTO.class);
            log.info("调用接口{}，入参：{}，返回值：{}",interfaceCode,JSON.toJSONString(paramMap),JSON.toJSONString(result));
            jsonObject = result.getResultData();
//            RiskInfoLog byId = iRiskInfoLogService.getById(8592528882401280l);
//            jsonObject=JSONObject.parseObject(byId.getResultData());
            JSONObject interfaceInfo = result.getInterfaceInfo();

            riskInfoLog.setResultCode(result.getResultCode())
                    .setResultData(JSON.toJSONString(jsonObject))
                    .setResultMsg(result.getResultMsg())
                    .setBillFlag(Objects.isNull(interfaceInfo)?null:interfaceInfo.getJSONObject("billingInfo").getString("billFlag"))
                    .setBillMsg(Objects.isNull(interfaceInfo)?null:interfaceInfo.getJSONObject("billingInfo").getString("billMsg"));
            if(Objects.nonNull(raiderEsDto.getJobLogId())){
                raiderJobLogService.update(null,
                        Wrappers.lambdaUpdate(RaiderJobLogEntity.class)
                                .set(RaiderJobLogEntity::getResultCode,result.getResultCode())
                                .eq(RaiderJobLogEntity::getJobLogId,raiderEsDto.getJobLogId()));
            }
        }finally {
            iRiskInfoLogService.save(riskInfoLog);
            logId.remove();
        }
        return jsonObject;
    }
    @Transactional(rollbackFor = Exception.class)
    public Object getRiskInfoForUpdateRaider2(RaiderEsDto raiderEsDto) throws Exception {
        Assert.notNull(raiderEsDto,"查询公司信息为空");
        String companyName = raiderEsDto.getCompanyName();
        String interfaceCode = raiderEsDto.getInterfaceCode();
        if(StringUtils.isBlank(companyName) || StringUtils.isBlank(interfaceCode)){
            throw new BaseException(LocaleHandler.getLocaleMsg("查询公司名称不能为空"));
        }
        Assert.hasText(companyName, LocaleHandler.getLocaleMsg("查询公司名称不能为空"));
        Assert.hasText(interfaceCode, LocaleHandler.getLocaleMsg("接口编码不能为空"));
        String url = RiskInfoEnum.getValueByCode(interfaceCode);
        Assert.hasText(url, LocaleHandler.getLocaleMsg("接口编码错误"));
        checkInterfaceCount(interfaceCode,raiderEsDto.getCompanyId());

        URI uri = new URI(url);
        HashMap<String, Object> paramMap = new HashMap<>();
        String encrypt = DESUtilForRisk.encrypt(companyName, appKey);
        paramMap.put("userName",userName);
//        paramMap.put("apiKey",apiKeyForLongi); // TODO 云里雾里
        paramMap.put("apiKey",encrypt);
        if(StringUtils.isNotEmpty(raiderEsDto.getEnterpriseName())){
            paramMap.put("enterpriseName", raiderEsDto.getEnterpriseName());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getAccountId())
                || Objects.equals(interfaceCode,"R12")
                || Objects.equals(interfaceCode,"R13")
                || Objects.equals(interfaceCode,"R14")
        ){
            paramMap.put("accountId",accountId);
        }


        if(StringUtils.isNotEmpty(raiderEsDto.getAccountType())){
            paramMap.put("accountType",raiderEsDto.getAccountType());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getEndDate())){
            paramMap.put("endDate",raiderEsDto.getEndDate());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getMonitorCycle())){
            paramMap.put("monitorCycle",raiderEsDto.getMonitorCycle());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getRiskSituation())){
            paramMap.put("riskSituation",raiderEsDto.getRiskSituation());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getEventLevel())){
            paramMap.put("eventLevel",raiderEsDto.getEventLevel());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getAssociatedEnterprises())){
            paramMap.put("associatedEnterprises",raiderEsDto.getAssociatedEnterprises());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getRiskChange())){
            paramMap.put("riskChange",raiderEsDto.getRiskChange());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getEventSubType())){
            paramMap.put("eventSubType",raiderEsDto.getEventSubType());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getMonitorId())){
            paramMap.put("monitorId",raiderEsDto.getMonitorId());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getMonitorCycle())){
            paramMap.put("monitorCycle",raiderEsDto.getMonitorCycle());
        }
        if(StringUtils.isNotEmpty(raiderEsDto.getTagList())){
            paramMap.put("newsTags",raiderEsDto.getTagList());
        }
        if(CollectionUtils.isNotEmpty(raiderEsDto.getCompanyNames())){
            paramMap.put("companyNames",raiderEsDto.getCompanyNames());
        }

        if(Objects.nonNull(raiderEsDto.getPageNum())){
            paramMap.put("currentPageNo",raiderEsDto.getPageNum());
        }
        if(Objects.nonNull(raiderEsDto.getPageSize())){
            paramMap.put("pageSize",raiderEsDto.getPageSize());
        }


        //日志
        if(Objects.isNull(logId.get())){
            logId.set(IdGenrator.generate());
        }else {
            logId.remove();
            logId.set(IdGenrator.generate());
        }
        Long id = logId.get();
        RiskInfoLog riskInfoLog = new RiskInfoLog()
                .setLogId(id)
                .setVendorId(raiderEsDto.getCompanyId())
                .setVendorCode(raiderEsDto.getCompanyCode())
                .setVendorName(raiderEsDto.getCompanyName())
                .setParameterIn(JSON.toJSONString(paramMap))
                .setInterfaceCode(interfaceCode);

        riskInfoLog.setParameterIn(JSON.toJSONString(paramMap));
        Object object = null;

        try {
            RiskResponse2DTO result = restTemplate.postForObject(uri, paramMap, RiskResponse2DTO.class);
            log.info("调用接口{}，入参：{}，返回值：{}",interfaceCode,JSON.toJSONString(paramMap),JSON.toJSONString(result));
            object = result.getResultData();
//            RiskInfoLog byId = iRiskInfoLogService.getById(8592528882401280l);
//            jsonObject=JSONObject.parseObject(byId.getResultData());
            JSONObject interfaceInfo = result.getInterfaceInfo();

            riskInfoLog.setResultCode(result.getResultCode())
                    .setResultData(JSON.toJSONString(object))
                    .setResultMsg(result.getResultMsg())
                    .setBillFlag(Objects.isNull(interfaceInfo)?null:interfaceInfo.getJSONObject("billingInfo").getString("billFlag"))
                    .setBillMsg(Objects.isNull(interfaceInfo)?null:interfaceInfo.getJSONObject("billingInfo").getString("billMsg"));
            if(Objects.nonNull(raiderEsDto.getJobLogId())){
                raiderJobLogService.update(null,
                        Wrappers.lambdaUpdate(RaiderJobLogEntity.class)
                                .set(RaiderJobLogEntity::getResultCode,result.getResultCode())
                                .eq(RaiderJobLogEntity::getJobLogId,raiderEsDto.getJobLogId()));
            }
        }finally {
            iRiskInfoLogService.save(riskInfoLog);
            logId.remove();
        }
        return object;
    }

    @Transactional(rollbackFor = Exception.class)
    public JSONObject getRiskInfoFromMap(RaiderEsDto raiderEsDto,HashMap<String, Object> paramMap) throws Exception {
        Assert.notNull(raiderEsDto,"查询入参为空");
        String companyName = raiderEsDto.getCompanyName();
        String interfaceCode = raiderEsDto.getInterfaceCode();
        if(StringUtils.isBlank(companyName) || StringUtils.isBlank(interfaceCode)){
            throw new BaseException(LocaleHandler.getLocaleMsg("查询公司名称不能为空"));
        }
        Assert.hasText(companyName, LocaleHandler.getLocaleMsg("查询公司名称不能为空"));
        Assert.hasText(interfaceCode, LocaleHandler.getLocaleMsg("接口编码不能为空"));
        String url = RiskInfoEnum.getValueByCode(interfaceCode);
        Assert.hasText(url, LocaleHandler.getLocaleMsg("接口编码错误"));
        checkInterfaceCount(interfaceCode,raiderEsDto.getCompanyId());

        URI uri = new URI(url);
        //日志
        if(Objects.isNull(logId.get())){
            logId.set(IdGenrator.generate());
        }else {
            logId.remove();
            logId.set(IdGenrator.generate());
        }
        Long id = logId.get();
        RiskInfoLog riskInfoLog = new RiskInfoLog()
                .setLogId(id)
                .setVendorId(raiderEsDto.getCompanyId())
                .setVendorCode(raiderEsDto.getCompanyCode())
                .setVendorName(raiderEsDto.getCompanyName())
                .setParameterIn(JSON.toJSONString(paramMap))
                .setInterfaceCode(interfaceCode);

        riskInfoLog.setParameterIn(JSON.toJSONString(paramMap));
        JSONObject jsonObject = null;

        try {
            RiskResponseDTO result = restTemplate.postForObject(uri, paramMap, RiskResponseDTO.class);
            log.info("调用接口{}，入参：{}，返回值：{}",interfaceCode,JSON.toJSONString(paramMap),JSON.toJSONString(result));
            jsonObject = result.getResultData();
            JSONObject interfaceInfo = result.getInterfaceInfo();

            riskInfoLog.setResultCode(result.getResultCode())
                    .setResultData(JSON.toJSONString(jsonObject))
                    .setResultMsg(result.getResultMsg())
                    .setBillFlag(Objects.isNull(interfaceInfo)?null:interfaceInfo.getJSONObject("billingInfo").getString("billFlag"))
                    .setBillMsg(Objects.isNull(interfaceInfo)?null:interfaceInfo.getJSONObject("billingInfo").getString("billMsg"));
            if(Objects.nonNull(raiderEsDto.getJobLogId())){
                raiderJobLogService.update(null,
                        Wrappers.lambdaUpdate(RaiderJobLogEntity.class)
                                .set(RaiderJobLogEntity::getResultCode,result.getResultCode())
                                .eq(RaiderJobLogEntity::getJobLogId,raiderEsDto.getJobLogId()));
            }
        }finally {
            iRiskInfoLogService.save(riskInfoLog);
            logId.remove();
        }
        return jsonObject;
    }

    //调用风险雷达接口，获取数据
    public RiskResponseDTO acquireRaiderData(HashMap<String, Object> paramMap,String interfaceCode) throws URISyntaxException {
        String url = RiskInfoEnum.getValueByCode(interfaceCode);
        URI uri = new URI(url);
        return restTemplate.postForObject(uri, paramMap, RiskResponseDTO.class);
    }

    public void doWriteRaiderLog(String parameterIn,RiskResponseDTO result, RaiderEsDto raiderEsDto){
        JSONObject interfaceInfo = result.getInterfaceInfo();
        RiskInfoLog riskInfoLog = new RiskInfoLog()
                .setLogId(IdGenrator.generate())
                .setVendorId(raiderEsDto.getCompanyId())
                .setVendorCode(raiderEsDto.getCompanyCode())
                .setVendorName(raiderEsDto.getCompanyName())
                .setParameterIn(parameterIn)
                .setInterfaceCode(raiderEsDto.getInterfaceCode())
                .setResultCode(result.getResultCode())
                .setResultData(JSON.toJSONString(result.getResultData()))
                .setResultMsg(result.getResultMsg())
                .setBillFlag(Objects.isNull(interfaceInfo)?null:interfaceInfo.getJSONObject("billingInfo").getString("billFlag"))
                .setBillMsg(Objects.isNull(interfaceInfo)?null:interfaceInfo.getJSONObject("billingInfo").getString("billMsg"));


        iRiskInfoLogService.save(riskInfoLog);
    }

    public static void doWriteRaiderLog1(String parameterIn,RiskResponseDTO result, RaiderEsDto raiderEsDto) {
        JSONObject interfaceInfo = result.getInterfaceInfo();
        System.out.println(Thread.currentThread().getName() + "---" + IdGenrator.generate());
    }

    public IndexResponse doWriteRaiderToEs(RiskResponseDTO result, RaiderEsDto raiderEsDto) {
        log.info("saveToEs,参数：{},{}",raiderEsDto.toString());
        String index = RiskIcToIndexEnum.getValueByCode(raiderEsDto.getInterfaceCode());
        JSONObject riskInfo = result.getResultData();
        if(Objects.isNull(riskInfo)) return null;
        IndexRequest indexRequest = new IndexRequest();
        String id = String.valueOf(raiderEsDto.getCompanyId());
        indexRequest.index(index).type("_doc").id(id).source(JSON.toJSONString(riskInfo), XContentType.JSON);
        try {
            return restHighLevelClient.index(indexRequest, null); // 依赖版本不同，第二个参数暂时传nul
        } catch (IOException e) {
            log.error("风险雷达保存es失败");
            e.printStackTrace();
        }
        return null;
    }

    public boolean isMonitor(CompanyInfo companyInfo){
        try {
            JSONObject riskInfo = this.getRiskInfo(companyInfo, RiskInfoEnum.F2.getCode(), false);
            if(Objects.isNull(riskInfo)) return false;
            if(Objects.equals(riskInfo.getString("monitorFlag"),"1")){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public RiskResponseDTO acquireRaiderDataAndCheck(HashMap<String, Object> paramMap,String interfaceCode,Long companyId){
        checkInterfaceCount(interfaceCode,companyId);
        try {
            return acquireRaiderData(paramMap,interfaceCode);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

}



