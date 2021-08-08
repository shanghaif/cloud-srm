package com.midea.cloud.srm.model.inq.inquiry.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 *  配额调整 模型
 * </pre>
*
* @author zhi1772778785@163.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-08 14:01:49
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_inquiry_quota_adjust")
public class QuotaAdjust extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配额调整ID
     */
    @TableId(value = "QUOTA_ADJUST_ID")
    private Long quotaAdjustId;

    /**
     * 配额调整编号
     */
    @TableField("QUOTA_ADJUST_CODE")
    private String quotaAdjustCode;

    /**
     * 配额调整标题
     */
    @TableField("QUOTA_ADJUST_NAME")
    private String quotaAdjustName;

    /**
     * 寻源单号
     */
    @TableField("CEEA_SOURCE_NO")
    private String ceeaSourceNo;

    /**
     * 寻源方式
     */
    @TableField("SOURCE_TYPE")
    private String sourceType;

    /**
     * 审批状态，1未审批；2已驳回；3已审批；
     */
    @TableField("STATUS")
    private String status;

    /**
     * 调整类型，1供货异常；2质量异常；3技术服务异常；4其他重大违约
     */
    @TableField("ADJUST_TYPE")
    private String adjustType;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 员工工号（隆基新增）
     */
    @TableField("CEEA_EMP_NO")
    private String ceeaEmpNo;

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
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
