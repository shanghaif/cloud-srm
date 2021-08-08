package com.midea.cloud.srm.ps.invoice.controller;

import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.ps.invoice.service.IOnlineInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
*  <pre>
 *  网上开票-预付款 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-31 16:51:12
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/ps/invoice/onlineInvoiceAdvance")
public class OnlineInvoiceAdvanceController extends BaseController {

    @Autowired
    private IOnlineInvoiceService iOnlineInvoiceService;

    /**
     * 批量删除网上开票预付款
     * @param onlineInvoiceAdvanceIds
     */
    @PostMapping("/batchDelete")
    public void batchDelete(@RequestBody List<Long> onlineInvoiceAdvanceIds) {
        iOnlineInvoiceService.batchDelete(onlineInvoiceAdvanceIds);
    }
}
