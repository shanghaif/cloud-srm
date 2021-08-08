package com.midea.cloud.common.enums;

/**
 * <pre>
 *  功能名称描述: 供应商控制单据控制类型 参考字典码:SUPPLIER_CONTROL_TYPE
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-24 14:08
 *  修改内容:
 * </pre>
 */
public enum SupplierControlType {
    ORG_FORZEN,         //组织冻结
    ORG_UNFORZEN,       //组织解冻
    ORG_INVALID,        //组织失效
    CAT_SUSPEND,        //品类暂停
    CAT_RENEW,          //品类恢复
    CAT_INVALID;        //品类终止
}
