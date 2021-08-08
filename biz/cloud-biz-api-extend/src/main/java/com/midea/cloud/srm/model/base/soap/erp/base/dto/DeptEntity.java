package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/15 16:59
 *  修改内容:
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD) //@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "id","deptId","effdt","effStatus","descr","descrshort",
        "lgoDescrEng","managerPosn","posnDescr","managerId","managerName","company","companyDescr","lgiDeptOwner",
        "lgoDeptBelongTo","lgiDeptLevel","lgiCostCenterId","treeNodeNum","partDeptidChn","lgiHrbpId","lgiIntDt","resourceStatus",
        "attr1","attr2","attr3","attr4","attr5"

}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@Data
@Accessors(chain = true)
public class DeptEntity {

    /**
     * 主键ID
     */
    @XmlElement(name = "ID", required = false)
    protected String id;

    /**
     * 部门ID/组织ID
     */
    @XmlElement(name = "DEPTID", required = false)
    protected String deptId;

    /**
     * 生效日期
     */
    @XmlElement(name = "EFFDT", required = false)
    protected String effdt;

    /**
     * 生效状态(A–有效,I-无效)
     */
    @XmlElement(name = "EFF_STATUS", required = false)
    protected String effStatus;

    /**
     * 组织名称
     */
    @XmlElement(name = "DESCR", required = false)
    protected String descr;

    /**
     * 组织简称
     */
    @XmlElement(name = "DESCRSHORT", required = false)
    protected String descrshort;

    /**
     * 组织英文名
     */
    @XmlElement(name = "LGI_DESCR_ENG", required = false)
    protected String lgoDescrEng;

    /**
     * 部门负责人职位ID
     */
    @XmlElement(name = "MANAGER_POSN", required = false)
    protected String managerPosn;

    /**
     * 部门负责人职位名称
     */
    @XmlElement(name = "POSN_DESCR", required = false)
    protected String posnDescr;

    /**
     * 组织负责人ID
     */
    @XmlElement(name = "MANAGER_ID", required = false)
    protected String managerId;

    /**
     * 组织负责人姓名
     */
    @XmlElement(name = "MANAGER_NAME", required = false)
    protected String managerName;

    /**
     * 公司ID
     */
    @XmlElement(name = "COMPANY", required = false)
    protected String company;

    /**
     * 公司名称
     */
    @XmlElement(name = "COMPANY_DESCR", required = false)
    protected String companyDescr;

    /**
     * 所属机构
     */
    @XmlElement(name = "LGI_DEPT_OWNER", required = false)
    protected String lgiDeptOwner;

    /**
     * 部门归属
     */
    @XmlElement(name = "LGI_DEPT_BELONG_TO", required = false)
    protected String lgoDeptBelongTo;

    /**
     * 部门层级
     */
    @XmlElement(name = "LGI_DEPT_LEVEL", required = false)
    protected String lgiDeptLevel;

    /**
     * 成本中心代码
     */
    @XmlElement(name = "LGI_COST_CENTER_ID", required = false)
    protected String lgiCostCenterId;

    /**
     * 树节点编号，排序使用
     */
    @XmlElement(name = "TREE_NODE_NUM", required = false)
    protected String treeNodeNum;

    /**
     * 父部门ID
     */
    @XmlElement(name = "PART_DEPTID_CHN", required = false)
    protected String partDeptidChn;

    /**
     * 员工ID
     */
    @XmlElement(name = "LGI_HRBP_ID", required = false)
    protected String lgiHrbpId;

    /**
     * 时间戳
     */
    @XmlElement(name = "LGI_INT_DT", required = false)
    protected String lgiIntDt;

    /**
     * 来源状态
     */
    @XmlElement(name = "RESOURCE_STATUS", required = false)
    protected String resourceStatus;

    /**
     * 备用字段1
     */
    @XmlElement(name = "Attr1", required = false)
    protected String attr1;

    /**
     * 备用字段2
     */
    @XmlElement(name = "Attr2", required = false)
    protected String attr2;

    /**
     * 备用字段3
     */
    @XmlElement(name = "Attr3", required = false)
    protected String attr3;

    /**
     * 备用字段4
     */
    @XmlElement(name = "Attr4", required = false)
    protected String attr4;

    /**
     * 备用字段5
     */
    @XmlElement(name = "Attr5", required = false)
    protected String attr5;

}

