package com.midea.cloud.srm.base.questionairesurvey.controller;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.filter.HttpServletHolder;
import com.midea.cloud.srm.base.questionairesurvey.service.SurveyScopeVendorService;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.SupplierHasSelectedDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.SurveyScopeVendorDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.VendorDTO;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.VendorFileDto;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyScopeVendor;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.spi.http.HttpHandler;
import java.util.List;
import java.io.IOException;
import java.util.Map;


/**
* <pre>
 *  问卷调查 前端控制器
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
@RestController
@RequestMapping("/base/surveyscopevendor")
public class SurveyScopeVendorController extends BaseController {

    @Autowired
    private SurveyScopeVendorService surveyScopeVendorService;

    @Autowired
    private SupplierClient supplierClient;

    /**
     * 添加问卷供应商
     */
    @RequestMapping("/addVendorSurvey")
    public void addVendorSurvey(@RequestBody SurveyScopeVendorDTO surveyScopeVendorDTO) {
        surveyScopeVendorService.addVendorSurvey(surveyScopeVendorDTO);
    }
    /**
     * 查询供应商
     */
    @RequestMapping("/getVendorInfo")
    public PageInfo<CompanyInfo> getVendorInfo(@RequestBody VendorDTO vendorDTO ) {
        return surveyScopeVendorService.listCompanyInfosByVendorDTO(vendorDTO);
    }
    /**
     * 批量删除已选问卷供应商
     */
    @RequestMapping("/deleteVendorSurvey")
    public void deleteVendorSurvey(Long id) {
        surveyScopeVendorService.deleteVendorSurvey(id);
    }
    /**
     * 批量导入供应商
     */
    @RequestMapping("/downLoadImportExcelTemplate")  //导入模板
    public void downLoadImportExcelTemplate(HttpServletResponse response) throws IOException {
        surveyScopeVendorService.downLoadImportExcelTemplate(response);
    }
    @RequestMapping("/customImportExcelTemplate")  //导入并且返回数据给前端
    public List<VendorDTO> customImportExcelTemplate(MultipartFile file, Fileupload fileupload) throws IOException {
        return surveyScopeVendorService.customImportExcelTemplate(file, fileupload);
    }
    /**
     * 导出已选择供应商
     */
    @PostMapping("/customExportSupplier")
    public void exportExcel(@RequestBody Map<String,Long> id) throws IOException {
        surveyScopeVendorService.exportExcel(id.get("id"), HttpServletHolder.getResponse());
    }
    /**
     * 查询已有供应商
     */
    @RequestMapping("/querySupplierHasSelected")
    public PageInfo<SurveyScopeVendor> querySupplierHasSelected(@RequestBody SupplierHasSelectedDTO supplierHasSelectedDTO) {
        PageUtil.startPage(supplierHasSelectedDTO.getPageNum(),supplierHasSelectedDTO.getPageSize());
        return new PageInfo<>(surveyScopeVendorService.querySupplierHasSelected(supplierHasSelectedDTO));
    }

    /**
     * 导出供应商反馈信息
     */
    @PostMapping("/feedbackResultExport")
    public void feedbackResultExport(Long surveyId)throws IOException{
        surveyScopeVendorService.feedbackResultExport(surveyId,HttpServletHolder.getResponse());
    }

    /**
     * 查询供应商的上传的附件列表
     */
    @PostMapping("/queryVendorAllFile")
    public PageInfo<VendorFileDto> queryVendorAllFile(@RequestBody VendorFileDto vendorFileDto){
        return surveyScopeVendorService.queryVendorAllFile(vendorFileDto);
    }
}
