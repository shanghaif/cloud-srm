package com.midea.cloud.srm.model.pm.ps.payment.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
*  <pre>
 *  付款申请-头表（隆基新增） 模型
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-25 21:04:34
 *  修改内容:
 * </pre>
*/
@Data
@ColumnWidth(20)
public class CeeaPaymentApplyHeadExcelDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 付款申请单号
     */
    @ExcelProperty(value = "付款申请单号",index = 0)
    private String paymentApplyNumber;


    /**
     * 费控回写单号
     */
    @ExcelProperty(value = "费控单据编号",index = 1)
    private String boeNo;

    /**
     * 申请日期
     */
    @ExcelProperty(value = "申请日期",index = 2)
    private String applyDate;

    /**
     * 单据状态
     */
    @ExcelProperty(value = "单据状态",index = 3)
    private String receiptStatus;

    /**
     * 业务实体名称
     */
    @ExcelProperty(value = "业务实体",index = 4)
    private String orgName;


    /**
     * 供应商编号
     */
    @ExcelProperty(value = "供应商编号",index = 5)
    private String vendorCode;

    /**
     * 供应商名称
     */
    @ExcelProperty(value = "供应商名称",index = 6)
    private String vendorName;


    /**
     * 成本类型名称
     */
    @ExcelProperty(value = "成本类型名称",index = 7)
    private String costTypeName;


    /**
     * 业务类型
     */
    @ExcelProperty(value = "业务类型",index = 8)
    private String businessType;

    /**
     * 是否代付
     */
    @ExcelProperty(value = "是否代付",index = 9)
    private String ifPayAgent;

    /**
     * 代付方业务实体名称
     */
    @ExcelProperty(value = "代付方业务实体名称",index = 10)
    private String payAgentOrgName;


    /**
     * 币种名称
     */
    @ExcelProperty(value = "币种名称",index = 11)
    private String currencyName;

    /**
     * 汇率
     */
    @ExcelProperty(value = "汇率",index = 12)
    private String gidailyRate;

    /**
     * 项目编号
     */
    @ExcelProperty(value = "项目编号",index = 13)
    private String projectCode;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称",index = 14)
    private String projectName;

    /**
     * 任务编号
     */
    @ExcelProperty(value = "任务编号",index = 15)
    private String taskCode;

    /**
     * 任务名称
     */
    @ExcelProperty(value = "任务名称",index = 16)
    private String taskName;

    /**
     * 是否电站业务
     */
    @ExcelProperty(value = "是否电站业务",index = 17)
    private String ifPowerStationBusiness;

    /**
     * 申请人用户名称
     */
    @ExcelProperty(value = "申请人用户名称",index = 18)
    private String applyUserNickname;


    /**
     * 申请部门名称
     */
    @ExcelProperty(value = "申请部门名称",index = 19)
    private String applyDeptName;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注",index = 20)
    private String comments;

    /**
     * 发票总额
     */
    @ExcelProperty(value = "发票总额",index = 21)
    private BigDecimal invoiceTotalAmount;

    /**
     * 申请付款总额
     */
    @ExcelProperty(value = "申请付款总额", index = 22)
    private BigDecimal applyPaymentTotalAmount;

}
