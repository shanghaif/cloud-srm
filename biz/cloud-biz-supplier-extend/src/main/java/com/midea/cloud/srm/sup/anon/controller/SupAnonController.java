package com.midea.cloud.srm.sup.anon.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.SessionUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeInfoDTO;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotion;
import com.midea.cloud.srm.model.supplier.info.dto.InfoDTO;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCategorySaveDTO;
import com.midea.cloud.srm.model.supplier.info.entity.*;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorContact.VendorContactOutputParameters;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorInfo.VendorInfoOutputParameters;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorSite.VendorSiteOutputParameters;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.VendorImportSaveDTO;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.FindVendorOrgCateRelParameter;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.VendorOrgCateRelsVO;
import com.midea.cloud.srm.sup.change.service.IInfoChangeService;
import com.midea.cloud.srm.sup.demotion.service.ICompanyDemotionService;
import com.midea.cloud.srm.sup.info.service.*;
import com.midea.cloud.srm.sup.register.service.IRegisterService;
import com.midea.cloud.srm.sup.responsibility.service.ISupplierLeaderService;
import com.midea.cloud.srm.sup.soap.erp.vendor.service.IVendorContactService;
import com.midea.cloud.srm.sup.soap.erp.vendor.service.IVendorInfoService;
import com.midea.cloud.srm.sup.soap.erp.vendor.service.IVendorSiteService;
import com.midea.cloud.srm.sup.vendorimport.service.IVendorImportService;
import com.midea.cloud.srm.sup.vendororgcategory.service.IVendorOrgCateRelService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
 *  修改日期: 2020-4-10 16:24
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/sup-anon/internal")
public class SupAnonController extends BaseController {

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Autowired
    private IOrgCategoryService iOrgCategoryService;

    @Autowired
    private IOrgInfoService iOrgInfoService;

    @Autowired
    private IBankInfoService iBankInfoService;

    @Autowired
    private ISiteInfoService iSiteInfoService;

    @Autowired
    private IFinanceInfoService iFinanceInfoService;

    @Autowired
    private IRegisterService iRegisterService;

    @Autowired
    private IVendorInfoService iVendorInfoService;

    @Autowired
    private IVendorSiteService iVendorSiteService;

    @Autowired
    private IContactInfoService iContactInfoService;

    @Autowired
    private IVendorContactService iVendorContactService;

    @Autowired
    private IInfoChangeService iInfoChangeService;

    @Autowired
    private IVendorImportService iVendorImportService;

    @Autowired
    private IVendorOrgCateRelService relService;

    @Autowired
    private ISupplierLeaderService iSupplierLeaderService;


    @Autowired
    private ICompanyDemotionService iCompanyDemotionService;

    /**
     * 升降级审批通过
     * @param companyDemotionId
     */
    @GetMapping("/demotion/demotionPassAnon")
    public void demotionPassAnon(@RequestParam("companyDemotionId") Long companyDemotionId) {
        Assert.notNull(companyDemotionId, "升降级单据id不能为空。");
        // 审批前校验
        CompanyDemotion demotion = iCompanyDemotionService.getById(companyDemotionId);
        Assert.isTrue(Objects.equals(ApproveStatusType.SUBMITTED.getValue(), demotion.getStatus()), "只有已提交单据才能审批");
        iCompanyDemotionService.approve(demotion);
    }


    /**
     * 发验证码到邮箱 用于供应商注册是
     *
     * @param email
     */
    @GetMapping("/sendVerifyCodeToEmailNew")
    public void sendVerifyCodeToEmailNew(@RequestParam("email") String email) {
        iRegisterService.sendVerifyCodeToEmailNew(email);
    }

    /**
     * 通过邮箱校验验证码(ceea)
     *
     * @param verifyCode
     */
    @GetMapping("/checkVerifyCodeByEmail")
    public void checkVerifyCodeByEmail(@RequestParam("verifyCode") String verifyCode, @RequestParam("email") String email) {
        iRegisterService.checkVerifyCodeByEmail(verifyCode, email);
    }

