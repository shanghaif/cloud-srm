package com.midea.cloud.srm.sup.quest.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplateProp;
import com.midea.cloud.srm.sup.quest.service.IQuestTemplatePropService;
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
 *  修改日期: Apr 16, 2021 5:33:01 PM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/quest/questTemplateProp")
public class QuestTemplatePropController extends BaseController {

    @Autowired
    private IQuestTemplatePropService questTemplatePropService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public QuestTemplateProp get(Long id) {
        Assert.notNull(id, "id不能为空");
        return questTemplatePropService.getById(id);
    }

    /**
    * 新增
    * @param questTemplateProp
    */
    @PostMapping("/add")
    public void add(@RequestBody QuestTemplateProp questTemplateProp) {
        Long id = IdGenrator.generate();
        questTemplateProp.setQuestTemplatePropId(id);
        questTemplatePropService.save(questTemplateProp);
    }

    /**
     * 批量新增或者修改
     * @param questTemplatePropList
     */
    @PostMapping("/batchSaveOrUpdate")
    public void batchSaveOrUpdate(@RequestBody List<QuestTemplateProp> questTemplatePropList) throws IOException{
         questTemplatePropService.batchSaveOrUpdate(questTemplatePropList);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        questTemplatePropService.removeById(id);
    }
    /**
     * 批量删除
     * @param ids
     */
    @GetMapping("/bathDelete")
    public void bathDelete(@RequestBody List<Long> ids) throws IOException{
        questTemplatePropService.batchDeleted(ids);
    }
    /**
    * 修改
    * @param questTemplateProp
    */
    @PostMapping("/modify")
    public void modify(@RequestBody QuestTemplateProp questTemplateProp) {
        questTemplatePropService.updateById(questTemplateProp);
    }

    /**
    * 分页查询
    * @param questTemplateProp
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<QuestTemplateProp> listPage(@RequestBody QuestTemplateProp questTemplateProp) {
        return questTemplatePropService.listPage(questTemplateProp);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<QuestTemplateProp> listAll() {
        return questTemplatePropService.list();
    }
}
