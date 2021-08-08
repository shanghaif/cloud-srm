package com.midea.cloud.srm.cm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <pre>
 *  隆基外部系统接口所有url公用类（由于这些url要使用到static方法里面，不能使用yml配置）
 * </pre>
 *
 * @author  ex_lizp6@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/29 18:12
 *  修改内容:
 * </pre>
 */
@Component
public class CmSaopUrl {
    //资产报账url
    public static String acceptOrderUrl;
    //资产报账url
    @Value("${ReceivableTypeInfo.RecUrl}")
    private  String acceptOrderUrlStr;

    @PostConstruct //注释用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化
    public void initAcceptOrderUrlStr(){
        acceptOrderUrl = acceptOrderUrlStr;
    }

    /**合同接口url*/
    public static String contractUrl;
    /**合同接口url*/
    @Value("${CM_USER.cmUrl}")
    public String contractUrlStr;
    @PostConstruct //注释用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化
    public void initContractUrlStr(){
        contractUrl = contractUrlStr;
    }

}
