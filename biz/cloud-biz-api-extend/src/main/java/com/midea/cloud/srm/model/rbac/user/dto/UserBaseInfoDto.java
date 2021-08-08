package com.midea.cloud.srm.model.rbac.user.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class UserBaseInfoDto {

    /**
     * 账号
     */
    @ExcelProperty( value = "*账号",index = 0)
    private String username;

    /**
     * 昵称
     */
    @ExcelProperty( value = "*姓名",index = 1)
    private String nickname;

    /**
     * 手机
     */
    @ExcelProperty( value = "手机号",index = 2)
    private String phone;

    /**
     * 邮箱
     */
    @ExcelProperty( value = "*邮箱",index = 3)
    private String email;

    /**
     * 公司名称
     */
    @ExcelProperty( value = "*公司名称",index = 4)
    private String ceeaCompany;

    /**
     * 部门
     */
    @ExcelProperty( value = "*部门名称",index = 5)
    private String department;

    /**
     * 员工工号
     */
    @ExcelProperty( value = "*员工工号",index = 6)
    private String ceeaEmpNo;

    /**岗位代码描述 */
    @ExcelProperty( value = "岗位名称",index = 7)
    private String ceeaJobcodeDescr;

    /**
     * 生效日期
     */
    @ExcelProperty( value = "*生效日期",index = 8)
    private String startDate;

    /**
     * 失效日期
     */
    @ExcelProperty( value = "失效日期",index = 9)
    private String endDate;

    /**
     * erp采购员工号
     */
    @ExcelProperty( value = "erp采购员工号",index = 10)
    private String ceeaPoAgentNumber;

    /**
     * 错误信息提示
     */
    @ExcelProperty( value = "错误信息提示",index = 11)
    private String errorMsg;


}
