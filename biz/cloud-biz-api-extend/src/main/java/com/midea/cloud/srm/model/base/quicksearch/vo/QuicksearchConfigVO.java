package com.midea.cloud.srm.model.base.quicksearch.vo;

import com.midea.cloud.srm.model.base.quicksearch.entity.QuicksearchAttrConfig;
import com.midea.cloud.srm.model.base.quicksearch.entity.QuicksearchConfig;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  快速查询配置 视图对象
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-5 17:04
 *  修改内容:
 * </pre>
 */
@Data
public class QuicksearchConfigVO extends QuicksearchConfig {

    private static final long serialVersionUID = 1L;

    private List<QuicksearchAttrConfig> attrConfigs;
}
