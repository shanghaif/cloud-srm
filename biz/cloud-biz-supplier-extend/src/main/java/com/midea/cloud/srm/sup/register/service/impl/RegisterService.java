package com.midea.cloud.srm.sup.register.service.impl;

import com.midea.cloud.common.constants.RedisKey;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.VerifyCode;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.rbac.user.dto.UserDTO;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.sup.register.service.IRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
@Service
@Slf4j
public class RegisterService implements IRegisterService {
    @Autowired
    private RbacClient rbacClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private BaseClient baseClient;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void checkVerifyCode(String verifyCode, HttpServletRequest request) {
        String sessionVerifyCode = (String) request.getSession().getAttribute("verifyCode");
//        String sessionVerifyCode = stringRedisTemplate.opsForValue().get(RedisKey.VERIFY_CODE);
        Assert.hasLength(sessionVerifyCode,"验证码报错请重新获取");
        if(StringUtils.isBlank(verifyCode) ||
                !sessionVerifyCode.toLowerCase().equals(verifyCode.toLowerCase())){
            throw new BaseException("验证码不正确");
        }

    }

    @Override
    public boolean checkVerifyCode(String verifyCode, String verifyKey, HttpServletRequest request) {
        String sessionVerifyCode = (String) request.getSession().getAttribute(verifyKey);
//        String sessionVerifyCode = stringRedisTemplate.opsForValue().get(RedisKey.VERIFY_CODE);
        Assert.hasLength(sessionVerifyCode,"验证码报错请重新获取");
        if(StringUtils.isBlank(verifyCode) || StringUtils.isBlank(sessionVerifyCode) ||
                !sessionVerifyCode.toLowerCase().equals(verifyCode.toLowerCase())){
            return false;
        }
        return true;
    }

