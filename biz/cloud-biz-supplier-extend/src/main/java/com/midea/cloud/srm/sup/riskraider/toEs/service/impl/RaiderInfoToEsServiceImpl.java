package com.midea.cloud.srm.sup.riskraider.toEs.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.constants.DESUtilForRisk;
import com.midea.cloud.common.enums.sup.RiskIcToIndexEnum;
import com.midea.cloud.common.enums.sup.RiskInfoEnum;
import com.midea.cloud.common.utils.NamedThreadFactory;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.riskraider.log.dto.RiskResponseDTO;
import com.midea.cloud.srm.model.supplier.riskraider.log.entity.RaiderJobLogEntity;
import com.midea.cloud.srm.model.supplier.riskraider.r7.dto.R7FinancialDto;
import com.midea.cloud.srm.model.supplier.riskraider.toEs.dto.RaiderEsDto;
import com.midea.cloud.srm.sup.info.service.impl.CompanyInfoServiceImpl;
import com.midea.cloud.srm.sup.riskraider.handle.HandleRaiderInfo;
import com.midea.cloud.srm.sup.riskraider.monitor.service.impl.HaveMonitorServiceImpl;
import com.midea.cloud.srm.sup.riskraider.r2.service.impl.R2RiskInfoServiceImpl;
import com.midea.cloud.srm.sup.riskraider.r7.service.impl.R7FinancialServiceImpl;
import com.midea.cloud.srm.sup.riskraider.r8.service.impl.R8DiscreditServiceImpl;
import com.midea.cloud.srm.sup.riskraider.toEs.service.RaiderInfoToEsService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
*  <pre>
 *  企业财务信息表 服务实现类
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:42:20
 *  修改内容:
 * </pre>
*/
@Service
@Slf4j
public class RaiderInfoToEsServiceImpl implements RaiderInfoToEsService {
    @Autowired
    private HandleRaiderInfo handleRaiderInfo;
    @Autowired
    private CompanyInfoServiceImpl companyInfoService;
    @Resource
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private HaveMonitorServiceImpl haveMonitorService;

    @Autowired
    private R2RiskInfoServiceImpl r2RiskInfoService;
    @Autowired
    private R7FinancialServiceImpl r7FinancialService;
    @Autowired
    private R8DiscreditServiceImpl r8DiscreditService;

//    private final String userName = "LJLNAPI";
//    private final String appKey = "2c90808f763ac4a80176f5ef6b2102f6";
//    private final String accountId = "ff808081704e2ca3017066f7182612e1";

    @Value("${raider.userName}")
    private String userName;
    @Value("${raider.appKey}")
    private String appKey;
    @Value("${raider.accountId}")
    private String accountId;




    private final ThreadPoolExecutor es;

    private final ForkJoinPool calculateThreadPool;


    //创建需求池
    public RaiderInfoToEsServiceImpl() {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        es = new ThreadPoolExecutor(cpuCount * 2 + 1, cpuCount * 2 + 1,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue(),
                new NamedThreadFactory("风险雷达-http-sender", false), new ThreadPoolExecutor.CallerRunsPolicy());
        calculateThreadPool = new ForkJoinPool(cpuCount + 1);
    }


    @Override
    public void saveR1ToEs(RaiderEsDto raiderEsDto) throws Exception {
        raiderEsDto.setInterfaceCode(RiskIcToIndexEnum.R1.getCode());

        HashMap<String, Object> paramMap = new HashMap<>();
        CompanyInfo byCompanyId = companyInfoService.getByCompanyId(raiderEsDto.getCompanyId());
        raiderEsDto.setCompanyName(byCompanyId.getCompanyName());
        raiderEsDto.setCompanyCode(byCompanyId.getCompanyCode());
        raiderEsDto.setKeywords(byCompanyId.getCompanyName());
        String keywords = DESUtilForRisk.encrypt(raiderEsDto.getKeywords(),appKey);
        paramMap.put("userName",userName);
        paramMap.put("keywords",keywords);
        saveToEsfromParamMap(raiderEsDto,paramMap,null);

    }
    @Override
    public void saveR2ToEs(CompanyInfo companyInfo) throws Exception {
        saveToEs(companyInfo,RiskIcToIndexEnum.R2.getCode(),null,true);
    }
    @Override
    public void saveR3ToEs(CompanyInfo companyInfo) throws Exception {
        saveToEs(companyInfo,RiskIcToIndexEnum.R3.getCode());
    }
    @Override
    public void saveR4ToEs(CompanyInfo companyInfo) throws Exception {
        saveToEs(companyInfo,RiskIcToIndexEnum.R4.getCode());
    }
    @Override
    public void saveR5ToEs(CompanyInfo companyInfo) throws Exception {
        saveToEs(companyInfo,RiskIcToIndexEnum.R5.getCode());
    }
    @Override
    public void saveR6ToEs(CompanyInfo companyInfo) throws Exception {
        saveToEs(companyInfo,RiskIcToIndexEnum.R6.getCode());
    }
    @Override
    public void saveR7ToEs(CompanyInfo companyInfo) throws Exception {
        saveToEs(companyInfo,RiskIcToIndexEnum.R7.getCode(),null,false);
    }
    @Override
    public void saveR8ToEs(CompanyInfo companyInfo) throws Exception {
        saveToEs(companyInfo,RiskIcToIndexEnum.R8.getCode(),null,false);
    }

