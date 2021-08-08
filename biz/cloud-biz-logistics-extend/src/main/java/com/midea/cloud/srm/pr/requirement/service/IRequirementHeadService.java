package com.midea.cloud.srm.pr.requirement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RequirementLineVO;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.PurchaseRequirementDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementHeadQueryDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementManageDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;

import java.util.Collection;
import java.util.List;

/**
*  <pre>
 *  采购需求头表 服务类
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-12 18:46:40
 *  修改内容:
 * </pre>
*/
public interface IRequirementHeadService extends IService<RequirementHead> {

    PageInfo<RequirementHead> listPage(RequirementHeadQueryDTO requirementHeadQueryDTO);

    void deleteByHeadId(Long requirementHeadId);

    Long addPurchaseRequirement(PurchaseRequirementDTO purchaseRequirementDTO, String auditStatus);

    PurchaseRequirementDTO getByHeadId(Long requirementHeadId);

    Long modifyPurchaseRequirement(PurchaseRequirementDTO purchaseRequirementDTO);

    /**
     * Description 批量修改采购需求头信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.14
     * @throws
     **/
    BaseResult<String> bachRequirement(List<RequirementHead> requirementHeadList);

    void updateApprovelStatus(Long requirementHeadId, String auditStatus);

    void updateApproved(Long requirementHeadId, String auditStatus);

    /**
     * 审批通过调用
     * @param requirementHeadId
     * @param auditStatus
     */
    void updateApproved1(Long requirementHeadId, String auditStatus);

    void reject(Long requirementHeadId, String rejectReason);

    /**
     * <pre>
     *  撤回
     * </pre>
     *
     * @author chenwt24@meicloud.com
     * @version 1.00.00
     *
     * <pre>
     *  修改记录
     *  修改后版本:
     *  修改人:
     *  修改日期: 2020-10-10
     *  修改内容:
     * </pre>
     */
    void withdraw(Long requirementHeadId, String rejectReason);

    void abandon(Long requirementHeadId);

    FSSCResult submitApproval(PurchaseRequirementDTO purchaseRequirementDTO, String auditStatus);

    PageInfo<RequirementManageDTO> listPageByParam(RequirementManageDTO requirementManageDTO);

    List<RequirementManageDTO> createPurchaseOrder(List<RequirementManageDTO> requirementManageDTOS);

    Collection<RequirementManageDTO> createPurchaseOrderNew(List<RequirementManageDTO> requirementManageDTOS);


    void submitPurchaseOrder(List<RequirementManageDTO> requirementManageDTOS);

    RequirementHead getRequirementHeadByParam(RequirementHeadQueryDTO requirementHeadQueryDTO);

    RequirementLineVO createSourceForm(List<RequirementManageDTO> requirementManageDTOS, int sourcingType);

    public BaseResult approvalMrp(PurchaseRequirementDTO purchaseRequirementDTO, String auditStatus);

    void assignByDivisionCategory(RequirementHead requirementHead, List<RequirementLine> requirementLineList);

    PageInfo<RequirementManageDTO> listPageByParamNew(RequirementManageDTO requirementManageDTO);

    void tranferOrders(RequirementHead requirementHead, List<RequirementLine> requirementLineList);

    void submitPurchaseOrderNew(List<RequirementManageDTO> requirementManageDTOS);
}
