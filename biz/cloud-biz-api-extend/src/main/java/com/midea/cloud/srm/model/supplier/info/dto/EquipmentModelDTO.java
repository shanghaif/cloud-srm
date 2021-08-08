package com.midea.cloud.srm.model.supplier.info.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * <pre>
 *  功能名称 设备导入模型DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/21 17:17
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class EquipmentModelDTO {

    @ExcelProperty(value = "新SRM供应商ID", index = 0)
    private String companyId;

    @ExcelProperty(value = "老SRM供应商编码（C开头的）", index = 1)
    private String companyCode;

    @ExcelProperty(value = "ERP供应商ID", index = 2)
    private String erpVendorId;

    @ExcelProperty(value = "供应商ERP编码", index = 3)
    private String erpVendorCode;

    @ExcelProperty(value = "企业名称", index = 4)
    private String companyName;

    @ExcelProperty(value = "设备类型", index = 5)
    private String equipmentType;

    @ExcelProperty(value = "设备名称", index = 6)
    private String equipmentName;

    @ExcelProperty(value = "规格型号", index = 7)
    private String specification;

    @ExcelProperty(value = "数量", index = 8)
    private String quantity;

    @ExcelProperty(value = "单位设备产能", index = 9)
    private String equipmentCapacity;

    @ExcelProperty(value = "生产厂家", index = 10)
    private String manufacturer;

    @ExcelProperty(value = "已服役年限", index = 11)
    private String serviceYear;

    @ExcelProperty( value = "错误提示信息",index = 12)
    private String errorMessage;
}
