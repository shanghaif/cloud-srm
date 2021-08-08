package com.midea.cloud.srm.model.base.seq.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <pre>
 *  序列号
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-11 17:08
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_seq_definition")
public class SeqDefinition extends BaseEntity {

    private static final long serialVersionUID = 2368196034289868902L;

    /**
     * ID
     */
    @TableId("SEQUENCE_ID")
    private Long sequenceId;

    /**
     * 序列名称
     */
    @TableField("SEQUENCE_NAME")
    private String sequenceName;

    /**
     * 单据编码
     */
    @TableField("SEQUENCE_CODE")
    private String sequenceCode;

    /**
     * 长度
     */
    @TableField("LENGTH")
    private Long length;

    /**
     * 前缀
     */
    @TableField("PREFIX")
    private String prefix;

    /**
     * 初始值
     */
    @TableField("INITIAL_VALUE")
    private Long initialValue;

    /**
     * 当前值
     */
    @TableField("CURRENT_VALUE")
    private Long currentValue;

    /**
     * 区域定义
     */
    @TableField("SCOPE_DEFINITION")
    private String scopeDefinition;

    /**
     * 序列号重置方式
     */
    @TableField("SEQUENCE_NUM_RESET")
    private String sequenceNumReset;

    /**
     * 基准偏移量
     */
    @TableField("OFFSET")
    private Long offset;

    /**
     * 开始时间
     */
    @TableField("START_DATE")
    private Date startDate;

    /**
     * 结束时间
     */
    @TableField(value = "END_DATE" ,updateStrategy = FieldStrategy.IGNORED)
    private Date endDate;

    /**
     * 偏移量1
     */
    @TableField("GRANULAR1")
    private String granular1;

    /**
     * 粒度控制2
     */
    @TableField("GRANULAR2")
    private String granular2;

    /**
     * 粒度控制3
     */
    @TableField("GRANULAR3")
    private String granular3;

    /**
     * 粒度控制4
     */
    @TableField("GRANULAR4")
    private String granular4;

    /**
     * 粒度控制5
     */
    @TableField("GRANULAR5")
    private String granular5;

    /**
     * 粒度控制6
     */
    @TableField("GRANULAR6")
    private String granular6;

    /**
     * 粒度控制7
     */
    @TableField("GRANULAR7")
    private String granular7;

    /**
     * 粒度控制8
     */
    @TableField("GRANULAR8")
    private String granular8;

    /**
     * 粒度控制9
     */
    @TableField("GRANULAR9")
    private String granular9;

    /**
     * 粒度控制10
     */
    @TableField("GRANULAR10")
    private String granular10;

    /**
     * 粒度控制11
     */
    @TableField("GRANULAR11")
    private String granular11;

    /**
     * 粒度控制12
     */
    @TableField("GRANULAR12")
    private String granular12;

    /**
     * 粒度控制13
     */
    @TableField("GRANULAR13")
    private String granular13;

    /**
     * 粒度控制14
     */
    @TableField("GRANULAR14")
    private String granular14;

    /**
     * 粒度控制15
     */
    @TableField("GRANULAR15")
    private String granular15;

    /**
     * 粒度控制16
     */
    @TableField("GRANULAR16")
    private String granular16;

    /**
     * 粒度控制17
     */
    @TableField("GRANULAR17")
    private String granular17;

    /**
     * 粒度控制18
     */
    @TableField("GRANULAR18")
    private String granular18;

    /**
     * 粒度控制19
     */
    @TableField("GRANULAR19")
    private String granular19;

    /**
     * 粒度控制20
     */
    @TableField("GRANULAR20")
    private String granular20;

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

}
