package com.midea.cloud.srm.sup.soap.erp.vendor.controller;

import com.midea.cloud.common.constants.RepushConst;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.srm.model.base.repush.entity.RepushStatus;
import com.midea.cloud.srm.model.supplier.info.entity.BankInfo;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.ContactInfo;
import com.midea.cloud.srm.model.supplier.info.entity.SiteInfo;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorBank.VendorBankInputParameters;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorBank.VendorBankOutputParameters;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorContact.VendorContactOutputParameters;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorInfo.VendorInfoOutputParameters;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorSite.VendorSiteOutputParameters;
import com.midea.cloud.srm.sup.info.service.IBankInfoService;
import com.midea.cloud.srm.sup.info.service.IContactInfoService;
import com.midea.cloud.srm.sup.info.service.ISiteInfoService;
import com.midea.cloud.srm.sup.info.service.impl.PushSiteInfoCallBack;
import com.midea.cloud.srm.sup.soap.erp.vendor.service.IVendorBankService;
import com.midea.cloud.srm.sup.soap.erp.vendor.service.IVendorContactService;
import com.midea.cloud.srm.sup.soap.erp.vendor.service.IVendorInfoService;
import com.midea.cloud.srm.sup.soap.erp.vendor.service.IVendorSiteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

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
 *  修改日期: 2020/9/4 11:10
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/erp/vendor")
@Slf4j
public class VendorSendController {

    @Resource
    private IVendorInfoService iVendorInfoService;

    @Resource
    private IVendorContactService iVendorContactService;

    @Resource
    private IContactInfoService iContactInfoService;

    @Resource
    private IVendorBankService iVendorBankService;

    @Resource
    private IBankInfoService iBankInfoService;

    @Resource
    private IVendorSiteService iVendorSiteService;

    @Resource
    private ISiteInfoService iSiteInfoService;

    /**
     * NSrm向Erp推送供应商基础信息
     * @param companyInfo
     * @return
     */
    @PostMapping("/sendVendorInfo")
    public VendorInfoOutputParameters sendVendorInfo(@RequestBody CompanyInfo companyInfo) {
        VendorInfoOutputParameters response = iVendorInfoService.sendVendorInfo(companyInfo);
        return response;
    }

    /**
     * NSrm向Erp推送供应商联系人信息
     * @param contactInfo
     * @return
     */
    @PostMapping("/sendVendorContact")
    public VendorContactOutputParameters sendVendorContact(@RequestBody ContactInfo contactInfo) {
        VendorContactOutputParameters response = iVendorContactService.sendVendorContact(contactInfo);
        return response;
    }

    /**
     * 根据供应商联系人信息id推送这一条供应商联系人数据到erp
     */
    @PostMapping("/sendVendorContactByContactInfoIds")
    public String sendVendorContactByContactInfoIds(@RequestBody List<Long> contactInfoIdList) {
        Assert.isTrue(CollectionUtils.isNotEmpty(contactInfoIdList), "供应商联系人id集合不能为空.");
        StringBuilder resultSrt = new StringBuilder();
        for(Long contactInfoId : contactInfoIdList){
            if(null != contactInfoId){
                ContactInfo contactInfo = iContactInfoService.getById(contactInfoId);
                VendorContactOutputParameters  vendorOutput = iVendorContactService.sendVendorContact(contactInfo);
                String vendorReturnStatus = "";
                if (null != vendorOutput) {
                    vendorReturnStatus = null != vendorOutput.getXesbresultinforec() ? vendorOutput.getXesbresultinforec().getReturnstatus() : "E";
                    if ("S".equals(vendorReturnStatus)) {
                        log.info("供应商联系人Id为" + contactInfoId + ",推送供应联系人信息成功！");
                    } else {
                        resultSrt.append(contactInfoId + ",");
                        log.error("供应商联系人Id为" + contactInfoId + ",推送供应联系人信息失败！失败信息: " + JsonUtil.entityToJsonStr(vendorOutput.getXesbresultinforec().getReturnmsg()));
                    }
                } else {
                    log.error("供应商联系人Id为" + contactInfoId + ",推送供应商联系人信息失败！失败信息为空");
                    resultSrt.append(contactInfoId + ",");
                }
            }
        }
        if(StringUtils.isNotBlank(resultSrt.toString())){
            return "供应商联系人Id集合为: "+resultSrt.toString()+" 推送供应商联系人信息失败！";
        }
        return "操作成功";

    }

