package com.midea.cloud.srm.sup.info.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.enums.review.CategoryStatus;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationRelation;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.change.entity.OrgCategoryChange;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCategoryImportDro;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCategoryQueryDTO;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCategorySaveDTO;
import com.midea.cloud.srm.model.supplier.info.dto.VendorDto;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.model.supplier.info.entity.OrgCategory;
import com.midea.cloud.srm.model.supplierauth.review.entity.ReviewForm;
import com.midea.cloud.srm.sup.change.service.IOrgCategoryChangeService;
import com.midea.cloud.srm.sup.dim.service.IDimFieldContextService;
import com.midea.cloud.srm.sup.info.mapper.OrgCategoryMapper;
import com.midea.cloud.srm.sup.info.service.ICompanyInfoService;
import com.midea.cloud.srm.sup.info.service.IOrgCategoryService;
import com.midea.cloud.srm.sup.vendororgcategory.mapper.VendorOrgCateRelMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * <pre>
 *  组织与品类(供应商档案)   服务实现类
 * </pre>
 *
 * @author zhuwl7@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 16:21:46
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class OrgCategoryServiceImpl extends ServiceImpl<OrgCategoryMapper, OrgCategory> implements IOrgCategoryService {
    @Autowired
    private IDimFieldContextService dimFieldContextService;

    @Resource
    private OrgCategoryMapper orgCategoryMapper;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private IOrgCategoryChangeService iOrgCategoryChangeService;

    @Resource
    private SupplierClient supplierClient;

    @Resource
    private FileCenterClient fileCenterClient;

    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    @Resource
    private VendorOrgCateRelMapper vendorOrgCateRelDao;

    @Override
    public List<VendorDto> queryCompanyByBusinessModeCode(List<Long> categoryIds) {
//        // 匹配采购分类中类编码
//        String categoryCode = "";
//        String companyType = "";
        List<VendorDto> validVendorOrgCate = null;
//        if(BusinessMode.INSIDE.getValue().equals(businessModeCode)){
//            categoryCode = "6001";
//            companyType = CompanyType.INSIDE.getValue();
//        }else if(BusinessMode.OUTSIDE.getValue().equals(businessModeCode)){
//            categoryCode = "6002";
//            companyType = CompanyType.OUTSIDE.getValue();
//        }else {
//            companyType = CompanyType.OVERSEA.getValue();
//            categoryCode = "6003";
//        }

//        List<Long> categoryIds = new ArrayList<>();
        // 根据采购分类中类
//        List<PurchaseCategory> purchaseCategories = baseClient.queryPurchaseCategoryByMiddleCode(categoryCode);
//        if(CollectionUtils.isNotEmpty(purchaseCategories)){
//            purchaseCategories.forEach(purchaseCategory -> {
//                categoryIds.add(purchaseCategory.getCategoryId());
//            });
//            // 根据小类查找供应商
//            if (CollectionUtils.isNotEmpty(categoryIds)) {
//                // 查询 供应商组织品类关系集
//                validVendorOrgCate = this.baseMapper.findValidVendorOrgCate(categoryIds);
//                if(CollectionUtils.isNotEmpty(validVendorOrgCate)){
//                    String finalCompanyType = companyType;
//                    validVendorOrgCate.forEach(vendorDto -> {
//                        vendorDto.setCompanyType(finalCompanyType);
//                    });
//                }
//            }
//
//        }
        // 根据小类查找供应商
        if (CollectionUtils.isNotEmpty(categoryIds)) {
            // 查询 供应商组织品类关系集
            validVendorOrgCate = this.baseMapper.findValidVendorOrgCate(categoryIds);
            // 检查供应商是否有所有品类的报价权限

        }
        return validVendorOrgCate;
    }

    @Override
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 检查上传文件
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<OrgCategoryImportDro> orgCategoryImportDros = readData(file);
        // 导入文件是否有错误信息标志
        AtomicBoolean errorFlag = new AtomicBoolean(false);
        // 导入的数据
        List<OrgCategory> orgCategoriesUpdate = new ArrayList<>();
        List<OrgCategory> orgCategoriesAdd = new ArrayList<>();
        // 每行报错信息
        List<String> errorList = new ArrayList<>();
        getImportData(orgCategoriesUpdate,orgCategoriesAdd ,orgCategoryImportDros, errorList, errorFlag);
        if (errorFlag.get()) {
            // 有报错
            fileupload.setFileSourceName("供应商品类组织关系导入报错");
            Fileupload fileupload1 = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                    orgCategoryImportDros, OrgCategoryImportDro.class, file.getName(), file.getOriginalFilename(), file.getContentType());
            return ImportStatus.importError(fileupload1.getFileuploadId(),fileupload1.getFileSourceName());
        }else {
            if(CollectionUtils.isNotEmpty(orgCategoriesAdd)){
                log.info("----------------------------供应商组织品类关系导入新增开始----------------------------------");
                this.saveBatch(orgCategoriesAdd);
                log.info("----------------------------供应商组织品类关系导入新增结束----------------------------------");
            }
            if(CollectionUtils.isNotEmpty(orgCategoriesUpdate)){
                log.info("----------------------------供应商组织品类关系导入更新开始----------------------------------");
                this.updateBatchById(orgCategoriesUpdate);
                log.info("----------------------------供应商组织品类关系导入更新结束----------------------------------");
            }
        }
        return ImportStatus.importSuccess();
    }

    public List<OrgCategoryImportDro> readData(MultipartFile file) {
        List<OrgCategoryImportDro> orgCategoryImportDros = new ArrayList<>();
        try {
            // 获取输入流
            InputStream inputStream = file.getInputStream();
            // 数据收集器
            AnalysisEventListenerImpl<OrgCategoryImportDro> listener = new AnalysisEventListenerImpl<>();
            ExcelReader excelReader = EasyExcel.read(inputStream,listener).build();
            // 第一个sheet读取类型
            ReadSheet readSheet = EasyExcel.readSheet(0).head(OrgCategoryImportDro.class).build();
            // 开始读取第一个sheet
            excelReader.read(readSheet);
            orgCategoryImportDros = listener.getDatas();
        } catch (IOException e) {
            throw new BaseException("excel解析出错");
        }
        return orgCategoryImportDros;
    }

    @Override
    public List<OrgCategory> listForCheck(OrgCategoryQueryDTO orgCategoryQueryDTO) {
        return orgCategoryMapper.listForCheck(orgCategoryQueryDTO);
    }

    /**
     * 获取所有的
     * @param orgCategory
     * @return
     */
    @Override
    public List<OrgCategory> listOrgCategoryByParam(OrgCategory orgCategory) {
        return orgCategoryMapper.selectList(new QueryWrapper<>(orgCategory));
    }

    @Override
    public List<Organization> supplierTree(Organization organization) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (loginAppUser == null) {
            return Collections.emptyList();
        }
        Long companyId = loginAppUser.getCompanyId();
        if (companyId == null) {
            return Collections.emptyList();
        }

        // 集团、事业部查询需要使用另外的接口，目前暂时无集团、事业部查询需求
        if (!"OU".equals(organization.getOrganizationTypeCode()) && !"INV".equals(organization.getOrganizationTypeCode())) {
            return Collections.emptyList();
        }

        // 获取品类对应的业务实体
        OrgCategory orgCategory = new OrgCategory();
        orgCategory.setCompanyId(companyId);
        List<OrgCategory> orgCategoryList = this.listOrgCategoryByParam(orgCategory);
        List<Long> organizationIdList = orgCategoryList.stream().map(OrgCategory::getOrgId).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(organizationIdList)) {
            return Collections.emptyList();
        }

        // 判断查询的是否是业务实体，如果是业务实体，则查询业务实体。如果是库存组织，则根据parent查询库存组织
        if ("OU".equals(organization.getOrganizationTypeCode())) { // 业务实体
            organization.setOrganizationIdList(organizationIdList);

            organization.setParentOrganizationIds(null);
            return baseClient.listOrganization(organization);
        } else if ("INV".equals(organization.getOrganizationTypeCode())) { // 库存组织
            String parentOrganizationIds = organization.getParentOrganizationIds();

            if (StringUtils.isNotBlank(parentOrganizationIds)) {
                Long parentOrganizationId = Long.parseLong(parentOrganizationIds);
                if (!organizationIdList.contains(parentOrganizationId)) { // 组织品类中必须包含上级的业务实体，否则没有权限
                    return Collections.emptyList();
                } else {
                    List<OrganizationRelation> relationList = baseClient.listChildrenOrganization(parentOrganizationId);
                    List<Long> invOrganizationIdList = relationList.stream().map(OrganizationRelation::getOrganizationId).collect(Collectors.toList());
                    organization.setOrganizationIdList(invOrganizationIdList);

                    organization.setParentOrganizationIds(null);
                    return baseClient.listOrganization(organization);
                }
            } else { // 如果没有选择业务实体，则不查询库存组织
                return Collections.emptyList();
            }
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 获取导入的数据, 并对数据进行校验
     *
     * @param orgCategoriesUpdate
     * @param orgCategoriesAdd
     * @param errorList
     * @throws IOException
     * @throws ParseException
     */
    private void getImportData(List<OrgCategory> orgCategoriesUpdate,List<OrgCategory> orgCategoriesAdd, List<OrgCategoryImportDro> orgCategoryImportDros, List<String> errorList, AtomicBoolean errorFlag) throws IOException, ParseException {
        if(CollectionUtils.isNotEmpty(orgCategoryImportDros)){
            // 获取所有组织
            List<String> orgName = new ArrayList<>();
            // 新SRM供应商ID
            List<Long> vendorIdList = new ArrayList<>();
            // 品类全路径
            List<String> categoryCodeList = new ArrayList<>();
            for(OrgCategoryImportDro orgCategoryImportDro : orgCategoryImportDros) {
                String orgName1 = orgCategoryImportDro.getOrgName();
                if(StringUtil.notEmpty(orgName1)){
                    orgName1 = orgName1.trim();
                    orgName.add(orgName1);
                }
                String categoryCode3 = orgCategoryImportDro.getCategoryCode3();
                if(StringUtil.notEmpty(categoryCode3)){
                    categoryCode3 = categoryCode3.trim();
                    categoryCodeList.add(categoryCode3);
                }
                String vendorId = orgCategoryImportDro.getVendorId();
                if(StringUtil.notEmpty(vendorId)){
                    vendorId = vendorId.trim();
                    Long id = Long.parseLong(vendorId);
                    vendorIdList.add(id);
                }
            }

            // 获取所有的组织
            Map<String, Organization> orgMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(orgName)) {
                orgName = orgName.stream().distinct().collect(Collectors.toList());
                List<Organization> orgList = baseClient.getOrganizationByNameList(orgName);
                if (CollectionUtils.isNotEmpty(orgList)) {
                    orgMap = orgList.stream().filter(organization -> StringUtil.notEmpty(organization.getOrganizationName())).
                            collect(Collectors.toMap(Organization::getOrganizationName,v->v,(k1,k2)->k1));
                }
            }

            // 获取所有的供应商id
            Map<Long, List<CompanyInfo>> companyMap = new HashMap<>();
            List<CompanyInfo> companyInfos = null;
            if (CollectionUtils.isNotEmpty(vendorIdList)) {
                vendorIdList = vendorIdList.stream().distinct().collect(Collectors.toList());
                companyInfos = iCompanyInfoService.list(new QueryWrapper<CompanyInfo>().in("COMPANY_ID",vendorIdList));
            }
            if (CollectionUtils.isNotEmpty(companyInfos)) {
                companyMap = companyInfos.stream().collect(Collectors.groupingBy(CompanyInfo::getCompanyId));
            }

            // 获取品类小类信息
            Map<String, PurchaseCategory> categoriesMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(categoryCodeList)) {
                categoryCodeList = categoryCodeList.stream().distinct().collect(Collectors.toList());
                categoriesMap = baseClient.queryPurchaseCategoryByLevelCodes(categoryCodeList);
            }

            // 获取所有的供应商组织品类信息系
            Map<String, OrgCategory> orgCategoryMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(vendorIdList) && CollectionUtils.isNotEmpty(orgName) && CollectionUtils.isNotEmpty(categoryCodeList)) {
                QueryWrapper<OrgCategory> queryWrapper = new QueryWrapper<>();
                queryWrapper.in("COMPANY_ID",vendorIdList);
                queryWrapper.in("ORG_NAME",orgName);
                queryWrapper.in("CATEGORY_CODE",categoryCodeList);
                List<OrgCategory> orgCategories = this.list(queryWrapper);
                if(CollectionUtils.isNotEmpty(orgCategories)){
                    orgCategoryMap = orgCategories.stream().filter(orgCategory -> StringUtil.notEmpty(orgCategory.getCompanyId())
                            && StringUtil.notEmpty(orgCategory.getOrgName()) && StringUtil.notEmpty(orgCategory.getCategoryCode())).
                            collect(Collectors.toMap(k -> k.getCompanyId() + k.getCategoryCode() + k.getOrgName(), v -> v, (k1, k2) -> k1));
                }
            }

            // 字典值
            Map<String, String> dicValueKey = getDicValueKey();
            HashSet<String> hashSet = new HashSet<>();
            int i = 1;
            // 遍历内容行获取数据,从2行开始,也就是行的下标为1
            for (OrgCategoryImportDro orgCategoryImportDro : orgCategoryImportDros) {
                // <------------------------------------一下正式开始获取表格内容-------------------------------------------->
                log.info("第"+i+"次循环");
                i++;
                // 错误信息
                StringBuffer errorMsg = new StringBuffer();
                OrgCategory orgCategory = new OrgCategory();
                StringBuffer onlyKey = new StringBuffer();
                boolean lineErrorFlag = true;

                // 新SRM供应商ID
                String vendorId = orgCategoryImportDro.getVendorId();
                if (StringUtil.notEmpty(vendorId)) {
                    vendorId = vendorId.trim();
                    onlyKey.append(vendorId);
                    Long id = 0L;
                    try {
                        id = Long.parseLong(vendorId);
                        if (null != companyMap.get(id)) {
                            CompanyInfo companyInfo = companyMap.get(id).get(0);
                            orgCategory.setCompanyId(companyInfo.getCompanyId());
                            orgCategory.setCompanyCode(companyInfo.getCompanyCode());
                            orgCategory.setCompanyName(companyInfo.getCompanyName());
                        } else {
                            errorFlag.set(true);
                            lineErrorFlag = false;
                            errorMsg.append("新SRM供应商ID不存在; ");
                        }
                    } catch (Exception e) {
                        errorFlag.set(true);
                        lineErrorFlag = false;
                        errorMsg.append("新SRM供应商ID非数字; ");
                    }
                }else {
                    errorFlag.set(true);
                    lineErrorFlag = false;
                    errorMsg.append("新SRM供应商ID不能为空; ");
                }

                // 品类小类
                String categoryCode3 = orgCategoryImportDro.getCategoryCode3();
                if (StringUtil.notEmpty(categoryCode3)) {
                    categoryCode3 = categoryCode3.trim();
                    onlyKey.append(categoryCode3);
                    if(null != categoriesMap.get(categoryCode3)){
                        PurchaseCategory purchaseCategory = categoriesMap.get(categoryCode3);
                        orgCategory.setCategoryId(purchaseCategory.getCategoryId());
                        orgCategory.setCategoryName(purchaseCategory.getCategoryName());
                        orgCategory.setCategoryCode(purchaseCategory.getCategoryCode());
                        orgCategory.setCategoryFullId(purchaseCategory.getStruct());
                        orgCategory.setCategoryFullName(purchaseCategory.getCategoryFullName());
                    }else {
                        errorFlag.set(true);
                        lineErrorFlag = false;
                        errorMsg.append("品类小类数据库不存在; ");
                    }
                } else {
                    errorFlag.set(true);
                    lineErrorFlag = false;
                    errorMsg.append("品类小类不能为空; ");
                }

                // 未注册供应商建议的生命周期
                String theLifeCycle = orgCategoryImportDro.getTheLifeCycle();
                if(StringUtil.notEmpty(theLifeCycle)){
                    theLifeCycle = theLifeCycle.trim();
                    if(StringUtil.isEmpty(dicValueKey.get(theLifeCycle))){
                        errorFlag.set(true);
                        lineErrorFlag = false;
                        errorMsg.append("未注册供应商建议的生命周期字典值不存在; ");
                    }else {
                        theLifeCycle = dicValueKey.get(theLifeCycle);
                    }
                }

                // 业务实体
                String orgName1 = orgCategoryImportDro.getOrgName();
                if (StringUtil.notEmpty(orgName1)){
                    orgName1 = orgName1.trim();
                    onlyKey.append(orgName1);
                    Organization organization = orgMap.get(orgName1);
                    if (null != organization) {
                        orgCategory.setOrgId(organization.getOrganizationId());
                        orgCategory.setOrgCode(organization.getOrganizationCode());
                        orgCategory.setOrgName(organization.getOrganizationName());
                    }else {
                        errorFlag.set(true);
                        lineErrorFlag = false;
                        errorMsg.append("业务实体数据库不存在; ");
                    }
                }else {
                    errorFlag.set(true);
                    lineErrorFlag = false;
                    errorMsg.append("业务实体不能为空; ");
                }

                if(!hashSet.add(onlyKey.toString())){
                    errorFlag.set(true);
                    lineErrorFlag = false;
                    errorMsg.append("供应商+品类+组织存在重复行; ");
                }

                // 状态
                String status = orgCategoryImportDro.getServiceStatus();
                if (StringUtil.notEmpty(status)) {
                    status = status.trim();
                    if("SRM尚未注册".equals(status)){
                        if(StringUtil.notEmpty(theLifeCycle)){
                            orgCategory.setServiceStatus(theLifeCycle);
                        }else {
                            lineErrorFlag = false;
                        }
                    }else {
                        if(StringUtil.notEmpty(dicValueKey.get(status))){
                            orgCategory.setServiceStatus(dicValueKey.get(status));
                        }else {
                            errorFlag.set(true);
                            lineErrorFlag = false;
                            errorMsg.append("状态字典值不存在; ");
                        }
                    }
                }else {
                    lineErrorFlag = false;
                }

                // 检查是否有重复数据
                if(lineErrorFlag){
                    StringBuffer key = new StringBuffer();
                    key.append(orgCategory.getCompanyId()).append(orgCategory.getCategoryCode()).append(orgCategory.getOrgName());
                    OrgCategory category = orgCategoryMap.get(key.toString());
                    if(null != category){
                        category.setServiceStatus(orgCategory.getServiceStatus());
                        orgCategoriesUpdate.add(category);
                    }else {
                        orgCategory.setOrgCategoryId(IdGenrator.generate());
                        orgCategoriesAdd.add(orgCategory);
                    }
                }

                if(errorMsg.length() > 0){
                    orgCategoryImportDro.setErrorMsg(errorMsg.toString());
                }else {
                    orgCategoryImportDro.setErrorMsg(null);
                }
            }
        }

    }

    public static final List<String> statusList;
    static {
        statusList = new ArrayList<>();
        statusList.addAll(Arrays.asList("注册","潜在","临时","合格","SRM尚未注册"));
    }

    public Map<String,String> getDicValueKey(){
        HashMap<String, String> map = new HashMap<>();
        map.put("注册","REGISTERED");
        map.put("潜在","VERIFY");
        map.put("临时","ONE_TIME");
        map.put("合格","GREEN");
        return map;
    }

    @Override
    @Transactional
    public void saveOrUpdateOrgCategory(OrgCategory orgCategory, Long companyId) {
        orgCategory.setCompanyId(companyId);
        if (orgCategory.getOrgCategoryId() != null) {
            orgCategory.setLastUpdateDate(new Date());
        } else {
            orgCategory.setCreationDate(new Date());
            Long id = IdGenrator.generate();
            orgCategory.setOrgCategoryId(id);
        }
        this.saveOrUpdate(orgCategory);
//        if(null != orgCategory.getDimFieldContexts() && !CollectionUtils.isEmpty(orgCategory.getDimFieldContexts())){
//            dimFieldContextService.saveOrUpdateList(orgCategory.getDimFieldContexts(),orgCategory.getOrgCategoryId(),companyId);
//        }
    }

    @Override
    public List<OrgCategory> getByCompanyId(Long companyId) {
        QueryWrapper<OrgCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("COMPANY_ID", companyId);
        List<OrgCategory> orgCategorys = companyId != null ? this.list(queryWrapper) : null;
//        ceea,隆基不需要
//        if(!CollectionUtils.isEmpty(orgCategorys)) {
//            for(OrgCategory orgCategory:orgCategorys){
//                Map<String,Object> dimFieldContexts = dimFieldContextService.findByOrderId(orgCategory.getOrgCategoryId());
//                orgCategory.setDimFieldContexts(dimFieldContexts);
//            }
//
//        }
        return orgCategorys;
    }

    @Override
    public void removeByCompanyId(Long companyId) {
        QueryWrapper<OrgCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("COMPANY_ID", companyId);
        this.remove(wrapper);
    }

    @Override
    public List<OrgCategory> listOrgCategoryByCompanyId(Long companyId) {
        Assert.notNull(companyId, "companyId不能为空");
        QueryWrapper<OrgCategory> queryWrapper = new QueryWrapper<>(new OrgCategory().setCompanyId(companyId));
        return orgCategoryMapper.selectList(queryWrapper);
    }

    @Override
    public List<OrgCategory> listOrgCategoryByServiceStatusAndCompanyId(Long companyId, String... serviceStatus) {
        Assert.notEmpty(serviceStatus, "serviceStatus不能为空");
        Assert.notNull(companyId, "companyId不能为空");
        OrgCategory orgCategory = new OrgCategory().setCompanyId(companyId);
        //会有两个筛选服务状态条件
//        if (serviceStatus.length == 1) {
//            orgCategory.setServiceStatus(serviceStatus[0]);
//        }
        

        QueryWrapper<OrgCategory> queryWrapper = new QueryWrapper<>(orgCategory);
        queryWrapper.in("SERVICE_STATUS", serviceStatus);
//        if (serviceStatus.length == 2) {
//            queryWrapper.eq("SERVICE_STATUS", serviceStatus[0])
//                    .or().eq("SERVICE_STATUS", serviceStatus[1]);
//        }
        return orgCategoryMapper.selectList(queryWrapper);
    }

    @Override
    public OrgCategory getByCategoryIdAndOrgIdAndCompanyId(Long categoryId, Long orgId, Long companyId) {
        QueryWrapper<OrgCategory> queryWrapper = new QueryWrapper<>(new OrgCategory().setCategoryId(categoryId)
                .setOrgId(orgId).setCompanyId(companyId));
        List<OrgCategory> orgCategories = this.list(queryWrapper);
        OrgCategory orgCategory = null;
        if (!CollectionUtils.isEmpty(orgCategories)) {
            orgCategory = orgCategories.get(0);
        }
        return orgCategory;
    }

    @Override
    public List<OrgCategory> getByCategoryIdAndCompanyId(Long categoryId, Long companyId) {
        QueryWrapper<OrgCategory> wrapper = new QueryWrapper<>(new OrgCategory().setCategoryId(categoryId).setCompanyId(companyId));
        wrapper.eq("COMPANY_ID", companyId);
        wrapper.eq("CATEGORY_ID", categoryId);
        wrapper.groupBy("SERVICE_STATUS");
        return this.list(wrapper);
    }

    /**
     * @return
     */
    @Override
    public List<OrgCategory> getByCategoryAll() {
        QueryWrapper<OrgCategory> wrapper = new QueryWrapper<>();
        wrapper.select("ORG_CATEGORY_ID,COMPANY_ID,CATEGORY_ID,SERVICE_STATUS");
        wrapper.ne("SERVICE_STATUS", "");
        wrapper.isNotNull("SERVICE_STATUS");
        wrapper.groupBy("SERVICE_STATUS");
        return this.list(wrapper);
    }

    @Override
    public void updateOrgCategoryServiceStatus(OrgCategory orgCategory) {
        UpdateWrapper<OrgCategory> updateWrapper = new UpdateWrapper<>(new OrgCategory().setCategoryId(orgCategory.getCategoryId())
                .setOrgId(orgCategory.getOrgId()).setCompanyId(orgCategory.getCompanyId()));
        updateWrapper.set("END_DATE", orgCategory.getEndDate()).set("START_DATE", orgCategory.getStartDate());
        this.update(orgCategory, updateWrapper);
    }

    @Override
    public boolean haveSupplier(OrgCategory orgCategory) {
        QueryWrapper<OrgCategory> queryWrapper = new QueryWrapper<>(new OrgCategory()
                .setOrgId(orgCategory.getOrgId())
                .setCategoryId(orgCategory.getCategoryId()));
        queryWrapper.ne("SERVICE_STATUS", CategoryStatus.RED.name());
        int count = this.count(queryWrapper);
        if (count > 0) {
            //有货源供应商
            return true;
        } else {
            //无货源供应商
            return false;
        }
    }

    @Override
    public PageInfo<OrgCategory> listPageOrgCategoryByParam(OrgCategoryQueryDTO orgCategoryQueryDTO) {
        PageUtil.startPage(orgCategoryQueryDTO.getPageNum(), orgCategoryQueryDTO.getPageSize());
//        List<PurchaseCategory> purchaseCategories = orgCategoryQueryDTO.getPurchaseCategories();
//        // 查找最小级采购分类
//        List<PurchaseCategory> categories = baseClient.queryMinLevelCategory(purchaseCategories);
//        // 查找品类关系
//        List<OrgCategory> orgCategories = new ArrayList<>();
//        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(categories)){
//            PageUtil.startPage(orgCategoryQueryDTO.getPageNum(), orgCategoryQueryDTO.getPageSize());
//            List<Long> longs = new ArrayList<>();
//            categories.forEach(temp->longs.add(temp.getCategoryId()));
//        }
        List<OrgCategory> orgCategories = this.baseMapper.listPageOrgCategoryByParam(orgCategoryQueryDTO);
        // 查找品类全路径
        if(CollectionUtils.isNotEmpty(orgCategories)){
            List<Long> ids = new ArrayList<>();
            orgCategories.forEach(orgCategory -> {
                Long categoryId = orgCategory.getCategoryId();
                if (StringUtil.notEmpty(categoryId)) {
                    ids.add(categoryId);
                }
            });
            if (CollectionUtils.isNotEmpty(ids)) {
                Map<String, String> idMap = baseClient.queryCategoryFullNameByLevelIds(ids);
                orgCategories.forEach(orgCategory -> {
                    Long categoryId = orgCategory.getCategoryId();
                    if (StringUtil.notEmpty(categoryId)) {
                        String s = idMap.get(String.valueOf(categoryId));
                        orgCategory.setCategoryFullName(s);
                    }
                });
            }

        }
        return new PageInfo<OrgCategory>(orgCategories);
    }

    @Override
    @Transactional
    public void collectOrgCategory(OrgCategorySaveDTO orgCategorySaveDTO) {
        OrgCategory orgCategory = orgCategorySaveDTO.getOrgCategory();
        ReviewForm reviewForm = orgCategorySaveDTO.getReviewForm();
        List<OrgCategoryChange> orgCategoryChanges = orgCategorySaveDTO.getOrgCategoryChanges();
        Assert.notNull(orgCategory, LocaleHandler.getLocaleMsg("合作品类关系不能为空"));
        Assert.notEmpty(orgCategoryChanges, LocaleHandler.getLocaleMsg("合作品类关系变更状态不能为空"));
        //获取唯一的合作品类关系
        Long orgId = orgCategory.getOrgId();
        Long categoryId = orgCategory.getCategoryId();
        Long companyId = orgCategory.getCompanyId();
        String afterServiceStatus = orgCategory.getServiceStatus();
        OrgCategory onlyOrgCategory = this.getByCategoryIdAndOrgIdAndCompanyId(categoryId, orgId, companyId);
        String beforeServiceStatus = "";
        log.info("------------------在供应商模块生成组织品类关系,组织品类关系入参:" + JsonUtil.entityToJsonStr(orgCategorySaveDTO) + "-------------------------------");
        //更新或新增合作品类关系
        if (onlyOrgCategory == null) {
            orgCategory.setOrgCategoryId(IdGenrator.generate());
            //工作流需要手动设置创建人及最后更新人
            if (reviewForm != null) {
                orgCategory.setCreatedBy(reviewForm.getCreatedBy());
                orgCategory.setCreatedId(reviewForm.getCreatedId());
                orgCategory.setCreatedByIp(reviewForm.getCreatedByIp());
                orgCategory.setLastUpdateDate(reviewForm.getLastUpdateDate());
                orgCategory.setLastUpdatedBy(reviewForm.getLastUpdatedBy());
                orgCategory.setLastUpdatedByIp(reviewForm.getLastUpdatedByIp());
                orgCategory.setLastUpdatedId(reviewForm.getLastUpdatedId());
            }
            this.save(orgCategory);
            //插入一条变更记录 ToDo 正常情况只会出现一条变更记录
//            for (OrgCategoryChange orgCategoryChange : orgCategoryChanges) {
//                saveOrgCategoryChange(orgId, categoryId, companyId, beforeServiceStatus, afterServiceStatus, orgCategoryId, orgCategoryChange);
//            }
        } else {
            beforeServiceStatus = onlyOrgCategory.getServiceStatus();
            //工作流需要手动设置创建人及最后更新人
            if (reviewForm != null) {
                onlyOrgCategory.setCreatedByIp(reviewForm.getCreatedByIp());
                onlyOrgCategory.setCreatedBy(reviewForm.getCreatedBy());
                onlyOrgCategory.setCreatedId(reviewForm.getCreatedId());
                onlyOrgCategory.setLastUpdatedId(reviewForm.getLastUpdatedId());
                onlyOrgCategory.setLastUpdatedByIp(reviewForm.getLastUpdatedByIp());
                onlyOrgCategory.setLastUpdateDate(reviewForm.getLastUpdateDate());
                onlyOrgCategory.setLastUpdatedBy(reviewForm.getLastUpdatedBy());
            }
            onlyOrgCategory.setServiceStatus(afterServiceStatus);
            this.updateById(onlyOrgCategory);
            //更新一条变更记录 ToDo 正常情况只会更新一条 有问题:同一单据会出现变更吗?
//            for (OrgCategoryChange orgCategoryChange : orgCategoryChanges) {
//                OrgCategoryChange onlyOrgCategoryChange = iOrgCategoryChangeService.getOne(new QueryWrapper<>(new OrgCategoryChange()
//                        .setOrgCategoryId(onlyOrgCategory.getOrgCategoryId())
//                        .setOrgId(onlyOrgCategory.getOrgId())
//                        .setCategoryId(onlyOrgCategory.getCategoryId())
//                        .setCompanyId(onlyOrgCategory.getCompanyId())
//                        .setFormType(orgCategoryChange.getFormType())
//                        .setFormNum(orgCategoryChange.getFormNum())));
//                QueryWrapper<OrgCategoryChange> queryWrapper = new QueryWrapper<>(new OrgCategoryChange()
//                        .setOrgCategoryId(onlyOrgCategory.getOrgCategoryId())
//                        .setCompanyId(onlyOrgCategory.getCompanyId())
//                        .setOrgId(onlyOrgCategory.getOrgId())
//                        .setCategoryId(onlyOrgCategory.getCategoryId()));
//                queryWrapper.orderByDesc("LAST_UPDATE_DATE");
//                List<OrgCategoryChange> list = iOrgCategoryChangeService.list(queryWrapper);
//                OrgCategoryChange onlyOrgCategoryChange = null;
//                if (!CollectionUtils.isEmpty(list)) {
//                    onlyOrgCategoryChange = list.get(0);
//                }
//                if (onlyOrgCategoryChange != null) {
//                    String beforeServiceStatus = onlyOrgCategoryChange.getAfterServiceStatus();
//                    iOrgCategoryChangeService.updateById(onlyOrgCategoryChange
//                            .setBeforeServiceStatus(beforeServiceStatus)
//                            .setAfterServiceStatus(afterServiceStatus));
//                } else {
//                    saveOrgCategoryChange(orgId, categoryId, companyId, beforeServiceStatus, afterServiceStatus, orgCategoryId, orgCategoryChange);
//                }
//            }
        }
    }

    private void saveOrgCategoryChange(Long orgId, Long categoryId, Long companyId, String beforeServiceStatus, String afterServiceStatus, long orgCategoryId, OrgCategoryChange orgCategoryChange) {
        iOrgCategoryChangeService.save(orgCategoryChange.setOrgCategoryChangeId(IdGenrator.generate())
                .setOrgCategoryId(orgCategoryId)
                .setOrgId(orgId)
                .setCategoryId(categoryId)
                .setCompanyId(companyId)
                .setAfterServiceStatus(afterServiceStatus)
                .setFormType(orgCategoryChange.getFormType())
                .setFormNum(orgCategoryChange.getFormNum()));
    }

    @Override
    public List<OrgCategory> querySingleSourceList(Long vendorId) {
        return this.baseMapper.querySingleSourceList(vendorId);
    }

    @Override
    @Transactional
    public void delete(Long orgCategoryId) {
        this.delete(orgCategoryId);
    }

    /**
     * 根据品类id智能查询供应商（绩效项目智能添加供应商时调用）
     *
     * @param categoryIds
     * @return
     */
    @Override
    public List<CompanyInfo> listCompanyInfosByCategoryIds(List<Long> categoryIds) {
        // 先根据categoryIds去重
        List<Long> duplicateRemoveCategoryIds = categoryIds.stream().distinct().collect(Collectors.toList());

        List<Long> resCompanyIds = new ArrayList<>();
        // 根据categoryIds先过滤一部分数据出来 Controller层已做判空处理
        List<OrgCategory> filterOrgCategories = this.list(Wrappers.lambdaQuery(OrgCategory.class)
                .eq(OrgCategory::getServiceStatus, CategoryStatus.GREEN.name())
                .in(OrgCategory::getCategoryId, duplicateRemoveCategoryIds)
        );
        if (CollectionUtils.isNotEmpty(filterOrgCategories)) {
            Map<Long, List<OrgCategory>> map = filterOrgCategories.stream()
                    .collect(Collectors.groupingBy(OrgCategory::getCompanyId));
            map.forEach((companyId, orgs) -> {
                Map<Long, List<OrgCategory>> temp = orgs.stream().collect(Collectors.groupingBy(OrgCategory::getCategoryId));
                boolean allGreen = true;
                for (Map.Entry<Long, List<OrgCategory>> entry : temp.entrySet()) {
                    allGreen = allGreen && entry.getValue().stream().anyMatch(e -> Objects.equals(CategoryStatus.GREEN.name(), e.getServiceStatus()));
                }
                if (allGreen) {
                    resCompanyIds.add(companyId);
                }
            });

        }
        // 获取这部分数据的companyIds
        return CollectionUtils.isNotEmpty(resCompanyIds) ? iCompanyInfoService.listByIds(resCompanyIds) : new ArrayList<>();
    }
}
