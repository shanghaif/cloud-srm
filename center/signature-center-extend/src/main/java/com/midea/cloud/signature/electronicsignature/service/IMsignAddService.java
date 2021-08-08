package com.midea.cloud.signature.electronicsignature.service;


import com.midea.cloud.srm.model.signature.SigningParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface IMsignAddService {


    /**
     * 智慧签回调接口
     * @param request
     * @return
     * @throws Exception
     */
    void callBack(String request) throws Exception;

    /**
     *
     * @return
     * @throws Exception
     */
    Map<String, Object> addSigningDemo(SigningParam signingParam) throws Exception;

    /**
     * 异步下载签署完成的合同文件
     * @param signAttchmentId
     * @return
     * @throws Exception
     */
    byte[] downLoadContractBySignAttchmentId(String signAttchmentId) throws Exception;

    void downLoadFileBySignAttchmentId(String signAttchmentId, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
