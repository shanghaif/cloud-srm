/**
 * 
 */
package com.midea.cloud.file.config;

import com.midea.cloud.common.utils.StringUtil;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Fastdfs自动配置
 * 
 * @author Nianhua Zhang
 * @since 1.0
 * 
 * fastdfs客户端有并发问题，storageClient 不能使用单例模型
 *
 */

@EnableConfigurationProperties(FastdfsProperties.class)
@Configuration
public class FastdfsAutoConfiguration {

	@Autowired
	private FastdfsProperties fastdfsProperties;
	
	/**
	 * 初始化ClientGlobal
	 * 
	 * @param config
	 */
	private void init(FastdfsProperties config) {
		ClientGlobal.setG_anti_steal_token(config.isAntiStealToken());
		ClientGlobal.setG_charset(config.getCharset());
		ClientGlobal.setG_connect_timeout(config.getConnectTimeout());
		ClientGlobal.setG_network_timeout(config.getNetworkTimeout());
		ClientGlobal.setG_secret_key(config.getSecretKey());
		ClientGlobal.setG_tracker_http_port(config.getTrackerHttpPort());
		String tackerServer = config.getTrackerServer();
		Assert.notNull(tackerServer, "tacker server is null");
		String[] szTrackerServers = StringUtil.split(tackerServer, ",");
		Assert.notNull(szTrackerServers, "trackere server array is null");
		InetSocketAddress[] trackerServers = new InetSocketAddress[szTrackerServers.length];
		for (int i = 0; i < szTrackerServers.length; ++i) {
			String[] parts = szTrackerServers[i].split("\\:", 2);

			Assert.isTrue(
					parts.length == 2,
					"the value of item \"tracker_server\" is invalid, the correct format is host:port");

			trackerServers[i] = new InetSocketAddress(parts[0].trim(),
					Integer.parseInt(parts[1].trim()));
		}
		ClientGlobal.setG_tracker_group(new TrackerGroup(trackerServers));
	}

	/**
	 * Init Tracker Client
	 * 
	 * @param config
	 * @return
	 */
	public TrackerClient trackerClient(FastdfsProperties config) {
		init(config);
		return new TrackerClient(ClientGlobal.g_tracker_group);
	}

	/**
	 * Init TrackerServer
	 * 
	 * @param trackerClient
	 * @return
	 * @throws IOException
	 */
	public TrackerServer trackerServer(TrackerClient trackerClient)
			throws IOException {
		return trackerClient.getConnection();
	}

	/**
	 * Init StorageServer
	 * 
	 * @param trackerClient
	 * @param trackerServer
	 * @return
	 * @throws IOException
	 */
	public StorageServer storageServer(TrackerClient trackerClient,
									   TrackerServer trackerServer) throws IOException {
		return trackerClient.getStoreStorage(trackerServer);
	}

	/**
	 * Init StorageClient1
	 * @return
	 * @throws IOException 
	 */
	public StorageClient1 storageClient() throws IOException {
		TrackerClient client = trackerClient(fastdfsProperties);
		return new StorageClient1(trackerServer(client), storageServer(client, trackerServer(client)));
	}

	public FastdfsProperties getFastdfsProperties() {
		return fastdfsProperties;
	}
}
