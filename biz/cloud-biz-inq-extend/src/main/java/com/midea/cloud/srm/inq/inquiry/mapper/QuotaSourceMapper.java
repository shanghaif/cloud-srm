package com.midea.cloud.srm.inq.inquiry.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaSourceDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaSource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 配额调整-关联寻源列表 Mapper 接口
 * </p>
 *
 * @author zhi1772778785@163.com
 * @since 2020-09-08
 */
public interface QuotaSourceMapper extends BaseMapper<QuotaSource> {
    List<QuotaSourceDTO> quotaSourceList(@Param(Constants.WRAPPER) QueryWrapper<QuotaSource>  wrapper);
}
