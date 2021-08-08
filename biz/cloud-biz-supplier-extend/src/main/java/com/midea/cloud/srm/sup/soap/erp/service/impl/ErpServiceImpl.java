package com.midea.cloud.srm.sup.soap.erp.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.enums.sup.VendorSiteCode;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.srm.feign.api.ApiClient;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.SiteInfo;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendor.VendorEntity;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendor.VendorSiteEntity;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendor.VendorSites;
import com.midea.cloud.srm.sup.info.service.ICompanyInfoService;
import com.midea.cloud.srm.sup.info.service.ISiteInfoService;
import com.midea.cloud.srm.sup.soap.erp.service.IErpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/27 18:19
 *  修改内容:
 * </pre>
 */
@Slf4j
@Service
public class ErpServiceImpl implements IErpService {

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private ISiteInfoService iSiteInfoService;

    @Resource
    private ApiClient apiClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SoapResponse dealVendorSites(List<VendorEntity> vendorEntityList, String instId, String requestTime) {

        Date nowDate = new Date();
        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setSuccess("true");
        String returnStatus = "";
        String resultMsg = "";
        SoapResponse.RESPONSE response = new SoapResponse.RESPONSE();
        SoapResponse.RESPONSE.EsbInfo esbInfo = new SoapResponse.RESPONSE.EsbInfo();
        if (CollectionUtils.isEmpty(vendorEntityList)) {
            returnStatus = "E";
            resultMsg = "接口返回值为空.";
        } else {
            try {
                for (VendorEntity vendorEntity : vendorEntityList) {
                    // 供应商接收(NSrm)接口接收数据保存日志
                    saveVendorAcceptInterfaceLog(vendorEntity);
                    updateSiteInfo(vendorEntity);
                }
                returnStatus = "S";
                resultMsg = "接收供应商数据成功";
            } catch (Throwable e) {
                returnStatus = "E";
                resultMsg = e.getMessage();
                esbInfo.setReturnStatus(returnStatus);
                esbInfo.setReturnMsg(resultMsg);
            }
        }
        esbInfo.setReturnStatus(returnStatus);
        esbInfo.setReturnMsg(resultMsg);
        esbInfo.setResponseTime(requestTime);
        if (StringUtils.isBlank(requestTime)) {
            requestTime = DateUtil.parseDateToStr(nowDate, "yyyy-MM-dd HH:mm:ss:SSS");
        }
        esbInfo.setInstId(instId);
        esbInfo.setRequestTime(requestTime);
        esbInfo.setAttr1("");
        esbInfo.setAttr2("");
        esbInfo.setAttr3("");
        response.setEsbInfo(esbInfo);
        soapResponse.setResponse(response);
        return soapResponse;
    }

