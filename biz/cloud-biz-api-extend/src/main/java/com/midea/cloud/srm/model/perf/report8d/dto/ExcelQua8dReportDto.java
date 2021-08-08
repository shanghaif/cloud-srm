package com.midea.cloud.srm.model.perf.report8d.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
* <pre>
 *  8D报告 excel导出模型
 * </pre>
*
* @author chenjw90@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Feb 1, 2021 9:57:27 PM
 *  修改内容:
 * </pre>
*/

@Data
@ColumnWidth(15) //列宽
public class ExcelQua8dReportDto {
private static final long serialVersionUID = 943145L;
 /**
 * 报告状态
 */
 @ExcelProperty( value = "报告状态",index = 0)
 private String reportStatus;
 /**
 * 打印选项
 */
 @ExcelProperty( value = "打印选项",index = 1)
 private String printOptions;
 /**
 * 来源单据类型
 */
 @ExcelProperty( value = "来源单据类型",index = 2)
 private String documentType;
 /**
 * 来源单号
 */
 @ExcelProperty( value = "来源单号",index = 3)
 private Long sourceOrderId;
 /**
 * 异常单负责人
 */
 @ExcelProperty( value = "异常单负责人",index = 4)
 private String exceptionOrderAgent;
 /**
 * 发出日期
 */
 @ExcelProperty( value = "发出日期",index = 5)
 private Date sendDate;
 /**
 * 回复日期
 */
 @ExcelProperty( value = "回复日期",index = 6)
 private Date responseDate;
 /**
 * 发出单位
 */
 @ExcelProperty( value = "发出单位",index = 7)
 private String sendDepartment;
 /**
 * 接收单位
 */
 @ExcelProperty( value = "接收单位",index = 8)
 private String receiveDepartment;
 /**
 * 品质审核人
 */
 @ExcelProperty( value = "品质审核人",index = 9)
 private String qualityChecker;
 /**
 * 物料编码
 */
 @ExcelProperty( value = "物料编码",index = 10)
 private Long materialCode;
 /**
 * 检验标准
 */
 @ExcelProperty( value = "检验标准",index = 11)
 private String checkStandard;
 /**
 * 物料名称
 */
 @ExcelProperty( value = "物料名称",index = 12)
 private String materialName;
 /**
 * 物料状态
 */
 @ExcelProperty( value = "物料状态",index = 13)
 private String materialStatus;
 /**
 * 3个月内发生次数
 */
 @ExcelProperty( value = "3个月内发生次数",index = 14)
 private Long threeMonthsHappens;
 /**
 * 供应商
 */
 @ExcelProperty( value = "供应商",index = 15)
 private String vendorName;
 /**
 * DATE CODE
 */
 @ExcelProperty( value = "DATE CODE",index = 16)
 private Long dateCode;
 /**
 * LOT NO
 */
 @ExcelProperty( value = "LOT NO",index = 17)
 private Long lotNo;
 /**
 * 不良描述
 */
 @ExcelProperty( value = "不良描述",index = 18)
 private String ngDescribe;
 /**
 * 组长(部门/职位/姓名)P
 */
 @ExcelProperty( value = "组长(部门/职位/姓名)P",index = 19)
 private String groupLeader;
 /**
 * 成员(部门/职位/姓名)
 */
 @ExcelProperty( value = "成员(部门/职位/姓名)",index = 20)
 private String groupMember;
 /**
 * 根本原因追查why1
 */
 @ExcelProperty( value = "根本原因追查why1",index = 21)
 private String why1;
 /**
 * why2
 */
 @ExcelProperty( value = "why2",index = 22)
 private String why2;
 /**
 * why3
 */
 @ExcelProperty( value = "why3",index = 23)
 private String why3;
 /**
 * why4
 */
 @ExcelProperty( value = "why4",index = 24)
 private String why4;
 /**
 * why5
 */
 @ExcelProperty( value = "why5",index = 25)
 private String why5;
 /**
 * 制程流出原因
 */
 @ExcelProperty( value = "制程流出原因",index = 26)
 private String processOutflowReason;
 /**
 * 品质流出原因
 */
 @ExcelProperty( value = "品质流出原因",index = 27)
 private String qualityOutflowReason;
 /**
 * 客户端确认数量
 */
 @ExcelProperty( value = "客户端确认数量",index = 28)
 private Long clientConfirmationTotal;
 /**
 * 在途确认数量
 */
 @ExcelProperty( value = "在途确认数量",index = 29)
 private Long transitConfirmationTotal;
 /**
 * 仓库库存确认数量
 */
 @ExcelProperty( value = "仓库库存确认数量",index = 30)
 private Long organizationConfirmationTotal;
 /**
 * 客户端确认处理方式
 */
 @ExcelProperty( value = "客户端确认处理方式",index = 31)
 private String clientHandleWay;
 /**
 * 在途确认处理方式
 */
 @ExcelProperty( value = "在途确认处理方式",index = 32)
 private String transitHandleWay;
 /**
 * 仓库库存处理方式
 */
 @ExcelProperty( value = "仓库库存处理方式",index = 33)
 private String organizationHandleWay;
 /**
 * 责任人
 */
 @ExcelProperty( value = "责任人",index = 34)
 private String interimMeasuresAgent;
 /**
 * 临时措施处理日期
 */
 @ExcelProperty( value = "临时措施处理日期",index = 35)
 private Date interimMeasuresDate;
 /**
 * 临时措施完成日期
 */
 @ExcelProperty( value = "临时措施完成日期",index = 36)
 private Date interimMeasuresFinishDate;
 /**
 * 对策者
 */
 @ExcelProperty( value = "对策者",index = 37)
 private String countermeasurePerson;
 /**
 * 执行部门
 */
 @ExcelProperty( value = "执行部门",index = 38)
 private String excuteDepartment;
 /**
 * 永久对策完成日期
 */
 @ExcelProperty( value = "永久对策完成日期",index = 39)
 private Date countermeasureFinishDate;
 /**
 * 执行者
 */
 @ExcelProperty( value = "执行者",index = 40)
 private String excutePerson;
 /**
 * 根本原因对策
 */
 @ExcelProperty( value = "根本原因对策",index = 41)
 private String countermeasureRootReason;
 /**
 * 流出原因对策
 */
 @ExcelProperty( value = "流出原因对策",index = 42)
 private String countermeasureOutflowReason;
 /**
 * 来料检验报告单号1
 */
 @ExcelProperty( value = "来料检验报告单号1",index = 43)
 private String itemExceptionId1;
 /**
 * 检验结果1
 */
 @ExcelProperty( value = "检验结果1",index = 44)
 private String checkResult1;
 /**
 * 来料检验报告单号2
 */
 @ExcelProperty( value = "来料检验报告单号2",index = 45)
 private String itemExceptionId2;
 /**
 * 检验结果2
 */
 @ExcelProperty( value = "检验结果2",index = 46)
 private String checkResult2;
 /**
 * 来料检验报告单号3
 */
 @ExcelProperty( value = "来料检验报告单号3",index = 47)
 private String itemExceptionId3;
 /**
 * 检验结果3
 */
 @ExcelProperty( value = "检验结果3",index = 48)
 private String checkResult3;
 /**
 * 改善前
 */
 @ExcelProperty( value = "改善前",index = 49)
 private String beforeImprovement;
 /**
 * 改善后
 */
 @ExcelProperty( value = "改善后",index = 50)
 private String afterImprovement;
 /**
 * 旧流程废除日期
 */
 @ExcelProperty( value = "旧流程废除日期",index = 51)
 private Date oldProcessAbolishDate;
 /**
 * 新流程执行日期
 */
 @ExcelProperty( value = "新流程执行日期",index = 52)
 private Date newProcessAbolishDate;
 /**
 * 平行展开
 */
 @ExcelProperty( value = "平行展开",index = 53)
 private String parallelExpansion;
 /**
 * 制定人
 */
 @ExcelProperty( value = "制定人",index = 54)
 private String madeBy;
 /**
 * 小组总结审核人
 */
 @ExcelProperty( value = "小组总结审核人",index = 55)
 private String groupSummaryChecker;
 /**
 * 批准人
 */
 @ExcelProperty( value = "批准人",index = 56)
 private String approvedBy;
 /**
 * 发出单位审核结论是否合格
 */
 @ExcelProperty( value = "发出单位审核结论是否合格",index = 60)
 private String isQualified;
 /**
 * 关闭原因
 */
 @ExcelProperty( value = "关闭原因",index = 61)
 private String closeReason;
 /**
 * 审核结论
 */
 @ExcelProperty( value = "审核结论",index = 62)
 private String checkConclusion;
 /**
 * 审核时间
 */
 @ExcelProperty( value = "审核时间",index = 63)
 private Date checkDate;
 /**
 * 审核人
 */
 @ExcelProperty( value = "审核人",index = 64)
 private String report8DChecker;
 /**
 * 提交审核人
 */
 @ExcelProperty( value = "提交审核人",index = 65)
 private String submitChecker;
 /**
 * 审核备注
 */
 @ExcelProperty( value = "审核备注",index = 66)
 private String checkComment;
 /**
 * 创建人ID
 */
 @ExcelProperty( value = "创建人ID",index = 67)
 private Long createdId;
 /**
 * 创建人
 */
 @ExcelProperty( value = "创建人",index = 68)
 private String createdBy;
 /**
 * 创建时间
 */
 @ExcelProperty( value = "创建时间",index = 69)
 private Date creationDate;
 /**
 * 创建人IP
 */
 @ExcelProperty( value = "创建人IP",index = 70)
 private String createdByIp;
 /**
 * 最后更新人ID
 */
 @ExcelProperty( value = "最后更新人ID",index = 71)
 private Long lastUpdatedId;
 /**
 * 更新人
 */
 @ExcelProperty( value = "更新人",index = 72)
 private String lastUpdatedBy;
 /**
 * 最后更新时间
 */
 @ExcelProperty( value = "最后更新时间",index = 73)
 private Date lastUpdatedDate;
 /**
 * 最后更新人IP
 */
 @ExcelProperty( value = "最后更新人IP",index = 74)
 private String lastUpdatedByIp;
 /**
 * 租户ID
 */
 @ExcelProperty( value = "租户ID",index = 75)
 private String tenantId;
 /**
 * 版本号
 */
 @ExcelProperty( value = "版本号",index = 76)
 private Long version;
 /**
 * 错误信息提示
 */
 @ExcelProperty( value = "错误信息提示",index = 77)
 private String errorMsg;

}