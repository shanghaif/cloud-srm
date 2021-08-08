package com.midea.cloud.srm.ps.invoice.controller;

import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.supcooperate.invoice.service.IInvoiceDetailService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
*  <pre>
 *  开票明细表 前端控制器
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
@RestController
@RequestMapping("/ps/invoice/invoiceDetail")
public class InvoiceDetailController extends BaseController {

    @Autowired
    private IInvoiceDetailService iInvoiceDetailService;

    /**
     * 批量删除
     * @param invoiceDetailIds
     */
    @PostMapping("/batchDelete")
    public void batchDeleteInvoiceDetails(@RequestBody List<Long> invoiceDetailIds) {
        if (CollectionUtils.isNotEmpty(invoiceDetailIds)) {
            iInvoiceDetailService.batchDeleteInvoiceDetails(invoiceDetailIds);
        }
    }

}
