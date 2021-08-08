package com.midea.cloud.srm.base.soap.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.constants.BaseConst;
import com.midea.cloud.common.enums.soap.InterfaceResourceEnum;
import com.midea.cloud.common.enums.soap.InterfaceStatusEnum;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.busiunit.service.IErpService;
import com.midea.cloud.srm.base.organization.service.*;
import com.midea.cloud.srm.base.soap.erp.service.IInvOrganizationsWsService;
import com.midea.cloud.srm.model.base.organization.entity.InvOrganization;
import com.midea.cloud.srm.model.base.organization.entity.Location;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationsType;
import com.midea.cloud.srm.model.base.soap.DataSourceEnum;
import com.midea.cloud.srm.model.base.soap.SoapConst;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.EsbInfoRequest;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.InvOrganizationsEntity;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.InvOrganizationsRequest;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.time.LocalDate;
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
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.base.soap.erp.service.IInvOrganizationsWsService")
@Component("invOrganizationsWsService")
public class InvOrganizationsWsServiceImpl implements IInvOrganizationsWsService {

    /**erp总线Service*/
    @Resource
    private IErpService iErpService;

    /**erp库存组织基础表Service*/
    @Resource
    private IInvOrganizationsService iInvOrganizationsService;

    /**组织设置表Service*/
    @Resource
    private IOrganizationService iOrganizationService;

    /**erp业务组织基础表Service*/
    @Resource
    private IBusinessUnitsService iBusinessUnitsService;
    /**erp组织类型表Service*/
    @Resource
    private IOrganizationsTypeService iOrganizationsTypeService;

    /**erp地点表Service*/
    @Resource
    private ILocationService iLocationService;

