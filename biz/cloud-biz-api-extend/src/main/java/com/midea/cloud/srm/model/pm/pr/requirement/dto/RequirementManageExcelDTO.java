package com.midea.cloud.srm.model.pm.pr.requirement.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.midea.cloud.srm.model.cm.contract.vo.ContractVo;
import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderPaymentProvision;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *   需求池管理DTO
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/7/29 13:43
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class RequirementManageExcelDTO implements Serializable{


    /**
     * 业务实体名称
     */
    @ExcelProperty(value = "业务实体",index = 0)
    private String orgName;

    /**
     * 库存组织名称
     */
    @ExcelProperty(value = "库存组织",index = 1)
    private String organizationName;

    /**
     * 采购需求编号(申请编号)
     */
    @ExcelProperty(value = "采购申请编号",index = 2)
    private String requirementHeadNum;

    /**
     * Erp申请编号
     */
    @ExcelProperty(value = "Erp申请编号",index = 3)
    private String esRequirementHeadNum;

    /**
     * 采购类型
     */
    @ExcelProperty(value = "采购类型",index = 4)
    private String ceeaPurchaseType;
    /**
     * 申请日期
     */
    @ExcelProperty(value = "申请日期",index = 5)
    private String applyDate;

    /**
     * 申请行号
     */
    @ExcelProperty(value = "申请行号",index = 6)
    private Integer rowNum;


    /**
     * 物料小类名称(longi)
     */
    @ExcelProperty(value = "物料小类",index = 7)
    private String categoryName;

    /**
     * 物料编码
     */
    @ExcelProperty(value = "物料编码",index = 8)
    private String materialCode;

    /**
     * 物料名称
     */
    @ExcelProperty(value = "物料名称",index = 9)
    private String materialName;

    /**
     * 需求数量(申请数量)
     */
    @ExcelProperty(value = "需求数量",index = 10)
    private String requirementQuantity;

    /**
     * 可下单数量
     */
    @ExcelProperty(value = "可下单数量",index = 11)
    private String orderQuantity;

    /**
     * 需求日期
     */
    @ExcelProperty(value = "需求日期",index = 12)
    private String requirementDate;

    /**
     * 单位描述
     */
    @ExcelProperty(value = "单位",index = 13)
    private String unit;

    /**
     * 是否目录化(longi)
     */
    @ExcelProperty(value = "是否目录化",index = 14)
    private String ceeaIfDirectory;

    /**
     * 货源供应商 Y:有,N:无
     */
    @ExcelProperty(value = "货源供应商",index = 15)
    private String haveSupplier;

    /**
     * 有效价格 Y:有,N:无
     */
    @ExcelProperty(value = "有效价格",index = 16)
    private String haveEffectivePrice;

    /**
     * 申请状态
     */
    @ExcelProperty(value = "申请状态",index = 17)
    private String applyStatus;

    /**
     * 策略负责采购员名称
     */
    @ExcelProperty(value = "策略负责采购员",index = 18)
    private String ceeaStrategyUserNickname;
    /**
     * 采购履行采购员名称
     */
    @ExcelProperty(value = "采购履行采购员",index = 19)
    private String ceeaPerformUserNickname;

    /**
     * 驳回原因
     */
    @ExcelProperty(value = "退回原因",index = 20)
    private String rejectReason;

    /**
     * 部门名称
     */
    @ExcelProperty(value = "申请人部门",index = 21)
    private String ceeaDepartmentName;


    /**
     * 申请人姓名(创建人姓名)
     */
    @ExcelProperty(value = "申请人",index = 22)
    private String createdFullName;

    /**
     * 需求来源
     */
    @ExcelProperty(value = "需求来源",index = 23)
    private String requirementSource;

    /**
     * 需求人列
     */
    @ExcelProperty(value = "需求人",index = 24)
    private String dmandLineRequest;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注",index = 25)
    private String comments;

    /**
     * 备注
     */
    @ExcelProperty(value = "是否创建订单",index = 26)
    private String ifCreateOrder;
    /**
     * 备注
     */
    @ExcelProperty(value = "是否创建寻源",index = 27)
    private String ifCreateBid;
    /**
     * 备注
     */
    @ExcelProperty(value = "是否暂挂",index = 28)
    private String ifHold;
    /**
     * 备注
     */
    @ExcelProperty(value = "退回操作人",index = 29)
    private String returnOperator;


}
