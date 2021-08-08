package com.midea.cloud.srm.base.busiunit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.srm.base.busiunit.service.IBusiUnitService;
import com.midea.cloud.srm.base.organization.service.IBusinessUnitsService;
import com.midea.cloud.srm.base.organization.service.IOrganizationService;
import com.midea.cloud.srm.model.base.organization.entity.BusinessUnits;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <pre>
 * ERP接口实现Service
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
@Service
@Slf4j
public class BusiUnitServiceImpl implements IBusiUnitService {

    /**erp业务组织表Service*/
    @Resource
    private IBusinessUnitsService iBusinessUnitsService;
    /**组织设置表Service*/
    @Resource
    private IOrganizationService iOrganizationService;

    @Override
    public SoapResponse execute(BusinessUnits businessUnits, String instId, String requestTime) {
        Date nowDate = new Date();
        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setSuccess("true");

        String returnStatus = "";
        String resultMsg = "";
        if(null == businessUnits){
            returnStatus = "E";
            resultMsg = "接口返回值为空.";
        }else{
            try {
                boolean isSaveOrUpdate = false;
                log.info("新增或修改业务实体数据: " + JsonUtil.entityToJsonStr(businessUnits));
                if (null != businessUnits) {
                    isSaveOrUpdate = iBusinessUnitsService.saveOrUpdate(businessUnits);
                    if(!isSaveOrUpdate){
                        returnStatus = "E";
                        resultMsg = "新增或修改业务实体表时报错";
                        log.error("新增或修改业务实体表时报错,businessUnits: ",JsonUtil.entityToJsonStr(businessUnits));
                    }
                }
                returnStatus = "S";
                resultMsg = "成功插入表.";
            }catch (Exception e){
                log.error("保存erp业务实体表时报错：",e);
                returnStatus = "E";
                resultMsg = "保存erp业务实体表时报错";
            }

        }

        SoapResponse.RESPONSE response = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfo = new SoapResponse.RESPONSE.EsbInfo();
        esbInfo.setReturnStatus(returnStatus);
        esbInfo.setReturnMsg(resultMsg);
        esbInfo.setResponseTime(requestTime);
        if(StringUtils.isBlank(requestTime)){
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfo.setInstId(instId);
        esbInfo.setRequestTime(requestTime);
        esbInfo.setAttr1("");
        esbInfo.setAttr2("");
        esbInfo.setAttr3("");
        response.setEsbInfo(esbInfo);
        SoapResponse.RESPONSE.ResultInfo resultInfo = new SoapResponse.RESPONSE.ResultInfo();
        resultInfo.setRESPONSEMESSAGE("");
        resultInfo.setRESPONSESTATUS("");
        response.setResultInfo(resultInfo);
        soapResponse.setResponse(response);
        log.info("新增或修改业务实体返回数据: " + JsonUtil.entityToJsonStr(soapResponse));
        return soapResponse;
    }

    @Override
    public SoapResponse execute(List<BusinessUnits> businessUnitsList, List<Organization> updateBusiOrgList, String instId, String requestTime) {
        Date nowDate = new Date();
        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setSuccess("true");
        String returnStatus = "";
        String resultMsg = "";
        if(CollectionUtils.isEmpty(businessUnitsList)){
            returnStatus = "E";
            resultMsg = "接口返回值为空.";
        }else{
            try {
                boolean isSaveOrUpdate = false;
                log.info("新增或修改业务实体数据: " + JsonUtil.entityToJsonStr(businessUnitsList));
                List<BusinessUnits> queryBusinessUnitsList = iBusinessUnitsService.list(new QueryWrapper<>(new BusinessUnits()));
                List<BusinessUnits> saveBusinessUnitsList = new ArrayList<>();
                if(CollectionUtils.isNotEmpty(businessUnitsList)){
                    for(BusinessUnits businessUnits : businessUnitsList){
                        if(null != businessUnits){
                            BusinessUnits saveBusinessUnits = new BusinessUnits();
                            BeanUtils.copyProperties(businessUnits, saveBusinessUnits);
                            Long itfHeaderid = IdGenrator.generate();
//                            BusinessUnits queryBusinessUnits = new BusinessUnits();
                            String esBusinessUnitId = businessUnits.getEsBusinessUnitId();
//                            queryBusinessUnits.setEsBusinessUnitId(esBusinessUnitId);
//                            List<BusinessUnits> queryBusinessUnitsList = iBusinessUnitsService.list(new QueryWrapper<>(queryBusinessUnits));
//                            if(CollectionUtils.isNotEmpty(queryBusinessUnitsList) && null != queryBusinessUnitsList.get(0)
//                                && null != queryBusinessUnitsList.get(0).getItfHeaderId()) {
//                                itfHeaderid = queryBusinessUnitsList.get(0).getItfHeaderId();
//                            }
                            if(CollectionUtils.isNotEmpty(queryBusinessUnitsList) && StringUtils.isNotBlank(esBusinessUnitId)){
                                for(BusinessUnits queryBudiUnit : queryBusinessUnitsList){
                                    if(null != queryBudiUnit && esBusinessUnitId.equals(queryBudiUnit.getEsBusinessUnitId())){
                                        itfHeaderid = queryBudiUnit.getItfHeaderId();
                                        break;
                                    }
                                }
                            }
                            saveBusinessUnits.setItfHeaderId(itfHeaderid);
                            saveBusinessUnitsList.add(saveBusinessUnits);
                        }
                    }

                    isSaveOrUpdate = iBusinessUnitsService.saveOrUpdateBatch(saveBusinessUnitsList);
                    if(!isSaveOrUpdate){
                        returnStatus = "E";
                        resultMsg = "新增或修改ERP业务实体表时报错";
                    }

                    /**保存业务实体类*/
                    iOrganizationService.saveOrUpdateOrganizationForErp(updateBusiOrgList);
                }
                if(isSaveOrUpdate) {
                    returnStatus = "S";
                    resultMsg = "成功业务实体插入表.";
                }
            }catch (Exception e){
                log.error("保存erp业务实体表时报错：",e);
                returnStatus = "E";
                resultMsg = "保存erp业务实体表时报错";
            }

        }

        SoapResponse.RESPONSE response = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfo = new SoapResponse.RESPONSE.EsbInfo();
        esbInfo.setReturnStatus(returnStatus);
        esbInfo.setReturnMsg(resultMsg);
        esbInfo.setResponseTime(requestTime);
        if(StringUtils.isBlank(requestTime)){
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfo.setInstId(instId);
        esbInfo.setRequestTime(requestTime);
        esbInfo.setAttr1("");
        esbInfo.setAttr2("");
        esbInfo.setAttr3("");
        response.setEsbInfo(esbInfo);
        soapResponse.setResponse(response);
        log.info("新增或修改业务实体返回数据: " + JsonUtil.entityToJsonStr(soapResponse));
        return soapResponse;
    }

    /**
     * Description xmlString转换成对象
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.08.05
     * @throws
     **/
    private static Object convertXmlStrToObject(Class clazz,String xmlStr)throws Exception{
        JAXBContext context=JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller=context.createUnmarshaller();
        StringReader sr=new StringReader(xmlStr);
        return unmarshaller.unmarshal(sr);
    }
}
