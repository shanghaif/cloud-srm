package com.midea.cloud.srm.base.formula.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePriceParam {
        private Long formulaId;
        private Long sourcingId;
        private String sourcingType;
        private Long vendorId;
    }