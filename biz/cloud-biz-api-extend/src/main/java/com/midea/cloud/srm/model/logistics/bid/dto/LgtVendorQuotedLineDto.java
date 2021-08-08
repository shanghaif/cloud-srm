package com.midea.cloud.srm.model.logistics.bid.dto;

import com.midea.cloud.srm.model.logistics.bid.entity.LgtVendorQuotedLine;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 物流评选->保存->参数
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
public class LgtVendorQuotedLineDto implements Serializable {

    private static final long serialVersionUID = 7812016467911526149L;
    /**
     * 供应报价行信息
     */
    private List<LgtVendorQuotedLine> lgtVendorQuotedLines;
    /**
     * 组合名称
     */
    private String combinationName;
}
