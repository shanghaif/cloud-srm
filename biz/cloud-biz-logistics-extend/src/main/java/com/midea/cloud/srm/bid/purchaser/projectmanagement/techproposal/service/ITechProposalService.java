package com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service;

import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.vo.ProgressOfTechBidVO;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.vo.ProgressOfTechScoreVO;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techscore.vo.TechScoreLineVO;

import java.util.List;
import java.util.Map;

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
