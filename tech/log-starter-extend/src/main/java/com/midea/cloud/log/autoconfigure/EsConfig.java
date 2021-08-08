package com.midea.cloud.log.autoconfigure;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.net.InetAddress;
import java.util.Arrays;

/**
 * @author tanjl11
 * @date 2020/12/16 13:20
 */
//@Configuration
public class EsConfig {
//    @Value("${elasticsearch.rest.uris:10.17.145.110:9200}")
//    private String restUris;
//
//    @Bean
//    public RestHighLevelClient client() {
//        RestHighLevelClient client = new RestHighLevelClient(
//                RestClient.builder(Arrays.stream(restUris.split(","))
//                        .map(e -> {
//                            String[] info = e.split(":");
//                            return new HttpHost(info[0], Integer.valueOf(info[1]));
//                        }).toArray(HttpHost[]::new))
//        );
//        return client;
//    }
}
