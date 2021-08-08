package com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry;

import lombok.Data;

/**
 * <pre>
 *  罚扣款单表
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/7 15:24
 *  修改内容:
 * </pre>
 */
@Data
public class PenaltyRequestDTO extends Penalty {

    /**
     * 开始罚扣款日期
     */
    private String startPenaltyTime;

    /**
     * 截至罚扣款日期
     */
    private String endPenaltyTime;

}
