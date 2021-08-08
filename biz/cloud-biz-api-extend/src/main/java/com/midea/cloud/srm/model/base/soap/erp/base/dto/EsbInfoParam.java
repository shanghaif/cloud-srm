
package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Description 隆基Idm入参标注格式
 * @Param
 * @return
 * @Author wuwl18@meicloud.com
 * @Date 2020.08.12
 * @throws
 **/
@Data
public class EsbInfoParam {

    private String instId;
    private String requestTime;
    private String attr1;
    private String attr2;
    private String attr3;

}

