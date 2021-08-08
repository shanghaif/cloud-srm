package com.midea.cloud.srm.model.base.material;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/12/11 10:24
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class MaterialItemChartExport {
    /**
     * 物料名称
     */
    @ExcelProperty(value = "物料名称", index = 0 )
    private String materialName;

    /**
     * 物料编码
     */
    @ExcelProperty(value = "物料编码", index = 1 )
    private String materialCode;

    /**
     * 单位
     */
    @ExcelProperty(value = "单位", index = 2 )
    private String unit;

    /**
     * 物料大类名称
     */
    @ExcelProperty(value = "物料大类名称", index = 3 )
    private String bigCategoryName;

    /**
     * 物料中类名称
     */
    @ExcelProperty(value = "物料中类名称", index = 4 )
    private String middleCategoryName;

    /**
     * 物料小类名称
     */
    @ExcelProperty(value = "物料小类名称", index = 5 )
    private String categoryName;

    /**
     * 组织序号
     */
    @ExcelProperty(value = "组织序号", index = 6 )
    private Integer organizatonIndex;

    /**
     * 所属组织
     */
    @ExcelProperty(value = "所属组织", index = 7 )
    private String organizationName;

    /**
     * 是否用于采购
     */
    @ExcelProperty(value = "是否用于采购", index = 8 )
    private String userPurchase;

    /**
     * 是否用于库存
     */
    @ExcelProperty(value = "是否用于库存", index = 9 )
    private String stockEnableFlag;

    /**
     * 库存组织是否启用
     */
    @ExcelProperty(value = "库存组织是否启用", index = 10 )
    private String itemStatus;


    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 11 )
    private Date creationDate;

}
