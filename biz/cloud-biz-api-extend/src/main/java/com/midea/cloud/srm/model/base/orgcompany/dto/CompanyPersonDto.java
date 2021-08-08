package com.midea.cloud.srm.model.base.orgcompany.dto;

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
 *  修改日期: 2020/10/23
 *  修改内容:
 * </pre>
 */
@ColumnWidth(20)
@Data
public class CompanyPersonDto implements Serializable {
    private static final long serialVersionUID = 6485313531259927923L;
    /**
     * 公司名称
     */
    @ExcelProperty(value = "公司名称", index = 0)
    private String companyName;

    /**
     * 税号
     */
    @ExcelProperty(value = "税号", index = 1)
    private String taxNumber;

    /**
     * 联系人姓名
     */
    @ExcelProperty(value = "联系人姓名", index = 2)
    private String name;

    /**
     * 性别(1-男,0-女)
     */
    @ExcelProperty(value = "联系人姓名", index = 3)
    private String sex;

    /**
     * 部门
     */
    @ExcelProperty(value = "部门", index = 4)
    private String department;

    /**
     * 职位
     */
    @ExcelProperty(value = "职位", index = 5)
    private String position;

    /**
     * 联系电话
     */
    @ExcelProperty(value = "联系电话", index = 6)
    private String phone;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱", index = 7)
    private String email;

    /**
     * 是否默认联系人,只能有一个默认(Y-是,N-否)
     */
    @ExcelProperty(value = "默认联系人", index = 8)
    private String isDefault;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注", index = 9)
    private String remark;

    /**
     * 是否激活(Y-是,N-否)
     */
    @ExcelProperty(value = "是否启用", index = 10)
    private String isActive;

    /**
     * 错误信息提示
     */
    @ExcelProperty(value = "错误信息提示", index = 11)
    private String errorMsg;
}
