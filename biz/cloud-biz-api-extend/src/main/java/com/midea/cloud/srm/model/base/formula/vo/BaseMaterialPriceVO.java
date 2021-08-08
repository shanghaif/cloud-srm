package com.midea.cloud.srm.model.base.formula.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
 *  修改日期: 2020/8/26 22:58
 *  修改内容:
 * </pre>
 */
@Data
public class BaseMaterialPriceVO {

    private Long baseMaterialPriceId;

    /**
     * DRFAT、ACTIVE、INVIALD
     */
    private String baseMaterialPriceStatus;

    /**
     * 基价类型
     */
    private String baseMaterialPriceType;

    /**
     * 有效期起始日期
     */
    private Date activeDateFrom;

    /**
     * 有效期结束日期
     */
    private Date activeDateTo;

    /**
     * 基材编码
     */
    private String baseMaterialCode;

    /**
     * 基材名称
     */
    private String baseMaterialName;

    /**
     * 基材ID
     */
    private Long baseMaterialId;

    /**
     * 单位
     */
    private String baseMaterialUnit;

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

    private String createdBy;

    private Date creationDate;

    private String lastUpdatedBy;

    private Date lastUpdateDate;
    //对应要素集合
    private Long essentialFactorId;

}
