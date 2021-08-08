package com.midea.cloud.srm.base.formula.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.base.formula.mapper.BaseMaterialPriceMapper;
import com.midea.cloud.srm.base.formula.service.IEssentialFactorCalculateService;
import com.midea.cloud.srm.base.formula.service.IEssentialFactorValueService;
import com.midea.cloud.srm.model.base.formula.entity.BaseMaterialPrice;
import com.midea.cloud.srm.model.base.formula.vo.EssentialFactorValueParameter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Implement of {@link IEssentialFactorCalculateService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class IEssentialFactorValueForBaseMaterialPriceServiceImpl implements IEssentialFactorValueService {

    @Resource
    private BaseMaterialPriceMapper baseMaterialPriceDao;

    private static final String TYPE = "YD";

    @Override
    public String value(EssentialFactorValueParameter parameter) {
        //如果来自于海鲜价，取序列化的值计算
        if (Objects.equals(parameter.getIsSeaFoodPriceFormula(), YesOrNo.YES.getValue())) {
            return parameter.getPrices().stream().filter(e->Objects.equals(parameter.getEssentialFactor().getEssentialFactorId(),e.getEssentialFactorId()))
                    .findAny().orElseThrow(()->new BaseException(String.format("获取海鲜价公式的基价[%s]失败",parameter.getEssentialFactor().getBaseMaterialName()))).getBaseMaterialPrice().toPlainString();
        }
        Date today = new Date();
        List<BaseMaterialPrice> baseMaterialPrices = baseMaterialPriceDao.selectList(
                Wrappers.lambdaQuery(BaseMaterialPrice.class)
                        .le(BaseMaterialPrice::getActiveDateFrom, today)
                        .ge(BaseMaterialPrice::getActiveDateTo, today)
                        .eq(Objects.nonNull(parameter.getPriceFrom()), BaseMaterialPrice::getPriceFrom, parameter.getPriceFrom())
                        .eq(Objects.nonNull(parameter.getEssentialFactor().getBaseMaterialId()), BaseMaterialPrice::getBaseMaterialId, parameter.getEssentialFactor().getBaseMaterialId())
        );
        if (CollectionUtils.isEmpty(baseMaterialPrices)) {
            new BaseException(
                    "基价获取失败 | 基材 [" + parameter.getEssentialFactor().getBaseMaterialCode()
                            + "]，价格类型 [" + parameter.getEssentialFactor().getPriceType() + "],数据来源[" + parameter.getPriceFrom() + "]");
        }
        PriorityQueue<BaseMaterialPrice> queue = new PriorityQueue<>((Comparator<BaseMaterialPrice>) (o1, o2) -> {
            int i1 = TYPE.indexOf(o1.getBaseMaterialPriceType().charAt(0));
            int i2 = TYPE.indexOf(o2.getBaseMaterialPriceType().charAt(0));
            //按日-昨天排名
            return Integer.compare(i2, i1);
        });
        for (BaseMaterialPrice baseMaterialPrice : baseMaterialPrices) {
            queue.add(baseMaterialPrice);
        }
        BaseMaterialPrice poll = queue.poll();
        queue = null;
        return poll.getBaseMaterialPrice().toPlainString();
    }
}
