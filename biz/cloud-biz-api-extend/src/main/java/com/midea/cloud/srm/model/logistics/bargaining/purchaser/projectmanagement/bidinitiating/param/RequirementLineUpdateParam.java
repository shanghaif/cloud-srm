package com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.bidinitiating.param;

import com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import lombok.Data;


import java.util.List;

/**
 * @author tanjl11
 * @date 2020/12/07 19:07
 */
@Data
public class RequirementLineUpdateParam {
    private Long bidingId;
    private Long purchaseRequestTemplateId;
    private List<BidRequirementLine> requirementLineList;
}
