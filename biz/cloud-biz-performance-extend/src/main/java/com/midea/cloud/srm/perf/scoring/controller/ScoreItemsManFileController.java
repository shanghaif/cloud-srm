package com.midea.cloud.srm.perf.scoring.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.perf.scoring.ScoreItemsManFile;
import com.midea.cloud.srm.perf.scoring.service.IScoreItemsManFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  绩效评分项目评分人附件表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-02-04 16:15:04
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/scoring/score-items-man-file")
public class ScoreItemsManFileController extends BaseController {

    @Autowired
    private IScoreItemsManFileService iScoreItemsManFileService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ScoreItemsManFile get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iScoreItemsManFileService.getById(id);
    }

    /**
    * 新增
    * @param scoreItemsManFile
    */
    @PostMapping("/add")
    public void add(@RequestBody ScoreItemsManFile scoreItemsManFile) {
        Long id = IdGenrator.generate();
        scoreItemsManFile.setScoreItemsManFileId(id);
        iScoreItemsManFileService.save(scoreItemsManFile);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iScoreItemsManFileService.removeById(id);
    }

    /**
    * 修改
    * @param scoreItemsManFile
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ScoreItemsManFile scoreItemsManFile) {
        iScoreItemsManFileService.updateById(scoreItemsManFile);
    }

    /**
    * 分页查询
    * @param scoreItemsManFile
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ScoreItemsManFile> listPage(@RequestBody ScoreItemsManFile scoreItemsManFile) {
        PageUtil.startPage(scoreItemsManFile.getPageNum(), scoreItemsManFile.getPageSize());
        QueryWrapper<ScoreItemsManFile> wrapper = new QueryWrapper<ScoreItemsManFile>(scoreItemsManFile);
        return new PageInfo<ScoreItemsManFile>(iScoreItemsManFileService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<ScoreItemsManFile> listAll() { 
        return iScoreItemsManFileService.list();
    }
 
}
