package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techscore.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * 
 * <pre>
 * 技术评分分页行VO
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月19日 下午7:22:09  
 *  修改内容:
 *          </pre>
 */
@Data
@Accessors(chain = true)
public class TechScoreHeadVO {

	private Long bidVendorId;// 供应商ID
	private Long vendorId;// 供应商ID
	private String vendorCode;// 供应商编码
	private String vendorName;// 供应商名称
	private String phone;// 电话
	private String linkManName;// 联系人
	private String email;// 邮箱
	private String scoreStatus;// 评分状态

	private String techComments; 	// 技术评分意见

}
