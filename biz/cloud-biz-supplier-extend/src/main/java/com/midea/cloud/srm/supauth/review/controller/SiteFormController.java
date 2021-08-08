package com.midea.cloud.srm.supauth.review.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.FormResultDTO;
import com.midea.cloud.srm.model.supplierauth.review.dto.LastSiteFormMessageDTO;
import com.midea.cloud.srm.model.supplierauth.review.dto.SiteFormDTO;
import com.midea.cloud.srm.model.supplierauth.review.entity.SiteForm;
import com.midea.cloud.srm.supauth.review.service.ISiteFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  现场评审单据 前端控制器
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
@RestController
@RequestMapping("/review/siteForm")
public class SiteFormController extends BaseController {

    @Autowired
    private ISiteFormService iSiteFormService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public SiteForm get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iSiteFormService.getById(id);
    }

    /**
    * 新增
    * @param siteForm
    */
    @PostMapping("/add")
    public void add(@RequestBody SiteForm siteForm) {
        Long id = IdGenrator.generate();
        siteForm.setSiteFormId(id);
        iSiteFormService.save(siteForm);
    }

    /**
    * 删除
    * @param siteFormId
    */
    @GetMapping("/delete")
    public void delete(Long siteFormId) {
        Assert.notNull(siteFormId, "siteFormId不能为空");
        iSiteFormService.deleteSiteFormById(siteFormId);
    }

    /**
     * 废弃订单
     * @param siteFormId
     */
    @GetMapping("/abandon")
    public void abandon(Long siteFormId) {
        Assert.notNull(siteFormId,"废弃订单id不能为空");
        iSiteFormService.abandon(siteFormId);
    }

    /**
    * 修改
    * @param siteForm
    */
    @PostMapping("/modify")
    public void modify(@RequestBody SiteForm siteForm) {
        iSiteFormService.updateById(siteForm);
    }

    /**
    * 分页条件查询现场评审单据(ceea:分页条件查询供方认证单据)
    * @param siteForm
    * @return
    */
    @PostMapping("/listPageByParm")
    public PageInfo<SiteForm> listPageByParm(@RequestBody SiteForm siteForm) {
        return iSiteFormService.listPageByParm(siteForm);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<SiteForm> listAll() {
        return iSiteFormService.list();
    }

    /**
     * 编辑与保存现场评审单据   modify by chensl26@meicloud.com  -->提交
     * @param siteFormDTO
     * @return
     */
    @PostMapping("/saveOrUpdateSiteForm")
    public FormResultDTO saveOrUpdateSiteForm(@RequestBody SiteFormDTO siteFormDTO) {
        return iSiteFormService.saveOrUpdateSiteForm(siteFormDTO);
    }

    /**
     * 根据siteFormId获取SiteFormDTO
     * @param siteFormId
     * @return
     */
    @GetMapping("/getSiteFormDTO")
    public SiteFormDTO getSiteFormDTO(Long siteFormId) {
        return iSiteFormService.getSiteFormDTO(siteFormId);
    }

    /**
     * 根据供应商ID查询上次评审信息
     * @param vendorId
     * @return
     */
    @GetMapping("/getLastSiteFormMessage")
    public LastSiteFormMessageDTO getLastSiteFormMessage(Long vendorId) {
        return iSiteFormService.getLastSiteFormMessage(vendorId);
    }

    /**
     * 根据资质审查单ID获取现场评审单
     * @param reviewFormId
     * @return
     */
    @GetMapping("/getSiteFormByReviewFormId")
    public SiteForm getSiteFormByReviewFormId(Long reviewFormId) {
        return iSiteFormService.getSiteFormByReviewFormId(reviewFormId);
    }

    /**
     * 审批通过(备用)
     * @param siteForm
     */
    @PostMapping("/pass")
    public void pass(@RequestBody SiteForm siteForm) {
        iSiteFormService.pass(siteForm);
    }
}
