package com.midea.cloud.srm.model.supplier.riskraider.log.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @Author vent
 * @Description
 **/
@Data
public class RiskResponseDTO {
    private String resultCode;
    private String resultMsg;
    private JSONObject resultData;
    private JSONObject interfaceInfo;
}
