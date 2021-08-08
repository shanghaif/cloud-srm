package com.midea.cloud.srm.bid.purchaser.projectmanagement.quoteauthorize.controller;

import com.midea.cloud.srm.bid.purchaser.projectmanagement.quoteauthorize.service.IQuoteAuthorizeService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.quoteauthorize.vo.QuoteAuthorizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
 *  修改日期: 2020年4月23日 下午7:14:32  
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/quoteAuthorize")
public class QuoteAuthorizeController {

	@Autowired
	private IQuoteAuthorizeService iQuoteAuthorizeService;

	/**
	 * 报价权限列表
	 */
	@GetMapping("/findQuoteAuthorizes")
	public List<QuoteAuthorizeVO> findQuoteAuthorizes(@RequestParam(name = "bidingId") Long bidingId, @RequestParam(name = "vendorId") Long vendorId) {
		return iQuoteAuthorizeService.findQuoteAuthorizes(bidingId, vendorId);
	}

	/**
	 * 保存报价权限列表
	 */
	@PostMapping("/saveQuoteAuthorize")
	public void saveQuoteAuthorize(@RequestParam(name = "bidVendorId") Long bidVendorId, @RequestBody List<QuoteAuthorizeVO> list) {
		iQuoteAuthorizeService.saveQuoteAuthorize(bidVendorId, list);
	}

	/**
	 * 校验报价权限设置
	 */
	@GetMapping("/checkQuoteAuthorize")
	public void checkQuoteAuthorize(@RequestParam(name = "bidingId") Long bidingId) {
		iQuoteAuthorizeService.checkQuoteAuthorize(bidingId);
	}
}
