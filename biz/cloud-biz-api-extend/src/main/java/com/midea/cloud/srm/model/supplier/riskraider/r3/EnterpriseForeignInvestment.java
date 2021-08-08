/**
  * Copyright 2021 json.cn 
  */
package com.midea.cloud.srm.model.supplier.riskraider.r3;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Auto-generated: 2021-01-21 16:53:7
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class EnterpriseForeignInvestment {

    private String enterpriseName;
    private String unifiedSocialCreditCode;
    private String legalRepresentative;
    private String enterpriseType;
    private String registrationInstitution;
    private String operateStatus;
    private String registeredCapital;
    private String registeredCapitalCurrency;
    private String realityCapital;
    private String realityCapitalCurrency;
    private String investmentForm;
    private String investmentRatio;
    private String establishmentDate;

}