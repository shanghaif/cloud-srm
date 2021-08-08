package com.midea.cloud.srm.base.categorydv.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.listener.AnalysisEventListenerImpl;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.base.categorydv.mapper.CategoryDvMapper;
import com.midea.cloud.srm.base.categorydv.service.ICategoryDvService;
import com.midea.cloud.srm.base.categorydv.utils.ExportUtils;
import com.midea.cloud.srm.base.organization.service.IOrganizationService;
import com.midea.cloud.srm.base.organization.service.IOrganizationUserService;
import com.midea.cloud.srm.base.purchase.service.IPurchaseCategoryService;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.base.categorydv.dto.CategoryDvDTO;
import com.midea.cloud.srm.model.base.categorydv.dto.CategoryDvImport;
import com.midea.cloud.srm.model.base.categorydv.dto.DvRequestDTO;
import com.midea.cloud.srm.model.base.categorydv.entity.CategoryDv;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.organization.entity.OrganizationUser;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
*  <pre>
 *  品类分工 服务实现类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-06 10:04:24
 *  修改内容:
 * </pre>
*/
@Service
public class CategoryDvServiceImpl extends ServiceImpl<CategoryDvMapper, CategoryDv> implements ICategoryDvService {
    @Autowired
    private IPurchaseCategoryService iPurchaseCategoryService;
    @Resource
    private CategoryDvMapper categoryDvMapper;
    @Resource
    private IOrganizationService organizationService;
    @Resource
    private IOrganizationUserService organizationUserService;
    @Resource
    private RbacClient rbacClient;
    @Resource
    private FileCenterClient fileCenterClient;

    @Override
    @Transactional
    public void saveOrUpdateDv(CategoryDv categoryDv) {
        if(categoryDv.getCategoryId() == null
                || categoryDv.getUserId() == null
                || categoryDv.getStartDate() == null){
            throw  new BaseException(LocaleHandler.getLocaleMsg("员工姓名、品类、开始日期不能为空"));
        }
        if(categoryDv.getEndDate() != null){
            if(DateChangeUtil.asDate(categoryDv.getEndDate()).before(new Date())){
                throw  new BaseException(LocaleHandler.getLocaleMsg("结束日期必须大于当前日期"));
            }
        }
        List<CategoryDv> dvs = this.checkExist(categoryDv);
        if(!CollectionUtils.isEmpty(dvs)){
            throw new BaseException(LocaleHandler.getLocaleMsg("数据已存在,请检查!"));
        }
        PurchaseCategory category = iPurchaseCategoryService.getById(categoryDv.getCategoryId());
        if(category == null ||  YesOrNo.NO.getValue().equals(category.getEnabled())){
            throw  new BaseException(LocaleHandler.getLocaleMsg("品类未启用或不存在"));
        }
        if(categoryDv.getCategoryDvId() != null){
            categoryDv.setLastUpdateDate(new Date());
        }else{
            Long id = IdGenrator.generate();
            categoryDv.setCategoryDvId(id);
            categoryDv.setCreationDate(new Date());
        }
        this.saveOrUpdate(categoryDv);
    }

    private List<CategoryDv> checkExist(CategoryDv categoryDv) {
        CategoryDv query = new CategoryDv();
        query.setCategoryId(categoryDv.getCategoryId());
        query.setStartDate(categoryDv.getStartDate());
        query.setUserId(categoryDv.getUserId());
        QueryWrapper<CategoryDv> wrapper = new QueryWrapper<CategoryDv>(query);
        if(categoryDv.getCategoryDvId() != null){
            wrapper.ne("CATEGORY_DV_ID",categoryDv.getCategoryDvId());
        }
        return this.list(wrapper);
    }

    @Override
    @Transactional
    public void saveOrUpdateDvBatch(List<CategoryDv> categoryDvs) {
        if(!CollectionUtils.isEmpty(categoryDvs)){
            for(CategoryDv categoryDv:categoryDvs){
                 this.saveOrUpdateDv(categoryDv);
            }
        }
    }

    @Override
    public PageInfo<CategoryDv> listPageByParam(DvRequestDTO requestDTO) {
        PageUtil.startPage(requestDTO.getPageNum(), requestDTO.getPageSize());
        List<CategoryDv> list = getCategoryDvs(requestDTO);
        return new PageInfo<>(list);
    }

