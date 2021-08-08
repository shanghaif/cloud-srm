package com.midea.cloud.srm.logistics.soap.tms.webservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.logistics.LogisticsStatus;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.api.ApiClient;
import com.midea.cloud.srm.logistics.expense.service.IPortService;
import com.midea.cloud.srm.logistics.soap.tms.service.ITmsPortService;
import com.midea.cloud.srm.logistics.soap.tms.webservice.service.ITmsPortWsService;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.logistics.expense.entity.LogisticsProject;
import com.midea.cloud.srm.model.logistics.expense.entity.Port;
import com.midea.cloud.srm.model.logistics.soap.tms.entity.TmsPort;
import com.midea.cloud.srm.model.logistics.soap.tms.entity.TmsProject;
import com.midea.cloud.srm.model.logistics.soap.tms.request.RequestEsbInfo;
import com.midea.cloud.srm.model.logistics.soap.tms.request.TmsPortEntity;
import com.midea.cloud.srm.model.logistics.soap.tms.request.TmsPortRequest;
import com.midea.cloud.srm.model.logistics.soap.tms.request.TmsProjectRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 * TMS 港口数据 WebService 接口实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/26
 *  修改内容:
 * </pre>
 */
@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.logistics.soap.tms.webservice.service.ITmsPortWsService")
@Component("iTmsPortWsService")
public class TmsPortWsServiceImpl implements ITmsPortWsService {

    /**
     * tms港口 接口表Service
     */
    @Resource
    private ITmsPortService iTmsPortService;

    /**
     * logistics港口 正式表Service
     */
    @Resource
    private IPortService iPortService;

    @Autowired
    private ApiClient apiClient;

