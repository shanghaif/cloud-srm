package com.midea.cloud.gernator.autocode.entity;


import java.io.Serializable;

public class TableDefDTO implements Serializable {

    public String code;//暂时界面没有
    public String javaCode;//后端字段java字段
    public String dbCode;//数据库字段

    public String  name;//表格头中文名称
    public String javaType;//java字段字段类型       暂时界面没有用
    public String filedType;//数据库字段类型

    public String filedIfnull;//数据库是否为空
    public String isPk;//主键标识

    public String isShowOnGrid;//是否显示在表格列表 0 否  1  是
    public String isQueryCondition;//是否在查询条件  0 否  1  是
    public String matchQueryCondition;//匹配方式（匹配查询条件） 0 精确  1 模糊

    public String isShowOnForm;//是否在添加或者修改表单显示 0 否  1  是

    public String dictItemCode;//字段code，表格用到，表单下拉用
    public String dictItemName;//暂时没有用
    public String dictItemCodeCamel;//暂时没有用

    public String isRequired;//是否必填

    public String componentType;//  date:日期控件    downlaod:可以下载控件

    private String filedDesc; //数据库字段描述

    private String partName;//分区名称


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getJavaCode() {
        return javaCode;
    }

    public void setJavaCode(String javaCode) {
        this.javaCode = javaCode;
    }

    public String getDbCode() {
        return dbCode;
    }

    public void setDbCode(String dbCode) {
        this.dbCode = dbCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFiledType() {
        return filedType;
    }

    public void setFiledType(String filedType) {
        this.filedType = filedType;
    }

    public String getFiledIfnull() {
        return filedIfnull;
    }

    public void setFiledIfnull(String filedIfnull) {
        this.filedIfnull = filedIfnull;
    }

    public String getIsPk() {
        return isPk;
    }

    public void setIsPk(String isPk) {
        this.isPk = isPk;
    }

    public String getIsShowOnGrid() {
        return isShowOnGrid;
    }

    public void setIsShowOnGrid(String isShowOnGrid) {
        this.isShowOnGrid = isShowOnGrid;
    }

    public String getIsQueryCondition() {
        return isQueryCondition;
    }

    public void setIsQueryCondition(String isQueryCondition) {
        this.isQueryCondition = isQueryCondition;
    }

    public String getMatchQueryCondition() {
        return matchQueryCondition;
    }

    public void setMatchQueryCondition(String matchQueryCondition) {
        this.matchQueryCondition = matchQueryCondition;
    }

    public String getIsShowOnForm() {
        return isShowOnForm;
    }

    public void setIsShowOnForm(String isShowOnForm) {
        this.isShowOnForm = isShowOnForm;
    }

    public String getDictItemCode() {
        return dictItemCode;
    }

    public void setDictItemCode(String dictItemCode) {
        this.dictItemCode = dictItemCode;
    }

    public String getDictItemName() {
        return dictItemName;
    }

    public void setDictItemName(String dictItemName) {
        this.dictItemName = dictItemName;
    }

    public String getDictItemCodeCamel() {
        return dictItemCodeCamel;
    }

    public void setDictItemCodeCamel(String dictItemCodeCamel) {
        this.dictItemCodeCamel = dictItemCodeCamel;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getFiledDesc() {
        return filedDesc;
    }

    public void setFiledDesc(String filedDesc) {
        this.filedDesc = filedDesc;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
}
