package com.midea.cloud.srm.sup.change.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeRequestDTO;
import com.midea.cloud.srm.model.supplier.change.dto.InfoChangeDTO;
import com.midea.cloud.srm.model.supplier.change.entity.InfoChange;

import java.util.List;

/**
 * <p>
 * 公司信息变更表 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-03-30
 */
public interface InfoChangeMapper extends BaseMapper<InfoChange> {

    List<InfoChangeDTO> listPageByParam(ChangeRequestDTO changeRequestDTO);
}
