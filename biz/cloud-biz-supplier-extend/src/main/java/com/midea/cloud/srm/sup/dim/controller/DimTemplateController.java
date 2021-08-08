package com.midea.cloud.srm.sup.dim.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.dim.dto.DimConfigDTO;
import com.midea.cloud.srm.model.supplier.dim.dto.DimTemplateDTO;
import com.midea.cloud.srm.model.supplier.dim.entity.DimTemplate;
import com.midea.cloud.srm.sup.dim.service.IDimTemplateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
*  <pre>
 *  供应商模板配置表 前端控制器
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-29 14:35:31
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/dim/dimTemplate")
public class DimTemplateController extends BaseController {

    @Autowired
    private IDimTemplateService iDimTemplateService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public DimTemplate get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iDimTemplateService.getById(id);
    }

    /**
    * 新增
    * @param dimTemplate
    */
    @PostMapping("/add")
    public void add(DimTemplate dimTemplate) {
        Long id = IdGenrator.generate();
        dimTemplate.setTemplateId(id);
        iDimTemplateService.save(dimTemplate);
    }
    
    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iDimTemplateService.removeById(id);
    }

    /**
    * 修改
    * @param dimTemplate
    */
    @PostMapping("/modify")
    public void modify(DimTemplate dimTemplate) {
        iDimTemplateService.updateById(dimTemplate);
    }

    /**
    * 分页查询
    * @param dimTemplate
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<DimTemplate> listPage(DimTemplate dimTemplate) {


        return iDimTemplateService.listPage(dimTemplate);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<DimTemplate> listAll() { 
        return iDimTemplateService.list();
    }

    /**
     * 新增
     * @param dimTemplate
     */
    @PostMapping("/saveOrUpdateTemplate")
    public DimTemplate saveOrUpdateTemplate(@RequestBody DimTemplate dimTemplate) {
        iDimTemplateService.checkTemplate(dimTemplate);
       return iDimTemplateService.saveOrUpdateTemplate(dimTemplate);
    }

    @GetMapping("/getByTemplateId")
    public DimTemplateDTO getByTemplateId(Long templateId){
        return iDimTemplateService.getByTemplateId(templateId);
    }


    @PostMapping("/getConfigByTemplate")
    public List<DimConfigDTO> getConfigByTemplate(@RequestBody DimTemplate dimTemplate){
        return iDimTemplateService.getConfigByTemplate(dimTemplate);
    }

    @GetMapping("/getDimTemplateCount")
    public int getDimTemplateCount(){
        return iDimTemplateService.count();
    }
}
