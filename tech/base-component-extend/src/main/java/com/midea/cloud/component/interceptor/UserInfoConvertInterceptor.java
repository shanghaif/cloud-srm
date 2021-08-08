package com.midea.cloud.component.interceptor;

import com.midea.cloud.common.constants.RedisKey;
import com.midea.cloud.srm.model.common.BasePage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.data.redis.core.HashOperations;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 用户信息转换过滤器[用户账号 -> 用户名]
 * </pre>`
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/04/1 14:12
 *  修改内容:
 * </pre>
 */
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
@Slf4j
public class UserInfoConvertInterceptor implements Interceptor {

    private HashOperations<String, String, String> hashOperations;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        List<Object> result = (List<Object>) invocation.proceed();
        try {
            if (CollectionUtils.isNotEmpty(result) && BasePage.class.isAssignableFrom(result.get(0).getClass())) {
                Map<String, Integer> userIndexs = new LinkedHashMap<>();
                int i = 0;
                for (Object basePage : result) {
                    String createdBy = getNameByFieldName("createdBy", basePage);
                    String lastUpdatedBy = getNameByFieldName("lastUpdatedBy", basePage);
                    if (createdBy != null && userIndexs.get(createdBy) == null) {
                        userIndexs.put(createdBy, i++);
                    }
                    if (lastUpdatedBy != null && userIndexs.get(lastUpdatedBy) == null) {
                        userIndexs.put(lastUpdatedBy, i++);
                    }
                }
                List<String> usernameList = hashOperations.multiGet(RedisKey.USER_CACHE, new ArrayList(userIndexs.keySet()));
                if (CollectionUtils.isNotEmpty(usernameList)) {
                    for (Object basePage : result) {
                        String createdBy = getNameByFieldName("createdBy", basePage);
                        String lastUpdatedBy = getNameByFieldName("lastUpdatedBy", basePage);
                        if (createdBy != null) {
                            int index = userIndexs.get(createdBy);
                            String createdUserName = usernameList.get(index);
                            if (StringUtils.isBlank(createdUserName)) {
                                ((BasePage) basePage).setCreatedUserName(createdBy);
                            } else {
                                ((BasePage) basePage).setCreatedUserName(createdUserName);
                            }
                        }
                        if (lastUpdatedBy != null) {
                            int index = userIndexs.get(lastUpdatedBy);
                            String lastUpdatedUserName = usernameList.get(index);
                            if (StringUtils.isBlank(lastUpdatedUserName)) {
                                ((BasePage) basePage).setLastUpdatedUserName(lastUpdatedBy);
                            } else {
                                ((BasePage) basePage).setLastUpdatedUserName(lastUpdatedUserName);
                            }
                        }
                    }
                }
            }
        } catch (Throwable throwable) {
            log.error("用户信息转换异常，请检查", throwable);
        } finally {
            return result;
        }
    }

    public String getNameByFieldName(String fieldName, Object basePage) throws Throwable {
        Class superClass = basePage.getClass();
        Field userNameField = null;
        while (superClass != null && userNameField == null) {
            try {
                userNameField = superClass.getDeclaredField(fieldName);
            } catch (Exception ex) {
            }
            superClass = superClass.getSuperclass();
        }
        if (userNameField != null) {
            userNameField.setAccessible(true);
            Object userNameObj = userNameField.get(basePage);
            if (userNameObj == null) {
                return null;
            }
            return String.valueOf(userNameObj);
        }
        return null;
    }

    public void setHashOperations(HashOperations<String, String, String> hashOperations) {
        this.hashOperations = hashOperations;
    }
}