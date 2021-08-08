package com.midea.cloud.srm.feign.supplierauth;

import com.midea.cloud.srm.feign.flow.WorkFlowFeignClient;
import com.midea.cloud.srm.feign.workflow.FlowBusinessCallbackClient;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.supplier.quest.dto.QuestSupplierDto;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestSupplier;
import com.midea.cloud.srm.model.supplier.quest.vo.QuestSupplierVo;
import com.midea.cloud.srm.model.supplierauth.review.dto.ReviewFormDTO;
import com.midea.cloud.srm.model.supplierauth.review.entity.ReviewForm;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <pre>
 *  供应商准模块 内部调用Feign接口
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/26
 *  修改内容:
 * </pre>
 */
@FeignClient(name = "cloud-biz-supplier", contextId = "cloud-biz-sup-auth-merge")
public interface SupplierAuthClient extends FlowBusinessCallbackClient {

    // 样品确认表[quaSample] - >>>>>

    /**
     * 统计样品确认的待确认单
     */
    @GetMapping("/qua/quaSample/countConfirmed")
    public WorkCount quaSampleCountConfirmed();

    // 样品确认表[quaSample]  <<<<<

    // 物料试用表[materialTrial] - >>>>>

    /**
     * 物料试用的待确认单
     */
    @GetMapping("/materialTrial/countConfirmed")
    public WorkCount materialTrialCountConfirmed();

    // 物料试用表[materialTrial]  <<<<<

    /**
     * 资质审查单据审批通过
     *
     * @param reviewForm
     */
    @PostMapping("/sup-auth-anon/review/reviewForm/ReviewFormPass")
    void ReviewFormPass(@RequestBody ReviewForm reviewForm);

    /**
     * 资质审查单据驳回
     *
     * @param reviewForm
     */
    @PostMapping("/sup-auth-anon/review/reviewForm/ReviewFormRejected")
    void ReviewFormRejected(@RequestBody ReviewForm reviewForm);

    /**
     * 资质审查单据撤回
     *
     * @param reviewForm
     */
    @PostMapping("/sup-auth-anon/review/reviewForm/ReviewFormWithdraw")
    void ReviewFormWithdraw(@RequestBody ReviewForm reviewForm);

    /**
     * 根据资质审查ID获取ReviewFormDTO
     *
     * @param reviewFormId
     * @return
     */
    @GetMapping("/sup-auth-anon/review/reviewForm/getReviewFormDTO")
    ReviewFormDTO getReviewFormDTO(@RequestParam("reviewFormId") Long reviewFormId);
    /**
     * 供应商认证审批审批通过
     * @param siteForm
     */
    @PostMapping("/sup-auth-anon/review/siteForm/SiteFormPass")
     void SiteFormPass(@RequestBody SiteForm siteForm);
    /**
     * 供应商认证审批驳回
     *
     * @param siteForm
     */
    @PostMapping("/sup-auth-anon/review/siteForm/SiteFormRejected")
    void SiteFormRejected(@RequestBody SiteForm siteForm);

    /**
     * 供应商认证审批审批撤回
     *
     * @param siteForm
     */
    @PostMapping("/sup-auth-anon/review/siteForm/SiteFormWithdraw")
    void SiteFormWithdraw(@RequestBody SiteForm siteForm);

    /**
     * 供应商认证审批获取
     * @param siteFormId
     */
    @GetMapping("/sup-auth-anon/review/siteForm/SiteFormGet")
     SiteForm SiteFormGet(@RequestParam("siteFormId")Long siteFormId);

    /**
     * 供应商问卷调查审批通过
     *
     * @param questSupplier
     */
    @PostMapping("/quest/questSupplier/flow/questSupplierPass")
    void questSupplierPass(@RequestBody QuestSupplier questSupplier);

    /**
     * 供应商问卷调查审批驳回
     *
     * @param questSupplier
     */
    @PostMapping("/quest/questSupplier/flow/questSupplierRejected")
    void questSupplierRejected(@RequestBody QuestSupplier questSupplier);

    /**
     * 供应商问卷调查撤回
     *
     * @param questSupplier
     */
    @PostMapping("/quest/questSupplier/flow/questSupplierWithdraw")
    void questSupplierWithdraw(@RequestBody QuestSupplier questSupplier);

    /**
     * 根据ID获取供应商问卷调查单
     *
     * @param questSupIdForQuery
     * @return
     */
    @GetMapping("/quest/questSupplier/flow/getQuestSupplierVo")
    QuestSupplierVo getQuestSupplierVo(@RequestParam("questSupIdForQuery") Long questSupIdForQuery);
    
    
    /**
     * 工作流回调入口，需要各个模块实现本模块下的各个功能的动态调用service
     */
    @PostMapping("/sup-auth-anon/callbackFlow")
    @Override
    String callbackFlow(@RequestParam("serviceBean") String serviceBean, @RequestParam("callbackMethod") String callbackMethod, @RequestParam("businessId") Long businessId , @RequestParam("param") String param);

}