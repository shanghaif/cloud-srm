package com.midea.cloud.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 美的云OSS
 *
 */
@Configuration
public class MideaOssConfig {

	@Value("${file.mideaoss.appId}")
	private String appId;

	@Value("${file.mideaoss.appKey}")
	private String appKey;

	@Value("${file.mideaoss.bucket}")
	private String bucket;

	@Value("${file.mideaoss.authHost}")
	private String authHost;

	@Value("${file.mideaoss.appHost}")
	private String appHost;

	public String getAppId() {
		return appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public String getBucket() {
		return bucket;
	}

	public String getAuthHost() {
		return authHost;
	}

	public String getAppHost() {
		return appHost;
	}

}
