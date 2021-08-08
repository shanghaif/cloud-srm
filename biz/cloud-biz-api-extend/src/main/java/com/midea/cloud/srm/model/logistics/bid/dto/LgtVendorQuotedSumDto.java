package com.midea.cloud.srm.model.logistics.bid.dto;

import com.midea.cloud.srm.model.logistics.bid.entity.LgtVendorQuotedLine;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtVendorQuotedSum;
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
public class LgtVendorQuotedSumDto implements Serializable {
    /**
     * 报价汇总明细
     */
    private List<LgtVendorQuotedSum> lgtVendorQuotedSums;
    /**
     * 供应商报价明细
     */
    private List<LgtVendorQuotedLine> lgtVendorQuotedLines;

    /**
     * 技术评选结论
     */
    private String technoSelection;
}
