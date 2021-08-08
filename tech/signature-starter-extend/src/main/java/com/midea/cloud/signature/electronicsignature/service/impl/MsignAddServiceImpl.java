package com.midea.cloud.signature.electronicsignature.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.midea.cloud.common.enums.FileUploadType;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.signature.electronicsignature.dto.*;
import com.midea.cloud.signature.electronicsignature.service.IMsignAddService;
import com.midea.cloud.signature.electronicsignature.utils.DateUtil;
import com.midea.cloud.signature.electronicsignature.utils.HttpClientUtil;
import com.midea.cloud.srm.feign.contract.ContractClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.cm.contract.entity.ContractHead;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.signature.SigningParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

@Service
@Slf4j
public class MsignAddServiceImpl implements IMsignAddService {
    protected Logger logger = LoggerFactory.getLogger(MsignAddServiceImpl.class);
    @Value("${cloud.scc.url}")
    private String MSIGN_URL;

    @Value("${cloud.scc.appId}")
    private String MSIGN_APPID;

    @Value("${cloud.scc.appKey}")
    private String MSIGN_APPKEY;

    @Value("${cloud.scc.addSigning}")
    private String MSIGN_ADDSIGNING;

    @Value("${cloud.scc.contractUrl}")
    private String MSIGN_CONTRACT_URL;

    @Value("${cloud.scc.srmSourceUrl}")
    private String SRM_SOURCE_URL;

    @Value("${cloud.scc.srmDownloadUrl}")
    private String SRM_DOWNLOAD_URL;

    @Resource
    private ContractClient contractClient;

    @Resource
    private FileCenterClient fileCenterClient;

    public static final String contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";


    @Override
    public void callBack(String request) throws Exception {
        try {
            log.info("-----------------------------------智汇签回调接口开始----------------------------------");
            log.info("智汇签回调接口{}参数: "+request);
            MsignRequest<SrmSysMsignCallBackDto> callBackDtoMsignRequest = formateMsignRequest(request);
            Assert.notNull(callBackDtoMsignRequest,"参数不能为空");
            log.error("参数不能为空");
            SrmSysMsignCallBackDto data = callBackDtoMsignRequest.getData();
            String receiptId = data.getReceiptId();
            Assert.notNull(receiptId,"单据ID不能为空");
            // 工作流ID
            String flowId = data.getFlowId();
            // 合同编号
            String contractNumber = data.getContractNumber();
            // 合同文件
            List<SrmSysMsignCallBackConDto> contractList = data.getContractList();
            Assert.notNull(contractList,"合同文件列表为空");
            String signAttchmentId = contractList.get(0).getSignAttchmentId();
            Assert.notNull(signAttchmentId,"参数signAttchmentId为空");
            // 获取文件下载流
            byte[] result = downLoadContractBySignAttchmentId(signAttchmentId);
            // 上传文件到服务器
            InputStream inputStream = new ByteArrayInputStream(result);
//            file.getName(); file.getOriginalFilename(); file.getContentType();
            MultipartFile file = new MockMultipartFile("file", "合同文件.pdf" ,contentType, inputStream);
            Fileupload fileupload = fileCenterClient.feignAnonClientUpload(file, "WEB_APP", FileUploadType.FASTDFS.name(), "signature", "callBack", "images");
            // 文件ID
            Long fileuploadId = fileupload.getFileuploadId();
            log.info("上传文件返回下载ID:"+fileuploadId);
            log.info("-----------------------------------智汇签回调接口结束----------------------------------");

            // 更新合同数据
            ContractHead contractHead = new ContractHead();
            contractHead.setContractHeadId(Long.parseLong(receiptId));
            contractHead.setStampContractFileuploadId(fileuploadId);
            contractClient.signingCallback(contractHead);

        } catch (Exception e) {
            log.error("智汇签回调报错:"+e.getMessage());
            log.error("智汇签回调报错:"+e);
            throw e;
        }
    }

