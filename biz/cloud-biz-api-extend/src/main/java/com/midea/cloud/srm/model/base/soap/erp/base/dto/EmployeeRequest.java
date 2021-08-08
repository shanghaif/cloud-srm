package com.midea.cloud.srm.model.base.soap.erp.base.dto;

import com.midea.cloud.srm.model.base.organization.entity.Employee;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * <pre>
 *  员工接口表请求类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/18 11:47
 *  修改内容:
 * </pre>
 */

@XmlAccessorType(XmlAccessType.FIELD)//@XmlAccessorType(XmlAccessType.FIELD) 映射这个类中的所有字段到XML
@XmlType(name = "", propOrder = {
        "esbInfo","requestInfo"
}) //propOrder 指定映射XML时的节点顺序，使用该属性时，必须列出JavaBean对象中的所有字段，否则会报错
@XmlRootElement(name = "EmployeeRequest")
public class EmployeeRequest {
    @XmlElement(required = true)
    protected EsbInfoRequest esbInfo;

    @XmlElement(required = true)
    protected EmployeeRequest.RequestInfo requestInfo;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
//            "header",
            "employees"
    })

    public static class RequestInfo {
        //        @XmlElement(name = "HEADER", required = true)
//        protected HeaderRequest header;
        @XmlElement(required = true)
        protected EmployeeRequest.RequestInfo.Employees employees;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "employee"
        })
        public static class Employees {
            @XmlElement(required = true)
            protected List<EmployeeEntity> employee;

            public List<EmployeeEntity> getEmployee() {
                return employee;
            }
            public void setEmployee(List<EmployeeEntity> employee) {
                this.employee = employee;
            }
        }

        public EmployeeRequest.RequestInfo.Employees getEmployees() {
            return employees;
        }

        public void setEmployees(EmployeeRequest.RequestInfo.Employees employees) {
            this.employees = employees;
        }
    }

    public EsbInfoRequest getEsbInfo() {
        return esbInfo;
    }

    public void setEsbInfo(EsbInfoRequest esbInfo) {
        this.esbInfo = esbInfo;
    }

    public EmployeeRequest.RequestInfo getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(EmployeeRequest.RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }
}
