package com.midea.cloud.srm.perf.vendorimprove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.constants.VendorAssesFormConstant;
import com.midea.cloud.common.enums.VendorImproveFormStatus;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.organization.entity.Organization;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCategory;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.vendorimprove.VendorImproveForm;
import com.midea.cloud.srm.model.perf.vendorimprove.dto.VendorImproveFormDto;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.supplier.info.dto.ImproveFormDto;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import com.midea.cloud.srm.perf.vendorimprove.mapper.VendorImproveFormMapper;
import com.midea.cloud.srm.perf.vendorimprove.service.IVendorImproveFormService;
import com.midea.cloud.srm.perf.vendorimprove.utils.ExportUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
*  <pre>
 *  供应商改善单表 服务实现类
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
*/
@Service
public class VendorImproveFormServiceImpl extends ServiceImpl<VendorImproveFormMapper, VendorImproveForm> implements IVendorImproveFormService {

    @Resource
    private BaseClient baseClient;
    @Resource
    private SupplierClient supplierClient;
    @Resource
    private FileCenterClient fileCenterClient;
    @Resource
    private RbacClient rbacClient;

    @Override
    public PageInfo<VendorImproveForm> listPage(VendorImproveForm vendorImproveForm) {
        // 校验参数非空
        Assert.notNull(vendorImproveForm.getPageNum(), "pageNum不能为空");
        Assert.notNull(vendorImproveForm.getPageSize(), "pageSize不能为空");
        // 设置分页
        PageUtil.startPage(vendorImproveForm.getPageNum(),vendorImproveForm.getPageSize());
        List<VendorImproveForm> vendorImproveForms = getVendorImproveForms(vendorImproveForm);
        return new PageInfo<>(vendorImproveForms);
    }

    private List<VendorImproveForm> getVendorImproveForms(VendorImproveForm vendorImproveForm) {
        // 设置查询条件
        QueryWrapper<VendorImproveForm> wrappers = new QueryWrapper<>();
        wrappers.like(StringUtil.notEmpty(vendorImproveForm.getImproveNo()),"IMPROVE_NO",vendorImproveForm.getImproveNo());
        wrappers.like(StringUtil.notEmpty(vendorImproveForm.getCategoryName()),"CATEGORY_NAME",vendorImproveForm.getCategoryName());
        wrappers.eq(StringUtil.notEmpty(vendorImproveForm.getStatus()),"STATUS",vendorImproveForm.getStatus());
        wrappers.eq(StringUtil.notEmpty(vendorImproveForm.getImproveTitle()),"IMPROVE_TITLE",vendorImproveForm.getImproveTitle());
        wrappers.eq(StringUtil.notEmpty(vendorImproveForm.getOrganizationId()),"ORGANIZATION_ID",vendorImproveForm.getOrganizationId());
        // 判断是否供应商
        if(AppUserUtil.userIsVendor()){
            wrappers.eq("VENDOR_CODE",AppUserUtil.getVendorCode());
            wrappers.in("STATUS", VendorImproveFormStatus.EVALUATED.getCode(),
                    VendorImproveFormStatus.IMPROVING.getCode(),VendorImproveFormStatus.UNDER_EVALUATION.getCode());
        }else {
            wrappers.like(StringUtil.notEmpty(vendorImproveForm.getVendorName()),"VENDOR_NAME",vendorImproveForm.getVendorName());
        }
        wrappers.orderByDesc("VENDOR_IMPROVE_ID");
        return this.list(wrappers);
    }

    @Override
    public Map<String,Object> add(VendorImproveForm vendorImproveForm) {
        Long id = IdGenrator.generate();
        vendorImproveForm.setVendorImproveId(id);
        // 状态设置为拟定
        vendorImproveForm.setStatus(VendorImproveFormStatus.DRAFT.getCode());
        // 设置单号
        vendorImproveForm.setImproveNo(baseClient.seqGen(SequenceCodeConstant.SEQ_SCC_PERF_VENDOR_IMPROVE_FORM));
        this.save(vendorImproveForm);
        HashMap<String, Object> result = new HashMap<>();
        result.put("vendorImproveId",id);
        return result;
    }

    @Override
    public void delete(Long vendorImproveId) {
        Assert.notNull(vendorImproveId,"参数不能为空:vendorImproveId");
        this.removeById(vendorImproveId);
    }

