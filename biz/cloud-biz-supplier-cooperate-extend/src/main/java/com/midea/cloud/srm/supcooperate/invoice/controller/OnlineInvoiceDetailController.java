package com.midea.cloud.srm.supcooperate.invoice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.InvoiceDetail;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoiceDetail;
import com.midea.cloud.srm.supcooperate.invoice.service.IOnlineInvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
*  <pre>
 *  网上开票-发票明细表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-31 16:48:49
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/invoice/onlineInvoiceDetail")
public class OnlineInvoiceDetailController extends BaseController {

    @Autowired
    private IOnlineInvoiceDetailService iOnlineInvoiceDetailService;

    /**
     * 获取发票详情集合，一定要加条件避免全查出来内存爆炸；
     * @param onlineInvoiceDetail
     * @return
     */
    @PostMapping("/list")
    public List<OnlineInvoiceDetail> listInvoiceDetail(@RequestBody OnlineInvoiceDetail onlineInvoiceDetail){
        if(Objects.isNull(onlineInvoiceDetail.getOnlineInvoiceId())){
            return new ArrayList<>();
        }
        return iOnlineInvoiceDetailService.list(Wrappers.lambdaQuery(OnlineInvoiceDetail.class)
                .eq(OnlineInvoiceDetail::getOnlineInvoiceId , onlineInvoiceDetail.getOnlineInvoiceId()));
    }


}
