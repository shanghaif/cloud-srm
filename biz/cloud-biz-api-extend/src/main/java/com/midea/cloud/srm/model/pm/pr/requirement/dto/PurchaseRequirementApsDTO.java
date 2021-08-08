package com.midea.cloud.srm.model.pm.pr.requirement.dto;

import java.util.List;

import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 投标控制页 视图对象
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-13 10:25
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class PurchaseRequirementApsDTO extends RequirementHead {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<RequirementLine> requirementLineList;//采购需求头列表
	
	private String ceeaEmpNo;//申请人
	 
}