    @Override
    public void notifySupplier(List<VendorImproveForm> vendorImproveForms) {
        if(CollectionUtils.isNotEmpty(vendorImproveForms)){
            vendorImproveForms.forEach((vendorImproveForm)->{
                if (isNotifySupplier(vendorImproveForm.getVendorImproveId())) {
                    vendorImproveForm.setMIsFeedback(VendorAssesFormConstant.M_IS_FEEDBACK_N);
                    vendorImproveForm.setVIsFeedback(VendorAssesFormConstant.V_IS_FEEDBACK_N);
                    vendorImproveForm.setStatus(VendorImproveFormStatus.IMPROVING.getCode());
                    if(StringUtil.notEmpty(vendorImproveForm.getVendorImproveId())){
                        this.updateById(vendorImproveForm);
                    }else {
                        // 第一次保存
                        Long id = IdGenrator.generate();
                        vendorImproveForm.setVendorImproveId(id);
                        // 设置单号
                        vendorImproveForm.setImproveNo(baseClient.seqGen(SequenceCodeConstant.SEQ_SCC_PERF_VENDOR_IMPROVE_FORM));
                        this.save(vendorImproveForm);
                    }
                }
            });
        }
    }

    /**
     * 是否可以通知供应商
     * @param vendorImproveId
     * @return
     */
    public boolean isNotifySupplier(Long vendorImproveId){
        VendorImproveForm vendorImproveForm = this.getById(vendorImproveId);
        String status = vendorImproveForm.getStatus();
        return VendorImproveFormStatus.DRAFT.getCode().equals(status) || null == status;
    }

    @Override
    public void vendorBuyers(VendorImproveForm vendorImproveForm) {
        Assert.notNull(vendorImproveForm.getVendorImproveId(),"缺少参数:vendorImproveId");
        UpdateWrapper<VendorImproveForm> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("V_FEEDBACK_TIME",StringUtil.getValue(vendorImproveForm.getVFeedbackTime(),new Date()));
        updateWrapper.set("V_FEEDBACK_FILE_UPLOAD_ID",vendorImproveForm.getVFeedbackFileUploadId());
        updateWrapper.set("V_FEEDBACK_FILE_SOURCE_NAME",vendorImproveForm.getVFeedbackFileSourceName());
        updateWrapper.set("V_FEEDBACK_EXPLANATION",vendorImproveForm.getVFeedbackExplanation());
        updateWrapper.set("V_IS_FEEDBACK", VendorAssesFormConstant.V_IS_FEEDBACK_Y);
        updateWrapper.set("STATUS", VendorImproveFormStatus.UNDER_EVALUATION.getCode());
        updateWrapper.eq("VENDOR_IMPROVE_ID",vendorImproveForm.getVendorImproveId());
        this.update(updateWrapper);
    }

    @Override
    public void buyersProcess(VendorImproveForm vendorImproveForm) {
        Assert.notNull(vendorImproveForm.getVendorImproveId(),"缺少参数:vendorImproveId");
        UpdateWrapper<VendorImproveForm> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("M_FEEDBACK_TIME",StringUtil.getValue(vendorImproveForm.getMFeedbackTime(),new Date()));
        updateWrapper.set("M_FEEDBACK_EXPLANATION",vendorImproveForm.getMFeedbackExplanation());
        updateWrapper.set("M_IS_FEEDBACK", VendorAssesFormConstant.M_IS_FEEDBACK_Y);
        updateWrapper.set("STATUS",VendorImproveFormStatus.EVALUATED.getCode());
        updateWrapper.eq("VENDOR_IMPROVE_ID",vendorImproveForm.getVendorImproveId());
        this.update(updateWrapper);
    }

