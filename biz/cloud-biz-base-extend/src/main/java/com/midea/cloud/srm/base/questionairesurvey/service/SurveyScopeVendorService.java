package com.midea.cloud.srm.base.questionairesurvey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.questionairesurvey.dto.*;
import com.midea.cloud.srm.model.base.questionairesurvey.entity.SurveyScopeVendor;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;

/**
* <pre>
 *  问卷调查 服务类
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
public interface SurveyScopeVendorService extends IService<SurveyScopeVendor>{

    /**
     * 批量添加供应商
     */
    void addVendorSurvey(SurveyScopeVendorDTO surveyScopeVendorDTO);
    /**
     * 批量清除已选择供应商
     */
    void deleteVendorSurvey(Long id);
    /**
     * 导入模板
     */
    void downLoadImportExcelTemplate(HttpServletResponse response) throws IOException;
    /**
     *  查询已有供应商
     */
    List<SurveyScopeVendor> querySupplierHasSelected(SupplierHasSelectedDTO supplierHasSelectedDTO);
    /**
     * 导入
     */
    List<VendorDTO> customImportExcelTemplate(MultipartFile file, Fileupload fileupload) throws IOException;
    /**
     * 导出
     */
    void exportExcel(Long id, HttpServletResponse response) throws IOException;
    /**
     * 列出反馈供应商清单
     */
    List<FeedbackSupplierDTO> listFeedbackVendor(FeedbackSupplierDTO feedbackSupplierDTO);
    /**
     * 统计反馈
     */
    FeedbackCountDTO queryFeedbackResult(Long id);

    PageInfo<CompanyInfo>listCompanyInfosByVendorDTO(VendorDTO vendorDTO);
    /**
     * 查找供应商信息
     * @param id
     * @return
     */
    SurveyScopeVendorSupplierDto queryScopeVendorInfo(Long id,String vendorCode);

    /**
     * 导出供应商反馈信息
     * @param surveyId
     * @param response
     */
    void feedbackResultExport(Long surveyId, HttpServletResponse response)throws IOException;

    /**
     * 查询供应商的上传的附件列表
     * @param vendorFileDto
     * @return
     */
    PageInfo<VendorFileDto> queryVendorAllFile(VendorFileDto vendorFileDto);
}
