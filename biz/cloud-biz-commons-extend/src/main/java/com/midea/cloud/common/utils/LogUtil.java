package com.midea.cloud.common.utils;

import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.srm.model.log.biz.entity.BizOperateLogInfo;
import com.midea.cloud.srm.model.log.biz.holder.BizDocumentInfoHolder;

import java.util.Objects;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/27 11:09
 *  修改内容:
 * </pre>
 */
public class LogUtil {
    private LogUtil() {

    }

    public static String getCurrentLogInfo(String message) {
        StringBuilder builder = new StringBuilder();
        builder.append("currentThread:").append(Thread.currentThread())
                .append(",message:").append(message);
        BizOperateLogInfo operateLogInfo = BizDocumentInfoHolder.get();
        if (Objects.nonNull(operateLogInfo)) {
            String username = operateLogInfo.getUsername();
            if (Objects.nonNull(username)) {
                builder.append(",user:").append(username);
            }
            String userType = operateLogInfo.getUserType();
            if (Objects.nonNull(userType)) {
                builder.append(",userType:").append(userType);
            }
        }
        return builder.toString();
    }

    public static String getCurrentLogInfo(ResultCode code) {
        return getCurrentLogInfo(code.getMessage());
    }
}
