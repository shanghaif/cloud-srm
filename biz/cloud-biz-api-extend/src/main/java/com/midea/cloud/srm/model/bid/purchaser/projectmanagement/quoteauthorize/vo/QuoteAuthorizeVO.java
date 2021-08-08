package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.quoteauthorize.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * 
 * <pre>
 * 报价权限表VO
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年4月23日 下午7:07:08  
 *  修改内容:
 *          </pre>
 */
@Data
@Accessors(chain = true)
public class QuoteAuthorizeVO {

	private Long requirementLineId;// 需求行ID
	private String categoryName;// 采购分类
	private String itemGroup;// 组合
	private String targetNum;// 标的编码
	private String targetDesc;// 标的名称
	private String forbidden;// 禁止报价

	private Long	orgId;
	private String	orgCode;
	private String	orgName;
	private Long	invId;
	private String	invCode;
	private String	invName;
	private Long	ouId;
	private String	ouCode;
	private String	ouName;

}
