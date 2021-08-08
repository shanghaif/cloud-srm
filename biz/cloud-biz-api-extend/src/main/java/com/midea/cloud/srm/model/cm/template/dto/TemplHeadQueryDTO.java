package com.midea.cloud.srm.model.cm.template.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * <pre>
 *  功能名称描述:
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-7-7 16:33
 *  修改内容:
 * </pre>
 */
@Data
public class TemplHeadQueryDTO extends BaseDTO {

    /**
     * 主键ID
     */
    private Long templHeadId;

    /**
     * 模板类型
     */
    private String templType;

    /**
     * 模板名称
     */
    private String templName;

    /**
     * 模板描述
     */
    private String templDescription;

    /**
     * 模板状态
     */
    private String templStatus;

    /**
     * 甲方
     */
    private String owner;

    /**
     * 传真
     */
    private String fax;

    /**
     * 电话
     */
    private String phone;

    /**
     * 签约地点
     */
    private String signingSite;

    /**
     * 邮编
     */
    private String postcode;

    /**
     * 开户行
     */
    private String openingBank;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 法定代表人
     */
    private String legalPerson;

    /**
     * 委托代理人
     */
    private String entrustedAgent;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    private LocalDate startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    private LocalDate endDate;

    /**
     * 失效原因
     */
    private String invalidReason;

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
    private String creationDate;

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
}
