package com.midea.cloud.srm.report.costreduction.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.OcrCurrencyType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.OrgUtils;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationRelation;
import com.midea.cloud.srm.model.base.purchase.entity.LatestGidailyRate;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.report.chart.dto.ChartDTO;
import com.midea.cloud.srm.model.report.chart.dto.LineSeriesData;
import com.midea.cloud.srm.model.report.chart.dto.SeriesData;
import com.midea.cloud.srm.model.report.costreduction.dto.CostReductionDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.CostReductionParamDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.CostReductionResultDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.CrSetCategoryInfoDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.CrSetMaterialInfoDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.OrderWarehouseDTO;
import com.midea.cloud.srm.model.report.costreduction.dto.ReachRateDTO;
import com.midea.cloud.srm.model.report.costreduction.entity.CrSet;
import com.midea.cloud.srm.model.report.supplier.entity.SupplierConfig;
import com.midea.cloud.srm.report.config.service.IConfigService;
import com.midea.cloud.srm.report.costreduction.mapper.CostReductionMapper;
import com.midea.cloud.srm.report.costreduction.mapper.CrSetMapper;
import com.midea.cloud.srm.report.costreduction.service.ICostReductionService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author kuangzm
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期:2020/12/9
 *  修改内容:
 *          </pre>
 */
@Service
public class CostReductionServiceImpl implements ICostReductionService {

	@Autowired
	private BaseClient baseClient;

	@Resource
	private CostReductionMapper costReductionMapper;

	@Resource
	private CrSetMapper crSetMapper;
	
	@Resource
	private IConfigService iConfigService;

