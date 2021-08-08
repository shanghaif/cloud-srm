package com.midea.cloud.flow.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.flow.process.entity.TemplateInstance;

import java.util.List;

/**
 * <p>
 * 流程实例表 Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-03-17
 */
public interface TemplateInstanceMapper extends BaseMapper<TemplateInstance> {

    /**
     * Description 根据条件获取流程实例信息（根据时间倒序）
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.17
     * @throws
     **/
    List<TemplateInstance> getTemplateInstanceByParam(TemplateInstance templateInstance);

}
