package com.midea.cloud.srm.supauth.review.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplier.info.dto.InfoDTO;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCateServiceStatusDTO;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplierauth.review.dto.ReviewFormDTO;
import com.midea.cloud.srm.model.supplierauth.review.entity.OrgCateJournal;
import com.midea.cloud.srm.model.supplierauth.review.entity.ReviewForm;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  资质审查单据 服务类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 16:34:39
 *  修改内容:
 * </pre>
*/
public interface IReviewFormService extends IService<ReviewForm> {

    /**
     * 保存或更新资质审查单据DTO
     * @param reviewFormDTO
     * @return
     */
    Map<String, Object> saveOrUpdateReviewForm(ReviewFormDTO reviewFormDTO);

    PageInfo<ReviewForm> listPageByParm(ReviewForm reviewForm);

    /**
     * 获取资质审查DTO
     * @param reviewFormId
     * @return
     */
    ReviewFormDTO getReviewFormDTO(Long reviewFormId);

    /**
     * 根据reviewFormId查询组织与品类服务状态
     * @param reviewFormId
     * @return
     */
    List<OrgCateServiceStatusDTO> listOrgCateServiceStatusByReviewId(Long reviewFormId);

    /**
     * 根据资质审查单Id删除资质审查单
     * @param reviewFormId
     */
    void deleteReviewFormById(Long reviewFormId);

    /**
     * 废弃订单
     * @param reviewFormId
     */
    @GetMapping("/abandon")
     void abandon(Long reviewFormId);

    /**
     * 工作流后更新资质审查单据
     * @param reviewForm
     */
    void updateReviewFormAfterWorkFlow(ReviewForm reviewForm);

    /**
     * 测试用
     * @param companyInfo
     */
    void giveVendorMainAccountRole(CompanyInfo companyInfo);

    /**
     * 审批(备用)
     * @param reviewForm
     */
    void pass(ReviewForm reviewForm);

    /**
     *根据资质审查ID查询合作ou和合作品类
     * @param reviewFormId
     * @return
     */
    InfoDTO listOrgAndCategoryByReviewId(Long reviewFormId);
    
    /**
     * 根据资质审查ID查询合作ou和合作品类
     * @param reviewFormId
     * @return
     */
    public List<OrgCateJournal> listOrgCateJournalByReviewId(Long reviewFormId);
}
