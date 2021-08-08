package com.midea.cloud.srm.price.estimate.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.price.estimate.entity.EstimateAttrLine;
import com.midea.cloud.srm.price.estimate.service.IEstimateAttrLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  价格估算头表 前端控制器
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
@RequestMapping("/estimate-attr-line")
public class EstimateAttrLineController extends BaseController {

    @Autowired
    private IEstimateAttrLineService iEstimateAttrLineService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public EstimateAttrLine get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iEstimateAttrLineService.getById(id);
    }

    /**
    * 新增
    * @param estimateAttrLine
    */
    @PostMapping("/add")
    public void add(@RequestBody EstimateAttrLine estimateAttrLine) {
        Long id = IdGenrator.generate();
        estimateAttrLine.setAttrLineId(id);
        iEstimateAttrLineService.save(estimateAttrLine);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iEstimateAttrLineService.removeById(id);
    }

    /**
    * 修改
    * @param estimateAttrLine
    */
    @PostMapping("/modify")
    public void modify(@RequestBody EstimateAttrLine estimateAttrLine) {
        iEstimateAttrLineService.updateById(estimateAttrLine);
    }

    /**
    * 分页查询
    * @param estimateAttrLine
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<EstimateAttrLine> listPage(@RequestBody EstimateAttrLine estimateAttrLine) {
        PageUtil.startPage(estimateAttrLine.getPageNum(), estimateAttrLine.getPageSize());
        QueryWrapper<EstimateAttrLine> wrapper = new QueryWrapper<EstimateAttrLine>(estimateAttrLine);
        return new PageInfo<EstimateAttrLine>(iEstimateAttrLineService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<EstimateAttrLine> listAll() { 
        return iEstimateAttrLineService.list();
    }
 
}
