package com.midea.cloud.log.aop.es;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.midea.cloud.srm.model.log.useroperation.entity.LogDocument;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * es工具类，es7
 * @author lizl7
 *
 */
@Component
@Slf4j
public class EsUtil {

	@Value("${es.host:10.17.145.110}")
	public String host;
	@Value("${es.port:9200}")
	public int port;
	@Value("${es.scheme:http}")
	public String scheme;
	
	public String Index_Pre_Logdocument ="logdocument";
	
	/**
	 * 根据自己的业务去定义
	 **/
	public String Index_Properties_LogDocument = "{\"properties\":{\"creationDate\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd HH:mm:ss\"},\"operationLogId\":{\"type\":\"long\",\"index\":true},\"nickname\":{\"type\":\"text\"},\"username\":{\"type\":\"keyword\"},\"userType\":{\"type\":\"keyword\"},\"methodName\":{\"type\":\"keyword\"},\"operationTime\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd HH:mm:ss\"},\"requestIp\":{\"type\":\"ip\"},\"requestUrl\":{\"type\":\"text\"},\"requestParam\":{\"type\":\"text\"},\"logInfo\":{\"type\":\"text\"},\"responseResult\":{\"type\":\"text\"},\"resultStatus\":{\"type\":\"keyword\"},\"errorInfo\":{\"type\":\"text\"},\"model\":{\"type\":\"keyword\"},\"requestStartTime\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd HH:mm:ss\"},\"requestEndTime\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd HH:mm:ss\"},\"responseDate\":{\"type\":\"long\"}}}";
	// 指定中文分词，没有插件，先不用
	// {\"properties\":{\"operationLogId\":{\"type\":\"long\",\"index\":true},\"nickname\":{\"type\":\"text\"},\"username\":{\"type\":\"keyword\"},\"userType\":{\"type\":\"keyword\"},\"methodName\":{\"type\":\"keyword\"},\"operationTime\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd HH:mm:ss\"},\"requestIp\":{\"type\":\"ip\"},\"requestUrl\":{\"type\":\"text\"},\"requestParam\":{\"type\":\"text\"},\"logInfo\":{\"type\":\"text\"},\"responseResult\":{\"type\":\"text\"},\"resultStatus\":{\"type\":\"keyword\"},\"errorInfo\":{\"type\":\"text\"},\"model\":{\"type\":\"keyword\"},\"requestStartTime\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd HH:mm:ss\"},\"requestEndTime\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd HH:mm:ss\"},\"responseDate\":{\"type\":\"long\"}}}

	public static RestHighLevelClient client = null;

