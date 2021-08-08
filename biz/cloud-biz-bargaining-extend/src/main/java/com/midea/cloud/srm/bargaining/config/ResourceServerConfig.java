package com.midea.cloud.srm.bargaining.config;

import com.google.common.collect.Lists;
import com.midea.cloud.common.constants.PermitAllUrl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.privilege.ResourceManager;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.component.security.CustomUserInfoTokenServices;
import com.midea.cloud.srm.feign.oauth.Oauth2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

import java.util.List;
import java.util.Map;

/**
 * 资源服务配置
 *
 * @author artifact
 */
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter implements ResourceManager{

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
                .antMatchers(PermitAllUrl.permitAllUrl(acquirePermitResource()))
                .permitAll().anyRequest().authenticated().and().httpBasic();
        http.headers().frameOptions().sameOrigin();
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

	@Override
	public String[] acquirePermitResource() {
		List<String> _permitUrls = Lists.newArrayList(
				"/bar-anon/**",
				"/job-anon/**", 
				"/favicon.ico", 
				"/css/**", 
				"/js/**", 
				"/fonts/**", 
				"/layui/**", 
				"/img/**", 
				"/pages/**", 
				"/*.html", 
				"/register/**", 
				"/feignServerCbpm/*"
        		);
		_permitUrls.add("/bidInitiating/biding/get");
        _permitUrls.add("/bidInitiating/biding/getCurrencyByBidingId");
        _permitUrls.add("/bidProcessConfig/bidProcessConfig/listAll");
        _permitUrls.add("/bidInitiating/bidFile/listAll");
        _permitUrls.add("/bidInitiating/group/listAll");
        _permitUrls.add("/bidInitiating/bidFileConfig/listAll");
        _permitUrls.add("/bidInitiating/bidRequirementLine/listPage");
        _permitUrls.add("/bidInitiating/bidRequirement/getByBidingId");
        _permitUrls.add("/inviteVendor/bidVendor/listPage");
        _permitUrls.add("/evaluation/scoreRule/getByBidingId");
        _permitUrls.add("/evaluation/scoreRuleLine/listPage");
        _permitUrls.add("/bidProcessConfig/processNode/listByBidingId");
        _permitUrls.add("/bidingResult/sourcingResultReport/generate");
		return _permitUrls.toArray(new String[0]);
	}

	@Override
	public String[] acquireForbidenResource() {
		throw new IllegalAccessError();
	}

}
