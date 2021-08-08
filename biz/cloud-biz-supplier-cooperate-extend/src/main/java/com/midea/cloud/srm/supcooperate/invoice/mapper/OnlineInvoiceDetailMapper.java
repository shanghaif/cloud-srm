package com.midea.cloud.srm.supcooperate.invoice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoiceDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 网上开票-发票明细表 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-08-31
 */
public interface OnlineInvoiceDetailMapper extends BaseMapper<OnlineInvoiceDetail> {

    void updateChangeFlag(@Param("collect")List<Long> collect);

    void updateUnitPrice(@Param("collect")List<Long> collect);

    void updateAmount(@Param("collect")List<Long> collect);

    void updateTax(@Param("collect")List<Long> collect);

    void updateCompareResult(@Param("collect")List<Long> collect);
}
