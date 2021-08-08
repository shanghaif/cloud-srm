package com.midea.cloud.gateway.controller;

import com.midea.cloud.common.constants.CredentialType;
import com.midea.cloud.common.constants.RedisKey;
import com.midea.cloud.common.constants.UserSecurityConstant;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.component.filter.HttpServletHolder;
import com.midea.cloud.gateway.security.LoginLog;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.oauth.Oauth2Client;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.external.entity.ExternalOrder;
import com.midea.cloud.srm.model.rbac.security.dto.UserSecurityDto;
import com.midea.cloud.srm.model.rbac.user.dto.UserDTO;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 登陆、刷新token、退出
 *
 * @author artifact
 */
@Slf4j
@RestController
public class TokenController {

    @Autowired
    private Oauth2Client oauth2Client;

    @Autowired
    private LoginLog loginLog;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private SupplierClient supplierClient;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RbacClient rbacClient;


    /**
     * 系统登陆<br>
     * 根据用户名登录<br>
     * 采用oauth2密码模式获取access_token和refresh_token
     *
     * @param username
     * @param password
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping("/sys/login")
    public Map<String, Object> login(String username, String password, String verifyCode) throws IOException, ServletException {
        /**
         * 校验密码是否错误, 是否锁定
         */
        verifyExpires(username,password);
//        /**
//         * 检查用户是否锁住
//         * 锁住规则: 距离上次密码错误小于等于5分钟, 并且累计错误错误次数大于5次
//         */
//        // 检查用户登录是否锁住
//        boolean userLoginLock = checkUserLoginLock(username);
//        Assert.isTrue(!userLoginLock, "密码错误次数过多，锁定"+RedisKey.REDIS_LOGIN_LAST_ERROR_TIME+"分钟，请稍后再试");

        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, "cloud");
        parameters.put("client_secret", "cloud_20200213");
        parameters.put(OAuth2Utils.SCOPE, "app");
        // 为了支持多类型登录，这里在username后拼装上登录类型
        parameters.put("username", username + "|" + CredentialType.USERNAME.name());
        parameters.put("password", password);

        Map<String, Object> tokenInfo = null;
        try {
            tokenInfo = oauth2Client.postAccessToken(parameters);
            if (null != tokenInfo && null != tokenInfo.get("additionalInformation")) {
                Map<String, Object> additionalInformationMap = (Map) tokenInfo.get("additionalInformation");
                if (StringUtils.isNotBlank(String.valueOf(additionalInformationMap.get("firstLogin")))) {
                    throw new BaseException(ResultCode.FIRST_LOGIN_MSG);
                }
            }
        } catch (FeignException feignException) {
            log.error("登入异常", feignException);
            if (feignException.status() == 400 || feignException.status() == 401) {
                // 登录失败, 记录错误
//                redisRecordLoginError(username);
                throw new BaseException(ResultCode.LOGIN_ERROR);
            }
            throw feignException;
        } catch (Exception e) {
            log.error("登入异常", e);
            // 登录失败, 记录错误
//            redisRecordLoginError(username);
            throw e;
        }
        loginLog.saveUserTrace(username);
        // 登录成功, 清除错误信息
