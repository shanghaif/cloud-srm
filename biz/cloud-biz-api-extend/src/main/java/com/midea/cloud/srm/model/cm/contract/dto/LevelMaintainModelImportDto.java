package com.midea.cloud.srm.model.cm.contract.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
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
 *  修改日期: 2020/10/6
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class LevelMaintainModelImportDto implements Serializable {

    /**
     * 小类名称
     */
    @ExcelProperty( value = "小类名称",index = 0)
    private String categoryName;

    /**
     * 合同定级限制金额（万元）
     */
    @ExcelProperty( value = "合同定级限制金额（万元）",index = 1)
    private String amount;

    /**
     * 运算关系
     */
    @ExcelProperty( value = "运算关系",index = 2)
    private String operational;

    /**
     * 合同级别
     */
    @ExcelProperty( value = "合同级别",index = 3)
    private String level;

    /**
     * 生效日期
     */
    @ExcelProperty( value = "生效日期",index = 4)
    private String startData;

    /**
     * 失效日期
     */
    @ExcelProperty( value = "失效日期",index = 5)
    private String endData;
}