    /**
     * 获取
     *
     * @param companyId
     */
    @GetMapping("/info/companyInfo/get")
    public CompanyInfo getCompanyInfoById(Long companyId) {
        Assert.notNull(companyId, "id不能为空");
        return iCompanyInfoService.getById(companyId);
    }


    /**
     * 修改
     *
     * @param companyInfo
     */
    @PostMapping("/info/companyInfo/modify")
    public void modify(@RequestBody CompanyInfo companyInfo) {
        iCompanyInfoService.updateById(companyInfo);
    }


    /**
     * 根据品类ID和组织ID和公司ID获取组织与品类
     *
     * @param categoryId
     * @param orgId
     * @param companyId
     * @return
     */
    @GetMapping("/info/orgCategory/getByCategoryIdAndOrgIdAndCompanyId")
    public OrgCategory getByCategoryIdAndOrgIdAndCompanyId(Long categoryId, Long orgId, Long companyId) {
        return iOrgCategoryService.getByCategoryIdAndOrgIdAndCompanyId(categoryId, orgId, companyId);
    }

    /**
     * 根据组织ID和公司ID获取合作组织
     *
     * @param orgId
     * @param companyId
     * @return
     */
    @GetMapping("/info/orgInfo/getOrgInfoByOrgIdAndCompanyId")
    public OrgInfo getOrgInfoByOrgIdAndCompanyId(Long orgId, Long companyId) {
        return iOrgInfoService.getOrgInfoByOrgIdAndCompanyId(orgId, companyId);
    }


    /**
     * 新增
     *
     * @param orgCategory
     */
    @PostMapping("/info/orgCategory/addOrgCategory")
    public void addOrgCategory(@RequestBody OrgCategory orgCategory) {
        Long id = IdGenrator.generate();
        orgCategory.setOrgCategoryId(id);
        iOrgCategoryService.save(orgCategory);
    }

    /**
     * 更新组织与品类服务状态
     *
     * @param orgCategory
     */
    @PostMapping("/info/orgCategory/updateOrgCategoryServiceStatus")
    public void updateOrgCategoryServiceStatus(@RequestBody OrgCategory orgCategory) {
        iOrgCategoryService.updateOrgCategoryServiceStatus(orgCategory);
    }

    /**
     * 新增
     *
     * @param orgInfo
     */
    @PostMapping("/info/orgInfo/addOrgInfo")
    public void addOrgInfo(@RequestBody OrgInfo orgInfo) {
        Long id = IdGenrator.generate();
        orgInfo.setOrgInfoId(id);
        iOrgInfoService.save(orgInfo);
    }

    /**
     * 更新组织服务状态
     *
     * @param orgInfo
     */
    @PostMapping("/info/orgInfo/updateOrgInfoServiceStatus")
    public void updateOrgInfoServiceStatus(@RequestBody OrgInfo orgInfo) {
        iOrgInfoService.updateOrgInfoServiceStatus(orgInfo);
    }

    /**
     * 根据条件获取
     *
     * @param bankInfo
     * @return
     */
    @PostMapping("/info/bankInfo/getBankInfoByParm")
    public BankInfo getBankInfoByParm(@RequestBody BankInfo bankInfo) {
        return iBankInfoService.getBankInfoByParm(bankInfo);
    }

    /**
     * 根据条件获取银行信息
     *
     * @param bankInfo
     * @return
     */
    @PostMapping("/info/bankInfo/getBankInfosByParam")
    public List<BankInfo> getBankInfosByParam(@RequestBody BankInfo bankInfo) {
        return iBankInfoService.getBankInfosByParam(bankInfo);
    }

    /**
     * 根据条件获取联系人信息
     *
     * @param contactInfo
     * @return
     */
    @PostMapping("/info/contactInfo/getContactInfoByParam")
    public List<ContactInfo> getContactInfosByParam(@RequestBody ContactInfo contactInfo) {
        return iContactInfoService.getContactInfosByParam(contactInfo);
    }

    /**
     * 新增
     *
     * @param bankInfo
     */
    @PostMapping("/info/bankInfo/addBankInfo")
    public void addBankInfo(@RequestBody BankInfo bankInfo) {
        iBankInfoService.saveOrUpdateBank(bankInfo);
    }

