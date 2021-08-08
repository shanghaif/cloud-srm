package com.midea.cloud.srm.base.questionairesurvey.service.impl;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.base.FileFlagEnum;
import com.midea.cloud.common.enums.base.ResultFlagEnum;
import com.midea.cloud.common.utils.*;

import com.midea.cloud.srm.base.questionairesurvey.mapper.SurveyHeaderMapper;
import com.midea.cloud.srm.base.questionairesurvey.mapper.SurveyScopeVendorMapper;
import com.midea.cloud.srm.base.questionairesurvey.service.SurveyScopeVendorService;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.*;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyHeader;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyScopeVendor;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.rbac.user.entity.User;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
* <pre>
 *  问卷调查 服务实现类
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 16, 2021 9:08:51 AM
 *  修改内容:
 * </pre>
*/
@Service
public class SurveyScopeVendorServiceImpl extends ServiceImpl<SurveyScopeVendorMapper, SurveyScopeVendor> implements SurveyScopeVendorService {

    @Autowired
    private SupplierClient supplierClient;

    @Autowired
    private SurveyScopeVendorMapper surveyScopeVendorMapper;

    @Autowired
    private SurveyHeaderMapper surveyHeaderMapper;

    @Autowired
    private FileCenterClient fileCenterClient;

    @Autowired
    private RbacClient rbacClient;

    @Override
    public FeedbackCountDTO queryFeedbackResult(Long id) {
        return surveyScopeVendorMapper.queryFeedbackResult(id);
    }

