package com.midea.cloud.srm.model.rbac.user.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <pre>
 *  用户角色关系导入模板类
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/23 16:02
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(45)
@HeadRowHeight(20)
public class RoleUserImport implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "*账号" ,index = 0)
    @NotEmpty(message = "账号不能为空;")
    private String userId;

    @ExcelProperty(value = "*角色名称" , index = 1)
    @NotEmpty(message = "角色名称不能为空;")
    private String roleName;

    @ExcelProperty(value = "*生效日期" , index = 2)
    @NotEmpty(message = "生效日期不能为空")
    private String startDate;

    @ExcelProperty(value = "失效日期" ,index = 3)
    private String endDate;

    @ExcelProperty(value = "错误信息", index = 4)
    private String errorMsg;
}
