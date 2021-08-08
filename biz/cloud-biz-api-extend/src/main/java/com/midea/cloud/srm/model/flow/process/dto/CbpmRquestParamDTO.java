package com.midea.cloud.srm.model.flow.process.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.flow.cbpm.ProcessChangeNodeHandlerDTO;
import lombok.Data;

import java.util.List;
import java.util.Map;
/**
 * <pre>
 * 流程创建、提交参数相关DTO
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/16
 *  修改内容:
 * </pre>
 */
@Data
public class CbpmRquestParamDTO extends BaseDTO {
    private static final long serialVersionUID = -1136394172339561492L;

    private String businessKey;//自定义的业务类型（关联模板ID）
    private String loginName;//当前登录用户名
    private String subjectTitle;//流程实例标题
    private String businessId;//业务单据ID,
    private String orgType;//组织类型
    private String orgCode;//组织ID
    private String fullPathId; // 组织全路径虚拟ID
    private String params;//待办url的参数

    private String fdTemplateId;//模板ID
    private String fdId;//流程实例ID
    private List<Map<String, Object>> subjectForMultiLanguages; // 流程实例主题(多语言主题)
    /**模板编码*/
    private String templateCode;
    /**菜单表-功能名称Id*/
    private String functionId;
    /**流程名称(或流程名称头)*/
    private String subject;
    /**业务系统模块ID*/
    private String fdModuleId;
    /**操作类型*/
    private String operationType;
    /**cbpm流程引擎有条件节点的时候必填*/
    Map<String, Object> cbpmFormMap;

    /**cbpm流程引擎FormParam的formData参数，有条件节点的时候必填*/
    Map<String, Object> formDataMap;

    /** 审批意见的附件processParam子参数id(processParam子参数)*/
    private List<Long> auditFileDocIdList;
    /**流程处理意见(processParam子参数)*/
    private String auditNote;
    /** 转办，传阅、沟通用户登录名,多个用户名用“;”隔开,
     * 满足下面条件之一时为必填：
     1、processParam中的operationType值为circulate【传阅】时；
     2、processParam中的operationType值为handler_communicate【沟通】时；
     3、processParam中的operationType值为handler_commission【转办】时；(processParam子参数)
     */
    private String toOtherPersons;
    /** 驳回的节点id-默认值为N1，默认驳回到起草节点(processParam子参数)*/
    private String jumpToNodeId;
    /** 驳回后是否直接返回本节点（true为直接返回本节点）processParam中的operationType值为handler_refuse【驳回】时，本参数必填(processParam子参数)*/
    private Boolean refusePassedToThisNode;
    /**流程节点处理人参数DTO(processParam子参数)*/
    private List<ProcessChangeNodeHandlerDTO> changeNodeHandler;
    /** 更改节点对应表单url的参数，"本参数作用如下：
     当流程流转到某个节点时，发送待办到MIP待办中心时，是默认使用服务引擎自己的流程url。
     如果业务系统需要特殊定制化某个节点发待办的url的话，就使用本参数。
     注意，formParam中的fdUrl参数是对应当前流程实例的，而本参数是对应某些节点特殊设置的，本参数优先级别高于formParam中的fdUrl参数"
     (processParam子参数)*/
    private List<String> changeNodeFormUrls;
    /** 更改节点配置参数如果单独对某个流程实例 修改节点名称、节点流转类型【并审、会审、串行】 或 转换抄送节点等，就使用本参数。
     * (processParam子参数)*/
    private String changeNodeProcessTypes;
    /** 人工决策分支目标节点的参数,起草人 或 审批人 要决定人工决策分支走向时必填(processParam子参数)*/
    private String manualBranchToNodes;

}
