package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.service;

import com.midea.cloud.srm.model.base.formula.dto.calculate.FormulaCalculateParam;
import com.midea.cloud.srm.model.base.formula.vo.BaseMaterialPriceVO;
import com.midea.cloud.srm.model.base.formula.vo.EssentialFactorValue;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.BidOrderLineFormulaPriceDetail;
import com.midea.cloud.srm.model.base.formula.vo.PricingFormulaCalculateResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 供应商投标行[公式报价]明细服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface IBidOrderLineFormulaPriceDetailService {

    /**
     * 根据[投标行ID]获取 供应商投标行[公式报价]明细
     *
     * @param lineId    投标行ID
     * @return  供应商投标行[公式报价]明细
     */
    List<BidOrderLineFormulaPriceDetail> findDetailsByLineId(Long lineId);

    /**
     * 根据[投标行[公式报价]明细ID]获取 供应商投标行[公式报价]明细的要素值
     *
     * @param detailId    投标行[公式报价]明细ID
     * @return  供应商投标行[公式报价]明细的要素值
     */
    List<EssentialFactorValue> findEssentialFactorValuesByDetailId(Long detailId);

    /**
     * 存储 供应商投标行[公式报价]明细集
     *
     * @param details   供应商投标行[公式报价]明细集
     */
    void saveDetails(List<BidOrderLineFormulaPriceDetail> details);

    void saveDetails(FormulaCalculateParam param);


    /**
     * 计算 指定投标行含税总价
     *
     * @param lineId    投标行ID
     * @return  投标行含税总价
     */
    PricingFormulaCalculateResult calculateOrderLineTaxTotalPrice(Long lineId, Map<Long, BaseMaterialPriceVO> collect);
}
