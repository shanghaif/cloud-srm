package com.midea.cloud.srm.model.inq.price.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
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
 *  修改日期: 2020/11/6
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class PriceLibraryExportDto implements Serializable {

    /**
     * 寻源单号
     */
    @ExcelProperty(value = "寻源单号", index = 0)
    private String sourceNo;

    /**
     * 报价行id
     */
    @ExcelProperty(value = "报价行id", index = 1)
    private String quotationLineId;

    /**
     * 物料编码
     */
    @ExcelProperty(value = "物料编码", index = 2)
    private String itemCode;

    /**
     * 物料描述
     */
    @ExcelProperty(value = "物料描述", index = 3)
    private String itemDesc;

    /**
     * 供应商编号
     */
    @ExcelProperty(value = "供应商编号", index = 4)
    private String vendorCode;


    /**
     * 供应商名称
     */
    @ExcelProperty(value = "供应商名称", index = 5)
    private String vendorName;

    /**
     * 业务实体
     */
    @ExcelProperty(value = "业务实体", index = 6)
    private String ceeaOrgName;

    /**
     * 库存组织
     */
    @ExcelProperty(value = "库存组织", index = 7)
    private String ceeaOrganizationCode;

    /**
     * 到货地点
     */
    @ExcelProperty(value = "到货地点", index = 8)
    private String ceeaArrivalPlace;


    /**
     * 价格类型
     */
    @ExcelProperty(value = "价格类型", index = 9)
    private String priceType;

    /**
     * 含税价
     */
    @ExcelProperty(value = "含税价", index = 10)
    private BigDecimal taxPrice;

    /**
     * 税码
     */
    @ExcelProperty(value = "税码", index = 11)
    private String taxKey;


    /**
     * 税率
     */
    @ExcelProperty(value = "税率", index = 12)
    private String taxRate;

    /**
     * 币种编码
     */
    @ExcelProperty(value = "币种编码", index = 13)
    private String currencyCode;

    /**
     * 配额分配类型
     */
    @ExcelProperty(value = "配额分配类型", index = 14)
    private String ceeaAllocationType;

    /**
     * 配额比例
     */
    @ExcelProperty(value = "配额比例", index = 15)
    private BigDecimal ceeaQuotaProportion;

    /**
     * 有效日期
     */
    @ExcelProperty(value = "价格有效期自", index = 16)
    private Date effectiveDate;

    /**
     * 失效日期
     */
    @ExcelProperty(value = "价格有效期至", index = 17)
    private Date expirationDate;

    /**
     * L/T
     */
    @ExcelProperty(value = "L/T", index = 18)
    private String ceeaLt;

    /**
     * 框架协议编号(合同编号)
     */
    @ExcelProperty(value = "合同编号", index = 19)
    private String contractCode;

    /**
     * 是否已上架
     */
    @ExcelProperty(value = "是否已上架(Y/N)", index = 20)
    private String ceeaIfUse;
}
