package com.midea.cloud.srm.model.pm.pr.requirement.param;

import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  创建寻源单据入参
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-23 16:07
 *  修改内容:
 * </pre>
 */
@Data
public class SourceBusinessGenParam {

    private List<RequirementLine> requirementLineList;//采购需求行
    private String businessGenType;//创建的单据类型

}