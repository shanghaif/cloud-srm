package com.midea.cloud.srm.inq.inquiry.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaAdjustDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaAdjust;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaBu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 配额调整 Mapper 接口
 * </p>
 *
 * @author zhi1772778785@163.com
 * @since 2020-09-08
 */
public interface QuotaAdjustMapper extends BaseMapper<QuotaAdjust> {
    List<QuotaAdjust> quotaAdjustList(@Param(Constants.WRAPPER) QueryWrapper<QuotaAdjustDTO> wrapper);

}
