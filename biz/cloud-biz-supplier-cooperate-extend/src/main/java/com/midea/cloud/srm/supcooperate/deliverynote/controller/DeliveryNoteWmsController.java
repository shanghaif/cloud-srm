package com.midea.cloud.srm.supcooperate.deliverynote.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteWms;
import com.midea.cloud.srm.supcooperate.deliverynote.service.IDeliveryNoteWmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  送货单WMS清单 前端控制器
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-17 19:11:49
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/deliverynote/deliveryNoteWms")
public class DeliveryNoteWmsController extends BaseController {
    @Autowired
    private IDeliveryNoteWmsService iDeliveryNoteWmsService;
    @Autowired
    private FileCenterClient fileCenterClient;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public DeliveryNoteWms get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iDeliveryNoteWmsService.getById(id);
    }

    /**
     * 新增
     *
     * @param deliveryNoteWms
     */
    @PostMapping("/add")
    public void add(@RequestBody DeliveryNoteWms deliveryNoteWms) {
        Long id = IdGenrator.generate();
        iDeliveryNoteWmsService.save(deliveryNoteWms);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iDeliveryNoteWmsService.removeById(id);
    }

    /**
     * 修改
     *
     * @param deliveryNoteWms
     */
    @PostMapping("/modify")
    public void modify(@RequestBody DeliveryNoteWms deliveryNoteWms) {
        iDeliveryNoteWmsService.updateById(deliveryNoteWms);
    }

    /**
     * wms清单分页查询
     *
     * @param deliveryNoteWms
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<DeliveryNoteWms> listPage(@RequestBody DeliveryNoteWms deliveryNoteWms) {
        PageUtil.startPage(deliveryNoteWms.getPageNum(), deliveryNoteWms.getPageSize());
        return new PageInfo<DeliveryNoteWms>(iDeliveryNoteWmsService.deliveryNoteWmsList(deliveryNoteWms));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<DeliveryNoteWms> listAll() {
        return iDeliveryNoteWmsService.list();
    }

    /**
     * excel 导入WMS清单
     *
     * @param file
     * @param
     */
    @PostMapping("/importExcel")
    public PageInfo<DeliveryNoteWms> saveByExcel(@RequestParam("file") MultipartFile file, Long id) throws Exception {
        PageUtil.startPage(null, null);
        return new PageInfo<DeliveryNoteWms>(iDeliveryNoteWmsService.importExcel(file, id));
    }

    /**
     * 下载模板
     */
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> result = iDeliveryNoteWmsService.downloadTemplate();
        String fileName = URLEncoder.encode(result.get("fileName").toString(), "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition"); // 解决axios下载后取不到文件名的问题
        response.setHeader("FileName", fileName); // 解决axios下载后取不到文件名的问题
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.getOutputStream().write((byte[]) result.get("buffer"));
        response.getOutputStream().close();
    }

    /**
     * excel导出
     */
    @GetMapping("/getexportExcel")
    public void getexportExcel(Long deliveryNoteId, HttpServletResponse response) throws IOException {
        iDeliveryNoteWmsService.excelEportRequirementLine(deliveryNoteId, response);
    }
}
