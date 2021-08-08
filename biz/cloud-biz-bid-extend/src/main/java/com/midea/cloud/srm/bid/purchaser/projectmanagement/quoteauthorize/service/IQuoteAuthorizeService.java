package com.midea.cloud.srm.bid.purchaser.projectmanagement.quoteauthorize.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.quoteauthorize.entity.QuoteAuthorize;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.quoteauthorize.vo.QuoteAuthorizeVO;

import java.util.List;

/**
 * 
 * 
 * <pre>
 * 报价权限
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年4月23日 下午7:16:58  
 *  修改内容:
 *          </pre>
 */
public interface IQuoteAuthorizeService extends IService<QuoteAuthorize> {

	/**
	 * 获取 报价权限集
	 *
	 * 更改 - 供应商默认可报价的需求行
	 * 原：直接获取所有需求行
	 * 现：根据供应商组织品类关系等获取有权限的需求行
	 *
	 * @param biddingId 投标单ID
	 * @param vendorId 	供应商ID
	 * @return	报价权限集
	 *
	 * @author zixuan.yan@meicloud.com
	 */
	List<QuoteAuthorizeVO> findQuoteAuthorizes(Long biddingId, Long vendorId);

	void saveQuoteAuthorize(Long bidVendorId, List<QuoteAuthorizeVO> list);

	void saveQuoteAuthorize(List<BidVendor> bidVendorVOList);

	void checkQuoteAuthorize(Long bidingId);

}
