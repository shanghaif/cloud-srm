package com.midea.cloud.srm.pr.anon.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.enums.pm.pr.requirement.RequirementApproveStatus;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.pm.pr.division.entity.DivisionCategory;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.PurchaseRequirementDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RequirementApplyRejectVO;
import com.midea.cloud.srm.pr.division.service.IDivisionCategoryService;
import com.midea.cloud.srm.pr.requirement.service.IRequirementHeadService;
import com.midea.cloud.srm.pr.requirement.service.IRequirementLineService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

/**
 * <pre>
 *  不需暴露外部网关的接口，服务内部调用接口(无需登录就可以对微服务模块间暴露)
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-11 9:09
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/pr-anon")
@Slf4j
public class PrAnonController {
    @Autowired
    private IRequirementLineService iRequirementLineService;

    @Autowired
    private IRequirementHeadService iRequirementHeadService;

    @Autowired
    private IDivisionCategoryService divisionCategoryService;

    @GetMapping("/requirementLine/getById")
    public RequirementLine getRequirementLineById(@RequestParam("requirementLineId") Long requirementLineId){
        Assert.notNull(requirementLineId, LocaleHandler.getLocaleMsg("requirementLineId"));
        return iRequirementLineService.getById(requirementLineId);
    }

    @GetMapping({"/requirementHead/getByHeadId"})
    public PurchaseRequirementDTO getPurchaseRequirementDTOByHeadId(@RequestParam("requirementHeadId") Long requirementHeadId){
        Assert.notNull(requirementHeadId, LocaleHandler.getLocaleMsg("requirementHeadId"));
        return iRequirementHeadService.getByHeadId(requirementHeadId);
    }

    /**
     * 通过审批(备用)
     *
     * @param requirementHeadId
     */
    @GetMapping("/requirementHead/approval")
    public void approval(@RequestParam("requirementHeadId")Long requirementHeadId) {
        iRequirementHeadService.updateApproved(requirementHeadId, RequirementApproveStatus.APPROVED.getValue());
    }

    /**
     * 驳回(备用)
     * @param requirementApplyRejectVO
     */
    @PostMapping("/requirementHead/reject")
    public void reject(@RequestBody RequirementApplyRejectVO requirementApplyRejectVO) {
        iRequirementHeadService.reject(requirementApplyRejectVO.getRequirementHeadId(),
                requirementApplyRejectVO.getRejectReason());
    }

    /**
     * 撤回(备用)
     * @param requirementApplyRejectVO
     */
    @PostMapping("/requirementHead/withdraw")
    public void withdraw(@RequestBody RequirementApplyRejectVO requirementApplyRejectVO) {
        iRequirementHeadService.withdraw(requirementApplyRejectVO.getRequirementHeadId(),
                requirementApplyRejectVO.getRejectReason());
    }


    /**
     * 根据条件获取品类分工信息
     * @param divisionCategory
     * @return
     */
    @PostMapping("/loadDivisionCategoryByParam")
    public List<DivisionCategory> loadDivisionCategoryByParam(@RequestBody DivisionCategory divisionCategory){
        List<DivisionCategory> queryResult = divisionCategoryService.list(new QueryWrapper<>(divisionCategory)
                .le("START_DATE" , LocalDate.now()));
        List<DivisionCategory> ouputQuery = new ArrayList<>();
        for(DivisionCategory category : queryResult){
            if(Objects.isNull(category)){
                continue;
            }
            if(Objects.isNull(category.getEndDate()) ||  category.getEndDate().isAfter(LocalDate.now())){
                ouputQuery.add(category);
            }
        }
        return ouputQuery;
    }

    @PostMapping("/getRequirementLineByIdsForAnon")
    public List<RequirementLine> getRequirementLineByIdsForAnon(@RequestBody List<Long> requirementLineIds){
        if(CollectionUtils.isEmpty(requirementLineIds)){
            return Collections.EMPTY_LIST;
        }
        QueryWrapper<RequirementLine> requirementLineWrapper = new QueryWrapper<>();
        requirementLineWrapper.in("REQUIREMENT_LINE_ID",requirementLineIds);
        return iRequirementLineService.list(requirementLineWrapper);
    }

    @PostMapping("/getRequirementHeadByIdsForAnon")
    public List<RequirementHead> getRequirementHeadByIdsForAnon(@RequestBody List<Long> requirementHeadIds){
        if(CollectionUtils.isEmpty(requirementHeadIds)){
            return Collections.EMPTY_LIST;
        }
        QueryWrapper<RequirementHead> requirementHeadWrapper = new QueryWrapper<>();
        requirementHeadWrapper.in("REQUIREMENT_HEAD_ID",requirementHeadIds);
        return iRequirementHeadService.list(requirementHeadWrapper);
    }

}
