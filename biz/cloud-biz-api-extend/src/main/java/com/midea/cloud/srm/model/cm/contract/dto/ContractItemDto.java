package com.midea.cloud.srm.model.cm.contract.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

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
@Accessors(chain = true)
public class ContractItemDto implements Serializable {
    /**
     * 业务实体
     */
    private Long ouId;
    /**
     * 库存组织
     */
    private Long invId;
    /**
     * 物料编码
     */
    private String itemCode;
    /**
     * 物料名称
     */
    private String itemName;

    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 物料ids
     */
    private List<Long> materialIds;

    /**
     * 业务实体id
     */
    private List<Long> ouIds;


}