    /**
     * 根据传入的地点回写NSrm数据库siteInfo的erpSiteId
     *
     * @param vendorEntity
     */
    public boolean updateSiteInfo(VendorEntity vendorEntity) {
        boolean status = false;
        VendorSites vendorSites = vendorEntity.getVendorSites();
        //获取erpVendorId 查询companyInfo表获取companyId
        Long erpVendorId = Long.valueOf(vendorEntity.getVendorId());
        List<CompanyInfo> companyInfos = iCompanyInfoService.list(
                new QueryWrapper<>(new CompanyInfo().setErpVendorId(erpVendorId))
        );

        //查询到erpVendorId的情况下，继续回写地点Id
        if (CollectionUtils.isEmpty(companyInfos)) {
            throw new BaseException("NSrm找不到对应的供应商");
        }
        Long companyId = companyInfos.get(0).getCompanyId();

        List<VendorSiteEntity> vendorSiteEntityList = vendorSites.getVendorSite();

        SiteInfo siteInfo = new SiteInfo().setCompanyId(companyId);
        List<SiteInfo> siteInfos = iSiteInfoService.list( new QueryWrapper<>(siteInfo));

        Assert.isTrue(CollectionUtils.isNotEmpty(siteInfos), "NSrm找不到对应的地点，对应的erp供应商Id："+ erpVendorId
                +"，对应的srm供应商Id："+companyId);

        /**如果供应商地点为空,供应商ID、erp供应商ID、所属业务实体ID和地点名称相同，则需要更新供应商地点ID(ERP)**/
        if(CollectionUtils.isNotEmpty(siteInfos)) {
            for(SiteInfo site : siteInfos) {
                if (null != site && (StringUtils.isBlank(site.getVendorSiteId())) ) {
                    String belongOprId = site.getBelongOprId();
//                    String addressDetail = site.getAddressDetail();
                    String vendorSiteCode = site.getVendorSiteCode();
                    //回传接口里的供应商地点判空
                    if (CollectionUtils.isNotEmpty(vendorSiteEntityList)) {
                        for (VendorSiteEntity siteEntity : vendorSiteEntityList) {
                            String vendorSiteId = siteEntity.getVendorSiteId();
                            String operateUnit = siteEntity.getOperateUnit();
                            String addressName = siteEntity.getAddressName();
//                            String detailAddress = siteEntity.getDetailAddress();
                            Assert.notNull(vendorSiteId, "回传的Erp供应商地点的地点ID为空！");
                            Assert.notNull(addressName, "回传的供应商地点的地点名称为空！");
                            if (StringUtils.isNotBlank(belongOprId) && StringUtils.isNotBlank(vendorSiteCode)
                                    && belongOprId.equals(operateUnit) && vendorSiteCode.equals(addressName)) {
                                site.setVendorSiteId(vendorSiteId).setIfReceiveFromErp("Y");
                                status = iSiteInfoService.updateById(site);
                                break;
                            }

//                            SiteInfo siteInfo = new SiteInfo().setCompanyId(companyId)
//                                    .setBelongOprId(operateUnit)
//                                    .setAddressDetail(detailAddress)
//                                    .setIfPushErp("Y");

//                            Assert.notNull(addressName, "回传的供应商地点的地点名称为空！");
//                            siteInfo.setVendorSiteCode(addressName);
//
//                            List<SiteInfo> siteInfos = iSiteInfoService.list(
//                                    new QueryWrapper<>(siteInfo)
//                            );
//                            Assert.isTrue(!CollectionUtils.isEmpty(siteInfos), "NSrm找不到对应的地点，对应的erp供应商Id："+ erpVendorId +"，地点对应的业务实体："+siteEntity.getOperateUnit()+"，地点对应的地点名称："+siteEntity.getAddressName());
//                            SiteInfo existSiteInfo = siteInfos.get(0);
//                            existSiteInfo.setVendorSiteId(vendorSiteId);
//                            status = iSiteInfoService.updateById(existSiteInfo);
                        }
                    }
                }
            }
        }

        return status;
    }

    /**
     * 将erp回传的供应商数据保存到接口日志里
     * @param vendorEntity
     */
    public void saveVendorAcceptInterfaceLog(VendorEntity vendorEntity) {
        log.info("开始保存供应商接收日志...");
        InterfaceLogDTO interfaceLogDTO = new InterfaceLogDTO();
        // 创建开始时间
        interfaceLogDTO.setCreationDateBegin(new Date());
        // 服务名字
        interfaceLogDTO.setServiceName("供应商erp回写");
        // 请求方式
        interfaceLogDTO.setServiceType("WEBSERVICE");
        // 发送方式
        interfaceLogDTO.setType("RECEIVE");
        // 单据类型
        interfaceLogDTO.setBillType("供应商接收");


        // 请求参数
        try {
            interfaceLogDTO.setServiceInfo(JSON.toJSONString(vendorEntity));
        } catch (Exception e) {
            log.error("供应商接收数据记录日志报错{}"+e.getMessage());
        }
        // 单据id
        String erpVendorId = vendorEntity.getVendorId();
        interfaceLogDTO.setBillId(erpVendorId);
        // 状态
        interfaceLogDTO.setStatus("SUCCESS");
        interfaceLogDTO.setTargetSys("SRM");

        try {
            apiClient.createInterfaceLogForAnon(interfaceLogDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存<erp回写供应商数据>日志报错{}"+e.getMessage());
        }
        log.info("保存供应商接收日志结束...");
    }
}
