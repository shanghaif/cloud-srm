package com.midea.cloud.srm.base.demo.service.impl;

import com.midea.cloud.common.annotation.CacheData;
import com.midea.cloud.common.annotation.CheckParam;
import com.midea.cloud.common.constants.RedisKey;
import com.midea.cloud.srm.base.demo.service.WprDemoService;
import com.midea.cloud.srm.model.annonations.NotNull;
import com.midea.cloud.srm.model.base.rediscache.entity.RedisCacheMan;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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
@Service
public class WprDemoServiceImpl implements WprDemoService {
    @Override
    @CacheData(keyName = RedisKey.NO_PARAM_CACHE_API,cacheTime = 60,interfaceName = "无参数缓存示例接口")
    public Object noParamCacheApi() {
        return "HelloWorld!";
    }

    @Override
    @CacheData(keyName = RedisKey.PARAM_CACHE_API,cacheTime = 60,interfaceName = "有参数缓存示例接口")
    public Object paramCacheApi(String param1, String param2, String param3) {
        return Arrays.asList(param1,param2,param3);
    }

    @Override
    @CheckParam
    public void testNoNullCheck1(@NotNull("redisCacheMan不能为空") RedisCacheMan redisCacheMan) {

    }

    @Override
    @CheckParam
    public void testNoNullCheck2(@NotNull("user不能为空") User user, RedisCacheMan redisCacheMan) {

    }

    @Override
    @CheckParam
    public void testNoNullCheck3(User user, @NotNull("redisCacheMan不能为空") RedisCacheMan redisCacheMan) {

    }
}
