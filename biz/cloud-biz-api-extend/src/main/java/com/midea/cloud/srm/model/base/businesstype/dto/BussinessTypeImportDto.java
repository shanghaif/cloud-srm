package com.midea.cloud.srm.model.base.businesstype.dto;

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
 *  修改日期: 2020/10/29
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class BussinessTypeImportDto implements Serializable {

    /**
     * 事业部名称
     */
    @ExcelProperty(value = "*事业部名称", index = 0)
    private String division;

    /**
     * 供应商地点名称
     */
    @ExcelProperty(value = "*供应商地点名称", index = 1)
    private String vendorSiteCode;

    /**
     * 单据类型
     */
    @ExcelProperty(value = "*单据类型", index = 2)
    private String paymentDocumentType;

    /**
     * 业务类型名称
     */
    @ExcelProperty(value = "*业务类型名称", index = 3)
    private String businessTypeName;

    /**
     * 是否代付
     */
    @ExcelProperty(value = "*是否代付(Y/N)", index = 4)
    private String ifPayAgent;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注", index = 5)
    private String remarks;

    /**
     * 错误信息提示
     */
    @ExcelProperty(value = "错误信息提示", index = 6)
    private String errorMsg;

}
