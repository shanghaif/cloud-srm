package com.midea.cloud.srm.model.cm.contract.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
*  <pre>
 *  合同定级维护表 模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-06 10:59:25
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_contract_level_maintain")
public class LevelMaintain extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 合同级别维护Id
     */
    @TableId("LEVEL_MAINTAIN_ID")
    private Long levelMaintainId;

    /**
     * ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 分类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 分类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;



    /**
     * 合同定级限制金额（万元）
     */
    @TableField("AMOUNT")
    private BigDecimal amount;

    /**
     * 运算关系
     */
    @TableField("OPERATIONAL")
    private String operational;

    /**
     * 运算公式
     */
    @TableField("FORMULA")
    private String formula;

    /**
     * 合同级别
     */
    @TableField("LEVEL")
    private String level;

    /**
     * 生效日期
     */
    @TableField("START_DATA")
    private LocalDate startData;

    /**
     * 失效日期
     */
    @TableField("END_DATA")
    private LocalDate endData;

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
     * 更新人
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

    /**
     * 是否有效(N/Y)
     */
    @TableField(exist = false)
    private String isValid;

    /**
     * 分类结构,该值以父分类节点ID+下划线+分类节点ID值组合而成(比如该分类ID:2,其父分类节点ID为1,则该字段值为1_2)
     */
    @TableField("STRUCT")
    private String struct;

    /**
     * 全路径品类名称
     */
    @TableField("CATEGORY_FULL_NAME")
    private String categoryFullName;

}
