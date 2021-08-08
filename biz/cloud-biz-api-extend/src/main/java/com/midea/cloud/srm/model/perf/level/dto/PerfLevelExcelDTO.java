package com.midea.cloud.srm.model.perf.level.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
*  <pre>
 *  绩效等级表Excel模板DTO
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-03
 *  修改内容:
 * </pre>等级名称	等级说明	综合得分开始	综合得分结束	是否有效
*/
@Data
public class PerfLevelExcelDTO implements Serializable {

    private static final long serialVersionUID = -8779391988600873915L;

    @ExcelProperty(value = "等级名称", index = 0)
    private String levelName;

    @ExcelProperty(value="等级说明", index =1)
    private String levelDescription;

    @ExcelProperty(value="综合得分开始", index =2)
    private BigDecimal scoreStart;

    @ExcelProperty(value="综合得分结束", index =3)
    private BigDecimal scoreEnd;

    @ExcelProperty(value="是否有效", index =4)
    private String status;

    @ExcelProperty(value="采购组织名称", index =5)
    private String organizationName;

}
