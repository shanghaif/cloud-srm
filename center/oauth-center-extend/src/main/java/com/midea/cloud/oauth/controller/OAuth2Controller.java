package com.midea.cloud.oauth.controller;

import com.midea.cloud.srm.feign.log.LogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping
public class OAuth2Controller {

	@Autowired
	private TokenStore tokenStore;

	/**
	 * 登陆用户信息，Principal由框架注入
	 * 
	 * @param principal
	 * @return
	 */
	@GetMapping("/user-me")
	public Principal principal(Principal principal) {
		log.debug("user-me:{}", principal.getName());
		return principal;
	}

	/**
	 * 移除access_token和refresh_token
	 * 
	 * @param access_token
	 */
	@DeleteMapping(value = "/remove_token", params = "access_token")
	public void removeToken(Principal principal, String access_token) {
		OAuth2AccessToken accessToken = tokenStore.readAccessToken(access_token);
		if (accessToken != null) {
			// 移除access_token
			tokenStore.removeAccessToken(accessToken);

			// 移除refresh_token
			if (accessToken.getRefreshToken() != null) {
				tokenStore.removeRefreshToken(accessToken.getRefreshToken());
			}

		}
	}

	@Autowired
	private LogClient logClient;

	/**
	 * 退出日志
	 * 
	 * @param username
	 */
	private void saveLogoutLog(String username) {
		log.info("{}退出", username);
		// 异步
		CompletableFuture.runAsync(() -> {
			try {
			} catch (Exception e) {
				// do nothing
			}

		});
	}

}
