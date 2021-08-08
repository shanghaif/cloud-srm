package com.midea.cloud.srm.perf.config;

import com.midea.cloud.common.utils.group.GroupUtil;
import com.midea.cloud.common.utils.group.SortUtil;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <pre>
 *  依赖注入Class工具类
 *  GroupUtil和SortUtil到spring容器
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/6/16 16:44
 *  修改内容:
 * </pre>
 */
@Configuration
@Import({GroupUtil.class, SortUtil.class, ExportExcelParam.class})
public class UtilConfig {
}
