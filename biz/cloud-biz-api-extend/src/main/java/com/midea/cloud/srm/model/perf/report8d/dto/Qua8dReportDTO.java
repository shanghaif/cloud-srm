package com.midea.cloud.srm.model.perf.report8d.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *  8D报告 模型
 * </pre>
 *
 * @author chenjw90@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 31, 2021 5:47:57 PM
 *  修改内容:
 * </pre>
 */
@Data
public class Qua8dReportDTO extends BaseDTO {
    private static final long serialVersionUID = 565405L;
    /**
     * 报告编号
     */
    private Long reportId;
    /**
     * 报告状态
     */
    private String reportStatus;
    /**
     * 来源单据类型
     */
    private String documentType;
    /**
     * 发出日期
     */
    private Date sendDate;
    /**
     * 回复日期
     */
    private Date responseDate;
    /**
     * 组长(部门/职位/姓名)P
     */
    private String groupLeader;
    /**
     * 成员(部门/职位/姓名)
     */
    private String groupMember;
    /**
     * 根本原因追查why1
     */
    private String why1;
    /**
     * 制程流出原因
     */
    private String processOutflowReason;
    /**
     * 品质流出原因
     */
    private String qualityOutflowReason;
    /**
     * 客户端确认数量
     */
    private Long clientConfirmationTotal;
    /**
     * 在途确认数量
     */
    private Long transitConfirmationTotal;
    /**
     * 仓库库存确认数量
     */
    private Long organizationConfirmationTotal;
    /**
     * 客户端确认处理方式
     */
    private String clientHandleWay;
    /**
     * 在途确认处理方式
     */
    private String transitHandleWay;
    /**
     * 仓库库存处理方式
     */
    private String organizationHandleWay;
    /**
     * 责任人
     */
    private String interimMeasuresAgent;
    /**
     * 临时措施处理日期
     */
    private Date interimMeasuresDate;
    /**
     * 临时措施完成日期
     */
    private Date interimMeasuresFinishDate;
    /**
     * 对策者
     */
    private String countermeasurePerson;
    /**
     * 执行部门
     */
    private String excuteDepartment;
    /**
     * 永久对策完成日期
     */
    private Date countermeasureFinishDate;
    /**
     * 执行者
     */
    private String excutePerson;
    /**
     * 根本原因对策
     */
    private String countermeasureRootReason;
    /**
     * 流出原因对策
     */
    private String countermeasureOutflowReason;
    /**
     * 来料检验报告单号1
     */
    private String itemExceptionId1;
    /**
     * 检验结果1
     */
    private String checkResult1;
    /**
     * 来料检验报告单号2
     */
    private String itemExceptionId2;
    /**
     * 检验结果2
     */
    private String checkResult2;
    /**
     * 来料检验报告单号3
     */
    private String itemExceptionId3;
    /**
     * 检验结果3
     */
    private String checkResult3;
    /**
     * 改善前
     */
    private String beforeImprovement;
    /**
     * 改善后
     */
    private String afterImprovement;
    /**
     * 旧流程废除日期
     */
    private Date oldProcessAbolishDate;
    /**
     * 新流程执行日期
     */
    private Date newProcessAbolishDate;
    /**
     * 平行展开
     */
    private String parallelExpansion;
    /**
     * 制定人
     */
    private String madeBy;
    /**
     * 批准人
     */
    private String approvedBy;
    /**
     * 审核人
     */
    private String report8DChecker;
}
