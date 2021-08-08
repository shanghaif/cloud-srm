package com.midea.cloud.srm.feign.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.categorydv.entity.CategoryDv;
import com.midea.cloud.srm.model.base.configguide.entity.ConfigGuide;
import com.midea.cloud.srm.model.base.customtable.vo.CustomTableVO;
import com.midea.cloud.srm.model.base.dept.dto.DeptDto;
import com.midea.cloud.srm.model.base.dept.entity.Companies;
import com.midea.cloud.srm.model.base.dept.entity.Dept;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.base.dict.dto.DictItemQueryDTO;
import com.midea.cloud.srm.model.base.dict.entity.DictItem;
import com.midea.cloud.srm.model.base.external.entity.ExternalInterfaceLog;
import com.midea.cloud.srm.model.base.external.entity.ExternalOrder;
import com.midea.cloud.srm.model.base.formula.dto.calculate.CalculatePriceForOrderResult;
import com.midea.cloud.srm.model.base.formula.dto.calculate.SeaFoodFormulaCalculateParam;
import com.midea.cloud.srm.model.base.formula.entity.EssentialFactor;
import com.midea.cloud.srm.model.base.formula.vo.PricingFormulaDetailVO;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.MaterialOrg;
import com.midea.cloud.srm.model.base.material.dto.ItemCodeUserPurchaseDto;
import com.midea.cloud.srm.model.base.material.dto.MaterialQueryDTO;
import com.midea.cloud.srm.model.base.material.dto.PurchaseCatalogQueryDto;
import com.midea.cloud.srm.model.base.material.vo.MaterialMaxCategoryVO;
import com.midea.cloud.srm.model.base.organization.dto.OrganizationMapDto;
import com.midea.cloud.srm.model.base.organization.entity.*;
import com.midea.cloud.srm.model.base.ou.dto.query.BaseOuGroupQueryDTO;
import com.midea.cloud.srm.model.base.ou.entity.BaseOuDetail;
import com.midea.cloud.srm.model.base.ou.entity.BaseOuGroup;
import com.midea.cloud.srm.model.base.ou.vo.BaseOuGroupDetailVO;
import com.midea.cloud.srm.model.base.purchase.dto.PurchaseCategoryAllInfo;
import com.midea.cloud.srm.model.base.purchase.dto.PurchaseRateCheck;
import com.midea.cloud.srm.model.base.purchase.entity.*;
import com.midea.cloud.srm.model.base.quotaorder.QuotaHead;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaHeadParamDto;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaLineDto;
import com.midea.cloud.srm.model.base.quotaorder.dto.QuotaParamDto;
import com.midea.cloud.srm.model.base.rediscache.entity.RedisCacheMan;
import com.midea.cloud.srm.model.base.region.dto.AreaDTO;
import com.midea.cloud.srm.model.base.region.dto.AreaPramDTO;
import com.midea.cloud.srm.model.base.region.dto.CityParamDto;
import com.midea.cloud.srm.model.base.region.entity.Region;
import com.midea.cloud.srm.model.base.repush.entity.Repush;
import com.midea.cloud.srm.model.base.sceneattachment.entity.SceneAttachment;
import com.midea.cloud.srm.model.base.work.entry.Work;
import com.midea.cloud.srm.model.cm.contract.entity.ContractPartner;
import com.midea.cloud.srm.model.common.TableInfo;
import com.midea.cloud.srm.model.logistics.expense.entity.Port;
import com.midea.cloud.srm.model.workflow.entity.SrmFlowBusWorkflow;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *  基础信息模块 内部调用Feign接口
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录:
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-2-11 17:R19
 *  修改内容:
 * </pre>
 */
@FeignClient("cloud-biz-base")
public interface BaseClient {

    /**
     * 采购订单删除回写数据
     * @param quotaLineDtos
     */
    @PostMapping("/quotaorder/rollbackQuotaLineByQuotaLineDtos")
    void rollbackQuotaLineByQuotaLineDtos(@RequestBody List<QuotaLineDto> quotaLineDtos);

    /**
     * 创建采购订单回写数据
     * @param quotaLineDtos
     */
    @PostMapping("/quotaorder/updateQuotaLineByQuotaLineDtos")
    void updateQuotaLineByQuotaLineDtos(@RequestBody List<QuotaLineDto> quotaLineDtos);

    /**
     * 根据物料ID和组织ID查找有效的配额
     * @return orgId+itemId - QuotaHead
     */
    @PostMapping("/quotaorder/queryQuotaHeadByOrgIdItemId")
    Map<String, QuotaHead> queryQuotaHeadByOrgIdItemId(@RequestBody QuotaParamDto quotaParamDto);

    /**
     * 检查检查配额数据中的供应商必须在价格库中具备有效价格。
     * @return 错误信息
     */
    @PostMapping("/quotaorder/checkVendorIfValidPrice")
    String checkVendorIfValidPrice(@RequestBody QuotaHeadParamDto quotaHeadParamDto);

    /**
     * 根据组织ID+物料ID+有效时间: 查找有效价格的配额
     * @return 错误信息
     */
    @PostMapping("/quotaorder/getQuotaHeadByOrgIdItemIdDate")
    QuotaHead getQuotaHeadByOrgIdItemIdDate(@RequestBody QuotaParamDto quotaParamDto);

    /**
     * 根据名字查找地名信息
     * @return areaName+parentId  : Region
     */
    @PostMapping("/region/queryRegionByName")
    Map<String,Region> queryRegionByName(@RequestBody List<String> areaNames);

    /**
     * 根据中类编码查找小类
     * @param middleCode
     * @return
     */
    @GetMapping("/purchase/purchaseCategory/queryPurchaseCategoryByMiddleCode")
    List<PurchaseCategory> queryPurchaseCategoryByMiddleCode(@RequestParam("middleCode") String middleCode);

