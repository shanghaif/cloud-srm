package com.midea.cloud.srm.inq.price.service.impl.convertor;

import com.google.common.collect.Lists;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.srm.model.inq.inquiry.entity.Header;
import com.midea.cloud.srm.model.inq.price.dto.ApprovalHeaderDetailResponseDTO;
import com.midea.cloud.srm.model.inq.price.dto.ApprovalItemDetailDTO;
import com.midea.cloud.srm.model.inq.price.dto.ApprovalLadderPriceDTO;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalFile;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalHeader;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalItem;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalLadderPrice;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-9 17:35
 *  修改内容:
 * </pre>
 */
public class ApprovalHeaderDetailConvertor {

    public static ApprovalHeaderDetailResponseDTO convert(ApprovalHeader approvalHeader, Header inquiryHeader,
                                                          List<ApprovalItem> approvalItems, List<ApprovalLadderPrice> ladderPrice,
                                                          List<ApprovalFile> approvalFiles, Map<String, Object> initProcess) {

        ApprovalHeaderDetailResponseDTO responseDTO = buildApprovalDetailHeader(approvalHeader, inquiryHeader);
        responseDTO.setInitWorkFlow(initProcess);
        HashMap<Long, List<ApprovalLadderPriceDTO>> ladderPriceMap = builtQuoteLadderPriceMap(ladderPrice);

        List<ApprovalItemDetailDTO> approvalDetailItems = new ArrayList<>();
        approvalItems.forEach(approvalItem -> {
            ApprovalItemDetailDTO approvalItemDetailDTO = new ApprovalItemDetailDTO();
            BeanUtils.copyProperties(approvalItem, approvalItemDetailDTO);
            if (YesOrNo.YES.getValue().equals(approvalItem.getIsLadder())) {
                approvalItemDetailDTO.setLadderPrices(ladderPriceMap.get(approvalItem.getApprovalItemId()));
            }
            approvalDetailItems.add(approvalItemDetailDTO);
        });

        responseDTO.setApprovalItems(approvalDetailItems);
        responseDTO.setApprovalFiles(approvalFiles);
        return responseDTO;
    }

    /**
     * 构建审批单详情头
     */
    private static ApprovalHeaderDetailResponseDTO buildApprovalDetailHeader(ApprovalHeader approvalHeader, Header inquiryHeader) {
        ApprovalHeaderDetailResponseDTO header = new ApprovalHeaderDetailResponseDTO();
        if (inquiryHeader != null) {
            BeanUtils.copyProperties(inquiryHeader, header);
        }
        BeanUtils.copyProperties(approvalHeader, header);
        header.setAuditStatus(approvalHeader.getStatus());
        return header;
    }

    /**
     * 阶梯价按照审批行组合
     */
    private static HashMap<Long, List<ApprovalLadderPriceDTO>> builtQuoteLadderPriceMap(List<ApprovalLadderPrice> approvalLadderPrices) {
        HashMap<Long, List<ApprovalLadderPriceDTO>> ladderPriceMap = new HashMap<>();

        if (CollectionUtils.isNotEmpty(approvalLadderPrices)) {
            approvalLadderPrices.forEach(approvalLadderPrice -> {
                if (ladderPriceMap.containsKey(approvalLadderPrice.getApprovalItemId())) {
                    ApprovalLadderPriceDTO priceDTO = new ApprovalLadderPriceDTO();
                    BeanUtils.copyProperties(approvalLadderPrice, priceDTO);
                    List<ApprovalLadderPriceDTO> approvalLadderPriceDTOS = ladderPriceMap.get(approvalLadderPrice.getApprovalItemId());
                    approvalLadderPriceDTOS.add(priceDTO);
                    ladderPriceMap.put(approvalLadderPrice.getApprovalItemId(), approvalLadderPriceDTOS);
                }else {
                    ApprovalLadderPriceDTO approvalLadderPriceDTO = new ApprovalLadderPriceDTO();
                    BeanUtils.copyProperties(approvalLadderPrice, approvalLadderPriceDTO);
                    ladderPriceMap.put(approvalLadderPrice.getApprovalItemId(), Lists.newArrayList(approvalLadderPriceDTO));
                }
            });
        }
        return ladderPriceMap;
    }
}