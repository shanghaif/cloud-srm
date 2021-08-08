package com.midea.cloud.common.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * <pre>
 * 异常信息获取工具类
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
@Slf4j
public class ExceptionUtil {
    /**
     * 提取报错信息
     * @param e
     * @return
     */
    public static String getErrorMsg(Throwable e){
        String errorMsg = "";
        try {
            // 堆栈错误信息
            String stackTrace = Arrays.toString(e.getStackTrace());
            // 错误信息
            String message = e.getMessage();
            LinkedHashMap<String, String> errorMsgMap = new LinkedHashMap<>();
            errorMsgMap.put("errorMsg", e.getClass().getName() + "=>" + message);
            errorMsgMap.put("stackTrace", stackTrace);
            errorMsg = JSON.toJSONString(errorMsgMap);
        } catch (Exception ex) {
            log.error("提取报错信息异常=>"+ex.getMessage());
            log.error("提取报错信息异常=>"+ex);
            errorMsg = "提取报错信息异常,请查看日志信息";
        }
        return errorMsg;
    }
}
