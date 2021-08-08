package com.midea.cloud.common.utils;

import com.midea.cloud.srm.model.api.interfacelog.dto.LogWriteBackDto;

/**
 * <pre>
 * 业务返回处理类
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
public interface LogWriteBack<R> {
    /**
     * 处理业务执行返回信息, 获取状态和报错信息
     * @param r
     * @return
     */
    LogWriteBackDto getReturnStatusInfo(R r);
}
