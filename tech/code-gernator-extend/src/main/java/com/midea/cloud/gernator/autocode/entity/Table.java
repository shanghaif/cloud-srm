package com.midea.cloud.gernator.autocode.entity;

import java.io.Serializable;

public  class Table implements Serializable {
    //表名称
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}