package com.midea.cloud.srm.model.pm.pr.requirement.dto;

import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/9/14 13:45
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class RequirementLineUpdateDTO extends RequirementLine {
    private BigDecimal thisUpdateNum;

}
