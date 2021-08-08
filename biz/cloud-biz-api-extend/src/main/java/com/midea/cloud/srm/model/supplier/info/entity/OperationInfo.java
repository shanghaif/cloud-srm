package com.midea.cloud.srm.model.supplier.info.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

/**
*  <pre>
 *  经营情况 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 16:21:46
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_operation_info")
public class OperationInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("OP_INFO_ID")
    private Long opInfoId;

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
     * 注册资金
     */
    @TableField("CEEA_REGISTERED_CAPITAL")
    private String ceeaRegisteredCapital;

    /**
     * 成立日期
     */
    @TableField("CEEA_COMPANY_CREATION_DATE")
    private LocalDate ceeaCompanyCreationDate;

    /**
     * 年营业额
     */
    @TableField("CEEA_YEAR_TURNOVER")
    private String ceeaYearTurnover;

    /**
     * 前三年净利润
     */
    @TableField("CEEA_PRE_THREE_YEARS_PROFIT")
    private String ceeaPreThreeYearsProfit;

    /**
     * 前三年资产负债率
     */
    @TableField("CEEA_PRE_THREE_YEARS_AAL")
    private String ceeaPreThreeYearsAal;

    /**
     * 前三年销售额
     */
    @TableField("CEEA_PRE_THREE_YEARS_SALE")
    private String ceeaPreThreeYearsSale;

    /**
     * 经营范围和各项业务所占比重
     */
    @TableField("CEEA_SCOPE_BUSINESS_RATIO")
    private String ceeaScopeBusinessRatio;

    /**
     * 是否具有供应太阳能行业相关物资的经验
     */
    @TableField("CEEA_IF_HAS_SOLAR_POWER")
    private String ceeaIfHasSolarPower;

    /**
     * 贵司的上下游产业布局
     */
    @TableField("CEEA_UP_DOWN_LAYOUT")
    private String ceeaUpDownLayout;

    /**
     * 3年内经营规模变化的预期
     */
    @TableField("CEEA_THREE_SCALE_CHANGE_EXP")
    private String ceeaThreeScaleChangeExp;

    /**
     * 降低客户采购成本的建议和要求
     */
    @TableField("CEEA_REDUCE_PUR_COST_ADVISE")
    private String ceeaReducePurCostAdvise;

    /**
     * 未来控制产品成本的计划和策略
     */
    @TableField("CEEA_PRO_COST_PLAN_STRATEGY")
    private String ceeaProCostPlanStrategy;

    /**
     * 年研发投入占销售额的比例
     */
    @TableField("CEEA_RD_SALE_RATE")
    private String ceeaRdSaleRate;

    /**
     * 请从技术角度解析贵司产品的优劣性
     */
    @TableField("CEEA_PRO_GOOD_BAD")
    private String ceeaProGoodBad;

    /**
     * 描述该产品后期研发方向(技术路线图)
     */
    @TableField("CEEA_PRO_TECH_ROUTE")
    private String ceeaProTechRoute;

    /**
     * 描述贵公司研发团队构成情况及研发能力评估
     */
    @TableField("CEEA_TEAM_SHAPE_ABILITY")
    private String ceeaTeamShapeAbility;

    /**
     * 请提供我们所采购物资的价格构成要素比例
     */
    @TableField("CEEA_PRO_PRICE_INSCAPE_RATE")
    private String ceeaProPriceInscapeRate;

    /**
     * 哪些要素对于今后成本降低有较大推动作用
     */
    @TableField("CEEA_REDUCE_COST_FACTOR")
    private String ceeaReduceCostFactor;

    /**
     * 贵司将怎样迎合我司对于价格持续优化的需求
     */
    @TableField("CEEA_HOW_UPGRADE_PRICE")
    private String ceeaHowUpgradePrice;

    /**
     * 售后能力
     */
    @TableField("CEEA_AFTER_SALES_ABILITY")
    private String ceeaAfterSalesAbility;

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
