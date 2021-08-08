package com.midea.cloud.srm.ps.statement.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.pm.ps.StatementStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.po.dto.WarehouseReceiptPageQueryDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementDTO.InsertGroup;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementDTO.SubmitGroup;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementDTO.UpdateGroup;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementReceiptDTO;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementReturnDTO;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementHead;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderRequestDTO;
import com.midea.cloud.srm.ps.statement.service.IStatementHeadService;

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
@RequestMapping("ps/statementHead")
public class StatementHeadController extends BaseController {

	@Autowired
	private IStatementHeadService iStatementHeadService;

	@Autowired
	private SupcooperateClient supcooperateClient;

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
		return supcooperateClient.getStatementById(statementHeadId);
	}

	/**
	 * 采购商作废
	 *
	 * @param statementHeadId
	 */
	@GetMapping("/cancelStatement")
	public void cancelStatement(@RequestParam(value = "statementHeadId", required = true) Long statementHeadId) {
		StatementHead checkHead = supcooperateClient.getStatementHeadById(statementHeadId);
		Assert.notNull(checkHead, "找不到对账单");
		Assert.isTrue(StatementStatusEnum.get(checkHead.getStatementStatus()) == StatementStatusEnum.REJECTED, "只能作废已驳回对账单");
		checkHead.setStatementStatus(StatementStatusEnum.CANCAL.getValue());
		supcooperateClient.saveOrUpdateBatchStatementHead(Arrays.asList(checkHead));
	}

	/**
	 * 分页查询
	 *
	 * @param statementHead
	 * @return
	 */
	@PostMapping("/listPage")
	public PageInfo<StatementHead> listPage(@RequestBody StatementHead statementHead) {
		return supcooperateClient.listStatementHeadPage(statementHead);
	}

	/**
	 * 对账单选择退货单
	 *
	 * @param returnOrderRequestDTO
	 * @return
	 */
	@PostMapping("/listStatementReturnDTOPage")
	public PageInfo<StatementReturnDTO> listStatementReturnDTOPage(@RequestBody ReturnOrderRequestDTO returnOrderRequestDTO) {
		return supcooperateClient.listStatementReturnDTOPage(returnOrderRequestDTO);
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
		return supcooperateClient.listStatementReceiptDTOPage(warehouseReceiptPageQueryDTO);
	}
}