    /**
     * 根据redisKey检查缓存是否开启
     * @param redisKey
     */
    @GetMapping("/rediscache/checkCacheIfOpen")
    boolean checkCacheIfOpen(@RequestParam("redisKey") String redisKey);

    /**
     * 根据redisKey检查缓存是否开启
     * @param redisKey
     */
    @PostMapping("/rediscache/checkCacheIfOpenNew")
    boolean checkCacheIfOpenNew(@RequestBody RedisCacheMan redisCacheMan);

    /**
     * 查找采购分类信息
     * @return 小类ID-全路径信息
     */
    @GetMapping("/purchase/purchaseCategory/queryPurchaseCategoryAllInfo")
    Map<Long, PurchaseCategoryAllInfo> queryPurchaseCategoryAllInfo();

    /**
     * 根据业务实体,库存组织,和物料稽核查找物料编码和是否用于采购状态
     * @return
     */
    @PostMapping("/material/materialItem/queryItemIdUserPurchase")
    Map<String, String> queryItemIdUserPurchase(@RequestBody ItemCodeUserPurchaseDto itemCodeUserPurchaseDto);

    // 序列号功能[seq] - >>>>>

    /**
     * 序列号（流水号）生成；模板请参考cdc_biz_base数据库的scc_base_seq_definition表定义
     *
     * @param sequenceCode 序列号编码统一在SequenceCodeConstant常量类里面配置
     * @return
     */
    @GetMapping("/seq/get")
    String seqGen(@RequestParam("sequenceCode") String sequenceCode);


    /**
     * 根据序列号编码获取序列流水号【服务内部调用接口（无需登录）】
     *
     * @param sequenceCode
     */
    @GetMapping("/base-anon/internal/seq/get")
    String seqGenForAnon(@RequestParam("sequenceCode") String sequenceCode);

    /**
     * 批量根据序列号编码获取序列流水号
     *
     * @param sequenceCode
     */
    @GetMapping("/base-anon/internal/seq/batchGetSeq")
    List<String> batchGetSeq(@RequestParam("sequenceCode") String sequenceCode, @RequestParam("sum") int sum);

    // 序列号功能[seq] - <<<<<

    // 黑名单功能[black] - >>>>>

    @GetMapping("/base-anon/internal/blackIPs")
    Set<String> findAllBlackIPs(@RequestParam("params") Map<String, Object> params);

    // 黑名单功能[black] - <<<<<

    // 组织架构 [organ] - >>>>>

    /**
     * 通过条件查询组织与用户关系列表
     *
     * @param organizationUser
     * @return
     */
    @PostMapping("/organization/organization-user/listByParam")
    List<OrganizationUser> listOrganUserByParam(@RequestBody OrganizationUser organizationUser);

    /**
     * 通过条件查询组织与用户关系列表
     *
     * @param organizationUser
     * @return
     */
    @PostMapping("/base-anon/internal/organization-user/listByParam")
    List<OrganizationUser> annoListOrganUserByParam(@RequestBody OrganizationUser organizationUser);

    /**
     * 批量新增组织与用户关系
     *
     * @param organizationUsers
     */
    @PostMapping("/organization/organization-user/addBatch")
    void addOrganUserBatch(@RequestBody List<OrganizationUser> organizationUsers);

    /**
     * 通过用户ID删除组织与用户信息
     *
     * @param userId
     */
    @GetMapping("/organization/organization-user/deleteByUserId")
    void deleteOrganUserByUserId(@RequestParam("userId") Long userId);

    /**
     * 查询所有的组织
     *
     * @return
     */
    @PostMapping("/organization/organization/listAll")
    List<Organization> listAllOrganization();

    /**
     * 根据orgIdList 查询指定的组织
     *
     * @param organization
     * @return
     */
    @PostMapping("/organization/organization/listOrganization")
    List<Organization> listOrganization(@RequestBody Organization organization);

    /**
     * 根据组织ID获取组织
     *
     * @param organizationId
     */
    @GetMapping("/organization/organization/get")
    Organization get(@RequestParam("organizationId") Long organizationId);

    /**
     * 通过FullPathId查找
     *
     * @return
     */
    @GetMapping("/organization/organization-user/queryByFullPathId")
    List<OrganizationUser> queryByFullPathId(@RequestParam(value = "fullPathId") String fullPathId);

    /**
     * 查找父组织id
     *
     * @param organizationId
     * @return
     */
    @GetMapping("/organization/relation/queryByOrganizationId")
    List<OrganizationRelation> queryByOrganizationId(@RequestParam("organizationId") Long organizationId);

    // 组织架构 [organ] - <<<<<

    // 组织关系[relation] - >>>>>

    /**
     * 根据关系ID与组织ID查找下级组织
     *
     * @return
     */
    @PostMapping("/organization/relation/listChildrenOrganization")
    List<OrganizationRelation> listChildrenOrganization(@RequestParam("organizationId") Long organizationId);

    // 组织关系 [relation] - <<<<<

    // 邮件服务[email] - >>>>>

    /**
     * 发送邮件
     */
    @PostMapping("/base-anon/internal/email/send")
    void sendEmail(@RequestParam("to") String to, @RequestParam("subject") String subject, @RequestParam("content") String content);

    // 邮件服务[email] - <<<<<

    // 字典[dict-item] - >>>>>

    /**
     * 字典code来查询字典条目
     *
     * @return
     * @author huangbf3
     */
    @GetMapping("/dict/base-dict-item/listAllByDictCode")
    List<DictItemDTO> listAllByDictCode(@RequestParam("dictCode") String dictCode);

    /**
     * 多个字典code来查询字典条目
     *
     * @return
     */
    @PostMapping("/dict/base-dict-item/listByDictCode")
    List<DictItemDTO> listByDictCode(@RequestBody List<String> dictCodes);

