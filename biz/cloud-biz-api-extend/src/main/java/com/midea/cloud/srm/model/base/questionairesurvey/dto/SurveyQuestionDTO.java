package com.midea.cloud.srm.model.base.questionairesurvey.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyQuestion;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveySelection;
import lombok.Data;

import java.util.List;

@Data
public class SurveyQuestionDTO {

    private SurveyQuestion surveyQuestion;
    private List<String> jobList;
    private List<SurveySelection> surveySelectionList;
}
