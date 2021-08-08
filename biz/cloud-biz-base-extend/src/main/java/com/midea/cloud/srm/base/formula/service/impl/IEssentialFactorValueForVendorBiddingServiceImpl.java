package com.midea.cloud.srm.base.formula.service.impl;

import com.alibaba.fastjson.JSON;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.srm.base.formula.service.IEssentialFactorCalculateService;
import com.midea.cloud.srm.base.formula.service.IEssentialFactorValueService;
import com.midea.cloud.srm.feign.base.IVendorBiddingEssentialFactorValueService;
import com.midea.cloud.srm.model.base.formula.enums.SourcingType;
import com.midea.cloud.srm.model.base.formula.vo.EssentialFactorValue;
import com.midea.cloud.srm.model.base.formula.vo.EssentialFactorValueParameter;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implement of {@link IEssentialFactorCalculateService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class IEssentialFactorValueForVendorBiddingServiceImpl implements IEssentialFactorValueService {

    @Override
    public String value(EssentialFactorValueParameter parameter) {
        //海鲜价公式，直接从冗余的字段取
        if (Objects.equals(parameter.getIsSeaFoodPriceFormula(), YesOrNo.YES.getValue())&& StringUtils.isNotBlank(parameter.getEssentialFactorValues())) {
            return Optional.ofNullable(parameter.getEssentialFactorValues())
                    .map(detail -> JSON.<Map<String, String>>parseObject(detail, Map.class))
                    .map(propertyValueMap -> {
                        List<EssentialFactorValue> essentialFactorValues = new ArrayList<>();
                        propertyValueMap.forEach((property, value) -> essentialFactorValues.add(
                                EssentialFactorValue.builder()
                                        .essentialFactorId(Long.parseLong(property))
                                        .essentialFactorValue(value)
                                        .build()
                        ));
                        return essentialFactorValues;
                    })
                    .orElseThrow(() -> new BaseException("供应商投标行[公式报价]明细的要素值获取失败"))
                    .stream().filter(value -> value.getEssentialFactorId().equals(parameter.getEssentialFactor().getEssentialFactorId()))
                    .findAny()
                    .map(EssentialFactorValue::getEssentialFactorValue)
                    .orElseThrow(() -> new BaseException(
                            "报价中的要素值获取失败, 要素名称 [" + parameter.getEssentialFactor().getEssentialFactorName() + "]"));
        }
        return SpringContextHolder.getBean(selectFeignClient(parameter.getSourcingType()))
                .findEssentialFactorValues(
                        Long.parseLong(parameter.getVendorBiddingBizId())
                )
                .stream()
                .filter(value -> value.getEssentialFactorId().equals(parameter.getEssentialFactor().getEssentialFactorId()))
                .findAny()
                .map(EssentialFactorValue::getEssentialFactorValue)
                .orElseThrow(() -> new BaseException(
                        "报价中的要素值获取失败 | 报价业务ID [" + parameter.getVendorBiddingBizId()
                                + "], 要素名称 [" + parameter.getEssentialFactor().getEssentialFactorName() + "]"));
    }

    protected Class<? extends IVendorBiddingEssentialFactorValueService> selectFeignClient(SourcingType sourcingType) {
        switch (sourcingType) {
            case BIDDING:
                return com.midea.cloud.srm.feign.bid.BidOrderLineFormulaPriceDetailClient.class;
            case BARGAINING:
                return com.midea.cloud.srm.feign.bargaining.BidOrderLineFormulaPriceDetailClient.class;
            case COMPETING:
            default:
                throw new BaseException("Could not find correct feign client with type [" + sourcingType + "]");
        }
    }
}