    /**
     * 根据条件获取
     *
     * @param siteInfo
     * @modifiedBy xiexh12@meicloud.com
     */
    @PostMapping("/info/siteInfo/getSiteInfoByParm")
    public SiteInfo getSiteInfoByParm(@RequestBody SiteInfo siteInfo) {
        return iSiteInfoService.getSiteInfoByParm(siteInfo);
    }

    /**
     * 根据条件获取
     *
     * @param siteInfo
     * @return
     */
    @PostMapping("/info/siteInfo/getSiteInfosByParam")
    public List<SiteInfo> getSiteInfosByParam(@RequestBody SiteInfo siteInfo) {
        return iSiteInfoService.getSiteInfosByParam(siteInfo);
    }

    /**
     * 新增/修改（地点信息）
     *
     * @param siteInfo
     * @modifiedBy xiexh12@meicloud.com
     */
    @PostMapping("/info/siteInfo/addSiteInfo")
    public void addSiteInfo(@RequestBody SiteInfo siteInfo) {
        iSiteInfoService.saveOrUpdateSite(siteInfo);
    }

    /**
     * 新增/修改（联系人信息）
     *
     * @param contactInfo
     * @modifiedBy xiexh12@meicloud.com
     */
    @PostMapping("/info/contactInfo/addContactInfo")
    public void addContactInfo(@RequestBody ContactInfo contactInfo) {
        iContactInfoService.saveOrUpdateContact(contactInfo);
    }

    /**
     * 根据公司ID和合作组织ID获取财务信息
     *
     * @param companyId
     * @param orgId
     * @return
     */
    @GetMapping("/info/financeInfo/getByCompanyIdAndOrgId")
    public FinanceInfo getByCompanyIdAndOrgId(Long companyId, Long orgId) {
        return iFinanceInfoService.getByCompanyIdAndOrgId(companyId, orgId);
    }

    /**
     * 新增
     *
     * @param financeInfo
     */
    @PostMapping("/info/financeInfo/addFinanceInfo")
    public void addFinanceInfo(@RequestBody FinanceInfo financeInfo) {
        Long id = IdGenrator.generate();
        financeInfo.setFinanceInfoId(id);
        iFinanceInfoService.save(financeInfo);
    }


    /**
     * 校验验证码
     *
     * @param verifyCode
     */
    @PostMapping("/register/checkVerifyCode")
    public void checkVerifyCode(String verifyCode) {
        iRegisterService.checkVerifyCode(verifyCode, SessionUtil.getRequest());
    }

    /**
     * 根据类型校验验证码
     *
     * @param verifyCode
     */
    @PostMapping("/register/checkVerifyCodeByKey")
    public Boolean checkVerifyCodeByKey(String verifyCode, String verifyKey) {
        return iRegisterService.checkVerifyCode(verifyCode, verifyKey, SessionUtil.getRequest());
    }

    //推送供应商信息

    /**
     * 获取
     *
     * @param companyId
     */
    @GetMapping("/get")
    public CompanyInfo get(Long companyId) {
        Assert.notNull(companyId, "id不能为空");
        return iCompanyInfoService.getById(companyId);
    }

    /**
     * NSrm向Erp推送供应商基础信息
     *
     * @param companyInfo
     * @return
     */
    @PostMapping("/erp/vendor/sendVendorInfo")
    public VendorInfoOutputParameters sendVendorInfo(@RequestBody CompanyInfo companyInfo) {
        VendorInfoOutputParameters response = iVendorInfoService.sendVendorInfo(companyInfo);
        return response;
    }

    /**
     * NSrm向Erp推送供应商地点信息
     *
     * @param siteInfo
     * @return
     */
    @RequestMapping("/erp/vendor/sendVendorSite")
    public VendorSiteOutputParameters sendVendorSite(@RequestBody SiteInfo siteInfo) {
        VendorSiteOutputParameters response = iVendorSiteService.sendVendorSite(siteInfo);
        return response;
    }