	/**
	 * 简本分析报表
	 * 
	 * @param param
	 * @return
	 */
	@Override
	public CostReductionResultDTO getCostReductionAnalysis(CostReductionParamDTO param) {
		CostReductionResultDTO result = new CostReductionResultDTO();
		// 设置时间
		this.setDateParam(param);

		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		// 获取用户id对应的权限节点
		List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
		// 根据类型过滤得出父节点
		List<Organization> filterByOrgTypeCode = permissionNodeResult.stream()
				.filter(p -> param.getOrganizationTypeCode().equals(p.getOrganizationTypeCode()))
				.collect(Collectors.toList());
		// 组织权限Map
		Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream()
				.collect(Collectors.toMap(Organization::getFullPathId, Function.identity()));
		// 获取所有组织节点
		List<OrganizationRelation> treeNewList = baseClient.allTree();
		List<String> resultFullPathIdList = new ArrayList<>();
		
		//组织过滤
		List<String> organizationList = null;
		if (StringUtil.isEmpty(param.getFullPathId())) {
	        for (Organization organization : filterByOrgTypeCode) {
	            String targetParentFullPathId = organization.getFullPathId();
	            organizationList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
	            resultFullPathIdList.addAll(organizationList);
	        }
		} else {
			organizationList = OrgUtils.findParentStart(treeNewList,param.getFullPathId(),permissionNodeResultMap);
            resultFullPathIdList.addAll(organizationList);
		}
		param.setList(resultFullPathIdList);

		// 汇率
		LatestGidailyRate gidailyRate = new LatestGidailyRate();
		gidailyRate.setToCurrency(OcrCurrencyType.CNY.getValue());
		Map<String, List<LatestGidailyRate>> currencyResult = baseClient.getCurrency(gidailyRate);

		// 获取所有的品类
		PurchaseCategory purchaseCategory = new PurchaseCategory();
		purchaseCategory.setEnabled("Y");
		purchaseCategory.setPageSize(999999);
		purchaseCategory.setPageNum(1);
		PageInfo<PurchaseCategory> categoryList = baseClient.listPageByParmCategory(purchaseCategory);
		List<PurchaseCategory> levelList = this.getCategoryListByLevel(param.getLevel(), categoryList);
		Map<Long, String> structMap = this.getCategoryStructMap(categoryList);

		List<OrderWarehouseDTO> list = this.costReductionMapper.queryWarehouse(param);

		CrSet set = crSetMapper.selectOne((Wrapper<CrSet>) new QueryWrapper().eq("YEAR", param.getYear()));
		
		// 获取冻结物料及价格
		Map<Long, BigDecimal> materialMap = this.getMaterialMap(param);

		// 获取品类降本率
		Map<Long, BigDecimal> categoryMap = this.getCategoryMap(param);

		// 计算降本金额、目标降本金额
		CostReductionDTO curYearData = this.getCrAmount(list, materialMap, currencyResult, categoryMap, set);
		// 目标采购金额
		result.setCurAmount(curYearData.getSumAmount());
		// 降本金额
		result.setCrAmount(curYearData.getAmount());
		// 降本率
		result.setCrRate(curYearData.getRate());
		// 上涨物料金额
		CostReductionDTO curYearDataNew = this.getCrAmountNew(list, materialMap, currencyResult, categoryMap, set);
		result.setUpMaterialAmount(curYearDataNew.getUpAmount());
		// 目标金额
		result.setCrTragetAmount(curYearData.getTargetAmount());
		if (null != set) {
			// 目标降本率
			result.setCrTragetRate(set.getRate());
		}
		
		// 品类降本达成率排名
		List<ReachRateDTO> categoryReachRate = new ArrayList<ReachRateDTO>();
		ReachRateDTO categoryReach = null;
		// 品类降本金额区间占比
		ChartDTO categoryAmountRate = new ChartDTO();
		// 品类上涨金额排名
		List<ReachRateDTO> categoryUpAmount = new ArrayList<ReachRateDTO>();
		ReachRateDTO categoryUp = null;
		
		List<OrderWarehouseDTO> tempList = null;
		CostReductionDTO temp = null;
		CostReductionDTO tempNew = null;
		
		int one = 0;
		int two = 0;
		int three = 0;
		int four = 0;
		SupplierConfig config = iConfigService.querySupplierConfig();
		
		for (PurchaseCategory category : levelList) {
			tempList = new ArrayList<OrderWarehouseDTO>();
			String structLevel = structMap.get(category.getCategoryId());
			if (null == structLevel || structLevel.isEmpty()) {
				continue;
			}
			for (OrderWarehouseDTO od : list) {
				String structData = structMap.get(od.getCategoryId());
				if (null == structData) {
					continue;
				}
				if (!structData.startsWith(structLevel)) {
					continue;
				}
				tempList.add(od);
			}
			if (tempList.size() == 0) {
				continue;
			}
			
			temp = this.getCrAmount(tempList, materialMap, currencyResult, categoryMap, set);
			if (null != temp && null != temp.getRate()) {
				BigDecimal targerRate = null;
				if (categoryMap.containsKey(category.getCategoryId())) {
					targerRate = categoryMap.get(category.getCategoryId());
				} else {
					if (null != set) {
						targerRate = set.getRate();
					}
				}
				categoryReach = new ReachRateDTO();
				categoryReach.setCategoryName(category.getCategoryName());
				categoryReach.setAmount(temp.getRate());
				if (null != targerRate) {
					categoryReach.setRate(temp.getRate().divide(targerRate, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
				} else {
					categoryReach.setRate(BigDecimal.ZERO);
				}
				categoryReachRate.add(categoryReach);
				
				if (temp.getAmount().doubleValue() <= new BigDecimal(config.getCategoryCrOne()).multiply(new BigDecimal(10000)).doubleValue()) {
					one ++;
				} else if (temp.getAmount().doubleValue() <= new BigDecimal(config.getCategoryCrTwoEnd()).multiply(new BigDecimal(10000)).doubleValue()) {
					two ++;
				} else if (temp.getAmount().doubleValue() <= new BigDecimal(config.getCategoryCrThreeEnd()).multiply(new BigDecimal(10000)).doubleValue()) {
					three++;
				} else {
					four++;
				}
			}

			tempNew = this.getCrAmountNew(tempList, materialMap, currencyResult, categoryMap, set);
			if (null != tempNew && null != tempNew.getRate()) {
				BigDecimal targerRate = null;
				if (categoryMap.containsKey(category.getCategoryId())) {
					targerRate = categoryMap.get(category.getCategoryId());
				} else {
					if (null != set) {
						targerRate = set.getRate();
					}
				}
				categoryReach = new ReachRateDTO();
				categoryReach.setCategoryName(category.getCategoryName());
				categoryReach.setAmount(tempNew.getRate());
				if (null != targerRate) {
					categoryReach.setRate(tempNew.getRate().divide(targerRate, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
				} else {
					categoryReach.setRate(BigDecimal.ZERO);
				}
				categoryReachRate.add(categoryReach);

				if (tempNew.getAmount().doubleValue() <= new BigDecimal(config.getCategoryCrOne()).multiply(new BigDecimal(10000)).doubleValue()) {
					one ++;
				} else if (tempNew.getAmount().doubleValue() <= new BigDecimal(config.getCategoryCrTwoEnd()).multiply(new BigDecimal(10000)).doubleValue()) {
					two ++;
				} else if (tempNew.getAmount().doubleValue() <= new BigDecimal(config.getCategoryCrThreeEnd()).multiply(new BigDecimal(10000)).doubleValue()) {
					three++;
				} else {
					four++;
				}
			}

			if (null != tempNew && null != tempNew.getUpAmount()) {
				categoryUp = new ReachRateDTO();
				categoryUp.setCategoryName(category.getCategoryName());
				categoryUp.setAmount(tempNew.getUpAmount());
				categoryUpAmount.add(categoryUp);
			}
		}
		
		List<SeriesData> seriesData = new ArrayList<SeriesData>();
		seriesData.add(new SeriesData("降本金额(X<="+config.getCategoryCrOne()+"万元)", new BigDecimal(one)));
    	seriesData.add(new SeriesData("降本金额("+config.getCategoryCrTwoStart()+"万元<X<="+config.getCategoryCrTwoEnd()+"万元)", new BigDecimal(two)));
    	seriesData.add(new SeriesData("降本金额("+config.getCategoryCrThreeStart()+"万元<X<="+config.getCategoryCrThreeEnd()+"万元)", new BigDecimal(three)));
    	seriesData.add(new SeriesData("降本金额(X>"+config.getCategoryCrFour()+"万元)", new BigDecimal(four)));
    	categoryAmountRate.setSeriesData(seriesData);
    	if (categoryUpAmount.size() > 0) {
    		Collections.sort(categoryUpAmount, Comparator.comparing(ReachRateDTO::getAmount).reversed());
    	}
    	if (categoryReachRate.size() > 0) {
    		Collections.sort(categoryReachRate, Comparator.comparing(ReachRateDTO::getRate).reversed());
    	}
    	result.setCategoryUpAmount(getFive(categoryUpAmount));
    	result.setCategoryAmountRate(categoryAmountRate);
    	result.setCategoryReachRate(getFive(categoryReachRate));
    	
    	
    	// 设置时间
    	param.setSeason(0);
    	this.setDateParam(param);
    	list = this.costReductionMapper.queryWarehouse(param);
    	
    	//月度
    	result.setMonthsTrend(this.getMonthsTrend(list, materialMap, currencyResult, categoryMap, set));
    	//年度
		result.setYearCumulativeRate(this.getYearCumulativeRate(list, materialMap, currencyResult, categoryMap, set));

		param.setSeason(null);
		// 同比
		param.setComfirmTimeBegin(this.setPerYear(param.getComfirmTimeBegin()));
		param.setComfirmTimeEnd(this.setPerYear(param.getComfirmTimeEnd()));
		param.setYear(param.getYear() - 1);
		set = crSetMapper.selectOne((Wrapper<CrSet>) new QueryWrapper().eq("YEAR", param.getYear()));
		materialMap = this.getMaterialMap(param);
		categoryMap = this.getCategoryMap(param);
		List<OrderWarehouseDTO> perlist = this.costReductionMapper.queryWarehouse(param);
		// 计算降本金额、目标降本金额
		CostReductionDTO perYearData = this.getCrAmount(perlist, materialMap, currencyResult, categoryMap, set);
		// 采购金额同比
		if (null != curYearData && null != curYearData.getSumAmount() &&  null != perYearData && null != perYearData.getSumAmount() && 0 != perYearData.getSumAmount().doubleValue()) {
			result.setYoyRate(new BigDecimal(1).subtract(
					curYearData.getSumAmount().divide(perYearData.getSumAmount(), 4, BigDecimal.ROUND_HALF_UP)));
		} else {
			result.setYoyRate(BigDecimal.ZERO);
		}
		// 上涨金额同比
		if (null != curYearData && null != curYearData.getUpAmount() && null != perYearData && null != perYearData.getUpAmount() && 0 != perYearData.getUpAmount().doubleValue()) {
			result.setYoyUpMaterialRate(new BigDecimal(1).subtract(
					curYearData.getUpAmount().divide(perYearData.getUpAmount(), 4, BigDecimal.ROUND_HALF_UP)));
		} else {
			result.setYoyUpMaterialRate(BigDecimal.ZERO);
		}

		return result;
	}
	
	private List<ReachRateDTO> getFive(List<ReachRateDTO> list ) {
		List<ReachRateDTO> newList = new ArrayList<ReachRateDTO>();
		if (null != list && list.size()>5) {
			for (int i=0;i<5;i++) {
				newList.add(list.get(i));
			}
			return newList;
		} else {
			return list;
		}
	}
	
	private ChartDTO getMonthsTrend(List<OrderWarehouseDTO> list, Map<Long, BigDecimal> materialMap,
			Map<String, List<LatestGidailyRate>> currencyResult, Map<Long, BigDecimal> categoryMap, CrSet set) {
		SimpleDateFormat format =  new SimpleDateFormat("M");
		List<OrderWarehouseDTO> tempList = null;
		CostReductionDTO temp = null;
		
    	List<String> xAxisData = new ArrayList<String>();
    	List<LineSeriesData> lineSeriesData = new ArrayList<LineSeriesData>();
    	LineSeriesData lineSeries1 = new LineSeriesData();
    	lineSeries1.setName("目标降本金额");
    	lineSeries1.setType("bar");
    	List<BigDecimal> data1 = new ArrayList<BigDecimal>();
    	LineSeriesData lineSeries2 = new LineSeriesData();
    	lineSeries2.setName("实际采购降本金额");
    	lineSeries2.setType("bar");
    	List<BigDecimal> data2 = new ArrayList<BigDecimal>();
    	LineSeriesData lineSeries3 = new LineSeriesData();
    	lineSeries3.setName("月降本达成率");
    	lineSeries3.setType("line");
    	List<BigDecimal> data3 = new ArrayList<BigDecimal>();
    	//月度
    	for (int i=1;i<=12;i++) {
    		tempList = new ArrayList<OrderWarehouseDTO>();
    		for (OrderWarehouseDTO od : list) {
    			String month = format.format(od.getConfirmTime());
    			if (String.valueOf(i).equals(month)) {
    				tempList.add(od);
    			}
			}
    		temp = null;
    		if (tempList.size()>0) {
    			temp = this.getCrAmount(tempList, materialMap, currencyResult, categoryMap, set);
    		}
    		xAxisData.add(i+"月");
    		if (null != temp) {
    			data1.add(temp.getTargetAmount());
    			data2.add(temp.getAmount());
    			if (null == temp.getTargetAmount() || temp.getTargetAmount().doubleValue() == 0) {
    				data3.add(BigDecimal.ZERO);
    			} else {
    				data3.add(temp.getAmount().divide(temp.getTargetAmount(),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
    			}
    			
    		} else {
    			data1.add(BigDecimal.ZERO);
    			data2.add(BigDecimal.ZERO);
    			data3.add(BigDecimal.ZERO);
    		}
    	}
    	lineSeries1.setData(data1);
    	lineSeries2.setData(data2);
    	lineSeries3.setData(data3);
    	lineSeriesData.add(lineSeries1);
    	lineSeriesData.add(lineSeries2);
    	lineSeriesData.add(lineSeries3);
    	ChartDTO monthsTrend = new ChartDTO();
    	monthsTrend.setLineSeriesData(lineSeriesData);
    	monthsTrend.setXAxisData(xAxisData);
    	return monthsTrend;
	}
	
	private ChartDTO getYearCumulativeRate(List<OrderWarehouseDTO> list, Map<Long, BigDecimal> materialMap,
			Map<String, List<LatestGidailyRate>> currencyResult, Map<Long, BigDecimal> categoryMap, CrSet set) {
		SimpleDateFormat format =  new SimpleDateFormat("M");
		List<OrderWarehouseDTO> tempList = null;
		CostReductionDTO temp = null;
		
    	List<String> xAxisData = new ArrayList<String>();
    	List<LineSeriesData> lineSeriesData = new ArrayList<LineSeriesData>();
    	LineSeriesData lineSeries = new LineSeriesData();
    	lineSeries.setName("年度累计降本率");
    	lineSeries.setType("bar");
    	List<BigDecimal> data = new ArrayList<BigDecimal>();
    	//月度
    	for (int i=1;i<=12;i++) {
    		tempList = new ArrayList<OrderWarehouseDTO>();
    		for (OrderWarehouseDTO od : list) {
    			String month = format.format(od.getConfirmTime());
    			if (Integer.valueOf(month).intValue()<=i) {
    				tempList.add(od);
    			}
			}
    		if (tempList.size()>0) {
    			temp = this.getCrAmount(tempList, materialMap, currencyResult, categoryMap, set);
    		}
    		xAxisData.add(i+"月");
    		if (null != temp) {
    			data.add(temp.getRate());
    		} else {
    			data.add(BigDecimal.ZERO);
    		}
    	}
    	lineSeries.setData(data);
    	lineSeriesData.add(lineSeries);
    	ChartDTO yearCumulativeRate = new ChartDTO();
    	yearCumulativeRate.setLineSeriesData(lineSeriesData);
    	yearCumulativeRate.setXAxisData(xAxisData);
    	return yearCumulativeRate;
	}
	

	private CostReductionDTO getCrAmount(List<OrderWarehouseDTO> list, Map<Long, BigDecimal> materialMap,
			Map<String, List<LatestGidailyRate>> currencyResult, Map<Long, BigDecimal> categoryMap, CrSet set) {
		CostReductionDTO result = new CostReductionDTO();
		BigDecimal sumAmount = BigDecimal.ZERO;
		BigDecimal tragetAmount = BigDecimal.ZERO;
		BigDecimal targetRate = BigDecimal.ZERO;
		BigDecimal materailTragetAmount = BigDecimal.ZERO;
		BigDecimal materialAmount = BigDecimal.ZERO;
		BigDecimal allAmount = BigDecimal.ZERO;
		Map<Long, Long> mcMap = new HashMap<Long, Long>();
		Map<Long,BigDecimal> priceMap = new HashMap<Long,BigDecimal>();
		if (null != list && list.size() > 0 && null != materialMap && materialMap.size() > 0) {
			Map<Long, BigDecimal> materialQuantity = new HashMap<Long, BigDecimal>();
			Map<Long, BigDecimal> materialSumAmount = new HashMap<Long, BigDecimal>();
			BigDecimal amount = null;
			BigDecimal quantity = null;
			for (OrderWarehouseDTO od : list) {
				mcMap.put(od.getMaterialId(), od.getCategoryId());
				amount = this.getOrderAmount(od, currencyResult);
				allAmount = allAmount.add(amount);
				if (materialMap.containsKey(od.getMaterialId())) {
					priceMap.put(od.getMaterialId(),materialMap.get(od.getMaterialId()));
					if (null != od.getWarehouseReceiptQuantity() && null != od.getReturnQuantity()) {
						quantity = od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity());
					} else if (null != od.getWarehouseReceiptQuantity()) {
						quantity = od.getWarehouseReceiptQuantity();
					}
					if (null != quantity) {
						if (materialQuantity.containsKey(od.getMaterialId())) {
							
							materialQuantity.put(od.getMaterialId(),
									materialQuantity.get(od.getMaterialId()).add(quantity));
						} else {
							materialQuantity.put(od.getMaterialId(), quantity);
						}
					}

					if (null != amount) {
						if (materialSumAmount.containsKey(od.getMaterialId())) {
							materialSumAmount.put(od.getMaterialId(),
									materialSumAmount.get(od.getMaterialId()).add(amount));
						} else {
							materialSumAmount.put(od.getMaterialId(), amount);
						}
					}

				}
			}

			Iterator it = priceMap.keySet().iterator();
			while (it.hasNext()) {
				Long materialId = (Long) it.next();
				Long categoryId = mcMap.get(materialId);
				BigDecimal price = priceMap.get(materialId);
				BigDecimal q = null;
				if (materialQuantity.containsKey(materialId)) {
					q = materialQuantity.get(materialId);
				}
				BigDecimal sum = null;
				if (materialSumAmount.containsKey(materialId)) {
					sum = materialSumAmount.get(materialId);
				}

				if (null != q && null != sum && null != price) {
					if (price.multiply(q).doubleValue() < sum.doubleValue()) {
						materialAmount = materialAmount.add(sum.subtract(price.multiply(q)));
					}
					sumAmount = sumAmount.add(price.multiply(q).subtract(sum));
					materailTragetAmount = materailTragetAmount.add(price.multiply(q));

					if (categoryMap.containsKey(categoryId)) {
						targetRate = categoryMap.get(categoryId);
					} else {
						if (null != set && null != set.getRate()) {
							targetRate = set.getRate();
						}
						
					}
					tragetAmount = tragetAmount.add(price.multiply(q).multiply(targetRate).divide(new  BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP));
				}
				
				
			}
			if(materailTragetAmount.doubleValue() == 0) {
				result.setRate(BigDecimal.ZERO);
			}else {
				result.setRate(
						sumAmount.divide(materailTragetAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
			}
			
			if (tragetAmount.doubleValue() != 0) {
				result.setTargetRate(targetRate);
				result.setReachRate(sumAmount.divide(tragetAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
			} else {
				result.setReachRate(BigDecimal.ZERO);
			}
		}
		result.setAmount(sumAmount);
		result.setSumAmount(sumAmount);
		result.setTargetAmount(tragetAmount);
		result.setUpAmount(materialAmount);
		
		return result;
	}


	private CostReductionDTO getCrAmountNew(List<OrderWarehouseDTO> list, Map<Long, BigDecimal> materialMap,
										 Map<String, List<LatestGidailyRate>> currencyResult, Map<Long, BigDecimal> categoryMap, CrSet set) {
		CostReductionDTO result = new CostReductionDTO();
		BigDecimal sumAmount = BigDecimal.ZERO;
		BigDecimal tragetAmount = BigDecimal.ZERO;
		BigDecimal targetRate = BigDecimal.ZERO;
		BigDecimal materailTragetAmount = BigDecimal.ZERO;
		BigDecimal materialAmount = BigDecimal.ZERO;
		BigDecimal allAmount = BigDecimal.ZERO;
		Map<Long, Long> mcMap = new HashMap<Long, Long>();
		Map<Long,BigDecimal> priceMap = new HashMap<Long,BigDecimal>();

		if (null != list && list.size() > 0 && null != materialMap && materialMap.size() > 0) {
			Map<Long, BigDecimal> materialQuantity = new HashMap<Long, BigDecimal>();
			Map<Long, BigDecimal> materialSumAmount = new HashMap<Long, BigDecimal>();
			BigDecimal amount = null;
			BigDecimal quantity = null;
			for (OrderWarehouseDTO od : list) {
				mcMap.put(od.getMaterialId(), od.getCategoryId());
				amount = this.getOrderAmount(od, currencyResult);
				allAmount = allAmount.add(amount);
				if (materialMap.containsKey(od.getMaterialId())) {
					priceMap.put(od.getMaterialId(),materialMap.get(od.getMaterialId()));
					if (null != od.getWarehouseReceiptQuantity() && null != od.getReturnQuantity()) {
						quantity = od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity());
					} else if (null != od.getWarehouseReceiptQuantity()) {
						quantity = od.getWarehouseReceiptQuantity();
					}
					if (null != quantity && materialMap.get(od.getMaterialId()).compareTo(od.getUnitPriceContainingTax())==-1) {
						if (materialQuantity.containsKey(od.getMaterialId())) {

							materialQuantity.put(od.getMaterialId(),
									materialQuantity.get(od.getMaterialId()).add(quantity));
						} else {
							materialQuantity.put(od.getMaterialId(), quantity);
						}
					}

					if (null != amount && materialMap.get(od.getMaterialId()).compareTo(od.getUnitPriceContainingTax())==-1) {
						if (materialSumAmount.containsKey(od.getMaterialId())) {
							materialSumAmount.put(od.getMaterialId(),
									materialSumAmount.get(od.getMaterialId()).add(amount));
						} else {
							materialSumAmount.put(od.getMaterialId(), amount);
						}
					}

				}
			}

			Iterator it = priceMap.keySet().iterator();
			while (it.hasNext()) {
				Long materialId = (Long) it.next();
				Long categoryId = mcMap.get(materialId);
				BigDecimal price = priceMap.get(materialId);
				BigDecimal q = null;
				if (materialQuantity.containsKey(materialId)) {
					q = materialQuantity.get(materialId);
				}
				BigDecimal sum = null;
				if (materialSumAmount.containsKey(materialId)) {
					sum = materialSumAmount.get(materialId);
				}

				if (null != q && null != sum && null != price) {
					if (price.multiply(q).doubleValue() < sum.doubleValue()) {
						materialAmount = materialAmount.add(sum.subtract(price.multiply(q)));
					}
					sumAmount = sumAmount.add(price.multiply(q).subtract(sum));
					materailTragetAmount = materailTragetAmount.add(price.multiply(q));

					if (categoryMap.containsKey(categoryId)) {
						targetRate = categoryMap.get(categoryId);
					} else {
						if (null != set && null != set.getRate()) {
							targetRate = set.getRate();
						}

					}
					tragetAmount = tragetAmount.add(price.multiply(q).multiply(targetRate).divide(new  BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP));
				}


			}
			result.setAmount(sumAmount);
			if(materailTragetAmount.doubleValue() == 0) {
				result.setRate(BigDecimal.ZERO);
			}else {
				result.setRate(
						sumAmount.divide(materailTragetAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
			}

			if (tragetAmount.doubleValue() != 0) {
				result.setTargetRate(targetRate);
				result.setReachRate(sumAmount.divide(tragetAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
			}

			result.setSumAmount(allAmount);
			result.setUpAmount(materialAmount);
			result.setTargetAmount(tragetAmount);

		}
		return result;
	}


	/**
	 * 获取冻结单价MAp
	 * 
	 * @param param
	 * @return
	 */
	private Map<Long, BigDecimal> getMaterialMap(CostReductionParamDTO param) {
		Map<Long, BigDecimal> map = new HashMap<Long, BigDecimal>();
		List<CrSetMaterialInfoDTO> list = this.costReductionMapper.queryMaterialInfo(param);
		if (null != list && list.size() > 0) {
			for (CrSetMaterialInfoDTO m : list) {
				map.put(m.getMaterialId(), m.getPrice());
			}
		}
		return map;
	}

	/**
	 * 获取冻结单价MAp
	 * 
	 * @param param
	 * @return
	 */
	private Map<Long, BigDecimal> getCategoryMap(CostReductionParamDTO param) {
		Map<Long, BigDecimal> map = new HashMap<Long, BigDecimal>();
		List<CrSetCategoryInfoDTO> list = this.costReductionMapper.queryCategoryInfo(param);
		if (null != list && list.size() > 0) {
			for (CrSetCategoryInfoDTO m : list) {
				map.put(m.getCategoryId(), m.getRate());
			}
		}
		return map;
	}

	private BigDecimal getOrderAmount(OrderWarehouseDTO od, Map<String, List<LatestGidailyRate>> currencyResult) {
		BigDecimal amount = BigDecimal.ZERO;
		if (null != od.getWarehouseReceiptQuantity()) {
			if (OcrCurrencyType.CNY.getValue().equals(od.getCurrency())) {
				if (null != od.getReturnQuantity() && null != od.getWarehouseReceiptQuantity()) {
					amount = od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity())
							.multiply(od.getUnitPriceContainingTax());
				} else if (null != od.getWarehouseReceiptQuantity()) {
					amount = od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax());
				}
			} else {
				BigDecimal rate = currencyResult.get(od.getCurrency()).get(0).getConversionRate();
				if (null != od.getReturnQuantity() && null != od.getWarehouseReceiptQuantity()) {
					amount = od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity())
							.multiply(od.getUnitPriceContainingTax()).multiply(rate);
				} else if (null != od.getWarehouseReceiptQuantity()) {
					amount = od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()).multiply(rate);
				}
			}
		}
		return amount;
	}

	private void setDateParam(CostReductionParamDTO dto) {
		if (null !=dto.getSeason() && dto.getSeason().equals(0)) {
			// 月度执行情况
			String dateModel1 = "$date-01-01 00:00:00";
			String dateModel2 = "$date-12-31 23:59:59";
			try {
				dto.setComfirmTimeBegin(DateUtil.parseDate(dateModel1.replace("$date", String.valueOf(dto.getYear()))));
				dto.setComfirmTimeEnd(DateUtil.parseDate(dateModel2.replace("$date", String.valueOf(dto.getYear()))));
			} catch (Exception e) {

			}
		} else {
			dto.setComfirmTimeBegin(DateUtil.getMonthStart(dto.getYear(), Integer.valueOf(dto.getStartMonth())));
			dto.setComfirmTimeEnd(DateUtil.getMonthEnd(dto.getYear(), Integer.valueOf(dto.getEndMonth())));
		}
	}

	private Date setPerYear(Date date) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		cale.add(Calendar.YEAR, -1);
		return cale.getTime();
	}

	/**
	 * 获取品类全路径
	 * 
	 * @param categoryList
	 * @return
	 */
	private Map<Long, String> getCategoryStructMap(PageInfo<PurchaseCategory> categoryList) {
		Map<Long, String> map = new HashMap<Long, String>();
		for (PurchaseCategory category : categoryList.getList()) {
			map.put(category.getCategoryId(), category.getStruct());
		}
		return map;
	}

	/**
	 * 获取前级别下所有品类
	 * 
	 * @param level
	 * @param categoryList
	 * @return
	 */
	private List<PurchaseCategory> getCategoryListByLevel(Integer level, PageInfo<PurchaseCategory> categoryList) {
		List<PurchaseCategory> list = new ArrayList<PurchaseCategory>();
		for (PurchaseCategory category : categoryList.getList()) {
			if (category.getLevel().equals(level)) {
				list.add(category);
			}
		}
		return list;
	}
	
	/**
	 * 月度降本率
	 */
	@Override
	public PageInfo<CostReductionDTO> queryMonthsDetail(CostReductionParamDTO param) {
		// 设置时间
		this.setDateParam(param);

		Integer pageSize = param.getPageSize();
    	Integer pageNum = param.getPageNum();
    	param.setPageNum(null);
    	param.setPageSize(null);
		
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		// 获取用户id对应的权限节点
		List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
		// 根据类型过滤得出父节点
		List<Organization> filterByOrgTypeCode = permissionNodeResult.stream()
				.filter(p -> param.getOrganizationTypeCode().equals(p.getOrganizationTypeCode()))
				.collect(Collectors.toList());
		// 组织权限Map
		Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream()
				.collect(Collectors.toMap(Organization::getFullPathId, Function.identity()));
		// 获取所有组织节点
		List<OrganizationRelation> treeNewList = baseClient.allTree();
		List<String> resultFullPathIdList = new ArrayList<>();

		//组织过滤
		List<String> organizationList = null;
		if (StringUtil.isEmpty(param.getFullPathId())) {
	        for (Organization organization : filterByOrgTypeCode) {
	            String targetParentFullPathId = organization.getFullPathId();
	            organizationList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
	            resultFullPathIdList.addAll(organizationList);
	        }
		} else {
			organizationList = OrgUtils.findParentStart(treeNewList,param.getFullPathId(),permissionNodeResultMap);
            resultFullPathIdList.addAll(organizationList);
		}
		param.setList(resultFullPathIdList);
		
		// 汇率
		LatestGidailyRate gidailyRate = new LatestGidailyRate();
		gidailyRate.setToCurrency(OcrCurrencyType.CNY.getValue());
		Map<String, List<LatestGidailyRate>> currencyResult = baseClient.getCurrency(gidailyRate);

		List<OrderWarehouseDTO> list = this.costReductionMapper.queryWarehouse(param);
		CrSet set = crSetMapper.selectOne((Wrapper<CrSet>) new QueryWrapper().eq("YEAR", param.getYear()));
		// 获取冻结物料及价格
		Map<Long, BigDecimal> materialMap = this.getMaterialMap(param);
		// 获取品类降本率
		Map<Long, BigDecimal> categoryMap = this.getCategoryMap(param);

		SimpleDateFormat format =  new SimpleDateFormat("M");
		
		List<OrderWarehouseDTO> tempList = null;
		CostReductionDTO temp = null;
		List<CostReductionDTO> resultList = new ArrayList<CostReductionDTO>();
		//月度
    	for (int i=Integer.valueOf(param.getStartMonth());i<=Integer.valueOf(param.getEndMonth());i++) {
    		tempList = new ArrayList<OrderWarehouseDTO>();
    		for (OrderWarehouseDTO od : list) {
    			String month = format.format(od.getConfirmTime());
    			if (String.valueOf(i).equals(month)) {
    				tempList.add(od);
    			}
			}
    		if (tempList.size() <=0) {
    			continue;
    		}
    		if (tempList.size()>0) {
    			temp = this.getCrAmount(tempList, materialMap, currencyResult, categoryMap, set);
    		}
    		
    		if (null != temp) {
    			if (temp.getTargetAmount().doubleValue() == 0) {
    				temp.setReachRate(BigDecimal.ZERO);
    			} else {
    				temp.setReachRate(temp.getSumAmount().divide(temp.getTargetAmount(),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
    			}
    		} else {
    			temp = new CostReductionDTO();
    			temp.setReachRate(BigDecimal.ZERO);
    		}
    		temp.setYm(param.getYear()+"年"+i+"月");
    		resultList.add(temp);
    	}

		return this.getPage(resultList, pageSize, pageNum);
	}
	
	
	/**
	 * 年度累计降本率
	 */
	@Override
	public PageInfo<CostReductionDTO> queryYearCumulativeDetail(CostReductionParamDTO param) {
		
		String startMonth = param.getStartMonth();
		param.setStartMonth("1");
		// 设置时间
		this.setDateParam(param);
		

		Integer pageSize = param.getPageSize();
    	Integer pageNum = param.getPageNum();
    	param.setPageNum(null);
    	param.setPageSize(null);
		
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		// 获取用户id对应的权限节点
		List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
		// 根据类型过滤得出父节点
		List<Organization> filterByOrgTypeCode = permissionNodeResult.stream()
				.filter(p -> param.getOrganizationTypeCode().equals(p.getOrganizationTypeCode()))
				.collect(Collectors.toList());
		// 组织权限Map
		Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream()
				.collect(Collectors.toMap(Organization::getFullPathId, Function.identity()));
		// 获取所有组织节点
		List<OrganizationRelation> treeNewList = baseClient.allTree();
		List<String> resultFullPathIdList = new ArrayList<>();

		//组织过滤
		List<String> organizationList = null;
		if (StringUtil.isEmpty(param.getFullPathId())) {
	        for (Organization organization : filterByOrgTypeCode) {
	            String targetParentFullPathId = organization.getFullPathId();
	            organizationList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
	            resultFullPathIdList.addAll(organizationList);
	        }
		} else {
			organizationList = OrgUtils.findParentStart(treeNewList,param.getFullPathId(),permissionNodeResultMap);
            resultFullPathIdList.addAll(organizationList);
		}
		param.setList(resultFullPathIdList);
		
		// 汇率
		LatestGidailyRate gidailyRate = new LatestGidailyRate();
		gidailyRate.setToCurrency(OcrCurrencyType.CNY.getValue());
		Map<String, List<LatestGidailyRate>> currencyResult = baseClient.getCurrency(gidailyRate);

		List<OrderWarehouseDTO> list = this.costReductionMapper.queryWarehouse(param);
		CrSet set = crSetMapper.selectOne((Wrapper<CrSet>) new QueryWrapper().eq("YEAR", param.getYear()));
		// 获取冻结物料及价格
		Map<Long, BigDecimal> materialMap = this.getMaterialMap(param);
		// 获取品类降本率
		Map<Long, BigDecimal> categoryMap = this.getCategoryMap(param);

		SimpleDateFormat format =  new SimpleDateFormat("M");
		
		List<OrderWarehouseDTO> tempList = null;
		CostReductionDTO temp = null;
		List<CostReductionDTO> resultList = new ArrayList<CostReductionDTO>();
		//月度
    	for (int i=Integer.valueOf(startMonth);i<=Integer.valueOf(param.getEndMonth());i++) {
    		tempList = new ArrayList<OrderWarehouseDTO>();
    		for (OrderWarehouseDTO od : list) {
    			String month = format.format(od.getConfirmTime());
    			if (i >= Integer.valueOf(month).intValue()) {
    				tempList.add(od);
    			}
			}
    		if (tempList.size() <0) {
    			continue;
    		}
    		if (tempList.size()>0) {
    			temp = this.getCrAmount(tempList, materialMap, currencyResult, categoryMap, set);
    		}
    		
    		if (null == temp) {
    			temp = new CostReductionDTO();
    		}
    		if (null != set && null != set.getRate()) {
    			temp.setTargetRate(set.getRate());
    		}
    		
    		temp.setYm(param.getYear()+"年"+i+"月");
    		resultList.add(temp);
    	}

		return this.getPage(resultList, pageSize, pageNum);
	}
	
	
	/**
	 * 品类降本达成率
	 */
	@Override
	public PageInfo<CostReductionDTO> queryCategorRateDetail(CostReductionParamDTO param) {
		// 设置时间
		this.setDateParam(param);

		Integer pageSize = param.getPageSize();
    	Integer pageNum = param.getPageNum();
    	param.setPageNum(null);
    	param.setPageSize(null);
		
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		// 获取用户id对应的权限节点
		List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
		// 根据类型过滤得出父节点
		List<Organization> filterByOrgTypeCode = permissionNodeResult.stream()
				.filter(p -> param.getOrganizationTypeCode().equals(p.getOrganizationTypeCode()))
				.collect(Collectors.toList());
		// 组织权限Map
		Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream()
				.collect(Collectors.toMap(Organization::getFullPathId, Function.identity()));
		// 获取所有组织节点
		List<OrganizationRelation> treeNewList = baseClient.allTree();
		List<String> resultFullPathIdList = new ArrayList<>();

		//组织过滤
		List<String> organizationList = null;
		if (StringUtil.isEmpty(param.getFullPathId())) {
	        for (Organization organization : filterByOrgTypeCode) {
	            String targetParentFullPathId = organization.getFullPathId();
	            organizationList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
	            resultFullPathIdList.addAll(organizationList);
	        }
		} else {
			organizationList = OrgUtils.findParentStart(treeNewList,param.getFullPathId(),permissionNodeResultMap);
            resultFullPathIdList.addAll(organizationList);
		}
		param.setList(resultFullPathIdList);
		
		// 汇率
		LatestGidailyRate gidailyRate = new LatestGidailyRate();
		gidailyRate.setToCurrency(OcrCurrencyType.CNY.getValue());
		Map<String, List<LatestGidailyRate>> currencyResult = baseClient.getCurrency(gidailyRate);
		
		// 获取所有的品类
		PurchaseCategory purchaseCategory = new PurchaseCategory();
		purchaseCategory.setEnabled("Y");
		purchaseCategory.setPageSize(999999);
		purchaseCategory.setPageNum(1);
		PageInfo<PurchaseCategory> categoryList = baseClient.listPageByParmCategory(purchaseCategory);
		List<PurchaseCategory> levelList = this.getCategoryListByLevel(param.getLevel(), categoryList);
		Map<Long, String> structMap = this.getCategoryStructMap(categoryList);

		List<OrderWarehouseDTO> list = this.costReductionMapper.queryWarehouse(param);
		CrSet set = crSetMapper.selectOne((Wrapper<CrSet>) new QueryWrapper().eq("YEAR", param.getYear()));
		// 获取冻结物料及价格
		Map<Long, BigDecimal> materialMap = this.getMaterialMap(param);
		// 获取品类降本率
		Map<Long, BigDecimal> categoryMap = this.getCategoryMap(param);

		List<OrderWarehouseDTO> tempList = null;
		CostReductionDTO temp = null;
		List<CostReductionDTO> resultList = new ArrayList<CostReductionDTO>();
		
		
		for (PurchaseCategory category : levelList) {
			tempList = new ArrayList<OrderWarehouseDTO>();
			String structLevel = structMap.get(category.getCategoryId());
			if (null == structLevel || structLevel.isEmpty()) {
				continue;
			}
			for (OrderWarehouseDTO od : list) {
				String structData = structMap.get(od.getCategoryId());
				if (null == structData) {
					continue;
				}
				if (!structData.startsWith(structLevel)) {
					continue;
				}
				tempList.add(od);
			}
			if (tempList.size() == 0) {
				continue;
			}
			
			temp = this.getCrAmount(tempList, materialMap, currencyResult, categoryMap, set);
			if (null != temp && null != temp.getRate()) {
				if (null != temp.getTargetAmount()) {
					if (null != temp.getReachRate()) {
						temp.setCategoryReachRate(temp.getReachRate());
					}
				} else {
					temp.setCategoryReachRate(BigDecimal.ZERO);
				}
			} else {
				temp = new CostReductionDTO();
				temp.setCategoryReachRate(BigDecimal.ZERO);
			}
			temp.setCategoryName(category.getCategoryName());
			temp.setCategoryCrAmount(temp.getAmount());
			resultList.add(temp);
		}
		
		if (resultList.size() >0) {
			 //排序
 		   Collections.sort(resultList, Comparator.comparing(CostReductionDTO::getCategoryReachRate).reversed());
 		   int no = 1;
 		   for (CostReductionDTO dto : resultList) {
 			   dto.setNo(no);
 			   no++;
 		   }
		}
		return this.getPage(resultList, pageSize, pageNum);
	}
	
	
	/**
	 * 品类降本金额区间占比
	 */
	@Override
	public PageInfo<CostReductionDTO> queryCategorAmountDetail(CostReductionParamDTO param) {
		// 设置时间
		this.setDateParam(param);

		Integer pageSize = param.getPageSize();
    	Integer pageNum = param.getPageNum();
    	param.setPageNum(null);
    	param.setPageSize(null);
		
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		// 获取用户id对应的权限节点
		List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
		// 根据类型过滤得出父节点
		List<Organization> filterByOrgTypeCode = permissionNodeResult.stream()
				.filter(p -> param.getOrganizationTypeCode().equals(p.getOrganizationTypeCode()))
				.collect(Collectors.toList());
		// 组织权限Map
		Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream()
				.collect(Collectors.toMap(Organization::getFullPathId, Function.identity()));
		// 获取所有组织节点
		List<OrganizationRelation> treeNewList = baseClient.allTree();
		List<String> resultFullPathIdList = new ArrayList<>();

		//组织过滤
		List<String> organizationList = null;
		if (StringUtil.isEmpty(param.getFullPathId())) {
	        for (Organization organization : filterByOrgTypeCode) {
	            String targetParentFullPathId = organization.getFullPathId();
	            organizationList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
	            resultFullPathIdList.addAll(organizationList);
	        }
		} else {
			organizationList = OrgUtils.findParentStart(treeNewList,param.getFullPathId(),permissionNodeResultMap);
            resultFullPathIdList.addAll(organizationList);
		}
		param.setList(resultFullPathIdList);
		
		// 汇率
		LatestGidailyRate gidailyRate = new LatestGidailyRate();
		gidailyRate.setToCurrency(OcrCurrencyType.CNY.getValue());
		Map<String, List<LatestGidailyRate>> currencyResult = baseClient.getCurrency(gidailyRate);
		
		// 获取所有的品类
		PurchaseCategory purchaseCategory = new PurchaseCategory();
		purchaseCategory.setEnabled("Y");
		purchaseCategory.setPageSize(999999);
		purchaseCategory.setPageNum(1);
		PageInfo<PurchaseCategory> categoryList = baseClient.listPageByParmCategory(purchaseCategory);
		List<PurchaseCategory> levelList = this.getCategoryListByLevel(param.getLevel(), categoryList);
		Map<Long, String> structMap = this.getCategoryStructMap(categoryList);

		List<OrderWarehouseDTO> list = this.costReductionMapper.queryWarehouse(param);
		CrSet set = crSetMapper.selectOne((Wrapper<CrSet>) new QueryWrapper().eq("YEAR", param.getYear()));
		// 获取冻结物料及价格
		Map<Long, BigDecimal> materialMap = this.getMaterialMap(param);
		// 获取品类降本率
		Map<Long, BigDecimal> categoryMap = this.getCategoryMap(param);
		//配置
		SupplierConfig config = iConfigService.querySupplierConfig();
		

		List<OrderWarehouseDTO> tempList = null;
		CostReductionDTO temp = null;
		List<CostReductionDTO> resultList = new ArrayList<CostReductionDTO>();
		
		BigDecimal sumAmount = BigDecimal.ZERO;
		int one = 0;
		int two = 0;
		int three = 0;
		int four = 0;
		for (PurchaseCategory category : levelList) {
			tempList = new ArrayList<OrderWarehouseDTO>();
			String structLevel = structMap.get(category.getCategoryId());
			if (null == structLevel || structLevel.isEmpty()) {
				continue;
			}
			for (OrderWarehouseDTO od : list) {
				String structData = structMap.get(od.getCategoryId());
				if (null == structData) {
					continue;
				}
				if (!structData.startsWith(structLevel)) {
					continue;
				}
				tempList.add(od);
			}
			if (tempList.size() == 0) {
				continue;
			}
			
			temp = this.getCrAmount(tempList, materialMap, currencyResult, categoryMap, set);
			if (null == temp) {
				temp = new CostReductionDTO();
			}
			if (temp.getAmount() == null) {
				temp.setAmount(BigDecimal.ZERO);
			}
			if (temp.getAmount().doubleValue() <= new BigDecimal(config.getCategoryCrOne()).multiply(new BigDecimal(10000)).doubleValue()) {
				temp.setBelongRange("金额<"+config.getCategoryCrOne()+"万元");
				one ++;
			} else if (temp.getAmount().doubleValue() <= new BigDecimal(config.getCategoryCrTwoEnd()).multiply(new BigDecimal(10000)).doubleValue()) {
				temp.setBelongRange(config.getCategoryCrTwoStart()+"万元"+"<="+"金额<"+config.getCategoryCrTwoEnd()+"万元");
				two ++;
			} else if (temp.getAmount().doubleValue() <= new BigDecimal(config.getCategoryCrThreeEnd()).multiply(new BigDecimal(10000)).doubleValue()) {
				temp.setBelongRange(config.getCategoryCrThreeStart()+"万元"+"<="+"金额<"+config.getCategoryCrThreeEnd()+"万元");
				three++;
			} else {
				temp.setBelongRange("金额>="+config.getCategoryCrFour()+"万元");
				four++;
			}
			temp.setCategoryName(category.getCategoryName());
			temp.setCategoryCrAmount(temp.getAmount());
			resultList.add(temp);
		}
		sumAmount = new BigDecimal(one).add(new BigDecimal(two)).add(new BigDecimal(three)).add(new BigDecimal(four));
		if (resultList.size() >0) {
			 //排序
 		   Collections.sort(resultList, Comparator.comparing(CostReductionDTO::getAmount).reversed());
 		   int no = 1;
 		   for (CostReductionDTO dto : resultList) {
 			   dto.setNo(no);
 			   //FIXME 降本金额占比
 			   if (dto.getAmount().doubleValue() <= new BigDecimal(config.getCategoryCrOne()).multiply(new BigDecimal(10000)).doubleValue()) {
 				   	if (null != sumAmount) {
 					   dto.setAmountRate(new BigDecimal(one).divide(sumAmount, 4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
 				   	} 
				} else if (dto.getAmount().doubleValue() <= new BigDecimal(config.getCategoryCrTwoEnd()).multiply(new BigDecimal(10000)).doubleValue()) {
					if (null != sumAmount) {
	 					dto.setAmountRate(new BigDecimal(two).divide(sumAmount, 4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
	 				} 
				} else if (dto.getAmount().doubleValue() <= new BigDecimal(config.getCategoryCrThreeEnd()).multiply(new BigDecimal(10000)).doubleValue()) {
					if (null != sumAmount) {
						dto.setAmountRate(new BigDecimal(three).divide(sumAmount, 4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
	 				} 
				} else {
					if (null != sumAmount) {
						dto.setAmountRate(new BigDecimal(four).divide(sumAmount, 4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
	 				} 
				}
 			   no++;
 		   }
		}
		return this.getPage(resultList, pageSize, pageNum);
	}
	
	
	/**
	 * 品类上涨金额排名
	 */
	@Override
	public PageInfo<CostReductionDTO> queryCategorUpAmountDetail(CostReductionParamDTO param) {
		// 设置时间
		this.setDateParam(param);

		Integer pageSize = param.getPageSize();
    	Integer pageNum = param.getPageNum();
    	param.setPageNum(null);
    	param.setPageSize(null);
		
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		// 获取用户id对应的权限节点
		List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
		// 根据类型过滤得出父节点
		List<Organization> filterByOrgTypeCode = permissionNodeResult.stream()
				.filter(p -> param.getOrganizationTypeCode().equals(p.getOrganizationTypeCode()))
				.collect(Collectors.toList());
		// 组织权限Map
		Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream()
				.collect(Collectors.toMap(Organization::getFullPathId, Function.identity()));
		// 获取所有组织节点
		List<OrganizationRelation> treeNewList = baseClient.allTree();
		List<String> resultFullPathIdList = new ArrayList<>();

		//组织过滤
		List<String> organizationList = null;
		if (StringUtil.isEmpty(param.getFullPathId())) {
	        for (Organization organization : filterByOrgTypeCode) {
	            String targetParentFullPathId = organization.getFullPathId();
	            organizationList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
	            resultFullPathIdList.addAll(organizationList);
	        }
		} else {
			organizationList = OrgUtils.findParentStart(treeNewList,param.getFullPathId(),permissionNodeResultMap);
            resultFullPathIdList.addAll(organizationList);
		}
		param.setList(resultFullPathIdList);
		
		// 汇率
		LatestGidailyRate gidailyRate = new LatestGidailyRate();
		gidailyRate.setToCurrency(OcrCurrencyType.CNY.getValue());
		Map<String, List<LatestGidailyRate>> currencyResult = baseClient.getCurrency(gidailyRate);
		
		// 获取所有的品类
		PurchaseCategory purchaseCategory = new PurchaseCategory();
		purchaseCategory.setEnabled("Y");
		purchaseCategory.setPageSize(999999);
		purchaseCategory.setPageNum(1);
		PageInfo<PurchaseCategory> categoryList = baseClient.listPageByParmCategory(purchaseCategory);
		List<PurchaseCategory> levelList = this.getCategoryListByLevel(param.getLevel(), categoryList);
		Map<Long, String> structMap = this.getCategoryStructMap(categoryList);

		List<OrderWarehouseDTO> list = this.costReductionMapper.queryWarehouse(param);
		CrSet set = crSetMapper.selectOne((Wrapper<CrSet>) new QueryWrapper().eq("YEAR", param.getYear()));
		// 获取冻结物料及价格
		Map<Long, BigDecimal> materialMap = this.getMaterialMap(param);
		// 获取品类降本率
		Map<Long, BigDecimal> categoryMap = this.getCategoryMap(param);

		List<OrderWarehouseDTO> tempList = null;
		CostReductionDTO temp = null;
		List<CostReductionDTO> resultList = new ArrayList<CostReductionDTO>();
		
		
		for (PurchaseCategory category : levelList) {
			tempList = new ArrayList<OrderWarehouseDTO>();
			String structLevel = structMap.get(category.getCategoryId());
			if (null == structLevel || structLevel.isEmpty()) {
				continue;
			}
			for (OrderWarehouseDTO od : list) {
				String structData = structMap.get(od.getCategoryId());
				if (null == structData) {
					continue;
				}
				if (!structData.startsWith(structLevel)) {
					continue;
				}
				tempList.add(od);
			}
			if (tempList.size() == 0) {
				continue;
			}
			
			temp = this.getCrAmountNew(tempList, materialMap, currencyResult, categoryMap, set);
			if (null != temp && ( null == temp.getUpAmount() || temp.getUpAmount().doubleValue() == 0)) {
				continue;
			}
			if (null == temp) {
				temp = new CostReductionDTO();
			}
			temp.setCategoryName(category.getCategoryName());
			resultList.add(temp);
		}
		
		if (resultList.size() >0) {
			 //排序
 		   Collections.sort(resultList, Comparator.comparing(CostReductionDTO::getUpAmount).reversed());
 		   int no = 1;
 		   for (CostReductionDTO dto : resultList) {
 			   dto.setNo(no);
 			   no++;
 		   }
		}
		return this.getPage(resultList, pageSize, pageNum);
	}
	
	
	/**
	 * 总的明细表
	 */
	@Override
	public PageInfo<CostReductionDTO> queryCostReductionDetail(CostReductionParamDTO param) {
		// 设置时间
		this.setDateParam(param);

		Integer pageSize = param.getPageSize();
    	Integer pageNum = param.getPageNum();
    	param.setPageNum(null);
    	param.setPageSize(null);
		
		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		// 获取用户id对应的权限节点
		List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
		// 根据类型过滤得出父节点
		List<Organization> filterByOrgTypeCode = permissionNodeResult.stream()
				.filter(p -> param.getOrganizationTypeCode().equals(p.getOrganizationTypeCode()))
				.collect(Collectors.toList());
		// 组织权限Map
		Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream()
				.collect(Collectors.toMap(Organization::getFullPathId, Function.identity()));
		// 获取所有组织节点
		List<OrganizationRelation> treeNewList = baseClient.allTree();
		List<String> resultFullPathIdList = new ArrayList<>();

		//组织过滤
		List<String> organizationList = null;
		if (StringUtil.isEmpty(param.getFullPathId())) {
	        for (Organization organization : filterByOrgTypeCode) {
	            String targetParentFullPathId = organization.getFullPathId();
	            organizationList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
	            resultFullPathIdList.addAll(organizationList);
	        }
		} else {
			organizationList = OrgUtils.findParentStart(treeNewList,param.getFullPathId(),permissionNodeResultMap);
            resultFullPathIdList.addAll(organizationList);
		}
		param.setList(resultFullPathIdList);
		
		// 汇率
		LatestGidailyRate gidailyRate = new LatestGidailyRate();
		gidailyRate.setToCurrency(OcrCurrencyType.CNY.getValue());
		Map<String, List<LatestGidailyRate>> currencyResult = baseClient.getCurrency(gidailyRate);
		
		List<OrderWarehouseDTO> list = this.costReductionMapper.queryWarehouse(param);
		CrSet set = crSetMapper.selectOne((Wrapper<CrSet>) new QueryWrapper().eq("YEAR", param.getYear()));
		// 获取冻结物料及价格
		Map<Long, BigDecimal> materialMap = this.getMaterialMap(param);
		// 获取品类降本率
		Map<Long, BigDecimal> categoryMap = this.getCategoryMap(param);

		
		List<CostReductionDTO> resultList = new ArrayList<CostReductionDTO>();
		
		//根据供应商、组织、物料 分组
		Map<String,List<OrderWarehouseDTO>> materialListMap = new HashMap<String,List<OrderWarehouseDTO>>();
		Map<String,List<OrderWarehouseDTO>> categoryListMap = new HashMap<String,List<OrderWarehouseDTO>>();
		List<OrderWarehouseDTO> keyList = null;
		List<OrderWarehouseDTO> keyList1 = null;
		for (OrderWarehouseDTO dto : list) {
			//物料降本
			String key = dto.getVendorId()+"_"+dto.getFullPathId()+"_"+dto.getMaterialId();
			if (materialListMap.containsKey(key)) {
				keyList = materialListMap.get(key);
			} else {
				keyList = new ArrayList<OrderWarehouseDTO>();
			}
			keyList.add(dto);
			materialListMap.put(key, keyList);
			//品类降本
			String key1 = dto.getVendorId()+"_"+dto.getFullPathId()+"_"+dto.getCategoryId();
			if (categoryListMap.containsKey(key1)) {
				keyList1 = categoryListMap.get(key1);
			} else {
				keyList1 = new ArrayList<OrderWarehouseDTO>();
			}
			keyList1.add(dto);
			categoryListMap.put(key1, keyList1);
		}
		
		List<OrderWarehouseDTO> tempList = null;
		CostReductionDTO temp = null;
		Map<String,CostReductionDTO> materialRateMap = new HashMap<String,CostReductionDTO>();
		//物料降本
		Iterator it = materialListMap.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			keyList = materialListMap.get(key);
			temp = this.getCrAmount(keyList, materialMap, currencyResult, categoryMap, set);
			materialRateMap.put(key, temp);
		}
		
		Map<String,CostReductionDTO> categoryRateMap = new HashMap<String,CostReductionDTO>();
		//品类降本
		it = categoryListMap.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			keyList1 = categoryListMap.get(key);
			temp = this.getCrAmount(keyList1, materialMap, currencyResult, categoryMap, set);
			categoryRateMap.put(key, temp);
		}
		
		for (OrderWarehouseDTO dto :list) {
			temp = new CostReductionDTO();
			temp.setCompanyName(dto.getCompanyName());
			temp.setOrganizationName(dto.getOrganizationName());
			temp.setCategoryName(dto.getCategoryName());
			temp.setMaterialName(dto.getMaterialName());
			temp.setConfirmTime(dto.getConfirmTime());
			String key = dto.getVendorId()+"_"+dto.getFullPathId()+"_"+dto.getMaterialId();
			if (materialRateMap.containsKey(key)) {
				temp.setMaterialCrAmount(materialRateMap.get(key).getAmount());
				temp.setMaterialCrRate(materialRateMap.get(key).getRate());
				temp.setMaterialUpAmount(materialRateMap.get(key).getUpAmount());
			}
			String key1 = dto.getVendorId()+"_"+dto.getFullPathId()+"_"+dto.getCategoryId();
			if (categoryRateMap.containsKey(key1)) {
				temp.setCategoryCrAmount(categoryRateMap.get(key1).getAmount());
				temp.setCategoryReachRate(categoryRateMap.get(key1).getReachRate());
			}
			resultList.add(temp);
		}
		return this.getPage(resultList, pageSize, pageNum);
	}
	
	private PageInfo getPage(List list,Integer pageSize,Integer pageNum) {
	   	 PageInfo pageInfo = new PageInfo() ;
	        List newList = new ArrayList();
	        //定义分页
	        if (null != pageNum && null != pageSize) {
	        	pageInfo.setPageSize(pageSize);
	        	pageInfo.setPageNum(pageNum);
	        	int start = (pageNum - 1) * pageSize;
	        	int end = 0;
	        	if (list.size() > (pageNum * pageSize)) {
	        		end = pageNum * pageSize;
	        	} else {
	        		end = list.size();
	        	}
	        	for (int i = start ;i<end;i++) {
	        		newList.add(list.get(i));
	        	}
	        	pageInfo.setList(newList);
	        	
	        } else {
	        	pageInfo.setList(list);
	        }
	        pageInfo.setTotal(list.size());
	    	return pageInfo;
	   }
}
