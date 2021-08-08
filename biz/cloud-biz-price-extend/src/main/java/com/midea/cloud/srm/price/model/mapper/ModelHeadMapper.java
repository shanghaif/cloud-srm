package com.midea.cloud.srm.price.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.price.model.entity.ModelHead;

import java.util.List;

/**
 * <p>
 * 价格模型头表 Mapper 接口
 * </p>
 *
 * @author wangpr@meicloud.com
 * @since 2020-07-25
 */
public interface ModelHeadMapper extends BaseMapper<ModelHead> {
    List<ModelHead> listPage(ModelHead modelHead);
}
