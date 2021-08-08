package com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.controller;

import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IBidOrderLineTemplatePriceDetailService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.vo.BidOrderLineTemplatePriceDetailVO;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Http接口 - 供应商投标行[模型报价]明细
 *
 * @author zixuan.yan@meicloud.com
 */
@RestController
@RequestMapping("/techProposal/bidOrderLineTemplatePriceDetails")
public class BidOrderLineTemplatePriceDetailController extends BaseController {

    @Resource
    private IBidOrderLineTemplatePriceDetailService     templatePriceDetailService;


    @GetMapping("/findDetailsByLineId")
    public List<BidOrderLineTemplatePriceDetailVO> findDetailsByLineId(Long lineId) {
        return templatePriceDetailService.findDetailsByLineId(lineId);
    }

    @PostMapping("/saveDetails")
    public void saveDetails(@RequestBody List<BidOrderLineTemplatePriceDetailVO> details) {
        templatePriceDetailService.saveDetails(details);
    }
}
