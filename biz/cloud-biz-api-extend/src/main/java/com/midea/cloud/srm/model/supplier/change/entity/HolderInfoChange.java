package com.midea.cloud.srm.model.supplier.change.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.util.Map;

import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  股东信息变更 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-28 14:13:08
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_holder_info_change")
public class HolderInfoChange extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 股东信息变更表ID
     */
    @TableId("HOLDER_CHANGE_ID")
    private Long holderChangeId;

    /**
     * 变更ID
     */
    @TableField("CHANGE_ID")
    private Long changeId;

    /**
     * ID
     */
    @TableField("HOLDER_INFO_ID")
    private Long holderInfoId;

    /**
     * 供应商ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 股东中文名字
     */
    @TableField("HOLDER_NAME")
    private String holderName;

    /**
     * 股东英文名字
     */
    @TableField("HOLDER_EN_NAME")
    private String holderEnName;

    /**
     * 身份证号码/组织机构代码
     */
    @TableField("ID_NUMBER")
    private String idNumber;

    /**
     * 股东性质
     */
    @TableField("HOLDER_TYPE")
    private String holderType;

    /**
     * 股东比例
     */
    @TableField("HOLDER_RATIO")
    private String holderRatio;

    /**
     * 联系电话
     */
    @TableField("PHONE_NUMBER")
    private String phoneNumber;

    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    @TableField(exist = false)
    Map<String,Object> dimFieldContexts;


}
