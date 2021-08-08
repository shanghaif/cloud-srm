package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.*;

import java.util.Date;

/**
 * 寻源模板
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ceea_bid_sourcing_template")
public class SourcingTemplate extends BaseEntity {

    @TableId("ID")
    private Long    id;                     // 表ID

    @TableField("TEMPLATE_SN")
    private String  sn;                     // 模板编号

    @TableField("TEMPLATE_NAME")
    private String  name;                   // 模板名称

    /**
     * @see com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.enums.SourcingTemplateStatus
     */
    @TableField("TEMPLATE_STATUS")
    private String  status;                 // 模板状态

    /**
     * @see com.midea.cloud.srm.model.inq.price.enums.SourcingType
     */
    @TableField("SOURCING_TYPE")
    private String  sourcingType;           // 寻源类型

    /**
     * JSON格式存储寻源项目信息
     * @see com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.SourceForm
     */
    @TableField("TEMPLATE_DATA")
    private String  templateData;           // 模板数据


    /* ============================ 默认字段 ============================ */

    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long    createdId;              // 创建人ID

    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String  createdBy;              // 创建人

    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date    creationDate;           // 创建时间

    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String  createdByIp;            // 创建人IP

    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long    lastUpdatedId;          // 最后更新人ID

    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String  lastUpdatedBy;          // 更新人

    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date    lastUpdateDate;         // 最后更新时间

    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String  lastUpdatedByIp;        // 最后更新人IP

    @TableField("TENANT_ID")
    private String  tenantId;               // 租户ID

    @TableField("VERSION")
    private Long    version;                // 版本号
}
