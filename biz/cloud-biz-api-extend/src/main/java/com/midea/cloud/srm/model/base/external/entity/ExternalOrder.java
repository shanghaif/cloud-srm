package com.midea.cloud.srm.model.base.external.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.time.LocalDate;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  外部订单表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-23 16:40:11
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_external_order")
public class ExternalOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表ID，主键，供其他表做外键
     */
    @TableId("SYS_EXTENERALl_ORDER_ID")
    private Long sysExtenerallOrderId;

    /**
     * *租户id
     */
    @TableField("RENT_ID")
    private String rentId;

    /**
     * *企业名称
     */
    @TableField("COMPANY_NAME")
    private String companyName;

    /**
     * *社会统一信用代码
     */
    @TableField("CREDIT_CODE")
    private String creditCode;

    /**
     * *企业标识
     */
    @TableField("COMPANY_FLAG")
    private String companyFlag;

    /**
     * *注册资本
     */
    @TableField("REGIST_CAPITAL")
    private String registCapital;

    /**
     * *法人
     */
    @TableField("LEGAL_PERSON")
    private String legalPerson;

    /**
     * *营业期限
     */
    @TableField("BUSINESS_DATE")
    private String businessDate;

    /**
     * *成立日期
     */
    @TableField("REGIST_DATE")
    private Date registDate;

    /**
     * *企业性质
     */
    @TableField("COMPANY_NATURE")
    private String companyNature;

    /**
     * 管理员账号
     */
    @TableField("USER_ACCOUNT")
    private String userAccount;

    /**
     * *租户姓名
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * *邮箱
     */
    @TableField("MAIL_ADDRESS")
    private String mailAddress;

    /**
     * *手机
     */
    @TableField("MOBILE_NUMBER")
    private String mobileNumber;

    /**
     * *注册住所
     */
    @TableField("POSTAL_ADDRESS")
    private String postalAddress;

    /**
     * *注册住所
     */
    @TableField("URL_ADDRESS")
    private String urlAddress;

    /**
     * *订单编号
     */
    @TableField("ORDER_NUMBER")
    private String orderNumber;

    /**
     * *订单套餐
     */
    @TableField("ORDER_DETAILS")
    private String orderDetails;

    /**
     * *购买有效期从
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * *购买有效期至
     */
    @TableField("END_DATE")
    private LocalDate endDate;

    /**
     * 限制用户数
     */
    @TableField("LIMIT_NUM")
    private Long limitNum;

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


}
