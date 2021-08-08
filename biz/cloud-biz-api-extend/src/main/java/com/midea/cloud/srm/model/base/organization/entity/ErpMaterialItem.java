package com.midea.cloud.srm.model.base.organization.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  物料维护表（隆基物料同步） 模型
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-20 15:19:46
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_material_item")
public class ErpMaterialItem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    List<Category> categoryList;

    /**
     * 主键ID
     */
    @TableId("ID")
    private Long id;

    /**
     * 组织
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 物料ID
     */
    @TableField("ITEM_ID")
    private Long itemId;

    /**
     * 组织ID
     */
    @TableField("ORGAN_ID")
    private String organId;

    /**
     * 物料编码
     */
    @TableField("ITEM_NUMBER")
    private String itemNumber;

    /**
     * 物料说明（ZHS）
     */
    @TableField("ITEM_DESC_ZHS")
    private String itemDescZhs;

    /**
     * 物料说明（US）
     */
    @TableField("ITEM_DESC_US")
    private String itemDescUs;

    /**
     * 物料长描述-ZHS
     */
    @TableField("ITEM_LONG_DESC_ZHS")
    private String itemLongDescZhs;

    /**
     * 物料长描述-US
     */
    @TableField("ITEM_LONG_DESC_US")
    private String itemLongDescUs;

    /**
     * 物料主单位
     */
    @TableField("PRIMARY_UNIT_OF_MEASURE")
    private String primaryUnitOfMeasure;

    /**
     * 物料辅助单位
     */
    @TableField("UNIT_OF_MEASURE")
    private String unitOfMeasure;

    /**
     * 批次控制（1、不控制；2存储期限；4、自定义；）
     */
    @TableField("LOT_CONTROL")
    private String lotControl;

    /**
     * 存储期限
     */
    @TableField("SHELF_LIFE_DAYS")
    private Long shelfLifeDays;

    /**
     * 物料状态（无效、有效）
     */
    @TableField("ITEM_STATUS")
    private String itemStatus;

    /**
     * 可存储（Y/N）
     */
    @TableField("STOCK_ENABLE_FLAG")
    private String stockEnableFlag;

    /**
     * 可采购（Y/N）
     */
    @TableField("PURCHASING_ENABLE_FLAG")
    private String purchasingEnableFlag;

    /**
     * 使用批准供应商（Y/N）
     */
    @TableField("MUST_APPROVE_VENDOR")
    private String mustApproveVendor;

    /**
     * 默认采购员编码
     */
    @TableField("DEFAULT_BUYER_NUM")
    private String defaultBuyerNum;

    /**
     * 默认采购员姓名
     */
    @TableField("DEFAULT_BUYER")
    private String defaultBuyer;

    /**
     * 允许BOM（Y/N）
     */
    @TableField("BOM_ENABLED_FLAG")
    private String bomEnabledFlag;

    /**
     * 在WIP中制造（Y/N）
     */
    @TableField("BUILD_IN_WIP_FLAG")
    private String buildInWipFlag;

    /**
     * 启用客户订单（Y/N）
     */
    @TableField("CUSTOMER_ORDER_FLAG")
    private String customerOrderFlag;

    /**
     * 启用内部订单（Y/N）
     */
    @TableField("INTERNAL_ORDER_FLAG")
    private String internalOrderFlag;

    /**
     * 启用开票（Y/N）
     */
    @TableField("INVOICED_ENABLE_FLAG")
    private String invoicedEnableFlag;

    /**
     * 可处理（Y/N）
     */
    @TableField("TRANSACTION_ENABLE_FLAG")
    private String transactionEnableFlag;

    /**
     * 接收方式（1、标准；2、检验；3、直接）
     */
    @TableField("RECEIVE_ROUTING_ID")
    private String receiveRoutingId;

    /**
     * 物料扩展属性
     */
    @TableField("EXTEND_ATTRIBUTES")
    private String extendAttributes;

    /**
     * 接口状态(NEW-新增，UPDATE-更新)
     */
    @TableField("ITF_STATUS")
    private String itfStatus;

    /**
     * 备用字段1
     */
    @TableField("ATTR1")
    private String attr1;

    /**
     * 备用字段2
     */
    @TableField("ATTR2")
    private String attr2;

    /**
     * 备用字段3
     */
    @TableField("ATTR3")
    private String attr3;

    /**
     * 备用字段4
     */
    @TableField("ATTR4")
    private String attr4;

    /**
     * 备用字段5
     */
    @TableField("ATTR5")
    private String attr5;

    /**
     * 类别集[]
     */
    @TableField("CATEGORY_SETS")
    private String categorySets;

    /**
     * 类别集代码
     */
    @TableField("CATEGORY_SET_CODE")
    private String categorySetCode;

    /**
     * 类别集名称（ZHS）
     */
    @TableField("CATEGORY_SET_NAME_ZHS")
    private String categorySetNameZhs;

    /**
     * 类别集名称（US）
     */
    @TableField("CATEGORY_SET_NAME_US")
    private String categorySetNameUs;

    /**
     * 类别值
     */
    @TableField("SET_VALUE")
    private String setValue;

    /**
     * 类别说明-ZHS
     */
    @TableField("SET_VALUE_DESC_ZHS")
    private String setValueDescZhs;

    /**
     * 类别说明（US）
     */
    @TableField("SET_VALUE_DESC_US")
    private String setValueDescUs;

    /**
     * 创建日期
     */
    @TableField("CREATION_DATE")
    private Date creationDate;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

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
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 导入状态（0未导入；1已导入）
     */
    @TableField("IMPORT_STATUS")
    private Integer importStatus;

    /**
     * 错误信息
     */
    @TableField("ERROR_MSG")
    private String errorMsg;

}
