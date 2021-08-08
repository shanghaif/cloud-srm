package com.midea.cloud.common.utils.group;


import com.midea.cloud.srm.model.perf.scoring.PerfScoreManScoring;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

/**
 * <pre>
 * 分组工具类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/6/15
 *  修改内容:
 * </pre>
 */
@Component
public class GroupUtil<T> {

	private String formatGroupKeyByFields(T obj, List<String> fields) {
		StringBuilder strBu =  new StringBuilder();
		for(String field : fields){
			strBu.append(getFieldValueByName(field,obj)).append("-");
		}
		return strBu.toString();
	}

	/**
	 * Description 集合分组
	 * @param list   // 需要分组排序的集合
	 * @param ObjClazz  // 集合里的对象
	 * @param fields // 分组字段
	 * @return
	 * @Author wuwl18@meicloud.com
	 * @Date 2020.06.15
	 **/
	public Map<String,List<T>> groupListByFields(List<T> list, Class ObjClazz, List<String> fields){
		Map<String,List<T>> resultMap = new HashMap<String,List<T>>();
		String groupKey = null;
		for(T obj : list){ 
			groupKey = formatGroupKeyByFields(obj,fields);
			if (resultMap.containsKey(groupKey)) {
				resultMap.get(groupKey).add(obj);
			} else {
				List<T> objectList = new ArrayList<T>();
				objectList.add(obj);
				resultMap.put(groupKey, objectList);
			}
		}
		return resultMap;
	}
	
	/**
	 * 根据属性名获取属性值
	 * */
	public Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GroupUtil groupUtil = new GroupUtil();
//		groupUtil.testGroupListByFields();

	}

	public void testGroupListByFields(){
		//构造测试例子start
		GroupUtil<PerfScoreManScoring> testGroup = new GroupUtil<PerfScoreManScoring>();
		PerfScoreManScoring t1 = new PerfScoreManScoring();
		PerfScoreManScoring t2 = new PerfScoreManScoring();
		PerfScoreManScoring t3 = new PerfScoreManScoring();
		PerfScoreManScoring t4 = new PerfScoreManScoring();
/*		t1.setPefTotalScoreId(1L);
		t1.setBuyerCompanyId(1L);
		t1.setOrganizationId(9L);
		t1.setTotalScore(10L);
		t2.setPefTotalScoreId(2L);
		t2.setBuyerCompanyId(2L);
		t2.setOrganizationId(9L);
		t2.setTotalScore(5L);
		t3.setPefTotalScoreId(3L);
		t3.setBuyerCompanyId(1L);
		t3.setOrganizationId(9L);
		t3.setTotalScore(20L);
		t4.setPefTotalScoreId(4L);
		t4.setBuyerCompanyId(1L);
		t4.setOrganizationId(9L);
		t4.setTotalScore(20L);*/

		List<PerfScoreManScoring> list = new ArrayList<PerfScoreManScoring>();
		list.add(t1);
		list.add(t2);
		list.add(t3);
		list.add(t4);
		//构造测试例子end


		System.out.println("===================通用分组=======================");

		List<String> fields = new LinkedList<String>();
		fields.add("buyerCompanyId");//指定分组字段
		fields.add("organizationId");//指定分组字段
		Map<String, List<PerfScoreManScoring>> rstMap2 = testGroup.groupListByFields(list,PerfScoreManScoring.class,fields);
		for( Map.Entry<String,List<PerfScoreManScoring>> entryTemp : rstMap2.entrySet()){
			System.out.println(entryTemp.getKey());
			for(PerfScoreManScoring obj : entryTemp.getValue()){
				System.out.println(testGroup.getFieldValueByName("pefTotalScoreId", obj));
			}
		}

		System.out.println("==================分组后排序========================");
		SortUtil<PerfScoreManScoring> testSort = new SortUtil<PerfScoreManScoring>();
		for( Map.Entry<String,List<PerfScoreManScoring>> entryTemp : rstMap2.entrySet()){
			System.out.println("组别："+entryTemp.getKey());

			//对每组进行排序start
			List<PerfScoreManScoring> objList = entryTemp.getValue();
			testSort.sortGeneral(objList, "totalScore", PerfScoreManScoring.class, SortUtil.DESC);
			testSort.sortRank(objList, PerfScoreManScoring.class, "totalScore", "totalScoreRanking", false);
			//对每组进行排序end

			//输出结果start
			for(PerfScoreManScoring t:objList){
/*			System.out.println(((PerfScoreManScoring)t).getPefTotalScoreId() + "," + ((PerfScoreManScoring)t).getTotalScore()
								+ "," + ((PerfScoreManScoring)t).getTotalScoreRanking()
					);*/
				System.out.println(((PerfScoreManScoring)t).getScoreManScoringId() + "," + ((PerfScoreManScoring)t).getScore()
						+ "," + ((PerfScoreManScoring)t).getCategoryId()
				);
			}
			//输出结果end
		}
	}

}
