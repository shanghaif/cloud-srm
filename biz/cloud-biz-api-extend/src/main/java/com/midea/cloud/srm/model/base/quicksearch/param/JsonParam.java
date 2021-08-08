package com.midea.cloud.srm.model.base.quicksearch.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;

/**
 * <pre>
 *  快速查询 param类
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonParam extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private String params;
    private String exParams;
    private Integer pagecount=1;
    private String language;
    private String entityId;
}
