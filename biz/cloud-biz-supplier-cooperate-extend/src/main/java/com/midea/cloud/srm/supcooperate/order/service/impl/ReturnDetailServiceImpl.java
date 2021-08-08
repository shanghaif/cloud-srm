package com.midea.cloud.srm.supcooperate.order.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.WarehouseReceiptDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementReturnDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.ReturnDetail;
import com.midea.cloud.srm.supcooperate.order.mapper.ReturnDetailMapper;
import com.midea.cloud.srm.supcooperate.order.service.IReturnDetailService;

/**
 * <pre>
 *  退货单明细 接口实现类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/17 16:40
 *  修改内容:
 *          </pre>
 */
@Service
public class ReturnDetailServiceImpl extends ServiceImpl<ReturnDetailMapper, ReturnDetail> implements IReturnDetailService {

	@Autowired
	private ReturnDetailMapper returnDetailMapper;

	public List<ReturnDetailDTO> getReturnDetailListByReturnOrderId(Long returnOrderId) {
		return returnDetailMapper.getReturnDetailListByReturnOrderId(returnOrderId);
	}

	@Override
	public PageInfo<StatementReturnDTO> listStatementReturnDTOPage(ReturnOrderRequestDTO returnOrderRequestDTO) {
		PageUtil.startPage(returnOrderRequestDTO.getPageNum(), returnOrderRequestDTO.getPageSize());
		return new PageInfo<StatementReturnDTO>(returnDetailMapper.listStatementReturnDTOPage(returnOrderRequestDTO));
	}

	/**
	 * 查询退货单明细
	 * @param id
	 * @return
	 */
	@Override
	public List<ReturnDetail> list(Long id) {
		return returnDetailMapper.list(id);
	}

	@Override
	public List<ReturnDetail> listByDeliveryNoteDetailId(Collection<Long> deliveryNoteDetailIdList) {
		if (CollectionUtils.isEmpty(deliveryNoteDetailIdList)) {
			return Collections.emptyList();
		}

		LambdaQueryWrapper<ReturnDetail> returnDetailLambdaQueryWrapper = Wrappers.lambdaQuery(ReturnDetail.class)
				.in(ReturnDetail::getDeliveryNoteDetailId, deliveryNoteDetailIdList);
		return this.list(returnDetailLambdaQueryWrapper);
	}
}
