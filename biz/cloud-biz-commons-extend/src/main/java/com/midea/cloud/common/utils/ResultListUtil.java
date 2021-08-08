package com.midea.cloud.common.utils;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.common.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

public class ResultListUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(ResultListUtil.class);
	
	/**
	 * #将sourceList的对象source的部分字段值（requireFieldMap中配置）copy到targetList的对象target中
	 * #注意：这里sourceList和targetList的元素只支持复制【本类】里的方法和属性，不支持【父类】的方法和属性，如需复制父类的属性，请在子类上覆盖需要复制的属性并覆写其getter、setter方法
	 * @param targetList 结果集
	 * @param sourceList 数据来源集
	 * @param searchKeyMap 结果集和数据来源集匹配的对应字段key为结果集的字段，value为数据来源集的字段
	 * @param requireFieldMap 数据来源集中的某些字段，需要将这些字段copy到结果集上
	 * <p><pre class="code">例子：
	 * List<TspPurOrder> result = xxx;// 结果集
	 * List<TspBdaVbCompanys> companyList = xxx; // 数据来源集
	 * String dataId = "vendorCompanyId";// 结果集用于跟数据来源集的sourceId匹配的字段名
	 * String sourceId = "companyId";// 数据来源集用于跟结果集的dataId匹配的字段名
	 * HashMap<String, String> requireFieldMap = new HashMap<String, String>();// 需要复制的字段集合
	 * requireFieldMap.put("vendorCompanyName", "companyName");
	 * HashMap<String, String> searchKeyMap = new HashMap<String, String>();// 结果集合数据来源集匹配的对应字段key为结果集的字段，value为数据来源集的字段
	 * searchKeyMap.put("vendorCompanyId", "companyId");
	 * ResultListUtil.copyRequireFields(result, companyList, searchKeyMap, requireFieldMap)
	 * </pre>
	 */
	public static <E, T> List<E> copyRequireFields(List<E> targetList, List<T> sourceList, Map<String, String> searchKeyMap, Map<String, String> requireFieldMap) throws Exception {
		searchKeyMap.forEach((key, value) -> {
			try {
				copyRequireFields(targetList, key, sourceList, value, requireFieldMap);
			} catch (Exception e) {
				logger.error("copyRequireFields ERROR", e);
			}
		});
		return null;
	}
	
	/**
	 * #将sourceList的对象source的部分字段值（requireFieldMap中配置）copy到targetList的对象target中，其中target的dataId字段的值和source的sourceId字段的值相等
	 * #注意：这里sourceList和targetList的元素只支持复制【本类】里的方法和属性，不支持【父类】的方法和属性，如需复制父类的属性，请在子类上覆盖需要复制的属性并覆写其getter、setter方法
	 * @param targetList 结果集
	 * @param dataId 结果集中用于匹配的字段名
	 * @param sourceList 数据来源集
	 * @param sourceId 数据来源集中用于匹配的字段名
	 * @param requireFieldMap 数据来源集中的某些字段，需要将这些字段copy到结果集上
	 * <p><pre class="code">例子：
	 * List<TspPurOrder> result = xxx;// 结果集
	 * List<TspBdaVbCompanys> companyList = xxx; // 数据来源集
	 * String dataId = "vendorCompanyId";// 结果集用于跟数据来源集的sourceId匹配的字段名
	 * String sourceId = "companyId";// 数据来源集用于跟结果集的dataId匹配的字段名
	 * HashMap<String, String> requireFieldMap = new HashMap<String, String>();// 需要复制的字段集合
	 * requireFieldMap.put("companyName","vendorCompanyName");  //companyName结果集对应名称, vendorCompanyName数据来源对应名称
	 * ResultListUtil.copyRequireFields(result, dataId, companyList, sourceId, coverMap);
	 * </pre>
	 */
	public static <E, T> List<E> copyRequireFields(List<E> targetList, String dataId, List<T> sourceList, String sourceId, Map<String, String> requireFieldMap) throws Exception {
		// 结果集和数据来源集不能为空
		if (targetList == null || targetList.size() == 0 || sourceList == null || sourceList.size() == 0) {
			return targetList;
		}
		// 结果集和数据来源集的数据类型必须是HashMap、BaseDTO、BaseEntity
		if (!checkListType(targetList) || !checkListType(sourceList)) {
			logger.error("copyRequireFields ERROR","E & T must be HashMap or BaseEntity or BaseDTO!");
			return targetList;
		}
		// 循环处理结果集，将对应的source在requireFieldMap上配置的属性值复制到结果集对象target上
		for (E data : targetList) {
			if (data instanceof HashMap) {// 结果集的数据类型如果是HashMap则进入此分支
				// 构造HashMap Get方法
				Method dataGetMethod = data.getClass().getMethod("get", Object.class);
				// 获取结果集对象target的dataId字段关联的值 
				Object dataIdVal = dataGetMethod.invoke(data, dataId);
				// 获取data 关于 id字段 相同的resoure( 将dataIdVal与数据来源集对象source的sourceId字段关联的值做比较，如果一样则表示匹配，则获得该匹配的对象 )
				T resource = getById(sourceList, sourceId, dataIdVal);
				if (resource == null) {
					continue;
				}
				// 构造HashMap Put方法
				Method dataPutMethod = data.getClass().getMethod("put", Object.class, Object.class);
				// 循环将需要复制的属性值从数据来源集copy到结果集上
				requireFieldMap.forEach((key, value) -> {
					try {
						if (resource instanceof HashMap) {// 数据来源集的数据类型如果是HashMap则进入此分支
							// 构造数据来源集对象的get方法用于获取数据来源集的对应字段的数据
							Method resourceGetMethod = resource.getClass().getMethod("get", Object.class);
							// 将数据来源集的对应字段的数据copy到结果集对应字段上
							dataPutMethod.invoke(data, resourceGetMethod.invoke(resource, value));
						} else if (resource instanceof BaseEntity || resource instanceof BaseDTO) {// 数据来源集的数据类型如果是BaseEntity/BaseDTO则进入此分支
							// 构造数据来源集对象的get方法用于获取数据来源集的对应字段的数据
							Method resourceGetMethod = resource.getClass().getMethod(getGetterName(value));
							// 将数据来源集的对应字段的数据copy到结果集对应字段上
							dataPutMethod.invoke(data, key, resourceGetMethod.invoke(resource));
						}
					} catch (Exception e) {
						logger.error("resource Entity must has get or getter or set or setter method", e);
					}
				});
			} else if (data instanceof BaseDTO || data instanceof BaseEntity) {// 结果集的数据类型如果是BaseEntity/BaseDTO则进入此分支
				// 构造结果集Get方法
				Method dataGetMethod = data.getClass().getMethod(getGetterName(dataId));
				// 获取结果集对象的dataId的字段值
				Object dataIdVal = dataGetMethod.invoke(data);
				// 获取跟结果集的dataId的字段值 与 数据来源集的sourceId的字段值值相同的source对象
				T resource = getById(sourceList, sourceId, dataIdVal);
				if (resource == null) {
					continue;
				}
				// 循环将需要复制的属性值从数据来源集copy到结果集上
				requireFieldMap.forEach((key, value) -> {
					try {
						// 获取结果集上对应字段的数据类型
						String typeName = data.getClass().getDeclaredField(key).getGenericType().getTypeName();
						// 通过上述类型构造该字段的setter方法
						Method dataPutMethod = data.getClass().getMethod(getSetterName(key), Class.forName(typeName));
						if (resource instanceof HashMap) {// 数据来源集的数据类型如果是HashMap则进入此分支
							// 构造数据来源集对象的字段的getter方法用于获取该字段的值
							Method resourceGetMethod = resource.getClass().getMethod("get", Object.class);
							// 利用上述方法和结果集的setter方法将对应字段的值从数据来源集copy到结果集上
							dataPutMethod.invoke(data, resourceGetMethod.invoke(resource, value));
						} else if (resource instanceof BaseEntity || resource instanceof BaseDTO) {// 数据来源集的数据类型如果是BaseEntity/BaseDTO则进入此分支
							// 构造数据来源集对象的字段的getter方法用于获取该字段的值
							Method resourceGetMethod = resource.getClass().getMethod(getGetterName(value));
							// 利用上述方法和结果集的setter方法将对应字段的值从数据来源集copy到结果集上
							dataPutMethod.invoke(data, resourceGetMethod.invoke(resource));
						}
					} catch (Exception e) {
						logger.error("resource Entity must has get or getter or set or setter method", e);
					}
				});
			}
		}
		return targetList;
	}
	
	/**
	 * #检查列表项的数据类型必须为BaseDTO、BaseEntity、HashMap
	 * @param targetList 需要检查类型的泛型列表
	 */
	private static <E> boolean checkListType(List<E> targetList) {
		E target = targetList.get(0);
		if (target instanceof BaseDTO || target instanceof BaseEntity || target instanceof HashMap) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * #获取列表的resourceId字段的值与dataIdVal相同的第一个列表项
	 * @param resourceList 数据列表
	 * @param resourceId 数据列表的对比字段
	 * @param dataIdVal 对比的值
	 */
	private static <T, E> T getById(List<T> resourceList, String resourceId, Object dataIdVal) {
		Optional<T> optional = resourceList.stream().filter(resource -> {
			try {
				if (resource instanceof HashMap) {
					// 构造HashMap Get方法
					Method resourceGetMethod = resource.getClass().getMethod("get", Object.class);
					// 获取resource的id字段关联的值
					Object resourceIdVal = resourceGetMethod.invoke(resource, resourceId);
					if (dataIdVal.equals(resourceIdVal)) {
						return true;
					} else {
						return false;
					}
				} else if (resource instanceof BaseEntity || resource instanceof BaseDTO) {
					// 构造getter方法
					Method resourceGetMethod = resource.getClass().getMethod(getGetterName(resourceId));
					// 获取id值
					Object resourceIdVal = resourceGetMethod.invoke(resource);
					Long dataIdLongVal = null;
					// 兼容BigDecimal与Long的比较
					if (dataIdVal instanceof BigDecimal) {
						BigDecimal bigValue = (BigDecimal)dataIdVal;
						dataIdLongVal = bigValue.longValue();
					}

					if (dataIdVal.equals(resourceIdVal) || resourceIdVal.equals(dataIdLongVal)) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			} catch (Exception e) {
				logger.error("resource Entity must has get or getter or set or setter method", e);
				return false;
			}
		}).findFirst();
		return optional.isPresent() ? optional.get() : null;
	}
	
	private static String getGetterName(String idName) {
		return "get" + idName.substring(0, 1).toUpperCase() + idName.substring(1);
	}
	
	private static String getSetterName(String idName) {
		return "set" + idName.substring(0, 1).toUpperCase() + idName.substring(1);
	}
	
	/**
	 * 获取列表的某个字段的合集
	 */
	public static <E, T> List<T> getAttributeList(List<E> itemList, String attrName) throws Exception {
		List<T> attrList = new ArrayList<T>();
		for (E item : itemList) {
			Method getMethod = item.getClass().getMethod(getGetterName(attrName));
			// 获取id值
			Object val = getMethod.invoke(item);
			attrList.add((T) val);
		}
		return attrList;
	}
}