    /**
     * 多条件来查询字典条目
     *
     * @return
     * @author huangbf3
     */
    @GetMapping("/dict/base-dict-item/listAllByParam")
    List<DictItemDTO> listAllByParam(@RequestBody DictItemDTO requestDto);

    /**
     * 根据字典码和字典条目名称查询字典条目名称
     * @param dto
     * @return
     */
    @PostMapping("/dict/base-dict-item/getDictItemsByDictCodeAndDictItemNames")
    List<DictItemDTO> getDictItemsByDictCodeAndDictItemNames(@RequestBody DictItemQueryDTO dto);

    // 字典[dict-item] - <<<<<

    // 物料[material] - >>>>>

    /**
     * 查询所有物料
     *
     * @return
     */
    @PostMapping("/material/materialItem/listAll")
    List<MaterialItem> listAllMaterialItem();

    // 物料[material] - <<<<<


    /**
     * 根据条件查询单位列表
     */
    @PostMapping("/purchase/purchaseUnit/listByParam")
    List<PurchaseUnit> listPurchaseUnitByParam(@RequestBody PurchaseUnit purchaseUnit);

    /**
     * 根据编码批量查询单位列表
     */
    @PostMapping("/purchase/purchaseUnit/listPurchaseUnitByCodeList")
    List<PurchaseUnit> listPurchaseUnitByCodeList(@RequestBody List<String> purchaseUnitCodeList);

    /**
     * 查询所有生效单位
     *
     * @return
     */
    @PostMapping("/purchase/purchaseUnit/listAll")
    List<PurchaseUnit> listAllEnablePurchaseUnit();

    // 场景附件管理[sceneAttachment] - >>>>>

    /**
     * 分页条件查询
     *
     * @param sceneAttachment
     * @return
     */
    @PostMapping("/sceneAttachment/listPageByParm")
    PageInfo<SceneAttachment> listPageByParm(@RequestBody SceneAttachment sceneAttachment);

    // 场景附件管理[sceneAttachment] - <<<<<

    // 任务管理[work] - >>>>>

    /**
     * 保存任务(接口已废弃)
     *
     * @param work
     * @return
     */
    @Deprecated
    @PostMapping("/work/save")
    void saveWork(@RequestBody Work work);

    /**
     * 完成任务更新(接口已废弃)
     *
     * @param fromId
     * @param topic
     * @return
     */
    @Deprecated
    @GetMapping("/work/finishWork")
    void finishWork(@RequestParam("fromId") Long fromId, @RequestParam("topic") String topic);


    /**
     * 取消任务(接口已废弃)
     *
     * @param fromId
     * @param topic
     */
    @Deprecated
    @GetMapping("/work/cancalWork")
    void cancalWork(@RequestParam("fromId") Long fromId, @RequestParam("topic") String topic);

    // 任务管理[work] - <<<<<

    /**
     * 保存或更新
     *
     * @param configGuide
     */
    @PostMapping("/configGuide/saveOrUpdateConfigGuide")
    void saveOrUpdateConfigGuide(@RequestBody ConfigGuide configGuide);

    /**
     * 条件查询物料接口
     */
    @PostMapping("/material/materialItem/listByParam")
    List<MaterialItem> listMaterialByParam(@RequestBody MaterialItem materialItem);

    /**
     * 按编码列表批量查询物料
     */
    @PostMapping("/material/materialItem/listMaterialByCodeBatch")
    List<MaterialItem> listMaterialByCodeBatch(@RequestBody List<String> materialCodeList);

    /**
     * 根据名称批量查询采购分类
     */
    @PostMapping("/purchase/purchaseCategory/listByNameBatch")
    List<PurchaseCategory> listPurchaseCategoryByNameBatch(@RequestBody List<String> purchaseCategoryNameList);

    /**
     * 根据level查询List
     */
    @PostMapping("/purchase/purchaseCategory/listByLevel")
    List<PurchaseCategory> listByLevel(@RequestBody PurchaseCategory purchaseCategory);

    /**
     * 自定义表格
     *
     * @param businessId
     * @param businessType
     * @return
     */
    @GetMapping("/customTable/queryCustomTableList")
    List<CustomTableVO> queryCustomTableList(@RequestParam(name = "businessId", required = true) Long businessId, @RequestParam(name = "businessType", required = true) String businessType);

    /**
     * 根据条件获取组织信息
     *
     * @param organization
     */
    @PostMapping("/organization/organization/getByParam")
    Organization getOrganizationByParam(@RequestBody Organization organization);

    /**
     * 根据组织名称查询组织
     *
     * @modifiedBy xiexh12@meicloud.com 10-09 17:07
     */
    @PostMapping("/organization/organization/getOrganization")
    Organization getOrganization(@RequestBody Organization organization);


    @PostMapping("/base-anon/internal/organization/organization/getOrganizationsByNames")
    Map<String, Organization> getOrganizationsByNames(@RequestBody Collection<String> names);


    // 订单管理[externalOrder] - >>>>>

    /**
     * 获取所有的订单（仅支持内部调用，不暴露到网关）
     *
     * @return
     */
    @PostMapping("/base-anon/internal/externalOrder/listAll")
    List<ExternalOrder> listExternalOrder();

    /**
     * 根据组织名称列表查询组织信息列表
     *
     * @return
     */
    @PostMapping("/organization/organization/getOrganizationByNameList")
    List<Organization> getOrganizationByNameList(@RequestBody List<String> orgNameList);

    /**
     * 根据组织名称列表查询组织信息列表（内部调用）
     */
    @PostMapping("/base-anon/internal/organization/organization/getOrganizationByNameList")
    List<Organization> getOrganizationByNameListAnon(@RequestBody List<String> orgNameList);

    // 订单管理[externalOrder] - <<<<<


    // 币种设置[purchaseCurrency] - >>>>>
    /*
     * @return
     */
    @PostMapping("/purchase/purchaseCurrency/listAll")
    List<PurchaseCurrency> listAllPurchaseCurrency();

