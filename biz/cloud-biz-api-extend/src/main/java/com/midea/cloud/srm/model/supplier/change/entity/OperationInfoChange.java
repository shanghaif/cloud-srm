package com.midea.cloud.srm.model.supplier.change.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.util.Map;

import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  经营情况变更 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-28 13:59:38
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_operation_info_change")
public class OperationInfoChange extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 经营情况变更ID
     */
    @TableId("OP_CHANGE_ID")
    private Long opChangeId;

    /**
     * 变更ID
     */
    @TableField("CHANGE_ID")
    private Long changeId;

    /**
     * ID
     */
    @TableField("OP_INFO_ID")
    private Long opInfoId;

    /**
     * 供应商ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 净资产(万元)
     */
    @TableField("NET_ASSETS")
    private BigDecimal netAssets;

    /**
     * 总资产(万元)
     */
    @TableField("TOTAL_ASSETS")
    private BigDecimal totalAssets;

    /**
     * 能否接受3个月付款期(Y,N)
     */
    @TableField("IS_ACCEPT_PAY")
    private String isAcceptPay;

    /**
     * 电脑数量
     */
    @TableField("PC_QTY")
    private Integer pcQty;

    /**
     * 财务软件
     */
    @TableField("FINANCIAL_SOFT")
    private String financialSoft;

    /**
     * 流动资金(万元)
     */
    @TableField("CURRENT_FUND")
    private BigDecimal currentFund;

    /**
     * 流动负债(万元)
     */
    @TableField("CURRENT_DEBT")
    private BigDecimal currentDebt;

    /**
     * 能否接受6个月银行承兑(Y,N)
     */
    @TableField("IS_ACCEPT_CD")
    private String isAcceptCd;

    /**
     * 管理人员电脑拥有率（%）
     */
    @TableField("PC_RATE")
    private Integer pcRate;

    /**
     * 生产制造软件
     */
    @TableField("PRO_SOFT")
    private String proSoft;

    /**
     * 本年度目标销售额(万元)
     */
    @TableField("SALES_TARGET")
    private BigDecimal salesTarget;

    /**
     * 本年度目标净利润(万元)
     */
    @TableField("PROFIT_TARGET")
    private BigDecimal profitTarget;

    /**
     * 是否享受当地政府优惠政策(Y,N)
     */
    @TableField("IS_ACCEPT_POLICY")
    private String isAcceptPolicy;

    /**
     * 企业管理信息系统
     */
    @TableField("COMPANY_SOFT")
    private String companySoft;

    /**
     * 仓库是否使用电脑(Y,N)
     */
    @TableField("IS_WH_PC")
    private String isWhPc;

    /**
     * 上一年度销售额(万元)
     */
    @TableField("PRE_SALES_TARGET")
    private BigDecimal preSalesTarget;

    /**
     * 上一年度净利润(万元)
     */
    @TableField("PRE_PROFIT_TARGET")
    private BigDecimal preProfitTarget;

    /**
     * 当地的产业特色
     */
    @TableField("INDUSTRY_SP")
    private String industrySp;

    /**
     * 技术设计软件
     */
    @TableField("DESIGN_SOFT")
    private String designSoft;

    /**
     * 企业内部是否联网办公(Y,N)
     */
    @TableField("IS_NET_OFFICE")
    private String isNetOffice;

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
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    @TableField(exist = false)
    Map<String,Object> dimFieldContexts;
}
