package com.midea.cloud.srm.supcooperate.invoice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoicePunish;
import com.midea.cloud.srm.supcooperate.invoice.service.IOnlineInvoicePunishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  网上开票-扣罚&派利明细 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-31 16:49:25
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/invoice/onlineInvoicePunish")
public class OnlineInvoicePunishController extends BaseController {

    @Autowired
    private IOnlineInvoicePunishService iOnlineInvoicePunishService;

    /**
     * 批量删除
     * @param onlineInvoicePunishIds
     */
    @PostMapping("/batchDelete")
    public void batchDelete(@RequestBody List<Long> onlineInvoicePunishIds) {
        iOnlineInvoicePunishService.batchDelete(onlineInvoicePunishIds);
    }
 
}
