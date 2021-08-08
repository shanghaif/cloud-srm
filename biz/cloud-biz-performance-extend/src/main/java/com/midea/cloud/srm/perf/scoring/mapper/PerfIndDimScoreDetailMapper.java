package com.midea.cloud.srm.perf.scoring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.perf.scoring.PerfIndDimScoreDetail;

import java.util.List;

/**
 * <p>
 * 指标维度绩效得分明细表 Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-06-16
 */
public interface PerfIndDimScoreDetailMapper extends BaseMapper<PerfIndDimScoreDetail> {

    /**
     * Description 根据条件获绩效指标绩效得分明细表集合(用于连表查询)
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.19
     **/
    List<PerfIndDimScoreDetail> findIndDimScoreDetailList(PerfIndDimScoreDetail indDimScoreDetail);
}
