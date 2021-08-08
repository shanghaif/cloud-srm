package com.midea.cloud.srm.model.inq.price.dto;

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
public class PriceLibraryParamDto implements Serializable {
    private static final long serialVersionUID = -4307992994777076948L;
    private Long vendorId;
    private Long itemId;
    private Long orgId;
    private Date requirementDate;
}
