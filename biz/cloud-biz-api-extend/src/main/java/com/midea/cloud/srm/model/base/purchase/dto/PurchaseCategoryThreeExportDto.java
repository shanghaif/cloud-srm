package com.midea.cloud.srm.model.base.purchase.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * <pre>
 * 三级采购分类
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/6
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class PurchaseCategoryThreeExportDto implements PurchaseCategoryDto {
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
     * 分类名称
     */
    @ExcelProperty( value = {"二级","*品类名称"},index = 3)
    private String categoryNameTwo;

    /**
     * 分类编码
     */
    @ExcelProperty( value = {"二级","*品类编码"},index = 4)
    private String categoryCodeTwo;

    /**
     * 是否启用(Y启用 N未启用)
     */
    @ExcelProperty( value = {"二级","*是否启用(Y/N)"},index = 5)
    private String enabledTwo;

    /**
     * 分类名称
     */
    @ExcelProperty( value = {"三级","*品类名称"},index = 6)
    private String categoryNameThree;

    /**
     * 分类编码
     */
    @ExcelProperty( value = {"三级","*品类编码"},index = 7)
    private String categoryCodeThree;
    /**
     * 主材住设备
     */
    @ExcelProperty( value = {"三级","主材主设备"},index = 8)
    private String mainMaterial;

    /**
     * 是否启用(Y启用 N未启用)
     */
    @ExcelProperty( value = {"三级","*是否启用(Y/N)"},index = 9)
    private String enabledThree;

}
