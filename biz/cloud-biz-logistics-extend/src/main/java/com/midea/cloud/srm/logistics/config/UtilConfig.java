package com.midea.cloud.srm.logistics.config;

import com.cloud.srm.cxf.client.CxfClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <pre>
 *  依赖注入Class工具类
 *  注入CxfClient类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/22 20:47
 *  修改内容:
 * </pre>
 */
@Configuration
@Import({CxfClient.class})
public class UtilConfig {
}
