package com.midea.cloud.srm.model.logistics.bid.dto;

import com.midea.cloud.srm.model.logistics.bid.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class LgtVendorQuotedHeadDto implements Serializable {
    /**
     * 项目头信息
     */
    private LgtBiding lgtBiding;
    /**
     * 供应商报价投
     */
    private LgtVendorQuotedHead lgtVendorQuotedHead;
    /**
     * 报价详情行
     */
    private List<LgtVendorQuotedLine> lgtVendorQuotedLines;

    /**
     * 船期信息
     */
    private List<LgtBidShipPeriod> lgtBidShipPeriods;

    /**
     * 供方必须上传附件-
     */
    private List<LgtFileConfig> lgtFileConfigs;
}
