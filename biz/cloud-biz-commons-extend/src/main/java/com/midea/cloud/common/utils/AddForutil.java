package com.midea.cloud.common.utils;

import com.midea.cloud.srm.feign.api.ApiClient;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;

import java.util.Date;

public  class AddForutil {
    /**
     * 保存请求和响应报文到日志中心
     * @param request
     */
    public static void saveRequestAndResponseToLog(String request, String response, String name, ApiClient apiClient,Boolean falg) throws Exception {
        String s="SUCCESS";
        InterfaceLogDTO interfaceLogDTO = new InterfaceLogDTO();
        // 创建开始时间
        interfaceLogDTO.setCreationDateBegin(new Date());
        // 服务名字
        interfaceLogDTO.setServiceName(name);
        // 请求方式
        interfaceLogDTO.setServiceType("WEBSERVICE");
        // 发送方式
        interfaceLogDTO.setType("RECEIVE");
        // 单据类型
        interfaceLogDTO.setBillType(name);
        // 请求参数
        interfaceLogDTO.setServiceInfo(request);
        // 报文id
        interfaceLogDTO.setBillId(String.valueOf(IdGenrator.generate()));
        // 响应信息
        interfaceLogDTO.setReturnInfo(response);
        // 状态
        if (!falg){
            s="FAIL";
        }
        interfaceLogDTO.setStatus(s);
        interfaceLogDTO.setTargetSys("SRM");
        // 完成时间
        interfaceLogDTO.setFinishDate(new Date());
        apiClient.createInterfaceLogForAnon(interfaceLogDTO);
    }
}
