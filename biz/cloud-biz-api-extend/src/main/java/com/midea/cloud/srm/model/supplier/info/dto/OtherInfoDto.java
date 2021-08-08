package com.midea.cloud.srm.model.supplier.info.dto;

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
 *  修改日期: 2020/7/2
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class OtherInfoDto implements Serializable {

    private static final long serialVersionUID = 3100777564843348971L;
    /**
     * 用户名
     */
    @ExcelProperty( value = "用户名",index = 0)
    private String username;

    /**
     * 商业模式
     */
    @ExcelProperty( value = "商业模式",index = 1)
    private String bizModel;

    /**
     * 厂房性质
     */
    @ExcelProperty( value = "厂房性质",index = 2)
    private String factoryType;

    /**
     * 建筑面积(平方米)
     */
    @ExcelProperty( value = "建筑面积(平方米)",index = 3)
    private String floorArea;

    /**
     * 员工人数
     */
    @ExcelProperty( value = "员工人数",index = 4)
    private String employeeQty;

    /**
     * 公司网站
     */
    @ExcelProperty( value = "公司网站",index = 5)
    private String companySite;

    /**
     * 占地面积(平方米)
     */
    @ExcelProperty( value = "占地面积(平方米)",index = 6)
    private String floorSpace;

    /**
     * 错误提示信息
     */
    @ExcelProperty( value = "错误提示信息",index = 7)
    private String errorMsg;
}