    public SoapResponse execute(InvOrganizationsRequest request) {
        Long startTime = System.currentTimeMillis();
        SoapResponse response = null;
        Date nowDate = new Date();
        LocalDate localDateNow = LocalDate.now();
        try {
            EsbInfoRequest esbInfo = request.getEsbInfo();
            InvOrganizationsRequest.RequestInfo requestInfo = request.getRequestInfo();
            InvOrganizationsRequest.RequestInfo.InvOrganizations invOrganizationsClass = requestInfo.getInvOrganizations();
            List<InvOrganizationsEntity> invOrganizationsEntityList = invOrganizationsClass.getInvOrganization();
            String instId = "";
            String requestTime = "";
            if (null != esbInfo) {
                instId = esbInfo.getInstId();
                requestTime = esbInfo.getRequestTime();
            }
            log.info("erp获取库存组织数据: " + (null != request ? request.toString() : "空"));
            /**获取库存组织和业务实体ID、Code和Name**/
            List<OrganizationsType> orgTypeList = iOrganizationsTypeService.list(); //组织类型(业务实体-OU，库存组织INV)
            Long orgTypeId = null;    //库存组织ID
            String orgTypeCode = "INV";    //库存组织Code
            String orgTypeName = null;    //库存组织名称
            Long busiUnitTypeId = null;    //业务实体ID
            String busiUnitTypeCode = "OU";    //业务实体Code
            String busiUnitTypeName = null;    //业务实体Name
            if(CollectionUtils.isNotEmpty(orgTypeList)){
                for(OrganizationsType orgType : orgTypeList){
                    if(null != orgType && orgTypeCode.equals(orgType.getOrganizationTypeCode())){
                        orgTypeId = orgType.getTypeId();
                        orgTypeName = orgType.getOrganizationTypeName();
                    }else if(null != orgType && busiUnitTypeCode.equals(orgType.getOrganizationTypeCode())){
                        busiUnitTypeId = orgType.getTypeId();
                        busiUnitTypeName = orgType.getOrganizationTypeName();
                    }
                }
            }

            List<InvOrganization> queryInvOrganizationList = iInvOrganizationsService.list(new QueryWrapper<>(new InvOrganization()));
            List<Organization> queryOrgList = iOrganizationService.list(new QueryWrapper<>(new Organization().setOrganizationTypeCode(BaseConst.ORG_INV))); //库存组织集合
            List<Organization> queryBusiOrgList = iOrganizationService.list(new QueryWrapper<>(new Organization().setOrganizationTypeCode(BaseConst.ORG_OU))); //业务实体集合
            List<InvOrganization> saveInvOrganizationList = new ArrayList<>();
            List<Organization> saveOrgnizationList = new ArrayList<>(); //修改或保存库存组织数据
            List<Organization> saveOrgUnitList = new ArrayList<>(); //修改或保存业务实体数据
            String locationName = "";   //收单/收货详细地址
            List<Location> locationList = iLocationService.list(new QueryWrapper<>(new Location())); //获取所有的地点集合
            if (CollectionUtils.isNotEmpty(invOrganizationsEntityList)) {
                for (InvOrganizationsEntity invOrganizationsEntity : invOrganizationsEntityList) {
                    if (null != invOrganizationsEntity) {
                        /**根据库存组织ID查询表数据，如果查询到记录，则为修改，反之为新增*/
                        String invOrganizationId = invOrganizationsEntity.getEsInvOrganizationId(); //库存组织ID
                        String invOrganizationCode = invOrganizationsEntity.getEsInvOrganizationCode(); //库存组织Code
                        String ceeaUnitId = invOrganizationsEntity.getEsBusinessUnitId();   //业务实体ID
                        Long itfHeaderId = IdGenrator.generate();
//                        InvOrganization queryOrganization = new InvOrganization(
//                        queryOrganization.setEsBusinessUnitId(invOrganizationId);
//                        List<InvOrganization> queryInvOrganizationList = iInvOrganizationsService.list(new QueryWrapper<>(queryOrganization));
                        if (CollectionUtils.isNotEmpty(queryInvOrganizationList) && StringUtils.isNotBlank(invOrganizationId)
                                && StringUtils.isNotBlank(invOrganizationCode)) {
                            for (InvOrganization invOrg : queryInvOrganizationList) {
                                if (null != invOrg && invOrganizationId.equals(invOrg.getEsInvOrganizationId())
                                        && invOrganizationCode.equals(invOrg.getEsInvOrganizationCode())) {
                                    itfHeaderId = invOrg.getItfHeaderId();
                                    break;
                                }
                            }
                        }
                        if (null == itfHeaderId) {
                            itfHeaderId = IdGenrator.generate();
                        }

                        //设置Erp库存组织表集合
                        InvOrganization invOrganization = new InvOrganization();
                        BeanUtils.copyProperties(invOrganizationsEntity, invOrganization);
                        invOrganization.setItfHeaderId(itfHeaderId);
                        invOrganization.setItfStatus(InterfaceStatusEnum.NEW.getName());
                        invOrganization.setCreationDate(nowDate);
                        invOrganization.setLastUpdateDate(nowDate);
                        invOrganization.setExternalSystemCode(InterfaceResourceEnum.ERP.getName());
                        saveInvOrganizationList.add(invOrganization);

                        //设置库存组织表集合（如果已经有的记录,则更新）
                        Long organizationId = null;
                        if (CollectionUtils.isNotEmpty(queryOrgList) && StringUtils.isNotBlank(invOrganizationId)) {
                            for (Organization org : queryOrgList) {
                                if (null != org && invOrganizationId.equals(org.getErpOrgId())) {
                                    organizationId = org.getOrganizationId();
                                    break;
                                }
                            }
                        }

                        //获取业务实体id,如果有业务实体则先创建/修改业务实体和库存组织
                        Long ceeaUnitOrgId = null; //业务实体对应的ID
                        if (CollectionUtils.isNotEmpty(queryOrgList) && StringUtils.isNotBlank(ceeaUnitId)) {
                            for (Organization org : queryOrgList) {
                                if (null != org && ceeaUnitId.equals(org.getErpOrgId())) {
                                    ceeaUnitOrgId = org.getOrganizationId();
                                    break;
                                }
                            }
                        }
                        Organization organization = new Organization();
                        if (null != ceeaUnitOrgId) { //如果有业务实体id
                            organization.setParentOrganizationIds(String.valueOf(ceeaUnitOrgId));
                        } else if (StringUtils.isNotBlank(ceeaUnitId)){
                            ceeaUnitOrgId = IdGenrator.generate();
                        }
                        String enabledFlag = invOrganizationsEntity.getEnabledFlag();  //是否启动
                        if (StringUtils.isBlank(enabledFlag)) {
                            enabledFlag = "Y";
                        }

                        //获取地点明细，如果locationList为空，则保存EsLocationName
                        String locationId = invOrganizationsEntity.getEsLocationId();
                        locationName = invOrganizationsEntity.getEsLocationName();
                        String locationAddress = "";    //详细地址
                        if(CollectionUtils.isNotEmpty(locationList) && StringUtils.isNotBlank(locationId)){
                            for(Location location : locationList){
                                if(null != location && null != location.getLocationId() && location.getLocationId().compareTo(Long.parseLong(locationId)) == 0){
                                    locationAddress = location.getAddrDetail();
                                    break;
                                }
                            }
                        }


                        //设置
                        organization.setOrganizationId(organizationId);
//                        organization.setIsUpdate(isUpdateOrg);
                        organization.setOrganizationTypeId(orgTypeId);
                        organization.setOrganizationTypeCode(orgTypeCode);
                        organization.setOrganizationTypeName(orgTypeName);
                        organization.setStartDate(localDateNow);
                        organization.setErpOrgId(invOrganizationId);
                        organization.setOrganizationCode(invOrganizationsEntity.getEsInvOrganizationCode());
                        organization.setOrganizationName(invOrganizationsEntity.getInvOrganizationName());
                        organization.setEnabled(enabledFlag);
                        organization.setCeeaReceivingLocationId(locationId);
                        organization.setCeeaReceivingLocation(locationName);
                        organization.setCeeaReceivingLocationAddres(locationAddress);
                        organization.setOrganizationSite(locationName);
                        organization.setCeeaOrganizationSiteId((StringUtils.isNotBlank(locationId) ? Long.valueOf(locationId) : null));
                        organization.setCeeaOrganizationSiteAddress(locationAddress);
                        organization.setDataResource(DataSourceEnum.ERP_SYS.getKey());
                        organization.setCeeaErpUnitId(ceeaUnitId);
                        organization.setCeeaErpUnitCode(invOrganizationsEntity.getEsBusinessUnitCode());
                        String parentOrganizationIds = "";      //父类ID
                        String parentOrganizationNames = "";    //父类名称
                        //获取库存组织父类ID和父类名称
                        if(CollectionUtils.isNotEmpty(queryBusiOrgList) && StringUtils.isNotBlank(ceeaUnitId)){
                            for(Organization busiOrg : queryBusiOrgList){
                                if(null != busiOrg && ceeaUnitId.equals(busiOrg.getErpOrgId())){
                                    parentOrganizationIds = String.valueOf(busiOrg.getOrganizationId());
                                    parentOrganizationNames = busiOrg.getOrganizationName();
                                    break;
                                }
                            }
                        }
                        organization.setParentOrganizationIds(parentOrganizationIds);
                        organization.setParentOrganizationNames(parentOrganizationNames);

//                        organization.setCeeaErpUnitId(ceeaUnitId);
                        saveOrgnizationList.add(organization);

//                        if (null != ceeaUnitOrgId) { //如果业务实体
//                            //设置业务实体保存数据
//                            Organization orgUnit = new Organization();
//                            orgUnit.setOrganizationId(organizationId);
////                            orgUnit.setIsUpdate(isUpdateOrg);
//                            orgUnit.setOrganizationTypeId(busiUnitTypeId);
//                            orgUnit.setOrganizationTypeCode(busiUnitTypeCode);
//                            orgUnit.setOrganizationTypeName(busiUnitTypeName);
//                            orgUnit.setStartDate(localDateNow);
//                            orgUnit.setErpOrgId(String.valueOf(ceeaUnitOrgId));
//                            orgUnit.setErpOrgId(invOrganizationId);
//                            orgUnit.setOrganizationCode(invOrganizationsEntity.getEsBusinessUnitCode());
//                            orgUnit.setOrganizationName("erp更新业务实体");
//                            orgUnit.setEnabled(enabledFlag);
//                            orgUnit.setCeeaReceivingLocationId(locationId);
//
//                            //获取地点明细，如果locationList为空，则保存EsLocationName

//                            orgUnit.setCeeaReceivingLocation(locationName);
//                            orgUnit.setOrganizationSite(locationName);
//                            orgUnit.setCeeaReceivingLocationAddres(locationAddress);
//                            orgUnit.setCeeaOrganizationSiteAddress(locationAddress);
//                            orgUnit.setCeeaOrganizationSiteId((StringUtils.isNotBlank(locationId) ? Long.valueOf(locationId) : null));
//                            orgUnit.setDataResource(DataSourceEnum.ERP_SYS.getKey());
//                            saveOrgUnitList.add(orgUnit);
//
//                        }
                    }
                }
                response = iErpService.saveOrUpdateInvOrganiztions(saveInvOrganizationList, instId, requestTime,
                        saveOrgnizationList, saveOrgUnitList); //保存库存组织基础数据
//            log.info("erp获取库存组织数据: " + (null != request ? request.toString() : "空"));
//            InvOrganizations invOrganizations = null;
//            if (null != invOrganizationsEntity) {
//                invOrganizations = new InvOrganizations();
//                BeanUtils.copyProperties(invOrganizationsEntity, invOrganizations);
//                invOrganizations.setItfHeaderId(IdGenrator.generate());
//                invOrganizations.setStatus(InterfaceStatusEnum.NEW.getName());
//                invOrganizations.setCreationDate(nowDate);
//                invOrganizations.setLastUpdateDate(nowDate);
//                invOrganizations.setExternalSystemCode(InterfaceResourceEnum.ERP.getName());
//            }
//            response = iErpService.saveOrUpdateInvOrganiztions(invOrganizations, instId, requestTime);
            }
            }catch(Exception e){
                log.error("webservice Erp获取库存组织信息时报错：", e);
            }
            log.info("erp获取库存组织接口插入数据用时:" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
            return response;
    }
}
