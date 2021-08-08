package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.controller;

import com.midea.cloud.srm.model.base.formula.dto.calculate.FormulaCalculateParam;
import com.midea.cloud.srm.model.base.formula.vo.EssentialFactorValue;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.BidOrderLineFormulaPriceDetail;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.techproposal.service.IBidOrderLineFormulaPriceDetailService;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Http接口 - 供应商投标行[公式报价]明细
 *
 * @author zixuan.yan@meicloud.com
 */
@RestController
@RequestMapping("/techProposal/bidOrderLineFormulaPriceDetails")
public class BidOrderLineFormulaPriceDetailController extends BaseController {

    @Resource
    private IBidOrderLineFormulaPriceDetailService  formulaPriceDetailService;


    @GetMapping("/findDetailsByLineId")
    public List<BidOrderLineFormulaPriceDetail> findDetailsByLineId(Long lineId) {
        return formulaPriceDetailService.findDetailsByLineId(lineId);
    }

    @GetMapping("/findEssentialFactorValuesByDetailId")
    public List<EssentialFactorValue> findEssentialFactorValuesByLineDetailId(Long detailId) {
        return formulaPriceDetailService.findEssentialFactorValuesByDetailId(detailId);
    }

    @PostMapping("/saveDetails")
    public void saveDetails(@RequestBody FormulaCalculateParam param) {
        formulaPriceDetailService.saveDetails(param);
    }
}
