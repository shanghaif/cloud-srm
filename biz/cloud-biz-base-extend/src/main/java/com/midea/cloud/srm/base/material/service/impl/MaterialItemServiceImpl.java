package com.midea.cloud.srm.base.material.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SpecialBusinessConstant;
import com.midea.cloud.common.enums.UserType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.base.BigCategoryTypeEnum;
import com.midea.cloud.common.enums.pm.po.OrderTypeEnum;
import com.midea.cloud.common.enums.pm.po.PriceSourceTypeEnum;
import com.midea.cloud.common.enums.rbac.RoleEnum;
import com.midea.cloud.common.enums.review.CategoryStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.handler.TitleColorSheetWriteHandler;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.base.busiunit.service.IErpService;
import com.midea.cloud.srm.base.job.MaterialCache;
import com.midea.cloud.srm.base.material.mapper.CategoryBusinessMapper;
import com.midea.cloud.srm.base.material.mapper.MaterialItemMapper;
import com.midea.cloud.srm.base.material.service.ICategoryBusinessService;
import com.midea.cloud.srm.base.material.service.IItemImageService;
import com.midea.cloud.srm.base.material.service.IMaterialItemService;
import com.midea.cloud.srm.base.material.service.IMaterialOrgService;
import com.midea.cloud.srm.base.material.utils.MaterialItemExportUtils;
import com.midea.cloud.srm.base.organization.service.IErpMaterialItemService;
import com.midea.cloud.srm.base.organization.service.IOrganizationService;
import com.midea.cloud.srm.base.purchase.mapper.PurchaseTaxMapper;
import com.midea.cloud.srm.base.purchase.service.IPurchaseCategoryService;
import com.midea.cloud.srm.base.purchase.service.IPurchaseUnitService;
import com.midea.cloud.srm.base.quotaorder.service.ICommonsService;
import com.midea.cloud.srm.base.serviceconfig.service.IBaseServiceConfigService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.contract.ContractClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.feign.pm.PmClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.MaterialOrg;
import com.midea.cloud.srm.model.base.material.dto.*;
import com.midea.cloud.srm.model.base.material.entity.CategoryBusiness;
import com.midea.cloud.srm.model.base.material.entity.ItemImage;
import com.midea.cloud.srm.model.base.material.enums.CeeaMaterialStatus;
import com.midea.cloud.srm.model.base.material.vo.MaterialItemVo;
import com.midea.cloud.srm.model.base.material.vo.MaterialMaxCategoryVO;
import com.midea.cloud.srm.model.base.organization.entity.Category;
import com.midea.cloud.srm.model.base.organization.entity.ErpMaterialItem;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseUnit;
import com.midea.cloud.srm.model.base.serviceconfig.entity.ServiceConfig;
import com.midea.cloud.srm.model.base.soap.DataSourceEnum;
import com.midea.cloud.srm.model.cm.contract.dto.ContractItemDto;
import com.midea.cloud.srm.model.cm.contract.entity.ContractPartner;
import com.midea.cloud.srm.model.cm.contract.vo.ContractVo;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import com.midea.cloud.srm.model.pm.pr.shopcart.entity.ShopCart;
import com.midea.cloud.srm.model.pm.pr.shopcart.enums.ShopCartStatus;
import com.midea.cloud.srm.model.rbac.role.entity.Role;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.entity.ContactInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <pre>
 *  物料维护 服务实现类
 * </pre>
 *
 * @author zhuwl7@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-06 15:22:48
 *  修改内容:
 * </pre>
 */
@Slf4j
@Service
public class MaterialItemServiceImpl extends ServiceImpl<MaterialItemMapper, MaterialItem> implements IMaterialItemService {
    @Autowired
    private IMaterialItemService iMaterialItemService;

    @Resource
    private IPurchaseCategoryService iPurchaseCategoryService;

    @Resource
    private IBaseServiceConfigService iBaseServiceConfigService;

    @Autowired
    private ICategoryBusinessService iCategoryBusinessService;

    /**
     * 物料-库存组织Service
     */
    @Resource
    private IMaterialOrgService iMaterialOrgService;
    @Autowired
    private SupplierClient supplierClient;
    @Autowired
    private InqClient inqClient;
    @Autowired
    private PmClient pmClient;
    @Autowired
    FileCenterClient fileCenterClient;
    @Autowired
    private ContractClient contractClient;

    @Autowired
    private IErpMaterialItemService iErpMaterialItemService;

    @Autowired
    private IOrganizationService iOrganizationService;

    @Autowired
    private IPurchaseUnitService iPurchaseUnitService;

    @Resource
    private PurchaseTaxMapper purchaseTaxMapper;

    @Autowired
    private BaseClient baseClient;

    @Resource
    private MaterialItemMapper materialItemMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IPurchaseCategoryService purchaseCategoryService;
    @Resource
    private IItemImageService iItemImageService;
    @Resource
    private ICommonsService iCommonsService;

    /* 物料状态为这几种时置为有效 Y, 其他置为无效 N */
    public static final HashSet<String> itemStatusSet;

    static {
        itemStatusSet = new HashSet<String>();

        itemStatusSet.add("Active");
        itemStatusSet.add("定版");
        itemStatusSet.add("实验");

    }

    @Override
    public Map<String, String> queryItemIdUserPurchase(ItemCodeUserPurchaseDto itemCodeUserPurchaseDto) {
        Long invId = itemCodeUserPurchaseDto.getInvId();
        Long orgId = itemCodeUserPurchaseDto.getOrgId();
        List<String> itemCodes = itemCodeUserPurchaseDto.getItemCodes();
        Map<String, String> map = new HashMap<>();
        if(StringUtil.notEmpty(invId) && StringUtil.notEmpty(orgId) && CollectionUtils.isNotEmpty(itemCodes)){
            itemCodes = itemCodes.stream().distinct().collect(Collectors.toList());
            itemCodeUserPurchaseDto.setItemCodes(itemCodes);
            List<ItemCodeUserPurchaseDto> itemCodeUserPurchaseDtos = this.baseMapper.queryItemIdUserPurchase(itemCodeUserPurchaseDto);
            if(CollectionUtils.isNotEmpty(itemCodeUserPurchaseDtos)){
                map = itemCodeUserPurchaseDtos.stream().filter(materialOrg ->
                        StringUtil.notEmpty(materialOrg.getMaterialCode())
                                && StringUtil.notEmpty(materialOrg.getUserPurchase())).
                        collect(Collectors.toMap(ItemCodeUserPurchaseDto::getMaterialCode, k-> "Y".equals(k.getUserPurchase()) && "Y".equals(k.getItemStatus()) ? "Y":"N",(k1,k2)->k1));
            }

        }
        return map;
    }

