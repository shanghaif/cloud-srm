package com.midea.cloud.srm.base.organization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组织关系 Mapper 接口
 * </p>
 *
 * @author chensl26@meicloud.com
 * @since 2020-02-15
 */
public interface OrganizationRelationMapper extends BaseMapper<OrganizationRelation> {

    List<OrganizationRelation> selectByParam(@Param("organizationRelation") OrganizationRelation organizationRelation);

    List<OrganizationRelation> selectByParamNew(@Param("organizationRelation") OrganizationRelation organizationRelation, @Param("fullPathIds") List<String> fullPathIds);

    void replaceFullPathId(@Param("oldIds") String oldIds, @Param("newIds") String newIds, @Param("relId") Long relId);
}
