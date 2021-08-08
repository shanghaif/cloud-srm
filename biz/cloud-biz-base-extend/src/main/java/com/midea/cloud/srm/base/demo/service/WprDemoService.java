package com.midea.cloud.srm.base.demo.service;

import com.midea.cloud.srm.model.base.rediscache.entity.RedisCacheMan;
import com.midea.cloud.srm.model.rbac.user.entity.User;

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
public interface WprDemoService {
    /**
     * 无参数缓存接口示例
     * @return
     */
    Object noParamCacheApi();

    /**
     * 无参数缓存接口示例
     * @return
     */
    Object paramCacheApi(String param1,String param2,String param3);

    void testNoNullCheck1(RedisCacheMan redisCacheMan);

    void testNoNullCheck2(User user,RedisCacheMan redisCacheMan);

    void testNoNullCheck3(User user,RedisCacheMan redisCacheMan);
}
