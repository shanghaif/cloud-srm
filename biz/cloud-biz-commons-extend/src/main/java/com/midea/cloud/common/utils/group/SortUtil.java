package com.midea.cloud.common.utils.group;

import com.midea.cloud.srm.model.perf.scoring.PerfScoreManScoring;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * <pre>
 * 排序工具类
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
public class SortUtil<T> {

	String sortfield = "default";
	String sortOrder = DESC;
	Class sortfieldTypeClass = null;
	public static String ASC = "ASC";//降序排序
	public static String DESC = "DESC";//升序排序
	
	/**
	 * 对于已排序的集合，给出排列名次，对于相同名次的对象，下一个对象的名次是否连续
	 * @param list        要进行给出排列名次的集合
	 * @param objClazz    集合里的对象
	 * @param _sortField  按某字段排序
	 * @param _rankField  存储排列名次的字段，名字来源于实体类属性
	 * @param continu     相同名次的对象，下一个对象的名次是否连续
	 */
	public void sortRank(List<T> list, Class objClazz, String _sortField, String _rankField, boolean continu){
		
		if (continu) { // 1,2,3,4,5 这样连续排名
			int i = 1;
			for (T obj : list) {
				setValueByProperty(obj, _rankField, (long) i);
				i++;
			}
		}
		else          // 1,1,3,4,4,6 这样不连续排名
		{
			int i = 1;
			T pre = null;
			for (T obj : list) {
				
				if(i==1){
					setValueByProperty(obj,_rankField,1L);
				}else{
					
					if(getFieldValueByName(_sortField, obj).equals(getFieldValueByName(_sortField, pre))){//当前和前一名排名相同
						setValueByProperty(obj,_rankField, getFieldValueByName(_rankField, pre));//记录前一个记录的排名
						i++;
						continue;
					}
					setValueByProperty(obj,_rankField,(long)i);
					
				}
				i++;
				pre = obj;
			
			}
		}
		
	}
	
	/**
	 * 给出某个list集合, 对集合里的对象进行排序， 排序的字段和排序顺序可以配置
	 * @param list        要进行排序的集合
	 * @param _sortField  按某字段排序
	 * @param objClazz    集合里的对象
	 * @param _sortOrder  排序的顺序
	 */
	public void sortGeneral(List<T> list, String _sortField, Class objClazz, String _sortOrder) {
		sortfield = _sortField;
		sortfieldTypeClass = getFiledType(_sortField, objClazz);
		sortOrder = _sortOrder;
		if (!sortfieldTypeClass.getName().equals("java.lang.Long") && !sortfieldTypeClass.getName().equals("java.lang.Double")
			&& !sortfieldTypeClass.getName().equals("java.math.BigDecimal")) {
			throw new RuntimeException("排序字段类型要求是Long、Double或BigDecimal类型");
		}

		Comparator<T> comparator = new Comparator<T>() {
			public int compare(T s1, T s2) {

				if (getFieldValueByName(sortfield, s1) == null || getFieldValueByName(sortfield, s2) == null) {
					throw new RuntimeException("比较字段的值不能为空");
				}

				// 控制排序顺序 start
				T first = null;
				T second = null;
				if (DESC.equals(sortOrder)) { // 从大到小排，3,2,1
					first = s2;
					second = s1;
				} else if (ASC.equals(sortOrder)) { // 从小到大排，1,2,3
					first = s1;
					second = s2;
				}
				// 控制排序顺序 end

				if (!getFieldValueByName(sortfield, first).equals(getFieldValueByName(sortfield, second))) {
					if (("java.lang.Double").equals(sortfieldTypeClass.getName())) {
						return Double.compare((Double) getFieldValueByName(sortfield, first), (Double) getFieldValueByName(sortfield, second));
					}else if(("java.math.BigDecimal").equals(sortfieldTypeClass.getName())){
						return ((BigDecimal)getFieldValueByName(sortfield, first)).compareTo((BigDecimal) getFieldValueByName(sortfield, second));
					}
					return Double.compare((Long) getFieldValueByName(sortfield, first), (Long) getFieldValueByName(sortfield, second));
				} else {
					return 0;
				}
			}
		};

		Collections.sort(list, comparator);

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
	 * 根据属性名获取属性类型
	 * */
	public Class<?> getFiledType(String field, Class classParam) {
		Field[] fields = classParam.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if(fields[i].getName().equals(field)){
				return fields[i].getType();
			}
		}
		return null;
	}
	
