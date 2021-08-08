package com.midea.cloud.srm.model.base.organization.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDate;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  业务实体接口表 模型
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
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_itf_fnd_business_units")
public class BusinessUnits extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("ITF_HEADER_ID")
    private Long itfHeaderId;

    /**
     * 企业集团
     */
    @TableField("BUSINESS_GROUP")
    private String businessGroup;

    /**
     * 企业系统
     */
    @TableField("EXTERNAL_SYSTEM_CODE")
    private String externalSystemCode;

    /**
     * 段号
     */
    @TableField("SEG_NUM")
    private BigDecimal segNum;

    /**
     * 接口实例id
     */
    @TableField("ITF_INSTANCE_ID")
    private BigDecimal itfInstanceId;

    /**
     * 状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 完成标识
     */
    @TableField("FINISHED_FLAG")
    private String finishedFlag;

    /**
     * 出错标识
     */
    @TableField("ERROR_FLAG")
    private String errorFlag;

    /**
     * 消息文本
     */
    @TableField("MESSAGE_TEXT")
    private String messageText;

    /**
     * 业务实体ID
     */
    @TableField("ES_BUSINESS_UNIT_ID")
    private String esBusinessUnitId;

    /**
     * 业务实体编码
     */
    @TableField("ES_BUSINESS_UNIT_CODE")
    private String esBusinessUnitCode;

    /**
     * 业务实体简称
     */
    @TableField("BUSINESS_SHORT_NAME")
    private String businessShortName;

    /**
     * 业务实体全称
     */
    @TableField("BUSINESS_FULL_NAME")
    private String businessFullName;

    /**
     * 启用标识
     */
    @TableField("ENABLED_FLAG")
    private String enabledFlag;

    /**
     * 错误类型
     */
    @TableField("ERROR_TYPE")
    private String errorType;

    /**
     * 错误次数
     */
    @TableField("ERROR_TIMES")
    private BigDecimal errorTimes;

    @TableField("C_ATTRIBUTE1")
    private String cAttribute1;

    @TableField("C_ATTRIBUTE2")
    private String cAttribute2;

    @TableField("C_ATTRIBUTE3")
    private String cAttribute3;

    @TableField("C_ATTRIBUTE4")
    private String cAttribute4;

    @TableField("C_ATTRIBUTE5")
    private String cAttribute5;

    @TableField("C_ATTRIBUTE6")
    private String cAttribute6;

    @TableField("C_ATTRIBUTE7")
    private String cAttribute7;

    @TableField("C_ATTRIBUTE8")
    private String cAttribute8;

    @TableField("C_ATTRIBUTE9")
    private String cAttribute9;

    @TableField("C_ATTRIBUTE10")
    private String cAttribute10;

    @TableField("C_ATTRIBUTE11")
    private String cAttribute11;

    @TableField("C_ATTRIBUTE12")
    private String cAttribute12;

    @TableField("C_ATTRIBUTE13")
    private String cAttribute13;

    @TableField("C_ATTRIBUTE14")
    private String cAttribute14;

    @TableField("C_ATTRIBUTE15")
    private String cAttribute15;

    @TableField("C_ATTRIBUTE16")
    private String cAttribute16;

    @TableField("C_ATTRIBUTE17")
    private String cAttribute17;

    @TableField("C_ATTRIBUTE18")
    private String cAttribute18;

    @TableField("C_ATTRIBUTE19")
    private String cAttribute19;

    @TableField("C_ATTRIBUTE20")
    private String cAttribute20;

    @TableField("C_ATTRIBUTE21")
    private String cAttribute21;

    @TableField("C_ATTRIBUTE22")
    private String cAttribute22;

    @TableField("C_ATTRIBUTE23")
    private String cAttribute23;

    @TableField("C_ATTRIBUTE24")
    private String cAttribute24;

    @TableField("C_ATTRIBUTE25")
    private String cAttribute25;

    @TableField("C_ATTRIBUTE26")
    private String cAttribute26;

    @TableField("C_ATTRIBUTE27")
    private String cAttribute27;

    @TableField("C_ATTRIBUTE28")
    private String cAttribute28;

    @TableField("C_ATTRIBUTE29")
    private String cAttribute29;

    @TableField("C_ATTRIBUTE30")
    private String cAttribute30;

    @TableField("C_ATTRIBUTE31")
    private String cAttribute31;

    @TableField("C_ATTRIBUTE32")
    private String cAttribute32;

    @TableField("C_ATTRIBUTE33")
    private String cAttribute33;

    @TableField("C_ATTRIBUTE34")
    private String cAttribute34;

    @TableField("C_ATTRIBUTE35")
    private String cAttribute35;

    @TableField("C_ATTRIBUTE36")
    private String cAttribute36;

    @TableField("C_ATTRIBUTE37")
    private String cAttribute37;

    @TableField("C_ATTRIBUTE38")
    private String cAttribute38;

    @TableField("C_ATTRIBUTE39")
    private String cAttribute39;

    @TableField("C_ATTRIBUTE40")
    private String cAttribute40;

    @TableField("C_ATTRIBUTE41")
    private String cAttribute41;

    @TableField("C_ATTRIBUTE42")
    private String cAttribute42;

    @TableField("C_ATTRIBUTE43")
    private String cAttribute43;

    @TableField("C_ATTRIBUTE44")
    private String cAttribute44;

    @TableField("C_ATTRIBUTE45")
    private String cAttribute45;

    @TableField("C_ATTRIBUTE46")
    private String cAttribute46;

    @TableField("C_ATTRIBUTE47")
    private String cAttribute47;

    @TableField("C_ATTRIBUTE48")
    private String cAttribute48;

    @TableField("C_ATTRIBUTE49")
    private String cAttribute49;

    @TableField("C_ATTRIBUTE50")
    private String cAttribute50;

    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    @TableField("TYPE")
    private String type;

    @TableField("TYPE_NAME")
    private String typeName;

    @TableField("ES_LOCATION_ID")
    private String esLocationId;

    @TableField("ES_LOACTION_NAME")
    private String esLoactionName;

    @TableField("COMPANY_NAME")
    private String companyName;

    /**
     * 生效日期
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效日期
     */
    @TableField("END_DATE")
    private LocalDate endDate;


}
