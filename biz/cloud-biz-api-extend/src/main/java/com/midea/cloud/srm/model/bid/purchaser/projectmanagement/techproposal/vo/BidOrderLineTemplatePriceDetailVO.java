package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.vo;

import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.BidOrderLineTemplatePriceDetail;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.BidOrderLineTemplatePriceDetailLine;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * VO for {@link BidOrderLineTemplatePriceDetail} and {@link BidOrderLineTemplatePriceDetailLine}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BidOrderLineTemplatePriceDetailVO implements Serializable {

    private BidOrderLineTemplatePriceDetail             templatePriceDetail;
    private List<BidOrderLineTemplatePriceDetailLine>   templatePriceDetailLines;
}
