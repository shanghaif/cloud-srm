package com.midea.cloud.srm.model.bargaining.suppliercooperate.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderHeadFile;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderlinePaymentTerm;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author zhuomb1@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/3 11:10
 *  修改内容:
 * </pre>
 */
@Data
public class BidOrderLineVO {

    /**
     * 投标行表--主键ID
     */

    @NumberFormat("")
    @ExcelProperty(value = "投标行表ID", index = 1)
    private Long orderLineId;
    /**
     * 投标行表--招标单ID
     */
    @NumberFormat("")
    @ExcelProperty(value = "招标单ID", index = 2)
    public Long bidingId;
    /**
     * 投标行表--供应商投标头表ID
     */
    @NumberFormat("")
    @ExcelProperty(value = "投标单投标ID", index = 3)
    private Long orderHeadId;
    /**
     * 投标行表--投标状态
     */
    @ExcelIgnore
    private String orderStatus;
    /**
     * 投标行表--轮次
     */
    @ExcelIgnore
    private Integer round;
    /**
     * 投标行表--供应商ID
     */
    @ExcelIgnore
    private Long bidVendorId;

    /**
     * 物料需求行表--物料需求ID
     */
    @NumberFormat
    @ExcelProperty(value = "投标需求行表ID")
    private Long requirementLineId;
    /**
     * 物料需求行表--投标编码
     */
    @ExcelProperty(value = "投标编码", index = 4)
    private String targetNum;
    /**
     * 物料需求行表--投标描述
     */
    @ExcelProperty(value = "标的描述", index = 5)
    private String targetDesc;
    /**
     * 物料需求行表--拦标价
     */
    @ExcelIgnore
    private String targetPrice;
    /**
     * 物料需求行表--物料组
     */
    @ExcelProperty(value = "物料组", index = 6)
    private String itemGroup;
    /**
     * 物料需求行表--采购组织
     */
    @ExcelProperty(value = "采购组织", index = 7)
    private String orgName;
    /**
     * 物料需求行表--采购数量
     */
    @ExcelProperty(value = "采购数量", index = 8)
    private String quantity;
    /**
     * 物料需求行表--单位编码
     */
    @ExcelIgnore
    private String uomCode;
    /**
     * 物料需求行表--单位描述
     */
    @ExcelProperty(value = "单位", index = 9)
    private String uomDesc;
    /**
     * 投标行表--含税报价
     */
    @ExcelProperty(value = "含税报价", index = 10)
    @NotNull(message = "含税报价不能为空")
    private BigDecimal price;
    /**
     * 投标行表--跟标报价，取上一轮的中标价格
     */
    @ExcelIgnore
    private BigDecimal finalPrice;
    /**
     * 税率
     */
    @ExcelProperty(value = "税率", index = 11)
    private BigDecimal taxRate;
    /**
     * 物料需求行表--备注
     */
    @ExcelProperty(value = "备注", index = 14)
    private String commnets;
    /**
     * 物料需求行表--企业编码
     */
    @ExcelIgnore
    private String companyCode;
    /**
     * 物料需求行表--组织编码
     */
    @ExcelIgnore
    private String organizationCode;

    /**
     * 定价开始时间
     */
    @ExcelProperty(value = "定价开始时间", index = 12)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date priceStartTime;
    /**
     * 定价结束时间
     */
    @ExcelProperty(value = "定价结束时间", index = 13)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date priceEndTime;
    /**
     * 跟标权限
     */
    @ExcelIgnore
    private String withStandardPerssiom;
    /**
     * 是否跟标
     */
    @ExcelIgnore
    private String withStandard;
    /**
     * 是否入围Y=入围，N=淘汰，D=待确定
     */
    @ExcelIgnore
    private String win;
    /**
     * 评选结果
     */
    @ExcelIgnore
    private String selectionStatus;
    /**
     * 评选结果
     */
    @ExcelIgnore
    private Integer rank;
    /**
     * 评选结果
     */
    @ExcelIgnore
    private BigDecimal totalAmount;
    /**
     * 采购分类
     */
    @ExcelIgnore
    private String categoryName;

    /**
     * 税码
     */
    @ExcelIgnore
    private String taxKey;

    private Long ouId;
    /**
     * ou组名
     */
    private String ouName;
    /**
     * 是否为基准ou
     */
    private String baseOu;
    /**
     * 交货地点
     */
    private String deliveryPlace;
    /**
     * 最小订单数
     */
    private String MQO;
    /**
     * 贸易条款
     */
    private String tradeTerm;
    /**
     * 运输方式
     */
    private String transportType;
    /**
     * 保修期
     */
    private Integer warrantyPeriod;
    /**
     * 采购类型
     */
    private String purchaseType;
    /**
     * 报价币种
     */
    @NotEmpty(message = "币种不能为空")
    private String currencyType;
    /**
     * 账期
     */
    private Integer paymentDay;
    /**
     * 付款条款
     */
    private String paymentTerm;
    /**
     * 付款方式
     */
    private String paymentWay;
    /**
     * 付款条款id
     */
    private String paymentTermId;
    /**
     * 公式ID
     */
    private Long formulaId;

    /**
     * 公式值
     */
    private String formulaValue;

    /**
     * 标的ID
     */
    private Long targetId;

    /**
     * 供货周期
     */
    private String leadTime;

    /**
     * 承诺交货期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date deliverDate;
    /**
     * 折息价
     */
    private BigDecimal discountPrice;

    /**
     * 本轮最低折息价
     */
    private BigDecimal currentRoundMinDiscountPrice;

    /**
     * 本轮最高折息价
     */
    private BigDecimal currentRoundMaxDiscountPrice;

    /**
     * 组合折息总金额
     */
    private BigDecimal totalDiscountAmount;

    /**
     * 本轮最低折息总金额
     */
    private BigDecimal currentRoundMinDiscountAmount;

    /**
     * 上一次报价
     */
    private BigDecimal lastPrice;

    /**
     * 价格浮动百分比
     */
    private BigDecimal compareRate;
    /**
     * 付款条款
     */
    @Valid
    @Size(min = 1, message = "付款条款至少有一条!")
    private List<OrderlinePaymentTerm> paymentTermList;

    private Long formulaPriceDetailId;   // 公式报价明细ID
    private String materialExtValues;      // 明细行物料扩展属性值 - { 扩展属性ID-1": "属性值-1", "扩展属性ID-2": "属性值-2" }
    private String essentialFactorValues;  // 明细行要素值 - { "要素ID-1": "要素值-1", "要素ID-2": "要素值-2" }
    /**
     * 库存组织名
     */
    private String invName;

    private String materialMatching;  // 物料配比

    private String comments;

    private Long preId;

    /**
     * 询比价附件
     */
    List<OrderHeadFile> files;
}