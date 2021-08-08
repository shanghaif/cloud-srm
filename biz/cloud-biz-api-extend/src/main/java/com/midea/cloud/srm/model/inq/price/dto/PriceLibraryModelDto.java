package com.midea.cloud.srm.model.inq.price.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  价格目录导入模板
 * </pre>
 *
 * @author dengyl23@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/29 14:04
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(30)
@HeadRowHeight(20)
public class PriceLibraryModelDto implements Serializable {

    @ExcelProperty(value = "*物料编码", index = 0)
    private String itemCode;

    @ExcelProperty(value = "*物料描述", index = 1)
    private String itemDesc;

    @ExcelProperty(value = "*供应商ERP编号", index = 2)
    private String vendorCode;

    @ExcelProperty(value = "供应商名称", index = 3)
    private String vendorName;

    /**
     * 业务实体编码
     */
    @ExcelProperty(value = "*业务实体", index = 4)
    private String ceeaOrgCode;

    /**
     * 库存组织编码
     */
    @ExcelProperty(value = "*库存组织", index = 5)
    private String ceeaOrganizationCode;

    /**
     * 到货地点
     */
    @ExcelProperty(value = "到货地点", index = 6)
    private String ceeaArrivalPlace;

    /**
     * 单价（含税）（BigDecimal）
     */
    @ExcelProperty(value = "*含税价", index = 7)
    private String taxPrice;

    /**
     * 税率编码
     */
    @ExcelProperty(value = "*税码", index = 8)
    private String taxKey;

    /**
     * 税率值（产品的坑，应该是BigDecimal，因多处引用无法修改）
     */
    @ExcelProperty(value = "*税率", index = 9)
    private String taxRate;
    /**
     * 币种编码
     */
    @ExcelProperty(value = "*币种", index = 10)
    private String currencyCode;


    /**
     * 配额分配类型
     */
    @ExcelProperty(value = "配额分配类型", index = 11)
    private String ceeaAllocationType;

    /**
     * 配额比例（原类型BigDecimal）
     */
    @ExcelProperty(value = "配额比例", index = 12)
    private String ceeaQuotaProportion;

    /**
     * 有效期（原类型Date，格式YYYY-MM-DD）
     */
    @ExcelProperty(value = "*价格有效期自", index = 13)
    private String effectiveDate;

    /**
     * 失效日期（原类型Date，格式YYYY-MM-DD）
     */
    @ExcelProperty(value = "*价格有效期至", index = 14)
    private String expirationDate;

    /**
     * 付款条件
     */
    @ExcelProperty(value = "*付款条件", index = 15)
    private String paymentTerms;

    /**
     * 付款方式
     */
    @ExcelProperty(value = "*付款方式", index = 16)
    private String paymentType;

    /**
     * 账期
     */
    @ExcelProperty(value = "*账期", index = 17)
    private String paymentDays;
    /**
     * L/T
     */
    @ExcelProperty(value = "*L/T", index = 18)
    private String ceeaLt;


    @ExcelProperty(value = "*框架协议", index = 19)
    private String frameworkAgreement;
}
