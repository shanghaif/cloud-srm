package com.midea.cloud.srm.inq.scoremodel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.inq.scoremodel.service.IScoreRuleModelItemService;
import com.midea.cloud.srm.inq.scoremodel.service.IScoreRuleModelService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.scoremodel.dto.ScoreRuleModelDto;
import com.midea.cloud.srm.model.inq.scoremodel.entity.ScoreRuleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  询价评分规则模板表 前端控制器
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-19 19:21:23
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/inq/scoreRuleModel")
public class ScoreRuleModelController extends BaseController {

    @Autowired
    private IScoreRuleModelService iScoreRuleModelService;

    @Autowired
    private IScoreRuleModelItemService iScoreRuleModelItemService;
    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public ScoreRuleModel get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iScoreRuleModelService.getById(id);
    }

    /**
     * 获取头信息
     *
     * @param scoreRuleModel
     */
    @PostMapping("/getHeadById")
    public ScoreRuleModelDto getHeadById(@RequestBody ScoreRuleModel scoreRuleModel) {
        Assert.notNull(scoreRuleModel.getScoreRuleModelId(), "id不能为空");
        return iScoreRuleModelService.getHeadById(scoreRuleModel.getScoreRuleModelId());
    }

    /**
     * 获取评分模板
     *
     * @param scoreRuleModel
     */
    @PostMapping("/getModelByTitle")
    public ScoreRuleModelDto getModelByCode(@RequestBody ScoreRuleModel scoreRuleModel) {
        Assert.notNull(scoreRuleModel.getTitle(), "模板编码不能为空");
        return iScoreRuleModelService.getModelByCode(scoreRuleModel.getTitle());
    }

    /**
     * 新增
     *
     * @param scoreRuleModel
     */
    @PostMapping("/add")
    public void add(@RequestBody ScoreRuleModel scoreRuleModel) {
        Long id = IdGenrator.generate();
        scoreRuleModel.setScoreRuleModelId(id);
        iScoreRuleModelService.save(scoreRuleModel);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iScoreRuleModelService.removeById(id);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("SCORE_RULE_MODEL_ID", id);
        iScoreRuleModelItemService.remove(queryWrapper);
    }

    /**
     * 修改
     *
     * @param scoreRuleModel
     */
    @PostMapping("/modify")
    public void modify(@RequestBody ScoreRuleModel scoreRuleModel) {
        iScoreRuleModelService.updateById(scoreRuleModel);
    }

    /**
     * 分页查询
     *
     * @param scoreRuleModel
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<ScoreRuleModel> listPage(@RequestBody ScoreRuleModel scoreRuleModel) {
        PageUtil.startPage(scoreRuleModel.getPageNum(), scoreRuleModel.getPageSize());
        String number = "";
        if(!StringUtil.isEmpty(scoreRuleModel.getScoreRuleModelNo())){
             number = scoreRuleModel.getScoreRuleModelNo();
        }
        scoreRuleModel.setScoreRuleModelNo(null);
        QueryWrapper<ScoreRuleModel> wrapper = new QueryWrapper<ScoreRuleModel>(scoreRuleModel);
        wrapper.like("SCORE_RULE_MODEL_NO",number);
        return new PageInfo<ScoreRuleModel>(iScoreRuleModelService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<ScoreRuleModel> listAll() {
        return iScoreRuleModelService.list();
    }

    /**
     * 保存
     *
     * @param modelDto
     */
    @PostMapping("/saveHead")
    public void saveAndUpdate(@RequestBody ScoreRuleModelDto modelDto) {
        Assert.notNull(modelDto.getModel(), "模板信息不能为空");
        iScoreRuleModelService.saveAndUpdate(modelDto);
    }

}
