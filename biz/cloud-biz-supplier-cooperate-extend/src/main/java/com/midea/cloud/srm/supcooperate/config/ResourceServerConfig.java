package com.midea.cloud.srm.supcooperate.config;

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
                "/sc-anon/**",
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
                "/registerService/**",

                // 以下代码来自pm模块的代码合并
                "/pr-anon/**",
                "/po-anon/**",
                "/pm-anon/**",
                "/ps-anon/**",
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
                "/registerService/**",
                "/dim/dim/listOrder",
                "/purchase/purchaseTax/listAll",
                "/purchase/purchaseUnit/listAll",
                
				"/druid",
				"/druid/**",
				"/monitor",
				"/monitor/**" //放开拦截，执行加拦截实现授权
        		);


        _permitUrls.add("/order/order/getOrderById");			// 根据ID查询采购订单信息
        _permitUrls.add("/order/orderDetail/getOrderDetailByOrderId");			// 根据ID查询采购订单详情信息
        _permitUrls.add("/order/orderAttach/getOrderAttachByOrderId");			// 根据ID查询采购订单附件信息
        _permitUrls.add("/order/orderPaymentProvision/getOrderPaymentProvisionByOrderId");			// 根据ID查询采购订单信息
        _permitUrls.add("/order/orderDetail/OrderDetailList");			// 根据ID查询采购订单信息
        _permitUrls.add("/order/orderDetail/batchUpdateOrderDetail");			// 根据ID查询采购订单信息
        _permitUrls.add("/order/warehousingReturnDetail/list");			// 根据ID查询采购订单信息
        _permitUrls.add("/order/order/batchUpdateOrder");			// 根据ID查询采购订单信息
        _permitUrls.add("/order/order/saveOrder");			// 根据ID查询采购订单信息
        _permitUrls.add("/order/orderDetail/acceptBuyerSubmit");			// 根据ID查询采购订单信息
        _permitUrls.add("/order/order/saveOrderForm");			// 根据ID查询采购订单信息
        _permitUrls.add("/order/order/getOrderByIds");          //获取订单列表
        _permitUrls.add("/order/orderDetail/getOrderDetailByRequirementLineIds");


        // 以下代码来自pm模块的代码合并

        /**
         *  采购申请详情页面接口权限(流程审批需要放开权限)
         */
        _permitUrls.add("/pr/requirementHead/getByHeadId");		// 根据头ID获取所有信息
        _permitUrls.add("/pr/requirementLine/get");				// 根据lineId获取行信息
        _permitUrls.add("/pr/requirementLine/listPage");		// 分页查询line行信息

        /**
         * 采购订单变更(流程审批需要放开权限)
         */
        _permitUrls.add("/po/order/queryOrderById");			// 根据ID查询采购订单信息
        _permitUrls.add("/payment/paymentApplyHead/getPaymentApply");
        _permitUrls.add("/order/warehousingReturnDetail/list");
        _permitUrls.add("/pr/requirementHead/getRequirementHeadByNumber");

		return _permitUrls.toArray(new String[0]);
	}

	@Override
	public String[] acquireForbidenResource() {
		throw new IllegalAccessError();
	}

}
