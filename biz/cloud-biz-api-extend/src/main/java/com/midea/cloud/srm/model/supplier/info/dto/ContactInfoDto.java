package com.midea.cloud.srm.model.supplier.info.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * 联系人信息(供应商导入)
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/2
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class ContactInfoDto implements Serializable {

    private static final long serialVersionUID = 3125428885820473576L;
    /**
     * 用户名
     */
    @ExcelProperty( value = "用户名",index = 0)
    private String username;

    /**
     * 联系人姓名
     */
    @ExcelProperty( value = "联系人姓名",index = 1)
    private String contactName;

    /**
     * 手机号码
     */
    @ExcelProperty( value = "手机号码",index = 2)
    private String mobileNumber;

    /**
     * 座机号码
     */
    @ExcelProperty( value = "座机号码",index = 3)
    private String phoneNumber;

    /**
     * 邮箱
     */
    @ExcelProperty( value = "邮箱",index = 4)
    private String email;

    /**
     * 联系人地址
     */
    @ExcelProperty( value = "联系人地址",index = 5)
    private String contactAddress;

    /**
     * 人员职务
     */
    @ExcelProperty( value = "人员职务",index = 6)
    private String position;

    /**
     * 传真号码
     */
    @ExcelProperty( value = "传真号码",index = 7)
    private String taxNumber;

    /**
     * 错误提示信息
     */
    @ExcelProperty( value = "错误提示信息",index = 8)
    private String errorMsg;

}
