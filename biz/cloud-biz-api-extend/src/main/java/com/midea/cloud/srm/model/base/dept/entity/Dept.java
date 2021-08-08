package com.midea.cloud.srm.model.base.dept.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
*  <pre>
 *  部门表(隆基部门同步) 模型
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-01 14:07:03
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_dept")
public class Dept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("ID")
    private Long id;

    /**
     * 部门ID/组织ID
     */
    @TableId("DEPTID")
    private String deptid;

    /**
     * 生效日期
     */
    @TableField("EFFDT")
    private Date effdt;

    /**
     * 生效状态(A–有效，I -  无效)
     */
    @TableField("EFF_STATUS")
    private String effStatus;

    /**
     * 组织名称
     */
    @TableField("DESCR")
    private String descr;

    /**
     * 组织简称
     */
    @TableField("DESCRSHORT")
    private String descrshort;

    /**
     * 组织英文名
     */
    @TableField("LGI_DESCR_ENG")
    private String lgiDescrEng;

    /**
     * 部门负责人职位ID
     */
    @TableField("MANAGER_POSN")
    private String managerPosn;

    /**
     * 部门负责人职位名称
     */
    @TableField("POSN_DESCR")
    private String posnDescr;

    /**
     * 组织负责人ID
     */
    @TableField("MANAGER_ID")
    private String managerId;

    /**
     * 组织负责人姓名
     */
    @TableField("MANAGER_NAME")
    private String managerName;

    /**
     * 公司ID
     */
    @TableField("COMPANY")
    private String company;

    /**
     * 公司名称
     */
    @TableField("COMPANY_DESCR")
    private String companyDescr;

    /**
     * 所属机构
     */
    @TableField("LGI_DEPT_OWNER")
    private String lgiDeptOwner;

    /**
     * 部门归属
     */
    @TableField("LGI_DEPT_BELONG_TO")
    private String lgiDeptBelongTo;

    /**
     * 部门层级
     */
    @TableField("LGI_DEPT_LEVEL")
    private String lgiDeptLevel;

    /**
     * 成本中心代码
     */
    @TableField("LGI_COST_CENTER_ID")
    private String lgiCostCenterId;

    /**
     * 树节点编号，排序使用
     */
    @TableField("TREE_NODE_NUM")
    private Long treeNodeNum;

    /**
     * 父部门ID
     */
    @TableField("PART_DEPTID_CHN")
    private String partDeptidChn;

    /**
     * 员工ID
     */
    @TableField("LGI_HRBP_ID")
    private String lgiHrbpId;

    /**
     * 时间戳
     */
    @TableField("LGI_INT_DT")
    private Date lgiIntDt;

    /**
     *     接口状态(NEW-数据在接口表里,没同步到业务表,UPDATE-数据更新到接口表里,SUCCESS-同步到业务表,ERROR-数据同步到业务表出错)
     */
    @TableField("ITF_STATUS")
    private String itfStatus;

    /**
     * 备用字段1
     */
    @TableField("Attr1")
    private String Attr1;

    /**
     * 备用字段2
     */
    @TableField("Attr2")
    private String Attr2;

    /**
     * 备用字段3
     */
    @TableField("Attr3")
    private String Attr3;

    /**
     * 备用字段4
     */
    @TableField("Attr4")
    private String Attr4;

    /**
     * 备用字段5
     */
    @TableField("Attr5")
    private String Attr5;

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
     * 最后更新
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;

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
     * 子节点
     */
    @TableField(exist = false)
    List<Dept> depts;

    /**
     * SRM公司ID
     */
    @TableField("SRM_COMPANY_ID")
    private BigDecimal srmCompanyId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Dept dept = (Dept) o;
        return Objects.equals(deptid, dept.deptid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptid);
    }
}
