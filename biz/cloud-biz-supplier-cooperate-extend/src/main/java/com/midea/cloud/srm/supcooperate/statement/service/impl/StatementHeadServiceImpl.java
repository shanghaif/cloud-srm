package com.midea.cloud.srm.supcooperate.statement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.pm.ps.StatementStatusEnum;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementDTO;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementHead;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementReceipt;
import com.midea.cloud.srm.model.pm.ps.statement.entity.StatementReturn;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnDetail;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceipt;
import com.midea.cloud.srm.supcooperate.order.service.IReturnDetailService;
import com.midea.cloud.srm.supcooperate.reconciliation.service.IWarehouseReceiptService;
import com.midea.cloud.srm.supcooperate.statement.controller.StatementHeadController.PassStatementDTO;
import com.midea.cloud.srm.supcooperate.statement.mapper.StatementHeadMapper;
import com.midea.cloud.srm.supcooperate.statement.service.IStatementHeadService;
import com.midea.cloud.srm.supcooperate.statement.service.IStatementReceiptService;
import com.midea.cloud.srm.supcooperate.statement.service.IStatementReturnService;

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
 *  修改日期: Jun 10, 20205:42:26 PM
 *  修改内容:
 *          </pre>
 */
@Service
public class StatementHeadServiceImpl extends ServiceImpl<StatementHeadMapper, StatementHead> implements IStatementHeadService {

	@Autowired
	private FileCenterClient fileCenterClient;

	@Autowired
	private IStatementReceiptService iStatementReceiptService;

	@Autowired
	private IStatementReturnService iStatementReturnService;

	@Autowired
	private IReturnDetailService iReturnDetailService;

	@Autowired
	private IWarehouseReceiptService iWarehouseReceiptService;

	@Override
	@Transactional
	public void saveOrUpdateStatement(StatementDTO statementDTO) {
		updateFile(statementDTO.getFileuploadList(), statementDTO.getStatementHead().getStatementHeadId());
		this.saveOrUpdate(statementDTO.getStatementHead());
		List<Long> warehouseReceiptIds = iStatementReceiptService.list(new QueryWrapper<StatementReceipt>(new StatementReceipt().setStatementHeadId(statementDTO.getStatementHead().getStatementHeadId()))).stream().map(StatementReceipt::getWarehouseReceiptId).collect(Collectors.toList());
		List<Long> returnDetailIds = iStatementReturnService.list(new QueryWrapper<StatementReturn>(new StatementReturn().setStatementHeadId(statementDTO.getStatementHead().getStatementHeadId()))).stream().map(StatementReturn::getReturnDetailId).collect(Collectors.toList());
		updateReturnDetailStatementRefer(returnDetailIds, YesOrNo.NO);
		updateWarehouseReceiptStatementRefer(warehouseReceiptIds, YesOrNo.NO);
		iStatementReceiptService.remove(new QueryWrapper<StatementReceipt>(new StatementReceipt().setStatementHeadId(statementDTO.getStatementHead().getStatementHeadId())));
		iStatementReturnService.remove(new QueryWrapper<StatementReturn>(new StatementReturn().setStatementHeadId(statementDTO.getStatementHead().getStatementHeadId())));
		statementDTO.getReceiptList().forEach(o -> o.setStatementReceiptId(IdGenrator.generate()));
		statementDTO.getReturnList().forEach(o -> o.setStatementReturnId(IdGenrator.generate()));
		iStatementReceiptService.saveOrUpdateBatch(statementDTO.getReceiptList());
		iStatementReturnService.saveOrUpdateBatch(statementDTO.getReturnList());
		updateReturnDetailStatementRefer(statementDTO.getReturnList().stream().map(StatementReturn::getReturnDetailId).collect(Collectors.toList()), YesOrNo.YES);
		updateWarehouseReceiptStatementRefer(statementDTO.getReceiptList().stream().map(StatementReceipt::getWarehouseReceiptId).collect(Collectors.toList()), YesOrNo.YES);
	}

	@Override
	public void deleteStatement(List<Long> statementHeadIds) {
		this.removeByIds(statementHeadIds);
		List<Long> warehouseReceiptIds = iStatementReceiptService.list(new QueryWrapper<StatementReceipt>().in("STATEMENT_HEAD_ID", statementHeadIds)).stream().map(StatementReceipt::getWarehouseReceiptId).collect(Collectors.toList());
		List<Long> returnDetailIds = iStatementReturnService.list(new QueryWrapper<StatementReturn>().in("STATEMENT_HEAD_ID", statementHeadIds)).stream().map(StatementReturn::getReturnDetailId).collect(Collectors.toList());
		iStatementReceiptService.removeByIds(warehouseReceiptIds);
		iStatementReturnService.removeByIds(returnDetailIds);
		updateReturnDetailStatementRefer(returnDetailIds, YesOrNo.NO);
		updateWarehouseReceiptStatementRefer(warehouseReceiptIds, YesOrNo.NO);
	}

