package com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto;

import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.AccountsPayable;
import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ShouldPayOpenBill;
import lombok.Data;


/**
 * <pre>
 *  应付未开票 数据请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/3 17:50
 *  修改内容:
 * </pre>
 */
@Data
public class ShouldPayOpenBillRequestDTO extends ShouldPayOpenBill {

    /**
     * 起始业务日期
     */
    private String startBusinessDate;

    /**
     * 截止业务日期
     */
    private String endBusinessDate;
}
