package com.midea.cloud.gernator.autocode.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CodeVo implements Serializable {
    //模块名称
    private String moduleName;
    //是否头行 0否,1 是
    private int isMultipleTables;
    //选择表名称
    private List<Table> tableList;

    //是否有导出 0否,1 是
    private int isExport;

    //是否有导入 0否,1 是
    private int isImport;

    //是否有增加 0否,1 是
    private int isAdd;

    //是否有编辑 0否,1 是
    private int isEditor;

    //是否有删除 0否,1 是
    private int isDeleted;

    //增加或者删除模式  值有pop 弹框  tap  标签  append 追加
    private String addOrEditorMode;

    //选择的头字段集合
    private List<TableDefDTO> selectHeadFileList;

    //选择的行子段集合
    private List<TableDefDTO> selectLineFileList;

    //自定义包名称
    private String packageName;

    //生成类型 1 接口   2 是功能界面
    private int gernateType;
    //作者 邮件格式
    private String author;
    //功能描述
    private String functionDesc;
    //前端tab名称
    private String pageName;
    //路由网关映射名称
    private String applicationName ;



    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getIsMultipleTables() {
        return isMultipleTables;
    }

    public void setIsMultipleTables(int isMultipleTables) {
        this.isMultipleTables = isMultipleTables;
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public void setTableList(List<Table> tableList) {
        this.tableList = tableList;
    }

    public int getIsExport() {
        return isExport;
    }

    public void setIsExport(int isExport) {
        this.isExport = isExport;
    }

    public int getIsImport() {
        return isImport;
    }

    public void setIsImport(int isImport) {
        this.isImport = isImport;
    }

    public int getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(int isAdd) {
        this.isAdd = isAdd;
    }

    public int getIsEditor() {
        return isEditor;
    }

    public void setIsEditor(int isEditor) {
        this.isEditor = isEditor;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getAddOrEditorMode() {
        return addOrEditorMode;
    }

    public void setAddOrEditorMode(String addOrEditorMode) {
        this.addOrEditorMode = addOrEditorMode;
    }

    public List<TableDefDTO> getSelectHeadFileList() {
        return selectHeadFileList;
    }

    public void setSelectHeadFileList(List<TableDefDTO> selectHeadFileList) {
        this.selectHeadFileList = selectHeadFileList;
    }

    public List<TableDefDTO> getSelectLineFileList() {
        return selectLineFileList;
    }

    public void setSelectLineFileList(List<TableDefDTO> selectLineFileList) {
        this.selectLineFileList = selectLineFileList;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getGernateType() {
        return gernateType;
    }

    public void setGernateType(int gernateType) {
        this.gernateType = gernateType;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFunctionDesc() {
        return functionDesc;
    }

    public void setFunctionDesc(String functionDesc) {
        this.functionDesc = functionDesc;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}