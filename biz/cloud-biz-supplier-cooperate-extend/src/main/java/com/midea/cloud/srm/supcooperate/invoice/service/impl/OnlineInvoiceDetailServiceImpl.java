package com.midea.cloud.srm.supcooperate.invoice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.model.suppliercooperate.invoice.entity.OnlineInvoiceDetail;
import com.midea.cloud.srm.supcooperate.invoice.mapper.OnlineInvoiceDetailMapper;
import com.midea.cloud.srm.supcooperate.invoice.service.IOnlineInvoiceDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  网上开票-发票明细表 服务实现类
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
@Service
public class OnlineInvoiceDetailServiceImpl extends ServiceImpl<OnlineInvoiceDetailMapper, OnlineInvoiceDetail> implements IOnlineInvoiceDetailService {

    @Override
    public void updateChangeFlag(List<Long> collect) {
        this.baseMapper.updateChangeFlag(collect);
    }

    @Override
    public void updateUnitPrice(List<Long> collect) {
        this.baseMapper.updateUnitPrice(collect);
    }

    @Override
    public void updateAmount(List<Long> collect) {
        this.baseMapper.updateAmount(collect);
    }

    @Override
    public void updateTax(List<Long> collect) {
        this.baseMapper.updateTax(collect);
    }

    @Override
    public void updateCompareResult(List<Long> collect) {
        this.baseMapper.updateCompareResult(collect);
    }
}