    // 币种设置[purchaseCurrency] - <<<<<

    /**
     * 查询所有生效
     *
     * @return
     */
    @PostMapping("/purchase/purchaseCurrency/listAll")
    List<PurchaseCurrency> listCurrencyAll();


    // 税率设置[purchaseTax] - >>>>>

    /**
     * 分页条件查询
     *
     * @param purchaseTax
     * @return
     */
    @PostMapping("/purchase/purchaseTax/listPage")
    PageInfo<PurchaseTax> listPagePurchaseTax(@RequestBody PurchaseTax purchaseTax);

    // 税率设置[purchaseTax] - <<<<<

    // 采购品类设置[purchaseCurrency] - >>>>>

    /**
     * 条件查询采购分类接口
     */
    @PostMapping("/purchase/purchaseCategory/getByParm")
    PurchaseCategory getPurchaseCategoryByParm(@RequestBody PurchaseCategory purchaseCategory);

    /**
     * 根据名称
     *
     * @param codes
     * @return
     */
    @PostMapping("/base-anon/internal/purchase/purchaseCategory/getCategoryByCodes")
    Map<String, PurchaseCategory> getCategoryByCodes(Collection<String> codes);

    // 采购品类设置[purchaseCurrency] - <<<<<

    /**
     * 根据字段模糊查询
     *
     * @param param   编码或名字
     * @param enabled 是否激活(N/Y)
     * @return
     */
    @GetMapping("/purchase/purchaseCategory/queryByParam")
    List<PurchaseCategory> queryPurchaseCategoryByParam(@RequestParam(name = "param") String param, @RequestParam(name = "enabled") String enabled);

    // 税率配置 []  >>>>>

    /**
     * 获取
     *
     * @param taxKey
     * @param language
     */
    @GetMapping("/purchase/purchaseTax/getByTaxKeyAndLanguage")
    PurchaseTax getByTaxKeyAndLanguage(@RequestParam(name = "taxKey", required = true) String taxKey,
                                       @RequestParam(name = "language", required = true) String language);

    // 税率配置 []  <<<<<

    /**
     * 根据条件查找品类分工
     *
     * @param categoryDv
     * @return
     */
    @PostMapping("/categoryDv/queryUserByCategoryId")
    List<CategoryDv> queryUserByCategoryId(@RequestBody CategoryDv categoryDv);

    /**
     * 查询所有省份
     *
     * @return
     */
    @PostMapping("/region/listAll")
    List<Region> listAll();

    /**
     * 根据类型查询地方信息
     * @return
     */
    @PostMapping("/region/queryRegion")
    List<AreaDTO> queryRegionByType(@RequestParam("queryType") String queryType, @RequestParam(name = "parentId",required = false) Long parentId);

    /**
     * 查询指定类型的地址
     *
     * @return
     */
    @PostMapping("/region/queryRegionById")
    List<AreaDTO> queryRegionById(@RequestBody AreaPramDTO areaPramDTO);

    /**
     * 查询所有生效
     *
     * @return
     */
    @PostMapping("/purchase/purchaseTax/listAll")
    List<PurchaseTax> listTaxAll();

    /**
     * 检查城市
     *
     * @return
     */
    @PostMapping("/region/checkCity")
    List<AreaDTO> checkCity(@RequestBody CityParamDto cityParamDto);

    /**
     * 根据子id获取父列表
     *
     * @return
     */
    @PostMapping("/organization/organization/getFatherByChild")
    OrganizationMapDto getFatherByChild(@RequestBody List<Long> orgIds);


    /**
     * 根据业务实体查找库存组织
     *
     * @param parentId
     * @return
     */
    @GetMapping("/organization/invOrganizations/queryIvnByParent")
    List<Organization> queryIvnByOuId(@RequestParam("parentId") Long parentId);

    /**
     * 根据库存组织编码查询库存组织
     *
     * @update xiexh12@meicloud.com
     */
    @PostMapping("/base-anon/internal/organization/invOrganizations/queryInvByInvCode")
    InvOrganization queryInvByInvCode(@RequestParam("invCode") String invCode);

    /**
     * 根据ids查找组织
     */
    @PostMapping("/base-anon/internal/organization/organization/getOrganizationsByIds")
    List<Organization> getOrganizationsByIds(@RequestBody Collection<Long> orgIds);

    /**
     * 根据条件查询组织信息
     *
     * @param organization
     * @return
     */
    @PostMapping("/base-anon/internal/organization/listOrganizationByParam")
    List<Organization> listOrganizationByParam(@RequestBody Organization organization);

    /**
     * 查询所有库存组织
     *
     * @return
     */
    @PostMapping("/organization/invOrganizations/listInvOrganizationsAll")
    List<InvOrganization> listInvOrganizationsAll();

    /**
     * 新增
     *
     * @param externalInterfaceLog
     */
    @PostMapping("/base-anon/external/external-interface-log/add")
    void addLog(@RequestBody ExternalInterfaceLog externalInterfaceLog);

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @GetMapping("/base/base-ou-group/queryGroupInfoById")
    BaseOuGroupDetailVO queryOuInfoById(@RequestParam("id") Long id);

    /**
     * 根据id获取ou组详情
     *
     * @param id
     * @return
     */
    @GetMapping("/base/base-ou-group/queryById")
    BaseOuGroupDetailVO queryOuDetailById(@RequestParam("id") Long id);

    /**
     * 根据idList返回组信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/base/base-ou-group/queryGroupInfoByIds")
    List<BaseOuGroupDetailVO> queryOuInfoByIds(@RequestBody Collection<Long> ids);

    /**
     * 根据idList返回组详情信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/base/base-ou-group/queryGroupInfoDetailByIds")
    List<BaseOuGroupDetailVO> queryOuInfoDetailByIds(@RequestBody Collection<Long> ids);

    /**
     * 根据币种code返回汇率
     *
     * @param fromCode
     * @param toCode
     * @return
     */
    @GetMapping("/purchase/latest-gidaily-rate/getRateByFromTypeAndToType")
    BigDecimal getRateByFromTypeAndToType(@RequestParam("from") String fromCode, @RequestParam("to") String toCode);

