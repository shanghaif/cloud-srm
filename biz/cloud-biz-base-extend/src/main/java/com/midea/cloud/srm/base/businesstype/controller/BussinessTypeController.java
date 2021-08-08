package com.midea.cloud.srm.base.businesstype.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.businesstype.service.IBussinessTypeService;
import com.midea.cloud.srm.model.base.businesstype.entity.BussinessType;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
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
 *  业务类型配置表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-21 14:57:34
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/businessType")
public class BussinessTypeController extends BaseController {

    @Autowired
    private IBussinessTypeService iBussinessTypeService;

    /**
    * 获取
    * @param bussinessTypeId
    */
    @GetMapping("/get")
    public BussinessType get(Long bussinessTypeId) {
        Assert.notNull(bussinessTypeId, "bussinessTypeId不能为空");
        return iBussinessTypeService.getById(bussinessTypeId);
    }

    /**
    * 新增
    * @param bussinessType
    */
    @PostMapping("/add")
    public void add(@RequestBody BussinessType bussinessType) {
        Long id = IdGenrator.generate();
        bussinessType.setBussinessTypeId(id);
        iBussinessTypeService.save(bussinessType);
    }
    
    /**
    * 删除
    * @param bussinessTypeId
    */
    @GetMapping("/delete")
    public void delete(Long bussinessTypeId) {
        Assert.notNull(bussinessTypeId, "bussinessTypeId不能为空");
        iBussinessTypeService.removeById(bussinessTypeId);
    }

    /**
    * 批量删除
    * @param bussinessTypeIds
    */
    @PostMapping("/batchDelete")
    public void batchDelete(@RequestBody List<Long> bussinessTypeIds) {
        iBussinessTypeService.removeByIds(bussinessTypeIds);
    }

    /**
    * 修改
    * @param bussinessType
    */
    @PostMapping("/modify")
    public void modify(@RequestBody BussinessType bussinessType) {
        iBussinessTypeService.updateById(bussinessType);
    }

    /**
    * 分页查询
    * @param bussinessType
    * @return
    */
    @PostMapping("/listPageByParam")
    public PageInfo<BussinessType> listPageByParam(@RequestBody BussinessType bussinessType) {
        return iBussinessTypeService.listPageByParam(bussinessType);
    }

    /**
     * 保存或编辑
     * @param bussinessType
     * @return
     */
    @PostMapping("/saveOrUpdateBussinessType")
    public Long saveOrUpdateBussinessType(@RequestBody BussinessType bussinessType) {
        return iBussinessTypeService.saveOrUpdateBussinessType(bussinessType);
    }

    /**
     * 导入文件模板下载
     * @return
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws IOException {
        iBussinessTypeService.importModelDownload(response);
    }

    /**
     * 导入文件
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iBussinessTypeService.importExcel(file, fileupload);
    }
}
