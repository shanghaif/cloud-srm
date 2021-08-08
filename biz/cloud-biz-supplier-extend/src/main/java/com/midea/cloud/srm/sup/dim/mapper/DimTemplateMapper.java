package com.midea.cloud.srm.sup.dim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.dim.dto.DimTemplateDTO;
import com.midea.cloud.srm.model.supplier.dim.entity.DimTemplate;

/**
 * <p>
 * 供应商模板配置表 Mapper 接口
 * </p>
 *
 * @author zhuwl7@meicloud.com
 * @since 2020-02-29
 */
public interface DimTemplateMapper extends BaseMapper<DimTemplate> {
    String  templateVersionGenrator();

    DimTemplateDTO getByTemplateId(Long templateId);
}
