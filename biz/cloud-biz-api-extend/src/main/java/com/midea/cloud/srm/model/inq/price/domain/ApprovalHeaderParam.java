package com.midea.cloud.srm.model.inq.price.domain;

import lombok.Data;

/**
 * <pre>
 *  价格审批头查询参数
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-9 16:17
 *  修改内容:
 * </pre>
 */
@Data
public class ApprovalHeaderParam {

    /**
     * 价格审批单号
     */
    private String approvalNo;

    /**
     * 寻价单号
     */
    private String businessNo;

    /**
     * 审核状态
     */
    private String status;
}