    @Override
    public SoapResponse execute(TmsPortRequest request) {

        Long startTime = System.currentTimeMillis();

        SoapResponse response = new SoapResponse();
        SoapResponse.RESPONSE innerResponse = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo responseEsbInfo = new SoapResponse.RESPONSE.EsbInfo();

        RequestEsbInfo esbInfo = request.getEsbInfo();
        String instId = "";
        String requestTime = "";
        if (null != esbInfo) {
            instId = esbInfo.getInstId();
            requestTime = esbInfo.getRequestTime();
        }

        TmsPortRequest.RequestInfo requestInfo = request.getRequestInfo();
        TmsPortRequest.RequestInfo.Ports portsClass = null;
        List<TmsPortEntity> portEntityList = null;
        if (null != requestInfo) {
            portsClass = requestInfo.getPorts();
            if (null != portsClass) {
                portEntityList = portsClass.getPort();
            }
        }
        // 接口传入的港口数据为空时
        if (CollectionUtils.isEmpty(portEntityList)) {
            response.setSuccess("E");
            responseEsbInfo.setInstId(instId);
            responseEsbInfo.setReturnStatus("E");
            responseEsbInfo.setReturnMsg("传入的港口数据为空.");
            responseEsbInfo.setRequestTime(requestTime);
            responseEsbInfo.setResponseTime(requestTime);
            innerResponse.setEsbInfo(responseEsbInfo);
            response.setResponse(innerResponse);
            return response;
        } else {
            log.info("开始获取tms港口数据: " + (null != request ? request.toString() : "空"));
            // 保存数据到接口表
            List<TmsPort> tmsPortList = convertToTmsPort(portEntityList);
            saveTmsPorts(tmsPortList);

            // 保存数据到正式表
            List<Port> portList = convertToLogisticsPort(portEntityList);
            response = saveOrUpdatePorts(portList, instId, requestTime);

            // 保存请求和响应报文到接口日志中心
            saveRequestAndResponseToLog(request, response);

            // 回写接口表数据的状态
            updateTmsPorts(tmsPortList);
            log.info("结束获取tms港口数据, 用时:" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
            return response;
        }
    }

    /**
     * 保存数据到 正式表
     *
     * @param portList
     * @param instId
     * @param requestTime
     */
    private SoapResponse saveOrUpdatePorts(List<Port> portList, String instId, String requestTime) {
        SoapResponse soapResponse = new SoapResponse();
        SoapResponse.RESPONSE innerResponse = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo responseEsbInfo = new SoapResponse.RESPONSE.EsbInfo();
        String returnMsg = "接收tms系统港口数据成功. ";
        int emptyPortCodeOrPortTypeNum = 0;
        int duplicatePortCodeAndPortTypeNum = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 过滤掉 港口代码或港口类型 为空的数据
        List<Port> filterEmptyPortCodeOrPortType = portList.stream()
                .filter(x -> (StringUtils.isNotEmpty(x.getPortCode()) && StringUtils.isNotEmpty(x.getPortType())))
                .collect(Collectors.toList());
        emptyPortCodeOrPortTypeNum = portList.size() - filterEmptyPortCodeOrPortType.size();
        // 过滤掉港口代码和港口类型组合重复的数据
        Map<String, Port> map = filterEmptyPortCodeOrPortType.stream()
                .collect(Collectors.toMap(k -> k.getPortCode().trim() + "-" + k.getPortType().trim(), Function.identity(), (o1, o2) -> o2));
        duplicatePortCodeAndPortTypeNum = filterEmptyPortCodeOrPortType.size() - map.size();
        List<Port> logisticsPorts = new ArrayList<>();
        for (Map.Entry<String, Port> m : map.entrySet()) {
            Port value = m.getValue();
            logisticsPorts.add(value);
        }

        // 开始根据组合属性判断, 存在即更新, 不存在即新增
        saveOrUpdateLogisticsPorts(logisticsPorts);

        if (emptyPortCodeOrPortTypeNum > 0) {
            returnMsg += "已忽略港口代码为空的数据: " + emptyPortCodeOrPortTypeNum + "条";
        }
        if (duplicatePortCodeAndPortTypeNum > 0) {
            returnMsg += "已忽略港口代码和港口类型组合重复的数据: " + duplicatePortCodeAndPortTypeNum + "条";
        }

        soapResponse.setSuccess("S");
        responseEsbInfo.setInstId(instId);
        responseEsbInfo.setReturnStatus("S");
        responseEsbInfo.setReturnMsg(returnMsg);
        responseEsbInfo.setRequestTime(requestTime);

        responseEsbInfo.setResponseTime(simpleDateFormat.format(new Date()));
        innerResponse.setEsbInfo(responseEsbInfo);
        soapResponse.setResponse(innerResponse);

        return soapResponse;
    }

    /**
     * 保存或更新数据到正式表
     *
     * @param logisticsPorts
     */
    private void saveOrUpdateLogisticsPorts(List<Port> logisticsPorts) {

        // 获取传入数据的港口代码集合
        List<String> tmsPortCodes = logisticsPorts.stream().map(x -> x.getPortCode()).collect(Collectors.toList());
        // 获取传入数据的港口类型集合
        List<String> tmsPortTypes = logisticsPorts.stream().map(x -> x.getPortType()).collect(Collectors.toList());
        Map<String, Port> map = logisticsPorts.stream().collect(Collectors.toMap(x -> x.getPortCode() + "-" + x.getPortType(), Function.identity()));

        // 根据传入数据的港口代码和港口类型 获取在数据库中存在的数据
        List<Port> dbPortList = iPortService.list(Wrappers.lambdaQuery(Port.class)
                .in(Port::getPortCode, tmsPortCodes)
                .in(Port::getPortType, tmsPortTypes)
        );
        Map<String, Map<String, List<Port>>> dbPortMap = dbPortList.stream().collect(Collectors.groupingBy(Port::getPortType, Collectors.groupingBy(Port::getPortCode)));

        List<Port> saveLogisticsPortList = new ArrayList<>();
        List<Port> updateLogisticsPortList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(logisticsPorts)) {
            logisticsPorts.forEach(port -> {
                String portType = port.getPortType();
                String portCode = port.getPortCode();
                // map二级判断
                if (dbPortMap.containsKey(portType) && dbPortMap.get(portType).containsKey(portCode)
                        && CollectionUtils.isNotEmpty(dbPortMap.get(portType).get(portCode))) {
                    port.setPortId(dbPortMap.get(portType).get(portCode).get(0).getPortId());
                    updateLogisticsPortList.add(port);
                }else {
                    saveLogisticsPortList.add(port);
                }
            });
        }

        if (CollectionUtils.isNotEmpty(updateLogisticsPortList)) {
            log.info("更新tms港口数据: " + updateLogisticsPortList.size() + "条");
            iPortService.updateBatchById(updateLogisticsPortList);
        }
        if (CollectionUtils.isNotEmpty(saveLogisticsPortList)) {
            log.info("保存tms港口数据: " + saveLogisticsPortList.size() + "条");
            iPortService.saveBatch(saveLogisticsPortList);
        }
    }

