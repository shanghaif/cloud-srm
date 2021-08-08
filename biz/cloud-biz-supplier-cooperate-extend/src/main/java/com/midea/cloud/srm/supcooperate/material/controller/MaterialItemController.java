package com.midea.cloud.srm.supcooperate.material.controller;

import com.midea.cloud.common.enums.supcooperate.MaterialItemType;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.suppliercooperate.material.dto.CeeaMaterialItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.supcooperate.material.service.IMaterialItemService;
import com.midea.cloud.srm.model.suppliercooperate.material.entity.CeeaMaterialItem;
import com.github.pagehelper.PageInfo;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
*  <pre>
 *  物料计划维护表 前端控制器
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-21 23:38:18
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/material/materialItem")
public class MaterialItemController extends BaseController {



    @Autowired
    private IMaterialItemService iMaterialItemService;

    /**
     * 发布物料计划维护
     */
    @GetMapping("/getmateriaPublish")
    public void getmateriaPublish(Long id){
        Assert.notNull(id, "id不能为空");
        CeeaMaterialItem materialItem = iMaterialItemService.getById(id);
        //只要是未发布的都可以转已发布
        if (materialItem.getSchType()==null||!materialItem.getSchType().equals(MaterialItemType.get("ISSUED"))){
            materialItem.setSchType("ISSUED");
            iMaterialItemService.updateById(materialItem);
        }
    }

    /**
    * 获取物料计划详情及明细列表
    * @param id
    */
    @GetMapping("/getMaterialItemDetail")
    public CeeaMaterialItemDTO getMaterialItemDetail(Long id) {
        Assert.notNull(id, "id不能为空");
        return iMaterialItemService.getMaterialItemDetail(id);
    }

    /**
    * 新增
    * @param materialItem
    */
    @PostMapping("/add")
    public void add(@RequestBody CeeaMaterialItem materialItem) {
        Long id = IdGenrator.generate();
        materialItem.setMaterialItemId(id);
        iMaterialItemService.save(materialItem);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iMaterialItemService.removeById(id);
    }

    /**
    * 修改
    * @param materialItem
    */
    @PostMapping("/modify")
    public void modify(@RequestBody CeeaMaterialItem materialItem) {
        iMaterialItemService.updateById(materialItem);
    }

    /**
    * 分页查询
    * @param materialItemDTO
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<CeeaMaterialItem> listPage(@RequestBody CeeaMaterialItemDTO materialItemDTO) {
        return iMaterialItemService.getMaterialItemList(materialItemDTO);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<CeeaMaterialItem> listAll() {
        return iMaterialItemService.list();
    }

    /**
     * 下载物料计划导入模板
     * @return
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(@RequestParam("monthlySchDate") String monthlySchDate, HttpServletResponse response) throws IOException, ParseException {
        iMaterialItemService.importModelDownload(monthlySchDate,response);
    }

    /**
     * 导入文件
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload,HttpServletResponse response) throws Exception {
        return iMaterialItemService.importExcel(file, fileupload, response);
    }

    /**
     * 导出
     * @param materialItemDTO
     * @return
     */
    @PostMapping("/export")
    public void export(@RequestBody CeeaMaterialItemDTO materialItemDTO, HttpServletResponse response) throws IOException, ParseException {
        iMaterialItemService.export(materialItemDTO,response);
    }

    /**
     * 导出
     * @param materialItemId
     * @return
     */
    @RequestMapping("/exportDetail")
    public void exportDetail(@RequestParam("materialItemId") String materialItemId, HttpServletResponse response) throws IOException, ParseException {
        iMaterialItemService.exportDetail(materialItemId,response);
    }

    /**
     * 下载物料计划详情导入模板
     * @return
     */
    @RequestMapping("/importModelDetailDownload")
    public void importModelDetailDownload(@RequestParam("materialItemId") String materialItemId, HttpServletResponse response) throws IOException, ParseException {
        iMaterialItemService.importModelDetailDownload(materialItemId,response);
    }


    /**
     * 导入物料计划详情
     * @param file
     */
    @RequestMapping("/importDetailExcel")
    public Map<String,Object> importDetailExcel(String materialItemId,@RequestParam("file") MultipartFile file, Fileupload fileupload,HttpServletResponse response) throws Exception {
        return iMaterialItemService.importDetailExcel(materialItemId,file, fileupload, response);
    }
}
