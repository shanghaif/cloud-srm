package com.midea.cloud.srm.base.dict.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.dict.service.IDictLanguageService;
import com.midea.cloud.srm.model.base.dict.entity.DictLanguage;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;




import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
*  <pre>
 *   前端控制器
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-21 00:52:55
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/dict/base-dict-language")
public class DictLanguageController extends BaseController {

    @Autowired
    private IDictLanguageService iDictLanguageService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public DictLanguage get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iDictLanguageService.getById(id);
    }

    /**
    * 新增
    * @param dictLanguage
    */
    @PostMapping("/add")
    public void add(DictLanguage dictLanguage) {
        Long id = IdGenrator.generate();
        dictLanguage.setLanguageId(id);
        iDictLanguageService.save(dictLanguage);
    }
    
    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iDictLanguageService.removeById(id);
    }

    /**
    * 修改
    * @param dictLanguage
    */
    @PostMapping("/modify")
    public void modify(DictLanguage dictLanguage) {
        iDictLanguageService.updateById(dictLanguage);
    }

    /**
    * 分页查询
    * @param dictLanguage
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<DictLanguage> listPage(DictLanguage dictLanguage) {
        PageUtil.startPage(dictLanguage.getPageNum(), dictLanguage.getPageSize());
        QueryWrapper<DictLanguage> wrapper = new QueryWrapper<DictLanguage>(dictLanguage);
        return new PageInfo<DictLanguage>(iDictLanguageService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<DictLanguage> listAll() {
        return iDictLanguageService.list();
    }
 
}
