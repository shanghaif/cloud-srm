package com.midea.cloud.srm.model.inq.price.dto;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-23 17:45
 *  修改内容:
 * </pre>
 */
public class ApprovalEditRequestDTO {

    /**
     * 价格审批单id
     */
    private Long approvalHeaderId;

    /**
     * 附件
     */
    private List<ApprovalFileDTO> approvalFiles;
}