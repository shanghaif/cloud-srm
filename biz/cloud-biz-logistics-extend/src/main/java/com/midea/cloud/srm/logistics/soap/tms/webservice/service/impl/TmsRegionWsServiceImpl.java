package com.midea.cloud.srm.logistics.soap.tms.webservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.api.ApiClient;
import com.midea.cloud.srm.logistics.expense.service.IRegionService;
import com.midea.cloud.srm.logistics.soap.tms.service.ITmsRegionService;
import com.midea.cloud.srm.logistics.soap.tms.webservice.service.ITmsRegionWsService;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.logistics.expense.entity.Region;
import com.midea.cloud.srm.model.logistics.soap.tms.entity.TmsRegion;
import com.midea.cloud.srm.model.logistics.soap.tms.request.RequestEsbInfo;
import com.midea.cloud.srm.model.logistics.soap.tms.request.TmsRegionEntity;
import com.midea.cloud.srm.model.logistics.soap.tms.request.TmsRegionRequest;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
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
 * TMS 行政区域数据 WebService 接口实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/26 19:25
 *  修改内容:
 * </pre>
 */
@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.logistics.soap.tms.webservice.service.ITmsRegionWsService")
@Component("iTmsRegionWsService")
public class TmsRegionWsServiceImpl implements ITmsRegionWsService {

    /**
     * tms行政区域 接口表Service
     */
    @Resource
    private ITmsRegionService iTmsRegionService;

    /**
     * logistics行政区域 正式表Service
     */
    @Resource
    private IRegionService iRegionService;

    @Autowired
    private ApiClient apiClient;

