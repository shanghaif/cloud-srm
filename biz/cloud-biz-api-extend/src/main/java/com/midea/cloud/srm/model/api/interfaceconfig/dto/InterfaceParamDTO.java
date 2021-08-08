package com.midea.cloud.srm.model.api.interfaceconfig.dto;

import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceParam;

import lombok.Data;

@Data
public class InterfaceParamDTO extends InterfaceParam {

    private Object value;
}
