package com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.Penalty;
import lombok.Data;

/**
 * <pre>
 *  未结算数量账单罚扣款明细 数据请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/5/6 13:40
 *  修改内容:
 * </pre>
 */
@Data
public class UnsettledPenaltyDTO extends Penalty {
    /**
     * 主键ID
     */
    @TableId("UNSETTLED_PENALTY_ID")
    private Long unsettledPenaltyId;

    /**
     * 未结算数量账单ID
     */
    private Long unsettledOrderId;
}
