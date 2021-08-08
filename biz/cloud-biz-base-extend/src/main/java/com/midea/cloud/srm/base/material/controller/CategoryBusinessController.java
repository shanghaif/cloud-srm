package com.midea.cloud.srm.base.material.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.material.service.ICategoryBusinessService;
import com.midea.cloud.srm.model.base.material.entity.CategoryBusiness;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
*  <pre>
 *  物料小类业务小类维护表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-26 20:04:01
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/category-business")
public class CategoryBusinessController extends BaseController {

    @Autowired
    private ICategoryBusinessService iCategoryBusinessService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public CategoryBusiness get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iCategoryBusinessService.getById(id);
    }

    /**
    * 新增
    * @param categoryBusiness
    */
    @PostMapping("/add")
    public void add(@RequestBody CategoryBusiness categoryBusiness) {
        Long id = IdGenrator.generate();
        categoryBusiness.setCategoryBusinessId(id);
        iCategoryBusinessService.save(categoryBusiness);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iCategoryBusinessService.removeById(id);
    }

    /**
    * 修改
    * @param categoryBusiness
    */
    @PostMapping("/modify")
    public void modify(@RequestBody CategoryBusiness categoryBusiness) {
        iCategoryBusinessService.updateById(categoryBusiness);
    }

    /**
    * 分页条件查询
    * @param categoryBusiness
    * @return
    */
    @PostMapping("/listPageByParam")
    public PageInfo<CategoryBusiness> listPage(@RequestBody CategoryBusiness categoryBusiness) {
        return iCategoryBusinessService.listByParam(categoryBusiness);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<CategoryBusiness> listAll() { 
        return iCategoryBusinessService.list();
    }

    /**
     * Description 模板导入下载
     * @Param  [response]
     * @Author fansb3@meicloud.com
     * @Date 2020/9/30
     **/
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws Exception {
        iCategoryBusinessService.importModelDownload(response);
    }

    /**
     * Description 导入文件并返回数据
     * @Param  [file, fileupload]
     * @Author fansb3@meicloud.com
     * @Date 2020/9/30
     **/
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iCategoryBusinessService.importExcel(file, fileupload);
    }
 
}
