package com.midea.cloud.srm.model.flow.process.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.flow.process.entity.TemplateLines;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  回调流程参数DTO
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/21 14::20
 *  修改内容:
 * </pre>
 * */
@Data
public class CbpmCallBackEventParamDTO extends BaseDTO {

    private static final long serialVersionUID = 7759405620004707194L;

    /*** cdpm流程fdTemplateId*/
    private String modelId;

    /*** 模板Id对应菜单表functionId*/
    private Long templateId;

    /*** 模板名称*/
    private String templateName;

    /*** 模板编码*/
    private String templateCode;

    /*** 描述说明*/
    private String description;

    private String pendingApproveUrl;

    private String feignClient;

    private String bussinessClass;

    /*** 语言*/
    private String language;

    /**以下字段用于查询*/
    /**业务类型*/
    private String bussinessType;
    /**业务单据ID*/
    private String businessId;
    /**模板ID*/
    private String businessKey;
    /**事件处理过程（业务方法）*/
    private String bussinessFunction;
}
