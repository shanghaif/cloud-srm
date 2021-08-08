package com.midea.cloud.srm.model.cm.contract.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <pre>
 *   合同物料DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-6-4 9:45
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class ContractMaterialDTO extends BaseDTO{


    /**
     * 主键ID,合同物料ID
     */
    private Long contractMaterialId;

    /**
     * 项次
     */
    private Long lineNumber;

    /**
     * 合同头信息ID
     */
    private Long contractHeadId;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 供应商编码
     */
    private String vendorCode;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 采购组织ID
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 采购组织编码
     */
    private String organizationCode;

    /**
     * 采购组织名称
     */
    private String organizationName;

    /**
     * 合同状态
     */
    private String contractStatus;

    /**
     * 采购分类编码
     */
    private String categoryCode;

    /**
     * 采购分类ID
     */
    private Long categoryId;

    /**
     * 采购分类名称
     */
    private String categoryName;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 未税单价
     */
    private BigDecimal untaxedPrice;

    /**
     * 已税单价
     */
    private BigDecimal taxedPrice;

    /**
     * 价格单位
     */
    private String priceUnit;

    /**
     * 单位编码
     */
    private String unitCode;

    /**
     * 单位ID
     */
    private Long unitId;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 币种编码
     */
    private String currencyCode;

    /**
     * 币种ID
     */
    private String currencyId;

    /**
     * 币种名称
     */
    private String currencyName;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 订单行号
     */
    private Long orderLineNumber;

    /**
     * 订单数量
     */
    private BigDecimal orderQuantity;

}
