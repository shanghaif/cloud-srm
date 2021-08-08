package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techscore.param;

import com.midea.cloud.srm.model.common.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 
 * <pre>
 * 技术评分列表分页查询参数
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月20日 下午4:39:45  
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TechScoreQueryParam extends BasePage {

	private static final long serialVersionUID = 2313147382534734677L;

	private Long bidingId;// 招投标ID


	/* ===================== 定制化字段 ===================== */

	private Long operateUserId;			// 操作用户Id
}
