package com.midea.cloud.srm.model.base.questionairesurvey.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
 * <pre>
 *  问卷调查 excel导出模型
 * </pre>
 *
 * @author yancj@1.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 14, 2021 5:07:37 PM
 *  修改内容:
 * </pre>
*/

@Data
@ColumnWidth(15) //列宽
public class ExcelSurveyScopeVendorDto {
    private static final long serialVersionUID = 703287L;

    @ExcelProperty( value = "*供应商编码", index = 0)
    private String vendorCode;

    @ExcelProperty( value = "供应商名称", index = 1)
    private String vendorName;

}