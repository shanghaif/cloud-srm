package com.midea.cloud.srm.bid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.bid.mapper.LgtVendorQuotedHeadMapper;
import com.midea.cloud.srm.bid.service.ILgtVendorQuotedHeadService;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtVendorQuotedHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  供应商报价头表 服务实现类
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-06 09:08:34
 *  修改内容:
 * </pre>
*/
@Service
public class LgtVendorQuotedHeadServiceImpl extends ServiceImpl<LgtVendorQuotedHeadMapper, LgtVendorQuotedHead> implements ILgtVendorQuotedHeadService {

    @Autowired
    private LgtVendorQuotedHeadMapper vendorQuotedHeadMapper;
    @Override
    public List<LgtVendorQuotedHead> listCurrency(LgtVendorQuotedHead vendorQuotedHead) {
        return vendorQuotedHeadMapper.listCurrency(vendorQuotedHead);
    }
}
