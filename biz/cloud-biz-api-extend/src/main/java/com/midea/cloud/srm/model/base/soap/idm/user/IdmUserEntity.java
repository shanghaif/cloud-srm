package com.midea.cloud.srm.model.base.soap.idm.user;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  隆基IDm接口用户实体类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/24 15:03
 *  修改内容:
 * </pre>
 *
 */
@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {
        "emplId","newPassword","oldPassword","attr1","attr2","attr3","attr4","attr5"
})
public class IdmUserEntity {

    /**以下字段是修改Idm密码*/
    @XmlElement(name = "EMPLID", required = false)
    private String emplId;  //用户名
    @XmlElement(name = "NEW_PASSWORD", required = false)
    private String newPassword;  //新的密码
    @XmlElement(name = "OLD_PASSWORD", required = false)
    private String oldPassword;  //旧密码
    @XmlElement(name = "ATTR1", required = false)
    private String attr1;	 //	备用字段1
    @XmlElement(name = "ATTR2", required = false)
    private String attr2;	 //	备用字段2
    @XmlElement(name = "ATTR3", required = false)
    private String attr3;	 //	备用字段3
    @XmlElement(name = "ATTR4", required = false)
    private String attr4;	 //	备用字段4
    @XmlElement(name = "ATTR5", required = false)
    private String attr5;  //备用字段5

}
