package com.midea.cloud.srm.base.soap.erp.service.impl;

import com.alibaba.fastjson.JSON;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.srm.base.busiunit.service.IErpService;
import com.midea.cloud.srm.base.organization.service.IErpBranchBankService;
import com.midea.cloud.srm.base.soap.erp.service.IBranchBankWsService;
import com.midea.cloud.srm.feign.api.ApiClient;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.base.organization.entity.ErpBranchBank;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.BranchBankEntity;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.BranchBankRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 *  （隆基）银行分行WebService接口实现类（Esb总线）
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/14 13:25
 *  修改内容:
 * </pre>
 */
@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.base.soap.erp.service.IBranchBankWsService")
@Component("iBranchBankWsService")
public class BranchBankWsServiceImpl implements IBranchBankWsService {
    
    /**erp总线Service*/
    @Resource
    private IErpService iErpService;
    /**银行分行接口表Service*/
    @Resource
    private IErpBranchBankService iErpBranchBankService;

    @Resource
    private ApiClient apiClient;

    @Override
    public SoapResponse execute(BranchBankRequest request) {
        Long startTime = System.currentTimeMillis();
        SoapResponse response = new SoapResponse();
        /**获取instId和requestTime*/
        EsbInfoRequest esbInfo = request.getEsbInfo();
        String instId = "";
        String requestTime = "";
        if (null != esbInfo) {
            instId = esbInfo.getInstId();
            requestTime = esbInfo.getRequestTime();
        }

        /**获取银行分行List,并保存数据*/
        BranchBankRequest.RequestInfo requestInfo = request.getRequestInfo();
        BranchBankRequest.RequestInfo.BranchBanks branchBanksClass = null;
        List<BranchBankEntity> branchBanksEntityList = null;
        if (null != requestInfo) {
            branchBanksClass = requestInfo.getBranchBanks();
            if (null != branchBanksClass) {
                branchBanksEntityList = branchBanksClass.getBranchBank();
            }
        }
        log.info("获取erp银行分行数据: " + (null != request ? request.toString() : "空"));
        List<ErpBranchBank> erpBranchBanksList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(branchBanksEntityList)) {
            for (BranchBankEntity branchBankEntity : branchBanksEntityList) {
                if (null != branchBankEntity) {
                    ErpBranchBank erpBranchBank = new ErpBranchBank();
                    BeanUtils.copyProperties(branchBankEntity, erpBranchBank);
                    erpBranchBanksList.add(erpBranchBank);
                }
            }
            // 日志收集类
            InterfaceLogDTO interfaceLogDTO = new InterfaceLogDTO();
            interfaceLogDTO.setServiceName("接收银行信息"); // 说明
            interfaceLogDTO.setServiceType("WEBSERVICE"); // 接收方式
            interfaceLogDTO.setType("RECEIVE"); // 类型
            interfaceLogDTO.setDealTime(1L);
            HashMap<String, Object> serviceInfo = new HashMap<>();
            serviceInfo.put("param1",erpBranchBanksList);
            serviceInfo.put("param2",instId);
            serviceInfo.put("param3",requestTime);
            interfaceLogDTO.setServiceInfo(JSON.toJSONString(serviceInfo)); // 入参
            interfaceLogDTO.setCreationDateBegin(new Date()); // 开始时间

            response = iErpService.saveOrUpdateBranchBanks(erpBranchBanksList, instId, requestTime);
            interfaceLogDTO.setCreationDateEnd(new Date()); // 结束时间
            interfaceLogDTO.setReturnInfo(JSON.toJSONString(response)); // 出参

            if(null != response.getResponse().getEsbInfo() && !"S".equals(response.getResponse().getEsbInfo().getReturnStatus())){
                interfaceLogDTO.setStatus("FAIL");
                interfaceLogDTO.setErrorInfo(response.getResponse().getEsbInfo().getReturnMsg());
            }else {
                interfaceLogDTO.setStatus("SUCCESS"); // 状态
                interfaceLogDTO.setFinishDate(new Date()); // 完成时间
            }
            try {
                apiClient.createInterfaceLog(interfaceLogDTO);
            } catch (Exception e) {
                log.error("日志保存失败",e);
            }
        }
        log.info("接收erp银行分行数据用时:"+(System.currentTimeMillis()-startTime)/1000 +"秒");
        return response;
    }
}
