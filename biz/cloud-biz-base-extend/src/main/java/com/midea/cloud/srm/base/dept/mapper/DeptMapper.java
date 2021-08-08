package com.midea.cloud.srm.base.dept.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.dept.dto.DeptDto;
import com.midea.cloud.srm.model.base.dept.entity.Dept;

import java.util.List;

/**
 * <p>
 * 部门表(隆基部门同步) Mapper 接口
 * </p>
 *
 * @author wuwl18@meiCloud.com
 * @since 2020-08-01
 */
public interface DeptMapper extends BaseMapper<Dept> {
    List<DeptDto> queryDeptByCompany(DeptDto deptDto);

    List<String> queryDeptidAll();
}
