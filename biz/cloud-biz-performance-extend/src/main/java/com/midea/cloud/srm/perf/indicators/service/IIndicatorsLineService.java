package com.midea.cloud.srm.perf.indicators.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.perf.inditors.entity.IndicatorsLine;

/**
 *  <pre>
 *  指标库行表 服务类
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-26 11:35:37
 *  修改内容:
 * </pre>
 */
public interface IIndicatorsLineService extends IService<IndicatorsLine> {

    /**
     * Description 根据ID删除指标行信息
     * @Param indicatorLineId 指标行Id
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.27
     * @throws BaseException
     **/
    BaseResult<String> deleteIndicatorsLine(Long indicatorLineId) throws BaseException;

}