//        delRedisLoginError(username);
        return tokenInfo;
    }

    /**
     * 系统登陆<br>
     * 人脸识别<br>
     * 采用oauth2密码模式获取access_token和refresh_token
     *
     * @param username
     * @param password
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @PostMapping("/sys/loginFace")
    public Map<String, Object> loginFace(@RequestBody UserSecurityDto userSecurityDto) throws IOException, ServletException {

    	//直接抛异常返回
    	Boolean isOk =rbacClient.verifyFace(userSecurityDto);
    	if( !isOk ) {
    		throw new BaseException("人脸识别失败");
    	}
    	
    	String username =userSecurityDto.getUsername();
    	String password =userSecurityDto.getUsername();
    	
    	//后面要做错误次数控制
    	
//        /**
//         * 检查用户是否锁住
//         * 锁住规则: 距离上次密码错误小于等于5分钟, 并且累计错误错误次数大于5次
//         */
//        // 检查用户登录是否锁住
//        boolean userLoginLock = checkUserLoginLock(username);
//        Assert.isTrue(!userLoginLock, "密码错误次数过多，锁定"+RedisKey.REDIS_LOGIN_LAST_ERROR_TIME+"分钟，请稍后再试");

    	//构造框架需要参数
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, "cloud");
        parameters.put("client_secret", "cloud_20200213");
        parameters.put(OAuth2Utils.SCOPE, "app");
        // 为了支持多类型登录，这里在username后拼装上登录类型
        parameters.put("username", username + "|" + CredentialType.FACE.name());
        parameters.put("password", password);

        Map<String, Object> tokenInfo = null;
        try {
        	//利用oauthcenter的security框架系统校验登录和token
            tokenInfo = oauth2Client.postAccessToken(parameters);
            if (null != tokenInfo && null != tokenInfo.get("additionalInformation")) {
                Map<String, Object> additionalInformationMap = (Map) tokenInfo.get("additionalInformation");
                if (StringUtils.isNotBlank(String.valueOf(additionalInformationMap.get("firstLogin")))) {
                    throw new BaseException(ResultCode.FIRST_LOGIN_MSG);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        loginLog.saveUserTrace(username);
        return tokenInfo;
    }
    
    private void delRedisLoginError(String username) {
        redisTemplate.delete(RedisKey.REDIS_LOGIN_LAST_ERROR_TIME+username);
        redisTemplate.delete(RedisKey.REDIS_LOGIN_ERROR_FREQUENCY+username);
    }

    private void redisRecordLoginError(String username) {
        Object errorNumber = redisTemplate.opsForValue().get(RedisKey.REDIS_LOGIN_ERROR_FREQUENCY + username);
        if (null != errorNumber) {
            int num = (int) errorNumber;
            redisTemplate.opsForValue().set(RedisKey.REDIS_LOGIN_ERROR_FREQUENCY + username, num + 1, 3600 * 24, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(RedisKey.REDIS_LOGIN_LAST_ERROR_TIME + username, RedisKey.REDIS_LOGIN_LAST_ERROR_TIME + username, UserSecurityConstant.PASSWORD_INCORRECT_LOCK_TIME,TimeUnit.MINUTES);
        } else {
            redisTemplate.opsForValue().set(RedisKey.REDIS_LOGIN_ERROR_FREQUENCY + username, 1, 3600 * 24,TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(RedisKey.REDIS_LOGIN_LAST_ERROR_TIME + username, RedisKey.REDIS_LOGIN_LAST_ERROR_TIME + username, UserSecurityConstant.PASSWORD_INCORRECT_LOCK_TIME,TimeUnit.MINUTES);
        }
    }

    private boolean checkUserLoginLock(String username) {
        boolean flag = false;
        // 获取剩余过期时间
        long ttl = redisTemplate.getExpire(RedisKey.REDIS_LOGIN_LAST_ERROR_TIME + username);
        if (-1 == ttl) {
            // 死锁了, 删除
            redisTemplate.delete(RedisKey.REDIS_LOGIN_LAST_ERROR_TIME+username);
        } else if (-2 != ttl) {
            // key存在, 检查累计错误次数
            Object number = redisTemplate.opsForValue().get(RedisKey.REDIS_LOGIN_ERROR_FREQUENCY + username);
            if (null != number && (int) number > 5) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Description 通过授权码实现单点登录
     *
     * @return
     * @throws
     * @Param
     * @Author wuwl18@meicloud.com
     * @Date 2020.07.23
     **/
    @RequestMapping("/sys/idmCodeLogin")
    public Map<String, Object> exchangeToken(String code) {
        if (StringUtils.isBlank(code)) {
            throw new BaseException(ResultCode.MISSING_SERVLET_REQUEST_PARAMETER);
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, "cloud");
        parameters.put("client_secret", "cloud_20200213");
        parameters.put(OAuth2Utils.SCOPE, "app");
        // 为了支持多类型登录，这里在username后拼装上登录类型
        parameters.put("username", "UNKNOW" + "|" + CredentialType.CODE);
        parameters.put("password", code);

        Map<String, Object> tokenInfo = null;
        try {
            tokenInfo = oauth2Client.postAccessToken(parameters);
        } catch (FeignException feignException) {
            log.error("登入异常", feignException);
            if (feignException.status() == 400 || feignException.status() == 401) {
                throw new BaseException(ResultCode.LOGIN_ERROR);
            }
            throw feignException;
        } catch (Exception e) {
            log.error("登入异常", e);
            throw e;
        }
        String username = (null != tokenInfo.get("additionalInformation") ? String.valueOf(((Map) tokenInfo.get("additionalInformation")).get("username")) : "");
        loginLog.saveUserTrace(username);
        return tokenInfo;
    }

    /**
     * Description 获取IdmToken方法
     *
     * @return
     * @throws
     * @Param
     * @Author wuwl18@meicloud.com
     * @Date 2020.07.23
     **/
    @RequestMapping("/sys/getIdmCode")
    public String exchangeToken() {
        HttpServletRequest request = HttpServletHolder.getRequest();
        String code = request.getParameter("code");
        return "success" + code;
    }

    /**
     * 系统刷新refresh_token
     *
     * @param refreshToken
     * @return
     */
    @RequestMapping("/sys/refresh_token")
    public Map<String, Object> refresh_token(String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) {
            throw new BaseException(ResultCode.MISSING_SERVLET_REQUEST_PART, "刷新失败，refreshToken不能为空");
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "refresh_token");
        parameters.put(OAuth2Utils.CLIENT_ID, "cloud");
        parameters.put("client_secret", "cloud_20200213");
        parameters.put(OAuth2Utils.SCOPE, "app");
        parameters.put("refresh_token", refreshToken);

        Map<String, Object> tokenInfo = null;
        try {
            tokenInfo = oauth2Client.postAccessToken(parameters);
        } catch (Exception Exception) {
            throw new BaseException("刷新token失败");
        }
        return tokenInfo;
    }

    /**
     * 退出
     *
     * @param accessToken
     */
    @RequestMapping("/sys/logout")
    public BaseResult logout(String accessToken, @RequestHeader(required = false, value = "Authorization") String token) {
        if (StringUtils.isBlank(accessToken)) {
            if (StringUtils.isNoneBlank(token)) {
                accessToken = token.substring(OAuth2AccessToken.BEARER_TYPE.length() + 1);
            }
        }
        if (StringUtils.isBlank(accessToken)) {
            throw new BaseException(ResultCode.MISSING_SERVLET_REQUEST_PART, "退出失败，accessToken不能为空");
        }
        try {
            // 更新用户痕迹
            loginLog.updateUserTrace(accessToken);
            oauth2Client.removeToken(accessToken);
        } catch (Exception ex) {
//			throw new BaseException("退出失败");
            log.error("退出失败", ex); // token已经失效，导致退出异常，直接返回退出成功
        }
        return BaseResult.buildSuccess();
    }

    /**
     * Description 根据外部项目用户名登录，采用oauth2密码模式获取access_token和refresh_token
     *
     * @return {"data":{"tenantId":"xxx"},"success":true}
     * @throws IOException
     * @Param username 用户名
     * @Param password 密码
     * @Author wuwl18@meicloud.com
     * @Date 2020.07.03
     **/
    @PostMapping("/sys/externalLogin")
    public void externalLogin(@RequestBody UserDTO user, HttpServletResponse response) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        response.setHeader("Context-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> dataMap = new HashMap<>();
        resultMap.put("success", false);
        if (null == user) {
            resultMap.put("msg", "账号或密码不能为空");
            resultMap.put("data", dataMap);
            response.getWriter().write(JsonUtil.entityToJsonStr(resultMap));
            return;
        }

        String username = user.getUsername();
        String password = user.getPassword();
        String rentId = "";  //租户ID
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            resultMap.put("msg", "账号或密码不能为空");
            resultMap.put("data", dataMap);
            response.getWriter().write(JsonUtil.entityToJsonStr(resultMap));
            return;
        }

        // 校验订单有效期是否到期，如果订单有效期到期，则不允许所有用户登入 【订单阈值控制】
        List<ExternalOrder> externalOrders = baseClient.listExternalOrder();
        if (CollectionUtils.isNotEmpty(externalOrders)) {
            ExternalOrder externalOrder = externalOrders.get(0);
            if (externalOrder.getEndDate() == null || externalOrder.getEndDate().isBefore(LocalDate.now())) {
                resultMap.put("msg", "当前实例已过期，需续费后才能继续试用，请进入门户 http://cloud.meicloud.com/购买续费SRM云"); // TODO 国家化是否需要？
                resultMap.put("data", dataMap);
                response.getWriter().write(JsonUtil.entityToJsonStr(resultMap));
                return;
            }
            rentId = (null != externalOrder ? externalOrder.getRentId() : "");
        } else {
            resultMap.put("msg", "当前实例已过期，需续费后才能继续试用，请进入门户 http://cloud.meicloud.com/购买续费SRM云"); // TODO 国家化是否需要？
            resultMap.put("data", dataMap);
            response.getWriter().write(JsonUtil.entityToJsonStr(resultMap));
            return;
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, "cloud");
        parameters.put("client_secret", "cloud_20200213");
        parameters.put(OAuth2Utils.SCOPE, "app");
        // 为了支持多类型登录，这里在username后拼装上登录类型
        parameters.put("username", username + "|" + CredentialType.USERNAME.name());
        parameters.put("password", password);

        try {
            oauth2Client.postAccessToken(parameters);
        } catch (FeignException feignException) {
            log.error("登入异常", feignException);
            if (feignException.status() == 400 || feignException.status() == 401) {
                resultMap.put("msg", ResultCode.LOGIN_ERROR.getMessage());
                resultMap.put("data", dataMap);
                response.getWriter().write(JsonUtil.entityToJsonStr(resultMap));
                return;
            }
        } catch (Exception e) {
            log.error("登入异常", e);
            resultMap.put("msg", e);
            resultMap.put("data", dataMap);
            response.getWriter().write(JsonUtil.entityToJsonStr(resultMap));
            return;
        }
        loginLog.saveUserTrace(username);
        resultMap.put("success", true);
        resultMap.put("msg", "");
        dataMap.put("tenantId", rentId);
        resultMap.put("data", dataMap);

        response.getWriter().write(JsonUtil.entityToJsonStr(resultMap));
    }




    /**
     * @param username
     * @param password
     * @return java.lang.Boolean
     * @author chenhb30@meicloud.com
     * @date 2020/9/19 15:49
     * @Desc 确认当前用户的密码是否过期
     */
    @RequestMapping("/sys/verifyExpires")
    public Boolean verifyExpires(String username, String password) throws IOException, ServletException {
        Assert.notNull(username,"用户名不能为空");
        Assert.notNull(password,"密码不能为空");

        // 校验密码，账号密码不在这里校验---lizl7
//        Boolean isTrue = rbacClient.verifyPassword(username, password);
//      if (!isTrue) {
//      throw new BaseException(ResultCode.LOGIN_ERROR, "账号或者密码不正确");
//  }
        
        // 校验是否被锁住
        Boolean isLock = rbacClient.verifyUserLock(username, password);
        if (isLock) {
            // 被锁住
            throw new BaseException(ResultCode.LOGIN_ERROR, "密码错误次数过多，将锁定" + UserSecurityConstant.PASSWORD_INCORRECT_LOCK_TIME + "分钟，请稍后再试");
        }

        return true;
    }

    /**
     * @param
     * @return java.util.Date
     * @author chenhb30@meicloud.com
     * @date 2020/9/19 15:48
     * @Desc 获取当前时间的前三个月时间
     */
    private Date getBeforeDate() {
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, -3);  //设置为前3月
        dBefore = calendar.getTime();
        return dBefore;
    }

}
