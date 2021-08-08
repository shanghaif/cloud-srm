package com.midea.cloud.srm.model.base.questionairesurvey.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author liuzh163@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/4/17 17:44
 *  修改内容:
 * </pre>
 */
@Data
public class HeaderScopeVendorDto extends BaseDTO {
    /**
     * 问卷ID
     */
    private Long surveyId;
    /**
     * 问卷版本号
     */
    private String surveyNum;
    /**
     * 事业部编码
     */
    private String buCode;
    /**
     * 事业部ID
     */
    private Long buId;
    /**
     * 问卷标题
     */
    private String surveyTitle;
    /**
     * 问卷说明
     */
    private String surveyDesc;
    /**
     * 问卷状态
     */
    private String statusCode;
    /**
     * 供应商发布范围
     */
    private String vendorScopeFlag;
    /**
     * 反馈范围
     */
    private String feedbackFlag;

    /**
     * 发布时间
     */
    private Date publishDate;
    /**
     * 反馈截止时间
     */
    private Date endDate;
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
     * 版本号
     */
    private Long version;

    /**
     * 供应商范围ID
     */
    private Long vendorScopeId;


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
}
