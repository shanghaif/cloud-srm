package com.midea.cloud.srm.sup.vendorimport.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.sup.VendorImportStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCategoryQueryDTO;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.VendorImportCategoryDTO;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.OrgDTO;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.VendorImportSaveDTO;
import com.midea.cloud.srm.model.supplier.vendorimport.entity.VendorImport;
import com.midea.cloud.srm.sup.vendorimport.service.IVendorImportDetailService;
import com.midea.cloud.srm.sup.vendorimport.service.IVendorImportService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
*  <pre>
 *  供应商引入表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-13 18:08:07
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/vendorImport")
public class VendorImportController extends BaseController {

    @Autowired
    private IVendorImportService iVendorImportService;

    @Autowired
    private IVendorImportDetailService iVendorImportDetailService;

    /**
     * 分页条件查询
     */
    @PostMapping("/listPageByParam")
    public PageInfo<VendorImport> listPageByParam(@RequestBody VendorImport vendorImport) {
        return iVendorImportService.listPageByParam(vendorImport);
    }

    /**
     * 根据引入Id查询引入详情
     * @param importId
     * @return
     */
    @GetMapping("/getVendorImportDetail")
    public VendorImportSaveDTO getVendorImportDetail(@RequestParam("importId") Long importId) {
        Assert.notNull(importId, "importId不能为空！");
        return iVendorImportService.getVendorImportDetail(importId);
    }

    /**
     * 根据供应商Id查询合作的组织列表
     * @param vendorId
     * @return
     */
    @GetMapping("/getOrgByVendorId")
    public List<OrgDTO> getOrgByVendorId(@RequestParam("vendorId") Long vendorId){
        Assert.notNull(vendorId, "vendorId为空！");
        return iVendorImportService.getOrgByVendorId(vendorId);
    }

    /**
     * 条件查询合作品类
     * （根据供应商Id和组织Id查询相应的品类，跨组织引入那里只查询是注册、绿牌和黄牌状态的品类）
     * @return
     */
    @PostMapping("/listOrgCategoryByParam")
    public List<OrgCategory> listOrgCategoryByParam(@RequestBody OrgCategoryQueryDTO orgCategoryQueryDTO) {
        return iVendorImportService.listOrgCategoryByParam(orgCategoryQueryDTO);
    }

    /**
     * 暂存（暂存即保存）
     * @param vendorImportSaveDTO
     * @return
     */
    @PostMapping("/saveTemporary")
    public Long saveTemporary(@RequestBody VendorImportSaveDTO vendorImportSaveDTO) {
        return iVendorImportService.saveTemporary(vendorImportSaveDTO, VendorImportStatus.DRAFT.getValue());
    }

    /**
     * 提交（可能新增的时候直接提交）
     * @param vendorImportSaveDTO
     * @return
     */
    @PostMapping("/submit")
    public Long submit(@RequestBody VendorImportSaveDTO vendorImportSaveDTO) {
        return iVendorImportService.submit(vendorImportSaveDTO, VendorImportStatus.SUBMITTED.getValue());
    }

    /**
     * 审批
     * @param importId
     */
    @GetMapping("/approve")
    public void approve(@RequestParam("importId") Long importId) {
        Assert.notNull(importId, "importId为空！");
        iVendorImportService.approve(importId);
    }

    /**
     * 驳回
     * @param importId
     */
    @GetMapping("/reject")
    public void reject(@RequestParam("importId") Long importId,@RequestParam("opinion") String opinion) {
        Assert.notNull(importId, "importId为空！");
        iVendorImportService.reject(importId,opinion);
    }


    /**
     * 撤回
     * @param importId
     */
    @GetMapping("/withdraw")
    public void withdraw(@RequestParam("importId") Long importId,@RequestParam("opinion") String opinion) {
        Assert.notNull(importId, "importId为空！");
        iVendorImportService.withdraw(importId,opinion);
    }



    @PostMapping("/delete")
    public void delete(@RequestBody VendorImport vendorImport){
        Long importId = vendorImport.getImportId();
        Assert.notNull(importId, "传入的vendorImport主键importId为空！");
        iVendorImportService.delete(importId);
    }

    /**
     * 废弃订单
     * @param importId
     */
    @GetMapping("/abandon")
    public void abandon(Long importId) {
        Assert.notNull(importId,"废弃订单id不能为空");
        iVendorImportService.abandon(importId);
    }


    /**
     * 根据供应商vendorId和orgId查询合作的品类列表
     */
    @GetMapping("/getCategoryListByParams")
    public List<VendorImportCategoryDTO> getCategoryListByParams(@RequestParam("vendorId") Long vendorId,
                                                                 @RequestParam("orgId") Long orgId) {
        Assert.notNull(vendorId, "vendorId为空！");
        Assert.notNull(orgId, "orgId为空！" );
        return iVendorImportService.getCategoryListByParams(vendorId, orgId);
    }

    /**
     * 刷新供应商组织品类关系数据(批量 刷新数据用, 页面无调用)
     */
    @PostMapping("/refresh")
    public void refresh() {
        iVendorImportService.refresh();
    }

    /**
     * 刷新供应商组织品类关系数据(刷新数据用, 页面无调用)
     */
    @PostMapping("/refreshByImportNum")
    public void refreshByImportNum(@RequestBody List<String> importNums) {
        //Assert.isTrue(StringUtils.isNotEmpty(importNum), "跨组织引入单号不能为空.");
        Assert.isTrue(CollectionUtils.isNotEmpty(importNums), "传入的跨组织引入单号集为空, 请检查重试..");
        importNums.forEach(importNum -> {
            Optional.ofNullable(importNums).orElseThrow(() -> new BaseException((LocaleHandler.getLocaleMsg("跨组织引入单号不能为空."))));
            List<VendorImport> vendorImportList = iVendorImportService.list(Wrappers.lambdaQuery(VendorImport.class).eq(VendorImport::getImportNum, importNum));
            iVendorImportService.refreshByImportNum(vendorImportList);
        });
    }

}
