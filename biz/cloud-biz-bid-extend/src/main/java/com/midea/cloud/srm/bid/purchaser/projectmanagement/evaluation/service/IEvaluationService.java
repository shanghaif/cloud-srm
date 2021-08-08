package com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.param.CreateFollowTenderParam;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.param.EvaluationQueryParam;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.vo.EvaluationResult;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderlinePaymentTerm;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.vo.BidOrderLineTemplateReportLineVO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * <pre>
 * 评选
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月26日 下午7:29:40  
 *  修改内容:
 *          </pre>
 */
public interface IEvaluationService {

	/**
	 * 获取 评选结果
	 *
	 * @param queryParam 查询参数
	 * @return 评选结果
	 */
	List<EvaluationResult> findEvaluationResults(EvaluationQueryParam queryParam);

	/**
	 * 获取 评选结果（分页）
	 *
	 * @param queryParam 查询参数
	 * @return 评选结果（分页）
	 */
	PageInfo<EvaluationResult> queryEvaluationPage(EvaluationQueryParam queryParam);

	/**
	 * 智能？？？评选
	 *
	 * @param biddingId	招标单ID
	 */
	void intelligentEvaluation(Long biddingId);

	/**
	 * 生成 价格审批单
	 *
	 * @param biddingId 招标单ID
	 */
	void generatePriceApproval(Long biddingId);


	void enterNextRound(List<Long> orderLineIdList);

	void eliminate(List<Long> orderLineIdList);

	void publicityOfResult(Long bidingId);

	void calculateWinBidResult(Long bidingId);

	void createFollowTender(CreateFollowTenderParam param);

	void endFollowBid(Long bidingId);

	void endEvaluation(Long bidingId);
	
	String getRoundEndTime(Long bidingId,Integer round);

	Map<String, Object> printPDF(Long bidingId) throws Exception;

	Map<String, Object> exportExcel(EvaluationQueryParam queryParam) throws Exception;

	/**
	 * 返回折息价格
	 *
	 * @param taxPrice        含税价
	 * @param type            币种类型
	 * @param isHonour        是否承兑
	 * @param currentDay      供应商报价账期
	 * @param monthlyInterest 月利率
	 * @param taxRate         税率
	 * @return
	 */
	BigDecimal calculateDiscountPrice(
			BigDecimal taxPrice,
			String type,
			Boolean isHonour,
			BigDecimal currentDay,
			BigDecimal monthlyInterest,
			BigDecimal taxRate,
			BigDecimal rate
	);

	/**
	 * 根据招标单的月利率和报价行进行折息计算
	 * @param orderLine
	 * @param monthlyInterest
	 * @param high 是否最高价格
	 * @return
	 */
	BigDecimal calculateDiscountPrice(OrderLine orderLine, BigDecimal monthlyInterest, boolean high, List<OrderlinePaymentTerm> terms);

	/**
	 * 手动更改评标结果
	 */
	void changeOrderLineStatus(List<Long> orderLinesIds,Boolean fail);

	/**
	 *计算配额结果
	 */
	List<EvaluationResult> calculateQuotaResult(Long bidingId);

	/**
	 * 生成模型报价报表，根据模型头+行分组
	 * @param currentResult 当前评选行
	 * @return 模型报价报表
	 */
	List<BidOrderLineTemplateReportLineVO> generateTemplatePriceReport(EvaluationResult currentResult);

	/**
	 * 导出评选结果
	 * @param queryParam
	 * @throws Exception
	 */
	void importModelDownload(EvaluationQueryParam queryParam, HttpServletResponse response) throws Exception;

	/**
	 * 评选结果导入
	 * @param file
	 * @param fileupload
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception;
}