    public List<CategoryDv> getCategoryDvs(DvRequestDTO requestDTO) {
        CategoryDv query = new CategoryDv();
        if(requestDTO.getCategoryId()!= null){
            query.setCategoryId(requestDTO.getCategoryId());
        }
        if(requestDTO.getUserId() != null){
            query.setUserId(requestDTO.getUserId());
        }
        QueryWrapper<CategoryDv> wrapper = new QueryWrapper<CategoryDv>(query);
        wrapper.like(StringUtils.isNotBlank(requestDTO.getCategoryName()),
                "CATEGORY_NAME",requestDTO.getCategoryName());
        wrapper.like(StringUtils.isNotBlank(requestDTO.getFullName()),
                "FULL_NAME", requestDTO.getFullName());
        if(StringUtils.isNotBlank(requestDTO.getIsActive())){
            if(YesOrNo.YES.getValue().equals(requestDTO.getIsActive())){
                wrapper.le("START_DATE",new Date());
                wrapper.ge("END_DATE",new Date());
            }else if(YesOrNo.NO.getValue().equals(requestDTO.getIsActive())){
                wrapper.gt("START_DATE",new Date())
                        .or()
                        .lt("END_DATE",new Date());
            }
        }
        wrapper.orderByDesc("LAST_UPDATE_DATE");
        return this.list(wrapper);
    }

    @Override
    public List<CategoryDvDTO> listByParam(DvRequestDTO requestDTO) {
        return categoryDvMapper.listByParam(requestDTO);
    }

    /**
     * 导入模板下载
     * @param response
     * @throws IOException
     */
    @Override
    public void importModelDownload(HttpServletResponse response) throws IOException {
        String fileName = "品类分工导入模板";
        ArrayList<CategoryDvImport> categoryDvImports = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        EasyExcelUtil.writeExcelWithModel(outputStream,fileName,categoryDvImports,CategoryDvImport.class);
    }

    @Override
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 文件校验
        EasyExcelUtil.checkParam(file,fileupload);
        // 读取excel数据
        List<CategoryDvImport> categoryDvImports = this.readData(file);
        List<CategoryDv> categoryDvs = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();

