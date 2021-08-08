package com.midea.cloud.srm.model.inq.price.vo;

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
 * @author chewj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chewj92
 *  修改日期: 2020/9/24 19:37
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class ApprovalAllVo {
    private ApprovalHeader approvalHeader;

    private List<ApprovalFile> approvalFileList;

    private List<ApprovalBiddingItemVO> approvalBiddingItemList;

    /**
     * 创建人昵称
     */
    private String createByName;
    /**
     * 创建人所属部门
     */
    private String createByDept;

}
