package com.midea.cloud.srm.model.inq.price.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItem;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItemPaymentTerm;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.validation.Valid;
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
 *  修改日期: 2020/9/24 16:34
 *  修改内容:
 * </pre>
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class ApprovalBiddingItemVO extends ApprovalBiddingItem {

    @Valid
    List<ApprovalBiddingItemPaymentTerm> approvalBiddingItemPaymentTermList;

    //用于创建合同时候分组的id
    private String groupId;

    public static ApprovalBiddingItemVO create(ApprovalBiddingItem approvalBiddingItem,
                                               List<ApprovalBiddingItemPaymentTerm> approvalBiddingItemPaymentTermList) {
        ApprovalBiddingItemVO vo = new ApprovalBiddingItemVO();
        BeanUtils.copyProperties(approvalBiddingItem, vo);
        vo.setApprovalBiddingItemPaymentTermList(approvalBiddingItemPaymentTermList);
        return vo;
    }

    /**
     * 是否虚拟合同（N/Y）
     */
    private String ceeaIfVirtual;
}
