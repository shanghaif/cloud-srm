package com.midea.cloud.srm.model.api.aps;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResultDTO<T> implements Serializable  {
    private Integer code;
    private String msg;
    private T data;
    private Object extra;
}
