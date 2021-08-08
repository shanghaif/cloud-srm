package com.midea.cloud.srm.model.inq.price.vo;

import com.midea.cloud.srm.model.inq.price.entity.ApprovalBiddingItem;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalFile;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalHeader;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj
 *  修改日期: 2020/8/27 19:04
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class ApprovalVo {
    private ApprovalHeader approvalHeader;

    private List<ApprovalFile> approvalFileList;

    private List<ApprovalBiddingItem> approvalBiddingItemList;
}
