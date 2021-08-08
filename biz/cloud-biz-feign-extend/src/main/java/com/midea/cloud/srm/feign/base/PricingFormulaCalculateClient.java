package com.midea.cloud.srm.feign.base;

import com.midea.cloud.srm.model.base.formula.vo.PricingFormulaCalculateParameter;
import com.midea.cloud.srm.model.base.formula.vo.PricingFormulaCalculateResult;
import com.midea.cloud.srm.model.base.formula.vo.PricingFormulaHeaderVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * FeignClient - 公式价格计算Api
 *
 * @author zixuan.yan@meicloud.com
 */
@FeignClient(name = "cloud-biz-base", contextId = "cloud-biz-base-pricingFormulaCalculate")
public interface PricingFormulaCalculateClient {

    /**
     * 计算公式价格
     *
     * @param parameter 计算参数
     * @return 计算结果
     */
    @PostMapping({"/pricingFormula/calculate"})
    PricingFormulaCalculateResult calculate(@RequestBody PricingFormulaCalculateParameter parameter);

    /**
     * 批量计算公式价格
     * c
     *
     * @param parameters 计算参数集
     * @return 计算结果集
     */
    @PostMapping({"/pricingFormula/calculateInBatch"})
    List<PricingFormulaCalculateResult> calculate(@RequestBody List<PricingFormulaCalculateParameter> parameters);

    /**
     * 根据物料id和value返回头信息
     * @param materialId
     * @param value
     * @return
     */
    @GetMapping("/bid/pricing-formula/getPricingFormulaHeaderByMaterialIdAndValue")
    List<PricingFormulaHeaderVO> getPricingFormulaHeaderByMaterialIdAndValue(@RequestParam Long materialId,@RequestParam String value);

    /**
     * 根据物料id返回头信息
     * @param materialId
     * @return
     */
    @GetMapping("/bid/pricing-formula/getPricingFormulaHeaderByMaterialId")
     List<PricingFormulaHeaderVO> getPricingFormulaHeaderByMaterialId(@RequestParam Long materialId) ;

}