    @Override
    public Map<String,MaterialItem> ListMaterialItemByCategoryCode(List<String> itemCodes) {
        Map<String, MaterialItem> itemHashMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(itemCodes)) {
            QueryWrapper<MaterialItem> wrapper = new QueryWrapper<>();
            wrapper.in("MATERIAL_CODE", itemCodes);
            List<MaterialItem> materialItems = materialItemMapper.ListMaterialItemByCategoryCode(wrapper);
            if (CollectionUtils.isNotEmpty(materialItems)) {
                itemHashMap = materialItems.stream().collect(Collectors.toMap(MaterialItem::getMaterialCode,Function.identity(), (k1, k2) -> k1));
            }
        }
        return itemHashMap;
    }

    @Override
    public List<MaterialItem> queryMaterialItemByCodes(List<String> materialCodeList) {
        if (CollectionUtils.isNotEmpty(materialCodeList)) {
            return this.baseMapper.queryMaterialItemByCodes(materialCodeList);
        }
        return null;
    }

    @Override
    public PageInfo<MaterialItem> listPage(MaterialItem materialItem) {
        PageUtil.startPage(materialItem.getPageNum(), materialItem.getPageSize());
        QueryWrapper<MaterialItem> wrapper = new QueryWrapper<>();

        // 获取当前登录用户是供应商
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String userType = loginAppUser.getUserType();
        if (UserType.VENDOR.name().equals(userType)) {
            List<String> stutus = new ArrayList<>();
            stutus.add(CeeaMaterialStatus.NOTIFIED.getCode());
            stutus.add(CeeaMaterialStatus.MAINTAINED.getCode());

            wrapper.eq("CEEA_SUPPLIER_CODE", loginAppUser.getCompanyCode());
            wrapper.in("CEEA_MATERIAL_STATUS", stutus); // 已通知、已维护
        } else {
            if (StringUtils.isNoneBlank(materialItem.getCeeaMaterialStatus())) {
                wrapper.eq("CEEA_MATERIAL_STATUS", materialItem.getCeeaMaterialStatus());
            }
        }

        if (null != materialItem.getMaterialId()) {
            wrapper.eq("MATERIAL_ID", materialItem.getMaterialId());
        }
        if (StringUtils.isNoneBlank(materialItem.getMaterialCode())) {
            wrapper.like("MATERIAL_CODE", materialItem.getMaterialCode());
        }
        if (StringUtils.isNoneBlank(materialItem.getMaterialName())) {
            wrapper.like("MATERIAL_NAME", materialItem.getMaterialName());
        }
        if (StringUtils.isNoneBlank(materialItem.getStatus())) {
            wrapper.eq("STATUS", materialItem.getStatus());
        }
        if (StringUtils.isNoneBlank(materialItem.getCategoryName())) {
            wrapper.like("CATEGORY_NAME", materialItem.getCategoryName());
        }
        if (StringUtils.isNoneBlank(materialItem.getCeeaSupplierName())) {
            wrapper.eq("CEEA_SUPPLIER_NAME", materialItem.getCeeaSupplierName());
        }
        if ("Y".equals(materialItem.getCeeaIfCatalogMaterial())) {
            wrapper.eq("CEEA_IF_CATALOG_MATERIAL", materialItem.getCeeaIfCatalogMaterial());
        }
        if ("N".equals(materialItem.getCeeaIfCatalogMaterial())) {
            wrapper.notIn("CEEA_IF_CATALOG_MATERIAL", "Y").or().isNull("CEEA_IF_CATALOG_MATERIAL");
        }
        if (StringUtils.isNoneBlank(materialItem.getCeeaContractNo())) {
            wrapper.eq("CEEA_CONTRACT_NO", materialItem.getCeeaContractNo());
        }

        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return new PageInfo<MaterialItem>(iMaterialItemService.list(wrapper));
    }

    @Override
    public PageInfo<MaterialItem> listPageMaterialItemChart(MaterialItem materialItem) {
        PageUtil.startPage(materialItem.getPageNum(), materialItem.getPageSize());
        //获取当前登录用户是供应商
        QueryWrapper<MaterialItem> wrapper = new QueryWrapper<>();

        // 获取当前登录用户是供应商
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String userType = loginAppUser.getUserType();
        if (UserType.VENDOR.name().equals(userType)) {
            List<String> stutus = new ArrayList<>();
            stutus.add(CeeaMaterialStatus.NOTIFIED.getCode());
            stutus.add(CeeaMaterialStatus.MAINTAINED.getCode());

            wrapper.eq("CEEA_SUPPLIER_CODE", loginAppUser.getCompanyCode());
            wrapper.in("CEEA_MATERIAL_STATUS", stutus); // 已通知、已维护
        } else {
            if (StringUtils.isNoneBlank(materialItem.getCeeaMaterialStatus())) {
                wrapper.eq("CEEA_MATERIAL_STATUS", materialItem.getCeeaMaterialStatus());
            }
        }
        if (null != materialItem.getMaterialId()) {
            wrapper.eq("MATERIAL_ID", materialItem.getMaterialId());
        }
        if (StringUtils.isNoneBlank(materialItem.getMaterialCode())) {
            wrapper.like("MATERIAL_CODE", materialItem.getMaterialCode());
        }
        if (StringUtils.isNoneBlank(materialItem.getMaterialName())) {
            wrapper.like("MATERIAL_NAME", materialItem.getMaterialName());
        }
        if (StringUtils.isNoneBlank(materialItem.getStatus())) {
            wrapper.eq("STATUS", materialItem.getStatus());
        }
        if (StringUtils.isNoneBlank(materialItem.getCategoryName())) {
            wrapper.like("CATEGORY_NAME", materialItem.getCategoryName());
        }
        if (StringUtils.isNoneBlank(materialItem.getCeeaSupplierName())) {
            wrapper.eq("CEEA_SUPPLIER_NAME", materialItem.getCeeaSupplierName());
        }
        if ("Y".equals(materialItem.getCeeaIfCatalogMaterial())) {
            wrapper.eq("CEEA_IF_CATALOG_MATERIAL", materialItem.getCeeaIfCatalogMaterial());
        }
        if ("N".equals(materialItem.getCeeaIfCatalogMaterial())) {
            wrapper.notIn("CEEA_IF_CATALOG_MATERIAL", "Y").or().isNull("CEEA_IF_CATALOG_MATERIAL");
        }
        if (StringUtils.isNoneBlank(materialItem.getCeeaContractNo())) {
            wrapper.eq("CEEA_CONTRACT_NO", materialItem.getCeeaContractNo());
        }
        wrapper.orderByDesc("LAST_UPDATE_DATE");

        List<MaterialItem> result = iMaterialItemService.list(wrapper);
        List<Long> materialItemId = result.stream().map(item -> item.getMaterialId()).collect(Collectors.toList());
        List<MaterialOrg> materialOrgList = getMaterialOrgList(materialItemId);
        Map<Long,StringBuffer> map = getMaterialOrgMap(materialOrgList);
        for(MaterialItem m : result){
            if(Objects.nonNull(map.get(m.getMaterialId()))){
                m.setOrgNames(map.get(m.getMaterialId()).toString());
            }
        }

        return new PageInfo<MaterialItem>(result);
    }



    /**
     * 获取所有的物料组织对应关系
     * @param materialItemIds
     * @return
     */
    private List<MaterialOrg> getMaterialOrgList(List<Long> materialItemIds){
        if(CollectionUtils.isEmpty(materialItemIds)){
            return Collections.EMPTY_LIST;
        }
        QueryWrapper<MaterialOrg> wrapper = new QueryWrapper<>();
        wrapper.in("MATERIAL_ID",materialItemIds);
        wrapper.in("ITEM_STATUS","Y");
        List<MaterialOrg> materialOrgList = iMaterialOrgService.list(wrapper);
        return materialOrgList;
    }

    /**
     * 将list转化为map
     * @param materialOrgList
     * @return
     */
    private Map<Long,StringBuffer> getMaterialOrgMap(List<MaterialOrg> materialOrgList){
        Map<Long,StringBuffer> result = new HashMap<>();
        for(MaterialOrg materialOrg : materialOrgList){
            if(Objects.nonNull(result.get(materialOrg.getMaterialId()))){
                result.get(materialOrg.getMaterialId()).append(materialOrg.getOrgName() + ",");
            }else{
                result.put(materialOrg.getMaterialId(),new StringBuffer(materialOrg.getOrgName() + ","));
            }
        }
        return result;
    }

    @Override
    @Transactional
    public void saveOrUpdateMBatch(List<MaterialItem> materialItems) {
        if (!CollectionUtils.isEmpty(materialItems)) {
            for (MaterialItem materialItem : materialItems) {
                /**
                 * 1、	若【配额管理类型】为“固定比例”或“综合比例”，则【最小拆单量】必填，否则【最小拆单量】不可填写；
                 * 2、	若【配额管理类型】为“配额达成率”，则【最大分配量】必填，否则【最大分配量】不可填写；
                 * 以上若校验成功则保存成功，若校验失败，则提示对应的错误。
                 */
                iCommonsService.checkMaterialItem(materialItem);

                //判断物料code是否重复
                QueryWrapper<MaterialItem> wrapper = new QueryWrapper<>();
                wrapper.eq(StringUtils.isNotBlank(materialItem.getMaterialCode()),
                        "MATERIAL_CODE", materialItem.getMaterialCode());
                wrapper.ne(materialItem.getMaterialId() != null,
                        "MATERIAL_ID", materialItem.getMaterialId());
                List<MaterialItem> existslist = this.list(wrapper);
                if (!CollectionUtils.isEmpty(existslist)) {
                    throw new BaseException(LocaleHandler.getLocaleMsg("物料编码已存在!"));
                }
                if (materialItem.getMaterialId() != null) {
                    materialItem.setLastUpdateDate(new Date());
                } else {
                    Long id = IdGenrator.generate();
                    materialItem.setMaterialId(id);
                    materialItem.setCreationDate(new Date());
                }
            }
            this.saveOrUpdateBatch(materialItems);
        }
    }

    @Override
    @Transactional
    public void saveOrUpdateM(MaterialItem materialItem) {
        //判断物料code是否重复
        QueryWrapper<MaterialItem> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(materialItem.getMaterialCode()),
                "MATERIAL_CODE", materialItem.getMaterialCode());
        wrapper.ne(materialItem.getMaterialId() != null,
                "MATERIAL_ID", materialItem.getMaterialId());
        List<MaterialItem> existslist = this.list(wrapper);
        if (!CollectionUtils.isEmpty(existslist)) {
            throw new BaseException(LocaleHandler.getLocaleMsg("物料编码已存在!"));
        }

        Long id = null;
        if (materialItem.getMaterialId() != null) {
            materialItem.setLastUpdateDate(new Date());
        } else {
            id = IdGenrator.generate();
            materialItem.setMaterialId(id);
            materialItem.setCreationDate(new Date());
        }

        List<MaterialOrg> materialOrgList = materialItem.getMaterialOrgList();
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        materialItem.setLastUpdatedBy((null != user ? user.getUsername() : ""));
        boolean isSaveOrUpdate = this.saveOrUpdate(materialItem);

        /**保存成功，则保存物料下的库存组织*/
        if (isSaveOrUpdate && CollectionUtils.isNotEmpty(materialOrgList)) {
            for (MaterialOrg materialOrg : materialOrgList) {
                if (null != materialOrg && null == materialOrg.getMaterialOrgId()) {
                    materialOrg.setMaterialOrgId(IdGenrator.generate());
                    materialOrg.setMaterialId(materialItem.getMaterialId());
                }
            }
            iMaterialOrgService.saveOrUpdateBatch(materialOrgList);
        }

    }

    @Override
    public List<MaterialItem> listMaterialByCodeBatch(List<String> materialCodeList) {
        QueryWrapper<MaterialItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("MATERIAL_CODE", materialCodeList);
        return this.list(queryWrapper);
    }

    @Override
    public List<MaterialItem> listMaterialByIdBatch(List<Long> materialIds) {
        QueryWrapper<MaterialItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("MATERIAL_ID", materialIds);
        return this.list(queryWrapper);
    }

    @Override
    public PageInfo<MaterialQueryDTO> listPageByParam(MaterialQueryDTO materialQueryDTO) {
        PageUtil.startPage(materialQueryDTO.getPageNum(), materialQueryDTO.getPageSize());
        List<MaterialQueryDTO> materialQueryDTOS = this.baseMapper.listPageByParam(materialQueryDTO);
        return new PageInfo<>(materialQueryDTOS);
    }

    /**
     * 手工创建订单-根据采购申请创建
     * 采购类型为【零价格采购】
     * 需根据订单供应商，及组织品类关系状态过滤可选择采购申请范围
     * 无需校验是否存在有效价格；
     * 订单单价默认为0，无需修改对应订单单价
     * <p>
     * 采购类型为【紧急采购】
     * 根据订单供应商对应组织品类关系过滤可选择采购申请范围
     * 无需校验是否存在有效价格；若价格库/合同存在有效价格可带出，否则可手工录入对应的订单单价；
     * <p>
     * 采购类型为【研发采购】
     * 根据订单供应商对应组织品类关系过滤可选择采购申请范围
     * 无需校验是否存在有效价格；若价格库/合同存在有效价格可带出，否则可手工录入对应的订单单价；
     *
     * @return
     */
    @Override
    public PageInfo<MaterialItemVo> listForOrder(MaterialQueryDTO materialQueryDTO) {
        Assert.notNull(materialQueryDTO.getVendorId(), LocaleHandler.getLocaleMsg("供应商id必传"));
        Assert.hasText(materialQueryDTO.getPurchaseType(), LocaleHandler.getLocaleMsg("采购类型不可为空"));
        Assert.notNull(materialQueryDTO.getCeeaOrgId(), LocaleHandler.getLocaleMsg("业务实体id不可为空"));
        Assert.notNull(materialQueryDTO.getOrganizationId(), LocaleHandler.getLocaleMsg("库存组织id不可为空"));

        List<PurchaseCategory> purchaseCategories = materialQueryDTO.getPurchaseCategories();
        String maxLevel = getMaxLevel();

        /*查找最小级采购分类*/
        ArrayList<PurchaseCategory> categories = new ArrayList<>();
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(purchaseCategories)) {
            for (PurchaseCategory purchaseCategory : purchaseCategories) {
                Long categoryId = purchaseCategory.getCategoryId();
                QueryWrapper<PurchaseCategory> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("LEVEL", maxLevel);
                queryWrapper.like("STRUCT", categoryId);
                List<PurchaseCategory> list = iPurchaseCategoryService.list(queryWrapper);
                categories.addAll(list);
            }
        }
        List<MaterialItemVo> materialItemVoList = new ArrayList<>();
        /*查找物料*/
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(categories)) {
            List<Long> longs = new ArrayList<>();
            categories.forEach(item -> longs.add(item.getCategoryId()));
            log.info(JsonUtil.arrayToJsonStr(longs));
            materialItemVoList = this.baseMapper.listByParam(materialQueryDTO, longs);
        }

        /*查询所有有效价格*/
        List<PriceLibrary> priceLibraryAll = inqClient.listAllEffective(new PriceLibrary().setVendorId(materialQueryDTO.getVendorId()).setCeeaOrganizationId(materialQueryDTO.getOrganizationId()));
        /*查询所有供应商品类组织关系*/
        List<OrgCategory> orgCategoryList = supplierClient.getOrgCategoryByOrgCategory(new OrgCategory().setOrgId(materialQueryDTO.getOrgId()).setCompanyId(materialQueryDTO.getVendorId()));

        List<Long> orgIds = new ArrayList<>() ;
        orgIds.add(materialQueryDTO.getOrgId());
        /*查询出所有有效合同*/
        List<ContractVo> contractVoList = contractClient.listAllEffectiveCM(new ContractItemDto().setMaterialIds(orgIds));
        //合同的合作伙伴
        List<Long> controlHeadIds = contractVoList.stream().map(c->c.getContractHeadId()).collect(Collectors.toList());
        List<ContractPartner> contractPartnerList = contractClient.listAllEffectiveCP(controlHeadIds);


        List<MaterialItemVo> result = new ArrayList<>();
        for (int i = 0; i < materialItemVoList.size(); i++) {
            MaterialItemVo item = materialItemVoList.get(i);

            /*检验是否有供应商品类组织关系 - 供应商组织品类关系判断方法*/
            OrgCategory orgCategoryItem = null;
            for (OrgCategory orgCategory : orgCategoryList) {
                if (null != orgCategory
                        && null != item.getOrgId() && Objects.equals(orgCategory.getOrgId(), item.getOrgId())
                        && Objects.equals(orgCategory.getCompanyId(), materialQueryDTO.getVendorId())
                        && null != item.getCategoryId() && Objects.equals(orgCategory.getCategoryId(), item.getCategoryId())
                        && null != orgCategory.getServiceStatus()
                        && (orgCategory.getServiceStatus().equals(CategoryStatus.VERIFY.name()) || orgCategory.getServiceStatus().equals(CategoryStatus.ONE_TIME.name()) || orgCategory.getServiceStatus().equals(CategoryStatus.GREEN.name()) || orgCategory.getServiceStatus().equals(CategoryStatus.YELLOW.name()))
                ) {
                    orgCategoryItem = orgCategory;
                    break;
                }
            }
            if (orgCategoryItem == null) {
                log.info("物料：" + JsonUtil.entityToJsonStr(item) + " 无供应商组织品类关系");
                continue;
            }
            log.info("materialCode = " + item.getMaterialCode() + "  materialName = " + item.getMaterialName() + "  vendorId = " + materialQueryDTO.getVendorId() + "的供应商组织品类关系为：" + JsonUtil.entityToJsonStr(orgCategoryItem));

            if (materialQueryDTO.getPurchaseType().equals(OrderTypeEnum.ZERO_PRICE.getValue()) || OrderTypeEnum.CONVENIENT.getValue().equals(materialQueryDTO.getPurchaseType())) {
                item.setNoTaxPrice(BigDecimal.ZERO);
                item.setTaxPrice(BigDecimal.ZERO);
                /*item.setTaxCode("IN 11");
                item.setTaxRate(new BigDecimal(11.00));
                item.setCurrencyId(7007437216088064L);
                item.setCurrencyCode("CNY");
                item.setCurrencyName("人民币元");*/
                result.add(item);
            } else if (materialQueryDTO.getPurchaseType().equals(OrderTypeEnum.URGENT.getValue()) ||
                    materialQueryDTO.getPurchaseType().equals(OrderTypeEnum.DEVELOP.getValue())) {
                /*判断价格库是否有价格（指定供应商，在业务实体下，库存组织下有物料编码，物料名称有有效价格）*/
                PriceLibrary priceEntity = null;
                for (PriceLibrary priceLibrary : priceLibraryAll) {
                    if (null != priceLibrary && StringUtils.isNotBlank(item.getMaterialName()) && item.getMaterialName().equals(priceLibrary.getItemDesc()) &&
                            StringUtils.isNotBlank(item.getMaterialCode()) && item.getMaterialCode().equals(priceLibrary.getItemCode()) &&
                            item.getOrgId() != null && !ObjectUtils.notEqual(item.getOrgId(), priceLibrary.getCeeaOrgId()) &&
                            Objects.equals(materialQueryDTO.getVendorId(), priceLibrary.getVendorId()) &&
                            item.getOrganizationId() != null && Objects.equals(item.getOrganizationId(), priceLibrary.getCeeaOrganizationId()) &&
                            StringUtils.isNotBlank(priceLibrary.getPriceType()) && "STANDARD".equals(priceLibrary.getPriceType())
                    ) {
                        /*判断价格库是否某些字段为空，为空则跳过该数据*/
                        if (priceLibrary.getNotaxPrice() == null ||
                                priceLibrary.getTaxPrice() == null ||
                                StringUtils.isBlank(priceLibrary.getTaxKey()) ||
                                StringUtils.isBlank(priceLibrary.getTaxRate()) ||
                                priceLibrary.getCurrencyId() == null ||
                                StringUtils.isBlank(priceLibrary.getCurrencyCode()) ||
                                StringUtils.isBlank(priceLibrary.getCurrencyName())
                        ) {
                            log.info("materialCode = " + item.getMaterialCode() + "  materialName:" + item.getMaterialName() + " 价格库某些字段为空:" + JsonUtil.entityToJsonStr(priceLibrary));
                        } else {
                            priceEntity = priceLibrary;
                            item.setNoTaxPrice(priceEntity.getNotaxPrice());
                            item.setTaxPrice(priceEntity.getTaxPrice());
                            item.setTaxCode(priceEntity.getTaxKey());
                            item.setTaxRate(new BigDecimal(priceEntity.getTaxRate()));
                            item.setCurrencyId(priceEntity.getCurrencyId());
                            item.setCurrencyCode(priceEntity.getCurrencyCode());
                            item.setCurrencyName(priceEntity.getCurrencyName());
                            /*关联框架合同 业务实体id，库存组织id，供应商id，合同为框架协议*/
//                            for (ContractVo contractVo : contractVoList) {
//                                if (null != contractVo && contractVo.getBuId() != null && !ObjectUtils.notEqual(contractVo.getBuId(), item.getOrgId())
//                                        && contractVo.getInvId() != null && Objects.equals(contractVo.getInvId(), item.getOrganizationId())
//                                        && contractVo.getHeadVendorId() != null && Objects.equals(contractVo.getHeadVendorId(), priceEntity.getVendorId())
//                                        && StringUtils.isNotBlank(contractVo.getIsFrameworkAgreement()) && contractVo.getIsFrameworkAgreement().equals("Y")
//                                ) {
//                                    /*判断合同是否某些字段为空，为空则跳过该数据*/
//                                    if (StringUtils.isNotBlank(contractVo.getContractCode())) {
//                                        item.setContractCode(contractVo.getContractCode());
//                                        break;
//                                    } else {
//                                        log.info("materialCode = " + item.getMaterialCode() + "  materialName:" + item.getMaterialName() + "物料选择的合同的合同编码为空：" + JsonUtil.entityToJsonStr(contractVo));
//                                    }
//                                }
//                            }


                            if(!StringUtil.isEmpty(priceLibrary.getContractCode())){
                                item.setContractCode(priceLibrary.getContractCode());
                            }else{
                                //添加比对合作伙伴 逻辑2020年11月10日
                                for (ContractVo contractVo : contractVoList) {
                                    boolean hitContractFlag =
//                                          null != contractVo &&
//                                          contractVo.getBuId() != null &&
//                                          !ObjectUtils.notEqual(contractVo.getBuId(), item.getOrgId())&&
                                            contractVo.getHeadVendorId() != null &&
//                                          contractVo.getInvId() != null &&
//                                          Objects.equals(contractVo.getInvId(), item.getOrganizationId()) &&
                                                    Objects.equals(contractVo.getHeadVendorId(), priceLibrary.getVendorId()) &&
                                                    StringUtils.isNotBlank(contractVo.getIsFrameworkAgreement()) &&
                                                    StringUtils.isNotBlank(contractVo.getContractCode()) &&
                                                    contractVo.getIsFrameworkAgreement().equals("Y");
                                    log.info("materialCode = " + item.getMaterialCode() + "  materialName:" + item.getMaterialName() + "物料选择的合同的合同编码为空：" + JsonUtil.entityToJsonStr(contractVo));

                                    boolean findContractNum = false;
                                    for(ContractPartner contractPartner : contractPartnerList){
                                        if(!contractPartner.getContractHeadId().equals(contractVo.getContractHeadId())) {
                                            continue;
                                        }
                                        if(findContractNum){
                                            break;
                                        }

                                        Long contractPartnerOUId = contractPartner.getOuId();
                                        //todo
                                        if(hitContractFlag && Objects.equals(contractVo.getBuId(), contractPartnerOUId)){
                                            /*判断合同是否某些字段为空，为空则跳过该数据*/
                                            item.setContractCode(contractVo.getContractCode());
                                            List<ContractVo> list = item.getContractVoList();
                                            if(null == list || list.isEmpty()){
                                                list = new ArrayList<>();
                                            }
                                            list.add(contractVo);
                                            item.setContractVoList(list);
                                            findContractNum = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                /*当找不到有效价格，去合同里找数据*/
                List<ContractVo> contractVos = new ArrayList<>();
                if (priceEntity == null) {
                    log.info("orgId = " + materialQueryDTO.getOrgId() + "  materialCode:" + item.getMaterialCode() + "  materialName:" + item.getMaterialName() + " 无有效价格");
                    /*判断是否有合同物料 条件为 指定供应商，在库存组织下有物料编码，物料名称有 有效合同物料*/
                    for (ContractVo contractVo : contractVoList) {
                        if (null != contractVo && contractVo.getBuId() != null && !ObjectUtils.notEqual(contractVo.getBuId(), item.getOrgId())
                                && contractVo.getMaterialCode() != null && contractVo.getMaterialCode().equals(item.getMaterialCode())
                                && contractVo.getMaterialName() != null && contractVo.getMaterialName().equals(item.getMaterialName())
                                && Objects.equals(contractVo.getHeadVendorId(), materialQueryDTO.getVendorId())
                                && contractVo.getInvId() != null && Objects.equals(contractVo.getInvId(), item.getOrganizationId())
                            /*&& StringUtils.isNotBlank(contractVo.getIsFrameworkAgreement()) && contractVo.getIsFrameworkAgreement().equals("N")*/
                        ) {
                            /*判断合同是否某些字段为空，为空则跳过该数据*/
                            if (contractVo.getTaxedPrice() == null ||
                                    StringUtils.isBlank(contractVo.getTaxKey()) ||
                                    contractVo.getTaxRate() == null ||
                                    contractVo.getCurrencyId() == null ||
                                    StringUtils.isBlank(contractVo.getCurrencyCode()) ||
                                    StringUtils.isBlank(contractVo.getCurrencyName())
                            ) {
                                log.info("orgId = " + materialQueryDTO.getOrgId() + "  materialCode:" + item.getMaterialCode() + "  materialName:" + item.getMaterialName() + " 合同某些字段为空:" + JsonUtil.entityToJsonStr(contractVo));
                            } else {
                                /*计算未税单价*/
                                BigDecimal taxRate = contractVo.getTaxRate().divide(new BigDecimal(100), 8, BigDecimal.ROUND_HALF_UP);
                                BigDecimal untaxedPrice = contractVo.getTaxedPrice().divide((new BigDecimal(1).add(taxRate)), 2, BigDecimal.ROUND_HALF_UP);
                                contractVo.setUntaxedPrice(untaxedPrice);

                                contractVos.add(contractVo);
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(contractVos)) {
                        ContractVo contractVo = contractVos.get(0);
                        item.setNoTaxPrice(contractVo.getUntaxedPrice());
                        item.setTaxPrice(contractVo.getTaxedPrice());
                        item.setTaxCode(contractVo.getTaxKey());
                        item.setTaxRate(contractVo.getTaxRate());
                        item.setCurrencyId(contractVo.getCurrencyId());
                        item.setCurrencyCode(contractVo.getCurrencyCode());
                        item.setCurrencyName(contractVo.getCurrencyName());
                        item.setContractCode(contractVo.getContractCode());
                        item.setContractVoList(contractVos);
                    } else {
                        log.info("orgId = " + materialQueryDTO.getOrgId() + "  materialCode:" + item.getMaterialCode() + "  materialName:" + item.getMaterialName() + " 无有效合同物料");
                    }
                }
                result.add(item);

            } else {
                throw new BaseException(LocaleHandler.getLocaleMsg("采购类型只能选择【零价格采购】【紧急采购】【研发采购】"));
            }
        }

        PageInfo<MaterialItemVo> pageInfo = PageUtil.pagingByFullData(materialQueryDTO.getPageNum(), materialQueryDTO.getPageSize(), result);

        /*设置物料大类*/
        List<Long> materialIds = pageInfo.getList().stream().map(item -> item.getMaterialId()).collect(Collectors.toList());
        ;
        List<MaterialMaxCategoryVO> materialMaxCategoryVOList = baseClient.queryCategoryMaxCodeByMaterialIds(materialIds);
        Map<Long, MaterialMaxCategoryVO> map = materialMaxCategoryVOList.stream().collect(Collectors.toMap(e -> e.getMaterialId(), e -> e));
        for (int i = 0; i < pageInfo.getList().size(); i++) {
            MaterialItemVo materialItemVo = pageInfo.getList().get(i);
            MaterialMaxCategoryVO materialMaxCategoryVO = map.get(materialItemVo.getMaterialId());
            if (materialMaxCategoryVO != null) {
                materialItemVo.setBigCategoryId(materialMaxCategoryVO.getCategoryId());
                materialItemVo.setBigCategoryCode(materialMaxCategoryVO.getCategoryCode());
            }
        }

        /*设置可用税率*/
        for (int i = 0; i < pageInfo.getList().size(); i++) {
            MaterialItemVo materialItemVo = pageInfo.getList().get(i);
            List<PurchaseTax> purchaseTaxList = baseClient.queryTaxByItemForOrder(materialItemVo.getMaterialId());
            materialItemVo.setPurchaseTaxList(purchaseTaxList);
        }

        /*设置价格来源*/
        for(int i=0;i<pageInfo.getList().size();i++){
            MaterialItemVo materialItemVo = pageInfo.getList().get(i);
            materialItemVo.setCeeaPriceSourceType(PriceSourceTypeEnum.MANUAL.getValue());
        }

        return pageInfo;
    }

    public PageInfo<MaterialItemVo> listForOrderNew(MaterialQueryDTO materialQueryDTO){
        //校验参数
        Assert.notNull(materialQueryDTO.getVendorId(), LocaleHandler.getLocaleMsg("供应商id必传"));
        Assert.hasText(materialQueryDTO.getPurchaseType(), LocaleHandler.getLocaleMsg("采购类型不可为空"));
        Assert.notNull(materialQueryDTO.getCeeaOrgId(), LocaleHandler.getLocaleMsg("业务实体id不可为空"));
        Assert.notNull(materialQueryDTO.getOrganizationId(), LocaleHandler.getLocaleMsg("库存组织id不可为空"));


        //查询外围系统的数据 MaterialList OrgCategoryList,PriceLibraryAll,
        return null;
    }


    @Override
    public PageInfo<MaterialQueryDTO> listMaterialByPurchaseCategory(MaterialQueryDTO materialQueryDTO) {
        PageUtil.startPage(materialQueryDTO.getPageNum(), materialQueryDTO.getPageSize());
        settingQueryCategoryByRole(materialQueryDTO);
        List<MaterialQueryDTO> materialQueryDTOS = this.baseMapper.listPageByWrapper(materialQueryDTO);
        return new PageInfo<>(materialQueryDTOS);
    }

    /**
     * 获取采购分类最大级别
     */
    public String getMaxLevel() {
        // 查找系统设置最大级数
        List<ServiceConfig> list = iBaseServiceConfigService.list();
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(list) && StringUtil.notEmpty(list.get(0).getServiceLevel())) {
            return list.get(0).getServiceLevel();
        } else {
            throw new BaseException("请在: 配置管理->管理层级设置->产品与服务配置 , 维护采购分类最大层级");
        }
    }

    @Override
    public PageInfo<MaterialItemVo> ceeaPurchaseMaterialListPage(MaterialQueryDTO materialQueryDTO) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (!StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType()) && !StringUtils.equals(UserType.BUYER.name(), loginAppUser.getUserType())) {
            Assert.isTrue(false, "用户类型不存在");
        }
        if (StringUtils.equals(UserType.VENDOR.name(), loginAppUser.getUserType())) {
            materialQueryDTO.setVendorId(loginAppUser.getCompanyId());
        } else {
            materialQueryDTO.setUserId(loginAppUser.getUserId());
        }
        PageHelper.startPage(materialQueryDTO.getPageNum(), materialQueryDTO.getPageSize());
        QueryWrapper<MaterialItem> queryWrapper = new QueryWrapper();
        queryWrapper.and(wrapper -> wrapper
                .like(StringUtils.isNoneBlank(materialQueryDTO.getMaterialKey()), "MATERIAL_NAME", materialQueryDTO.getMaterialKey())
                .or()
                .like(StringUtils.isNoneBlank(materialQueryDTO.getMaterialKey()), "MATERIAL_CODE", materialQueryDTO.getMaterialKey())
        );
        queryWrapper.eq(StringUtils.isNoneBlank(materialQueryDTO.getMaterialStatus()), "", materialQueryDTO.getMaterialStatus());
        queryWrapper.and(wrapper -> wrapper
                .like(StringUtils.isNoneBlank(materialQueryDTO.getSupplierKey()), "CEEA_SUPPLIER_CODE", materialQueryDTO.getSupplierKey())
                .or()
                .like(StringUtils.isNoneBlank(materialQueryDTO.getSupplierKey()), "CEEA_SUPPLIER_NAME", materialQueryDTO.getSupplierKey())
        );
        queryWrapper.eq(StringUtils.isNoneBlank(materialQueryDTO.getIfCatalogMaterial()), "CEEA_IF_CATALOG_MATERIAL", materialQueryDTO.getIfCatalogMaterial());
        queryWrapper.eq(materialQueryDTO.getCategoryId() != null, "CATEGORY_ID", materialQueryDTO.getCategoryId());
        queryWrapper.like(StringUtils.isNoneBlank(materialQueryDTO.getContractNo()), "CEEA_CONTRACT_NO", materialQueryDTO.getContractNo());
        List<MaterialItem> materialItemList = this.list(queryWrapper);
        List<MaterialItemVo> result = new ArrayList<>();
        materialItemList.forEach(item -> {
            MaterialItemVo materialItemVo = new MaterialItemVo();
            BeanUtils.copyProperties(item, materialItemVo);
            List<PriceLibrary> priceLibraryList = inqClient.listEffectivePrice(
                    new PriceLibrary().setCeeaOrgId(materialQueryDTO.getOrgId())
                            .setCeeaOrganizationId(materialQueryDTO.getOrganizationId())
                            .setItemCode(item.getMaterialCode())
                            .setItemDesc(item.getMaterialName())
            );
            if (CollectionUtils.isEmpty(priceLibraryList)) {
                Assert.notNull(null, LocaleHandler.getLocaleMsg("物料名称：[" + item.getMaterialName() + "] 物料编码[：" + item.getMaterialCode() + "]无有效价格"));
            }
            LocalDate now = LocalDate.now();
            LocalDate priceLibraryLocalDate = DateUtil.dateToLocalDate(priceLibraryList.get(0).getExpirationDate()).plusDays(1);
            materialItemVo.setYear(now.until(priceLibraryLocalDate).getYears())
                    .setMonth(now.until(priceLibraryLocalDate).getMonths())
                    .setDay(now.until(priceLibraryLocalDate).getDays());
            result.add(materialItemVo);
        });
        return new PageInfo<MaterialItemVo>(result);
    }

    @Override
    public MaterialItem findMaterialItemById(Long materialItemId) {
        MaterialItem materialItem = getBaseMapper().selectById(materialItemId);
        if (null != materialItem) {
            List<MaterialOrg> materialOrgList = iMaterialOrgService.list(new QueryWrapper<>(new MaterialOrg().setMaterialId(materialItem.getMaterialId())));
            materialItem.setMaterialOrgList(materialOrgList);
        }
        return materialItem;
    }

    /**
     * @Description 物料详情
     * @Param [materialItemId]
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.25 10:04
     */
    @Override
    public MaterialItem getMaterialItemById(Long materialItemId) {
        MaterialItem mi = this.getById(materialItemId);

        // 填充联系人
        if (StringUtils.isBlank(mi.getCeeaNames()) || StringUtils.isBlank(mi.getCeeaSecondNames())) {
            if (mi.getCeeaSupplierId() != null) {
				/*List<Long> vendorIds = new ArrayList<>();
				vendorIds.add(mi.getCeeaSupplierId());*/
                List<ContactInfo> contactInfos = supplierClient.listContactInfoByCompanyId(mi.getCeeaSupplierId()); // 联系人
                if (!CollectionUtils.isEmpty(contactInfos)) {
                    StringBuilder firstCont = new StringBuilder();
                    for (ContactInfo ci : contactInfos) {
                        if (StringUtils.isBlank(mi.getCeeaNames())) {
                            if ((ci.getContactName() + "").equals(mi.getCeeaSecondNames())) { // 与第二联系人重复
                                continue;
                            }
                            mi.setCeeaNames(ci.getContactName());
                            mi.setCeeaTelephones(ci.getMobileNumber());
                            mi.setCeeaEmails(ci.getEmail());
                            firstCont.append(ci.getContactName() + "，" + ci.getMobileNumber() + "，" + ci.getEmail());
                            continue; // 填写完第一联系人，用下一条数据填写第二联系人
                        }
                        if (StringUtils.isBlank(mi.getCeeaSecondNames())) {
                            if (firstCont.indexOf(ci.getContactName() + "，" + ci.getMobileNumber() + "，" + ci.getEmail()) > -1) {
                                continue; //
                            }
                            mi.setCeeaSecondNames(ci.getContactName());
                            mi.setCeeaSecondTelephones(ci.getMobileNumber());
                            mi.setCeeaEmails(ci.getEmail());
                            break; // 填写联系人完成
                        } else {
                            break; // 填写联系人完成
                        }
                    }
                }
            }
        }
        return mi;
    }

    /**
     * @param materialItem
     * @return
     * @Description 详情保存
     * @Param [materialItem]
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.28 10:42
     */
    @Override
    @Transactional
    public void ceeaSaveOrUpdate(MaterialItemDto materialItem) {
        Assert.notNull(materialItem, "获取数据失败");
      //kuangzm 增加新增保存方法字段验证
        Assert.notNull(materialItem.getMaterialItem(), "物料信息不能未空");
        Assert.notNull(materialItem.getMaterialItem().getMaterialCode(), "物料编码不能未空");
        Assert.notNull(materialItem.getMaterialItem().getMaterialName(), "物料描述不能未空");
        Assert.notNull(materialItem.getMaterialItem().getCategoryId(), "品类不能未空");
        Assert.notNull(materialItem.getMaterialItem().getUnit(), "单位不能未空");

        MaterialItem mi = materialItem.getMaterialItem();
        
        //kuangzm 增加新增保存方法
        if (null != mi.getMaterialId()) {
        	this.updateById(mi);
        } else {
        	Long id = IdGenrator.generate();
        	mi.setMaterialId(id);
        	this.save(mi);
        }
        
        // 保存附件
        List<Fileupload> matFiles = materialItem.getMatFiles();
        fileCenterClient.bindingFileupload(matFiles, mi.getMaterialId());

        // 保存图片
        List<ItemImage> itemImages = materialItem.getItemImages();
        iItemImageService.remove(Wrappers.lambdaQuery(ItemImage.class).
                eq(ItemImage::getMaterialId,mi.getMaterialId()));
        if(CollectionUtils.isNotEmpty(itemImages)){
            itemImages.forEach(itemImage -> {
                itemImage.setMaterialId(mi.getMaterialId());
                itemImage.setItemImageId(IdGenrator.generate());
            });
            iItemImageService.saveBatch(itemImages);
        }
    }

    /**
     * @param id
     * @return
     * @Description 物料详情
     * @Param [id]
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.28 11:10
     */
    @Override
    public MaterialItemDto ceeaGet(Long id) {
        Assert.notNull(id,"缺少参数:物料id");
        MaterialItemDto mid = new MaterialItemDto();
        MaterialItem mi = getMaterialItemById(id);
        PageInfo<Fileupload> matFiles = fileCenterClient.listPage(new Fileupload().setBusinessId(id).setFileFunction("materialMaintenance"), "");

        mid.setMaterialItem(mi);
        if (matFiles != null) {
            mid.setMatFiles(matFiles.getList());
        }

        // 获取图片
        List<ItemImage> itemImages = iItemImageService.list(Wrappers.lambdaQuery(ItemImage.class).
                eq(ItemImage::getMaterialId, id));
        mid.setItemImages(itemImages);


        return mid;
    }

    /**
     * @Description 分布查询采购目录
     * @Param [materialQueryDTO]
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.18 10:27
     * 如果勾选了目录化物料：
     * 业务实体+库存组织查找价格库是否为上架为是 + 有效期的价格库数据 + 查询附表
     * 2020-10-30奇怪需求：查询【物料附表！！】关联物料信息
     * <p>
     * 如果没有勾选目录化物料：
     * 按业务实体 + 库存组织查询物料维护的所有物料，采购目录页面显示查出来的所有数据
     * 按照查出来的物料 业务实体+库存组织+物料编码+物料描述 去价格库查询已上架+有效期内的行
     * 如果有，显示多个供应商，如果没有，显示一个物料
     * 2020-10-30奇怪需求：查询【物料主表！！】关联物料信息
     */
    @Override
    public PageInfo<MaterialQueryDTO> ceeaListPurchaseCatalogPage(MaterialQueryDTO materialQueryDTO) {
        /*查询出 备品备件，综合类物资的物料小类*/
        List<PurchaseCategory> bigCategories = new ArrayList<>();
        /*获取备品备件大类*/
        PurchaseCategory purchaseCategory1 = purchaseCategoryService.list(new QueryWrapper<>(new PurchaseCategory().setCategoryCode(BigCategoryTypeEnum.SPARE_PARTS.getCode()))).get(0);
        /*获取综合类物资*/
        PurchaseCategory purchaseCategory2 = purchaseCategoryService.list(new QueryWrapper<>(new PurchaseCategory().setCategoryCode(BigCategoryTypeEnum.COMPREHENSIVE_MATERIALS.getCode()))).get(0);
        bigCategories.add(purchaseCategory1);
        bigCategories.add(purchaseCategory2);

        /*查找最小级采购分类*/
        String maxLevel = getMaxLevel();
        List<PurchaseCategory> categories = new ArrayList<>();
        for (PurchaseCategory purchaseCategory : bigCategories) {
            Long categoryId = purchaseCategory.getCategoryId();
            QueryWrapper<PurchaseCategory> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("LEVEL", maxLevel);
            queryWrapper.like("STRUCT", categoryId);
            List<PurchaseCategory> list = iPurchaseCategoryService.list(queryWrapper);
            categories.addAll(list);
        }
        List<Long> longs = categories.stream().map(item -> item.getCategoryId()).collect(Collectors.toList());
        materialQueryDTO.setSmallCategoryIds(longs);

        if (materialQueryDTO == null) return new PageInfo<>();
        PageUtil.startPage(materialQueryDTO.getPageNum(), materialQueryDTO.getPageSize());
        List<MaterialQueryDTO> list = null;
        if ("Y".equals(materialQueryDTO.getIfCatalogMaterial())) {
            /*勾选目录化*/
            list = baseMapper.ceeaListPurchaseCatalogPage2(materialQueryDTO);
        } else {
            /*没有勾选目录化*/
            list = baseMapper.ceeaListPurchaseCatalogPage(materialQueryDTO);
        }
        return new PageInfo<MaterialQueryDTO>(list);
    }

    /**
     * @Description 分布查询采购目录
     * @Param [materialQueryDTO]
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.18 10:27
     * 如果勾选了目录化物料：
     * 业务实体+库存组织查找价格库是否为上架为是 + 有效期的价格库数据 + 查询附表
     * 2020-10-30奇怪需求：查询【物料附表！！】关联物料信息
     * <p>
     * 如果没有勾选目录化物料：
     * 按业务实体 + 库存组织查询物料维护的所有物料，采购目录页面显示查出来的所有数据
     * 按照查出来的物料 业务实体+库存组织+物料编码+物料描述 去价格库查询已上架+有效期内的行
     * 如果有，显示多个供应商，如果没有，显示一个物料
     * 2020-10-30奇怪需求：查询【物料主表！！】关联物料信息
     */
    @Override
    public PageInfo<MaterialQueryDTO> ceeaListPurchaseCatalogPageNew(MaterialQueryDTO materialQueryDTO) {
        Assert.notNull(materialQueryDTO.getCeeaOrgId(), "业务实体不能为空");
        Assert.notNull(materialQueryDTO.getCeeaOrganizationId(), "库存组织不能为空");
        // 获取采购分类条件ID
        List<String> categoryNameList = new ArrayList<>();
        if (StringUtil.isEmpty(materialQueryDTO.getCategoryName())) {
            categoryNameList.add(BigCategoryTypeEnum.SPARE_PARTS.getValue());
            categoryNameList.add(BigCategoryTypeEnum.COMPREHENSIVE_MATERIALS.getValue());
            List<PurchaseCategory> categoryList = purchaseCategoryService.list(new QueryWrapper<PurchaseCategory>().
                    in("CATEGORY_NAME", categoryNameList));
            materialQueryDTO.setCategoryId1(String.valueOf(categoryList.get(0).getCategoryId()));
            materialQueryDTO.setCategoryId2(String.valueOf(categoryList.get(1).getCategoryId()));
        } else {
            categoryNameList.add(materialQueryDTO.getCategoryName());
            List<PurchaseCategory> categoryList = purchaseCategoryService.list(new QueryWrapper<PurchaseCategory>().
                    in("CATEGORY_NAME", categoryNameList));
            materialQueryDTO.setCategoryId1(String.valueOf(categoryList.get(0).getCategoryId()));
        }


        settingQueryCategoryByRole(materialQueryDTO);
        // 是否目录化
        String ifCatalogMaterial = materialQueryDTO.getIfCatalogMaterial();
        PageUtil.startPage(materialQueryDTO.getPageNum(), materialQueryDTO.getPageSize());
        List<MaterialQueryDTO> materialQueryDTOS = null;
        if (YesOrNo.YES.getValue().equals(ifCatalogMaterial)) {
            //目录化
            materialQueryDTOS = this.baseMapper.queryMaterialQueryDTOByCatalogingY(materialQueryDTO);

        } else {
            //非目录化
            materialQueryDTOS = this.baseMapper.queryMaterialQueryDTOByCatalogingN(materialQueryDTO);
        }

        return new PageInfo<>(materialQueryDTOS);
    }

    /**
     * 根据角色设置查询品类信息；目前只有需求组、物控组（目录化）、物控组it
     * @param materialQueryDTO
     */
    private void settingQueryCategoryByRole(MaterialQueryDTO materialQueryDTO){
        //需求组，物控化目录组不显示it物料
        int showBit     =   0b00;
        int notShowIT   =   0b10;
        int showIT      =   0b01;
        List<Role> notShowITRoles = AppUserUtil.getLoginAppUser().getRolePermissions().stream().filter(
                role -> RoleEnum.LONGI_DG.getRoleCode().equals(role.getRoleCode()) ||
                        RoleEnum.LONGI_DD.getRoleCode().equals(role.getRoleCode())
        ).collect(Collectors.toList());
        if (!notShowITRoles.isEmpty()) {
            showBit = showBit | notShowIT;
        }
        //物控组-it，只显示it物料
        List<Role> showITRoles = AppUserUtil.getLoginAppUser().getRolePermissions().stream()
                .filter(role -> RoleEnum.LONGI_IT.getRoleCode().equals(role.getRoleCode())).collect(Collectors.toList());
        if(!showITRoles.isEmpty()){
            showBit = showBit | showIT;
        }
        //it品类
        Map<String, PurchaseCategory> itCategoryData = baseClient.getCategoryByCodes(Arrays.asList(SpecialBusinessConstant.IT_CATEGORY));
        List<Long> categoryIds = new ArrayList<>();
        for(String categoryCode : itCategoryData.keySet()){
            PurchaseCategory category = itCategoryData.get(categoryCode);
            categoryIds.add(category.getCategoryId());
        }
        if(showBit == showIT){
            materialQueryDTO.setCategoryIds(categoryIds);
            materialQueryDTO.setCategoryFilter("showIT");
        }else if(showBit == notShowIT){
            materialQueryDTO.setCategoryIds(categoryIds);
            materialQueryDTO.setCategoryFilter("notShowIT");
        }
    }

    /**
     * @Description 单个物料加入购物车
     * @Param [materialId]
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.18 14:22
     * <p>
     * 2020-10-30加入购物车逻辑修改
     * 校验购物车中是否有记录，需加上供应商这个条件
     * 如果是目录化物料 附表 添加购物车
     * 如果非目录化 直接加入
     */
    @Override
    public String ceeaAddToShoppingCart(MaterialQueryDTO mqd) {
        Assert.notNull(mqd.getMaterialId(), "物料Id不能为空");

        // 参数转换
        MaterialQueryDTO materialQueryDTO = new MaterialQueryDTO();
        materialQueryDTO.setMaterialId(mqd.getMaterialId()); // 物料ID
        materialQueryDTO.setMaterialCode(mqd.getCategoryMaterialCode());
        materialQueryDTO.setMaterialName(mqd.getCategoryMaterialName());
        materialQueryDTO.setCeeaOrgId(mqd.getCeeaOrgId()); // 业务实体
        materialQueryDTO.setCeeaOrganizationId(mqd.getCeeaOrganizationId()); // 库存组织
        materialQueryDTO.setIfCatalogMaterial(mqd.getIfCatalogMaterial()); // 是否目录化
        materialQueryDTO.setCategoryName(mqd.getCategoryName()); // 品类
        materialQueryDTO.setCeeaSupplierId(mqd.getCeeaSupplierId()); //供应商id

        // 获取采购分类条件ID
        List<String> categoryNameList = new ArrayList<>();
        if (StringUtil.isEmpty(materialQueryDTO.getCategoryName())) {
            categoryNameList.add(BigCategoryTypeEnum.SPARE_PARTS.getValue());
            categoryNameList.add(BigCategoryTypeEnum.COMPREHENSIVE_MATERIALS.getValue());
            List<PurchaseCategory> categoryList = purchaseCategoryService.list(new QueryWrapper<PurchaseCategory>().
                    in("CATEGORY_NAME", categoryNameList));
            materialQueryDTO.setCategoryId1(String.valueOf(categoryList.get(0).getCategoryId()));
            materialQueryDTO.setCategoryId2(String.valueOf(categoryList.get(1).getCategoryId()));
        } else {
            categoryNameList.add(materialQueryDTO.getCategoryName());
            List<PurchaseCategory> categoryList = purchaseCategoryService.list(new QueryWrapper<PurchaseCategory>().
                    in("CATEGORY_NAME", categoryNameList));
            materialQueryDTO.setCategoryId1(String.valueOf(categoryList.get(0).getCategoryId()));
        }
        // 是否目录化
        String ifCatalogMaterial = materialQueryDTO.getIfCatalogMaterial();
        PageUtil.startPage(materialQueryDTO.getPageNum(), materialQueryDTO.getPageSize());
        List<MaterialQueryDTO> list = null;
        if (YesOrNo.YES.getValue().equals(ifCatalogMaterial)) {
            /**
             * 是目录化
             */
            list = this.baseMapper.queryMaterialQueryDTOByCatalogingY1(materialQueryDTO);

        } else {
            /**
             * 非目录化
             * 1. 先根据业务实体和库存组织去物料维护筛选用于用于采购的物料
             * iMaterialOrgService
             */
            list = this.baseMapper.queryMaterialQueryDTOByCatalogingN(materialQueryDTO);
        }


        if (CollectionUtils.isEmpty(list)) {
            return "操作失败，未找当前物料";
        }
        MaterialQueryDTO mat = list.get(0);
        if (mat.getCategoryId() == null || StringUtils.isBlank(mat.getCategoryName())) {
            Assert.notNull(null, "物料的品类为空不能加入购物车");
        }
        if (StringUtils.isBlank(mat.getUnit()) || StringUtils.isBlank(mat.getUnitName())) {
            Assert.notNull(null, "物料的单位为空不能加入购物车");
        }

        // 构建购物车数据表
        ShopCart shopCart = new ShopCart(); // 购物车
        shopCart.setMaterialCode(mat.getMaterialCode()); // 物料编码
        shopCart.setMaterialName(mat.getMaterialName()); //物料描述
        shopCart.setOrgId(mat.getOrgId()); // 业务实体
        shopCart.setOrganizationId(mat.getOrganizationId()); // 库存组织
        shopCart.setStatus(ShopCartStatus.DRAFT.getCode()); // 未提交
        shopCart.setCreatedBy(AppUserUtil.getUserName()); // 创建人
        if (!Objects.isNull(mat.getCeeaSupplierId())) {
            shopCart.setSupplierId(mat.getCeeaSupplierId()); // 供应商
        }

        /*todo 校验购物车*/
        List<ShopCart> exists = pmClient.ceeaListByShopCart(shopCart);
        if (!CollectionUtils.isEmpty(exists)) {
            return "已存在于购物车中";
        }
        //获取小类Code
        QueryWrapper<CategoryBusiness> wrapper =new QueryWrapper<CategoryBusiness>();
        wrapper.eq(mat.getCategoryId()!=null,"CATEGORY_ID",mat.getCategoryId());
        mat.setCategoryCode(iCategoryBusinessService.getOne(wrapper).getCategoryCode());
        // 加入购物车
        pmClient.ceeaAddToShopCart(mat);
        return "已加入购物车";
    }

    /**
     * @Description 保存页面上编辑的供应商
     * @Param [materialQueryDTO]
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.22 10:01
     */
    @Override
    public String ceeaUpdateSupplier(List<MaterialQueryDTO> materialQueryDTO) {
        if (CollectionUtils.isEmpty(materialQueryDTO)) {
            return "请勾选物料行数据";
        }
        List<Long> ids = new ArrayList<>();
        for (MaterialQueryDTO mat : materialQueryDTO) {
            if (mat.getMaterialId() != null) {
                ids.add(mat.getMaterialId());
            }
        }
        if (ids.size() > 0) {
            List<MaterialItem> mats = this.listByIds(ids);
            for (MaterialItem mat : mats) {
                for (MaterialQueryDTO mi : materialQueryDTO) {
                    if (mat.getMaterialId().equals(mi.getMaterialId())) {
                        BeanCopyUtil.copyProperties(mat, mi);
                        break;
                    }
                }
            }
            this.saveOrUpdateBatch(mats);
        }
        return "操作成功";
    }

    @Override
    public MaterialItem findMaterialItemByMaterialCode(String materialCode) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("MATERIAL_CODE", materialCode);
        MaterialItem materialItem = getOne(queryWrapper);
        return materialItem;
    }

    /**
     * @Description 通知供应商
     * @Param [materialIds]
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.22 11:46
     */
    @Override
    public String ceeaNotifyVendor(List<Long> materialIds) {
        StringBuilder msg = new StringBuilder();
        if (CollectionUtils.isEmpty(materialIds)) {
            QueryWrapper<MaterialItem> qw = new QueryWrapper();
            qw.isNotNull("CEEA_SUPPLIER_CODE");

            List<MaterialItem> mats = this.list(qw);
            if (!CollectionUtils.isEmpty(mats)) {
                for (MaterialItem mat : mats) {
                    mat.setCeeaMaterialStatus(CeeaMaterialStatus.NOTIFIED.getCode());
                }
                this.saveOrUpdateBatch(mats);
            }
        } else {
            List<MaterialItem> mats = this.listByIds(materialIds);
            for (MaterialItem mat : mats) {
                if (StringUtils.isNoneBlank(mat.getCeeaSupplierCode())) {
                    mat.setCeeaMaterialStatus(CeeaMaterialStatus.NOTIFIED.getCode());
                } else {
                    msg.append("有供应商编码，才可通知供应商维护物料信息");
                    break;
                }
            }
            if (msg.length() < 1) {
                this.saveOrUpdateBatch(mats);
            }
        }
        return msg.toString();
    }

    /**
     * @Description 导入模板下载
     * @Param [response]
     * @Author dengyl23@meicloud.com
     * @Date 2020.09.23 11:17
     */
    @Override
    public void importModelDownload(HttpServletResponse response) throws Exception {
        String fileName = "物料维护导入模板";
        ArrayList<MaterialItemModelDto> MaterialItemFormDtos = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        List<Integer> rows = Arrays.asList(0);
        List<Integer> columns = Arrays.asList(0, 1, 3, 5);
        TitleColorSheetWriteHandler titleColorSheetWriteHandler = new TitleColorSheetWriteHandler(rows, columns, IndexedColors.RED.index);
        EasyExcelUtil.writeExcelWithModel(outputStream, MaterialItemFormDtos, MaterialItemModelDto.class, fileName, titleColorSheetWriteHandler);
    }

    /**
     * @Description 导出错误信息模板下载
     * @Param [response errorMessage]
     * @Author dengyl23@meicloud.com
     * @Date 2020.09.23 11:17
     */
    @Override
    public void outputModelDownload(HttpServletResponse response, List<MaterialItemErrorDto> errorDtos) throws Exception {
        String fileName = "物料维护导入结果";
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        List<Integer> rows = Arrays.asList(0);
        List<Integer> columns = Arrays.asList(0, 1);
        TitleColorSheetWriteHandler titleColorSheetWriteHandler = new TitleColorSheetWriteHandler(rows, columns, IndexedColors.RED.index);
        EasyExcelUtil.writeExcelWithModel(outputStream, errorDtos, MaterialItemErrorDto.class, fileName, titleColorSheetWriteHandler);
    }

    /**
     * @Description 导入文件
     * @Param [file]
     * @Author dengyl23@meicloud.com
     * @Date 2020.09.23 11:18
     */
    @Transactional
    @Override
    public void importExcel(MultipartFile file) throws Exception {
        try {
            //校验文件格式
            String originalFilename = file.getOriginalFilename();
            if (!EasyExcelUtil.isExcel(originalFilename)) {
                throw new RuntimeException("请导入正确的Excel文件");
            }
            InputStream inputStream = file.getInputStream();
            ArrayList<MaterialItem> materialItemArrayList = new ArrayList<>();
            List<Object> objects = EasyExcelUtil.readExcelWithModel(inputStream, MaterialItemModelDto.class);
            List<MaterialItem> updateList = new ArrayList<>();
            List<MaterialItem> addList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(objects)) {
                objects.forEach((object -> {
                    if (null != object) {
                        MaterialItemModelDto materialItemModelDto = (MaterialItemModelDto) object;
                        checkRequireParam(materialItemModelDto);
                        //判断物料code是否重复
                        QueryWrapper<MaterialItem> wrapper = new QueryWrapper<>();
                        wrapper.eq(StringUtils.isNotBlank(materialItemModelDto.getMaterialCode()),
                                "MATERIAL_CODE", materialItemModelDto.getMaterialCode());
                        wrapper.eq(materialItemModelDto.getMaterialName() != null,
                                "MATERIAL_NAME", materialItemModelDto.getMaterialName());
                        List<MaterialItem> existslist = this.list(wrapper);
                        if (CollectionUtils.isNotEmpty(existslist)) {
                            existslist.forEach(materialItem -> {
                                if (StringUtils.isNotBlank(materialItemModelDto.getCategoryName())) {
                                    materialItem.setCategoryName(materialItemModelDto.getCategoryName());
                                }
                                if (null != materialItemModelDto.getCeeaOrderQuantityMinimum()) {
                                    materialItem.setCeeaOrderQuantityMinimum(materialItemModelDto.getCeeaOrderQuantityMinimum());
                                }
                                if (StringUtils.isNotBlank(materialItemModelDto.getCeeaWeight())) {
                                    materialItem.setCeeaWeight(materialItemModelDto.getCeeaWeight());
                                }
                                if (StringUtils.isNotBlank(materialItemModelDto.getCeeaSize())) {
                                    materialItem.setCeeaSize(materialItemModelDto.getCeeaSize());
                                }
                                if (StringUtils.isNotBlank(materialItemModelDto.getCeeaBrand())) {
                                    materialItem.setCeeaBrand(materialItemModelDto.getCeeaBrand());
                                }
                                if (StringUtils.isNotBlank(materialItemModelDto.getCeeaColor())) {
                                    materialItem.setCeeaColor(materialItemModelDto.getCeeaColor());
                                }
                                if (StringUtils.isNotBlank(materialItemModelDto.getCeeaTexture())) {
                                    materialItem.setCeeaTexture(materialItemModelDto.getCeeaTexture());
                                }
                                if (StringUtils.isNotBlank(materialItemModelDto.getCeeaUsage())) {
                                    materialItem.setCeeaUsage(materialItemModelDto.getCeeaUsage());
                                }
                                materialItem.setCeeaDeliveryCycle(materialItemModelDto.getCeeaDeliveryCycle());
                                materialItem.setSpecification(materialItemModelDto.getSpecification());
                                updateList.add(materialItem);
                            });
                        } else {
                            MaterialItem materialItem = new MaterialItem();
                            BeanUtils.copyProperties(materialItemModelDto, materialItem);
                            Long id = IdGenrator.generate();
                            materialItem.setMaterialId(id);
                            materialItem.setCreationDate(new Date());
                            addList.add(materialItem);
                        }
                    }
                }));
            }
            this.updateBatchById(updateList);
            this.saveBatch(addList);
        } catch (Exception e) {
            log.info("import excel error:{}", e);
            throw new BaseException(ResultCode.IMPORT_EXCEPTIONS);
        }
    }

    @Override
    public MaterialItemDto checkImportExcel(MultipartFile file) throws Exception {
        try {
            //校验文件格式
            String originalFilename = file.getOriginalFilename();
            if (!EasyExcelUtil.isExcel(originalFilename)) {
                throw new RuntimeException("请导入正确的Excel文件");
            }
            InputStream inputStream = file.getInputStream();
            List<Object> objects = EasyExcelUtil.readExcelWithModel(inputStream, MaterialItemModelDto.class);
            List<MaterialItemErrorDto> errorDtos = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(objects)) {
                for (int i = 0; i < objects.size(); i++) {
                    if (null != objects.get(i)) {
                        MaterialItemModelDto materialItemModelDto = (MaterialItemModelDto) objects.get(i);
                        checkRequireParam(materialItemModelDto);
                        //判断物料code是否重复
                        QueryWrapper<MaterialItem> wrapper = new QueryWrapper<>();
                        wrapper.eq(StringUtils.isNotBlank(materialItemModelDto.getMaterialCode()),
                                "MATERIAL_CODE", materialItemModelDto.getMaterialCode());
                        wrapper.eq(materialItemModelDto.getMaterialName() != null,
                                "MATERIAL_NAME", materialItemModelDto.getMaterialName());
                        List<MaterialItem> existslist = this.list(wrapper);
                        if (CollectionUtils.isEmpty(existslist)) {
                            MaterialItemErrorDto errorDto = new MaterialItemErrorDto();
                            errorDto.setLineNumber(i + 1);
                            errorDto.setErrorMessage("物料不存在");
                            errorDtos.add(errorDto);
                        }
                    }
                }
            }
            MaterialItemDto materialItemDto = new MaterialItemDto();
            materialItemDto.setErrorDtos(errorDtos);
            if (errorDtos.size() > 0) {
                materialItemDto.setNumber(0);
                return materialItemDto;
            }
            MaterialItemErrorDto errorDto = new MaterialItemErrorDto();
            errorDto.setLineNumber(0);
            errorDto.setErrorMessage("所有行导入成功");
            materialItemDto.setNumber(1);
            return materialItemDto;
        } catch (Exception e) {
            log.info("import excel error:{}", e);
            throw new BaseException(ResultCode.IMPORT_EXCEPTIONS);
        }
    }

    public void checkRequireParam(MaterialItemModelDto materialItemModelDto) {
        if (StringUtils.isBlank(materialItemModelDto.getMaterialCode())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("物料编码不能为空!"));
        }
        if (StringUtils.isBlank(materialItemModelDto.getMaterialName())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("物料描述不能为空!"));
        }
        if (StringUtils.isBlank(materialItemModelDto.getSpecification())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("规格型号不能为空!"));
        }
        if (StringUtils.isBlank(materialItemModelDto.getCeeaDeliveryCycle())) {
            throw new BaseException(LocaleHandler.getLocaleMsg("送货周期不能为空!"));
        }
    }

    /**
     * @Description 导出物料维护数据
     * @Param: [materialItemExportParam, response]
     * @Return: void
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/9/25 9:28
     */
    @Override
    public void exportMaterialItemExcel(ExportExcelParam<MaterialItem> materialItemExportParam, HttpServletResponse response) throws IOException {
        // 获取导出的数据
        List<List<Object>> dataList = this.queryExportData(materialItemExportParam);
        // 标题
        List<String> head = materialItemExportParam.getMultilingualHeader(materialItemExportParam, MaterialItemExportUtils.getMaterItemTitles());
        // 文件名
        String fileName = materialItemExportParam.getFileName();
        // 开始导出
        EasyExcelUtil.exportStart(response, dataList, head, fileName);
    }


    /**
     * @Description 查询导出数据并转换excel数据集
     * @Param: [materialItemExportParam]
     * @Return: java.util.List<java.util.List < java.lang.Object>>
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/9/24 10:30
     */
    private List<List<Object>> queryExportData(ExportExcelParam<MaterialItem> materialItemExportParam) {
        MaterialItem queryParam = materialItemExportParam.getQueryParam(); // 查询参数
        /**
         * 先查询要导出的数据有多少条, 大于2万是建议用户填分页参数进行分页导出,每页不能超过2万
         */
        int count = queryCountByParam(queryParam);
        if(count > 20000){
            /**
             * 要导出的数据超过20000, 要求用户选择分页参数
             */
            if(StringUtil.notEmpty(queryParam.getPageSize()) && StringUtil.notEmpty(queryParam.getPageNum())){
                Assert.isTrue(queryParam.getPageSize() <=20000,"分页导出单页上限20000");
            }else {
                throw new BaseException("当前要导出的数据超过20000条,请选择分页导出,单页上限20000;");
            }
        }

        boolean flag = null != queryParam.getPageSize() && null != queryParam.getPageNum();
        if (flag) {
            // 设置分页
            PageUtil.startPage(queryParam.getPageNum(), queryParam.getPageSize());
        }
        List<MaterialItem> materialItemList = this.queryMaterialItemByParam(queryParam);
        // 转Map
        List<Map<String, Object>> mapList = BeanMapUtils.objectsToMaps(materialItemList);
        List<String> titleList = materialItemExportParam.getTitleList();
        List<List<Object>> results = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(mapList)) {
            mapList.forEach((map) -> {
                List<Object> list = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(titleList)) {
                    titleList.forEach((title) -> {
                        Object value = map.get(title);
                        if ("ceeaMaterialStatus".equals(title)) {
                            String ceeaMaterialStatus = (String) value;
                            list.add(translationCeeaMaterialStatus(ceeaMaterialStatus));
                        } else if ("ceeaIfCatalogMaterial".equals(title)) {
                            String ceeaIfCatalogMaterial = (String) value;
                            if (StringUtils.isNotBlank(ceeaIfCatalogMaterial)) {
                                list.add(ceeaIfCatalogMaterial.equals("Y") ? "是" : "否");
                            } else {
                                list.add("");
                            }
                        } else {
                            if (null != value) {
                                list.add(value);
                            } else {
                                list.add("");
                            }
                        }
                    });
                }
                results.add(list);
            });
        }
        return results;
    }

    private String translationCeeaMaterialStatus(String type) {
        if (StringUtils.isNotBlank(type)) {
            switch (type) {
                case "NOT_NOTIFIED":
                    return CeeaMaterialStatus.NOT_NOTIFIED.getType();
                case "MAINTAINED":
                    return CeeaMaterialStatus.MAINTAINED.getType();
                case "NOTIFIED":
                    return CeeaMaterialStatus.NOTIFIED.getType();
                default:
                    return "";
            }
        } else {
            return "";
        }

    }

    /*
     * @Description 获取导出表数据
     * @Param: [queryParam]
     * @Return: java.util.List<com.midea.cloud.srm.model.base.material.MaterialItem>
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/9/24 10:29
     */
    private List<MaterialItem> queryMaterialItemByParam(MaterialItem queryParam) {
        QueryWrapper<MaterialItem> queryWrapper = new QueryWrapper<>();
        String ceeaMaterialStatus = queryParam.getCeeaMaterialStatus();
        String categoryName = queryParam.getCategoryName();
        String materialName = queryParam.getMaterialName();
        String ifCatalog = queryParam.getCeeaIfCatalogMaterial();
        String ceeaSupplierName = queryParam.getCeeaSupplierName();
        String contractNo = queryParam.getCeeaContractNo();
        queryWrapper.lambda().eq(StringUtils.isNotBlank(categoryName), MaterialItem::getCategoryName, categoryName)
                .eq(StringUtils.isNotBlank(ceeaMaterialStatus), MaterialItem::getCeeaMaterialStatus, ceeaMaterialStatus)
                .eq(StringUtils.isNotBlank(materialName), MaterialItem::getMaterialName, materialName)
//                .eq(StringUtils.isNotBlank(ifCatalog), MaterialItem::getCeeaIfCatalogMaterial, ifCatalog)
                .eq(StringUtils.isNotBlank(ceeaSupplierName), MaterialItem::getCeeaSupplierName, ceeaSupplierName)
                .eq(StringUtils.isNotBlank(contractNo), MaterialItem::getCeeaContractNo, contractNo);
        if("Y".equals(ifCatalog)){
            queryWrapper.lambda().eq(MaterialItem::getCeeaIfCatalogMaterial,ifCatalog);
        }else {
            queryWrapper.and(wrapper ->wrapper.ne("CEEA_IF_CATALOG_MATERIAL","Y").
                    or().isNull("CEEA_IF_CATALOG_MATERIAL"));
        }
        List<MaterialItem> materialItemList = list(queryWrapper);
        return materialItemList;
    }

    private int queryCountByParam(MaterialItem queryParam) {
        QueryWrapper<MaterialItem> queryWrapper = new QueryWrapper<>();
        String ceeaMaterialStatus = queryParam.getCeeaMaterialStatus();
        String categoryName = queryParam.getCategoryName();
        String materialName = queryParam.getMaterialName();
        String ifCatalog = queryParam.getCeeaIfCatalogMaterial();
//        if ("N".equals(materialItem.getCeeaIfCatalogMaterial())) {
//            wrapper.notIn("CEEA_IF_CATALOG_MATERIAL", "Y").or().isNull("CEEA_IF_CATALOG_MATERIAL");
//        }
        String ceeaSupplierName = queryParam.getCeeaSupplierName();
        String contractNo = queryParam.getCeeaContractNo();
        queryWrapper.lambda().eq(StringUtils.isNotBlank(categoryName), MaterialItem::getCategoryName, categoryName)
                .eq(StringUtils.isNotBlank(ceeaMaterialStatus), MaterialItem::getCeeaMaterialStatus, ceeaMaterialStatus)
                .eq(StringUtils.isNotBlank(materialName), MaterialItem::getMaterialName, materialName)
//                .eq(StringUtils.isNotBlank(ifCatalog), MaterialItem::getCeeaIfCatalogMaterial, ifCatalog)
                .eq(StringUtils.isNotBlank(ceeaSupplierName), MaterialItem::getCeeaSupplierName, ceeaSupplierName)
                .eq(StringUtils.isNotBlank(contractNo), MaterialItem::getCeeaContractNo, contractNo);
        if("Y".equals(ifCatalog)){
            queryWrapper.lambda().eq(MaterialItem::getCeeaIfCatalogMaterial,ifCatalog);
        }else {
            queryWrapper.and(wrapper -> wrapper.ne("CEEA_IF_CATALOG_MATERIAL", "Y").
                    or().isNull("CEEA_IF_CATALOG_MATERIAL"));
        }
        return this.count(queryWrapper);
    }

    @Override
    public List<PurchaseCatalogQueryDto> listCeeaListPurchaseCatalog(List<PurchaseCatalogQueryDto> purchaseCatalogQueryDtoList) {
        return baseMapper.PurchaseCatalogQueryBatch(purchaseCatalogQueryDtoList);
    }

    /**
     * 设置erp物料Id（导数据，忽略）
     */
    @Override
    @Transactional
    public void setErpMaterialId() {
        List<MaterialItem> materialItems = this.list();
        List<MaterialItem> updateMaterialItems = new ArrayList<>();
        for (MaterialItem materialItem : materialItems) {
            if (materialItem != null) {
                String materialCode = materialItem.getMaterialCode();
                Assert.notNull(materialCode, "主键Id为[" + materialItem.getMaterialId() + "]的物料的物料编码为空！");
                List<ErpMaterialItem> erpMaterialItems = iErpMaterialItemService.list(
                        new QueryWrapper<>(new ErpMaterialItem().setItemNumber(materialCode)));
                if (CollectionUtils.isEmpty(erpMaterialItems)) {
                    log.info("根据物料编码[" + materialCode + "]查询ceea_base_material_item表找不到对应的物料！");
                } else {
                    Long erpMaterialId = erpMaterialItems.get(0).getItemId();
                    if (erpMaterialId == null) {
                        log.info("根据物料编码[" + materialCode + "]查询ceea_base_material_item表对应的物料的erp物料Id为空！");
                    }
                    materialItem.setCeeaErpMaterialId(erpMaterialId);
                    updateMaterialItems.add(materialItem);
                }
            }
        }
        //循环结束
        this.updateBatchById(updateMaterialItems);
    }


    @Override
    public MaterialItem saveOrUpdateSrmMaterialItem(ErpMaterialItem erpMaterialItem,
                                                    MaterialItem saveMaterialItem,
                                                    MaterialCache materialCache) {
        if (Objects.isNull(saveMaterialItem)) {
            saveMaterialItem = new MaterialItem();
            saveMaterialItem.setMaterialId(IdGenrator.generate());
            saveMaterialItem = setMaterialItemField(erpMaterialItem,
                    saveMaterialItem,
                    materialCache);
            materialCache.getSaveMaterialItemList().add(saveMaterialItem);
        } else {
            saveMaterialItem = setMaterialItemField(erpMaterialItem,
                    saveMaterialItem,
                    materialCache);
            materialCache.getUpdateMaterialItemList().add(saveMaterialItem);
        }
        return saveMaterialItem;
    }

    /**
     * @Description 保存或更新物料信息(批量)
     */
    @Override
    public void saveOrUpdateSrmMaterialItems(List<ErpMaterialItem> erpMaterialItemList, Map<String, MaterialItem> dbMaterialItemMap, AtomicInteger successCount, AtomicInteger errorCount) {
//        List<MaterialItem> saveMaterialList = new ArrayList<>();
//        List<MaterialItem> updateMaterialList = new ArrayList<>();
//        MaterialItem saveMaterialItem = new MaterialItem();
//        MaterialItem updateMaterialItem = new MaterialItem();
//        for (ErpMaterialItem erpMaterialItem : erpMaterialItemList) {
//            //存在即更新
//            if (dbMaterialItemMap.containsKey(erpMaterialItem.getItemNumber())) {
//                MaterialItem material = dbMaterialItemMap.get(erpMaterialItem.getItemNumber());
//                updateMaterialItem.setMaterialId(material.getMaterialId());
//                updateMaterialItem = setMaterialItemField(erpMaterialItem, updateMaterialItem);
//                updateMaterialList.add(updateMaterialItem);
//            } else {
//                Long id = IdGenrator.generate();
//                saveMaterialItem.setMaterialId(id);
//                saveMaterialItem = setMaterialItemField(erpMaterialItem, saveMaterialItem);
//                saveMaterialList.add(saveMaterialItem);
//            }
//
//            try {
//                if (CollectionUtils.isNotEmpty(saveMaterialList)) {
//                    this.saveBatch(saveMaterialList);
//                }
//                if (CollectionUtils.isNotEmpty(updateMaterialList)) {
//                    this.updateBatchById(updateMaterialList);
//                }
//                int success = saveMaterialList.size() + updateMaterialList.size();
//                successCount.addAndGet(success);
//            } catch (Exception e) {
//                e.printStackTrace();
//                errorCount.addAndGet(500);
//            }
//        }
    }

    /**
     * @param erpMaterialItem
     * @description 设置物料的字段（除了materialId之外的）@modifiedBy xiexh12@meicloud.com
     */
    private MaterialItem setMaterialItemField(ErpMaterialItem erpMaterialItem,
                                              MaterialItem materialItem,
                                              MaterialCache materialCache) {
        List<Category> erpCategoryList = erpMaterialItem.getCategoryList();

        materialItem.setMaterialCode(erpMaterialItem.getItemNumber());
        materialItem.setMaterialName(erpMaterialItem.getItemDescZhs());
        if (itemStatusSet.contains(erpMaterialItem.getItemStatus())) {
            materialItem.setStatus("Y");
        } else {
            materialItem.setStatus("N");
        }
        materialItem.setUnit(erpMaterialItem.getPrimaryUnitOfMeasure());
        materialItem.setUnitName(erpMaterialItem.getPrimaryUnitOfMeasure());
        //设置物料的erp物料Id
        materialItem.setCeeaErpMaterialId(erpMaterialItem.getItemId());
        //将物料接口里的单位维护到单位表里 #保存单位记录，DB操作不多
        saveOrUpdatePurchaseUnit(erpMaterialItem.getPrimaryUnitOfMeasure(), materialCache);

        /**开始 如果传入的物料有物料分类，则保存物料分类（物料小类），否则置空 **/
        AtomicBoolean hasCategory = new AtomicBoolean(false);
        if (CollectionUtils.isNotEmpty(erpCategoryList)) {
            erpMaterialItem.getCategoryList().forEach(category -> {
                if (IErpService.CATEGORY_SET_CODE.equals(category.getCategorySetCode())
                        && IErpService.CATEGORY_SET_NAME_ZHS.equals(category.getCategorySetNameUs())
                        && category.getSetValue() != null
                        && category.getSetValueDescZhs() != null
                        && !IErpService.CATEGORY_SET_VALUE_DEFAULT.equals(category.getSetValue())
                        && !IErpService.CATEGORY_VALUE_DES_ZHS.equals(category.getSetValueDescZhs())) {
                    hasCategory.set(true);
                }
            });
        }
        //获取并设置物料的品类全路径名称
        if (hasCategory.get()) {
            materialItem = setMaterialPurchaseCategory(materialItem, erpCategoryList, materialCache);
        }
        /**结束 如果传入的物料有物料分类，则保存物料分类（物料小类），否则置空 **/

        /**开始 如果传入的物料有库存分类，解析保存库存分类，否则置空 **/
        AtomicBoolean hasInventory = new AtomicBoolean(false);
        erpCategoryList.forEach(category -> {
            if (IErpService.INVENTORY_SET_CODE.equals(category.getCategorySetCode())
                    && IErpService.INVENTORY_SET_NAME_ZHS.equals(category.getCategorySetNameZhs())
                    && category.getSetValue() != null
                    && category.getSetValueDescZhs() != null) {
                hasInventory.set(true);
            }
        });
        if (hasInventory.get()) {
            materialItem = setMaterialInventory(materialItem, erpCategoryList);
        }

        return materialItem;
    }

    /**
     * 设置物料的库存类别属性 库存类别编码、库存类别名称
     *
     * @param saveMaterialItem
     * @param erpCategoryList
     * @return
     */
    private MaterialItem setMaterialInventory(MaterialItem saveMaterialItem, List<Category> erpCategoryList) {
        for (Category category : erpCategoryList) {
            String categorySetCode = category.getCategorySetCode(); //获取类别集代码 '1'
            String categorySetName = category.getCategorySetNameZhs(); //获取类别集名称 '库存'

            //开始 遍历循环得到库存类别
            if (StringUtils.isNotBlank(categorySetCode) && StringUtils.isNotBlank(categorySetName)
                    && categorySetCode.equals("1") && categorySetName.equals("库存")) {
                //设置库存分类编码和库存分类名称
                String inventoryCode = category.getSetValue();
                String inventoryName = category.getSetValueDescZhs();
                saveMaterialItem.setInventoryCode(inventoryCode);
                saveMaterialItem.setInventoryName(inventoryName);
            }
            //结束 遍历循环得到库存类别
        }
        return saveMaterialItem;
    }


    private final String PURCHASE_CATEGORY_CACHE = "PURCHASE_CATEGORY_CACHE_LIST";//品类分工

    /**
     * 设置物料的类别属性
     * 物料小类Id，物料小类名称，品类struct，品类categoryFullName
     *
     * @param saveMaterialItem
     * @param erpCategoryList
     * @return
     */
    private MaterialItem setMaterialPurchaseCategory(MaterialItem saveMaterialItem,
                                                     List<Category> erpCategoryList,
                                                     MaterialCache materialCache) {
        /** 根据物料对应的品类编码去品类表查询品类（品类表里那个编码上的唯一索引不能取消） **/
        List<PurchaseCategory> cacheCategorys = materialCache.getCategorys();
        if (Objects.isNull(cacheCategorys) || cacheCategorys.isEmpty()) {
            //缓存中不存在就查询数据库
            cacheCategorys = iPurchaseCategoryService.list();
            materialCache.getCategorys().addAll(cacheCategorys);
        }
        for (Category category : erpCategoryList) {
            String categorySetCode = category.getCategorySetCode(); //获取类别集代码 '1100000101'
            String categorySetName = category.getCategorySetNameUs(); //获取类别集名称 'SRM物料类别'

            //开始 遍历循环得到SRM物料类别
            if (IErpService.CATEGORY_SET_CODE.equals(categorySetCode) &&
                    IErpService.CATEGORY_SET_NAME_ZHS.equals(categorySetName)) {

                //获取物料的类别编码 如300615
                String purchaseCategoryCode = category.getSetValue();

                //校验传入的物料小类是否已经维护
                //Assert.notEmpty(categorys, "找不到对应的物料小类，请先维护物料小类！");
                if (CollectionUtils.isNotEmpty(cacheCategorys)) {
                    PurchaseCategory purchaseCategory = null;
                    for (PurchaseCategory cacheData : cacheCategorys) {
                        if (cacheData.getCategoryCode().equals(purchaseCategoryCode)) {
                            purchaseCategory = cacheData;
                            break;
                        }
                    }
                    //缓存中没有找到该品类，物料的品类四属性都置空
                    if (Objects.isNull(purchaseCategory)) {
                        continue;
                    }

                    saveMaterialItem.setCategoryId(purchaseCategory.getCategoryId());
                    saveMaterialItem.setCategoryName(purchaseCategory.getCategoryName());
                    //校验传入的品类在srm品类表中的3级分类结构是否为空
                    Assert.notNull(purchaseCategory.getStruct(), "物料对应的物料小类对应的3级分类结构为空！");
                    //开始 设置物料对应品类的3级分类结构struct以及品类全名称categoryFullName
                    saveMaterialItem.setStruct(purchaseCategory.getStruct());
                    PurchaseCategory cate = new PurchaseCategory().setStruct(purchaseCategory.getStruct());

                    //根据品类获取全称
                    String struct = cate.getStruct();
                    List<Long> categoryIds = StringUtil.stringConvertNumList(struct, "-");
                    if (!CollectionUtils.isEmpty(categoryIds)) {
                        List<PurchaseCategory> list = materialCache.getCategorys();
                        //获取品类名称全称
                        String[] categoryFullNames = new String[categoryIds.size()];
                        for (int i = 0; i < categoryIds.size(); i++) {
                            Long categoryId = categoryIds.get(i);
                            for (PurchaseCategory cacheCategory : list) {
                                if (categoryId.equals(cacheCategory.getCategoryId())) {
                                    categoryFullNames[i] = cacheCategory.getCategoryName();
                                    break;
                                }
                            }
                        }
                        //拼接成 “-”连接的形式
                        String categoryFullName = String.join("-", categoryFullNames);
                        cate.setCategoryFullName(categoryFullName);
                    }
                    //
                    Assert.notNull(cate.getCategoryFullName(), "物料对应的物料小类对应的品类全路径名称为空！");
                    String saveCategoryFullName = cate.getCategoryFullName();
                    //结束 设置物料对应品类的3级分类结构struct以及品类全名称categoryFullName
                    saveMaterialItem.setCategoryFullName(saveCategoryFullName);
//                    iPurchaseCategoryService.SaveOrUpdateByCategoryFullCode(purchaseCategoryCode, saveCategoryFullName);
                    break;
                }
            }
            //结束 遍历循环得到SRM物料类别
        }
        return saveMaterialItem;
    }


    private final String PURCHASE_UNIT = "IMPORT_PURCHASE_UNIT";

    /**
     * 将物料接口里的单位维护到单位表里
     *
     * @param purchaseUnit
     */
    private void saveOrUpdatePurchaseUnit(String purchaseUnit, MaterialCache materialCache) {
        QueryWrapper<PurchaseUnit> unitQueryWrapper = new QueryWrapper<>();
        unitQueryWrapper.eq("UNIT_CODE", purchaseUnit);
        unitQueryWrapper.eq("UNIT_NAME", purchaseUnit);

        List<PurchaseUnit> purchaseUnits = materialCache.getPurchaseUnits();
        if (Objects.isNull(purchaseUnits) || purchaseUnits.isEmpty()) {
            purchaseUnits = iPurchaseUnitService.list();
            materialCache.getPurchaseUnits().addAll(purchaseUnits);
        }
        AtomicBoolean dbExistFlag = new AtomicBoolean(false);
        purchaseUnits.stream().forEach(e -> {
            if (purchaseUnit.equals(e.getUnitName()) && purchaseUnit.equals(e.getUnitCode())) {
                dbExistFlag.set(true);
            }
        });
        if (!dbExistFlag.get()) {
            PurchaseUnit saveUnit = new PurchaseUnit();
            Long unitId = IdGenrator.generate();
            saveUnit.setUnitId(unitId);
            saveUnit.setUnitCode(purchaseUnit);
            saveUnit.setUnitName(purchaseUnit);
            saveUnit.setEnabled("Y");
            saveUnit.setSourceSystem(DataSourceEnum.ERP_SYS.getValue());
            iPurchaseUnitService.save(saveUnit);
            //保存到缓存中
            materialCache.getPurchaseUnits().add(saveUnit);
        }
    }


    /**
     * 保存物料库存组织关系
     *
     * @param erpMaterialItem
     * @param materialItem
     * @param invs
     * @param invOUs
     * @throws BaseException
     */
    @Override
    public void saveOrUpdateSrmMaterialOrg(ErpMaterialItem erpMaterialItem,
                                           MaterialItem materialItem,
                                           List<Organization> invs,
                                           Map<String, Organization> invOUs,
                                           MaterialCache materialCache) throws BaseException {
        /** 查询Srm物料表scc_base_material_item获取material_id **/
        Long materialId = materialItem.getMaterialId();

        //查询库存组织
        String organizationCode = erpMaterialItem.getOrgCode();
        List<Organization> organizations = invs.stream().filter(
                organization -> organizationCode.equals(organization.getOrganizationCode())
        ).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(organizations)) {
            throw new BaseException("维护物料库存组织关系时找不到对应的库存组织！");
        }
        if (Objects.isNull(organizations.get(0).getParentOrganizationIds())) {
            throw new BaseException("维护物料库存组织关系时根据库存组织编码查询对应的业务实体Id为空！");
        }

        Long organizationId = organizations.get(0).getOrganizationId();
        String organizationName = organizations.get(0).getOrganizationName();
        Long orgId = Long.valueOf(organizations.get(0).getParentOrganizationIds());

        //查询这条物料所属库存组织对应的业务实体
        Organization ou = Objects.nonNull(invOUs.get(organizationCode)) ? invOUs.get(organizationCode) : null;
        if (Objects.isNull(ou)) {
            throw new BaseException("维护物料库存组织关系时找不到对应的业务实体！");
        }
        if (Objects.isNull(ou.getOrganizationName())) {
            throw new BaseException("维护物料库存组织关系时业务实体名称为空！");
        }

        String orgName = ou.getOrganizationName();
        MaterialOrg saveMaterialOrg = new MaterialOrg();
        saveMaterialOrg.setMaterialId(materialId); //物料Id
        saveMaterialOrg.setOrganizationId(organizationId); //库存组织Id
        saveMaterialOrg.setOrganizationName(organizationName); //库存组织名称
        saveMaterialOrg.setOrgId(orgId); //业务实体Id
        saveMaterialOrg.setOrgName(orgName); //业务实体名称
        //物料状态 有效、无效
        if (itemStatusSet.contains(erpMaterialItem.getItemStatus())) {
            saveMaterialOrg.setItemStatus("Y");
        } else {
            saveMaterialOrg.setItemStatus("N");
        }
        saveMaterialOrg.setUserPurchase(erpMaterialItem.getPurchasingEnableFlag()); //可采购
        saveMaterialOrg.setStockEnableFlag(erpMaterialItem.getStockEnableFlag()); //可存储

        //查询数据库中是否有记录
        boolean isCreate = true;
        for (int i = 0; i < materialCache.getInDBMaterialOrgList().size(); i++) {
            MaterialOrg materialOrg = materialCache.getInDBMaterialOrgList().get(i);
            if (materialOrg.getMaterialId().equals(materialId) &&
                    materialOrg.getOrganizationId().equals(organizationId) &&
                    materialOrg.getOrgId().equals(orgId)) {
                saveMaterialOrg.setMaterialOrgId(materialOrg.getMaterialOrgId());
                isCreate = false;
                break;
            }
        }

        if (isCreate) {
            saveMaterialOrg.setMaterialOrgId(IdGenrator.generate()); //主键Id
            materialCache.getSaveMaterialOrgList().add(saveMaterialOrg);
            materialCache.getInDBMaterialOrgList().add(saveMaterialOrg);
        } else {
            materialCache.getUpdateMaterialOrgList().add(saveMaterialOrg);
        }
    }

    public static final List<String> code1 = Arrays.stream(new String[]{"10", "30", "40", "50", "60"}).collect(Collectors.toList());
    public static final List<String> code2 = Arrays.asList(new String[]{"20"});
    public static final Set<String> code3 = Arrays.stream(new String[]{"70"}).collect(Collectors.toSet());
    public static final Set<String> code4 = Arrays.stream(new String[]{"7001", "7002", "7003", "7004", "7005", "7007"}).collect(Collectors.toSet());

    /**
     * 根据物料筛选可填税率
     *
     * @param materialId
     * @return
     */
    @Override
    public List<PurchaseTax> queryTaxByItem(Long materialId) {
        List<PurchaseTax> purchaseTaxes = null;
        Assert.notNull(materialId, "缺少参数:materialId");
        /**
         * 1、物料大类编码为10、30、40、50、60的物料，仅能选择IN开头的税率，如IN 13,IN 11 ,不包括IN VAT 和IN-ASSET；
         * 2、物料大类编码为20，仅能选择IN-ASSET开头的税率；
         * 3、物料大类编码为70，其中中类是7001,7002,7003,7004,7005,7007且物料编码开头是61的行，仅能选择IN-ASSET开头的税率；其他的仅能选择IN开头；
         */
        /**
         * 修正:
         * 大类是70且物料编码开头是（61或78）的行税码选择IN-ASSET，反之70大类的其他行选择税码为IN。
         */
        MaterialItem materialItem = this.getById(materialId);
        Assert.notNull(materialItem, "找不到该物料");
        // 采购员分类编码
        String struct = materialItem.getStruct();
        Assert.notNull(struct, "请维护该物料的采购分类");
        List<String> idList = Arrays.asList(struct.split("-"));
        // 物料大类
        PurchaseCategory purchaseCategory = iPurchaseCategoryService.getById(Long.parseLong(idList.get(0).trim()));
        String categoryCode = purchaseCategory.getCategoryCode();
        if (code1.contains(categoryCode)) {
            /**
             * 仅能选择IN开头的税率，如IN 13,IN 11 ,不包括IN VAT 和IN-ASSET；
             */
            purchaseTaxes = purchaseTaxMapper.queryTaxBy1();
        } else if (code2.contains(categoryCode)) {
            /**
             * 仅能选择IN-ASSET开头的税率；
             */
            purchaseTaxes = purchaseTaxMapper.queryTaxBy2();
        } else if (code3.contains(categoryCode)) {
            /**
             * 大类是70且物料编码开头是（61或78）的行税码选择IN-ASSET，反之70大类的其他行选择税码为IN。
             */

//            String categoryCode1 = iPurchaseCategoryService.getById(Long.parseLong(idList.get(1).trim())).getCategoryCode();
            if (StringUtils.startsWith(materialItem.getMaterialCode(), "61") || StringUtils.startsWith(materialItem.getMaterialCode(), "78")) {
                purchaseTaxes = purchaseTaxMapper.queryTaxBy2();
            } else {
                purchaseTaxes = purchaseTaxMapper.queryTaxBy3();
            }
        }
        return purchaseTaxes;
    }

    /**
     * 创建订单-税率可选规则
     * 1、物料大类编码为10、30、40、60的物料，仅能选择IN开头的税率，如IN 13,IN 11 ,不包括IN VAT 和IN-ASSET；
     * 2、物料大类编码为20且物料编码开头是（61或78）的行，仅能选择IN-ASSET开头的税率；
     * 3、物料大类编码是70且物料编码开头是（61或78）的行，仅能选择IN-ASSET开头的税率；其他的仅能选择IN开头；
     * 4. 税率为vat0的没有限制,都可选
     *
     * @param materialId
     * @return
     */
    @Override
    public List<PurchaseTax> queryTaxByItemForOrder(Long materialId) {
        List<String> bigCategoryCodeList = Arrays.asList("10", "30", "40", "60");
        List<String> materialCodeList = Arrays.asList("61", "78");

        List<PurchaseTax> purchaseTaxes = null;
        MaterialItem materialItem = null;
        String struct = null;
        List<String> idList = null;

        try {
            Assert.notNull(materialId, "缺少参数:materialId");
            materialItem = this.getById(materialId);
            Assert.notNull(materialItem, "找不到该物料");
            // 采购员分类编码
            struct = materialItem.getStruct();
            Assert.notNull(struct, "请维护该物料的采购分类");
            idList = Arrays.asList(struct.split("-"));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        // 物料大类
        PurchaseCategory purchaseCategory = iPurchaseCategoryService.getById(Long.parseLong(idList.get(0).trim()));
        String categoryCode = purchaseCategory.getCategoryCode();

        if (bigCategoryCodeList.contains(categoryCode)) {
            /* 物料大类编码为10、30、40、60的物料，仅能选择IN开头的税率，如IN 13,IN 11 ,不包括IN VAT 和IN-ASSET；*/
            purchaseTaxes = purchaseTaxMapper.queryTaxBy1();
        } else if ("20".equals(categoryCode) &&
                (StringUtils.startsWith(materialItem.getMaterialCode(), "61") ||
                        StringUtils.startsWith(materialItem.getMaterialCode(), "78"))
        ) {
            /* 物料大类编码为20且物料编码开头是（61或78）的行，仅能选择IN-ASSET开头的税率；*/
            purchaseTaxes = purchaseTaxMapper.queryTaxBy2();
        } else if ("70".equals(categoryCode)) {
            /**
             * 物料大类编码是70且物料编码开头是（61或78）的行，仅能选择IN-ASSET开头的税率；
             * 其他的仅能选择IN开头；
             */
            if (StringUtils.startsWith(materialItem.getMaterialCode(), "61") ||
                    StringUtils.startsWith(materialItem.getMaterialCode(), "78")) {
                purchaseTaxes = purchaseTaxMapper.queryTaxBy2();
            } else {
                purchaseTaxes = purchaseTaxMapper.queryTaxBy3();
            }
        } else {
            purchaseTaxes = purchaseTaxMapper.selectList(new QueryWrapper<>());
        }
        /*如果不包含VAT 0,需要添加VAT 0税率*/
        boolean hasVAT = false;
        for(PurchaseTax purchaseTax : purchaseTaxes){
            if("VAT 0".equals(purchaseTax.getTaxKey())){
                hasVAT = true;
            }
        }
        if(!hasVAT){
            List<PurchaseTax> purchaseTaxList = purchaseTaxMapper.selectList(new QueryWrapper<>(new PurchaseTax().setTaxKey("VAT 0").setEnabled("Y")));
            if(CollectionUtils.isNotEmpty(purchaseTaxList)){
                purchaseTaxes.add(purchaseTaxList.get(0));
            }
        }

        return purchaseTaxes;
    }

    //返回物料id和可选税率Key
    @Override
    public Map<Long, Set<String>> queryTaxItemBatch(Collection<Long> materialIds) {
        List<String> bigCategoryCodeList = Arrays.asList("10", "30", "40", "60");

        List<MaterialItem> list = list(Wrappers.lambdaQuery(MaterialItem.class)
                .select(MaterialItem::getMaterialId, MaterialItem::getStruct, MaterialItem::getMaterialName, MaterialItem::getMaterialCode)
                .in(MaterialItem::getMaterialId, materialIds)
        );
        for (MaterialItem materialItem : list) {
            if (StringUtil.isEmpty(materialItem.getStruct())) {
                throw new BaseException(String.format("请维护物料%s的采购分类"));
            }
        }
        Map<Long, Set<String>> result = new HashMap<>();
        Function<MaterialItem, Long> function = materialItem -> Long.valueOf(materialItem.getStruct().split("-")[0]);
        if (Objects.nonNull(list) && !list.isEmpty()) {
            Map<Long, List<MaterialItem>> categoryIdMap = list.stream().map(e -> e.setStruct(e.getStruct().split("-1")[0]))
                    .collect(Collectors.groupingBy(function));
            if (!org.springframework.util.CollectionUtils.isEmpty(categoryIdMap)) {
                Map<Long, PurchaseCategory> categoryMap = iPurchaseCategoryService.list(Wrappers.lambdaQuery(PurchaseCategory.class)
                        .select(PurchaseCategory::getCategoryId, PurchaseCategory::getCategoryCode)
                        .in(PurchaseCategory::getCategoryId, categoryIdMap.keySet())
                ).stream().collect(Collectors.toMap(PurchaseCategory::getCategoryId, Function.identity()));
                List<PurchaseTax> queryTaxBy1 = null;
                List<PurchaseTax> queryTaxBy2 = null;
                List<PurchaseTax> queryTaxBy3 = null;
                List<PurchaseTax> all = null;

                for (Map.Entry<Long, PurchaseCategory> entry : categoryMap.entrySet()) {
                    PurchaseCategory value = entry.getValue();
                    String categoryCode = value.getCategoryCode();
                    Long categoryId = value.getCategoryId();
                    List<MaterialItem> temp = categoryIdMap.get(categoryId);
                    for (MaterialItem materialItem : temp) {
                        if (bigCategoryCodeList.contains(categoryCode)) {
                            /* 物料大类编码为10、30、40、60的物料，仅能选择IN开头的税率，如IN 13,IN 11 ,不包括IN VAT 和IN-ASSET；*/
                            if (Objects.isNull(queryTaxBy1)) {
                                queryTaxBy1 = purchaseTaxMapper.queryTaxBy1();
                            }
                            Set<String> taxKey = queryTaxBy1.stream().map(PurchaseTax::getTaxKey).collect(Collectors.toSet());
                            result.putIfAbsent(materialItem.getMaterialId(), taxKey);
                        } else if ("20".equals(categoryCode) &&
                                (StringUtils.startsWith(materialItem.getMaterialCode(), "61") ||
                                        StringUtils.startsWith(materialItem.getMaterialCode(), "78"))
                        ) {
                            /* 物料大类编码为20且物料编码开头是（61或78）的行，仅能选择IN-ASSET开头的税率；*/
                            if (Objects.isNull(queryTaxBy2)) {
                                queryTaxBy2 = purchaseTaxMapper.queryTaxBy2();
                            }
                            Set<String> taxKey = queryTaxBy2.stream().map(PurchaseTax::getTaxKey).collect(Collectors.toSet());
                            result.putIfAbsent(materialItem.getMaterialId(), taxKey);
                        } else if ("70".equals(categoryCode)) {
                            /**
                             * 物料大类编码是70且物料编码开头是（61或78）的行，仅能选择IN-ASSET开头的税率；
                             * 其他的仅能选择IN开头；
                             */
                            if (StringUtils.startsWith(materialItem.getMaterialCode(), "61") ||
                                    StringUtils.startsWith(materialItem.getMaterialCode(), "78")) {
                                if (Objects.isNull(queryTaxBy2)) {
                                    queryTaxBy2 = purchaseTaxMapper.queryTaxBy2();
                                }
                                Set<String> taxKey = queryTaxBy2.stream().map(PurchaseTax::getTaxKey).collect(Collectors.toSet());
                                result.putIfAbsent(materialItem.getMaterialId(), taxKey);
                            } else {
                                if (Objects.isNull(queryTaxBy3)) {
                                    queryTaxBy3 = purchaseTaxMapper.queryTaxBy3();
                                }
                                Set<String> taxKey = queryTaxBy3.stream().map(PurchaseTax::getTaxKey).collect(Collectors.toSet());
                                result.putIfAbsent(materialItem.getMaterialId(), taxKey);
                            }
                        } else {
                            if (Objects.isNull(all)) {
                                all = purchaseTaxMapper.selectList(null);
                            }
                            Set<String> taxKey = all.stream().map(PurchaseTax::getTaxKey).collect(Collectors.toSet());
                            result.putIfAbsent(materialItem.getMaterialId(), taxKey);
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<MaterialItem> listAllForTranferOrder(MaterialQueryDTO materialQueryDTO) {
        return materialItemMapper.listAllForTranferOrder(materialQueryDTO);
    }

    @Override
    public List<MaterialItem> listAllForImportPriceLibrary() {
        return materialItemMapper.listAllForImportPriceLibrary();
    }

    /**
     * 根据物料编码查询物料列表
     *
     * @param materialCodes
     * @return
     */
    @Override
    public Map<String,MaterialItem> listMaterialItemsByCodes(List<String> materialCodes) {
        Map<String, MaterialItem> itemHashMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(materialCodes)) {
            materialCodes = materialCodes.stream().distinct().collect(Collectors.toList());
            List<MaterialItem> materialItems = this.list(Wrappers.lambdaQuery(MaterialItem.class)
                    .in(MaterialItem::getMaterialCode, materialCodes));
            if (CollectionUtils.isNotEmpty(materialItems)) {
                itemHashMap = materialItems.stream().collect(Collectors.toMap(MaterialItem::getMaterialCode, v->v, (k1, k2) -> k1));
            }
        }
        return itemHashMap;
    }
}
