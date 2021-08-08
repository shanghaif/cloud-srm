package com.midea.cloud.srm.supcooperate.invoice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.invoice.dto.InvoicePunishQueryDTO;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoicePunish;
import com.midea.cloud.srm.supcooperate.invoice.service.IInvoicePunishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  扣罚&派利明细 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-22 17:39:56
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/invoice/invoicePunish")
public class InvoicePunishController extends BaseController {

    @Autowired
    private IInvoicePunishService iInvoicePunishService;

    /**
     * 批量删除
     * @param invoicePunishIds
     */
    @PostMapping("/batchDelete")
    public void bachDeleteInvoicePunishes(@RequestBody List<Long> invoicePunishIds) {
        iInvoicePunishService.bachDeleteInvoicePunishes(invoicePunishIds);
    }

    /**
     * 分页条件查询扣罚
     * @param invoicePunishQueryDTO
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo listPageByParam(@RequestBody InvoicePunishQueryDTO invoicePunishQueryDTO) {
        return iInvoicePunishService.listPageByParam(invoicePunishQueryDTO);
    }
}
