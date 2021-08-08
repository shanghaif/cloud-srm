package com.midea.cloud.srm.model.inq.price.dto;

import com.midea.cloud.srm.model.inq.price.entity.ApprovalFile;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-9 17:01
 *  修改内容:
 * </pre>
 */
@Data
public class ApprovalHeaderDetailResponseDTO {

    /**
     * 价格审批单头主键
     */
    private Long approvalHeaderId;

    /**
     * 价格审批单号
     */
    private String approvalNo;

    /**
     * 询价单号
     */
    private String inquiryNo;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 组织名称
     */
    private String organizationName;

    /**
     * 评分规则
     */
    private String inquiryRule;

    /**
     * 报价方式
     */
    private String quoteRule;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 审批状态
     */
    private String auditStatus;

    /**
     * 寻源方式
     */
    private String sourceType;

    /**
     * 发起人
     */
    private String createdBy;

    /**
     * 报价精度
     */
    private String priceNum;

    /**
     * 价格审批列表
     */
    private List<ApprovalItemDetailDTO> approvalItems;

    /**
     * 初始化工作流信息
     */
    private Map<String, Object> initWorkFlow;

    /**
     * 流程ID
     */
    private String cbpmInstaceId;

    /**
     * 审批附件
     */
    private List<ApprovalFile> approvalFiles;
}