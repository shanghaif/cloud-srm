package com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.techproposal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 
 * 
 * <pre>
 * 技术评分进度
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月23日 上午11:18:34  
 *  修改内容:
 *          </pre>
 */
@Data
@Accessors(chain = true)
public class ProgressOfTechScoreVO {

	private Long techScoreHeadId;// 技术评分头ID
	private String fullName;// 评委
	private String position;// 岗位
	private String phone;// 联系电话
	private String email;// 邮箱
	private BigDecimal score;// 总分
	private String progressOfTechScore;// 技术评分进度

	private Long 	userId; 			// 评委用户ID
	private String 	userName;			// 评委用户名
	private Long  	maxEvaluateScore;	// 最高可评分数
	private String	isFirstResponse;	// 是否第一负责人
	private String	techComments;		// 技术评分意见
}
