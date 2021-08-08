package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * 项目需求导入模板
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/27
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class BidRequirementLineDto implements Serializable {

    /**
     * ou组编号
     */
    @ExcelProperty( value = "OU组编号",index = 0)
    private String ouCode;

    /**
     * 是否为基准ou
     */
    @ExcelProperty( value = "是否为基准ou(N/Y)",index = 1)
    private String baseOu;

    /**
     * 采购组织
     */
    @ExcelProperty( value = "业务实体名称",index = 2)
    private String orgName;

    /**
     * 库存组织名称
     */
    @ExcelProperty( value = "库存组织名称",index = 3)
    private String  invName;

    /**
     * 交货地点
     */
    @ExcelProperty( value = "交货地点(省/市)",index = 4)
    private String deliveryPlace;

    /**
     * 价格类型
     */
    @ExcelProperty( value = "价格类型",index = 5)
    private String priceType;

    /**
     * 公式值
     */
    @ExcelProperty( value = "公式值",index = 6)
    private String formulaValue;

    /**
     * 采购类型
     */
    @ExcelProperty( value = "采购类型",index = 7)
    private String purchaseType;

    /**
     * 贸易条款
     */
    @ExcelProperty( value = "贸易条款",index = 8)
    private String tradeTerm;

    /**
     * 运输方式
     */
    @ExcelProperty( value = "运输方式",index = 9)
    private String transportType;

    /**
     * 是否显示需求量
     */
    @ExcelProperty( value = "是否显示需求量(N/Y)",index = 10)
    private String showRequireNum;

    /**
     * 保修期(月)
     */
    @ExcelProperty( value = "保质期(月)",index = 11)
    private String warrantyPeriod;

    /**
     * 指定供应商名
     */
    @ExcelProperty( value = "指定供应商名",index = 12)
    private String awardedSupplierName;

    /**
     * 标的编号
     */
    @ExcelProperty( value = "*物料编码",index = 13)
    private String targetNum;

    /**
     * 物料名称
     */
    @ExcelProperty( value = "物料名称",index = 14)
    private String targetDesc;

    /**
     * 单位描述
     */
    @ExcelProperty( value = "单位描述",index = 15)
    private String uomDesc;

    /**
     * 定价开始时间
     */
    @ExcelProperty( value = "定价开始时间",index = 16)
    private String priceStartTime;

    /**
     * 定价结束时间
     */
    @ExcelProperty( value = "定价结束时间",index = 17)
    private String priceEndTime;

    /**
     * 采购数量
     */
    @ExcelProperty( value = "预计采购数量",index = 18)
    private String quantity;

    /**
     * 预计采购金额(万元)
     */
    @ExcelProperty( value = "预计采购金额(万元)",index = 19)
    private String amount;

    /**
     * 备注
     */
    @ExcelProperty( value = "备注",index = 20)
    private String comments;

    /**
     * 需求日期
     */
    @ExcelProperty( value = "需求日期",index = 21)
    private String ceeaDemandDate;

    /**
     * 错误信息提示
     */
    @ExcelProperty( value = "错误信息提示",index = 22)
    private String errorMsg;
}
