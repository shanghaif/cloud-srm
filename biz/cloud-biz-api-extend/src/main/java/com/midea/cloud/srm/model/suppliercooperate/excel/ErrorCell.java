package com.midea.cloud.srm.model.suppliercooperate.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *  报错单元格
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/22 8:38
 *  修改内容:
 * </pre>
 */
@Data
@NoArgsConstructor
public class ErrorCell {
    /**
     * sheet下标
     */
    private Integer sheetIndex;
    /**
     * 报错所在列
     */
    private Integer column;

    /**
     * 行报错报错信息，写在原来最右边列的右边一列
     */
    private String lineErrorContents;
    /**
     * 报错所在行
     */
    private Integer row;
    /**
     * 报错批注内容
     */
    private String comment;

    public ErrorCell(Integer sheetIndex,Integer column,Integer row){
        this.sheetIndex = sheetIndex;
        this.column = column;
        this.row = row;
    }
    public ErrorCell(Integer sheetIndex,Integer column,Integer row,String comment){
        this.sheetIndex = sheetIndex;
        this.column = column;
        this.row = row;
        this.comment = comment;
    }

}
