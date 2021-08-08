package com.midea.cloud.srm.model.supplier.info.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.time.LocalDate;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  基本信息从表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-04 09:47:45
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sup_company_info_detail")
public class CompanyInfoDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,基本信息从表ID
     */
    @TableId("COMPANY_DETAIL_ID")
    private Long companyDetailId;

    /**
     * 公司ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 员工数量
     */
    @TableField("STAFF_QUANTITY")
    private String staffQuantity;

    /**
     * 管理人员数量
     */
    @TableField("MANAGER_QUANTITY")
    private String managerQuantity;

    /**
     * 技术人员数量
     */
    @TableField("TECHNICIST_QUANTITY")
    private String technicistQuantity;

    /**
     * 生产员工数量
     */
    @TableField("PRODUCTOR_QUANTITY")
    private String productorQuantity;

    /**
     * 是否研发部门
     */
    @TableField("IF_RAD")
    private String ifRad;

    /**
     * 研发人员数量
     */
    @TableField("RAD_STAFF_QUANTITY")
    private String radStaffQuantity;

    /**
     * 行业排名
     */
    @TableField("BUSINESS_RANK")
    private String businessRank;

    /**
     * 市场份额
     */
    @TableField("MARKET_SHARE")
    private String marketShare;

    /**
     * 国际同行前五(行业前三的企业名称)
     */
    @TableField("INTERNATIONAL_TOP_FIVE")
    private String internationalTopFive;

    /**
     * 国内同行前五
     */
    @TableField("INTERNAL_TOP_FIVE")
    private String internalTopFive;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @TableField("END_DATE")
    private LocalDate endDate;

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
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

}
