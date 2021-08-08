package com.midea.cloud.srm.perf.vendorimprove.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.ExportExcel;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.vendorimprove.VendorImproveForm;
import com.midea.cloud.srm.model.supplier.info.dto.ImproveFormDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  供应商改善单表 服务类
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
public interface IVendorImproveFormService extends IService<VendorImproveForm> , ExportExcel<VendorImproveForm> {
    /**
     * 供应商改善单查询
     * @param vendorImproveForm
     * @return
     */
    PageInfo<VendorImproveForm> listPage(VendorImproveForm vendorImproveForm);

    /**
     * 新建供应商改善单
     * @param vendorImproveForm
     */
    Map<String,Object> add(VendorImproveForm vendorImproveForm);

    /**
     * 批量删除单子
     * @param vendorImproveId
     */
    void delete(Long vendorImproveId);

    /**
     * 批量通知供应商
     * @param vendorImproveForms
     */
    void notifySupplier(List<VendorImproveForm> vendorImproveForms);

    /**
     * 供应商提交改善反馈信息
     * @param vendorImproveForm
     */
    void vendorBuyers(VendorImproveForm vendorImproveForm);

    /**
     * 采购商评价反馈信息
     * @param vendorImproveForm
     */
    void buyersProcess(VendorImproveForm vendorImproveForm);

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
     * 查询指定供应商的改善信息
     * @param vendorId
     * @return
     */
    List<ImproveFormDto> getImproveFormDtoByVendorId(@RequestParam("vendorId") Long vendorId);
}
