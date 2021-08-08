package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.vo;

import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidBidingCurrency;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderHead;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderlinePaymentTerm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

/**
 * 评选对象. VO for {@link BidRequirementLine} & {@link OrderLine}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class EvaluationResult implements Serializable {

	private Long		bidingId;						// 招标单id

	/* ===================== Start 招标单字段 ===================== */

	private Long        requirementLineId;				// 需求行ID
	private Long 		targetId;						// 标的ID
	private String 		targetNum;						// 标的编码
	private String 		targetDesc;						// 标的描述
	private Long 		categoryId;						// 品类ID
	private String 		categoryCode;					// 品类编码
	private String 		categoryName;					// 品类名称
	private String 		uomCode;						// 单位编码
	private String 		uomDesc;						// 单位描述

	private String 		itemGroup;						// 组合标识
	private String		materialMatching;				// 物料配比

	private Long		ouId;							// OU组ID
	private String		ouNumber;						// OU组编码
	private String		ouName;							// OU组名称
	private Long 		orgId;							// 业务实体ID
	private String 		orgCode;						// 业务实体编码
	private String 		orgName;						// 业务实体名称
	private String 		fullPathId; 					// 组织全路径虚拟ID
	private Long		invId;							// 库存组织ID
	private String		invCode;						// 库存组织编码
	private String		invName;						// 库存组织名称

	private Date 		priceStartTime;					// 价格有效期开始
	private Date 		priceEndTime;				 	// 价格有效期开始

	private String		priceType;						// 价格类型
	private String		quotaDistributeType;			// 配额分类类型
	private String 		deliveryPlace;					// 交货地点
	private String 		tradeTerm;						// 贸易条款
	private String 		purchaseType;					// 采购类型
	private String 		rowType;						// 行类型
	private String		purchaseRequestNum;				// 采购申请号
	private String		purchaseRequestRowNum;			// 采购申请行号
	private String		isCreateSourcingResult;			// 是否创建寻源结果审批单

	private Long 		fromContractId;    				// 来源合同ID
	private String 		fromContractCode;  				// 来源合同编码
	private Long 		fromContractLineId; 			// 来源合同行ID

	private String 		baseOu;							// 是否为基准ou
	private String		pricingFormulaValue;			// 价格公式值

	private String		standardCurrency;				// 本币币种

	private Double 		quantity;						// 预计采购量
	private BigDecimal 	amount;							// 预计采购金额（万元）
	private BigDecimal 	taxCurrentPrice;				// 含税现价
	private BigDecimal 	targetPrice;					// 含税拦标价

	/* ====================== End 招标单字段 ====================== */


	/* ===================== Start 投标单字段 ===================== */

	private Long 		orderLineId; 					// 投标行ID
	private Integer 	round;							// 轮次
	private String		win;							// 是否进入下一轮
	private String 		withStandard;					// 是否跟标
	private String 		comments;						// 备注
	private String		isProxyBidding;					// 是否代理报价

	private Long		bidVendorId;					// 投标供应商ID
	private Long		vendorId;						// 供应商Id
	private String		vendorCode;						// 供应商编码
	private String 		vendorName;						// 供应商名称

	private Long		currencyId;						// 报价币种ID
	private String		currencyCode;					// 报价币种编码
	private String 		currencyName;					// 报价币种名称
	private String 		MQO;							//
	private String 		transportType;					// 运输方式
	private Integer 	warrantyPeriod;					// 保修期
	private String 		leadTime;						// 供货周期
	private Date 		deliverDate;					// 承诺交货期

	private Collection<OrderlinePaymentTerm> paymentTerms;	// 付款条款

	private BigDecimal 	price;							// 本轮含税报价
	private BigDecimal 	totalAmount;					// 本轮组合总金额

	private BigDecimal 	currentRoundMinPrice;			// 本轮含税最低价
	private BigDecimal 	currentRoundMaxPrice;			// 本轮含税最高价
	private BigDecimal 	discountPrice;					// 本轮折息价
	private BigDecimal 	currentRoundMinDiscountPrice;	// 本轮最低折息价
	private BigDecimal 	currentRoundMaxDiscountPrice;	// 本轮最高折息价
	private BigDecimal 	totalDiscountAmount;			// 本轮组合折息总金额
	private BigDecimal 	currentRoundMinDiscountAmount;	// 本轮最低折息总金额
	private BigDecimal 	currentRoundMaxDiscountAmount;	// 本轮最高折息总金额

	private String 		selectionStatus;				// 评选结果
	private BigDecimal 	priceScore;						// 价格得分
	private BigDecimal 	techScore;						// 技术得分
	private BigDecimal 	perfScore;						// 绩效得分
	private BigDecimal 	compositeScore;					// 综合得分
	private Integer 	rank;							// 排名

	private String		taxKey;							// 税率KEY
	private BigDecimal	taxRate;						// 税率
	private BigDecimal	priceTax;						// 汇率
	private BigDecimal	noTaxPrice;						// 原币未税价
	private BigDecimal	standardCurrencyPrice;			// 本币含税价
	private BigDecimal	standardCurrencyNoTaxPrice;		// 本币未税价

	private BigDecimal	quotaQuantity;					// 配额数量
	private BigDecimal	quotaRatio;						// 配额比例

	/* ====================== End 投标单字段 ====================== */


	private String 		updateReason;					// 修改原因

	/**
	 * 是否海鲜价公式
	 */
	private String isSeaFoodFormula;
	/**
	 * 基价列表
	 */
	private String priceJson;
	/**
	 * 公式值
	 */
	private String formulaValue;
	/**
	 * 公式id
	 */
	private Long formulaId;
	/**
	 * 供应商要素报价明细
	 */
	private String essentialFactorValues;

	private String formulaResult;
	/**
	 * 创建 评选对象
	 *
	 * @param bidVendor			投标供应商
	 * @param orderHeader		供应商报价[头]
	 * @param orderLine			供应商报价[行]
	 * @param bidding 			招标单
	 * @param demandLine		寻源需求行
	 * @param biddingCurrency 	供应商报价[行]关联币种信息
	 * @param paymentTerms		供应商报价[行]关联付款条件
	 * @return A new {@link EvaluationResult}.
	 */
	public static EvaluationResult create(BidVendor bidVendor,
										  OrderHead orderHeader, OrderLine orderLine,
										  Biding bidding, BidRequirementLine demandLine,
										  BidBidingCurrency biddingCurrency,
										  Collection<OrderlinePaymentTerm> paymentTerms) {
		EvaluationResult vo = new EvaluationResult();
		if(orderLine.getQuotaRatio()!=null&&
				orderLine.getQuotaQuantity()==null){
			orderLine.setQuotaQuantity(orderLine.getQuotaRatio()
					.multiply(BigDecimal.valueOf(0.01))
					.multiply(BigDecimal.valueOf(demandLine.getQuantity())));
		}
		BeanUtils.copyProperties(demandLine, vo, getNullPropertyNames(demandLine));
		BeanUtils.copyProperties(orderLine, vo, getNullPropertyNames(orderLine));

		// 设置 是否代理报价
		vo.setIsProxyBidding(orderHeader.getIsProxyBidding());

		// 设置 供应商信息
		vo.setBidVendorId(bidVendor.getBidVendorId());
		vo.setVendorId(bidVendor.getVendorId());
		vo.setVendorCode(bidVendor.getVendorCode());
		vo.setVendorName(bidVendor.getVendorName());

		// 设置 报价币种信息
		vo.setCurrencyId(biddingCurrency.getCurrencyId());
		vo.setCurrencyCode(biddingCurrency.getCurrencyCode());
		vo.setCurrencyName(biddingCurrency.getCurrencyName());

		// 设置 汇率
		vo.setPriceTax(biddingCurrency.getPriceTax());
		// 设置 本位币
		vo.setStandardCurrency(bidding.getStandardCurrency());

		if(Objects.nonNull(vo.getPrice())&&Objects.nonNull(vo.getTaxRate())){
			// 计算 原币未税价
			vo.setNoTaxPrice(
					vo.getPrice().divide(BigDecimal.ONE.add(vo.getTaxRate().multiply(BigDecimal.valueOf(0.01))), 2, RoundingMode.HALF_UP)
			);

			// 计算 本币含税价
			vo.setStandardCurrencyPrice(
					vo.getPrice().divide(vo.getPriceTax(), 2, RoundingMode.HALF_UP)
			);

			// 计算 本币未税价
			vo.setStandardCurrencyNoTaxPrice(
					vo.getNoTaxPrice().divide(vo.getPriceTax(), 2, RoundingMode.HALF_UP)
			);

		}

		// 设置 付款条件
		vo.setPaymentTerms(paymentTerms);

		return vo;
	}

	private static String[] getNullPropertyNames(Object bean) {
		BeanWrapper wrapper = new BeanWrapperImpl(bean);
		return Arrays.stream(wrapper.getPropertyDescriptors())
				.map(pd -> {
					Object value = wrapper.getPropertyValue(pd.getName());
					return value == null ? pd.getName() : null;
				})
				.filter(Objects::nonNull)
				.toArray(String[]::new);
	}
}
