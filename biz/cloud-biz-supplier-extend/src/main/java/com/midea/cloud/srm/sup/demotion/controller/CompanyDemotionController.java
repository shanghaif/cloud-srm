package com.midea.cloud.srm.sup.demotion.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.demotion.dto.CompanyDemotionDTO;
import com.midea.cloud.srm.model.supplier.demotion.dto.CompanyDemotionQueryDTO;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotion;
import com.midea.cloud.srm.sup.demotion.service.ICompanyDemotionService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
*  <pre>
 *  供应商升降级表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-05 17:49:32
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/demotion/company-demotion")
public class CompanyDemotionController extends BaseController {

    @Resource
    private ICompanyDemotionService iCompanyDemotionService;

    /**
    * 查询单据详情
    * @param companyDemotionId
    */
    @GetMapping("/getDemotionById")
    public CompanyDemotionDTO getDemotionById(@NotNull Long companyDemotionId) {
        return iCompanyDemotionService.getDemotionById(companyDemotionId);
    }

    /**
    * 单据暂存
    * @param demotionDTO
    */
    @PostMapping("/saveTemporary")
    public Long saveTemporary(@RequestBody CompanyDemotionDTO demotionDTO) {
        // 暂存前必填校验
        iCompanyDemotionService.check(demotionDTO, ApproveStatusType.DRAFT.getValue());
        return iCompanyDemotionService.saveTemporary(demotionDTO);
    }

    /**
     * 单据提交
     * @param demotionDTO
     */
    @PostMapping("/submit")
    public void submit(@RequestBody CompanyDemotionDTO demotionDTO) {
        // 提交前必填校验
        iCompanyDemotionService.check(demotionDTO, ApproveStatusType.SUBMITTED.getValue());
        iCompanyDemotionService.submit(demotionDTO);
    }

    /**
     * 单据审批
     * @param companyDemotionId
     */
    @GetMapping("/approve")
    public void approve(@NotNull Long companyDemotionId) {
        // 审批前校验
        CompanyDemotion demotion = iCompanyDemotionService.getById(companyDemotionId);
        Assert.isTrue(Objects.equals(ApproveStatusType.SUBMITTED.getValue(), demotion.getStatus()), "只有已提交单据才能审批");
        iCompanyDemotionService.approve(demotion);
    }

    /**
     * 单据驳回
     * @param companyDemotionId
     */
    @GetMapping("/reject")
    public void reject(@NotNull Long companyDemotionId) {
        // 驳回前校验
        CompanyDemotion demotion = iCompanyDemotionService.getById(companyDemotionId);
        Assert.isTrue(Objects.equals(ApproveStatusType.SUBMITTED.getValue(), demotion.getStatus()), "只有已提交单据才能驳回。");
        iCompanyDemotionService.reject(demotion);
    }

    /**
     * 单据撤回
     * @param companyDemotionId
     */
    @GetMapping("/withDraw")
    public void withDraw(@NotNull Long companyDemotionId) {
        // 撤回前校验
        CompanyDemotion demotion = iCompanyDemotionService.getById(companyDemotionId);
        Assert.isTrue(Objects.equals(ApproveStatusType.SUBMITTED.getValue(), demotion.getStatus()), "只有已提交单据才能撤回。");
        iCompanyDemotionService.withDraw(demotion);
    }
    
    /**
    * 升降级单据删除
    * @param companyDemotionId
    */
    @GetMapping("/deleteById")
    public void deleteById(@NotNull Long companyDemotionId) {
        iCompanyDemotionService.deleteById(companyDemotionId);
    }

    /**
    * 分页条件查询
    * @param queryDTO
    * @return
    */
    @PostMapping("/listPageByParam")
    public PageInfo<CompanyDemotion> listPageByParam(@RequestBody CompanyDemotionQueryDTO queryDTO) {
        return iCompanyDemotionService.listPageByParam(queryDTO);
    }



}
