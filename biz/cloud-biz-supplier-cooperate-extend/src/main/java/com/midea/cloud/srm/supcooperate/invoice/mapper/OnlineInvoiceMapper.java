package com.midea.cloud.srm.supcooperate.invoice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.OnlineInvoiceQueryDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoice;

import java.util.List;

/**
 * <p>
 * 网上开票表 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-08-31
 */
public interface OnlineInvoiceMapper extends BaseMapper<OnlineInvoice> {

    List<OnlineInvoice> findList(OnlineInvoiceQueryDTO onlineInvoiceQueryDTO);

    void updateDealineAndPeriod(Long onlineInvoiceId);

    void updateChangeFlag(Long onlineInvoiceId);

    void updateUnitPrice(Long onlineInvoiceId);

    void updateAmount(Long onlineInvoiceId);

    void updateTax(Long onlineInvoiceId);

    void updateCompareResult(Long onlineInvoiceId);

    List<OnlineInvoice> findListNew(OnlineInvoiceQueryDTO onlineInvoiceQueryDTO);
}
