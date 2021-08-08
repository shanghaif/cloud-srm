package com.midea.cloud.srm.model.flow.process.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

/**
 * <pre>
 *  cbpm工作流新增系统-详细子表-配置表DTO
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/26 19:50
 *  修改内容:
 * </pre>
 */
@Data
public class ProcessSystemDetailConfigDTO extends BaseDTO {
    private static final long serialVersionUID = 3408598037485259626L;

    /**配置key(url) */
    private String fdKey;
    /**配置描述(restful url接口地址) */
    private String fdDesc;
    /**配置类型(restful)*/
    private String connectorType;
    /**配置值(后调接口url)*/
    private String fdValue;

}
