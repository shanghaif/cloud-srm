package com.midea.cloud.srm.model.rbac.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/**
 * <pre>
 *  用户信息 模型
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 17:27:10
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_rbac_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId("USER_ID")
    private Long userId;

    /**
     * 公司ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 公司编码
     */
    @TableField(exist = false)
    private String companyCode;

    /**
     * 公司名称
     */
    @TableField(exist = false)
    private String companyName;

    /**
     * 账号
     */
    @TableField("USERNAME")
    private String username;

    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 昵称
     */
    @TableField("NICKNAME")
    private String nickname;

    /**
     * 手机
     */
    @TableField("PHONE")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("EMAIL")
    private String email;

    /**
     * 部门
     */
    @TableField("DEPARTMENT")
    private String department;

    /**
     * 用户类型： 采购商： BUYER, 供应商： VENDOR
     */
    @TableField("USER_TYPE")
    private String userType;

    /**
     * 账号类型： 主账号： Y, 子账号：N
     */
    @TableField("MAIN_TYPE")
    private String mainType;

    /**
     * erp采购员工号
     */
    @TableField("CEEA_PO_AGENT_NUMBER")
    private String ceeaPoAgentNumber;

    /**
     * erp采购员姓名
     */
    @TableField("CEEA_PO_AGENT_NAME")
    private String ceeaPoAgentName;

    /**
     * 生效日期
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效日期
     */
    @TableField(value = "END_DATE", updateStrategy = FieldStrategy.IGNORED)
    private LocalDate endDate;

    /**
     * 是否阅读协议 Y确认N没确认
     */
    @TableField("IS_CONFIRM")
    private String isConfirm;

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
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**员工工号（隆基新增）*/
    @TableField("CEEA_EMP_NO")
    private String ceeaEmpNo;

    /**公司（隆基新增）*/
    @TableField("CEEA_COMPANY")
    private String ceeaCompany;
    /**公司描述（隆基新增）*/
    @TableField("CEEA_COMPANY_DESCR")
    private String ceeaCompanyDescr;

    /**部门编码 */
    @TableField("CEEA_DEPTID")
    private String ceeaDeptId;

    /**岗位代码 */
    @TableField("CEEA_JOBCODE")
    private String ceeaJobcode;
    /**岗位代码描述 */
    @TableField("CEEA_JOBCODE_DESCR")
    private String ceeaJobcodeDescr;

    /**以下自动用于查询 */
    /**查询username或nickname的参数*/
    @TableField(exist = false)
    private String queryName;


}
