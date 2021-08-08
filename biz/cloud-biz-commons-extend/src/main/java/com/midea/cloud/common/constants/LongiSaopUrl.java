package com.midea.cloud.common.constants;

import org.springframework.beans.factory.annotation.Value;

/**
 * <pre>
 *  隆基外部系统接口所有url公用类（由于这些url要使用到static方法里面，不能使用yml配置）
 * </pre>
 *
 * @author wuwl18@meicloud.com
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
public class LongiSaopUrl {

    /**IDM系统接口--------------start-----------**/
    /**创建主账号接口*/
    public static String createIdmUserUrl = "http://soatest.longi.com:8011/IDMSB/ESB/MainUser/ProxyServices/BpmSendmainUsersSoapProxy?wsdl";
    /**修改密码接口*/
    public static String changePasswordForIdmUser ="http://soatest.longi.com:8011/IDMSB/IDM/UpdatePassWord/ProxyServices/IdmAcceptUpdatePassWordSoapProxy?wsdl";
    /**IDM系统接口--------------end-----------**/



}