    /**
     * 校验是否有对应的税率
     *
     * @param check
     * @return
     */
    @PostMapping("/purchase/latest-gidaily-rate/checkRateFromCodeToCode")
    List<String> checkRateFromCodeToCode(@RequestBody PurchaseRateCheck check);

    /**
     * 根据id返回公式详情
     */
    @GetMapping("/bid/pricing-formula/getPricingFormulaById")
    PricingFormulaDetailVO getPricingFormulaById(@RequestParam("headerId") Long headerId);

    /**
     * 根据ids返回公式详情列表
     *
     * @param ids
     * @return
     */
    @GetMapping("/bid/pricing-formula/getPricingFormulaByIds")
    List<PricingFormulaDetailVO> getPricingFormulaByIds(@RequestParam("ids") Long[] ids);

    /**
     *  根据公式id、供应商报价、基价保存结果计算公式（订单使用）
     * @param param
     * @return
     */
    @PostMapping("/bid/pricing-formula/calculatePriceForOrder")
    CalculatePriceForOrderResult calculatePriceForOrder(@RequestBody SeaFoodFormulaCalculateParam param);

    /**
     * 根据公式id、供应商报价、基价保存结果计算公式（批量计算）
     * @param params
     * @return
     */
    @PostMapping("/bid/pricing-formula/calculatePriceForOrderBatch")
    Map<String, CalculatePriceForOrderResult> calculatePriceForOrderBatch(@RequestBody List<SeaFoodFormulaCalculateParam> params);

    /**
     * 查找最小级采购品类
     *
     * @param purchaseCategories
     * @return
     */
    @PostMapping("/purchase/purchaseCategory/queryMinLevelCategory")
    List<PurchaseCategory> queryMinLevelCategory(@RequestBody List<PurchaseCategory> purchaseCategories);

    /**
     * 根据id查询品类信息
     * @param purchaseCategoryIds
     * @return
     */
    @PostMapping("/purchase/purchaseCategory/listCategoryByIds")
    List<PurchaseCategory> listCategoryByIds(@RequestBody List<Long> purchaseCategoryIds);

    /**
     * 根据物料小类查询物料大类
     *
     * @param purchaseCategory
     * @return
     */
    @PostMapping("/purchase/purchaseCategory/queryMaxLevelCategory")
    PurchaseCategory queryMaxLevelCategory(@RequestBody PurchaseCategory purchaseCategory);

    /**
     * 根据物料编码获取物料维护
     *
     * @param materialCode
     * @return
     * @Author dengyl23@meicloud.com
     */
    @GetMapping("/material/materialItem/findMaterialItemByMaterialCode")
    MaterialItem findMaterialItemByMaterialCode(@RequestParam("materialCode") String materialCode);

    /**
     * 根据id修改物料维护信息
     *
     * @param materialItem
     */
    @PostMapping("/material/materialItem/modify")
    void updateMaterialItemById(@RequestBody MaterialItem materialItem);

    /**
     * 获取所有不重复编码的物料
     */
    @GetMapping("/base-anon/internal/MaterialItemListAll")
    List<MaterialItem> MaterialItemListAlls();

    /**
     * 查询所有物料小类
     *
     * @return
     */
    @GetMapping("/base-anon/internal/listParamCategoryMin")
    List<PurchaseCategory> listParamCategoryMin();

    /**
     * 查询所有货币
     *
     * @return
     */
    @GetMapping("/base-anon/internal/PurchaseCurrencyListAll")
    List<PurchaseCurrency> PurchaseCurrencyListAll();

    /**
     * 根据参数查询货币
     *
     * @return
     */
    @PostMapping("/purchase/purchaseCurrency/getPurchaseCurrencyByParam")
    PurchaseCurrency getPurchaseCurrencyByParam(@RequestBody PurchaseCurrency purchaseCurrency);

    /**
     * 查询所有库存组织
     *
     * @return
     */
    @GetMapping("/base-anon/internal/InvOrganizationListAll")
    List<InvOrganization> InvOrganizationListAll();

    /**
     * 查询所有库存组织
     *
     * @return
     */
    @GetMapping("/base-anon/internal/iOrganizationServiceListAll")
    List<Organization> iOrganizationServiceListAll();

    /**
     * 查询OU组
     *
     * @param queryDTO
     * @return
     */
    @PostMapping("/base/base-ou-group/queryOuDetailByDto")
    List<BaseOuGroupDetailVO> queryOuDetailByDto(@RequestBody BaseOuGroupQueryDTO queryDTO);

    @GetMapping("/organization/organization/getOrgParentCodeByOrgId")
    String getOrgParentCodeByOrgId(@RequestParam Long orgId);

    /**
     * @Description 根据条件查询物料列表
     * @Param: [materialItem]
     * @Return: com.github.pagehelper.PageInfo<com.midea.cloud.srm.model.base.material.MaterialItem>
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/9/26 16:14
     */
    @PostMapping("/material/materialItem/listPage")
    PageInfo<MaterialItem> listMaterialItemPage(@RequestBody MaterialItem materialItem);


    /**
     * @Description 根据目录化标识查询采购目录
     * @Param: [materialQueryDTO]
     * @Return: java.util.List<com.midea.cloud.srm.model.base.material.dto.MaterialQueryDTO>
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/9/26 16:46
     */
    @PostMapping("/material/materialItem/listCeeaListPurchaseCatalog")
    List<PurchaseCatalogQueryDto> listCeeaListPurchaseCatalog(@RequestBody List<PurchaseCatalogQueryDto> purchaseCatalogQueryDtoList);

