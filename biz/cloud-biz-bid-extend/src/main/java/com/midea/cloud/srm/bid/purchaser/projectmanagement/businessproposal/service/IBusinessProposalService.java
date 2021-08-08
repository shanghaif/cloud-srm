package com.midea.cloud.srm.bid.purchaser.projectmanagement.businessproposal.service;

import java.util.List;
import java.util.Map;

import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.businessproposal.vo.BusinessItemVO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.businessproposal.vo.CancelBidParam;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.businessproposal.vo.OrderDetailVO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderHeadFile;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidOrderHeadVO;

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

	/**
	 * 返回
	 * @param orderHeadId
	 * @return
	 */
	BidOrderHeadVO queryNewOrderDetailList(Long orderHeadId);
	/**
	 * 生成投标监控报表
	 * 
	 * @param bidingId
	 */
	Map<String,Object> generateBidReport(Long bidingId) throws Exception;

	/**
	 * 根据投标头返回商务标
	 * @param orderHeadId
	 * @return
	 */
	List<OrderHeadFile> getOrderHeadFileByOrderHeadId(Long orderHeadId);

	/**
	 * 根据招标id和供应商id返回当前轮次的技术文件
	 */
	List<OrderHeadFile> getOrderHeadFileByVendorIdAndBidingId(Long biddingId,Long vendorId);
}
