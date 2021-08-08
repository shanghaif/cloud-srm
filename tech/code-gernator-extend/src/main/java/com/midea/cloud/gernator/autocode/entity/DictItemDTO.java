package com.midea.cloud.gernator.autocode.entity;

import java.io.Serializable;

public class DictItemDTO implements Serializable {

	private static final long serialVersionUID = -4698489083374382541L;
	public String javaCode;//java字段
    public String dictItemCode;//数字字典编码
    public String dictItemCodeCamel;//数字字典编码（驼峰形式）
    public String dictItemName;//数字字典名称
    public Long dictId;//字典ID
    public String dictItemValue;//数字字典值

    public String getJavaCode() {
        return javaCode;
    }

    public void setJavaCode(String javaCode) {
        this.javaCode = javaCode;
    }

    public String getDictItemCode() {
        return dictItemCode;
    }

    public void setDictItemCode(String dictItemCode) {
        this.dictItemCode = dictItemCode;
    }

    public String getDictItemCodeCamel() {
        return dictItemCodeCamel;
    }

    public void setDictItemCodeCamel(String dictItemCodeCamel) {
        this.dictItemCodeCamel = dictItemCodeCamel;
    }

    public String getDictItemName() {
        return dictItemName;
    }

    public void setDictItemName(String dictItemName) {
        this.dictItemName = dictItemName;
    }

	public Long getDictId() {
		return dictId;
	}

	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	public String getDictItemValue() {
		return dictItemValue;
	}

	public void setDictItemValue(String dictItemValue) {
		this.dictItemValue = dictItemValue;
	}
}
