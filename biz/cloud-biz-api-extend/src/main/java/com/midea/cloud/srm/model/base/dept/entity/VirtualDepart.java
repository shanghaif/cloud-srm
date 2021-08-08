package com.midea.cloud.srm.model.base.dept.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/**
*  <pre>
 *  虚拟组织表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-22 10:53:05
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_virtual_depart")
public class VirtualDepart extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 虚拟部门ID
     */
    @TableId("VIRTUAL_DEPART_ID")
    private Long virtualDepartId;

    /**
     * SRM公司ID
     */
    @TableField("COMPANY")
    private String company;

    /**
     * 公司名称
     */
    @TableField("COMPANY_DESCR")
    private String companyDescr;

    /**
     * 部门编码/部门ID
     */
    @TableField("DEPTID")
    private String deptid;

    /**
     * 部门名称
     */
    @TableField("DESCR")
    private String descr;

    /**
     * 父部门ID
     */
    @TableField("PART_DEPTID_CHN")
    private String partDeptidChn;

    /**
     * 父部门名称
     */
    @TableField("PART_DESCR_CHN")
    private String partDescrChn;

    /**
     * 生效时间
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 生效状态(A–有效,I-无效)
     */
    @TableField("EFF_STATUS")
    private String effStatus;

    /**
     * 失效时间
     */
    @TableField("END_DATE")
    private LocalDate endDate;

    /**
     * 部门层级
     */
    @TableField("LGI_DEPT_LEVEL")
    private String lgiDeptLevel;

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
     * 组织id
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 组织编码
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 组织名字
     */
    @TableField("ORG_NAME")
    private String orgName;


}
