package com.midea.cloud.srm.model.base.material.dto;

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
 *  修改日期: 2020/11/3
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class MaterialItemSecImportDto implements Serializable {

    /**
     * 物料编码
     */
    @ExcelProperty(value = "*物料编码", index = 0)
    private String materialCode;

    /**
     * 物料名称
     */
    @ExcelProperty(value = "*物料描述", index = 1)
    private String materialName;

    /**
     * 供应商编码
     */
    @ExcelProperty(value = "*供应商编码", index = 2)
    private String ceeaSupplierCode;

    /**
     * 规格型号
     */
    @ExcelProperty(value = "*规格/型号", index = 3)
    private String specification;

    /**
     * 最小起订量
     */
    @ExcelProperty(value = "最小起订量", index = 4)
    private String ceeaOrderQuantityMinimum;

    /**
     * 送货周期
     */
    @ExcelProperty(value = "*送货周期", index = 5)
    private String ceeaDeliveryCycle;

    /**
     * 重量
     */
    @ExcelProperty(value = "重量", index = 6)
    private String ceeaWeight;

    /**
     * 尺寸
     */
    @ExcelProperty(value = "尺寸", index = 7)
    private String ceeaSize;

    /**
     * 品牌
     */
    @ExcelProperty(value = "品牌", index = 8)
    private String ceeaBrand;

    /**
     * 颜色
     */
    @ExcelProperty(value = "颜色", index = 9)
    private String ceeaColor;

    /**
     * 联系人姓名
     */
    @ExcelProperty(value = "供应商联系人1", index = 10)
    private String ceeaNames;

    /**
     * 联系人电话
     */
    @ExcelProperty(value = "电话1", index = 11)
    private String ceeaTelephones;

    /**
     * 联系人邮箱
     */
    @ExcelProperty(value = "邮箱1", index = 12)
    private String ceeaEmails;

    /**
     * 第二联系人姓名
     */
    @ExcelProperty(value = "供应商联系人2", index = 13)
    private String ceeaSecondNames;

    /**
     * 第二联系人电话
     */
    @ExcelProperty(value = "电话2", index = 14)
    private String ceeaSecondTelephones;

    /**
     * 第二联系人邮箱
     */
    @ExcelProperty(value = "邮箱2", index = 15)
    private String ceeaSecondEmails;

    /**
     * 材质
     */
    @ExcelProperty(value = "备用材质", index = 16)
    private String ceeaTexture;

    /**
     * 使用用途
     */
    @ExcelProperty(value = "备件使用用途", index = 17)
    private String ceeaUsage;

    /**
     * 错误信息提示
     */
    @ExcelProperty(value = "错误信息提示", index = 18)
    private String errorMsg;
}