        if(CollectionUtils.isNotEmpty(categoryDvImports)){
            boolean errorFlag = checkData(categoryDvImports,categoryDvs);
            if(errorFlag){
                // 有报错
                fileupload.setFileSourceName("品类分工导入报错");
                Fileupload fileupload1 = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                        categoryDvImports, CategoryDvImport.class, file.getName(), file.getOriginalFilename(), file.getContentType());
                result.put("status", YesOrNo.NO.getValue());
                result.put("message","error");
                result.put("fileuploadId",fileupload1.getFileuploadId());
                result.put("fileName",fileupload1.getFileSourceName());
            }else {
                // 开始导入
                if(CollectionUtils.isNotEmpty(categoryDvs)){
                    for (CategoryDv categoryDv : categoryDvs){
                        saveOrUpdateDv(categoryDv);
                    }
                }
                result.put("status", YesOrNo.YES.getValue());
                result.put("message","success");
            }
        }else {
            result.put("status", YesOrNo.YES.getValue());
            result.put("message","success");
        }

        return result;
    }

    public boolean checkData(List<CategoryDvImport> categoryDvImports,List<CategoryDv> categoryDvs) {
        boolean errorFlag = false;
        for(CategoryDvImport categoryDvImport : categoryDvImports){
            StringBuffer errorMsg = new StringBuffer();
            CategoryDv categoryDv = new CategoryDv();

            // 校验采购分类名称
            String categoryName = categoryDvImport.getCategoryName();
            if(StringUtil.notEmpty(categoryName)){
                List<PurchaseCategory> purchaseCategories = iPurchaseCategoryService.list(
                        new QueryWrapper<>(new PurchaseCategory().setCategoryName(categoryName.trim())));
                if(CollectionUtils.isNotEmpty(purchaseCategories)){
                    PurchaseCategory purchaseCategory = purchaseCategories.get(0);
                    iPurchaseCategoryService.setCategoryFullName(purchaseCategory);
                    categoryDv.setCategoryFullName(purchaseCategory.getCategoryFullName());
                    categoryDv.setCategoryId(purchaseCategory.getCategoryId());
                    categoryDv.setCategoryName(purchaseCategory.getCategoryName());
                }else {
                    errorMsg.append("该品类不存在; ");
                    errorFlag = true;
                }
            }else {
                errorMsg.append("品类名称不能为空; ");
                errorFlag = true;
            }

            // 校验采购组织名称
            String orgName = categoryDvImport.getOrgName();
            if(StringUtil.notEmpty(orgName)){
                List<Organization> organizations = organizationService.list(new QueryWrapper<>(new Organization().setOrganizationName(orgName.trim())));
                if(CollectionUtils.isNotEmpty(organizations)){
                    Organization organization = organizations.get(0);
                    List<OrganizationUser> organizationUsers = organizationUserService.list(
                            new QueryWrapper<>(new OrganizationUser().setOrganizationId(organization.getOrganizationId())));
                    if(CollectionUtils.isNotEmpty(organizationUsers)){
                        categoryDv.setOrgId(organization.getOrganizationId());
                        categoryDv.setOrgName(organization.getOrganizationName());
                        categoryDv.setFullPathId(organizationUsers.get(0).getFullPathId());
                    }else {
                        errorMsg.append("该用户没有该组织权限; ");
                        errorFlag = true;
                    }
                }else {
                    errorMsg.append("该组织不存在; ");
                    errorFlag = true;
                }
            }

            // 校验员工账号
            String userName = categoryDvImport.getUserName();
            if(StringUtil.notEmpty(userName)){
                LoginAppUser byUsername = rbacClient.findByUsername(userName.trim());
                if(null != byUsername && StringUtil.notEmpty(byUsername.getUsername())){
                    List<OrganizationUser> organizationUsers = byUsername.getOrganizationUsers();
                    // 检验员工是否有在组织权限
                    if(CollectionUtils.isNotEmpty(organizationUsers)){
                        boolean flag = true;
                        if (StringUtil.notEmpty(categoryDv.getOrgId())) {
                            for(OrganizationUser organizationUser : organizationUsers){
                                if(categoryDv.getOrgId().equals(organizationUser.getOrganizationId())){
                                    flag = false;
                                }
                            }
                        }
                        if(flag){
                            errorMsg.append("该用户没有该组织权限; ");
                            errorFlag = true;
                        }
                    }else if (StringUtil.notEmpty(categoryDv.getOrgId())){
                        errorMsg.append("该用户没有该组织权限; ");
                        errorFlag = true;
                    }
                    categoryDv.setUserId(byUsername.getUserId());
                    categoryDv.setUserName(byUsername.getUsername());
                    categoryDv.setFullName(byUsername.getNickname());
                }
            }else {
                errorMsg.append("员工账号不能为空; ");
                errorFlag = true;
            }

            // 校验生效时间
            String startDate = categoryDvImport.getStartDate();
            if (StringUtil.notEmpty(startDate)){
                try {
                    Date date = DateUtil.parseDate(startDate);
                    LocalDate localDate = DateUtil.dateToLocalDate(date);
                    categoryDv.setStartDate(localDate);
                } catch (ParseException e) {
                    errorMsg.append("生效时间格式不能解析; ");
                    errorFlag = true;
                }
            }else {
                errorMsg.append("生效时间不能为空; ");
                errorFlag = true;
            }

            // 校验结束时间
            String endDate = categoryDvImport.getEndDate();
            if (StringUtil.notEmpty(endDate)){
                try {
                    Date date = DateUtil.parseDate(endDate);
                    LocalDate localDate = DateUtil.dateToLocalDate(date);
                    categoryDv.setEndDate(localDate);
                } catch (ParseException e) {
                    errorMsg.append("失效时间格式不能解析; ");
                    errorFlag = true;
                }
            }

            if (StringUtil.notEmpty(categoryDv.getCategoryId()) &&
                    StringUtil.notEmpty(categoryDv.getUserId()) &&
                    StringUtil.notEmpty(categoryDv.getStartDate())) {
                if(null != categoryDv.getEndDate()){
                    if(DateChangeUtil.asDate(categoryDv.getEndDate()).before(new Date())){
                        errorMsg.append("结束日期必须大于当前日期; ");
                        errorFlag = true;
                    }
                }

                List<CategoryDv> dvs = this.checkExist(categoryDv);
                if(!CollectionUtils.isEmpty(dvs)){
                    errorMsg.append("数据已存在,请检查!; ");
                    errorFlag = true;
                }

                PurchaseCategory category = iPurchaseCategoryService.getById(categoryDv.getCategoryId());
                if(category == null ||  YesOrNo.NO.getValue().equals(category.getEnabled())){
                    errorMsg.append("品类未启用或不存在; ");
                    errorFlag = true;
                }
            }

            if(errorMsg.length() > 1){
                categoryDvImport.setErrorMsg(errorMsg.toString());
            }

            categoryDvs.add(categoryDv);
        }
        return errorFlag;
    }

    public List<CategoryDvImport> readData(MultipartFile file) {
        List<CategoryDvImport> categoryDvImports;
        try {
            // 获取输入流
            InputStream inputStream = file.getInputStream();
            // 数据收集器
            AnalysisEventListenerImpl<CategoryDvImport> listener = new AnalysisEventListenerImpl<>();
            ExcelReader excelReader = EasyExcel.read(inputStream,listener).build();
            // 第一个sheet读取类型
            ReadSheet readSheet = EasyExcel.readSheet(0).head(CategoryDvImport.class).build();
            // 开始读取第一个sheet
            excelReader.read(readSheet);
            categoryDvImports = listener.getDatas();
        } catch (IOException e) {
            throw new BaseException("excel解析出错");
        }
        return categoryDvImports;
    }

    @Override
    public List<List<Object>> queryExportData(ExportExcelParam<CategoryDv> param) {
        CategoryDv queryParam = param.getQueryParam();
        // 检查是否要分页导出
        boolean flag = StringUtil.notEmpty(queryParam.getPageSize()) && StringUtil.notEmpty(queryParam.getPageNum());
        if (flag) {
            // 设置分页
            PageUtil.startPage(queryParam.getPageNum(), queryParam.getPageSize());
        }
        DvRequestDTO dto = new DvRequestDTO();
        BeanCopyUtil.copyProperties(dto,queryParam);
        // 查询数据
        List<CategoryDv> categoryDvs = getCategoryDvs(dto);

        List<List<Object>> dataList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(categoryDvs)) {
            List<Map<String, Object>> mapList = BeanMapUtils.objectsToMaps(categoryDvs);
            ArrayList<String> titleList = param.getTitleList();
            if (CollectionUtils.isNotEmpty(titleList)) {
                for(Map<String, Object> map : mapList){
                    ArrayList<Object> objects = new ArrayList<>();
                    for(String key : titleList){
                        if("startDate".equals(key) || "endDate".equals(key)){
                            setDate(map,objects,key);
                        }else {
                            objects.add(map.get(key));
                        }
                    }
                    dataList.add(objects);
                }
            }
        }
        return dataList;
    }

    private void setDate(Map<String, Object> map, ArrayList<Object> list, String title) {
        Object date = map.get(title);
        if(null != date){
            LocalDate assessmentDate = (LocalDate)date;
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            list.add(assessmentDate.format(dateTimeFormatter));
        }else {
            list.add("");
        }
    }

    @Override
    public List<String> getMultilingualHeader(ExportExcelParam<CategoryDv> param) {
        LinkedHashMap<String, String> categoryDvTitles = ExportUtils.getCategoryDvTitles();
        return param.getMultilingualHeader(param,categoryDvTitles);
    }

    @Override
    public void exportStart(ExportExcelParam<CategoryDv> param, HttpServletResponse response) throws IOException {
        // 获取导出的数据
        List<List<Object>> dataList = queryExportData(param);
        // 标题
        List<String> head = getMultilingualHeader(param);
        // 文件名
        String fileName = param.getFileName();
        // 开始导出
        EasyExcelUtil.exportStart(response, dataList, head, fileName);
    }
}
