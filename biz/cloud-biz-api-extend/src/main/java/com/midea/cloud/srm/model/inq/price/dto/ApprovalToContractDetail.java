package com.midea.cloud.srm.model.inq.price.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalBiddingItemVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tanjl11
 * @date 2020/10/13 9:55
 * 价格审批转合同创建头
 */
@Data
public class ApprovalToContractDetail {

    /**
     * 价格审批头信息
     */
    private Long approvalHeaderId;
    private String approvalNo;
    /**
     * 供应商id
     */
    private Long vendorId;

    /**
     * 来源类型
     */
    private String sourceType;
    /**
     * 中标总金额
     */
    private BigDecimal bidAmount;
    /**
     * 价格行信息
     */
    List<ApprovalBiddingItemVO> contractLineList;

    /**
     * 是否虚拟合同（N/Y）
     */
    private String ceeaIfVirtual;
}
