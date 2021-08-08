package com.midea.cloud.srm.base.questionairesurvey.controller;

import com.midea.cloud.srm.base.questionairesurvey.service.SurveyQuestionService;
import com.midea.cloud.srm.base.questionairesurvey.service.SurveyScopeVendorService;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.QuestionDetailDto;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.SurveyQuestionSupplierDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.SurveyScopeVendorSupplierDto;
import feign.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author liuzh163@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/4/20 19:31
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/basesupplier/surveyquestionsupplier")
public class SurveyQuestionSupplierController {
    @Autowired
    private SurveyScopeVendorService surveyScopeVendorService;
    @Autowired
    private SurveyQuestionService surveyQuestionService;

    /**
     * 查询问卷调查详细页面
     * @param id 问卷ID
     * @return
     */
    @PostMapping("/questionSurveyInfo")
    public QuestionDetailDto questionSurveyInfo(@RequestParam("id") Long id,@RequestParam("vendorCode")String vendorCode){
        SurveyScopeVendorSupplierDto surveyScopeVendorSupplierDto = surveyScopeVendorService.queryScopeVendorInfo(id,vendorCode);
        List<SurveyQuestionSupplierDTO> surveyQuestionSupplierDTOList = surveyQuestionService.questionSurveyInfo(id,surveyScopeVendorSupplierDto.getVendorScopeId());

        QuestionDetailDto questionDetailDto = new QuestionDetailDto();
        questionDetailDto.setSurveyScopeVendorSupplierDto(surveyScopeVendorSupplierDto);
        questionDetailDto.setSurveyQuestionSupplierDTOList(surveyQuestionSupplierDTOList);
        return questionDetailDto;
    }
}
