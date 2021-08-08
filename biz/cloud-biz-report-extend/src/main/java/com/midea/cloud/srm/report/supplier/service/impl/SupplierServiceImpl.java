package com.midea.cloud.srm.report.supplier.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.OcrCurrencyType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.OrgUtils;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationRelation;
import com.midea.cloud.srm.model.base.purchase.entity.LatestGidailyRate;
import com.midea.cloud.srm.model.base.region.dto.AreaDTO;
import com.midea.cloud.srm.model.base.region.dto.AreaPramDTO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.report.chart.dto.ChartDTO;
import com.midea.cloud.srm.model.report.chart.dto.SeriesData;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierAnalysisDTO;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierAnalysisDetailDTO;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierCategoryDTO;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierMapDTO;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierMonthsDTO;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierParamDTO;
import com.midea.cloud.srm.model.report.supplier.dto.SupplierPerformanceDTO;
import com.midea.cloud.srm.model.report.supplier.entity.SupplierConfig;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.report.config.service.IConfigService;
import com.midea.cloud.srm.report.supplier.mapper.SupplierMapper;
import com.midea.cloud.srm.report.supplier.service.ISupplierService;

/**
 * 
 * 
 * <pre>
 * 供应商分析serviceImpl
 * </pre>
 * 
 * @author  kuangzm
 * @version 1.00.00
 * 
 *<pre>
 * 	修改记录
 * 	修改后版本:
 *	修改人： 
 *	修改日期:2020年11月24日 上午8:28:32
 *	修改内容:
 * </pre>
 */
@Service
public class SupplierServiceImpl  implements ISupplierService {

	@Resource
    private SupplierMapper supplierMapper;

    @Autowired
    private BaseClient baseClient;
    
    
    @Autowired
    private IConfigService iConfigService;
    
    
    
    /*
     * @Description:供应商分析
     * @param param
     * @return
     */
    @Override
    public SupplierAnalysisDTO getSupplierAnalysis(SupplierParamDTO param) {
    	SupplierAnalysisDTO result = new SupplierAnalysisDTO();
    	
    	//设置时间
    	this.setDateParam(param);
    	
    	LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
    	//获取用户id对应的权限节点
        List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
    	 //根据类型过滤得出父节点
        List<Organization> filterByOrgTypeCode = permissionNodeResult.stream().filter(p -> param.getOrganizationTypeCode().equals(p.getOrganizationTypeCode())).collect(Collectors.toList());
    	//组织权限Map
        Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream().collect(Collectors.toMap(Organization::getFullPathId,
                Function.identity()));
        //获取所有组织节点
        List<OrganizationRelation> treeNewList = baseClient.allTree();
        List<String> resultFullPathIdList = new ArrayList<>();
        List<String> tempList = null;
    	
        for (Organization organization : filterByOrgTypeCode) {
            String targetParentFullPathId = organization.getFullPathId();
            if (null != param.getFullPathId() && !param.getFullPathId().equals(targetParentFullPathId)) {
        		continue;
        	}
            tempList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
            resultFullPathIdList.addAll(tempList);
        }
        param.setList(resultFullPathIdList);
        
        
        //汇率
        LatestGidailyRate gidailyRate = new LatestGidailyRate();
        gidailyRate.setToCurrency(OcrCurrencyType.CNY.getValue());
        Map<String,List<LatestGidailyRate>> currencyResult = baseClient.getCurrency(gidailyRate);
        
        SupplierConfig config = iConfigService.querySupplierConfig();
        
    	//供应商数量
    	result.setSum(getSupplierCount(param));
    	//活跃金额供应商数
    	result.setActiveNum(getActiveCount(param,currencyResult,config));
    	//供应商新增数量
    	result.setAddNum(getAddCount(param));
    	//供应商退出数量
    	result.setOutNum(getOutCount(param));
    	//供应商合作年限
    	result.setCooperation(this.getCooperation(param));
    	//供应商地图分布
    	result.setChinaMap(getMap(param));
    	//供应商金额供方数量占比
    	result.setPurchase(getPurchaseCount(param, currencyResult,config));
    	//采购金额供方排名
    	result.setPurchaseRank(getPurchaseRank(param, currencyResult));
    	//供应商等级占比
    	result.setLevel(this.getPerformance(param));
    	//品类供货供方数占比
    	result.setCategory(getCategory(param,config));
    	
    	return result;
    }
    
    /*
     * @Description:供应商总数
     * @param param
     * @return
     */
    private BigDecimal getSupplierCount(SupplierParamDTO param) {
    	return supplierMapper.getSupplierCount(param);
    }
    
