package com.midea.cloud.srm.model.base.questionairesurvey.dto;

import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyResult;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author liuzh163@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/4/17 1:30
 *  修改内容:
 * </pre>
 */
@Data
public class EmployeeResultDto extends BaseDTO {

    /**
     * 员工范围ID
     */
    private Long scopeId;

    /**
     * 问卷ID
     */
    private Long surveyId;

    /**
     * 岗位范围ID
     */
    private Long jobScopeId;

    /**
     * 供应商范围ID
     */
    private String vendorScopeId;

    /**
     * 人员ID
     */
    private String employeeCode;

    /**
     * 员工全名
     */
    private String employeeName;

    /**
     * 员工岗位
     */
    private String employeeJob;
    /**
     * 问题ID
     */
    private String questionId;

    /**
     * 创建人ID
     */
    private Long createdId;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 最后更新人ID
     */
    private Long lastUpdatedId;
    /**
     * 最后更新人
     */
    private String lastUpdatedBy;
    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;
    /**
     * 版本号
     */
    private Long version;

    private List<SurveyResultDto> surveyResultDtoList;

}
