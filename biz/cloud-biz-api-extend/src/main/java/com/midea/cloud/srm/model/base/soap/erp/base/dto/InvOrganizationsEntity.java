package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;

/**
 *  <pre>
 *  库存组织接口表实体类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-04 15:39:50
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "itfHeaderId","businessGroup","externalSystemCode","segNum","itfInstanceId","status","finishedFlag","errorFlag",
        "messageText","esInvOrganizationId","esInvOrganizationCode","invOrganizationName","esBusinessUnitId","esBusinessUnitCode",
        "enabledFlag","errorType","errorTimes","cAttribute1","cAttribute2","cAttribute3","cAttribute4","cAttribute5","cAttribute6",
        "cAttribute7","cAttribute8","cAttribute9","cAttribute10","cAttribute11","cAttribute12","cAttribute13","cAttribute14",
        "cAttribute15","cAttribute16","cAttribute17","cAttribute18","cAttribute19","cAttribute20","cAttribute21","cAttribute22",
        "cAttribute23","cAttribute24","cAttribute25","cAttribute26","cAttribute27","cAttribute28","cAttribute29","cAttribute30",
        "cAttribute31","cAttribute32","cAttribute33","cAttribute34","cAttribute35","cAttribute36","cAttribute37","cAttribute38",
        "cAttribute39","cAttribute40","cAttribute41","cAttribute42","cAttribute43","cAttribute44","cAttribute45","cAttribute46",
        "cAttribute47","cAttribute48","cAttribute49","cAttribute50","creationDate","createdBy","lastUpdateDate","lastUpdatedBy",
        "address","esLocationId","esLocationName","orgTypeCode","orgTypeName","objectVersionNum","languageCode","extendAttributes","startDate","endDate"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class InvOrganizationsEntity {

    /**
     * PK值
     * XmlElement 字段和方法级别的注解。该注解会将字段或get/set方法对应的字段映射成本类对应元素的属性,name
     * 用于指定映射时的节点属性名称，若不指定，默认使用方法名小写作为元素名
     */
    @XmlElement(name = "ITF_HEADER_ID", required = false)
    protected String itfHeaderId;

    /**
     * 企业集团
     */
    @XmlElement(name = "BUSINESS_GROUP", required = false)
    protected String businessGroup;

    /**
     * 企业系统code
     */
    @XmlElement(name = "EXTERNAL_SYSTEM_CODE", required = false)
    protected String externalSystemCode;

    /**
     * 段号
     */
    @XmlElement(name = "SEG_NUM", required = false)
    protected String segNum;

    /**
     * 接口实例id
     */
    @XmlElement(name = "ITF_INSTANCE_ID", required = false)
    protected String itfInstanceId;

    /**
     * 状态
     */
    @XmlElement(name = "STATUS", required = false)
    protected String status;

    /**
     * 完成标识
     */
    @XmlElement(name = "FINISHED_FLAG", required = false)
    protected String finishedFlag;

    /**
     * 出错标识
     */
    @XmlElement(name = "ERROR_FLAG", required = false)
    protected String errorFlag;

    /**
     * 消息文本
     */
    @XmlElement(name = "MESSAGE_TEXT", required = false)
    protected String messageText;

    /**
     * 库存组织ID
     */
    @XmlElement(name = "ES_INV_ORGANIZATION_ID", required = false)
    protected String esInvOrganizationId;

    /**
     * 库存组织编码
     */
    @XmlElement(name = "ES_INV_ORGANIZATION_CODE", required = false)
    protected String esInvOrganizationCode;

    /**
     * 库存组织名称
     */
    @XmlElement(name = "INV_ORGANIZATION_NAME", required = false)
    protected String invOrganizationName;

    /**
     * 业务实体ID
     */
    @XmlElement(name = "ES_BUSINESS_UNIT_ID", required = false)
    protected String esBusinessUnitId;

    /**
     * 业务实体编码
     */
    @XmlElement(name = "ES_BUSINESS_UNIT_CODE", required = false)
    protected String esBusinessUnitCode;

    /**
     * 启用标识
     */
    @XmlElement(name = "ENABLED_FLAG", required = false)
    protected String enabledFlag;

    /**
     * 错误类型
     */
    @XmlElement(name = "ERROR_TYPE", required = false)
    protected String errorType;

    /**
     * 错误次数
     */
    @XmlElement(name = "ERROR_TIMES", required = false)
    protected String errorTimes;

    @XmlElement(name = "C_ATTRIBUTE1", required = false)
    protected String cAttribute1;

    @XmlElement(name = "C_ATTRIBUTE2", required = false)
    protected String cAttribute2;

    @XmlElement(name = "C_ATTRIBUTE3", required = false)
    protected String cAttribute3;

    @XmlElement(name = "C_ATTRIBUTE4", required = false)
    protected String cAttribute4;

    @XmlElement(name = "C_ATTRIBUTE5", required = false)
    protected String cAttribute5;

    @XmlElement(name = "C_ATTRIBUTE6", required = false)
    protected String cAttribute6;

    @XmlElement(name = "C_ATTRIBUTE7", required = false)
    protected String cAttribute7;

    @XmlElement(name = "C_ATTRIBUTE8", required = false)
    protected String cAttribute8;

    @XmlElement(name = "C_ATTRIBUTE9", required = false)
    protected String cAttribute9;

    @XmlElement(name = "C_ATTRIBUTE10", required = false)
    protected String cAttribute10;

    @XmlElement(name = "C_ATTRIBUTE11", required = false)
    protected String cAttribute11;

    @XmlElement(name = "C_ATTRIBUTE12", required = false)
    protected String cAttribute12;

    @XmlElement(name = "C_ATTRIBUTE13", required = false)
    protected String cAttribute13;

    @XmlElement(name = "C_ATTRIBUTE14", required = false)
    protected String cAttribute14;

    @XmlElement(name = "C_ATTRIBUTE15", required = false)
    protected String cAttribute15;

    @XmlElement(name = "C_ATTRIBUTE16", required = false)
    protected String cAttribute16;

    @XmlElement(name = "C_ATTRIBUTE17", required = false)
    protected String cAttribute17;

    @XmlElement(name = "C_ATTRIBUTE18", required = false)
    protected String cAttribute18;

    @XmlElement(name = "C_ATTRIBUTE19", required = false)
    protected String cAttribute19;

    @XmlElement(name = "C_ATTRIBUTE20", required = false)
    protected String cAttribute20;

    @XmlElement(name = "C_ATTRIBUTE21", required = false)
    protected String cAttribute21;

    @XmlElement(name = "C_ATTRIBUTE22", required = false)
    protected String cAttribute22;

    @XmlElement(name = "C_ATTRIBUTE23", required = false)
    protected String cAttribute23;

    @XmlElement(name = "C_ATTRIBUTE24", required = false)
    protected String cAttribute24;

    @XmlElement(name = "C_ATTRIBUTE25", required = false)
    protected String cAttribute25;

    @XmlElement(name = "C_ATTRIBUTE26", required = false)
    protected String cAttribute26;

    @XmlElement(name = "C_ATTRIBUTE27", required = false)
    protected String cAttribute27;

    @XmlElement(name = "C_ATTRIBUTE28", required = false)
    protected String cAttribute28;

    @XmlElement(name = "C_ATTRIBUTE29", required = false)
    protected String cAttribute29;

    @XmlElement(name = "C_ATTRIBUTE30", required = false)
    protected String cAttribute30;

    @XmlElement(name = "C_ATTRIBUTE31", required = false)
    protected String cAttribute31;

    @XmlElement(name = "C_ATTRIBUTE32", required = false)
    protected String cAttribute32;

    @XmlElement(name = "C_ATTRIBUTE33", required = false)
    protected String cAttribute33;

    @XmlElement(name = "C_ATTRIBUTE34", required = false)
    protected String cAttribute34;

    @XmlElement(name = "C_ATTRIBUTE35", required = false)
    protected String cAttribute35;

    @XmlElement(name = "C_ATTRIBUTE36", required = false)
    protected String cAttribute36;

    @XmlElement(name = "C_ATTRIBUTE37", required = false)
    protected String cAttribute37;

    @XmlElement(name = "C_ATTRIBUTE38", required = false)
    protected String cAttribute38;

    @XmlElement(name = "C_ATTRIBUTE39", required = false)
    protected String cAttribute39;

    @XmlElement(name = "C_ATTRIBUTE40", required = false)
    protected String cAttribute40;

    @XmlElement(name = "C_ATTRIBUTE41", required = false)
    protected String cAttribute41;

    @XmlElement(name = "C_ATTRIBUTE42", required = false)
    protected String cAttribute42;

    @XmlElement(name = "C_ATTRIBUTE43", required = false)
    protected String cAttribute43;

    @XmlElement(name = "C_ATTRIBUTE44", required = false)
    protected String cAttribute44;

    @XmlElement(name = "C_ATTRIBUTE45", required = false)
    protected String cAttribute45;

    @XmlElement(name = "C_ATTRIBUTE46", required = false)
    protected String cAttribute46;

    @XmlElement(name = "C_ATTRIBUTE47", required = false)
    protected String cAttribute47;

    @XmlElement(name = "C_ATTRIBUTE48", required = false)
    protected String cAttribute48;

    @XmlElement(name = "C_ATTRIBUTE49", required = false)
    protected String cAttribute49;

    @XmlElement(name = "C_ATTRIBUTE50", required = false)
    protected String cAttribute50;

    @XmlElement(name = "CREATION_DATE", required = false)
    protected String creationDate;

    @XmlElement(name = "CREATED_BY", required = false)
    protected String createdBy;

    @XmlElement(name = "LAST_UPDATE_DATE", required = false)
    protected String lastUpdateDate;

    @XmlElement(name = "LAST_UPDATED_BY", required = false)
    protected String lastUpdatedBy;

    @XmlElement(name = "ADDRESS", required = false)
    protected String address;

    @XmlElement(name = "ES_LOCATION_ID", required = false)
    protected String esLocationId;

    @XmlElement(name = "ES_LOCATION_NAME", required = false)
    protected String esLocationName;

    /**以下是库存组织接口的属性，现在还没有放到srm库存组织表*/
    @XmlElement(name ="ORG_TYPE_CODE", required = false)
    private String orgTypeCode; //组织类型代码
    @XmlElement(name ="ORG_TYPE_NAME", required = false)
    private String orgTypeName;	//组织类型名称
    @XmlElement(name ="OBJECT_VERSION_NUM", required = false)
    private String objectVersionNum;	//对象版本号
    @XmlElement(name ="LANGUAGE_CODE", required = false)
    private String languageCode;	//	语言代码
    @XmlElement(name ="EXTEND_ATTRIBUTES", required = false)
    private String extendAttributes;	//库存组织属性
    @XmlElement(name ="START_DATE", required = false)
    private String startDate;	//起始日期
    @XmlElement(name ="END_DATE", required = false)
    private String endDate;	    //截止日期
}
