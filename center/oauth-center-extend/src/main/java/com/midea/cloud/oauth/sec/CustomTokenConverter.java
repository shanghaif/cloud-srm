package com.midea.cloud.oauth.sec;

import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  TOKEN增强
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-6-30 11:47
 *  修改内容:
 * </pre>
 */
@Component
public class CustomTokenConverter extends DefaultAccessTokenConverter implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (accessToken instanceof DefaultOAuth2AccessToken && authentication.getPrincipal() instanceof LoginAppUser) {
            LoginAppUser loginAppUser = (LoginAppUser) authentication.getPrincipal();
            Map<String, Object> additionalInfos = new HashMap<String, Object>();
            additionalInfos.put(ITokenTemplate.USERNAME, loginAppUser.getUsername());
            additionalInfos.put("userType", loginAppUser.getUserType());
            additionalInfos.put("nickName", loginAppUser.getNickname());
            additionalInfos.put("userId", loginAppUser.getUserId());
            // 用户是否第一次登陆
            additionalInfos.put(ITokenTemplate.FIRST_LOGIN, (StringUtils.isBlank(loginAppUser.getFirstLogin()) ? "" : loginAppUser.getFirstLogin()));
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfos);
        }
        return accessToken;
    }

}