    /**
     * 解析智慧签回调的请求参数
     * @param request
     * @return
     */
    private MsignRequest<SrmSysMsignCallBackDto> formateMsignRequest(String request) {
        JSONObject jsonObject = JSONObject.parseObject(request);//JSON字符串转成JSONObject
        MsignRequest<SrmSysMsignCallBackDto> msignCallBackRequest = JSONObject.toJavaObject(jsonObject, MsignRequest.class);//实体类封装
        JSONObject dataObject = jsonObject.getJSONObject("data");//获取智慧签的data
        SrmSysMsignCallBackDto srmSysMsignCallBackDto = JSONObject.toJavaObject(dataObject, SrmSysMsignCallBackDto.class);//封装成回调实体类
        //解析回调的合同详细信息
        if(dataObject.containsKey("contractList")) {
            JSONArray jsonArray = dataObject.getJSONArray("contractList");
            List<SrmSysMsignCallBackConDto> srmSysMsignCallBackConDtoList = jsonArray.toJavaList(SrmSysMsignCallBackConDto.class);//封装实体类
            srmSysMsignCallBackDto.setContractList(srmSysMsignCallBackConDtoList);
        }
        msignCallBackRequest.setData(srmSysMsignCallBackDto);
        return msignCallBackRequest;
    }

