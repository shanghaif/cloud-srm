package com.midea.cloud.srm.model.api.aps;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PiNotifyDatasForSrmDTO implements Serializable {
    private String plantCode;
    private List<PiNotifyForSrmDTO> notifyForSrmDtoS;
}
