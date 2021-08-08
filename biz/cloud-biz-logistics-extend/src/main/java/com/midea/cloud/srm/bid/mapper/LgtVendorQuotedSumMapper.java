package com.midea.cloud.srm.bid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtVendorQuotedSum;

import java.util.List;

/**
 * <p>
 * 供应商报价汇总 Mapper 接口
 * </p>
 *
 * @author wangpr@meiCloud.com
 * @since 2021-01-06
 */
public interface LgtVendorQuotedSumMapper extends BaseMapper<LgtVendorQuotedSum> {

    List<LgtVendorQuotedSum> listCurrency(LgtVendorQuotedSum vendorQuotedSum);
}
