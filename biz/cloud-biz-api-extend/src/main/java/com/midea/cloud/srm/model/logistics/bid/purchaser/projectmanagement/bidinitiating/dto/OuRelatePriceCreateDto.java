package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/4 17:17
 *  修改内容:
 * </pre>
 */
@Data
public class OuRelatePriceCreateDto {


    private BigDecimal priceDiff;
    /**
     * 业务实体名
     */
    private String orgName;
    /**
     * 业务实体ID
     */
    private Long orgId;

    private Long ouId;

    private String ouName;
    @NotNull(message = "需求行id不能为空")
    private Long requirementLineId;

}
