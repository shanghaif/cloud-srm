package com.midea.cloud.api.inter.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class ThreadHttpSend implements Runnable {
	private String url;
	private String json;
	private RestTemplate restTemplate = new RestTemplate();
	
	public ThreadHttpSend(String url, String json, RestTemplate restTemplate) {
		super();
		this.url = url;
		this.json = json;
		this.restTemplate = restTemplate;
	}
	
	@Override
	public void run() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set("appId", "paas");
		header.set("timestamp", "123456987");
		header.set("sign", "99d04e92b87719d06a6d2cb997cca0f3");
		HttpEntity<String> httpEntity = new HttpEntity<String>(json, header);
		restTemplate.postForObject(url, httpEntity, String.class);
	}

}