	private void updateReturnDetailStatementRefer(List<Long> returnDetailIds, YesOrNo yesOrNo) {
		if (returnDetailIds.size() > 0) {
			List<ReturnDetail> listByIds = iReturnDetailService.listByIds(returnDetailIds);
			listByIds.forEach(o -> o.setStatementRefer(yesOrNo.getValue()));
			iReturnDetailService.updateBatchById(listByIds);
		}
	}

	private void updateWarehouseReceiptStatementRefer(List<Long> warehouseReceiptIds, YesOrNo yesOrNo) {
		if (warehouseReceiptIds.size() > 0) {
			List<WarehouseReceipt> listByIds = iWarehouseReceiptService.listByIds(warehouseReceiptIds);
//			listByIds.forEach(o -> o.setStatementRefer(yesOrNo.getValue()));
			iWarehouseReceiptService.updateBatchById(listByIds);
		}
	}

	@Override
	@Transactional
	public void passStatement(PassStatementDTO passStatementDTO) {
		StatementHead checkHead = this.getById(passStatementDTO.getStatementHeadId());
		Assert.notNull(checkHead, "找不到对账单");
		Assert.isTrue(StatementStatusEnum.get(checkHead.getStatementStatus()) == StatementStatusEnum.SUBMITTED, "只能审核已提交对账单");
		checkHead.setStatementStatus(StatementStatusEnum.PASS.getValue());
		checkHead.setSupplierNote(passStatementDTO.getSupplierNote());
		this.updateById(checkHead);
	}

	/**
	 * 绑定未绑定对账单的文件
	 *
	 * @param fileUploadList
	 * @param businessId
	 */
	private void updateFile(List<Fileupload> fileUploadList, Long businessId) {
		if (fileUploadList == null || fileUploadList.size() == 0) {
			return;
		}
		List<Long> fileuploadIds = new ArrayList<Long>();
		fileUploadList.forEach(item -> {
			if (item.getBusinessId() == null) {// 新增的文件，需要绑定
				fileuploadIds.add(item.getFileuploadId());
			}
		});
		if (fileuploadIds.size() != 0) {
			fileCenterClient.binding(fileuploadIds, businessId);
		}
	}

	@Override
	public StatementDTO getStatementById(Long statementHeadId) {
		StatementDTO result = new StatementDTO();
		StatementHead statementHead = this.getById(statementHeadId);
		List<StatementReceipt> receiptList = iStatementReceiptService.list(new QueryWrapper<StatementReceipt>(new StatementReceipt().setStatementHeadId(statementHeadId)));
		List<StatementReturn> returnList = iStatementReturnService.list(new QueryWrapper<StatementReturn>(new StatementReturn().setStatementHeadId(statementHeadId)));
		Fileupload fileupload = new Fileupload();
		fileupload.setPageNum(1);
		fileupload.setPageSize(10000);
		PageInfo<Fileupload> filePage = fileCenterClient.listPage(fileupload.setBusinessId(statementHeadId), "N");
		result.setStatementHead(statementHead).setReceiptList(receiptList).setReturnList(returnList).setFileuploadList(filePage.getList());
		return result;
	}

	@Override
	public void cancelStatement(Long statementHeadId) {
		StatementHead checkHead = this.getById(statementHeadId);
		Assert.notNull(checkHead, "找不到对账单");
		Assert.isTrue(StringUtils.equals(StatementStatusEnum.CREATE.getValue(), checkHead.getStatementStatus()), "只能作废拟定对账单");
		checkHead.setStatementStatus(StatementStatusEnum.CANCAL.getValue());
		this.updateById(checkHead);
		List<Long> warehouseReceiptIds = iStatementReceiptService.list(new QueryWrapper<StatementReceipt>(new StatementReceipt().setStatementHeadId(statementHeadId))).stream().map(StatementReceipt::getWarehouseReceiptId).collect(Collectors.toList());
		List<Long> returnDetailIds = iStatementReturnService.list(new QueryWrapper<StatementReturn>(new StatementReturn().setStatementHeadId(statementHeadId))).stream().map(StatementReturn::getReturnDetailId).collect(Collectors.toList());
		updateReturnDetailStatementRefer(returnDetailIds, YesOrNo.NO);
		updateWarehouseReceiptStatementRefer(warehouseReceiptIds, YesOrNo.NO);
	}

}
