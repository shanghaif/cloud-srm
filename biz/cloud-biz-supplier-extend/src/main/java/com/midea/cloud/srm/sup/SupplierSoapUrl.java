package com.midea.cloud.srm.sup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <pre>
 * 供应商模块-隆基外部系统接口url公用类（由于这些url要使用到static方法里面，不能使用yml配置）
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/31
 *  修改内容:
 * </pre>
 */
@Component
public class SupplierSoapUrl {

    /** 供应商银行接口访问url **/
    public static String sendVendorBankUrl;

    /** 供应商银行接口访问url **/
    @Value("${SUP_VENDOR.sendVendorBankUrl}")
    public String sendVendorBankUrlStr;

    @PostConstruct //注释用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化
    public void initSendVendorBankUrl(){
        sendVendorBankUrl = sendVendorBankUrlStr;
    }

    /** 供应商联系人接口访问url **/
    public static String sendVendorContactUrl;

    /** 供应商联系人接口访问url **/
    @Value("${SUP_VENDOR.sendVendorContactUrl}")
    public String sendVendorContactUrlStr;

    @PostConstruct //注释用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化
    public void initSendVendorContactUrl(){
        sendVendorContactUrl = sendVendorContactUrlStr;
    }

    /** 供应商地点接口访问url **/
    public static String sendVendorSiteUrl;

    @Value("${SUP_VENDOR.sendVendorSiteUrl}")
    public String sendVendorSiteUrlStr;

    @PostConstruct //注释用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化
    public void initSendVendorSiteUrl(){
        sendVendorSiteUrl = sendVendorSiteUrlStr;
    }

    /** 供应商基础信息接口访问url **/
    public static String sendVendorInfoUrl;

    /** 供应商基础信息接口访问url **/
    @Value("${SUP_VENDOR.sendVendorInfoUrl}")
    public String sendVendorInfoUrlStr;

    @PostConstruct //注释用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化
    public void initSendVendorInfoUrl(){
        sendVendorInfoUrl = sendVendorInfoUrlStr;
    }
}