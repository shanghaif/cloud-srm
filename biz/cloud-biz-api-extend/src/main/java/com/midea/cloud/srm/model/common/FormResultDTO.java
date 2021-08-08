package com.midea.cloud.srm.model.common;

import lombok.Data;

/**
 * <pre>
 *  功能名称描述: 添加或修改单据时,结果返回DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-27 16:04
 *  修改内容:
 * </pre>
 */
@Data
public class FormResultDTO {

    /**
     * 表单ID
     */
    private Long formId;

    /**
     * 是否启用工作流
     */
    private String enableWorkFlow;

    /**
     * 功能ID
     */
    private Long functionId;


}
