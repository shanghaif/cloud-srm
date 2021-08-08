package com.midea.cloud.srm.sup.soap.erp.vendor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.srm.model.supplier.info.entity.BankInfo;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorBank.*;
import com.midea.cloud.srm.sup.SupplierSoapUrl;
import com.midea.cloud.srm.sup.info.service.ICompanyInfoService;
import com.midea.cloud.srm.sup.soap.erp.vendor.service.IVendorBankService;
import com.midea.cloud.srm.sup.soap.erp.vendor.vendorBank.service.ErpAcceptVendorBankSoapBizPtt;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.cxf.endpoint.Client;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  erp的供应商银行信息Service实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/4 17:22
 *  修改内容:
 * </pre>
 */
@Service
public class VendorBankServiceImpl implements IVendorBankService {

    @Resource
    private ICompanyInfoService iCompanyInfoService;

    /** 供应商银行接口访问用户名 **/
    @Value("${SUP_VENDOR.vendorUsername}")
    private  String vendorUsername;
    /** 供应商银行接口访问密码 **/
    @Value("${SUP_VENDOR.vendorPassword}")
    private String vendorPassword;
    /** 银行调用erp接口超时时间(单位毫秒) */
    @Value("${SUP_VENDOR.cxfClientConnectTimeout}")
    public  int cxfClientConnectTimeout;

    @Override
    public VendorBankOutputParameters sendVendorBank(BankInfo bankInfo) {
        // 修改密码接口地址
        String address = SupplierSoapUrl.sendVendorBankUrl;
        // 代理工厂
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        // 设置代理地址
        jaxWsProxyFactoryBean.setAddress(address);
        jaxWsProxyFactoryBean.setUsername(vendorUsername);
        jaxWsProxyFactoryBean.setPassword(vendorPassword);
        // 设置接口类型
        jaxWsProxyFactoryBean.setServiceClass(ErpAcceptVendorBankSoapBizPtt.class);
        // 创建一个代理接口实现
        ErpAcceptVendorBankSoapBizPtt service = (ErpAcceptVendorBankSoapBizPtt) jaxWsProxyFactoryBean.create();
        // 通过代理对象获取本地客户端
        Client proxy = ClientProxy.getClient(service);
        // 通过本地客户端设置 网络策略配置
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
        // 用于配置客户端HTTP端口的属性
        HTTPClientPolicy policy = new HTTPClientPolicy();
        // 超时控制 单位 : 毫秒
        policy.setConnectionTimeout(this.cxfClientConnectTimeout);
        policy.setReceiveTimeout(this.cxfClientConnectTimeout);
        conduit.setClient(policy);
        /**创建一个供应商银行信息请求类 */
        VendorBankInputParameters vendorBankInput = new VendorBankInputParameters();
        APPSCUXPOVENDORBAX1137211X1X1 esbInfo = new APPSCUXPOVENDORBAX1137211X1X1();
        Date nowDate = new Date();
        esbInfo.setInstid("31");
        esbInfo.setRequesttime(String.valueOf(nowDate.getTime()));
        vendorBankInput.setPesbinforec(esbInfo);

        APPSCUXPOVENDORBAX1137211X1X7 requestInfos = new APPSCUXPOVENDORBAX1137211X1X7();
        List<APPSCUXPOVENDORBAX1137211X1X8> vendorBankList = new ArrayList<>();
        APPSCUXPOVENDORBAX1137211X1X8 vendorBank = new APPSCUXPOVENDORBAX1137211X1X8();

        Long erpVendorId = bankInfo.getErpVendorId();
        //设置erp供应商Id
        vendorBank.setErpvendorid(new BigDecimal(erpVendorId));
        CompanyInfo queryCompanyInfo = new CompanyInfo();
        queryCompanyInfo.setErpVendorId(erpVendorId);
        QueryWrapper<CompanyInfo> companyInfoQueryWrapper = new QueryWrapper<>(queryCompanyInfo);
        List<CompanyInfo> companyInfoList = iCompanyInfoService.list(companyInfoQueryWrapper);
        //设置供应商编码
        if(CollectionUtils.isNotEmpty(companyInfoList) && null != companyInfoList.get(0)){
            String vendorCode = companyInfoList.get(0).getCompanyCode();
            vendorBank.setVendornumber(vendorCode);
        }
        //设置银行编码
        String bankCode = bankInfo.getBankCode();
        vendorBank.setBankcode(bankCode);
        //设置银行名称
        String bankName = bankInfo.getBankName();
        vendorBank.setBankname(bankName);
        //设置分行编码
        String branchBankCode = bankInfo.getUnionCode();
        vendorBank.setBranchbankcode(branchBankCode);
        //设置分行名称
        String branchBankName = bankInfo.getOpeningBank();
        vendorBank.setBranchbankname(branchBankName);
        //设置账户名称
        String bankAccountName = bankInfo.getBankAccountName();
        vendorBank.setBankaccountname(bankAccountName);
        //设置账户编码
        String bankAccount = bankInfo.getBankAccount();
        vendorBank.setBankaccountnumber(bankAccount);
        //设置币种
        String currencyCode = bankInfo.getCurrencyCode();
        vendorBank.setCurrencycode(currencyCode);
        //设置是否主账户

        //设置启用状态
        String enable = bankInfo.getCeeaEnabled();
        vendorBank.setEnabledflag(enable);
        //设置备注
        vendorBank.setComments("备注");
        //设置来源系统编码
        vendorBank.setSourcesyscode("NSrm");
        //设置来源系统行Id
        String Id = String.valueOf(bankInfo.getBankInfoId());
        vendorBank.setSourcelineid(Id);
        //设置Attr1 银行所属国家代码
        vendorBank.setAttr1("CN");

        vendorBankList.add(vendorBank);
        requestInfos.setPVENDORBANKTBLITEM(vendorBankList);
        vendorBankInput.setPvendorbanktbl(requestInfos);
        System.out.println("推送供应商银行信息到Erp request "+ JsonUtil.entityToJsonStr(vendorBankInput));
        VendorBankOutputParameters response = service.erpAcceptVendorBankSoapBiz(vendorBankInput);
        System.out.println("推送供应商银行信息到Erp response: "+ JsonUtil.entityToJsonStr(response));
        return response;
    }
}
