package com.midea.cloud.srm.feign.supplier;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.VendorDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyScopeVendor;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.param.IntelligentRecommendParam;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.vo.IntelligentRecommendVO;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeInfoDTO;
import com.midea.cloud.srm.model.supplier.info.dto.*;
import com.midea.cloud.srm.model.supplier.info.entity.*;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorBank.VendorBankOutputParameters;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorContact.VendorContactOutputParameters;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorInfo.VendorInfoOutputParameters;
import com.midea.cloud.srm.model.supplier.soap.erp.vendor.vendorSite.VendorSiteOutputParameters;
import com.midea.cloud.srm.model.supplier.vendorimport.dto.VendorImportSaveDTO;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.FindVendorOrgCateRelParameter;
import com.midea.cloud.srm.model.supplier.vendororgcategory.vo.VendorOrgCateRelsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * <pre>
 *  供应商模块 内部调用Feign接口
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: ${DATE} ${TIME}
 *  修改内容:
 * </pre>
 */
@FeignClient("cloud-biz-supplier")
public interface SupplierClient {

    /**
     * 根据品类查找供应商
     * @param categoryIds
     * @return
     */
    @PostMapping("/info/orgCategory/queryCompanyByBusinessModeCode")
    List<VendorDto> queryCompanyByBusinessModeCode(@RequestBody List<Long> categoryIds);

    // 供应商基础信息[info] - >>>>>

    /**
     * 通过公司ID批量获取公司信息
     *
     * @param companyIds
     */
    @GetMapping("/info/companyInfo/getByIds")
    List<CompanyInfo> getComponyByIds(@RequestParam("companyIds") List<Long> companyIds);

    @PostMapping("/sup-anon/internal/companyInfo/listCompanyByCodes")
    List<CompanyInfo> listCompanyByCodes(@RequestBody List<String> companyCodes);

    @GetMapping("/info/bankInfo/getByCompanyId")
    List<BankInfo> getByBankInfoCompanyId(@RequestParam("companyId") Long companyId);

    @GetMapping("/info/siteInfo/getByCompanyId")
    List<SiteInfo> getBySiteInfoCompanyId(@RequestParam("companyId") Long companyId);

    @GetMapping("/info/companyInfo/getOrgCateServiceStatusById")
    OrgCateServiceStatusDTO getOrgCateServiceStatusById(@RequestParam("orgId") Long orgId, @RequestParam("categoryId") Long categoryId,
                                                        @RequestParam("vendorId") Long vendorId);

    @PostMapping("/info/companyInfo/listPageByDTO")
    PageInfo<CompanyInfo> listPageByDTO(@RequestBody CompanyRequestDTO companyRequestDTO);

    /**
     * 推送供应商基础信息到Erp
     *
     * @param companyInfo
     * @return ErpVendorId Erp供应商Id
     * @update xiexh12@meicloud.com 2020/09/07
     */
    @PostMapping("/sup-anon/internal/erp/vendor/sendVendorInfo")
    VendorInfoOutputParameters sendVendorInfo(@RequestBody CompanyInfo companyInfo);


    /**
     * 获取所有的供应商信息
     *
     * @return
     * @author huangbf3
     */
    @PostMapping("/info/companyInfo/listAll")
    List<CompanyInfo> listAllCompanyInfo();

    /**
     * 获取所有的供应商信息
     *
     * @return
     * @author huangbf3
     */
    @PostMapping("/info/companyInfo/CompanyInfoListAll")
    List<CompanyInfo> CompanyInfoListAll();


    /**
     * 通过公司ID获取公司信息
     *
     * @param companyId
     */
    @GetMapping("/sup-anon/internal/info/companyInfo/get")
    CompanyInfo getCompanyInfo(@RequestParam("companyId") Long companyId);

    /**
     * 通过公司ID获取公司信息【服务内部调用接口（无需登录）】
     *
     * @param companyId
     */
    @GetMapping("/sup-anon/internal/info/companyInfo/get")
    CompanyInfo getCompanyInfoForAnon(@RequestParam("companyId") Long companyId);

