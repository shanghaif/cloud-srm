package com.midea.cloud.api.inter.common;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebServiceUtil {
	
	
	public static Object getType(Object value , Class clazz) throws InstantiationException, IllegalAccessException {
		if (null == value) {
			return null;
		} else if ("java.lang.Long".equals(clazz.getName())) {
			if (null == value || value.toString().isEmpty()) {
				return null;
			}
			return Long.valueOf(value.toString());
		} else if ("java.lang.String".equals(clazz.getName())) {
			return value.toString();
		} else if ("java.lang.Integer".equals(clazz.getName())) {
			return Integer.valueOf(value.toString());
		} else if ("java.lang.Double".equals(clazz.getName())) {
			return Double.valueOf(value.toString());
		} else if ("java.math.BigDecimal".equals(clazz.getName())) {
			return new BigDecimal(value.toString());
		} else if ("java.util.Date".equals(clazz.getName())) {
			try {
				return new SimpleDateFormat("yyyy-MM-dd").parse(value.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if ("java.sql.Timestamp".equals(clazz.getName())) {
			try {
				return new Timestamp( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.toString()).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	
	public static boolean isBaseType(Class clazz) throws InstantiationException, IllegalAccessException {
		if ("java.lang.Long".equals(clazz.getName())) {
			return true;
		} else if ("java.lang.String".equals(clazz.getName())) {
			return true;
		} else if ("java.lang.Integer".equals(clazz.getName())) {
			return true;
		} else if ("java.lang.Double".equals(clazz.getName())) {
			return true;
		} else if ("java.math.BigDecimal".equals(clazz.getName())) {
			return true;
		} else if ("java.util.Date".equals(clazz.getName())) {
			return true;
		} else if ("java.sql.Timestamp".equals(clazz.getName())) {
			return true;
		}
		return false;
	}
	
	
	public static String getType(String type) {
		switch (type) {
		case "Long":
			return "java.lang.Long";
		case "String":
			return "java.lang.String";
		case "Integer":
			return "java.lang.Integer";
		case "Double":
			return "java.lang.Double";
		case "Date":
			return "java.util.Date";
		case "DATE":
			return "java.util.Date";
		case "LocalDate":
			return "java.time.LocalDate";
		case "DATETIME":
			return "java.sql.Timestamp";
		case "LIST":
			return "java.util.List";
		case "List":
			return "java.util.List";
		case "List_Long":
			return "java.util.List";
		case "Map":
			return "java.util.Map";
		case "BIGINT":
			return "java.lang.Long";
		case "VARCHAR":
			return "java.lang.String";
		case "MEDIUMTEXT":
			return "java.lang.String";
		case "BigDecimal":
			return "java.math.BigDecimal";
		default:
			System.out.println(type);
			return null;
		}
	}
	
	
	/**
	 * 反射获取对象
	 * @param obj
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> getObjectToMap(Object obj,Map<String,Object> map) throws Exception {
		Field [] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			field.setAccessible(true);
			Object value = field.get(obj);
			if (!isBaseType(field.getType())) {
				Class clazz = field.getType();
				if (clazz.isArray()) {
					List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
					for (int i=0;i<Array.getLength(value);i++) {
						list.add(getObjectToMap(value,new HashMap<String,Object>()));
					}
					map.put(name, list);
				} else {
					map.put(name, getObjectToMap(value,new HashMap<String,Object>()));
				}
			} else {
				map.put(name, value);
			}
		}
		return map;
	}
}
