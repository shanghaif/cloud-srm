package com.midea.cloud.srm.sup.config;

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
				"/sup-anon/**", 
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
				"/feignServerCbpm/*", 
				"/erp/vendor/**",
				"/info/vendorInformation/**",
				"/dim/dim/listOrder",
				"/registerService/**",
				"/info/siteInfo/**",


				// 以下代码来自sup-auth模块的代码合并
				"/sup-auth-anon/**",
				"/job-anon/**",
				"/favicon.ico",
				"/css/**",
				"/js/**",
				"/fonts/**",
				"/layui/**",
				"/img/**",
				"/pages/**",
				"/*.html",
				"/feignServerCbpm/*",
				"/review/reviewForm/giveVendorMainAccountRole",
				"/registerService/**",
				
				"/druid",
				"/druid/**",
				"/monitor",
				"/monitor/**" //放开拦截，执行加拦截实现授权
		);

    	/**
    	 *  绿色通道审批流程(流程审批需要放开权限)
    	 */
    	_permitUrls.add("/info/companyInfo/getInfoByParam");		// 根据参数查询公司信息

    	/**
    	 * 供应商跨组织引入(流程审批需要放开权限)
    	 */
    	_permitUrls.add("/vendorImport/getVendorImportDetail");		// 根据引入ID查询
    	_permitUrls.add("/vendorImport/getOrgByVendorId");			// 根据供应商ID查询

    	/**
    	 * 供应商信息变更(流程审批需要放开权限)
    	 */
    	_permitUrls.add("/change/infoChange/getInfoByChangeId");	// 根据变更ID查询

    	/**
    	 * 订单变更(流程审批需要放开权限)
    	 */
    	_permitUrls.add("/info/companyInfo/listPageByOrgIdAndKeyWord");		// 根据orgId和关键字查询公司信息

    	/**
    	 * 采购订单(流程审批需要放开权限)
    	 */
    	_permitUrls.add("/info/siteInfo/listSiteInfoByParam");				// 查询供应商地点
    	
    	/**
    	 * 跨组织引入(流程审批需要放开权限)
    	 */
    	_permitUrls.add("/vendorImport/listOrgCategoryByParam");			// 根据参数查询组织
    	_permitUrls.add("/info/orgCategory/getOrgCategoryByOrgCategory");			// 根据参数查询组织
    	_permitUrls.add("/info/companyInfo/listPageByOrgId");			// 根据参数查询组织
    	_permitUrls.add("/entry/entryConfig/getEntryConfigByTypeAndCategoryId");			// 根据参数查询组织
    	_permitUrls.add("/info/companyInfo/modify");


		// 以下代码来自sup-auth模块的代码合并
		/**
		 *  资质审查业务查询接口(流程审批需要放开权限)
		 */
		_permitUrls.add("/review/reviewForm/getReviewFormDTO");		// 根据单据ID查询资质审查单据信息
		/**
		 *  供应商认证(流程审批需要放开权限)
		 */
		_permitUrls.add("/review/siteForm/getSiteFormDTO");			// 根据siteFormId查询
		_permitUrls.add("/review/reviewForm/listOrgAndCategoryByReviewId");		// 根据reviewFormId查询
		_permitUrls.add("/review/reviewForm/getOrganizationById");		// 根据reviewFormId查询
		/**
		 * 供方样品确认
		 */
		_permitUrls.add("/qua/quaSample/listPageByParam");	// 根据条件查询样品确认单


    	return _permitUrls.toArray(new String[0]);
	}

	@Override
	public String[] acquireForbidenResource() {
		throw new IllegalAccessError();
	}

}
