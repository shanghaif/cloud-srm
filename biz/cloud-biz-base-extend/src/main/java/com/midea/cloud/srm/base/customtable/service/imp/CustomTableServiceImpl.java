package com.midea.cloud.srm.base.customtable.service.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.customtable.mapper.CustomTableMapper;
import com.midea.cloud.srm.base.customtable.service.ICustomTableService;
import com.midea.cloud.srm.base.dict.service.IDictItemService;
import com.midea.cloud.srm.model.base.customtable.entity.CustomTable;
import com.midea.cloud.srm.model.base.customtable.vo.CustomTableVO;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;

/**
 * 
 * 
 * <pre>
 * 评选自定义表格
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月26日 下午7:29:54  
 *  修改内容:
 *          </pre>
 */
@Service
public class CustomTableServiceImpl extends ServiceImpl<CustomTableMapper, CustomTable> implements ICustomTableService {

	@Autowired
	private IDictItemService iDictItemService;

	@Override
	public List<CustomTableVO> queryCustomTableList(Long businessId, String businessType) {
		List<CustomTableVO> result = new ArrayList<CustomTableVO>();
		List<CustomTable> customTableList = this.list(new QueryWrapper<CustomTable>(new CustomTable().setBusinessId(businessId).setCreatedId(AppUserUtil.getLoginAppUser().getUserId())).orderByAsc("ORDER_NUM"));
		List<DictItemDTO> dictItemList = iDictItemService.listAllByDictCode(businessType);
		Collections.sort(dictItemList, new Comparator<DictItemDTO>() {
			@Override
			public int compare(DictItemDTO o1, DictItemDTO o2) {
				if (o1.getDictItemNo() != null && o2.getDictItemNo() != null) {
					return o1.getDictItemNo().compareTo(o2.getDictItemNo());
				}
				return 0;
			}
		});
		if (customTableList.size() == 0) {
			dictItemList.forEach(dto -> result.add(new CustomTableVO().setColumnCode(dto.getDictItemCode()).setColumnName(dto.getDictItemName()).setShowFlag("N").setOrderNum(dto.getDictItemNo())));
		} else {
			customTableList.forEach(table -> result.add(new CustomTableVO().setColumnCode(table.getColumnCode()).setColumnName(table.getColumnName()).setShowFlag(table.getShowFlag()).setOrderNum(table.getOrderNum())));
			Set<String> columnCodeSet = result.stream().map(CustomTableVO::getColumnCode).collect(Collectors.toSet());
			for (DictItemDTO dto : dictItemList) {
				if (!columnCodeSet.contains(dto.getDictItemCode())) {
					result.add(new CustomTableVO().setColumnCode(dto.getDictItemCode()).setColumnName(dto.getDictItemName()).setShowFlag("N").setOrderNum(dto.getDictItemNo()));
				}
			}
		}
		return result;
	}

	@Override
	public void saveOrUpdateCustomTable(Long businessId, String businessType, List<CustomTableVO> list) {
		Assert.notEmpty(list, "参数列表不能为空");
		for (CustomTableVO vo : list) {
			Assert.notNull(vo.getColumnCode(), "列编码不能为空");
		}
		this.remove(new QueryWrapper<CustomTable>(new CustomTable().setBusinessId(businessId).setBusinessType(businessType).setCreatedId(AppUserUtil.getLoginAppUser().getUserId())));
		List<CustomTable> saveList = new ArrayList<CustomTable>();
		int orderNum = 1;
		for (CustomTableVO vo : list) {
			saveList.add(new CustomTable().setCustomTableId(IdGenrator.generate()).setBusinessId(businessId).setBusinessType(businessType).setColumnCode(vo.getColumnCode()).setColumnName(vo.getColumnName()).setShowFlag(vo.getShowFlag()).setOrderNum(orderNum++));
		}
		this.saveOrUpdateBatch(saveList);
	}

}