    /**
     * NSrm向Erp推送供应商联系人信息
     *
     * @param contactInfo
     * @return
     */
    @PostMapping("/erp/vendor/sendVendorContact")
    public VendorContactOutputParameters sendVendorContact(@RequestBody ContactInfo contactInfo) {
        VendorContactOutputParameters response = iVendorContactService.sendVendorContact(contactInfo);
        return response;
    }

    /**
     * 根据companyId查询供应商联系人信息 只返回一个联系人结果
     *
     * @param companyId
     */
    @PostMapping("/info/contactInfo/getContactInfoByCompanyId")
    public ContactInfo getContactInfoByCompanyId(@RequestParam("companyId") Long companyId) {
        Assert.notNull(companyId, "根据供应商Id查询供应商信息时供应商Id为空！");
        return iContactInfoService.getContactInfoByCompanyId(companyId);
    }

    @PostMapping("/info/companyInfo/getVendorClassificationByCompanyIds")
    public List<CompanyInfo> getVendorClassificationByCompanyIdsForAnon(@RequestBody Collection<Long> companyIds) {
        if (CollectionUtils.isEmpty(companyIds)) {
            return Collections.emptyList();
        }
        return iCompanyInfoService.list(Wrappers.lambdaQuery(CompanyInfo.class)
                .select(CompanyInfo::getCompanyId, CompanyInfo::getVendorClassification)
                .in(CompanyInfo::getCompanyId, companyIds)
        ).stream().collect(Collectors.toList());
    }

