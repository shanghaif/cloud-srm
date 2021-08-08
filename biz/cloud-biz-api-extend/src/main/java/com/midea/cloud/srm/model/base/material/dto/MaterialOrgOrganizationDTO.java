package com.midea.cloud.srm.model.base.material.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

/**
 * <pre>
 *  物料Id 库存组织Id 业务实体Id DTO
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/28 16:52
 *  修改内容:
 * </pre>
 */
@Data
public class MaterialOrgOrganizationDTO extends BaseDTO {

    /**
     * 物料Id
     */
    private Long materialId;

    /**
     * 库存组织Id
     */
    private Long organizationId;

    /**
     * 业务实体Id
     */
    private Long orgId;

}
