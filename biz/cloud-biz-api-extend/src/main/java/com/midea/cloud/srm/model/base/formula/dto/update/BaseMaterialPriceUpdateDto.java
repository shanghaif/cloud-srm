package com.midea.cloud.srm.model.base.formula.dto.update;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

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
 *  修改日期: 2020/8/27 0:03
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class BaseMaterialPriceUpdateDto {
    @NotNull(message = "基价id不能为空")
    private Long baseMaterialPriceId;

    private String baseMaterialPriceType;

    /**
     * 有效期起始日期
     */
    private Date activeDateFrom;

    private String baseMaterialPriceStatus;

    /**
     * 有效期结束日期
     */
    private Date activeDateTo;


    /**
     * 币种
     */
    private String currencyType;

    /**
     * 价格
     */
    private BigDecimal baseMaterialPrice;

    /**
     * 数据来源
     */
    private String priceFrom;

    /**
     * 数据采集开始时间
     */
    private Date collectStartDate;

    /**
     * 数据采集结束时间
     */
    private Date collectEndDate;

    private String baseMaterialCode;
}
