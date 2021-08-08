package com.midea.cloud.srm.model.base.formula.vo;

import com.midea.cloud.srm.model.base.formula.dto.calculate.BaseMaterialPriceDTO;
import com.midea.cloud.srm.model.base.formula.entity.EssentialFactor;
import com.midea.cloud.srm.model.base.formula.enums.SourcingType;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.price.baseprice.dto.BasePriceDto;
import lombok.*;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * 入参 - 要素值获取
 *
 * @author zixuan.yan@meicloud.com
 */
@Data
public class EssentialFactorValueParameter implements Serializable {

    private final SourcingType sourcingType;
    private final MaterialItem materialItem;
    private final String vendorBiddingBizId;
    private final EssentialFactor essentialFactor;
    private final String priceFrom;
    private final String isSeaFoodPriceFormula;
    private final List<BaseMaterialPriceDTO> prices;
    private final String essentialFactorValues;

    public EssentialFactorValueParameter(SourcingType sourcingType, MaterialItem materialItem, String vendorBiddingBizId, EssentialFactor essentialFactor,
                                         String priceFrom, String isSeaFoodPriceFormula, List<BaseMaterialPriceDTO> prices, String essentialFactorValues) {
        this.sourcingType = sourcingType;
        this.materialItem = materialItem;
        this.vendorBiddingBizId = vendorBiddingBizId;
        this.essentialFactor = essentialFactor;
        this.priceFrom = priceFrom;
        this.isSeaFoodPriceFormula = isSeaFoodPriceFormula;
        this.prices = prices;
        this.essentialFactorValues = essentialFactorValues;
    }

    /**
     * 创建 要素值获取参数
     *
     * @param sourcingType       寻源类型（招标/询比价/竞价）
     * @param materialItem       寻源行需求物料
     * @param vendorBiddingBizId 供应商报价业务ID
     * @param essentialFactor    需要获取值的要素定义
     * @return 要素值
     */
    public static EssentialFactorValueParameter from(SourcingType sourcingType,
                                                     MaterialItem materialItem, String vendorBiddingBizId,
                                                     EssentialFactor essentialFactor,
                                                     String priceFrom, String isSeaFoodPriceFormula, List<BaseMaterialPriceDTO> prices,String essentialFactorValues
    ) {
        Assert.notNull(materialItem, "[materialItem] could not be null.");
        Assert.notNull(essentialFactor, "[essentialFactor] could not be null.");
        return new EssentialFactorValueParameter(sourcingType, materialItem, vendorBiddingBizId, essentialFactor, priceFrom, isSeaFoodPriceFormula, prices,essentialFactorValues);
    }
}
