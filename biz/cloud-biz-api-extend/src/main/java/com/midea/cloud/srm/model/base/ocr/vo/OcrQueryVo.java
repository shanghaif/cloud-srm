package com.midea.cloud.srm.model.base.ocr.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class OcrQueryVo implements Serializable {
    private String appId;
    private String appKey;
    private String signature;
    private byte[] imageData_bizLicense;
    private String ts;
    private String mipId;


}
