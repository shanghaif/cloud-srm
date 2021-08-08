package com.midea.cloud.srm.model.logistics.bid.dto;

import com.midea.cloud.srm.model.logistics.bid.entity.LgtVendorQuotedSum;
import lombok.Data;

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
@Data
public class LgtVendorQuotedSumResult implements Serializable {
    /**
     * 报价明细汇总
     */
    private List<LgtVendorQuotedSum> lgtVendorQuotedSums;

    /**
     * 决标结果
     */
    private String bidResult;

    /**
     * 是否入围
     */
    private String shortlisted;

    /**
     * 操作类型 (Y:入围/淘汰 N:决标)
     */
    private String operateType;
}
