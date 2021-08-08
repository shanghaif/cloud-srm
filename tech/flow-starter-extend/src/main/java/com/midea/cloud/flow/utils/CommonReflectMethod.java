package com.midea.cloud.flow.utils;


import com.midea.cloud.component.context.container.SpringContextHolder;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 动态调用
 *
 */
public class CommonReflectMethod {

    /**
     * Description 获取表单数据
     * @Param classUrl 类全路径
     * @Param function 方法名
     * @return 
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.23
     * @throws 
     **/
    public static Object commonReflectMethod(String classUrl, String function, Object[] paramObj, Class[] param) throws Exception{
        //获取要操作的类
        Class clazz=Class.forName(classUrl);

        Object obj = null;

//        Object bean = SpringBeanUtil.getBean(clazz);
        Object bean = SpringContextHolder.getApplicationContext().getBean(clazz);;
        if(param != null && param.length > 0){
            Method method=clazz.getMethod(function,param);
            //反射传入数据
            obj=method.invoke(bean,paramObj);
        } else {
            Method method=clazz.getMethod(function);
            //反射传入数据
            obj=method.invoke(bean);
        }
        return obj;
    }

//    public static void main(String[] args) {
//        String classUrl = "com.midea.srm.mip.feign.client.SrmPosFeignClient";
//        try {
//            Class clazz=Class.forName(classUrl);
//            System.out.println(clazz.getSimpleName());
//            Class[] cla = new Class[1];
//            cla[0] = Map.class;
//            try {
//                Method method=clazz.getMethod("handleMipEventCallback",cla);
//                System.out.println(method.getName());
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
}
