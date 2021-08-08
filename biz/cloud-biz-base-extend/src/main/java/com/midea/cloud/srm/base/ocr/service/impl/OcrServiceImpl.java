package com.midea.cloud.srm.base.ocr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.enums.OcrCurrencyType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.Byte2InputStream;
import com.midea.cloud.common.utils.ChineseNumToArabicNumUtil;
import com.midea.cloud.common.utils.OcrPasswordUtils;
import com.midea.cloud.common.utils.idaas.Pbkdf2Encoder;
import com.midea.cloud.common.utils.redis.RedisLockUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.base.config.OcrOuterConfig;
import com.midea.cloud.srm.base.ocr.service.IOcrService;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.ocr.vo.CompanyResultVo;
import com.midea.cloud.srm.model.base.ocr.vo.OcrQueryVo;
import com.midea.cloud.srm.model.base.ocr.vo.OcrResultVo;
import com.midea.cloud.srm.model.base.ocr.vo.ResultVo;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author zhuwl7@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-2 15:05
 *  修改内容:
 * </pre>
 */
@Slf4j
@Service
public class OcrServiceImpl implements IOcrService {
    private static final String TOKENURL = "http://apiprod.midea.com/iam/v1/security/getAccessToken?grantType=client_credential&accessKeyId=%s&timestamp=%s&signature=%s";
    private static final String GRANTTYPE = "client_credential";
    private static final String OCRTOKEN = "ocr_token_value";

    @Value("${cloud.scc.ocr.appId}")
    private String appId;

    @Value("${cloud.scc.ocr.appKey}")
    private String appKey;

    @Value("${cloud.scc.ocr.mipId}")
    private String mipId;

    @Value("${cloud.scc.ocr.url}")
    private String url;

    @Value("${cloud.scc.ocr.checkLicenseOcr}")
    private String checkLicenseOcr;

    @Autowired(required = false)
    private OcrOuterConfig ocrOuterConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private RedisLockUtil lockUtil;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ResultVo checkLicenseFromOcr(byte[] param) {
        String currentTs = String.valueOf(System.currentTimeMillis());
        ResponseEntity<ResultVo> responseEntity = null;
        if (Objects.isNull(ocrOuterConfig)||!ocrOuterConfig.isEnable()) {
            OcrQueryVo vo = new OcrQueryVo();
            vo.setAppId(appId);
            vo.setAppKey(appKey);
            vo.setTs(currentTs);
            vo.setMipId(mipId);
            vo.setImageData_bizLicense(param);
            vo.setSignature(OcrPasswordUtils.MD5Encode(appId + vo.getTs() + appKey));
            responseEntity = restTemplate.postForEntity(url + checkLicenseOcr, vo, ResultVo.class);
        } else {
            String accessToken = getAccessToken(currentTs);
            HttpEntity entity = buildHttpEntity(param, accessToken);
            responseEntity = restTemplate.postForEntity(ocrOuterConfig.getUrl(), entity, ResultVo.class);
        }
        return responseEntity.getBody();
    }


    @Override
    public ResultVo recognizeImage(Long fileuploadId) {
        ResultVo resultVo = new ResultVo();
        try {
            Fileupload fileupload = new Fileupload();
            fileupload.setFileuploadId(fileuploadId);
            Response download = fileCenterClient.downloadFileByParam(fileupload);
            Assert.notNull(download.body(), "附件下载失败");
            InputStream inputStream = download.body().asInputStream();
            byte[] bytes = Byte2InputStream.inputStream2byte(inputStream);
            long startMills = System.currentTimeMillis();
            log.info("开始OCR识别startMills={}", startMills);
            resultVo = this.checkLicenseFromOcr(bytes);
            long endMills = System.currentTimeMillis();
            long costMills = endMills - startMills;
            log.info("结束OCR识别endMills={} 耗时={}ms", endMills, costMills);
            if (!"200".equals(resultVo.getCode())) {
                log.error(resultVo.getMsg());
                throw new BaseException(LocaleHandler.getLocaleMsg("ocr识别报错" + resultVo.getCode()));
            }
        } catch (IOException e) {
            throw new BaseException(e.getMessage());
        }
        return resultVo;
    }

