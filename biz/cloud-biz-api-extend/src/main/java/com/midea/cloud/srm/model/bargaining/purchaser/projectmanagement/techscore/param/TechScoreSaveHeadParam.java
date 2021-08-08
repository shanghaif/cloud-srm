package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.param;

import lombok.Data;

import java.util.List;

/**
 * 
 * 
 * <pre>
 *  保存参数
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 * <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月20日 上午10:48:49  
 *  修改内容: 
 * </pre>
 */
@Data
public class TechScoreSaveHeadParam {
	
	private Long bidingId;// 招标单ID
	private Long bidVendorId;// 供应商ID
	private String type;// SAVE , SUBMIT
	private List<TechScoreSaveLineParam> lineList;// 评分行数据


	/* ===================== 定制化字段 ===================== */

	private Long 	operateUserId;			// 操作用户Id
	private String 	operateUsername;		// 操作用户名
	private Long 	proxyOperateUserId;		// 代理操作用户Id
	private String 	proxyOperateUsername;	// 代理操作用户名
	private String	techComments; 			// 技术评分意见
}
