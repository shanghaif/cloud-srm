package com.midea.cloud.srm.sup.quest.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplateOrg;
import com.midea.cloud.srm.sup.quest.service.IQuestTemplateOrgService;
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
 *  修改日期: Apr 16, 2021 5:32:42 PM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/quest/questTemplateOrg")
public class QuestTemplateOrgController extends BaseController {

    @Autowired
    private IQuestTemplateOrgService questTemplateOrgService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public QuestTemplateOrg get(Long id) {
        Assert.notNull(id, "id不能为空");
        return questTemplateOrgService.getById(id);
    }

    /**
    * 新增
    * @param questTemplateOrg
    */
    @PostMapping("/add")
    public void add(@RequestBody QuestTemplateOrg questTemplateOrg) {
        Long id = IdGenrator.generate();
        questTemplateOrg.setQuestTemplateOrgId(id);
        questTemplateOrgService.save(questTemplateOrg);
    }

    /**
     * 批量新增或者修改
     * @param questTemplateOrgList
     */
    @PostMapping("/batchSaveOrUpdate")
    public void batchSaveOrUpdate(@RequestBody List<QuestTemplateOrg> questTemplateOrgList) throws IOException{
         questTemplateOrgService.batchSaveOrUpdate(questTemplateOrgList);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        questTemplateOrgService.removeById(id);
    }
    /**
     * 批量删除
     * @param ids
     */
    @GetMapping("/bathDelete")
    public void bathDelete(@RequestBody List<Long> ids) throws IOException{
        questTemplateOrgService.batchDeleted(ids);
    }
    /**
    * 修改
    * @param questTemplateOrg
    */
    @PostMapping("/modify")
    public void modify(@RequestBody QuestTemplateOrg questTemplateOrg) {
        questTemplateOrgService.updateById(questTemplateOrg);
    }

    /**
    * 分页查询
    * @param questTemplateOrg
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<QuestTemplateOrg> listPage(@RequestBody QuestTemplateOrg questTemplateOrg) {
        return questTemplateOrgService.listPage(questTemplateOrg);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<QuestTemplateOrg> listAll() {
        return questTemplateOrgService.list();
    }
}
