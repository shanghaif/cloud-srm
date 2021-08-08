package com.midea.cloud.srm.model.base.material.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

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
 *  修改日期: 2020/11/13
 *  修改内容:
 * </pre>
 */
@Data
public class ItemCodeUserPurchaseDto extends BaseDTO {
    /**
     * 业务实体Id
     */
    private Long orgId;
    /**
     * 库存组织Id
     */
    private Long invId;
    /**
     * 物料Id集合
     */
    private List<String> itemCodes;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 是否用于采购
     */
    private String userPurchase;

    /**
     * 物料状态
     */
    private String itemStatus;
}
