package com.midea.cloud.flow.model.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * <pre>
 * 模拟人事模块信息。
 * </pre>
 *
 * @author developer developer@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class EmployeeBaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 员工id
     */
    private String employeeId;
    /**
     * 员工姓名
     */
    private String employeeName;

    /**
     * 员工工号
     */
    private String employeeCode;

    /**
     * 所在组织名称
     */
    private String unitName;

    /**
     * 扩展属性，保留字段，暂时未用到
     */

    private Map<String, String> extProperty;


    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }


    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Map<String, String> getExtProperty() {
        return extProperty;
    }

    public void setExtProperty(Map<String, String> extProperty) {
        this.extProperty = extProperty;
    }

    @Override
    public String toString() {
        return "EmployeeBasePojo{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", employeeCode='" + employeeCode + '\'' +
                ", unitName='" + unitName + '\'' +
                ", extProperty=" + extProperty +
                '}';
    }
}
