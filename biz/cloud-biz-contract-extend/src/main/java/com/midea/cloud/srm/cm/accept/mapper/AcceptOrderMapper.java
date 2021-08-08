package com.midea.cloud.srm.cm.accept.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.midea.cloud.srm.model.cm.accept.dto.AcceptOrderDTO;
import com.midea.cloud.srm.model.cm.accept.entity.AcceptOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 合同验收单 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-06-03
 */
public interface AcceptOrderMapper extends BaseMapper<AcceptOrder> {
    List<AcceptOrder> listByParm(@Param(Constants.WRAPPER) QueryWrapper<AcceptOrderDTO> wrapper);
}