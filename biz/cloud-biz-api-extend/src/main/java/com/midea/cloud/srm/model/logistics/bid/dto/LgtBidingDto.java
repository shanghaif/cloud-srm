package com.midea.cloud.srm.model.logistics.bid.dto;

import com.midea.cloud.srm.model.logistics.bid.entity.LgtBiding;
import lombok.Data;

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
public class LgtBidingDto extends LgtBiding {
    /**
     * 供应商ID
     */
    private Long vendorId;
    /**
     * 撤回原因
     */
    private String withdrawReason;
    /**
     * 投标状态
     */
    private String status;
}
