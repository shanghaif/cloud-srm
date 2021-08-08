package com.midea.cloud.gateway.controller;

import com.midea.cloud.common.constants.SysConstant;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <pre>
 *  多语言控制器
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-2 15:24
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/locale")
public class LocaleController {

    @GetMapping("/modify")
    public BaseResult setLocale(String locale, HttpSession session) {
        Assert.notNull(locale, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        session.setAttribute(SysConstant.LOCALE_SESSION_ATTR_NAME, locale);
        return BaseResult.buildSuccess();
    }

    @GetMapping("/get")
    public String getLocale(HttpSession session) {
        Object locale = session.getAttribute(SysConstant.LOCALE_SESSION_ATTR_NAME);
        if (StringUtils.isEmpty(locale)) {
            return SysConstant.DEFAULT_LOCALE;
        }
        return String.valueOf(locale);
    }

}
