package com.midea.cloud.srm.base.demo.controller;

import com.alibaba.fastjson.JSON;
import com.midea.cloud.common.annotation.ApiLog;
import com.midea.cloud.common.annotation.CacheClear;
import com.midea.cloud.common.annotation.CacheData;
import com.midea.cloud.common.annotation.CheckParam;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.srm.base.demo.dto.ExcelImportData;
import com.midea.cloud.srm.base.demo.service.WprDemoService;
import com.midea.cloud.srm.base.demo.service.impl.LogWriteBackImpl;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.rediscache.entity.RedisCacheMan;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/wprDemoController")
@Slf4j
public class WprDemoController {

    @Resource
    private WprDemoService wprDemoService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private BaseClient baseClient;
    @Resource
    private LogWriteBackImpl logWriteBack;
    /**
     * 无参数缓存接口示例
     * @return
     */
    @RequestMapping("/noParamCacheApi")
    public Object noParamCacheApi(){
        return wprDemoService.noParamCacheApi();
    }

    /**
     * 无参数缓存接口示例
     * @return
     */
    @RequestMapping("/paramCacheApi")
    public Object paramCacheApi(@RequestParam("param1") String param1,
                                @RequestParam("param2") String param2,
                                @RequestParam("param3") String param3){
        return wprDemoService.paramCacheApi(param1, param2, param3);
    }

    @RequestMapping("/getRedisList")
    @CacheData(keyName = "WPR",interfaceName = "测试")
    public List<String> getRedisList(String param) {
        return Arrays.asList("wpr1","wpr2","wpr3");
    }

    @RequestMapping("/getRedisMap")
    @CacheData(keyName = "WPR_WPR",interfaceName = "测试")
    public Map<String,Object> getRedisMap(String param) throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("wpr1","测试1");
        map.put("wpr2","测试2");
        map.put("wpr3","测试3");
        return map;
    }

    @RequestMapping("/cacheClearWPR_WPR")
    @CacheClear(keyName = "WPR_WPR")
    public void cacheClearWPR_WPR(){

    }

    @RequestMapping("/cacheClearWPR")
    @CacheClear(keyName = "WPR")
    public void cacheClearWPR(){

    }

    @RequestMapping("/testNoNullCheck")
    @CheckParam
    public RedisCacheMan testNoNullCheck(@RequestBody(required = false) RedisCacheMan redisCacheMan){
        return redisCacheMan;
    }

    @RequestMapping("/testNoNullCheck1")
    public void testNoNullCheck1(){
        wprDemoService.testNoNullCheck1(null);
    }

    @RequestMapping("/testNoNullCheck2")
    public void testNoNullCheck2(){
        wprDemoService.testNoNullCheck2(new User(),null);
    }

    @RequestMapping("/testNoNullCheck3")
    public void testNoNullCheck3(){
        wprDemoService.testNoNullCheck3(new User(),null);
    }

    @RequestMapping("/importExcelData")
    public List<ExcelImportData> importExcelData(@RequestParam("file") MultipartFile file){
        List<ExcelImportData> excelImportData = EasyExcelUtil.readExcelWithModel(file, ExcelImportData.class);
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        EasyExcelUtil.checkParamNoNullAndDic(excelImportData, errorFlag);
        return excelImportData;
    }

    @RequestMapping("/testEasyExcelDicByCode")
    public void testEasyExcelDicByCode(String dicCodes){
        Map<String, String> dicCodeName1 = EasyExcelUtil.getDicCodeName(dicCodes,baseClient);
        log.info(JSON.toJSONString(dicCodeName1));
        Map<String, String> dicCodeName2 = EasyExcelUtil.getDicCodeName(dicCodes);
        log.info(JSON.toJSONString(dicCodeName2));
        Map<String, String> dicCodeName3 = EasyExcelUtil.getDicNameCode(dicCodes,baseClient);
        log.info(JSON.toJSONString(dicCodeName3));
        Map<String, String> dicCodeName4 = EasyExcelUtil.getDicNameCode(dicCodes);
        log.info(JSON.toJSONString(dicCodeName4));
    }

    @RequestMapping("/testEasyExcelDicByCodes")
    public void testEasyExcelDicByCodes(@RequestBody List<String> dicCodes){
        Map<String, Map<String, String>> dicCodeNameByCodes = EasyExcelUtil.getDicCodeNameByCodes(dicCodes);
        log.info(JSON.toJSONString(dicCodeNameByCodes));
        Map<String, Map<String, String>> dicNameCodeByCodes = EasyExcelUtil.getDicNameCodeByCodes(dicCodes);
        log.info(JSON.toJSONString(dicNameCodeByCodes));
    }

    @RequestMapping("/testEasyExcelCurrency")
    public void testEasyExcelCurrency(){
        Map<String, String> currencyCodeName1 = EasyExcelUtil.getCurrencyCodeName();
        log.info(JSON.toJSONString(currencyCodeName1));
        Map<String, String> currencyCodeName2 = EasyExcelUtil.getCurrencyCodeName(baseClient);
        log.info(JSON.toJSONString(currencyCodeName2));

        Map<String, String> currencyCodeName3 = EasyExcelUtil.getCurrencyNameCode();
        log.info(JSON.toJSONString(currencyCodeName3));
        Map<String, String> currencyCodeName4 = EasyExcelUtil.getCurrencyNameCode(baseClient);
        log.info(JSON.toJSONString(currencyCodeName4));
    }

    @RequestMapping("/testApiLog")
    @ApiLog(serviceName = "测试Api日志Aop",aClass = LogWriteBackImpl.class,billType = "测试",targetSys = "测试")
    public Map<String,Object> testApiLog(@RequestBody Map<String,Object> param){
        return param;
    }

    @RequestMapping("/testApiLogException")
    @ApiLog(serviceName = "测试Api日志Aop",aClass = LogWriteBackImpl.class,billType = "测试",targetSys = "测试")
    public Map<String,Object> testApiLogException(@RequestBody Map<String,Object> param){
        throw new BaseException("我是抛出异常!!!");
    }

    @RequestMapping("/testLoadMethonAnotation")
    public void testLoadMethonAnotation(){
        logWriteBack.testLoadMethonAnotation();
    }

}

