package com.midea.cloud.gernator.autocode.entity;


import java.io.Serializable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author yixinyx@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class CreateCodeResultDTO implements Serializable {

    public String serviceFilePath;//service文件路径
    public String serviceImplFilePath; //ServiceImpl文件路径
    public String entityFilePath;//entity对象文件路径
    public String dtoFilePath;  //dto对象文件路径
    public String daoFilePath; //Dao文件路径
    public String daoXmlFilePath; //Dao.xml文件路径
    public String controllerFilePath;  //Controller文件路径
    public String listWFilePath; //list.w文件路径
    public String listWJsFilePath;//listw.js文件路径
    public String detailWFilePath; //detail.w文件路径
    public String detailWJsFilePath; //detail.js文件路径

    public String listWUrl; //list w文件访问路径

    public CreateCodeResultDTO(String serviceFilePath, String serviceImplFilePath, String entityFilePath, String dtoFilePath, String daoFilePath, String daoXmlFilePath, String controllerFilePath, String listWFilePath, String listWJsFilePath, String detailWFilePath, String detailWJsFilePath) {
        this.serviceFilePath = serviceFilePath;
        this.serviceImplFilePath = serviceImplFilePath;
        this.entityFilePath = entityFilePath;
        this.dtoFilePath = dtoFilePath;
        this.daoFilePath = daoFilePath;
        this.daoXmlFilePath = daoXmlFilePath;
        this.controllerFilePath = controllerFilePath;
        this.listWFilePath = listWFilePath;
        this.listWJsFilePath = listWJsFilePath;
        this.detailWFilePath = detailWFilePath;
        this.detailWJsFilePath = detailWJsFilePath;
    }

    public String getServiceFilePath() {
        return serviceFilePath;
    }

    public void setServiceFilePath(String serviceFilePath) {
        this.serviceFilePath = serviceFilePath;
    }

    public String getServiceImplFilePath() {
        return serviceImplFilePath;
    }

    public void setServiceImplFilePath(String serviceImplFilePath) {
        this.serviceImplFilePath = serviceImplFilePath;
    }

    public String getEntityFilePath() {
        return entityFilePath;
    }

    public void setEntityFilePath(String entityFilePath) {
        this.entityFilePath = entityFilePath;
    }

    public String getDtoFilePath() {
        return dtoFilePath;
    }

    public void setDtoFilePath(String dtoFilePath) {
        this.dtoFilePath = dtoFilePath;
    }

    public String getDaoFilePath() {
        return daoFilePath;
    }

    public void setDaoFilePath(String daoFilePath) {
        this.daoFilePath = daoFilePath;
    }

    public String getDaoXmlFilePath() {
        return daoXmlFilePath;
    }

    public void setDaoXmlFilePath(String daoXmlFilePath) {
        this.daoXmlFilePath = daoXmlFilePath;
    }

    public String getControllerFilePath() {
        return controllerFilePath;
    }

    public void setControllerFilePath(String controllerFilePath) {
        this.controllerFilePath = controllerFilePath;
    }

    public String getListWFilePath() {
        return listWFilePath;
    }

    public void setListWFilePath(String listWFilePath) {
        this.listWFilePath = listWFilePath;
    }

    public String getListWJsFilePath() {
        return listWJsFilePath;
    }

    public void setListWJsFilePath(String listWJsFilePath) {
        this.listWJsFilePath = listWJsFilePath;
    }

    public String getDetailWFilePath() {
        return detailWFilePath;
    }

    public void setDetailWFilePath(String detailWFilePath) {
        this.detailWFilePath = detailWFilePath;
    }

    public String getDetailWJsFilePath() {
        return detailWJsFilePath;
    }

    public void setDetailWJsFilePath(String detailWJsFilePath) {
        this.detailWJsFilePath = detailWJsFilePath;
    }

    public String getListWUrl() {
        return listWUrl;
    }

    public void setListWUrl(String listWUrl) {
        this.listWUrl = listWUrl;
    }
}
