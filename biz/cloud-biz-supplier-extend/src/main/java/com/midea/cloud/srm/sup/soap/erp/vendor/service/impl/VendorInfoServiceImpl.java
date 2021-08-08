package com.midea.cloud.srm.sup.soap.erp.vendor.service.impl;

import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.srm.model.base.soap.DataSourceEnum;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorInfo.*;
import com.midea.cloud.srm.sup.SupplierSoapUrl;
import com.midea.cloud.srm.sup.soap.erp.vendor.service.IVendorInfoService;
import com.midea.cloud.srm.sup.soap.erp.vendor.vendorInfo.service.ErpAcceptVendorsSoapBizPtt;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  erp的供应商基础信息Service实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/4 15:30
 *  修改内容:
 * </pre>
 */
@Service
public class VendorInfoServiceImpl implements IVendorInfoService {

    /** 供应商基础信息接口访问用户名 **/
    @Value("${SUP_VENDOR.vendorUsername}")
    private  String vendorUsername;
    /** 供应商基础信息接口访问密码 **/
    @Value("${SUP_VENDOR.vendorPassword}")
    private String vendorPassword;

    @Override
    public VendorInfoOutputParameters sendVendorInfo(CompanyInfo companyInfo) {
        // 修改密码接口地址
        String address = SupplierSoapUrl.sendVendorInfoUrl;
        // 代理工厂
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        // 设置代理地址
        jaxWsProxyFactoryBean.setAddress(address);
        jaxWsProxyFactoryBean.setUsername(vendorUsername);
        jaxWsProxyFactoryBean.setPassword(vendorPassword);
        // 设置接口类型
        jaxWsProxyFactoryBean.setServiceClass(ErpAcceptVendorsSoapBizPtt.class);
        // 创建一个代理接口实现
        ErpAcceptVendorsSoapBizPtt service = (ErpAcceptVendorsSoapBizPtt) jaxWsProxyFactoryBean.create();
        /**创建一个供应商基础信息请求类 */
        VendorInfoInputParameters vendorInfo = new VendorInfoInputParameters();
        APPSCUXPOVENDORSYX1137205X1X1 esbInfo = new APPSCUXPOVENDORSYX1137205X1X1();
        Date nowDate = new Date();
        esbInfo.setInstid("11");
        esbInfo.setRequesttime(String.valueOf(nowDate.getTime()));
        vendorInfo.setPesbinforec(esbInfo);

        APPSCUXPOVENDORSYX1137205X1X7 requestInfos = new APPSCUXPOVENDORSYX1137205X1X7();
        List<APPSCUXPOVENDORSYX1137205X1X8> supplierList = new ArrayList<>();
        APPSCUXPOVENDORSYX1137205X1X8 supplier = new APPSCUXPOVENDORSYX1137205X1X8();
        String companyName = companyInfo.getCompanyName();
        supplier.setVendorname(companyName);
        String companyCode = companyInfo.getCompanyCode();
        supplier.setVendornumber(companyCode);
        supplier.setSourcesyscode(DataSourceEnum.NSRM_SYS.getKey());
        String Id = String.valueOf(companyInfo.getCompanyId());
        supplier.setSourcelineid(Id);
        supplierList.add(supplier);
        requestInfos.setPVENDORTBLITEM(supplierList);
        vendorInfo.setPvendortbl(requestInfos);
        System.out.println("推送供应商信息到Erp request "+ JsonUtil.entityToJsonStr(vendorInfo));
        VendorInfoOutputParameters response = service.erpAcceptVendorsSoapBiz(vendorInfo);
        System.out.println("推送供应商信息到Erp response: "+ JsonUtil.entityToJsonStr(response));
        return response;
    }
}
