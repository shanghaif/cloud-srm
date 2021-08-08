package com.midea.cloud.srm.model.pm.pr.requirement.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/20
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class RequirementExport implements Serializable {

    @ExcelProperty(value = "申请编号", index = 0 )
    private String requirementHeadNum;

    @ExcelProperty(value = "ERP申请编号", index = 1 )
    private String esRequirementHeadNum;

    @ExcelProperty(value = "采购类型", index = 2 )
    private String ceeaPurchaseType;

    @ExcelProperty(value = "申请状态", index = 3 )
    private String auditStatus;

    @ExcelProperty(value = "申请日期", index = 4 )
    private Date applyDate;

    @ExcelProperty(value = "业务实体", index = 5 )
    private String orgName;

    @ExcelProperty(value = "库存组织", index = 6 )
    private String organizationName;

    @ExcelProperty(value = "申请部门", index = 7 )
    private String ceeaDepartmentName;

    @ExcelProperty(value = "申请人", index = 8 )
    private String createdFullName;

    @ExcelProperty(value = "物料大类", index = 9 )
    private String categoryName;

    @ExcelProperty(value = "项目编号", index = 10 )
    private String ceeaProjectNum;

    @ExcelProperty(value = "项目名称", index = 11 )
    private String ceeaProjectName;

    @ExcelProperty(value = "项目负责人名称", index = 12 )
    private String ceeaProjectUserNickname;

    @ExcelProperty(value = "财务业务小类", index = 13 )
    private String ceeaBusinessSmall;

    @ExcelProperty(value = "头备注", index = 14 )
    private String comments;

    @ExcelProperty(value = "申请行号", index = 15 )
    private Integer rowNum;

    @ExcelProperty(value = "行状态", index = 16 )
    private String applyStatus;

    // TODO 转名称
    @ExcelProperty(value = "物料分类", index = 17 )
    private String lineCategoryName;

    @ExcelProperty(value = "物料编号", index = 18 )
    private String materialCode;

    @ExcelProperty(value = "物料名称", index = 19 )
    private String materialName;

    @ExcelProperty(value = "单位", index = 20 )
    private String unit;

    @ExcelProperty(value = "申请数量", index = 21 )
    private BigDecimal requirementQuantity;

    @ExcelProperty(value = "预算单价", index = 22 )
    private BigDecimal notaxPrice;

    @ExcelProperty(value = "预算金额", index = 23 )
    private BigDecimal totalAmount;

    @ExcelProperty(value = "是否目录化", index = 24 )
    private String ceeaIfDirectory;

    @ExcelProperty(value = "需求日期", index = 25 )
    private Date requirementDate;

    @ExcelProperty(value = "指定供应商", index = 26 )
    private String vendorName;

    @ExcelProperty(value = "已下单数量", index = 27 )
    private BigDecimal ceeaExecutedQuantity;

    @ExcelProperty(value = "需求变更数量", index = 28 )
    private BigDecimal ceeaFirstQuantity;

    @ExcelProperty(value = "需求人", index = 29 )
    private String dmandLineRequest;

    // TODO 字段名字转换
    @ExcelProperty(value = "明细备注", index = 30 )
    private String lineComments;

    @ExcelProperty(value = "收货地点", index = 31 )
    private String ceeaDeliveryPlace;

























}
