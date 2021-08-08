package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo;

import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirement;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  招标立项-项目需求页 视图对象
 * </pre>
 *
 * @author fengdc3@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-20 16:44
 *  修改内容:
 * </pre>
 */
@Data
public class BidRequirementVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private BidRequirement bidRequirement;
    private List<BidRequirementLine> bidRequirementLineList;
}
