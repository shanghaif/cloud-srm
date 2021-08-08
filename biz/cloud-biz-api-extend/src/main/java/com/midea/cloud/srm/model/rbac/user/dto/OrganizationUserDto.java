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
 *  修改日期: 2020/8/29
 *  修改内容:
 * </pre>
 */
@ColumnWidth(20)
@Data
public class OrganizationUserDto implements Serializable {
    private static final long serialVersionUID = 3942781748587236179L;

    // *账号	*业务实体名称	*库存组织名称	*生效日期	失效日期
    /**
     * 账号
     */
    @ExcelProperty( value = "*账号",index = 0)
    private String username;

    /**
     * 业务实体
     */
    @ExcelProperty( value = "*业务实体",index = 1)
    private String orgName;

    /**
     * 库存组织
     */
    @ExcelProperty( value = "*库存组织",index = 2)
    private String invName;

    /**
     * 生效日期
     */
    @ExcelProperty( value = "*生效日期",index = 3)
    private String startDate;

    /**
     * 失效日期
     */
    @ExcelProperty( value = "失效日期",index = 4)
    private String endDate;

    /**
     * 错误信息提示
     */
    @ExcelProperty( value = "错误信息提示",index = 5)
    private String errorMsg;
}
