package com.midea.cloud.srm.sup.info.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.dto.SiteInfoQueryDTO;
import com.midea.cloud.srm.model.supplier.info.entity.BankInfo;
import com.midea.cloud.srm.model.supplier.info.entity.SiteInfo;
import com.midea.cloud.srm.sup.info.service.ISiteInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  地点信息（供应商） 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-20 11:19:01
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/info/siteInfo")
public class SiteInfoController extends BaseController {

    @Autowired
    private ISiteInfoService iSiteInfoService;

    /**
     * 查询供应商地点
     * @param siteInfoQueryDTO
     * @return
     */
    @PostMapping("/listSiteInfoByParam")
    public List<SiteInfo> listSiteInfoByParam(@RequestBody SiteInfoQueryDTO siteInfoQueryDTO) {
        return iSiteInfoService.listSiteInfoByParam(siteInfoQueryDTO);
    }

    /**
     * 查询供应商地点(订单专用)
     * @param siteInfoQueryDTO
     * @return
     */
    @PostMapping("/listSiteInfoForOrder")
    public List<SiteInfo> listSiteInfoForOrder(@RequestBody SiteInfoQueryDTO siteInfoQueryDTO){
        return iSiteInfoService.listSiteInfoForOrder(siteInfoQueryDTO);
    }

    /**
     * 根据公司ID获取供应商地点信息
     * @param companyId
     * @return
     */
    @GetMapping("/getByCompanyId")
    public List<SiteInfo> getBySiteInfoCompanyId(Long companyId) {
        return iSiteInfoService.getByCompanyId(companyId);
    }

    /**
     * 新增
     * @param siteInfo
     */
    @PostMapping("/addSiteInfo")
    public void addSiteInfo(@RequestBody SiteInfo siteInfo) {
        Long id = IdGenrator.generate();
        siteInfo.setSiteInfoId(id);
        iSiteInfoService.save(siteInfo);
    }

    /**
     * 修改
     * @param siteInfo
     */
    @PostMapping("/modify")
    public void modify(SiteInfo siteInfo) {
        iSiteInfoService.updateById(siteInfo);
    }

    /**
     * 删除（删除地点信息）
     * @param siteInfoId
     */
    @GetMapping("/delete")
    public void delete(@RequestParam("siteInfoId") Long siteInfoId) {
        Assert.notNull(siteInfoId, "id不能为空！");
        iSiteInfoService.removeById(siteInfoId);
    }

    /**
     * 根据条件获取
     * @param siteInfo
     * @return
     */
    @PostMapping("/getSiteInfosByParam")
    public List<SiteInfo> getSiteInfosByParam(@RequestBody SiteInfo siteInfo) {
        return iSiteInfoService.getSiteInfosByParam(siteInfo);
    }

    /**
     * 导数据用（忽略）
     */
    @PostMapping("/adjustSiteErpOrgId")
    public void adjustSiteErpOrgId() {
        iSiteInfoService.adjustSiteErpOrgId();
    }

}
