package com.midea.cloud.srm.bid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.bid.mapper.LgtVendorQuotedSumMapper;
import com.midea.cloud.srm.bid.service.ILgtVendorQuotedSumService;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtVendorQuotedSum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  供应商报价汇总 服务实现类
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
public class LgtVendorQuotedSumServiceImpl extends ServiceImpl<LgtVendorQuotedSumMapper, LgtVendorQuotedSum> implements ILgtVendorQuotedSumService {

    @Autowired
    private LgtVendorQuotedSumMapper vendorQuotedSumMapper;
    @Override
    public List<LgtVendorQuotedSum> listCurrency(LgtVendorQuotedSum vendorQuotedSum) {
        return vendorQuotedSumMapper.listCurrency(vendorQuotedSum);
    }
}
