package com.midea.cloud.srm.model.cm.accept.dto;

import com.midea.cloud.srm.model.cm.accept.entity.DetailLine;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class DetailLineDTO extends DetailLine{
    private LocalDate startProductionDate;
    private LocalDate endProductionDate;
}
