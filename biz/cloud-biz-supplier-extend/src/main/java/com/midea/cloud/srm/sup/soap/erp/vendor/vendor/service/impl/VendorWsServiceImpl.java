package com.midea.cloud.srm.sup.soap.erp.vendor.vendor.service.impl;

import com.midea.cloud.srm.model.base.soap.erp.base.dto.*;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendor.VendorEntity;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendor.VendorRequest;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendor.VendorSites;
import com.midea.cloud.srm.sup.soap.erp.service.IErpService;
import com.midea.cloud.srm.sup.soap.erp.vendor.vendor.service.IVendorWsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;
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
 *  修改日期: 2020/8/20 17:37
 *  修改内容:
 * </pre>
 */

@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.sup.soap.erp.vendor.vendor.service.IVendorWsService")
@Component("iVendorWsService")
public class VendorWsServiceImpl implements IVendorWsService {

    /**
     * 供应商模块Erp总线Service
     */
    @Autowired
    private IErpService iErpService;

    @Override
    public SoapResponse execute(VendorRequest request) {
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

        /**获取供应商List,并保存数据*/
        VendorRequest.RequestInfo requestInfo = request.getRequestInfo();
        VendorRequest.RequestInfo.Vendors vendorsClass = null;
        List<VendorEntity> vendorEntityList = null;
        List<CategorySetsEntity> categorySetsEntityList = null;
        if(null != requestInfo){
            vendorsClass = requestInfo.getVendors();
            if(null != vendorsClass){
                vendorEntityList = vendorsClass.getVendor();
            }
        }

        log.info("erp获取供应商接口数据：" + (null != request? request.toString() : "空"));
        if (!vendorEntityList.isEmpty()) {
            response = iErpService.dealVendorSites(vendorEntityList, instId, requestTime);
        }
        log.info("erp供应商接口插入数据用时："+(System.currentTimeMillis()-startTime)/1000+"秒");
        return response;
    }
}
