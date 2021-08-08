package com.midea.cloud.srm.model.flow.cbpm;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  流程表格信息（流程表格与人工决策选择项列表-子表）
 * </pre>
 *
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
public class CbpmProcessNodesDTO extends BaseDTO {
    private static final long serialVersionUID = 1326870259127099847L;
    /** 节点id*/
    private String nodeId;
    /**节点名称 */
    private String nodeName;
    /**节点类型*/
    private String processType;
    /**节点处理人账号组合，分号隔开*/
    private String handlerIds;
    /**节点处理人名称组合，分号隔开*/
    private String handlerNames;
    /**节点处理人列表     ""id"":""chenchao17"",
     ""name"":""chen chao"",
     ""type"":""USER""*/
    private List<Map<String, Object>> handlers;
    /**是否公式运算错误*/
    private String handlerOperationError;
    /**是否当前节点*/
    private String currentNode;
    /**是否可修改*/
    private Boolean  canSelected;
    /**是否必须修改*/
    private Boolean mustSelected;
    /**节点说明*/
    private String nodeDesc;
    /**节点类型编码（详见节点类型编码表）*/
    private String activityType;
    /** 操作人MIP用户ID组合，分号隔开*/
    private String handlerMipIds;

    /**以下是节点审批信息(通过方法set值)*/
    /**操作名称 */
    private String fdOperationName;
    /**审批意见 */
    private String fdAuditeInfo;
    /**操作时间 */
    private String fdHandlerTime;
    /**附件集合信息(附件ID：docId，附件名称：docName，附件大小：docSize，附件下载地址：downloadUrl) */
    private List<Map> docMainList;
}
