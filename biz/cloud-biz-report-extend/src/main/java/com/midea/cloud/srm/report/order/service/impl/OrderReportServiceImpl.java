package com.midea.cloud.srm.report.order.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.OcrCurrencyType;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.OrgUtils;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationRelation;
import com.midea.cloud.srm.model.base.purchase.entity.LatestGidailyRate;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.report.purchase.dto.OrganizationRatio;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseCategoryDTO;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseDetailDTO;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseDetailInfoDTO;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseMonthRatio;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseParamDTO;
import com.midea.cloud.srm.model.report.purchase.dto.PurchaseResultDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ReturnDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.report.order.mapper.OrderReportMapper;
import com.midea.cloud.srm.report.order.service.IOrderReportService;

/**
 * <pre>
 *  采购订单明细表 服务实现类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:13
 *  修改内容:
 * </pre>
 */
@Service
public class OrderReportServiceImpl  implements IOrderReportService {

    @Resource
    private OrderReportMapper orderMapper;

    @Autowired
    private BaseClient baseClient;

    @Override
    public PurchaseResultDTO getPurchaseAnalysis(PurchaseParamDTO dto) {
    	PurchaseResultDTO result = new PurchaseResultDTO();
    	//设置时间
    	this.setDateParam(dto);
    	
    	LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
    	//获取用户id对应的权限节点
        List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
    	 //根据类型过滤得出父节点
        List<Organization> filterByOrgTypeCode = permissionNodeResult.stream().filter(p -> dto.getOrganizationTypeCode().equals(p.getOrganizationTypeCode())).collect(Collectors.toList());
    	//组织权限Map
        Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream().collect(Collectors.toMap(Organization::getFullPathId,
                Function.identity()));
        //获取所有组织节点
        List<OrganizationRelation> treeNewList = baseClient.allTree();
        List<String> resultFullPathIdList = new ArrayList<>();
        
        //汇率
        LatestGidailyRate gidailyRate = new LatestGidailyRate();
        gidailyRate.setToCurrency(OcrCurrencyType.CNY.getValue());
        Map<String,List<LatestGidailyRate>> currencyResult = baseClient.getCurrency(gidailyRate);
        
        List<OrganizationRatio> executionList = new ArrayList<OrganizationRatio>();
		List<OrganizationRatio> receiveList = new ArrayList<OrganizationRatio>();
		List<OrganizationRatio> punctualityList = new ArrayList<OrganizationRatio>();
		List<String> tempList = null;
		OrganizationRatio ratio = null;
		BigDecimal rate = null;
		
		PurchaseDetailDTO detail = null;
		
		Map<String,PurchaseDetailDTO> orderRateMap = this.queryOrderRateMap(dto);
		
		List<OrderDetailDTO> orderList = this.orderMapper.getExecutionList(dto);
		
		List<OrderDetailDTO> punctualitys = this.orderMapper.punctualityYearList(dto);
		
        for (Organization organization : filterByOrgTypeCode) {
            String targetParentFullPathId = organization.getFullPathId();
           
            tempList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
            
            //采购执行情况
            ratio = new OrganizationRatio();
            ratio.setFullPathId(targetParentFullPathId);
            ratio.setOrganizationName(organization.getOrganizationName());
            detail = this.getExecution(orderList, tempList, currencyResult);
            if (null != detail) {
            	rate = detail.getRate();
                ratio.setRate(rate == null?BigDecimal.ZERO : rate);
                executionList.add(ratio);
            }
            
            
            //采购接收情况
            ratio = new OrganizationRatio();
            ratio.setFullPathId(targetParentFullPathId);
            ratio.setOrganizationName(organization.getOrganizationName());
            detail = this.getOrderRate(orderRateMap, tempList);
            if (null != detail && null != detail.getRate()) {
            	 rate = detail.getRate();
                 ratio.setRate(rate == null?BigDecimal.ZERO : rate);
                 receiveList.add(ratio);
            }
            
            //采购准时率
            ratio = new OrganizationRatio();
            ratio.setFullPathId(targetParentFullPathId);
            ratio.setOrganizationName(organization.getOrganizationName());
            detail = this.punctuality(punctualitys, tempList, currencyResult);
            if (null != detail) {
            	 rate = detail.getRate();
                 ratio.setRate(rate == null?BigDecimal.ZERO : rate);
                 punctualityList.add(ratio);
            }
           
            
            resultFullPathIdList.addAll(tempList);
        }
        
        result.setReceiveList(this.getBidFive(receiveList));
        
        result.setExecutionList(this.getBidFive(executionList));
        
        result.setPunctualityList(this.getBidFive(punctualityList));
        
        
        dto.setList(resultFullPathIdList);
        
    	//申请量
        result.setOrderDetailLineNum(this.getPurApplyCount(dto));
        //采购金额
        result.setPurchaseAmount(this.getPurchaseTotalPrice(dto,currencyResult));
        //交货金额
        result.setWarehousingAmount(this.getDeliveryTotalPrice(dto, currencyResult));
        
        //月度执行情况
        String dateModel1 = "$date-01-01 00:00:00";
        String dateModel2 = "$date-12-31 23:59:59";
        try {
        	dto.setComfirmTimeBegin(DateUtil.parseDate(dateModel1.replace("$date",String.valueOf(dto.getYear()))));
        	dto.setComfirmTimeEnd(DateUtil.parseDate(dateModel2.replace("$date",String.valueOf(dto.getYear()))));
		} catch (Exception e) {
			
		}
        
        //月度
        result.setMonths(this.getExecutionYear(dto, currencyResult));
       
        return result;
    }
    
