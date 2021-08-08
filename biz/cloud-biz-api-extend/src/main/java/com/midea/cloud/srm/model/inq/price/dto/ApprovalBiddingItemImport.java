package com.midea.cloud.srm.model.inq.price.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <pre>
 * 价格审批单-中标行信息 导入模型
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/11
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class ApprovalBiddingItemImport implements Serializable {

    /**
     * 价格类型
     */
    @ExcelProperty( value = "价格类型",index = 0)
    private String priceType;

    /**
     * 业务实体名称
     */
    @ExcelProperty( value = "*业务实体名称",index = 1)
    private String orgName;

    /**
     * 库存组织名称
     */
    @ExcelProperty( value = "*库存组织名称",index = 2)
    private String organizationName;

    /**
     * 物料编码
     */
    @ExcelProperty( value = "*物料编码",index = 3)
    private String itemCode;

    /**
     * 物料描述
     */
    @ExcelProperty( value = "物料描述",index = 4)
    private String itemDescription;

    /**
     * 供应商编码
     */
    @ExcelProperty( value = "*供应商编码",index = 5)
    private String vendorCode;

    /**
     * 供应商名称
     */
    @ExcelProperty( value = "供应商名称",index = 6)
    private String vendorName;

    /**
     * 到货地点
     */
    @ExcelProperty( value = "到货地点",index = 7)
    private String arrivalPlace;

    /**
     * 单价（含税）
     */
    @ExcelProperty( value = "含税单价",index = 8)
    private String taxPrice;

    /**
     * 税率编码
     */
    @ExcelProperty( value = "*税率编码",index = 9)
    private String taxKey;

    /**
     * 税率
     */
    @ExcelProperty( value = "税率",index = 10)
    private String taxRate;

    /**
     * 币种编码
     */
    @ExcelProperty( value = "币种编码",index = 11)
    private String currencyCode;

    /**
     * 配额分配类型
     */
    @ExcelProperty( value = "配额分配类型",index = 12)
    private String quotaDistributionType;

    /**
     * 配额比例
     */
    @ExcelProperty( value = "配额比例",index = 13)
    private String quotaProportion;

    /**
     * 价格开始生效日期
     */
    @ExcelProperty( value = "价格开始生效自",index = 14)
    private String startTime;

    /**
     * 价格失效日期
     */
    @ExcelProperty( value = "价格开始生效至",index = 15)
    private String endTime;

    /**
     * 需求数量
     */
    @ExcelProperty( value = "需求数量",index = 16)
    private String needNum;

    /**
     * 最小起订量
     */
    @ExcelProperty( value = "最小起订量",index = 17)
    private String minOrderQuantity;

    /**
     * L/T
     */
    @ExcelProperty( value = "L/T",index = 18)
    private String lAndT;

    /**
     * 错误
     */
    @ExcelProperty( value = "错误信息提示",index = 19)
    private String errorMsg;
}
