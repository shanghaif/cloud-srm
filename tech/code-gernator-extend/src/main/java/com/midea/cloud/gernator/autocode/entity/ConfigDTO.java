package com.midea.cloud.gernator.autocode.entity;

import java.io.Serializable;

public class ConfigDTO implements Serializable {

    public String modelName;//模块名
	public String tableName;//表名
	public String tableComment;//表注释
    public String pk;//主键
	public String entity;//实体类名
    public String dto;//dto对象名称
    public String author;//功能作者

    public String isNeedExcelImport;//是否需要导入功能 是为Y，否为N
    public String isNeedExcelExport;//是否需要导出功能 是为Y，否为N
    public String isNeedCommonFile;//是否需要统一附件 是为Y，否为N

    public String isReplaceExsitedFile;//是否替换已有文件 是为Y，否为N

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getDto() {
        return dto;
    }

    public void setDto(String dto) {
        this.dto = dto;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsNeedExcelImport() {
        return isNeedExcelImport;
    }

    public void setIsNeedExcelImport(String isNeedExcelImport) {
        this.isNeedExcelImport = isNeedExcelImport;
    }

    public String getIsNeedExcelExport() {
        return isNeedExcelExport;
    }

    public void setIsNeedExcelExport(String isNeedExcelExport) {
        this.isNeedExcelExport = isNeedExcelExport;
    }

    public String getIsNeedCommonFile() {
        return isNeedCommonFile;
    }

    public void setIsNeedCommonFile(String isNeedCommonFile) {
        this.isNeedCommonFile = isNeedCommonFile;
    }

    public String getIsReplaceExsitedFile() {
        return isReplaceExsitedFile;
    }

    public void setIsReplaceExsitedFile(String isReplaceExsitedFile) {
        this.isReplaceExsitedFile = isReplaceExsitedFile;
    }
}
