package com.midea.cloud.srm.model.logistics.expense.dto;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class ChargeCodeDto extends BaseDTO {
    /**
     * 费用项(字典编码CHARGE_NAME)
     */
    @TableField("CHARGE_CODE")
    private String chargeCode;

    /**
     * 费用项名称(字典编码CHARGE_NAME)
     */
    @TableField("CHARGE_NAME")
    private String chargeName;
}
