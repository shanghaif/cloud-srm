package com.midea.cloud.api.config;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.api.inter.service.impl.WebserviceGenerator;
import com.midea.cloud.api.interfaceconfig.service.IInterfaceColumnService;
import com.midea.cloud.api.interfaceconfig.service.IInterfaceConfigService;
import com.midea.cloud.api.interfaceconfig.service.IInterfaceParamService;
import com.midea.cloud.api.systemconfig.service.ISystemConfigService;
import com.midea.cloud.common.constants.PermitAllUrl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.component.security.CustomUserInfoTokenServices;
import com.midea.cloud.srm.feign.oauth.Oauth2Client;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceColumn;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceConfig;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceParam;
import com.midea.cloud.srm.model.api.systemconfig.entity.SystemConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * 资源服务配置
 *
 * @author artifact
 */
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.resource.clientId:unknow}")
    private String clientId;

    @Autowired
    private Oauth2Client oAuth2Client;
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionAuthenticationStrategy(new SessionFixationProtectionStrategy())
                .and().csrf().disable().exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) -> {
                            throw new BaseException(ResultCode.NEED_PERMISSION);
                        })
                .accessDeniedHandler(
                        (request, response, accessException) -> {
                            throw new BaseException(ResultCode.SESSION_VALID_ERROR);
                        })
                .and().authorizeRequests()
                .antMatchers(PermitAllUrl.permitAllUrl(
                		"/api-anon/**", 
                		"/job-anon/**",
                		"/registerService/**",
                		"/favicon.ico" , 
        				"/druid",
        				"/druid/**",
        				"/monitor",
        				"/monitor/**", //放开拦截，执行加拦截实现授权
                		"/**")).permitAll()
                .anyRequest().authenticated().and().httpBasic();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)
            throws Exception {
        resources.accessDeniedHandler((request, response, accessException) -> {
            throw new BaseException(ResultCode.SESSION_VALID_ERROR);
        });
        resources.authenticationEntryPoint((request, response, authException) -> {
            throw new BaseException(ResultCode.SESSION_VALID_ERROR);
        });
    }

    @Bean
    public CustomUserInfoTokenServices userInfoTokenServices() {
        CustomUserInfoTokenServices services = new CustomUserInfoTokenServices(
                this.clientId, new CustomUserInfoTokenServices.ITokenService() {
            @Override
            public Map<String, Object> getUserInfoByToken(String accessToken) {
                return oAuth2Client.getUserInfoByToken(accessToken);
            }
        });
        return services;
    }
    
   

}
