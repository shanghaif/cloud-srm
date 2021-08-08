package com.midea.cloud.srm.model.pm.pr.requirement.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Description 到货计划报表导出订单信息实体类
 * @Param
 * @return
 * @Author wuwl18@meicloud.com
 * @Date 2020.12.14
 * @throws
 **/
@Data
@Accessors(chain = true)
public class DeliveryPlanReportRequirementDTO {
    /** 申请明细ID */
    private Long requirementLineId;

    /** 申请人 */
    private String createFullName;

    /** 采购申请头备注 */
    private String ceeaDepartmentName;

    /** 申请明细备注 */
    private String comments;

}
