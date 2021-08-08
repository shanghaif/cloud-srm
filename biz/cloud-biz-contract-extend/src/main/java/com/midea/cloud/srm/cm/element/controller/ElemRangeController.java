package com.midea.cloud.srm.cm.element.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.cm.element.service.IElemRangeService;
import com.midea.cloud.srm.model.cm.element.entity.ElemRange;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  合同要素维护表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-12 16:29:03
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/elem-range")
public class ElemRangeController extends BaseController {

    @Autowired
    private IElemRangeService iElemRangeService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ElemRange get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iElemRangeService.getById(id);
    }

    /**
    * 新增
    * @param elemRange
    */
    @PostMapping("/add")
    public void add(@RequestBody ElemRange elemRange) {
        Long id = IdGenrator.generate();
        elemRange.setElemRangeId(id);
        iElemRangeService.save(elemRange);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iElemRangeService.removeById(id);
    }

    /**
    * 修改
    * @param elemRange
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ElemRange elemRange) {
        iElemRangeService.updateById(elemRange);
    }

    /**
    * 分页查询
    * @param elemRange
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ElemRange> listPage(@RequestBody ElemRange elemRange) {
        PageUtil.startPage(elemRange.getPageNum(), elemRange.getPageSize());
        QueryWrapper<ElemRange> wrapper = new QueryWrapper<ElemRange>(elemRange);
        return new PageInfo<ElemRange>(iElemRangeService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<ElemRange> listAll() { 
        return iElemRangeService.list();
    }
 
}
