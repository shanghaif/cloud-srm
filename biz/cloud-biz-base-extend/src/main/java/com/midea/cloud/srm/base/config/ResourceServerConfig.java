package com.midea.cloud.srm.base.config;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.midea.cloud.common.constants.PermitAllUrl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.privilege.ResourceManager;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.component.security.CustomUserInfoTokenServices;
import com.midea.cloud.srm.feign.oauth.Oauth2Client;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
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
public class ResourceServerConfig extends ResourceServerConfigurerAdapter implements ResourceManager {

    @Value("${security.oauth2.resource.clientId:unknow}")
    private String clientId;

    @Autowired
    private Oauth2Client oAuth2Client;

    @Resource
    private RbacClient rbacClient;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    
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
                .permitAll().anyRequest().authenticated().and().httpBasic()
                .and().authenticationProvider(new AuthenticationProvider() {
                    @Override
                    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
//                        String username = usernamePasswordAuthenticationToken.getPrincipal().toString();
//                        String password = authentication.isAuthenticated() ? null : usernamePasswordAuthenticationToken.getCredentials().toString();
//                        LoginAppUser loginAppUser = rbacClient.findByUsername(username);
//                        if(ObjectUtils.isEmpty(loginAppUser)){
//                            return null;
//                        }else if(authentication.isAuthenticated() || bCryptPasswordEncoder(password,loginAppUser.getPassword())){
//                            usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password, loginAppUser.getAuthorities());
//                            usernamePasswordAuthenticationToken.setDetails(JSONObject.toJSON(loginAppUser));
//
//                        }
                        return null;
                    }

                    @Override
                    public boolean supports(Class<?> authentication) {
                        return false;
                    }
                });
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
				"/base-anon/**", 
				"/job-anon/**", 
				"/favicon.ico", 
				"/css/**", 
				"/js/**",
                "/fonts/**", 
                "/layui/**", 
                "/img/**", 
                "/pages/**", 
                "/*.html",
                "/ureport/**", 
                "/registerService/**", 
                "/workflow/**",
                "/region/queryRegion",
                "/purchase/purchaseTax/listAll",
                "/purchase/purchaseUnit/listAll",
                "/sceneAttachment/listPageByParm",
                "/organization/relation/selectTreeByType",
                "/dict/base-dict-item/listAllByListParam",
                "/dict/base-dict-item/listAllByParam",
                "/purchase/purchaseCategory/listChildren",
                "/base/location/getLocationsByOrganization",
                "/region/queryRegionByParam",
                "/organization/relation/tree",
                "/organization/relation/treeNew",
                "/repush/**",
                "/flow/**",
                "/flow-anon/**",
                
				"/druid",
				"/druid/**",
				"/monitor",
				"/monitor/**" //放开拦截，执行加拦截实现授权
        		);
		
		/**
		 * 公共接口(流程审批需要放开权限)
		 */
		_permitUrls.add("/purchase/purchaseCurrency/listAll");	// 查询币种
		_permitUrls.add("/material/materialItem/listAllForTranferOrder");	// 查询币种
		_permitUrls.add("/material/materialItem/queryTaxByItemForOrder");	// 查询币种
		_permitUrls.add("/purchase/purchaseCategory/listByCategoryCode");	// 查询币种
		_permitUrls.add("/organization/organization/listAllOrganization");	// 查询业务实体
		_permitUrls.add("/virtual-depart/pageDept");	// 查询部门信息
		_permitUrls.add("/organization/organization/get");	// 查询部门信息
		_permitUrls.add("/material/materialItem/listMaterialItemsByIds");	
		_permitUrls.add("/dict/base-dict-item/queryPageByConditions");
		_permitUrls.add("/dict/base-dict-item/listByDictCode");
		_permitUrls.add("/organization/organization/getOrganizationByOrgCode");
		_permitUrls.add("/organization/organization-user/listByParam");
		_permitUrls.add("/seq/get");
		_permitUrls.add("/material/materialItem/queryTaxByItem");
		_permitUrls.add("/base/base-ou-group/queryGroupInfoDetailByIds");
		_permitUrls.add("/dict/base-dict-item/listAllByDictCode");
		_permitUrls.add("/organization/organization/getByParam");
		_permitUrls.add("/workflow/getHeavyPush");
		_permitUrls.add("/quicksearch/quicksearchConfig/listByFormCondition");
		_permitUrls.add("/purchase/purchaseCategory/MinByIfDeliverPlan");
		_permitUrls.add("/purchase/purchaseCategory/listCategoryByIds");


    	return _permitUrls.toArray(new String[0]);
	}

	@Override
	public String[] acquireForbidenResource() {
		throw new IllegalAccessError();
	}

}
