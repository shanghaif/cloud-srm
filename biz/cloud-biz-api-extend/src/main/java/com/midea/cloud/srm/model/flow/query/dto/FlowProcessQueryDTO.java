package com.midea.cloud.srm.model.flow.query.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <pre>
 *  流程条件查询实体类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/11 11:16
 *  修改内容:
 * </pre>
 */
@Data
public class FlowProcessQueryDTO extends BaseDTO {

    private static final long serialVersionUID = -7967686905041019345L;

    /**流程实例ID */
    @Setter @Getter
    private String fdId;

    /**系统模块id*/
    @Setter @Getter
    private String fdModuleId;

    /**业务系统实例id */
    @Setter @Getter
    private String formInstanceId;

    /**业务系统模块id*/
    @Setter @Getter
    private String formTemplateId;

    /**用户登录名*/
    @Setter @Getter
    private String loginName;

    /**模板编码*/
    @Setter @Getter
    private String fdTemplateCode;

    /**流程模板ID*/
    @Setter @Getter
    private String fdTemplateId;

    /**标题 */
    @Setter @Getter
    private String docSubject;

    /**排序字段 */
    @Setter @Getter
    private String orderBy;

    /**是否升序 */
    @Setter @Getter
    private Boolean isAsc;

    /**流程到达该节点时间的开始时间（时间戳传递 例如1504396800） */
    @Setter @Getter
    private Long fdStartDateBegin;

    /**页码 */
    @Setter @Getter
    private Integer page;

    /**每页大小 */
    @Setter @Getter
    private Integer pageSize;

    /**发起人姓名 */
    @Setter @Getter
    private String docCreatorName;

    /**发起人的用户账号 */
    @Setter @Getter
    private String docCreatorLoginName;

    /**流程主题*/
    @Setter @Getter
    private String fdSubject;

    /**流程状态编码(参考流程状态编码表)*/
    @Setter @Getter
    private String fdStatus;

    /**创建人登录账号*/
    @Setter @Getter
    private String docCreatorId;

    /**流程提交开始时间(时间戳)*/
    @Setter @Getter
    private String docCreateStartTime;

    /**流程提交结束时间(时间戳)*/
    @Setter @Getter
    private String docCreateEndTime;

    /** 权限账号(为空查询所有数据,不为空查询该账号有权限的数据)*/
    @Setter @Getter
    private String fdCurrentUserAccount;

    /**流程状态*/
    @Setter @Getter
    private String docStatus;

    /**节点实例id（从获取流程详情接口获取）*/
    @Setter @Getter
    private String nodeInstanceId;

    /**起草结束时间（时间戳传递 例如1504396800）*/
    @Setter @Getter
    private Long fdStartDateEnd;

    /**模板ID*/
    @Setter @Getter
    private String templateId;

    /**业务系统表单数据 json格式 获取流程表格与人工决策选择项列表接口参数*/
    @Setter @Getter
    private String formData;

    /***以下是 替换流程实例处理人部分参数----- start**/
    /**旧处理人用户账号*/
    @Setter @Getter
    private String oldHandlerId;
    /**新处理人用户账号*/
    @Setter @Getter
    private String newHandlerId;
    /** 操作备注*/
    @Setter @Getter
    private String comment;
    /**"流程实例ID，格式如：[""111"",""222""]"*/
    @Setter @Getter
    private ArrayList<String> processIds;
    /** "节点ID，格式如：[""N3"",""N4""]"*/
    @Setter @Getter
    private ArrayList<String> nodeIds;
    /***以上是 替换流程实例处理人部分参数----- end**/

}
