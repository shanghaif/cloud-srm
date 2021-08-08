package com.midea.cloud.srm.supauth.anon.controller;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.OpType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.workflow.FlowBusinessCallbackClient;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryConfig;
import com.midea.cloud.srm.model.supplierauth.review.dto.ReviewFormDTO;
import com.midea.cloud.srm.model.supplierauth.review.entity.ReviewForm;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteForm;
import com.midea.cloud.srm.model.workflow.service.IFlowBusinessCallbackService;
import com.midea.cloud.srm.supauth.entry.service.IEntryConfigService;
import com.midea.cloud.srm.supauth.review.service.IReviewFormService;
import com.midea.cloud.srm.supauth.review.service.ISiteFormService;

/**
 * <pre>
 *  不需暴露外部网关的接口，服务内部调用接口(无需登录就可以对微服务模块间暴露)
 * </pre>
 *
 * @author ex_lizp6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-10 18:01
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/sup-auth-anon/")
@Slf4j
public class supAuthAnonController implements FlowBusinessCallbackClient {
    @Autowired
    private IReviewFormService iReviewFormService;
    @Autowired
    private ISiteFormService iSiteFormService;

    @Autowired
    private IEntryConfigService iEntryConfigService;
    /**
     * 资质审查单据审批通过(备用)
     *
     * @param reviewForm
     */
    @PostMapping("/review/reviewForm/ReviewFormPass")
    public void ReviewFormPass(@RequestBody ReviewForm reviewForm) {
        iReviewFormService.pass(reviewForm);
    }
    /**
     * 资质审查单据驳回
     *
     * @param reviewForm
     */
    @PostMapping("/review/reviewForm/ReviewFormRejected")
    public void ReviewFormRejected(@RequestBody ReviewForm reviewForm) {
        iReviewFormService.updateById(reviewForm.setApproveStatus(ApproveStatusType.REJECTED.getValue()));
    }
    /**
     * 资质审查单据撤回
     *
     * @param reviewForm
     */
    @PostMapping("/review/reviewForm/ReviewFormWithdraw")
    public void ReviewFormWithdraw(@RequestBody ReviewForm reviewForm) {
        iReviewFormService.updateById(reviewForm.setApproveStatus(ApproveStatusType.WITHDRAW.getValue()));
    }
    /**
     * 根据资质审查ID获取ReviewFormDTO
     *
     * @param reviewFormId
     * @return
     */
    @GetMapping("/review/reviewForm/getReviewFormDTO")
    public ReviewFormDTO getReviewFormDTO(Long reviewFormId) {
        Assert.notNull(reviewFormId, "reviewFormId不能为空");
        return iReviewFormService.getReviewFormDTO(reviewFormId);
    }

    /**
     * 供应商认证审批审批通过
     * @param siteForm
     */
    @PostMapping("/review/siteForm/SiteFormPass")
    public void SiteFormPass(@RequestBody SiteForm siteForm) {
        iSiteFormService.pass(siteForm);
    }
    /**
     * 供应商认证审批驳回
     * @param siteForm
     */
    @PostMapping("/review/siteForm/SiteFormRejected")
    public void SiteFormRejected(@RequestBody SiteForm siteForm) {
            if (!ApproveStatusType.SUBMITTED.getValue().equals(siteForm.getApproveStatus())) {
                throw new BaseException(LocaleHandler.getLocaleMsg("已提交状态才可驳回"));
            }
            iSiteFormService.updateById(siteForm.setApproveStatus(ApproveStatusType.REJECTED.getValue()));
    }
    /**
     * 供应商认证审批撤回
     * @param siteForm
     */
    @PostMapping("/review/siteForm/SiteFormWithdraw")
    public void SiteFormWithdraw(@RequestBody SiteForm siteForm) {
            if (!ApproveStatusType.SUBMITTED.getValue().equals(siteForm.getApproveStatus())) {
                throw new BaseException(LocaleHandler.getLocaleMsg("已提交状态才可撤回"));
            }
            iSiteFormService.updateById(siteForm.setApproveStatus(ApproveStatusType.WITHDRAW.getValue()));
    }

    /**
     * 供应商认证审批获取
     * @param siteFormId
     */
    @GetMapping("/review/siteForm/SiteFormGet")
    public SiteForm SiteFormGet(Long siteFormId) {
        Assert.notNull(siteFormId, "id不能为空");
        return iSiteFormService.getById(siteFormId);
    }

    /**
     * 根据供方准入类型获取引入流程配置对象数据
     *
     * @param quaReviewType 供方准入类型,参考字典码QUA_REVIEW_TYPE
     * @return EntryConfig
     */
    @GetMapping("/entry/entryConfig/getEntryConfigByQuaReviewType")
    public EntryConfig getEntryConfigByQuaReviewType(String quaReviewType) {
        Assert.notNull(quaReviewType, "资质审查类型为空");
        return iEntryConfigService.getEntryConfigByQuaReviewType(quaReviewType);
    }

    /**
     * 工作流回调入口，需要各个模块实现本模块下的各个功能的动态调用service
     */
    @PostMapping("/callbackFlow")
	@Override
	public String callbackFlow(String serviceBean, String callbackMethod, Long businessId, String param) throws Exception {
    	IFlowBusinessCallbackService iFlowBusinessCallbackService =null;
    	
        Class clazz=Class.forName(serviceBean);
        Object bean = SpringContextHolder.getApplicationContext().getBean(clazz);
        iFlowBusinessCallbackService =(IFlowBusinessCallbackService)bean;
        log.error("method: {}, businessId:{}, param:{}", callbackMethod, businessId, param);
    	//SpringContextHolder.getBean(name);
    	if( "submitFlow".equals(callbackMethod) ) {
    		iFlowBusinessCallbackService.submitFlow(businessId, param);
    		return null;
    	}
    	if( "passFlow".equals(callbackMethod) ) {
    		iFlowBusinessCallbackService.passFlow(businessId, param);
    		return null;
    	}
    	if( "rejectFlow".equals(callbackMethod) ) {
    		iFlowBusinessCallbackService.rejectFlow(businessId, param);
    		return null;
    	}
    	if( "withdrawFlow".equals(callbackMethod) ) {
    		iFlowBusinessCallbackService.withdrawFlow(businessId, param);
    		return null;
    	}
    	if( "destoryFlow".equals(callbackMethod) ) {
    		iFlowBusinessCallbackService.destoryFlow(businessId, param);
    		return null;
    	}
    	if( "getVariableFlow".equals(callbackMethod) ) {
    		return iFlowBusinessCallbackService.getVariableFlow(businessId, param);
    	}
    	if( "getDataPushFlow".equals(callbackMethod) ) {
    		return iFlowBusinessCallbackService.getDataPushFlow(businessId, param);
    	}
    	return null;
	}

}
