package com.midea.cloud.srm.model.cm.contract.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <pre>
 *  合同头信息导入模型
 * </pre>
 *
 * @author fansb3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/13 15:56
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class ContractHeadModelDTO implements Serializable {

    @ExcelProperty( value = "合同序列号",index = 0)
    private String contractNo;

    @ExcelProperty( value = "合同编号",index = 1)
    private String contractCode;

    @ExcelProperty( value = "*合同名称",index = 2)
    private String contractName;

    @ExcelProperty( value = "*合同类型",index = 3)
    private String contractClass;

    /*@ExcelProperty( value = "*模板名称",index = 3)
    private String modelName;*/

    @ExcelProperty( value = "*供应商名称",index = 4)
    private String vendorName;

    @ExcelProperty( value = "*供应商编码",index = 5)
    private String vendorCode;

    @ExcelProperty( value = "*有效日期从",index = 6)
    private String effectiveDateFrom;

    @ExcelProperty( value = "*有效日期至",index = 7)
    private String effectiveDateTo;

    @ExcelProperty( value = "*是否标准合同",index = 8)
    private String enable;

    @ExcelProperty( value = "*是否框架协议",index = 9)
    private String isFrameworkAgreement;

  /*  @ExcelProperty( value = "是否电站业务",index = 9)
    private String isPowerStation;*/

    @ExcelProperty( value = "合同级别",index = 10)
    private String contractLevel;

    @ExcelProperty( value = "*送审业务实体名",index = 11)
    private String buName;

    @ExcelProperty( value = "*币种",index = 12)
    private String currencyName;

    @ExcelProperty( value = "合同总额",index = 13)
    private String includeTaxAmount;

    /*@ExcelProperty( value = "是否总部",index = 13)
    private String isHeadquarters;

    @ExcelProperty( value = "备注",index = 14)
    private String contractRemark;

    @ExcelProperty( value = "起草人意见",index = 15)
    private String drafterOpinion;*/

    @ExcelProperty( value = "错误提示信息",index = 14)
    private String errorMessage;
}
