package com.midea.cloud.srm.model.supplierauth.materialtrial.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.util.Date;

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
 *  修改日期: 2020-3-20 11:36
 *  修改内容:
 * </pre>
 */
@Data
public class RequestTrialDTO extends BaseDTO {
    /**
     * 试用申请单号
     */
    private String trialNumber;


    /**
     * 样品确认主键ID
     */
    private Long sampleId;


    /**
     * 样品确认编号
     */
    private String sampleNumber;

    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 公司Code
     */
    private String vendorCode;

    /**
     * 公司名称
     */
    private String vendorName;

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

    /**
     * 试用结果
     */
    private String trialResult;

    /**
     * 试用单据状态
     */
    private String approveStatus;

    /**
     * 物料试用开始时间
     */
    private Date StartDate;

    /**
     * 物料试用结束时间
     */
    private Date EndDate;
}
