package com.midea.cloud.api.interfaceconfig.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.api.interfaceconfig.dto.InterfaceConfigDTO;
import com.midea.cloud.srm.model.api.interfaceconfig.entity.InterfaceConfig;
import com.midea.cloud.srm.model.base.notice.dto.NoticeRequestDTO;

/**
 * <p>
 * 接口配置 Mapper 接口
 * </p>
 *
 * @author kuangzm@meicloud.com
 * @since 2020-06-08
 */
public interface InterfaceConfigMapper extends BaseMapper<InterfaceConfig> {

	List interfaceConfigFindList(InterfaceConfigDTO InterfaceConfig);
}
