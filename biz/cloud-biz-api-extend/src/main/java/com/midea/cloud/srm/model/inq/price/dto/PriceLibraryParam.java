package com.midea.cloud.srm.model.inq.price.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

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
 *  修改日期: 2020/8/27
 *  修改内容:
 * </pre>
 */

@Data
public class PriceLibraryParam extends BaseDTO {
    /**
     * 寻源单号
     */
    private String sourceNo;

    /**
     * 采购组织id
     */
    private Long organizationId;

    /**
     * 供应商id
     */
    private Long vendorId;
}
