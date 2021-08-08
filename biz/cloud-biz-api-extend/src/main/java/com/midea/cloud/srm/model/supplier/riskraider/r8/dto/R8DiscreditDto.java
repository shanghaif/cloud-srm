package com.midea.cloud.srm.model.supplier.riskraider.r8.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.R8DiscreditCustom;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.R8DiscreditGuarantee;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.R8DiscreditRelation;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Author vent
 * @Description
 **/
@Data
@Accessors(chain = true)
public class R8DiscreditDto extends BaseDTO {
    /**
     * 企业失信ID
     */
    private Long discreditId;

    /**
     * 失信标志（0：非失信，1：失信）
     */
    private String discreditFlag;

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
     * 更新人
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
    private String tenantId;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 供应商编号
     */
    private String vendorCode;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 主体企业失信
     */
    private R8DiscreditMainDto mainDiscredit;

    /**
     * 担保企业失信
     */
    private R8DiscreditGuaranteeDto guaranteeDiscredit;

    /**
     * 关联企业失信信息
     */
    private R8DiscreditRelationDto relationDiscredit;

    /**
     * 自定义失信信息
     */
    private List<R8DiscreditCustom> customDiscredit;
}
