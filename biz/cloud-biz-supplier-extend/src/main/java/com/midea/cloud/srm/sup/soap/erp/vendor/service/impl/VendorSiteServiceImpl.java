package com.midea.cloud.srm.sup.soap.erp.vendor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.SiteInfo;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorSite.*;
import com.midea.cloud.srm.sup.SupplierSoapUrl;
import com.midea.cloud.srm.sup.info.service.ICompanyInfoService;
import com.midea.cloud.srm.sup.info.service.ISiteInfoService;
import com.midea.cloud.srm.sup.soap.erp.vendor.service.IVendorSiteService;
import com.midea.cloud.srm.sup.soap.erp.vendor.vendorSite.service.ErpAcceptVendorSiteSoapBizPtt;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  erp的供应商地点信息Service实现类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/20 11:44
 *  修改内容:
 * </pre>
 */
@Service
public class VendorSiteServiceImpl implements IVendorSiteService {

    @Resource
    private ICompanyInfoService iCompanyInfoService;

    @Resource
    private ISiteInfoService iSiteInfoService;

    /** 供应商地点接口访问用户名 **/
    @Value("${SUP_VENDOR.vendorUsername}")
    private  String vendorUsername;
    /** 供应商地点接口访问密码 **/
    @Value("${SUP_VENDOR.vendorPassword}")
    private String vendorPassword;
    /** 地点调用erp接口超时时间(单位毫秒) */
    @Value("${SUP_VENDOR.cxfClientConnectTimeout}")
    public  int cxfClientConnectTimeout;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VendorSiteOutputParameters sendVendorSite(SiteInfo siteInfo) {
        // 修改密码接口地址
        String address = SupplierSoapUrl.sendVendorSiteUrl;
        // 代理工厂
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        // 设置代理地址
        jaxWsProxyFactoryBean.setAddress(address);
        jaxWsProxyFactoryBean.setUsername(vendorUsername);
        jaxWsProxyFactoryBean.setPassword(vendorPassword);
        // 设置接口类型
        jaxWsProxyFactoryBean.setServiceClass(ErpAcceptVendorSiteSoapBizPtt.class);
        // 创建一个代理接口实现
        ErpAcceptVendorSiteSoapBizPtt service = (ErpAcceptVendorSiteSoapBizPtt) jaxWsProxyFactoryBean.create();
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
        /**创建一个供应商地点信息请求类 */
        VendorSiteInputParameters vendorSiteInput = new VendorSiteInputParameters();
        APPSCUXPOVENDORSIX1137207X1X1 esbInfo = new APPSCUXPOVENDORSIX1137207X1X1();
        Date nowDate = new Date();
        esbInfo.setInstid("41");
        esbInfo.setRequesttime(String.valueOf(nowDate.getTime()));
        vendorSiteInput.setPesbinforec(esbInfo);

        APPSCUXPOVENDORSIX1137207X1X7 requestInfos = new APPSCUXPOVENDORSIX1137207X1X7();
        List<APPSCUXPOVENDORSIX1137207X1X8> vendorSiteList = new ArrayList<>();
        APPSCUXPOVENDORSIX1137207X1X8 vendorSite = new APPSCUXPOVENDORSIX1137207X1X8();

        //开始设置供应商地点字段

        Long erpVendorId = siteInfo.getErpVendorId();
        //设置erp供应商Id erpVendorId
        vendorSite.setErpvendorid(new BigDecimal(erpVendorId));
        CompanyInfo queryCompanyInfo = new CompanyInfo();
        queryCompanyInfo.setErpVendorId(erpVendorId);
        QueryWrapper<CompanyInfo> companyInfoQueryWrapper = new QueryWrapper<>(queryCompanyInfo);
        List<CompanyInfo> companyInfoList = iCompanyInfoService.list(companyInfoQueryWrapper);
        //设置供应商编码
        if(CollectionUtils.isNotEmpty(companyInfoList) && null != companyInfoList.get(0)){
            String vendorCode = companyInfoList.get(0).getCompanyCode();
            vendorSite.setVendornumber(vendorCode);
        }

        BeanUtils.copyProperties(siteInfo, vendorSite);
        String country = siteInfo.getCountry();
        Assert.notNull(country, "国家不能为空！");

        //设置address_detail
        String addressDetail = siteInfo.getAddressDetail();
        Assert.notNull(addressDetail, "详细地址不能为空！");
        vendorSite.setAddressdetail(addressDetail);
        //设置Address_Name 地址名称
        //String addressName = siteInfo.getAddressName();
        //Assert.notNull(addressName, "存在地点的地址名称为空，请完善地点的地址名称！");
        //vendorSite.setAddressname(addressName);

        //设置vendor_site_code 地点名称
        String vendorSiteCode = siteInfo.getVendorSiteCode();

        if(StringUtils.isEmpty(vendorSiteCode)){
            throw new BaseException("存在地点的地点名称为空，请完善地点的地点名称！");
        }
        vendorSite.setVendorsitecode(vendorSiteCode);
        vendorSite.setAddressname(vendorSiteCode);

        //设置所属业务实体Id（推送Erp地点时需要设置所属的业务实体Id）
        Long orgId = siteInfo.getOrgId();
        String belongOprId = siteInfo.getBelongOprId();
        Assert.notNull(belongOprId, "所选的业务实体erp业务实体Id为空！");
        vendorSite.setBelongoprid(new BigDecimal(belongOprId));
        //设置可采购 Y
        String purchaseFlag = siteInfo.getPurchaseFlag();
        vendorSite.setPurchaseflag(purchaseFlag);
        //设置可付款 Y
        String paymentFlag = siteInfo.getPaymentFlag();
        vendorSite.setPaymentflag(paymentFlag);
        //设置仅限于询价 N
        String rfqOnlyFlag = siteInfo.getRfqOnlyFlag();
        vendorSite.setRfqonlyflag(rfqOnlyFlag);
        //设置启用状态 Y
        String enabledFlag = siteInfo.getEnabledFlag();
        vendorSite.setEnabledflag(enabledFlag);
        //设置来源系统编码
        vendorSite.setSourcesyscode("NSrm");
        //设置来源系统行Id
        String Id = String.valueOf(siteInfo.getSiteInfoId());
        vendorSite.setSourcelineid(Id);
        vendorSiteList.add(vendorSite);

        requestInfos.setPvendorsitetblitem(vendorSiteList);
        vendorSiteInput.setPvendorsitetbl(requestInfos);
        System.out.println("供应商地点推送到Erp request "+ JsonUtil.entityToJsonStr(vendorSite));
        VendorSiteOutputParameters response = service.erpAcceptVendorSiteSoapBiz(vendorSiteInput);
        //将推送成功的供应商地点数据保存到数据库
//        String error = "";
//        String siteInfoReturnStatus = (null != response.getXesbresultinforec() ? response.getXesbresultinforec().getReturnstatus() : "E");
//        //如果推送成功
//        if ("S".equals(siteInfoReturnStatus)) {
//            SiteInfo existSiteInfo = iSiteInfoService.getById(siteInfo.getSiteInfoId());
//            if (existSiteInfo.getSiteInfoId() == null) {
//                iSiteInfoService.save(siteInfo);
//            }else{
//                iSiteInfoService.updateById(siteInfo);
//            }
//        }
//        else if ("E".equals(siteInfoReturnStatus)) {
//            error = "推送供应商地点信息出错";
//            throw new BaseException(error);
//        }
        System.out.println("供应商地点推送到Erp response: "+ JsonUtil.entityToJsonStr(response));
        return response;
    }
}
