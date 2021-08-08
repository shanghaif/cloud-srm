package com.midea.cloud.srm.model.base.questionairesurvey.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyScopeVendor;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * <pre>
 *  问卷调查
 * </pre>
 *
 * @author liuzh163@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/4/17 0:18
 *  修改内容:
 * </pre>
 */
@Data
public class SurveyScopeVendorSupplierDto extends BaseDTO {
    /**
     * 问卷ID
     */
    private Long surveyId;


    /**
     * 问卷标题
     */
    private String surveyTitle;

    /**
     * 问卷说明
     */
    private String surveyDesc;


    /**
     * 供应商范围ID
     */
    private Long vendorScopeId;

    /**
     * 员工范围ID
     */
    private Long scopeId;


    /**
     * 供应商编码
     */
    private String vendorCode;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 供应商类型
     */
    private String vendorType;

    /**
     * 结果标识；Y：已反馈；N：未反馈
     */
    private String resultFlag;

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
     * 租户ID
     */
    private Long tenantId;
    /**
     * 版本号
     */
    private Long version;
    /**
     * 附件ID
     */
    private String fileRelationId;
    /**
     * 附件名称
     */
    private String fileName;
    /**
     * 供应商上传文件ID
     */
    private Long docId;
    /**
     *供应商上传文件名称
     */
    private String docName;
}
