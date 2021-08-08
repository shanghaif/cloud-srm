package com.midea.cloud.srm.model.base.material.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 *  物料小类业务小类导入模型
 * </pre>
 *
 * @author fansb3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/29 14:08
 *  修改内容:
 * </pre>
 */
@ColumnWidth(20)
@Data
public class CategoryBusinessModelDto implements Serializable {
    private static final long serialVersionUID = 7254793873164021634L;

    @ExcelProperty(
            value = {"*物料小类名称"},
            index = 0
    )
    private String categoryName;
    @ExcelProperty(
            value = {"*业务小类编码"},
            index = 1
    )
    private String businessLittleType;
    @ExcelProperty(
            value = {"业务小类名称"},
            index = 2
    )
    private String businessLittleTypeName;
    @ExcelProperty(
            value = {"错误信息提示"},
            index = 3
    )
    private String errorMsg;
}
