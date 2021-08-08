package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;
import java.util.Date;

/**
*  <pre>
 *  地址接口表 模型
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-04 15:38:43
 *  修改内容:
 * </pre>
*/
@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder = {
        "locationId","locationName","locationDescr","legalFlag","addrDetail","timeZone","contractor","shipToLocationId",
        "shipToLocationName","shipToFlag","receiveFlag","officialFlag","billFalg","internalFlag","organizationId",
        "organizationCode","attr1","attr2","attr3","attr4","attr5"
})
public class LocationsEntity {
    /**
     * 地点Id
     */
    @XmlElement(name = "LOCATION_ID", required = false)
    private String locationId;

    /**
     * 地点名称
     */
    @XmlElement(name = "LOCATION_NAME", required = false)
    private String locationName;

    /**
     * 地点说明
     */
    @XmlElement(name = "LOCATION_DESCR", required = false)
    private String locationDescr;

    /**
     * 法定地点
     */
    @XmlElement(name = "LEGAL_FLAG", required = false)
    private String legalFlag;

    /**
     * 地址
     */
    @XmlElement(name = "ADDR_DETAIL", required = false)
    private String addrDetail;

    /**
     * 时区
     */
    @XmlElement(name = "TIME_ZONE", required = false)
    private String timeZone;

    /**
     * 联系人
     */
    @XmlElement(name = "CONTRACTOR", required = false)
    private String contractor;

    /**
     * 收货地点Id
     */
    @XmlElement(name = "SHIP_TO_LOCATION_ID", required = false)
    private String shipToLocationId;

    /**
     * 收货地点名称
     */
    @XmlElement(name = "SHIP_TO_LOCATION_NAME", required = false)
    private String shipToLocationName;

    /**
     * 收货地点
     */
    @XmlElement(name = "SHIP_TO_FLAG", required = false)
    private String shipToFlag;

    /**
     * 接收地点
     */
    @XmlElement(name = "RECEIVE_FLAG", required = false)
    private String receiveFlag;

    /**
     * 办公地点
     */
    @XmlElement(name = "OFFICIAL_FLAG", required = false)
    private String officialFlag;

    /**
     * 收单地点
     */
    @XmlElement(name = "BILL_FALG", required = false)
    private String billFalg;

    /**
     * 内部地点
     */
    @XmlElement(name = "INTERNAL_FLAG", required = false)
    private String internalFlag;

    /**
     * 库存组织Id
     */
    @XmlElement(name = "ORGANIZATION_ID", required = false)
    private String organizationId;

    /**
     * 库存组织代码
     */
    @XmlElement(name = "ORGANIZATION_CODE", required = false)
    private String organizationCode;

    /**
     * 备用字段1
     */
    @XmlElement(name = "ATTR1", required = false)
    private String attr1;

    /**
     * 备用字段2
     */
    @XmlElement(name = "ATTR2", required = false)
    private String attr2;

    /**
     * 备用字段3
     */
    @XmlElement(name = "ATTR3", required = false)
    private String attr3;

    /**
     * 备用字段4
     */
    @XmlElement(name = "ATTR4", required = false)
    private String attr4;

    /**
     * 备用字段5
     */
    @XmlElement(name = "ATTR5", required = false)
    private String attr5;

}
