package com.midea.cloud.srm.model.pm.ps.anon.external;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.time.LocalDate;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  FSSC状态反馈表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-02 08:47:44
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_ps_external_fssc_status")
public class FsscStatus extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,FSSC单据状态反馈ID
     */
    @TableId("FSSC_STATUS_ID")
    private Long fsscStatusId;

    /**
     * 主键,FSSC单号
     */
    @TableField("FSSC_NO")
    private String fsscNo;

    /**
     * 单据类型
1、预付款报账单:FOREIGN_ADVANCE_PAYMENT
2、三单匹配单 PURCHASE_BOE_LGi
3、挂账付款单 FOREIGN_PAYMENT_BOE
     */
    @TableField("BOE_TYPE_CODE")
    private String boeTypeCode;

    /**
     * 外系统单号
     */
    @TableField("PERIPHERAL_SYSTEM_NUM")
    private String peripheralSystemNum;

    /**
     * 状态,10:单据撤回,11:单据驳回,30:单据完成,100:ERP发票反馈
     */
    @TableField("STATUS")
    private String status;

    /**
     * 驳回时，推送驳回原因
     */
    @TableField("REASON")
    private String reason;

    /**
     * 执行动作
     */
    @TableField(exist = false)
    private String action = "0";

    /**
     * 来源系统编码
     */
    @TableField(exist = false)
    private String sourceSysteCode;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @TableField("END_DATE")
    private LocalDate endDate;

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
     * 租户ID
     */
    @TableField("TENANT_ID")
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
