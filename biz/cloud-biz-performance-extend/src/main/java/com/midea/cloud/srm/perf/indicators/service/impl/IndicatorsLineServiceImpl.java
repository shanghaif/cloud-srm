package com.midea.cloud.srm.perf.indicators.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.srm.model.perf.inditors.entity.IndicatorsLine;
import com.midea.cloud.srm.perf.indicators.mapper.IndicatorsLineMapper;
import com.midea.cloud.srm.perf.indicators.service.IIndicatorsLineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 *  <pre>
 *  指标库行表 服务实现类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-26 11:35:37
 *  修改内容:
 * </pre>
 */
@Service
public class IndicatorsLineServiceImpl extends ServiceImpl<IndicatorsLineMapper, IndicatorsLine> implements IIndicatorsLineService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult<String> deleteIndicatorsLine(Long indicatorsLineId) throws BaseException {
        Assert.notNull(indicatorsLineId, "id不能为空");
        BaseResult<String> result = BaseResult.build(ResultCode.OPERATION_FAILED.getMessage(), ResultCode.OPERATION_FAILED.getMessage());
        try {
            int updateCount = getBaseMapper().deleteById(indicatorsLineId);
            if(0 < updateCount){
                result.setCode(ResultCode.SUCCESS.getCode());
                result.setMessage(ResultCode.SUCCESS.getMessage());
            }
        }catch (Exception e){
            log.error("根据ID: "+indicatorsLineId+"删除指标行信息");
            throw new BaseException(ResultCode.OPERATION_FAILED.getMessage());
        }
        return result;
    }

}
