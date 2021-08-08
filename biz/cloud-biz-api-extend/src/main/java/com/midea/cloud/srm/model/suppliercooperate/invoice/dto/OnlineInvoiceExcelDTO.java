package com.midea.cloud.srm.model.suppliercooperate.invoice.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
 *  修改日期: 2020/9/1 17:44
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class OnlineInvoiceExcelDTO implements Serializable {

    /**
     * 网上发票号
     */
    @ExcelProperty(value = "网上发票号",index = 0)
    private String onlineInvoiceNum;

    /**
     * 应付账款到期日
     */
    @ExcelProperty(value = "应付账款到期日",index = 1)
    private String accountPayableDealine;

    /**
     * 发票状态
     */
    @ExcelProperty(value = "发票状态",index = 2)
    private String invoiceStatus;

    /**
     * 导入状态
     */
    @ExcelProperty(value = "导入状态",index = 3)
    private String importStatus;

    /**
     * 业务实体名称
     */
    @ExcelProperty(value = "业务实体名称",index = 4)
    private String orgName;


    /**
     * 供应商编码
     */
    @ExcelProperty(value = "供应商编码",index = 5)
    private String vendorCode;

    /**
     * 供应商名称
     */
    @ExcelProperty(value = "供应商名称",index = 6)
    private String vendorName;

    /**
     * 成本类型名称
     */
    @ExcelProperty(value = "成本类型名称",index = 7)
    private String costTypeName;

    /**
     * 税务发票号
     */
    @ExcelProperty(value = "税务发票号",index = 8)
    private String taxInvoiceNum;
    /**
     * 费控发票号(备用,暂不使用)
     */
    @ExcelProperty(value = "费控发票号",index = 9)
    private String fsscNo;

    /**
     * 业务类型
     */
    @ExcelProperty(value = "业务类型",index = 10)
    private String businessType;

    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人",index = 11)
    private String createdBy;


    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间",index = 12)
    private String creationDate;

    /**
     * 摘要
     */
    @ExcelProperty(value = "备注",index = 13)
    private String comment;

    /**
     * 含税总额(系统)
     */
    @ExcelProperty(value = "含税总额(系统)",index = 14)
    private BigDecimal taxTotalAmount;

    /**
     * 税额(系统)
     */
    @ExcelProperty(value = "税额(系统)",index = 15)
    private BigDecimal totalTax;

    /**
     * 发票总额
     */
    @ExcelProperty(value = "发票总额",index = 16)
    private BigDecimal actualInvoiceAmountY;

    /**
     * 发票税额
     */
    @ExcelProperty(value = "发票税额",index = 17)
    private BigDecimal invoiceTax;

    /**
     * 附件张数
     */
    @ExcelProperty(value = "附件张数",index = 18)
    private Integer bpcount;

    /**
     * 付款账期名称(费控需要传)
     */
    @ExcelProperty(value = "付款条款",index = 19)
    private String payAccountPeriodName;
}
