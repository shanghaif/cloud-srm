package com.midea.cloud.srm.model.supplier.change.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
*  <pre>
 *  公司信息变更表
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-30 19:57:36
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_info_change")
public class InfoChange extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 变更单ID
     */
    @TableId("CHANGE_ID")
    private Long changeId;


    /**
     * 公司ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 变更单据申请号
     */
    @TableField("CHANGE_APPLY_NO")
    private String changeApplyNo;

    /**
     * 变更单类型,公司基础信息变更:COMPANY_INFO_CHANGE,公司银行财务信息变更:FINANCE_BANK_INFO_CHANGE
     */
    @TableField("CHANGE_TYPE")
    private String changeType;

    /**
     * 是否供应商
     */
    @TableField("USER_TYPE")
    private String userType;

    /**
     * 变更单据说明
     */
    @TableField("CHANGE_EXPLAIN")
    private String changeExplain;

    /**
     * 公司变更状态
     */
    @TableField("CHANGE_STATUS")
    private String changeStatus;

    /**
     * 变更审批日期
     */
    @TableField("CHANGE_APPROVED_DATE")
    private Date changeApprovedDate;

    /**
     * 变更审批人
     */
    @TableField("CHANGE_APPROVED_BY")
    private String changeApprovedBy;


    /**
     * 变更审批人
     */
    @TableField("CHANGE_APPROVED_BY_ID")
    private Long changeApprovedById;

    /**
     * 变更申请日期
     */
    @TableField("CHANGE_APPLY_DATE")
    private Date changeApplyDate;

    /**
     * 变更相关附件ID
     */
    @TableField("CHANGE_FILE_ID")
    private Long changeFileId;

    /**
     * 变更相关附件名称
     */
    @TableField("CHANGE_FILE_NAME")
    private String changeFileName;


    /**
     * 创建人
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
    @TableField("LAST_UPDATED_IP")
    private String lastUpdatedIp;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 通知业务人员ID
     */
    @TableField("NOTICE_BY_ID")
    private Long noticeById;

    /**
     * 通知业务人员名称
     */
    @TableField("NOTICE_BY_NAME")
    private String noticeByName;


    @TableField(exist = false)
    Long menuId;

    @TableField("CBPM_INSTANCE_ID")
    private String cbpmInstaceId;

    @TableField("APPROVE_STATUS")
    private String approveStatus;

    /**变更注意事项*/
    @TableField("CHANGE_PRECAUTIONS")
    private String changePrecautions;
    /**是否是4M变更(是-Y,否-N)*/
    @TableField("ENABLE_4M_CHANGE")
    private String enable4MChange;
    /**
     * BPM起草人意见
     */
    @TableField("CEEA_DRAFTER_OPINION")
    private String ceeaDrafterOpinion;

}