    private PurchaseDetailDTO getOrderRate(Map<String,PurchaseDetailDTO> orderRateMap,List<String> list) {
    	PurchaseDetailDTO dto = new PurchaseDetailDTO();
    	BigDecimal confirmNumber = BigDecimal.ZERO;
    	BigDecimal orderNumber = BigDecimal.ZERO;
    	for (String path : list) {
    		if (orderRateMap.containsKey(path)) {
    			confirmNumber = confirmNumber.add(orderRateMap.get(path).getConfirmNumber());
    			orderNumber = orderNumber.add(orderRateMap.get(path).getOrderNumber());
    		}
    	}
    	
    	if (orderNumber.doubleValue() > 0 ) {
    		dto.setRate(confirmNumber.divide(orderNumber, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
    	} else {
    		dto.setRate(BigDecimal.ZERO);
    	}
    	return dto;
    }
    
    private PurchaseDetailDTO getExecution(List<OrderDetailDTO> orderList,List<String> paths,Map<String,List<LatestGidailyRate>> currencyResult) {
    	List<OrderDetailDTO> list = new ArrayList<OrderDetailDTO>();
    	for (OrderDetailDTO order : orderList) {
    		if (paths.contains(order.getFullPathId())) {
    			list.add(order);
    		}
    	}
    	return this.getExecution(currencyResult, list);
    }
    
    private PurchaseDetailDTO punctuality(List<OrderDetailDTO> punctualitys,List<String> paths,Map<String,List<LatestGidailyRate>> currencyResult) {
    	List<OrderDetailDTO> list = new ArrayList<OrderDetailDTO>();
    	for (OrderDetailDTO order : punctualitys) {
    		if (paths.contains(order.getFullPathId())) {
    			list.add(order);
    		}
    	}
    	return this.punctuality(currencyResult, list);
    }
    
    private Map<String,PurchaseDetailDTO> queryOrderRateMap(PurchaseParamDTO dto) {
    	List<PurchaseDetailDTO> list = orderMapper.getOrderRateMap(dto);
    	Map<String,PurchaseDetailDTO> map = new HashMap<String,PurchaseDetailDTO>();
    	if (null != list && list.size() > 0) {
    		for (PurchaseDetailDTO d : list) {
    			map.put(d.getFullPathId(), d);
    		}
    	}
    	return map;
    }
    
   
    
    
    @Override
    public List<PurchaseCategoryDTO> getPurchaseCategory(PurchaseParamDTO dto) {
    	List<PurchaseCategoryDTO> list = this.getPurchaseCategoryList(dto);
        return getCategorySix(list);
    }
    
    /**
     * 品类采购金额占比排名
     * @param dto
     * @return
     */
    @Override
    public PageInfo<PurchaseCategoryDTO> getPurchaseCategoryDetail(PurchaseParamDTO dto) {
    	Integer pageSize = dto.getPageSize();
    	Integer pageNum = dto.getPageNum();
    	dto.setPageNum(null);
    	dto.setPageSize(null);
    	List<PurchaseCategoryDTO> list = this.getPurchaseCategoryList(dto);
    	 //排序
        if (list.size() > 0) {
    		Collections.sort(list, Comparator.comparing(PurchaseCategoryDTO::getRate).reversed());
    		int i=1;
    		for (PurchaseCategoryDTO d : list) {
    			d.setNum(i);
    			i++;
    		}
    	}
        return getPage(list,pageSize,pageNum);
    }
    
    
    private List<PurchaseCategoryDTO> getPurchaseCategoryList(PurchaseParamDTO dto) {
    	//设置时间
    	this.setDateParam(dto);
    	LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
    	//获取用户id对应的权限节点
        List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
    	 //根据类型过滤得出父节点
        List<Organization> filterByOrgTypeCode = permissionNodeResult.stream().filter(p -> dto.getOrganizationTypeCode().equals(p.getOrganizationTypeCode())).collect(Collectors.toList());
    	//组织权限Map
        Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream().collect(Collectors.toMap(Organization::getFullPathId,
                Function.identity()));
        //获取所有组织节点
        List<OrganizationRelation> treeNewList = baseClient.allTree();
        List<String> resultFullPathIdList = new ArrayList<>();
        //汇率
        LatestGidailyRate gidailyRate = new LatestGidailyRate();
        gidailyRate.setToCurrency(OcrCurrencyType.CNY.getValue());
        Map<String,List<LatestGidailyRate>> currencyResult = baseClient.getCurrency(gidailyRate);
		List<String> tempList = null;
		if (StringUtil.isEmpty(dto.getFullPathId())) {
	        for (Organization organization : filterByOrgTypeCode) {
	            String targetParentFullPathId = organization.getFullPathId();
	            tempList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
	            resultFullPathIdList.addAll(tempList);
	        }
		} else {
			tempList = OrgUtils.findParentStart(treeNewList,dto.getFullPathId(),permissionNodeResultMap);
            resultFullPathIdList.addAll(tempList);
		}
		dto.setList(resultFullPathIdList);
		
       List<OrderDetailDTO> list = this.orderMapper.queryPurchaseCategory(dto);
       List<PurchaseCategoryDTO> categorys = new ArrayList<PurchaseCategoryDTO>();
       
       //获取所有的品类
       PurchaseCategory purchaseCategory = new PurchaseCategory();
       purchaseCategory.setEnabled("Y");
       purchaseCategory.setPageSize(999999);
       purchaseCategory.setPageNum(1);
       PageInfo<PurchaseCategory> categoryList = baseClient.listPageByParmCategory(purchaseCategory);
       
       List<PurchaseCategory> levelList = this.getCategoryListByLevel(dto.getLevel(), categoryList);
       
       Map<Long,String> structMap = this.getCategoryStructMap(categoryList);
       
       
       //计算总入库金额
       BigDecimal sumAmount = BigDecimal.ZERO;
       for (OrderDetailDTO od : list) {
    	   if(!structMap.containsKey(od.getCategoryId())) { //排除品类错误的数据
    		   continue;
    	   }
			if (null != od.getWarehouseReceiptQuantity()) {
                BigDecimal returnNum =BigDecimal.ZERO;
                // 如果有退货单的，则进行扣减
                ReturnDetailDTO returnDetailDTO = new ReturnDetailDTO();
                returnDetailDTO.setOrderDetailId(od.getOrderDetailId());
                List<ReturnDetailDTO> returnDetailList = this.orderMapper.queryReturnDetailByOrderDetailId(returnDetailDTO);
                if(returnDetailList!=null && returnDetailList.size()>0){
                    returnNum = returnDetailList.get(0).getReturnNum();
                }
			   if (OcrCurrencyType.CNY.getValue().equals(od.getCurrency())) {
				   sumAmount = sumAmount.add((od.getWarehouseReceiptQuantity().subtract(returnNum)).multiply(od.getUnitPriceContainingTax()));
			   } else {
				   if (currencyResult.containsKey(od.getCurrency())) {
					   BigDecimal rate = currencyResult.get(od.getCurrency()).get(0).getConversionRate();
					   sumAmount = sumAmount.add((od.getWarehouseReceiptQuantity().subtract(returnNum)).multiply(od.getUnitPriceContainingTax()).multiply(rate));
				   }
			   }
			}
       }
       
       
       //计算 每个品类金额
       for (PurchaseCategory category : levelList) {
    	   boolean flag = false;
    	   if (null != list && list.size() > 0) {
        	   BigDecimal categoryAmount = BigDecimal.ZERO;
        	   String structLevel = structMap.get(category.getCategoryId());
        	   if (null == structLevel || structLevel.isEmpty()) {
        		   continue;
        	   }
        	   for (OrderDetailDTO od : list) {
        		   String structData = structMap.get(od.getCategoryId());
        		   if (null == structData)  {
        			   continue;
        		   }
        		   if (!structData.startsWith(structLevel)) {
        			   continue;
        		   }
        		   flag = true;

                   BigDecimal returnNum =BigDecimal.ZERO;
                   // 如果有退货单的，则进行扣减
                   ReturnDetailDTO returnDetailDTO = new ReturnDetailDTO();
                   returnDetailDTO.setOrderDetailId(od.getOrderDetailId());
                   List<ReturnDetailDTO> returnDetailList = this.orderMapper.queryReturnDetailByOrderDetailId(returnDetailDTO);
                   if(returnDetailList!=null && returnDetailList.size()>0){
                       returnNum = returnDetailList.get(0).getReturnNum();
                   }

        		   if (OcrCurrencyType.CNY.getValue().equals(od.getCurrency())) {
                   	if (null != od.getWarehouseReceiptQuantity()) {

                   		categoryAmount = categoryAmount.add((od.getWarehouseReceiptQuantity().subtract(returnNum)).multiply(od.getUnitPriceContainingTax()));

                   	}
                   	
                   }else {
                   	//数量*含税单价*汇率
                	   if (currencyResult.containsKey(od.getCurrency())) {
                		   BigDecimal rate = currencyResult.get(od.getCurrency()).get(0).getConversionRate();
                           if (null != od.getWarehouseReceiptQuantity()) {
                        	   categoryAmount = categoryAmount.add((od.getWarehouseReceiptQuantity().subtract(returnNum)).multiply(od.getUnitPriceContainingTax()).multiply(rate));
                       		}
                	   }
                   }
        	   }
        	   if (flag) {
        		   PurchaseCategoryDTO categoryDto = new PurchaseCategoryDTO();
        		   categoryDto.setCategoryId(category.getCategoryId());
            	   categoryDto.setCategoryName(category.getCategoryName());
            	   if (sumAmount.doubleValue() == 0) {
            		   categoryDto.setRate(BigDecimal.ZERO);
            	   } else {
            		   categoryDto.setRate(categoryAmount.divide(sumAmount,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
            	   }
            	   
            	   categoryDto.setPurchaseAmount(sumAmount);
            	   categoryDto.setWarehousingAmount(categoryAmount);
            	   categorys.add(categoryDto);
        	   }
           }
       }
       return categorys;
    }
    
    /**
     * 获取前5个品类占比及其他
     * @param list
     * @return
     */
    private List<PurchaseCategoryDTO> getCategorySix(List<PurchaseCategoryDTO> list) {
    	if (null == list) {
    		return null;
    	}
    	
    	if (list.size() > 0) {
    		Collections.sort(list, Comparator.comparing(PurchaseCategoryDTO::getRate).reversed());
    	}
    	
    	ArrayList<PurchaseCategoryDTO> newList = new ArrayList<PurchaseCategoryDTO>();
    	
    	if (list.size() > 5) {
    		BigDecimal sum = BigDecimal.ZERO;
    		for (int i=0;i<5;i++) {
    			newList.add(list.get(i));
    			sum = sum.add(list.get(i).getRate());
    		}
    		PurchaseCategoryDTO category = new PurchaseCategoryDTO();
    		category.setCategoryName("其他");
    		category.setRate(new BigDecimal(100).subtract(sum));
    		newList.add(category);
    		
    		if (list.size() > 0) {
        		Collections.sort(newList, Comparator.comparing(PurchaseCategoryDTO::getRate).reversed());
        	}
    		return newList;
    		
    	} else {
    		return list;
    	}
    }
    
    
    /**
     * 获取前级别下所有品类
     * @param level
     * @param categoryList
     * @return
     */
    private List<PurchaseCategory> getCategoryListByLevel(Integer level ,PageInfo<PurchaseCategory> categoryList) {
    	List<PurchaseCategory> list = new ArrayList<PurchaseCategory>();
    	for (PurchaseCategory category : categoryList.getList()) {
    		if (category.getLevel().equals(level)) {
    			list.add(category);
    		}
    	}
    	return list;
    }
    
    /**
     * 	获取品类全路径
     * @param categoryList
     * @return
     */
    private Map<Long,String> getCategoryStructMap(PageInfo<PurchaseCategory> categoryList){
    	Map<Long,String> map = new HashMap<Long,String>();
    	for(PurchaseCategory category : categoryList.getList()) {
    		map.put(category.getCategoryId(), category.getStruct());
    	}
    	return map;
    }
    
    private List<OrganizationRatio> getBidFive(List<OrganizationRatio> list) {
    	if (null == list) {
    		return null;
    	}
    	if (list.size() > 0) {
    		Collections.sort(list, Comparator.comparing(OrganizationRatio::getRate).reversed());
    	}
    	if (list.size() > 5) {
    		return list.subList(0, 5);
    	}
    	return list;
    }
    
    /**
     * 申请量：根据筛选条件，取集团所有采购申请订单数量（已审批通过的申请采购订单行数）
     * @param dto
     * @return
     */
    private Integer getPurApplyCount(PurchaseParamDTO dto) {
        return orderMapper.getPurApplyCount(dto);
    }

    /**
     * 采购金额：根据筛选条件，取集团所有采购总金额（采购商已确认订单）（涉及金额需要转换人民币）
     * @param dto
     * @return
     */
    private BigDecimal getPurchaseTotalPrice(PurchaseParamDTO dto,Map<String,List<LatestGidailyRate>> currencyResult) {
        BigDecimal resultBdg = new BigDecimal(0);
        List<OrderDetail> purchaseTotalPrice = orderMapper.getPurchaseTotalPrice(dto);
        //统计采购总额(非人民币根据汇率换算)
        for (OrderDetail orderDetail : purchaseTotalPrice) {
            if (OcrCurrencyType.CNY.getValue().equals(orderDetail.getCurrency())) {
            	//数量*单价
                resultBdg=resultBdg.add(orderDetail.getOrderNum().multiply(orderDetail.getUnitPriceContainingTax()));
            }else {
            	//数量*含税单价*汇率
                BigDecimal rate = currencyResult.get(orderDetail.getCurrency()).get(0).getConversionRate();
                resultBdg=resultBdg.add(orderDetail.getOrderNum().multiply(orderDetail.getUnitPriceContainingTax())
                        .multiply(rate));
            }
        }
        return resultBdg;
    }
    
    
    /**
     * 交货金额：根据筛选条件，取集团所有交货总金额（入库金额=入库数量*入库含税单价）（涉及金额需要转换人民币）
     * @param dto
     * @param currencyResult
     * @return
     */
    public BigDecimal getDeliveryTotalPrice(PurchaseParamDTO dto,Map<String,List<LatestGidailyRate>> currencyResult) {
        BigDecimal resultBdg = new BigDecimal(0);
        List<OrderDetailDTO> deliveryTotalPrice = orderMapper.getDeliveryTotalPrice(dto);
        //统计采购总额(非人民币根据汇率换算)
        for (OrderDetailDTO od : deliveryTotalPrice) {
            if (OcrCurrencyType.CNY.getValue().equals(od.getCurrency())) {
            	//数量*单价
                resultBdg=resultBdg.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()));
            }else {
            	//数量*含税单价*汇率
                BigDecimal rate = currencyResult.get(od.getCurrency()).get(0).getConversionRate();
                resultBdg=resultBdg.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax())
                        .multiply(rate));
            }
        }
        return resultBdg;
    }
    
    
    
