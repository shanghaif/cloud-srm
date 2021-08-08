package com.midea.cloud.srm.model.base.purchase.dto;

import lombok.Data;

import java.util.Collection;
import java.util.List;

/**
 * @author tanjl11
 * @date 2020/10/18 17:30
 */
@Data
public class PurchaseRateCheck {
    private String fromCode;

    private Collection<String> toCodes;
}
