package com.midea.cloud.common.enums.pm.ps;

/**
 * <pre>
 *  网上开票状态  对应字典码 INVOICE_STATUS
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/4 9:27
 *  修改内容:
 * </pre>
 */
public enum InvoiceStatus {

    DRAFT,//新建
    UNDER_APPROVAL,//审批中  采购商开具无此状态
    APPROVAL,//已审批
    REVIEWED,//已复核
    DROP,//已作废
    REJECTED;//已驳回
}
