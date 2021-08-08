package com.midea.cloud.log.aop.es;

import com.midea.cloud.srm.model.log.useroperation.entity.LogDocument;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.*;



/**
 * es处理类,es7
 * @author lizl7
 *
 */
@Component
@Slf4j
public class EsLogHandler{

	   @Autowired
	   EsUtil esUtil;

	   /**
	    * 用于缓存当天的索引是否已经创建
	    */
	   Map<String, String> mapIndexByDay =new HashMap<String, String>();
	   
    /**
     * 保存支持es7
     * @param document
     * @throws Exception
     */
    public void saveLog(LogDocument document) throws Exception {
    	//判断是否已经存在当天索引
    	Date dateNow =new Date();
    	String index =esUtil.getIndexByDate(esUtil.Index_Pre_Logdocument, dateNow);
    	if( !mapIndexByDay.containsKey(index) ) {
    		//先查询没有存在，没有的话创建
        	if( !esUtil.indexExist(index) ) {
        		//新建
        		esUtil.createIndex(index);
        	}
    		mapIndexByDay.put(index, index);
    	}
    	//es的时间是用UTC存储，yyyy-MM-dd HH:mm:ss也当作UTC，所以存之前要用UTC存，查询是以UTC的值比较
    	document.setCreationDate(esUtil.dateStringToStringUTC(document.getOperationTime()));
    	//document.setOperationTime(esUtil.dateStringToStringD(document.getOperationTime()));
    	//document.setTimestamp(esUtil.dateToStringE(dateNow));
    	//document.setTimestamp(dateNow);
    	document.setTraceId(MDC.get("X-B3-TraceId"));
    	document.setSpanId(MDC.get("X-B3-SpanId"));
    	document.setParentSpanId(MDC.get("X-B3-ParentSpanId"));
    	//保存文档
    	esUtil.insert(index, document);
    }
    

}
