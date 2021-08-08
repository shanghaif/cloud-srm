package com.midea.cloud.srm.base.organization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.organization.entity.Employee;

import java.util.List;

/**
 * <p>
 * 员工表（隆基员工同步） Mapper 接口
 * </p>
 *
 * @author xiexh12@midea.com
 * @since 2020-08-18
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    List<Employee> getEmployees(int rowNumber);

    int getEmployeeCount();
}
