package com.midea.cloud.srm.ps.statement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentApplyHeadQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentApplySaveDTO;
import com.midea.cloud.srm.model.pm.ps.statement.entity.PaymentApplyHead;
import com.midea.cloud.srm.model.pm.ps.statement.entity.PaymentApplyLine;
import com.midea.cloud.srm.ps.statement.service.IPaymentApplyHeadService;
import com.midea.cloud.srm.ps.statement.service.IPaymentApplyLineService;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * <pre>
 *   付款申请头 前端控制器
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jun 15, 202011:34:21 AM
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("ps/paymentApply")
public class PaymentApplyHeadController extends BaseController {

	@Autowired
	private IPaymentApplyHeadService iPaymentApplyHeadService;

	@Autowired
	private IPaymentApplyLineService iPaymentApplyLineService;

	/**
	 * 根据参数获取付款申请头行
	 *
	 * @param paymentApplyHeadQueryDTO
	 * @return
	 */
	@PostMapping("/getPaymentApplyByParm")
	public PaymentApplySaveDTO getPaymentApplyByParm(@RequestBody PaymentApplyHeadQueryDTO paymentApplyHeadQueryDTO) {
		return iPaymentApplyHeadService.getPaymentApplyByParm(paymentApplyHeadQueryDTO);
	}

	/**
	 * 保存付款申请
	 *
	 * @param paymentApplySaveDTO
	 */
	@PostMapping("/savePaymentApply")
	public void savePaymentApply(@RequestBody PaymentApplySaveDTO paymentApplySaveDTO) {
		iPaymentApplyHeadService.savePaymentApply(paymentApplySaveDTO);
	}

	/**
	 * 列表
	 *
	 * @param paymentPlanHeadQueryDTO
	 * @return
	 */
	@PostMapping("/listPage")
	public PageInfo<PaymentApplyHead> listPage(@RequestBody PaymentApplyHeadQueryDTO paymentApplyHeadQueryDTO) {
		return iPaymentApplyHeadService.listPage(paymentApplyHeadQueryDTO);
	}

	@Data
	@Accessors(chain = true)
	public static class PaymentApplyDTO {
		private PaymentApplyHead paymentApplyHead;
		private List<PaymentApplyLine> paymentApplyLines;
	}

	/**
	 * 获取详情
	 *
	 * @param paymentApplyHeadId
	 * @return
	 */
	@GetMapping("/getPaymentApplyById")
	public PaymentApplyDTO getPaymentApplyById(Long paymentApplyHeadId) {
		PaymentApplyHead paymentApplyHead = iPaymentApplyHeadService.getById(paymentApplyHeadId);
		List<PaymentApplyLine> paymentApplyLineList = iPaymentApplyLineService.list(new QueryWrapper<PaymentApplyLine>(new PaymentApplyLine().setPaymentApplyHeadId(paymentApplyHeadId)));
		return new PaymentApplyDTO().setPaymentApplyHead(paymentApplyHead).setPaymentApplyLines(paymentApplyLineList);
	}

	/**
	 * 同步ERP
	 *
	 * @param paymentApplyHeadId
	 */
	@GetMapping("/syncERP")
	public void syncERP(Long paymentApplyHeadId) {
		iPaymentApplyHeadService.syncERP(paymentApplyHeadId);
	}

}
