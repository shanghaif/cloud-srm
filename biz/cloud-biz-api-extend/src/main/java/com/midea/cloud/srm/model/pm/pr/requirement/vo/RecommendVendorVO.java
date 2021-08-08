package com.midea.cloud.srm.model.pm.pr.requirement.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <pre>
 *  邀请供应商  视图对象
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-22 17:02
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class RecommendVendorVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long requirementHeadId;//采购需求头ID
    private String requirementHeadNum;//申请编号
    private Long requirementLineId;//采购需求行ID
    private Integer rowNum;//申请行号
    private Long organizationId;//采购组织id
    private String organizationCode;//采购组织Code
    private String fullPathId; //组织全路径虚拟ID
    private String organizationName;//采购组织名称
    private Long categoryId;//品类id
    private String categoryName;//品类名称
    private Long itemId;//物料id
    private String itemCode;//物料编码
    private String itemDesc;//物料描述
    private BigDecimal requirementQuantity;//需求数量
    private Long vendorId;//供应商id
    private String vendorCode;//供应商编码
    private String vendorName;//供应商名称
    private BigDecimal orderQuantity;// 可下单数量
    private BigDecimal notaxPrice;//单价（未税）
    private String currency;//币种
    private String taxKey;//税率编码
    private String taxRate;//税率
    private String priceUnit;//价格单位
    private BigDecimal quota;//配额
    private String buyerName;//采购员名称
    private String inventoryPlace;//库存地点
    private String unit;//单位
    private LocalDate requirementDate;//需求日期
    private String costNum;//成本编号
    private String costType;//成本类型
    private String receivedFactory;//收货工厂

    //    private BigDecimal standardQuota; // 标准配额比(取该物料，该供应商的标准匹配额比)
    private BigDecimal alreadyQuota; // 已分配配额比(计算该物料，该供应商已分配总数量的占比)
    private BigDecimal alreadyNum; // 已分配数量(取该物料，该供应商的已分配量)
    private BigDecimal orderQuota;// 本次分配数量(展示该供应商的计划分配数量，可修改)(订单数量)
    private BigDecimal totalDistribution; // 本次分配总量(汇总值,展示该供应商、该物料本次分配的总数量，即【本次分配数量】之和)
    private BigDecimal afterQuota; // 分配后配额 (展示经过本次分配后供应商的分配比例 分配后配额=（该供应商本次分配总量+该供应商已分配数量）/（该物料)
    private int allocationQuotaFlag; // 分配配额标识(0:未分配,1:已分配)

}
