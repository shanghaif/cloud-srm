package com.midea.cloud.srm.model.supplierauth.review.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/**
*  <pre>
 *  组织与品类关系日志表 模型
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-14 11:08:28
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_auth_org_cate_jour")
public class OrgCateJournal extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("ORG_CATE_JOUR_ID")
    private Long orgCateJournalId;

    /**
     * 组织品类日志单据类型,参考字典码ORG_CATE_BILL_TYPE
     */
    @TableField("ORG_CATE_BILL_TYPE")
    private String orgCateBillType;

    /**
     * 组织品类日志单据ID
1、当ORG_CATE_BILL_TYPE值为REVIEW_FORM,ORG_CATE_BILL_ID为资质审查单据ID
2、当ORG_CATE_BILL_TYPE值为SITE_FORM,ORG_CATE_BILL_ID为现场评审单据ID
3、当ORG_CATE_BILL_TYPE值为ORG_CAT_FORM,ORG_FORM,ORG_CATE_BILL_ID为组织品类控制单据ID
4、当ORG_CATE_BILL_TYPE值为EFFECT_FORM,ORG_CATE_BILL_ID为供方生效单据ID
     */
    @TableField("ORG_CATE_BILL_ID")
    private Long orgCateBillId;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 合作组织名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 组织CODE
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 组织ID
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 父组织ID
     */
    @TableField("PARENT_ORG_ID")
    private Long parentOrgId;

    /**
     * 父组织名称
     */
    @TableField("PARENT_ORG_NAME")
    private String parentOrgName;

    /**
     * 父组织CODE
     */
    @TableField("PARENT_ORG_CODE")
    private String parentOrgCode;

    /**
     * 品类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 品类CODE
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 合作品类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 品类全路径名称
     */
    @TableField("CATEGORY_FULL_NAME")
    private String categoryFullName;

    /**
     * 组织服务状态
     */
    @TableField("ORG_SERVICE_STATUS")
    private String orgServiceStatus;

    /**
     * 品类服务状态
     */
    @TableField("CATEGORY_SERVICE_STATUS")
    private String categoryServiceStatus;

    /**
     * 分类级别(0根节点,1一级节点,2二级节点 3三级节点...n级节点)
     */
    @TableField("LEVEL")
    private Integer level;

    /**
     * 采购组织以及品类是否暂停恢复终止开关,Y 是,N 否
     */
    @TableField("ORG_OR_CATE_YES_OR_NO")
    private String orgOrCateYesOrNo;

    /**
     * 是否被引用,(Y是 N否),参考字典码YES_OR_NO
     */
    @TableField("QUOTED")
    private String quoted;

    /**
     * 生效时间
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效时间
     */
    @TableField("END_DATE")
    private LocalDate endDate;

    /**
     * 版本号
     */
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
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;


    /**
     * 物料ID
     */
    @TableField("MATERIAL_ID")
    private Long materialId;
    
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
     * 试用数量
     */
    @TableField("QUANTITY")
    private Long quantity;
    
    /**
     * 试用结果
     */
    @TableField("RESULT")
    private String result;
    
    /**
     * 测试结果说明
     */
    @TableField("RESULT_REMARK")
    private String resultRemark;
    
    /**
     * 组织路径ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;
    
}
