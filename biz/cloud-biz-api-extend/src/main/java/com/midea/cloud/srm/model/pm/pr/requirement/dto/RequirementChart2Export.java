package com.midea.cloud.srm.model.pm.pr.requirement.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/12/15 14:21
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class RequirementChart2Export implements Serializable {
    /**
     * 申请单编号
     */
    @ExcelProperty(value = "申请单编号", index = 0 )
    private String requirementHeadNum;

    /**
     * 采购履行人名称
     */
    @ExcelProperty(value = "采购履行人名称", index = 1 )
    private String ceeaPerformUserNickname;

    /**
     * 策略采购员名称
     */
    @ExcelProperty(value = "策略采购员名称", index = 2 )
    private String ceeaStrategyUserNickname;

    /**
     * 状态(数据字典APPLICATION_STATUS)
     */
    @ExcelProperty(value = "状态", index = 3 )
    private String applyStatus;

    /**
     * 寻源单据编号
     */
    @ExcelProperty(value = "寻源单据编号", index = 4 )
    private String sourceNo;

    /**
     * 购类型采购类型(CEEA, 数据字典PURCHASE_TYPE)
     */
    @ExcelProperty(value = "采购类型", index = 5 )
    private String ceeaPurchaseType;

    /**
     * 是否目录化(longi)
     */
    @ExcelProperty(value = "是否目录化", index = 6 )
    private String ceeaIfDirectory;

    /**
     * 需求部门
     */
    @ExcelProperty(value = "需求部门", index = 7 )
    private String requirementDepartment;

    /**
     * 部门名称
     */
    @ExcelProperty(value = "部门名称", index = 8 )
    private String ceeaDepartmentName;

    /**
     * 创建人
     */
    @ExcelProperty(value = "申请人", index = 9 )
    private String createdBy;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 10 )
    private Date creationDate;

    /**
     * 业务主体名称(longi)
     */
    @ExcelProperty(value = "业务实体", index = 11 )
    private String orgName;

    /**
     * 资产类别
     */
    @ExcelProperty(value = "资产类别", index = 12 )
    private String ceeaAssetType;

    /**
     * 是否暂挂，暂挂状态默认不查出来
     */
    @ExcelProperty(value = "是否暂挂", index = 13 )
    private String ifHold;

    /**是否取消(取消分配的时候为true)*/
    @ExcelProperty(value = "是否取消", index = 14 )
    private boolean enableUnAssigned;

    /**
     * 申请单行号
     */
    @ExcelProperty(value = "申请单行号", index = 15 )
    private Integer rowNum;

    /**
     * 库存组织名称(longi)
     */
    @ExcelProperty(value = "库存组织名称", index = 16 )
    private String organizationName;

    /**
     * 物料编码
     */
    @ExcelProperty(value = "物料编码", index = 17 )
    private String materialCode;

    /**
     * 物料名称
     */
    @ExcelProperty(value = "物料名称", index = 18 )
    private String materialName;

    /**
     * 物料小类名称(longi)
     */
    @ExcelProperty(value = "物料小类名称", index = 19 )
    private String categoryName;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称", index = 20 )
    private String ProjectName;

    /**
     * 单位描述
     */
    @ExcelProperty(value = "单位描述", index = 21 )
    private String unit;

    /**
     * 未税单价(预算单价)
     */
    @ExcelProperty(value = "预算单价", index = 22 )
    private BigDecimal notaxPrice;

    /**
     * 需求数量
     */
    @ExcelProperty(value = "需求数量", index = 23 )
    private BigDecimal requirementQuantity;

    /**
     * 采购订单号
     */
    @ExcelProperty(value = "采购订单号", index = 24 )
    private String orderNumber;

    /**
     * 已下单数量
     */
    @ExcelProperty(value = "已下单数量", index = 25 )
    private BigDecimal ceeaExecutedQuantity;

    /**
     * 剩余可下单数量
     */
    @ExcelProperty(value = "可下单数量", index = 26 )
    private BigDecimal orderQuantity;

    /**
     * 需求日期
     */
    @ExcelProperty(value = "需求日期", index = 27 )
    private Date requirementDate;

    /*是否指定*/
    @ExcelProperty(value = "是否指定", index = 28 )
    private String vendorName;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注", index = 29 )
    private String comments;

    /**
     * 项目编号
     */
    @ExcelProperty(value = "项目编号", index = 30 )
    private String ProjectNumber;

    /**
     * 任务编号
     */
    @ExcelProperty(value = "项目编号", index = 31 )
    private String TaskNumber;

    /**
     * 任务名称
     */
    @ExcelProperty(value = "项目编号", index = 32 )
    private String TaskName;

    /**
     * 合同序列号
     */
    @ExcelProperty(value = "合同序列号", index = 33 )
    private String contractNo;

    /**
     * 合同编号
     */
    @ExcelProperty(value = "合同编号", index = 34 )
    private String contractCode;

    /**
     * 审批通过时间
     */
    @ExcelProperty(value = "审批通过时间",index = 35)
    private Date approvalDate;
}
