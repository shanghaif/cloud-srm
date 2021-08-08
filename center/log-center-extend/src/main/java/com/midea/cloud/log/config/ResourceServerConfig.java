//package com.midea.cloud.log.config;
//
//import com.midea.cloud.common.constants.PermitAllUrl;
//import com.midea.cloud.component.security.CustomUserInfoTokenServices;
//import com.midea.cloud.srm.feign.oauth.Oauth2Client;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.Map;
//
///**
// * 资源服务配置
// *
// * @author artifact
// */
//@EnableResourceServer
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Value("${security.oauth2.resource.clientId:unknow}")
//    private String clientId;
//
//    @Autowired
//    private Oauth2Client oAuth2Client;
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().exceptionHandling()
//                .authenticationEntryPoint(
//                        (request, response, authException) ->
//                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//                .and().authorizeRequests().antMatchers(PermitAllUrl.permitAllUrl("/logs-anon/**")).permitAll()
//                .anyRequest().authenticated().and().httpBasic();
//    }
//
//    @Bean
//    public CustomUserInfoTokenServices userInfoTokenServices() {
//        CustomUserInfoTokenServices services = new CustomUserInfoTokenServices(
//                this.clientId, new CustomUserInfoTokenServices.ITokenService() {
//            @Override
//            public Map<String, Object> getUserInfoByToken(String accessToken) {
//                return oAuth2Client.getUserInfoByToken(accessToken);
//            }
//        });
//        return services;
//    }
//
//}
