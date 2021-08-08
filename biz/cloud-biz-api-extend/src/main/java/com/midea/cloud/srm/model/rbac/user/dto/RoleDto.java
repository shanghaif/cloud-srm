package com.midea.cloud.srm.model.rbac.user.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/30
 *  修改内容:
 * </pre>
 */
@ColumnWidth(20)
@Data
public class RoleDto implements Serializable {

    private static final long serialVersionUID = 1345918833802960170L;
    /**
     * 角色编码
     */
    @ExcelProperty( value = "*角色编码",index = 0)
    private String roleCode;

    /**
     * 角色名称
     */
    @ExcelProperty( value = "*角色名称",index = 1)
    private String roleName;

    /**
     * 角色类型
     */
    @ExcelProperty( value = "*角色类型",index = 2)
    private String roleType;

    /**
     * 生效时间
     */
    @ExcelProperty( value = "*生效时间",index = 3)
    private String startDate;

    /**
     * 失效时间
     */
    @ExcelProperty( value = "失效时间",index = 4)
    private String endDate;

    /**
     * 错误信息提示
     */
    @ExcelProperty( value = "错误信息提示",index = 5)
    private String errorMsg;

}
