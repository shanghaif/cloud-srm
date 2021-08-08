package com.midea.cloud.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.midea.cloud.srm.model.flow.query.dto.FlowProcessQueryDTO;

import java.util.*;

/**
 * <pre>
 *  Json工具类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/10 18:01
 *  修改内容:
 * </pre>
 */
public class JsonUtil {
    /**
     * Description json字符转为Map
     * @Param strJson json字符
     * @return Map
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     * @throws
     **/
    public static Map parseJsonStrToMap(String strJson) {
        Map mapData = (Map) JSON.parseObject(strJson, Map.class);
        return mapData;
    }

    public static List<Map<String,Object>> jsonStrToListMap(String str){
        JSONArray jsonArray = JSONArray.parseArray(str);//把String转换为json
        List<Map<String, Object>> listMap=new ArrayList<Map<String, Object>>();
        for(int i=0;i<jsonArray.size();i++){
            Map<String, Object> map=new HashMap<String, Object>();
            JSONObject json=JSONObject.parseObject(jsonArray.get(i).toString());
            Set<String> setKey = json.keySet();
            for(String key : setKey){
                map.put(key, json.get(key));
            }
            listMap.add(map);
        }
        return listMap;
    }

    /**
     * Description 实体类转为json
     * @Param [ojbParamIn]
     * @return java.lang.String
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     * @throws
     **/
    public static String entityToJsonStr(Object ojbParamIn){
        String strTemp = JSON.toJSONString(ojbParamIn, SerializerFeature.WriteMapNullValue);
        return strTemp;
    }

    /**
     * Description 集合转为json格式字符
     * @Param List[listParamIn]
     * @return String
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     **/
    @SuppressWarnings("rawtypes")
    public static String arrayToJsonStr(List listParamIn){
        String strTemp = JSONArray.toJSONString(listParamIn, SerializerFeature.WriteMapNullValue);
        return strTemp;
    }

    /**
     * Description json字符转为List
     * @Param [strJson]
     * @return java.util.List
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     **/
    @SuppressWarnings("rawtypes")
    public static List parseJsonStrToList(String strJson){
        List listResult =(List)JSON.parseObject(strJson, List.class);
        return listResult;
    }

    /**
     * Description json转为List<T>
     * @Param [strJson, cls]
     * @return java.util.List
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     **/
    public static List parseJsonStrToList(String strJson,Class cls){
        List<Class> listResult =(List<Class>)JSON.parseArray(strJson, cls);
        return listResult;
    }

    /**
     * Description Object转为Map
     * @Param object 对象
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.11
     **/
    public static Map<String, Object> parseObjectToMap(Object object){
        return JSONObject.parseObject(JSONObject.toJSONString(object), Map.class);
    }

    /**
     * json字符串转对象
     * @param strParamIn
     * @param classParamIn
     * @return
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object parseJsonStrToEntity(String strParamIn,Class classParamIn){
		Object objTemp =JSONObject.parseObject(strParamIn, classParamIn);
		return objTemp;
	}
	
	
    public static void main(String[] args) {
        FlowProcessQueryDTO flowProcessQuery = new FlowProcessQueryDTO();
        flowProcessQuery.setFdId("111");
        flowProcessQuery.setFdModuleId("fdModuleId");
        flowProcessQuery.setIsAsc(false);
        flowProcessQuery.setFdStartDateBegin(30L);
        flowProcessQuery.setPage(10);
        Map<String, Object > map = parseObjectToMap(flowProcessQuery);
        for(Map.Entry<String, Object> mapResult : map.entrySet()){
            System.out.println("key: "+mapResult.getKey() +", value: "+mapResult.getValue());
        }

        String listStr = "{processIdList:[\"1276682997286879257\"]}";
        JsonUtil.parseJsonStrToMap(listStr);
        System.out.println("listStr:"+listStr);
    }

}
