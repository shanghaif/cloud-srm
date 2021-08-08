package com.midea.cloud.srm.perf.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.model.perf.template.dto.PerfTemplateDimWeightDTO;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateDimWeight;

import java.util.List;

/**
 * <p>
 * 绩效模型指标维度表 Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-05-30
 */
public interface PerfTemplateDimWeightMapper extends BaseMapper<PerfTemplateDimWeight> {

    /**
     * Description 根据条件获取绩效指标维度和指标信息(暂时不需要，有bug)
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.01
     * @throws BaseException
     **/
    List<PerfTemplateDimWeightDTO> findPerTemplateDimWeightAndLine(PerfTemplateDimWeight perfTemplateDimWeight) throws BaseException;

    /**
     * Description 根据条件获取绩效指标维度(如果有绩效指标和绩效指标行信息就查询)
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.12
     * @throws BaseException
     **/
    List<PerfTemplateDimWeight> findPerTemplateDimWeightByParam(PerfTemplateDimWeight templateDimWeight);
}
