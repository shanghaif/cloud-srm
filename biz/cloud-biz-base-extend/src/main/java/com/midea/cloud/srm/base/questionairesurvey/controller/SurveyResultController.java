package com.midea.cloud.srm.base.questionairesurvey.controller;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.component.filter.HttpServletHolder;
import com.midea.cloud.srm.base.questionairesurvey.service.SurveyResultService;
import com.midea.cloud.srm.base.questionairesurvey.service.SurveyScopeVendorService;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.FeedbackCountDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.FeedbackSupplierDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.SurveyQuestionDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.VendorFileDto;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
* <pre>
 *  问卷调查 前端控制器
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 17, 2021 5:30:44 PM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/base/surveyresult")
public class SurveyResultController extends BaseController {

    @Autowired
    SurveyScopeVendorService surveyScopeVendorService;

    @Autowired
    SurveyResultService surveyResultService;

    //返回供应商反馈列表
    @RequestMapping("/listFeedbackVendor")
    public PageInfo<FeedbackSupplierDTO> listFeedbackVendor(@RequestBody FeedbackSupplierDTO feedbackSupplierDTO) {
        return new PageInfo<>(surveyScopeVendorService.listFeedbackVendor(feedbackSupplierDTO));
    }
    //返回统计结果
    @RequestMapping("/queryFeedbackResult")
    public FeedbackCountDTO queryFeedbackResult(@RequestBody Map<String,Long> map) {
        return surveyScopeVendorService.queryFeedbackResult(map.get("surveyId"));
    }
    //返回图形反馈
    @RequestMapping("/queryFeedbackChartResult")
    public List<SurveyQuestionDTO> queryFeedbackChartResult(@RequestBody Map<String,Long> map) {
        return surveyResultService.queryFeedbackChartResult(map.get("surveyId"));
    }

    //导出统计结果
    @PostMapping("/resultExport")
    public void resultExport(@RequestBody Map<String,Long> map)throws IOException{
        surveyResultService.resultExport(map.get("surveyId"), HttpServletHolder.getResponse());
    }


}
