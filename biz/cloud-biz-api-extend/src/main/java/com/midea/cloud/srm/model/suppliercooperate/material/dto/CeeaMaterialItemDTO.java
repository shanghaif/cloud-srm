package com.midea.cloud.srm.model.suppliercooperate.material.dto;

import com.midea.cloud.srm.model.suppliercooperate.material.entity.CeeaMaterialDetail;
import com.midea.cloud.srm.model.suppliercooperate.material.entity.CeeaMaterialItem;
import lombok.Data;

import java.util.List;

@Data
public class CeeaMaterialItemDTO extends CeeaMaterialItem {
    /**
     * 业务实体集合
     */
    private List<Long> orgIds;
    /**
     * 库存组织集合
     */
    private List<Long> organizationIds;
    /**
     * 物料小类集合
     */
    private List<Long> categoryIds;
    /**
     * 物料计划明细集合
     */
    private List<CeeaMaterialDetail> materialDetailList;
    /**
     * 物料计划对象
     */
    private CeeaMaterialItem materialItem;


}
