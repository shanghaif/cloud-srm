package com.midea.cloud.srm.base.organization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.organization.entity.Employee;

/**
*  <pre>
 *  员工表（隆基员工同步） 服务类
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-18 11:18:28
 *  修改内容:
 * </pre>
*/
public interface IEmployeeService extends IService<Employee> {

    void employeeDataToUser();
}
