package com.midea.cloud.srm.base.questionairesurvey.controller;

import com.midea.cloud.srm.base.questionairesurvey.service.SurveyResultService;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.SurveyResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
 *  修改日期: 2021/4/20 19:29
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/basesupplier/surveyresultsupplier")
public class SurveyResultSupplierController {
    @Autowired
    private SurveyResultService surveyResultService;
    /**
     * 保存问卷填写内容
     * @param surveyResultDtoList
     */
    @PostMapping("/save")
    public void save(@RequestBody List<SurveyResultDto> surveyResultDtoList){
        surveyResultService.saveQuestionnaireInfo(surveyResultDtoList);
    }
}
