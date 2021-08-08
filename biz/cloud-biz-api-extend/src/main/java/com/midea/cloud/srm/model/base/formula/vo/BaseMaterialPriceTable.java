package com.midea.cloud.srm.model.base.formula.vo;

import lombok.Data;

import java.util.List;

/**
 * @author tanjl11
 * @date 2020/11/02 15:49
 */
@Data
public class BaseMaterialPriceTable {
    private Long baseMaterialId;
    /**
     *基材名称
     */
    private String baseMaterialName;

    private List<BaseMaterialPriceVO> prices;
    /**
     * 用于标志是否已经改变，change='Y'不可以改变
     */
    private String change;
}