    @Override
    public Map<String, Object> addSigningDemo(SigningParam signingParam) throws Exception {

        //构造新增智汇签接口参数示例
        SrmSysMsignAddDto srmSysMsignAddDto = new SrmSysMsignAddDto();

        //*****************************data业务数据********************************//
        //设置外部单据id
        srmSysMsignAddDto.setReceiptId(signingParam.getReceiptId());
        //设置外部流程id（可与外部单据相同），为了保障每次调用智汇签接口不重复，deamo是用时间戳
        srmSysMsignAddDto.setFlowId(signingParam.getReceiptId());
        //设置合同编号
        srmSysMsignAddDto.setContractNumber(signingParam.getContractCode());
        //设置客户签署合同名称
        srmSysMsignAddDto.setContractName(signingParam.getContractName());
        //设置发起日期（yyyy-MM-dd）
        srmSysMsignAddDto.setLaunchTime(DateUtil.dateToStr(new Date(), DateUtil.DATE_FORMAT_DEFAULT));
        //设置数据源，来自哪个系统数据
        srmSysMsignAddDto.setSource("MCSRM");
        //设置我方签署信息
        srmSysMsignAddDto.setMyBusinessList(new ArrayList<>());
        //设置他方签署信息
        srmSysMsignAddDto.setOtherBusinessList(new ArrayList<>());
        //设置签署合同详细信息
        srmSysMsignAddDto.setContractList(new ArrayList<>());

        //*****************************myBusinessList业务数据********************************//
        srmSysMsignAddDto.getMyBusinessList().add(new SrmSysMsignAddMyDto());
        //设置我方企业名称（或个人姓名）
        srmSysMsignAddDto.getMyBusinessList().get(0).setBusinessName("测试电子签章甲方有限公司-武汉工厂-内部");
        //设置我方企业统一社会信用代码（或个人证件号）
        srmSysMsignAddDto.getMyBusinessList().get(0).setBusinessId("91420100758179913E");
        //设置对公或对私0  1  默认0（对公“0”，对私“1”，目前暂时不支持对私）
        srmSysMsignAddDto.getMyBusinessList().get(0).setPublicOrPrivate("0");

        //*****************************otherBusinessList业务数据********************************//
        srmSysMsignAddDto.getOtherBusinessList().add(new SrmSysMsignAddOtherDto());
        //设置对公或对私，0-公；1-私
        srmSysMsignAddDto.getOtherBusinessList().get(0).setPublicOrPrivate("0");

        // TODO<-----------------------------------------设置他方信息------------------------------------------------>
        //设置他方企业名称（publicOrPrivate对公必填，对私不用填）
        srmSysMsignAddDto.getOtherBusinessList().get(0).setOtherBusinessName("测试电子签章乙方有限公司-材料");
        //设置他方企业统一社会信用代码（publicOrPrivate对公必填，对私不用填）
        srmSysMsignAddDto.getOtherBusinessList().get(0).setOtherBusinessId("91440101799407185N");
        //设置他方签约人姓名（必填)
        srmSysMsignAddDto.getOtherBusinessList().get(0).setOtherName(StringUtil.notEmpty(signingParam.getName())?signingParam.getName():"王培任");//请注意保护个人隐私
        //设置他方签约人证件号
//        srmSysMsignAddDto.getOtherBusinessList().get(0).setOtherId("440982199506214556");//请注意保护个人隐私 换成真实身份证号码
        //设置他方签约人手机号（必填）
        srmSysMsignAddDto.getOtherBusinessList().get(0).setOtherPhone(StringUtil.notEmpty(signingParam.getPhone())?signingParam.getPhone():"18825108436");//请注意保护个人隐私 换成手机号码
        //设置邮箱
        srmSysMsignAddDto.getOtherBusinessList().get(0).setOtherEmail(StringUtil.notEmpty(signingParam.getEmail())?signingParam.getEmail():"wangpr@meicloud.com"); //1036709220@qq.com

        //*****************************contractList业务数据********************************//
        srmSysMsignAddDto.getContractList().add(new SrmSysMsignAddContractDto());

        // TODO<-----------------------------------------设置我方下载地址--------------------------------------------->
        //设置下载地址（可以提供统一的地址或者每次不一样的地址，需要提供）
        srmSysMsignAddDto.getContractList().get(0).setUrl(SRM_SOURCE_URL+SRM_DOWNLOAD_URL+signingParam.getFileuploadId());
//        srmSysMsignAddDto.getContractList().get(0).setUrl("http://gsrmsit.midea.com/srmpos/common/downloadFile?docId=175bfa9d43086beaf03af1a444798c80");
//        srmSysMsignAddDto.getContractList().get(0).setUrl("http://10.74.73.28:61000/migndemo/feignPermit/msign/downLoadContract");//本机地址，需要关闭防火墙、改IP


        //设置合同文件名称
        srmSysMsignAddDto.getContractList().get(0).setFileName(signingParam.getContractName());
        //设置合同原件文件编号
        srmSysMsignAddDto.getContractList().get(0).setFileNumber(signingParam.getContractCode());
        //设置固化后附件ID（可以同合同文件编号）
        srmSysMsignAddDto.getContractList().get(0).setAttchmentId(signingParam.getReceiptId());
        //设置签署方详细信息
        srmSysMsignAddDto.getContractList().get(0).setContractInfoList(new ArrayList<>());

        //*****************************contractInfoList业务数据********************************//
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().add(new SrmSysMsignAddConInfoDto());

        // TODO<----------------------------------------设置采购方信息----------------------------------------------->
        //设置签署方企业证件号（或个人）
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(0).setSignatoryInfoId("91420100758179913E"); //企业统一信用代码 必须真实有效的
        //设置签署方企业名称（或个人）
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(0).setSignatoryInfoName("测试电子签章甲方有限公司-武汉工厂-内部"); //企业名称 必须真实有效的


        //设置签章类型(Single单页签章、Multi多页签章、Edges骑缝章、Key关键字签章、Auto最后一页自动盖章）
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(0).setSignType("Key");
//        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(0).setSignType("Multi");
        //设置对公或对私，0-公；1-私
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(0).setPublicOrPrivate("0");
        //设置签章位置信息
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(0).setSignPos(new SrmSysMsignAddSignPosDto());

        //*****************************signPos业务数据********************************//
        //设置定位类型，0-坐标定位，1-关键字定位，默认0，若选择关键字定位，签署类型(signType)必须指定为关键字签署才会生效
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(0).getSignPos().setPosType("1");
//        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(0).getSignPos().setPosType("0");
        //设置 签署页码，若为多页签章，支持页码格式“1-3,5,8“，若为坐标定位时，不可空
//        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(0).getSignPos().setPosPage(StringUtil.notEmpty(signingParam.getPosPage())?signingParam.getPosPage():"6");
        //设置签署位置X坐标，若为关键字定位，相对于关键字的X坐标偏移量，默认0
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(0).getSignPos().setPosX(30D);
//        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(0).getSignPos().setPosX(StringUtil.notEmpty(signingParam.getX1())?signingParam.getX1():40D);
        //设置签署位置Y坐标，若为关键字定位，相对于关键字的Y坐标偏移量，默认0
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(0).getSignPos().setPosY(-10D);
//        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(0).getSignPos().setPosY(StringUtil.notEmpty(signingParam.getY())?signingParam.getY():600D);
        //设置关键字，仅限关键字签章时有效，若为关键字定位时，不可空
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(0).getSignPos().setKey("甲方（章）");
        //设置印章展现宽度
//        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(0).getSignPos().setWidth(300D);


        //*****************************contractInfoList业务数据********************************//
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().add(new SrmSysMsignAddConInfoDto());
        //设置签署方企业证件号（或个人）
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(1).setSignatoryInfoId("91440101799407185N");
        //设置签署方企业名称（或个人）
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(1).setSignatoryInfoName("测试电子签章乙方有限公司-材料");
        //设置签章类型(Single单页签章、Multi多页签章、Edges骑缝章、Key关键字签章、Auto最后一页自动盖章）
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(1).setSignType("Key");
//        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(1).setSignType("Multi");
        //设置对公或对私，0-公；1-私
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(1).setPublicOrPrivate("0");
        //设置签章位置信息
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(1).setSignPos(new SrmSysMsignAddSignPosDto());

        //*****************************signPos业务数据********************************//
        //设置定位类型，0-坐标定位，1-关键字定位，默认0，若选择关键字定位，签署类型(signType)必须指定为关键字签署才会生效
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(1).getSignPos().setPosType("1");
//        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(1).getSignPos().setPosType("0");
        //设置 签署页码，若为多页签章，支持页码格式“1-3,5,8“，若为坐标定位时，不可空
//        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(1).getSignPos().setPosPage(StringUtil.notEmpty(signingParam.getPosPage())?signingParam.getPosPage():"6");
        //设置签署位置X坐标，若为关键字定位，相对于关键字的X坐标偏移量，默认0
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(1).getSignPos().setPosX(30D);
//        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(1).getSignPos().setPosX(StringUtil.notEmpty(signingParam.getX2())?signingParam.getX2():300D);
        //设置签署位置Y坐标，若为关键字定位，相对于关键字的Y坐标偏移量，默认0
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(1).getSignPos().setPosY(-10D);
//        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(1).getSignPos().setPosY(StringUtil.notEmpty(signingParam.getY())?signingParam.getY():600D);
        //设置关键字，仅限关键字签章时有效，若为关键字定位时，不可空
        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(1).getSignPos().setKey("乙方（章）");
        //设置印章展现宽度
//        srmSysMsignAddDto.getContractList().get(0).getContractInfoList().get(1).getSignPos().setWidth(300D);

        //*********************以上请求报文参数已经构造完成*************************************************
        System.out.println("请求新增智汇签合同接口开始");
        String request = JSON.toJSONString(getMsignRequestParams(getMsignRequest(srmSysMsignAddDto)));
        System.out.println("请求智汇签接口参数：" + request);
        Map<String, Object> response = postAddSigning(srmSysMsignAddDto);
        System.out.println("请求智汇签接口结束");

        Map<String, Object> result = new HashMap<>();
        result.put("requestParams", request);
        result.put("responseReturn", response);
        return result;
    }

