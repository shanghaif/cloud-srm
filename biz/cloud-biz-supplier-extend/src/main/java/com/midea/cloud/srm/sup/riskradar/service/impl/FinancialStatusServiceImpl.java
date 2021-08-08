package com.midea.cloud.srm.sup.riskradar.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.model.supplier.riskradar.entity.FinancialStatus;
import com.midea.cloud.srm.sup.riskradar.mapper.FinancialStatusMapper;
import com.midea.cloud.srm.sup.riskradar.service.IFinancialStatusService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  财务状况表 服务实现类
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
public class FinancialStatusServiceImpl extends ServiceImpl<FinancialStatusMapper, FinancialStatus> implements IFinancialStatusService {
    @Override
    public List<FinancialStatus> getLastTwoYearData(Long vendorId) {
        return this.baseMapper.getLastTwoYearData(vendorId);
    }
}
