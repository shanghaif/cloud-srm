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
 *  修改日期: 2020/11/1
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class OrgCategoryImportDro implements Serializable {

    /**
     * 序号
     */
    @ExcelProperty( value = "序号",index = 0)
    private String no;

    /**
     * SRMID
     */
    @ExcelProperty( value = "SRMID",index = 1)
    private String vendorId;

    /**
     * ERP
     */
    @ExcelProperty( value = "ERP",index = 2)
    private String erp;

    /**
     * 供应商名称
     */
    @ExcelProperty( value = "供应商名称",index = 3)
    private String companyName;

    /**
     * 中类
     */
    @ExcelProperty( value = "中类",index = 4)
    private String categoryName2;

    /**
     * 小类
     */
    @ExcelProperty( value = "小类",index = 5)
    private String categoryName3;

    /**
     * 小类编码
     */
    @ExcelProperty( value = "小类编码",index = 6)
    private String categoryCode3;

    /**
     * 供应商性质
     */
    @ExcelProperty( value = "供应商性质",index = 7)
    private String vendorNature;

    /**
     * 未注册供应商生命周期
     */
    @ExcelProperty( value = "未注册供应商生命周期",index = 8)
    private String theLifeCycle;

    /**
     * 状态
     */
    @ExcelProperty( value = "状态",index = 9)
    private String serviceStatus;

    /**
     * 业务实体
     */
    @ExcelProperty( value = "业务实体",index = 10)
    private String orgName;

    /**
     * 错误提示信息
     */
    @ExcelProperty( value = "错误提示信息",index = 11)
    private String errorMsg;
}
