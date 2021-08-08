package com.midea.cloud.srm.supcooperate.invoice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoiceAdvance;
import com.midea.cloud.srm.supcooperate.invoice.service.IOnlineInvoiceAdvanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/invoice/onlineInvoiceAdvance")
public class OnlineInvoiceAdvanceController extends BaseController {

    @Autowired
    private IOnlineInvoiceAdvanceService iOnlineInvoiceAdvanceService;

    /**
     * 根据费控单号查询引用的预付款
     * @param fsscNo
     * @return
     */
    @GetMapping("/listOnlineInvoiceAdvanceByFsscNo")
    public List<OnlineInvoiceAdvance> listOnlineInvoiceAdvanceByFsscNo(@RequestParam("fsscNo") String fsscNo) {
        return iOnlineInvoiceAdvanceService.listOnlineInvoiceAdvanceByFsscNo(fsscNo);
    }

    /**
     * 批量删除网上开票预付款明细
     * @param onlineInvoiceAdvanceIds
     */
    @PostMapping("/batchDelete")
    public void batchDelete(@RequestBody List<Long> onlineInvoiceAdvanceIds) {
        iOnlineInvoiceAdvanceService.removeByIds(onlineInvoiceAdvanceIds);
    }

    /**
     * 根据开票预付id集获取开票预付
     * @param onlineInvoiceAdvanceIds
     * @return
     */
    @PostMapping("/listOnlineInvoiceAdvanceByIds")
    public List<OnlineInvoiceAdvance> listOnlineInvoiceAdvanceByIds(@RequestBody List<Long> onlineInvoiceAdvanceIds) {
        return iOnlineInvoiceAdvanceService.listByIds(onlineInvoiceAdvanceIds);
    }

    /**
     * 批量修改网上开票中预付款可用金额
     * @param onlineInvoiceAdvances
     */
    @PostMapping("/updateOnlineInvoiceAdvanceByParam")
    public void updateOnlineInvoiceAdvanceByParam(@RequestBody List<OnlineInvoiceAdvance> onlineInvoiceAdvances) {
        iOnlineInvoiceAdvanceService.updateOnlineInvoiceAdvanceByParam(onlineInvoiceAdvances);
    }

    @GetMapping("/listOnlineInvoiceAdvanceByOnlineInvoiceId")
    public List<OnlineInvoiceAdvance> listOnlineInvoiceAdvanceByOnlineInvoiceId(@RequestParam("onlineInvoiceId") Long onlineInvoiceId) {
        Assert.notNull(onlineInvoiceId, "onlineInvoiceId不能为空");
        return iOnlineInvoiceAdvanceService.listOnlineInvoiceAdvanceByOnlineInvoiceId(onlineInvoiceId);
    }
}