    /**
     * 根据companyId获取对应的级别
     *
     * @param companyIds
     * @return
     */
    @PostMapping("/sup-anon/internal/info/companyInfo/getVendorClassificationByCompanyIds")
    List<CompanyInfo> getVendorClassificationByCompanyIdsForAnon(@RequestBody Collection<Long> companyIds);


    /**
     * 根据公司查找对应的品类的牌子关系
     *
     * @param companyIdAndCategoryIds
     * @return
     */
    @PostMapping("/sup-anon/internal/info/companyInfo/getOrgRelation")
    List<OrgCategory> getOrgRelationByCompanyIdsForAnon(@RequestBody Map<String,Collection<Long>> companyIdAndCategoryIds);

    /**
     * 根据条件获取公司信息
     *
     * @param companyInfo
     */
    @PostMapping("/info/companyInfo/getByParam")
    CompanyInfo getCompanyInfoByParam(@RequestBody CompanyInfo companyInfo);

    /**
     * 修改
     *
     * @param companyInfo
     */
    @PostMapping("/info/companyInfo/modify")
    void modify(@RequestBody CompanyInfo companyInfo);


    /**
     * 修改
     *
     * @param companyInfo
     */
    @PostMapping("/sup-anon/internal/info/companyInfo/modify")
    void modifyForAnon(@RequestBody CompanyInfo companyInfo);


    // 供应商基础信息[info] - <<<<<


    // 财务信息[info] - >>>>>


    @GetMapping("/info/financeInfo/getByCompanyIdAndOrgId")
    FinanceInfo getFinanceInfoByCompanyIdAndOrgId(@RequestParam("companyId") Long companyId, @RequestParam("orgId") Long orgId);

    @GetMapping("/sup-anon/internal/info/financeInfo/getByCompanyIdAndOrgId")
    FinanceInfo getFinanceInfoByCompanyIdAndOrgIdForAnon(@RequestParam("companyId") Long companyId, @RequestParam("orgId") Long orgId);

    @GetMapping("/info/financeInfo/getByCompanyId")
    List<FinanceInfo> getByFinanceInfoCompanyId(@RequestParam("companyId") Long companyId);


    /**
     * 新增
     *
     * @param financeInfo
     */
    @PostMapping("/info/financeInfo/addFinanceInfo")
    void addFinanceInfo(@RequestBody FinanceInfo financeInfo);

    /**
     * 新增
     *
     * @param financeInfo
     */
    @PostMapping("/sup-anon/internal/info/financeInfo/addFinanceInfo")
    void addFinanceInfoForAnon(@RequestBody FinanceInfo financeInfo);

    // 财务信息[info] - <<<<<


    // 银行信息[info] - >>>>>

    /**
     * 新增
     *
     * @param bankInfo
     */
    @PostMapping("/info/bankInfo/addBankInfo")
    void addBankInfo(@RequestBody BankInfo bankInfo);


    /**
     * 新增
     *
     * @param bankInfo
     */
    @PostMapping("/sup-anon/internal/info/bankInfo/addBankInfo")
    void addBankInfoForAnon(@RequestBody BankInfo bankInfo);


    /**
     * 根据条件获取
     *
     * @param bankInfo
     * @return
     */
    @PostMapping("/info/bankInfo/getBankInfoByParm")
    BankInfo getBankInfoByParm(@RequestBody BankInfo bankInfo);


    /**
     * 根据条件获取
     *
     * @param bankInfo
     * @return
     */
    @PostMapping("/sup-anon/internal/info/bankInfo/getBankInfoByParm")
    BankInfo getBankInfoByParmForAnon(@RequestBody BankInfo bankInfo);

    /**
     * 根据条件获取银行信息 anon
     *
     * @param bankInfo
     * @return
     */
    @PostMapping("/sup-anon/internal/info/bankInfo/getBankInfosByParam")
    List<BankInfo> getBankInfosByParamForAnon(@RequestBody BankInfo bankInfo);

