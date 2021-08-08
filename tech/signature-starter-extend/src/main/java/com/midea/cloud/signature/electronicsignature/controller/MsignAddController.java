package com.midea.cloud.signature.electronicsignature.controller;

import com.alibaba.fastjson.JSON;
import com.midea.cloud.signature.electronicsignature.service.IMsignAddService;
import com.midea.cloud.srm.feign.api.ApiClient;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.signature.SigningParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/feignPermit/msign")
public class MsignAddController {
    protected Logger logger = LoggerFactory.getLogger(MsignAddController.class);
    @Autowired
    private IMsignAddService msignAddService;
    @Resource
    private ApiClient apiClient;

    /**
     * 智慧签回调接口，提供给智慧签调用该接口，将签署成功的合同文件ID返回调用方
     * @param request
     * @return
     */
    @PostMapping(value = "/callBack" )
    public void callBack(@RequestParam String request) throws Exception {
        InterfaceLogDTO interfaceLogDTO = new InterfaceLogDTO();
        try {
            interfaceLogDTO.setServiceInfo(request);
            interfaceLogDTO.setCreationDateBegin(new Date());
            interfaceLogDTO.setServiceName("智慧签回调接口");
            interfaceLogDTO.setServiceType("HTTP");
            interfaceLogDTO.setType("RECEIVE");
            msignAddService.callBack(request);
            interfaceLogDTO.setStatus("SUCCESS");
            interfaceLogDTO.setCreationDateEnd(new Date());
        } catch (Exception e) {
            // 堆栈错误信息
            String stackTrace = Arrays.toString(e.getStackTrace());
            // 错误信息
            String message = e.getMessage();
            ConcurrentHashMap<String, String> errorMsg = new ConcurrentHashMap<>();
            errorMsg.put("message", e.getClass().getName() + ": " + message);
            errorMsg.put("stackTrace", stackTrace);
            interfaceLogDTO.setErrorInfo(JSON.toJSONString(errorMsg));
            interfaceLogDTO.setStatus("FAIL");
            interfaceLogDTO.setCreationDateEnd(new Date());
            throw e;
        }finally {
            apiClient.createInterfaceLogAnon(interfaceLogDTO);
        }

    }

    /**
     * 新增智慧签合同演示接口
     * @return
     */
    @PostMapping(value = "/addSigning")
    public Map<String,Object> addSigning(@RequestBody SigningParam signingParam) throws Exception {
        return msignAddService.addSigningDemo(signingParam);
    }

    /**
     * 根据签章系统返回的id下载pdf
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/downLoadContractBySignAttchmentId")
    public void downLoadContractBySignAttchmentId(@RequestParam("signAttchmentId") String signAttchmentId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        msignAddService.downLoadFileBySignAttchmentId(signAttchmentId, request, response);
    }

    /**
     * 测试接口是否调通
     * @return
     */
    @GetMapping(value = "/helloWorld")
    public String helloWorld(){
        return "helloWorld";
    }

}
