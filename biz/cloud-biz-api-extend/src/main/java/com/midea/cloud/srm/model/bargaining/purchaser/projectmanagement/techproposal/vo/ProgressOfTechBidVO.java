package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.vo;

import java.math.BigDecimal;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * 
 * <pre>
 * 技术投标进度
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月21日 下午3:32:43  
 *  修改内容:
 *          </pre>
 */
@Data
@Accessors(chain = true)
public class ProgressOfTechBidVO {

	private Long bidVendorId;// 投标供应商表ID
	private Long vendorId;// 供应商ID
	private String vendorName;// 供应商名称
	private String bidDetail;// 投标详情(投标单号)
	private String bidStatus;// 投标状态
	private String progressOfTechBid;// 技术评标进度
	private BigDecimal techTotalScore;// 技术总得分

}
