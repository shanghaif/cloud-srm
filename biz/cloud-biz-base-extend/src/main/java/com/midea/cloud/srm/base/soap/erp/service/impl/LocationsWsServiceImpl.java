package com.midea.cloud.srm.base.soap.erp.service.impl;

import com.midea.cloud.srm.base.busiunit.service.IErpService;
import com.midea.cloud.srm.base.organization.service.ILocationService;
import com.midea.cloud.srm.base.soap.erp.service.ILocationsWsService;
import com.midea.cloud.srm.model.base.organization.entity.Location;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.LocationsEntity;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.LocationsRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * ERP接口实现WebService
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/21 19:25
 *  修改内容:
 * </pre>
 */
@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.base.soap.erp.service.ILocationsWsService")
@Component("iLocationsWsService")
public class LocationsWsServiceImpl implements ILocationsWsService {

    /**erp总线Service*/
    @Resource
    private IErpService iErpService;
    /**地址接口表Service*/
    @Resource
    private ILocationService iLocationService;

    @Override
    public SoapResponse execute(LocationsRequest request) {
        Long startTime = System.currentTimeMillis();
        SoapResponse response = new SoapResponse();
        /**获取instId和requestTime*/
        EsbInfoRequest esbInfo = request.getEsbInfo();
        String instId = "";
        String requestTime = "";
        if(null != esbInfo){
            instId = esbInfo.getInstId();
            requestTime = esbInfo.getRequestTime();
        }

        /**获取地址接口List,并保存数据*/
        LocationsRequest.RequestInfo requestInfo = request.getRequestInfo();
        LocationsRequest.RequestInfo.Locations locationsClass = null;
        List<LocationsEntity> locationsEntityList = null;
        if(null != requestInfo){
            locationsClass = requestInfo.getLocations();
            if(null != locationsClass){
                locationsEntityList = locationsClass.getLocation();
            }
        }
        log.info("erp获取地址接口数据: " + (null != request ? request.toString() : "空"));
        List<Location> locationsList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(locationsEntityList)){
            for(LocationsEntity locationEntity : locationsEntityList){
                if (null != locationEntity) {
                    Location location = new Location();
                    BeanUtils.copyProperties(locationEntity, location);
                    location.setLocationId(StringUtils.isNotBlank(locationEntity.getLocationId())?
                            Long.parseLong(locationEntity.getLocationId()): null);
                    location.setOrganizationId(StringUtils.isNotBlank(locationEntity.getOrganizationId()) ?
                            Long.parseLong(locationEntity.getLocationId()) : null);
                    location.setShipToLocationId(StringUtils.isNotBlank(locationEntity.getShipToLocationId()) ?
                            Long.parseLong(locationEntity.getShipToLocationId()) : null);
                    locationsList.add(location);
                }
            }
            response = iErpService.saveOrUpdateLocations(locationsList, instId, requestTime);
        }
        log.info("erp获取地址接口插入数据用时:"+(System.currentTimeMillis()-startTime)/1000 +"秒");
        return response;
    }
}
