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
public class CompanyAddressDto implements Serializable {
    /**
     * 公司名称
     */
    @ExcelProperty(value = "公司名称", index = 0)
    private String companyName;

    /**
     * 国家
     */
    @ExcelProperty(value = "国家", index = 1)
    private String country;

    /**
     * 地区
     */
    @ExcelProperty(value = "地区", index = 2)
    private String area;

    /**
     * 城市
     */
    @ExcelProperty(value = "城市", index = 3)
    private String city;

    /**
     * 详细地址
     */
    @ExcelProperty(value = "详细地址", index = 4)
    private String address;

    /**
     * 邮政编码
     */
    @ExcelProperty(value = "邮政编码", index = 5)
    private String postalCode;

    /**
     * 地址备注
     */
    @ExcelProperty(value = "地址备注", index = 6)
    private String remark;

    /**
     * 是否激活(Y-是,N-否)
     */
    @ExcelProperty(value = "是否启用", index = 7)
    private String isActive;

    /**
     * 错误信息提示
     */
    @ExcelProperty(value = "错误信息提示", index = 8)
    private String errorMsg;

}
