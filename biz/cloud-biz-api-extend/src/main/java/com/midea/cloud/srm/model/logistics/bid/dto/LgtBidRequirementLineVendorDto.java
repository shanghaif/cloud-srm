package com.midea.cloud.srm.model.logistics.bid.dto;

import com.midea.cloud.srm.model.logistics.bid.entity.LgtBidRequirementLine;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtBidTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 供应商查看详情 - 需求明细
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
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LgtBidRequirementLineVendorDto implements Serializable {

    /**
     * 需求明细
     */
    private List<LgtBidRequirementLine> lgtBidRequirementLines;
    /**
     * 需求明细模板
     */
    private List<LgtBidTemplate> lgtBidTemplates;
}
