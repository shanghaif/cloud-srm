package com.midea.cloud.srm.model.pm.pr.requirement.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.midea.cloud.srm.model.common.converter.ExcelNumToStringConverter;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  采购需求行导入 DTO
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-18 16:47:40
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(25)
public class RequirementLineImportDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NumberFormat
    @ExcelProperty(value = "申请行号")
    private Integer rowNum;

    @ExcelProperty(value = "采购组织")
    private String purchaseOrganization;

    @ExcelProperty(value = "需求部门")
    private String requirementDepartment;

    @NumberFormat
    @ExcelProperty(value = "预算")
    private BigDecimal budget;

    @ExcelProperty(value = "物料编码", converter = ExcelNumToStringConverter.class)
    private String itemCode;

    @ExcelProperty(value = "需求数量")
    private BigDecimal requirementQuantity;

    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(value = "需求日期")
    private Date requirementDate;

    @ExcelProperty(value = "申请原因")
    private String applyReason;

    @ExcelProperty(value = "库存地点")
    private String inventoryPlace;

    @ExcelProperty(value = "成本类型")
    private String costType;

    @ExcelProperty(value = "成本编号")
    private String costNum;

    @ExcelProperty(value = "指定品牌")
    private String brand;


}