	/**
	 * 对象任意属性赋值
	 * 对象要求有get,set方法
	 */
	public Object setValueByProperty(Object objMain,String property,Object objValue) {
		try{
			
			Method[] arrayMethodsOut =objMain.getClass().getMethods();
			Map<String,Method> mapMethodsOut =new HashMap<String, Method>(); 
			for( int i=0;i<arrayMethodsOut.length;i++ ){
				mapMethodsOut.put(arrayMethodsOut[i].getName(), arrayMethodsOut[i]);
			}
			
			String sMethodNameSet = "set" + this.formatFirstUpper(property);
			Method methodTempSet =mapMethodsOut.get( sMethodNameSet );
			if( methodTempSet!=null ){
				try{
					//避免系统的Set方法,影响其他属性
					methodTempSet.invoke(objMain, new Object[]{objValue});
				}catch (Exception e) {
					e.printStackTrace();
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String formatFirstUpper(String sValue){
		String sResult =sValue.substring(0,1).toUpperCase()+sValue.substring(1);
		return sResult;
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		//构造测试例子start
		SortUtil<PerfScoreManScoring> testSort = new SortUtil<PerfScoreManScoring>();
		PerfScoreManScoring t1 = new PerfScoreManScoring();
		PerfScoreManScoring t2 = new PerfScoreManScoring();
		PerfScoreManScoring t3 = new PerfScoreManScoring();
		PerfScoreManScoring t4 = new PerfScoreManScoring();
/*		t1.setPefTotalScoreId(1L);
		t1.setBuyerCompanyId(1L);
		t1.setTotalScore(10L);
		t2.setPefTotalScoreId(2L);
		t2.setBuyerCompanyId(1L);
		t2.setTotalScore(5L);
		t3.setPefTotalScoreId(3L);
		t3.setBuyerCompanyId(1L);
		t3.setTotalScore(20L);
		t4.setPefTotalScoreId(4L);
		t4.setBuyerCompanyId(1L);
		t4.setTotalScore(20L);*/
		
		List<PerfScoreManScoring> list = new ArrayList<PerfScoreManScoring>();
		list.add(t1);
		list.add(t2);
		list.add(t3);
		list.add(t4);
		//构造测试例子end
		
		//对任意对象集合进行指定字段排序
		testSort.sortGeneral(list, "totalScore", PerfScoreManScoring.class, DESC);
		
		//对于上述已排序的集合，排列名次
		testSort.sortRank(list, PerfScoreManScoring.class, "totalScore", "totalScoreRanking", false);

		//输出结果
		for(Object t:list){
/*		System.out.println(((PerfScoreManScoring)t).getPefTotalScoreId() + "," + ((PerfScoreManScoring)t).getTotalScore()
							+ "," + ((PerfScoreManScoring)t).getTotalScoreRanking()
				);*/
			System.out.println(((PerfScoreManScoring)t).getScoreManScoringId() + "," + ((PerfScoreManScoring)t).getScore()
					+ "," + ((PerfScoreManScoring)t).getCategoryId()
			);
		}
	}
	
	
/*	public void sort(List<PerfScoreManScoring> list){
		
		Comparator<PerfScoreManScoring> comparator = new Comparator<PerfScoreManScoring>() {
			public int compare(PerfScoreManScoring s1, PerfScoreManScoring s2) {
				
				if (!s1.getTotalScore().equals(s2.getTotalScore())) {
					return Double.compare(s2.getTotalScore(), s1.getTotalScore()); // 从大到小排，3,2,1
				} else {
					return 0;
				}
			}
		};

		Collections.sort(list, comparator);
		
	}*/
}
