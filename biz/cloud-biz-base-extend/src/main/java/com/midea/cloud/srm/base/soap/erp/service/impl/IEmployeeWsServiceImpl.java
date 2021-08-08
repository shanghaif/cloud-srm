package com.midea.cloud.srm.base.soap.erp.service.impl;

import com.midea.cloud.srm.base.busiunit.service.IErpService;
import com.midea.cloud.srm.base.organization.service.IEmployeeService;
import com.midea.cloud.srm.base.organization.service.IPositionService;
import com.midea.cloud.srm.base.soap.erp.service.IEmployeeWsService;
import com.midea.cloud.srm.model.base.organization.entity.Employee;
import com.midea.cloud.srm.model.base.organization.entity.Position;
import com.midea.cloud.srm.model.base.soap.erp.base.dto.*;
import com.midea.cloud.srm.model.base.soap.erp.dto.SoapResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/18 13:40
 *  修改内容:
 * </pre>
 */
@Slf4j
@WebService(targetNamespace = "http://www.aurora-framework.org/schema",
        endpointInterface = "com.midea.cloud.srm.base.soap.erp.service.IEmployeeWsService")
@Component("iEmployeeWsService")
public class IEmployeeWsServiceImpl implements IEmployeeWsService {

    /**erp总线Service*/
    @Resource
    private IErpService iErpService;
    /**员工接口表Service*/
    @Resource
    private IEmployeeService iEmployeeService;

    public SoapResponse execute(EmployeeRequest request) {
        Long startTime = System.currentTimeMillis();
        SoapResponse response = new SoapResponse();
        /**获取instId和requestTime*/
        EsbInfoRequest esbInfo = request.getEsbInfo();
        String instId = "";
        String requestTime = "";
        if (null != esbInfo) {
            instId = esbInfo.getInstId();
            requestTime = esbInfo.getRequestTime();
        }

        /**获取员工List,并保存数据*/
        EmployeeRequest.RequestInfo requestInfo = request.getRequestInfo();
        EmployeeRequest.RequestInfo.Employees employeesClass = null;
        List<EmployeeEntity> employeesEntityList = null;
        if (null != requestInfo) {
            employeesClass = requestInfo.getEmployees();
            if (null != employeesClass) {
                employeesEntityList = employeesClass.getEmployee();
            }
        }
        log.info("erp获取员工接口数据: " + (null != request ? request.toString() : "空"));
        List<Employee> employeesList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(employeesEntityList)) {
            for (EmployeeEntity employeeEntity : employeesEntityList) {
                String empRcd = employeeEntity.getEmplRcd();
                if (null != employeeEntity) {
                    Employee employee = new Employee();
                    BeanUtils.copyProperties(employeeEntity, employee);
                    String emplid = employeeEntity.getEmplid();
                    employee.setEmplid(StringUtils.isNotEmpty(emplid) ? Long.valueOf(emplid) : null);
                    employeesList.add(employee);
                }
            }
            response = iErpService.saveOrUpdateEmployees(employeesList, instId, requestTime);
        }
        log.info("erp获取用户接口插入数据用时:"+(System.currentTimeMillis()-startTime)/1000 +"秒");
        return response;
    }
}
