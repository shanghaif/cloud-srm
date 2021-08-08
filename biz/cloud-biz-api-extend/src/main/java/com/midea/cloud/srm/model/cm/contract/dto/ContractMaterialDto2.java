package com.midea.cloud.srm.model.cm.contract.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/4
 *  修改内容:
 * </pre>
 */
@Data
public class ContractMaterialDto2 implements Serializable {

    private static final long serialVersionUID = -6785623177063793339L;
    /**
     * 含税金额
     */
    private BigDecimal amount;
    /**
     * 供应商ID
     */
    private Long vendorId;
    /**
     * 供应商编码
     */
    private String vendorCode;
    /**
     * 供应商名字
     */
    private String vendorName;
    /**
     * 合同序号
     */
    private String contractNo;
    /**
     * 合同编号
     */
    private String contractCode;
    /**
     * 合同头ID
     */
    private Long contractHeadId;
    /**
     * 物料行ID
     */
    private Long contractMaterialId;

}
