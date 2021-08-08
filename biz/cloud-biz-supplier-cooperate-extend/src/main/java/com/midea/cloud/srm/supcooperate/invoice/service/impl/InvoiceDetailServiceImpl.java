package com.midea.cloud.srm.supcooperate.invoice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoiceDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetail;
import com.midea.cloud.srm.supcooperate.invoice.mapper.InvoiceDetailMapper;
import com.midea.cloud.srm.supcooperate.invoice.service.IInvoiceDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IWarehousingReturnDetailService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
*  <pre>
 *  开票明细表 服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-22 17:14:23
 *  修改内容:
 * </pre>
*/
@Service
public class InvoiceDetailServiceImpl extends ServiceImpl<InvoiceDetailMapper, InvoiceDetail> implements IInvoiceDetailService {

    @Autowired
    private IWarehousingReturnDetailService iWarehousingReturnDetailService;

    @Override
    @Transactional
    public void batchDeleteInvoiceDetails(List<Long> invoiceDetailIds) {
        if (CollectionUtils.isNotEmpty(invoiceDetailIds)) {
            for (Long invoiceDetailId : invoiceDetailIds) {
                InvoiceDetail byId = this.getById(invoiceDetailId);
                BigDecimal invoiceQuantity = byId.getInvoiceQuantity();//开票通知本次开票数量
                //恢复入退库明细未开票数量
                WarehousingReturnDetail warehousingReturnDetail = iWarehousingReturnDetailService.getById(byId.getWarehousingReturnDetailId());
                BigDecimal notInvoiceQuantity = warehousingReturnDetail.getNotInvoiceQuantity();//入退库明细未开票数量
                iWarehousingReturnDetailService.updateById(warehousingReturnDetail.setNotInvoiceQuantity(notInvoiceQuantity.add(invoiceQuantity)));
                this.removeById(invoiceDetailId);
            }
        }
    }
}
