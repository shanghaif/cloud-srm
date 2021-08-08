package com.midea.cloud.srm.model.flow.process.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * <pre>
 * 审批流程接口参数实体类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/16 19:57
 *  修改内容:
 * </pre>
 */
@Data
public class ApproveProcessDTO extends BaseDTO {
    private static final long serialVersionUID = 2916400243629816070L;

    /**业务系统模块ID*/
//    private String fdModuleId;

    /**流程实例ID */
    private String fdId;
    /**流程模板id */
    private String fdTemplateId;
    /**流程模板编码 */
    private String fdTemplateCode;
    /**用户登录名 */
    private String loginName;
    /**流程参数Map */
    private Map<String, Object> processParam;
    /**表单参数Map */
    private Map<String, Object> formParam;

    /**扩展参数 */
    private String extend;
    /**审批意见的附件id */
    private String auditFileDocIdList;
}
