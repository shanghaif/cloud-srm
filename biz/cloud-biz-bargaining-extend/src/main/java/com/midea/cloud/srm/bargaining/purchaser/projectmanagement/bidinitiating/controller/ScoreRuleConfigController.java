//package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.github.pagehelper.PageInfo;
//import com.midea.cloud.common.utils.PageUtil;
//import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidinitiating.service.IBidScoreRuleConfigService;
//import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.entity.ScoreRuleConfig;
//import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.evaluation.vo.ScoreRuleConfigVO;
//import com.midea.cloud.srm.model.common.BaseController;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.Assert;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
//*  <pre>
// *  招标评分规则模板表 前端控制器
// * </pre>
//*
//* @author fengdc3@meiCloud.com
//* @version 1.00.00
//*
//*  <pre>
// *  修改记录
// *  修改后版本:
// *  修改人:
// *  修改日期: 2020-03-29 09:39:06
// *  修改内容:
// * </pre>
//*/
//@RestController
//@RequestMapping("/evaluation/ruleConfig")
//public class ScoreRuleConfigController extends BaseController {
//
//    @Autowired
//    private IBidScoreRuleConfigService iBidScoreRuleConfigService;
//
//    /**
//    * 获取
//    * @param id
//    */
//    @GetMapping("/get")
//    public ScoreRuleConfig get(Long id) {
//        Assert.notNull(id, "id不能为空");
//        return iBidScoreRuleConfigService.getById(id);
//    }
//
//    /**
//     * 新增
//     * @param scoreRuleConfigVO
//     */
//    @PostMapping("/add")
//    public void add(@RequestBody ScoreRuleConfigVO scoreRuleConfigVO) {
//        iBidScoreRuleConfigService.saveScoreRuleAndLineConfig(scoreRuleConfigVO);
//    }
//
//    /**
//     * 修改
//     *
//     * @param scoreRuleConfigVO
//     */
//    @PostMapping("/modify")
//    public void modify(@RequestBody ScoreRuleConfigVO scoreRuleConfigVO) {
//        iBidScoreRuleConfigService.updateScoreRuleAndLineConfig(scoreRuleConfigVO);
//    }
//
//    /**
//    * 删除
//    * @param ruleConfigId
//    */
//    @GetMapping("/delete")
//    public void delete(Long ruleConfigId) {
//        iBidScoreRuleConfigService.removeScoreRuleConfigAndLineById(ruleConfigId);
//    }
//
//    /**
//    * 分页查询
//    * @param scoreRuleConfig
//    * @return
//    */
//    @PostMapping("/listPage")
//    public PageInfo<ScoreRuleConfig> listPage(@RequestBody ScoreRuleConfig scoreRuleConfig) {
//        PageUtil.startPage(scoreRuleConfig.getPageNum(), scoreRuleConfig.getPageSize());
//        QueryWrapper<ScoreRuleConfig> wrapper = new QueryWrapper<ScoreRuleConfig>(scoreRuleConfig);
//        return new PageInfo<ScoreRuleConfig>(iBidScoreRuleConfigService.list(wrapper));
//    }
//
//    /**
//    * 查询所有
//    * @return
//    */
//    @PostMapping("/listAll")
//    public List<ScoreRuleConfig> listAll() {
//        return iBidScoreRuleConfigService.list();
//    }
//
//}
