package com.midea.cloud.srm.model.inq.price.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <pre>
 *  价格目录导出
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-26 9:00
 *  修改内容:
 * </pre>
 */
@Data
public class PriceLibraryExcelVO {

    /**
     * 寻源单号
     */
    @ExcelProperty(value = "寻源单号")
    private String sourceNo;

    /**
     * 寻源方式
     */
    @ExcelProperty(value = "寻源方式")
    private String sourceType;

    /**
     * 审批单号
     */
    @ExcelProperty(value = "审批单号")
    private String approvalNo;

    /**
     * 供应商编码
     */
    @ExcelProperty(value = "供应商编码")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @ExcelProperty(value = "供应商名称")
    private String vendorName;

    /**
     * 采购组织名称
     */
    @ExcelProperty(value = "采购组织名称")
    private String organizationName;

    /**
     * 物料编码
     */
    @ExcelProperty(value = "物料编码")
    private String itemCode;

    /**
     * 物料描述
     */
    @ExcelProperty(value = "物料描述")
    private String itemDesc;

    /**
     * 规格型号
     */
    @ExcelProperty(value = "规格型号")
    private String specification;

    /**
     * 单位
     */
    @ExcelProperty(value = "单位")
    private String unit;

    /**
     * 单价（未税）
     */
    @ExcelProperty(value = "单价（未税）")
    private BigDecimal notaxPrice;

    /**
     * 有效日期
     */
    @ExcelProperty(value = "有效日期")
    private String effectiveDate;

    /**
     * 失效日期
     */
    @ExcelProperty(value = "失效日期")
    private String expirationDate;

    /**
     * 币种
     */
    @ExcelProperty(value = "币种")
    private String currency;

    /**
     * 税率编码
     */
    @ExcelProperty(value = "税码")
    private String taxKey;

    /**
     * 税率
     */
    @ExcelProperty(value = "税率")
    private String taxRate;

    /**
     * 阶梯价类型
     */
    @ExcelProperty(value = "阶梯价类型")
    private String ladderType;

    /**
     * 合同号
     */
    @ExcelProperty(value = "合同号")
    private String contractNo;

    /**
     * 物料组编码
     */
    @ExcelProperty(value = "物料组编码")
    private String itemGroupCode;

    /**
     * 最小订单数量
     */
    @ExcelProperty(value = "最小订单数量")
    private String minOrderQuantity;

    /**
     * 最小包装数量
     */
    @ExcelProperty(value = "最小包装数量")
    private String minPackQuantity;

    /**
     * 价格类型
     */
    @ExcelProperty(value = "价格类型")
    private String priceType;

    /**
     * 业务类型
     */
    @ExcelProperty(value = "业务类型")
    private String businessType;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remarks;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private String creationDate;

    /**
     * 最后更新时间
     */
    @ExcelProperty(value = "最后更新时间")
    private String lastUpdateDate;
}