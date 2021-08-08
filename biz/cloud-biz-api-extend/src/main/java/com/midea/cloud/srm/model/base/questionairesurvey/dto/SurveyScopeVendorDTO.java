package com.midea.cloud.srm.model.base.questionairesurvey.dto;

import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyScopeVendor;
import lombok.Data;

import java.util.List;

@Data
public class SurveyScopeVendorDTO {

    private List<SurveyScopeVendor> surveyScopeVendorList;
    private Long surveyId;
}
