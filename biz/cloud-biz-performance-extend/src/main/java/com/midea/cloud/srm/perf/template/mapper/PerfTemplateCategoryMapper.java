package com.midea.cloud.srm.perf.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateCategory;

import java.util.List;

/**
 * <p>
 * 绩效模型采购分类表 Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-05-28
 */
public interface PerfTemplateCategoryMapper extends BaseMapper<PerfTemplateCategory> {

    /**
     * Description 根据条件获取绩效模型采购分类表
     * @Param perfTemplateCategory 绩效模型采购分类表对象
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.15
     **/
    List<PerfTemplateCategory> findTemplateCateGoryByParam(PerfTemplateCategory perfTemplateCategory);
}
