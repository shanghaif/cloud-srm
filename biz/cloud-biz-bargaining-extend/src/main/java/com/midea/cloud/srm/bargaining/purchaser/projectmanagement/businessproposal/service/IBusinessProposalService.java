package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.businessproposal.service;

import java.util.List;
import java.util.Map;

import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.businessproposal.vo.BusinessItemVO;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.businessproposal.vo.CancelBidParam;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.businessproposal.vo.OrderDetailVO;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderHeadFile;
import com.midea.cloud.srm.model.bargaining.suppliercooperate.vo.BidOrderHeadVO;

/**
 * 
 * 
 * <pre>
 * 商务标管理
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月25日 上午10:04:31  
 *  修改内容:
 *          </pre>
 */
public interface IBusinessProposalService {

	/**
	 * 开标校验
	 * 
	 * @param bidingId
	 */
	String openBidCheck(Long bidingId);

	/**
	 * 开标
	 * 
	 * @param bidingId
	 */
	void openBid(Long bidingId);

	/**
	 * 列表
	 * 
	 * @param bidingId
	 * @return
	 */
	List<BusinessItemVO> queryBusinessItemList(Long bidingId);

	/**
	 *
	 * @param cancelBidParam
	 */
	void cancelBid(CancelBidParam cancelBidParam);

	/**
	 * 投标详情
	 * 
	 * @param bidingId
	 * @return
	 */
	List<OrderDetailVO> queryOrderDetailList(Long bidingId);

	BidOrderHeadVO queryNewOrderDetailList(Long orderHeadId);

    List<OrderHeadFile> getOrderHeadFileByOrderHeadId(Long orderHeadId);

	List<OrderHeadFile> getOrderHeadFileByVendorIdAndBidingId(Long biddingId, Long vendorId);

	/**
	 * 生成投标监控报表
	 * 
	 * @param bidingId
	 */
	Map<String,Object> generateBidReport(Long bidingId) throws Exception;
}
