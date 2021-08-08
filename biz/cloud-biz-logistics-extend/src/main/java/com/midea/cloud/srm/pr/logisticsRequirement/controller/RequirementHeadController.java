package com.midea.cloud.srm.pr.logisticsRequirement.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.logistics.pr.requirement.LogisticsApproveStatus;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.pr.requirement.dto.LogisticsPurchaseRequirementDTO;
import com.midea.cloud.srm.model.logistics.pr.requirement.dto.LogisticsRequirementHeadQueryDTO;
import com.midea.cloud.srm.model.logistics.pr.requirement.dto.LogisticsRequirementManageDTO;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RequirementApplyRejectVO;
import com.midea.cloud.srm.pr.logisticsRequirement.service.IRequirementHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  物流采购需求头表 前端控制器
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
@RestController(value = "LogisticsRequirementHeadController")
@RequestMapping("/pr/requirement-head")
public class RequirementHeadController extends BaseController {

    @Autowired
    private IRequirementHeadService iRequirementHeadService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public LogisticsRequirementHead get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iRequirementHeadService.getById(id);
    }

    /**
    * 新增
    * @param purchaseRequirementDTO
    */
    @PostMapping("/add")
    public Long add(@RequestBody LogisticsPurchaseRequirementDTO purchaseRequirementDTO) {
        return iRequirementHeadService.addPurchaseRequirement(purchaseRequirementDTO);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iRequirementHeadService.deleteByHeadId(id);
    }

    /**
    * 修改
    * @param purchaseRequirementDTO
    */
    @PostMapping("/modify")
    public Long modify(@RequestBody LogisticsPurchaseRequirementDTO purchaseRequirementDTO) {
        return iRequirementHeadService.modifyPurchaseRequirement(purchaseRequirementDTO);
    }

    /**
    * 分页查询
    * @param purchaseRequirementDTO
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<LogisticsRequirementHead> listPage(@RequestBody LogisticsRequirementHeadQueryDTO purchaseRequirementDTO) {
        return iRequirementHeadService.listPage(purchaseRequirementDTO);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<LogisticsRequirementHead> listAll() {
        return iRequirementHeadService.list();
    }

    /**
     * 获取详情
     *
     * @param requirementHeadId
     */
    @GetMapping("/getByHeadId")
    public LogisticsPurchaseRequirementDTO getByHeadId(Long requirementHeadId) {
        return iRequirementHeadService.getByHeadId(requirementHeadId);
    }

    /**
     * 提交审批(longi)
     *
     * @param purchaseRequirementDTO
     */
//    @PreAuthorize("hasAuthority('pr:requirementApply:submitAudit')")
    @PostMapping("/submitApproval")
    public void submitApproval(@RequestBody LogisticsPurchaseRequirementDTO purchaseRequirementDTO) {
        iRequirementHeadService.submitApproval(purchaseRequirementDTO);

    }

    /**
     * 通过审批
     *
     * @param requirementHeadId
     */
//    @PreAuthorize("hasAuthority('pr:requirementApply:confirm')")
    @GetMapping("/approval")
    public void approval(Long requirementHeadId) {
        iRequirementHeadService.updateApproved(requirementHeadId, LogisticsApproveStatus.APPROVED.getValue());
    }

    /**
     * 驳回
     * @param requirementApplyRejectVO
     */
//    @PreAuthorize("hasAuthority('pr:requirementApply:refuse')")
    @PostMapping("/reject")
    public void reject(@RequestBody RequirementApplyRejectVO requirementApplyRejectVO) {
        iRequirementHeadService.reject(requirementApplyRejectVO.getRequirementHeadId(),
                requirementApplyRejectVO.getRejectReason());
    }

    /**
     * 撤回
     * @param requirementApplyRejectVO
     */
    @PostMapping("/withdraw")
    public void withdraw(@RequestBody RequirementApplyRejectVO requirementApplyRejectVO) {
        iRequirementHeadService.withdraw(requirementApplyRejectVO.getRequirementHeadId(),
                requirementApplyRejectVO.getRejectReason());
    }

    /**
     * 废弃
     *
     * @param requirementHeadId
     */
//    @PreAuthorize("hasAuthority('pr:requirementApply:cancel')")
    @GetMapping("/abandon")
    public void abandon(Long requirementHeadId) {
        iRequirementHeadService.abandon(requirementHeadId);
    }

    /**
     * 需求池首页分页查询
     * @param purchaseRequirementDTO
     * @return
     */
    @PostMapping("/listPageNew")
    public PageInfo<LogisticsRequirementHead> listPageNew(@RequestBody LogisticsRequirementHeadQueryDTO purchaseRequirementDTO) {
        PageUtil.startPage(purchaseRequirementDTO.getPageNum(), purchaseRequirementDTO.getPageSize());
        return new PageInfo<LogisticsRequirementHead>(iRequirementHeadService.listPageNew(purchaseRequirementDTO));
    }

    /**
     * 批量分配/转办采购申请
     *
     * @param requirementManageDTO
     */
    @PostMapping("/bachAssigned")
    public void bachAssigned(@RequestBody LogisticsRequirementManageDTO requirementManageDTO) {
        iRequirementHeadService.bachAssigned(requirementManageDTO);
    }

    /**
     * 批量取消分配采购申请
     *
     * @param requirementManageDTO
     */
    @PostMapping("/bachUnAssigned")
    public void bachUnAssigned(@RequestBody LogisticsRequirementManageDTO requirementManageDTO) {
        iRequirementHeadService.bachUnAssigned(requirementManageDTO);
    }

    /**
     * 创建招标单：生成物流招标单据
     *
     * @param requirementManageDTO
     */
    @PostMapping("/doCreateBidding")
    public void doCreateBidding(@RequestBody LogisticsRequirementManageDTO requirementManageDTO) {
        iRequirementHeadService.bachUnAssigned(requirementManageDTO);
    }

    /**
     * 批量删除采购申请
     * @param requirementHeadIds
     */
    @PostMapping("/batchDelete")
    public void batchDelete(@RequestBody List<Long> requirementHeadIds){
        iRequirementHeadService.batchDelete(requirementHeadIds);
    }

    /**
     * 批量复制采购申请
     * @param requirementHeadIds
     * @return
     */
    @PostMapping("/batchCopy")
    public List<Long> batchCopy(@RequestBody List<Long> requirementHeadIds){
        return iRequirementHeadService.batchCopy(requirementHeadIds);
    }

    /**
     * 释放采购申请
     * 去除招标id，招标编号，招标名称
     * @param id
     */
    @PostMapping("/release")
    public void release(@RequestParam Long id){
        iRequirementHeadService.release(id);
    }
}
