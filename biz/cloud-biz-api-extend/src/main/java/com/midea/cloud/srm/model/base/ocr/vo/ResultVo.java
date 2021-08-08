package com.midea.cloud.srm.model.base.ocr.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class ResultVo  implements Serializable {
   private String code;
   private String msg;
   private OcrResultVo data;


}
