package com.midea.cloud.srm.pr.requirement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementHeadQueryDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementManageDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequisitionDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 采购需求头表 Mapper 接口
 * </p>
 *
 * @author fengdc3@meiCloud.com
 * @since 2020-05-12
 */
public interface RequirementHeadMapper extends BaseMapper<RequirementHead> {

    /**
     * Description 根据ID批量修改采购需求头信息
     * @Param 
     * @return 
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.14
     * @throws 
     **/
    int bachRequirementHead(@Param("requirementHeadList") List<RequirementHead> requirementHeadList);

    /**
     * Description 根据采购需求行表ID获取采购需求头和行集合信息
     * @Param requirementLineIdList 多个采购需求行id
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.20
     **/
    List<RequirementHeadQueryDTO> findRequirementHeadAndLineByLineIds(@Param("requirementLineIdList") List<Long> requirementLineIdList);

    /**
     * 获取的RequirementManageDTO只包含头行ID
     * @param requirementManageDTO
     * @return
     */
    List<RequirementManageDTO> listPageByParam(RequirementManageDTO requirementManageDTO);

    /**
     * 通过listPageByParam获取的id再次查出数据
     * @param requirementManageDTO
     * @return
     */
    List<RequirementManageDTO> listPageByIds(@Param("requirementManageDTOs") List<RequirementManageDTO> requirementManageDTO);

    List<RequirementManageDTO> listPageByIdsNew(@Param("lineIds") Collection<Long> lineIds);

    List<RequirementManageDTO> listPageByParamNew(@Param("param") RequirementManageDTO requirementManageDTO);

    Long listPageByParamNewCount(@Param("param") RequirementManageDTO requirementManageDTO);


    Integer getRequestNumber(@Param("param") String requestNumber);

    Integer getRowNumByOne(RequirementLine requirementLine);

}
