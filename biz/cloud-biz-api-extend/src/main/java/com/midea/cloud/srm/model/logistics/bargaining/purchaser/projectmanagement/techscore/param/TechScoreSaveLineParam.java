package com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.techscore.param;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 
 * 
 * <pre>
 * 保存行数据
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月20日 上午10:46:52  
 *  修改内容:
 *          </pre>
 */
@Data
public class TechScoreSaveLineParam {

	private Long ruleLineId;// 评分规则ID
	private BigDecimal score;// 评分

}
