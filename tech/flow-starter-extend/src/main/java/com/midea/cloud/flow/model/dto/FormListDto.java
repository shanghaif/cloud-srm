package com.midea.cloud.flow.model.dto;

import java.util.UUID;

/**
 * @author wenjie.liang@meicloud.com
 * @version 1.0.0
 * @date 2020-06-22
 **/
public class FormListDto {

    private final String uuid;

    {
        uuid = UUID.randomUUID().toString();
    }
    private String formId;
    private String formName;
    private String formUrl;
    private String application;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFormUrl() {
        return formUrl;
    }

    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }
}
