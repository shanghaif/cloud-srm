package com.midea.cloud.srm.model.base.questionairesurvey.dto;

import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class VendorDTO extends BaseEntity {
    private String vendorName;
    private Long surveyId;
    private String vendorCode;
    private String supBusinessType; //供应商业务类型
    private List<String> vendorCodes;
}
