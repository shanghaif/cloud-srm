package com.midea.cloud.log.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchConfig {

	private String clusterName;

	private String clusterNodes;

    /**
     * 使用elasticsearch实现类时才触发
     *
     * @return
     */
//	@Bean
//    @ConditionalOnBean(value = EsLogServiceImpl.class)
//	public TransportClient getESClient() {
//		// 设置集群名字
//		Settings settings = Settings.builder().put("cluster.name", this.clusterName).build();
//		TransportClient client = new PreBuiltTransportClient(settings);
//		try {
//			// 读取的ip列表是以逗号分隔的
//			for (String clusterNode : this.clusterNodes.split(",")) {
//				String ip = clusterNode.split(":")[0];
//				String port = clusterNode.split(":")[1];
//				((TransportClient) client)
//						.addTransportAddress(new TransportAddress(InetAddress.getByName(ip), Integer.parseInt(port)));
//			}
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//
//		return client;
//	}
}
