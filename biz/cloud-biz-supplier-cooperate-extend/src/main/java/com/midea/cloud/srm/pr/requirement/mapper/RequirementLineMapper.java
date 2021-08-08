package com.midea.cloud.srm.pr.requirement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementHeadQueryDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirermentLineQueryDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采购需求行表 Mapper 接口
 * </p>
 *
 * @author fengdc3@meiCloud.com
 * @since 2020-05-12
 */
public interface RequirementLineMapper extends BaseMapper<RequirementLine> {

    /**
     * Description 根据ID批量修改采购需求行信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.15
     * @throws
     **/
    int bachUpdateRequirementLine(@Param("requirementLineList") List<RequirementLine> requirementLineList);

    /**
     * Description 获取采购需求合并信息
     * @Param requirementLineIdList 多个采购需求行id
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.17
     * @throws
     **/
    List<RequirementLine> findRequirementMergeList(@Param("requirementLineIdList") List<Long> requirementLineIdList);

    Long queryLineNum(Map<String, Object> param);

    List<RequirementLine> ceeaListForOrder(RequirermentLineQueryDTO params);

    List<RequirementLine> listPageRequirementChart(RequirementHeadQueryDTO requirementHeadQueryDTO);
}
