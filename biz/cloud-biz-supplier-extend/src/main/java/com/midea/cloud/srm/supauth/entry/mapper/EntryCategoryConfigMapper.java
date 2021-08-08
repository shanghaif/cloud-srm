package com.midea.cloud.srm.supauth.entry.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryCategoryConfig;

/**
 * <p>
 * 供应商准入流程行表（品类配置） Mapper 接口
 * </p>
 *
 * @author xiexh12@meicloud.com
 * @since 2020-09-15
 */
public interface EntryCategoryConfigMapper extends BaseMapper<EntryCategoryConfig> {

    List<Long> getCategoryIdListByEntryConfigId(Long entryConfigId);

}
