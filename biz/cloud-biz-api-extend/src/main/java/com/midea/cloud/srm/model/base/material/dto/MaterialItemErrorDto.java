package com.midea.cloud.srm.model.base.material.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author wuhx29@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/24 0:58
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(60)
@HeadRowHeight(-10)
public class MaterialItemErrorDto implements Serializable {

    @ExcelProperty(value = "第几条物料数据", index = 0)
    private Integer lineNumber;

    @ExcelProperty(value = "错误信息", index = 1)
    private String errorMessage;
}

