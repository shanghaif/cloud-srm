package com.midea.cloud.srm.model.logistics.bid.vo;

import com.midea.cloud.srm.model.logistics.bid.entity.LgtBidVendor;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtPayPlan;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtQuoteAuthorize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  邀请供应商 模型
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2021/1/13 14:27
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LgtVendorVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //邀请供应商
    private LgtBidVendor bidVendor;
    //报价权限
    private List<LgtQuoteAuthorize> quoteAuthorizeList;
    //付款信息表
    private List<LgtPayPlan> payPlanList;

}
