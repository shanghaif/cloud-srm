package com.midea.cloud.srm.model.cm.contract.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *  合同合作伙伴导入模型
 * </pre>
 *
 * @author fansb3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/13 15:58
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class ContractPartnerModelDTO implements Serializable {

    @ExcelProperty( value = "合同序列号",index = 0)
    private String contractNo;

    @ExcelProperty( value = "合同编号",index = 1)
    private String contractCode;

    @ExcelProperty( value = "*合同名称",index = 2)
    private String contractName;

    @ExcelProperty( value = "*伙伴类型",index = 3)
    private String partnerType;

    @ExcelProperty( value = "*伙伴名称",index = 4)
    private String partnerName;

    @ExcelProperty( value = "错误提示信息",index = 5)
    private String errorMessage;

}
