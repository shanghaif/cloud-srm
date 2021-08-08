package com.midea.cloud.srm.supcooperate.invoice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoiceNoticeReturn;
import com.midea.cloud.srm.supcooperate.invoice.service.IInvoiceNoticeReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  开票通知退库明细表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-27 11:56:33
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/invoice/invoice-notice-return")
public class InvoiceNoticeReturnController extends BaseController {

    @Autowired
    private IInvoiceNoticeReturnService iInvoiceNoticeReturnService;


}
