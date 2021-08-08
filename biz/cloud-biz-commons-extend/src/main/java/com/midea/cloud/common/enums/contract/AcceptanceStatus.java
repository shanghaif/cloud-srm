package com.midea.cloud.common.enums.contract;

import com.midea.cloud.common.enums.inq.QuotaStatus;

/**
 * <pre>
 *  功能名称描述:
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-6-3 15:07
 *  修改内容:
 * </pre>
 */
public enum AcceptanceStatus {

    /**
     * 拟定
     */
    DRAFT,

    /**
     * 审批中
     */
    UNDER_REVIEW,

    /**
     * 已驳回
     */
    REJECTED,

    /**
     * 已通过
     */
    APPROVED,
    /**
     * 已废弃
     */
    ABANDONED,
    /**
     * 已撤回
     */
    WITHDRAW;
    ;

    public static String[] getString() {
        int length = AcceptanceStatus.values().length;
        String[] str=new String[length];
        for(int i=0;i<length;i++){
            str[i]=AcceptanceStatus.values()[i].toString();
        }
        return str;
    }

}