    /**
     * 根据条件获取银行信息
     *
     * @param bankInfo
     * @return
     */
    @PostMapping("/info/bankInfo/getBankInfosByParam")
    List<BankInfo> getBankInfosByParam(@RequestBody BankInfo bankInfo);

    /**
     * 推送供应商银行信息到Erp
     *
     * @param bankInfo
     * @update xiexh12@meicloud.com 2020/09/07
     */
    @PostMapping("/erp/vendor/sendVendorBank")
    VendorBankOutputParameters sendVendorBank(@RequestBody BankInfo bankInfo);

    // 银行信息[info] - <<<<<


    // 地点信息[info] - >>>>>

    /**
     * 新增
     *
     * @param siteInfo
     */
    @PostMapping("/info/siteInfo/addSiteInfo")
    void addSiteInfo(@RequestBody SiteInfo siteInfo);


    /**
     * 新增/修改地点信息
     *
     * @param siteInfo
     */
    @PostMapping("/sup-anon/internal/info/siteInfo/addSiteInfo")
    void addSiteInfoForAnon(@RequestBody SiteInfo siteInfo);

    /**
     * 新增/修改联系人信息
     *
     * @param contactInfo
     */
    @PostMapping("/sup-anon/internal/info/contactInfo/addContactInfo")
    void addContactInfoForAnon(@RequestBody ContactInfo contactInfo);


    /**
     * 根据条件获取
     *
     * @param siteInfo
     * @return
     */
    @PostMapping("/info/siteInfo/getSiteInfoByParm")
    SiteInfo getSiteInfoByParm(@RequestBody SiteInfo siteInfo);


    /**
     * 根据条件获取
     *
     * @param siteInfo
     * @return
     */
    @PostMapping("/sup-anon/internal/info/siteInfo/getSiteInfoByParm")
    SiteInfo getSiteInfoByParmForAnon(@RequestBody SiteInfo siteInfo);

    /**
     * 根据条件获取
     *
     * @param siteInfo
     * @return
     */
    @PostMapping("/sup-anon/internal/info/siteInfo/getSiteInfosByParam")
    List<SiteInfo> getSiteInfosByParamForAnon(@RequestBody SiteInfo siteInfo);

    /**
     * 推送供应商地点信息到Erp
     *
     * @param siteInfo
     * @update xiexh12@meicloud.com 2020/09/24
     */
    @PostMapping("/sup-anon/internal/erp/vendor/sendVendorSite")
    VendorSiteOutputParameters sendVendorSite(@RequestBody SiteInfo siteInfo);

    // 地点信息[info] - <<<<<


    // 联系人信息[info] - >>>>>

    /**
     * 根据条件获取联系人信息
     *
     * @param contactInfo
     * @return
     */
    @PostMapping("/sup-anon/internal/info/contactInfo/getContactInfoByParam")
    List<ContactInfo> getContactInfosByParamForAnon(@RequestBody ContactInfo contactInfo);

    // 联系人信息[info] - <<<<<



    // 合作组织[info] - >>>>>

    /**
     * 根据服务状态和供应商ID查询合作组织
     *
     * @param serviceStatus
     * @param companyId
     * @return
     */
    @GetMapping("/info/orgInfo/listOrgInfoByServiceStatusAndCompanyId")
    List<OrgInfo> listOrgInfoByServiceStatusAndCompanyId(@RequestParam("companyId") Long companyId, @RequestParam("serviceStatus") String... serviceStatus);


    /**
     * 根据组织ID和公司ID获取合作组织
     *
     * @param orgId
     * @param companyId
     * @return
     */
    @GetMapping("/info/orgInfo/getOrgInfoByOrgIdAndCompanyId")
    OrgInfo getOrgInfoByOrgIdAndCompanyId(@RequestParam("orgId") Long orgId, @RequestParam("companyId") Long companyId);

    /**
     * 根据条件筛选供应商组织品类关系
     *
     * @param orgCategory
     * @return
     */
    @PostMapping("/info/orgCategory/getOrgCategoryByOrgCategory")
    List<OrgCategory> getOrgCategoryByOrgCategory(@RequestBody OrgCategory orgCategory);

