package com.midea.cloud.srm.perf.supplierenotice.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.perf.supplierenotice.dto.QuaSupplierEnoticeDTO;
import com.midea.cloud.srm.model.perf.supplierenotice.entity.QuaSupplierEnotice;
import com.midea.cloud.srm.perf.supplierenotice.service.QuaSupplierEnoticeService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
* <pre>
 *  21 前端控制器
 * </pre>
*
* @author wengzc@media.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Feb 1, 2021 5:12:43 PM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/quasupplierenotice")
public class QuaSupplierEnoticeController extends BaseController {

    @Autowired
    private QuaSupplierEnoticeService quaSupplierEnoticeService;
    @Resource
    private FileCenterClient fileCenterClient;

    /**
    * 获取
    * @param noticeId
    */
    @GetMapping("/get")
    public QuaSupplierEnotice get(Long noticeId) {
        Assert.notNull(noticeId, "noticeId不能为空");
        return quaSupplierEnoticeService.getById(noticeId);
    }

    /**
    * 新增
    * @param quaSupplierEnotice
    */
    @PostMapping("/add")
    public void add(@RequestBody QuaSupplierEnotice quaSupplierEnotice) {
        quaSupplierEnoticeService.add(quaSupplierEnotice);
    }
    
    /**
    * 删除
    * @param noticeId
    */
    @GetMapping("/delete")
    public void delete(Long noticeId) {
        Assert.notNull(noticeId, "noticeId不能为空");
        quaSupplierEnoticeService.removeById(noticeId);
    }

    /**
    * 修改
    * @param quaSupplierEnotice
    */
    @PostMapping("/modify")
    public void modify(@RequestBody QuaSupplierEnotice quaSupplierEnotice) {
        quaSupplierEnoticeService.updateById(quaSupplierEnotice);
        if (!CollectionUtils.isEmpty(quaSupplierEnotice.getFileUploads())) {
            fileCenterClient.bindingFileupload(quaSupplierEnotice.getFileUploads(), quaSupplierEnotice.getNoticeId());
        }
    }

    /**
    * 分页查询+条件查询
    * @param quaSupplierEnotice
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<QuaSupplierEnoticeDTO> listPage(@RequestBody QuaSupplierEnotice quaSupplierEnotice) {
        return quaSupplierEnoticeService.listPage(quaSupplierEnotice);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<QuaSupplierEnotice> listAll() {
        return quaSupplierEnoticeService.list();
    }

    /**
     * 导入文件内容
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return quaSupplierEnoticeService.importExcel(file,fileupload);
    }

    /**
     * 导出文件
     * @param excelParam
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestBody QuaSupplierEnotice excelParam, HttpServletResponse response) throws Exception {
        quaSupplierEnoticeService.exportExcel(excelParam,response);
    }
}
