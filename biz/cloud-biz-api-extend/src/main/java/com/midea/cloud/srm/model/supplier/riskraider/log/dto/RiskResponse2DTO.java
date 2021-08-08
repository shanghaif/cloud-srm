package com.midea.cloud.srm.model.supplier.riskraider.log.dto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @Author vent
 * @Description
 **/
@Data
public class RiskResponse2DTO {
    private String resultCode;
    private String resultMsg;
    private Object resultData;
    private JSONObject interfaceInfo;
}
