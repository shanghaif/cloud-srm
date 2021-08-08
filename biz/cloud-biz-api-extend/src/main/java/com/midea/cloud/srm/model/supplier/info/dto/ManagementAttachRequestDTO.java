package com.midea.cloud.srm.model.supplier.info.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/12 11:38
 *  修改内容:
 * </pre>
 */
@Data
public class ManagementAttachRequestDTO extends BaseDTO {

    /**
     * 供应商编码（公司编码）
     */
    private String companyCode;

    /**
     * 供应商名称（公司名称）
     */
    private String companyName;

    /**
     * 到期时间
     */
    private String dueDate;

    /**
     * 到期时间（未来）
     */
    private String futureDate;

    /**
     * 到期时间（过去）
     */
    private String pastDate;

    /**
     * 证件有效期开始日期
     */
    private Date authDate;

    /**
     * 证件有效期终止日期
     */
    private Date endDate;

    /**
     * 是否启用提醒
     */
    private String isUseReminder;
}
