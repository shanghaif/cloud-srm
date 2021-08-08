package com.midea.cloud.srm.supcooperate.invoice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.redis.RedisLockUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoiceDetail;
import com.midea.cloud.srm.supcooperate.invoice.service.IInvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/invoice/invoiceDetail")
public class InvoiceDetailController extends BaseController {

    @Autowired
    private IInvoiceDetailService iInvoiceDetailService;

    /**
     * 批量删除
     * @param invoiceDetailIds
     */
    @PostMapping("/batchDelete")
    public void batchDeleteInvoiceDetails(@RequestBody List<Long> invoiceDetailIds) {
        iInvoiceDetailService.batchDeleteInvoiceDetails(invoiceDetailIds);
    }

    @GetMapping("/getByInvoiceDetailId")
    public InvoiceDetail getByInvoiceDetailId(@RequestParam("invoiceDetailId") Long invoiceDetailId) {
        return iInvoiceDetailService.getById(invoiceDetailId);
    }
 
}
