package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * <pre>
 * 评选列表
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月26日 下午4:44:56  
 *  修改内容:
 *          </pre>
 */
@Data
@Accessors(chain = true)
public class EvaluationVO {

	private Long orderLineId;// 投标行表ID
	private String win;// 下轮允许投标
	private Long orgId;// 采购组织ID
	private String fullPathId; // 组织全路径虚拟ID
	private String orgName;// 采购组织名称
	private String itemGroup;// 组合
	private String targetNum;// 标的编码
	private String targetDesc;// 标的描述
	private BigDecimal currentRoundMinPrice;// 本轮最低价
	private BigDecimal taxCurrentPrice;// 含税现价
	private BigDecimal targetPrice;// 含税拦标价
	private String vendorName;// 投标供应商
	private BigDecimal price;// 本轮含税报价
	private BigDecimal priceScore;// 价格得分
	private BigDecimal techScore;// 技术得分
	private BigDecimal perfScore;// 绩效得分
	private BigDecimal compositeScore;// 综合得分
	private Integer rank;// 排名
	private Integer round;// 轮次
	private String selectionStatus;// 评选结果
	private String withStandard;// 是否跟标
	private Double quantity;// 预计采购量
	private BigDecimal amount;// 预计采购金额（万元）
	private BigDecimal totalAmount;// 组合总金额
	private String updateReason;// 修改原因
	private String comments;// 备注
	private Date priceStartTime;// 定价开始时间
	private Date priceEndTime;// 定价结束时间
	private String rowType;// 行类型
	private String uomDesc;// 单位
	private String categoryName;// 采购分类

}