	@PostConstruct
	public void init() {
		try {
			client = new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, scheme)));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 根据日期获取，拼接索引
	 *
	 * @param index index名
	 * @return boolean
	 */
	public String getIndexByDate(String indexPre, Date date) throws Exception {
		String index =indexPre+"_"+dateToStringB(date);
		return index;
	}
	
	/**
	 * 判断某个index是否存在
	 *
	 * @param index index名
	 * @return boolean
	 */
	public boolean indexExist(String index) throws Exception {
		GetIndexRequest request = new GetIndexRequest(index);
		request.local(false);
		request.humanReadable(true);
		request.includeDefaults(false);
		return client.indices().exists(request, RequestOptions.DEFAULT);
	}

	/**
	 * 创建索引
	 *
	 * @param index index名
	 * @return boolean
	 */
	public boolean createIndex(String index) throws Exception {
		CreateIndexRequest request = new CreateIndexRequest(index);
		request.settings(Settings.builder().put("index.number_of_shards", 1).put("index.number_of_replicas", 0));
		request.mapping(Index_Properties_LogDocument, XContentType.JSON);
		CreateIndexResponse res = client.indices().create(request, RequestOptions.DEFAULT);
		if (!res.isAcknowledged()) {
			throw new RuntimeException("创建索引失败");
		}
		return true;
	}

	/**
	 * 插入数据
	 *
	 * @param index index
	 * @param list  带插入列表
	 */
	public void insert(String index, LogDocument document) {
		BulkRequest request = new BulkRequest();
		request.add(new IndexRequest(index).source(JSON.toJSONString(document), XContentType.JSON));
		try {
			client.bulk(request, RequestOptions.DEFAULT);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 插入/更新一条记录
	 *
	 * @param index  index
	 * @param entity 对象
	 */
	public void insertOrUpdateOne(String index, LogDocument document) {
		IndexRequest request = new IndexRequest(index);
		request.id(document.getOperationLogId().toString());
		request.source(JSON.toJSONString(document), XContentType.JSON);
		try {
			client.index(request, RequestOptions.DEFAULT);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 批量插入数据
	 *
	 * @param index index
	 * @param list  带插入列表
	 */
	public void insertBatch(String index, List<LogDocument> list) {
		BulkRequest request = new BulkRequest();
		list.forEach(item -> request.add(new IndexRequest(index).source(JSON.toJSONString(item), XContentType.JSON)));
		try {
			client.bulk(request, RequestOptions.DEFAULT);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 批量删除
	 *
	 * @param index  index
	 * @param idList 待删除列表
	 */
	public <T> void deleteBatch(String index, Collection<T> idList) {
		BulkRequest request = new BulkRequest();
		idList.forEach(item -> request.add(new DeleteRequest(index, item.toString())));
		try {
			client.bulk(request, RequestOptions.DEFAULT);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 搜索
	 *
	 * @param index   index
	 * @param builder 查询参数
	 * @param c       结果类对象
	 * @return java.util.ArrayList
	 */
	public <T> List<T> search(String index, SearchSourceBuilder builder, Class<T> c) {
		SearchRequest request = new SearchRequest(index);
		request.source(builder);
		try {
			SearchResponse response = client.search(request, RequestOptions.DEFAULT);
			SearchHit[] hits = response.getHits().getHits();
			List<T> res = new ArrayList<>(hits.length);
			for (SearchHit hit : hits) {
				res.add(JSON.parseObject(hit.getSourceAsString(), c));
			}
			System.err.println("查询符合满足条件的总条数=" + response.getHits().getTotalHits());
			return res;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 常用分页查询 from size
	 *
	 * @param index
	 * @param page
	 * @param builder
	 * @param c
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	public <T> Page<T> searchPage(String index, Page page, SearchSourceBuilder builder, Class<T> c) throws IOException {
		SearchRequest searchRequest = new SearchRequest(index);
		searchRequest.source(builder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		long totalCount = response.getHits().getTotalHits().value;
		page.setTotal(totalCount);
		// 数据总页数
		SearchHit[] hits = response.getHits().getHits();
		List<T> res = new ArrayList<>(hits.length);
		for (SearchHit hit : hits) {
			res.add(JSON.parseObject(hit.getSourceAsString(), c));
		}
		page.setRecords(res);
		return page;
	}

	/**
	 * 删除index
	 *
	 * @param index index
	 * @return void
	 */
	public void deleteIndex(String index) {
		try {
			client.indices().delete(new DeleteIndexRequest(index), RequestOptions.DEFAULT);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * delete by query
	 *
	 * @param index   index
	 * @param builder builder
	 */
	public void deleteByQuery(String index, QueryBuilder builder) {
		DeleteByQueryRequest request = new DeleteByQueryRequest(index);
		request.setQuery(builder);
		// 设置批量操作数量,最大为10000
		request.setBatchSize(10000);
		request.setConflicts("proceed");
		try {
			client.deleteByQuery(request, RequestOptions.DEFAULT);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
    /**
     * yyyy-MM-dd
     * @param date
     * @return
     */
    public String dateToString(Date date) {
    	String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		try {
			if( date!=null ) {
				return simpleDateFormat.format(date);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
    }

    /**
     * yyyy.MM.dd格式
     * @param date
     * @return
     */
    public String dateToStringB(Date date) {
    	String dateFormat = "yyyy.MM.dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		try {
			if( date!=null ) {
				return simpleDateFormat.format(date);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
    }
    
    
    /**
     * yyyy-MM-dd HH:mm:ss格式
     * @param date
     * @return
     */
    public String dateToStringC(Date date) {
    	String dateFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		try {
			if( date!=null ) {
				return simpleDateFormat.format(date);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
    }
    
    /**
     * yyyy-MM-ddTHH:mm:ss.SSSZ格式
     * @param date
     * @return
     */
    public String dateToStringD(Date date) {
    	String dateFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		try {
			if( date!=null ) {
				String s =simpleDateFormat.format(date);
				s =s.replace(" ", "T");
				s =s + "000Z";
				return s;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
    }
    
    /**
     * yyyy-MM-ddTHH:mm:ss.SSSZ格式
     * @param date
     * @return
     */
    public String dateStringToStringD(String s) {
		s =s.replace(" ", "T");
		s =s + "000Z";
		return s;
    }
    
    /**
     * yyyy-MM-dd格式
     * @param date
     * @return
     */
    public String dateToStringE(Date date) {
    	String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		try {
			if( date!=null ) {
				String s =simpleDateFormat.format(date);
				return s;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
    }
    
    /**
     * yyyy-MM-dd HH:mm:ss格式减少8小时转为UTC字符串
     * @param date
     * @return
     */
    public String dateStringToStringUTC(String s) {
    	String dateFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		try {
			Date date =simpleDateFormat.parse(s);
			date =new Date(date.getTime()-1000*60*60*8);
			return simpleDateFormat.format(date);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
    }
    
}
