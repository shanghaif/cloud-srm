package com.midea.cloud.srm.model.cm.contract.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

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
 *  修改日期: 2020/12/10 14:52
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class ContractLineExport {
    /**
     * 合同序列号
     */
    @ExcelProperty(value = "合同序列号", index = 0 )
    private String contractNo;

    /**
     * 合同编号
     */
    @ExcelProperty(value = "合同编号", index = 1 )
    private String contractCode;

    /**
     * 合同状态
     */
    @ExcelProperty(value = "合同状态", index = 2 )
    private String contractStatus;

    /**
     * 已下单数量
     */
    @ExcelProperty(value = "已下单数量", index = 3 )
    private BigDecimal ceeaUsedNumber;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 4 )
    private Date creationDate;

    /**
     * 采购员名称
     */
    @ExcelProperty(value = "采购员名称", index = 5 )
    private String createdBy;

    /**
     * 合同名称
     */
    @ExcelProperty(value = "合同名称", index = 6 )
    private String contractName;

    /**
     * 合同类型,对应字典:CONTRACT_TYPE
     */
    @ExcelProperty(value = "合同类型", index = 7 )
    private String contractClass;

    /**
     * 合同模板
     */
    @ExcelProperty(value = "合同模板", index = 8 )
    private String modelName;

    /**
     * 供应商名称
     */
    @ExcelProperty(value = "供应商名称", index = 9 )
    private String vendorName;

    /**
     * 是否总部(Y-是,N-否;默认为否)
     */
    @ExcelProperty(value = "是否总部", index = 10 )
    private String isHeadquarters;

    /**
     * 合同有效日期从
     */
    @ExcelProperty(value = "合同有效日期从", index = 11 )
    private Date effectiveDateFrom;

    /**
     * 合同有效日期至
     */
    @ExcelProperty(value = "合同有效日期至", index = 12 )
    private Date effectiveDateTo;

    /**
     * 币种名称
     */
    @ExcelProperty(value = "币种名称", index = 13 )
    private String currencyName;

    /**
     * 合同级别(字典:CONTRACT_LEVEL)
     */
    @ExcelProperty(value = "合同级别", index = 14 )
    private String contractLevel;



    /**
     * 价格审批单号
     */
    @ExcelProperty(value = "价格审批单号", index = 15 )
    private String sourceNumber;

    /**
     * 寻源类型
     */
    /*@ExcelProperty(value = "寻源类型", index = 16 )
    private String sourceType;*/

    /**
     * 业务实体
     */
    @ExcelProperty(value = "业务实体", index = 16 )
    private String buName;

    /**
     * 库存组织
     */
    @ExcelProperty(value = "库存组织", index = 17 )
    private String invName;

    /**
     * 物料编码
     */
    @ExcelProperty(value = "物料编码", index = 18 )
    private String materialCode;

    /**
     * 物料名称
     */
    @ExcelProperty(value = "物料名称", index = 19 )
    private String materialName;


    /**
     * 物料小类
     */
    @ExcelProperty(value = "物料小类", index = 20 )
    private String categoryName;

    /**
     * 物料中类
     */
    @ExcelProperty(value = "物料中类", index = 21 )
    private String middleCategoryName;

    /**
     * 物料大类
     */
    @ExcelProperty(value = "物料大类", index = 22 )
    private String bigCategoryName;

    /**
     * 交货日期
     */
    @ExcelProperty(value = "交货日期", index = 23 )
    private Date deliveryDate;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量", index = 24 )
    private BigDecimal contractQuantity;

    /**
     * 单位
     */
    @ExcelProperty(value = "单位", index = 25 )
    private String unitName;

    /**
     * 单价
     */
    @ExcelProperty(value = "单价", index = 26 )
    private BigDecimal taxedPrice;

    /**
     * 税前金额
     */
    @ExcelProperty(value = "税前金额", index = 27 )
    private BigDecimal unAmount;

    /**
     * 含税金额
     */
    @ExcelProperty(value = "含税金额", index = 28 )
    private BigDecimal amount;

    /**
     * 税率
     */
    @ExcelProperty(value = "税率", index = 29 )
    private BigDecimal taxRate;

    /**
     * 合同总金额（含税）：以物料明细行金额自动汇总
     */
    @ExcelProperty(value = "合同总金额", index = 30 )
    private BigDecimal includeTaxAmount;


    /**
     * 质保期
     */
    @ExcelProperty(value = "质保期", index = 31 )
    private Integer shelfLife;

    /**
     * 是否框架协议(Y-是,N-否;默认为否)
     */
    @ExcelProperty(value = "是否框架协议", index = 32 )
    private String isFrameworkAgreement;

    /**
     * 是否虚拟合同（N/Y）
     */
    @ExcelProperty(value = "是否虚拟合同", index = 33 )
    private String ceeaIfVirtual;

    /**
     * 合同备注
     */
    @ExcelProperty(value = "合同备注", index = 34 )
    private String contractRemark;

    /**
     * 发运地
     */
    @ExcelProperty(value = "发运地", index = 35 )
    private String shipFrom;

    /**
     * 目的地
     */
    @ExcelProperty(value = "目的地", index = 36 )
    private String destination;

    /**
     * 价格生效日期(YYYY-MM-DD)
     */
    @ExcelProperty(value = "价格生效日期", index = 37 )
    private Date startDate;

    /**
     * 价格失效日期(YYYY-MM-DD)
     */
    @ExcelProperty(value = "价格失效日期", index = 38 )
    private Date endDate;

    /**
     * 是否便携合同(Y-是,N-否)
     */
    @ExcelProperty(value = "是否便携合同", index = 39 )
    private String ceeaIsPortableContract;

    /**
     * 物料编码
     */
    @ExcelIgnore
    private Long materialId;

    /**
     * 采购分类大类
     */
    @ExcelIgnore
    private Long bigPurchaseCategoryId;

    /**
     * 采购分类中类
     */
    @ExcelIgnore
    private Long middlePurchaseCategoryId;


    //todo 送审业务实体 合同行号 寻源单号行号 采购申请号行号 分项 详细地址信息备注(发运地、目的地详细备注) 合同审结日期 审核人


}
