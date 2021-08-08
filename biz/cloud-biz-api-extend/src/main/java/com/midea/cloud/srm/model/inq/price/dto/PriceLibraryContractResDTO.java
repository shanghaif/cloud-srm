package com.midea.cloud.srm.model.inq.price.dto;

import lombok.Data;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author dengyl23@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/4 20:50
 *  修改内容:
 * </pre>
 */
@Data
public class PriceLibraryContractResDTO {

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 合同序号
     */
    private String contractNo;

    /**
     * 合同名称
     */
    private String contractName;
    /**
     * 合同id
     */
    Long contractHeadId;

    /**
     * 合同编号
     */
    private String contractCode;
}
