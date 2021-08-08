package com.midea.cloud.srm.model.base.questionairesurvey.dto;

import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author liuzh163@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/4/25 11:05
 *  修改内容:
 * </pre>
 */
@Data
public class VendorFileDto extends BaseEntity {
    private String surveyId;
    private String fileName;
    private Long fileUploadId;
    private String vendorName;
    private String vendorCode;
    private Date uploadDate;
}
