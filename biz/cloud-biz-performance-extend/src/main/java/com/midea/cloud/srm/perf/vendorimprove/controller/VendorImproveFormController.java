package com.midea.cloud.srm.perf.vendorimprove.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.vendorimprove.VendorImproveForm;
import com.midea.cloud.srm.model.supplier.info.dto.ImproveFormDto;
import com.midea.cloud.srm.perf.vendorimprove.service.IVendorImproveFormService;
import com.midea.cloud.srm.perf.vendorimprove.utils.ExportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  供应商改善单表 前端控制器
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
@RestController
@RequestMapping("/vendorImprove")
public class VendorImproveFormController extends BaseController {

    @Autowired
    private IVendorImproveFormService iVendorImproveFormService;

    /**
     * 供应商改善单分页查询
     * @param vendorImproveForm
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<VendorImproveForm> listPage(@RequestBody VendorImproveForm vendorImproveForm) {
        return iVendorImproveFormService.listPage(vendorImproveForm);
    }

    /**
     * 查询自定供应商的改善信息
     * @param vendorId
     * @return
     */
    @PostMapping("/getImproveFormDtoByVendorId")
    public List<ImproveFormDto> getImproveFormDtoByVendorId(@RequestParam("vendorId") Long vendorId){
        return iVendorImproveFormService.getImproveFormDtoByVendorId(vendorId);
    }

    /**
     * 供应商改善单查询
     * @param vendorImproveId
     * @return
     */
    @GetMapping("/queryById")
    public VendorImproveForm queryById(@RequestParam Long vendorImproveId) {
        return iVendorImproveFormService.getById(vendorImproveId);
    }

    /**
     * 创建改善单
     * @param vendorImproveForm
     */
    @PostMapping("/add")
    public Map<String,Object> add(@RequestBody VendorImproveForm vendorImproveForm) {
        return iVendorImproveFormService.add(vendorImproveForm);
    }

    /**
     * 修改
     * @param vendorImproveForm
     */
    @PostMapping("/modify")
    public void modify(@RequestBody VendorImproveForm vendorImproveForm) {
        Assert.notNull(vendorImproveForm.getVendorImproveId(),"参数不能为空:vendorImproveId");
        iVendorImproveFormService.updateById(vendorImproveForm);
    }
    
    /**
    * 单个删除
    * @param vendorImproveId
    */
    @GetMapping("/delete")
    public void delete( Long vendorImproveId) {
        iVendorImproveFormService.delete(vendorImproveId);
    }

    /**
     * 通知供应商(采购商)
     * @param vendorImproveForms
     */
    @PostMapping("/notifySupplier")
    public void notifySupplier(@RequestBody List<VendorImproveForm> vendorImproveForms){
        iVendorImproveFormService.notifySupplier(vendorImproveForms);
    }

    /**
     * 供应商提交改善反馈信息
     * @param vendorImproveForm
     */
    @PostMapping("/vendorFeedback")
    public void vendorBuyers(@RequestBody VendorImproveForm vendorImproveForm){
        iVendorImproveFormService.vendorBuyers(vendorImproveForm);
    }

    /**
     * 采购商评价反馈信息
     * @param vendorImproveForm
     */
    @PostMapping("/buyersProcess")
    public void buyersProcess(@RequestBody VendorImproveForm vendorImproveForm){
        iVendorImproveFormService.buyersProcess(vendorImproveForm);
    }

    /**
     * 获取导出标题
     * @return
     */
    @RequestMapping("/exportExcelTitle")
    public Map<String,String> exportExcelTitle(){
        return ExportUtils.getVendorImproveFormTitles();
    }

    /**
     * 导出文件
     * @param vendorImproveFormDto
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestBody ExportExcelParam<VendorImproveForm> vendorImproveFormDto, HttpServletResponse response) throws Exception {
        iVendorImproveFormService.exportStart(vendorImproveFormDto, response);
    }

    /**
     * 导入文件模板下载
     * @return
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws IOException {
        iVendorImproveFormService.importModelDownload(response);
    }

    /**
     * 导入文件
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iVendorImproveFormService.importExcel(file,fileupload);
    }

    /**
     * 导入文件
     * @param msg
     */
    @RequestMapping("/error")
    public void error(String msg){
        throw new BaseException(msg);
    }
}
