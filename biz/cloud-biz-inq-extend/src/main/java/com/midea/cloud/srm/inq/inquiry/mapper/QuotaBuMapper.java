package com.midea.cloud.srm.inq.inquiry.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.midea.cloud.srm.model.inq.inquiry.dto.QuotaBuDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaBu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 配额事业部中间表 Mapper 接口
 * </p>
 *
 * @author yourname@meiCloud.com
 * @since 2020-09-05
 */
public interface QuotaBuMapper extends BaseMapper<QuotaBu> {
    List<QuotaBuDTO> quotaBuList(@Param(Constants.WRAPPER) QueryWrapper<QuotaBu> wrapper);

}