    @Override
    public void saveR9ToEs(CompanyInfo companyInfo) throws Exception {
        saveToEsPage(companyInfo,RiskIcToIndexEnum.R9.getCode(),null,1,15,null);
    }

    @Override
    public void saveR10ToEs(CompanyInfo companyInfo) throws Exception {
        saveToEsPage(companyInfo,RiskIcToIndexEnum.R10.getCode(),null,1,15,"新闻");
    }
    @Override
    public void saveR11ToEs(CompanyInfo companyInfo) throws Exception {
        saveToEs(companyInfo,RiskIcToIndexEnum.R11.getCode());
    }
    @Override
    public void saveR12ToEs(RaiderEsDto raiderEsDto) throws Exception {
        raiderEsDto.setInterfaceCode(RiskIcToIndexEnum.R12.getCode());
        saveToEsForUpdate(raiderEsDto,null);
    }
    @Override
    public void saveR13ToEs(RaiderEsDto raiderEsDto) throws Exception {
        raiderEsDto.setInterfaceCode(RiskIcToIndexEnum.R13.getCode());
        saveToEsForUpdate(raiderEsDto,null);
    }
    @Override
    public void saveR14ToEs(RaiderEsDto raiderEsDto) throws Exception {
        raiderEsDto.setInterfaceCode(RiskIcToIndexEnum.R14.getCode());
        saveToEsForUpdate2(raiderEsDto,null);
    }

    @Override
    public void saveToEs(RaiderEsDto raiderEsDto) throws Exception {
        CompanyInfo companyInfo = new CompanyInfo().setCompanyId(raiderEsDto.getCompanyId());
        saveToEs(companyInfo,raiderEsDto.getInterfaceCode());
    }

    @Override
    public void addMonitorEnterprise(CompanyInfo companyInfo) throws Exception {
        CompanyInfo byCompanyId = companyInfoService.getByCompanyId(companyInfo.getCompanyId());
        companyInfo.setCompanyCode(byCompanyId.getCompanyCode()).setCompanyName(byCompanyId.getCompanyName());
        handleRaiderInfo.getRiskInfo(companyInfo,RiskIcToIndexEnum.F5.getCode(),true);
    }

    @Override
    public void cancelMonitorEnterprise(CompanyInfo companyInfo) throws Exception {
        CompanyInfo byCompanyId = companyInfoService.getByCompanyId(companyInfo.getCompanyId());
        companyInfo.setCompanyCode(byCompanyId.getCompanyCode()).setCompanyName(byCompanyId.getCompanyName());
        handleRaiderInfo.getRiskInfo(companyInfo,RiskIcToIndexEnum.F6.getCode(),true);
    }

    //    @Override
//    public void saveR7ToEs(CompanyInfo companyInfo) throws Exception {
//        log.info("saveOrUpdateR7FromRaider,参数：{}",companyInfo.toString());
//        Objects.nonNull(companyInfo.getCompanyId());
//        companyInfo = companyInfoService.getByCompanyId(companyInfo.getCompanyId());
//        R7FinancialDto financialDto = handleRaiderInfo.getRiskInfo(companyInfo, RiskInfoEnum.R7.getCode(), R7FinancialDto.class, true);
//        IndexRequest indexRequest = new IndexRequest();
//        String id = String.valueOf(companyInfo.getCompanyId());
//        indexRequest.index("raider_r7").type("_doc").id(id).source(JSON.toJSONString(financialDto), XContentType.JSON);
//        IndexResponse response = restHighLevelClient.index(indexRequest);
//    }

