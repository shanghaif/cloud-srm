package com.midea.cloud.srm.model.cm.contract.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 *   合同物料Excel模型
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-6-12 9:49
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class ContractMaterialModel implements Serializable {


    private static final long serialVersionUID = 2295195567257663853L;

    /**
     * 合同行号
     */
    @ExcelProperty( value = "合同行号", index = 0)
    private String lineNumber;

    /**
     * 物料编码
     */
    @ExcelProperty( value = "物料编码", index = 1)
    private String materialCode;

    /**
     * 采购分类编码
     */
    @ExcelProperty( value = "采购分类编码", index = 2)
    private String categoryCode;

    /**
     * 金额
     */
    @ExcelProperty( value = "金额", index = 3)
    private String amount;

    /**
     * 合同数量
     */
    @ExcelProperty( value = "合同数量", index = 4)
    private String contractQuantity;

    /**
     * 订单数量
     */
    @ExcelProperty( value = "订单数量", index = 5)
    private String orderQuantity;

    /**
     * 未税单价
     */
    @ExcelProperty( value = "未税单价", index = 6)
    private String untaxedPrice;

    /**
     * 人工单价
     */
    @ExcelProperty( value = "人工单价", index = 7)
    private String peoplePrice;

    /**
     * 材料单价
     */
    @ExcelProperty( value = "材料单价", index = 8)
    private String materialPrice;

    /**
     * 税率编码
     */
    @ExcelProperty( value = "税率编码", index = 9)
    private String taxKey;

    /**
     * 单位编码
     */
    @ExcelProperty( value = "单位编码", index = 10)
    private String unitCode;


    /**
     * 导入错误信息反馈
     */
    @ExcelProperty( value = "导入错误信息反馈",index = 11)
    private String errorMsg;


}
