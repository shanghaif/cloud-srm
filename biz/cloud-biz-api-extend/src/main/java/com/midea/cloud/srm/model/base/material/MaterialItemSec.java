package com.midea.cloud.srm.model.base.material;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.base.material.entity.ItemSceImage;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  物料附表维护 模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-27 14:39:23
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_material_item_sec")
public class MaterialItemSec extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 物料附表ID
     */
    @TableId("MATERIAL_SECONDARY_ID")
    private Long materialSecondaryId;

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
     * 状态(Y启用,N不启用)
     */
    @TableField("STATUS")
    private String status;

    /**
     * 规格型号
     */
    @TableField("SPECIFICATION")
    private String specification;

    /**
     * 物料小类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 采购分类名称(物料小类名称)
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 分类结构,该值以父分类节点ID+下划线+分类节点ID值组合而成(比如该分类ID:2,其父分类节点ID为1,则该字段值为1_2)（不使用）
     */
    @TableField("STRUCT")
    private String struct;

    /**
     * 品类全称(不使用)
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
     * 库存分类编码（隆基拉物料数据新增，暂时冗余）(不使用)
     */
    @TableField("INVENTORY_CODE")
    private String inventoryCode;

    /**
     * 库存分类名称（隆基拉物料数据新增，暂时冗余）(不使用)
     */
    @TableField("INVENTORY_NAME")
    private String inventoryName;

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
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 物料图片(不使用)
     */
    @TableField("CEEA_MATERIAL_PICTURE_URL")
    private String ceeaMaterialPictureUrl;

    /**
     * 采购物料维护状态(不使用)
     */
    @TableField("CEEA_MATERIAL_STATUS")
    private String ceeaMaterialStatus;

    /**
     * 供应商id
     */
    @TableField("CEEA_SUPPLIER_ID")
    private Long ceeaSupplierId;

    /**
     * 供应商编码
     */
    @TableField("CEEA_SUPPLIER_CODE")
    private String ceeaSupplierCode;

    /**
     * 供应商名称
     */
    @TableField("CEEA_SUPPLIER_NAME")
    private String ceeaSupplierName;

    /**
     * 是否目录化物料(不使用)
     */
    @TableField("CEEA_IF_CATALOG_MATERIAL")
    private String ceeaIfCatalogMaterial;

    /**
     * 合同编号(不使用)
     */
    @TableField("CEEA_CONTRACT_NO")
    private String ceeaContractNo;

    /**
     * 上架人id(不使用)
     */
    @TableField("CEEA_USER_ID")
    private Long ceeaUserId;

    /**
     * 上架人工号(不使用)
     */
    @TableField("CEEA_EMP_NO")
    private String ceeaEmpNo;

    /**
     * 上架人名称(不使用)
     */
    @TableField("CEEA_NICKNAME")
    private String ceeaNickname;

    /**
     * 上架时间(不使用)
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
     * 使用用途
     */
    @TableField("CEEA_USAGE")
    private String ceeaUsage;

    /**
     * 材质
     */
    @TableField("CEEA_TEXTURE")
    private String ceeaTexture;

    /**
     * 富文本
     */
    @TableField("CEEA_RICH_TEXT")
    private String ceeaRichText;

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
     * erp物料Id(不使用)
     */
    @TableField("CEEA_ERP_MATERIAL_ID")
    private Long ceeaErpMaterialId;

    /**
     * 物料id
     */
    @TableField("MATERIAL_ID")
    private Long materialId;

    /**
     * 价格库id
     */
    @TableField("PRICE_LIBRARY_ID")
    private Long priceLibraryId;

    /**
     * 物料图片
     */
    @TableField(exist = false)
    private List<ItemSceImage> itemSceImages;

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