    @Override
    public CompanyResultVo recognizeLcImage(Long fileuploadId) {
        CompanyResultVo companyResultVo = new CompanyResultVo();
        Assert.notNull(fileuploadId, "id不能为空");
        ResultVo resultVo = this.recognizeImage(fileuploadId);
        if (resultVo.getData() != null) {
            companyResultVo = this.change2CompanyResutl(resultVo.getData());
        }
        return companyResultVo;
    }

    private CompanyResultVo change2CompanyResutl(OcrResultVo data) {
        CompanyResultVo companyResultVo = new CompanyResultVo();
        try {
            if (StringUtils.isNotBlank(data.getBiz_license_address())) {
                companyResultVo.setCompanyAddress(data.getBiz_license_address());
            }
            if (StringUtils.isNotBlank(data.getBiz_license_company_name())) {
                companyResultVo.setCompanyName(data.getBiz_license_company_name());
            }
            if (StringUtils.isNotBlank(data.getBiz_license_credit_code())) {
                companyResultVo.setLcCode(data.getBiz_license_credit_code());
            }
            if (StringUtils.isNotBlank(data.getBiz_license_scope())) {
                companyResultVo.setBusinessScope(data.getBiz_license_scope());
            }
            if (StringUtils.isNotBlank(data.getBiz_license_owner_name())) {
                companyResultVo.setLegalPerson(data.getBiz_license_owner_name());
            }

            if (StringUtils.isNotBlank(data.getBiz_license_reg_capital())) {
                BigDecimal regNum = new BigDecimal(0);
                String replace;
                String currency = "";
                if (data.getBiz_license_reg_capital().contains(OcrCurrencyType.USD.getName())) {
                    replace = data.getBiz_license_reg_capital().replace(OcrCurrencyType.USD.getName(), "");
                    regNum = ChineseNumToArabicNumUtil.chinese2Number(replace);
                    currency = OcrCurrencyType.USD.getValue();
                } else if (data.getBiz_license_reg_capital().contains(OcrCurrencyType.CNY.getName())) {
                    replace = data.getBiz_license_reg_capital().replace(OcrCurrencyType.CNY.getName(), "");
                    regNum = ChineseNumToArabicNumUtil.chinese2Number(replace);
                    currency = OcrCurrencyType.CNY.getValue();
                } else if (data.getBiz_license_reg_capital().contains(OcrCurrencyType.HKD.getName())) {
                    replace = data.getBiz_license_reg_capital().replace(OcrCurrencyType.HKD.getName(), "");
                    regNum = ChineseNumToArabicNumUtil.chinese2Number(replace);
                    currency = OcrCurrencyType.HKD.getValue();
                }
                regNum = regNum.divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP);
                companyResultVo.setRegisteredCapital(regNum.toString());
                companyResultVo.setRegistCurrency(currency);
            }
            if (StringUtils.isNotBlank(data.getBiz_license_start_time())) {
                Date creationDate = DateUtils.parseDate(data.getBiz_license_start_time(), new String[]{"yyyy年MM月dd日"});
                if (creationDate != null) {
                    companyResultVo.setCompanyCreationDate(creationDate);
                }
            }
            if (StringUtils.isNotBlank(data.getBiz_license_operating_period())) {
                String operatingPeriod = data.getBiz_license_operating_period();
                Date startDate;
                Date endDate;
                if ("永久".equals(operatingPeriod) ||
                        "长期".equals(operatingPeriod)) {
                    startDate = companyResultVo.getCompanyCreationDate();
                    endDate = DateUtils.parseDate("9999-12-31", new String[]{"yyyy-MM-dd"});
                } else {
                    String period = operatingPeriod.replace("自", "");
                    int num = period.indexOf("至");
                    String startDatePeriod = period.substring(0, num);
                    String endDatePeriod = period.substring(num + 1, period.length());
                    startDate = DateUtils.parseDate(startDatePeriod, new String[]{"yyyy年MM月dd日"});
                    endDate = DateUtils.parseDate(endDatePeriod, new String[]{"yyyy年MM月dd日"});
                }
                if (startDate != null) {
                    companyResultVo.setBusinessStartDate(startDate);
                }
                if (endDate != null) {
                    companyResultVo.setBusinessEndDate(endDate);
                }
            }
        } catch (Exception e) {
            log.info(LocaleHandler.getLocaleMsg("数据转换报错"));
        }
        return companyResultVo;
    }

    @Override
    public void test() {
        try {
            byte[] buffer = null;
            File file = new File("D:\\123.png");
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            buffer = bos.toByteArray();
            ResultVo result = this.checkLicenseFromOcr(buffer);
            System.out.println(result);
            CompanyResultVo companyResultVo = this.change2CompanyResutl(result.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void test2() {
        try {
//            String pp = "{\n" +
//                    "\t\"code\": 200,\n" +
//                    "\t\"msg\": \"success\",\n" +
//                    "\t\"data\": {\n" +
//                    "\t\t\"biz_license_credit_code\": \"91440606729220761M\",\n" +
//                    "\t\t\"biz_license_scope\": \"生产经营磁控管及配套产品。（依法须经批准的项目，经相关佛山市顺德区北滘镇马龙村委会马部门批准后方可开展经营活动。）=\",\n" +
//                    "\t\t\"biz_license_start_time\": \"2001年07月06日\",\n" +
//                    "\t\t\"biz_license_company_type\": \"有限责任公司(台港澳与境内合资)\",\n" +
//                    "\t\t\"biz_license_company_name\": \"广东威特真空电子制造有限公司\",\n" +
//                    "\t\t\"biz_license_serial_number\": \"\",\n" +
//                    "\t\t\"biz_license_owner_name\": \"马赤兵\",\n" +
//                    "\t\t\"biz_license_reg_capital\": \"贰仟伍佰万美元\",\n" +
//                    "\t\t\"type\": \"business_license\",\n" +
//                    "\t\t\"biz_license_paidin_capital\": \"\",\n" +
//                    "\t\t\"biz_license_address\": \"佛山市顺德区北滘镇马龙村委会马村永安路5号\",\n" +
//                    "\t\t\"biz_license_operating_period\": \"2001年07月06日至2021年07月05日\",\n" +
//                    "\t\t\"biz_license_registration_code\": \"\",\n" +
//                    "\t\t\"time_cost\": \"{recognize=736, preprocess=29}\",\n" +
//                    "\t\t\"biz_license_composing_form\": \"\"\n" +
//                    "\t}\n" +
//                    "}";
            //   ResultVo resultVo = JSON.parseObject(pp, ResultVo.class);
            //  System.out.println(resultVo);
            Fileupload fileupload = new Fileupload();
            fileupload.setFileuploadId(6929447641219072L);
            Response download = fileCenterClient.downloadFileByParam(fileupload);
            InputStream inputStream = download.body().asInputStream();
            OutputStream outputStream = new FileOutputStream("D:\\456.png");
            int byteCount = 0;
            byte[] bytes = new byte[1024];
            while ((byteCount = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, byteCount);
                outputStream.flush();
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private HttpEntity buildHttpEntity(byte[] file, String token) {
        JSONObject param = new JSONObject();
        param.put("imageData_bizLicense", file);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-IDaaS-Token", token);
        headers.add("Content-Type", "application/json");
        HttpEntity entity = new HttpEntity(param, headers);
        return entity;
    }

    private String getAccessToken(String currentTs) {
        Object o = redisTemplate.opsForValue().get(OCRTOKEN);
        String accessToken = null;
        if (Objects.isNull(o)) {
            lockUtil.lock(OCRTOKEN, 10);
            try {
                //请求token
                String appId = ocrOuterConfig.getAppId();
                Pbkdf2Encoder encoder = new Pbkdf2Encoder(ocrOuterConfig.getAppKey());
                String signature = encoder.encode(StringUtils.join(GRANTTYPE, appId, currentTs));
                String accessTokenUrl = String.format(TOKENURL, appId, currentTs, signature);
                log.info("GetAccessToken,请求网关入参" + accessTokenUrl);
                String resultStr = restTemplate.getForObject(accessTokenUrl, String.class);
                JSONObject resultObj = JSON.parseObject(resultStr);
                Object code = resultObj.get("code");
                if ("0".equals(code.toString())) {
                    JSONObject result = resultObj.getJSONObject("result");
                    if (!Objects.isNull(result)) {
                        accessToken = result.getString("accessToken");
                        redisTemplate.opsForValue().set(OCRTOKEN, accessToken, 7000, TimeUnit.SECONDS);
                    }
                } else {
                    log.error("请求accessToken出错:{},url:{}", resultStr, accessTokenUrl);
                    throw new BaseException(String.format("请求accessToken出错:%s,url:%s", resultStr, accessTokenUrl));
                }
            } finally {
                lockUtil.unlock(OCRTOKEN);
            }
        } else {
            accessToken = o.toString();
        }
        return accessToken;
    }


}
