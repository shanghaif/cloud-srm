package com.midea.cloud.srm.sup.riskradar.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.model.supplier.riskradar.entity.FinancialRating;
import com.midea.cloud.srm.sup.riskradar.mapper.FinancialRatingMapper;
import com.midea.cloud.srm.sup.riskradar.service.IFinancialRatingService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  财务评级表 服务实现类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 14:14:17
 *  修改内容:
 * </pre>
*/
@Service
public class FinancialRatingServiceImpl extends ServiceImpl<FinancialRatingMapper, FinancialRating> implements IFinancialRatingService {
    @Override
    public List<FinancialRating> getLastTwoYearData(Long vendorId) {
        return this.baseMapper.getLastTwoYearData(vendorId);
    }
}
