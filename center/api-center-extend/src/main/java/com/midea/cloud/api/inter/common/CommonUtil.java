package com.midea.cloud.api.inter.common;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceColumn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {
	
	
	/**
	 * 判断是否默认
	 * @param converMap
	 * @param result
	 * @param column
	 */
	public static void setValue(Map<String,Object> converMap,Object value,InterfaceColumn column) {
		if (null != column.getDefaultValue() && !column.getDefaultValue().isEmpty() 
				&& (null == value || value.toString().isEmpty())) {
			value = column.getDefaultValue();
		}
		column.setValue(value);
	}
	
	/**
	 * 判断是否默认
	 * @param converMap
	 * @param result
	 * @param column
	 */
	public static void setMapValue(Map<String,Object> converMap,Object value,InterfaceColumn column) {
		if (null != column.getDefaultValue() && !column.getDefaultValue().isEmpty() 
				&& (null == value || value.toString().isEmpty())) {
			value = column.getDefaultValue();
		}
		if (null != column.getConverType() && !column.getConverType().isEmpty()) {
			
			if (StringUtil.isNotEmpty(column.getConverName())) {
				converMap.put(column.getConverName(), convertColumn(column.getColumnDesc(), value, column.getConverType()));
			} else {
				converMap.put(column.getColumnName(), convertColumn(column.getColumnDesc(), value, column.getConverType()));
			}
		} else {
			if (StringUtil.isNotEmpty(column.getConverName())) {
				converMap.put(column.getConverName(), convertColumn(column.getColumnDesc(), value, column.getColumnType()));
			} else {
				converMap.put(column.getColumnName(), convertColumn(column.getColumnDesc(), value, column.getColumnType()));
			}
		}
	}
	
	
	public static Object convertColumn (String columnDesc,Object value,String converyType) {
		try {
			if (null == value || value.toString().isEmpty()) {
				return value;
			}
			if (null == converyType || converyType.isEmpty()) {
				return value;
			}
			if (converyType.equals("Timestamp")) {
				String temp = value.toString();
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(temp);
				return new Timestamp(date.getTime());
			} else if (converyType.equals("Date")) {
				String temp = value.toString();
				return new SimpleDateFormat("yyyy-MM-dd").parse(temp);
			} else if (converyType.equals("Long")) {
				return Long.valueOf(value.toString());
			} else if (converyType.equals("String")) {
				return value.toString();
			} else if (converyType.equals("Integer")) {
				return Integer.valueOf(value.toString());
			} else if (converyType.equals("BigDecimal")) {
				return new BigDecimal(value.toString());
			} else if (converyType.equals("List_Long")) {
				String [] values = value.toString().split(",");
				return  values;
			} else if (converyType.equals("MultipartFile")) {
				return value;
			}else {
				return value.toString();
			}
		} catch (Exception e) {
			throw new BaseException(ResultCode.UNKNOWN_ERROR,columnDesc+"转换"+converyType+"类型失败");
		}
	}


	
	//遍历Map获取字段
	public static void mapToColumn(Map<String,Object> map,List<InterfaceColumn> columns, Long parentColumnId) {
		Iterator it = map.keySet().iterator();
		InterfaceColumn column = null;
		while (it.hasNext()) {
			String key = (String) it.next();
			Object value = map.get(key);
			String type = getObj(value);
			column = new InterfaceColumn();
			column.setParentId(parentColumnId);
			Long id = IdGenrator.generate();
			column.setColumnId(id);
			column.setColumnType(type);
			column.setColumnName(key);
			column.setColumnDesc(key);
			column.setSource("MANUAL");
			if (type.equals("Map")) {
				Map<String,Object> temp = (Map<String, Object>) value;
				mapToColumn(temp, columns, id);
			} else if (type.equals("LIST")) {
				List list = (List) value;
				if (null != list && list.size() > 0 ) {
					if (getObj(list.get(0)).equals("Map")) {
						mapToColumn((Map<String,Object>)list.get(0), columns, id);
					}
				}
			} else if (type.equals("JSONObject")) {
				column.setColumnType("Map");
				Map<String,Object> temp = JsonToMap((JSONObject)value);
				mapToColumn(temp, columns, id);
			} else if (type.equals("JSONArray")) {
				column.setColumnType("List");
				JSONArray array = (JSONArray)value;
				if (array.size() > 0) {
					Map<String,Object> temp = JsonToMap(array.getJSONObject(0));
					mapToColumn(temp, columns, id);
				}
			}
			columns.add(column);
		}
	}
	
	public static Map<String,Object> JsonToMap(JSONObject j){
	    Map<String,Object> map = new HashMap<>();
	    Iterator<String> iterator = j.keySet().iterator();
	    while(iterator.hasNext())
	    {
	        String key = (String)iterator.next();
	        Object value = j.get(key);
	        map.put(key, value);
	    }
	    return map;
	}
	
	public static String getObj(Object obj) {
		if (obj instanceof Integer) {
			return "Integer";
		} else if (obj instanceof String) {
			return "String";
		} else if (obj instanceof Long) {
			return "Long";
		} else if (obj instanceof Date) {
			return "Date";
		} else if (obj instanceof Timestamp) {
			return "Timestamp";
		} else if (obj instanceof BigDecimal) {
			return "BigDecimal";
		} else if (obj instanceof List) {
			return "LIST";
		} else if (obj instanceof Map) {
			return "Map";
		} else if (obj instanceof JSONObject) {
			return "JSONObject";
		} else if (obj instanceof JSONObject) {
			return "JSONArray";
		}
		return "String";
	}
	
	
	public static Object getTypeValue(String type,ResultSet rs,String columnName) {
		try {
			if (type.equals("String")) {
				return rs.getString(columnName);
			} else if (type.equals("Long")) {
				return rs.getLong(columnName);
			} else if (type.equals("Data")) {
				return rs.getDate(columnName);
			} else if (type.equals("BigDecimal")){
				return rs.getBigDecimal(columnName);
			}else {
				return rs.getString(columnName);
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * 获取字段父节点Map集合
	 * @param columns
	 * @param parentId
	 * @return
	 */
	public static List<InterfaceColumn> getColumnByParentId(List<InterfaceColumn> columns, Long parentId) {
		List<InterfaceColumn> temp = new ArrayList<InterfaceColumn>();
		if (null != columns && columns.size() > 0) {
			for (InterfaceColumn column : columns) {
				if (null != parentId) {
					if (null != column.getParentId()) {
						if (parentId.longValue() == column.getParentId().longValue()) {
							temp.add(column);
						}
					}
				} else {
					if (null == column.getParentId()) {
						temp.add(column);
					}
				}
			}
		}
		return temp;
	}
	
	public static Map<Long,List<InterfaceColumn>> getColumnMap(List<InterfaceColumn> columns) {
		Map<Long,List<InterfaceColumn>> map = new HashMap<Long,List<InterfaceColumn>>();
		List<InterfaceColumn> list = null;
		for (InterfaceColumn column:columns) {
			if (map.containsKey(column.getParentId())) {
				list = map.get(column.getParentId());
			} else {
				list = new ArrayList<InterfaceColumn>();
			}
			list.add(column);
			map.put(column.getParentId(), list);
		}
		return map;
	}
	
	/**
	 * 获取URL
	 * @param params
	 * @return
	 */
	public static String getUrl(List<InterfaceColumn> params) {
		StringBuffer sb = new StringBuffer();
		if (null != params && params.size() > 0) {
			for (InterfaceColumn param : params) {
				if (null != param.getValue()) {
					if (sb.length()>0) {
						sb.append("&");
					}
					if (null != param.getConverName() && !param.getConverName().isEmpty()) {
						sb.append(param.getConverName()).append("=").append(param.getValue());
					} else {
						sb.append(param.getColumnName()).append("=").append(param.getValue());
					}
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 获取FORM
	 * @param params
	 * @return
	 */
	public static MultiValueMap getForm(List<InterfaceColumn> params) {
		MultiValueMap map = new LinkedMultiValueMap();
		if (null != params && params.size() > 0) {
			for (InterfaceColumn param : params) {
				if (null != param.getValue()) {
					if (null != param.getConverName() && !param.getConverName().isEmpty()) {
						map.add(param.getConverName(), param.getValue());
					} else {
						map.add(param.getColumnName(), param.getValue());
					}
				}
			}
		}
		return map;
	}
	
	
	
	/**
	 * 获取参数替换后的sql语句
	 */
	public static String getSql(String sql, List<InterfaceColumn> params) {
		if (!sql.isEmpty() && params.size() > 0) {
			for (InterfaceColumn param : params) {
				if (sql.indexOf("#" + param.getColumnName()) != -1) {
					if (null != param.getColumnType() && param.getColumnType().equals("String")) {
						if (null == param.getValue()) {
							sql = sql.replace("#" + param.getColumnName(), "");
						} else {
							sql = sql.replace("#" + param.getColumnName(), "'" + param.getValue() + "'");
						}
					} else if (null != param.getColumnType() && param.getColumnType().equals("Long")) {
						if (null == param.getValue()) {
							sql = sql.replace("#" + param.getColumnName(), "");
						} else {
							sql = sql.replace("#" + param.getColumnName(), param.getValue().toString());
						}
					}
				}
			}
		}
		return sql;
	}
	
	
	/**
	 * 累加参数
	 */
	public static List<InterfaceColumn>  getParamByMap(Map<String,Object> map,List<InterfaceColumn> params) {
		List<InterfaceColumn> resultList = new ArrayList<InterfaceColumn>();
		if (null != params && params.size() > 0) {
			resultList.addAll(params);
		}
		if (null != map) {
			Iterator it = map.keySet().iterator();
			InterfaceColumn p = null;
			while(it.hasNext()) {
				String key = (String) it.next();
				Object value = map.get(key);
				p = new InterfaceColumn();
				p.setColumnName(key);
				if (value instanceof Long) {
					p.setColumnType("Long");
				} else if (value instanceof String) {
					p.setColumnType("String");
				} else if (value instanceof Double) {
					p.setColumnType("Double");
				} else if (value instanceof Integer) {
					p.setColumnType("Integer");
				} else if (value instanceof Date) {
					p.setColumnType("Date");
				} else if (value instanceof Timestamp) {
					p.setColumnType("Timestamp");
				} else if (value instanceof BigDecimal) {
					p.setColumnType("BigDecimal");
				}
				p.setValue(value);
				resultList.add(p);
			}
		}
		return resultList;
	}
	
	
	/**
	 * 过滤相同的参数
	 * @param values
	 * @param parent
	 * @return
	 */
	public static List<InterfaceColumn> getParam(List<InterfaceColumn> values ,List<InterfaceColumn> parent) {
		List<InterfaceColumn> list = new ArrayList<InterfaceColumn>();
		if (null != parent && parent.size() > 0) {
			for (InterfaceColumn dto : parent) {
				Boolean flag = false;
				if (null != values && values.size() > 0) {
					for (InterfaceColumn temp : values) {
						if (dto.getColumnName().equals(temp.getColumnName())) {
							flag = true;
							break;
						}
					}
					if(!flag) {
						list.add(dto);
					}
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 判断是否默认
	 * @param converMap
	 * @param result
	 * @param column
	 */
	public static void setValueToMap(Map<String,Object> converMap,Object value,InterfaceColumn column) {
		if (null != column.getDefaultValue() && !column.getDefaultValue().isEmpty() 
				&& (null == value || value.toString().isEmpty())) {
			value = column.getDefaultValue();
		}
		if (null != column.getConverType() && !column.getConverType().isEmpty()) {
			
			if (StringUtil.isNotEmpty(column.getConverName())) {
				converMap.put(column.getConverName(), convertColumn(column.getColumnDesc(), value, column.getConverType()));
			} else {
				converMap.put(column.getColumnName(), convertColumn(column.getColumnDesc(), value, column.getConverType()));
			}
		} else {
			if (StringUtil.isNotEmpty(column.getConverName())) {
				converMap.put(column.getConverName(), convertColumn(column.getColumnDesc(), value, column.getColumnType()));
			} else {
				converMap.put(column.getColumnName(), convertColumn(column.getColumnDesc(), value, column.getColumnType()));
			}
		}
	}
}
