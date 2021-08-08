package com.midea.cloud.srm.model.base.quotaorder.dto;

import com.midea.cloud.srm.model.base.quotaorder.QuotaHead;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

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
public class QuotaHeadDto extends QuotaHead {

    /**
     * 供应商ID
     */
    private Long companyId;

    /**
     * 供应商名称
     */
    private String companyName;

    /**
     * 已分配量
     */
    private BigDecimal allocatedAmount;

    /**
     * 配额比(%)
     */
    private BigDecimal quota;

    /**
     * 配额基数
     */
    private BigDecimal baseNum;

    /**
     * 创建时间开始
     */
    private Date creationDateStart;

    /**
     * 创建时间结束
     */
    private Date creationDateEnd;
}
