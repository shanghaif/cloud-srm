package com.midea.cloud.srm.model.bid.purchaser.techdiscuss.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  技术交流项目信息表 模型
 * </pre>
*
* @author fengdc3@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 11:09:20
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_bid_tech_discuss_proj_info")
public class TechDiscussProjInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("PROJ_ID")
    private Long projId;

    /**
     * 项目名称
     */
    @TableField("PROJ_NAME")
    private String projName;

    /**
     * 项目编号
     */
    @TableField("PROJ_CODE")
    private String projCode;

    /**
     * 项目类型,对应字典:TECHNOLOGY_PROJECT_TYPE
     * ALL :全部,EQUIPMENT_ENGINEERING:设备/工程,INVENTION:研发类,
     * SERVICE:服务类
     */
    @TableField("PROJ_TYPE")
    private String projType;

    /**
     * 项目状态,对应字典:TECHNOLOGY_PROJECT_STATE
     * ALL:全部,DRAW_UP:拟定,PUBLICITY:已发布
     */
    @TableField("STATUS")
    private String status;

    /**
     * 发布人
     */
    @TableField("PUBLISH_BY")
    private String publishBy;

    /**
     * 发布时间
     */
    @TableField("PUBLISH_TIME")
    private Date publishTime;

    /**
     * 发布范围,对应字典:TECHNOLOGY_RELEASE_SCOPE
     * ALL:全部,OPEN:公开发布,INVITE:邀请发布
     */
    @TableField("PUBLISH_RANGE")
    private String publishRange;

    /**
     * 截止时间
     */
    @TableField("STOP_TIME")
    private Date stopTime;

    /**
     * 已回复数量
     */
    @TableField("REPLY_COUNT")
    private Integer replyCount;

    /**
     * 币种
     */
    @TableField("CURRENCY")
    private String currency;

    /**
     * 税率编码
     */
    @TableField("TAX_KEY")
    private String taxKey;

    /**
     * 税率
     */
    @TableField("TAX_RATE")
    private String taxRate;

    /**
     * 需求简述
     */
    @TableField("RESUME")
    private String resume;

    /**
     * 附件ID
     */
    @TableField("FILEUPLOAD_ID")
    private Long fileuploadId;

    /**
     * 附件名称
     */
    @TableField("FILE_NAME")
    private String fileName;

    /**
     * 附件备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 是否需提供参考价格  Y:是  N:否
     */
    @TableField("SHOW_PRICE_ENABLED")
    private String showPriceEnabled;

    /**
     * 是否有效  Y:有效  N:无效
     */
    @TableField("ENABLE_FLAG")
    private String enableFlag;

    /**
     * 企业编码
     */
    @TableField("COMPANY_CODE")
    private String companyCode;

    /**
     * 组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 拥有者/租户/所属组等
     */
    @TableField("TENANT_ID")
    private Long tenantId;

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
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
