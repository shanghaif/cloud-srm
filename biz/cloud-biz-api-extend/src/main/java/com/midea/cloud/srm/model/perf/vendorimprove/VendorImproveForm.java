package com.midea.cloud.srm.model.perf.vendorimprove;

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
 *  供应商改善单表 模型
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_perf_vendor_improve_form")
public class VendorImproveForm extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 供应商改善单ID
     */
    @TableId("VENDOR_IMPROVE_ID")
    private Long vendorImproveId;

    /**
     * 改善单号
     */
    @TableField("IMPROVE_NO")
    private String improveNo;

    /**
     * 改善开始日期
     */
    @TableField("IMPROVE_DATE_START")
    private LocalDate improveDateStart;

    /**
     * 改善结束日期
     */
    @TableField("IMPROVE_DATE_END")
    private LocalDate improveDateEnd;

    /**
     * 改善主题
     */
    @TableField("IMPROVE_TITLE")
    private String improveTitle;

    /**
     * 改善主项目
     */
    @TableField("IMPROVE_PROJECT")
    private String improveProject;

    /**
     * 状态(DRAFT-拟定,IMPROVING-改善中,UNDER_EVALUATION-评价中,EVALUATED-已评价)
     */
    @TableField("STATUS")
    private String status;

    /**
     * 责任跟进人账号
     */
    @TableField("RESP_USER_NAME")
    private String respUserName;

    /**
     * 责任跟进人名字
     */
    @TableField("RESP_FULL_NAME")
    private String respFullName;

    /**
     * 采购组织ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 采购组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 采购组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 供应商编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 采购分类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 采购分类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 采购分类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 改善附件文件id
     */
    @TableField("FILE_UPLOAD_ID")
    private String fileUploadId;

    /**
     * 改善附件文件名
     */
    @TableField("FILE_SOURCE_NAME")
    private String fileSourceName;

    /**
     * 改善说明
     */
    @TableField("EXPLANATION")
    private String explanation;

    /**
     * 供应商反馈时间
     */
    @TableField("V_FEEDBACK_TIME")
    private Date vFeedbackTime;

    /**
     * 供应商反馈附件文件id
     */
    @TableField("V_FEEDBACK_FILE_UPLOAD_ID")
    private String vFeedbackFileUploadId;

    /**
     * 供应商反馈附件文件名
     */
    @TableField("V_FEEDBACK_FILE_SOURCE_NAME")
    private String vFeedbackFileSourceName;

    /**
     * 供应商反馈说明
     */
    @TableField("V_FEEDBACK_EXPLANATION")
    private String vFeedbackExplanation;

    /**
     * 供应商供应商是否已反馈,Y:是,N:否
     */
    @TableField("V_IS_FEEDBACK")
    private String vIsFeedback;

    /**
     * 处理供应商反馈时间
     */
    @TableField("M_FEEDBACK_TIME")
    private Date mFeedbackTime;

    /**
     * 处理供应商反馈附件文件id
     */
    @TableField("M_FEEDBACK_FILE_UPLOAD_ID")
    private String mFeedbackFileUploadId;

    /**
     * 处理供应商反馈附件文件名
     */
    @TableField("M_FEEDBACK_FILE_SOURCE_NAME")
    private String mFeedbackFileSourceName;

    /**
     * 处理供应商反馈说明
     */
    @TableField("M_FEEDBACK_EXPLANATION")
    private String mFeedbackExplanation;

    /**
     * 处理供应商供应商是否已反馈,Y:是,N:否
     */
    @TableField("M_IS_FEEDBACK")
    private String mIsFeedback;

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
     * 更新人
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
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

}
