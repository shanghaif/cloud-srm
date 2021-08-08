package com.midea.cloud.srm.sup.register.controller;

import com.midea.cloud.common.utils.SessionUtil;
import com.midea.cloud.component.filter.HttpServletHolder;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.rbac.user.dto.UserDTO;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.sup.register.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 *  供应商注册功能名称
 * </pre>
 *
 * @author zhuwl7@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-9 10:38
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/register")
public class RegisterController extends BaseController {

    @Autowired
    private IRegisterService iRegisterService;
    /**
     * 供应商验证获取
     * @return
     */
    @GetMapping("/getVerifyCode")
    public void getVerificationCode() throws IOException {
        iRegisterService.getVerificationCode(HttpServletHolder.getResponse(), HttpServletHolder.getRequest());
    }

    /**
     * 首页登录页图形验证码获取
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/getHomeVerifyCode")
    public void getHomeVerifyCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        iRegisterService.getVerificationCode(response, request, "home");
    }

    /**
     * 校验验证码
     * @param verifyCode
     */
    @PostMapping("/checkVerifyCode")
    public void checkVerifyCode(String verifyCode)  {
         iRegisterService.checkVerifyCode(verifyCode, HttpServletHolder.getRequest());
    }


    /**
     * 注册
     * @param user
     */
    @PostMapping("/registerAccount")
    public void registerAccount(@RequestBody UserDTO user)  {
        iRegisterService.registerAccount(user,SessionUtil.getRequest());
    }


    /**
     * 校验注册账号是否重复
     * @param username
     */
    @PostMapping("/checkUserName")
    public void checkUserName(String username)  {
         iRegisterService.checkUserName(username);
    }

    /**
     *发验证码到邮箱 (ceea)
     * @param user
     */
    @PostMapping("/sendVerifyCodeToEmail")
    public void sendVerifyCodeToEmail(@RequestBody UserDTO user) {
        iRegisterService.sendVerifyCodeToEmail(user);
    }

    /**
     * 通过用户名校验验证码(ceea)
     * @param verifyCode
     */
    @PostMapping("/checkVerifyCodeByUsername")
    public void checkVerifyCodeByUsername(String verifyCode, String username)  {
        iRegisterService.checkVerifyCodeByUsername(verifyCode, username);
    }

    /**
     *发验证码到邮箱 用于供应商注册是
     * @param email
     */
    @GetMapping("/sendVerifyCodeToEmailNew")
    public void sendVerifyCodeToEmailNew(@RequestParam("email") String email) {
        iRegisterService.sendVerifyCodeToEmailNew(email);
    }

    /**
     * 通过邮箱校验验证码(ceea)
     * @param verifyCode
     */
    @GetMapping("/checkVerifyCodeByEmail")
    public void checkVerifyCodeByEmail(@RequestParam("verifyCode") String verifyCode,@RequestParam("email") String email)  {
        iRegisterService.checkVerifyCodeByEmail(verifyCode, email);
    }

    /**
     * 判断供应商注册链接是否有效
     * @param verifyCode
     */
    @GetMapping("/isValidLink")
    public Boolean isValidLink(@RequestParam("verifyCode") String verifyCode)  {
        return iRegisterService.isValidLink(verifyCode);
    }
}