    /**
     * 根据物料id获取物料库存组织信息
     *
     * @param materialIds
     * @return
     */
    @PostMapping("/base-anon/internal/listMaterialOrgByMaterialIds")
    List<MaterialOrg> listMaterialOrgByMaterialIds(@RequestBody Collection<Long> materialIds);

    /*
     * @Description  物料与库存组织关系
     * @Date         2020/10/16 17:35
     * @Author       chenjj120@meicloud.com
     * @return
     * @Deprecated    调试的时候用就行了，开发中不能用，这个接口会返回上百万数据导致内存爆炸；
     **/
    @Deprecated
    @GetMapping("/base/materialOrg/listAll")
    List<MaterialOrg> listMaterialOrgAll();

    /**
     * 根据OU组编码查询详情
     *
     * @param ouGroupCode
     * @return
     */
    @GetMapping("/base/base-ou-group/queryBaseOuDetailByCode")
    BaseOuDetail queryBaseOuDetailByCode(@RequestParam("ouGroupCode") String ouGroupCode);

    @GetMapping("/purchase/purchaseCategory/listByCategoryCode")
    List<PurchaseCategory> listByCategoryCode(@RequestParam("categoryCode") String categoryCode);

    /**
     * 通过组织获取全部部门(包括虚拟部门,结算用)
     *
     * @param organization
     * @return
     */
    @PostMapping("/organization/organization/getAllDeptByOrganization")
    List<DeptDto> getAllDeptByOrganization(@RequestBody Organization organization);

    /**
     * 查询允许超计划发货的物料小类
     *
     * @return
     */
    @GetMapping("/purchase/purchaseCategory/listMinByIfBeyondDeliver")
    List<PurchaseCategory> listMinByIfBeyondDeliver();

    /**
     * 查询是否为允许超计划发货的物料小类
     *
     * @return
     */
    @GetMapping("/purchase/purchaseCategory/MinByIfBeyondDeliver")
    PurchaseCategory MinByIfBeyondDeliver(@RequestParam("categoryId") Long categoryId);

    /**
     * 判断是否为用于执行到货计划物料
     *
     * @return
     */
    @GetMapping("/purchase/purchaseCategory/MinByIfDeliverPlan")
    PurchaseCategory MinByIfDeliverPlan(@RequestParam("categoryId") Long categoryId);

    /**
     * 判断是否为用于执行到货计划物料
     *
     * @return
     */
    @GetMapping("/purchase/purchaseCategory/checkByIfDeliverPlan")
    PurchaseCategory checkByIfDeliverPlan(@RequestParam("struct") String struct);

    /**
     * 获取所有用于执行到货计划物料
     *
     * @return
     */
    @GetMapping("/purchase/purchaseCategory/listMinByIfDeliverPlan")
    List<PurchaseCategory> listMinByIfDeliverPlan();

    /**
     * 保存接口重推信息
     *
     * @param repush
     */
    @PostMapping("/base-anon/internal/saveRepush")
    void saveRepush(@RequestBody Repush repush);

    /**
     * 更新接口重推信息
     *
     * @param repush
     */
    @PostMapping("/base-anon/internal/updateRepush")
    void updateRepush(@RequestBody Repush repush);

    /**
     * 根据物料id查询大类
     */
    @PostMapping("/base-anon/internal/queryCategoryMaxCodeByMaterialIds")
    List<MaterialMaxCategoryVO> queryCategoryMaxCodeByMaterialIds(@RequestBody Collection<Long> materialIds);

    /**
     * 检查物料的大类编码是否包含50
     *
     * @param materialIds
     * @return
     */
    @PostMapping("/purchase/purchaseCategory/checkBigClassCodeIsContain50")
    boolean checkBigClassCodeIsContain50(@RequestBody List<Long> materialIds);

    /**
     * 通过采购分类全路径名称查找采购分类
     *
     * @param categoryFullNameList
     * @return
     */
    @PostMapping("/purchase/purchaseCategory/queryPurchaseByCategoryFullName")
    List<PurchaseCategory> queryPurchaseByCategoryFullName(@RequestBody List<String> categoryFullNameList);

    /**
     * 根据UO组编码查找OU组
     *
     * @param ouCodes
     * @return
     */
    @PostMapping("/base/base-ou-group/queryOuByOuCodeList")
    List<BaseOuGroup> queryOuByOuCodeList(@RequestBody List<String> ouCodes);


    @PostMapping("/base/location/getLocationsByOrganization")
    List<Location> getLocationsByOrganization(@RequestBody Organization organization);

    @GetMapping("/base/location/getgetLocationsByLocationName")
    Location getgetLocationsByLocationName(@RequestParam("locationName") String locationName);

    @PostMapping("/material/materialItem/listMaterialItemsByIds")
    List<MaterialItem> listMaterialItemsByIds(@RequestBody List<Long> materialIds);

    @GetMapping("/material/materialItem/queryTaxByItemForOrder")
    List<PurchaseTax> queryTaxByItemForOrder(@RequestParam("materialId") Long materialId);

    @PostMapping("/material/materialItem/listMaterialByIdBatch")
    List<MaterialItem> listMaterialByIdBatch(@RequestBody Collection<Long> materialIds);

    @GetMapping("/material/materialItem/listAllForTranferOrder")
    List<MaterialItem> listAllMaterialItemForTranferOrder(@RequestBody MaterialQueryDTO materialQueryDTO);

    /**
     * 根于业务实体查找合作伙伴信息
     *
     * @param ouId
     * @return
     */
    @GetMapping("/orgcompany/company-head/queryContractPartnerByOuId")
    ContractPartner queryContractPartnerByOuId(@RequestParam("ouId") Long ouId);

