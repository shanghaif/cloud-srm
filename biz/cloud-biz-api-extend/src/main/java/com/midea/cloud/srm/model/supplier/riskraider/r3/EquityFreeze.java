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
public class EquityFreeze {

    private String personSubjectToEnforcement;
    private String amount;
    private String idTypeOfPersonSubjectToEnforcement;
    private String idNumOfPersonSubjectToEnforcement;
    private String courtOfExecution;
    private String itemOfExecution;
    private String documentNumber;
    private String documentNumberOfWrittenVerdict;
    private String documentNumberOfNotice;
    private String status;
    private String publicityDate;
    private String beginDateOfFreeze;
    private String endDateOfFreeze;
    private String timeLimitOfFreeze;
    private String details;

}