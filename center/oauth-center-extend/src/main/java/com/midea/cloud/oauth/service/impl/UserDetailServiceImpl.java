package com.midea.cloud.oauth.service.impl;

import com.midea.cloud.common.constants.CredentialType;
import com.midea.cloud.common.constants.DESUtil;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.component.filter.HttpServletHolder;
import com.midea.cloud.oauth.sec.ITokenTemplate;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.rbac.security.dto.UserSecurityDto;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现security框架的标准接口，org.springframework.security.core.userdetails.UserDetailsService
 *
 */
@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private RbacClient rbacClient;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private ITokenTemplate iTokenTemplate;

    /**是否使用Idm上的用户名/密码登陆(是-true,否false，生产的必须为true)*/
    @Value("${IDM.userIdmPassword}")
    private boolean userIdmPassword;

    @Autowired
    private ClientDetailsService clientDetailsService;

    /**
     * 加载用户信息，在这里实现各种方式的认证
     */
    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 为了支持多类型登录，这里username后面拼装上登录类型,如username|type
        String[] params = username.split("\\|");
        username = params[0]; // 真正的用户名
        CredentialType credentialType = CredentialType.valueOf(params[1]); // 登录类型
        String code = null; // 授权码
        Map<String, Object> authentication = null;
        HttpServletRequest request = HttpServletHolder.getRequest();
        String password = "";
        LoginAppUser loginAppUser = null;
        
        //密码校验，token校验，idm校验等等
        if (params.length == 2) {
            if (CredentialType.PHONE == credentialType) { // 短信登录
                // TODO
            } else if (CredentialType.CODE == credentialType) {
                if (request != null) {
                    code = request.getParameter(ITokenTemplate.CODE); // 授权码
                    authentication = iTokenTemplate.authentication(code); // // 通过token从IDM获取用户信息
                    username = String.valueOf(authentication.get(ITokenTemplate.USERNAME));
                    password = code;
                }
            } else if (CredentialType.USERNAME == credentialType) { //password方式
                /**先调用检测idm系统是否宕机接口，如果没有宕机，则使用idm校验;反正则使用srm校验**/
                password = request.getParameter("password");

                if(userIdmPassword){    //是否使用Idm上的用户名/密码登陆
                    String heartBeat = iTokenTemplate.getHeartBeat();
                    if("ok".equals(heartBeat)) { //idm挂了，先使用srm用户名密码登陆
                        /**添加隆基password方式统一认证*/
                        Map userMap = iTokenTemplate.authentication(username, password);
                        if (null != userMap) {
                            if(null != userMap.get(ITokenTemplate.FIRST_LOGIN)){ //第一次登陆
                                loginAppUser = new LoginAppUser();
                                loginAppUser.setPassword(passwordEncoder.encode(password));
                                loginAppUser.setFirstLogin(String.valueOf(userMap.get(ITokenTemplate.FIRST_LOGIN)));
                                loginAppUser.setAdditionalInformation(authentication);
                                return loginAppUser;
                            }
                            username = String.valueOf(userMap.get(ITokenTemplate.USERNAME));
                        }else {
                            // 让后面的判断抛异常
                            username =null;
                        }
                    }else {
                        // 本地密码校验
                        loginAppUser = rbacClient.findByUsername(username);
                        if( !loginAppUser.getPassword().equals( DESUtil.encrypt(password) ) ) {
                            // 让后面的判断抛异常
                            username =null;
                        }
                    }
                }else{
                    // 本地密码校验
                    loginAppUser = rbacClient.findByUsername(username);
                    if( !loginAppUser.getPassword().equals( DESUtil.encrypt(password) ) ) {
                        // 让后面的判断抛异常
                        username =null;
                    }
                }

            } else if (CredentialType.FACE == credentialType) { 
            	//人脸识别方式
            	//由于通过password传人脸信息会超过消息头的最大长度，这里是直接通过，有gateway校验
            	password =username;//password后面会用于生成token，所以要初始化值
                loginAppUser = rbacClient.findByUsername(username);
                
//                password = request.getParameter("password");
//                UserSecurityDto userSecurityDto =new UserSecurityDto();
//                userSecurityDto.setUsername(username);
//                userSecurityDto.setFaceFileBase64(password);
//            	//直接抛异常返回
//            	Boolean isOk =rbacClient.verifyFace(userSecurityDto);
//            	if( !isOk ) {
//            		// 让后面的判断抛异常
//            		username =null;
//            		throw new BaseException("人脸识别失败");
//            	}

            }
        }

        /**正式环境使用*/
//        log.debug("查询用户:{}", username);
//        LoginAppUser loginAppUser = rbacClient.findByUsername(username);
//        if (loginAppUser == null) {
//            throw new UsernameNotFoundException("该账户没在srm里面");
//        } else if (!loginAppUser.isEnabled()) {
//            log.warn("账号已被禁用: {}", username);
//            throw new DisabledException("账号已被禁用");
//        }

        log.debug("查询用户:{}", username);
        Assert.isTrue(StringUtils.isNotBlank(username), "账号或密码错误");
        /**方便测试使用，如果是IDM的用户，验证通过之后要是srm系统里面没有则保存*/
        loginAppUser = rbacClient.findByUsername(username);
        if (authentication == null) {
            authentication = new HashMap<>();
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(request.getParameter(OAuth2Utils.CLIENT_ID));
            authentication.put("session_invalid_seconds", clientDetails.getAccessTokenValiditySeconds());
        }
        if (loginAppUser == null) {
            throw new UsernameNotFoundException("该账户没在srm里面");
        } else if (!loginAppUser.isEnabled()) {
            log.warn("账号已被禁用: {}", username);
            throw new DisabledException("账号已被禁用");
        }
        if (CredentialType.JWT == credentialType) {
            loginAppUser.setPassword(passwordEncoder.encode((username)));
        } else {
            loginAppUser.setPassword(passwordEncoder.encode(password));
            loginAppUser.setAdditionalInformation(authentication);
        }
        return loginAppUser;
    }

}