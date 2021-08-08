package com.midea.cloud.srm.model.cm.contract.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <pre>
 *   合同付款计划DTO
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-6-16 10:18
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class PayPlanRequestDTO extends BaseDTO{

    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 供应商编码
     */
    private String vendorCode;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 业务实体ID
     */
    private Long orgId;

    /**
     * 业务实体编码
     */
    private String orgCode;

}
