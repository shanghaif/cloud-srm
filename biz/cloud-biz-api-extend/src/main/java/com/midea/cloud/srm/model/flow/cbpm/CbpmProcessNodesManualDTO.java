package com.midea.cloud.srm.model.flow.cbpm;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

/**
 * <pre>
 * 人工决策选择项列表（流程表格与人工决策选择项列表-子表）
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/29
 *  修改内容:
 * </pre>
 */
@Data
public class CbpmProcessNodesManualDTO extends BaseDTO {

    private static final long serialVersionUID = 3602448971199930301L;
    /** 人工决策节点ID*/
    private String nodeId;
    /** 人工决策节点名称。 也作为前台选项选择栏标题*/
    private String nodeName;
    /** 选中的目标节点ID*/
    private String chosenToNodeId;
    /**单选框组是否为只读模式（true表示只读） */
    private Boolean readOnly;
    /**Array<Json>	分支节点选项列表（单选框组）text-选项名
     * toNodeId-第N条分支流向的节点的ID */
    private String branchNodeOptions;
}
