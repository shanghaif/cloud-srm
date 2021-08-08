package com.midea.cloud.srm.model.base.formula.dto.create;


import lombok.Data;
import lombok.experimental.Accessors;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
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
 * 包括新增、修改、删除
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/26 23:55
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class BaseMaterialPriceCreateDto {

    /**
     * 基价类型
     * DAY_PRICE("DAY_PRICE", "当日价格"),
     * WEEK_PRICE("WEEK_PRICE", "周均价"),
     * MONTH_PRICE("MONTH_PRICE", "月均价");
     */
    @NotEmpty(message = "价格类型不能为空")
    private String baseMaterialPriceType;

    /**
     * 有效期起始日期
     */
    @NotNull(message = "有效期起始日期不能为空")
    private Date activeDateFrom;

    /**
     * 有效期结束日期
     */
    @NotNull(message = "有效期结束日期不能为空")
    private Date activeDateTo;

    /**
     * 基材编码
     */
    @NotEmpty(message = "基材编码不能为空")
    private String baseMaterialCode;
    /**
     * 基材id
     */
    private Long baseMaterialId;
    /**
     * 基材名称
     */
    private String baseMaterialName;
    /**
     * 单位
     */
    private String baseMaterialUnit;

    /**
     * 币种
     */
    @NotEmpty(message = "币种不能为空")
    private String currencyType;

    /**
     * 价格
     */
    @DecimalMin(value = "0", message = "价格必须大于等于0")
    @NotNull(message = "价格不能为空")
    private BigDecimal baseMaterialPrice;

    /**
     * 数据来源
     */
    @NotEmpty(message = "数据来源必填")
    private String priceFrom;

    /**
     * 数据采集开始时间
     */
    private Date collectStartDate;

    /**
     * 数据采集结束时间
     */
    private Date collectEndDate;
}
