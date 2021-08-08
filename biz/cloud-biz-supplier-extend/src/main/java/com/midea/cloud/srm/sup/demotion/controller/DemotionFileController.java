package com.midea.cloud.srm.sup.demotion.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.demotion.entity.DemotionFile;
import com.midea.cloud.srm.sup.demotion.service.IDemotionFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  供应商升降级附件行表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-05 17:51:16
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/demotion/demotion-file")
public class DemotionFileController extends BaseController {

    @Autowired
    private IDemotionFileService iDemotionFileService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public DemotionFile get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iDemotionFileService.getById(id);
    }

    /**
    * 新增
    * @param demotionFile
    */
    @PostMapping("/add")
    public void add(@RequestBody DemotionFile demotionFile) {
        Long id = IdGenrator.generate();
        demotionFile.setAttachFileId(id);
        iDemotionFileService.save(demotionFile);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iDemotionFileService.removeById(id);
    }

    /**
    * 修改
    * @param demotionFile
    */
    @PostMapping("/modify")
    public void modify(@RequestBody DemotionFile demotionFile) {
        iDemotionFileService.updateById(demotionFile);
    }

    /**
    * 分页查询
    * @param demotionFile
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<DemotionFile> listPage(@RequestBody DemotionFile demotionFile) {
        PageUtil.startPage(demotionFile.getPageNum(), demotionFile.getPageSize());
        QueryWrapper<DemotionFile> wrapper = new QueryWrapper<DemotionFile>(demotionFile);
        return new PageInfo<DemotionFile>(iDemotionFileService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<DemotionFile> listAll() { 
        return iDemotionFileService.list();
    }
 
}
