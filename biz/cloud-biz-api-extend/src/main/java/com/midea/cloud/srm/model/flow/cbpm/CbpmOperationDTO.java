package com.midea.cloud.srm.model.flow.cbpm;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 * 操作人信息列表-CbpmOperationListDTO(根据角色分组后的流程可用操作接口返回值)子表
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/28 15:02
 *  修改内容:
 * </pre>
 */
@Data
public class CbpmOperationDTO extends BaseDTO {

    private static final long serialVersionUID = 4123683564842030956L;

    /**操作名称 */
    private String name;
    /**操作类型 */
    private String operationType;
    /**排序 */
    private String order;
    /** 是否在流程定义中自定义的操作*/
    private Boolean isDefinition;
    /**此操作对应的任务信息列表 */
    private List<CbpmOperationTaskListDTO> operationTaskList;
}
