package com.midea.cloud.srm.model.logistics.bid.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 计费方式组合
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
public class BillingCombination extends BaseDTO {
    /**
     * 计费单位编码
     */
    private String chargeUnit;

    /**
     * 计费单位名称
     */
    private String chargeUnitName;
}
