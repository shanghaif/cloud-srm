package com.midea.cloud.srm.base.formula.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.googlecode.aviator.AviatorEvaluator;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.srm.base.formula.mapper.EssentialFactorMapper;
import com.midea.cloud.srm.base.formula.service.CalculateInfoHolder;
import com.midea.cloud.srm.base.formula.service.IEssentialFactorCalculateService;
import com.midea.cloud.srm.base.formula.service.IPricingFormulaCalculateService;
import com.midea.cloud.srm.model.base.formula.dto.calculate.BaseMaterialPriceDTO;
import com.midea.cloud.srm.model.base.formula.entity.EssentialFactor;
import com.midea.cloud.srm.model.base.formula.entity.PricingFormulaLine;
import com.midea.cloud.srm.model.base.formula.enums.EssentialFactorFromType;
import com.midea.cloud.srm.model.base.formula.vo.BaseMaterialPriceVO;
import com.midea.cloud.srm.model.base.formula.vo.EssentialFactorValueParameter;
import com.midea.cloud.srm.model.base.formula.vo.PricingFormulaCalculateParameter;
import com.midea.cloud.srm.model.base.formula.vo.PricingFormulaCalculateResult;
import com.midea.cloud.srm.model.log.biz.holder.BizDocumentInfoHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.midea.cloud.srm.model.base.formula.enums.PricingFormulaLineType.FIELD;

/**
 * Implement of {@link IPricingFormulaCalculateService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class IPricingFormulaCalculateServiceImpl implements IPricingFormulaCalculateService {

    @Resource
    private EssentialFactorMapper essentialFactorDao;
    @Resource
    private IEssentialFactorCalculateService essentialFactorCalculateService;


    @Override
    public PricingFormulaCalculateResult calculate(PricingFormulaCalculateParameter parameter) {

        // 获取 要素定义集
        Map<Long, EssentialFactor> essentialFactors = essentialFactorDao.selectList(
                Wrappers.lambdaQuery(EssentialFactor.class)
                        .in(EssentialFactor::getEssentialFactorId, parameter.getFormulaLines().stream()
                                .map(PricingFormulaLine::getEssentialFactorId)
                                .collect(Collectors.toSet()))
        ).stream().collect(Collectors.toMap(EssentialFactor::getEssentialFactorId, x -> x, (x, y) -> x));
        List<BaseMaterialPriceDTO> prices = Collections.emptyList();
        //海鲜价的话，以priceJSON这个字符为准，其中寻源及订单计算要用到
        if (Objects.equals(YesOrNo.YES.getValue(), parameter.getIsSeaFoodPrice())) {
            prices = JSON.parseArray(parameter.getPriceJSON(), BaseMaterialPriceDTO.class);
        }
        final List<BaseMaterialPriceDTO> point = prices;
        // 获取 要素值集
        Map<String, String> expressionParameter = new HashMap<>();
        parameter.getFormulaLines().stream()
                .filter(formulaLine -> FIELD.getCode().equals(formulaLine.getPricingFormulaLineType()))
                .forEach(formulaLine -> {

                    // 获取 要素定义
                    EssentialFactor essentialFactor = essentialFactors.get(formulaLine.getEssentialFactorId());
                    Map<Long, BaseMaterialPriceVO> temp = Optional.ofNullable(parameter.getCollect()).orElse(new HashMap<>());
                    BaseMaterialPriceVO baseMaterialPriceVO = temp.get(essentialFactor.getEssentialFactorId());
                    Assert.notNull(essentialFactor, "要素定义获取失败 | essentialFactorId [" + formulaLine.getEssentialFactorId() + "]");
                    String priceFrom = Objects.isNull(baseMaterialPriceVO) ? null : baseMaterialPriceVO.getPriceFrom();

                    // 获取 要素值,这里是一个个要素去获取的，一个要素对应了一个基价,供应商报价是一个json字符串
                    String essentialFactorValue = essentialFactorCalculateService
                            .select(EssentialFactorFromType.getByItemValue(essentialFactor.getEssentialFactorFrom()))
                            .value(EssentialFactorValueParameter.from(
                                    parameter.getSourcingType(), parameter.getMaterialItem(),
                                    parameter.getVendorBiddingBizId(), essentialFactor, priceFrom
                                    , parameter.getIsSeaFoodPrice(), point, parameter.getEssentialFactorValues()
                            ));

                    // 存储 要素名称-要素值 对应关系
                    expressionParameter.put(formulaLine.getPricingFormulaLineValue(), essentialFactorValue);
                });

        // 公式处理 - 百分比格式转换 & 变量替换（注：直接使用AviatorEvaluator的变量替换是不支持#特殊符号的）
        String formula = parameter.getFormulaHeader().getPricingFormulaValue()
                .replaceAll("%", " * 0.01 ");
        Map<String, String> temp = new HashMap();
        expressionParameter.forEach((k, v) -> {
            temp.put(k, v);
        });
        for (Map.Entry<String, String> entry : expressionParameter.entrySet()) {
            formula = formula.replaceAll(entry.getKey(), entry.getValue() + "M");
        }

        // 表达式计算
        Object price = AviatorEvaluator.execute(formula);

        // 确定结果
        BigDecimal calculateResult = new BigDecimal(price.toString());

        return PricingFormulaCalculateResult.create(parameter, calculateResult,temp);
    }

    @Override
    public List<PricingFormulaCalculateResult> calculate(List<PricingFormulaCalculateParameter> parameters) {
        return parameters.stream()
                .map(this::calculate)
                .collect(Collectors.toList());
    }
}
