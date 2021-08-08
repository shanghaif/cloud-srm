package com.midea.cloud.srm.model.base.purchase.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * <pre>
 * 一级品类导入
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/5
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class PurchaseCategoryOneDto implements PurchaseCategoryDto {

    /**
     * 分类名称
     */
    @ExcelProperty( value = {"一级","*品类名称"},index = 0)
    private String categoryNameOne;

    /**
     * 分类编码
     */
    @ExcelProperty( value = {"一级","*品类编码"},index = 1)
    private String categoryCodeOne;

    /**
     * 是否启用(Y启用 N未启用)
     */
    @ExcelProperty( value = {"一级","*是否启用(Y/N)"},index = 2)
    private String enabledOne;

    /**
     * 错误提示信息
     */
    @ExcelProperty( value = "错误提示信息",index = 3)
    private String errorMsg;

}
