package com.midea.cloud.srm.model.rbac.user.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 *  erp用户导入信息
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/23 15:48
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(50)
@HeadRowHeight(20)
public class ErpUserImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账号 0 必填
     */
    @ExcelProperty(value = "*账号" ,index = 0)
    @NotEmpty(message = "账号不能为空" )
    private String userId;

    /**
     * 姓名 1 必填
     */
    @ExcelProperty(value = "*姓名" ,index = 1)
    @NotEmpty(message = "姓名不能为空")
    private String nikeName;

    /**
     * 手机号 2 非必填
     */
    @ExcelProperty(value = "手机号", index = 2)
    private String phone;

    /**
     * 邮箱 3 必填
     */
    @ExcelProperty(value = "*邮箱" ,index = 3)
    @NotEmpty(message = "邮箱不能为空")
    private String email;

    /**
     * 公司名称 4 必填
     */
    @ExcelProperty(value = "*公司名称" ,index = 4)
    @NotEmpty(message = "公司名称不能为空")
    private String companyName;

    /**
     * 部门名称 5 必填
     */
    @ExcelProperty(value = "*部门名称" ,index = 5)
    @NotEmpty(message = "部门名称不能为空")
    private String deptName;

    /**
     * 员工工号 6 必填
     */
    @ExcelProperty(value = "*员工工号" ,index = 6)
    @NotEmpty(message = "员工工号不能为空")
    private String empNo;

    /**
     * 岗位名称 7 非必填
     */
    @ExcelProperty(value = "岗位名称" ,index = 7)
    private String jobName;

    /**
     * 生效日期 8 必填
     */
    @ExcelProperty(value = "*生效日期" ,index = 8)
    @NotEmpty(message = "生效日期不能为空")
    private String effectiveFrom;

    /**
     * 失效日期 9 非必填
     */
    @ExcelProperty(value = "失效日期" ,index = 9)
    private String effectiveTo;

    /**
     * ERP采购员姓名 10 非必填
     */
    @ExcelProperty(value = "ERP采购员姓名" ,index = 10)
    private String erpPoAgentName;

    /**
     * 数据行号
     */
    @ExcelIgnore
    private int row;

}
