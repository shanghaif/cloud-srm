package com.midea.cloud.srm.model.inq.price.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-9 16:02
 *  修改内容:
 * </pre>
 */
@Data
public class ApprovalHeaderQueryRequestDTO extends BaseDTO {

    /**
     * 价格审批单号
     */
    private String approvalNo;

    /**
     * 寻源单号(不使用)
     */
    private String businessNo;

    /**
     * 审核状态
     */
    private String status;

    /**
     * 标题
     */
    private String ceeaTitle;

    /**
     * 创建日期-开始
     */
    private String startDate;

    /**
     * 创建日期-结束
     */
    private String endDate;

    /**
     * 创建人
     */
    private String nickName;

    /**
     * 物料编号
     */
    private String itemCode;


    /**
     * 供应商
     */
    private String vendorName;


    private String creationDate;

    /**
     * 创建人
     */
    private String createdBy;
}