    /**
     * 根于业务实体查找合作伙伴信息
     *
     * @param ouIdList
     * @return
     */
    @PostMapping("/orgcompany/company-head/queryContractPartnerByOuIdList")
    List<ContractPartner> queryContractPartnerByOuIdList(@RequestBody List<Long> ouIdList);

    @GetMapping("/organization/organization/listAllOrganizationForImport")
    List<Organization> listAllOrganizationForImport();

    @GetMapping("/material/materialItem/listAllForImportPriceLibrary")
    List<MaterialItem> listAllForImportPriceLibrary();

    @GetMapping("/purchase/purchaseCurrency/listAllCurrencyForImport")
    List<PurchaseCurrency> listAllCurrencyForImport();

    @GetMapping("/base-anon/internal/listTable")
    List<TableInfo> listTable();


    /**
     * 根据物料编码列表查找物料信息和公式
     *
     * @param materialCodeList
     */
    @PostMapping("/material/materialItem/queryMaterialItemByCodes")
    List<MaterialItem> queryMaterialItemByCodes(@RequestBody List<String> materialCodeList);

    /**
     * 根据物料编码列表查询物料列表
     */
    @PostMapping("/base-anon/internal/materialItem/listMaterialItemsByCodes")
    Map<String, MaterialItem> listMaterialItemsByCodes(@RequestBody List<String> materialCodes);
    /**
     * 根据物料编码列表查询物料及小类信息列表
     */
    @PostMapping("/base-anon/internal/materialItem/ListMaterialItemByCategoryCode")
    Map<String, MaterialItem> ListMaterialItemByCategoryCode(@RequestBody List<String> materialCodes);

    /**
     * 获取祖先部门
     *
     * @param deptid
     */
    @GetMapping("/dept/queryGrandParentDept")
    Dept queryGrandParentDept(@RequestParam("deptid") String deptid);

    /**
     * 获取
     *
     * @param deptid
     */
    @GetMapping("/dept/getDept")
    Dept get(@RequestParam("deptid") String deptid);

    /**
     * 通过采购分类名字列表查询小类
     *
     * @param categoryNameList
     * @return
     */
    @PostMapping("/purchase/purchaseCategory/queryPurchaseCategoryByLevelNames")
    Map<String, PurchaseCategory> queryPurchaseCategoryByLevelNames(@RequestBody List<String> categoryNameList);

    /**
     * 通过采购分类编码列表查询小类
     *
     * @param categoryCodeList
     * @return
     */
    @PostMapping("/purchase/purchaseCategory/queryPurchaseCategoryByLevelCodes")
    Map<String, PurchaseCategory> queryPurchaseCategoryByLevelCodes(@RequestBody List<String> categoryCodeList);

    /**
     * 通过小类ID查找全名
     *
     * @param ids
     * @return
     */
    @PostMapping("/purchase/purchaseCategory/queryCategoryFullNameByLevelIds")
    Map<String, String> queryCategoryFullNameByLevelIds(@RequestBody List<Long> ids);

    @PostMapping("/organization/organization/getBuCodeByOuGroupId")
    List<String> getBuCodeByOuGroupId(@RequestBody Collection<Long> ouGroupId);

    @PostMapping("/organization/organization/getBuCodeByOrgIds")
    List<String> getBuCodeByOrgIds(@RequestBody Collection<Long> ouGroupId);

    /**
     * 根据开户行查询银行分行编码
     *
     * @param openingBanks
     * @return
     */
    @PostMapping("/organization/erp-branch-bank/getUnionCodeByOpeningBanks")
    Map<String, String> getUnionCodeByOpeningBanks(@RequestBody List<String> openingBanks);

    /**
     * 根据公司名称查找公司
     *
     * @return
     */
    @PostMapping("/dept/companies/queryCompaniesByCompanyFullNameList")
    Map<String, Companies> queryCompaniesByCompanyFullNameList(@RequestBody List<String> companyFullNameList);

    /**
     * 根据部门名称获取部门信息
     *
     * @param descrList
     * @return 公司+部门  - > 部门信息
     */
    @PostMapping("/dept/queryDeptByDescrList")
    Map<String, Dept> queryDeptByDescrList(@RequestBody List<String> descrList);

    /**
     * 根据岗位名称查找岗位信息
     *
     * @param descrList
     * @return
     */
    @PostMapping("/organization/position/queryPositionBydescrshortList")
    Map<String, Position> queryPositionBydescrshortList(@RequestBody List<String> descrList);

    /**
     * 根据用户id获取组织权限信息
     *
     * @param userIds
     * @return 用户id+组织id -> 组织
     */
    @PostMapping("/organization/organization/queryOrganizationUserBuUserIds")
    Map<String, OrganizationUser> queryOrganizationUserBuUserIds(@RequestBody List<Long> userIds);

    /**
     * 保存用户组织权限数据
     *
     * @param organizationUsers
     */
    @PostMapping("/organization/organization/saveOrganizationUserImport")
    void saveOrganizationUserImport(@RequestBody List<OrganizationUser> organizationUsers);

    /**
     * 获取审批流信息
     */
    @GetMapping("/base-anon/internal/getSrmFlowBusWorkflow")
    SrmFlowBusWorkflow getSrmFlowBusWorkflow(@RequestParam("formInstanceId") Long formInstanceId);

    /**
     * 批量获取审批流信息
     * @param formInstanceIds
     * @return
     */
    @PostMapping("/base-anon/internal/workflow/getSrmFlowBusWorkflowList")
    List<SrmFlowBusWorkflow> getSrmFlowBusWorkflowList(@RequestBody List<Long> formInstanceIds);

    /**
     * 获取部门信息
     * @param wrapper
     * @return
     */
    @PostMapping("/base-anon/internal/dept/getDeptListbyWrapper")
    List<Dept> getDeptListbyWrapper(@RequestBody QueryWrapper<Dept> wrapper);

