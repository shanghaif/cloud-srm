package com.midea.cloud.srm.model.inq.price.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  价格目录添加
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-14 10:20
 *  修改内容:
 * </pre>
 */
@Data
public class PriceLibraryAddParam {

    /**
     * 价格id
     */
    private Long priceLibraryId;

    /**
     * 采购组织id
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 采购组织名称
     */
    private String organizationName;

    /**
     * 供应商id
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
     * 寻源方式
     */
    private String sourceType;

    /**
     * 寻源单号
     */
    private String sourceNo;

    /**
     * 审批单号
     */
    private String approvalNo;

    /**
     * 物料id
     */
    private Long itemId;

    /**
     * 物料编码
     */
    private String itemCode;

    /**
     * 物料描述
     */
    private String itemDesc;

    /**
     * 品类id
     */
    private Long categoryId;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 物料组编码
     */
    private String itemGroupCode;

    /**
     * 规格型号
     */
    private String specification;

    /**
     * 订单数量
     */
    private BigDecimal orderQuantity;

    /**
     * 单价（未税）
     */
    private BigDecimal notaxPrice;

    /**
     * 单价（含税）
     */
    private BigDecimal taxPrice;

    /**
     * 是否阶梯价
     */
    private String isLadder;

    /**
     * 阶梯价类型
     */
    private String ladderType;

    /**
     * 合同号
     */
    private String contractNo;

    /**
     * 最小订单数量
     */
    private String minOrderQuantity;

    /**
     * 最小包装数量
     */
    private String minPackQuantity;

    /**
     * 价格类型
     */
    private String priceType;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 单位
     */
    private String unit;

    /**
     * 税率编码
     */
    private String taxKey;

    /**
     * 税率
     */
    private String taxRate;

    /**
     * 币种
     */
    private String currency;

    /**
     * 有效日期
     */
    private Date effectiveDate;

    /**
     * 失效日期
     */
    private Date expirationDate;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 同步状态
     */
    private String syncStatus;

    /**
     * 删除标识
     */
    private String delFlag;

    /**
     * 阶梯价
     */
    private List<PriceLibraryLadderPriceAddParam> ladderPrices;
}