    @Override
    public List<List<Object>> queryExportData(ExportExcelParam<VendorImproveForm> vendorImproveFormDto) {
        // 获取查询参数
        VendorImproveForm queryParam = vendorImproveFormDto.getQueryParam();
        boolean flag = StringUtil.notEmpty(queryParam.getPageSize()) && StringUtil.notEmpty(queryParam.getPageNum());
        if (flag) {
            // 设置分页
            PageUtil.startPage(queryParam.getPageNum(), queryParam.getPageSize());
        }
        // 查询数据
        List<VendorImproveForm> vendorImproveForms = getVendorImproveForms(queryParam);
        // 转map
        List<Map<String, Object>> mapList = BeanMapUtils.objectsToMaps(vendorImproveForms);
        // 获取标题列表
        ArrayList<String> titleList = vendorImproveFormDto.getTitleList();
        List<List<Object>> results = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(mapList)){
            mapList.forEach((map)->{
                if(CollectionUtils.isNotEmpty(titleList)){
                    ArrayList<Object> list = new ArrayList<>();
                    titleList.forEach((title)->{
                        if("improveDateStart".equals(title)){
                            setDate(map, list, title);
                        }else if("improveDateEnd".equals(title)){
                            setDate(map, list, title);
                        }else if ("status".equals(title)){
                            Object object = map.get(title);
                            if(null != object){
                                list.add(ExportUtils.keyValue.get(object.toString()));
                            }else {
                                list.add("");
                            }
                        }else {
                            Object object = map.get(title);
                            if(null != object){
                                list.add(object);
                            }else {
                                list.add("");
                            }
                        }
                    });
                    results.add(list);
                }
            });
        }
        return results;
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
    public List<String> getMultilingualHeader(ExportExcelParam<VendorImproveForm> vendorImproveFormDto) {
        ArrayList<String> titleList = vendorImproveFormDto.getTitleList();
        LinkedHashMap<String, String> vendorImproveFormTitles = ExportUtils.getVendorImproveFormTitles();
        ArrayList<String> results = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(titleList)){
            titleList.forEach((title)->{
                results.add(vendorImproveFormTitles.get(title));
            });
        }
        return results;
    }

    @Override
    public void exportStart(ExportExcelParam<VendorImproveForm> vendorImproveFormDto, HttpServletResponse response) throws IOException {
        // 获取导出的数据
        List<List<Object>> dataList = queryExportData(vendorImproveFormDto);
        // 标题
        List<String> head = getMultilingualHeader(vendorImproveFormDto);
        // 文件名
        String fileName = vendorImproveFormDto.getFileName();
        // 开始导出
        EasyExcelUtil.exportStart(response, dataList, head, fileName);
    }

    @Override
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 校验传参
        EasyExcelUtil.checkParam(file, fileupload);

        HashMap<String,Object> result = new HashMap<>();
        InputStream inputStream = file.getInputStream();
        ArrayList<VendorImproveFormDto> vendorImproveFormDtos = new ArrayList<>();
        ArrayList<VendorImproveForm> vendorImproveForms = new ArrayList<>();

        // 检查导入数据
        List<Object> objects = EasyExcelUtil.readExcelWithModel(inputStream, VendorImproveFormDto.class);
        if(CollectionUtils.isNotEmpty(objects)){
            objects.forEach((object -> {
                if(null != object){
                    VendorImproveFormDto vendorImproveFormDto = (VendorImproveFormDto)object;
                    VendorImproveForm vendorImproveForm = new VendorImproveForm();
                    // 检查参数
                    checkParam(vendorImproveFormDto, vendorImproveForm);
                    vendorImproveForms.add(vendorImproveForm);
                    vendorImproveFormDtos.add(vendorImproveFormDto);
                }
            }));
        }

        // 检查是否有错误信息
        boolean flag = true;
        if(CollectionUtils.isNotEmpty(vendorImproveFormDtos)){
            for(VendorImproveFormDto vendorImproveFormDto : vendorImproveFormDtos){
                String errorMsg = vendorImproveFormDto.getErrorMsg();
                if(errorMsg.length() > 1){
                    flag = false;
                    break;
                }
            }
        }
        if(flag){
            // 无无错误信息保存数据
            if(CollectionUtils.isNotEmpty(vendorImproveForms)){
                vendorImproveForms.forEach(vendorImproveForm -> add(vendorImproveForm));
            }
            result.put("status", YesOrNo.YES.getValue());
            result.put("message","success");
            return result;
        }else {
            Fileupload fileupload1 = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload,
                    vendorImproveFormDtos, VendorImproveFormDto.class, file.getName(), file.getOriginalFilename(), file.getContentType());
            result.put("status",YesOrNo.NO.getValue());
            result.put("message","error");
            result.put("fileuploadId",fileupload1.getFileuploadId());
            result.put("fileName",fileupload1.getFileSourceName());
            return result;
        }
    }

    public void checkParam(VendorImproveFormDto vendorImproveFormDto, VendorImproveForm vendorImproveForm) {
        StringBuffer errorMsg = new StringBuffer();
        vendorImproveFormDto.setErrorMsg(null);

        // 校验日期
        String improveDateStart = vendorImproveFormDto.getImproveDateStart();
        String improveDateEnd = vendorImproveFormDto.getImproveDateEnd();
        if(StringUtil.notEmpty(improveDateStart)){
            try {
                LocalDate localDateStart = DateUtil.dateToLocalDate(DateUtil.parseDate(improveDateStart));
                vendorImproveForm.setImproveDateStart(localDateStart);
            } catch (Exception e) {
                errorMsg.append("改善开始日期格式不对; ");
            }
        }
        if(StringUtil.notEmpty(improveDateEnd)){
            try {
                LocalDate localDateEnd = DateUtil.dateToLocalDate(DateUtil.parseDate(improveDateEnd));
                vendorImproveForm.setImproveDateEnd(localDateEnd);
            } catch (Exception e) {
                errorMsg.append("改善结束日期格式不对; ");
            }
        }

        // 设置不需校验字段
        vendorImproveForm.setImproveTitle(vendorImproveFormDto.getImproveTitle());
        vendorImproveForm.setImproveProject(vendorImproveFormDto.getImproveProject());
        vendorImproveForm.setExplanation(vendorImproveFormDto.getExplanation());

        // 校验责任跟进人名字
        String respUserName = vendorImproveFormDto.getRespUserName();
        if (StringUtil.notEmpty(respUserName)) {
            LoginAppUser byUsername = rbacClient.findByUsername(respUserName);
            if(null != byUsername && StringUtil.notEmpty(byUsername.getNickname())){
                vendorImproveForm.setRespUserName(byUsername.getUsername());
                vendorImproveForm.setRespFullName(byUsername.getNickname());
            }else {
                errorMsg.append("系统找不到该责任跟进人账号");
            }
        }

        // 校验供应商编码
        String vendorCode = vendorImproveFormDto.getVendorCode();
        if (StringUtil.notEmpty(vendorCode)) {
            CompanyInfo companyInfo = supplierClient.getCompanyInfoByParam(new CompanyInfo().setCompanyCode(vendorCode));
            if(null != companyInfo && StringUtil.notEmpty(companyInfo.getCompanyName())){
                vendorImproveForm.setVendorCode(companyInfo.getCompanyCode());
                vendorImproveForm.setVendorName(companyInfo.getCompanyName());
            }else {
                errorMsg.append("系统找不到该供应商编码; ");
            }
        }

        // 校验采购组织
        String organizationName = vendorImproveFormDto.getOrganizationName();
        if (StringUtil.notEmpty(organizationName)) {
            Organization organization = baseClient.getOrganizationByParam(new Organization().setOrganizationName(organizationName));
            if(null != organization && StringUtil.notEmpty(organization.getOrganizationCode())){
                vendorImproveForm.setOrganizationId(organization.getOrganizationId());
                vendorImproveForm.setOrganizationCode(organization.getOrganizationCode());
                vendorImproveForm.setOrganizationName(organization.getOrganizationName());
            }else {
                errorMsg.append("系统找不到该采购组织; ");
            }
        }

        // 校验采购分类

        String categoryName = vendorImproveFormDto.getCategoryName();
        if(StringUtil.notEmpty(categoryName)){
            PurchaseCategory category = baseClient.getPurchaseCategoryByParm(new PurchaseCategory().setCategoryName(categoryName));
            if(null != category && StringUtil.notEmpty(category.getCategoryCode())){
                vendorImproveForm.setCategoryId(category.getCategoryId());
                vendorImproveForm.setCategoryCode(category.getCategoryCode());
                vendorImproveForm.setCategoryName(category.getCategoryName());
            }else {
                errorMsg.append("系统找不到该采购组织; ");
            }
        }

        vendorImproveFormDto.setErrorMsg(errorMsg.toString());
    }

    @Override
    public void importModelDownload(HttpServletResponse response) throws IOException {
        String fileName = "供应商改善导入模板";
        ArrayList<VendorImproveFormDto> vendorImproveFormDtos = new ArrayList<>();
        ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileName);
        EasyExcelUtil.writeExcelWithModel(outputStream,fileName,vendorImproveFormDtos,VendorImproveFormDto.class);
    }

    @Override
    public List<ImproveFormDto> getImproveFormDtoByVendorId(Long vendorId) {
        CompanyInfo companyInfo = supplierClient.getCompanyInfo(vendorId);
        List<ImproveFormDto> improveFormDtos = new ArrayList<>();
        if(null != companyInfo && StringUtil.notEmpty(companyInfo.getCompanyCode())){
            String companyCode = companyInfo.getCompanyCode();
            List<VendorImproveForm> improveForms = this.list(new QueryWrapper<>(new VendorImproveForm().setVendorCode(companyCode)));
            if(CollectionUtils.isNotEmpty(improveForms)){
                improveForms.forEach(vendorImproveForm -> {
                    ImproveFormDto improveFormDto = new ImproveFormDto();
                    BeanCopyUtil.copyProperties(improveFormDto,vendorImproveForm);
                    improveFormDtos.add(improveFormDto);
                });
            }
        }
        return improveFormDtos;
    }
}