    public void  saveToEs(CompanyInfo companyInfo,String interfaceCode) throws Exception {
        saveToEs(companyInfo,interfaceCode,null);
    }

    public <T> void saveToEs(CompanyInfo companyInfo,String interfaceCode,Class<T>clazz) throws Exception {
        log.info("saveToEs,参数：{},{}",companyInfo.toString(),interfaceCode);
        CompanyInfo byCompanyId = companyInfoService.getByCompanyId(companyInfo.getCompanyId());
        companyInfo.setCompanyCode(byCompanyId.getCompanyCode()).setCompanyName(byCompanyId.getCompanyName());
        String index = RiskIcToIndexEnum.getValueByCode(interfaceCode);
        JSONObject riskInfo = handleRaiderInfo.getRiskInfo(companyInfo, interfaceCode);
        if(Objects.isNull(riskInfo)) return;
        IndexRequest indexRequest = new IndexRequest();
        String id = String.valueOf(companyInfo.getCompanyId());
        if(Objects.nonNull(clazz)){
            T source = JSON.toJavaObject(riskInfo,clazz);
            indexRequest.index(index).type("_doc").id(id).source(JSON.toJSONString(source), XContentType.JSON);
        }else {
            indexRequest.index(index).type("_doc").id(id).source(JSON.toJSONString(riskInfo), XContentType.JSON);
        }
        IndexResponse response = restHighLevelClient.index(indexRequest, null); // 依赖版本不同，第二个参数暂时传nul
    }
    public <T> void saveToEs(CompanyInfo companyInfo,String interfaceCode,Class<T>clazz,boolean hasAccountId) throws Exception {
        log.info("saveToEs,参数：{},{}",companyInfo.toString(),interfaceCode);
        CompanyInfo byCompanyId = companyInfoService.getByCompanyId(companyInfo.getCompanyId());
        companyInfo.setCompanyCode(byCompanyId.getCompanyCode()).setCompanyName(byCompanyId.getCompanyName());
        String index = RiskIcToIndexEnum.getValueByCode(interfaceCode);
//        JSONObject riskInfo = null;
//        Thread.sleep(3000);
        JSONObject riskInfo = handleRaiderInfo.getRiskInfo(companyInfo, interfaceCode,hasAccountId);
        if(Objects.isNull(riskInfo)) return;
        IndexRequest indexRequest = new IndexRequest();
        String id = String.valueOf(companyInfo.getCompanyId());
        if(Objects.nonNull(clazz)){
            T source = JSON.toJavaObject(riskInfo,clazz);
            indexRequest.index(index).type("_doc").id(id).source(JSON.toJSONString(source), XContentType.JSON);
        }else {
            indexRequest.index(index).type("_doc").id(id).source(JSON.toJSONString(riskInfo), XContentType.JSON);
        }
        IndexResponse response = restHighLevelClient.index(indexRequest, null); // 依赖版本不同，第二个参数暂时传nul


        log.info("----------"+interfaceCode);

        //R2接口保存或更新mysql数据
        if(Objects.equals(RiskInfoEnum.R2.getCode(),interfaceCode)){
            r2RiskInfoService.saveOrUpdateRiskR2(riskInfo,companyInfo.getCompanyId());
        }
        //R7接口保存或更新mysql数据
        if(Objects.equals(RiskInfoEnum.R7.getCode(),interfaceCode)){
            r7FinancialService.saveOrUpdateRiskR7(riskInfo,companyInfo.getCompanyId());
        }
        //R8接口保存或更新mysql数据
        if(Objects.equals(RiskInfoEnum.R8.getCode(),interfaceCode)){
            r8DiscreditService.saveOrUpdateRiskR8(riskInfo,companyInfo.getCompanyId());
        }


    }

