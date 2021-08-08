/**
 * 
 */
package com.midea.cloud.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * fastdfs配置类
 * @author Nianhua Zhang
 * @since  1.0
 *
 */
@ConfigurationProperties(prefix = "fastdfs")
public class FastdfsProperties implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8816042397158846671L;

	/**
	 * 连接超时时间，默认5S
	 */
	private int connectTimeout = 5000;
	
	/**
	 * 网络超时时间，默认30S
	 */
	private int networkTimeout = 30000;
	
	/**
	 * 字符集，默认UTF-8
	 */
	private String charset = "UTF-8";
	
	/**
	 * 防盗链token，默认False
	 */
	private boolean antiStealToken = false;
	
	/**
	 * Key, 默认Key
	 */
	private String secretKey = "FastDFS1234567890";
	
	/**
	 * http端口，默认端口
	 */
	private int trackerHttpPort = 80;
	
	/**
	 * 服务器地址，默认连接
	 */
	private String trackerServer = "127.0.0.1:22122";

	private String httpDownloadUrl;

	/**
	 * @return the connectTimeout
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * @param connectTimeout the connectTimeout to set
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * @return the networkTimeout
	 */
	public int getNetworkTimeout() {
		return networkTimeout;
	}

	/**
	 * @param networkTimeout the networkTimeout to set
	 */
	public void setNetworkTimeout(int networkTimeout) {
		this.networkTimeout = networkTimeout;
	}

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @param charset the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @return the antiStealToken
	 */
	public boolean isAntiStealToken() {
		return antiStealToken;
	}

	/**
	 * @param antiStealToken the antiStealToken to set
	 */
	public void setAntiStealToken(boolean antiStealToken) {
		this.antiStealToken = antiStealToken;
	}

	/**
	 * @return the secretKey
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * @param secretKey the secretKey to set
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * @return the trackerHttpPort
	 */
	public int getTrackerHttpPort() {
		return trackerHttpPort;
	}

	/**
	 * @param trackerHttpPort the trackerHttpPort to set
	 */
	public void setTrackerHttpPort(int trackerHttpPort) {
		this.trackerHttpPort = trackerHttpPort;
	}

	/**
	 * @return the trackerServer
	 */
	public String getTrackerServer() {
		return trackerServer;
	}

	/**
	 * @param trackerServer the trackerServer to set
	 */
	public void setTrackerServer(String trackerServer) {
		this.trackerServer = trackerServer;
	}

	public String getHttpDownloadUrl() {
		return httpDownloadUrl;
	}

	public void setHttpDownloadUrl(String httpDownloadUrl) {
		this.httpDownloadUrl = httpDownloadUrl;
	}
}
