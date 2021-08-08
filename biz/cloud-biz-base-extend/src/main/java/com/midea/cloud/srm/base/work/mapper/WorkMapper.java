package com.midea.cloud.srm.base.work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.work.dto.WorkRequestDTO;
import com.midea.cloud.srm.model.base.work.entry.Work;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**s
 * <p>
 * 任务表 Mapper 接口
 * </p>
 *
 * @author huanghb14@example.com
 * @since 2020-02-12
 */
public interface WorkMapper extends BaseMapper<Work> {

    List findList(WorkRequestDTO workRequestDTO);

    List<Map<String,Integer>> workCount(@Param("toVendorId")Long toVendorId,@Param("handleId")Long handleId);
}