    /**
     * 采购执行情况（%）：根据筛选条件，按不同事业部前五采购收货达成情况，（时间按订单需求日期为准）采购执行情况=  sum（（入库数量-退货数量）*订单单价 * 汇率)/sum(订单数量* 单价 * 汇率)*100%
     * @param dto
     * @param currencyResult
     * @return
     */
    public PurchaseDetailDTO getExecution(Map<String,List<LatestGidailyRate>> currencyResult,List<OrderDetailDTO> excutionList) {
    	PurchaseDetailDTO detail = new PurchaseDetailDTO();
        BigDecimal deliveryAmount = new BigDecimal(0);
        BigDecimal sumAmount = new BigDecimal(0);
        //统计采购总额(非人民币根据汇率换算)
        for (OrderDetailDTO od : excutionList) {
            if (OcrCurrencyType.CNY.getValue().equals(od.getCurrency())) {
            	if (null != od.getWarehouseReceiptQuantity() && null != od.getReturnQuantity() ) {
            		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()));
            	} else if (null != od.getWarehouseReceiptQuantity()) {
            		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()));
            	}
            	//总金额
            	sumAmount = sumAmount.add(od.getOrderNum().multiply(od.getUnitPriceContainingTax()));
            }else {
            	//数量*含税单价*汇率
            	if (currencyResult.containsKey(od.getCurrency())) {
            		BigDecimal rate = currencyResult.get(od.getCurrency()).get(0).getConversionRate();
                    if (null != od.getWarehouseReceiptQuantity() && null != od.getReturnQuantity() ) {
                		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()).multiply(rate));
                	} else if (null != od.getWarehouseReceiptQuantity()) {
                		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()).multiply(rate));
                	}
                	//总金额
                	sumAmount = sumAmount.add(od.getOrderNum().multiply(od.getUnitPriceContainingTax()).multiply(rate));
            	}
            }
        }
        
        detail.setPurchaseAmount(sumAmount);
        detail.setWarehousingAmount(deliveryAmount);
        if(null== sumAmount ||sumAmount.equals(BigDecimal.ZERO) ) {
        	return null;
        } else {
        	detail.setRate(deliveryAmount.divide(sumAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        }
        return detail;
    }
    
    
    /**
     * 采购执行情况（%）：根据筛选条件，按不同事业部前五采购收货达成情况，（时间按订单需求日期为准）采购执行情况=  sum（（入库数量-退货数量）*订单单价 * 汇率)/sum(订单数量* 单价 * 汇率)*100%
     * @param dto
     * @param currencyResult
     * @return
     */
    public PurchaseDetailDTO getExecution(PurchaseParamDTO dto,Map<String,List<LatestGidailyRate>> currencyResult) {
    	PurchaseDetailDTO detail = new PurchaseDetailDTO();
        BigDecimal deliveryAmount = new BigDecimal(0);
        BigDecimal sumAmount = new BigDecimal(0);
        List<OrderDetailDTO> excutionList = orderMapper.getExecution(dto);
        //统计采购总额(非人民币根据汇率换算)
        for (OrderDetailDTO od : excutionList) {
            if (OcrCurrencyType.CNY.getValue().equals(od.getCurrency())) {
            	if (null != od.getWarehouseReceiptQuantity() && null != od.getReturnQuantity() ) {
            		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()));
            	} else if (null != od.getWarehouseReceiptQuantity()) {
            		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()));
            	}
            	//总金额
            	sumAmount = sumAmount.add(od.getOrderNum().multiply(od.getUnitPriceContainingTax()));
            }else {
            	//数量*含税单价*汇率
            	if (currencyResult.containsKey(od.getCurrency())) {
            		BigDecimal rate = currencyResult.get(od.getCurrency()).get(0).getConversionRate();
                    if (null != od.getWarehouseReceiptQuantity() && null != od.getReturnQuantity() ) {
                		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()).multiply(rate));
                	} else if (null != od.getWarehouseReceiptQuantity()) {
                		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()).multiply(rate));
                	}
                	//总金额
                	sumAmount = sumAmount.add(od.getOrderNum().multiply(od.getUnitPriceContainingTax()).multiply(rate));
            	}
            }
        }
        
        detail.setPurchaseAmount(sumAmount);
        detail.setWarehousingAmount(deliveryAmount);
        if(null== sumAmount ||sumAmount.equals(BigDecimal.ZERO) ) {
        	return null;
        } else {
        	detail.setRate(deliveryAmount.divide(sumAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        }
        return detail;
    }
    
    /**
     * 采购执行情况（%）：根据筛选条件，按不同事业部前五采购收货达成情况，（时间按订单需求日期为准）采购执行情况=  sum（（入库数量-退货数量）*订单单价 * 汇率)/sum(订单数量* 单价 * 汇率)*100%
     * @param dto
     * @param currencyResult
     * @return
     */
    public List<PurchaseMonthRatio> getExecutionYear(PurchaseParamDTO dto,Map<String,List<LatestGidailyRate>> currencyResult) {
        BigDecimal deliveryAmount = null;
        BigDecimal sumAmount = null;
        BigDecimal rate = null;
        SimpleDateFormat format = new SimpleDateFormat("M");
        List<OrderDetailDTO> excutionList = orderMapper.punctualityYear(dto);
        
        List<PurchaseMonthRatio> months = new ArrayList<PurchaseMonthRatio>();
        PurchaseMonthRatio month = null;
        for (int i=1;i<=12;i++) {
        	deliveryAmount = new BigDecimal(0);
        	sumAmount = new BigDecimal(0);
        	rate = new BigDecimal(0);
        	for (OrderDetailDTO od : excutionList) {
        		
        		if (null == od.getRequirementDate() || od.getRequirementDate().getMonthValue() != i) {
        			continue;
        		}
                if (OcrCurrencyType.CNY.getValue().equals(od.getCurrency())) {
                	if (null != od.getWarehouseReceiptQuantity() && null != od.getReturnQuantity() ) {
                		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()));
                	} else if (null != od.getWarehouseReceiptQuantity()) {
                		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()));
                	}
                	//总金额
                	sumAmount = sumAmount.add(od.getOrderNum().multiply(od.getUnitPriceContainingTax()));
                	
                }else {
                	//数量*含税单价*汇率
                	if (currencyResult.containsKey(od.getCurrency())) {
                		 BigDecimal exchangeRate = currencyResult.get(od.getCurrency()).get(0).getConversionRate();
                         if (null != od.getWarehouseReceiptQuantity() && null != od.getReturnQuantity() ) {
                     		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()).multiply(exchangeRate));
                     	} else if (null != od.getWarehouseReceiptQuantity()) {
                     		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()).multiply(exchangeRate));
                     	}
                     	//总金额
                     	sumAmount = sumAmount.add(od.getOrderNum().multiply(od.getUnitPriceContainingTax()).multiply(exchangeRate));
                	}
                }
            }
        	month = new PurchaseMonthRatio();
        	month.setMonth(i+"月");
        	month.setDeliveryAmount(deliveryAmount);
        	month.setOrderAmount(sumAmount);
        	
        	 if(null != sumAmount && !sumAmount.equals(BigDecimal.ZERO) ) {
        		 rate = deliveryAmount.divide(sumAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
             }
        	 month.setRate(rate);
        	 months.add(month);
        	
        }
       return months;
    }
    
    
    /**
     * 采购准时率：根据筛选条件，按不同事业部前五采购收货达成情况，（时间按订单需求日期为准）采购执行情况=  sum（（入库数量-退货数量）*订单单价 * 汇率)/sum(订单数量* 单价 * 汇率)*100%
     * @param dto
     * @param currencyResult
     * @return
     */
    public PurchaseDetailDTO punctuality(PurchaseParamDTO dto,Map<String,List<LatestGidailyRate>> currencyResult) {
    	PurchaseDetailDTO detail = new PurchaseDetailDTO();
    	BigDecimal deliveryAmount = null;
        BigDecimal sumAmount = null;
        List<OrderDetailDTO> excutionList = orderMapper.punctualityYear(dto);
    	deliveryAmount = new BigDecimal(0);
    	sumAmount = new BigDecimal(0);
    	for (OrderDetailDTO od : excutionList) {
            if (OcrCurrencyType.CNY.getValue().equals(od.getCurrency())) {
            	if (null != od.getWarehouseReceiptQuantity() && null != od.getReturnQuantity() ) {
            		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()));
            	} else if (null != od.getWarehouseReceiptQuantity()) {
            		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()));
            	}
            	//总金额
            	sumAmount = sumAmount.add(od.getOrderNum().multiply(od.getUnitPriceContainingTax()));
            	
            }else {
            	//数量*含税单价*汇率
            	if (currencyResult.containsKey(od.getCurrency())) {
	                BigDecimal exchangeRate = currencyResult.get(od.getCurrency()).get(0).getConversionRate();
	                if (null != od.getWarehouseReceiptQuantity() && null != od.getReturnQuantity() ) {
	            		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()).multiply(exchangeRate));
	            	} else if (null != od.getWarehouseReceiptQuantity()) {
	            		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()).multiply(exchangeRate));
	            	}
	            	//总金额
	            	sumAmount = sumAmount.add(od.getOrderNum().multiply(od.getUnitPriceContainingTax()).multiply(exchangeRate));
            	}
            }
        }
        
    	detail.setPurchaseAmount(sumAmount);
    	detail.setWarehousingAmount(deliveryAmount);
    	if(null != sumAmount && !sumAmount.equals(BigDecimal.ZERO) ) {
    		detail.setRate(deliveryAmount.divide(sumAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        }else {
        	return null;
        }
    	return detail;
    }
    
    
    /**
     * 采购准时率：根据筛选条件，按不同事业部前五采购收货达成情况，（时间按订单需求日期为准）采购执行情况=  sum（（入库数量-退货数量）*订单单价 * 汇率)/sum(订单数量* 单价 * 汇率)*100%
     * @param dto
     * @param currencyResult
     * @return
     */
    public PurchaseDetailDTO punctuality(Map<String,List<LatestGidailyRate>> currencyResult ,List<OrderDetailDTO> excutionList) {
    	PurchaseDetailDTO detail = new PurchaseDetailDTO();
    	BigDecimal deliveryAmount = null;
        BigDecimal sumAmount = null;
    	deliveryAmount = new BigDecimal(0);
    	sumAmount = new BigDecimal(0);
    	for (OrderDetailDTO od : excutionList) {
            if (OcrCurrencyType.CNY.getValue().equals(od.getCurrency())) {
            	if (null != od.getWarehouseReceiptQuantity() && null != od.getReturnQuantity() ) {
            		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()));
            	} else if (null != od.getWarehouseReceiptQuantity()) {
            		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()));
            	}
            	//总金额
            	sumAmount = sumAmount.add(od.getOrderNum().multiply(od.getUnitPriceContainingTax()));
            	
            }else {
            	//数量*含税单价*汇率
            	if (currencyResult.containsKey(od.getCurrency())) {
	                BigDecimal exchangeRate = currencyResult.get(od.getCurrency()).get(0).getConversionRate();
	                if (null != od.getWarehouseReceiptQuantity() && null != od.getReturnQuantity() ) {
	            		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().subtract(od.getReturnQuantity()).multiply(od.getUnitPriceContainingTax()).multiply(exchangeRate));
	            	} else if (null != od.getWarehouseReceiptQuantity()) {
	            		deliveryAmount = deliveryAmount.add(od.getWarehouseReceiptQuantity().multiply(od.getUnitPriceContainingTax()).multiply(exchangeRate));
	            	}
	            	//总金额
	            	sumAmount = sumAmount.add(od.getOrderNum().multiply(od.getUnitPriceContainingTax()).multiply(exchangeRate));
            	}
            }
        }
        
    	detail.setPurchaseAmount(sumAmount);
    	detail.setWarehousingAmount(deliveryAmount);
    	if(null != sumAmount && !sumAmount.equals(BigDecimal.ZERO) ) {
    		detail.setRate(deliveryAmount.divide(sumAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        }else {
        	return null;
        }
    	return detail;
    }
    

    private void addChildFullPathId(OrganizationRelation parentNodes, List<String> resultFullPathIdList) {
        for (OrganizationRelation childNode : parentNodes.getChildOrganRelation()) {
            resultFullPathIdList.add(childNode.getFullPathId());
            if (!CollectionUtils.isEmpty(childNode.getChildOrganRelation())) {
                addChildFullPathId(childNode,resultFullPathIdList);
            }
        }
    }
    
    private void setDateParam(PurchaseParamDTO dto) {
    	if(dto.getSeason().equals(0)) {
   		 //月度执行情况
           String dateModel1 = "$date-01-01 00:00:00";
           String dateModel2 = "$date-12-31 23:59:59";
           try {
	           	dto.setComfirmTimeBegin(DateUtil.parseDate(dateModel1.replace("$date",String.valueOf(dto.getYear()))));
	           	dto.setComfirmTimeEnd(DateUtil.parseDate(dateModel2.replace("$date",String.valueOf(dto.getYear()))));
	   		} catch (Exception e) {
	   			
	   		}
	   	} else {
	   		dto.setComfirmTimeBegin(DateUtil.getStartDateOfQuarter(dto.getYear(),dto.getSeason()));
	       	dto.setComfirmTimeEnd(DateUtil.getEndDateOfQuarter(dto.getYear(),dto.getSeason()));
	   	}
    }

    public Map<String, Object> getBetweenDate(int year) throws ParseException {
        String dateModel1 = "$date-01-01 00:00:00";
        String dateModel2 = "$date-12-31 23:59:59";
        Map<String, Object> betweenDate = new HashMap<>();
        betweenDate.put("startDate",DateUtil.parseDate(dateModel1.replace("$date",String.valueOf(year))));
        betweenDate.put("endDate",DateUtil.parseDate(dateModel2.replace("$date",String.valueOf(year))));
        return betweenDate;
    }
    
    @Override
    public PageInfo<PurchaseDetailDTO> queryOrderConfirm(PurchaseParamDTO dto) {
    	//设置时间
    	this.setDateParam(dto);
    	
    	Integer pageSize = dto.getPageSize();
    	Integer pageNum = dto.getPageNum();
    	dto.setPageNum(null);
    	dto.setPageSize(null);
    	
    	LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
    	//获取用户id对应的权限节点
        List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
    	 //根据类型过滤得出父节点
        List<Organization> filterByOrgTypeCode = permissionNodeResult.stream().filter(p -> dto.getOrganizationTypeCode().equals(p.getOrganizationTypeCode())).collect(Collectors.toList());
    	//组织权限Map
        Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream().collect(Collectors.toMap(Organization::getFullPathId,
                Function.identity()));
        //获取所有组织节点
        List<OrganizationRelation> treeNewList = baseClient.allTree();
        List<String> resultFullPathIdList = new ArrayList<>();
        
        //汇率
        LatestGidailyRate gidailyRate = new LatestGidailyRate();
        gidailyRate.setToCurrency(OcrCurrencyType.CNY.getValue());
        Map<String,List<LatestGidailyRate>> currencyResult = baseClient.getCurrency(gidailyRate);
        List<String> tempList = null;
        PurchaseDetailDTO detail = null;
        
        List<PurchaseDetailDTO> list = new ArrayList<PurchaseDetailDTO>();
        for (Organization organization : filterByOrgTypeCode) {
        	if (null != dto.getFullPathId() && !dto.getFullPathId().equals(organization.getFullPathId())) {
        		continue;
        	}
            String targetParentFullPathId = organization.getFullPathId();
            tempList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
            dto.setList(tempList);
            detail = this.orderMapper.getOrderRate(dto);
            if(null != detail && null !=  detail.getRate()) {
            	 detail.setOrganizationName(organization.getOrganizationName());
                 detail.setFullPathId(organization.getFullPathId());
                 list.add(detail);
            }
        }
        
        //排序
        if (list.size() > 0) {
    		Collections.sort(list, Comparator.comparing(PurchaseDetailDTO::getRate).reversed());
    		int i=1;
    		for (PurchaseDetailDTO d : list) {
    			d.setNum(i);
    			i++;
    		}
    	}
        
        return getPage(list,pageSize,pageNum);
    }
    
    @Override
    public PageInfo<PurchaseDetailDTO> queryOrderWarehousing(PurchaseParamDTO dto) {
    	//设置时间
    	this.setDateParam(dto);
    	
    	Integer pageSize = dto.getPageSize();
    	Integer pageNum = dto.getPageNum();
    	dto.setPageNum(null);
    	dto.setPageSize(null);
    	
    	LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
    	//获取用户id对应的权限节点
        List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
    	 //根据类型过滤得出父节点
        List<Organization> filterByOrgTypeCode = permissionNodeResult.stream().filter(p -> dto.getOrganizationTypeCode().equals(p.getOrganizationTypeCode())).collect(Collectors.toList());
    	//组织权限Map
        Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream().collect(Collectors.toMap(Organization::getFullPathId,
                Function.identity()));
        //获取所有组织节点
        List<OrganizationRelation> treeNewList = baseClient.allTree();
        List<String> resultFullPathIdList = new ArrayList<>();
        
        //汇率
        LatestGidailyRate gidailyRate = new LatestGidailyRate();
        gidailyRate.setToCurrency(OcrCurrencyType.CNY.getValue());
        Map<String,List<LatestGidailyRate>> currencyResult = baseClient.getCurrency(gidailyRate);
        List<String> tempList = null;
        PurchaseDetailDTO detail = null;
        
        List<PurchaseDetailDTO> list = new ArrayList<PurchaseDetailDTO>();
        for (Organization organization : filterByOrgTypeCode) {
        	if (null != dto.getFullPathId() && !dto.getFullPathId().equals(organization.getFullPathId())) {
        		continue;
        	}
            String targetParentFullPathId = organization.getFullPathId();
            tempList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
            dto.setList(tempList);
            detail = this.getExecution(dto,currencyResult);
            if (null != detail) {
            	 detail.setOrganizationName(organization.getOrganizationName());
                 detail.setFullPathId(organization.getFullPathId());
                 list.add(detail);
            }
           
        }
        
        //排序
        if (list.size() > 0) {
    		Collections.sort(list, Comparator.comparing(PurchaseDetailDTO::getRate).reversed());
    		int i=1;
    		for (PurchaseDetailDTO d : list) {
    			d.setNum(i);
    			i++;
    		}
    	}
        return getPage(list,pageSize,pageNum);
    }
    
    /**
     * 采购准时率排名
     * @param dto
     * @return
     */
    @Override
    public PageInfo<PurchaseDetailDTO> queryOrderPunctuality(PurchaseParamDTO dto) {
    	//设置时间
    	this.setDateParam(dto);
    	
    	Integer pageSize = dto.getPageSize();
    	Integer pageNum = dto.getPageNum();
    	dto.setPageNum(null);
    	dto.setPageSize(null);
    	
    	LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
    	//获取用户id对应的权限节点
        List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
    	 //根据类型过滤得出父节点
        List<Organization> filterByOrgTypeCode = permissionNodeResult.stream().filter(p -> dto.getOrganizationTypeCode().equals(p.getOrganizationTypeCode())).collect(Collectors.toList());
    	//组织权限Map
        Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream().collect(Collectors.toMap(Organization::getFullPathId,
                Function.identity()));
        //获取所有组织节点
        List<OrganizationRelation> treeNewList = baseClient.allTree();
        List<String> resultFullPathIdList = new ArrayList<>();
        
        //汇率
        LatestGidailyRate gidailyRate = new LatestGidailyRate();
        gidailyRate.setToCurrency(OcrCurrencyType.CNY.getValue());
        Map<String,List<LatestGidailyRate>> currencyResult = baseClient.getCurrency(gidailyRate);
        List<String> tempList = null;
        PurchaseDetailDTO detail = null;
        
        List<PurchaseDetailDTO> list = new ArrayList<PurchaseDetailDTO>();
        for (Organization organization : filterByOrgTypeCode) {
        	if (null != dto.getFullPathId() && !dto.getFullPathId().equals(organization.getFullPathId())) {
        		continue;
        	}
            String targetParentFullPathId = organization.getFullPathId();
            tempList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
            dto.setList(tempList);
            detail = this.punctuality(dto, currencyResult);
            if (null != detail) {
            	 detail.setOrganizationName(organization.getOrganizationName());
                 detail.setFullPathId(organization.getFullPathId());
                 list.add(detail);
            }
           
        }
        
        //排序
        if (list.size() > 0) {
    		Collections.sort(list, Comparator.comparing(PurchaseDetailDTO::getRate).reversed());
    		int i=1;
    		for (PurchaseDetailDTO d : list) {
    			d.setNum(i);
    			i++;
    		}
    	}
        return getPage(list,pageSize,pageNum);
    }
    
    
    /**
     * 采购准时率趋势
     * @param dto
     * @return
     */
    @Override
    public PageInfo<PurchaseMonthRatio> queryOrderPunctualityYear(PurchaseParamDTO dto) {
    	Integer pageSize = dto.getPageSize();
    	Integer pageNum = dto.getPageNum();
    	dto.setPageNum(null);
    	dto.setPageSize(null);
    	
    	LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
    	//获取用户id对应的权限节点
        List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
    	 //根据类型过滤得出父节点
        List<Organization> filterByOrgTypeCode = permissionNodeResult.stream().filter(p -> dto.getOrganizationTypeCode().equals(p.getOrganizationTypeCode())).collect(Collectors.toList());
    	//组织权限Map
        Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream().collect(Collectors.toMap(Organization::getFullPathId,
                Function.identity()));
        //获取所有组织节点
        List<OrganizationRelation> treeNewList = baseClient.allTree();
        List<String> resultFullPathIdList = new ArrayList<>();
        
        //汇率
        LatestGidailyRate gidailyRate = new LatestGidailyRate();
        gidailyRate.setToCurrency(OcrCurrencyType.CNY.getValue());
        Map<String,List<LatestGidailyRate>> currencyResult = baseClient.getCurrency(gidailyRate);
        List<String> tempList = null;
        PurchaseDetailDTO detail = null;
        
        List<PurchaseDetailDTO> list = new ArrayList<PurchaseDetailDTO>();
        for (Organization organization : filterByOrgTypeCode) {
        	if (null != dto.getFullPathId() && !dto.getFullPathId().equals(organization.getFullPathId())) {
        		continue;
        	}
            String targetParentFullPathId = organization.getFullPathId();
            tempList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
            resultFullPathIdList.addAll(tempList);
        }
        dto.setList(resultFullPathIdList);
        List<PurchaseMonthRatio> resultList = this.getExecutionYear(dto, currencyResult);
        
        if (null != resultList) {
        	for (PurchaseMonthRatio month : resultList) {
        		month.setMonth(dto.getYear()+"年"+month.getMonth());
        	}
        }
        return getPage(resultList,pageSize,pageNum);
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
    
    
    /**
     * 采购订单明细
     * @param dto
     * @return
     */
    @Override
    public PageInfo<PurchaseDetailInfoDTO> queryPurchaseDetailList(PurchaseParamDTO dto) {
    	if (null != dto.getEndMonth()) {
    		Calendar cal = Calendar.getInstance();
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
    		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    		dto.setStartMonth(dto.getStartMonth()+"-01");
    		try {
    			cal.setTime(format.parse(dto.getEndMonth()));
    			cal.add(Calendar.MONTH, 1);
    			cal.add(Calendar.DAY_OF_MONTH, -1);
    			String endMonth = format1.format(cal.getTime()) + " 23:59:59";
    			dto.setEndMonth(endMonth);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    	
    	LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
    	//获取用户id对应的权限节点
        List<Organization> permissionNodeResult = baseClient.getFullPathIdByTypeCode(loginAppUser.getUserId());
    	 //根据类型过滤得出父节点
        List<Organization> filterByOrgTypeCode = permissionNodeResult.stream().filter(p -> dto.getOrganizationTypeCode().equals(p.getOrganizationTypeCode())).collect(Collectors.toList());
    	//组织权限Map
        Map<String, Organization> permissionNodeResultMap = permissionNodeResult.stream().collect(Collectors.toMap(Organization::getFullPathId,
                Function.identity()));
        //获取所有组织节点
        List<OrganizationRelation> treeNewList = baseClient.allTree();
        List<String> resultFullPathIdList = new ArrayList<>();
        
        List<String> tempList = null;
        
        List<PurchaseDetailDTO> list = new ArrayList<PurchaseDetailDTO>();
        for (Organization organization : filterByOrgTypeCode) {
        	if (null != dto.getFullPathId() && !dto.getFullPathId().equals(organization.getFullPathId())) {
        		continue;
        	}
            String targetParentFullPathId = organization.getFullPathId();
            tempList = OrgUtils.findParentStart(treeNewList,targetParentFullPathId,permissionNodeResultMap);
            resultFullPathIdList.addAll(tempList);
        }
        dto.setList(resultFullPathIdList);
        
        PageUtil.startPage(dto.getPageNum(), dto.getPageSize());
    	List<PurchaseDetailInfoDTO> resultList = this.orderMapper.queryPurchaseDetailList(dto);
    	return new PageInfo<PurchaseDetailInfoDTO>(resultList);
    }
}
