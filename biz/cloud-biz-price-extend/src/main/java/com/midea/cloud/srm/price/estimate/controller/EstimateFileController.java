package com.midea.cloud.srm.price.estimate.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.price.estimate.entity.EstimateFile;
import com.midea.cloud.srm.price.estimate.service.IEstimateFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  价格估算文件表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:36:39
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/estimate-file")
public class EstimateFileController extends BaseController {

    @Autowired
    private IEstimateFileService iEstimateFileService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public EstimateFile get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iEstimateFileService.getById(id);
    }

    /**
    * 新增
    * @param estimateFile
    */
    @PostMapping("/add")
    public void add(@RequestBody EstimateFile estimateFile) {
        Long id = IdGenrator.generate();
        estimateFile.setEstimateFileId(id);
        iEstimateFileService.save(estimateFile);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iEstimateFileService.removeById(id);
    }

    /**
    * 修改
    * @param estimateFile
    */
    @PostMapping("/modify")
    public void modify(@RequestBody EstimateFile estimateFile) {
        iEstimateFileService.updateById(estimateFile);
    }

    /**
    * 分页查询
    * @param estimateFile
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<EstimateFile> listPage(@RequestBody EstimateFile estimateFile) {
        PageUtil.startPage(estimateFile.getPageNum(), estimateFile.getPageSize());
        QueryWrapper<EstimateFile> wrapper = new QueryWrapper<EstimateFile>(estimateFile);
        return new PageInfo<EstimateFile>(iEstimateFileService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<EstimateFile> listAll() { 
        return iEstimateFileService.list();
    }
 
}