    /**
     * Nsrm向Erp推送供应商银行信息
     * @param bankInfo
     * @return
     */
    @RequestMapping("/sendVendorBank")
    public VendorBankOutputParameters sendVendorBank(@RequestBody BankInfo bankInfo) {
        VendorBankOutputParameters response = iVendorBankService.sendVendorBank(bankInfo);
        return response;
    }

    /**
     * 根据供应商银行信息id推送这一条供应商银行数据到erp
     */
    @PostMapping("/sendVendorBankByBankInfoIds")
    public String sendVendorBankByBankInfoIds(@RequestBody List<Long> bankInfoIdList) {
        Assert.isTrue(CollectionUtils.isNotEmpty(bankInfoIdList), "供应商银行id集合不能为空.");
        StringBuilder resultSrt = new StringBuilder();
        for(Long bankInfoId : bankInfoIdList){
            if(null != bankInfoId){
                BankInfo bankInfo = iBankInfoService.getById(bankInfoId);
                VendorBankOutputParameters vendorOutput = iVendorBankService.sendVendorBank(bankInfo);
                String vendorReturnStatus = "";
                if (null != vendorOutput) {
                    vendorReturnStatus = null != vendorOutput.getXesbresultinforec() ? vendorOutput.getXesbresultinforec().getReturnstatus() : "E";
                    if ("S".equals(vendorReturnStatus)) {
                        log.info("供应商银行Id为" + bankInfoId + ",推送供应银行信息成功！");
                    } else {
                        resultSrt.append(bankInfoId + ",");
                        log.error("供应商银行Id为" + bankInfoId + ",推送供应银行点息失败！失败信息: " + JsonUtil.entityToJsonStr(vendorOutput.getXesbresultinforec().getReturnmsg()));
                    }
                } else {
                    log.error("供应商银行Id为" + bankInfoId + ",推送供应商银行信息失败！失败信息为空");
                    resultSrt.append(bankInfoId + ",");
                }
            }
        }
        if(StringUtils.isNotBlank(resultSrt.toString())){
            return "供应商银行Id集合为: "+resultSrt.toString()+" 推送供应商银行信息失败！";
        }
        return "操作成功";

    }

    /**
     * NSrm向Erp推送供应商地点信息
     * @param siteInfo
     * @return
     */
    @RequestMapping("/sendVendorSite")
    public VendorSiteOutputParameters sendVendorSite(@RequestBody SiteInfo siteInfo) {
        VendorSiteOutputParameters response = iVendorSiteService.sendVendorSite(siteInfo);
        return response;


    }

    /**
     * 根据供应商地点id推送这一条地点数据到erp
     */
    @PostMapping("/sendVendorSiteBySiteInfoIds")
    public String sendVendorSiteBySiteInfoIds(@RequestBody List<Long> siteInfoIdList) {
        Assert.isTrue(CollectionUtils.isNotEmpty(siteInfoIdList), "供应商地点id集合不能为空.");
        StringBuilder resultSrt = new StringBuilder();
        for(Long siteInfoId : siteInfoIdList){
            if(null != siteInfoId) {
                SiteInfo siteInfo = iSiteInfoService.getById(siteInfoId);
                VendorSiteOutputParameters vendorSiteOutput = iVendorSiteService.sendVendorSite(siteInfo);
                String vendorSiteReturnStatus = "";
                if (null != vendorSiteOutput) {
                    vendorSiteReturnStatus = null != vendorSiteOutput.getXesbresultinforec() ? vendorSiteOutput.getXesbresultinforec().getReturnstatus() : "E";
                    if ("S".equals(vendorSiteReturnStatus)) {
                        log.info("供应商地点Id为" + siteInfoId + ",推送供应商地点信息成功！");
                    } else {
                        resultSrt.append(siteInfoId + ",");
                        log.error("供应商地点Id为" + siteInfoId + ",推送供应商地点信息失败！失败信息: " + JsonUtil.entityToJsonStr(vendorSiteOutput.getXesbresultinforec().getReturnmsg()));
                    }
                } else {
                    log.error("供应商地点Id为" + siteInfoId + ",推送供应商地点信息失败！失败信息为空");
                    resultSrt.append(siteInfoId + ",");
                }
            }
        }
        if(StringUtils.isNotBlank(resultSrt.toString())){
            return "供应商地点Id集合为: "+resultSrt.toString()+" 推送供应商地点信息失败！";
        }
        return "操作成功";
    }

}
