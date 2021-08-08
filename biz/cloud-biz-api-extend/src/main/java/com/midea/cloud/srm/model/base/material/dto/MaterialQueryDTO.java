package com.midea.cloud.srm.model.base.material.dto;

import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  功能名称  物料查询DTO
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/25 15:39
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class MaterialQueryDTO extends BaseDTO{

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
     * 状态(Y启用,N不启用)
     */
    private String status;

    /**
     * 规格型号
     */
    private String specification;

    /**
     * 物料小类ID
     */
    private Long categoryId;

    /**
     * 物料小类名称
     */
    private String categoryName;

    /**
     * 物料小类编码
     */
    private String categoryCode;

    /**
     * 采购分类全名
     */
    private String categoryFullName;

    /**
     * 采购分类全ID
     */
    private String struct;

    /**
     * 单位
     */
    private String unit;

    /**
     * 单位名称
     */
    private String unitName;

    private Long version;

    /**
     * 创建人ID
     */
    private Long createdId;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 创建人IP
     */
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    private String lastUpdatedByIp;

    /**
     * 物料快照文件ID
     */
    private String materialPictureFileId;

    /**
     * 物料快照
     */
    private String materialPictureName;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 业务主体ID
     */
    private Long orgId;

    /**
     * 业务主体编码
     */
    private String orgCode;

    /**
     * 业务主体名称
     */
    private String orgName;

    /**
     * 库存组织ID
     */
    private Long organizationId;

    /**
     * 库存组织编码
     */
    private String organizationCode;

    /**
     * 库存组织名称
     */
    private String organizationName;

    /**
     * 品类集
     */
    private List<PurchaseCategory> purchaseCategories;

    /**
     * 供应商id
     */
    private Long vendorId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 物料编码或物料名称
     */
    private String materialKey;

    /**
     * 物料维护状态
     */
    private String materialStatus;

    /**
     * 供应商编码或供应商名称
     */
    private String supplierKey;

    /**
     * 是否目录化物料
     */
    private String ifCatalogMaterial;
	private String ceeaIfCatalogMaterial;
	private String ceeaIfUse; // 是否已上架=是否目录化

    /**
     * 合同编号
     */
    private String contractNo;
	private String ceeaContractNo; // 查询结果字段

    /**
     * 有效开始时间
     */
    private Date effectiveDate;

    /**
     * 有效结束时间
     */
    private Date expirationDate;

	private List<Long> ceeaOrgIds;

	private List<Long> ceeaOrganizationIds;

	/**
	 * 物料图片url
	 */
	private String ceeaMaterialPictureUrl;

	/**
	 * 业务实体编码
	 */
	private String ceeaOrgCode;

	/**
	 * 业务实体ID
	 */
	private Long ceeaOrgId;
	/**
	 * 业务实体名称
	 */
	private String ceeaOrgName;

	/**
	 * 库存组织ID
	 */
	private Long ceeaOrganizationId;

	/**
	 * 库存组织编码
	 */
	private String ceeaOrganizationCode;

	/**
	 * 库存组织名称
	 */
	private String ceeaOrganizationName;

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
	 * 单价（含税，预算单价）
	 */
	private BigDecimal taxPrice;

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
     * 采购类型
     */
    private String purchaseType;

    /**
     * 是否可以执行到货计划
     */
    private String ceeaIfDeliverPlan;

    /**
     * 物料id集合
     */
    private List<Long> materialIds;

    /**
     * 物料小类编码id
     */
    private List<Long> smallCategoryIds;


    /**
     * 物料名称（目录化加入购物车使用）
     */
    private String categoryMaterialName;

    /**
     * 物料编码（目录化加入购物车使用）
     */
    private String categoryMaterialCode;

    /**
     * 采购类型
     */
    private String ceeaPurchaseType;

    private String categoryId1;
    private String categoryId2;
    private String categoryId3;

    /**
     * 品类id
     */
    private List<Long> categoryIds;
    /**
     * 品类过滤
     */
    private String categoryFilter;

    /**
     * 物资类别
     */
    private String categoryType;

    //大中小类别名称
    private String bigCategoryName;
    private String middleCategoryName;
    private String smallCategoryName;
}
