package com.midea.cloud.common.enums.sup.risk;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/1
 *  修改内容:
 * </pre>
 */
public enum RiskMonitoringStatus {
    /**
     * 新建
     */
    ADD,

    /**
     * 新建审批中
     */
    APPROVAL_ADD,

    /**
     * 关闭审批中
     */
    APPROVAL_CLOSE,

    /**
     * 监控中
     */
    MONITORING,

    /**
     * 已关闭
     */
    CLOSED;
}
