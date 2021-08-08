package com.midea.cloud.srm.model.flow.process.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  cbpm工作流新增系统-详细子表DTO
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/26 19:49
 *  修改内容:
 * </pre>
 */
@Data
public class ProcessSystemDetailDTO extends BaseDTO {

    private static final long serialVersionUID = 7221334768349431767L;
    /**系统主键id */
    private String fdMethod;
    /**创建人id */
    private String fdType;
    /**域名*/
    private String configName;
    /**模块名称*/
    private String configDes;
    /**工作流新增系统-详细子表-配置表集合 */
    private List<ProcessSystemDetailConfigDTO> configValueList;

}