    public <T> void saveToEsfromParamMap(RaiderEsDto raiderEsDto,HashMap<String, Object> paramMap,Class<T>clazz) throws Exception {
        log.info("saveToEs,参数：{}",raiderEsDto);
        String interfaceCode = raiderEsDto.getInterfaceCode();
        CompanyInfo companyInfo = companyInfoService.list(Wrappers.lambdaQuery(CompanyInfo.class).eq(CompanyInfo::getCompanyName,raiderEsDto.getKeywords())).stream().findFirst().orElse(new CompanyInfo());
        raiderEsDto.setCompanyCode(companyInfo.getCompanyCode()).setCompanyId(companyInfo.getCompanyId()).setCompanyName(companyInfo.getCompanyName());
        String index = RiskIcToIndexEnum.getValueByCode(interfaceCode);

//        ConcurrentHashMap<Thread,Long> idMaps = new ConcurrentHashMap<>();

        JSONObject riskInfo = handleRaiderInfo.getRiskInfoFromMap(raiderEsDto, paramMap);

        if(Objects.isNull(riskInfo)) return;
        IndexRequest indexRequest = new IndexRequest();
        String id = String.valueOf(companyInfo.getCompanyId());
        if(Objects.nonNull(clazz)){
            T source = JSON.toJavaObject(riskInfo,clazz);
            indexRequest.index(index).type("_doc").id(id).source(JSON.toJSONString(source), XContentType.JSON);
        }else {
            indexRequest.index(index).type("_doc").id(id).source(JSON.toJSONString(riskInfo), XContentType.JSON);
        }
        IndexResponse response = restHighLevelClient.index(indexRequest, null); // 依赖版本不同，第二个参数暂时传nul
    }


    public <T> void saveToEsPage(CompanyInfo companyInfo,String interfaceCode,Class<T>clazz,Integer pageNo,Integer pageSize,String newsTag) throws Exception {
        log.info("saveToEs,参数：{},{}",companyInfo.toString(),interfaceCode);
        CompanyInfo byCompanyId = companyInfoService.getByCompanyId(companyInfo.getCompanyId());
        companyInfo.setCompanyCode(byCompanyId.getCompanyCode()).setCompanyName(byCompanyId.getCompanyName());
        String index = RiskIcToIndexEnum.getValueByCode(interfaceCode);
        JSONObject riskInfo = handleRaiderInfo.getRiskInfo(companyInfo, interfaceCode,pageNo,pageSize,newsTag);
        if(Objects.isNull(riskInfo)) return;
        IndexRequest indexRequest = new IndexRequest();
        String id = String.valueOf(companyInfo.getCompanyId());
        if(Objects.nonNull(clazz)){
            T source = JSON.toJavaObject(riskInfo,clazz);
            indexRequest.index(index).type("_doc").id(id).source(JSON.toJSONString(source), XContentType.JSON);
        }else {
            indexRequest.index(index).type("_doc").id(id).source(JSON.toJSONString(riskInfo), XContentType.JSON);
        }
        IndexResponse response = restHighLevelClient.index(indexRequest, null); // 依赖版本不同，第二个参数暂时传nul
    }

    public <T> void saveToEsForUpdate(RaiderEsDto raiderEsDto,Class<T>clazz) throws Exception {
        log.info("saveToEs,参数：{},{}",raiderEsDto.toString());
        String interfaceCode = raiderEsDto.getInterfaceCode();
        CompanyInfo companyInfo = companyInfoService.getByCompanyId(raiderEsDto.getCompanyId());
        raiderEsDto.setCompanyName(companyInfo.getCompanyName()).setCompanyCode(companyInfo.getCompanyCode());
        String index = RiskIcToIndexEnum.getValueByCode(interfaceCode);
        JSONObject riskInfo = handleRaiderInfo.getRiskInfoForUpdateRaider(raiderEsDto);
        if(Objects.isNull(riskInfo)) return;
        IndexRequest indexRequest = new IndexRequest();
        String id = String.valueOf(companyInfo.getCompanyId());
        if(Objects.nonNull(clazz)){
            T source = JSON.toJavaObject(riskInfo,clazz);
            indexRequest.index(index).type("_doc").id(id).source(JSON.toJSONString(source), XContentType.JSON);
        }else {
            indexRequest.index(index).type("_doc").id(id).source(JSON.toJSONString(riskInfo), XContentType.JSON);
        }

        IndexResponse response = restHighLevelClient.index(indexRequest, null); // 依赖版本不同，第二个参数暂时传nul
    }

