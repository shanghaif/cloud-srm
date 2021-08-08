package com.midea.cloud.srm.supcooperate.invoice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoiceNoticeQueryDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoiceNotice;

import java.util.List;

/**
 * <p>
 * 开票通知表 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-06-27
 */
public interface InvoiceNoticeMapper extends BaseMapper<InvoiceNotice> {

    /**
     * 分页条件查询
     * @param invoiceNoticeQueryDTO
     * @return
     */
    List<InvoiceNoticeDTO> listPageByParm(InvoiceNoticeQueryDTO invoiceNoticeQueryDTO);

    /**
     * 服务类创建网上开票
     * @param invoiceNoticeQueryDTO
     * @return
     */
    List<InvoiceNoticeDTO> orderListPageByParam(InvoiceNoticeQueryDTO invoiceNoticeQueryDTO);
}
