package com.midea.cloud.srm.base.dict.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.dict.service.IDictService;
import com.midea.cloud.srm.model.base.dict.entity.Dict;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.ExportExcelParamCommon;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.logistics.bid.dto.BillingCombination;
import com.midea.cloud.srm.model.suppliercooperate.orderreceive.dto.OrderReceiveDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  字典表 前端控制器
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-19 10:33:17
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/dict/base-dict")
public class DictController extends BaseController {

    @Autowired
    private IDictService iDictService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public Dict get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iDictService.getById(id);
    }

    /**
    * 新增
    * @param dict
    */
    @PostMapping("/add")
    public void add(Dict dict) {
        Long id = IdGenrator.generate();
        dict.setDictId(id);
        iDictService.save(dict);
    }
    
    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iDictService.removeById(id);
    }
    /**
     * 批量删除
     */
    @PostMapping("/batchDelete")
    public void batchDelete(@RequestBody List<Long>ids) throws Exception {
        if(ids.size() == 0) {
            Assert.isTrue(false,"请先勾选头字典");
        }
        iDictService.removeDictAndItems(ids);
    }

    /**
    * 修改
    * @param dict
    */
    @PostMapping("/modify")
    public void modify(Dict dict) {
        iDictService.updateById(dict);
    }

    /**
    * 分页查询
    * @param dict
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<Dict> listPage(Dict dict) {
        PageUtil.startPage(dict.getPageNum(), dict.getPageSize());
        QueryWrapper<Dict> wrapper = new QueryWrapper<Dict>(dict);
        return new PageInfo<Dict>(iDictService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<Dict> listAll() {
        return iDictService.list();
    }

    /**
     * 保存与更新
     */
    @PostMapping("/saveOrUpdateDict")
    public void saveOrUpdateDict(@RequestBody Dict dict){

         iDictService.saveOrUpdateDict(dict);
    }

    /**
     * 根据条件分页查询
     * @param dict
     * @return
     */
    @PostMapping("/queryPageByConditions")
    public PageInfo<Dict> queryPageByConditions(@RequestBody Dict dict){

        return iDictService.queryPageByConditions(dict, dict.getPageNum(), dict.getPageSize());
    }

    /**
     * 字典左边导出
     */
    public void exportExcelAll(@RequestBody Dict excelParam, HttpServletResponse response) throws Exception {
        iDictService.exportExcel(excelParam,response);
    }
    /**
     * 字典左边导出
     */
    @RequestMapping("/leftExportExcelByModel")
    public void exportExcel(@RequestBody List<Long> ids,HttpServletResponse response) throws Exception {
        if(ids.size() == 0) {
            this.exportExcelAll(null,response);
        }
        else {
            iDictService.exportExcel(ids,response);
        }

    }
//    /**
//     * 字典左边导出自定义
//     */
//    @RequestMapping()
//    public void exportExcelByCustom(HttpServletRequest request, HttpServletResponse response, @RequestBody ExportExcelParamCommon excelParam) {
//
//    }

    /**
     * 字典左边导入模板下载
     */
    @RequestMapping("/leftImportExcelTemplate")
    public void exportExcelTemplate(HttpServletResponse response) throws IOException {
        iDictService.leftImportExcelTemplate(response);
    }

    /**
     * 字典左边导入
     */
    @RequestMapping("/leftImportExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iDictService.leftImportExcel(file,fileupload);
    }

    /**
     * 通过计费方式查找计费单位
     * @param chargeMethod
     * @return
     */
    @GetMapping("/queryBillingCombination")
    public List<BillingCombination> queryBillingCombination(String chargeMethod){
        Assert.notNull(chargeMethod,"通过计费方式查找计费单位缺少参数:chargeMethod");
        return iDictService.queryBillingCombination(chargeMethod);
    }

    /**
     * 通过计费方式查找计费单位
     * @param chargeMethod 计费方式名字集合
     * @return 计费方式编码-(计费单位-计费单位名称)
     */
    @PostMapping("/queryBillingCombinationMap")
    public Map<String, Map<String,String>> queryBillingCombinationMap(@RequestBody List<String> chargeMethod){
        return iDictService.queryBillingCombinationMap(chargeMethod);
    }

}