    public <T> void saveToEsForUpdate2(RaiderEsDto raiderEsDto,Class<T>clazz) throws Exception {
        log.info("saveToEs,参数：{},{}",raiderEsDto.toString());
        String interfaceCode = raiderEsDto.getInterfaceCode();
        CompanyInfo companyInfo = companyInfoService.getByCompanyId(raiderEsDto.getCompanyId());
        raiderEsDto.setCompanyName(companyInfo.getCompanyName()).setCompanyCode(companyInfo.getCompanyCode());
        String index = RiskIcToIndexEnum.getValueByCode(interfaceCode);
        Object riskInfo = handleRaiderInfo.getRiskInfoForUpdateRaider2(raiderEsDto);
        if(Objects.isNull(riskInfo)) return;
        IndexRequest indexRequest = new IndexRequest();
        String id = String.valueOf(companyInfo.getCompanyId());
        JSONObject json = new JSONObject();
        json.put("array", riskInfo);
        indexRequest.index(index).type("_doc").id(id).source(JSON.toJSONString(json), XContentType.JSON);


        IndexResponse response = restHighLevelClient.index(indexRequest, null); // 依赖版本不同，第二个参数暂时传nul
    }

    public XContentBuilder a(){
        XContentBuilder builder = null;
        try {
            builder = XContentFactory.jsonBuilder()
                    .startObject().startObject("type").field("date_detection","false")
                    .endObject().endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder;
    }

    public void saveOrUpdateR1(RaiderEsDto raiderEsDto) throws Exception {
        raiderEsDto.setInterfaceCode(RiskIcToIndexEnum.R1.getCode());

        HashMap<String, Object> paramMap = new HashMap<>();
        String keywords = DESUtilForRisk.encrypt(raiderEsDto.getKeywords(),appKey);
        paramMap.put("userName",userName);
        paramMap.put("keywords",keywords);

        JSONObject riskInfo = handleRaiderInfo.getRiskInfoFromMap(raiderEsDto, paramMap);

        saveEs(riskInfo,RiskIcToIndexEnum.R1.getValue(),raiderEsDto.getCompanyId(),R7FinancialDto.class);


    }

    public void saveOrUpdateR2(RaiderEsDto raiderEsDto) throws Exception {
        raiderEsDto.setInterfaceCode(RiskIcToIndexEnum.R2.getCode());

        HashMap<String, Object> paramMap = new HashMap<>();
        buildUserName(paramMap);
        buildEnterpriseName(paramMap,raiderEsDto.getCompanyName());
        buildAccountId(paramMap);

        JSONObject riskInfo = handleRaiderInfo.getRiskInfoFromMap(raiderEsDto, paramMap);

        saveEs(riskInfo,RiskIcToIndexEnum.R1.getValue(),raiderEsDto.getCompanyId(),R7FinancialDto.class);

        r2RiskInfoService.saveOrUpdateRiskR2(riskInfo,raiderEsDto.getCompanyId());


    }

    HashMap<String, Object> buildUserName(HashMap<String, Object> paramMap){
        paramMap.put("userName",userName);
        return paramMap;
    }
    HashMap<String, Object> buildEnterpriseName(HashMap<String, Object> paramMap,String companyName) throws Exception {
        paramMap.put("enterpriseName",DESUtilForRisk.encrypt(companyName,appKey));
        return paramMap;
    }
    HashMap<String, Object> buildAccountId(HashMap<String, Object> paramMap) {
        paramMap.put("accountId",accountId);
        return paramMap;
    }

    public <T> IndexResponse saveEs(JSONObject riskInfo,String index,Long companyId,Class<T>clazz) throws IOException {
        if(Objects.isNull(riskInfo)) return null;
        IndexRequest indexRequest = new IndexRequest();
        String id = String.valueOf(companyId);
        T source = JSON.toJavaObject(riskInfo,clazz);
        indexRequest.index(index).type("_doc").id(id).source(JSON.toJSONString(source), XContentType.JSON);
        return restHighLevelClient.index(indexRequest, null); // 依赖版本不同，第二个参数暂时传nul
    }

    @Override
    public void saveOrUpdateBatch(RaiderEsDto raiderEsDto) throws Exception{
        List<Long> companyIds = raiderEsDto.getCompanyIds();
        List<String> interfaceCodes = raiderEsDto.getInterfaceCodes();
        Assert.isTrue(CollectionUtils.isNotEmpty(companyIds),"请勾选供应商");
        Assert.isTrue(CollectionUtils.isNotEmpty(interfaceCodes),"请选择需要更新的接口");
        ArrayList<Runnable> runnables = new ArrayList<>();
        for (Long companyId : companyIds) {
            for (String interfaceCode : interfaceCodes) {
                runnables.add(buildTask(interfaceCode,companyId,null));
            }
        }
        ArrayList<CompletableFuture<Void>> completableFutures = new ArrayList<>();
        for (Runnable runnable : runnables) {
            completableFutures.add(CompletableFuture.runAsync(runnable, es));
        }
        CompletableFuture[] completableFuturesArr = completableFutures.toArray(new CompletableFuture[completableFutures.size()]);
        CompletableFuture.allOf(completableFuturesArr).join();
    }

    public void saveOrUpdateBatch(List<RaiderJobLogEntity> logEntities,String interfaceCode) throws Exception{
        ArrayList<Runnable> runnables = new ArrayList<>();
        for (RaiderJobLogEntity jobLog : logEntities) {
            runnables.add(buildTask(interfaceCode,jobLog.getVendorId(),jobLog.getJobLogId()));
        }
        ArrayList<CompletableFuture<Void>> completableFutures = new ArrayList<>();
        for (Runnable runnable : runnables) {
            completableFutures.add(CompletableFuture.runAsync(runnable, es));
        }
        CompletableFuture[] completableFuturesArr = completableFutures.toArray(new CompletableFuture[completableFutures.size()]);
        CompletableFuture.allOf(completableFuturesArr).join();
    }

    public Runnable buildTask(String interfaceCode,Long companyId,Long jobLogId){

        switch (interfaceCode){
            case "R1":
                return ()->{
                    try {
                        saveR1ToEs(new RaiderEsDto().setCompanyId(companyId).setJobLogId(jobLogId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            case "R2":
                return ()->{
                    try {
                        saveR2ToEs(new CompanyInfo().setCompanyId(companyId).setJobLogId(jobLogId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            case "R3":
                return ()->{
                    try {
                        saveR3ToEs(new CompanyInfo().setCompanyId(companyId).setJobLogId(jobLogId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            case "R4":
                return ()->{
                    try {
                        saveR4ToEs(new CompanyInfo().setCompanyId(companyId).setJobLogId(jobLogId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            case "R5":
                return ()->{
                    try {
                        saveR5ToEs(new CompanyInfo().setCompanyId(companyId).setJobLogId(jobLogId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            case "R6":
                return ()->{
                    try {
                        saveR6ToEs(new CompanyInfo().setCompanyId(companyId).setJobLogId(jobLogId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            case "R7":
                return ()->{
                    try {
                        saveR7ToEs(new CompanyInfo().setCompanyId(companyId).setJobLogId(jobLogId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            case "R8":
                return ()->{
                    try {
                        saveR8ToEs(new CompanyInfo().setCompanyId(companyId).setJobLogId(jobLogId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            case "R9":
                return ()->{
                    try {
                        saveR9ToEs(new CompanyInfo().setCompanyId(companyId).setJobLogId(jobLogId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            case "R10":
                return ()->{
                    try {
                        saveR10ToEs(new CompanyInfo().setCompanyId(companyId).setJobLogId(jobLogId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            case "R11":
                return ()->{
                    try {
                        saveR11ToEs(new CompanyInfo().setCompanyId(companyId).setJobLogId(jobLogId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            case "R12":
                return ()->{
                    try {
                        saveR12ToEs(new RaiderEsDto().setCompanyId(companyId).setJobLogId(jobLogId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            case "R13":
                return ()->{
                    try {
                        saveR13ToEs(new RaiderEsDto().setCompanyId(companyId).setJobLogId(jobLogId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            case "R14":
                return ()->{
                    try {
                        saveR14ToEs(new RaiderEsDto().setCompanyId(companyId).setJobLogId(jobLogId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            default:
                return null;
        }
    }

    public void saveR1(RaiderEsDto raiderEsDto) throws Exception {
        raiderEsDto.setInterfaceCode(RiskIcToIndexEnum.R1.getCode());
        HashMap<String, Object> paramMap = buildParamMap(raiderEsDto);
        RiskResponseDTO riskResponseDTO = handleRaiderInfo.acquireRaiderDataAndCheck(paramMap, raiderEsDto.getInterfaceCode(), raiderEsDto.getCompanyId());
        saveData(paramMap,riskResponseDTO,raiderEsDto);
    }
    HashMap<String, Object> buildParamMap(RaiderEsDto raiderEsDto) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<>();
        String keywords = DESUtilForRisk.encrypt(raiderEsDto.getKeywords(),appKey);
        paramMap.put("userName",userName);
        paramMap.put("keywords",keywords);
        return paramMap;
    }
    void saveData(HashMap<String, Object> paramMap,RiskResponseDTO riskResponseDTO,RaiderEsDto raiderEsDto) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        //存日志，存es，存mysql
        CompletableFuture.runAsync(()->{
            handleRaiderInfo.doWriteRaiderLog(paramMap.toString(),riskResponseDTO,raiderEsDto);
            countDownLatch.countDown();
        },es);
        CompletableFuture.runAsync(()->{
            handleRaiderInfo.doWriteRaiderToEs(riskResponseDTO,raiderEsDto);
            countDownLatch.countDown();
        },es);
        countDownLatch.await();

    }

    @Override
    public HashMap<String, JSONObject> queryFromEs(Long companyId) throws InterruptedException {
        HashMap<String, JSONObject> map = new HashMap<>();

        CountDownLatch countDownLatch = new CountDownLatch(10);
        CompletableFuture.runAsync(()->{
            String source = queryInfoByCompanyId(companyId, RiskIcToIndexEnum.R1.getValue());
            map.put(RiskIcToIndexEnum.R1.getCode(),JSONObject.parseObject(source));
            countDownLatch.countDown();
        },es);
        CompletableFuture.runAsync(()->{
            String source = queryInfoByCompanyId(companyId, RiskIcToIndexEnum.R2.getValue());
            map.put(RiskIcToIndexEnum.R2.getCode(),JSONObject.parseObject(source));
            countDownLatch.countDown();
        },es);
        CompletableFuture.runAsync(()->{
            String source = queryInfoByCompanyId(companyId, RiskIcToIndexEnum.R3.getValue());
            map.put(RiskIcToIndexEnum.R3.getCode(),JSONObject.parseObject(source));
            countDownLatch.countDown();
        },es);
        CompletableFuture.runAsync(()->{
            String source = queryInfoByCompanyId(companyId, RiskIcToIndexEnum.R4.getValue());
            map.put(RiskIcToIndexEnum.R4.getCode(),JSONObject.parseObject(source));
            countDownLatch.countDown();
        },es);
        CompletableFuture.runAsync(()->{
            String source = queryInfoByCompanyId(companyId, RiskIcToIndexEnum.R5.getValue());
            map.put(RiskIcToIndexEnum.R5.getCode(),JSONObject.parseObject(source));
            countDownLatch.countDown();
        },es);
        CompletableFuture.runAsync(()->{
            String source = queryInfoByCompanyId(companyId, RiskIcToIndexEnum.R6.getValue());
            map.put(RiskIcToIndexEnum.R6.getCode(),JSONObject.parseObject(source));
            countDownLatch.countDown();
        },es);
        CompletableFuture.runAsync(()->{
            String source = queryInfoByCompanyId(companyId, RiskIcToIndexEnum.R7.getValue());
            map.put(RiskIcToIndexEnum.R7.getCode(),JSONObject.parseObject(source));
            countDownLatch.countDown();
        },es);
        CompletableFuture.runAsync(()->{
            String source = queryInfoByCompanyId(companyId, RiskIcToIndexEnum.R8.getValue());
            map.put(RiskIcToIndexEnum.R8.getCode(),JSONObject.parseObject(source));
            countDownLatch.countDown();
        },es);
        CompletableFuture.runAsync(()->{
            String source = queryInfoByCompanyId(companyId, RiskIcToIndexEnum.R9.getValue());
            map.put(RiskIcToIndexEnum.R9.getCode(),JSONObject.parseObject(source));
            countDownLatch.countDown();
        },es);
        CompletableFuture.runAsync(()->{
            String source = queryInfoByCompanyId(companyId, RiskIcToIndexEnum.R10.getValue());
            map.put(RiskIcToIndexEnum.R10.getCode(),JSONObject.parseObject(source));
            countDownLatch.countDown();
        },es);

        countDownLatch.await(10,TimeUnit.SECONDS);

        return map;
    }

    String queryInfoByCompanyId(Long companyId,String index) {
        GetRequest request = new GetRequest(index);
        request.id(String.valueOf(companyId));
        GetResponse response = null;
        try {
            response = restHighLevelClient.get(request, null); // 依赖版本不同，第二个参数暂时传nul
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.getSourceAsString();
    }




    public static void main(String[] args) {
        Runnable runnable = () -> {
            try {
                Thread.sleep(3000);
                System.out.println("sssss");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();

        System.out.println("111");

    }




}

