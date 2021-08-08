package com.midea.cloud.srm.sup.quest.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.quest.dto.QuestResultDto;
import com.midea.cloud.srm.model.supplier.quest.dto.QuestSupplierDto;
import com.midea.cloud.srm.model.supplier.quest.entity.QuestResult;
import com.midea.cloud.srm.model.supplier.quest.vo.QuestResultVo;
import com.midea.cloud.srm.sup.quest.service.IQuestResultService;
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
 *  修改日期: Apr 16, 2021 5:34:12 PM
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/quest/questResult")
public class QuestResultController extends BaseController {

    @Autowired
    private IQuestResultService questResultService;

    /**
     * 新增或修改
     * @param
     */
    @PostMapping("/saveOrUpdateQuestResultForm")
    public QuestResultDto saveOrUpdateQuestSupplierForm(@RequestBody QuestResultDto questResultDto) {
        return questResultService.saveOrUpdateQuestResultForm(questResultDto);
    }

    /**
     * 查询明细
     * @param questSupId
     * @return
     */
    @GetMapping("/getQuestResultDtoByQuestSupId")
    public QuestResultVo getQuestResultDtoByQuestSupId(Long questSupId) {

        return questResultService.getQuestResultVoByQuestSupId(questSupId);
    }

    /**
    * 分页查询
    * @param questResult
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<QuestResult> listPage(@RequestBody QuestResult questResult) {

        return questResultService.listPage(questResult);
    }
}
