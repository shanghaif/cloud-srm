package com.midea.cloud.srm.supcooperate.invoice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoice;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoiceAdvance;
import com.midea.cloud.srm.supcooperate.invoice.mapper.OnlineInvoiceAdvanceMapper;
import com.midea.cloud.srm.supcooperate.invoice.service.IOnlineInvoiceAdvanceService;
import com.midea.cloud.srm.supcooperate.invoice.service.IOnlineInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
*  <pre>
 *  网上开票-预付款 服务实现类
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
@Service
public class OnlineInvoiceAdvanceServiceImpl extends ServiceImpl<OnlineInvoiceAdvanceMapper, OnlineInvoiceAdvance> implements IOnlineInvoiceAdvanceService {

    @Autowired
    IOnlineInvoiceService iOnlineInvoiceService;

    @Autowired
    IOnlineInvoiceAdvanceService iOnlineInvoiceAdvanceService;

    @Override
    public List<OnlineInvoiceAdvance> listOnlineInvoiceAdvanceByFsscNo(String fsscNo) {
        /*fsscNo费控返回时的字段,需要转化为我们系统的boeNo*/
        OnlineInvoice onlineInvoice = iOnlineInvoiceService.getOne(new QueryWrapper<>(new OnlineInvoice().setBoeNo(fsscNo)));
        List<OnlineInvoiceAdvance> onlineInvoiceAdvances = new ArrayList<>();
        if (onlineInvoice != null) {
            onlineInvoiceAdvances = iOnlineInvoiceAdvanceService.list(new QueryWrapper<>(new OnlineInvoiceAdvance().setOnlineInvoiceId(onlineInvoice.getOnlineInvoiceId())));
        }
        return onlineInvoiceAdvances;
    }

    @Override
    public void updateOnlineInvoiceAdvanceByParam(List<OnlineInvoiceAdvance> onlineInvoiceAdvances) {
        this.updateBatchById(onlineInvoiceAdvances);
    }

    @Override
    public List<OnlineInvoiceAdvance> listOnlineInvoiceAdvanceByOnlineInvoiceId(Long onlineInvoiceId) {
        List<OnlineInvoiceAdvance> onlineInvoiceAdvances = iOnlineInvoiceAdvanceService.list(Wrappers.lambdaQuery(OnlineInvoiceAdvance.class)
                .eq(OnlineInvoiceAdvance::getOnlineInvoiceId, onlineInvoiceId));
        return onlineInvoiceAdvances;
    }
}
