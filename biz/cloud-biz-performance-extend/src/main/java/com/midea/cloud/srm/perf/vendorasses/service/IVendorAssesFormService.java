package com.midea.cloud.srm.perf.vendorasses.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.ExportExcel;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.vendorasses.VendorAssesForm;
import com.midea.cloud.srm.model.perf.vendorasses.dto.VendorAssesFormOV;
import com.midea.cloud.srm.model.supplier.info.dto.AssesFormDto;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  供应商考核单表 服务类
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:55:47
 *  修改内容:
 * </pre>
*/
public interface IVendorAssesFormService extends IService<VendorAssesForm>, ExportExcel<VendorAssesForm> {

    /**
     * 废弃订单
     * @param vendorAssesId
     */
    void abandon(Long vendorAssesId);

    /**
     * 供应商考核单分页查询(采购商)
     * @param vendorAssesForm
     * @return
     */
    PageInfo<VendorAssesFormOV> listPage(VendorAssesForm vendorAssesForm);

    /**
     * 新增考核单(采购商)
     * @param vendorAssesForm
     */
    Map<String,Object> add(VendorAssesForm vendorAssesForm);

    /**
     * 批量删除
     * @param vendorAssesId
     */
    void delete(Long vendorAssesId);

    /**
     * 通知供应商
     * @param vendorAssesForm
     */
    void notifySupplier(VendorAssesForm vendorAssesForm);

    /**
     * 批量处理通知供应商
     * @param vendorAssesForms
     */
    void notifySupplier(List<VendorAssesForm> vendorAssesForms);

    /**
     * 供应商反馈信息
     * @param vendorAssesForm
     */
    void vendorFeedback(VendorAssesForm vendorAssesForm);

    /**
     * 采购商处理
     * @param vendorAssesForm
     */
    void buyersProcess(VendorAssesForm vendorAssesForm);

    /**
     * 导入excel
     * @param file
     * @return
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;

    /**
     * 导入模板下载
     * @param response
     */
    void importModelDownload(HttpServletResponse response) throws IOException;

    /**
     * 罚款明细查询
     * @param vendorAssesForm
     * @return
     */
    List<VendorAssesForm> queryAmerceInfo(VendorAssesForm vendorAssesForm);

    Map<String,Object> submitWithFlow(VendorAssesForm vendorAssesForm);

    /**
     * 查询供应商考核信息
     * @param vendorId
     * @return
     */
    List<AssesFormDto> getAssesFormDtoByVendorId(Long vendorId);

    /**
     * 根据考核时间+指标维度+指标名称+评价结果-->建议考核金额
     *  获取对的建议考核金额
     * @param assesForm
     * @return
     */
    BigDecimal getAssessmentPenalty(VendorAssesForm assesForm);

    /**
     * 分页查询供应商考核单(结算用)
     * @param vendorAssesForm
     * @return
     */
    PageInfo<VendorAssesFormOV> listPageForInvoice(VendorAssesForm vendorAssesForm);

    /**
     * 供应商确认考核
     * @param vendorAssesForm
     */
    void vendorAffirm(VendorAssesForm vendorAssesForm);
}
