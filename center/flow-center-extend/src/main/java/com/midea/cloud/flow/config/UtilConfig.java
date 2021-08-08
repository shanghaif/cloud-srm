package com.midea.cloud.flow.config;

import com.midea.cloud.common.utils.redis.RedisLockUtil;
import com.midea.cloud.common.utils.redis.RedisUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <pre>
 *  依赖注入Class工具类
 *  RedisLockUtil RedisUtil
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-7-9 17:00
 *  修改内容:
 * </pre>
 */
@Configuration
@Import({RedisLockUtil.class, RedisUtil.class})
public class UtilConfig {
}
