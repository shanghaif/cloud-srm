package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.vo;

import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.entity.ScoreRule;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.evaluation.entity.ScoreRuleLine;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * <pre>
 * 招标评分规则 视图对象
 * </pre>
 * 
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月26日 下午16:44:56
 *  修改内容:
 *          </pre>
 */
@Data
public class ScoreRuleVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private ScoreRule scoreRule;
	private List<ScoreRuleLine> scoreRuleLineList;
}