    /**
     * 根据供应商id集合获取组织品类关系
     * @param companyIds
     * @return
     */
    @PostMapping("sup-anon/internal/orgCategory/listOrgCategoryByCompanyIds")
    List<OrgCategory> listOrgCategoryByCompanyIds(@RequestBody  List<Long> companyIds);
    /**
     * 根据条件筛选供应商组织品类关系(orgIds companyId)
     * @param orgCategoryQueryDTO
     * @return
     */
    @PostMapping("/info/orgCategory/getOrgCategoryForCheck")
    List<OrgCategory> getOrgCategoryForCheck(@RequestBody OrgCategoryQueryDTO orgCategoryQueryDTO);

    /**
     * 获取所有组织与品类的品类服务状态
     *
     * @return
     */
    @GetMapping("/info/orgCategory/getByCategoryAll")
    List<OrgCategory> getByCategoryAll();

    /**
     * 根据公司ID查询组织与品类关系
     *
     * @param companyId
     * @return
     */
    @GetMapping("/info/orgCategory/listOrgCategoryByCompanyId")
    List<OrgCategory> listOrgCategoryByCompanyId(@RequestParam("companyId") Long companyId);

    /**
     * 获取品类集合
     *
     * @param orgCategoryList
     * @return
     */
    @PostMapping("/sup-anon/internal/orgCategory/listOrgCategory")
    List<OrgCategory> listOrgCategory(@RequestBody List<OrgCategory> orgCategoryList);

    /**
     * 根据组织ID和公司ID获取合作组织
     *
     * @param orgId
     * @param companyId
     * @return
     */
    @GetMapping("/sup-anon/internal/info/orgInfo/getOrgInfoByOrgIdAndCompanyId")
    OrgInfo getOrgInfoByOrgIdAndCompanyIdForAnon(@RequestParam("orgId") Long orgId, @RequestParam("companyId") Long companyId);


    /**
     * 新增
     *
     * @param orgInfo
     */
    @PostMapping("/info/orgInfo/addOrgInfo")
    void addOrgInfo(@RequestBody OrgInfo orgInfo);

    /**
     * 新增
     *
     * @param orgInfo
     */
    @PostMapping("/sup-anon/internal/info/orgInfo/addOrgInfo")
    void addOrgInfoForAnon(@RequestBody OrgInfo orgInfo);


    /**
     * 更新组织服务状态
     *
     * @param orgInfo
     */
    @PostMapping("/info/orgInfo/updateOrgInfoServiceStatus")
    void updateOrgInfoServiceStatus(@RequestBody OrgInfo orgInfo);


    /**
     * 更新组织服务状态
     *
     * @param orgInfo
     */
    @PostMapping("/sup-anon/internal/info/orgInfo/updateOrgInfoServiceStatus")
    void updateOrgInfoServiceStatusForAnon(@RequestBody OrgInfo orgInfo);

    // 合作组织[info] - <<<<<


    // 合作组织与品类[info] - >>>>>

    /**
     * 根据服务状态和供应商Id查询组织与品类
     *
     * @param serviceStatus
     * @param companyId
     * @return
     */
    @GetMapping("/info/orgCategory/listOrgCategoryByServiceStatusAndCompanyId")
    List<OrgCategory> listOrgCategoryByServiceStatusAndCompanyId(@RequestParam("companyId") Long companyId, @RequestParam("serviceStatus") String... serviceStatus);


    /**
     * 根据品类ID和组织ID获取组织与品类
     *
     * @param categoryId
     * @param orgId
     * @param companyId
     * @return
     */
    @GetMapping("/info/orgCategory/getByCategoryIdAndOrgIdAndCompanyId")
    OrgCategory getByCategoryIdAndOrgIdAndCompanyId(@RequestParam("categoryId") Long categoryId,
                                                    @RequestParam("orgId") Long orgId,
                                                    @RequestParam("companyId") Long companyId);

