package com.midea.cloud.srm.model.cm.contract.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.cm.contract.entity.ContractMaterial;
import com.midea.cloud.srm.model.cm.contract.entity.ContractPartner;
import com.midea.cloud.srm.model.cm.contract.entity.PayPlan;
import lombok.Data;
import lombok.experimental.Accessors;

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
 *  修改日期: 2020/10/7 13:51
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class ContractVo extends ContractMaterial {
    /**
     * 币种编码
     */
    private String currencyCode;

    /**
     * 币种ID
     */
    private Long currencyId;

    /**
     * 币种名称
     */
    private String currencyName;

    /**
     * 合同编号
     */
    private String contractCode;

    /**
     * 合同计划
     */
    private List<PayPlan> payPlanList;

    /**
     * 供应商id
     */
    private Long headVendorId;

    /**
     * 供应商名称
     */
    private String headVendorName;

    /**
     * 供应商编码
     */
    private String headVendorCode;

    /**
     * 合同序号
     */
    private String contractNo;

    /**
     *
     */
    private String ceeaControlMethod;

    /**
     * 是否框架协议(Y-是,N-否;默认为否)
     */
    private String isFrameworkAgreement;
    /**
     * 合作伙伴
     */
    List<ContractPartner> contractPartnerList;
}
