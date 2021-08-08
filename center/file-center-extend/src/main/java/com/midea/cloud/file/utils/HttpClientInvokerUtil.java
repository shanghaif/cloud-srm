package com.midea.cloud.file.utils;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * HTTP调用工具
 * 
 *
 */
public class HttpClientInvokerUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientInvokerUtil.class.getName());

	/**
	 * 调用http get方法
	 * 
	 * @param url
	 * @return
	 */
	public static String invokeGetMethodByHttp(String url) throws Exception {
		String result = "";
		GetMethod request = new GetMethod(url);
		request.setRequestHeader("Charset", "utf-8");
		HttpClient client = new HttpClient();
		client.executeMethod(request);
		result = request.getResponseBodyAsString();
		return result;
	}

	/**
	 * 原生字符串发送post请求
	 *
	 * @param url
	 * @param jsonStr
	 * @return
	 * @throws IOException
	 */
	public static String invokePostMethodByHttp(String url, String jsonStr) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000)
				.setSocketTimeout(60000).build();
		httpPost.setConfig(requestConfig);
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("DataEncoding", "UTF-8");

		CloseableHttpResponse httpResponse = null;
		try {
			httpPost.setEntity(new StringEntity(jsonStr));
			httpResponse = httpClient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity);
			return result;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 原生字符串发送post请求
	 *
	 * @param url
	 * @param jsonStr
	 * @return
	 * @throws IOException
	 */
	public static String invokePostMethodByHttp(String url, String jsonStr, Map<String, String> headMap) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000)
				.setSocketTimeout(60000).build();
		httpPost.setConfig(requestConfig);
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("DataEncoding", "UTF-8");

		if (headMap != null) {
			for (Entry<String, String> entry : headMap.entrySet()) {
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}

		CloseableHttpResponse httpResponse = null;
		try {
			httpPost.setEntity(new StringEntity(jsonStr));
			httpResponse = httpClient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity);
			return result;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 调用http post方法
	 * 
	 * @param url
	 * @param body
	 * @param contentType
	 * @return
	 */
	public static String invokePostMethodByHttp(String url, String body, String contentType) throws Exception {
		String result = "";
		PostMethod request = new PostMethod(url);
		HttpClient client = new HttpClient();
		request.getParams().setContentCharset("UTF-8");
		request.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		request.setRequestHeader("Content-Type", contentType);
		request.setRequestHeader("Charset", "UTF-8");
		request.setRequestBody(body);
		client.executeMethod(request);
		result = request.getResponseBodyAsString();
		return result;
	}

	/**
	 * 调用http post方法
	 *
	 * @param url
	 * @param body
	 * @param contentType
	 * @return
	 */
	public static String invokePostMethodByHttp(String url, String body, String contentType,
			HostConfiguration hostConfiguration) throws Exception {
		String result = "";
		PostMethod request = new PostMethod(url);
		HttpClient client = new HttpClient();
		request.getParams().setContentCharset("UTF-8");
		request.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		request.setRequestHeader("Content-Type", contentType);
		request.setRequestHeader("Charset", "UTF-8");
		request.setRequestBody(body);
		if (hostConfiguration != null) {
			client.setHostConfiguration(hostConfiguration);
		}
		client.executeMethod(request);
		result = request.getResponseBodyAsString();
		return result;
	}

	/**
	 * 调用http post方法
	 * 
	 * @param url
	 * @param params
	 * @param body
	 * @param contentType
	 * @return
	 */
	public static String invokePostMethodByHttp(String url, HashMap<String, String> params, String body,
			String contentType) throws Exception {
		String result = "";
		PostMethod request = new PostMethod(url);
		HttpClient client = new HttpClient();
		request.getParams().setContentCharset("UTF-8");
		request.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		request.setRequestHeader("Content-Type", contentType);
		request.setRequestHeader("Charset", "UTF-8");
		for (Entry<String, String> entry : params.entrySet()) {
			request.setParameter(entry.getKey(), entry.getValue());
		}
		request.setRequestBody(body);
		client.getHostConfiguration().setProxy("172.16.6.191", 8080);// 设置代理，使finddler工具能抓取 httpclient发送的请求
		client.executeMethod(request);
		result = request.getResponseBodyAsString();
		return result;
	}

	/**
	 * 调用http post方法
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String invokePostMethodByHttp(String url, Map<String, String> params) throws Exception {
		String result = "";
		PostMethod request = new PostMethod(url);
		HttpClient client = new HttpClient();
		for (Entry<String, String> entry : params.entrySet()) {
			request.setParameter(entry.getKey(), entry.getValue());
		}
		client.executeMethod(request);
		result = request.getResponseBodyAsString();
		return result;
	}

	/**
	 * 调用http post方法
	 * 
	 * @param url
	 * @param data
	 * @return
	 */
	public static String invokePostMethodByHttp(String url, NameValuePair[] data) throws Exception {
		String result = "";
		PostMethod request = new PostMethod(url);
		HttpClient client = new HttpClient();
		request.getParams().setContentCharset("UTF-8");
		request.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		request.setRequestHeader("Charset", "UTF-8");
		request.setRequestBody(data);
		client.executeMethod(request);
		result = request.getResponseBodyAsString();
		return result;
	}

	/**
	 * 调用http post方法
	 * 
	 * @param url
	 * @param body
	 * @param contentType
	 * @return
	 */
	public static String invokePutMethodByHttp(String url, String body, String contentType) throws Exception {
		String result = "";
		PutMethod request = new PutMethod(url);
		HttpClient client = new HttpClient();
		request.setRequestHeader("Content-Type", contentType);
		request.setRequestHeader("Charset", "utf-8");
		request.setRequestBody(body);
		client.executeMethod(request);
		result = request.getResponseBodyAsString();
		return result;
	}

	/**
	 * 调用http put方法
	 * 
	 * @param url
	 * @param body
	 * @param contentType
	 * @param headerMap
	 * @return
	 * @throws IOException
	 */
	public static String invokePutMethodByHttp(String url, String body, String contentType,
			HashMap<String, String> headerMap) throws IOException {
		String result = "";
		PutMethod request = new PutMethod(url);
		HttpClient client = new HttpClient();
		request.setRequestHeader("Content-Type", contentType);
		request.setRequestHeader("Charset", "utf-8");
		for (Entry<String, String> entry : headerMap.entrySet()) {
			request.setRequestHeader(entry.getKey(), entry.getValue());
		}
		request.setRequestBody(body);
		// client.getHostConfiguration().setProxy("127.0.0.1",
		// 8888);//设置代理，使finddler工具能抓取 httpclient发送的请求
		client.executeMethod(request);
		result = request.getResponseBodyAsString();
		return result;
	}

	/**
	 * 中转文件
	 * 
	 * @param inputStream 上传的文件流
	 * @return 响应结果
	 * @throws IOException
	 */
	public static int httpClientUploadFile(InputStream inputStream, String targetURL, Map<String, String> headerMap)
			throws IOException {
		String result;
		PutMethod request = new PutMethod(targetURL);
		for (Entry<String, String> entry : headerMap.entrySet()) {
			request.setRequestHeader(entry.getKey(), entry.getValue());
		}
		int status = 0;
		try {
			request.setRequestEntity(new InputStreamRequestEntity(inputStream));
			String contentLength = String.valueOf(request.getRequestEntity().getContentLength());
			request.setRequestHeader("content-length", contentLength);// 计算文件字节
			HttpClient client = new HttpClient();
			// client.getHostConfiguration().setProxy("127.0.0.1",
			// 8888);//设置代理，使finddler工具能抓取 httpclient发送的请求
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			status = client.executeMethod(request);
			if (status == HttpStatus.SC_OK) {
				logger.debug("上传成功");
				// 上传成功
			} else {
				// 上传失败
				logger.error("上传失败:" + status);
			}

		} catch (Exception ex) {
			logger.error("上传失败:" + ex.getMessage(), ex);
		} finally {
			request.releaseConnection();
		}
		return status;
	}

	/**
	 * 调用http get方法
	 * 
	 * @param url
	 * @return
	 */
	public static InputStream invokeGetMethodByInputStream(String url, Map<String, String> headerMap) throws Exception {
		GetMethod request = new GetMethod(url);
		if (headerMap != null) {
			for (Entry<String, String> entry : headerMap.entrySet()) {
				request.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		HttpClient client = new HttpClient();
		client.executeMethod(request);
		InputStream in = request.getResponseBodyAsStream();
		return in;
	}

	/**
	 * 调用http get方法
	 *
	 * @param url
	 * @return
	 */
	public static InputStream invokeGetMethodWithProxyByInputStream(String url, String contentType) throws Exception {
		GetMethod request = new GetMethod(url);
		request.setRequestHeader("Charset", "utf-8");
		if (contentType != null) {
			request.setRequestHeader("Content-Type", contentType);
		}
		HttpClient client = new HttpClient();
		// client.getHostConfiguration().setProxy(util.proxyHost,util.proxyPort);
		client.executeMethod(request);
		InputStream in = request.getResponseBodyAsStream();
		return in;
	}

	/**
	 * 发送delete请求
	 *
	 * @param url
	 * @param token
	 * @param jsonStr
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String invokeDeleteMethodByHttp(String url, String token, String jsonStr) {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000)
				.setSocketTimeout(60000).build();
		httpDelete.setConfig(requestConfig);
		httpDelete.setHeader("Content-type", "application/json");
		httpDelete.setHeader("DataEncoding", "UTF-8");
		httpDelete.setHeader("token", token);

		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpDelete);
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity);
			return result;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