    /**
     * 根据品类ID和组织ID获取组织与品类
     *
     * @param categoryId
     * @param companyId
     * @return
     */
    @GetMapping("/info/orgCategory/getByCategoryIdAndCompanyId")
    List<OrgCategory> getByCategoryIdAndCompanyId(@RequestParam("categoryId") Long categoryId,
                                                  @RequestParam("companyId") Long companyId);

    /**
     * 根据品类ID和组织ID获取组织与品类
     *
     * @param categoryId
     * @param orgId
     * @param companyId
     * @return
     */
    @GetMapping("/sup-anon/internal/info/orgCategory/getByCategoryIdAndOrgIdAndCompanyId")
    OrgCategory getByCategoryIdAndOrgIdAndCompanyIdForAnon(@RequestParam("categoryId") Long categoryId,
                                                           @RequestParam("orgId") Long orgId,
                                                           @RequestParam("companyId") Long companyId);

    /**
     * 新增
     *
     * @param orgCategory
     */
    @PostMapping("/info/orgCategory/addOrgCategory")
    void addOrgCategory(@RequestBody OrgCategory orgCategory);

    /**
     * 新增
     *
     * @param orgCategory
     */
    @PostMapping("/sup-anon/internal/info/orgCategory/addOrgCategory")
    void addOrgCategoryForAnon(@RequestBody OrgCategory orgCategory);


    /**
     * 更新组织与品类服务状态
     *
     * @param orgCategory
     */
    @PostMapping("/info/orgCategory/updateOrgCategoryServiceStatus")
    void updateOrgCategoryServiceStatus(@RequestBody OrgCategory orgCategory);


    /**
     * 更新组织与品类服务状态
     *
     * @param orgCategory
     */
    @PostMapping("/sup-anon/internal/info/orgCategory/updateOrgCategoryServiceStatus")
    void updateOrgCategoryServiceStatusForAnon(@RequestBody OrgCategory orgCategory);


    // 合作组织与品类[info] - <<<<<

    // 供应商联系人信息[contactInfo] - >>>>>

    /**
     * 根据条件获取联系人信息
     */
    @PostMapping("/info/contactInfo/getContactInfoByParam")
    ContactInfo getContactInfoByParam(@RequestBody ContactInfo contactInfo);

    /**
     * 根据供应商id列表获取联系人信息列表
     */
    @PostMapping("/info/contactInfo/listContactInfoByParam")
    List<ContactInfo> listContactInfoByParam(@RequestBody List<Long> vendorIdList);

    /**
     * 推送供应商联系人信息到Erp
     *
     * @param contactInfo
     * @update xiexh12@meicloud.com 2020/09/07
     */
    @PostMapping("/sup-anon/internal/erp/vendor/sendVendorContact")
    VendorContactOutputParameters sendVendorContact(@RequestBody ContactInfo contactInfo);

    /**
     * 根据contactInfo的companyId查询对应的供应商联系人
     *
     * @param companyId
     * @update xiexh12@meicloud.com 2020/09/09
     */
    @PostMapping("/sup-anon/internal/info/contactInfo/getContactInfoByCompanyId")
    ContactInfo getContactInfoByCompanyId(@RequestParam("companyId") Long companyId);

    // 供应商联系人信息[contactInfo] - <<<<<


    // 供应商注册[register] - >>>>>

    /**
     * 校验验证码
     *
     * @param verifyCode
     */
    @PostMapping("/sup-anon/internal/register/checkVerifyCode")
    public void checkVerifyCodeForAnon(@RequestParam("verifyCode") String verifyCode);

    /**
     * 校验验证码
     *
     * @param verifyCode
     */
    @PostMapping("/sup-anon/internal/register/checkVerifyCodeByKey")
    public Boolean checkVerifyCodeByKey(@RequestParam("verifyCode") String verifyCode, @RequestParam("verifyKey") String verifyKey);

    // 供应商注册[register] - <<<<<

    //根据条件获取共供应商数量
    @PostMapping("/info/companyInfo/getCountByParam")
    public int getCountByParam(@RequestBody CompanyInfo companyInfo);