    @PostMapping("/info/companyInfo/getOrgRelation")
    public List<OrgCategory> getOrgRelationByCompanyIdsForAnon(@RequestBody Map<String, Collection<Long>> companyIdAndCategoryIds) {
        Collection<Long> companyIds = companyIdAndCategoryIds.get("companyIds");
        Collection<Long> categoryIds = companyIdAndCategoryIds.get("categoryIds");
        if (CollectionUtils.isEmpty(companyIds) || CollectionUtils.isEmpty(categoryIds)) {
            return Collections.emptyList();
        }
        return iOrgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                .select(OrgCategory::getCompanyId, OrgCategory::getCategoryId, OrgCategory::getOrgCategoryId
                        , OrgCategory::getServiceStatus)
                .in(OrgCategory::getCategoryId, categoryIds)
                .in(OrgCategory::getCompanyId, companyIds)
        );
    }

    /**
     * 获取组织品类信息
     *
     * @param orgCategoryList
     * @return
     */
    @PostMapping("/orgCategory/listOrgCategory")
    public List<OrgCategory> listOrgCategory(@RequestBody List<OrgCategory> orgCategoryList) {
        if (orgCategoryList.isEmpty()) {
            return new ArrayList<>();
        }
        Set<String> orgCodes = new HashSet<>();
        Set<String> categoryCodes = new HashSet<>();
        Set<Long> categoryIds = new HashSet<>();
        Set<Long> orgIds = new HashSet<>();
        for (OrgCategory orgCategory : orgCategoryList) {
            if (!StringUtils.isEmpty(orgCategory.getOrgCode())) {
                orgCodes.add(orgCategory.getOrgCode());
            }
            if (!StringUtils.isEmpty(orgCategory.getCategoryCode())) {
                categoryCodes.add(orgCategory.getCategoryCode());
            }
            if (Objects.nonNull(orgCategory.getCategoryId())) {
                categoryIds.add(orgCategory.getCategoryId());
            }
            if (Objects.nonNull(orgCategory.getOrgId())) {
                orgIds.add(orgCategory.getOrgId());
            }
        }
        List<OrgCategory> list = iOrgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                .in(!categoryIds.isEmpty(), OrgCategory::getCategoryId, categoryIds)
                .in(!orgIds.isEmpty(), OrgCategory::getOrgId, orgIds)
                .in(!orgCodes.isEmpty(), OrgCategory::getOrgCode, orgCodes)
                .in(!categoryCodes.isEmpty(), OrgCategory::getCategoryCode, categoryCodes)
        );
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }
        Set<Long> companyIds = list.stream().map(OrgCategory::getCompanyId).collect(Collectors.toSet());
        Function<CompanyInfo,String> getCode= companyInfo -> Optional.ofNullable(companyInfo.getCompanyCode()).orElse("");
        Map<Long, String> resultMap = iCompanyInfoService.list(Wrappers.lambdaQuery(CompanyInfo.class)
                .select(CompanyInfo::getCompanyId, CompanyInfo::getCompanyCode)
                .in(CompanyInfo::getCompanyId, companyIds)
        ).stream().collect(Collectors.toMap(CompanyInfo::getCompanyId,getCode));
        for (OrgCategory orgCategory : list) {
            orgCategory.setCompanyCode(resultMap.get(orgCategory.getCompanyId()));
        }
        return list;
    }

    /**
     * 查询变更详情
     *
     * @param changeId
     * @return
     */
    @GetMapping("/change/infoChange/getInfoByChangeId")
    public ChangeInfoDTO getInfoByChangeId(@RequestParam("changeId") Long changeId) {
        return iInfoChangeService.getInfoByChangeId(changeId);
    }

    /**
     * 供应商信息变更审批
     *
     * @param changeInfo
     */
    @PostMapping("/change/infoChange/InfoChangeApprove")
    public void InfoChangeApprove(@RequestBody ChangeInfoDTO changeInfo) {
        iInfoChangeService.commonCheck(changeInfo, ApproveStatusType.APPROVED.getValue());
        iInfoChangeService.saveOrUpdateChange(changeInfo, ApproveStatusType.APPROVED.getValue());
    }

    /**
     * 已驳回
     *
     * @param changeInfo
     */
    @PostMapping("/change/infoChange/InfoChangeRejected")
    public void InfoChangeRejected(@RequestBody ChangeInfoDTO changeInfo) {
        iInfoChangeService.commonCheck(changeInfo, ApproveStatusType.APPROVED.getValue());
        iInfoChangeService.updateChange(changeInfo, ApproveStatusType.REJECTED.getValue());
    }

    /**
     * 已驳回
     *
     * @param changeInfo
     */
    @PostMapping("/change/infoChange/InfoChangeWithdraw")
    public void InfoChangeWithdraw(@RequestBody ChangeInfoDTO changeInfo) {
        iInfoChangeService.commonCheck(changeInfo, ApproveStatusType.APPROVED.getValue());
        iInfoChangeService.updateChange(changeInfo, ApproveStatusType.WITHDRAW.getValue());
    }


    /**
     * 根据引入Id查询跨组织引入详情
     *
     * @param importId
     * @return
     */
    @GetMapping("/vendorImport/getVendorImportDetail")
    public VendorImportSaveDTO getVendorImportDetail(@RequestParam("importId") Long importId) {
        Assert.notNull(importId, "importId不能为空！");
        return iVendorImportService.getVendorImportDetail(importId);
    }

    /**
     * 跨组织引入审批
     *
     * @param importId
     */
    @GetMapping("/vendorImport/VendorImportApprove")
    public void VendorImportApprove(@RequestParam("importId") Long importId) {
        Assert.notNull(importId, "importId为空！");
        iVendorImportService.approve(importId);
    }

    /**
     * 跨组织引入驳回
     *
     * @param importId
     */
    @GetMapping("/vendorImport/VendorImportReject")
    public void VendorImportReject(Long importId) {
        Assert.notNull(importId, "importId为空！");
        iVendorImportService.reject(importId, null);
    }


    /**
     * 跨组织引入撤回
     *
     * @param importId
     */
    @GetMapping("/vendorImport/VendorImportWithdraw")
    public void VendorImportWithdraw(Long importId) {
        Assert.notNull(importId, "importId为空！");
        iVendorImportService.withdraw(importId, null);
    }

    @PostMapping("/vendorOrgCateRel/findVendorOrgCateRels")
    public List<VendorOrgCateRelsVO> findVendorOrgCateRels(@RequestBody FindVendorOrgCateRelParameter parameter) {
        return relService.findVendorOrgCateRels(parameter);
    }

    /**
     * 获取Info
     */
    @GetMapping("/info/companyInfo/getInfoByParam")
    public InfoDTO getInfoByParam(Long companyId) {
        return iCompanyInfoService.getInfoByParam(companyId);
    }

    /**
     * 绿色通道审批通过
     *
     * @modified xiexh12@meicloud.com
     */
    @PostMapping("/info/companyInfo/companyGreenChannelApprove")
    public Long companyGreenChannelApprove(@RequestBody InfoDTO infoDTO) {
        Assert.isTrue(!ObjectUtils.isEmpty(infoDTO), "审批通过对象不能为空。");
        return iCompanyInfoService.companyGreenChannelApprove(infoDTO);
    }

    /**
     * 绿色通道驳回
     *
     * @param companyInfo
     */
    @PostMapping("/info/companyInfo/CompanyInfoReject")
    public void CompanyInfoReject(@RequestBody CompanyInfo companyInfo) {
        Assert.isTrue(!ObjectUtils.isEmpty(companyInfo), "驳回对象不能为空。");
        companyInfo.setStatus(ApproveStatusType.REJECTED.getValue());
        iCompanyInfoService.updateById(companyInfo);
    }

    /**
     * 绿色通道撤回
     *
     * @param companyInfo
     */
    @PostMapping("/info/companyInfo/CompanyInfoWithdraw")
    public void CompanyInfoWithdraw(@RequestBody CompanyInfo companyInfo) {
        Assert.isTrue(!ObjectUtils.isEmpty(companyInfo), "撤回对象不能为空。");
        companyInfo.setStatus(ApproveStatusType.WITHDRAW.getValue());
        iCompanyInfoService.updateById(companyInfo);
    }

    /**
     * 新增或修改供应商supplier leader关系
     *
     * @param companyId
     * @param responsibilityId
     */
    @PostMapping("/supplier-leader/saveOrUpdateSupplierLeaderForAnon")
    public void saveOrUpdateSupplierLeaderForAnon(@RequestParam("companyId") Long companyId,
                                                  @RequestParam("responsibilityId") Long responsibilityId) {
        iSupplierLeaderService.saveOrUpdateSupplierLeader(companyId, responsibilityId);
    }


    @PostMapping("/companyInfo/listCompanyByCodes")
    List<CompanyInfo> listCompanyByCodes(@RequestBody List<String> companyCodes) {
        if (companyCodes.isEmpty()) {
            return new ArrayList<>();
        }
        return iCompanyInfoService.list(Wrappers.lambdaQuery(CompanyInfo.class)
                .in(CompanyInfo::getCompanyCode, companyCodes));
    }


    @PostMapping("orgCategory/listOrgCategoryByCompanyIds")
    List<OrgCategory> listOrgCategoryByCompanyIds(@RequestBody List<Long> companyIds) {
        if (companyIds.isEmpty()) {
            return new ArrayList<>();
        }
        return iOrgCategoryService.list(Wrappers.lambdaQuery(OrgCategory.class)
                .in(OrgCategory::getCompanyId, companyIds));
    }

    /**
     * 汇总合作品类关系
     *
     * @param orgCategorySaveDTO add by chensl26
     */
    @PostMapping("/info/orgCategory/collectOrgCategory")
    public void collectOrgCategory(@RequestBody OrgCategorySaveDTO orgCategorySaveDTO) {
        iOrgCategoryService.collectOrgCategory(orgCategorySaveDTO);
    }


    /**
     * 根据供应商erpcode获取供应商信息
     *
     * @param erpCodes
     */
    @PostMapping("/companyInfo/getErpCodes")
    public List<CompanyInfo> getErpCodes(@RequestBody List<String> erpCodes) {
        if (!CollectionUtils.isEmpty(erpCodes)) {
            QueryWrapper<CompanyInfo> queryCompanyInfoWrapper = new QueryWrapper<>();
            queryCompanyInfoWrapper.in("ERP_VENDOR_CODE", erpCodes);
            return iCompanyInfoService.list(queryCompanyInfoWrapper);
        }else {
            return null;
        }
    }

}
