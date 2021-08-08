package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.vo;

import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.BidOrderLineTemplatePriceDetail;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.BidOrderLineTemplatePriceDetailLine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private BidOrderLineTemplatePriceDetail templatePriceDetail;
    private List<BidOrderLineTemplatePriceDetailLine>   templatePriceDetailLines;
}
