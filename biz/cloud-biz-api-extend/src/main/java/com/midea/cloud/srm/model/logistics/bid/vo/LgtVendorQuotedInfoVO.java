package com.midea.cloud.srm.model.logistics.bid.vo;

import com.midea.cloud.srm.model.logistics.bid.entity.LgtBidShipPeriod;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtVendorFile;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtVendorQuotedHead;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtVendorQuotedLine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  供应商报价信息
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2021/1/12 14:29
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LgtVendorQuotedInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //供应商报价头表
    private LgtVendorQuotedHead vendorQuotedHead;
    //供应商报名附件表
    private List<LgtVendorFile> vendorFileList;
    //供应商船期明细表
    private List<LgtBidShipPeriod> bidShipPeriodList;
    //供应商报价明细表
    private List<LgtVendorQuotedLine> vendorQuotedLineList;

}
