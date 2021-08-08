package com.midea.cloud.srm.model.perf.scoreproject.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.util.List;

import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.junit.Test;

/**
 *  <pre>
 *  绩效评分项目评分人表 模型
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-06 15:10:36
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_perf_score_items_man")
public class PerfScoreItemsMan extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**绩效评分项目评分人-供应商指标集合*/
    @TableField(exist = false)
    private List<PerfScoreItemManSupInd> perfScoreItemManSupIndList;

    /**绩效评分项目评分人-供应商集合*/
//    @TableField(exist = false)
//    private List<PerfScoreItemsManSup> perfScoreItemsManSupList;
    /**绩效评分项目评分人-指标集合*/
//    @TableField(exist = false)
//    private List<PerfScoreItemsManIndicator> perfScoreItemsManIndicatorList;
    /**绩效评分项目供应商表ID(用于查询)*/
    @TableField(exist = false)
    private Long scoreItemsSupId;
    /**绩效评分项目评分人-绩效维度ID(用于查询)*/
    @TableField(exist = false)
    private Long dimWightId;

    /**
     * 主键ID
     */
    @TableId("SCORE_ITEMS_MAN_ID")
    private Long scoreItemsManId;

    /**
     * 绩效评分项目ID
     */
    @TableField("SCORE_ITEMS_ID")
    private Long scoreItemsId;

    /**
     * 评分人账号ID
     */
    @TableField("SCORE_USER_ID")
    private Long scoreUserId;

    /**
     * 评分人账号
     */
    @TableField("SCORE_USER_NAME")
    private String scoreUserName;

    /**
     * 评分人名称
     */
    @TableField("SCORE_NICK_NAME")
    private String scoreNickName;

    /**
     * 评分人邮件
     */
    @TableField("SCORE_USER_EMAIL")
    private String scoreUserEmail;

    /**
     * 评分人手机
     */
    @TableField("SCORE_USER_PHONE")
    private String scoreUserPhone;

    /**
     * 选择评分人备注
     */
    @TableField("COMMENTS")
    private String comments;

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
     * 创建人姓名
     */
    @TableField(value = "CREATED_FULL_NAME", fill = FieldFill.INSERT)
    private String createdFullName;

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
