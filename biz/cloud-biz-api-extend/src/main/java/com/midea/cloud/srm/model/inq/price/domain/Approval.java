package com.midea.cloud.srm.model.inq.price.domain;

import lombok.Data;

/**
 * <pre>
 *  价格审批
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-9 10:56
 *  修改内容:
 * </pre>
 */
@Data
public class Approval {

    /**
     * 价格审批单头主键
     */
    private Long approvalHeaderId;
    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 价格审批单号
     */
    private String approvalNo;

    /**
     * 询价单id
     */
    private Long inquiryId;
}