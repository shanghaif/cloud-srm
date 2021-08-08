//package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.github.pagehelper.PageInfo;
//import com.midea.cloud.common.utils.IdGenrator;
//import com.midea.cloud.common.utils.PageUtil;
//import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidScoreRuleLineConfigService;
//import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.entity.ScoreRuleLineConfig;
//import com.midea.cloud.srm.model.common.BaseController;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.Assert;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
//*  <pre>
// *  招标评分规则明细模板表 前端控制器
// * </pre>
//*
//* @author fengdc3@meiCloud.com
//* @version 1.00.00
//*
//*  <pre>
// *  修改记录
// *  修改后版本:
// *  修改人:
// *  修改日期: 2020-03-29 09:39:07
// *  修改内容:
// * </pre>
//*/
//@RestController
//@RequestMapping("/evaluation/ruleLineConfig")
//public class ScoreRuleLineConfigController extends BaseController {
//
//    @Autowired
//    private IBidScoreRuleLineConfigService iBidScoreRuleLineConfigService;
//
//    /**
//    * 获取
//    * @param id
//    */
//    @GetMapping("/get")
//    public ScoreRuleLineConfig get(Long id) {
//        Assert.notNull(id, "id不能为空");
//        return iBidScoreRuleLineConfigService.getById(id);
//    }
//
//    /**
//    * 新增
//    * @param scoreRuleLineConfig
//    */
//    @PostMapping("/add")
//    public void add(@RequestBody ScoreRuleLineConfig scoreRuleLineConfig) {
//        Long id = IdGenrator.generate();
//        scoreRuleLineConfig.setRuleLineConfigId(id);
//        iBidScoreRuleLineConfigService.save(scoreRuleLineConfig);
//    }
//
//    /**
//    * 删除
//    * @param id
//    */
//    @GetMapping("/delete")
//    public void delete(Long id) {
//        Assert.notNull(id, "id不能为空");
//        iBidScoreRuleLineConfigService.removeById(id);
//    }
//
//    /**
//    * 修改
//    * @param scoreRuleLineConfig
//    */
//    @PostMapping("/modify")
//    public void modify(@RequestBody ScoreRuleLineConfig scoreRuleLineConfig) {
//        iBidScoreRuleLineConfigService.updateById(scoreRuleLineConfig);
//    }
//
//    /**
//    * 分页查询
//    * @param scoreRuleLineConfig
//    * @return
//    */
//    @PostMapping("/listPage")
//    public PageInfo<ScoreRuleLineConfig> listPage(@RequestBody ScoreRuleLineConfig scoreRuleLineConfig) {
//        PageUtil.startPage(scoreRuleLineConfig.getPageNum(), scoreRuleLineConfig.getPageSize());
//        QueryWrapper<ScoreRuleLineConfig> wrapper = new QueryWrapper<ScoreRuleLineConfig>(scoreRuleLineConfig);
//        return new PageInfo<ScoreRuleLineConfig>(iBidScoreRuleLineConfigService.list(wrapper));
//    }
//
//    /**
//    * 查询所有
//    * @return
//    */
//    @PostMapping("/listAll")
//    public List<ScoreRuleLineConfig> listAll() {
//        return iBidScoreRuleLineConfigService.list();
//    }
//
//}
