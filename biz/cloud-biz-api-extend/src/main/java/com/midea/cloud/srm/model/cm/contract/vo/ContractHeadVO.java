package com.midea.cloud.srm.model.cm.contract.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  合同头信息DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-28 10:03
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class ContractHeadVO extends BaseDTO {
    /**
     * 合同序号
     */
    @ExcelProperty(value = "合同序号", index = 0)
    private String contractNo;
    /**
     * 合同编号
     */
    @ExcelProperty(value = "合同编号", index = 1)
    private String contractCode;
    /**
     * 合同状态
     */
    @ExcelProperty(value = "合同状态", index = 2)
    private String contractStatus;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建日期", index = 3)
    private Date creationDate;
    /**
     * 创建人
     */
    @ExcelProperty(value = "采购员", index = 4)
    private String createdBy;
    /**
     * 合同名称
     */
    @ExcelProperty(value = "合同名称", index = 5)
    private String contractName;
    /**
     * 合同类型
     */
    @ExcelProperty(value = "合同类型", index = 6)
    private String contractClass;
    /**
     * 模板名称
     */
    @ExcelProperty(value = "合同模板", index = 7)
    private String templName;
    /**
     * 供应商名称
     */
    @ExcelProperty(value = "供应商", index = 8)
    private String vendorName;
    /**
     * 是否总部(Y-是,N-否;默认为否)
     */
    @ExcelProperty(value = "是否总部", index = 9)
    private String isHeadquarters;
    /**
     * 合同有效日期从
     */
    @ExcelProperty(value = "合同有效日期从", index = 10)
    private String effectiveDateFrom;

    /**
     * 合同有效日期至
     */
    @ExcelProperty(value = "合同有效日期至", index = 11)
    private String effectiveDateTo;
    /**
     * 币种名称
     */
    @ExcelProperty(value = "币种", index = 12)
    private String currencyName;
    /**
     * 合同级别(字典:CONTRACT_LEVEL)
     */
    @ExcelProperty(value = "合同级别", index = 13)
    private String contractLevel;
    /**
     * 业务实体名称
     */
    @ExcelProperty(value = "送审主体业务实体", index = 14)
    private String orgName;
    /**
     * 明细行号（1，2，...）
     */
    @ExcelProperty(value = "合同行号", index = 15)
    private Long Line;
    /**
     * 寻源方式
     */
    @ExcelProperty(value = "寻源类型", index = 16)
    private String sourceType;
    /**
     * 采购申请单号-行号
     */
    @ExcelProperty(value = "采购申请号行号", index = 17)
    private String reqNumber;
    /**
     * 业务实体名称
     */
    @ExcelProperty(value = "业务实体", index = 18)
    private String buName;
    /**
     * 库存组织名称
     */
    @ExcelProperty(value = "库存组织", index = 19)
    private String invName;
    /**
     * 物料编码
     */
    @ExcelProperty(value = "物资编码", index = 20)
    private String materialCode;
    /**
     * 物料名称
     */
    @ExcelProperty(value = "物料名称", index = 21)
    private String materialName;
    /**
     * 采购分类名称
     */
    @ExcelProperty(value = "物资小类", index = 22)
    private String categoryName;
    /**
     * 采购分类全名
     */
    @ExcelProperty(value = "采购分类全名", index = 23)
    private String categoryFullName;
    /**
     * 订单数量
     */
    @ExcelProperty(value = "订单数量", index = 24)
    private BigDecimal orderQuantity;
    /**
     * 价格单位
     */
    @TableField("PRICE_UNIT")
    @ExcelProperty(value = "单位", index = 25)
    private String priceUnit;
    /**
     * 金额
     */
    @ExcelProperty(value = "单价", index = 26)
    private BigDecimal amount;
    /**
     * 未税单价
     */
    @ExcelProperty(value = "未税单价", index = 27)
    private BigDecimal untaxedPrice;

    /**
     * 含税单价
     */
    @ExcelProperty(value = "含税单价", index = 28)
    private BigDecimal taxedPrice;
    /**
     * 税率
     */
    @ExcelProperty(value = "税率", index = 29)
    private BigDecimal taxRate;
    /**
     * 合同总金额（含税）：以物料明细行金额自动汇总
     */
    @ExcelProperty(value = "合同总金额（含税）", index = 30)
    private BigDecimal includeTaxAmount;
    /**
     * 生效日期(YYYY-MM-DD)
     */
    @ExcelProperty(value = "价格执行有效期自", index = 31)
    private String startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @ExcelProperty(value = "价格执行有效期至", index = 32)
    private String endDate;
    /**
     * 保质期(月)
     */
    @ExcelProperty(value = "保质期", index = 33)
    private BigDecimal shelfLife;
    /**
     * 合同备注
     */
    @ExcelProperty(value = "合同备注", index = 34)
    private String contractRemark;
    /**
     * 最后更新时间
     */
    @ExcelProperty(value = "合同审结日期", index = 35)
    private Date lastUpdateDate;
    @ExcelIgnore
    private int row;
}

