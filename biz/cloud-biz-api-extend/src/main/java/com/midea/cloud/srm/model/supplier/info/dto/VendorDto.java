package com.midea.cloud.srm.model.supplier.info.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

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
public class VendorDto extends BaseDTO {

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 企业CODE
     */
    private String companyCode;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 供应商分类
     */
    private String companyType;
}
