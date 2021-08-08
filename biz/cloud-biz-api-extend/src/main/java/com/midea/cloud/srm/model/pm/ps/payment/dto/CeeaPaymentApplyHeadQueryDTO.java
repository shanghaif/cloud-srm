package com.midea.cloud.srm.model.pm.ps.payment.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj
 *  修改日期: 2020/8/31 19:10
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class CeeaPaymentApplyHeadQueryDTO extends BaseDTO {
    /**
     * 付款申请单号
     */
    private String paymentApplyNumber;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 业务实体id（多选）
     */
    private List<Long> orgIdList;

    /**
     * 供应商编号或名称
     */
    private String vendorKey;

    /**
     * 成本类型名称
     */
    private String costTypeName;

    /**
     * 成本类型编码
     */
    private String costTypeCode;

    /**
     * 单据状态
     */
    private String receiptStatus;

    /**
     * 合同编号
     */
    private String contractNum;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 是否代付
     */
    private String ifPayAgent;

    /**
     * 代付方业务实体（多选）
     */
    private List<Long> payAgentOrgIds;

    /**
     * 是否电站业务
     */
    private String ifPowerStationBusiness;

    /**
     * 项目编号
     */
    private String projectCode;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 供应商Id
     */
    private Long vendorId;

}
