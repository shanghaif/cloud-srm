package com.midea.cloud.srm.base.organization.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.organization.mapper.EmployeeMapper;
import com.midea.cloud.srm.base.organization.service.IEmployeeService;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.organization.entity.Employee;
import com.midea.cloud.srm.model.rbac.user.dto.UserDTO1;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  员工表（隆基员工同步） 服务实现类
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
@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    /**
     * Srm用户rbacClient 跨模块调用
     */
    @Autowired
    private RbacClient rbacClient;

    @Override
    public void employeeDataToUser() {
        int totalRows = this.baseMapper.getEmployeeCount();
        //int totalRows = 2000;
        List<User> saveUsers = new ArrayList<>();
        List<User> updateUsers = new ArrayList<>();
        for(int rowNumber = 0; rowNumber < totalRows; rowNumber+=1000){
            /** get partly Employee data */
            List<Employee> employees = this.baseMapper.getEmployees(rowNumber);
            /** get all user data from rbac_user */
            List<UserDTO1> existUserIdAndCeeaEmpNos = rbacClient.getUserIdAndCeeaEmpNos();

            for (Employee employee : employees) {
                Date nowDate = new Date();
                Long saveId = IdGenrator.generate();
                String empId = (null != employee.getEmplid() ? String.valueOf(employee.getEmplid()) : "");
                User saveUser = new User();
                User updateUser = new User();
                if (StringUtils.isNotBlank(empId) && CollectionUtils.isNotEmpty(existUserIdAndCeeaEmpNos)) {
                    boolean updated = false;
                    for (UserDTO1 userIdAndceeaEmpNo : existUserIdAndCeeaEmpNos){
                        if (null != userIdAndceeaEmpNo && empId.equals(userIdAndceeaEmpNo.getCeeaEmpNo())){
                            Long updateId = userIdAndceeaEmpNo.getUserId();
                            String updateEmplRcd = employee.getEmplRcd();
                            saveUser.setCeeaEmpNo(empId);
                            updateUser.setUserId(updateId);
                            updateUser.setCeeaEmpNo(empId);
                            updateUser.setUsername(empId+updateEmplRcd);
                            updateUser.setPassword(String.valueOf(empId+empId));
                            String name = employee.getName();//隆基 姓名 --> 昵称
                            updateUser.setNickname(name);
                            String mobilePhone = employee.getMobilePhone();//隆基 移动电话 --> 手机
                            updateUser.setPhone(mobilePhone);
                            String email = employee.getEmailAddr2();//隆基 个人邮箱 --> 邮箱
                            updateUser.setEmail(email);
                            String department = employee.getDeptDescr();//隆基 部门 --> 部门
                            updateUser.setDepartment(department);
                            updateUser.setUserType("BUYER");//用户类型 -- 设置为采购商
                            updateUser.setMainType("N");//账号类型 -- 设置为子账号
                            updateUser.setStartDate(LocalDate.now());//生效日期 设置为当前日期
                            updateUser.setIsConfirm("N");//是否阅读协议 设置为N
                            String company = employee.getCompany();//隆基 公司 --> 公司（隆基新增）
                            updateUser.setCeeaCompany(company);
                            String companyDescr = employee.getCompanyDescr();//隆基 公司描述 --> 公司描述（隆基新增）
                            updateUser.setCeeaCompanyDescr(companyDescr);
                            String deptid = employee.getDeptid();//隆基 部门编码 --> 部门编码
                            updateUser.setCeeaDeptId(deptid);
                            String jobcode = employee.getJobcode();//隆基 岗位代码
                            updateUser.setCeeaJobcode(jobcode);
                            String jobcodeDescr = employee.getJobcodeDescr();
                            updateUser.setCeeaJobcodeDescr(jobcodeDescr);
                            updateUser.setLastUpdateDate(nowDate);

                            updateUsers.add(updateUser);
                            updated = true;
                            break;
                        }
                    }
                    if(!updated){
                        saveUser.setUserId(saveId);
                        String saveEmplRcd = employee.getEmplRcd();
                        saveUser.setCeeaEmpNo(empId);
                        saveUser.setUsername(empId+saveEmplRcd);
                        saveUser.setPassword(String.valueOf(empId+empId));
                        String name = employee.getName();//隆基 姓名 --> 昵称
                        saveUser.setNickname(name);
                        String mobilePhone = employee.getMobilePhone();//隆基 移动电话 --> 手机
                        saveUser.setPhone(mobilePhone);
                        String email = employee.getEmailAddr2();//隆基 个人邮箱 --> 邮箱
                        saveUser.setEmail(email);
                        String department = employee.getDeptDescr();//隆基 部门 --> 部门
                        saveUser.setDepartment(department);
                        saveUser.setUserType("BUYER");//用户类型 -- 设置为采购商
                        saveUser.setMainType("N");//账号类型 -- 设置为子账号
                        saveUser.setStartDate(LocalDate.now());//生效日期 设置为当前日期
                        saveUser.setIsConfirm("N");//是否阅读协议 设置为N
                        String company = employee.getCompany();//隆基 公司 --> 公司（隆基新增）
                        saveUser.setCeeaCompany(company);
                        String companyDescr = employee.getCompanyDescr();//隆基 公司描述 --> 公司描述（隆基新增）
                        saveUser.setCeeaCompanyDescr(companyDescr);
                        String deptid = employee.getDeptid();//隆基 部门编码 --> 部门编码
                        saveUser.setCeeaDeptId(deptid);
                        String jobcode = employee.getJobcode();//隆基 岗位代码
                        saveUser.setCeeaJobcode(jobcode);
                        String jobcodeDescr = employee.getJobcodeDescr();
                        saveUser.setCeeaJobcodeDescr(jobcodeDescr);
                        saveUser.setCreationDate(nowDate);
                        saveUser.setLastUpdateDate(nowDate);
                        saveUsers.add(saveUser);
                    }
                }
            }

        }
        log.info("saveUsers::"+saveUsers.size());
        rbacClient.saveUsersFromEmployee(saveUsers);
        log.info("updateUsers::"+updateUsers.size());
        rbacClient.updateUsersFromEmployee(updateUsers);
        log.info("=============success==========");
    }
}
