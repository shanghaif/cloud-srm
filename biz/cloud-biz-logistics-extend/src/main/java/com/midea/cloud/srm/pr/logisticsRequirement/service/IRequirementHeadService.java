package com.midea.cloud.srm.pr.logisticsRequirement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.logistics.pr.requirement.dto.LogisticsPurchaseRequirementDTO;
import com.midea.cloud.srm.model.logistics.pr.requirement.dto.LogisticsRequirementHeadQueryDTO;
import com.midea.cloud.srm.model.logistics.pr.requirement.dto.LogisticsRequirementManageDTO;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementHead;

import java.util.List;

/**
*  <pre>
 *  物流采购需求头表 服务类
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-27 10:59:47
 *  修改内容:
 * </pre>
*/
public interface IRequirementHeadService extends IService<LogisticsRequirementHead> {

    Long addPurchaseRequirement(LogisticsPurchaseRequirementDTO purchaseRequirementDTO);

    void deleteByHeadId(Long requirementHeadId);

    Long modifyPurchaseRequirement(LogisticsPurchaseRequirementDTO purchaseRequirementDTO);

    /**
     * 采购申请分页条件查询
     * @param requirementHeadQueryDTO
     * @return
     */
    PageInfo<LogisticsRequirementHead> listPage(LogisticsRequirementHeadQueryDTO requirementHeadQueryDTO);

    LogisticsPurchaseRequirementDTO getByHeadId(Long requirementHeadId);

    void submitApproval(LogisticsPurchaseRequirementDTO purchaseRequirementDTO);

    void updateApproved(Long requirementHeadId, String auditStatus);

    void withdraw(Long requirementHeadId, String rejectReason);

    void reject(Long requirementHeadId, String rejectReason);

    void abandon(Long requirementHeadId);

    void bachAssigned(LogisticsRequirementManageDTO requirementManageDTO);

    void bachUnAssigned(LogisticsRequirementManageDTO requirementManageDTO);

    /**
     * 需求池分页条件查询
     * @param requirementHeadQueryDTO
     * @return
     */
    List<LogisticsRequirementHead> listPageNew(LogisticsRequirementHeadQueryDTO requirementHeadQueryDTO);

    /**
     * 批量删除物流采购申请
     * @param requirementHeadIds
     */
    void batchDelete(List<Long> requirementHeadIds);

    /**
     * 批量复制物流采购申请
     * @param requirementHeadIds
     * @return
     */
    List<Long> batchCopy(List<Long> requirementHeadIds);

    /**
     * 招标释放采购申请
     * @param id
     */
    void release(Long id);

    /**
     * 根据单号释放申请接口
     * @param requirementHeadNum
     */
    void release(List<String> requirementHeadNum);
}
