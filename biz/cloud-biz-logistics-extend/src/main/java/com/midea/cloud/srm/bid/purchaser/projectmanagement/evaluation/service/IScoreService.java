package com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service;

import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;

import java.util.List;

/**
 * 
 * 
 * <pre>
 * 供应商分数
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月27日 下午2:46:57  
 *  修改内容:
 *          </pre>
 */
public interface IScoreService {

	void calculateScore(Biding biding, List<OrderLine> submissionOrderLineList);

	void scoreToRank(Biding biding, List<OrderLine> submissionOrderLineList);
}
