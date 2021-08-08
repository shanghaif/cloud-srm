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
public class ShareholderInfo {

    private String shareholderName;
    private String shareholderType;
    private String subscribedCapital;
    private String subscribedTime;
    private String actualContributionTime;
    private String actualPaymentAmount;
    private String shareRatio;

}