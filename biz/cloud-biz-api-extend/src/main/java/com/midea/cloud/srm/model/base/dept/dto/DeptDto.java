package com.midea.cloud.srm.model.base.dept.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/22
 *  修改内容:
 * </pre>
 */
@Data
public class DeptDto extends BaseDTO {
    /**
     * 虚拟部门ID
     */
    private Long virtualDepartId;

    /**
     * SRM公司ID
     */
    private String company;

    /**
     * 公司名称
     */
    private String companyDescr;

    /**
     * 部门编码/部门ID
     */
    private String deptid;

    /**
     * 部门名称
     */
    private String descr;

    /**
     * 父部门ID
     */
    private String partDeptidChn;

    /**
     * 父部门名称
     */
    private String partDescrChn;

    /**
     * 生效时间
     */
    private LocalDate startDate;

    /**
     * 生效状态(A–有效,I-无效)
     */
    private String effStatus;

    /**
     * 部门层级
     */
    private String lgiDeptLevel;

    /**
     * 更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;

    /**
     * 组织id
     */
    private Long orgId;

    /**
     * 组织编码
     */
    private String orgCode;

    /**
     * 组织名字
     */
    private String orgName;

    /**
     * 是否可编辑(N-否,Y-是)
     */
    private String isEdit;

    /**
     * 失效时间
     */
    private LocalDate endDate;

}
