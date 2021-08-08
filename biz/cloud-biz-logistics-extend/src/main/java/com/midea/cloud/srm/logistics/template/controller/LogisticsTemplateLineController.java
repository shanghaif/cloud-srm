package com.midea.cloud.srm.logistics.template.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.logistics.template.service.ILogisticsTemplateLineService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.template.entity.LogisticsTemplateLine;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Optional;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  物流采购申请模板行表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-27 16:26:04
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/logistics/logistics-template-line")
public class LogisticsTemplateLineController extends BaseController {

    @Autowired
    private ILogisticsTemplateLineService iLogisticsTemplateLineService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public LogisticsTemplateLine get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iLogisticsTemplateLineService.getById(id);
    }

    /**
    * 新增
    * @param logisticsTemplateLine
    */
    @PostMapping("/add")
    public void add(@RequestBody LogisticsTemplateLine logisticsTemplateLine) {
        Long id = IdGenrator.generate();
        logisticsTemplateLine.setTemplateLineId(id);
        iLogisticsTemplateLineService.save(logisticsTemplateLine);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iLogisticsTemplateLineService.removeById(id);
    }

    /**
    * 修改
    * @param logisticsTemplateLine
    */
    @PostMapping("/modify")
    public void modify(@RequestBody LogisticsTemplateLine logisticsTemplateLine) {
        iLogisticsTemplateLineService.updateById(logisticsTemplateLine);
    }

    /**
    * 分页查询
    * @param logisticsTemplateLine
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<LogisticsTemplateLine> listPage(@RequestBody LogisticsTemplateLine logisticsTemplateLine) {
        PageUtil.startPage(logisticsTemplateLine.getPageNum(), logisticsTemplateLine.getPageSize());
        QueryWrapper<LogisticsTemplateLine> wrapper = new QueryWrapper<LogisticsTemplateLine>(logisticsTemplateLine);
        return new PageInfo<LogisticsTemplateLine>(iLogisticsTemplateLineService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<LogisticsTemplateLine> listAll() { 
        return iLogisticsTemplateLineService.list();
    }

    /**
     * 根据头id获取模板行
     * @param headId
     * @return
     */
    @GetMapping("/listTemplateLinesByHeadId")
    public List<LogisticsTemplateLine> listTemplateLinesByHeadId(@RequestParam("headId") Long headId){
        Optional.ofNullable(headId).orElseThrow(() -> new BaseException(LocaleHandler.getLocaleMsg("物流模板头id为空.")));
        return iLogisticsTemplateLineService.list(Wrappers.lambdaQuery(LogisticsTemplateLine.class).eq(LogisticsTemplateLine::getHeadId, headId));
    }
 
}
