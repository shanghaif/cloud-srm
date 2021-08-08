package com.midea.cloud.srm.model.flow.cbpm;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  流程节点信息列表ListDto
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/29 21:51
 *  修改内容:
 * </pre>
 */
@Data
public class CbpmProcessNodesListDTO extends BaseDTO {
    private static final long serialVersionUID = -3093140227809162423L;

    /***自定义参数 ------------------------- start---------*/
    /**是否能选择*/
    private Boolean selectPerson;
    /**流程状态*/
    private String fdStatus;
    /**流程状态Code*/
    private String fdStatusCode;
    /**前端提示信息*/
    private String message;
    /**当前节点列表（如果当前节点是并行分支情况，就会有多个当前节点。其他情况一般只有1个当前节点）*/
//    private List<Map<String, Object>> currNodes;

    /**表单数据Map*/
    private Map<String, Object> formDataMap;
    /***自定义参数 ------------------------- end---------*/

    /**流程表格信息*/
    private List<CbpmProcessNodesDTO> processNodes;

    /**人工决策选择项列表*/
    private List<CbpmProcessNodesManualDTO> manualNodes;
}
