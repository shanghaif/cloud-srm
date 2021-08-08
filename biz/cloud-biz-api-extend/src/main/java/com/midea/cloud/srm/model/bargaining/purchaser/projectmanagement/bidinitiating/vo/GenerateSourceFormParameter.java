package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.vo;

import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirement;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.enums.SourceFrom;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RequirementLineVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 入参 - 寻源单生成
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenerateSourceFormParameter implements Serializable {

    private SourceForm  sourceForm;

    /**
     * 创建 寻源单生成入参
     *
     * @param requirementLines  采购需求[行]
     * @return A new {@link GenerateSourceFormParameter}.
     */
    public static GenerateSourceFormParameter create(Collection<RequirementLineVO> requirementLines) {

        Biding bidding = Biding.builder()
                .sourceFrom(SourceFrom.PURCHASE_REQUEST.getItemValue())
                .monthlyInterest(BigDecimal.valueOf(0.0435))
                .quotaDistributeType("NULL_ALLOCATION")
                .bondAmount(BigDecimal.ZERO)
                .standardCurrency("CNY")
                .pricePrecision(4)
                .exchangeRateType("COMPANY")
                .showRateType("N")
                .currencyChangeDate(new Date())
                .build();
        BidRequirement demandHeader = BidRequirement.builder()
                .build();
        List<BidRequirementLine> demandLines = requirementLines.stream()
                .map(requirementLine -> BidRequirementLine.builder()
                        .targetId(requirementLine.getMaterialId())
                        .targetNum(requirementLine.getMaterialCode())
                        .targetDesc(requirementLine.getMaterialName())
                        .categoryId(requirementLine.getCategoryId())
                        .categoryCode(requirementLine.getCategoryCode())
                        .categoryName(requirementLine.getCategoryName())
                        .uomCode(requirementLine.getUnitCode())
                        .uomDesc(requirementLine.getUnit())
                        .quantity(requirementLine.getRequirementQuantity().doubleValue())
                        .purchaseType(requirementLine.getPurchaseType())
                        .purchaseRequestNum(requirementLine.getRequirementHeadNum())
                        .purchaseRequestRowNum(String.valueOf(requirementLine.getRowNum()))
                        .fullPathId(requirementLine.getFullPathId())
                        .orgId(requirementLine.getOrgId())
                        .orgCode(requirementLine.getOrgCode())
                        .orgName(requirementLine.getOrgName())
                        .invId(requirementLine.getOrganizationId())
                        .invCode(requirementLine.getOrganizationCode())
                        .invName(requirementLine.getOrganizationName())
                        .awardedSupplierId(requirementLine.getVendorId())
                        .awardedSupplierName(requirementLine.getVendorName())
                        .deliveryPlace(requirementLine.getCeeaDeliveryPlace())
                        .ceeaDemandDate(requirementLine.getRequirementDate())
                        .amount(requirementLine.getCeeaTotalBudget())
                        .build()
                )
                .collect(Collectors.toList());

        return GenerateSourceFormParameter.builder()
                .sourceForm(SourceForm.builder()
                        .bidding(bidding)
                        .demandHeader(demandHeader)
                        .demandLines(demandLines)
                        .build()
                )
                .build();
    }
}
