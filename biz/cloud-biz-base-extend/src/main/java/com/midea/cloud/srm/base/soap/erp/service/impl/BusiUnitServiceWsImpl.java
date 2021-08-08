package com.midea.cloud.srm.base.soap.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.constants.BaseConst;
import com.midea.cloud.common.enums.soap.InterfaceResourceEnum;
import com.midea.cloud.common.enums.soap.InterfaceStatusEnum;
import com.midea.cloud.srm.base.busiunit.service.IBusiUnitService;
import com.midea.cloud.srm.base.organization.service.ILocationService;
import com.midea.cloud.srm.base.organization.service.IOrganizationService;
import com.midea.cloud.srm.base.organization.service.IOrganizationsTypeService;
import com.midea.cloud.srm.base.soap.erp.service.IBusiUnitWsService;
import com.midea.cloud.srm.model.base.organization.entity.BusinessUnits;
import com.midea.cloud.srm.model.base.organization.entity.Location;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationsType;
import com.midea.cloud.srm.model.base.soap.DataSourceEnum;
import com.midea.cloud.srm.model.base.soap.erp.dto.REQUEST;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
@WebService(targetNamespace = "http://www.aurora-framework.org/schema", endpointInterface = "com.midea.cloud.srm.base.soap.erp.service.IBusiUnitWsService")
@Component("busiUnitWsService")
public class BusiUnitServiceWsImpl implements IBusiUnitWsService {

    /**erp总线业务实体Service*/
    @Resource
    private IBusiUnitService iBusiUnitService;

    /**erp组织类型表Service*/
    @Resource
    private IOrganizationsTypeService iOrganizationsTypeService;
    /**组织设置表Service*/
    @Resource
    private IOrganizationService iOrganizationService;
    /**erp地点表Service*/
    @Resource
    private ILocationService iLocationService;

