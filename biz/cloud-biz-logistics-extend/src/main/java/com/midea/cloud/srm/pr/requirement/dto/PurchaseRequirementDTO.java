package com.midea.cloud.srm.pr.requirement.dto;

import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementAttach;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

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
public class PurchaseRequirementDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private RequirementHead requirementHead;//采购需求头
    private List<RequirementLine> requirementLineList;//采购需求头列表
    private List<RequirementAttach> requirementAttaches;//采购需求附件

    private Long menuId;


}
