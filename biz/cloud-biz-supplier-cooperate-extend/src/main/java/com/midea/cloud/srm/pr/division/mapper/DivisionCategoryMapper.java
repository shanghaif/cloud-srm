package com.midea.cloud.srm.pr.division.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.pm.pr.division.entity.DivisionCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品类分工规则表 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-07-22
 */
public interface DivisionCategoryMapper extends BaseMapper<DivisionCategory> {
    List<DivisionCategory> getListOrgByPurchaseCategory(@Param("maps")Map<String,Long>  OrgByPurchaseCategoryMap );
}
