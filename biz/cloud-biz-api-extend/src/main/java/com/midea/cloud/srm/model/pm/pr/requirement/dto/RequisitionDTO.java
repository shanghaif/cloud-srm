package com.midea.cloud.srm.model.pm.pr.requirement.dto;

import com.midea.cloud.srm.model.pm.pr.requirement.entity.Requisition;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequisitionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequisitionDTO extends Requisition {
    /**
     * 采购申请明细List
     */
    private List<RequisitionDetail> requisitionDetailList;
    /**
     * 采购需求编号(申请编号)
     */
    private String requirementHeadNum;

    /**
     * 库存组织ID
     */
    private Long organizationId;

    /**
     * 库存组织名称
     */
    private String organizationName;

    /**
     * 组织编码(ceea:库存组织编码)
     */
    private String organizationCode;
    /**
     * 物料大类编码(longi)
     */
    private String categoryMixCode;

    private Long OrgId;//业务实体id
    private String OrgCode;//业务实体code
    private String OrgName;//业务实体name

    /**
     * 部门ID
     */
    private String ceeaDepartmentId;

    /**
     * 部门编码(弃用)
     */
    private String ceeaDepartmentCode;

    /**
     * 部门名称
     */

    private String ceeaDepartmentName;
}
