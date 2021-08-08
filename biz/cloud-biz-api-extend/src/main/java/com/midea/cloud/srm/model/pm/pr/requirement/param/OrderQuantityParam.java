package com.midea.cloud.srm.model.pm.pr.requirement.param;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 *  维护可下单数量入参
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-9 16:17
 *  修改内容:
 * </pre>
 */
@Data
public class OrderQuantityParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long requirementLineId;//采购申请行ID
    private Integer rowNum;//申请行号
    private BigDecimal revertAmount;//更改的数量,增加为正数,减少为负数

}