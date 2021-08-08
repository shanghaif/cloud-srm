package com.midea.cloud.srm.model.supplier.info.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <pre>
 * 近三年采购金额
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/1
 *  修改内容:
 * </pre>
 */
@Data
public class PurchaseAmountDto extends BaseDTO {
    /**
     * 年度
     */
    private int year;

    /**
     * 金额
     */
    private BigDecimal amount;
}
