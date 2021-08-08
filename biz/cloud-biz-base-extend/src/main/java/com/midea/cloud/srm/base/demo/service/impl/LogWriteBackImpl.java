package com.midea.cloud.srm.base.demo.service.impl;

import com.midea.cloud.common.annotation.AuthData;
import com.midea.cloud.common.enums.api.ResultStatus;
import com.midea.cloud.common.enums.rbac.MenuEnum;
import com.midea.cloud.common.utils.LogWriteBack;
import com.midea.cloud.srm.model.api.interfacelog.dto.LogWriteBackDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Slf4j
@Service
public class LogWriteBackImpl implements LogWriteBack<Map<String,Object>>{

    @Override
    public LogWriteBackDto getReturnStatusInfo(Map<String,Object> map) {
        log.info("name:"+map.get("name"));
        log.info("age:"+map.get("age"));
        return LogWriteBackDto.builder().status(ResultStatus.SUCCESS.name()).build();
    }

    @AuthData(module = MenuEnum.SUPPLIER_SIGN)
    public void testLoadMethonAnotation(){
        MenuEnum menuEnum = loadMethonAnotation();
    }

    private final String    SRM_PACKAGE                 =   "com.midea.cloud";          //拦截范围，SRM包名前缀

    private MenuEnum loadMethonAnotation(){
        //获取当前运行栈中的对象
        List<StackTraceElement> runStack = Arrays.stream(new Exception().getStackTrace()).filter(
                s -> s.getClassName().indexOf(SRM_PACKAGE) == 0 && s.getClassName().indexOf("$") < 0
        ).collect(Collectors.toList());
        AuthData annotation = null;
        //匹配类中的方法查看是否有注解
        for(int i = 0 ; i < runStack.size() ; i++){
            try {
                //加载栈信息
                StackTraceElement stack = runStack.get(i);
                List<Method> methodList = Arrays.stream(Class.forName(stack.getClassName()).getDeclaredMethods())
                        .filter(method ->
                                method.getName().equals(stack.getMethodName()) &&
                                        Objects.nonNull(method.getAnnotation(AuthData.class))
                        ).collect(Collectors.toList());

                if(!methodList.isEmpty()){
                    //取最下层第一个标注的方法
                    annotation = methodList.get(0).getAnnotation(AuthData.class);
                    break;
                }
            } catch (ClassNotFoundException e) {
                log.error("数据权限拦截器出现异常",e);
                e.printStackTrace();
            }
        }
        if(Objects.nonNull(annotation) && annotation.module().length > 0){
            MenuEnum menuEnum = null;
            for(MenuEnum menuAnnotation : annotation.module()){
                if(menuAnnotation.equals(MenuEnum.SUPPLIER_SIGN)){
                }else{
                    menuEnum = menuAnnotation;
                }
            }
            return menuEnum;
        }
        return null;
    }
}
