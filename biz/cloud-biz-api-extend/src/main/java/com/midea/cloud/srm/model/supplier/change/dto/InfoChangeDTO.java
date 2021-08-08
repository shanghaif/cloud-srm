package com.midea.cloud.srm.model.supplier.change.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author zhuwl7@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-31 13:51
 *  修改内容:
 * </pre>
 */
@Data
public class InfoChangeDTO extends BaseDTO {

    /**
     * 变更单ID
     */
    private Long changeId;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司CODE
     */
    private String companyCode;

    /**
     * 企业性质
     */
    private String companyType;


    /**
     * 境外关系
     */
    private String overseasRelation;

    /**
     * 统一社会信用代码
     */
    private String lcCode;

    /**
     * 法定代表人
     */
    private String legalPerson;


    /**
     * 变更单据申请号
     */
    private String changeApplyNo;

    /**
     * 变更单类型,公司基础信息变更:COMPANY_INFO_CHANGE,公司银行财务信息变更:FINANCE_BANK_INFO_CHANGE
     */
    private String changeType;

    /**
     * 变更单据说明
     */
    private String changeExplain;

    /**
     * 变更状态
     */
    private String changeStatus;

    /**
     * 变更审批日期
     */
    private Date changeApprovedDate;

    /**
     * 变更审批人
     */
    private String changeApprovedBy;

    /**
     * 变更申请日期
     */
    private Date changeApplyDate;

    /**
     * 变更相关附件ID
     */
    private Long changeFileId;

    /**
     * 变更相关附件名称
     */
    private String changeFileName;


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
    private String lastUpdatedIp;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 租户ID
     */
    private Long tenantId;
}
