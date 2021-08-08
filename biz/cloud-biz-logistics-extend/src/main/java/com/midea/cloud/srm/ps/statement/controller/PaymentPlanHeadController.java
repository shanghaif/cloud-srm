package com.midea.cloud.srm.ps.statement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.pm.ps.PaymentSchedulesStatusEnum;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentPlanHeadQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentPlanLineQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.PaymentPlanSaveDTO;
import com.midea.cloud.srm.model.pm.ps.statement.entity.PaymentPlanHead;
import com.midea.cloud.srm.model.pm.ps.statement.entity.PaymentPlanLine;
import com.midea.cloud.srm.ps.statement.service.IPaymentPlanHeadService;
import com.midea.cloud.srm.ps.statement.service.IPaymentPlanLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 *
 * <pre>
 * 付款计划
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
@RequestMapping("ps/paymentPlanHead")
public class PaymentPlanHeadController extends BaseController {

	@Autowired
	private IPaymentPlanHeadService iPaymentPlanHeadService;
	@Autowired
	private IPaymentPlanLineService iPaymentPlanLineService;

	/**
	 * 列表
	 *
	 * @param paymentPlanHeadQueryDTO
	 * @return
	 */
	@PostMapping("/listPage")
	public PageInfo<PaymentPlanHead> listPage(@RequestBody PaymentPlanHeadQueryDTO paymentPlanHeadQueryDTO) {
		return iPaymentPlanHeadService.listPage(paymentPlanHeadQueryDTO);
	}

	/**
	 * 保存
	 *
	 * @param paymentPlanSaveDTO
	 */
	@PostMapping("/savePaymentPlan")
	public void savePaymentPlan(@RequestBody PaymentPlanSaveDTO paymentPlanSaveDTO) {
		paymentPlanSaveDTO.getPaymentPlanHead().setPaymentPlanHeadId(null);
		paymentPlanSaveDTO.getPaymentPlanHead().setPaymentPlanStatus(PaymentSchedulesStatusEnum.DRAFT.getValue());
		iPaymentPlanHeadService.saveOrUpdatePaymentPlan(paymentPlanSaveDTO);
	}

	/**
	 * 更新
	 *
	 * @param paymentPlanSaveDTO
	 */
	@PostMapping("/updatePaymentPlan")
	public void updatePaymentPlan(@RequestBody PaymentPlanSaveDTO paymentPlanSaveDTO) {
		paymentPlanSaveDTO.getPaymentPlanHead().setPaymentPlanStatus(PaymentSchedulesStatusEnum.DRAFT.getValue());
		iPaymentPlanHeadService.saveOrUpdatePaymentPlan(paymentPlanSaveDTO);
	}

	/**
	 * 提交
	 *
	 * @param paymentPlanSaveDTO
	 */
	@PostMapping("/submitPaymentPlan")
	public void submitPaymentPlan(@RequestBody PaymentPlanSaveDTO paymentPlanSaveDTO) {
		paymentPlanSaveDTO.getPaymentPlanHead().setPaymentPlanStatus(PaymentSchedulesStatusEnum.UNDER_REVIEW.getValue());
		iPaymentPlanHeadService.saveOrUpdatePaymentPlan(paymentPlanSaveDTO);
	}

	/**
	 * 获取详情
	 *
	 * @param paymentPlanHeadId
	 * @return
	 */
	@GetMapping("/getPaymentPlanById")
	public PaymentPlanSaveDTO getPaymentPlanById(@RequestParam(value = "paymentPlanHeadId", required = true) Long paymentPlanHeadId) {
		return iPaymentPlanHeadService.getPaymentPlanById(paymentPlanHeadId);
	}

	/**
	 * 根据源id查找付款计划
	 *
	 * @param sourceLineId
	 * @return
	 */
	@GetMapping("/getPaymentPlanLineBySourceLineId")
	public List<PaymentPlanLine> getPaymentPlanLineBySourceLineId(@RequestParam(value = "sourceLineId") Long sourceLineId) {
		return iPaymentPlanLineService.list(new QueryWrapper<>(new PaymentPlanLine().setSourceLineId(sourceLineId)));
	}

	/**
	 * 删除
	 *
	 * @param paymentPlanIds
	 */
	@PostMapping("/deletePaymentPlan")
	public void deletePaymentPlan(@RequestBody @NotEmpty List<Long> paymentPlanIds) {
		iPaymentPlanHeadService.deletePaymentPlan(paymentPlanIds);
	}

	/**
	 * 驳回
	 *
	 * @param paymentPlanSaveDTO
	 */
	@PostMapping("/rejectPaymentPlan")
	public void rejectPaymentPlan(@RequestBody PaymentPlanSaveDTO paymentPlanSaveDTO) {
		iPaymentPlanHeadService.rejectPaymentPlan(paymentPlanSaveDTO);
	}

	/**
	 * 通过
	 *
	 * @param paymentPlanSaveDTO
	 */
	@PostMapping("/passPaymentPlan")
	public void passPaymentPlan(@RequestBody PaymentPlanSaveDTO paymentPlanSaveDTO) {
		iPaymentPlanHeadService.passPaymentPlan(paymentPlanSaveDTO);
	}

	/**
	 * 选择付款明细行
	 *
	 * @param paymentPlanLineQueryDTO
	 * @return
	 */
	@PostMapping("/listPaymentPlanLinePage")
	public PageInfo<PaymentPlanLine> listPaymentPlanLinePage(@RequestBody PaymentPlanLineQueryDTO paymentPlanLineQueryDTO) {
		return iPaymentPlanHeadService.listPaymentPlanLinePage(paymentPlanLineQueryDTO);
	}
}
