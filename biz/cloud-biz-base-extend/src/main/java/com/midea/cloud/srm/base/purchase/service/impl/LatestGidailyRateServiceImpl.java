package com.midea.cloud.srm.base.purchase.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.base.purchase.mapper.LatestGidailyRateMapper;
import com.midea.cloud.srm.base.purchase.service.ILatestGidailyRateService;
import com.midea.cloud.srm.model.base.purchase.entity.LatestGidailyRate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
*  <pre>
 *  汇率表（最新的汇率，持续更新） 服务实现类
 * </pre>
*
* @author xiexh12@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-27 16:29:44
 *  修改内容:
 * </pre>
*/
@Service
public class LatestGidailyRateServiceImpl extends ServiceImpl<LatestGidailyRateMapper, LatestGidailyRate> implements ILatestGidailyRateService {
    @Override
    public List<LatestGidailyRate> getRMBRate(String toCurrencyCode) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("FROM_CURRENCY_CODE","CNY");
        wrapper.eq("TO_CURRENCY_CODE",toCurrencyCode);
        return this.list(wrapper);
    }
    
    @Override
    public Map<String,List<LatestGidailyRate>> getCurrency(LatestGidailyRate gidailyRate) {
        List<LatestGidailyRate> result = this.list();
        if (CollectionUtils.isEmpty(result)) {
            throw new BaseException(String.format("不存在对应%s币种汇率",gidailyRate.getToCurrency()));
        }
        Map<String,List<LatestGidailyRate>> resultMap = new HashMap<String,List<LatestGidailyRate>>();
        result.forEach(e->{
        	List<LatestGidailyRate> list = null;
        	if (resultMap.containsKey(e.getFromCurrency())) {
        		list = resultMap.get(e.getFromCurrency());
        	} else {
        		list = new ArrayList<LatestGidailyRate>();
        	}
        	list.add(e);
            resultMap.put(e.getFromCurrency(), list);
        });
        return resultMap;
    }


    @Override
    public Map<String, LatestGidailyRate> getLatestGidailyRate(String toCurrencyCode, String conversionType) {
        Map<String, LatestGidailyRate> gidailyRateMap = new HashMap<>();
        List<LatestGidailyRate> latestGidailyRates = this.list(Wrappers.lambdaQuery(LatestGidailyRate.class).
                eq(LatestGidailyRate::getToCurrencyCode, toCurrencyCode).
                eq(LatestGidailyRate::getConversionType, conversionType));
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(latestGidailyRates)){
            gidailyRateMap = latestGidailyRates.stream().collect(Collectors.toMap(LatestGidailyRate::getFromCurrencyCode, Function.identity(), (k1, k2) -> k1));
        }
        return gidailyRateMap;
    }
}
