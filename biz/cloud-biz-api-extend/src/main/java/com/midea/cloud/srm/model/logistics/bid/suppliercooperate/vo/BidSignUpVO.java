package com.midea.cloud.srm.model.logistics.bid.suppliercooperate.vo;

import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidFileConfig;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BidSignUpVO extends SupplierBidingVO {

    /** 驳回原因 */
    private String rejectReason;
    /** 回复时间 */
    private Date replyDatetime;
    /** 备注 */
    private String comments;
    /** 附件信息 */
    List<BidVendorFileVO> vendorFileVOs;
    /**
     * 要求上传的附件信息
     */
    private List<BidFileConfig> fileConfigs;
}