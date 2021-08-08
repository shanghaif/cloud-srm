package com.midea.cloud.srm.base.organization.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.organization.service.IEmployeeService;
import com.midea.cloud.srm.model.base.organization.entity.Employee;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  员工表（隆基员工同步） 前端控制器
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
@RestController
@RequestMapping("/organization/employee")
public class EmployeeController extends BaseController {

    @Autowired
    private IEmployeeService iEmployeeService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public Employee get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iEmployeeService.getById(id);
    }

    /**
    * 新增
    * @param employee
    */
    @PostMapping("/add")
    public void add(@RequestBody Employee employee) {
        Long id = IdGenrator.generate();
        employee.setId(id);
        iEmployeeService.save(employee);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iEmployeeService.removeById(id);
    }

    /**
    * 修改
    * @param employee
    */
    @PostMapping("/modify")
    public void modify(@RequestBody Employee employee) {
        iEmployeeService.updateById(employee);
    }

    /**
    * 分页查询
    * @param employee
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<Employee> listPage(@RequestBody Employee employee) {
        PageUtil.startPage(employee.getPageNum(), employee.getPageSize());
        QueryWrapper<Employee> wrapper = new QueryWrapper<Employee>(employee);
        return new PageInfo<Employee>(iEmployeeService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<Employee> listAll() { 
        return iEmployeeService.list();
    }

    /**
     *
     */
    @PostMapping("dataTransfer")
    public void employeeDataToUser(){
        iEmployeeService.employeeDataToUser();
    }

}
