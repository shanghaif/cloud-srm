package com.midea.cloud.srm.model.logistics.bid.dto;

import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidcontrol.vo.BidControlTopInfoVO;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtVendorQuotedHead;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  物流招标头表控制
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
public class LgtVendorQuotedHeadVo implements Serializable {
    /**
     * 供应商报价头
     */
    private List<LgtVendorQuotedHead> lgtVendorQuotedHeads;

    /**
     * 投标控制页 视图对象
     */
    private BidControlTopInfoVO bidControlTopInfoVO;
}
