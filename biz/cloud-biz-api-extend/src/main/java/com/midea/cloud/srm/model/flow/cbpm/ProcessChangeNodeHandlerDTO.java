package com.midea.cloud.srm.model.flow.cbpm;

import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  流程节点处理人参数DTO
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/27 23:37
 *  修改内容:
 * </pre>
 */
@Data
public class ProcessChangeNodeHandlerDTO {
    /**节点ID*/
    private String nodeId;
    /**节点处理人列表*/
    private List<ProcessHandlersDTO> handlers;
}
