package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techscore.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 
 * 
 * <pre>
 * 评分明细行VO
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月20日 上午10:09:15  
 *  修改内容:
 *          </pre>
 */
@Data
@Accessors(chain = true)
public class TechScoreLineVO {

	private Long ruleLineId;// 评分规则ID
	private String scoreItem;// 评分项
	private String scoreStandard;// 评分标准
	private Integer scoreWeight;// 权重
	private BigDecimal score;// 评分

}
