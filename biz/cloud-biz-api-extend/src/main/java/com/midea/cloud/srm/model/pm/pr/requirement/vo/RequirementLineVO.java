package com.midea.cloud.srm.model.pm.pr.requirement.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import com.midea.cloud.srm.model.cm.contract.vo.ContractVo;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderPaymentProvision;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import sun.rmi.runtime.Log;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/9/17 11:32
 *  修改内容:
 * </pre>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class RequirementLineVO extends RequirementLine {
    /**
     * 未税单价
     */
    private BigDecimal noTaxPrice;

    /**
     * 含税单价
     */
    private BigDecimal taxPrice;

    /**
     * 采购类型
     */
    private String purchaseType;

    /**
     * 后续单据ID
     */
    private Long followFormId;

    /**
     * 后续单据编号
     */
    private String followFormCode;

    /**
     * 合同list
     */
    private List<ContractVo> contractVoList;

    /**
     * 合同编号
     */
    private String contractCode;

    /**
     * 物料大类id
     */
    private Long bigCategoryId;

    /**
     * 物料大类编码
     */
    private String bigCategoryCode;

    /**
     * 物料大类名称
     */
    private String bigCategoryName;

    /**
     * 可用税率
     */
    private List<PurchaseTax> purchaseTaxList;

    /**
     * 对应的价格
     */
    private PriceLibrary priceLibrary;

    /**
     * 付款条款(付款条件，付款方式，付款账期)
     */
    private List<OrderPaymentProvision> orderPaymentProvisionList;

    /**
     * 需求日期
     */
    private LocalDate requirementDate;

    /**
     * 预算总金额
     */
    private BigDecimal ceeaTotalBudget;


}