    /**
     * 请求智汇签接口
     * @param srmSysMsignAddDto
     * @return
     * @throws Exception
     */
    private Map<String, Object> postAddSigning(SrmSysMsignAddDto srmSysMsignAddDto){
        try {
            //智汇签请求参数
            MsignRequest msignRequest = getMsignRequest(srmSysMsignAddDto);
            String responsStr = HttpClientUtil.httpPostRequest(getMsignUrl(MSIGN_ADDSIGNING), getMsignRequestHeader(), getMsignRequestParams(msignRequest));
            logger.info("请求智汇签接口-MSIGN_ADDSIGNING-返回信息：" + responsStr);
            Map<String, Object> response = JSON.parseObject(responsStr);
            return response;
        } catch (Exception e) {
            logger.error("postAddSigning Exception", e);
        }
        return null;
    }

    //智汇签请求头
    private Map<String, Object> getMsignRequestHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        return header;
    }

    /**
     * 获取请求参数
     * @param msignRequest
     * @return
     */
    private Map<String, Object> getMsignRequestParams(MsignRequest<?> msignRequest) {
        Map<String, Object> request = new HashMap<>();
        request.put("request", JSON.toJSONString(msignRequest));
        return request;
    }

    //智汇签请求URL
    private String getMsignUrl(String serviceName) {
        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(MSIGN_URL).append(serviceName);
        return urlBuffer.toString();
    }

    private <T> MsignRequest<?> getMsignRequest(T data) {
        MsignRequest msignRequest = new MsignRequest(MSIGN_APPID, MSIGN_APPKEY);
        msignRequest.setData(data);
        return msignRequest;
    }

    @Override
    public byte[] downLoadContractBySignAttchmentId(String signAttchmentId) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("signAttchmentId", signAttchmentId);
        //构造请求参数
        MsignRequest mapMsignRequest =getMsignRequest(data);
        String responsStr = HttpClientUtil.httpPostRequest(getMsignUrl(MSIGN_CONTRACT_URL), getMsignRequestHeader(), getMsignRequestParams(mapMsignRequest));
        logger.info("请求智汇签接口-MSIGN_CONTRACT_URL-返回信息：" + responsStr);

        JSONObject jsonObject = JSONObject.parseObject(responsStr);
        JSONObject dataJsonObject = jsonObject.getJSONObject("data");
        if(dataJsonObject != null) {
            String stream = (String) dataJsonObject.get("stream");
            byte[] bytes = Base64.getDecoder().decode(stream);
            return bytes;
        }
        return new byte[0];
    }

    @Override
    public void downLoadFileBySignAttchmentId(String signAttchmentId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = null;
        try {
            byte[] result = downLoadContractBySignAttchmentId(signAttchmentId);
            String fileName = "SRM新增智慧签合同DEMO";
            if(!fileName.contains("\\.")) {
                fileName = fileName + ".pdf";
            }
            String userAgent = request.getHeader("user-agent").toLowerCase();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            if (userAgent.contains("MSIE") || userAgent.contains("msie") || userAgent.contains("trident")
                    || userAgent.contains("Trident")) {
                response.setHeader("content-disposition",
                        "attachment;filename="
                                + URLEncoder.encode(fileName, "UTF-8")
                                .replaceAll("\\+", "%20"));
            } else if (userAgent.contains("firefox")) {
                response.setHeader("content-disposition", "attachment;filename=" + new String(
                        ("\"" + fileName + "\"").getBytes("UTF-8"),
                        "ISO-8859-1"));
            } else {
                response.setHeader("content-disposition", "attachment;filename=" + new String(
                        ("\"" + fileName + "\"").getBytes("UTF-8"),
                        "ISO-8859-1"));
            }
            outputStream = response.getOutputStream();
            outputStream.write(result);
        } catch (Exception e) {
            logger.error("downLoadContractBySignAttchmentId Exception", e);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
