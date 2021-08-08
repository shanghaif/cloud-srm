package com.midea.cloud.srm.inq.scoremodel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.inq.scoremodel.service.IScoreRuleModelItemService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.scoremodel.entity.ScoreRuleModelItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  评分规则明细表 前端控制器
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-19 19:21:24
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/inq/scoreRuleModelItem")
public class ScoreRuleModelItemController extends BaseController {

    @Autowired
    private IScoreRuleModelItemService iScoreRuleModelItemService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public ScoreRuleModelItem get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iScoreRuleModelItemService.getById(id);
    }

    /**
     * 新增
     *
     * @param scoreRuleModelItem
     */
    @PostMapping("/add")
    public void add(@RequestBody ScoreRuleModelItem scoreRuleModelItem) {
        Long id = IdGenrator.generate();
        scoreRuleModelItem.setScoreRuleModelItemId(id);
        iScoreRuleModelItemService.save(scoreRuleModelItem);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iScoreRuleModelItemService.removeById(id);
    }

    /**
     * 修改
     *
     * @param scoreRuleModelItem
     */
    @PostMapping("/modify")
    public void modify(@RequestBody ScoreRuleModelItem scoreRuleModelItem) {
        iScoreRuleModelItemService.updateById(scoreRuleModelItem);
    }

    /**
     * 分页查询
     *
     * @param scoreRuleModelItem
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<ScoreRuleModelItem> listPage(@RequestBody ScoreRuleModelItem scoreRuleModelItem) {
        PageUtil.startPage(scoreRuleModelItem.getPageNum(), scoreRuleModelItem.getPageSize());
        QueryWrapper<ScoreRuleModelItem> wrapper = new QueryWrapper<ScoreRuleModelItem>(scoreRuleModelItem);
        return new PageInfo<ScoreRuleModelItem>(iScoreRuleModelItemService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<ScoreRuleModelItem> listAll() {
        return iScoreRuleModelItemService.list();
    }

}
