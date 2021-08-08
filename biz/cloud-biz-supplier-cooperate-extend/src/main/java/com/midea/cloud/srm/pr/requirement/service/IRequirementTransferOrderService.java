package com.midea.cloud.srm.pr.requirement.service;

import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementManageDTO;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * <pre>
 *  申请转订单相关服务；采购申请转订单过于复杂，单独抽离出来
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/23 下午 03:23
 *  修改内容:
 * </pre>
 */
public interface IRequirementTransferOrderService {

    /**
     * 申请转订单--创建采购订单列表
     * @param requirementManageDTOS
     * @return
     */
    Collection<RequirementManageDTO> createPurchaseOrderNew(List<RequirementManageDTO> requirementManageDTOS,
    LocalDate from,LocalDate to
    );

    /**
     * 申请转订单--提交创建采购订单列表
     * @param requirementManageDTOS
     */
    void submitPurchaseOrderNew(List<RequirementManageDTO> requirementManageDTOS);
}
