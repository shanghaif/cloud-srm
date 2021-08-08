package com.midea.cloud.srm.sup.quest.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.quest.dto.QuestTemplateDto;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestTemplate;
import com.midea.cloud.srm.model.supplier.quest.vo.QuestTemplateVo;
import com.midea.cloud.srm.sup.quest.service.IQuestTemplateService;
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
 *  修改日期: Apr 16, 2021 5:17:12 PM
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/quest/questTemplate")
public class QuestTemplateController extends BaseController {

    @Autowired
    private IQuestTemplateService questTemplateService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public QuestTemplate get(Long id) {
        Assert.notNull(id, "id不能为空");
        return questTemplateService.getById(id);
    }

    /**
     * 新增
     *
     * @param questTemplate
     */
    @PostMapping("/add")
    public void add(@RequestBody QuestTemplate questTemplate) {
        Long id = IdGenrator.generate();
        questTemplate.setQuestTemplateId(id);
        questTemplateService.save(questTemplate);
    }

    /**
     * 批量新增或者修改
     *
     * @param questTemplateList
     */
    @PostMapping("/batchSaveOrUpdate")
    public void batchSaveOrUpdate(@RequestBody List<QuestTemplate> questTemplateList) throws IOException {
        questTemplateService.batchSaveOrUpdate(questTemplateList);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        questTemplateService.removeById(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @GetMapping("/bathDelete")
    public void bathDelete(@RequestBody List<Long> ids) throws IOException {
        questTemplateService.batchDeleted(ids);
    }

    /**
     * 修改
     *
     * @param questTemplate
     */
    @PostMapping("/modify")
    public void modify(@RequestBody QuestTemplate questTemplate) {
        //如果是将模板设置为无效，则判断是否已经分配给了供应商
        if ("N".equals(questTemplate.getQuestTemplateStatus())) {
            Integer count = questTemplateService.checkUseBySupplier(questTemplate.getQuestTemplateId());
            if (count > 0) {
                throw new BaseException("该模板已分配给了供应商，不能置为无效");
            }
        }
        questTemplateService.updateById(questTemplate);
    }

    /**
     * 分页查询
     *
     * @param questTemplate
     * @return
     */
    @PostMapping("/listPageByParm")
    public PageInfo<QuestTemplate> listPageByParm(@RequestBody QuestTemplate questTemplate) {
        return questTemplateService.listPageByParm(questTemplate);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<QuestTemplate> listAll() {
        return questTemplateService.list();
    }


    /**
     * <pre>
     *  查询模板数据模板组模板标签组，标签组字段数据
     * </pre>
     *
     * @author xujj78@meicloud.com
     * @version 1.00.00
     *
     * <pre>
     *  修改记录
     *  修改后版本:
     *  修改人:
     *  修改日期: ${DATE} ${TIME}
     *  修改内容:
     * </pre>
     */
    @GetMapping("/questTemplateData")
    public QuestTemplateVo questTemplateData(Long questTemplateId) {

        return questTemplateService.getQuestTemplateById(questTemplateId);
    }

    /**
     * 保存或修改模板数据
     * @param questTemplateDto
     * @return
     */
    @PostMapping("/saveQuestTemplateData")
    public BaseResult saveQuestTemplateData(@RequestBody QuestTemplateDto questTemplateDto) {
        BaseResult<Long> result = BaseResult.build(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
        Long aLong = questTemplateService.saveQuestTemplateData(questTemplateDto);
        result.setData(aLong);
        return result;
    }



}
