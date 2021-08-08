package com.midea.cloud.srm.model.base.formula.dto.calculate;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * 保存基价时的拆分对象
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/31 11:18
 *  修改内容:
 * </pre>
 */
@Data
public class PriceSpiltDto {
    /**
     * 开始时间
     */
    private Date from;
    /**
     * 结束时间
     */
    private Date to;
    /**
     * 权重
     */
    private Integer priority;
    /**
     * 业务id
     */
    private Long bizId;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 1 修改 0 删除
     */
    private Integer opType;
    /**
     * 数据来源
     */
    private String priceFrom;
    /**
     * 基价名
     */
    private String name;
}
