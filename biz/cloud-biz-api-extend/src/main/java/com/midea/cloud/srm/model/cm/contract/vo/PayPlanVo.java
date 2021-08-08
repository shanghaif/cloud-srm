package com.midea.cloud.srm.model.cm.contract.vo;

import com.midea.cloud.srm.model.cm.contract.entity.PayPlan;
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
 *  修改日期: 2020/9/1 11:00
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class PayPlanVo extends PayPlan {
    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 未付金额
     */
    private BigDecimal unpaidAmount;
}
