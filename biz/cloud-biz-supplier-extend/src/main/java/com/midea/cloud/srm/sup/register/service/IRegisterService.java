package com.midea.cloud.srm.sup.register.service;

import com.midea.cloud.srm.model.rbac.user.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author zhuwl7@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-9 10:48
 *  修改内容:
 * </pre>
 */
public interface IRegisterService {
    /**
     * 获取验证码
     * @param response
     * @param request
     * @throws IOException
     */
    void getVerificationCode(HttpServletResponse response, HttpServletRequest request) throws IOException;

    /**
     * 根据类型获取验证码
     * @param response
     * @param request
     * @param verifyKey
     * @throws IOException
     */
    void getVerificationCode(HttpServletResponse response, HttpServletRequest request, String verifyKey) throws IOException;

    /**
     * 校验验证
     * @param verifyCode
     * @param request
     */
    void checkVerifyCode(String verifyCode, HttpServletRequest request);

    /**
     * 根据类型校验验证
     * @param verifyCode
     * @param verifyKey
     * @param request
     */
    boolean checkVerifyCode(String verifyCode, String verifyKey, HttpServletRequest request);

    /**
     * 校验名字重复
     * @param userName
     */
    void checkUserName(String userName);

    /**
     * 注册账号
     * @param user
     * @param request
     */
    void registerAccount(UserDTO user, HttpServletRequest request);

    /**
     * 发验证码到邮箱
     * @param user
     */
    void sendVerifyCodeToEmail(UserDTO user);

    /**
     * 发验证码到邮箱新, 用于供应商注册 (ceea)
     * @param email
     */
    void sendVerifyCodeToEmailNew(String email);

    /**
     * 通过用户名校验验证码(ceea)
     * @param verifyCode
     * @param username
     */
    void checkVerifyCodeByUsername(String verifyCode, String username);

    /**
     * 通过邮箱校验验证码(ceea)
     * @param verifyCode
     * @param email
     */
    void checkVerifyCodeByEmail(String verifyCode, String email);

    /*****
     * 供应商注册链接是否有效
     * @param verifyCode
     * @return
     */
    Boolean isValidLink(String verifyCode);
}
