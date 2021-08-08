package com.midea.cloud.srm.base.purchase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCurrency;

import java.util.List;

/**
 * <p>
 * 币种设置 Mapper 接口
 * </p>
 *
 * @author chensl26@meicloud.com
 * @since 2020-02-26
 */
public interface PurchaseCurrencyMapper extends BaseMapper<PurchaseCurrency> {

    List<PurchaseCurrency> listAllCurrencyForImport();
}
