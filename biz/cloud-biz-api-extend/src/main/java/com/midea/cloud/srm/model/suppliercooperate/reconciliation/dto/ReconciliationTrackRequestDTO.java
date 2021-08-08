package com.midea.cloud.srm.model.suppliercooperate.reconciliation.dto;

import com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry.ReconciliationTrack;
import lombok.Data;

/**
 * <pre>
 *  对账单跟踪 数据请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/3 15:33
 *  修改内容:
 * </pre>
 */
@Data
public class ReconciliationTrackRequestDTO extends ReconciliationTrack {

    /**
     * 起始业务日期
     */
    private String startBusinessDate;

    /**
     * 截止业务日期
     */
    private String endBusinessDate;
}
