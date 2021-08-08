package com.midea.cloud.srm.model.flow.process.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 更新流程主题-实体类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/14 11:38
 *  修改内容:
 * </pre>
 */
@Data
public class ChangeProcessSubjectDTO extends BaseDTO {
    private static final long serialVersionUID = -6310453135555873183L;

    /**流程实例ID */
    private String fdId	= "";

    /**用户登录名 */
    private String loginName;

    /**流程实例主题 */
    private String subject;

    /**"流程实例主题(多语言主题)，格式如下:[{""languageType"":""zh-CN"",""subject"":""这是主题""}]" */
    private List<Map<String, Object>> subjectForMultiLanguages;

    /**语言编码(subjectsForMultiLanguage子参数) */
    private String languageType;

    /**主题流程(subjectsForMultiLanguage子参数) */
/*
    private String subject;*/

    /**流程主题的主语言的语言编码 */
    private String mainLanguageType4Subject;

}
