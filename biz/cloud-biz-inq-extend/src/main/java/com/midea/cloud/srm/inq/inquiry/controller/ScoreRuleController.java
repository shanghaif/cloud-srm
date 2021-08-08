package com.midea.cloud.srm.inq.inquiry.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.inq.inquiry.service.IScoreRuleService;
import com.midea.cloud.srm.model.inq.inquiry.entity.ScoreRule;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  招标评分规则表 前端控制器
 * </pre>
*
* @author zhongbh
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 18:46:55
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/inquiry/scoreRule")
public class ScoreRuleController extends BaseController {

    @Autowired
    private IScoreRuleService iScoreRuleService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ScoreRule get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iScoreRuleService.getById(id);
    }

    /**
    * 新增
    * @param scoreRule
    */
    @PostMapping("/add")
    public void add(@RequestBody ScoreRule scoreRule) {
        Long id = IdGenrator.generate();
        scoreRule.setScoreRuleId(id);
        iScoreRuleService.save(scoreRule);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iScoreRuleService.removeById(id);
    }

    /**
    * 修改
    * @param scoreRule
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ScoreRule scoreRule) {
        iScoreRuleService.updateById(scoreRule);
    }

    /**
    * 分页查询
    * @param scoreRule
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ScoreRule> listPage(@RequestBody ScoreRule scoreRule) {
        PageUtil.startPage(scoreRule.getPageNum(), scoreRule.getPageSize());
        QueryWrapper<ScoreRule> wrapper = new QueryWrapper<ScoreRule>(scoreRule);
        return new PageInfo<ScoreRule>(iScoreRuleService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<ScoreRule> listAll() { 
        return iScoreRuleService.list();
    }
 
}
