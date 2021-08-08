package com.midea.cloud.srm.supauth.entry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryConfig;

/**
 * <p>
 * 供应商准入流程 Mapper 接口
 * </p>
 *
 * @author chensl26@meicloud.com
 * @since 2020-03-05
 */
public interface EntryConfigMapper extends BaseMapper<EntryConfig> {

    List<EntryConfig> listPageByParam(@Param(Constants.WRAPPER) QueryWrapper<EntryConfig> wrapper);
}
