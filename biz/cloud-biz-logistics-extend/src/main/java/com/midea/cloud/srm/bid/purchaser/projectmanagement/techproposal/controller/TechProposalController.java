package com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.controller;

import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.ITechProposalService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.vo.ProgressOfTechBidVO;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.vo.ProgressOfTechScoreVO;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techscore.vo.TechScoreLineVO;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
 *  修改日期: 2020年3月20日 下午5:30:25  
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/techProposal")
public class TechProposalController extends BaseController {

	@Autowired
	private ITechProposalService iTechProposalService;

	/**
	 * 技术开标校验
	 * 
	 * @param bidingId
	 */
	@GetMapping("/openBidCheck")
	public String openBidCheck(@RequestParam(required = true) Long bidingId) {
		return iTechProposalService.openBidCheck(bidingId);
	}

	/**
	 * 技术开标
	 * 
	 * @param bidingId
	 */
	@GetMapping("/openBid")
	public void openBid(@RequestParam(required = true) Long bidingId) {
		iTechProposalService.openBid(bidingId);
	}

	/**
	 * 1.当前需评审供应商数量 <br>
	 * 2.已完成评审的供应商
	 * 
	 * @param bidingId
	 * @return
	 */
	@GetMapping("/countStatus")
	public Map<String, Integer> countStatus(@RequestParam(required = true) Long bidingId) {
		return iTechProposalService.countStatus(bidingId);
	}

	/**
	 * 技术投标进度
	 * 
	 * @param bidingId
	 * @param vendorName
	 * @param progressOfTechBid
	 * @return
	 */
	@GetMapping("/queryProgressOfTechBidList")
	public List<ProgressOfTechBidVO> queryProgressOfTechBidList(@RequestParam Long bidingId, @RequestParam(required = false) String vendorName, @RequestParam(required = false)String progressOfTechBid) {
		return iTechProposalService.queryProgressOfTechBidList(bidingId, vendorName, progressOfTechBid, true);
	}

	/**
	 * 技术评分进度
	 * 
	 * @param bidingId
	 * @param bidVendorId
	 * @return
	 */
	@GetMapping("/queryProgressOfTechScoreList")
	public List<ProgressOfTechScoreVO> queryProgressOfTechScoreList(@RequestParam(required = true) Long bidingId, @RequestParam(required = false) Long bidVendorId) {
		return iTechProposalService.queryProgressOfTechScoreList(bidingId, bidVendorId);
	}

	/**
	 * 评分明细
	 * 
	 * @param techScoreHeadId
	 * @return
	 */
	@GetMapping("/queryTechScoreLineList")
	public List<TechScoreLineVO> queryTechScoreLineList(@RequestParam(required = true) Long techScoreHeadId) {
		return iTechProposalService.queryTechScoreLineList(techScoreHeadId);
	}
}
