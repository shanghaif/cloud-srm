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
public class OrgCategoryDto implements Serializable {

    private static final long serialVersionUID = 675245321622729565L;
    /**
     * 用户名
     */
    @ExcelProperty( value = "用户名",index = 0)
    private String username;

    /**
     * 合作组织
     */
    @ExcelProperty( value = "组织全路径",index = 1)
    private String orgName;

    /**
     * 采购品类
     */
    @ExcelProperty( value = "采购品类",index = 2)
    private String categoryName;

    /**
     * 品类状态
     */
    @ExcelProperty( value = "品类状态",index = 3)
    private String serviceStatus;

    /**
     * 生效日期
     */
    @ExcelProperty( value = "生效日期",index = 4)
    private String startDate;

    /**
     * 失效日期
     */
    @ExcelProperty( value = "失效日期",index = 5)
    private String endDate;

    /**
     * 错误提示信息
     */
    @ExcelProperty( value = "错误提示信息",index = 6)
    private String errorMsg;


}
