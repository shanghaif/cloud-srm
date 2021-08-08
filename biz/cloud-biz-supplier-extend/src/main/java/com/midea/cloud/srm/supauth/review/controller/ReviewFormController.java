package com.midea.cloud.srm.supauth.review.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.OpType;
import com.midea.cloud.common.enums.bpm.TempIdToModuleEnum;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.ObjectUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.workflow.FlowBusinessCallbackClient;
import com.midea.cloud.srm.feign.workflow.FlowClient;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.dto.InfoDTO;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCateServiceStatusDTO;
import com.midea.cloud.srm.model.supplierauth.review.dto.ReviewFormDTO;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgCateJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.ReviewForm;
import com.midea.cloud.srm.supauth.review.service.IReviewFormService;

/**
 * <pre>
 *  资质审查单据 前端控制器
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 16:34:39
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/review/reviewForm/")
public class ReviewFormController extends BaseController {

    @Autowired
    private IReviewFormService iReviewFormService;

    /**
     * Base模块Feign
     */
    @Resource
    private BaseClient baseClient;
    
    @Resource
    private FlowClient flowClient;

    /**
     * 分页查询
     *
     * @param reviewForm
     * @return
     */
    @PostMapping("/listPageByParm")
    public PageInfo<ReviewForm> listPageByParm(@RequestBody ReviewForm reviewForm) {
        return iReviewFormService.listPageByParm(reviewForm);
    }

    /**
     * 删除
     *
     * @param reviewFormId
     */
    @GetMapping("/delete")
    public void delete(Long reviewFormId) {
        Assert.notNull(reviewFormId, "reviewFormId不能为空");
        iReviewFormService.deleteReviewFormById(reviewFormId);
    }

    /**
     * 废弃订单
     * @param reviewFormId
     */
    @GetMapping("/abandon")
    public void abandon(Long reviewFormId) {
        Assert.notNull(reviewFormId,"废弃订单id不能为空");
        iReviewFormService.abandon(reviewFormId);
    }

    /**
     * 保存或更新资质审查单据DTO----》或提交
     *
     * @param reviewFormDTO
     * @return
     */
    @PostMapping("/saveOrUpdateReviewForm")
    public Map<String, Object> saveOrUpdateReviewForm(@RequestBody ReviewFormDTO reviewFormDTO) {
    	boolean submit = false;
        //trun提交
        if (Objects.equals(reviewFormDTO.getOpType(), OpType.SUBMISSION.toString())) {
            ObjectUtil.validate(reviewFormDTO);
            submit = true;
        }
        Map<String, Object> map = iReviewFormService.saveOrUpdateReviewForm(reviewFormDTO);
        
        //提交的后续处理，不涉及业务处理，一般是先修改srm单据为提交状态，再提交oa（注意由于不是同一个事务，有可能是提交不到oa），由工作流模块控制提交是否直接通过，根据集成模式推送数据到OA
        if (submit) {
        	Long id = (Long) map.get("businessId");
        	flowClient.submitFlow(id, TempIdToModuleEnum.REVIEW.getValue(), null);
        }
        
        return map;
    }

    @GetMapping("/checkIfCanOp")
    public void checkIfCanOp(@RequestParam("vendorId") Long vendorId) {
        int count = iReviewFormService.count(Wrappers.lambdaQuery(
                ReviewForm.class)
                .eq(ReviewForm::getVendorId, vendorId).nested(
                        e -> e.eq(ReviewForm::getApproveStatus, ApproveStatusType.DRAFT.getValue())
                                .or().eq(ReviewForm::getApproveStatus, ApproveStatusType.SUBMITTED.getValue())
                )
        );
        if (count != 0) {
            throw new BaseException("该供应商已有资质审查单据在走流程");
        }
    }

    /**
     * 审批通过(备用)
     *
     * @param reviewForm
     */
    @PostMapping("/pass")
    public void pass(@RequestBody ReviewForm reviewForm) {
        iReviewFormService.pass(reviewForm);
    }

    /**
     * 根据资质审查ID获取ReviewFormDTO
     *
     * @param reviewFormId
     * @return
     */
    @GetMapping("/getReviewFormDTO")
    public ReviewFormDTO getReviewFormDTO(Long reviewFormId) {
        Assert.notNull(reviewFormId, "reviewFormId不能为空");
        return iReviewFormService.getReviewFormDTO(reviewFormId);
    }

    /**
     * 根据资质审查单号ID查询供应商档案组织与品类状态
     *
     * @param reviewFormId
     * @return
     */
    @GetMapping("/listOrgCateServiceStatusByReviewId")
    public List<OrgCateServiceStatusDTO> listOrgCateServiceStatusByReviewId(Long reviewFormId) {
        Assert.notNull(reviewFormId, "reviewFormId不能为空");
        return iReviewFormService.listOrgCateServiceStatusByReviewId(reviewFormId);
    }

    /**
     * 根据资质审查ID查询合作ou和合作品类 modify by chensl26
     *
     * @param reviewFormId
     * @return
     */
    @GetMapping("/listOrgAndCategoryByReviewId")
    public InfoDTO listOrgAndCategoryByReviewId(@RequestParam Long reviewFormId) {
        return iReviewFormService.listOrgAndCategoryByReviewId(reviewFormId);
    }

    /**
     * Description 根据组织设置Id获取组织设置信息
     *
     * @return
     * @Param organizationId 组织设置Id
     * @Author wuwl18@meicloud.com
     * @Date 2020.09.20
     **/
    @GetMapping("/getOrganizationById")
    public Organization get(Long organizationId) {
        return baseClient.get(organizationId);
    }

    /**
     * 根据资质审查ID查询合作ou和合作品类
     *
     * @param reviewFormId
     * @return
     */
    @GetMapping("/listOrgCateJournalByReviewId")
    public List<OrgCateJournal> listOrgCateJournalByReviewId(@RequestParam Long reviewFormId) {
        return iReviewFormService.listOrgCateJournalByReviewId(reviewFormId);
    }
}
