package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.controller;

import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidRequirementLineTemplatePriceService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.BidRequirementLineTemplatePriceVO;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Http接口 - 寻源需求行[模型报价]明细
 *
 * @author zixuan.yan@meicloud.com
 */
@RestController
@RequestMapping("/bidInitiating/bidRequirementLineTemplatePrice")
public class BidRequirementLineTemplatePriceController extends BaseController {

    @Resource
    private IBidRequirementLineTemplatePriceService     templatePriceService;


    @GetMapping("/findDetailsByLineId")
    public List<BidRequirementLineTemplatePriceVO> findDetailsByLineId(long lineId) {
        return templatePriceService.findDetailsByLineId(lineId);
    }

    @PostMapping("/saveDetails")
    public void saveDetails(@RequestBody List<BidRequirementLineTemplatePriceVO> details) {
        templatePriceService.saveDetails(details);
    }

    @DeleteMapping("/deleteDetails")
    public void deleteDetails(Long[] detailIds) {
        templatePriceService.deleteDetails(detailIds);
    }

    @DeleteMapping("/deleteDetailLines")
    public void deleteDetailLines(Long[] detailLineIds) {
        templatePriceService.deleteDetailLines(detailLineIds);
    }
}
