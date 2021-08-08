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

/**
*  <pre>
 *   模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-07 13:59:21
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_companies")
public class Companies extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("BASE_COMPANIES_ID")
    private Long baseCompaniesId;

    /**
     * 公司ID
     */
    @TableId("COMPANY_ID")
    private BigDecimal companyId;

    /**
     * 公司代码
     */
    @TableField("COMPANY_CODE")
    private String companyCode;

    /**
     * 公司简称
     */
    @TableField("COMPANY_SHORT_NAME")
    private String companyShortName;

    /**
     * 公司全称
     */
    @TableField("COMPANY_FULL_NAME")
    private String companyFullName;

    /**
     * 公司类型
     */
    @TableField("COMPANY_TYPE")
    private String companyType;

    /**
     * 帐套ID
     */
    @TableField("SET_OF_BOOKS_ID")
    private BigDecimal setOfBooksId;

    /**
     * 公司级别ID
     */
    @TableField("COMPANY_LEVEL_ID")
    private BigDecimal companyLevelId;

    /**
     * 上级公司ID
     */
    @TableField("PARENT_COMPANY_ID")
    private BigDecimal parentCompanyId;

    /**
     * 主管岗位ID
     */
    @TableField("CHIEF_POSITION_ID")
    private BigDecimal chiefPositionId;

    /**
     * 公司简称ID
     */
    @TableField("COMPANY_SHORT_NAME_ID")
    private BigDecimal companyShortNameId;

    /**
     * 公司全称ID
     */
    @TableField("COMPANY_FULL_NAME_ID")
    private BigDecimal companyFullNameId;

    /**
     * 地址
     */
    @TableField("ADDRESS")
    private String address;

    /**
     * 有效日期从
     */
    @TableField("START_DATE_ACTIVE")
    private Date startDateActive;

    /**
     * 有效日期到
     */
    @TableField("END_DATE_ACTIVE")
    private Date endDateActive;

    /**
     * 排序
     */
    @TableField("SEQUENCE")
    private BigDecimal sequence;

    /**
     * 最后更新日期
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新用户ID
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 创建日期
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建用户ID
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 启用标识
     */
    @TableField("ENABLED_FLAG")
    private String enabledFlag;

    /**
     * 邮箱
     */
    @TableField("EMAIL")
    private String email;


}