    @Override
    public SoapResponse execute(TmsRegionRequest request) {

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

        TmsRegionRequest.RequestInfo requestInfo = request.getRequestInfo();
        TmsRegionRequest.RequestInfo.Regions regionsClass = null;
        List<TmsRegionEntity> regionEntityList = null;
        if (null != requestInfo) {
            regionsClass = requestInfo.getRegions();
            if (null != regionsClass) {
                regionEntityList = regionsClass.getRegion();
            }
        }
        // 接口传入的行政区域数据为空时
        if (CollectionUtils.isEmpty(regionEntityList)) {
            response.setSuccess("E");
            responseEsbInfo.setInstId(instId);
            responseEsbInfo.setReturnStatus("E");
            responseEsbInfo.setReturnMsg("传入的行政区域数据为空.");
            responseEsbInfo.setRequestTime(requestTime);
            responseEsbInfo.setResponseTime(requestTime);
            innerResponse.setEsbInfo(responseEsbInfo);
            response.setResponse(innerResponse);
            return response;
        } else {
            log.info("开始获取tms行政区域数据: " + (null != request ? request.toString() : "空"));
            // 保存数据到接口表
            List<TmsRegion> tmsRegionList = convertToTmsRegion(regionEntityList);
            saveTmsProjects(tmsRegionList);

            // 保存数据到正式表
            List<Region> logisticsRegionList = convertToLogisticsRegion(regionEntityList);
            response = saveOrUpdateRegions(logisticsRegionList, instId, requestTime);

            // 保存请求和响应报文到接口日志中心
            saveRequestAndResponseToLog(request, response);

            // 回写接口表数据的状态
            //updateTmsRegions(tmsRegionList);

            log.info("结束获取tms行政区域数据, 用时:" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
            return response;
        }
    }

    /**
     * 保存tms行政区域数据到 行政区域接口表
     *
     * @param TmsRegionList
     */
    private void saveTmsProjects(List<TmsRegion> TmsRegionList) {
        if (CollectionUtils.isNotEmpty(TmsRegionList)) {
            iTmsRegionService.saveBatch(TmsRegionList);
        }
    }

    /**
     * 保存tms行政区域数据到 行政区域正式表
     *
     * @param logisticsRegionList
     * @param instId
     * @param requestTime
     */
    private SoapResponse saveOrUpdateRegions(List<Region> logisticsRegionList, String instId, String requestTime) {
        SoapResponse soapResponse = new SoapResponse();
        SoapResponse.RESPONSE innerResponse = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo responseEsbInfo = new SoapResponse.RESPONSE.EsbInfo();
        String returnMsg = "接收tms系统行政区域数据成功. ";
        int emptyProjectCodeNum = 0;
        int duplicateProjectCodeNum = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 过滤掉行政区域代码为空的数据
        List<Region> filterEmptyRegionCode = logisticsRegionList.stream().filter(x -> StringUtils.isNotEmpty(x.getRegionCode())).collect(Collectors.toList());
        emptyProjectCodeNum = logisticsRegionList.size() - filterEmptyRegionCode.size();
        // 过滤掉行政区域编码重复的数据
        Map<String, Region> map = filterEmptyRegionCode.stream().collect(Collectors.toMap(Region::getRegionCode, Function.identity(), (o1, o2) -> o2));
        duplicateProjectCodeNum = filterEmptyRegionCode.size() - map.size();
        List<Region> logisticsRegions = new ArrayList<>();
        for (Map.Entry<String, Region> m : map.entrySet()) {
            Region value = m.getValue();
            logisticsRegions.add(value);
        }

        // 开始进行校验和保存, 存在即更新, 不存在即新增
        // saveOrUpdatelogisticsRegions(logisticsRegions);

        if (emptyProjectCodeNum > 0) {
            returnMsg += "已忽略行政区域编码为空的数据: " + emptyProjectCodeNum + "条";
        }
        if (duplicateProjectCodeNum > 0) {
            returnMsg += "已忽略行政区域编码重复的数据: " + duplicateProjectCodeNum + "条";
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
     * @param logisticsRegions
     */
    private void saveOrUpdatelogisticsRegions(List<Region> logisticsRegions) {

        // 获取传入数据的行政区域编码集合
        List<String> tmsProjectCodes = logisticsRegions.stream().filter(x -> StringUtils.isNotEmpty(x.getRegionCode())).map(x -> x.getRegionCode()).collect(Collectors.toList());
        Map<String, Region> map = logisticsRegions.stream().collect(Collectors.toMap(Region::getRegionCode, Function.identity()));

        // 根据传入数据的行政区域代码 获取数据库中存在的数据
        List<Region> dbRegionList = iRegionService.list(Wrappers.lambdaQuery(Region.class)
                .in(Region::getRegionCode, tmsProjectCodes)
        );
        Map<String, List<Region>> dbRegionMap = dbRegionList.stream().collect(Collectors.groupingBy(Region::getRegionCode));

        List<Region> savelogisticsRegionList = new ArrayList<>();
        List<Region> updatelogisticsRegionList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(logisticsRegions)) {
            logisticsRegions.forEach(region -> {
                String regionCode = region.getRegionCode();
                if (dbRegionMap.containsKey(regionCode) && CollectionUtils.isNotEmpty(dbRegionMap.get(regionCode))) {
                    region.setRegionId(dbRegionMap.get(regionCode).get(0).getRegionId());
                    updatelogisticsRegionList.add(region);
                } else {
                    savelogisticsRegionList.add(region);
                }
            });
        }

        if (CollectionUtils.isNotEmpty(updatelogisticsRegionList)) {
            log.info("更新tms行政区域数据: " + updatelogisticsRegionList.size() + "条");
            iRegionService.updateBatchById(updatelogisticsRegionList);
        }
        if (CollectionUtils.isNotEmpty(savelogisticsRegionList)) {
            log.info("保存tms行政区域数据: " + savelogisticsRegionList.size() + "条");
            iRegionService.saveBatch(savelogisticsRegionList);
        }
    }

    /**
     * 将报文中的实体tmsProjectEntity转为接口表的实体tmsProject
     *
     * @param regionEntityList
     * @return
     */
    private List<TmsRegion> convertToTmsRegion(List<TmsRegionEntity> regionEntityList) {
        List<TmsRegion> TmsRegionList = new ArrayList<>();

        for (TmsRegionEntity tmsRegionEntity : regionEntityList) {
            if (null != tmsRegionEntity) {
                TmsRegion tmsRegion = new TmsRegion();
                BeanUtils.copyProperties(tmsRegionEntity, tmsRegion);
                tmsRegion.setRegionId(IdGenrator.generate()).setImportStatus(0);
                TmsRegionList.add(tmsRegion);
            }
        }
        return TmsRegionList;
    }

    /**
     * 将报文中的实体tmsProjectEntity转为正式表的实体logisticsRegion
     *
     * @param regionEntityList
     * @return
     */
    private List<Region> convertToLogisticsRegion(List<TmsRegionEntity> regionEntityList) {
        List<Region> logisticsRegionList = new ArrayList<>();

        for (TmsRegionEntity tmsRegionEntity : regionEntityList) {
            if (null != tmsRegionEntity) {
                Region logisticsRegion = new Region();
                BeanUtils.copyProperties(tmsRegionEntity, logisticsRegion);
                logisticsRegion.setRegionId(IdGenrator.generate())
                        .setRegionCode(tmsRegionEntity.getAdminRegionCode())
                        .setRegionName(tmsRegionEntity.getAdminRegionName())
                        .setRegionNameEn(tmsRegionEntity.getAttribute1())
                        .setParentRegionCode(tmsRegionEntity.getAttribute2())
                        .setSourceSystem(tmsRegionEntity.getSourceSysId());
                logisticsRegionList.add(logisticsRegion);
            }
        }
        return logisticsRegionList;
    }

    /**
     * 回写接口表数据状态
     *
     * @param tmsRegions
     */
    private void updateTmsRegions(List<TmsRegion> tmsRegions) {
        tmsRegions.forEach(tmsRegion -> {
            tmsRegion.setImportStatus(1).setImportDate(new Date());
        });
        iTmsRegionService.updateBatchById(tmsRegions);
    }

    /**
     * 保存请求和响应报文到日志中心
     * @param request
     * @param response
     */
    public void saveRequestAndResponseToLog(TmsRegionRequest request, SoapResponse response) {
        log.info("开始保存tms行政区域接收日志...");
        InterfaceLogDTO interfaceLogDTO = new InterfaceLogDTO();
        // 创建开始时间
        interfaceLogDTO.setCreationDateBegin(new Date());
        // 服务名字
        interfaceLogDTO.setServiceName("tms行政区域接收");
        // 请求方式
        interfaceLogDTO.setServiceType("WEBSERVICE");
        // 发送方式
        interfaceLogDTO.setType("RECEIVE");
        // 单据类型
        interfaceLogDTO.setBillType("tms行政区域接收");

        // 请求参数
        try {
            interfaceLogDTO.setServiceInfo(JSON.toJSONString(request));
        } catch (Exception e) {
            log.error("tms行政区域接收数据记录日志报错{}"+e.getMessage());
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
            log.error("保存<tms接收行政区域数据>日志报错{}"+e.getMessage());
        }
        log.info("保存tms行政区域接收日志结束...");
    }

}
