package com.midea.cloud.srm.model.base.formula.vo;

import com.midea.cloud.srm.model.base.formula.entity.PricingFormulaHeader;
import com.midea.cloud.srm.model.base.formula.entity.PricingFormulaLine;
import com.midea.cloud.srm.model.base.formula.enums.SourcingType;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 入参 - 公式价格计算参数
 *
 * @author zixuan.yan@meicloud.com
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class PricingFormulaCalculateParameter implements Serializable {

    private String calculateId;
    private SourcingType sourcingType;
    private MaterialItem materialItem;
    private String vendorBiddingBizId;
    private PricingFormulaHeader formulaHeader;
    private List<PricingFormulaLine> formulaLines;
    private Map<Long, BaseMaterialPriceVO> collect;
    private String isSeaFoodPrice;
    private String priceJSON;
    private String essentialFactorValues;

    /**
     * 创建价格计算参数
     *
     * @param sourcingType       寻源类型（招标/询比价/竞价）
     * @param materialItem       寻源行需求物料
     * @param vendorBiddingBizId 供应商报价业务ID
     * @param formulaHeader      公式头
     * @param formulaLines       公式行
     * @return 计算参数
     */
    public static PricingFormulaCalculateParameter from(SourcingType sourcingType,
                                                        MaterialItem materialItem, String vendorBiddingBizId,
                                                        PricingFormulaHeader formulaHeader, List<PricingFormulaLine> formulaLines,
                                                        Map<Long, BaseMaterialPriceVO> collect,
                                                        String isSeaFoodPrice,
                                                        String priceJSON,
                                                        String essentialFactorValues

    ) {
        Assert.notNull(materialItem, "[materialItem] could not be null.");
        Assert.notNull(formulaHeader, "[formulaHeader] could not be null.");
        Assert.notNull(formulaLines, "[formulaLines] could not be null.");
        return new PricingFormulaCalculateParameter(
                UUID.randomUUID().toString(),
                sourcingType, materialItem,
                vendorBiddingBizId,
                formulaHeader, formulaLines,
                collect,
                isSeaFoodPrice,
                priceJSON,
                essentialFactorValues
        );
    }
}