    /**
     * 保存数据到 接口表
     *
     * @param tmsPortList
     */
    private void saveTmsPorts(List<TmsPort> tmsPortList) {
        if (CollectionUtils.isNotEmpty(tmsPortList)) {
            iTmsPortService.saveBatch(tmsPortList);
        }
    }

    /**
     * 将报文中的实体tmsPortEntity转为接口表的实体tmsPort
     *
     * @param tmsPortEntityList
     */
    private List<TmsPort> convertToTmsPort(List<TmsPortEntity> tmsPortEntityList) {
        List<TmsPort> tmsPortList = new ArrayList<>();

        for (TmsPortEntity tmsPortEntity : tmsPortEntityList) {
            if (null != tmsPortEntity) {
                TmsPort tmsPort = new TmsPort();
                BeanUtils.copyProperties(tmsPortEntity, tmsPort);
                tmsPort.setPortId(IdGenrator.generate()).setImportStatus(0);
                tmsPortList.add(tmsPort);
            }
        }
        return tmsPortList;
    }

    /**
     * 将报文中的实体tmsPortEntity转为正式表的实体tmsPort
     *
     * @param tmsPortEntityList
     */
    private List<Port> convertToLogisticsPort(List<TmsPortEntity> tmsPortEntityList) {
        List<Port> logisticsPortList = new ArrayList<>();

        for (TmsPortEntity tmsPortEntity : tmsPortEntityList) {
            if (null != tmsPortEntity) {
                Port port = new Port();
                BeanUtils.copyProperties(tmsPortEntity, port);
                if (YesOrNo.YES.getValue().equals(tmsPortEntity.getStatus())) {
                    port.setStatus(LogisticsStatus.EFFECTIVE.getValue());
                } else {
                    port.setStatus(LogisticsStatus.INEFFECTIVE.getValue());
                }
                port.setPortId(IdGenrator.generate());
                logisticsPortList.add(port);
            }
        }
        return logisticsPortList;
    }

    /**
     * 回写接口表数据状态
     * @param tmsPorts
     */
    private void updateTmsPorts(List<TmsPort> tmsPorts) {
        tmsPorts.forEach(tmsPort -> {
            tmsPort.setImportStatus(1).setImportDate(new Date());
        });
        iTmsPortService.updateBatchById(tmsPorts);
    }

    /**
     * 保存请求和响应报文到日志中心
     * @param request
     * @param response
     */
    public void saveRequestAndResponseToLog(TmsPortRequest request, SoapResponse response) {
        log.info("开始保存tms港口接收日志...");
        InterfaceLogDTO interfaceLogDTO = new InterfaceLogDTO();
        // 创建开始时间
        interfaceLogDTO.setCreationDateBegin(new Date());
        // 服务名字
        interfaceLogDTO.setServiceName("tms港口接收");
        // 请求方式
        interfaceLogDTO.setServiceType("WEBSERVICE");
        // 发送方式
        interfaceLogDTO.setType("RECEIVE");
        // 单据类型
        interfaceLogDTO.setBillType("tms港口接收");

        // 请求参数
        try {
            interfaceLogDTO.setServiceInfo(JSON.toJSONString(request));
        } catch (Exception e) {
            log.error("tms港口接收数据记录日志报错{}"+e.getMessage());
        }
        // 报文id
        String instId = request.getEsbInfo().getInstId();
        interfaceLogDTO.setBillId(instId);

        // 响应信息
        interfaceLogDTO.setReturnInfo(JSON.toJSONString(response));

        // 状态
        if (response.getResponse().getEsbInfo().getReturnStatus().equals("S")) {
            interfaceLogDTO.setStatus("SUCCESS");
        } else {
            interfaceLogDTO.setErrorInfo(response.getResponse().getEsbInfo().getReturnMsg());
            interfaceLogDTO.setStatus("FAIL");
        }

        interfaceLogDTO.setTargetSys("SRM");
        // 完成时间
        interfaceLogDTO.setFinishDate(new Date());

        try {
            apiClient.createInterfaceLogForAnon(interfaceLogDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存<tms接收港口数据>日志报错{}"+e.getMessage());
        }
        log.info("保存tms港口接收日志结束...");
    }

}