    @Override
    public PageInfo<CompanyInfo> listCompanyInfosByVendorDTO(VendorDTO vendorDTO) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select("VENDOR_CODE");
        if(vendorDTO.getSurveyId() != null) {
            wrapper.eq("SURVEY_ID",vendorDTO.getSurveyId());
            List<SurveyScopeVendor> surveyScopeVendors = this.list(wrapper);
            List<String> vendorCodes = surveyScopeVendors.stream().map(SurveyScopeVendor::getVendorCode).collect(Collectors.toList());
            vendorDTO.setVendorCodes(vendorCodes);
        }
        return supplierClient.listCompanyInfosByVendorDTO(vendorDTO);
    }

    @Override
    public List<FeedbackSupplierDTO> listFeedbackVendor(FeedbackSupplierDTO feedbackSupplierDTO) {
        return surveyScopeVendorMapper.listFeedbackVendor(feedbackSupplierDTO);
    }

    @Override
    public void exportExcel(Long id, HttpServletResponse response) throws IOException {
        // 查询数据
        List<ExcelSurveyScopeVendorDto> excelSurveyScopeVendorDtoList = new ArrayList<>();
        if(id != null) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.select("VENDOR_CODE","VENDOR_NAME");
            wrapper.eq("SURVEY_ID",id);
            excelSurveyScopeVendorDtoList = this.list(wrapper);
        }
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "导出文件名");
        EasyExcel.write(outputStream).head(ExcelSurveyScopeVendorDto.class).sheet(0).sheetName("sheetName").doWrite(excelSurveyScopeVendorDtoList);
    }

    @Override
    public void feedbackResultExport(Long surveyId, HttpServletResponse response)throws IOException {
        //查询数据
        List<ExcelFeedBackResultDto> excelFeedBackResultDtos = new ArrayList<>();
        if(surveyId!=null){
            excelFeedBackResultDtos = surveyScopeVendorMapper.queryVendorScopeFeedBack(surveyId);
        }

        for(int i =0;i<excelFeedBackResultDtos.size();i++){
            ExcelFeedBackResultDto excelFeedBackResultDto = excelFeedBackResultDtos.get(i);
            excelFeedBackResultDto.setNum(i+1);
            //设置excel中的序号与反馈状态修改
            String resultFlag = excelFeedBackResultDto.getResultFlag();
            if(ResultFlagEnum.Y.getValue().equals(resultFlag)){
                excelFeedBackResultDto.setResultFlag(ResultFlagEnum.Y.getFlag());
            }else if(ResultFlagEnum.N.getValue().equals(resultFlag)){
                excelFeedBackResultDto.setResultFlag(ResultFlagEnum.N.getFlag());
            }else{
                excelFeedBackResultDto.setResultFlag(ResultFlagEnum.EXPIRED.getFlag());
            }

            //设置供应商的账号信息
            VendorDTO vendorDTO = new VendorDTO();
            vendorDTO.setVendorCode(excelFeedBackResultDto.getVendorCode());
            CompanyInfo companyInfo = supplierClient.getCompanyInfo(vendorDTO);
            User user = rbacClient.queryByCompanyId(companyInfo.getCompanyId());
            if(user!=null){
                excelFeedBackResultDto.setUserName(user.getUsername());
            }
        }
        //获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "导出文件名");
        EasyExcel.write(outputStream).head(ExcelFeedBackResultDto.class).sheet(0).sheetName("sheetName").doWrite(excelFeedBackResultDtos);
    }

    @Override
    public PageInfo<VendorFileDto> queryVendorAllFile(VendorFileDto vendorFileDto) {
        List<VendorFileDto> vendorFileDtos = new ArrayList<>();
        //根据surveyId查询到对应的供应商list
        PageUtil.startPage(vendorFileDto.getPageNum(), vendorFileDto.getPageSize());
        QueryWrapper<SurveyScopeVendor> wrapper = new QueryWrapper<>();
        wrapper.eq("SURVEY_ID",vendorFileDto.getSurveyId());
        wrapper.eq("RESULT_FLAG", FileFlagEnum.Y.getValue());
        List<SurveyScopeVendor> surveyScopeVendors = surveyScopeVendorMapper.selectList(wrapper);
        //遍历供应商list拿到对应的vendorScopeId，再通过vendorScopeId到fileupload中拿到对应的fileuploadId
        for(SurveyScopeVendor vendor:surveyScopeVendors) {
            VendorFileDto vendorFileDto2 = new VendorFileDto();
            Fileupload fileupload = fileCenterClient.getByBusinessId(vendor.getVendorScopeId());
            if (StringUtil.notEmpty(vendor) && StringUtil.notEmpty(fileupload)) {
                vendorFileDto2.setFileUploadId(fileupload.getFileuploadId());
                vendorFileDto2.setFileName(fileupload.getFileSourceName());
                vendorFileDto2.setVendorCode(vendor.getVendorCode());
                vendorFileDto2.setVendorName(vendor.getVendorName());
                vendorFileDto2.setUploadDate(vendor.getLastUpdateDate());
                vendorFileDtos.add(vendorFileDto2);
            }
        }
        return new PageInfo<>(vendorFileDtos);
    }

    @Override
    public List<SurveyScopeVendor> querySupplierHasSelected(SupplierHasSelectedDTO supplierHasSelectedDTO) {
        QueryWrapper wrapper = new QueryWrapper();
        List<SurveyScopeVendor> surveyScopeVendors = new ArrayList<>();
        if(supplierHasSelectedDTO.getSurveyId() != null) {
            wrapper.eq("SURVEY_ID",supplierHasSelectedDTO.getSurveyId());
            surveyScopeVendors = this.list(wrapper);
        }
        return surveyScopeVendors;
    }

    @Override
    @Transactional
    public List<VendorDTO> customImportExcelTemplate(MultipartFile file, Fileupload fileupload) throws IOException {
        // 检查参数
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<ExcelSurveyScopeVendorDto> excelSurveyScopeVendorDtoList = EasyExcelUtil.readExcelWithModel(file, ExcelSurveyScopeVendorDto.class);
        // 筛选数据
        List<VendorDTO> vendorDTOList = filterData(excelSurveyScopeVendorDtoList);
        return vendorDTOList;
    }

    public List<VendorDTO> filterData(List<ExcelSurveyScopeVendorDto> excelSurveyScopeVendorDtoList){

        List<VendorDTO> vendorDTOList = new ArrayList<>();
        Set<String> set = new HashSet();
        if(CollectionUtils.isNotEmpty(excelSurveyScopeVendorDtoList)) {
            //去重
            for(ExcelSurveyScopeVendorDto surveyQuestionDTO : excelSurveyScopeVendorDtoList) {
                set.add(surveyQuestionDTO.getVendorCode());
            }
            List<String> list = new ArrayList<>();
            list.addAll(set);
            vendorDTOList = supplierClient.listCompanyInfosByStringList(list);
        }
        if (CollectionUtils.isEmpty(vendorDTOList)) {
            Assert.isTrue(false,"导入失败，没有可导入的供应商信息");
        }
        return vendorDTOList;
    }

    @Override
    public void downLoadImportExcelTemplate(HttpServletResponse response) throws IOException {
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response,"模板下载");
        EasyExcel.write(outputStream).head(ExcelSurveyScopeVendorDto.class).sheet(0).sheetName("sheetName").doWrite(Arrays.asList(new ExcelSurveyScopeVendorDto()));
    }

    @Override
    public void deleteVendorSurvey(Long id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("SURVEY_ID",id);
        this.remove(wrapper);
    }

    @Override
    public void addVendorSurvey(SurveyScopeVendorDTO surveyScopeVendorDTO) {
        Long surveyId = surveyScopeVendorDTO.getSurveyId();
        for(SurveyScopeVendor surveyScopeVendor : surveyScopeVendorDTO.getSurveyScopeVendorList()) {
            surveyScopeVendor.setVendorScopeId(IdGenrator.generate());
            surveyScopeVendor.setSurveyId(surveyId);
        }
        this.saveBatch(surveyScopeVendorDTO.getSurveyScopeVendorList());
    }

    @Override
    public SurveyScopeVendorSupplierDto queryScopeVendorInfo(Long id,String vendorCode) {
        //供应商信息
        QueryWrapper<SurveyScopeVendor> scopeVendorQueryWrapper = new QueryWrapper();
        scopeVendorQueryWrapper.eq("SURVEY_ID",id);
        //scopeVendorQueryWrapper.eq("VENDOR_CODE","C005666");
        if(vendorCode!=""){
            scopeVendorQueryWrapper.eq("VENDOR_CODE", vendorCode);
        }else{
            scopeVendorQueryWrapper.eq("VENDOR_CODE", AppUserUtil.getVendorCode());
        }
        SurveyScopeVendor surveyScopeVendor = this.getOne(scopeVendorQueryWrapper);
        //问卷
        SurveyHeader surveyHeader = surveyHeaderMapper.selectById(id);

        SurveyScopeVendorSupplierDto surveyScopeVendorSupplierDto = new SurveyScopeVendorSupplierDto();
        BeanUtils.copyProperties(surveyHeader, surveyScopeVendorSupplierDto);
        BeanUtils.copyProperties(surveyScopeVendor, surveyScopeVendorSupplierDto);

        //查询供应商上传的文件
        Fileupload fileupload = fileCenterClient.getByBusinessId(surveyScopeVendor.getVendorScopeId());
        if(StringUtil.notEmpty(fileupload)){
            surveyScopeVendorSupplierDto.setDocId(fileupload.getFileuploadId());
            surveyScopeVendorSupplierDto.setDocName(fileupload.getFileSourceName());
        }


        //如果反馈结果为未反馈或已过期，将最后更新时间设置为null
        String resultFlag = surveyScopeVendorSupplierDto.getResultFlag();
        if(ResultFlagEnum.N.getValue().equals(resultFlag)||ResultFlagEnum.EXPIRED.getValue().equals(resultFlag)){
            surveyScopeVendorSupplierDto.setLastUpdateDate(null);
        }


        return surveyScopeVendorSupplierDto;
    }

}
