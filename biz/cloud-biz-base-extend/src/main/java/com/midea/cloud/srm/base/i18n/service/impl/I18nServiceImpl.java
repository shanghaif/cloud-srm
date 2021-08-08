package com.midea.cloud.srm.base.i18n.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONArray;
import com.midea.cloud.common.constants.SysConstant;
import com.midea.cloud.common.enums.InvokeType;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.common.utils.ZipUtil;
import com.midea.cloud.component.config.RibbonTemplateWrapper;
import com.midea.cloud.component.filter.HttpServletHolder;
import com.midea.cloud.srm.base.i18n.service.I18nService;
import com.midea.cloud.srm.model.base.i18n.LanguageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *  国际化 服务实现类
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-03-15 15:54:01
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class I18nServiceImpl implements I18nService {

    private String[] serviceIds = {
            "cloud-biz-base",
            "cloud-biz-bidding",
            "cloud-biz-bargaining",
            "cloud-biz-contract",
            "cloud-biz-price",
            "cloud-biz-gateway",
            "cloud-biz-report",
            "cloud-biz-supplier",
            "cloud-biz-logistics",
            "cloud-biz-performance",
            "cloud-biz-supplier-cooperate",
            "log-center",
            "file-center",
            "oauth-center",
            "api-center",
            "rbac-center"
    };

    @Autowired
    RibbonTemplateWrapper ribbonTemplate;

    @Override
    public void downloadI18nExcel() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(SysConstant.INVOKE_TYPE, InvokeType.INTERAL_INVOKE.getCode());
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ExcelWriter excelWriter = EasyExcel.write(HttpServletHolder.getResponse().getOutputStream()).build();
        EasyExcelUtil.getServletOutputStream(HttpServletHolder.getResponse(), "多语言翻译导出");
        int i = 0;
        for (String serviceId : serviceIds) {
            WriteSheet writeSheet = EasyExcel.writerSheet(i++, serviceId).head(LanguageInfo.class).build();
            ResponseEntity<String> propStr = null;
            try {
                propStr = ribbonTemplate.postForEntity("http://" + (serviceId.equals("cloud-biz-gateway") ? "cloud-biz-gateway/cloud-srm" : serviceId) + "/job-anon/internal/language/getLanguages", httpEntity, String.class);
            } catch (Exception ex) {
                log.error("获取服务{}国际化信息失败", serviceId);
            }
            if (propStr != null && propStr.getBody() != null) {
                List<LinkedHashMap> properties = JSONArray.parseArray(propStr.getBody(), LinkedHashMap.class);
                // 要导出的数据
                List<LanguageInfo> LanguageInfos = getLanguageInfos(properties);
                excelWriter.write(LanguageInfos, writeSheet);
            }
        }
        excelWriter.finish();
    }

    @Override
    public void convertI18nProp(MultipartFile i18nExcel) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        EasyExcel.read(i18nExcel.getInputStream(), LanguageInfo.class, new AnalysisEventListener<LanguageInfo>() {
            List<LanguageInfo> sheetData = new ArrayList<>();

            @Override
            public void invoke(LanguageInfo languageInfo, AnalysisContext analysisContext) {
                sheetData.add(languageInfo);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                String serviceId = analysisContext.readSheetHolder().getSheetName();
                HttpHeaders headers = new HttpHeaders();
                headers.add(SysConstant.INVOKE_TYPE, InvokeType.INTERAL_INVOKE.getCode());
                HttpEntity<String> httpEntity = new HttpEntity<>(headers);
                ResponseEntity<String> propStr = null;
                try {
                    propStr = ribbonTemplate.postForEntity("http://" + (serviceId.equals("cloud-biz-gateway") ? "cloud-biz-gateway/cloud-srm" : serviceId) + "/job-anon/internal/language/getLanguageFiles", httpEntity, String.class);
                } catch (Exception ex) {
                    log.error("获取服务{}国际化文件信息失败", serviceId);
                }
                if (propStr != null && propStr.getBody() != null) {
                    List<String> i18nFiles = JSONArray.parseArray(propStr.getBody(), String.class);

                    Map<String, InputStream> lanFiles = new HashMap<>();
                    List<File> lanFileList = new ArrayList<>();
                    try {
                        for (int i = 0; i < 3; i++) {
                            String lanBase64File = i18nFiles.get(i);
                            byte[] lanByte = decodeI18nFile(decoder, lanBase64File);
                            lanBase64File = buildLanFile(lanByte, sheetData, i);
                            File tempLanFile = createTempLanFile(serviceId, lanBase64File);
                            lanFileList.add(tempLanFile);
                            String fileName = i == 0 ? "messages_zh_CN" : i == 1 ? "messages_en_US" : "messages_ja_JP";
                            appendZipFile(lanFiles, serviceId, fileName, tempLanFile);
                        }
                        ZipUtil.toZip(lanFiles, HttpServletHolder.getResponse().getOutputStream());
                        for (File lanFile : lanFileList) {
                            delTempLanFile(lanFile);
                        }
                    } catch (Exception e) {
                        log.error("解析服务{}国际化文件信息失败", serviceId);
                    }
                }
                sheetData = new ArrayList<>(); // 重置
            }
        }).head(LanguageInfo.class).doReadAll();
    }

    private void appendZipFile(Map<String, InputStream> lanFiles, String serviceId, String fileName, File tempLanFile) throws IOException {
        lanFiles.put(serviceId + "/" + fileName + ".properties", new FileInputStream(tempLanFile));
    }

    private File createTempLanFile(String fileName, String lanFile) throws IOException {
        File tempLanFile = File.createTempFile(fileName, ".properties");
        FileWriter fileWriter = new FileWriter(tempLanFile);
        fileWriter.write(lanFile);
        fileWriter.close();
        return tempLanFile;
    }

    private void delTempLanFile(File tempLanFile) throws IOException {
        tempLanFile.deleteOnExit();
    }

    private String buildLanFile(byte[] lanFileByte, List<LanguageInfo> sheetData, int mode) throws UnsupportedEncodingException {
        String lanFile = new String(lanFileByte, "UTF-8");

        int preEndLocation = 0;
        for (LanguageInfo languageInfo : sheetData) {
            String key = languageInfo.getKey();
            String val = null;
            switch (mode) {
                case 0:
                    val = languageInfo.getZh_value();
                    break;
                case 1:
                    val = languageInfo.getEn_value();
                    break;
                default:
                    val = languageInfo.getJa_value();
            }
            if (val == null) {
                val = "";
            }
            String reg = "((?<=\\s)?)(" + key + ")(=?)((\\u0020|\\u3000|\\S)*)";
            Matcher matcher = Pattern.compile(reg).matcher(lanFile);
            if (matcher.find()) {
                String blankStr = matcher.group(1);
                String eqStr = matcher.group(3);
                if (blankStr == null) {
                    blankStr = "\n";
                }
                if (eqStr == null) {
                    eqStr = "=";
                }
                preEndLocation = matcher.end();
                lanFile = matcher.replaceAll(blankStr + key + eqStr + val);
            } else {
                if (preEndLocation == 0) {
                    val = val + "\n";
                } else {
                    key = "\n" + key;
                }
                lanFile = lanFile.substring(0, preEndLocation) + key + "=" + val + lanFile.substring(preEndLocation, lanFile.length());
            }
        }
        return lanFile;
    }


    private byte[] decodeI18nFile(BASE64Decoder decoder, String i18nFile) throws Exception {
        if (i18nFile != null) {
            return decoder.decodeBuffer(new ByteArrayInputStream(i18nFile.getBytes("UTF-8")));
        }
        return null;
    }

    private void injectLineMap(Map<String, List<Integer>> lineMap, byte[] i18nByte) throws IOException {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(i18nByte), "UTF-8"));
        String currentLine = null;
        int currentLineNum = 0;
        while ((currentLine = fileReader.readLine()) != null) {
            List<Integer> currentLineList = lineMap.get(currentLine);
            if (currentLineList == null) {
                currentLineList = new ArrayList<>();
                lineMap.put(currentLine.startsWith("#") ? currentLine : currentLine.split("=")[0], currentLineList);
            }
            currentLineList.add(++currentLineNum);
        }
    }

    private List<LanguageInfo> getLanguageInfos(List<LinkedHashMap> properties) {
        Map<String, LanguageInfo> lans = new LinkedHashMap<>();
        int currentLan = 0;
        for (LinkedHashMap prop : properties) {
            for (Object key : prop.keySet()) {
                LanguageInfo languageInfo = lans.get(key);
                if (languageInfo == null) {
                    languageInfo = new LanguageInfo();
                    languageInfo.setKey(String.valueOf(key));
                    lans.put(String.valueOf(key), languageInfo);
                }
                switch (currentLan) {
                    case 0:
                        languageInfo.setZh_value(String.valueOf(prop.get(key)));
                        break;
                    case 1:
                        languageInfo.setEn_value(String.valueOf(prop.get(key)));
                        break;
                    default:
                        languageInfo.setJa_value(String.valueOf(prop.get(key)));
                }
            }
            currentLan++;
        }
        return new ArrayList<>(lans.values());
    }
}
