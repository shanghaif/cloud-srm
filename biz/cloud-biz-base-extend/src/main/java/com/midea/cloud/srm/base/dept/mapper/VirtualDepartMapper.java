package com.midea.cloud.srm.base.dept.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.dept.dto.DeptDto;
import com.midea.cloud.srm.model.base.dept.entity.VirtualDepart;

import java.util.List;

/**
 * <p>
 * 虚拟组织表 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-09-22
 */
public interface VirtualDepartMapper extends BaseMapper<VirtualDepart> {

    List<DeptDto> queryVirtualDepartByOrgId(DeptDto deptDto);

    List<String> queryBeptidAll();
}
