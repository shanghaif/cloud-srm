package com.midea.cloud.srm.model.logistics.base.formula.dto.calculate;

import com.midea.cloud.srm.model.base.formula.vo.BaseMaterialPriceVO;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.BidOrderLineFormulaPriceDetail;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Data
public class FormulaCalculateParam {
    //公式行列表
    private List<BidOrderLineFormulaPriceDetail> bidDetails;
    private List<BidOrderLineFormulaPriceDetail> brgDetails;
    private List<BaseMaterialPriceVO> prices;
    private Long bidingId;
    private Long vendorId;
    private String sourcingType;
}
