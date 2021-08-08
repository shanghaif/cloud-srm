package com.midea.cloud.srm.model.supplierauth.quasample.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.time.LocalDate;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author zhuwl7@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-18 8:51
 *  修改内容:
 * </pre>
 */
@Data
public class RequestSampleDTO extends BaseDTO {


    /**
     * 样品确认编号
     */
    private String sampleNumber;

    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 公司名称
     */
    private String vendorCode;

    /**
     * 公司名称
     */
    private String vendorName;

    /**
     * 样品接收人
     */
    private String receiver;

    /**
     * 样品接收人电话
     */
    private String receiverPhone;

    /**
     * 资质审查单号表ID
     */
    private Long reviewFormId;

    /**
     * 资质审查单号，对应SCC_SUP_QUA_REVIEW_FORM表
     */
    private String reviewFormNumber;

    /**
     * 审批状态(DRAFT拟定、SUBMITTED已提交、REJECTED已驳回、APPROVED已审批,参考字典码APPROVE_STATUS_TYPE
     */
    private String approveStatus;

    /**
     * 要求送样时间
     */
    private LocalDate requireStarTime;

    /**
     * 要求送结束
     */
    private LocalDate requireEndTime;

    /**
     * 是否需要物料试用(Y，N)
     */
    private String isMaterialTrial;


    /**
     * 采购组织ID
     */
    private Long purchaseOrgId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 采购组织CODE
     */
    private String purchaseOrgCode;

    /**
     * 采购组织名称
     */
    private String purchaseOrgName;

    /**
     * 品类ID
     */
    private Long categoryId;

    /**
     * 品类CODE
     */
    private String categoryCode;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 物料CODE
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    private String testResult;
}
