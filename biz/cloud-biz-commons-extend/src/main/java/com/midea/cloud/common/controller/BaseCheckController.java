package com.midea.cloud.common.controller;

import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.LogUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.slf4j.Logger;
import org.springframework.validation.BindingResult;

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
 *  修改日期: 2020/8/27 20:57
 *  修改内容:
 * </pre>
 */

public abstract class BaseCheckController extends BaseController {
    protected void checkParamBeforeHandle(Logger log, BindingResult result, Object dto) {
        if (result.hasFieldErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            log.error(LogUtil.getCurrentLogInfo(
                    String.format("错误原因:%s,参数:%s", errorMessage, dto)
            ));
            throw new BaseException(ResultCode.PARAM_VALID_ERROR.getCode(), errorMessage);
        }
    }
}
