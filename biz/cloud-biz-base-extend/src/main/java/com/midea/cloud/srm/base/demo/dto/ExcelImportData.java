package com.midea.cloud.srm.base.demo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.midea.cloud.srm.model.annonations.ExcelParamCheck;
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
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Data
public class ExcelImportData implements Serializable {

    @ExcelParamCheck(ifRequired = true,errorMsg = "[姓名]不能为空")
    @ExcelProperty(value = "姓名", index = 0 )
    private String name;

    @ExcelProperty(value = "年龄", index = 1 )
    private String age;

    @ExcelParamCheck(dicCode = "LOGISTICS_STATUS",dicErrorMsg = "[状态]字典值不存在")
    @ExcelProperty(value = "状态", index = 2 )
    private String status;

    @ExcelParamCheck(isCurrency = true)
    @ExcelProperty(value = "币种", index = 3 )
    private String currency;

    @ExcelProperty(value = "错误信息提示", index = 4 )
    private String errorMsg;
}
