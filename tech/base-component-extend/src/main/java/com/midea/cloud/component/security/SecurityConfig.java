package com.midea.cloud.component.security;

import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.BeanMapUtils;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;

import java.util.Date;
import java.util.Map;

/**
 * <pre>
 * 安全相关配置
 * </pre>`
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/03/30 07:51
 *  修改内容:
 * </pre>
 */
@Configuration("oauthSecurityConfig")
@Slf4j
public class SecurityConfig {

    @Value("${security.oauth2.resource.clientId:unknow}")
    private String clientId;

    private static final String ACCESS = "access:";

    private static final String CLIENT = "client:";

    private RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

    @Bean
    @Primary
    @ConditionalOnMissingBean(ClientDetailsService.class)
    public CustomUserInfoTokenServices tokenServices(RedisConnectionFactory connectionFactory) {
        TokenStore tokenStore = new RedisTokenStore(connectionFactory);
        RedisTemplate redisTemplate = SpringContextHolder.getBean("redisTemplate", RedisTemplate.class);
        CustomUserInfoTokenServices services = new CustomUserInfoTokenServices(
                this.clientId, new CustomUserInfoTokenServices.ITokenService() {
            @Override
            public Map<String, Object> getUserInfoByToken(String accessToken) {
                OAuth2Authentication authentication = tokenStore.readAuthentication(accessToken);
                if (authentication == null) {
                    throw new BaseException(ResultCode.SESSION_VALID_ERROR);
                }
                if (authentication instanceof OAuth2Authentication) {
                    Integer validitySeconds = null;
                    Object nickName = null;
                    Object userId = null;
                    Object principal = authentication.getUserAuthentication().getPrincipal();
                    if (principal != null) {
                        nickName = ((LoginAppUser) principal).getNickname();
                        userId = ((LoginAppUser) principal).getUserId();
                    }
                    // 自动续期
                    try {
                        validitySeconds = Integer.valueOf(((LoginAppUser) authentication.getPrincipal()).getAdditionalInformation().get("session_invalid_seconds").toString());
                        // 修改过期时间
                        byte[] accessKey = serializationStrategy.serialize(ACCESS + accessToken);
                        Date expireDate = new Date(System.currentTimeMillis() + validitySeconds * 1000L);
                        redisTemplate.execute(connection -> {
                            try {
                                return connection.pExpireAt(accessKey, expireDate.getTime());
                            } catch (Exception e) {
                                return connection.expireAt(accessKey, expireDate.getTime() / 1000);
                            }
                        }, true);
                        log.debug("token自动续期成功：{}-{}", nickName, userId);
                    } catch (Exception ex) {
                        log.debug("token自动续期失败：{}-{}", nickName, userId);
                    }
                }
                authentication.setAuthenticated(true);
                return BeanMapUtils.beanToMap(authentication);
            }
        });
        return services;
    }

}
