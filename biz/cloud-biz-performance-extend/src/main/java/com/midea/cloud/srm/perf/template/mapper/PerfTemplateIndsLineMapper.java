package com.midea.cloud.srm.perf.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateIndsLine;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateLine;

import java.util.List;

/**
 * <p>
 * 绩效模型指标信息行表 Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-05-30
 */
public interface PerfTemplateIndsLineMapper extends BaseMapper<PerfTemplateIndsLine> {

    /**
     * Description 根据条件获取绩效模型指标行表集合
     * @Param templateLine 绩效模型指标行表实体
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.12
     **/
    List<PerfTemplateIndsLine> findTempateIndsLineByParam(PerfTemplateIndsLine templateIndsLine);
}
