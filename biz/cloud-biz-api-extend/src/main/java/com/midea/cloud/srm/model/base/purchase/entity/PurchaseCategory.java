//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.midea.cloud.srm.model.base.purchase.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_purchase_category")
public class PurchaseCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("CATEGORY_ID")
    private Long categoryId;

    /**
     * 分类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 分类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 分类别名
     */
    @TableField("ALIAS")
    private String alias;

    /**
     * 分类级别(0根节点,1一级节点,2二级节点 3三级节点...n级节点)
     */
    @TableField("LEVEL")
    private Integer level;

    /**
     * 父级分类ID(指向本表ID),根节点该值为null
     */
    @TableField("PARENT_ID")
    private Long parentId;

    /**
     * 排序(同分类级别值越大,排名越靠前)
     */
    @TableField("CORDER")
    private Integer corder;

    /**
     * 分类结构,该值以父分类节点ID+中划线+分类节点ID值组合而成(比如该分类ID:2,其父分类节点ID为1,则该字段值为1_2)
     */
    @TableField("STRUCT")
    private String struct;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @TableField("START_DATE")
    private Date startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @TableField("END_DATE")
    private Date endDate;

    /**
     * 是否启用(Y启用 N未启用)
     */
    @TableField("ENABLED")
    private String enabled;

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
     * 父品类名称
     */
    @TableField(exist = false)
    private String parentName;

    /**
     * 父品类编码
     */
    @TableField(exist = false)
    private String parentCode;

    /**
     * 全路径品类名称
     */
    @TableField(exist = false)
    private String categoryFullName;

    /**
     * 主材（数据字典）
     */
    @TableField("MAIN_MATERIAL")
    private String mainMaterial;

    /**
     * 是否需同步ERP(是-Y，否-N)
     */
    @TableField("CEEA_ENABLE_SYN_ERP")
    private String ceeaEnableSynErp;

    /**
     * 锁定周期(天数，整数)
     */
    @TableField(value = "CEEA_LOCK_PERIOD", updateStrategy = FieldStrategy.IGNORED)
    private Integer ceeaLockPeriod;

    /**
     * 是否用于执行到货计划（Y：是，N：否，默认N）
     */
    @TableField("CEEA_IF_DELIVER_PLAN")
    private String ceeaIfDeliverPlan;

    /**
     * 是否允许超计划发货（Y：是，N：否，默认N）
     */
    @TableField("CEEA_IF_BEYOND_DELIVER")
    private String ceeaIfBeyondDeliver;


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PurchaseCategory)) {
            return false;
        } else {
            PurchaseCategory other = (PurchaseCategory)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                label301: {
                    Object this$categoryId = this.getCategoryId();
                    Object other$categoryId = other.getCategoryId();
                    if (this$categoryId == null) {
                        if (other$categoryId == null) {
                            break label301;
                        }
                    } else if (this$categoryId.equals(other$categoryId)) {
                        break label301;
                    }

                    return false;
                }

                label294: {
                    Object this$categoryCode = this.getCategoryCode();
                    Object other$categoryCode = other.getCategoryCode();
                    if (this$categoryCode == null) {
                        if (other$categoryCode == null) {
                            break label294;
                        }
                    } else if (this$categoryCode.equals(other$categoryCode)) {
                        break label294;
                    }

                    return false;
                }

                Object this$categoryName = this.getCategoryName();
                Object other$categoryName = other.getCategoryName();
                if (this$categoryName == null) {
                    if (other$categoryName != null) {
                        return false;
                    }
                } else if (!this$categoryName.equals(other$categoryName)) {
                    return false;
                }

                label280: {
                    Object this$alias = this.getAlias();
                    Object other$alias = other.getAlias();
                    if (this$alias == null) {
                        if (other$alias == null) {
                            break label280;
                        }
                    } else if (this$alias.equals(other$alias)) {
                        break label280;
                    }

                    return false;
                }

                Object this$level = this.getLevel();
                Object other$level = other.getLevel();
                if (this$level == null) {
                    if (other$level != null) {
                        return false;
                    }
                } else if (!this$level.equals(other$level)) {
                    return false;
                }

                label266: {
                    Object this$parentId = this.getParentId();
                    Object other$parentId = other.getParentId();
                    if (this$parentId == null) {
                        if (other$parentId == null) {
                            break label266;
                        }
                    } else if (this$parentId.equals(other$parentId)) {
                        break label266;
                    }

                    return false;
                }

                Object this$corder = this.getCorder();
                Object other$corder = other.getCorder();
                if (this$corder == null) {
                    if (other$corder != null) {
                        return false;
                    }
                } else if (!this$corder.equals(other$corder)) {
                    return false;
                }

                Object this$struct = this.getStruct();
                Object other$struct = other.getStruct();
                if (this$struct == null) {
                    if (other$struct != null) {
                        return false;
                    }
                } else if (!this$struct.equals(other$struct)) {
                    return false;
                }

                Object this$startDate = this.getStartDate();
                Object other$startDate = other.getStartDate();
                if (this$startDate == null) {
                    if (other$startDate != null) {
                        return false;
                    }
                } else if (!this$startDate.equals(other$startDate)) {
                    return false;
                }

                label238: {
                    Object this$endDate = this.getEndDate();
                    Object other$endDate = other.getEndDate();
                    if (this$endDate == null) {
                        if (other$endDate == null) {
                            break label238;
                        }
                    } else if (this$endDate.equals(other$endDate)) {
                        break label238;
                    }

                    return false;
                }

                label231: {
                    Object this$enabled = this.getEnabled();
                    Object other$enabled = other.getEnabled();
                    if (this$enabled == null) {
                        if (other$enabled == null) {
                            break label231;
                        }
                    } else if (this$enabled.equals(other$enabled)) {
                        break label231;
                    }

                    return false;
                }

                Object this$createdId = this.getCreatedId();
                Object other$createdId = other.getCreatedId();
                if (this$createdId == null) {
                    if (other$createdId != null) {
                        return false;
                    }
                } else if (!this$createdId.equals(other$createdId)) {
                    return false;
                }

                label217: {
                    Object this$createdBy = this.getCreatedBy();
                    Object other$createdBy = other.getCreatedBy();
                    if (this$createdBy == null) {
                        if (other$createdBy == null) {
                            break label217;
                        }
                    } else if (this$createdBy.equals(other$createdBy)) {
                        break label217;
                    }

                    return false;
                }

                label210: {
                    Object this$creationDate = this.getCreationDate();
                    Object other$creationDate = other.getCreationDate();
                    if (this$creationDate == null) {
                        if (other$creationDate == null) {
                            break label210;
                        }
                    } else if (this$creationDate.equals(other$creationDate)) {
                        break label210;
                    }

                    return false;
                }

                Object this$createdByIp = this.getCreatedByIp();
                Object other$createdByIp = other.getCreatedByIp();
                if (this$createdByIp == null) {
                    if (other$createdByIp != null) {
                        return false;
                    }
                } else if (!this$createdByIp.equals(other$createdByIp)) {
                    return false;
                }

                Object this$lastUpdatedId = this.getLastUpdatedId();
                Object other$lastUpdatedId = other.getLastUpdatedId();
                if (this$lastUpdatedId == null) {
                    if (other$lastUpdatedId != null) {
                        return false;
                    }
                } else if (!this$lastUpdatedId.equals(other$lastUpdatedId)) {
                    return false;
                }

                label189: {
                    Object this$lastUpdatedBy = this.getLastUpdatedBy();
                    Object other$lastUpdatedBy = other.getLastUpdatedBy();
                    if (this$lastUpdatedBy == null) {
                        if (other$lastUpdatedBy == null) {
                            break label189;
                        }
                    } else if (this$lastUpdatedBy.equals(other$lastUpdatedBy)) {
                        break label189;
                    }

                    return false;
                }

                label182: {
                    Object this$lastUpdateDate = this.getLastUpdateDate();
                    Object other$lastUpdateDate = other.getLastUpdateDate();
                    if (this$lastUpdateDate == null) {
                        if (other$lastUpdateDate == null) {
                            break label182;
                        }
                    } else if (this$lastUpdateDate.equals(other$lastUpdateDate)) {
                        break label182;
                    }

                    return false;
                }

                Object this$lastUpdatedByIp = this.getLastUpdatedByIp();
                Object other$lastUpdatedByIp = other.getLastUpdatedByIp();
                if (this$lastUpdatedByIp == null) {
                    if (other$lastUpdatedByIp != null) {
                        return false;
                    }
                } else if (!this$lastUpdatedByIp.equals(other$lastUpdatedByIp)) {
                    return false;
                }

                label168: {
                    Object this$version = this.getVersion();
                    Object other$version = other.getVersion();
                    if (this$version == null) {
                        if (other$version == null) {
                            break label168;
                        }
                    } else if (this$version.equals(other$version)) {
                        break label168;
                    }

                    return false;
                }

                Object this$tenantId = this.getTenantId();
                Object other$tenantId = other.getTenantId();
                if (this$tenantId == null) {
                    if (other$tenantId != null) {
                        return false;
                    }
                } else if (!this$tenantId.equals(other$tenantId)) {
                    return false;
                }

                label154: {
                    Object this$parentName = this.getParentName();
                    Object other$parentName = other.getParentName();
                    if (this$parentName == null) {
                        if (other$parentName == null) {
                            break label154;
                        }
                    } else if (this$parentName.equals(other$parentName)) {
                        break label154;
                    }

                    return false;
                }

                Object this$parentCode = this.getParentCode();
                Object other$parentCode = other.getParentCode();
                if (this$parentCode == null) {
                    if (other$parentCode != null) {
                        return false;
                    }
                } else if (!this$parentCode.equals(other$parentCode)) {
                    return false;
                }

                Object this$categoryFullName = this.getCategoryFullName();
                Object other$categoryFullName = other.getCategoryFullName();
                if (this$categoryFullName == null) {
                    if (other$categoryFullName != null) {
                        return false;
                    }
                } else if (!this$categoryFullName.equals(other$categoryFullName)) {
                    return false;
                }

                return true;
            }
        }
    }

    @Override
    protected boolean canEqual(Object other) {
        return other instanceof PurchaseCategory;
    }

    @Override
    public int hashCode() {
        boolean PRIME = true;
        int result = super.hashCode();
        Object $categoryId = this.getCategoryId();
        result = result * 59 + ($categoryId == null ? 43 : $categoryId.hashCode());
        Object $categoryCode = this.getCategoryCode();
        result = result * 59 + ($categoryCode == null ? 43 : $categoryCode.hashCode());
        Object $categoryName = this.getCategoryName();
        result = result * 59 + ($categoryName == null ? 43 : $categoryName.hashCode());
        Object $alias = this.getAlias();
        result = result * 59 + ($alias == null ? 43 : $alias.hashCode());
        Object $level = this.getLevel();
        result = result * 59 + ($level == null ? 43 : $level.hashCode());
        Object $parentId = this.getParentId();
        result = result * 59 + ($parentId == null ? 43 : $parentId.hashCode());
        Object $corder = this.getCorder();
        result = result * 59 + ($corder == null ? 43 : $corder.hashCode());
        Object $struct = this.getStruct();
        result = result * 59 + ($struct == null ? 43 : $struct.hashCode());
        Object $startDate = this.getStartDate();
        result = result * 59 + ($startDate == null ? 43 : $startDate.hashCode());
        Object $endDate = this.getEndDate();
        result = result * 59 + ($endDate == null ? 43 : $endDate.hashCode());
        Object $enabled = this.getEnabled();
        result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
        Object $createdId = this.getCreatedId();
        result = result * 59 + ($createdId == null ? 43 : $createdId.hashCode());
        Object $createdBy = this.getCreatedBy();
        result = result * 59 + ($createdBy == null ? 43 : $createdBy.hashCode());
        Object $creationDate = this.getCreationDate();
        result = result * 59 + ($creationDate == null ? 43 : $creationDate.hashCode());
        Object $createdByIp = this.getCreatedByIp();
        result = result * 59 + ($createdByIp == null ? 43 : $createdByIp.hashCode());
        Object $lastUpdatedId = this.getLastUpdatedId();
        result = result * 59 + ($lastUpdatedId == null ? 43 : $lastUpdatedId.hashCode());
        Object $lastUpdatedBy = this.getLastUpdatedBy();
        result = result * 59 + ($lastUpdatedBy == null ? 43 : $lastUpdatedBy.hashCode());
        Object $lastUpdateDate = this.getLastUpdateDate();
        result = result * 59 + ($lastUpdateDate == null ? 43 : $lastUpdateDate.hashCode());
        Object $lastUpdatedByIp = this.getLastUpdatedByIp();
        result = result * 59 + ($lastUpdatedByIp == null ? 43 : $lastUpdatedByIp.hashCode());
        Object $version = this.getVersion();
        result = result * 59 + ($version == null ? 43 : $version.hashCode());
        Object $tenantId = this.getTenantId();
        result = result * 59 + ($tenantId == null ? 43 : $tenantId.hashCode());
        Object $parentName = this.getParentName();
        result = result * 59 + ($parentName == null ? 43 : $parentName.hashCode());
        Object $parentCode = this.getParentCode();
        result = result * 59 + ($parentCode == null ? 43 : $parentCode.hashCode());
        Object $categoryFullName = this.getCategoryFullName();
        result = result * 59 + ($categoryFullName == null ? 43 : $categoryFullName.hashCode());
        return result;
    }
}