    /**
     * 获取所有用于执行到货计划，及周期大于0的的品类
     *
     * @return
     */
    @GetMapping("/base-anon/internal/purchaseCategory/listMinByDPByLockPeriod")
    List<PurchaseCategory> listMinByDPByLockPeriod();

    @PostMapping("/base-anon/internal/queryTaxByItemBatch")
    Map<Long, Set<String>> queryTaxByItemBatch(@RequestBody Collection<Long> materialIds);

    /**
     * 通过批量组织ID获取对应业务实体全部部门(包括虚拟部门,结算用)
     *
     * @param organizationIds
     * @return
     */
    @PostMapping("/organization/organization/getAllDeptByOrganizations")
    Map<Long, List<DeptDto>> getAllDeptByOrganizations(@RequestBody List<Long> organizationIds);

    @GetMapping("/base-anon/internal/listFormulaMaterialCode")
    Set<String> listFormulaMaterialCode(@RequestParam("name") String name);

    @PostMapping("/base-anon/internal/listMaterialOrgByMaterialIdsAndInvIds")
    List<MaterialOrg> listMaterialOrgByMaterialIdsAndInvIds(@RequestBody Map<String, Collection<Long>> idMap);

    /**
     * 根据id获取部门
     * @param id
     * @return
     */
    @GetMapping("/base-anon/internal/dept/getById")
    Dept getDeptByIdAnon(@RequestParam("id") Long id);

    @PostMapping("/base-anon/internal/dict/listDictItemByDictCode")
    List<DictItem> listDictItemByDictCode(@RequestParam("dictCode") String dictCode);
	    /**
     * 根据orgERPID获取业务实体/库存组织
     * @return
     */
    @PostMapping("/base-anon/internal/organization/listByErpOrgId")
    List<Organization> listByErpOrgId(@RequestBody List<String> orgErpId);

    /**
     * 条件查询字典条目  add by chensl26 2020-12-02
     * @param dictItemDTO
     * @return
     */
    @PostMapping("/dict/base-dict-item/listDictItemsByParam")
    List<DictItem> listDictItemsByParam(@RequestBody DictItemDTO dictItemDTO);

    @PostMapping("/base-anon/internal/purchaseCurrency/listPurchaseCurrencyAnon")
    List<PurchaseCurrency> listPurchaseCurrencyAnon(@RequestBody List<String> currencyCodeList);

    @PostMapping("/base-anon/internal/purchaseCategory/listPurchaseCategoryAnon")
    List<PurchaseCategory> listPurchaseCategoryAnon(@RequestBody List<Long> categoryIds);

    @PostMapping("/bid/essential-factor/listByEssentialFactorName")
    List<EssentialFactor> listByEssentialFactorName(@RequestBody List<String> essentialFactorNameList);

    /**
     *
     * @param formId
     * @return
     */
    @GetMapping("/base-anon/internal/workflow/getFlowIdByFormId")
    String getFlowIdByFormIdAnon(@RequestParam("formId") Long formId);

    /**
     * 根据userid获取权限节点
     * @return
     */
    @GetMapping("/organization/organization/getFullPathIdByTypeCode")
    List<Organization> getFullPathIdByTypeCode(@RequestParam(name = "userId", required = true)Long userId);

    /**
     * 获取组织树状图
     * @return
     */
    @PostMapping("/organization/relation/treeNew")
    List<OrganizationRelation> allTree();

    /**
     * 获取汇率
     *
     * @param convertDate
     * @param sourceCurrency
     * @return
     */
    @PostMapping("/purchase/latest-gidaily-rate/getCurrency")
    Map<String,List<LatestGidailyRate>> getCurrency(@RequestBody LatestGidailyRate gidailyRate);


    /**
     * 根据目标币种和汇率类型获取汇率
     * @param toCurrencyCode 目标币种编码
     * @param conversionType 汇率类型
     * @return
     */
    @GetMapping("/purchase/latest-gidaily-rate/getLatestGidailyRate")
    Map<String,LatestGidailyRate> getLatestGidailyRate(
            @RequestParam("toCurrencyCode") String toCurrencyCode,
            @RequestParam("conversionType")String conversionType);

    /**
     * 条件查询采购分类接口
     */
    @PostMapping("/purchase/purchaseCategory/listPageByParm")
    PageInfo<PurchaseCategory> listPageByParmCategory(@RequestBody PurchaseCategory purchaseCategory);

    /**
     * 根据物料获取税率
     * @param materialIds
     * @return
     */
    @PostMapping("/purchase/purchaseTax/getPurchaseTaxByMaterialIds")
    Map<Long,List<PurchaseTax>> getPurchaseTaxByMaterialIds(@RequestBody List<Long> materialIds);

    /**
     * 获取省数据
     * @param provinceNameList
     * @return
     */
    @PostMapping("/region/listProvinceByNameBatch")
    List<Region> listProvinceByNameBatch(@RequestBody List<String> provinceNameList);

    /**
     * 获取市数据
     * @param cityNameList
     * @return
     */
    @PostMapping("/region/listCityByNameBatch")
    List<Region> listCityByNameBatch(@RequestBody List<String> cityNameList);

    /**
     * 获取县数据
     * @param countyNameList
     * @return
     */
    @PostMapping("/region/listCountyByNameBatch")
    List<Region> listCountyByNameBatch(@RequestBody List<String> countyNameList);

    /**
     * 获取港口数据
     * @param portNameList
     * @return
     */
    @PostMapping("/logistics/port/listPortByNameBatch")
    List<Port> listPortByNameBatch(@RequestBody List<String> portNameList);

    /**
     * 通过计费方式查找计费单位
     * @param chargeMethod 计费方式名字集合
     * @return 计费方式编码-计费单位信息
     */
    @PostMapping("/dict/base-dict/queryBillingCombinationMap")
    Map<String, Map<String,String>> queryBillingCombinationMap(@RequestBody List<String> chargeMethod);

}
