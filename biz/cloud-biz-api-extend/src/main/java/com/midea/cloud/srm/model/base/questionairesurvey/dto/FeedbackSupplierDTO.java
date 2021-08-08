package com.midea.cloud.srm.model.base.questionairesurvey.dto;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class FeedbackSupplierDTO extends BaseEntity {
    private Long surveyId;
    private String surveyTitle;
    private String vendorCode;
    private String vendorName;
    private String resultFlag;
    private Date feedbackTime;
}
