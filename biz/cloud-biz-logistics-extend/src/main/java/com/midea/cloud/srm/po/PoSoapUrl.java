package com.midea.cloud.srm.po;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <pre>
 *  PO模块-隆基外部系统接口所有url公用类（由于这些url要使用到static方法里面，不能使用yml配置）
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
public class PoSoapUrl {

    /**订单变更接口Url*/
    @Value("${SOAP_URL.URL_EDIT_PURCHASE_ORDER}")
    public static String urlEditPurchaseOrder;

    /**订单变更接口Url*/
    public String urlEditPurchaseOrderStr;

    @PostConstruct //注释用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化
    public void initUrlEditPurchaseOrder(){
        urlEditPurchaseOrder = urlEditPurchaseOrderStr;
    }
}
