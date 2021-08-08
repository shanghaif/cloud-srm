package com.midea.cloud.srm.ps.statement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.pm.ps.StatementStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.po.dto.WarehouseReceiptPageQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementDTO.InsertGroup;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementDTO.SubmitGroup;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementDTO.UpdateGroup;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementReceiptDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementReturnDTO;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementHead;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderRequestDTO;
import com.midea.cloud.srm.ps.statement.service.IStatementHeadService;
import com.midea.cloud.srm.supcooperate.order.service.IReturnDetailService;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IWarehouseReceiptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

/**
 *
 * <pre>
 * 对账单
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jun 23, 20209:20:58 AM
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/pm/ps/statementHead")
public class StatementHeadController extends BaseController {

	@Autowired
	private IStatementHeadService iStatementHeadService;

	@Resource
	private com.midea.cloud.srm.supcooperate.statement.service.IStatementHeadService scIStatementHeadService;

	@Autowired
	private IReturnDetailService scIReturnDetailService;

	@Autowired
	private IWarehouseReceiptService scIWarehouseReceiptService;

	/**
	 * 采购商新增
	 *
	 * @param statementDTO
	 * @return
	 */
	@PostMapping("/saveStatement")
	public void saveStatement(@Validated(value = { InsertGroup.class }) @RequestBody StatementDTO statementDTO, BindingResult bindingResult) {
		if (bindingResult.getAllErrors() != null && bindingResult.getAllErrors().size() > 0) {
			throw new BaseException(bindingResult.getAllErrors().get(bindingResult.getAllErrors().size() - 1).getDefaultMessage());
		}
		iStatementHeadService.saveStatement(statementDTO);
	}

	/**
	 * 采购商修改
	 *
	 * @param statementDTO
	 * @return
	 */
	@PostMapping("/updateStatement")
	public void updateStatement(@Validated(value = { UpdateGroup.class }) @RequestBody StatementDTO statementDTO, BindingResult bindingResult) {
		if (bindingResult.getAllErrors() != null && bindingResult.getAllErrors().size() > 0) {
			throw new BaseException(bindingResult.getAllErrors().get(bindingResult.getAllErrors().size() - 1).getDefaultMessage());
		}
		iStatementHeadService.updateStatement(statementDTO);
	}

	/**
	 * 采购商提交
	 *
	 * @param statementDTO
	 * @return
	 */
	@PostMapping("/submitStatement")
	public void submitStatement(@Validated(value = { SubmitGroup.class }) @RequestBody StatementDTO statementDTO, BindingResult bindingResult) {
		if (bindingResult.getAllErrors() != null && bindingResult.getAllErrors().size() > 0) {
			throw new BaseException(bindingResult.getAllErrors().get(bindingResult.getAllErrors().size() - 1).getDefaultMessage());
		}
		iStatementHeadService.submitStatement(statementDTO);
	}

	/**
	 * 采购商删除
	 *
	 * @param statementHeadIds
	 */
	@PostMapping("/deleteStatement")
	public void deleteStatement(@RequestBody @NotEmpty List<Long> statementHeadIds) {
		iStatementHeadService.deleteStatement(statementHeadIds);
	}

	/**
	 * 采购商撤回
	 *
	 * @param statementHeadList
	 */
	@PostMapping("/recallStatement")
	public void recallStatement(@RequestBody @NotEmpty List<Long> statementHeadIds) {
		iStatementHeadService.recallStatement(statementHeadIds);
	}

	/**
	 * 根据ID获取详情
	 *
	 * @param statementHeadId
	 * @return
	 */
	@GetMapping("/getStatementById")
	public StatementDTO getStatementById(@RequestParam(value = "statementHeadId", required = true) Long statementHeadId) {
		return scIStatementHeadService.getStatementById(statementHeadId);
	}

	/**
	 * 采购商作废
	 *
	 * @param statementHeadId
	 */
	@GetMapping("/cancelStatement")
	public void cancelStatement(@RequestParam(value = "statementHeadId", required = true) Long statementHeadId) {
		StatementHead checkHead = scIStatementHeadService.getById(statementHeadId);
		Assert.notNull(checkHead, "找不到对账单");
		Assert.isTrue(StatementStatusEnum.get(checkHead.getStatementStatus()) == StatementStatusEnum.REJECTED, "只能作废已驳回对账单");
		checkHead.setStatementStatus(StatementStatusEnum.CANCAL.getValue());
		scIStatementHeadService.saveOrUpdateBatch(Arrays.asList(checkHead));
	}

	/**
	 * 分页查询
	 *
	 * @param statementHead
	 * @return
	 */
	@PostMapping("/listPage")
	public PageInfo<StatementHead> listPage(@RequestBody StatementHead statementHead) {
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		if (!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType()) && !StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())) {
			Assert.isTrue(false, "用户类型不存在");
		}
		StatementHead head = new StatementHead();
		BeanUtils.copyProperties(statementHead, head);
		QueryWrapper<StatementHead> queryWrapper = new QueryWrapper<StatementHead>(head);
		if (StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
			head.setVendorId(loginAppUser.getCompanyId());
			queryWrapper.ne("STATEMENT_STATUS", StatementStatusEnum.CREATE.getValue());
			queryWrapper.isNotNull("SUBMIT_TIME");
		}
		queryWrapper.like(StringUtils.isNotBlank(statementHead.getVendorCode()), "VENDOR_CODE", statementHead.getVendorCode());
		queryWrapper.like(StringUtils.isNotBlank(statementHead.getVendorName()), "VENDOR_NAME", statementHead.getVendorName());
		queryWrapper.like(StringUtils.isNotBlank(statementHead.getStatementNumber()), "STATEMENT_NUMBER", statementHead.getStatementNumber());
		queryWrapper.like(StringUtils.isNotBlank(statementHead.getOrganizationName()), "ORGANIZATION_NAME", statementHead.getOrganizationName());
		queryWrapper.ge(statementHead.getStatementStartTime() != null, "STATEMENT_START_TIME", statementHead.getStatementStartTime());
		queryWrapper.le(statementHead.getStatementEndTime() != null, "STATEMENT_END_TIME", statementHead.getStatementEndTime());
		queryWrapper.orderByDesc("STATEMENT_HEAD_ID");
		PageUtil.startPage(statementHead.getPageNum(), statementHead.getPageSize());
		return new PageInfo<StatementHead>(scIStatementHeadService.list(queryWrapper));
//		return supcooperateClient.listStatementHeadPage(statementHead);
	}

	/**
	 * 对账单选择退货单
	 *
	 * @param returnOrderRequestDTO
	 * @return
	 */
	@PostMapping("/listStatementReturnDTOPage")
	public PageInfo<StatementReturnDTO> listStatementReturnDTOPage(@RequestBody ReturnOrderRequestDTO returnOrderRequestDTO) {
		return scIReturnDetailService.listStatementReturnDTOPage(returnOrderRequestDTO);
	}

	/**
	 * 对账单选择入库单
	 *
	 * @param warehouseReceiptPageQueryDTO
	 * @return
	 */
	@PostMapping("/listStatementReceiptDTOPage")
	public PageInfo<StatementReceiptDTO> listStatementReceiptDTOPage(@RequestBody WarehouseReceiptPageQueryDTO warehouseReceiptPageQueryDTO) {
		warehouseReceiptPageQueryDTO.setWriteOff(YesOrNo.NO.getValue());
		return scIWarehouseReceiptService.listStatementReceiptDTOPage(warehouseReceiptPageQueryDTO);
	}
}
