package com.midea.cloud.srm.model.base.material;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  物料维护 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-06 15:22:48
 *  修改内容:
 * </pre>
*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_material_item")
public class MaterialItem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**物料维护-库存组织集合*/
    @TableField(exist = false)
    private List<MaterialOrg> materialOrgList;

    /**
     * 物料ID
     */
    @TableId("MATERIAL_ID")
    private Long materialId;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 物料编码
     */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /**
     * 物料名称
     */
    @TableField("MATERIAL_NAME")
    private String materialName;

    /**
     * 状态(Y启用,N不启用,隆基目前废弃,启用状态取物料库存组织行上的状态)
     */
    @TableField("STATUS")
    private String status;

    /**
     * 规格型号(维护100个汉字)
     */
    @TableField("SPECIFICATION")
    private String specification;

    /**
     * 物料小类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 物料小类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 物料小类编码
     */
    @TableField(exist = false)
    private String categoryCode;

    /**
     * 分类结构,该值以父分类节点ID+中划线+分类节点ID值组合而成(比如该分类ID:2,其父分类节点ID为1,则该字段值为1_2)
     */
    @TableField("STRUCT")
    private String struct;

    /**
     * 采购分类全名
     */
    @TableField("CATEGORY_FULL_NAME")
    private String categoryFullName;

    /**
     * 单位
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 单位名称
     */
    @TableField("UNIT_NAME")
    private String unitName;

    /**
     * 库存分类编码（隆基拉物料数据新增，暂时冗余）
     */
    @TableField("INVENTORY_CODE")
    private String inventoryCode;

    /**
     * 库存分类名称（隆基拉物料数据新增，暂时冗余）
     */
    @TableField("INVENTORY_NAME")
    private String inventoryName;

    @TableField("VERSION")
    private Long version;

    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedByIp;

    /**
     * 物料快照文件ID
     */
    @TableField("MATERIAL_PICTURE_FILE_ID")
    private String materialPictureFileId;

    /**
     * 物料快照
     */
    @TableField("MATERIAL_PICTURE_NAME")
    private String materialPictureName;

    /**
     * 物料图片
     */
    @TableField("CEEA_MATERIAL_PICTURE_URL")
    private String ceeaMaterialPictureUrl;

    /**
     * 一站式采购-采购物料维护状态
     */
    @TableField("CEEA_MATERIAL_STATUS")
    private String ceeaMaterialStatus;

    /**
     * 一站式采购-供应商id
     */
    @TableField("CEEA_SUPPLIER_ID")
    private Long ceeaSupplierId;

    /**
     * 一站式采购-供应商编码
     */
    @TableField("CEEA_SUPPLIER_CODE")
    private String ceeaSupplierCode;

    /**
     * 一站式采购-供应商名称
     */
    @TableField("CEEA_SUPPLIER_NAME")
    private String ceeaSupplierName;

    /**
     * 是否目录化物料
     */
    @TableField("CEEA_IF_CATALOG_MATERIAL")
    private String ceeaIfCatalogMaterial;

    /**
     * 合同编号
     */
    @TableField("CEEA_CONTRACT_NO")
    private String ceeaContractNo;

    /**
     * 上架人id
     */
    @TableField("CEEA_USER_ID")
    private Long ceeaUserId;

    /**
     * 上架人工号
     */
    @TableField("CEEA_EMP_NO")
    private String ceeaEmpNo;

    /**
     * 上架人昵称
     */
    @TableField("CEEA_NICKNAME")
    private String ceeaNickname;

    /**
     * 上架时间
     */
    @TableField("CEEA_ON_SHELF_TIME")
    private Date ceeaOnShelfTime;

    /**
     * 重量
     */
    @TableField("CEEA_WEIGHT")
    private String ceeaWeight;

    /**
     * 尺寸
     */
    @TableField("CEEA_SIZE")
    private String ceeaSize;

    /**
     * 最小起订量
     */
    @TableField("CEEA_ORDER_QUANTITY_MINIMUM")
    private BigDecimal ceeaOrderQuantityMinimum;

    /**
     * 送货周期
     */
    @TableField("CEEA_DELIVERY_CYCLE")
    private String ceeaDeliveryCycle;

    /**
     * 品牌
     */
    @TableField("CEEA_BRAND")
    private String ceeaBrand;

    /**
     * 颜色
     */
    @TableField("CEEA_COLOR")
    private String ceeaColor;

    /**
     * 联系人姓名
     */
    @TableField("CEEA_NAMES")
    private String ceeaNames;

    /**
     * 联系人电话
     */
    @TableField("CEEA_TELEPHONES")
    private String ceeaTelephones;

    /**
     * 联系人邮箱
     */
    @TableField("CEEA_EMAILS")
    private String ceeaEmails;

    /**
     * 第二联系人姓名
     */
    @TableField("CEEA_SECOND_NAMES")
    private String ceeaSecondNames;

    /**
     * 第二联系人电话
     */
    @TableField("CEEA_SECOND_TELEPHONES")
    private String ceeaSecondTelephones;

    /**
     * 第二联系人邮箱
     */
    @TableField("CEEA_SECOND_EMAILS")
    private String ceeaSecondEmails;

    /**
     * 材质
     */
    @TableField("CEEA_TEXTURE")
    private String ceeaTexture;

    /**
     * 使用用途
     */
    @TableField("CEEA_USAGE")
    private String ceeaUsage;

    /**
     * 富文本
     */
    @TableField("CEEA_RICH_TEXT")
    private String ceeaRichText;

    /**
     * erp物料Id
     */
    @TableField("CEEA_ERP_MATERIAL_ID")
    private Long ceeaErpMaterialId;

    /**
     * 公式值
     */
    @TableField(exist = false)
    private String pricingFormulaValue;
    /**
     * 公式id
     */
    @TableField(exist = false)
    private Long pricingFormulaHeaderId;

    /**
     * 业务实体名称
     */
    @TableField(exist = false)
    private String orgNames;

    /**
     * 物料大类名称
     */
    @TableField(exist = false)
    private String bigCategoryName;

    /**
     * 物料中类名称
     */
    @TableField(exist = false)
    private String middleCategoryName;

    /**
     * 组织序号
     */
    @TableField(exist = false)
    private Integer organizatonIndex;

    /**
     * 组织名称
     */
    @TableField(exist = false)
    private String organizationName;

    /**
     * 是否用于采购
     */
    @TableField(exist = false)
    private String userPurchase;

    /**
     * 是否用于库存
     */
    @TableField(exist = false)
    private String stockEnableFlag;

    /**
     * 库存组织是否启用
     */
    @TableField(exist = false)
    private String itemStatus;

    /**
     * 物料大类id
     */
    @TableField(exist = false)
    private Long bigCategoryId;

    /**
     * 物料中类id
     */
    @TableField(exist = false)
    private Long middleCategoryId;

    /**
     * 开始时间
     */
    @TableField(exist = false)
    private Date startDate;

    /**
     * 结束时间
     */
    @TableField(exist = false)
    private Date endDate;

    /**
     * 库存组织id
     */
    @TableField(exist = false)
    private Long organizationId;

    /**
     * 配额管理类型
     * @enum com.midea.cloud.common.enums.base.QuotaManagementType
     */
    @NotBlank(message = "配额管理类型不能为空")
    @TableField("QUOTA_MANAGEMENT_TYPE")
    private String quotaManagementType;

    /**
     * 最少拆单量
     */
    @TableField("MINI_SPLIT")
    private BigDecimal miniSplit;

    /**
     * 最大分配量
     */
    @TableField("MAX_ALLOCATION")
    private BigDecimal maxAllocation;

}
