package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.businessproposal.vo;

import java.math.BigDecimal;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * 
 * <pre>
 * 投标详情
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年4月11日 上午9:48:38  
 *  修改内容:
 *          </pre>
 */
@Data
@Accessors(chain = true)
public class OrderDetailVO {

	private String orgName;// 采购组织
	private String itemGroup;// 组合
	private String targetNum;// 标的编码
	private String targetDesc;// 标的描述
	private BigDecimal lastRoundPrice;// 上轮报价
	private BigDecimal price;// 含税报价
	private BigDecimal targetPrice;// 含税拦标价
	private Integer lastRoundRank;// 上轮单编码排名
	private BigDecimal lastRoundMinPrice;// 上轮最低价
	private String purchaseCategory;// 采购分类
	private Double quantity;// 预计采购量
	private BigDecimal amount;// 预计采购金额（万元）
	private String comments;// 备注

}
