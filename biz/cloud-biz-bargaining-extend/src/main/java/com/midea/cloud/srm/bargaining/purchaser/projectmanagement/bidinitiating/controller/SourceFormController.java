package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidRequirementLineService;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.ISourceFormService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.GenerateSourceFormParameter;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.GenerateSourceFormResult;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo.SourceForm;
import com.midea.cloud.srm.model.base.material.MaterialItemAttributeRelate;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.base.material.vo.MaterialItemAttributeRelateVO;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Http接口 - 寻源单创建
 *
 * @author zixuan.yan@meicloud.com
 */
@RestController
@RequestMapping("/bidInitiating/sourceForm")
public class SourceFormController extends BaseController {

    @Resource
    private ISourceFormService  sourceFormService;
    @Resource
    private IBidRequirementLineService lineService;


    @PostMapping("/generate")
    public GenerateSourceFormResult generateForm(@RequestBody GenerateSourceFormParameter parameter) {
        return sourceFormService.generateForm(parameter);
    }

    /**
     * 合同物料价格转寻缘单
     */
    @PostMapping("/generateByCm")
    public GenerateSourceFormResult generateByCm(@RequestBody SourceForm sourceForm) {
        GenerateSourceFormParameter parameter = GenerateSourceFormParameter.builder()
                .sourceForm(sourceForm)
                .build();
        return sourceFormService.generateForm(parameter);
    }

    @PostMapping("/getBidingOrgId2factor")
    public List<MaterialItemAttributeRelateVO> getBidingOrgId2factor(@RequestParam("bidingId") Long bidingId) {
        List<MaterialItemAttributeRelateVO> collect = lineService.list(Wrappers.query(new BidRequirementLine())
                .select("distinct ORG_ID,CEEA_ORG_CODE AS orgCode,ORG_NAME")
                .eq("BIDING_ID", bidingId)
        ).stream().map(e -> {
            MaterialItemAttributeRelateVO relate = new MaterialItemAttributeRelateVO();
            relate.setRelateId(e.getOrgId());
            relate.setAttributeName(e.getOrgName());
            relate.setKeyFeature("N");
            relate.setAttributeValue(e.getOrgCode());
            return relate;
        }).collect(Collectors.toList());
        return collect;
    }
}
