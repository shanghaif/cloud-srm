package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  总线Header实体类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/14 13:20
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "businessgroup",
        "systemcode",
        "username",
        "password",
        "batchnum",
        "token"
})
public class HeaderRequest {
    @XmlElement(name = "BUSINESS_GROUP", required = true)
    protected String businessgroup;
    @XmlElement(name = "SYSTEM_CODE", required = true)
    protected String systemcode;
    @XmlElement(name = "USER_NAME", required = true)
    protected String username;
    @XmlElement(name = "PASSWORD", required = true)
    protected String password;
    @XmlElement(name = "BATCH_NUM", required = true)
    protected String batchnum;
    @XmlElement(name = "TOKEN", required = true)
    protected String token;
}
