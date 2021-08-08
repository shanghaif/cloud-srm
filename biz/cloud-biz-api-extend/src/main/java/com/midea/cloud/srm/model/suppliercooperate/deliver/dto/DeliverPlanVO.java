package com.midea.cloud.srm.model.suppliercooperate.deliver.dto;


import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class DeliverPlanVO {
    private Long id;
    private String dates;

}
