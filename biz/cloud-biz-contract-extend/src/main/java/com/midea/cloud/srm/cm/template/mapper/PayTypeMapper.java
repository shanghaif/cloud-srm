package com.midea.cloud.srm.cm.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.cm.template.entity.PayType;

import java.util.List;

/**
 * <p>
 * 合同付款类型 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-05-13
 */
public interface PayTypeMapper extends BaseMapper<PayType> {

    List<PayType> listAll();
}