    @Override
    public void getVerificationCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        getVerificationCode(response, request, "verifyCode");
    }

    @Override
    public void getVerificationCode(HttpServletResponse response, HttpServletRequest request, String verifyKey) throws IOException {
        ServletOutputStream os =  response.getOutputStream();
        int width=120;
        int height=30;
        BufferedImage verifyImg=new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        //生成对应宽高的初始图片
        String randomText = VerifyCode.drawRandomText(width,height,verifyImg);
        request.getSession().setAttribute(verifyKey, randomText);
//        stringRedisTemplate.opsForValue().set(RedisKey.VERIFY_CODE, randomText, 30, TimeUnit.MINUTES);
        // 将图形写给浏览器
        response.setContentType("image/jpeg");
        // 发头控制浏览器不要缓存
        response.setDateHeader("expries", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        // 将图片写给浏览器
        ImageIO.write(verifyImg, "jpg", os);
        os.flush();
        os.close();
    }

    @Override
    public void checkUserName(String username) {
      Assert.hasLength(username,"账号不能为空");
        List<User> users = rbacClient.checkByUsername(username);
        if(!CollectionUtils.isEmpty(users)){
            throw  new BaseException("账号名重复");
        }
    }

    @Override
    public void registerAccount(UserDTO user,  HttpServletRequest request) {
        Assert.notNull(user,"不能传空值");
        //校验验证码
        checkVerifyCodeByEmail(user.getVerifyCode(),user.getEmail());
//        Assert.hasLength(user.getVerifyCode(),"验证码不能为空");
        Assert.hasLength(user.getPassword(),"密码不能为空");
        Assert.hasLength(user.getUsername(),"账号不能为空");
        Assert.hasText(user.getEmail(), "email不能为空");
        Assert.hasText(user.getNickname(), "昵称不能为空");
//        this.checkVerifyCode(user.getVerifyCode(),request);
        this.checkUserName(user.getUsername());
        User registUser = new User();
        registUser.setPassword(user.getPassword());
        registUser.setUsername(user.getUsername());
        registUser.setNickname(user.getNickname());
        registUser.setEmail(user.getEmail());
        registUser.setCeeaEmpNo(user.getUsername());
        rbacClient.registerVendor(registUser);

    }

    @Override
    @Transactional
    public void sendVerifyCodeToEmail(UserDTO user) {
        String username = user.getUsername();
        String email = user.getEmail();
        Assert.hasText(username, LocaleHandler.getLocaleMsg("账号不能为空"));
        Assert.hasText(email, LocaleHandler.getLocaleMsg("邮箱不能为空"));
        //生成验证码
        int width=120;
        int height=30;
        BufferedImage verifyImg=new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        String randomText = VerifyCode.drawRandomText(width,height,verifyImg);
        if (stringRedisTemplate.hasKey(RedisKey.VERIFY_CODE + username)) {
            throw new BaseException(LocaleHandler.getLocaleMsg("验证码已发至对应邮箱,请检查!"));
        } else {
            try {
                //发送验证码
                baseClient.sendEmail(email, "【隆基集团 N-SRM】验证码发送成功! ", String.format("<p>您的验证码发送成功，请尽快注册!。</p>" +
                        "<p>您的账号：%s</p>" +
                        "<p>您的验证码：%s</p>", username, randomText));
            } catch (Exception e) {
                e.printStackTrace();
                log.error("操作失败",e);
                throw new BaseException(LocaleHandler.getLocaleMsg("邮箱发送失败,请联系系统管理员!"));
            }
            //存储验证码
            stringRedisTemplate.opsForValue().set(RedisKey.VERIFY_CODE + username, randomText, 60, TimeUnit.SECONDS);
        }
    }

    @Override
    public void sendVerifyCodeToEmailNew(String email) {
        // 邮箱
        Assert.hasText(email, LocaleHandler.getLocaleMsg("邮箱不能为空"));

        //生成验证码
        int width=120;
        int height=30;
        BufferedImage verifyImg=new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        String randomText = VerifyCode.drawRandomText(width,height,verifyImg);

        //
        if (redisUtil.exists(RedisKey.VERIFY_CODE + email)) {
            throw new BaseException(LocaleHandler.getLocaleMsg("验证码已发至对应邮箱,请检查!"));
        } else {
            try {
                //发送验证码
                baseClient.sendEmail(email, "【隆基集团 N-SRM】验证码发送成功! ", String.format("<p>您的验证码发送成功，请尽快注册!。</p>" +
                        "<p>您的验证码：%s</p>", randomText));
            } catch (Exception e) {
                e.printStackTrace();
                log.error("操作失败",e);
                throw new BaseException(LocaleHandler.getLocaleMsg("邮箱发送失败,请联系系统管理员!"));
            }
            //存储验证码
            redisUtil.set(RedisKey.VERIFY_CODE + email, randomText, 120);
        }
    }

    public static void main(String[] args) {
        //生成验证码
        int width=120;
        int height=30;
        BufferedImage verifyImg=new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        String randomText = VerifyCode.drawRandomText(width,height,verifyImg);
        System.out.println(randomText);
    }

    @Override
    public void checkVerifyCodeByUsername(String verifyCode, String username) {
        Assert.hasText(username, LocaleHandler.getLocaleMsg("账号不能为空"));

        String sessionVerifyCode = stringRedisTemplate.opsForValue().get(RedisKey.VERIFY_CODE + username);
        Assert.hasText(sessionVerifyCode,"验证码报错请重新获取");
        if(StringUtils.isBlank(verifyCode) ||
                !sessionVerifyCode.toLowerCase().equals(verifyCode.toLowerCase())){
            throw new BaseException("验证码不正确");
        }
    }

    @Override
    public void checkVerifyCodeByEmail(String verifyCode, String email) {
        Assert.notNull(email, LocaleHandler.getLocaleMsg("邮箱不能为空"));
        Assert.notNull(verifyCode, "验证码不能为空");
        String sessionVerifyCode = redisUtil.get(RedisKey.VERIFY_CODE + email);
        Assert.notNull(sessionVerifyCode,"验证码已过期请重新获取");
        if(!sessionVerifyCode.toLowerCase().equals(verifyCode.toLowerCase())){
            throw new BaseException("验证码不正确");
        }
    }

    // 供应商注册链接是否有效
    @Override
    public Boolean isValidLink(String verifyCode) {
        String key = RedisKey.EMAIL_URL_CACHE + ":[\"" + verifyCode + "\"]";
        return redisUtil.exists(key);
    }
}