    /**
     * 根据名称列表批量获取公司信息
     */
    @PostMapping("/info/companyInfo/getComponyByNameList")
    List<CompanyInfo> getComponyByNameList(@RequestBody List<String> companyNameList);

    /**
     * 根据条件批量获取公司信息
     */
    @PostMapping("/info/companyInfo/getComponyByCodeList")
    Map<String,CompanyInfo> getComponyByCodeList(@RequestBody List<String> companyCodeList);

    /**
     * 判断是否已经配置模板
     */
    @GetMapping("/dim/dimTemplate/getDimTemplateCount")
    public int getDimTemplateCount();

    /**
     * 招标查询智能推荐列表
     */
    @PostMapping("/vendorOrgCategory/listIntelligentRecommendInfo")
    List<IntelligentRecommendVO> listIntelligentRecommendInfo(@RequestBody IntelligentRecommendParam recommendParam);

    /**
     * 根据合作组织ID和供应商查询基本信息
     *
     * @param companyRequestDTO
     * @return
     */
    @PostMapping("/info/companyInfo/queryVendorByNameAndOrgId")
    CompanyInfo queryVendorByNameAndOrgId(@RequestBody CompanyRequestDTO companyRequestDTO);

    /**
     * 是否有货源供应商(ceea)
     *
     * @param orgCategory
     * @return
     */
    @PostMapping("/info/orgCategory/haveSupplier")
    boolean haveSupplier(@RequestBody OrgCategory orgCategory);

    /**
     * 汇总合作品类关系
     *
     * @param orgCategorySaveDTO
     */
    @PostMapping("/info/orgCategory/collectOrgCategory")
    void collectOrgCategory(@RequestBody OrgCategorySaveDTO orgCategorySaveDTO);

    /**
     * 汇总合作品类关系(暴露外部的接口)
     * @param orgCategorySaveDTO    add by chensl26
     */
    @PostMapping("/sup-anon/internal/info/orgCategory/collectOrgCategory")
    void collectOrgCategoryForAnon(@RequestBody OrgCategorySaveDTO orgCategorySaveDTO);

    @GetMapping("/info/contactInfo/listContactInfoByCompanyId")
    List<ContactInfo> listContactInfoByCompanyId(@RequestParam("companyId") Long companyId);

    @GetMapping("/info/companyInfo/listAllForImport")
    List<CompanyInfo> listAllForImport();
    /**
     * 查询变更详情
     * @param changeId
     * @return
     */
    @GetMapping("/sup-anon/internal/change/infoChange/getInfoByChangeId")
     ChangeInfoDTO getInfoByChangeId(@RequestParam("changeId") Long changeId);
    /**
     * 供应商信息变更审批
     * @param changeInfo
     */
    @PostMapping("/sup-anon/internal/change/infoChange/InfoChangeApprove")
     void InfoChangeApprove(@RequestBody ChangeInfoDTO changeInfo);

    /**
     * 已驳回
     *
     * @param changeInfo
     */
    @PostMapping("/sup-anon/internal/change/infoChange/InfoChangeRejected")
     void InfoChangeRejected(@RequestBody ChangeInfoDTO changeInfo);
    /**
     * 已驳回
     *
     * @param changeInfo
     */
    @PostMapping("/sup-anon/internal/change/infoChange/InfoChangeWithdraw")
     void InfoChangeWithdraw(@RequestBody ChangeInfoDTO changeInfo);

    /**
     * 根据引入Id查询引入详情
     * @param importId
     * @return
     */
    @GetMapping("/sup-anon/internal/vendorImport/getVendorImportDetail")
     VendorImportSaveDTO getVendorImportDetail(@RequestParam("importId") Long importId);
    /**
     * 跨组织引入审批
     * @param importId
     */
    @GetMapping("/sup-anon/internal/vendorImport/VendorImportApprove")
     void VendorImportApprove(@RequestParam("importId") Long importId);

    /**
     * 跨组织引入驳回
     * @param importId
     */
    @GetMapping("/sup-anon/internal/vendorImport/VendorImportReject")
     void VendorImportReject(@RequestParam("importId") Long importId);


