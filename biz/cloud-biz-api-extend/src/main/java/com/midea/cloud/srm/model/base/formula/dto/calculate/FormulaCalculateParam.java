package com.midea.cloud.srm.model.base.formula.dto.calculate;

import com.midea.cloud.srm.model.base.formula.vo.BaseMaterialPriceVO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.BidOrderLineFormulaPriceDetail;
import lombok.Data;

import java.util.List;

/**
 * @author tanjl11
 * @date 2020/11/02 16:49
 */
@Data
public class FormulaCalculateParam {
    //公式行列表
    private List<BidOrderLineFormulaPriceDetail> bidDetails;
    private List<com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.BidOrderLineFormulaPriceDetail> brgDetails;
    private List<BaseMaterialPriceVO> prices;
    private Long bidingId;
    private Long vendorId;
    private String sourcingType;
}
