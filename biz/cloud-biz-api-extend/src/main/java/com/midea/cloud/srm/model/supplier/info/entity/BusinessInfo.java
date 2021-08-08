package com.midea.cloud.srm.model.supplier.info.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Map;

/**
*  <pre>
 *  业务信息(ceea客户情况) 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-07 10:30:12
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_business_info")
public class BusinessInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("BUSINESS_INFO_ID")
    private Long businessInfoId;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 供应商ID
     */
    @TableId("COMPANY_ID")
    private Long companyId;

    /**
     * 客户
     */
    @TableField("CUSTOMER")
    private String customer;

    /**
     * 合作频率
     */
    @TableField("CEEA_WORK_FREQUENCY")
    private String ceeaWorkFrequency;

    /**
     * 所属区域
     */
    @TableField("CEEA_AREA")
    private String ceeaArea;

    /**
     * 备注
     */
    @TableField("CEEA_COMMENT")
    private String ceeaComment;

    /**
     * 客户产业
     */
    @TableField("CUSTOMER_IND")
    private String customerInd;

    /**
     * 上年度销售量
     */
    @TableField("PRE_SALES_VOL")
    private String preSalesVol;

    /**
     * 上年度销售金额(万元)
     */
    @TableField("PRE_SALES_AMOUNT")
    private String preSalesAmount;

    /**
     * 占销售总额比例（%）
     */
    @TableField("TOTAL_SALES_RATE")
    private String totalSalesRate;

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

    @TableField(exist = false)
    Map<String,Object> dimFieldContexts;
}
