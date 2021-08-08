package com.midea.cloud.srm.cm.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.cm.template.dto.TemplHeadQueryDTO;
import com.midea.cloud.srm.model.cm.template.entity.TemplHead;

import java.util.List;

/**
 * <p>
 * 合同模板头表 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-05-19
 */
public interface TemplHeadMapper extends BaseMapper<TemplHead> {

    List<TemplHead> listPageByParm(TemplHeadQueryDTO templHeadQueryDTO);
}
