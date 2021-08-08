package com.midea.cloud.srm.supauth.review.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.FormResultDTO;
import com.midea.cloud.srm.model.supplierauth.review.dto.LastSiteFormMessageDTO;
import com.midea.cloud.srm.model.supplierauth.review.dto.SiteFormDTO;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteForm;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;

/**
*  <pre>
 *  现场评审单据 服务类
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-15 17:06:13
 *  修改内容:
 * </pre>
*/
public interface ISiteFormService extends IService<SiteForm> {

    /**
     * 保存或编辑现场评审单据
     * @param siteFormDTO
     * @return
     */
    FormResultDTO saveOrUpdateSiteForm(SiteFormDTO siteFormDTO);

    /**
     * 分页条件查询现场评审单据
     * @param siteForm
     * @return
     */
    PageInfo<SiteForm> listPageByParm(SiteForm siteForm);

    /**
     * 根据siteFormId获取SiteFormDTO
     * @param siteFormId
     * @return
     */
    SiteFormDTO getSiteFormDTO(Long siteFormId);

    /**
     * 根据供应商ID查询上次评审信息
     * @param vendorId
     * @return
     */
    LastSiteFormMessageDTO getLastSiteFormMessage(Long vendorId);

    /**
     * 删除
     * @param siteFormId
     */
    void deleteSiteFormById(Long siteFormId);

    /**
     * 废弃订单
     * @param siteFormId
     */
    @GetMapping("/abandon")
    void abandon(Long siteFormId);

    /**
     * 根据资质审查单ID获取现场评审单
     * @param reviewFormId
     * @return
     */
    SiteForm getSiteFormByReviewFormId(Long reviewFormId);

    /**
     * 审批通过(备用)
     * @param siteForm
     */
    void pass(SiteForm siteForm);
}
