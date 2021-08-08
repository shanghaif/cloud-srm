package com.midea.cloud.srm.sup.riskraider.r2.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.sup.riskraider.r2.service.IR2RiskLabelService;
import com.midea.cloud.srm.model.supplier.riskraider.r2.entity.R2RiskLabel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  风险标签数据表 前端控制器
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:19:50
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/cloud-biz-supplier-extend/r2-risk-label")
public class R2RiskLabelController extends BaseController {

    @Autowired
    private IR2RiskLabelService iR2RiskLabelService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public R2RiskLabel get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iR2RiskLabelService.getById(id);
    }

    /**
    * 新增
    * @param r2RiskLabel
    */
    @PostMapping("/add")
    public void add(@RequestBody R2RiskLabel r2RiskLabel) {
        Long id = IdGenrator.generate();
        r2RiskLabel.setRiskLabelId(id);
        iR2RiskLabelService.save(r2RiskLabel);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iR2RiskLabelService.removeById(id);
    }

    /**
    * 修改
    * @param r2RiskLabel
    */
    @PostMapping("/modify")
    public void modify(@RequestBody R2RiskLabel r2RiskLabel) {
        iR2RiskLabelService.updateById(r2RiskLabel);
    }

    /**
    * 分页查询
    * @param r2RiskLabel
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<R2RiskLabel> listPage(@RequestBody R2RiskLabel r2RiskLabel) {
        PageUtil.startPage(r2RiskLabel.getPageNum(), r2RiskLabel.getPageSize());
        QueryWrapper<R2RiskLabel> wrapper = new QueryWrapper<R2RiskLabel>(r2RiskLabel);
        return new PageInfo<R2RiskLabel>(iR2RiskLabelService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<R2RiskLabel> listAll() { 
        return iR2RiskLabelService.list();
    }
 
}
