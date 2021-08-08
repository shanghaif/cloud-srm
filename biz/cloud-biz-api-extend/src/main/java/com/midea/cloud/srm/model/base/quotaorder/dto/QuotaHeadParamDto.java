package com.midea.cloud.srm.model.base.quotaorder.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */

@Data
@Accessors(chain = true)
public class QuotaHeadParamDto implements Serializable {
    private static final long serialVersionUID = 2973822018101431927L;
    private Long quotaHeadId;
    private Date requirementDate;

}
