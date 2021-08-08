package com.midea.cloud.srm.pr.logisticsRequirement.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.logistics.pr.requirement.dto.LogisticsRequirementHeadQueryDTO;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementHead;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 物流采购需求头表 Mapper 接口
 * </p>
 *
 * @author chenwt24@meiCloud.com
 * @since 2020-11-27
 */
public interface LogisticsRequirementHeadMapper extends BaseMapper<LogisticsRequirementHead> {

    /**
     * 根据条件查询采购申请列表
     * @param requirementHeadQueryDTO
     * @return
     */
    List<LogisticsRequirementHead> list(LogisticsRequirementHeadQueryDTO requirementHeadQueryDTO);
}
