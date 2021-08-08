package com.midea.cloud.srm.model.base.material.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author dengyl23@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/27 19:29
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class PurchaseCatalogQueryDto extends BaseDTO {

    /**
     * 序号
     */
    private Integer rowId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 业务主体名称
     */
    private String orgName;

    /**
     * 库存组织名称
     */
    private String organizationName;

    /**
     * 最小起订量
     */
    private BigDecimal ceeaOrderQuantityMinimum;

    /**
     * 是否目录化
     */
    private String ceeaIfUse;

    /**
     * 业务实体ID
     */
    private Long ceeaOrgId;

    /**
     * 库存组织ID
     */
    private Long ceeaOrganizationId;
    /**
     * 库存组织编码
     */
    private String ceeaOrganizationCode;

    /**
     * 业务实体编码
     */
    private String ceeaOrgCode;

    /**
     * 物料小类ID
     */
    private Long categoryId;
    /**
     * 单价（含税）
     */
    private BigDecimal taxPrice;

    /**
     * 币种id
     */
    private Long currencyId;

    /**
     * 币种编码
     */
    private String currencyCode;

    /**
     * 币种名称
     */
    private String currencyName;

    /**
     * 物料ID
     */
    private Long materialId;
    /**
     * 采购分类全名
     */
    private String categoryFullName;

    /**
     * 单位
     */
    private String unit;

    /**
     * 一站式采购-供应商id
     */
    private Long ceeaSupplierId;

    /**
     * 一站式采购-供应商编码
     */
    private String ceeaSupplierCode;

    /**
     * 一站式采购-供应商名称
     */
    private String ceeaSupplierName;
    /**
     * 规格型号(维护100个汉字)
     */
    private String specification;
    /**
     * 单位名称
     */
    private String unitName;
    /**
     * 合同编号
     */
    private String ceeaContractNo;
}