    public SoapResponse execute(REQUEST request) {
        Long startTime = System.currentTimeMillis();
        SoapResponse response = null;
        try{
            REQUEST.RequestInfo requestInfo = request.getRequestInfo();
            REQUEST.RequestInfo.CONTEXT.BUSINESSUNIT businessunit = null;
            List<REQUEST.RequestInfo.CONTEXT.BUSINESSUNIT.RECORD> recordList = null;
            REQUEST.EsbInfo esbInfo = null;
            REQUEST.RequestInfo.HEADER header = null;
            if(null != requestInfo) {
                businessunit = (null != requestInfo.getCONTEXT() ? requestInfo.getCONTEXT().getBUSINESSUNIT() : null);
                recordList = (null != businessunit ? businessunit.getRECORD() : null);
                esbInfo = request.getEsbInfo();
                header = requestInfo.getHEADER();
            }
            String instId = "";
            String requestTime = "";
            BigDecimal segNum = null;
            if(null != esbInfo){
                instId = esbInfo.getInstId();
                requestTime = esbInfo.getRequestTime();
            }
            if(null != header){
                segNum = (StringUtils.isNoneBlank(header.getSEGNUM()) ? new BigDecimal(header.getSEGNUM()) : new BigDecimal("0"));
            }
            log.info("erp获取业务实体数据: " + (null != recordList ? recordList.toString() : "空"));
            List<BusinessUnits> businessUnitsList = new ArrayList<>();
            Date nowDate = new Date();
            LocalDate localDateNow = LocalDate.now();

            /**获取库存组织和业务实体ID、Code和Name**/
            List<OrganizationsType> orgTypeList = iOrganizationsTypeService.list(); //组织类型(业务实体-OU，库存组织INV)
            Long busiUnitTypeId = null;    //业务实体ID
            String busiUnitTypeCode = "OU";    //业务实体Code
            String busiUnitTypeName = null;    //业务实体Name
            if(CollectionUtils.isNotEmpty(orgTypeList)){
                for(OrganizationsType orgType : orgTypeList){
                    if(null != orgType && busiUnitTypeCode.equals(orgType.getOrganizationTypeCode())){
                        busiUnitTypeId = orgType.getTypeId();
                        busiUnitTypeName = orgType.getOrganizationTypeName();
                    }
                }
            }

            /**获取集团ID和Name**/
            List<Organization> queryGroupList = iOrganizationService.list(new QueryWrapper<>(new Organization().setOrganizationTypeCode(BaseConst.ORG_GROUP))); //集团集合
            Long groupOrganizationId = 0L;    //集团组织ID
            String groupOrganizationName = "";  //集团组织名称
            if(CollectionUtils.isNotEmpty(queryGroupList) && null != queryGroupList.get(0)){
                groupOrganizationId = queryGroupList.get(0).getOrganizationId();
                groupOrganizationName = queryGroupList.get(0).getOrganizationName();
            }

            List<Organization> queryBusiOrgList = iOrganizationService.list(new QueryWrapper<>(new Organization().setOrganizationTypeCode(BaseConst.ORG_OU))); //业务实体集合
            List<Organization> updateBusiOrgList = new ArrayList<>();     //更新的业务实体类
            List<Location> locationList = iLocationService.list(new QueryWrapper<>(new Location())); //获取所有的地点集合
            if(CollectionUtils.isNotEmpty(recordList)){
                for(REQUEST.RequestInfo.CONTEXT.BUSINESSUNIT.RECORD record : recordList){
                    if (null != record) {
                        BusinessUnits businessUnits = new BusinessUnits();
                        String operationUnitId = (null != record.getORGANIZATIONID() ? record.getORGANIZATIONID().getValue() : ""); //业务实体Id
                        String operationUnitCode = (null != record.getSHORTCODE() ? record.getSHORTCODE().getValue() : "");    //业务实体简码
                        String operationUnitTypeCode = (null != record.getORGANIZATIONTYPECODE() ? record.getORGANIZATIONTYPECODE().getValue() : ""); //业务实体类型代码
                        String operationUnitTypeName = (null != record.getORGANIZATIONTYPENAME() ? record.getORGANIZATIONTYPENAME().getValue() : ""); //业务实体类型名称
                        String operationName = (null != record.getNAME() ? record.getNAME().getValue() : ""); //业务实体名称
                        String startDate = (null != record.getDATEFROM() ? record.getDATEFROM().getValue() : ""); //起始日期
                        String endDate = (null != record.getDATETO() ? record.getDATETO().getValue() : "");//	截止日期
                        String locationId = (null != record.getLOCATIONID() ? record.getLOCATIONID().getValue() : "");//	地点Id
                        String locationName = (null != record.getLOCATIONNAME() ? record.getLOCATIONNAME().getValue() : "");//	地点名称
                        String internalExternal = (null != record.getINTERNALEXTERNALFLAG() ? record.getINTERNALEXTERNALFLAG().getValue() : "");//	内部或外部
                        String companyName = (null != record.getCOMPANYNAME() ? record.getCOMPANYNAME().getValue() : "");//	公司名称	String(240)
                        String objectVersionNum = (null != record.getOBJECTVERSIONNUMBER() ? record.getOBJECTVERSIONNUMBER().getValue() : "");//对象版本号
                        String langueCode = (null != record.getLANGUECODE() ? record.getLANGUECODE().getValue() : "");//语言代码
                        String extendAttributes = (null != record.getEXTENTATTRIBUTES() ? record.getEXTENTATTRIBUTES().getValue() : "");//	业务实体属性
                        String enableFlag = (null != record.getENABLEDFLAG() ? record.getENABLEDFLAG().getValue() : "");
                        String businessGroupId = (null != record.getBUSINESSGROUPID() ? record.getBUSINESSGROUPID().getValue() : "");//企业集团
                        businessUnits.setEsBusinessUnitId(operationUnitId);
                        businessUnits.setEsBusinessUnitCode(operationUnitCode);
                        businessUnits.setBusinessShortName(operationUnitTypeName);
                        businessUnits.setBusinessFullName(operationName);
                        businessUnits.setEnabledFlag(enableFlag);
                        businessUnits.setBusinessGroup(businessGroupId);
                        businessUnits.setEsLocationId(locationId);
                        businessUnits.setEsLoactionName(locationName);
                        businessUnits.setCompanyName(companyName);
                        businessUnits.setSegNum(segNum);
                        businessUnits.setCreatedBy("1");
                        businessUnits.setExternalSystemCode(InterfaceResourceEnum.ERP.getName());
                        businessUnits.setStatus(InterfaceStatusEnum.NEW.getName());
                        businessUnits.setCreationDate(nowDate);
                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        if(StringUtils.isNotBlank(startDate)) {
                            businessUnits.setStartDate(LocalDate.parse(startDate, fmt));
                        }
                        if(StringUtils.isNotBlank(endDate)) {
                            businessUnits.setEndDate(LocalDate.parse(endDate, fmt));
                        }
                        businessUnitsList.add(businessUnits);

                        Long organizationId = null;
                        //设置业务实体保存数据
                        if(CollectionUtils.isNotEmpty(queryBusiOrgList)){
                            for(Organization org : queryBusiOrgList){
                                if(null != org && StringUtils.isNotBlank(operationUnitId) && operationUnitId.equals(org.getErpOrgId())){
                                    organizationId = org.getOrganizationId();
                                    break;
                                }
                            }
                        }
                        Organization orgUnit = new Organization();
                        orgUnit.setOrganizationId(organizationId);
                        orgUnit.setParentOrganizationIds(String.valueOf(groupOrganizationId));
                        orgUnit.setParentOrganizationNames(groupOrganizationName);
                        orgUnit.setOrganizationTypeId(busiUnitTypeId);
                        orgUnit.setOrganizationTypeCode(busiUnitTypeCode);
                        orgUnit.setOrganizationTypeName(busiUnitTypeName);
                        orgUnit.setErpOrgId(operationUnitId);
                        orgUnit.setOrganizationCode(operationName);
                        orgUnit.setOrganizationName(operationName);
                        if (StringUtils.isBlank(enableFlag)) {
                            enableFlag = "Y";
                        }
                        orgUnit.setEnabled(enableFlag);

                        //获取地点明细，如果locationList为空，则保存EsLocationName
                        orgUnit.setCeeaReceivingLocation(locationName);
                        orgUnit.setOrganizationSite(locationName);
                        String locationAddress = "";    //详细地址
                        if(CollectionUtils.isNotEmpty(locationList) && StringUtils.isNotBlank(locationId)){
                            for(Location location : locationList){
                                if(null != location && null != location.getLocationId() && location.getLocationId().compareTo(Long.parseLong(locationId)) == 0){
                                    locationAddress = location.getAddrDetail();
                                    break;
                                }
                            }
                        }
                        orgUnit.setCeeaReceivingLocationAddres(locationAddress);
                        orgUnit.setCeeaOrganizationSiteAddress(locationAddress);
                        orgUnit.setCeeaReceivingLocationId(locationId);
                        orgUnit.setCeeaOrganizationSiteId((StringUtils.isNotBlank(locationId) ? Long.valueOf(locationId) : null));
                        orgUnit.setDataResource(DataSourceEnum.ERP_SYS.getKey());
                        orgUnit.setCeeaCompanyName(companyName);
                        if(StringUtils.isNotBlank(startDate)) {
                            orgUnit.setStartDate(LocalDate.parse(startDate, fmt));
                        }else{
                            orgUnit.setStartDate(localDateNow);
                        }
                        if(StringUtils.isNotBlank(endDate)) {
                            orgUnit.setEndDate(LocalDate.parse(endDate, fmt));
                        }
                        updateBusiOrgList.add(orgUnit);
                    }
                }
            }
            response = iBusiUnitService.execute(businessUnitsList,updateBusiOrgList, instId, requestTime);
        }catch (Exception e){
            log.error("webservice Erp获取库存组织信息时报错：",e);
        }
        log.info("erp获取业务实体接口插入数据用时:"+(System.currentTimeMillis()-startTime)/1000 +"秒");
        return response;
    }

}