    /*
     * @Description:活跃供应商数
     * @param param
     * @return
     */
    private BigDecimal getActiveCount(SupplierParamDTO param,Map<String,List<LatestGidailyRate>> currencyResult,SupplierConfig config) {
    	//获取活跃金额判断值
    	List<OrderDetailDTO> list = supplierMapper.getWareHouse(param);
    	Map<Long, BigDecimal> vendorMap = new HashMap<Long,BigDecimal>();
    	if(null != list && list.size() > 0) {
    		BigDecimal sumAmount = null;
    		for (OrderDetailDTO od : list) {
    			if (vendorMap.containsKey(od.getVendorId())) {
    				sumAmount = vendorMap.get(od.getVendorId());
    			} else {
    				sumAmount = BigDecimal.ZERO;
    			}
				
    			if (null != od.getWarehouseReceiptQuantity()) {
    				if (OcrCurrencyType.CNY.getValue().equals(od.getCurrency())) {
    					if (null != od.getReturnQuantity() && null != od.getWarehouseReceiptQuantity()) {
    						sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity())
    								.multiply(od.getUnitPriceContainingTax()));
    					} else if (null != od.getWarehouseReceiptQuantity()) {
    						sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()));
    					}
    				} else {
    					BigDecimal rate = currencyResult.get(od.getCurrency()).get(0).getConversionRate();
    					if (null != od.getReturnQuantity() && null != od.getWarehouseReceiptQuantity()) {
    						sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity())
    								.multiply(od.getUnitPriceContainingTax()).multiply(rate));
    					} else if (null != od.getWarehouseReceiptQuantity()) {
    						sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()).multiply(rate));
    					}
    				}
    			}
				vendorMap.put(od.getVendorId(), sumAmount);
    		}
    	}
    	int i = 0;
    	Iterator it = vendorMap.keySet().iterator();
    	while(it.hasNext()) {
    		Long key = (Long) it.next();
    		BigDecimal temp = vendorMap.get(key);
    		if (null != temp && temp.doubleValue() >= config.getOrderAmount().multiply(new BigDecimal(10000)).doubleValue()) {
    			i++;
    		}
    	}
    	
    	return new BigDecimal(i);
    }
    
    
    /*
     * @Description:供应商新增数量
     * @param param
     * @return
     */
    private BigDecimal getAddCount(SupplierParamDTO param) {
    	return supplierMapper.getAddCount(param);
    }
    
    /*
     * @Description:供应商退出数量
     * @param param
     * @return
     */
    private BigDecimal getOutCount(SupplierParamDTO param) {
    	return supplierMapper.getOutCount(param);
    }
    
    private ChartDTO getCooperation(SupplierParamDTO param) {
    	ChartDTO chart = new ChartDTO();
    	List<SupplierMonthsDTO> list = this.supplierMapper.getCompanyMonths(param);
    	if(null != list && list.size() > 0) {
    		int oneYear = 0;
    		int threeYear = 0;
    		int fiveYear = 0;
    		int otherYear = 0;
    		for (SupplierMonthsDTO dto : list) {
    			if(dto.getMonths() <12) {
    				oneYear ++;
    			} else if (dto.getMonths()<36) {
    				threeYear ++;
    			} else if (dto.getMonths() < 60) {
    				fiveYear ++;
    			} else {
    				otherYear ++ ;
    			}
    		}
    		List<SeriesData> seriesData = new ArrayList<SeriesData>();
    		seriesData.add(new SeriesData("1年以内",new BigDecimal(oneYear)));
    		seriesData.add(new SeriesData("1-3年",new BigDecimal(threeYear)));
    		seriesData.add(new SeriesData("3-5年",new BigDecimal(fiveYear)));
    		seriesData.add(new SeriesData("5年以上",new BigDecimal(otherYear)));
    		chart.setSeriesData(seriesData);
    	}
    	return chart;
    }
    
    
    private ChartDTO getMap(SupplierParamDTO param) {
    	ChartDTO chart = new ChartDTO();
    	List<SupplierMapDTO> list = this.supplierMapper.getMap(param);
    	if(null != list && list.size() > 0) {
    		List<SeriesData> seriesData = new ArrayList<SeriesData>();
    		SeriesData series = null;
    		AreaPramDTO areaPramDTO = new AreaPramDTO();
    		areaPramDTO.setQueryType("province");
    		List<AreaDTO> areaList = baseClient.queryRegionById(areaPramDTO);
    		for (SupplierMapDTO dto : list) {
    			series = new SeriesData();
    			for (AreaDTO area:areaList) {
    				if (null != area && null != dto && area.getProvinceId().equals(dto.getCompanyProvince())) {
    					series.setName(area.getProvince().replace("省",""));
    					if ("新疆维吾尔自治区".equals(area.getProvince())) {
    						series.setName("新疆");
    					} else if ("西藏自治区".equals(area.getProvince())) {
    						series.setName("西藏");
    					} else if ("广西壮族自治区".equals(area.getProvince())) {
    						series.setName("广西");
    					} else if ("内蒙古自治区".equals(area.getProvince())) {
    						series.setName("内蒙古");
    					} else if ("宁夏回族自治区".equals(area.getProvince())) {
    						series.setName("宁夏");
    					}
    					break;
    				}
    			}
    			
    			series.setValue(dto.getNum());
    			seriesData.add(series);
    		}
    		chart.setSeriesData(seriesData);
    	}
    	return chart;
    }
    
    
    /*
     * @Description:采购金额供方数量占比
     * @param param
     * @return
     */
    private ChartDTO getPurchaseCount(SupplierParamDTO param,Map<String,List<LatestGidailyRate>> currencyResult,SupplierConfig config) {
    	ChartDTO chart = new ChartDTO();
    	List<OrderDetailDTO> list = supplierMapper.getWareHouse(param);
    	Map<Long, BigDecimal> vendorMap = new HashMap<Long,BigDecimal>();
    	if(null != list && list.size() > 0) {
    		BigDecimal sumAmount = null;
    		for (OrderDetailDTO od : list) {
    			if (vendorMap.containsKey(od.getVendorId())) {
    				sumAmount = vendorMap.get(od.getVendorId());
    			} else {
    				sumAmount = BigDecimal.ZERO;
    			}
    			
				if (null != od.getWarehouseReceiptQuantity()) {
				   if (OcrCurrencyType.CNY.getValue().equals(od.getCurrency())) {
					   if (null != od.getReturnQuantity() && null != od.getWarehouseReceiptQuantity()) {
						   sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()));
					   } else if (null != od.getWarehouseReceiptQuantity()) {
						   sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()));
					   }
				   } else {
					   BigDecimal rate = currencyResult.get(od.getCurrency()).get(0).getConversionRate();
					   if (null != od.getReturnQuantity() && null != od.getWarehouseReceiptQuantity()) {
						   sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()).multiply(rate));
					   } else if (null != od.getWarehouseReceiptQuantity()) {
						   sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()).multiply(rate));
					   }
					   
				   }
				}
				vendorMap.put(od.getVendorId(), sumAmount);
    		}
    	}
    	int one = 0;
    	int two = 0;
    	int third = 0;
    	int four = 0;
    	Iterator it = vendorMap.keySet().iterator();
    	while(it.hasNext()) {
    		Long key = (Long) it.next();
    		BigDecimal temp = vendorMap.get(key);
    		if (null != temp && temp.doubleValue() <= new BigDecimal(config.getPurchaseOne()).multiply(new BigDecimal(10000)).doubleValue()) {
    			one++;
    		} else if (null != temp && temp.doubleValue() <= new BigDecimal(config.getPurchaseTwoEnd()).multiply(new BigDecimal(10000)).doubleValue()) {
    			two++;
    		} else if (null != temp && temp.doubleValue() <= new BigDecimal(config.getPurchaseThreeEnd()).multiply(new BigDecimal(10000)).doubleValue()) {
    			third++;
    		} else {
    			four ++ ;
    		}
    	}
    	List<SeriesData> seriesData = new ArrayList<SeriesData>();
		seriesData.add(new SeriesData("金额（X<="+config.getPurchaseOne()+"万）",new BigDecimal(one)));
		seriesData.add(new SeriesData("金额（"+config.getPurchaseTwoStart()+"万<X<="+config.getPurchaseTwoEnd()+"万）",new BigDecimal(two)));
		seriesData.add(new SeriesData("金额（"+config.getPurchaseThreeStart()+"万<X<="+config.getPurchaseThreeEnd()+"万）",new BigDecimal(third)));
		seriesData.add(new SeriesData("金额（X>="+config.getPurchaseFour()+"万）",new BigDecimal(four)));
		chart.setSeriesData(seriesData);
		return chart;
    }
    
    /*
     * @Description:采购金额供方数量占比
     * @param param
     * @return
     */
    private ChartDTO getPurchaseRank(SupplierParamDTO param,Map<String,List<LatestGidailyRate>> currencyResult) {
    	ChartDTO chart = new ChartDTO();
    	List<OrderDetailDTO> list = supplierMapper.getWareHouse(param);
    	Map<Long, BigDecimal> vendorMap = new HashMap<Long,BigDecimal>();
    	Map<Long,String> nameMap = new HashMap<Long,String>();
    	if(null != list && list.size() > 0) {
    		BigDecimal sumAmount = null;
    		for (OrderDetailDTO od : list) {
    			if (vendorMap.containsKey(od.getVendorId())) {
    				sumAmount = vendorMap.get(od.getVendorId());
    			} else {
    				sumAmount = BigDecimal.ZERO;
    			}
    			
				if (null != od.getWarehouseReceiptQuantity()) {
				   if (OcrCurrencyType.CNY.getValue().equals(od.getCurrency())) {
					   if (null != od.getReturnQuantity() && null != od.getWarehouseReceiptQuantity()) {
						   sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()));
					   } else if (null != od.getWarehouseReceiptQuantity()) {
						   sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()));
					   }
				   } else {
					   BigDecimal rate = currencyResult.get(od.getCurrency()).get(0).getConversionRate();
					   if (null != od.getReturnQuantity() && null != od.getWarehouseReceiptQuantity()) {
						   sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()).multiply(rate));
					   } else if (null != od.getWarehouseReceiptQuantity()) {
						   sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()).multiply(rate));
					   }
				   }
				}
				vendorMap.put(od.getVendorId(), sumAmount);
				nameMap.put(od.getVendorId(), od.getVendorName());
    		}
    	}
    	
    	
    	List<SeriesData> seriesData = new ArrayList<SeriesData>();
    	SeriesData series = null;
    	Iterator it = vendorMap.keySet().iterator();
    	while(it.hasNext()) {
    		Long key = (Long) it.next();
    		BigDecimal temp = vendorMap.get(key);
    		series = new SeriesData();
    		series.setName(nameMap.get(key));
    		series.setValue(temp);
    		seriesData.add(series);
    	}
    	
    	//排序
    	if (seriesData.size() > 0) {
    		Collections.sort(seriesData, Comparator.comparing(SeriesData::getValue).reversed());
    	}
    	//取前10名
    	if(seriesData.size() > 10) {
    		List<SeriesData> newList = new ArrayList<SeriesData>();
    		for(int i=0;i<10;i++) {
    			newList.add(seriesData.get(i));
    		}
    		chart.setSeriesData(newList);
    	} else {
    		chart.setSeriesData(seriesData);
    	}    	
		return chart;
    }
    
    /*
     * @Description:供应商等级占比
     * @param param
     * @return
     */
    private ChartDTO getPerformance(SupplierParamDTO param) {
    	ChartDTO chart = new ChartDTO();
    	List<SupplierPerformanceDTO> list = getNewPerformance(supplierMapper.getPeformance(param));
    	if (null != list && list.size() > 0) {
    		List<SeriesData> seriesData = new ArrayList<SeriesData>();
        	int one = 0;
        	int two = 0;
        	int three = 0;
        	int four = 0;
        	for (SupplierPerformanceDTO dto : list) {
        		if (dto.getScore().doubleValue() >= 90) {
        			one++;
        		} else if (dto.getScore().doubleValue() >= 80) {
        			two++;
        		} else if (dto.getScore().doubleValue() >= 60) {
        			three++;
        		} else {
        			four ++;
        		}
        	}
        	seriesData.add(new SeriesData("优", new BigDecimal(one)));
        	seriesData.add(new SeriesData("良", new BigDecimal(two)));
        	seriesData.add(new SeriesData("合格", new BigDecimal(three)));
        	seriesData.add(new SeriesData("不合格", new BigDecimal(four)));
        	chart.setSeriesData(seriesData);
    	}
    	return chart;
    }
    
    private List<SupplierPerformanceDTO> getNewPerformance(List<SupplierPerformanceDTO> list) {
    	List<SupplierPerformanceDTO> newList = new ArrayList<SupplierPerformanceDTO>();
    	Map<String,Object> map = new HashMap<String,Object>();
    	if (null != list && list.size() >0 ) {
    		for (SupplierPerformanceDTO dto : list) {
    			String key = dto.getFullPathId()+"_"+dto.getCategoryId()+"_"+dto.getCompanyId();
    			if (map.containsKey(key)) {
    				continue;
    			}
    			newList.add(dto);
    		}
    	}
    	return newList;
    }
    
    /*
     * @Description:品类供货供方数占比
     * @param param
     * @return
     */
    private ChartDTO getCategory(SupplierParamDTO param,SupplierConfig config) {
    	ChartDTO chart = new ChartDTO();
    	List<SupplierCategoryDTO> list = supplierMapper.getCategory(param);
    	if (null != list && list.size() > 0) {
    		List<SeriesData> seriesData = new ArrayList<SeriesData>();
        	int one = 0;
        	int two = 0;
        	int three = 0;
        	int four = 0;
        	for (SupplierCategoryDTO dto : list) {
        		if (dto.getNum().doubleValue() <=config.getCategoryOne()) {
        			one++;
        		} else if (dto.getNum().doubleValue() <=config.getCategoryTwoEnd()) {
        			two++;
        		} else if (dto.getNum().doubleValue() <= config.getCategoryThreeEnd()) {
        			three++;
        		} else {
        			four ++;
        		}
        	}
        	seriesData.add(new SeriesData("数量(X<="+config.getCategoryOne()+")", new BigDecimal(one)));
        	seriesData.add(new SeriesData("数量("+config.getCategoryTwoStart()+"<X<="+config.getCategoryTwoEnd()+")", new BigDecimal(two)));
        	seriesData.add(new SeriesData("数量("+config.getCategoryThreeStart()+"<X<="+config.getCategoryThreeEnd()+")", new BigDecimal(three)));
        	seriesData.add(new SeriesData("数量(X>"+config.getCategoryFour()+")", new BigDecimal(four)));
        	chart.setSeriesData(seriesData);
    	}
    	return chart;
    }
    
    
    
    private void setDateParam(SupplierParamDTO dto) {
    	if(dto.getSeason().equals(0)) {
   		 //月度执行情况
           String dateModel1 = "$date-01-01 00:00:00";
           String dateModel2 = "$date-12-31 23:59:59";
           try {
	           	dto.setStartDate(DateUtil.parseDate(dateModel1.replace("$date",String.valueOf(dto.getYear()))));
	           	dto.setEndDate(DateUtil.parseDate(dateModel2.replace("$date",String.valueOf(dto.getYear()))));
	   		} catch (Exception e) {
	   			
	   		}
	   	} else {
	   		dto.setStartDate(DateUtil.getStartDateOfQuarter(dto.getYear(),dto.getSeason()));
	       	dto.setEndDate(DateUtil.getEndDateOfQuarter(dto.getYear(),dto.getSeason()));
	   	}
    }
    
    @Override
    public List<SupplierAnalysisDetailDTO> queryCooperationDetail(SupplierParamDTO param) {
    	//设置时间
    	this.setDateParam(param);
    	
    	LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
    	//获取用户id对应的权限节点
        List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
    	 //根据类型过滤得出父节点
        List<Organization> filterByOrgTypeCode = permissionNodeResult.stream().filter(p -> param.getOrganizationTypeCode().equals(p.getOrganizationTypeCode())).collect(Collectors.toList());
    	//组织权限Map
        Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream().collect(Collectors.toMap(Organization::getFullPathId,
                Function.identity()));
        //获取所有组织节点
        List<OrganizationRelation> treeNewList = baseClient.allTree();
        List<String> resultFullPathIdList = new ArrayList<>();
        List<String> tempList = null;
    	
        for (Organization organization : filterByOrgTypeCode) {
            String targetParentFullPathId = organization.getFullPathId();
            if (null != param.getFullPathId() && !param.getFullPathId().equals(targetParentFullPathId)) {
        		continue;
        	}
            tempList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
            resultFullPathIdList.addAll(tempList);
        }
        param.setList(resultFullPathIdList);
        
       return this.supplierMapper.queryCooperationDetail(param);
    }
    
    @Override
    public PageInfo<SupplierAnalysisDetailDTO> queryPurchaseAmount(SupplierParamDTO param) {
    	
    	Integer pageSize = param.getPageSize();
    	Integer pageNum = param.getPageNum();
    	param.setPageNum(null);
    	param.setPageSize(null);
    	
    	//设置时间
    	this.setDateParam(param);
    	
    	LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
    	//获取用户id对应的权限节点
        List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
    	 //根据类型过滤得出父节点
        List<Organization> filterByOrgTypeCode = permissionNodeResult.stream().filter(p -> param.getOrganizationTypeCode().equals(p.getOrganizationTypeCode())).collect(Collectors.toList());
    	//组织权限Map
        Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream().collect(Collectors.toMap(Organization::getFullPathId,
                Function.identity()));
        //获取所有组织节点
        List<OrganizationRelation> treeNewList = baseClient.allTree();
        List<String> resultFullPathIdList = new ArrayList<>();
        List<String> tempList = null;
    	
        for (Organization organization : filterByOrgTypeCode) {
            String targetParentFullPathId = organization.getFullPathId();
            if (null != param.getFullPathId() && !param.getFullPathId().equals(targetParentFullPathId)) {
        		continue;
        	}
            tempList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
            resultFullPathIdList.addAll(tempList);
        }
        param.setList(resultFullPathIdList);
        
        
        //汇率
        LatestGidailyRate gidailyRate = new LatestGidailyRate();
        gidailyRate.setToCurrency(OcrCurrencyType.CNY.getValue());
        Map<String,List<LatestGidailyRate>> currencyResult = baseClient.getCurrency(gidailyRate);
        
        
       List<OrderDetailDTO> list  = this.supplierMapper.getWareHouse(param);
       List<SupplierAnalysisDetailDTO> details = null;
       Map<String,SupplierAnalysisDetailDTO> map = new HashMap<String,SupplierAnalysisDetailDTO>();
       if (null != list && list.size() > 0) {
    	   SupplierAnalysisDetailDTO temp = null;
    	   for (OrderDetailDTO od : list) {
    		   BigDecimal sumAmount = BigDecimal.ZERO;
    		   if (null != od.getWarehouseReceiptQuantity()) {
				   if (OcrCurrencyType.CNY.getValue().equals(od.getCurrency())) {
					   if (null != od.getReturnQuantity() && null != od.getWarehouseReceiptQuantity()) {
						   sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()));
					   } else if (null != od.getWarehouseReceiptQuantity()) {
						   sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()));
					   }
				   } else {
					   BigDecimal rate = currencyResult.get(od.getCurrency()).get(0).getConversionRate();
					   if (null != od.getReturnQuantity() && null != od.getWarehouseReceiptQuantity()) {
						   sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()).multiply(rate));
					   } else if (null != od.getWarehouseReceiptQuantity()) {
						   sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()).multiply(rate));
					   }
				   }
				}
    		   
    		   String key = od.getOrganizationName() +"_"+od.getVendorId() + "_" + od.getCategoryId();
    		   if (map.containsKey(key)) {
    			   temp = map.get(key);
    			   temp.setOrderAmount(temp.getOrderAmount().add(sumAmount));
    		   } else {
    			   temp = new SupplierAnalysisDetailDTO();
    			   temp.setCategoryName(od.getCategoryName());
    			   temp.setOrganizationName(od.getOrganizationName());
    			   temp.setCompanyName(od.getVendorName());
    			   temp.setOrderAmount(sumAmount);
    		   }
    		   map.put(key, temp);
    	   }
    	   if (null != map && map.size() > 0) {
    		   Iterator it = map.keySet().iterator();
    		   details = new ArrayList<SupplierAnalysisDetailDTO>();
    		   while (it.hasNext()) {
    			   String key = (String) it.next();
    			   details.add(map.get(key));
    		   }
    	   }
    	  
    	   if (null != details && details.size() > 0) {
    		   //排序
    		   Collections.sort(details, Comparator.comparing(SupplierAnalysisDetailDTO::getOrderAmount).reversed());
    		   //设置排序
    		   int i=1;
    		   for (SupplierAnalysisDetailDTO dto : details) {
    			   dto.setNo(i);
    			   i++;
    		   }
    	   }
       }
    	//返回分页
    	return this.getPage(details, pageSize, pageNum);
    }
    
    @Override
    public PageInfo<SupplierAnalysisDetailDTO> queryPeformanceDetail(SupplierParamDTO param) {
    	Integer pageSize = param.getPageSize();
    	Integer pageNum = param.getPageNum();
    	param.setPageNum(null);
    	param.setPageSize(null);
    	//设置时间
    	this.setDateParam(param);
    	
    	LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
    	//获取用户id对应的权限节点
        List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
    	 //根据类型过滤得出父节点
        List<Organization> filterByOrgTypeCode = permissionNodeResult.stream().filter(p -> param.getOrganizationTypeCode().equals(p.getOrganizationTypeCode())).collect(Collectors.toList());
    	//组织权限Map
        Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream().collect(Collectors.toMap(Organization::getFullPathId,
                Function.identity()));
        //获取所有组织节点
        List<OrganizationRelation> treeNewList = baseClient.allTree();
        List<String> resultFullPathIdList = new ArrayList<>();
        List<String> tempList = null;
    	
        for (Organization organization : filterByOrgTypeCode) {
            String targetParentFullPathId = organization.getFullPathId();
            if (null != param.getFullPathId() && !param.getFullPathId().equals(targetParentFullPathId)) {
        		continue;
        	}
            tempList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
            resultFullPathIdList.addAll(tempList);
        }
        param.setList(resultFullPathIdList);
        
        List<SupplierAnalysisDetailDTO> list  = this.supplierMapper.queryPeformanceDetail(param);
        
        if (null != list && list.size() > 0) {
 		   //排序
 		   Collections.sort(list, Comparator.comparing(SupplierAnalysisDetailDTO::getScore).reversed());
 		   //设置排序
 		   int i=1;
 		   for (SupplierAnalysisDetailDTO dto : list) {
 		   		//供应商分析中，供应商等级占比，指标评分值需要根据分数换算成优、良、合格、不合格
			   //【优】>=90；
			   //【良】>=80,<90;
			   //【合格】>=60,<80;
			   //【不合格】<60.
			   if(dto.getScore().intValue()>=90){
				   dto.setIndicatorLineDes("优");
			   }else if(dto.getScore().intValue()>=80){
				   dto.setIndicatorLineDes("良");
			   }else if(dto.getScore().intValue()>=60){
				   dto.setIndicatorLineDes("合格");
			   }else{
				   dto.setIndicatorLineDes("不合格");
			   }
 			   dto.setNo(i);
 			   i++;
 		   }
 	   }
        
        return this.getPage(list, pageSize, pageNum);
    }
    
    
    @Override
    public PageInfo<SupplierAnalysisDetailDTO> querySupplierDetail(SupplierParamDTO param) {
    	
    	Integer pageSize = param.getPageSize();
    	Integer pageNum = param.getPageNum();
    	param.setPageNum(null);
    	param.setPageSize(null);
    	
    	//设置时间
    	this.setDateParam(param);
    	
    	LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
    	//获取用户id对应的权限节点
        List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
    	 //根据类型过滤得出父节点
        List<Organization> filterByOrgTypeCode = permissionNodeResult.stream().filter(p -> param.getOrganizationTypeCode().equals(p.getOrganizationTypeCode())).collect(Collectors.toList());
    	//组织权限Map
        Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream().collect(Collectors.toMap(Organization::getFullPathId,
                Function.identity()));
        //获取所有组织节点
        List<OrganizationRelation> treeNewList = baseClient.allTree();
        List<String> resultFullPathIdList = new ArrayList<>();
        List<String> tempList = null;
    	
        for (Organization organization : filterByOrgTypeCode) {
            String targetParentFullPathId = organization.getFullPathId();
            if (null != param.getFullPathId() && !param.getFullPathId().equals(targetParentFullPathId)) {
        		continue;
        	}
            tempList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
            resultFullPathIdList.addAll(tempList);
        }
        param.setList(resultFullPathIdList);
        
        
        //汇率
        LatestGidailyRate gidailyRate = new LatestGidailyRate();
        gidailyRate.setToCurrency(OcrCurrencyType.CNY.getValue());
        Map<String,List<LatestGidailyRate>> currencyResult = baseClient.getCurrency(gidailyRate);
        
        
       List<SupplierAnalysisDetailDTO> list  = this.supplierMapper.querySupplierDetail(param);
       List<SupplierAnalysisDetailDTO> details = new ArrayList<SupplierAnalysisDetailDTO>();
       Map<String,SupplierAnalysisDetailDTO> map = new HashMap<String,SupplierAnalysisDetailDTO>();
       if (null != list && list.size() > 0) {
    	   SupplierAnalysisDetailDTO temp = null;
    	   for (SupplierAnalysisDetailDTO od : list) {
    		   BigDecimal sumAmount = BigDecimal.ZERO;
    		   if (null != od.getWarehouseReceiptQuantity()) {
				   if (OcrCurrencyType.CNY.getValue().equals(od.getCurrency())) {
					   if (null != od.getReturnQuantity() && null != od.getWarehouseReceiptQuantity()) {
						   sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()));
					   } else if (null != od.getWarehouseReceiptQuantity()) {
						   sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()));
					   }
				   } else {
					   BigDecimal rate = currencyResult.get(od.getCurrency()).get(0).getConversionRate();
					   if (null != od.getReturnQuantity() && null != od.getWarehouseReceiptQuantity()) {
						   sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()).multiply(rate));
					   } else if (null != od.getWarehouseReceiptQuantity()) {
						   sumAmount = sumAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()).multiply(rate));
					   }
				   }
				}
    		   
    		   String key = od.getFullPathId() +"_"+od.getVendorId() + "_" + od.getCategoryId()+"_"+od.getMaterialCode();
    		   if (map.containsKey(key)) {
    			   temp = map.get(key);
    			   temp.setOrderAmount(temp.getOrderAmount().add(sumAmount));
    		   } else {
    			   temp = od;
    			   temp.setOrderAmount(sumAmount);
    		   }
    		   map.put(key, temp);
    	   }
    	   if (null != map && map.size() > 0) {
    		   Iterator it = map.keySet().iterator();
    		   
    		   while (it.hasNext()) {
    			   String key = (String) it.next();
    			   details.add(map.get(key));
    		   }
    	   }
    	  
    	   if (null != details && details.size() > 0) {
    		   //排序
    		   Collections.sort(details, Comparator.comparing(SupplierAnalysisDetailDTO::getOrderAmount).reversed());
    		   //设置排序
    		   int i=1;
    		   for (SupplierAnalysisDetailDTO dto : details) {
    			   dto.setNo(i);
    			   i++;
    		   }
    	   }
       }
    	//返回分页
    	return this.getPage(details, pageSize, pageNum);
    }


	@Override
	public PageInfo<SupplierAnalysisDetailDTO> queryCategoryDetail(SupplierParamDTO param) {

		Integer pageSize = param.getPageSize();
		Integer pageNum = param.getPageNum();
		param.setPageNum(null);
		param.setPageSize(null);

		//配置
		SupplierConfig config = iConfigService.querySupplierConfig();

		//设置时间
		this.setDateParam(param);

		LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
		//获取用户id对应的权限节点
		List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
		//根据类型过滤得出父节点
		List<Organization> filterByOrgTypeCode = permissionNodeResult.stream().filter(p -> param.getOrganizationTypeCode().equals(p.getOrganizationTypeCode())).collect(Collectors.toList());
		//组织权限Map
		Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream().collect(Collectors.toMap(Organization::getFullPathId,
				Function.identity()));
		//获取所有组织节点
		List<OrganizationRelation> treeNewList = baseClient.allTree();
		List<String> resultFullPathIdList = new ArrayList<>();
		List<String> tempList = null;

		for (Organization organization : filterByOrgTypeCode) {
			String targetParentFullPathId = organization.getFullPathId();
			if (null != param.getFullPathId() && !param.getFullPathId().equals(targetParentFullPathId)) {
				continue;
			}
			tempList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
			resultFullPathIdList.addAll(tempList);
		}
		param.setList(resultFullPathIdList);

		List<SupplierAnalysisDetailDTO> list  = this.supplierMapper.queryCategoryDetail(param);
		Map<Long,SupplierAnalysisDetailDTO> categoryMap = new HashMap<Long,SupplierAnalysisDetailDTO>();
		if (null != list && list.size() > 0) {
			for (SupplierAnalysisDetailDTO dto : list) {
				categoryMap.put(dto.getCategoryId(), dto);
			}
		}
		Map<String,SupplierAnalysisDetailDTO> map = new HashMap<String,SupplierAnalysisDetailDTO>();

		List<SupplierAnalysisDetailDTO> details = null;
		list  = this.supplierMapper.querySupplierDetail(param);
		List<SupplierAnalysisDetailDTO> listExclude = this.supplierMapper.querySupplierDetailExclude(param);
		Map<Long,Object> existMap = new HashMap<Long,Object>();
		Double one=0d, two=0d, three=0d, four=0d;
		for (SupplierAnalysisDetailDTO od : list) {

			if (categoryMap.containsKey(od.getCategoryId())) {
				SupplierAnalysisDetailDTO c = categoryMap.get(od.getCategoryId());
				od.setCooperationVendorNum(c.getCooperationVendorNum());
				od.setOutVendorNum(c.getOutVendorNum());
			}
			if(od.getCooperationVendorNum()!=null) {
				if (od.getCooperationVendorNum().doubleValue() <= new BigDecimal(config.getCategoryOne()).doubleValue()) {
					one++;
				} else if (od.getCooperationVendorNum().doubleValue() <= new BigDecimal(config.getCategoryTwoEnd()).doubleValue()) {
					two++;
				} else if (od.getCooperationVendorNum().doubleValue() <= new BigDecimal(config.getCategoryThreeEnd()).doubleValue()) {
					three++;
				} else {
					four++;
				}
			}
		}

		if (null != list && list.size() > 0) {
			SupplierAnalysisDetailDTO temp = null;
			for (SupplierAnalysisDetailDTO od : list) {
				String key = od.getMaterialCode();
				if (categoryMap.containsKey(od.getCategoryId())) {
					SupplierAnalysisDetailDTO c = categoryMap.get(od.getCategoryId());
					od.setCooperationVendorNum(c.getCooperationVendorNum());
					od.setOutVendorNum(c.getOutVendorNum());
				}

				if(od.getCooperationVendorNum()!=null) {
					if (od.getCooperationVendorNum().doubleValue() <= new BigDecimal(config.getCategoryOne()).doubleValue()) {
						od.setBelongRange("品类供方数<" + config.getCategoryOne() + "家");
						od.setBelongRangePercent(BigDecimal.valueOf(one / (one + two + three + four)));
					} else if (od.getCooperationVendorNum().doubleValue() <= new BigDecimal(config.getCategoryTwoEnd()).doubleValue()) {
						od.setBelongRange(config.getCategoryTwoStart() + "家" + "<=" + "品类供方数<" + config.getCategoryTwoEnd() + "家");
						od.setBelongRangePercent(BigDecimal.valueOf(two / (one + two + three + four)));
					} else if (od.getCooperationVendorNum().doubleValue() <= new BigDecimal(config.getCategoryThreeEnd()).doubleValue()) {
						od.setBelongRange(config.getCategoryThreeStart() + "家" + "<=" + "品类供方数<" + config.getCategoryThreeEnd() + "家");
						od.setBelongRangePercent(BigDecimal.valueOf(three / (one + two + three + four)));
					} else {
						od.setBelongRange("品类供方数>=" + config.getCategoryFour() + "家");
						od.setBelongRangePercent(BigDecimal.valueOf(four / (one + two + three + four)));
					}
				}

				existMap.put(od.getCategoryId(),null);

				if (map.containsKey(key)) {
					temp = map.get(key);
					temp.setCompanyName(temp.getCompanyName()+","+od.getCompanyName());
				} else {
					temp = od;
				}
				map.put(key, temp);
			}

			if (null != map && map.size() > 0) {
				Iterator it = map.keySet().iterator();
				details = new ArrayList<SupplierAnalysisDetailDTO>();
				while (it.hasNext()) {
					String key = (String) it.next();
					details.add(map.get(key));
				}
			}

			Iterator itTemp =  categoryMap.keySet().iterator();
			while(itTemp.hasNext()) {
				Long key = (Long) itTemp.next();
				if (existMap.containsKey(key)) {
					continue;
				}
				temp = categoryMap.get(key);
				details.add(temp);
			}

			if (null != details && details.size() > 0) {
				//排序
				Collections.sort(details, Comparator.comparing(SupplierAnalysisDetailDTO::getCooperationVendorNum).reversed());
				//设置排序
				int i=1;
				for (SupplierAnalysisDetailDTO dto : details) {
					dto.setNo(i);
					i++;
				}
				//把没有数量的供应商，也补充进来
				details.addAll(listExclude);
			}
		}


		//返回分页
		return this.getPage(details, pageSize, pageNum);
	}

    
    @Override
    public PageInfo<SupplierAnalysisDetailDTO> queryCategoryDetailNew(SupplierParamDTO param) {
    	
    	Integer pageSize = param.getPageSize();
    	Integer pageNum = param.getPageNum();
    	param.setPageNum(null);
    	param.setPageSize(null);

		//配置
		SupplierConfig config = iConfigService.querySupplierConfig();

    	//设置时间
    	this.setDateParam(param);
    	
    	LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
    	//获取用户id对应的权限节点
        List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
    	 //根据类型过滤得出父节点
        List<Organization> filterByOrgTypeCode = permissionNodeResult.stream().filter(p -> param.getOrganizationTypeCode().equals(p.getOrganizationTypeCode())).collect(Collectors.toList());
    	//组织权限Map
        Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream().collect(Collectors.toMap(Organization::getFullPathId,
                Function.identity()));
        //获取所有组织节点
        List<OrganizationRelation> treeNewList = baseClient.allTree();
        List<String> resultFullPathIdList = new ArrayList<>();
        List<String> tempList = null;
    	
        for (Organization organization : filterByOrgTypeCode) {
            String targetParentFullPathId = organization.getFullPathId();
            if (null != param.getFullPathId() && !param.getFullPathId().equals(targetParentFullPathId)) {
        		continue;
        	}
            tempList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
            resultFullPathIdList.addAll(tempList);
        }
        param.setList(resultFullPathIdList);
        
       List<SupplierAnalysisDetailDTO> list  = this.supplierMapper.queryCategoryDetail(param);
       Map<Long,SupplierAnalysisDetailDTO> categoryMap = new HashMap<Long,SupplierAnalysisDetailDTO>();
       if (null != list && list.size() > 0) {
    	   for (SupplierAnalysisDetailDTO dto : list) {
    		   categoryMap.put(dto.getCategoryId(), dto);
    	   }
       }
       Map<Long,SupplierAnalysisDetailDTO> map = new HashMap<Long,SupplierAnalysisDetailDTO>();
       
       List<SupplierAnalysisDetailDTO> details = null;
       list  = this.supplierMapper.querySupplierDetail(param);
		List<SupplierAnalysisDetailDTO> listExclude = this.supplierMapper.querySupplierDetailExclude(param);
       Map<Long,Object> existMap = new HashMap<Long,Object>();
		Double one=0d, two=0d, three=0d, four=0d;
		for (SupplierAnalysisDetailDTO od : list) {

			if (categoryMap.containsKey(od.getCategoryId())) {
				SupplierAnalysisDetailDTO c = categoryMap.get(od.getCategoryId());
				od.setCooperationVendorNum(c.getCooperationVendorNum());
				od.setOutVendorNum(c.getOutVendorNum());
			}
			if(od.getCooperationVendorNum()!=null) {
				if (od.getCooperationVendorNum().doubleValue() <= new BigDecimal(config.getCategoryOne()).doubleValue()) {
					one++;
				} else if (od.getCooperationVendorNum().doubleValue() <= new BigDecimal(config.getCategoryTwoEnd()).doubleValue()) {
					two++;
				} else if (od.getCooperationVendorNum().doubleValue() <= new BigDecimal(config.getCategoryThreeEnd()).doubleValue()) {
					three++;
				} else {
					four++;
				}
			}
		}

       if (null != list && list.size() > 0) {
    	   SupplierAnalysisDetailDTO temp = null;
    	   for (SupplierAnalysisDetailDTO od : list) {
			   Long key = od.getCategoryId();
    		   if (categoryMap.containsKey(od.getCategoryId())) {
    			   SupplierAnalysisDetailDTO c = categoryMap.get(od.getCategoryId());
    			   od.setCooperationVendorNum(c.getCooperationVendorNum());
    			   od.setOutVendorNum(c.getOutVendorNum());
    		   }

			   if(od.getCooperationVendorNum()!=null) {
				   if (od.getCooperationVendorNum().doubleValue() <= new BigDecimal(config.getCategoryOne()).doubleValue()) {
					   od.setBelongRange("品类供方数<" + config.getCategoryOne() + "家");
					   od.setBelongRangePercent(BigDecimal.valueOf(one / (one + two + three + four)));
				   } else if (od.getCooperationVendorNum().doubleValue() <= new BigDecimal(config.getCategoryTwoEnd()).doubleValue()) {
					   od.setBelongRange(config.getCategoryTwoStart() + "家" + "<=" + "品类供方数<" + config.getCategoryTwoEnd() + "家");
					   od.setBelongRangePercent(BigDecimal.valueOf(two / (one + two + three + four)));
				   } else if (od.getCooperationVendorNum().doubleValue() <= new BigDecimal(config.getCategoryThreeEnd()).doubleValue()) {
					   od.setBelongRange(config.getCategoryThreeStart() + "家" + "<=" + "品类供方数<" + config.getCategoryThreeEnd() + "家");
					   od.setBelongRangePercent(BigDecimal.valueOf(three / (one + two + three + four)));
				   } else {
					   od.setBelongRange("品类供方数>=" + config.getCategoryFour() + "家");
					   od.setBelongRangePercent(BigDecimal.valueOf(four / (one + two + three + four)));
				   }
			   }

    		   existMap.put(od.getCategoryId(),null);

    		   if (map.containsKey(key)) {
    			   temp = map.get(key);
    			   temp.setCompanyName(temp.getCompanyName()+","+od.getCompanyName());
    		   } else {
    			   temp = od;
    		   }
    		   map.put(key, temp);
    	   }

    	   if (null != map && map.size() > 0) {
    		   Iterator it = map.keySet().iterator();
    		   details = new ArrayList<SupplierAnalysisDetailDTO>();
    		   while (it.hasNext()) {
    			   Long key = (Long) it.next();
    			   temp = categoryMap.get(key);
    			   if (null != temp ) {
    				   if (null == temp.getCooperationVendorNum()) {
        				   temp.setCooperationVendorNum(0);
        			   }
        			   details.add(map.get(key));
    			   }
    		   }
    	   }

    	   Iterator itTemp =  categoryMap.keySet().iterator();
    	   while(itTemp.hasNext()) {
    		   Long key = (Long) itTemp.next();
    		   if (existMap.containsKey(key)) {
    			   continue;
    		   }
    		   temp = categoryMap.get(key);
    		   if (null == temp.getCooperationVendorNum()) {
    			   temp.setCooperationVendorNum(0);
    		   }
    		   details.add(temp);
    	   }

    	   if (null != details && details.size() > 0) {
    		   //排序
    		   Collections.sort(details, Comparator.comparing(SupplierAnalysisDetailDTO::getCooperationVendorNum).reversed());
    		   //设置排序
    		   int i=1;
    		   for (SupplierAnalysisDetailDTO dto : details) {
    			   dto.setNo(i);
    			   i++;
    		   }
    		 //把没有数量的供应商，也补充进来
			   details.addAll(listExclude);
    	   }
       }


    	//返回分页
    	return this.getPage(details, pageSize, pageNum);
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
