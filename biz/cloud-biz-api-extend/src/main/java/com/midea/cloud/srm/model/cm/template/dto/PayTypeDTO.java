package com.midea.cloud.srm.model.cm.template.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * <pre>
 *  合同付款类型DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-5-13 19:19
 *  修改内容:
 * </pre>
 */
@Data
public class PayTypeDTO extends BaseDTO{

    /**
     * 主键ID
     */
    private Long payTypeId;

    /**
     * 付款类型
     */
    private String payType;

    /**
     * 付款说明
     */
    private String payExplain;

    /**
     * 付款天数
     */
    private Integer payDelaytime;

    /**
     * 支付日期是否必填
     */
    private String payDateRequired;

    /**
     * 是否验收后触发
     */
    private String isAfterCheck;

    /**
     * 是否开票后触发
     */
    private String isAfterInvoicing;

    /**
     * 逻辑说明
     */
    private String logicalExplain;

    /**
     * 状态,生效:EFFECTIVE,失效:INVALID
     */
    private String payTypeStatus;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    private LocalDate startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    private LocalDate endDate;

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
}
