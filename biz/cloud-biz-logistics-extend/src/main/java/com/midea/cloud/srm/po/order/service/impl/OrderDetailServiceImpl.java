package com.midea.cloud.srm.po.order.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.pm.po.PurchaseOrderRowStatusEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.handler.CellTypeSheetWriteHandler;
import com.midea.cloud.common.handler.SpinnerSheetWriteHandler;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.contract.ContractClient;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.feign.supcooperate.SupcooperateClient;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.cm.contract.dto.ContractMaterialDTO;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import com.midea.cloud.srm.model.pm.po.dto.ImportOrderDetailTemplateDTO;
import com.midea.cloud.srm.model.pm.po.dto.NetPriceQueryDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.po.order.service.IOrderDetailService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 *
 *
 * <pre>
 * 订单明细
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年4月27日 下午2:48:38
 *  修改内容:
 *          </pre>
 */
@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

	private final static String SEPARATOR = "--";

	@Autowired
	private SupcooperateClient supcooperateClient;

	@Autowired
	private BaseClient baseClient;

	@Autowired
	private InqClient inqClient;

	@Autowired
	private ContractClient contractClient;

	@Override
	public void closeOrderDetail(List<Long> ids) {
		List<OrderDetail> detailListByIds = supcooperateClient.getOrderDetailListByIds(ids);
		for (OrderDetail orderDetail : detailListByIds) {
			orderDetail.setOrderDetailStatus(PurchaseOrderRowStatusEnum.CLOSED.getValue());
		}
		supcooperateClient.batchUpdateOrderDetail(detailListByIds);
	}

	@Override
	public Map<String, Object> downloadTemplate() throws Exception {
		String fileName = UUID.randomUUID().toString() + ".xls";
		Map<Integer, String[]> mapDropDown = new HashMap<Integer, String[]>();
		List<DictItemDTO> saleLabelDictList = baseClient.listAllByDictCode("SALE_LABEL");
		List<String> saleLabelList = new ArrayList<String>();
		for (DictItemDTO dto : saleLabelDictList) {
			saleLabelList.add(dto.getDictItemName() + SEPARATOR + dto.getDictItemCode());
		}
		mapDropDown.put(6, saleLabelList.toArray(new String[] {}));
		List<DictItemDTO> costTypeDictList = baseClient.listAllByDictCode("COST_TYPE");
		List<String> costTypeList = new ArrayList<String>();
		for (DictItemDTO dto : costTypeDictList) {
			costTypeList.add(dto.getDictItemName() + SEPARATOR + dto.getDictItemCode());
		}
		mapDropDown.put(7, costTypeList.toArray(new String[] {}));
		SpinnerSheetWriteHandler handler1 = new SpinnerSheetWriteHandler(mapDropDown);
		CellTypeSheetWriteHandler handler2 = new CellTypeSheetWriteHandler(ImportOrderDetailTemplateDTO.class);
		EasyExcel.write(fileName, ImportOrderDetailTemplateDTO.class).registerWriteHandler(handler2).registerWriteHandler(handler1).sheet("模板").doWrite(new ArrayList<ImportOrderDetailTemplateDTO>());
		File file = new File(fileName);
		byte[] buffer = FileUtils.readFileToByteArray(file);
		file.delete();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("buffer", buffer);
		result.put("fileName", "模板.xls");
		return result;
	}

	@Override
	public List<OrderDetail> importExcel(Long vendorId, Long organizationId, MultipartFile file) throws Exception {
		AnalysisEventListenerImpl<ImportOrderDetailTemplateDTO> listener = new AnalysisEventListenerImpl<ImportOrderDetailTemplateDTO>();
		ExcelReader excelReader = EasyExcel.read(file.getInputStream(), ImportOrderDetailTemplateDTO.class, listener).build();
		ReadSheet readSheet = EasyExcel.readSheet(0).build();
		excelReader.read(readSheet);
		excelReader.finish();
		List<ImportOrderDetailTemplateDTO> dataList = listener.getDatas();
		List<OrderDetail> result = new ArrayList<OrderDetail>();
		Set<String> materialCodeSet = new HashSet<String>();
		List<DictItemDTO> costTypeDictList = baseClient.listAllByDictCode("COST_TYPE");
		List<DictItemDTO> saleLabelDictList = baseClient.listAllByDictCode("SALE_LABEL");
		Set<String> costTypeSet = costTypeDictList.stream().map(DictItemDTO::getDictItemCode).collect(Collectors.toSet());
		Set<String> saleLabelSet = saleLabelDictList.stream().map(DictItemDTO::getDictItemCode).collect(Collectors.toSet());
		Map<String, Map<String, ContractMaterialDTO>> contractInfo = new HashMap<String, Map<String, ContractMaterialDTO>>();
		ContractMaterialDTO queryContractMaterialDTO = new ContractMaterialDTO();
		queryContractMaterialDTO.setPageNum(1);
		queryContractMaterialDTO.setPageSize(10000000);
		PageInfo<ContractMaterialDTO> listPageMaterialDTOByParm = contractClient.listPageMaterialDTOByParm(queryContractMaterialDTO.setOrganizationId(organizationId).setVendorId(vendorId));
		if (listPageMaterialDTOByParm != null && listPageMaterialDTOByParm.getList() != null) {
			contractInfo = listPageMaterialDTOByParm.getList().stream().collect(Collectors.groupingBy(ContractMaterialDTO::getContractNo, Collectors.toMap(ContractMaterialDTO::getMaterialCode, Function.identity(), (k1, k2) -> k1)));
		}
		int index = 1;
		for (ImportOrderDetailTemplateDTO dto : dataList) {
			Assert.notNull(dto.getMaterialCode(), "第" + index + "行物料编码不能为空");
			if (StringUtils.isNotBlank(dto.getContractNo())) {
				if (!contractInfo.containsKey(dto.getContractNo())) {
					throw new BaseException("第" + index + "行合同编号不存在");
				}
				if (contractInfo.get(dto.getContractNo()).get(dto.getMaterialCode()) == null) {
					throw new BaseException("第" + index + "行物料编码不存在于合同中");
				}
			}
			if (StringUtils.isNotBlank(dto.getSaleLabel()) && (dto.getSaleLabel().split(SEPARATOR).length != 2 || !saleLabelSet.contains(dto.getSaleLabel().split(SEPARATOR)[1]))) {
				throw new BaseException("不能改变寄售标识下拉框的值");
			}
			if (StringUtils.isNotBlank(dto.getCostType()) && (dto.getCostType().split(SEPARATOR).length != 2 || !costTypeSet.contains(dto.getCostType().split(SEPARATOR)[1]))) {
				throw new BaseException("不能改变成本类型下拉框的值");
			}
			materialCodeSet.add(dto.getMaterialCode());
			index++;
		}
		if (materialCodeSet.size() > 0) {
			List<MaterialItem> materialItemList = baseClient.listMaterialByCodeBatch(Arrays.asList(materialCodeSet.toArray(new String[] {})));
			Map<String, List<MaterialItem>> materialMap = materialItemList.stream().collect(Collectors.groupingBy(MaterialItem::getMaterialCode));
			index = 1;
			for (ImportOrderDetailTemplateDTO dto : dataList) {
				Assert.isTrue(materialMap.containsKey(dto.getMaterialCode()), "物料编码不存在");
				OrderDetail d = new OrderDetail();
				BeanUtils.copyProperties(dto, d);
				d.setRequirementDate(dto.getRequirementDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				if (StringUtils.isNotBlank(dto.getContractNo())) {
					ContractMaterialDTO contractMaterialDTO = contractInfo.get(dto.getContractNo()).get(dto.getMaterialCode());
					d.setExternalId(contractMaterialDTO.getContractHeadId());
					d.setExternalNum(contractMaterialDTO.getContractNo());
					d.setExternalRowNum(contractMaterialDTO.getLineNumber());
					d.setExternalRowId(contractMaterialDTO.getContractMaterialId());
					d.setMaterialName(contractMaterialDTO.getMaterialName());
					d.setMaterialId(contractMaterialDTO.getMaterialId());
					d.setCategoryName(contractMaterialDTO.getCategoryName());
					d.setCategoryId(contractMaterialDTO.getCategoryId());
					d.setUnitPriceExcludingTax(contractMaterialDTO.getUntaxedPrice());
					d.setUnit(contractMaterialDTO.getUnitCode());
					d.setCurrency(contractMaterialDTO.getCurrencyCode());
				} else {
					PriceLibrary priceLibrary = inqClient.getPriceLibraryByParam(new NetPriceQueryDTO().setVendorId(vendorId).setOrganizationId(organizationId).setMaterialId(materialMap.get(dto.getMaterialCode()).get(0).getMaterialId()));
					Assert.notNull(priceLibrary, "第" + index + "行物料编码不存在与价格目录");
					d.setMaterialName(priceLibrary.getItemDesc());
					d.setMaterialId(priceLibrary.getItemId());
					d.setCategoryName(priceLibrary.getCategoryName());
					d.setCategoryId(priceLibrary.getCategoryId());
					d.setUnitPriceExcludingTax(priceLibrary.getNotaxPrice());
					d.setUnit(priceLibrary.getUnit());
					d.setCurrency(priceLibrary.getCurrency());
				}
				if (StringUtils.isNotBlank(d.getSaleLabel())) {
					d.setSaleLabel(d.getSaleLabel().split(SEPARATOR)[1]);
				}
				if (StringUtils.isNotBlank(d.getCostType())) {
					d.setCostType(d.getCostType().split(SEPARATOR)[1]);
				}
				result.add(d);
				index++;
			}
		}
		return result;
	}

}
