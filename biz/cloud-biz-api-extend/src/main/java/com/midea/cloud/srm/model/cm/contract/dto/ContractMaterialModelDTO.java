package com.midea.cloud.srm.model.cm.contract.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * 合同物料明细导入模型
 * </pre>
 *
 * @author fansb3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/13 15:57
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class ContractMaterialModelDTO implements Serializable {

    @ExcelProperty( value = "合同序列号",index = 0)
    private String contractNo;

    @ExcelProperty( value = "合同编号",index = 1)
    private String contractCode;

    @ExcelProperty( value = "*合同名称",index = 2)
    private String contractName;

    @ExcelProperty( value = "*业务实体名称",index = 3)
    private String buName;

    @ExcelProperty( value = "*库存组织",index = 4)
    private String invName;

    @ExcelProperty( value = "交货地点",index = 5)
    private String tradingLocations;

    @ExcelProperty( value = "*物料编码",index = 6)
    private String materialCode;

    @ExcelProperty( value = "*物料名称",index = 7)
    private String materialName;

    @ExcelProperty( value = "数量",index = 8)
    private String contractQuantity;

    @ExcelProperty( value = "*含税单价",index = 9)
    private String taxedPrice;

    @ExcelProperty( value = "*税率编码",index = 10)
    private String taxKey;

    @ExcelProperty( value = "*税率",index = 11)
    private String taxRate;

    @ExcelProperty( value = "*价格有效期从",index = 12)
    private String startDate;

    @ExcelProperty( value = "*价格有效期至",index = 13)
    private String endDate;

    @ExcelProperty( value = "已下单数量",index = 14)
    private String OrderQuantity;

    @ExcelProperty( value = "错误提示信息",index = 15)
    private String errorMessage;


}
