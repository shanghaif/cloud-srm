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
public class EquityPledge {

    private String registrationNum;
    private String pledgor;
    private String amount;
    private String pawnee;
    private String registrationDate;
    private String publicityDate;
    private String status;
    private String pledgorKeyNo;
    private String pawneeKeyNo;
    private String relatedEnterpriseName;

}