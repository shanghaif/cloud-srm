package com.midea.cloud.srm.model.rbac.user.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

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
public class UserAuthorityDto {
    /**
     * 账号
     */
    @ExcelProperty( value = "账号",index = 0)
    private String username;

    /**
     * 昵称
     */
    @ExcelProperty( value = "姓名",index = 1)
    private String nickname;

    /**
     * 手机
     */
    @ExcelProperty( value = "手机",index = 2)
    private String phone;

    /**
     * 邮箱
     */
    @ExcelProperty( value = "邮箱",index = 3)
    private String email;

    /**
     * 部门
     */
    @ExcelProperty( value = "部门",index = 4)
    private String department;

    /**
     * 生效日期
     */
    @ExcelProperty( value = "生效日期",index = 5)
    private String startDate;

    /**
     * 失效日期
     */
    @ExcelProperty( value = "失效日期",index = 6)
    private String endDate;

    /**
     * 错误信息提示
     */
    @ExcelProperty( value = "错误信息提示",index = 7)
    private String errorMsg;


}
