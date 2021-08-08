package com.midea.cloud.srm.model.suppliercooperate.invoice.dto;

import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoicePunish;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  功能名称 开票通知扣罚查询DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/15 17:36
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class InvoicePunishQueryDTO extends InvoicePunish {

    private List<Long> invoiceNoticeIds;

    private String isService;//是否服务发票
}
