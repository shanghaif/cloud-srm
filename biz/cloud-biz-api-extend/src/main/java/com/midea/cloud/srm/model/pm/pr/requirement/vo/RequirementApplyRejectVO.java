package com.midea.cloud.srm.model.pm.pr.requirement.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <pre>
 *  采购需求拒绝  视图对象
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-20 14:32
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class RequirementApplyRejectVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long requirementHeadId;//采购需求头ID
    private String rejectReason;//拒绝原因

}
