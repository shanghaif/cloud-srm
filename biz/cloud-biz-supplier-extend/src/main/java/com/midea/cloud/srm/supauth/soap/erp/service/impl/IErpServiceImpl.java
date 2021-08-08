package com.midea.cloud.srm.supauth.soap.erp.service.impl;

import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.srm.model.supplierauth.soap.erp.dto.*;
import com.midea.cloud.srm.supauth.soap.erp.service.IErpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
 *  修改日期: 2020/9/2 13:31
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class IErpServiceImpl implements IErpService {

    /*@Autowired
    VendorService vendorService;*/

    @Override
    public VendorResponse getVendors(String instId, String requestTime) {
        Date nowDate = new Date();
        VendorResponse vendorResponse = new VendorResponse();
        vendorResponse.setSuccess("true");
        String returnStatus = "S";
        String resultMsg = "";
        /*VendorService.vendothing(List<VendorEntity> vendorEntityList);*/
        List<VendorEntity> vendorEntityList = new ArrayList<>();
        VendorEntity vendorEntity = new VendorEntity();
        vendorEntity.setVendorName("美云智数");
        vendorEntity.setVendorNumber("121212");
        vendorEntity.setSourceSysCode("SRM");
        vendorEntity.setSourceLineId("1111");

        vendorEntityList.add(vendorEntity);
        resultMsg = "success";

        VendorResponse.RESPONSE response = new VendorResponse.RESPONSE();
        VendorResponse.RESPONSE.EsbInfo esbInfo = new VendorResponse.RESPONSE.EsbInfo();
        VendorResponse.RESPONSE.ResponseInfo responseInfo = new VendorResponse.RESPONSE.ResponseInfo();
        VendorResponse.RESPONSE.ResponseInfo.Vendors vendors = new VendorResponse.RESPONSE.ResponseInfo.Vendors();
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

        vendors.setVendor(vendorEntityList);
        responseInfo.setVendors(vendors);
        response.setResponseInfo(responseInfo);
        vendorResponse.setResponse(response);
        return vendorResponse;
    }

    @Override
    public VendorContactResponse getVendorContacts(String instId, String requestTime) {
        Date nowDate = new Date();
        VendorContactResponse vendorContactResponse = new VendorContactResponse();
        vendorContactResponse.setSuccess("true");
        String returnStatus = "S";
        String resultMsg = "";
        /*VendorService.vendothing(List<VendorEntity> vendorEntityList);*/
        List<VendorContactEntity> vendorContactEntityList = new ArrayList<>();
        VendorContactEntity vendorContactEntity = new VendorContactEntity();

        vendorContactEntity.setErpVendorId(Long.valueOf(1));
        vendorContactEntity.setVendorNumber("121212");
        vendorContactEntity.setContractorName("联系人姓名");
        vendorContactEntity.setSourceSysCode("SRM");
        vendorContactEntity.setSourceLineId("1111");

        vendorContactEntityList.add(vendorContactEntity);
        resultMsg = "success";

        VendorContactResponse.RESPONSE response = new VendorContactResponse.RESPONSE();
        VendorContactResponse.RESPONSE.EsbInfo esbInfo = new VendorContactResponse.RESPONSE.EsbInfo();
        VendorContactResponse.RESPONSE.ResponseInfo responseInfo = new VendorContactResponse.RESPONSE.ResponseInfo();
        VendorContactResponse.RESPONSE.ResponseInfo.VendorContacts vendorContacts = new VendorContactResponse.RESPONSE.ResponseInfo.VendorContacts();
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

        vendorContacts.setVendorContact(vendorContactEntityList);
        responseInfo.setVendorContacts(vendorContacts);
        response.setResponseInfo(responseInfo);
        vendorContactResponse.setResponse(response);
        return vendorContactResponse;
    }
    @Override
    public VendorBankResponse getVendorBanks(String instId, String requestTime) {
        Date nowDate = new Date();
        VendorBankResponse vendorBankResponse = new VendorBankResponse();
        vendorBankResponse.setSuccess("true");
        String returnStatus = "S";
        String resultMsg = "";
        /*VendorService.vendothing(List<VendorEntity> vendorEntityList);*/
        List<VendorBankEntity> vendorBankEntityList = new ArrayList<>();
        VendorBankEntity vendorBankEntity = new VendorBankEntity();

        vendorBankEntity.setErpVendorId(Long.valueOf(1));
        vendorBankEntity.setVendorNumber("121212");
        vendorBankEntity.setBankCode("bankCode");
        vendorBankEntity.setBankName("银行名称");
        vendorBankEntity.setSourceSysCode("SRM");
        vendorBankEntity.setSourceLineId("1111");

        vendorBankEntityList.add(vendorBankEntity);
        resultMsg = "success";

        VendorBankResponse.RESPONSE response = new VendorBankResponse.RESPONSE();
        VendorBankResponse.RESPONSE.EsbInfo esbInfo = new VendorBankResponse.RESPONSE.EsbInfo();
        VendorBankResponse.RESPONSE.ResponseInfo responseInfo = new VendorBankResponse.RESPONSE.ResponseInfo();
        VendorBankResponse.RESPONSE.ResponseInfo.VendorBanks vendorBanks = new VendorBankResponse.RESPONSE.ResponseInfo.VendorBanks();
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

        vendorBanks.setVendorBank(vendorBankEntityList);
        responseInfo.setVendorBanks(vendorBanks);
        response.setResponseInfo(responseInfo);
        vendorBankResponse.setResponse(response);
        return vendorBankResponse;
    }

}
