package com.midea.cloud.srm.model.cm.contract.dto;

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
 *  修改日期: 2020/12/10 14:07
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class ContractHeadExport implements Serializable {
    @ExcelProperty(value = "序号", index = 0 )
    private String index;

    @ExcelProperty(value = "合同序号", index = 1 )
    private String contractNo;
    @ExcelProperty(value = "合同编号", index = 2 )
    private String contractCode;
    @ExcelProperty(value = "合同名称", index = 3 )
    private String contractName;
    @ExcelProperty(value = "价格审批单号", index = 4 )
    private String sourceNumber;
    @ExcelProperty(value = "状态", index = 5 )
    private String contractStatus;
    @ExcelProperty(value = "操作类型", index = 6 )
    private String sourceType;
    @ExcelProperty(value = "合同类型", index = 7 )
    private String contractType;
    @ExcelProperty(value = "业务实体", index = 8 )
    private String buName;
    @ExcelProperty(value = "供应商编码", index = 9 )
    private String vendorCode;
    @ExcelProperty(value = "供应商名称", index = 10 )
    private String vendorName;

    /**
     * 框架协议编号
     */
    @ExcelProperty(value = "框架协议编号", index = 11 )
    private String frameworkAgreementCode;

    /**
     * 框架协议名称
     */
    @ExcelProperty(value = "框架协议名称", index = 12 )
    private String frameworkAgreementName;

    /**
     * 原合同编号
     */
    @ExcelProperty(value = "原合同编号", index = 13 )
    private String contractOldCode;

    /**
     * 原合同序号
     */
    @ExcelProperty(value = "原合同序号", index = 14 )
    private String contractOldNo;

    /**
     * 模板名称
     */
    @ExcelProperty(value = "模板名称", index = 15 )
    private String modelName;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @ExcelProperty(value = "合同生效日期", index = 16 )
    private Date startDate;

    /**
     * 失效日期
     */
    @ExcelProperty(value = "合同失效日期", index = 17)
    private Date endDate;

    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人", index = 18 )
    private String createdBy;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 19 )
    private Date creationDate;

    /**
     * 最后更新人
     */
    @ExcelProperty(value = "最后更新人", index = 20 )
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @ExcelProperty(value = "最后更新时间", index = 21 )
    private Date lastUpdateDate;

    /**
     * 供应商确认时间
     */
    @ExcelProperty(value = "供应商确认时间", index = 22 )
    private LocalDate vendorConfirmDate;

    /**
     * 合同总金额（含税）：以物料明细行金额自动汇总
     */
    @ExcelProperty(value = "合同总金额", index = 23 )
    private BigDecimal includeTaxAmount;



}
