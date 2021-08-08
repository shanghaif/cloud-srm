package com.midea.cloud.flow.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.model.flow.process.dto.TemplateHeaderDTO;
import com.midea.cloud.srm.model.flow.process.entity.TemplateHeader;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 工作流模板头表 Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-03-17
 */
public interface TemplateHeaderMapper extends BaseMapper<TemplateHeader> {
    /**
     * Description 根据条件获取流程头、行相关信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.23
     * @throws
     **/
    TemplateHeaderDTO queryProcessTemplateByParam(@Param("templateHeader") TemplateHeaderDTO TemplateHeader) throws BaseException;
}
