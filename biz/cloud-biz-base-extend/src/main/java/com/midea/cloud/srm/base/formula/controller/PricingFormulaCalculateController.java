package com.midea.cloud.srm.base.formula.controller;

import com.midea.cloud.srm.base.formula.service.IPricingFormulaCalculateService;
import com.midea.cloud.srm.model.base.formula.vo.PricingFormulaCalculateParameter;
import com.midea.cloud.srm.model.base.formula.vo.PricingFormulaCalculateResult;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Http接口 - 公式计算
 *
 * @author zixuan.yan@meicloud.com
 */
@RestController
@RequestMapping("/pricingFormula")
public class PricingFormulaCalculateController extends BaseController {

    @Resource
    private IPricingFormulaCalculateService     calculateService;


    @PostMapping("calculate")
    public PricingFormulaCalculateResult calculate(@RequestBody PricingFormulaCalculateParameter parameter) {
        return calculateService.calculate(parameter);
    }

    @PostMapping("calculateInBatch")
    public List<PricingFormulaCalculateResult> calculate(@RequestBody List<PricingFormulaCalculateParameter> parameters) {
        return calculateService.calculate(parameters);
    }
}
