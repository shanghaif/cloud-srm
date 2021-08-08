package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.inq.inquiry.mapper.QuoteAuthMapper;
import com.midea.cloud.srm.inq.inquiry.service.IQuoteAuthService;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuoteAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *   服务实现类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-21 15:25:37
 *  修改内容:
 * </pre>
*/
@Service
public class QuoteAuthServiceImpl extends ServiceImpl<QuoteAuthMapper, QuoteAuth> implements IQuoteAuthService {

    @Autowired
    IQuoteAuthService iQuoteAuthService;
    @Override
    public List<QuoteAuth> getByHeadId(Long headId) {
        QueryWrapper<QuoteAuth> wrapper = new QueryWrapper();
        wrapper.eq("INQUIRY_ID", headId);
        return iQuoteAuthService.list(wrapper);
    }
}