    /**
     * 跨组织引入撤回
     * @param importId
     */
    @GetMapping("/sup-anon/internal/vendorImport/VendorImportWithdraw")
     void VendorImportWithdraw(@RequestParam("importId") Long importId);

    /**
     * 获取Info
     */
    @GetMapping("/sup-anon/internal/info/companyInfo/getInfoByParam")
     InfoDTO getInfoByParam(@RequestParam("companyId")Long companyId);
    /**
     * 绿色通道审批通过
     * @modified xiexh12@meicloud.com
     */
    @PostMapping("/sup-anon/internal/info/companyInfo/companyGreenChannelApprove")
     Long companyGreenChannelApprove(@RequestBody InfoDTO infoDTO);

    /**
     * 绿色通道驳回
     *
     * @param companyInfo
     */
    @PostMapping("/sup-anon/internal/info/companyInfo/CompanyInfoReject")
     void CompanyInfoReject(@RequestBody CompanyInfo companyInfo);
    /**
     * 绿色通道撤回
     *
     * @param companyInfo
     */
    @PostMapping("/sup-anon/internal/info/companyInfo/CompanyInfoWithdraw")
     void CompanyInfoWithdraw(@RequestBody CompanyInfo companyInfo);

    @PostMapping("/sup-anon/internal/vendorOrgCateRel/findVendorOrgCateRels")
    List<VendorOrgCateRelsVO> findVendorOrgCateRels(@RequestBody FindVendorOrgCateRelParameter parameter);

    /**
     * 新增或修改供应商supplier leader关系
     */
    @PostMapping("/sup-anon/internal/supplier-leader/saveOrUpdateSupplierLeaderForAnon")
    void saveOrUpdateSupplierLeaderForAnon(@RequestParam("companyId") Long companyId,
                                           @RequestParam("responsibilityId") Long responsibilityId);


    /**
     * 根据条件获取供应商组织品类关系
     * @param orgCategory
     * @return
     */
    @PostMapping("/info/orgCategory/listOrgCategoryByParam")
    List<OrgCategory> listOrgCategoryByParam(@RequestBody OrgCategory orgCategory);

    /**
     * 根据供应商erpcode获取供应商信息
     *
     * @param erpCodes
     */
    @PostMapping("/sup-anon/internal/companyInfo/getErpCodes")
    List<CompanyInfo> getErpCodes(@RequestBody List<String> erpCodes);

    /**
     * 获取所有的供应商(根据名称)
     * @param companyInfoNameList
     * @return
     */
    @PostMapping("/info/companyInfo/listVendorByNameBatch")
    List<CompanyInfo> listVendorByNameBatch(@RequestBody List<String> companyInfoNameList);

    /**
     * 认证证件到期
     */
    @PostMapping("/info/vendorInformation/listAllManagementAttachPageByDTO")
    List<ManagementAttach> listAllManagementAttachPageByDTO(@RequestBody ManagementAttach managementAttach);

    /**
     * 根据品类id智能查询供应商（绩效项目智能添加供应商时调用）
     * @param categoryIds
     * @return
     */
    @PostMapping("/info/orgCategory/listCompanyInfosByCategoryIds")
    List<CompanyInfo> listCompanyInfosByCategoryIds(@RequestBody List<Long> categoryIds);

    /**
     * 获取供应商通过dto
     */
    @PostMapping("/info/companyInfo/listCompanyInfosByVendorDTO")
    PageInfo<CompanyInfo> listCompanyInfosByVendorDTO(@RequestBody VendorDTO vendorDTO);

    /**
     * 获取供应商通过List
     */
    @PostMapping("/info/companyInfo/listCompanyInfosByStringList")
    List<VendorDTO> listCompanyInfosByStringList(@RequestParam("list") List<String> list);

    /**
     * 通过dto获取供应商信息
     * @param vendorDTO
     * @return
     */
    @PostMapping("/info/companyInfo/getCompanyInfo")
    CompanyInfo getCompanyInfo(@RequestBody VendorDTO vendorDTO);
}
