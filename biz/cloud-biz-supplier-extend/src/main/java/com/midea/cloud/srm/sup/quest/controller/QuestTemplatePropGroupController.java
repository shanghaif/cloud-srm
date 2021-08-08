package com.midea.cloud.srm.sup.quest.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplatePropGroup;
import com.midea.cloud.srm.sup.quest.service.IQuestTemplatePropGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


/**
* <pre>
 *  问卷调查 前端控制器
 * </pre>
*
* @author bing5.wang@midea.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 16, 2021 6:29:05 PM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/quest/questTemplatePropGroup")
public class QuestTemplatePropGroupController extends BaseController {

    @Autowired
    private IQuestTemplatePropGroupService questTemplatePropGroupService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public QuestTemplatePropGroup get(Long id) {
        Assert.notNull(id, "id不能为空");
        return questTemplatePropGroupService.getById(id);
    }

    /**
    * 新增
    * @param questTemplatePropGroup
    */
    @PostMapping("/add")
    public void add(@RequestBody QuestTemplatePropGroup questTemplatePropGroup) {
        Long id = IdGenrator.generate();
        questTemplatePropGroup.setQuestTemplatePropGroupId(id);
        questTemplatePropGroupService.save(questTemplatePropGroup);
    }

    /**
     * 批量新增或者修改
     * @param questTemplatePropGroupList
     */
    @PostMapping("/batchSaveOrUpdate")
    public void batchSaveOrUpdate(@RequestBody List<QuestTemplatePropGroup> questTemplatePropGroupList) throws IOException{
         questTemplatePropGroupService.batchSaveOrUpdate(questTemplatePropGroupList);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        questTemplatePropGroupService.removeById(id);
    }
    /**
     * 批量删除
     * @param ids
     */
    @GetMapping("/bathDelete")
    public void bathDelete(@RequestBody List<Long> ids) throws IOException{
        questTemplatePropGroupService.batchDeleted(ids);
    }
    /**
    * 修改
    * @param questTemplatePropGroup
    */
    @PostMapping("/modify")
    public void modify(@RequestBody QuestTemplatePropGroup questTemplatePropGroup) {
        questTemplatePropGroupService.updateById(questTemplatePropGroup);
    }

    /**
    * 分页查询
    * @param questTemplatePropGroup
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<QuestTemplatePropGroup> listPage(@RequestBody QuestTemplatePropGroup questTemplatePropGroup) {
        return questTemplatePropGroupService.listPage(questTemplatePropGroup);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<QuestTemplatePropGroup> listAll() {
        return questTemplatePropGroupService.list();
    }
}
