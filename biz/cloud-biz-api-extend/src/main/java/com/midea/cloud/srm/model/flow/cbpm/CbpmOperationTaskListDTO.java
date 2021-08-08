package com.midea.cloud.srm.model.flow.cbpm;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

/**
 * <pre>
 *  操作对应的任务信息列表-CbpmOperationDTO(操作人信息列表)子表
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/28 15:14
 *  修改内容:
 * </pre>
 */
@Data
public class CbpmOperationTaskListDTO extends BaseDTO {

    private static final long serialVersionUID = 5662768572178189078L;
    /**任务id */
    private String fdTaskId;
    /**任务类型 */
    private String fdActivityType;
    /**节点实例id */
    private String fdNodeInstanceId;
    /**节点id */
    private String fdNodeId;
    /**节点名称 */
    private String fdNodeName;
    /**节点描述 */
    private String description;
}
