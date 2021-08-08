package com.midea.cloud.api.inter.service.impl;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.springframework.stereotype.Service;

import com.midea.cloud.api.inter.common.WebServiceUtil;
import com.midea.cloud.api.inter.service.IInterService;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.utils.JsonUtil;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceColumn;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceConfig;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceParam;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WebserviceGenerator {

	private static String TARGET_NAME_SPACE = "http://www.aurora-framework.org/schema";

	private static String PATH = "com.midea.cloud.api.soap.webservice.service";
	
	private String getPath(String className) {
		return PATH + "." + className + ".";
	}
	public static ClassPool POOL =  ClassPool.getDefault();
	
	static {
		//添加当前线程的ClassPath 到动态字节中
		POOL.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
	}
	
	static Map<String,JaxWsServerFactoryBean> factoryBeanMap = new HashMap<String,JaxWsServerFactoryBean>();
	static Map<String,Server> serverMap = new HashMap<String,Server>();
	
	public Class<?> createDynamicClazz(String path,String className, String method, Class param, Class result, String type)
			throws Exception {
		String serviceName = "";
		if (className.toLowerCase().indexOf("service") == -1) {
			serviceName = className + "ServiceImpl";
		}
		
		
		Class service = this.createDynamicService(path,className, method, param, result,type);
		// 创建类
		CtClass cc = null;
		boolean flag = false;
		cc = POOL.makeClass(path + serviceName);
		// 实现接口
		cc.addInterface(POOL.get(service.getName()));
		
		// 创建方法
		CtClass p = POOL.get(param.getName());
		CtClass r = null;
		if (null != result) {
			r = POOL.get(result.getName());
		}
		CtMethod ctMethod = null;
		StringBuffer body = new StringBuffer();
		if ("HTTP".equals(type)) {
			// 参数： 1：返回类型 2：方法名称 3：传入参数类型 4：所属类CtClass
			ctMethod = new CtMethod(r, method, new CtClass[] { p }, cc);
			ctMethod.setModifiers(Modifier.PUBLIC);
			body.append("{");
			body.append("\n   return " + WebserviceGenerator.class.getName() + ".receiveCommon(\"" + className + "\",$1);");
			body.append("\n}");
			ctMethod.setBody(body.toString());
		}  else if ("HTTP_LIST".equals(type)) {
			if (null != p) {
				ctMethod = CtMethod
						.make("public " + r.getName() + "  " + method + "( " + p.getName() + " []  params ); ", cc);
				body.append("{");
				body.append("\n   return " + WebserviceGenerator.class.getName() + ".receiveCommon(\"" + className + "\",$1);");
				body.append("\n}");
			} else {
				ctMethod = CtMethod
						.make("public " + r.getName() + " " + method + "( " + p.getName() + " []  params ); ", cc);
				body.append("{");
				body.append("\n   return " + WebserviceGenerator.class.getName() + ".receiveCommon(\"" + className + "\",$1);");
				body.append("\n}");
			}
			ctMethod.setBody(body.toString());
		} else if ("SQL_MAP".equals(type)) {
			// 参数： 1：返回类型 2：方法名称 3：传入参数类型 4：所属类CtClass
			ctMethod = CtMethod
					.make("public " + r.getName() + "  " + method + "( " + p.getName() + " params ); ", cc);
			body.append("{");
			body.append("\n try { ");
			body.append("\n "+IInterService.class.getName()+" localIInterService = ("+IInterService.class.getName()+")"+SpringContextHolder.class.getName()+".getBean("+IInterService.class.getName()+".class);");
			body.append("\n return (" + result.getName() + "  ) "+WebserviceGenerator.class.getName()+".mapToObject(localIInterService.apiInfoWebservice(\""+className+"\", $1).getData()," + result.getName() + ".class);");
			body.append("\n } catch(Exception e) { ");
			body.append("\n e.printStackTrace(); ");
			body.append("\n throw e; ");
			body.append("\n } ");
			body.append("\n}");
			ctMethod.setBody(body.toString());
		} else if ("SQL_LIST".equals(type)) {
			if (null != p) {
				ctMethod = CtMethod
						.make("public " + r.getName() + "[]   " + method + "( " + p.getName() + " params ); ", cc);
				body.append("{");
				body.append("\n try { ");
				body.append("\n "+IInterService.class.getName()+" localIInterService = ("+IInterService.class.getName()+")"+SpringContextHolder.class.getName()+".getBean("+IInterService.class.getName()+".class);");
				body.append("\n return (" + result.getName() + " [] ) "+WebserviceGenerator.class.getName()+".mapToObject(localIInterService.apiInfoWebservice(\""+className+"\", $1).getData()," + result.getName() + ".class);");
				body.append("\n } catch(Exception e) { ");
				body.append("\n e.printStackTrace(); ");
				body.append("\n throw e; ");
				body.append("\n } ");
				body.append("\n}");
				ctMethod.setBody(body.toString());
			} else {
				ctMethod = CtMethod.make("public " + r.getName() + "[]   " + method + "(); ", cc);
				body.append("{");
				body.append("\n "+IInterService.class.getName()+" localIInterService = ("+IInterService.class.getName()+")"+SpringContextHolder.class.getName()+".getBean("+IInterService.class.getName()+".class);");
				body.append("\n return (" + result.getName() + " [] ) "+WebserviceGenerator.class.getName()+".mapToObject(localIInterService.apiInfoWebservice(\""+className+"\", null).getData()," + result.getName() + ".class);");
				body.append("\n}");
				ctMethod.setBody(body.toString());
			}
		}
		//方法抛出异常
		ctMethod.setExceptionTypes(new CtClass[] {POOL.get("java.lang.Exception")});
		cc.addMethod(ctMethod);
		ClassFile ccFile = cc.getClassFile();
		ConstPool constPool = ccFile.getConstPool();
//		if (!flag) {
			// 添加类注解
			AnnotationsAttribute bodyAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
			Annotation bodyAnnot = new Annotation("javax.jws.WebService", constPool);
			bodyAnnot.addMemberValue("targetNamespace", new StringMemberValue(TARGET_NAME_SPACE, constPool));
			bodyAnnot.addMemberValue("endpointInterface", new StringMemberValue(service.getName(), constPool));
			bodyAttr.addAnnotation(bodyAnnot);
			Annotation componentAnnot = new Annotation("org.springframework.stereotype.Component", constPool);
			componentAnnot.addMemberValue("value", new StringMemberValue("i" + className + "Service", constPool));
			bodyAttr.addAnnotation(componentAnnot);
			ccFile.addAttribute(bodyAttr);
//		}
			
		//增加方法注解
		AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag); 
        Annotation annot = new Annotation("java.lang.Override", constPool); 
        attr.addAnnotation(annot); 
        ctMethod.getMethodInfo().addAttribute(attr);
        
		printClass(cc);
		return getClass(cc, flag);
	}

	public Class<?> createDynamicService(String path, String className, String method, Class param, Class result, String type)
			throws Exception {
		String serviceName = "";
		if (className.toLowerCase().indexOf("service") == -1) {
			serviceName = "I" + className + "Service";
		}
		
		boolean flag = false;
		CtClass cc = null;
		// 创建类
		cc = POOL.makeInterface(path + serviceName);
		
		// 创建方法
		CtClass p = POOL.get(param.getName());
		CtClass r = null;
		if (null != result) {
			r = POOL.get(result.getName());
			
			System.out.println("service:"+result.getName()+":"+r.getFields().length);
		}
		
		CtMethod ctMethod = null;
		if ("HTTP".equals(type)) {
			// 参数： 1：返回类型 2：方法名称 3：传入参数类型 4：所属类CtClass
			ctMethod = new CtMethod(r, method, new CtClass[] { p }, cc);
		}  else if ("HTTP_LIST".equals(type)) {
			if (null != p) {
				ctMethod = CtMethod
						.make("public " + r.getName() + " " + method + "( " + p.getName() + " []  params ); ", cc);
			} else {
				ctMethod = CtMethod
						.make("public " + r.getName() + " " + method + "( " + p.getName() + " []  params ); ", cc);
			}
		} else if ("SQL_MAP".equals(type)) {
			// 参数： 1：返回类型 2：方法名称 3：传入参数类型 4：所属类CtClass
			ctMethod = CtMethod.make("public " + r.getName() + "  " + method + "( " + p.getName() + " params ); ",cc);
		} else if ("SQL_LIST".equals(type)) {
			// 参数： 1：返回类型 2：方法名称 3：传入参数类型 4：所属类CtClass
			ctMethod = CtMethod.make("public " + r.getName() + "[]   " + method + "( " + p.getName() + " params ); ",cc);
			if (null != p) {
				ctMethod = CtMethod.make("public " + r.getName() + "[]   " + method + "( " + p.getName() + " params ); ", cc);
			} else {
				ctMethod = CtMethod.make("public " + r.getName() + "[]   " + method + "();", cc);
			}
		}
		//方法抛出异常
		ctMethod.setExceptionTypes(new CtClass[] {POOL.get("java.lang.Exception")});
		cc.addMethod(ctMethod);
		ClassFile ccFile = cc.getClassFile();
		ConstPool constPool = ccFile.getConstPool();
//		if (!flag) {
			// 添加类注解
			AnnotationsAttribute bodyAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
			Annotation bodyAnnot = new Annotation("javax.jws.WebService", constPool);
			bodyAnnot.addMemberValue("targetNamespace", new StringMemberValue(TARGET_NAME_SPACE, constPool));
			bodyAnnot.addMemberValue("name", new StringMemberValue("i" + className + "Service", constPool));
			bodyAttr.addAnnotation(bodyAnnot);
			ccFile.addAttribute(bodyAttr);
//		}
		//增加方法注解
		AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag); 
        Annotation annot = new Annotation("javax.jws.WebMethod", constPool); 
        annot.addMemberValue("action", new StringMemberValue("excute", constPool));
        attr.addAnnotation(annot); 
        ctMethod.getMethodInfo().addAttribute(attr);
		
		printClass(cc);
		return getClass(cc, flag);
	}

	public Class<?> createDynamicParam(String path ,String className, List<InterfaceColumn> list) throws Exception {
		boolean flag = false;
		// 创建类
		CtClass cc = null;
		cc = POOL.makeClass(path + className + "Param");
		// 增加接口
		cc.addInterface(POOL.get("java.io.Serializable"));
		ClassFile ccFile = cc.getClassFile();
		ConstPool constPool = ccFile.getConstPool();
		AnnotationsAttribute bodyAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
		Annotation bodyAnnot = new Annotation("lombok.Data", constPool);
		bodyAttr.addAnnotation(bodyAnnot);
		ccFile.addAttribute(bodyAttr);
		
		if (null != list && list.size() > 0) {
			for (InterfaceColumn param : list) {
				addField(WebServiceUtil.getType(param.getColumnType()), param.getColumnName(), cc);
			}
		}
		printClass(cc);
		return getClass(cc, flag);
	}
	
	private void removeField (CtClass cc) throws NotFoundException {
		if (null != cc.getFields() && cc.getFields().length > 0) {
			for (CtField field : cc.getFields()) {
				cc.removeField(field);
			}
		}
	}

	

	public Class<?> createDynamicColumn(String path, String className, List<InterfaceColumn> list,
			Map<Long, List<InterfaceColumn>> columnMap,String end) throws Exception {
		boolean flag = false;
		CtClass  cc = null;
		cc = POOL.makeClass(path + className + end);
		cc.addInterface(POOL.get("java.io.Serializable"));
		ClassFile ccFile = cc.getClassFile();
		ConstPool constPool = ccFile.getConstPool();
		AnnotationsAttribute bodyAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
		Annotation bodyAnnot = new Annotation("lombok.Data", constPool);
		bodyAttr.addAnnotation(bodyAnnot);
		ccFile.addAttribute(bodyAttr);
		
		if (null != list && list.size() > 0) {
			for (InterfaceColumn column : list) {
				String type = WebServiceUtil.getType(column.getColumnType());
				String temp = null;
				if ("java.util.List".equals(type) || "java.util.Map".equals(type)) {
					temp = this.createDynamicColumn(path, column.getColumnName(), columnMap.get(column.getColumnId()),
							columnMap,end).getName();
				}
				if ("java.util.List".equals(type)) {
					addListFiled( temp, column.getColumnName(), cc);
				} else if ("java.util.Map".equals(type)) {
					this.setFile(column, cc, temp);
				} else {
					this.setFile(column, cc, type);
				}
			}
		}
		
		printClass(cc);
		return getClass(cc, flag);
		
	}

	private void setFile(InterfaceColumn column, CtClass cc, String type)
			throws CannotCompileException, NotFoundException {
		if (null != column.getConverName() && !column.getConverName().isEmpty()) {
			addField(type, column.getConverName(), cc);
		} else {
			addField(type, column.getColumnName(), cc);
		}
	}

	private static void addField(String type, String name, CtClass cc)
			throws CannotCompileException, NotFoundException {
		CtField field = new CtField(POOL.get(type), name, cc);
		field.setModifiers(Modifier.PROTECTED);

		// 增加xml元素注解
		List<AttributeInfo> attributeInfos = field.getFieldInfo().getAttributes();
		AnnotationsAttribute annotationsAttribute = !attributeInfos.isEmpty()
				? (AnnotationsAttribute) attributeInfos.get(0)
				: new AnnotationsAttribute(field.getFieldInfo().getConstPool(), AnnotationsAttribute.visibleTag);
		Annotation annotation = new Annotation("javax.xml.bind.annotation.XmlElement",
				field.getFieldInfo().getConstPool());
		annotationsAttribute.addAnnotation(annotation);
		field.getFieldInfo().addAttribute(annotationsAttribute);

		cc.addField(field);
	}

	private static void addListFiled(String type, String name, CtClass cc)
			throws CannotCompileException, NotFoundException {
		CtField field = CtField.make("protected " + type + " []  " + name + ";", cc);
		// 增加xml元素注解
		List<AttributeInfo> attributeInfos = field.getFieldInfo().getAttributes();
		AnnotationsAttribute annotationsAttribute = !attributeInfos.isEmpty()
				? (AnnotationsAttribute) attributeInfos.get(0)
				: new AnnotationsAttribute(field.getFieldInfo().getConstPool(), AnnotationsAttribute.visibleTag);
		Annotation annotation = new Annotation("javax.xml.bind.annotation.XmlElement",
				field.getFieldInfo().getConstPool());
		annotationsAttribute.addAnnotation(annotation);
		field.getFieldInfo().addAttribute(annotationsAttribute);

		cc.addField(field);
	}

	public void initWebservice(InterfaceConfig config, List<InterfaceColumn> columns,
			List<InterfaceColumn> params) throws Exception {
		String className = config.getInterfaceCode();
		String path = getPath(className);
		this.webPush(path, config, columns, params);
	}
	
	public void webservicePush(InterfaceConfig config, List<InterfaceColumn> columns,
			List<InterfaceColumn> params) throws Exception {
		
		
		String className = config.getInterfaceCode();
		String path = getPath(className);
		if (factoryBeanMap.containsKey(className)) {
			path = getPath(className+new Date().getTime());
			JaxWsServerFactoryBean factoryBean =factoryBeanMap.get(className);
			Server server = serverMap.get(className);
			server.stop();
			server.destroy();
		}
		this.webPush(path, config, columns, params);
	}
	
	/**
	 * 发布服务
	 * @param path
	 * @param springBus
	 * @param config
	 * @param columns
	 * @param params
	 * @return
	 */
	private void webPush(String path, InterfaceConfig config, List<InterfaceColumn> columns,
			List<InterfaceColumn> params) throws Exception {
		try {
			Class<?> webservice = null;
				String className = config.getInterfaceCode();
				String method = "excute";
				if (config.getSource().equals("HTTP_RE")) {
					Map<Long, List<InterfaceColumn>> columnMap = this.getColumnMap(params);
					Class column = this.createDynamicColumn(path, className, columnMap.get(null),columnMap,"Param");
					if (config.getDataType().equals("MAP")) {
						webservice = new WebserviceGenerator().createDynamicClazz(path,className, method, column,BaseResult.class, "HTTP");
					} else {
						webservice = new WebserviceGenerator().createDynamicClazz(path,className, method, column,BaseResult.class, "HTTP_LIST");
					}
					
				} else if (config.getSource().equals("JDBC_RE")) {
					Map<Long, List<InterfaceColumn>> columnMap = this.getColumnMap(columns);
					Map<Long, List<InterfaceColumn>> paramMap = this.getColumnMap(params);
					Class param = this.createDynamicColumn(path, className, paramMap.get(null),paramMap,"Param");
					Class column = this.createDynamicColumn(path, className, columnMap.get(null),columnMap,"Result");
					if (config.getDataType().equals("MAP")) {
						webservice = new WebserviceGenerator().createDynamicClazz(path,className, method, param, column,"SQL_MAP");
					} else {
						webservice = new WebserviceGenerator().createDynamicClazz(path,className, method, param, column,"SQL_LIST");
					}
				}
				
				JaxWsServerFactoryBean factoryBean = new JaxWsServerFactoryBean();
				
			    // Web服务的地址
			    factoryBean.setAddress("/" + className + "Service");
			    // Web服务对象调用接口
		        factoryBean.setServiceClass(webservice);
		        Server server = factoryBean.create();
		        server.start();
		        
		        factoryBeanMap.put(className, factoryBean);
		        serverMap.put(className, server);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Map<Long, List<InterfaceColumn>> getColumnMap(List<InterfaceColumn> columns) throws Exception {
		Map<Long, List<InterfaceColumn>> columnMap = null;
		if (null != columns && columns.size() > 0) {
			columnMap = new HashMap<Long, List<InterfaceColumn>>();
			List<InterfaceColumn> tempList = null;
			for (InterfaceColumn column : columns) {
				if (columnMap.containsKey(column.getParentId())) {
					tempList = columnMap.get(column.getParentId());
				} else {
					tempList = new ArrayList<InterfaceColumn>();
				}
				tempList.add(column);
				columnMap.put(column.getParentId(), tempList);
			}
		}
		return columnMap;
	}
	
	private static String OUTPUT_PATH = "D:/test";
	/**
	 * 打印输出类
	 * @param cc
	 * @throws CannotCompileException
	 * @throws IOException
	 */
	private void printClass(CtClass cc) throws CannotCompileException, IOException {
//		cc.writeFile(OUTPUT_PATH);// 将上面构造好的类写入到指定的工作空间中
	}
	
	private Class<?> getClass(CtClass cc,boolean flag) throws IOException, CannotCompileException, ClassNotFoundException {
		cc.defrost();//可修改
		return cc.toClass();
	}
	
	
	public static BaseResult receiveCommon(String code,Object obj) throws Exception {
		IInterService localIInterService = (IInterService)SpringContextHolder.getBean(IInterService.class);
	    return localIInterService.apiInfoWebservice(code, obj);
	}
	
	
	/**
	 * 反射新建对象
	 * @param obj
	 * @param clazz
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static  Object mapToObject(Object obj,Class c) throws Exception{
		if (obj instanceof Map) {
			Map<String,Object> map = JsonUtil.parseObjectToMap(obj);
			return getField(map, c);
		} else if (obj instanceof List) {
			List<Map<String,Object>> list = (List<Map<String, Object>>) obj;
			Object array = Array.newInstance(c, list.size());
			for (int i=0;i<list.size() ; i++) {
				Array.set(array, i, getField(list.get(i),c)); 
			}
			return array;
		} else {
			return null;
		}
	}
	
	public static Object getField(Map<String,Object> map ,Class c) throws Exception {
		Object o = c.newInstance();
		Field [] fields = c.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			if (map.containsKey(name)) {
				field.setAccessible(true);
				Object value = map.get(name);
				if (value instanceof Map) {
					 Object temp = mapToObject(value, field.getType());
					 field.set(o, temp);
				} else if (value instanceof List) {
					Object temp = mapToObject(value, field.getType().getComponentType());
					field.set(o, temp);
				} else {
					field.set(o, WebServiceUtil.getType(map.get(name),field.getType()));
				}
				
			}
		}
		return o;
	}
	

}