package com.midea.cloud.srm.model.perf.vendorasses.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.perf.vendorasses.VendorAssesForm;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class VendorAssesFormOV extends VendorAssesForm {
    /**
     * 税额
     */
    @TableField("TAX")
    private BigDecimal tax;
}
