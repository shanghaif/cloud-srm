package com.midea.cloud.srm.base.purchase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;

import java.util.List;

/**
 * <p>
 * 税率设置 Mapper 接口
 * </p>
 *
 * @author chensl26@meicloud.com
 * @since 2020-02-27
 */
public interface PurchaseTaxMapper extends BaseMapper<PurchaseTax> {
    List<PurchaseTax> queryTaxBy1();
    List<PurchaseTax> queryTaxBy2();
    List<PurchaseTax> queryTaxBy3();
}
