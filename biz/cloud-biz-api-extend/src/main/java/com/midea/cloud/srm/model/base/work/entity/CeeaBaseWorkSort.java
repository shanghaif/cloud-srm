package com.midea.cloud.srm.model.base.work.entity;

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
 * <pre>
 * 供应商我的任务列表排序
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 * 修改记录
 * 修改后版本:
 * 修改人:
 * 修改日期: 2020-9-24 15:15
 * 修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_work_sort")
public class CeeaBaseWorkSort extends BaseEntity {

    @TableId("WORK_SORT_ID")
    private Long workSortId;// 主键id
    @TableField("LIST_NAME")
    private String listName;// 列表名称
    @TableField("USER_ID")
    private Long userId;// 用户id
    @TableField("SORT")
    private Integer sort;
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;
    @TableField("VERSION")
    private Long version;
    @TableField("TENANT_ID")
    private Long tenantId;
}
