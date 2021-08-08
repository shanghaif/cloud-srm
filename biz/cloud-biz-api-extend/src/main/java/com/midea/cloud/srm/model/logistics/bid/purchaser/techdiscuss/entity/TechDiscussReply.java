package com.midea.cloud.srm.model.logistics.bid.purchaser.techdiscuss.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
*  <pre>
 *  技术交流供应商回复表 模型
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
@TableName("scc_lgt_tech_discuss_reply")
public class TechDiscussReply extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("REPLY_ID")
    private Long replyId;

    /**
     * 项目ID
     */
    @TableField("PROJ_ID")
    private Long projId;

    /**
     * 供应商编码
     */
    @TableField("REPLY_CODE")
    private String replyCode;

    /**
     * 供应商编码
     */
    @TableField("SUPPLIER_CODE")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @TableField("SUPPLIER_NAME")
    private String supplierName;

    /**
     * 联系人
     */
    @TableField("CONTACT_NAME")
    private String contactName;

    /**
     * 电话(移动电话)
     */
    @TableField("TELEPHONE")
    private String telephone;

    /**
     * 邮箱
     */
    @TableField("EMAIL")
    private String email;

    /**
     * 是否已回复  Y:已回复  N:未回复
     */
    @TableField("STATUS")
    private String status;

    /**
     * 是否有效  Y:有效  N:无效
     */
    @TableField("ENABLE_FLAG")
    private String enableFlag;

    /**
     * 技术建议文件ID
     */
    @TableField("SUGGESTION_FILE_ID")
    private Long suggestionFileId;

    /**
     * 参考价格
     */
    @TableField("REFERENCE_PRICE")
    private BigDecimal referencePrice;

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
    @TableField("TAX")
    private BigDecimal tax;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

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
