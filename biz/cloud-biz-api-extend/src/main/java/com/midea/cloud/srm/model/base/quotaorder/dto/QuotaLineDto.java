package com.midea.cloud.srm.model.base.quotaorder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuotaLineDto implements Serializable {
    /**
     * 配额行Id
     */
    private Long quotaLineId;

    /**
     * 供应商ID
     */
    private Long companyId;

    /**
     * 分配量
     */
    private BigDecimal amount;
}
