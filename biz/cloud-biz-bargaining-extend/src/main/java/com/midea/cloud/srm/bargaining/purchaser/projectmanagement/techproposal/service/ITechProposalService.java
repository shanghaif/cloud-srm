package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.service;

import java.util.List;
import java.util.Map;

import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.vo.ProgressOfTechBidVO;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.vo.ProgressOfTechScoreVO;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.vo.TechScoreLineVO;

/**
 * 
 * 
 * <pre>
 * 技术标管理
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月20日 下午5:32:17  
 *  修改内容:
 *          </pre>
 */
public interface ITechProposalService {

	String openBidCheck(Long bidingId);

	void openBid(Long bidingId);

	Map<String, Integer> countStatus(Long bidingId);

	List<ProgressOfTechBidVO> queryProgressOfTechBidList(Long bidingId, String vendorName, String progressOfTechBid, boolean needScale);

	List<ProgressOfTechScoreVO> queryProgressOfTechScoreList(Long bidingId, Long bidVendorId);

	List<TechScoreLineVO> queryTechScoreLineList(Long techScoreHeadId);

}
