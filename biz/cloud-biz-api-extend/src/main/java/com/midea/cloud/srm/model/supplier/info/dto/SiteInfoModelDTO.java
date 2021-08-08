package com.midea.cloud.srm.model.supplier.info.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <pre>
 *  功能名称 地点信息导入模型DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/21 16:16
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class SiteInfoModelDTO implements Serializable {

    @ExcelProperty(value = "新SRM供应商ID", index = 0)
    @NotEmpty(message = "新SRM供应商ID不能为空")
    private String companyId;

    @ExcelProperty(value = "老SRM供应商编码（C开头的）", index = 1)
    private String companyCode;

    @ExcelProperty(value = "ERP供应商ID", index = 2)
    private String erpVendorId;

    @ExcelProperty(value = "供应商ERP编码", index = 3)
    private String erpVendorCode;

    @ExcelProperty(value = "企业名称", index = 4)
    private String companyName;

    @ExcelProperty(value = "业务实体", index = 5)
    @NotEmpty(message = "业务实体不能为空")
    private String orgName;

    @ExcelProperty(value = "地点名称", index = 6)
    @NotEmpty(message = "地点名称不能为空")
    private String vendorSiteCode;

    @ExcelProperty(value = "国家", index = 7)
    private String country;

    @ExcelProperty(value = "省份/州", index = 8)
    private String province;

    @ExcelProperty(value = "城市", index = 9)
    private String city;

    @ExcelProperty(value = "详细地址", index = 10)
    private String addressDetail;

    @ExcelProperty(value = "邮政编码", index = 11)
    private String postCode;

    @ExcelProperty(value = "地址备注", index = 12)
    private String siteComment;

    @ExcelProperty(value = "启用", index = 13)
    private String enabledFlag;

    @ExcelProperty(value = "可采购", index = 14)
    private String purchaseFlag;

    @ExcelProperty(value = "可付款", index = 15)
    private String paymentFlag;

    @ExcelProperty(value = "仅限于询价", index = 16)
    private String rfqOnlyFlag;

    @ExcelProperty(value = "ERP地点ID", index = 17)
    private String vendorSiteId;

    @ExcelProperty( value = "错误提示信息",index = 18)
    private String errorMessage;
}
