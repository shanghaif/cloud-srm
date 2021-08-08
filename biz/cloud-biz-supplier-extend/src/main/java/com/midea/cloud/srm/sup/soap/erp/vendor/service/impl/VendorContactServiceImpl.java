package com.midea.cloud.srm.sup.soap.erp.vendor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.ContactInfo;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorContact.*;
import com.midea.cloud.srm.sup.SupplierSoapUrl;
import com.midea.cloud.srm.sup.info.service.ICompanyInfoService;
import com.midea.cloud.srm.sup.soap.erp.vendor.service.IVendorContactService;
import com.midea.cloud.srm.sup.soap.erp.vendor.vendorContact.service.ErpAcceptVendorContactSoapBizPtt;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  erp的供应商联系人信息Service实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/4 17:19
 *  修改内容:
 * </pre>
 */
@Service
public class VendorContactServiceImpl implements IVendorContactService {

    @Resource
    private ICompanyInfoService iCompanyInfoService;

    /** 供应商联系人接口访问用户名 **/
    @Value("${SUP_VENDOR.vendorUsername}")
    private  String vendorUsername;
    /** 供应商联系人接口访问密码 **/
    @Value("${SUP_VENDOR.vendorPassword}")
    private String vendorPassword;

    @Override
    public VendorContactOutputParameters sendVendorContact(ContactInfo contactInfo) {
        // 修改密码接口地址
        String address = SupplierSoapUrl.sendVendorContactUrl;
        // 代理工厂
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        // 设置代理地址
        jaxWsProxyFactoryBean.setAddress(address);
        jaxWsProxyFactoryBean.setUsername(vendorUsername);
        jaxWsProxyFactoryBean.setPassword(vendorUsername);
        // 设置接口类型
        jaxWsProxyFactoryBean.setServiceClass(ErpAcceptVendorContactSoapBizPtt.class);
        // 创建一个代理接口实现
        ErpAcceptVendorContactSoapBizPtt service = (ErpAcceptVendorContactSoapBizPtt) jaxWsProxyFactoryBean.create();
        /**创建一个供应商联系人信息请求类 */
        VendorContactInputParameters vendorContactInput = new VendorContactInputParameters();
        APPSCUXPOVENDORCOX1137209X1X1 esbInfo = new APPSCUXPOVENDORCOX1137209X1X1();
        Date nowDate = new Date();
        esbInfo.setInstid("21");
        esbInfo.setRequesttime(String.valueOf(nowDate.getTime()));
        vendorContactInput.setPesbinforec(esbInfo);

        APPSCUXPOVENDORCOX1137209X1X7 requestInfos = new APPSCUXPOVENDORCOX1137209X1X7();
        List<APPSCUXPOVENDORCOX1137209X1X8> vendorContactList = new ArrayList<>();
        APPSCUXPOVENDORCOX1137209X1X8 vendorContact = new APPSCUXPOVENDORCOX1137209X1X8();

        Long erpVendorId = contactInfo.getErpVendorId();
        vendorContact.setErpvendorid(new BigDecimal(erpVendorId));
        CompanyInfo queryCompanyInfo = new CompanyInfo();
        queryCompanyInfo.setErpVendorId(erpVendorId);
        QueryWrapper<CompanyInfo> companyInfoQueryWrapper = new QueryWrapper<>(queryCompanyInfo);
        CompanyInfo companyInfo = iCompanyInfoService.getOne(companyInfoQueryWrapper);
        if(null != companyInfo){
            String vendorCode = companyInfo.getCompanyCode();
            vendorContact.setVendornumber(vendorCode);
        }
        String contactName = contactInfo.getContactName();
        vendorContact.setContractorname(contactName);
        String phoneNumber = contactInfo.getPhoneNumber();
        vendorContact.setPhonnumber(phoneNumber);
        String emailAddress = contactInfo.getEmail();
        vendorContact.setEmailaddress(emailAddress);
        vendorContact.setSourcesyscode("NSrm");
        String Id = String.valueOf(contactInfo.getContactInfoId());
        vendorContact.setSourcelineid(Id);

        vendorContactList.add(vendorContact);
        requestInfos.setPVENDORCONTACTTBLITEM(vendorContactList);
        vendorContactInput.setPvendorcontacttbl(requestInfos);
        System.out.println("推送供应商联系人信息到Erp request "+ JsonUtil.entityToJsonStr(vendorContactInput));
        VendorContactOutputParameters response = service.erpAcceptVendorContactSoapBiz(vendorContactInput);
        System.out.println("推送供应商联系人信息到Erp response: "+ JsonUtil.entityToJsonStr(response));
        return response;
    }
}
