package com.midea.cloud.srm.model.base.repush.entity;

/**
 * <pre>
 *  推送状态
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-6 14:58
 *  修改内容:
 * </pre>
 */
public enum RepushStatus {

    SUCCESS(0), FAIL(1);
    private Integer status;

    private RepushStatus(Integer status) {
        this.status = status;
    }

}
