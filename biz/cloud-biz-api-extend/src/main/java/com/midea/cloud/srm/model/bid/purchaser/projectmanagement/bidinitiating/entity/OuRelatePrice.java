package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  单个需求行的ou之间的价格关系
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/4 10:47
 *  修改内容:
 * </pre>
 */
@Data
@TableName(value = "ceea_ou_relate_price")
public class OuRelatePrice {
    /**
     * 关联id
     */
    @TableId(value = "RELATE_ID")
    private Long relateId;
    /**
     * ou组ID
     */
    @TableField(value = "OU_ID")
    private Long ouId;
    /**
     * 需求头ID
     */
    @TableField(value = "REQUIRE_HEADER_ID")
    private Long requireHeaderId;
    /**
     * 需求行id
     */
    @TableField(value = "REQUIRE_LINE_ID")
    private Long requirementLineId;
    /**
     * ou组名称
     */
    @TableField(value = "OU_NAME")
    private String ouName;
    /**
     * 业务实体名
     */
    @TableField(value = "ORG_NAME")
    private String orgName;
    /**
     * 业务实体ID
     */
    @TableField(value = "ORG_ID")
    private Long orgId;
    /**
     * 价格差
     */
    @TableField(value = "PRICE_DIFF")
    private BigDecimal priceDiff;
    /**
     * 基准OU组ID
     */
    @TableField(value = "BASE_OU_ID")
    private Long baseOuId;
    /**
     * 基准OU组名称
     */
    @TableField(value = "BASE_OU_NAME")
    private String baseOuName;
    @TableField(value = "BASE_ORG_ID")
    private Long baseOrgId;
    @TableField(value = "BASE_ORG_NAME")
    private String baseOrgName;
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
     * 拥有者/租户/所属组等
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;
}
